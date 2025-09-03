package com.example.internshipproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.internshipproject.AppDatabase;
import com.example.internshipproject.DatabaseClient;
import com.example.internshipproject.entities.Supplier;
import com.example.internshipproject.halpers.JsonHelper;
import com.example.internshipproject.R;
import com.example.internshipproject.interfaces.SupplierDao;


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
                AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                SupplierDao supplierDao = db.supplierDao();

                Supplier supplier = new Supplier();
                supplier.supplierInfo = name.getText().toString()+" "+ surname.getText().toString();

                if(!supplier.supplierInfo.matches(".*\\d.*")){

                    supplier.supplierType = type.getSelectedItem().toString();
                    supplier.lastReservedDays = "";

                    supplierDao.insert(supplier);
                    JsonHelper jsonHelper = new JsonHelper();
                    jsonHelper.addClient(v.getContext(),name.getText().toString()+" "+ surname.getText().toString(),type.getSelectedItem().toString(),"");

                    finish();
                    startActivity(getIntent());

                }else{
                    Toast.makeText(AddActivity.this, "Invalid name/surname.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
