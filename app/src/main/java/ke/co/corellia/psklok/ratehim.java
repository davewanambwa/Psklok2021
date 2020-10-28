package ke.co.corellia.psklok;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ratehim extends AppCompatActivity {
LazyInitializedSingletonExample lz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratehim);
        RatingBar ratingbar;
        Button button;
        lz =LazyInitializedSingletonExample.getInstance();

        ratingbar=(RatingBar)findViewById(R.id.ratingBar);
        button=(Button)findViewById(R.id.button);
        //Performing action on Button Click
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                String rating=String.valueOf(ratingbar.getRating());

                cDriverSign myTask = new cDriverSign(getApplicationContext(),lz.getTripid(),rating);

                myTask.execute();

                ratehim.super.onBackPressed();
               finish();
                System.exit(0);

            }

        });




    }

    private class cDriverSign extends AsyncTask<String, Void, String> {

        private Context context;
        private ProgressDialog dialog;

        private String trip,rating;

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
            //  tc.setText(result);


        }

        public cDriverSign(Context context, String trip,String rating) {
            this.context = context;
            this.trip = trip;
            this.rating =rating;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {}



        public void whenSendPostRequest_thenCorrect()
                throws IOException {


            OkHttpClient client = new OkHttpClient();

            // String url=  "https://www.psklok.com/klok/one.php?action=getdr";
            String url="https://www.psklok.com/klok/two.php?action=rate2&t="+trip+"&r="+rating;//+"&Email="+email+"&PhoneNumber="+phone+"&password="+password;


            okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();




            try{
                Call call = client.newCall(request);
                Response response= client.newCall(request).execute();
                // Response response = call.execute();

                LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
                rep = response.body().string().replaceAll("\\n","");
                rep = rep.replaceAll("\\r","");
                instance1.setDriver(rep);

                Log.d("dd",rep);
            }
            catch( Exception e) {
                Log.d("dd",e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }
}