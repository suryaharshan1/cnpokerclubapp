package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise;

import android.text.TextUtils;


import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.ObjectParser;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.SplitwiseResponse;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class SplitwiseRequest<T> extends AbstractGoogleJsonClientRequest<T> {

    public SplitwiseRequest(Splitwise client, String requestMethod,
                          String uriTemplate, HttpContent content, Class<T> responseClass) {
        super(client,
                requestMethod,
                uriTemplate,
                content,
                responseClass);
    }

    @Override
    public Splitwise getAbstractGoogleClient() {
        return (Splitwise) super.getAbstractGoogleClient();
    }

    @Override
    public SplitwiseRequest<T> setDisableGZipContent(boolean disableGZipContent) {
        return (SplitwiseRequest<T>) super.setDisableGZipContent(disableGZipContent);
    }

    @Override
    public SplitwiseRequest<T> setRequestHeaders(HttpHeaders headers) {
        return (SplitwiseRequest<T>) super.setRequestHeaders(headers);
    }

    @Override
    public SplitwiseRequest<T> set(String fieldName, Object value) {
        return (SplitwiseRequest<T>) super.set(fieldName, value);
    }

    @Override
    public T execute() throws IOException {
        HttpResponse response = super.executeUnparsed();
        ObjectParser parser = response.getRequest().getParser();
        // This will degrade parsing performance but is an inevitable workaround
        // for the inability to parse JSON arrays.
        String content = response.parseAsString();
        if (response.isSuccessStatusCode()
                && !TextUtils.isEmpty(content)
                && content.charAt(0) == '[') {
            content = TextUtils.concat("{\"", SplitwiseResponse.KEY_DATA, "\":", content, "}")
                    .toString();
        }
        Reader reader = new StringReader(content);
        return parser.parseAndClose(reader, getResponseClass());
    }
}
