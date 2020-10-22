package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sharepromo extends AppCompatActivity {

    LazyInitializedSingletonExample lz ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharepromo);
        getSupportActionBar().hide();
        lz =LazyInitializedSingletonExample.getInstance();
        TextView tx =findViewById(R.id.txtcode);

        tx.setText(lz.getFcode());

        Button btn1,btn2;
        btn1 =findViewById(R.id.btnconfirm);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
finish();
            }
        });

        btn2 =findViewById(R.id.btnshare);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi Use this code to Sign up to Klok for safe deliveries! : "+lz.getFcode());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);


            }
        });
    }
}
