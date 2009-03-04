package com.mpower.dao.ibatis;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.GiftDao;
import com.mpower.dao.util.QueryUtil;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.DistributionLine;
import com.mpower.domain.model.paymentInfo.Gift;

@Repository("giftDAO")
public class IBatisGiftDao extends AbstractIBatisDao implements GiftDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisGiftDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public Gift maintainGift(final Gift gift) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainGift: gift = " + gift);
        }
        Gift aGift = (Gift)insertOrUpdate(gift, "GIFT");
        // TODO: delete distribution lines?
        if (gift.getDistributionLines() != null) {
            for (DistributionLine line : aGift.getDistributionLines()) {
                if (line.getGiftId() == null || line.getGiftId() <= 0) {
                    line.setGiftId(aGift.getId());
                }
            }
        }
        batchInsertOrUpdate(gift.getDistributionLines(), "DISTRO_LINE");
        return aGift;
    }

    @Override
    public Gift readGiftById(Long giftId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftById: giftId = " + giftId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", giftId);
        return (Gift)getSqlMapClientTemplate().queryForObject("SELECT_GIFT_BY_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readGiftsByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftsByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> searchGifts(Map<String, Object> searchparams)
    {
    	Map<String, Object> params = setupParams();
    	QueryUtil.translateSearchParamsToIBatisParams(searchparams, params, fieldMap);
    	
    	List<Gift> gifts = getSqlMapClientTemplate().queryForList("SELECT_GIFT_BY_SEARCH_TERMS", params);
    	return gifts;
    }
    
    // These are the fields we support for searching.
    private Map<String, String> fieldMap = new HashMap<String, String>();
    {
    	
    	// Constituent
    	fieldMap.put("person.accountNumber", "CONSTITUENT_ID");
    	fieldMap.put("person.firstName", "FIRST_NAME");
    	fieldMap.put("person.lastName", "LAST_NAME");
    	fieldMap.put("person.organizationName", "ORGANIZATION_NAME");

    	// Address
    	fieldMap.put("postalCode", "POSTAL_CODE");
    	
    	// Gift
    	fieldMap.put("referenceNumber", "GIFT_ID");
    	fieldMap.put("amount", "AMOUNT");

    }

    @Override
    public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate) {
        if (logger.isDebugEnabled()) {
            logger.debug("analyzeMajorDonor: constituentId = " + constituentId + " beginDate = " + beginDate + " currentDate = " + currentDate);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("beginDate", beginDate);
        params.put("currentDate", currentDate);
        BigDecimal bd = (BigDecimal)getSqlMapClientTemplate().queryForObject("ANALYZE_FOR_MAJOR_DONOR", params);
        if (bd == null) {
            return 0.00d;
        }
        return bd.doubleValue();
    }
    
	@SuppressWarnings("unchecked")
    @Override
    public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate) {
        if (logger.isDebugEnabled()) {
            logger.debug("analyzeLapsedDonor:  beginDate = " + beginDate + " currentDate = " + currentDate);
        }
        Map<String, Object> params = setupParams();
        params.put("beginDate", beginDate);
        params.put("currentDate", currentDate);
        return getSqlMapClientTemplate().queryForList("ANALYZE_FOR_LAPSED_DONOR", params);
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<Gift> readAllGiftsBySite() {
        if (logger.isDebugEnabled()) {
            logger.debug("readAllGiftsBySite:");
        }
        Map<String, Object> params = setupParams();
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_GIFTS_BY_SITE", params);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readGiftsByCommitmentId(Long commitmentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftsByCommitmentId: commitmentId = " + commitmentId);
        }
        Map<String, Object> params = setupParams();
        params.put("commitmentId", commitmentId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_COMMITMENT_ID", params);
    }

    @Override
    public BigDecimal readGiftsReceivedSumByCommitmentId(Long commitmentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftsReceivedSumByCommitmentId: commitmentId = " + commitmentId);
        }
        Map<String, Object> params = setupParams();
        params.put("commitmentId", commitmentId);
        BigDecimal result = (BigDecimal) getSqlMapClientTemplate().queryForObject("READ_GIFTS_RECEIVED_SUM_BY_COMMITMENT_ID", params);
        if (result == null) {
            result = BigDecimal.ZERO;
        }
        return result;
    }
}
