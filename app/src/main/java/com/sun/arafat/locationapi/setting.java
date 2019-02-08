package com.sun.arafat.locationapi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class setting extends AppCompatActivity {
    private TextView switchStatus;
    private Switch mySwitch;
    PersonDatabase mydb;
    EditText editname,editsurname,editmarks,editid;
    Button btnadd;
    Button btnview;
    Button btnupdate;
    Button btndelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mydb = new PersonDatabase(this);

        editname =(EditText) findViewById(R.id.editText2);
        editmarks =(EditText) findViewById(R.id.editText);
        editid = (EditText)findViewById(R.id.editText3);
        btnadd =(Button) findViewById(R.id.button);
        btnview =(Button) findViewById(R.id.button2);
        btnupdate =(Button) findViewById(R.id.button3);
        btndelete =(Button) findViewById(R.id.button4);
        AddData();
        viewAll();
        DeleteData();
        DATAUPDATE();



    }

/////datadesh...............................................
public void AddData(){
    btnadd.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = mydb.insertdata(editname.getText().toString(),
                            editmarks.getText().toString());
                    if (isInserted == true)
                        Toast.makeText(setting.this, "সেভ করা হয়েছে", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(setting.this, "সেভ করা হইনি", Toast.LENGTH_SHORT).show();
                }
            }
    );
}

    public void viewAll() {
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getAllData();
                if (res.getCount() == 0){
                    //message
                    showMessage("দুখিত", "খুজে পাওয়া যাইনি");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID : "+res.getString(0)+"\n");
                    buffer.append("NAME : "+res.getString(1)+"\n");
                    buffer.append("NUMBER : "+res.getString(2)+"\n\n");
                }

                //Show All DATA
                showMessage("DATA",buffer.toString());
            }
        });
    }

    public void DATAUPDATE(){
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isupdated = mydb.updateuserData(editid.getText().toString(),
                        editname.getText().toString(),
                        editmarks.getText().toString());
                if (isupdated == true)
                    Toast.makeText(setting.this, "Change Successful ", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(setting.this, "Sorry", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DeleteData(){
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = mydb.deleteData(editid.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(setting.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(setting.this, "Sorry", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
