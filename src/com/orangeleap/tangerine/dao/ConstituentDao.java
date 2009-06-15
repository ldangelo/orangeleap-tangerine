package com.orangeleap.tangerine.dao;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Constituent;

public interface ConstituentDao {

    public Constituent readConstituentById(Long id);

    public Constituent readConstituentByAccountNumber(String accountNumber);

    public List<Constituent> readAllConstituentsBySite();

    public List<Constituent> readAllConstituentsBySite(String sortColumn, String dir, int start, int limit);

    public int getConstituentCountBySite();

    public Constituent readConstituentByLoginId(String loginId);

    public List<Constituent> readConstituentsByIds(List<Long> ids);

    public Constituent maintainConstituent(Constituent constituent);

    public List<Constituent> searchConstituents(Map<String, Object> params);

    public List<Constituent> searchConstituents(Map<String, Object> params, List<Long> ignoreIds);

	public List<Constituent> readAllConstituentsByAccountRange(Long fromId, Long toId);

	public List<Constituent> findConstituents(Map<String, Object> params,
			List<Long> ignoreIds);

}
