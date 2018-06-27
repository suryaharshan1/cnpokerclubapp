package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer;

import java.io.IOException;

public class SplitwiseRequestInitializer extends CommonGoogleJsonClientRequestInitializer {
    public SplitwiseRequestInitializer() {
        super();
    }

    @Override
    protected void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> request)
            throws IOException {
        super.initializeJsonRequest(request);
        initializeSplitwiseRequest((SplitwiseRequest<?>) request);
    }

    protected void initializeSplitwiseRequest(SplitwiseRequest<?> request)
            throws java.io.IOException {
    }
}
