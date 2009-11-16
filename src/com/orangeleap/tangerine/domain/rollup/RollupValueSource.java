package com.orangeleap.tangerine.domain.rollup;

import com.orangeleap.tangerine.domain.Constituent;

// Gift, Adjusted Gift, Pledge, etc.
public interface RollupValueSource {
	
	public Constituent getConstituent();
	public Long getConstituentId();
	
}
