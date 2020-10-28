package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

public class Walletshare extends AppCompatActivity  {
    String jsonStr;
    static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    private String number;
    Context context;
    LazyInitializedSingletonExample lz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walletshare);
        getSupportActionBar().hide();

        context =this;

     lz = LazyInitializedSingletonExample.getInstance();

        cSignin myTask = new cSignin(String.valueOf(lz.getCustomerid()));

        myTask.execute();
/*

        Button btn =findViewById(R.id.btnlookup);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
                }
            }
        }); */

        FloatingActionButton btn2= findViewById(R.id.fab);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
                }
            }
        });





        ListView listview = (ListView) findViewById(R.id.lspeople);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=listview.getItemAtPosition(position);
                HashMap h =(HashMap)listview.getItemAtPosition(position);
             String j =h.get("id").toString();
                String u =h.get("FullName").toString();

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

                builder.setTitle("Please confirm wallet remove");
                builder.setMessage("Are you sure you want to stop sharing yor wallet with "+u+"?");
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do something when user want to exit the app
                        // Let allow the system to handle the event, such as exit the app
                        cadd2 myTask = new cadd2(j);

                        myTask.execute();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do something when want to stay in the app
                        // Toast.makeText(getApplicationContext(), "thank you", Toast.LENGTH_LONG).show();
                    }
                });

                // Create the alert dialog using alert dialog builder
                android.app.AlertDialog dialog = builder.create();

                // Finally, display the dialog when user press back button
                dialog.show();

              //  cadd myTask = new cadd(number);

                //myTask.execute();

            }
        });
    }







    private String getListData() {

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
                user.put("FullName", obj.getString("FullName").toUpperCase());
                user.put("Telephone", "Tel :"+obj.getString("Telephone"));
                user.put("ShareDate", "Date :"+obj.getString("ShareDate"));
                userList.add(user);
            }
            ListAdapter adapter = new SimpleAdapter(Walletshare.this, userList, R.layout.list_row, new String[]{"id2", "FullName", "Telephone","ShareDate","id"}, new int[]{R.id.id2, R.id.fullname, R.id.Telephone,R.id.ShareDate,R.id.id});
            lv.setAdapter(adapter);
        } catch (JSONException ex) {
            Log.e("JsonParser Example", "unexpected JSON exception", ex);
        }
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);





        if (reqCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                //int numberIndex2 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.N);
               number = cursor.getString(numberIndex);

                int numberIndex2 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY);
                //int numberIndex2 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.N);
               String  number2 = cursor.getString(numberIndex2);
              // String nm=cursor.getString(numberIndex2);
                // Do something with the phone number
                //...
                //Toast.makeText(this,number,Toast.LENGTH_LONG).show();
                number =number.replaceAll(" ","");

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

                builder.setTitle("Please confirm wallet share");
                builder.setMessage("Are you sure you want to share with "+number2+" ("+number+")?");
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do something when user want to exit the app
                        // Let allow the system to handle the event, such as exit the app
                        cadd myTask = new cadd(number);

                        myTask.execute();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do something when want to stay in the app
                        // Toast.makeText(getApplicationContext(), "thank you", Toast.LENGTH_LONG).show();
                    }
                });

                // Create the alert dialog using alert dialog builder
                android.app.AlertDialog dialog = builder.create();

                // Finally, display the dialog when user press back button
                dialog.show();

                cadd myTask = new cadd(number);

                myTask.execute();
            }
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
            String url="https://www.psklok.com/klok/two.php?action=mywallet&d="+cid;
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

    private class cadd extends AsyncTask<String, Void, String> {


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



            cSignin myTask = new cSignin(String.valueOf(lz.getCustomerid()));

            myTask.execute();



        }

        public cadd(String cid) {



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

                jsonObject.put("sid", cid);

                jsonObject.put("cid", lz.getCustomerid());



            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json =jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url="https://www.psklok.com/klok/two.php?action=mywalletadd";
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

    private class cadd2 extends AsyncTask<String, Void, String> {


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



            cSignin myTask = new cSignin(String.valueOf(lz.getCustomerid()));

            myTask.execute();



        }

        public cadd2(String cid) {



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

                jsonObject.put("sid", cid);

                jsonObject.put("cid", lz.getCustomerid());



            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json =jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url="https://www.psklok.com/klok/two.php?action=mywalletrem";
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
