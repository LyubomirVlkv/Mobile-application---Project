package com.example.finalapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class DBActivity extends AppCompatActivity {
    protected interface OnQuerySuccess{
        public void OnSuccess();
    }
    protected interface OnSelectSuccess{
        public void OnElementSelected(
                String ID, String Height, String Weight, String Result
        );
    }
    // protected String DBFILE = getFilesDir().getPath()+"/KONTAKTI.db";


    protected double IndexBIResult (int height, int weight){
        DecimalFormat df = new DecimalFormat("#.##");
        double result = 0;
        double doubleheight = (double) height / 100;
        result = (weight/(Math.pow(doubleheight,2)));
    result = Double.valueOf(df.format(result));
        return result;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void SelectSQL(String SelectQ,
                             String[] args,
                             OnSelectSuccess success
    )
            throws Exception
    {
        SQLiteDatabase db=SQLiteDatabase
                .openOrCreateDatabase(getFilesDir().getPath()+"/DBBMI.db", null);
        Cursor cursor=db.rawQuery(SelectQ, args);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String ID=cursor.getString(cursor.getColumnIndex("ID"));
            @SuppressLint("Range") String Height=cursor.getString(cursor.getColumnIndex("Height"));
            @SuppressLint("Range") String Weight=cursor.getString(cursor.getColumnIndex("Weight"));
            @SuppressLint("Range") String Result=cursor.getString(cursor.getColumnIndex("Result"));
            success.OnElementSelected(ID, Height, Weight, Result);
        }
        db.close();
    }

    protected void ExecSQL(String SQL, Object[] args, OnQuerySuccess success)
            throws Exception
    {
        SQLiteDatabase db=SQLiteDatabase
                .openOrCreateDatabase(getFilesDir().getPath()+"/DBBMI.db", null);
        if(args!=null)
            db.execSQL(SQL, args);
        else
            db.execSQL(SQL);

        db.close();
        success.OnSuccess();
    }
    protected void initDB() throws  Exception{
        ExecSQL(
                "CREATE TABLE if not exists BMICalcualor2( " +
                        "ID integer PRIMARY KEY AUTOINCREMENT, " +
                        "Height text not null, " +
                        "Weight text not null, " +
                        "Result text not null " +
                        ")",
                null,
                ()-> Toast.makeText(getApplicationContext(),
                        "DB Init Successful", Toast.LENGTH_LONG).show()

        );
    }


}
