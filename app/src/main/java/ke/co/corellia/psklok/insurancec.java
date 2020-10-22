package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

public class insurancec extends AppCompatActivity {

    MaterialCheckBox cch ;
    LinearLayout lt;
    Double ins,cost;
    LazyInitializedSingletonExample lz;

    Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lz =LazyInitializedSingletonExample.getInstance();
        setContentView(R.layout.activity_insurancec);
        lt =findViewById(R.id.ltins);
        cch =findViewById(R.id.chkins);

        TextInputEditText tx;
        tx =findViewById(R.id.txtvalue);
        TextView txco;
        txco=findViewById(R.id.txtinsurance);


        btn1=findViewById(R.id.btnconfirm);
        btn2 =findViewById(R.id.btncancel);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lz.setInsured(cch.isChecked());
                setResult(20);
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(4);
                //setResult(6, intentb);
                finish();
            }
        });



        tx.addTextChangedListener(new TextWatcher() {

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

       // getSupportActionBar().hide();

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
}
