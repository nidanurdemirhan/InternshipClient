package com.example.internshipproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.internshipproject.DataBaseHalper;
import com.example.internshipproject.R;

public class EditClientActivity extends AppCompatActivity {
    Spinner spinnerOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_page);
        Intent intent = getIntent();


        TextView name = findViewById(R.id.textView);
        TextView surname = findViewById(R.id.textView2);
        String clientInfo =intent.getStringExtra("clientInfo");
        name.setText(clientInfo.split(" ")[0]);
        surname.setText(clientInfo.split(" ")[1]);
        spinnerOptions = findViewById(R.id.typeInputChange);
        String[] options = {"Stock", "Contract", "Both"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOptions.setAdapter(adapter);
        Button btnBack = findViewById(R.id.submitChange);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHalper db = new DataBaseHalper(v.getContext());
                boolean aa = db.changeClientStatus(clientInfo,spinnerOptions.getSelectedItem().toString());
                if(aa){
                    Toast.makeText(EditClientActivity.this,spinnerOptions.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}


