package com.atbot43.androidlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.atbot43.awsutils.*;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    private final String requestUrl = "https://sit-api.central.tech/v1/customer/1051182D-8C8C-43FB-B8E4-1A02B8ED9B27";
    private final String accessKey = "AKIAJFRZ35RIVNVNQ4NQ";
    private final String secretKey = "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";
    private final String apiKey = "4KtWVSnAdm3kFuHygoWAo1dssKPMHGmd5pU1b00E";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            requestWithOkHttpRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestWithOkHttpRequest() throws Exception {

        AWSClientConfigs configs = new AWSClientConfigs(accessKey, secretKey, apiKey);

        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        Request signedRequest = AWSSigner.signRequestV4(request, configs);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        client.newCall(signedRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.e(responseHeaders.name(i), responseHeaders.value(i));
                }

                Log.e("response", response.body().string());
            }
        });
    }
}
