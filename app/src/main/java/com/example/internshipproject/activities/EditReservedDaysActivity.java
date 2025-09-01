package com.example.internshipproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.internshipproject.halpers.JsonHelper;
import com.example.internshipproject.R;


public class EditReservedDaysActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reserved_days);
        Button submitButton = findViewById(R.id.submitButton);
        EditText days = findViewById(R.id.reservedDaysInput);

        TextView clientInfo =findViewById(R.id.info);
        TextView clientType =findViewById(R.id.type);
        Intent intent = getIntent();
        clientInfo.setText(intent.getStringExtra("clientInfo"));
        clientType.setText(intent.getStringExtra("clientType"));//bu bilgi contract adapterdan geliyo onu bi değiştir

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonHelper jsonHelper = new JsonHelper();
                jsonHelper.updateReservedDays(v.getContext(),clientInfo.getText().toString(),days.getText().toString());
            }
        });

    }

}
