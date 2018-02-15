package com.atbot43.androidlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.atbot43.awsutils.AWSClientConfigs;
import com.atbot43.awsutils.AWSSigningRequestV4Interceptor;
import com.atbot43.awsutils.Signer;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    private static final String TAG = RetrofitActivity.class.getSimpleName();

    private final String baseUrl = "https://sit-api.central.tech/v1/";
    private final String accessKey = "AKIAJFRZ35RIVNVNQ4NQ";
    private final String secretKey = "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";
    private final String apiKey = "4KtWVSnAdm3kFuHygoWAo1dssKPMHGmd5pU1b00E";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            requestWithRetrofit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestWithRetrofit() {
        AWSClientConfigs configs = new AWSClientConfigs(accessKey, secretKey, apiKey);

        AWSSigningRequestV4Interceptor awsInterceptor = Signer.signingRequestV4Interceptor(configs);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(awsInterceptor)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiService service = retrofit.create(ApiService.class);
        retrofit2.Call<CustomerDao> call = service.getCustomer("1051182D-8C8C-43FB-B8E4-1A02B8ED9B27");

        call.enqueue(new retrofit2.Callback<CustomerDao>() {
            @Override
            public void onResponse(retrofit2.Call<CustomerDao> call, retrofit2.Response<CustomerDao> response) {

                CustomerDao customerResponse = response.body();
                Log.i(TAG, "Card no : " + customerResponse.getCardNo());
            }

            @Override
            public void onFailure(retrofit2.Call<CustomerDao> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
