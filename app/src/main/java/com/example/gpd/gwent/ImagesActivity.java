package com.example.gpd.gwent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

public class ImagesActivity extends AppCompatActivity {
    private  ImageView imageView;
    private Button pr, nx, bInfo;
    private static int cardIndex;
    ArrayList<HashMap<String, String>> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        cardList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("allCardInfo");
        setCardList(cardList);
        setCardIndex(0);
        pr = (Button) findViewById(R.id.previous);
        nx = (Button) findViewById(R.id.next);
        bInfo = (Button) findViewById(R.id.moreInfo);

        if (cardList.size() == 1) {
            pr.setVisibility(View.INVISIBLE);
            nx.setVisibility(View.INVISIBLE);
        }

        //Load the image
        imageView = (ImageView) findViewById(R.id.imgSwitch);
        loadImageFromUrl(cardList.get(cardIndex).get("image"));

        //Load the text
        loadText(cardIndex);

        pr.setOnClickListener(new View.OnClickListener(){ //Previous button, loads previous text and image
                @Override
                public void onClick(View v) {
                    left();
            }
        });


        nx.setOnClickListener(new View.OnClickListener(){ //next button, loads next text and image
                @Override
                public void onClick(View v) {
                    right();
            }
        });

        bInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImagesActivity.this, InfoActivity.class);
                intent.putExtra("cardInfo", cardList.get(cardIndex));
                startActivity(intent);
            }
        });

        imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                right();
            }
            @Override
            public void onSwipeRight() {
                left();
            }
        });
    }

    private void loadImageFromUrl(String url) { //loads the image url. with some magic from picasso.
        try {
            for (int i = cardIndex; i < cardIndex+3; i++) {
                try {
                    Picasso.with(this).load(cardList.get(i).get("image")).fetch();
                } catch (Exception j) {

                }
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                Picasso.with(this).load(url).error(R.mipmap.ic_launcher)
                        .into(imageView, new com.squareup.picasso.Callback() {

                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }

        } catch (Exception e) {
            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            imageView.setImageDrawable(null);
            Toast.makeText(this, "No image to display.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadText(int index) { //loads the card text, only the name and flavor.
        TextView textView = (TextView) findViewById(R.id.txtSwitch);
        String cards = "";
        cards += cardList.get(index).get("name") + "\n" +
                 cardList.get(index).get("flavor") +"\n\n";
                //"group: " + cardList.get(cardIndex).get("group") +"\n\n";
        textView.setText(cards);
    }

    public void left() {
        int currIndex = getCardIndex();
        if (currIndex > 0) {
            currIndex -= 1;
            setCardIndex(currIndex);
            loadImageFromUrl(cardList.get(currIndex).get("image"));
            loadText(cardIndex);
        } else {
            return;
        }
    }

    public void right() {
        int currIndex = getCardIndex();
        if (currIndex < cardList.size() -1 ) {
            currIndex += 1;
            setCardIndex(currIndex);
            loadImageFromUrl(cardList.get(currIndex).get("image"));
            loadText(cardIndex);
        } else {
            return;
        }
    }

    public static void setCardIndex(int cardIndex) {
        ImagesActivity.cardIndex = cardIndex;
    }

    public static int getCardIndex() {
        return cardIndex;
    }

    public void setCardList(ArrayList<HashMap<String, String>> cardList) {
        this.cardList = cardList;
    }
}
