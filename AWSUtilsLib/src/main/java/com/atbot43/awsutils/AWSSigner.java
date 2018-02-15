package com.atbot43.awsutils;

import android.support.annotation.NonNull;

import com.amazonaws.DefaultRequest;
import com.amazonaws.auth.AWS4Signer;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by mrdevxus on 2/9/18.
 */

//@Keep
public class AWSSigner {

    public static Request signRequestV4(@NonNull Request request,
                                        @NonNull AWSClientConfigs configs) throws IOException {

        AWS4Signer signer = new AWS4Signer();
        signer.setServiceName(configs.getServiceName());
        signer.setRegionName(configs.getRegion());

        AWSBasicCredentialsProvider credentialsProvider =
                new AWSBasicCredentialsProvider(configs.getAccessKey(), configs.getSecretKey());

        Request.Builder requestBuilder = request.newBuilder();
        DefaultRequest awsRequest = new DefaultRequest(configs.getServiceName());

        HttpUrl url = AWSHttpRequestUtils.setEndpoint(requestBuilder, awsRequest, request.url());

        AWSHttpRequestUtils.setQueryParams(awsRequest, url);
        AWSHttpRequestUtils.setHttpMethod(awsRequest, request.method());
        AWSHttpRequestUtils.setBody(awsRequest, request.body());

        signer.sign(awsRequest, credentialsProvider.getCredentials());
        awsRequest.addHeader("x-api-key", configs.getApiKey());

        AWSHttpRequestUtils.applyAwsHeaders(requestBuilder, awsRequest.getHeaders());
        return requestBuilder.build();
    }

    public static AWSSigningRequestV4Interceptor signingRequestV4Interceptor(@NonNull AWSClientConfigs configs) {
        return new AWSSigningRequestV4Interceptor(configs);
    }
}
