package com.orangeleap.tangerine.service.rule;

import org.apache.commons.logging.Log;
import org.drools.runtime.rule.ConsequenceException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.OrangeLeapRuleAgent;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.exception.DuplicateConstituentException;
import com.orangeleap.tangerine.type.RuleEventNameType;
import com.orangeleap.tangerine.type.RuleObjectType;
import com.orangeleap.tangerine.util.OLLogger;


public class CommunicationHistoryRulesInterceptor implements ApplicationContextAware, ApplicationListener {

	private static final Log logger = OLLogger.getLog(CommunicationHistoryRulesInterceptor.class);

	private ApplicationContext applicationContext;
	private String ruleFlowName;

	public void doApplyRules(CommunicationHistory communicationHistory) throws DuplicateConstituentException, ConstituentValidationException {

		String site = communicationHistory.getSite().getName();
       
		OrangeLeapRuleAgent olAgent = (OrangeLeapRuleAgent)applicationContext.getBean("OrangeLeapRuleAgent");
		OrangeLeapRuleBase olRuleBase = olAgent.getOrangeLeapRuleBase(RuleEventNameType.TOUCHPOINT_SAVE, site, false);
		OrangeLeapRuleSession olWorkingMemory = olRuleBase.newRuleSession();
		
		olWorkingMemory.put(RuleObjectType.TOUCHPOINT, communicationHistory);
		
		Constituent constituent = communicationHistory.getConstituent();
		if (constituent == null && communicationHistory.getConstituentId() != null) {
			constituent = ((ConstituentService)applicationContext.getBean("constituentService")).readConstituentById(communicationHistory.getConstituentId());
		}
		olWorkingMemory.put(RuleObjectType.CONSTITUENT, constituent);
		
		if (communicationHistory.getGiftId() != null && !communicationHistory.getGiftId().equals(0)) {
			Gift gift = ((GiftService)applicationContext.getBean("giftService")).readGiftById(communicationHistory.getGiftId());
			olWorkingMemory.put(RuleObjectType.GIFT, gift);
		}


		try {

			logger.debug("*** firing all rules");

			olWorkingMemory.executeRules(); 

		} catch (ConsequenceException ce) {
			logger.error("*** ConsequenceException firing rules for communicationHistory "+communicationHistory.getId(), ce);
		} catch (OrangeLeapConsequenceRuntimeException ce) {
			logger.error(ce);
		} catch (Exception e) {
			logger.error("*** Exception firing rules for communicationHistory "+communicationHistory.getId(), e);
		}

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public String getRuleFlowName() {
		return ruleFlowName;
	}

	public void setRuleFlowName(String ruleFlowName) {
		this.ruleFlowName = ruleFlowName;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {

	}
}