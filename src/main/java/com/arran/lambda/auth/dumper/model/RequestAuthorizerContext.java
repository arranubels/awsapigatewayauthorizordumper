package com.arran.lambda.auth.dumper.model;

public class RequestAuthorizerContext {
    String HeaderAuth1, QueryString1, StageVar1, AccountId;
    private String authorizationToken;
    private String methodArn;
    private String type;

    public String getHeaderAuth1() {
        return HeaderAuth1;
    }

    public void setHeaderAuth1(String headerAuth1) {
        HeaderAuth1 = headerAuth1;
    }

    public String getQueryString1() {
        return QueryString1;
    }

    public void setQueryString1(String queryString1) {
        QueryString1 = queryString1;
    }

    public String getStageVar1() {
        return StageVar1;
    }

    public void setStageVar1(String stageVar1) {
        StageVar1 = stageVar1;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getMethodArn() {
        return methodArn;
    }

    public void setMethodArn(String methodArn) {
        this.methodArn = methodArn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
