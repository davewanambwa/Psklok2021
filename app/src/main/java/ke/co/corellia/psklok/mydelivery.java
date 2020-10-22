package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class mydelivery extends AppCompatActivity {
    String jsonStr;
    static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    private String number;
    Context context;
    LazyInitializedSingletonExample lz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydelivery);

        lz = LazyInitializedSingletonExample.getInstance();

        getSupportActionBar().hide();

      cSignin myTask = new cSignin(String.valueOf(lz.getCustomerid()));

        myTask.execute();
    }

    private void jlist()
    {

        String jsonStr = getListData();
        try {
            ArrayList<HashMap<String, String>> userList = new ArrayList<>();
            ListView lv = (ListView) findViewById(R.id.lspeople);
            JSONObject jObj = new JSONObject(jsonStr);
            JSONArray jsonArry = jObj.getJSONArray("contacts");
            for (int i = 0; i < jsonArry.length(); i++) {
                HashMap<String, String> user = new HashMap<>();
                JSONObject obj = jsonArry.getJSONObject(i);
                user.put("id", obj.getString("id"));
                user.put("id2", String.valueOf(i+1));
                user.put("FullName", "FROM :"+obj.getString("fullname").toUpperCase());
                user.put("Telephone", "DATE SENT :"+obj.getString("starts"));
                user.put("ShareDate", "SAFE CODE :"+obj.getString("Passwordgen"));
                userList.add(user);
            }
            ListAdapter adapter = new SimpleAdapter(mydelivery.this, userList, R.layout.list_row, new String[]{"id2", "FullName", "Telephone","ShareDate","id"}, new int[]{R.id.id2, R.id.fullname, R.id.Telephone,R.id.ShareDate,R.id.id});
            lv.setAdapter(adapter);
        } catch (JSONException ex) {
            Log.e("JsonParser Example", "unexpected JSON exception", ex);
        }
    }

    private String getListData() {

        return jsonStr;
    }


    private class cSignin extends AsyncTask<String, Void, String> {


        private String cid,rep;



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
            try {
                reader = new JSONObject(result);
                jsonStr=result;
                jlist();




            } catch (JSONException e) {
                e.printStackTrace();

            }






        }

        public cSignin(String cid) {



            this.cid=  cid;






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

                jsonObject.put("cid", cid);





            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json =jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url="https://corellia.co.ke/rider/two.php?action=mybox&d="+cid;
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