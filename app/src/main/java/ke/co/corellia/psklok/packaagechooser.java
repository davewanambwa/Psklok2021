package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class packaagechooser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packaagechooser);
        getSupportActionBar().hide();

        LazyInitializedSingletonExample lz;
        lz =LazyInitializedSingletonExample.getInstance();


        LinearLayout btn1,btn2,btn3;
        btn1 =findViewById(R.id.btn1);
        btn2 =findViewById(R.id.btn2);
        btn3 =findViewById(R.id.btn3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lz.setWeight(1);

                Intent intent = getIntent();
                setResult(20, intent);
                finish();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lz.setWeight(2);


                Intent intent = getIntent();
                setResult(20, intent);

                finish();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lz.setWeight(3);


                Intent intent = getIntent();
                setResult(20, intent);
                finish();
            }
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();

        setResult(4, intent);
        finish();
    }
}
