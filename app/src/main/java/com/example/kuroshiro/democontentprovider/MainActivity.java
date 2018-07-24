package com.example.kuroshiro.democontentprovider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    Button btnshowallcontact;
    Button btnaccesscalllog;
    Button btnaccessmediastore;
    Button btnaccessbookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnshowallcontact = findViewById(R.id.btnshowallcontact);
        btnshowallcontact.setOnClickListener(this);
        btnaccesscalllog = findViewById(R.id.btnaccesscalllog);
        btnaccesscalllog.setOnClickListener(this);
        btnaccessmediastore = findViewById(R.id.btnmediastore);
        btnaccessmediastore.setOnClickListener(this);
        btnaccessbookmarks = findViewById(R.id.btnaccessbookmarks);
        btnaccessbookmarks.setOnClickListener(this);
    }

    // @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.layout.activity_main, menu);
//        return true;
//    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v == btnshowallcontact) {
            intent = new Intent(this, ShowAllContactActivity.class);
            startActivity(intent);
        } else if (v == btnaccesscalllog) {
            accessTheCallLog();
        } else if (v == btnaccessmediastore) {
            accessMediaStore();
        }
    }

    /**
     * hàm lấy danh sách lịch sử cuộc gọi
     * với thời gian nhỏ hơn 30 giây và sắp xếp theo ngày gọi
     */
    public void accessTheCallLog() {
        String[] projection = new String[]{
                Calls.DATE,
                Calls.NUMBER,
                Calls.DURATION
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor c = getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection,
                Calls.DURATION + "<?", new String[]{"30"},
                Calls.DATE + " Asc");
        c.moveToFirst();
        String s="";
        while(c.isAfterLast()==false){
            for(int i=0;i<c.getColumnCount();i++){
                s+=c.getString(i)+" - ";
            }
            s+="\n";
            c.moveToNext();
        }
        c.close();
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
    /**
     * hàm đọc danh sách các Media trong SD CARD
     */
    public void accessMediaStore()
    {
        String []projection={
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_ADDED,
                MediaStore.MediaColumns.MIME_TYPE
        };
        CursorLoader loader=new CursorLoader
                (this, Media.EXTERNAL_CONTENT_URI,
                        projection, null, null, null);
        Cursor c=loader.loadInBackground();
        c.moveToFirst();
        String s="";
        while(!c.isAfterLast()){
            for(int i=0;i<c.getColumnCount();i++){
                s+=c.getString(i)+" - ";
            }
            s+="\n";
            c.moveToNext();
        }
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        c.close();
    }

}
