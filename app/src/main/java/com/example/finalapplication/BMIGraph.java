package com.example.finalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BMIGraph extends DBActivity {

    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmigraph);


        btn = findViewById(R.id.button_return);

        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }

        });
    }
}