package com.example.finalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends DBActivity {
    protected EditText editWeight;
    protected EditText editHeight;
    protected double editResult;
    protected Button btnInsert;
    protected ListView simpleList;
    protected EditText ShowResult;


    protected void FillListView() throws Exception{
        final ArrayList<String> listResults=
                new ArrayList<>();
        SelectSQL(
                "SELECT * FROM BMICalcualor2 ORDER BY Weight",
                null,
                (ID, Weight, Height, Result)->{
                    listResults.add(ID+"\t"+Weight+"\t"+Height+"\t"+Result+"\n");
                }
        );
        simpleList.clearChoices();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.activity_list_view,
                R.id.textView,
                listResults

        );
        simpleList.setAdapter(arrayAdapter);
    }


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editWeight=findViewById(R.id.edit_weight);
        editHeight=findViewById(R.id.edit_height);
        btnInsert=findViewById(R.id.button_calculate);
        simpleList=findViewById(R.id.SimpleList);
        Button btn;
        btn = findViewById(R.id.button_graph);

       EditText calcHeight = findViewById(R.id.edit_height);
        EditText calcWeight = findViewById(R.id.edit_weight);


        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView clickedText=view.findViewById(R.id.textView);
                String selected = clickedText.getText().toString();
                String[] elements=selected.split("\t");
                String ID=elements[0];
                String Height=elements[1];
                String Weight=elements[2];
                Intent intent=new Intent(MainActivity.this,
                        UpdateDelete.class
                );
                Bundle b=new Bundle();
                b.putString("ID", ID);
                b.putString("Height", Height);
                b.putString("Weight", Weight);
                intent.putExtras(b);
                startActivityForResult(intent, 200, b);






            }
        });

        btn.setOnClickListener( new View.OnClickListener() {
             @Override
             public void onClick (View v){
                 Intent intent = new Intent(getBaseContext(), BMIGraph.class);
                 startActivity(intent);
             }

         });




        try {
            initDB();
            FillListView();
        } catch (Exception e) {
           e.printStackTrace();
        }


        btnInsert.setOnClickListener(view -> {
            try {
                int intHeight = Integer.valueOf(calcHeight.getText().toString());
                int intWeight = Integer.valueOf(calcWeight.getText().toString());
                editResult = IndexBIResult(intHeight, intWeight);
                String result = String.valueOf(editResult);


                ShowResult = (EditText)findViewById(R.id.shresult);
                ShowResult.setText(result);


                ExecSQL(
                        "INSERT INTO BMICalcualor2(Weight, Height, Result) " +
                                "VALUES(?, ?, ?) ",
                        new Object[]{
                                editWeight.getText().toString(),
                                editHeight.getText().toString(),
                               result
                        },
                        ()-> Toast.makeText(getApplicationContext(),
                                "Record Inserted", Toast.LENGTH_LONG).show()

                );
                FillListView();



            }catch (Exception e){
                Toast.makeText(getApplicationContext(),
                        "Insert Failed: "+e.getLocalizedMessage()
                        , Toast.LENGTH_SHORT).show();
            }

        });

    }

}