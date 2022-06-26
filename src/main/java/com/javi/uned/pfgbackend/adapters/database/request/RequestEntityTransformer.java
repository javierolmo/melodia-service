package com.javi.uned.pfgbackend.adapters.database.request;

import com.javi.uned.pfgbackend.domain.sheet.model.Request;

public class RequestEntityTransformer {

    private RequestEntityTransformer() {

    }

    public static Request toDomainObject(RequestEntity requestEntity) {

        return new Request(
                requestEntity.getId(),
                requestEntity.getUserId(),
                requestEntity.getStartDateTime(),
                requestEntity.getEndDateTime(),
                requestEntity.getAzfCode(),
                requestEntity.getSpecs()
        );
    }

    public static RequestEntity toEntity(Request request) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setId(request.getId());
        requestEntity.setUserId(request.getUserId());
        requestEntity.setStartDateTime(request.getStartDateTime());
        requestEntity.setEndDateTime(request.getEndDateTime());
        requestEntity.setAzfCode(request.getAzfCode());
        requestEntity.setSpecs(request.getSpecs());
        return requestEntity;
    }

}
