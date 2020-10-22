package ke.co.corellia.psklok;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.io.IOException;
import java.math.BigDecimal;

import ke.co.corellia.psklok.Config;
import ke.co.corellia.psklok.LazyInitializedSingletonExample;
import ke.co.corellia.psklok.PaymentDetails;
import ke.co.corellia.psklok.R;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class wallet extends AppCompatActivity {
    private static final int PAYPAL_REQUEST_CODE = 7777;

    LazyInitializedSingletonExample instance1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    Button btnPayNow;
    EditText edtAmount;
   TextView tc ;

    String amount = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        instance1 = LazyInitializedSingletonExample.getInstance();
        getSupportActionBar().hide();
        cwallet myTask = new cwallet(instance1.getCustomerid());
        myTask.execute();

        //start paypal service
       // Intent intent = new Intent(this,PayPalService.class);
      //  intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
       // startService(intent);

Button btn  = findViewById(R.id.btnShare);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent2 = new Intent(wallet.this, Walletshare.class);
        startActivity(intent2);// Activity is started with requestCode 2btn
    }
});



        btnPayNow = findViewById(R.id.btnPayNow);
        edtAmount = findViewById(R.id.edtAmount);
        tc= findViewById(R.id.txwallet);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPayment();
                amount = edtAmount.getText().toString();

                LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
                instance1.setTopamount(Double.valueOf(amount));
                cwalletmod myTask = new cwalletmod(instance1.getCustomerid(),amount);
                myTask.execute();
            }
        });
    }
    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    private void processPayment() {
        amount = edtAmount.getText().toString();


        Intent intent = new Intent(this, webwallet.class);
        startActivity(intent);

        /*
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD",
                "Purchase Goods",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();

        cwallet myTask = new cwallet(instance1.getCustomerid());
        myTask.execute();


        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this, PaymentDetails.class)
                                .putExtra("Payment Details", paymentDetails)
                                .putExtra("Amount", amount));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }



    private class cwallet extends AsyncTask<String, Void, String> {

        private Context context;
        private ProgressDialog dialog;

        private String customer;

        String rep;



        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value




            String h =rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            instance1.setWallet(Double.valueOf(result));
            tc.setText("WALLET BALANCE :"+result+"/=");

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {}

        public cwallet(String customer) {

            this.customer = customer;

        }

        public void whenSendPostRequest_thenCorrect()
                throws IOException {


            OkHttpClient client = new OkHttpClient();

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";
            String url="https://corellia.co.ke/rider/one.php?action=wallet&id="+customer;


            okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();




            try{
                Call call = client.newCall(request);
                Response response= client.newCall(request).execute();
                // Response response = call.execute();

                LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
                rep = response.body().string().replaceAll("\\n","");
                rep = response.body().string().replaceAll("\\r","");
                instance1.setRiderequesteresponse(rep);

                Log.d("dd",rep);
            }
            catch( Exception e) {
                Log.d("dd",e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }

    private class cwalletmod extends AsyncTask<String, Void, String> {

        private Context context;
        private ProgressDialog dialog;

        private String customer,amount;

        String rep;



        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value




            String h =rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            //instance1.setWallet(Double.valueOf(result));
           // tc.setText("YOUR WALLET :"+result+"/=");

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {}

        public cwalletmod(String customer,String Amount) {

            this.customer = customer;
            this.amount = Amount;

        }

        public void whenSendPostRequest_thenCorrect()
                throws IOException {


            OkHttpClient client = new OkHttpClient();

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";
            String url="https://corellia.co.ke/rider/one.php?action=walletmod&id="+customer+"&amount="+amount;


            okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();




            try{
                Call call = client.newCall(request);
                Response response= client.newCall(request).execute();
                // Response response = call.execute();

                LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
                rep = response.body().string().replaceAll("\\n","");
                rep = response.body().string().replaceAll("\\r","");
                instance1.setRiderequesteresponse(rep);

                Log.d("dd",rep);
            }
            catch( Exception e) {
                Log.d("dd",e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }
}
