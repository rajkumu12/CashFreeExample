package com.knick.cashfreeexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cashfree.pg.CFPaymentService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;

public class MainActivity extends AppCompatActivity {

    Button btn_make_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_make_payment=findViewById(R.id.make_payment);

        btn_make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiatepayment();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Same request code for all payment APIs.
//        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d("m", "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d("resp", key + " : " + bundle.getString(key));
                    }
                }
        }
    }


    private void initiatepayment() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("Put your own Api Url").addConverterFactory(GsonConverterFactory.create())
                .build();
        final APi service = retrofit.create(APi.class);
      /*progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Registering......");
        progressDialog.setCancelable(false );
        progressDialog.show();*/

        retrofit2.Call<Res> call = service.getToken("1", "order123455");
        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("Token generated")){
                        String token=response.body().getCftoken();
                        Map<String, String> params = new HashMap<>();
                        params.put(PARAM_APP_ID, "xxxxxxxxxxxx86455cec24xxx");
                        params.put(PARAM_ORDER_ID, "order123455");
                        params.put(PARAM_ORDER_AMOUNT, "1");
                        params.put(PARAM_ORDER_NOTE, "Order for food");
                        params.put(PARAM_CUSTOMER_NAME,"Rajeev");
                        params.put(PARAM_CUSTOMER_PHONE, "97xxxxxxx");
                        params.put(PARAM_CUSTOMER_EMAIL, "raXXXXXXXXXXjan@gmail.com");
                        params.put(PARAM_ORDER_CURRENCY, "INR");
                        for(Map.Entry entry : params.entrySet()) {
                            Log.d("CFSKDSample", entry.getKey() + " " + entry.getValue());
                        }

                        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
                        cfPaymentService.setOrientation(0);
                        cfPaymentService.doPayment(MainActivity.this, params, token, "PROD", "#F8A31A", "#FFFFFF", false);
                    }else{
                        Toast.makeText(MainActivity.this, "msg2"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "msg3"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Res> call, Throwable t) {

                Toast.makeText(MainActivity.this, "msg4"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("lll",""+t.getMessage());
            }
        });
    }
}