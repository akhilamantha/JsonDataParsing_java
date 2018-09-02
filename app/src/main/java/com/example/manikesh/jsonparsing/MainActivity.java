package com.example.manikesh.jsonparsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/*
import java.util.List;
*/
//import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
  //  private ListView lv;

    private RecyclerView recyclerView;

   // private RecyclerView recyclerView;
   // private RecyclerView recyclerView;
    // URL to get contacts JSON
    private static String url = "https://api.androidhive.info/contacts/";

    ArrayList<Customers> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        //lv = (ListView) findViewById(R.id.list);
   Customers c = new Customers();
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     * AsyncTask enables proper and easy use of the UI thread. This class allows
     * you to perform background operations and publish results on the UI thread without
     * having to manipulate threads and/or handlers.
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");

                        Customers contact = new Customers();
                        contact.setName(name);
                        contact.setEmail(email);
                        contact.setPhone(id);
                       /* String address = c.getString("address");
                        String gender = c.getString("gender");*/

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                       /* String home = phone.getString("home");
                        String office = phone.getString("office");*/

                        // tmp hash map for single contact
                      //  HashMap<String, String> contact = new HashMap<>();


                        // adding each child node to HashMap key => value
                       /* contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);*/

                      // contact.setEmail("email");
                       contact.getEmail();
                       contact.getName();
                       contact.getPhone();

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into RECYCLER VIEW
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, (ArrayList) contactList,
                    R.layout.list_item, new String[]{"name", "email",
                    "mobile"}, new int[]{R.id.name,
                    R.id.email, R.id.mobile});

            adapter.areAllItemsEnabled();
            
        }

    }
}
