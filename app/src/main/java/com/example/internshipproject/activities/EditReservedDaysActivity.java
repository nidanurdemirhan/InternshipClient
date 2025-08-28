package com.example.internshipproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.internshipproject.DataBaseHalper;
import com.example.internshipproject.R;


public class EditReservedDaysActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reserved_days);
        Button submitButton = findViewById(R.id.submitButton);
        EditText days = findViewById(R.id.reservedDaysInput);

        TextView clientInfo =findViewById(R.id.info);
        Intent intent = getIntent();
        clientInfo.setText(intent.getStringExtra("clientInfo"));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //burda database değiştirmek yerine yeni listeler oluşturulacak
                DataBaseHalper db = new DataBaseHalper(v.getContext());
                boolean aa = db.changeReservedDays(clientInfo.getText().toString(),days.getText().toString());
                if(aa){
                    Toast.makeText(EditReservedDaysActivity.this,days.getText().toString(),Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

}
