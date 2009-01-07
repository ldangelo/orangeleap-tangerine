package com.mpower.controller.address;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Address;

public class AddressManagerEditFormController extends AddressFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected Address findAddress(HttpServletRequest request) {
        Address address = super.findAddress(request);
        if (address.getId() != null) {
            Address originalAddress = addressService.readAddress(address.getId());

            try {
                address = (Address)BeanUtils.cloneBean(originalAddress);
                address.setId(null);
            }
            catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("findAddress: Exception occurred", e);
                }
            }
        }
        return address;
    }
}
