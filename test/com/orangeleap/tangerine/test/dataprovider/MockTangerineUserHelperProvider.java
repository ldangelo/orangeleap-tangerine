package com.orangeleap.tangerine.test.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

import com.orangeleap.common.security.OrangeLeapSystemAuthenticationToken;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class MockTangerineUserHelperProvider {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
    public static TangerineUserHelper createMockUserHelper() {
        final Mockery tangerineUserHelperMock = new Mockery();
        final TangerineUserHelper tangerineUserHelper = tangerineUserHelperMock.mock(TangerineUserHelper.class);
        final List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");
        
        tangerineUserHelperMock.checking(new Expectations() {{
            allowing (tangerineUserHelper).lookupUserId(); will(returnValue(100L));
            allowing (tangerineUserHelper).lookupUserName(); will(returnValue("nryan"));
            allowing (tangerineUserHelper).lookupUserSiteName(); will(returnValue("company1"));
            allowing (tangerineUserHelper).lookupUserPassword(); will(returnValue("password"));
            allowing (tangerineUserHelper).lookupUserRoles(); will(returnValue(roles));
            allowing (tangerineUserHelper).getToken(); will(returnValue(new OrangeLeapSystemAuthenticationToken("test", "test", "pass", "company1", 
            		new GrantedAuthority[] {new GrantedAuthorityImpl("ROLE_SUPER_ADMIN"), new GrantedAuthorityImpl("ROLE_USER")})));
        }});
        return tangerineUserHelper;
    }
}

