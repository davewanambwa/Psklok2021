package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

public class myprof extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprof);

        Button btn1 , btn2;
        String cid, fname,fcccode;
        btn1 =findViewById(R.id.btncancel);
        btn2 =findViewById(R.id.btnconfirm);
        getSupportActionBar().hide();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        //SharedPreferences.Editor editor = pref.edit();

        if (pref.contains("id")) {
            cid = String.valueOf(pref.getInt("id", -1));
            fname = pref.getString("fname", "0");
            fcccode = pref.getString("fcccode", "0");
            TextInputEditText tx =findViewById(R.id.fname);
            tx.setText(fname);
            TextInputEditText tx2 =findViewById(R.id.email);
            tx2.setText(pref.getString("femail", "0"));
            EditText tx3 =findViewById(R.id.phone);
            tx3.setText(pref.getString("ftelephone", "0"));

            CountryCodePicker ccp =findViewById(R.id.ccp);

         ccp.setCountryForPhoneCode(Integer.parseInt(fcccode));
        }


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}