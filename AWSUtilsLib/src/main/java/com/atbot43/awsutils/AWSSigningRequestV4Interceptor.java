package com.atbot43.awsutils;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mrdevxus on 2/13/18.
 */

public class AWSSigningRequestV4Interceptor implements Interceptor {
    @NonNull
    private final AWSClientConfigs configs;


    public AWSSigningRequestV4Interceptor(@NonNull AWSClientConfigs configs) {
        this.configs = configs;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request signedRequest = AWSSigner.signRequestV4(chain.request(),configs);
        return chain.proceed(signedRequest);
    }
}