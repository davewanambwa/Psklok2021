package ke.co.corellia.psklok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class walletcontact extends AppCompatActivity {
    private final int REQUEST_CODE=99;
    static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walletcontact);

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
                            String number = cursor.getString(numberIndex);
                            int numberIndex2 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            String pname = cursor.getString(numberIndex2);
                            // Do something with the phone number
                            //...
                           // Toast.makeText(this,number,Toast.LENGTH_LONG).show();
                        }
                    }

    }

}

