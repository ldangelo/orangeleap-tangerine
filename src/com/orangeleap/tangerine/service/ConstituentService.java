package com.orangeleap.tangerine.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindException;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface ConstituentService {

    public Constituent maintainConstituent(Constituent constituent) throws ConstituentValidationException, BindException;

    public Constituent readConstituentById(Long id);

    public Constituent readConstituentByAccountNumber(String accountNumber);

    public Constituent readConstituentByLoginId(String id);

	public List<Constituent> searchConstituents(Map<String, Object> params);

    public List<Constituent> searchConstituents(Map<String, Object> params, List<Long> ignoreIds);
    
    public List<Constituent> findConstituents(Map<String, Object> params, List<Long> ignoreIds);

    public Constituent createDefaultConstituent();

    public List<Constituent> analyzeLapsedDonor(Date beginDate, Date currentDate);

    public void setLapsedDonor(Long constituentId);

    public List<Constituent> readAllConstituentsBySite();

    public List<Constituent> readAllConstituentsBySite(SortInfo sort);

    public int getConstituentCountBySite();

	public List<Constituent> readAllConstituentsByAccountRange(Long fromId, Long toId);

/*	boolean hasReceivedCommunication(Long constituentId, String commType);

*/	
	boolean hasReceivedCommunication(Long constituentId, String commType,
			int number, String timeUnits);
	
}
