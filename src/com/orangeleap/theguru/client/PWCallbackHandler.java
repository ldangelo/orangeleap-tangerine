package com.orangeleap.theguru.client;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.orangeleap.tangerine.util.ApplicationContextProvider;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.common.security.*;

public class PWCallbackHandler extends  OrangeLeapAuthentication implements CallbackHandler
{
	public void handle (Callback[] callbacks) throws IOException, UnsupportedCallbackException
    {
    	CasUtil.populateOrageLeapAuthenticationWithCasCredentials(this, System.getProperty("casClient.serviceUrl"));
    	
    	for (int i = 0; i < callbacks.length; i++)
        {
            if (callbacks[i] instanceof WSPasswordCallback)
            {
                WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
                System.out.println("identifier: "+pc.getIdentifer()+", usage: "+pc.getUsage());

                if (pc.getUsage() == WSPasswordCallback.USERNAME_TOKEN)
                {
                    // for passwords sent in digest mode we need to provide the password,
                    // because the original one can't be un-digested from the message

                    // we can throw either of the two Exception types if authentication fails
                    if (! this.getUserName().equals(pc.getIdentifer()))
                        throw new IOException("unknown user: "+pc.getIdentifer());

                    // this will throw an exception if the passwords don't match
                    pc.setPassword(this.getPassword());

                } else if (pc.getUsage() == WSPasswordCallback.USERNAME_TOKEN_UNKNOWN)
                {
                    // for passwords sent in cleartext mode we can compare passwords directly

                    if (! this.getUserName().equals(pc.getIdentifer()))
                        throw new IOException("unknown user: "+pc.getIdentifer());

                    // we can throw either of the two Exception types if authentication fails
                    if (! this.getPassword().equals(pc.getPassword()))
                        throw new IOException("password incorrect for user: "+pc.getIdentifer());
                }
            } else
            {
                throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }
        }
    }

}
