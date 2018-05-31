package com.example.gpd.gwent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

public class InfoActivity extends AppCompatActivity {
    HashMap<String, String> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView textView = (TextView) findViewById(R.id.txtInfooo) ;
        cardList = (HashMap<String, String>) getIntent().getSerializableExtra("cardInfo");
        String info = "name: " + cardList.get("name") +"\n" +
                        "strength:\t" + cardList.get("strength") +"\n" +
                        "group:\t" + cardList.get("group") +"\n" +
                        "rarity:\t" + cardList.get("rarity") +"\n" +
                        "text:\t" + cardList.get("text") +"\n" +
                        "traits:\t" + cardList.get("traits") +"\n" +
                        "faction:\t" + cardList.get("faction") +"\n" +
                        "lane:\t" +  cardList.get("lane") + "\n" +
                        "loyalty:\t" + cardList.get("loyalty") +"\n" +
                        "image:\t" + cardList.get("image") +"\n" +
                        "flavor:\t" + cardList.get("flavor") +"\n" +
                        "tokens:\t" + cardList.get("tokens");
        textView.setText(info);
    }
}
