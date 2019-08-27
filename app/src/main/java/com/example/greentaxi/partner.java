package com.example.greentaxi;

import android.content.Intent;
<<<<<<< HEAD
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
=======
import android.os.Bundle;
>>>>>>> 기존에 있던 PHP 관련 된것들 삭제
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class partner extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.partner_back:
                Intent intent = new Intent(this, main_logined.class);
                startActivity(intent);
                break;
<<<<<<< HEAD
            case R.id.partner_add:
                Intent mlntent = new Intent(Intent.ACTION_PICK);
                mlntent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(mlntent, 0);




                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            ((Cursor) cursor).moveToFirst();
            String sName = cursor.getString(0);
            String sNumber = cursor.getString(1);
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data );
    }
}

=======
        }
    }
}
>>>>>>> 기존에 있던 PHP 관련 된것들 삭제
