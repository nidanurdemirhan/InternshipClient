package com.example.internshipproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.internshipproject.ClientModel;
import com.example.internshipproject.DataBaseHalper;
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

                ClientModel newClient = new ClientModel(name.getText().toString(),surname.getText().toString(),type.getSelectedItem().toString(),"");
                boolean success = dbHalper.addOneClient(newClient);
                if (success){
                    Toast.makeText(AddActivity.this,"true",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
