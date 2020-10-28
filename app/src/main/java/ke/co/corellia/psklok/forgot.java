package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class forgot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        getSupportActionBar().hide();
        AppCompatButton resetbtn  =findViewById(R.id.btnreset);
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText txemail =findViewById(R.id.emailreset);
cRest myTask = new cRest(txemail.getText().toString());

                myTask.execute();
            }
        });
    }


    private class cRest extends AsyncTask<String, Void, String> {


        private String email,rep;



        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value




            String h ="0";

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);
            int id=0;
try {
     id =Integer.parseInt(rep.trim());
}
catch (Exception e)
{
    id=0;
}








            if (id==1)
            {
                Intent intent = getIntent();
               // setResult(4, intent);
                Toast.makeText(forgot.this, "Check your email for a link to reset your password",Toast.LENGTH_LONG).show();
                finish();
            }
            else
            {
                Toast.makeText(forgot.this, "That email has not been registered",Toast.LENGTH_LONG).show();
            }



        }

        public cRest(String email) {



            this.email = email;





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





            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json =jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url="https://www.psklok.com/klok/two.php?action=resetpasswordreq&email="+email;
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