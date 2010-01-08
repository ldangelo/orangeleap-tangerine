/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.controller.gift;

import com.orangeleap.tangerine.web.common.NoneStringTrimmerEditor;
import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GiftPledgeRecurringGiftLinesController extends AbstractMutableGridFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "pledgeService")
    private PledgeService pledgeService;

    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;

	@Override
	protected AbstractEntity findEntity(HttpServletRequest request) {
		return giftService.createDefaultGift(getConstituent(request));
	}

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
	    TangerineForm form = (TangerineForm) command;
	    Gift gift = (Gift) form.getDomainObject();
        DistributionLine defaultDistributionLine = determineDefaultDistributionLine(request, form);

        List<DistributionLine> commitmentLines = null;
        int numCommitments = 0;
        boolean isPledge = false;
        if (form.getFieldValue("selectedPledgeIds") != null) {
	        Set<String> selectedPledgeIds = StringUtils.commaDelimitedListToSet(form.getFieldValue("selectedPledgeIds").toString());
            commitmentLines = pledgeService.findDistributionLinesForPledges(selectedPledgeIds);
            numCommitments = selectedPledgeIds.size();
            isPledge = true;
        }
        else if (form.getFieldValue("selectedRecurringGiftIds") != null) {
	        Set<String> selectedRecurringGiftIds = StringUtils.commaDelimitedListToSet(form.getFieldValue("selectedRecurringGiftIds").toString());
            commitmentLines = recurringGiftService.findDistributionLinesForRecurringGifts(selectedRecurringGiftIds);
            numCommitments = selectedRecurringGiftIds.size();
            isPledge = false;
        }

	    // The Gift object has been changed - make sure the TangerineForm is updated also
	    List<DistributionLine> combinedLines = giftService.combineGiftCommitmentDistributionLines(gift.getDistributionLines(), commitmentLines,
                defaultDistributionLine, gift.getAmount(), numCommitments, getConstituent(request), isPledge);
	    gift.setDistributionLines(combinedLines);

	    rebindEntityValuesToForm(request, form, gift);
	    
        return new ModelAndView(getSuccessView(), getCommandName(), form);
    }

    private DistributionLine determineDefaultDistributionLine(HttpServletRequest request, TangerineForm form) {
        DistributionLine line = new DistributionLine(getConstituent(request));
        Gift gift = (Gift) form.getDomainObject();
        line.setAmount(gift.getAmount());
        line.setPercentage(new BigDecimal("100.00"));

        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(line);
        for (Map.Entry<String, Object> entry : form.getFieldMap().entrySet()) {
            if (entry.getKey().startsWith(new StringBuilder(StringConstants.TANG_DUMMY).append(StringConstants.DISTRIBUTION_LINES).toString()) &&
                    entry.getValue() != null && StringUtils.hasText(entry.getValue().toString())) {
                String unescapedKey = TangerineForm.unescapeFieldName(entry.getKey());
                unescapedKey = unescapedKey.replaceFirst(new StringBuilder(StringConstants.TANG_DUMMY).append(StringConstants.DISTRIBUTION_LINES).append("\\[0\\]\\.").toString(), StringConstants.EMPTY);
                if (unescapedKey.startsWith(StringConstants.CUSTOM_FIELD_MAP_START)) {
                    unescapedKey = new StringBuilder(unescapedKey).append(StringConstants.DOT_VALUE).toString();
                }
                Object value = entry.getValue();
                if (value instanceof String && value != null) {
                    value = NoneStringTrimmerEditor.replaceNone((String) value); // Make sure the word 'none' is replaced with empty string
                }
                bw.setPropertyValue(unescapedKey, value);
            }
        }
        return line;
    }
}
