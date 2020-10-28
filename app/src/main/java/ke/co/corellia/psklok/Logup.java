package ke.co.corellia.psklok;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Logup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);
        getSupportActionBar().hide();
        MaterialButton btn = findViewById(R.id.cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CountryCodePicker ccp;
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.detectSIMCountry(true);
        ccp.setDefaultCountryUsingNameCode("KE");




        MaterialButton btn2 = findViewById(R.id.signup);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText tel =findViewById(R.id.phone);
                TextInputEditText  emax =findViewById(R.id.email);
                TextInputEditText password =findViewById(R.id.code);
                TextInputEditText  fname =findViewById(R.id.fname);
                TextInputEditText  fpromo =findViewById(R.id.Pcode);

                String email,passw,telephone,fullname,country,promo;



                email = emax.getText().toString();
                passw = password.getText().toString();
                telephone=tel.getText().toString();
                fullname=fname.getText().toString();
                promo=fpromo.getText().toString();


                String phone =  ccp.getSelectedCountryCodeWithPlus();
                phone=phone +telephone;



                cSignin myTask = new cSignin(email,passw,phone,fullname,ccp.getSelectedCountryCodeWithPlus(),promo);

                myTask.execute();


            }
        });



    }

    private class cSignin extends AsyncTask<String, Void, String> {


        private String email,password,telephone,fullname,rep,country,promo;



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
            //   tc.setText(result);
            Double rp=0.0;
            rp=Double.valueOf(result);

            if (rp==1)
            {
               /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("id", rp.intValue());
                  editor.commit(); // commit changes
                */
            //   Toast.makeText(Logup.this,"WELCOME!",Toast.LENGTH_LONG).show();

                finish();


            }
            if (rp==2)
            {
               /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("id", rp.intValue());
                  editor.commit(); // commit changes
                */
                //   Toast.makeText(Logup.this,"WELCOME!",Toast.LENGTH_LONG).show();

                Toast.makeText(Logup.this,"Phone number is already registered",Toast.LENGTH_LONG).show();


            }
            else
            {
                Toast.makeText(Logup.this,"check your data",Toast.LENGTH_LONG).show();
            }

        }

        public cSignin(String email, String password, String telephone, String fullname,String Country,String promo) {
            this.email = email;
            this.password = password;
            this.telephone = telephone;
            this.fullname = fullname;
            this.country =Country;
            this.promo =promo;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {}



        public void whenSendPostRequest_thenCorrect()
                throws IOException {

            //  LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            //String rid= instance1.();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            //String json ="jj";
            //
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("email", email);
                jsonObject.put("password", password);
                jsonObject.put("fullname", fullname);
                jsonObject.put("Telephone", telephone);
                jsonObject.put("Country", country);
                jsonObject.put("promo", promo);




            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json =jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url="https://www.psklok.com/klok/two.php?action=logup";
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://www.psklok.com/klok/one.php?action=getdr";



            // okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();




            try{
                //  Call call = client.newCall(request);
                Response response= client.newCall(request).execute();
                // Response response = call.execute();

                rep=response.body().string();
                rep = rep.replaceAll("\\n","");
                rep = rep.replaceAll("\\r","");

                Log.d("dd",rep);
            }
            catch( Exception e) {
                Log.d("dd",e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }
}
