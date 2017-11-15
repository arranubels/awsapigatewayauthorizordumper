/*
* Copyright 2015-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at
*
*     http://aws.amazon.com/apache2.0/
*
* or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/
package com.arran.lambda.auth.dumper;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.arran.lambda.auth.dumper.model.AuthPolicy;
import com.arran.lambda.auth.dumper.model.RequestAuthorizerContext;
import com.arran.lambda.auth.dumper.model.TokenAuthorizerContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;

import com.amazonaws.services.lambda.runtime.Context;

import java.util.HashMap;

/**
 * Handles IO for a Java Lambda function as a custom authorizer for API Gateway
 *
 * @author Jack Kohn
 *
 */
public class APIGatewayAuthorizerHandler implements RequestHandler<RequestAuthorizerContext, AuthPolicy> {

    @Override
    public AuthPolicy handleRequest(RequestAuthorizerContext input, Context context) {

        ObjectMapper om = new ObjectMapper();
        try {
            String j = om.writeValueAsString(input);
            context.getLogger().log("Body: " + j);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        context.getLogger().log("AuthToken: " + input.getAuthorizationToken());
        context.getLogger().log("Method ARN: " + input.getMethodArn());
        context.getLogger().log("Type: " + input.getType());

        String methodArn = input.getMethodArn();
        if (methodArn == null) {
            methodArn = "";
        }
        String resource = ""; // root resource
        String region = ""; // root resource
        String awsAccountId = ""; // root resource
        String restApiId = ""; // root resource
        String stage = ""; // root resource
        String httpMethod = ""; // root resource
        String[] arnPartials = methodArn.split(":");
        if (arnPartials.length > 3) {
            region = arnPartials[3];
            if (arnPartials.length > 4) {
                awsAccountId = arnPartials[4];
                if (arnPartials.length > 5) {
                    String[] apiGatewayArnPartials = arnPartials[5].split("/", 4);
                    if (apiGatewayArnPartials.length > 0) {
                        restApiId = apiGatewayArnPartials[0];
                        if (apiGatewayArnPartials.length > 1) {
                            stage = apiGatewayArnPartials[1];
                            if (apiGatewayArnPartials.length > 2) {
                                httpMethod = apiGatewayArnPartials[2];
                                if (apiGatewayArnPartials.length == 4) {
                                    resource = apiGatewayArnPartials[3];
                                }
                            }
                        }
                    }
                }
            }
        }

        context.getLogger().log("Method " + httpMethod);
        context.getLogger().log("Path " + resource);

        String principalId = "";

        HashMap<String, Object> newContext = new HashMap<>();

        AuthPolicy authPolicy = new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getAllowAllPolicy(region, awsAccountId, restApiId, stage));
        authPolicy.setContext(newContext);
        return authPolicy;
    }

}
