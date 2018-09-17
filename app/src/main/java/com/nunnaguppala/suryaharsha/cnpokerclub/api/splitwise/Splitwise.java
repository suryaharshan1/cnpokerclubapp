package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Group;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ListExpenses;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ListGroups;

import java.io.IOException;

public class Splitwise extends AbstractGoogleJsonClient{

    public static final String DEFAULT_ROOT_URL = "https://www.splitwise.com/api/";

    public static final String DEFAULT_SERVICE_PATH = "v3.0/";

    public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

    public Splitwise(HttpTransport httpTransport, JsonFactory jsonFactory,
                     HttpRequestInitializer httpRequestInitializer){
        this(new Builder(httpTransport, jsonFactory, httpRequestInitializer));

    }

    protected Splitwise(Builder builder) {
        super(builder);
    }

    @Override
    protected void initialize(AbstractGoogleClientRequest<?> httpClientRequest) throws IOException {
        super.initialize(httpClientRequest);
    }

    public Groups groups(){
      return new Groups();
    }

    public Expenses expenses() {
        return new Expenses();
    }

    public class Expenses {
        public ListExpensesRequest listExpenses(int groupId) throws IOException{
            ListExpensesRequest request = new ListExpensesRequest(groupId);
            initialize(request);
            return request;
        }

        public class ListExpensesRequest extends SplitwiseRequest<ListExpenses> {
            private static final String REST_PATH = "get_expenses/";

            protected ListExpensesRequest(int groupId) {
                super(Splitwise.this,
                        HttpMethods.GET,
                        REST_PATH+"?group_id="+groupId+"&limit=0",
                        null,
                        ListExpenses.class);
            }

            @Override
            public ListExpensesRequest set(String fieldName, Object value) {
                return (ListExpensesRequest) super.set(fieldName, value);
            }
        }
    }

    public class Groups {
        public ListGroupsRequest listGroups() throws IOException{
            ListGroupsRequest request = new ListGroupsRequest();
            initialize(request);
            return request;
        }

        public GetGroupRequest getGroup(int groupId) throws IOException{
            GetGroupRequest request = new GetGroupRequest(groupId);
            initialize(request);
            return request;
        }

        public class GetGroupRequest extends SplitwiseRequest<Group> {
            private static final String REST_PATH = "get_group/";

            protected GetGroupRequest(int groupId) {
                super(Splitwise.this,
                        HttpMethods.GET,
                        REST_PATH+groupId,
                        null,
                        Group.class);
            }

            @Override
            public GetGroupRequest set(String fieldName, Object value) {
                return (GetGroupRequest) super.set(fieldName, value);
            }
        }

        public class ListGroupsRequest extends SplitwiseRequest<ListGroups> {

            private static final String REST_PATH = "get_groups";

            protected ListGroupsRequest() {
                super(Splitwise.this,
                        HttpMethods.GET,
                        REST_PATH,
                        null,
                        ListGroups.class);
            }

            @Override
            public ListGroupsRequest set(String fieldName, Object value) {
                return (ListGroupsRequest) super.set(fieldName, value);
            }
        }
    }

    public static final class Builder extends AbstractGoogleJsonClient.Builder {

        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer){
            super(httpTransport, jsonFactory, DEFAULT_ROOT_URL, DEFAULT_SERVICE_PATH, httpRequestInitializer, false);
        }

        @Override
        public Splitwise build() {
            return new Splitwise(this);
        }

        @Override
        public Builder setRootUrl(String rootUrl) {
            return (Builder) super.setRootUrl(rootUrl);
        }

        @Override
        public Builder setServicePath(String servicePath) {
            return (Builder) super.setServicePath(servicePath);
        }

        @Override
        public Builder setGoogleClientRequestInitializer(
                GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder) super
                    .setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }

        @Override
        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
        }

        @Override
        public Builder setApplicationName(String applicationName) {
            return (Builder) super.setApplicationName(applicationName);
        }

        @Override
        public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
            return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
        }

        @Override
        public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
            return (Builder) super
                    .setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
        }

        @Override
        public Builder setSuppressAllChecks(boolean suppressAllChecks) {
            return (Builder) super.setSuppressAllChecks(suppressAllChecks);
        }

        public Builder setSplitwiseRequestInitializer(
                SplitwiseRequestInitializer splitwiseRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer(splitwiseRequestInitializer);
        }
    }
}
