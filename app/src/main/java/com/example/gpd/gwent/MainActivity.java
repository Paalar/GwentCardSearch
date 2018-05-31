package com.example.gpd.gwent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static String url;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> cardList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cardName = (EditText) findViewById(R.id.cardName);
                //Toast.makeText(MainActivity.this, cardName.getText().toString(), Toast.LENGTH_LONG).show();
                cardList = new ArrayList<>();
                if (!cardName.getText().toString().trim().isEmpty()) {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    setUrl("https://gwent-api.herokuapp.com/card/name/" + cardName.getText().toString());
                    new getImage().execute();
                } else if (cardName.getText().toString().trim().isEmpty())
                    Toast.makeText(MainActivity.this, "Need to search for a filter or a card name", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void onFilterClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        cardList = new ArrayList<>();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioCommon:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/rarity/common");
                    new getImage().execute();
                break;
            case R.id.radioRare:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "rarity/rare");
                new getImage().execute();
                break;
            case R.id.radioEpic:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "rarity/epic");
                new getImage().execute();
                break;
            case R.id.radioLegendary:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "rarity/legendary");
                new getImage().execute();
                break;
            case R.id.radioNeutral:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "faction/neutral");
                new getImage().execute();
                break;
            case R.id.radioNorthern:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "faction/northern%20realms");
                new getImage().execute();
                break;
            case R.id.radioScoia:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "faction/scoia'tael");
                new getImage().execute();
                break;
            case R.id.radioMonster:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "faction/monsters");
                new getImage().execute();
                break;
            case R.id.radioSkellige:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "faction/skellige");
                new getImage().execute();
                break;
            case R.id.radioAny:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "lane/any");
                new getImage().execute();
                break;
            case R.id.radioMelee:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "lane/melee");
                new getImage().execute();
                break;
            case R.id.radioRanged:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "lane/ranged");
                new getImage().execute();
                break;
            case R.id.radioSiege:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "lane/siege");
                new getImage().execute();
                break;
            case R.id.radioBronze:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "group/bronze");
                new getImage().execute();
                break;
            case R.id.radioSilver:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "group/silver");
                new getImage().execute();
                break;
            case R.id.radioGold:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "group/gold");
                new getImage().execute();
                break;
            case R.id.radioEvent:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "lane/event");
                new getImage().execute();
                break;
            case R.id.radioLeader:
                if (checked)
                    setUrl("https://gwent-api.herokuapp.com/card/" + "group/leader");
                new getImage().execute();
                break;
        }
    }

    private String getUrl() { //not used atm.
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private class getImage extends AsyncTask<Void, Void, Void> { //not really sure honestly.

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
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try { //If there are several objects/cards, parse all of the information.
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int j = 0; j < jsonArray.length(); j++ ) {
                        JSONObject e = jsonArray.getJSONObject(j);

                        HashMap<String, String> info = new HashMap<>();
                        info.put("name", e.getString("name"));
                        info.put("strength", e.getString("strength"));
                        info.put("group", e.getString("group"));
                        info.put("rarity", e.getString("rarity"));
                        info.put("text", e.getString("text"));
                        info.put("traits", e.getString("traits"));
                        info.put("faction", e.getString("faction"));
                        info.put("lane", e.getString("lane"));
                        info.put("loyalty", e.getString("loyalty"));
                        info.put("image", e.getString("image"));
                        info.put("flavor", e.getString("flavor"));
                        info.put("tokens", e.getString("tokens"));
                        cardList.add(info);
                    }

                } catch (final JSONException e) { //If it is just a single object/card in the JSON file, parse it's information.
                    try {
                        JSONObject jsonObject = new JSONObject(jsonStr);


                        HashMap<String, String> info = new HashMap<>();
                        info.put("name", jsonObject.getString("name"));
                        info.put("strength", jsonObject.getString("strength"));
                        info.put("group", jsonObject.getString("group"));
                        info.put("rarity", jsonObject.getString("rarity"));
                        info.put("text", jsonObject.getString("text"));
                        info.put("traits", jsonObject.getString("traits"));
                        info.put("faction", jsonObject.getString("faction"));
                        info.put("lane", jsonObject.getString("lane"));
                        info.put("loyalty", jsonObject.getString("loyalty"));
                        info.put("image", jsonObject.getString("image"));
                        info.put("flavor", jsonObject.getString("flavor"));
                        info.put("tokens", jsonObject.getString("tokens"));
                        cardList.add(info);

                    } catch (final JSONException i) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            } else { //If nothing works, this will launch. Probably server-side fault.
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });

            }

            return null;

        }

        @Override // If the JSON information has been parsed correctly it will launch a new activity that will display the information
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (cardList.size() != 0) {
                Intent intent = new Intent(MainActivity.this, ImagesActivity.class);
                intent.putExtra("allCardInfo", cardList);
                startActivity(intent);
            } else {
                EditText cardName = (EditText) findViewById(R.id.cardName);
                Toast.makeText(MainActivity.this, cardName.getText().toString() + " doesn't exist in the database", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
