package com.example.internshipproject.halpers;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;

public class JsonHelper {
    private static final String FILE_NAME = "suppliers.json";

    private static JSONObject loadOrCreate(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);

        if (!file.exists()) {
            try {
                JSONObject root = new JSONObject();
                root.put("clients", new JSONArray());
                save(context, root);
                return root;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return new JSONObject(sb.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }

    private static void save(Context context, JSONObject root) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(root.toString(4).getBytes());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void addClient(Context context, String supplierInfo, String supplierType, String lastReservedDays) {
        JSONObject root = loadOrCreate(context);

        try {
            JSONArray clients = root.getJSONArray("clients");

            JSONObject newClient = new JSONObject();
            newClient.put("supplierInfo", supplierInfo);
            newClient.put("supplierType", supplierType);
            newClient.put("lastReservedDays", lastReservedDays);

            clients.put(newClient);

            save(context, root);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void deleteClient(Context context, String supplierInfoToDelete) {
        try {
            JSONObject root = loadOrCreate(context);
            JSONArray clients = root.getJSONArray("clients");

            JSONArray updatedClients = new JSONArray();

            for (int i = 0; i < clients.length(); i++) {
                JSONObject client = clients.getJSONObject(i);
                String info = client.getString("supplierInfo");
                if (!info.equals(supplierInfoToDelete)) {
                    updatedClients.put(client);
                }
            }

            root.put("clients", updatedClients);

            save(context, root);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public  void updateReservedDays(Context context, String supplierInfo, String newReservedDays) {
        JSONObject root = loadOrCreate(context);

        try {
            JSONArray clients = root.getJSONArray("clients");

            for (int i = 0; i < clients.length(); i++) {
                JSONObject client = clients.getJSONObject(i);

                if (client.has("supplierInfo") && client.getString("supplierInfo").equals(supplierInfo)) {
                    client.put("lastReservedDays", newReservedDays);
                    break;
                }
            }

            save(context, root);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public  void updateType(Context context, String supplierInfo, String newType) {
        JSONObject root = loadOrCreate(context);

        try {
            JSONArray clients = root.getJSONArray("clients");

            for (int i = 0; i < clients.length(); i++) {
                JSONObject client = clients.getJSONObject(i);

                if (client.has("supplierInfo") && client.getString("supplierInfo").equals(supplierInfo)) {
                    client.put("supplierType", newType);
                    break;
                }
            }

            save(context, root);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

