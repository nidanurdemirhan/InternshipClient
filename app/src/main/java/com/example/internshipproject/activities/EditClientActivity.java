package com.example.internshipproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.internshipproject.AppDatabase;
import com.example.internshipproject.DatabaseClient;
import com.example.internshipproject.halpers.JsonHelper;
import com.example.internshipproject.R;
import com.example.internshipproject.interfaces.SupplierDao;

public class EditClientActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Spinner spinnerOptions;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_page);
        Intent intent = getIntent();

        TextView info = findViewById(R.id.textView);
        TextView type = findViewById(R.id.textView2);

        String clientInfo =intent.getStringExtra("clientInfo");
        String clientType = intent.getStringExtra("clientType");
        info.setText(clientInfo);
        type.setText(clientType);


        spinnerOptions = findViewById(R.id.typeInputChange);
        String[] options = {"Stock", "Contract", "Both"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOptions.setAdapter(adapter);
        Button btnBack = findViewById(R.id.submitChange);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                SupplierDao supplierDao = db.supplierDao();
                supplierDao.updateType(clientInfo,spinnerOptions.getSelectedItem().toString());

                JsonHelper jsonHelper = new JsonHelper();
                jsonHelper.updateType(v.getContext(),clientInfo,spinnerOptions.getSelectedItem().toString());
            }
        });
    }
}


