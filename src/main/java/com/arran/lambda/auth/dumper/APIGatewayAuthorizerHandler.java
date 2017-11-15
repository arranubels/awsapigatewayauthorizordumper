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

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.arran.lambda.auth.dumper.model.AuthPolicy;
import com.arran.lambda.auth.dumper.model.RequestAuthorizerContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.amazonaws.services.lambda.runtime.Context;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Handles IO for a Java Lambda function as a custom authorizer for API Gateway
 *
 * @author Jack Kohn
 *
 */
public class APIGatewayAuthorizerHandler implements RequestStreamHandler {

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        String s = IOUtils.toString(input);
        context.getLogger().log("Body: " + s);
    }
}
