package com.mpower.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mpower.domain.PasswordChange;
import com.mpower.service.SessionServiceImpl;

public class PasswordChangeValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return PasswordChange.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordChange pwChange = (PasswordChange) target;

        String authenticatedPW = SessionServiceImpl.lookupUserPassword();
        if (!authenticatedPW.equals(pwChange.getCurrentPassword())) {
            errors.rejectValue("currentPassword", "currentPasswordIncorrect", null, "current password is incorrect");
            logger.debug("current password, " + authenticatedPW + ", didn't match " + pwChange.getCurrentPassword());
        } else if (!pwChange.getNewPassword().equals(pwChange.getNewPasswordConfirm())) {
            errors.rejectValue("newPasswordConfirm", "newPasswordConfirmIncorrect", null, "new passwords must match");
            logger.debug("new password, " + pwChange.getNewPassword() + ", didn't match confirm password " + pwChange.getNewPasswordConfirm());
        } else {
            // TODO: validated password requirements here
        }
    }
}
