package com.price.streamwise.catalog.events.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import com.price.streamwise.catalog.events.model.ActionEnum;
import com.price.streamwise.catalog.events.model.CatalogChangeModel;
import com.price.streamwise.catalog.utils.UserContextHolder;


@Component
public class SimpleSourceBean {
    private final StreamBridge streamBridge;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    public SimpleSourceBean(StreamBridge streamBridge){
        this.streamBridge = streamBridge;
    }

    public void publishOrganizationChange(ActionEnum action, String organizationId){
       logger.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
        CatalogChangeModel change =  new CatalogChangeModel(
                CatalogChangeModel.class.getTypeName(),
                action.toString(),
                organizationId,
                UserContextHolder.getContext().getCorrelationId());

        streamBridge.send("catalogChangeTopic", change);
    }
}
