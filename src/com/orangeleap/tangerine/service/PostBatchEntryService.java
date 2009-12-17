package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.customization.Picklist;
import org.springframework.validation.BindException;

import java.util.Date;
import java.util.Map;

public interface PostBatchEntryService {
    boolean executeBatchEntry(PostBatch batch, AbstractCustomizableEntity entity) throws BindException;

    void clearBatchErrorsInEntity(AbstractCustomizableEntity entity);

    boolean isValidPostedDate(String postedDateString);

    Date parsePostedDate(String postedDateString);

    void addBatchErrorToEntity(AbstractCustomizableEntity entity, String errorMsgKey, String... args);

    boolean hasBatchErrorsInEntity(AbstractCustomizableEntity entity);

    String getBatchErrorsInEntity(AbstractCustomizableEntity entity);

    void createBankProjectCodeMaps(Map<String, String> bankCodeMap, Map<String, String> projectCodeMap);

    void addBankProjectCodesToMap(Picklist picklist, Map<String, String> map);
}
