package ke.co.corellia.psklok;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
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

public class Login extends AppCompatActivity {

    LazyInitializedSingletonExample lz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatTextView bt1 =findViewById(R.id.btntx2);
        AppCompatTextView bt2 =findViewById(R.id.btntxt1);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Login.this, forgot.class);
                startActivityForResult(intent2, 2);// Activity is started with requestCode 2btn
            }
        });


        lz=LazyInitializedSingletonExample.getInstance();

        CountryCodePicker ccp;
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.detectSIMCountry(true);
        ccp.setDefaultCountryUsingNameCode("KE");

        getSupportActionBar().hide();
     /*  AppCompatButton btn = findViewById(R.id.cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Would You Like to exit?");
                builder.setIcon(R.drawable.logonamewhitewords__ek1);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = getIntent();

                        setResult(100, intent);
                        finish();   // stop chronometer here


                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();



            }
        });
*/
       // AppCompatButton btn3 = findViewById(R.id.signup);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Login.this, Logup.class);
                startActivityForResult(intent2, 2);// Activity is started with requestCode 2btn
            }
        });


        Button btn2 = findViewById(R.id.btnLogin);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextInputEditText pastx =findViewById(R.id.code);
               EditText emax =findViewById(R.id.phone);

                String email,password,telephone;

                email = emax.getText().toString();
                password = pastx.getText().toString();





                String phone =  ccp.getSelectedCountryCodeWithPlus();
                email=phone +email;









                cSignin myTask = new cSignin(email,password);

                myTask.execute();


            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

        builder.setMessage("Would you like to exit?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = getIntent();

                setResult(100, intent);
                finish();   // stop chronometer here


            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class cSignin extends AsyncTask<String, Void, String> {


        private String email,password,rep;



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
            JSONObject reader = null;
            String id ="0";
            String wallet ="0";
            String fname ="0";
            String astatus="0";
            String tt="Wrong phone number or password";
            try {
                reader = new JSONObject(result);

                JSONArray sysa = reader.getJSONArray("contacts");

                JSONObject sys  = sysa.getJSONObject(0);
                id= sys.getString("id");
                wallet= sys.getString("wallet");
                fname= sys.getString("fullname");
                astatus= sys.getString("Status");
                lz.setFcode(sys.getString("promocode"));




            } catch (JSONException e) {
                e.printStackTrace();

            }




            Double rp=0.0;
            rp=Double.valueOf(id);

            if (astatus.equals("Pending"))
            {
                tt ="Please activate your account by checking email inbox";
                rp=0.0;
            }

            if (rp>0)
            {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("id", rp.intValue());
                editor.putString("fname", fname);
                editor.commit(); // commit changes
                Intent intent = getIntent();
                setResult(4, intent);
                finish();


            }
            else
            {

                Toast.makeText(Login.this,tt,Toast.LENGTH_LONG).show();
            }

        }

        public cSignin(String email, String password) {



                this.email = email;
                this.password = password;





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

                jsonObject.put("phone", email);
                jsonObject.put("password", password);




            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json =jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url="https://corellia.co.ke/rider/two.php?action=getuser";
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";



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
