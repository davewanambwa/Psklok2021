package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.checkbox.MaterialCheckBox;
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

public class recepiant extends AppCompatActivity {
    static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    String number,number2,rec;
    LazyInitializedSingletonExample lz;
    Button btn2;

    MaterialCheckBox cch ;
    LinearLayout lt;
    Double ins,cost;


    Button btn1, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepiant);

        lt =findViewById(R.id.ltins);
        cch =findViewById(R.id.chkins);


        TextInputEditText tx,tx2;
        tx =findViewById(R.id.txtvalue);
        tx2 =findViewById(R.id.txtvalue2);
        TextView txco;
        txco=findViewById(R.id.txtinsurance);

        lz = LazyInitializedSingletonExample.getInstance();

        getSupportActionBar().hide();

        //Intent intent = new Intent(Intent.ACTION_PICK);

        ImageButton btn =findViewById(R.id.btnlookup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
                }
            }
        });

        btn2 =findViewById(R.id.btnconfirm);
        btn2.setEnabled(false);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //  setResult(21);
                finish();

            }
        });

        Button btn3 =findViewById(R.id.btncancel);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(4);
                finish();
            }
        });


        tx2.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                {
                    ins=Double.valueOf(s.toString());
                    int h;
                    cost=0.04*ins;
                    h=cost.intValue();
                    lz.setInsurancecost(h);
                    lz.setInsurancevalue(ins.intValue());
                    //cost =Mcost);
                    txco.setText("COST:"+h+"/=");
                }

            }
        });
        tx.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        cch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(  cch.isChecked())
                {
                    lt.setVisibility(View.VISIBLE);
                    lz.setInsured(true);
                }
                else
                {
                    lt.setVisibility(View.GONE);
                    lz.setInsured(false);
                }
            }
        });



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
                int numberIndex2 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY);
                number = cursor.getString(numberIndex);
                number2 = cursor.getString(numberIndex2);
                rec =number2;
                // String nm=cursor.getString(numberIndex2);
                // Do something with the phone number
                //...
                //Toast.makeText(this,number,Toast.LENGTH_LONG).show();
                number =number.replaceAll(" ","");


                        // Do something when user want to exit the app
                        // Let allow the system to handle the event, such as exit the app
                        TextInputEditText tx =findViewById(R.id.txtvalue);
                        tx.setText(number2.toString().toUpperCase());

cadd myTask = new cadd(number.toString().toUpperCase());

                        myTask.execute();
                       // Walletshare.cadd myTask = new Walletshare.cadd(number);

                       // myTask.execute();


              //  Walletshare.cadd myTask = new Walletshare.cadd(number);

               // myTask.execute();
            }
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
            JSONObject reader = null;
            String id ="0";
            TextView tx =findViewById(R.id.txtwarn);
            tx.setVisibility(View.GONE);
            try {
                reader = new JSONObject(result);

                JSONArray sysa = reader.getJSONArray("contacts");

                JSONObject sys  = sysa.getJSONObject(0);
                id= sys.getString("id");
                if (id.equals("0"))


                {
                    btn2.setEnabled(false);
                    tx.setVisibility(View.VISIBLE);
                    tx.setText(rec+" is not registered on PSKlok");
                    Toast.makeText(getApplicationContext(),rec+" is not registered",Toast.LENGTH_LONG).show();
                    lz.setPdestination("");
                    lz.setPname("");
                }
                else
                {
                    lz.setPdestination(number.toString());
                    lz.setPname(number2.toString());
                    btn2.setEnabled(true);
                }





            } catch (JSONException e) {
                e.printStackTrace();

            }


           // Walletshare.cSignin myTask = new Walletshare.cSignin(String.valueOf(lz.getCustomerid()));

          //  myTask.execute();



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

              //  jsonObject.put("cid", lz.getCustomerid());



            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json =jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url="https://www.psklok.com/klok/two.php?action=checkno&d="+cid;
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
