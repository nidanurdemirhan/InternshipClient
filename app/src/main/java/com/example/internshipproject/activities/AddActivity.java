package com.example.internshipproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.internshipproject.Models.ClientModel;
import com.example.internshipproject.halpers.DataBaseHalper;
import com.example.internshipproject.halpers.JsonHelper;
import com.example.internshipproject.R;


public class AddActivity extends AppCompatActivity {

    Spinner spinnerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        spinnerOptions = findViewById(R.id.typeInput);
        String[] options = {"Stock", "Contract", "Both"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOptions.setAdapter(adapter);
        submitClient();
    }

    public void submitClient(){
        Button submitClient = findViewById(R.id.submitClient);
        EditText name = findViewById(R.id.nameInput);
        EditText surname = findViewById(R.id.surnameInput);
        Spinner type = findViewById(R.id.typeInput);

        submitClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHalper dbHalper = new DataBaseHalper(AddActivity.this);
                String clientInfo = name.getText().toString()+" "+ surname.getText().toString();
                ClientModel newClient = new ClientModel(clientInfo,type.getSelectedItem().toString(),"");
                boolean success = dbHalper.addOneClient(newClient);
                JsonHelper jsonHelper = new JsonHelper();
                jsonHelper.addClient(v.getContext(),clientInfo,type.getSelectedItem().toString(),"");

                if (success){
                    Toast.makeText(AddActivity.this,"Supplier Added",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
