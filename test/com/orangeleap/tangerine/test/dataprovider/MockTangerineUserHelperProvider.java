package com.orangeleap.tangerine.test.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.springframework.security.providers.cas.CasAuthenticationToken;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;

import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.security.TangerineAuthenticationDetails;

public class MockTangerineUserHelperProvider {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    public static TangerineUserHelper createMockUserHelper() {
        final Mockery tangerineUserHelperMock = new Mockery();
        final TangerineUserHelper tangerineUserHelper = tangerineUserHelperMock.mock(TangerineUserHelper.class);
        final List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");
        
        tangerineUserHelperMock.checking(new Expectations() {{
            allowing (tangerineUserHelper).lookupUserId(); will(returnValue(new Long(1)));
            allowing (tangerineUserHelper).lookupUserName(); will(returnValue("nryan"));
            allowing (tangerineUserHelper).lookupUserSiteName(); will(returnValue("company1"));
            //allowing (tangerineUserHelper).lookupUserPassword(); will(returnValue("password"));
            allowing (tangerineUserHelper).lookupUserRoles(); will(returnValue(roles));
            allowing (tangerineUserHelper).getToken(); will(returnValue( buildDummyToken("nryan","company1","password")));
        }});
        return tangerineUserHelper;
    }

    /* Create the dummy token, including GrantedAuthority and UserDetail */
    public static CasAuthenticationToken buildDummyToken(String username, String site, String password) {

        GrantedAuthority[] ga = new GrantedAuthority[1];
        ga[0] = new GrantedAuthorityImpl("ROLE_USER");
         User user = new User(username,password,true,true,true,true,ga);
        Assertion assertion = new AssertionImpl(username);

        // Create a fake CAS authentication token
        CasAuthenticationToken token = new CasAuthenticationToken("tangerine-client-key", user, password,ga,user,assertion);

        TangerineAuthenticationDetails details = new TangerineAuthenticationDetails();
        details.setUserName(username);
        details.setSite(site);
        details.setPersonId(0L);
        token.setDetails(details);

        return token;

    }
}
