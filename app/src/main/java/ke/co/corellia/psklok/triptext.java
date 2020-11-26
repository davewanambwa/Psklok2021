package ke.co.corellia.psklok;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class triptext extends AppCompatActivity {
    private String number;
    Context context;
    LazyInitializedSingletonExample lz;
    String jsonStr;
    ListView listview;
    static final int REQUEST_SELECT_PHONE_NUMBER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg);

        getSupportActionBar().hide();
        context =this;


        lz = LazyInitializedSingletonExample.getInstance();

        FloatingActionButton bt =  findViewById(R.id.fab);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ke.co.corellia.psklok.triptext.this, replymsg.class);
                startActivity(intent2);// Activity is started with requestCode 2btn
            }
        });

       listview = (ListView) findViewById(R.id.lspeople);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=listview.getItemAtPosition(position);
                HashMap h =(HashMap)listview.getItemAtPosition(position);
                String j =h.get("id").toString();
                //lz.setrReportid(j);
                String u =h.get("FullName").toString();

                /* android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

                builder.setTitle("Report Isssue");
                builder.setMessage("Would you like to report an issue with this trip ("+u+")?");
                builder.setCancelable(true);

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { */
                        // Do something when user want to exit the app
                        // Let allow the system to handle the event, such as exit the app
                     //   Walletshare.cadd2 myTask = new Walletshare.cadd2(j);

                      //  myTask.execute();
                        Intent intent2 = new Intent(ke.co.corellia.psklok.triptext.this, replymsg.class);
                        startActivity(intent2);// Activity is started with requestCode 2btn
                //   }
                //});

              /*  builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do something when want to stay in the app
                        // Toast.makeText(getApplicationContext(), "thank you", Toast.LENGTH_LONG).show();
                    }
                });
*/
                // Create the alert dialog using alert dialog builder
               // android.app.AlertDialog dialog = builder.create();

                // Finally, display the dialog when user press back button
             //   dialog.show();

                //  cadd myTask = new cadd(number);

                //myTask.execute();

            }
        });

    cSignin myTask = new cSignin(String.valueOf(lz.getCustomerid()));

        myTask.execute();
    }private String getListData() {

        return jsonStr;
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
                user.put("FullName", obj.getString("message").toUpperCase());
                user.put("Telephone", "FARE :"+obj.getString("sent"));
                user.put("ShareDate", "Date :"+obj.getString("sent"));
                userList.add(user);
            }
            ListAdapter adapter = new SimpleAdapter(ke.co.corellia.psklok.triptext.this, userList, R.layout.list_row, new String[]{"id2", "FullName", "Telephone","ShareDate","id"}, new int[]{R.id.id2, R.id.fullname, R.id.Telephone,R.id.ShareDate,R.id.id});
            lv.setAdapter(adapter);
        } catch (JSONException ex) {
            Log.e("JsonParser Example", "unexpected JSON exception", ex);
        }
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
            String url="https://www.psklok.com/klok/two.php?action=mymsg";
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

