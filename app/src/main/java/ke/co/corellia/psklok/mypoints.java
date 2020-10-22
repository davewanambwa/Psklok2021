package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class mypoints extends AppCompatActivity {

    LazyInitializedSingletonExample lz;
    private String points ="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypoints);
        lz =LazyInitializedSingletonExample.getInstance();
        cfare myTask = new cfare(lz.getCustomerid());
        myTask.execute();
        Button btn =findViewById(R.id.btnlookup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class cfare extends AsyncTask<String, Void, String> {


        private String pack, rep;


        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);
            JSONObject reader = null;

            try {
                reader = new JSONObject(result);

                JSONArray sysa = reader.getJSONArray("contacts");

                JSONObject sys = sysa.getJSONObject(0);
                points = sys.getString("points");
                TextView tx =findViewById(R.id.txtpoints);
                tx.setText(points);


            } catch (JSONException e) {
                e.printStackTrace();

            }


        }

        public cfare(String pPackage) {


            this.pack = pPackage;


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


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

                jsonObject.put("cid", pack);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json = jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url = "https://corellia.co.ke/rider/two.php?action=mypoints";
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";


            // okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                //  Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();

                rep = response.body().string();
                rep = rep.replaceAll("\\n", "");
                rep = rep.replaceAll("\\r", "");

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }
}