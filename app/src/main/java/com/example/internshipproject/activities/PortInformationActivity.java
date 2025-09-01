package com.example.internshipproject.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.internshipproject.R;
import com.example.internshipproject.halpers.DataBaseHalper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import java.nio.file.Files;

public class PortInformationActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_information);

        Button sendServerButton = findViewById(R.id.connectServer);
        EditText ipInput = findViewById(R.id.textView3);
        EditText portInput = findViewById(R.id.textView4);
        sendServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    // "10.0.2.2"
                    //8080
                    try (Socket socket = new Socket(ipInput.getText().toString(), Integer.parseInt(portInput.getText().toString()));
                         DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                         DataInputStream dis = new DataInputStream(socket.getInputStream())) {

                        File file = new File(getFilesDir(), "suppliers.json");
                        String fullPath = file.getAbsolutePath();
                        Log.d("FILE_PATH", fullPath);
                        byte[] bytes = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            bytes = Files.readAllBytes(file.toPath());
                        }

                        dos.writeInt(bytes.length);
                        dos.write(bytes);
                        dos.flush();
                        Log.d("CLIENT", "File send: " + file.getAbsolutePath());

                        int length = dis.readInt();
                        byte[] received = new byte[length];
                        dis.readFully(received);

                        File receivedFile = new File(getFilesDir(), "contracts.json");
                        try (FileOutputStream fos = new FileOutputStream(receivedFile)) {
                            fos.write(received);
                        }

                        Log.d("CLIENT", "File Received: " + receivedFile.getAbsolutePath());

                        DataBaseHalper db = new DataBaseHalper(v.getContext());
                        db.changeReservedDays();
                        int month = 5; // which month
                        db.addAssignments(month,receivedFile);
                        startActivity(new Intent(PortInformationActivity.this, SendServerActivity.class));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();




            }
        });

    }
}
