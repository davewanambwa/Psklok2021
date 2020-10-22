package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ridechooser extends AppCompatActivity {
    private Intent intentb;
    LazyInitializedSingletonExample lz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ridechooser);
         intentb = getIntent();
        getSupportActionBar().hide();
       lz= LazyInitializedSingletonExample.getInstance();

        LinearLayout lwallet =findViewById(R.id.ltwallet);
        lwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intentsf= new Intent(ridechooser.this,wallet.class);
              Intent intentsf= new Intent(ridechooser.this,wallet.class);
                //Activity is started with requestCode 2

                startActivity(intentsf);
            }
        });


        LinearLayout lpack =findViewById(R.id.ltparcel);
        lpack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               setResult(6, intentb);

lz.setRide(false);
lz.setWeight(4);
              finish();
            }
        });

        LinearLayout lride =findViewById(R.id.ltride);
        lride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lz.setWeight(4);
                setResult(8, intentb);
                lz.setPdestination("0");

                lz.setRide(true);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Please confirm");
        builder.setMessage("Are you sure want to exit the app?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do something when user want to exit the app
                // Let allow the system to handle the event, such as exit the app
              //  MainActivity.super.onBackPressed();

                setResult(100, intentb);

                finish();
              //  ridechooser.super.onBackPressed();
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
    }

}
