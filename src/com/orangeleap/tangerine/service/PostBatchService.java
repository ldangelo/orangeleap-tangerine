package com.orangeleap.tangerine.service;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.relationship.ConstituentTreeNode;

public interface PostBatchService {

    public Map<String, String> readAllowedGiftSelectFields();
    public Map<String, String> readAllowedGiftUpdateFields();
    public List<PostBatch> listBatchs(PostBatch postbatch);
    public PostBatch readBatch(Long batchId);
    public PostBatch maintainBatch(PostBatch postbatch);
    public List<AbstractPaymentInfoEntity> createBatchSelectionList(PostBatch postbatch);
    public List<AbstractPaymentInfoEntity> getBatchSelectionList(PostBatch postbatch);
	public PostBatch postBatch(PostBatch postbatch);

}