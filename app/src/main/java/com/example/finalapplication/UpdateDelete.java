package com.example.finalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDelete extends DBActivity {
    protected EditText editWeight, editHeight;
    protected double editResult;
    protected Button btnUpdate, btnDelete;
    protected String ID;

    private void BackToMain(){
        finishActivity(200);
        Intent i=new Intent(UpdateDelete.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        editWeight=findViewById(R.id.editWeight);
        editHeight=findViewById(R.id.editHeight);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);
        EditText calcHeight = findViewById(R.id.editHeight);
        EditText calcWeight = findViewById(R.id.editWeight);


        Bundle b=getIntent().getExtras();
        if(b!=null){
            ID=b.getString("ID");
            editHeight.setText(b.getString("Height"));
            editWeight.setText(b.getString("Weight"));

        }
        btnDelete.setOnClickListener(view -> {
            try{
                ExecSQL("DELETE FROM BMICalcualor2 WHERE " +
                                "ID = ?",
                        new Object[]{ID},
                        ()-> Toast.makeText(getApplicationContext(),
                                "Delete Successful", Toast.LENGTH_LONG).show()
                );

            }catch (Exception exception){
                Toast.makeText(getApplicationContext(),
                        "Delete Error: "+exception.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }finally {
                BackToMain();
            }
        });

        btnUpdate.setOnClickListener(view -> {
            try{
                int intHeight = Integer.valueOf(calcHeight.getText().toString());
                int intWeight = Integer.valueOf(calcWeight.getText().toString());
                editResult = IndexBIResult(intHeight, intWeight);
                String result = String.valueOf(editResult);
                //validation(editWeight, editHeight);
                ExecSQL("UPDATE BMICalcualor2 SET " +
                                "Height = ?, " +
                                "Weight = ?, " +
                                "Result = ? " +
                                "WHERE ID = ?",
                        new Object[]{
                                editHeight.getText().toString(),
                                editWeight.getText().toString(),
                                result,
                                ID},
                        ()-> Toast.makeText(getApplicationContext(),
                                "Update Successful", Toast.LENGTH_LONG).show()
                );

            }catch (Exception exception){
                Toast.makeText(getApplicationContext(),
                        "Update Error: "+exception.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }finally {
                BackToMain();
            }
        });


    }
}