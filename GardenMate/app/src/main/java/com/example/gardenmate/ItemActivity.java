package com.example.gardenmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class ItemActivity extends AppCompatActivity {

    public static Upload item;
    public static String name;
    String imageUrl;

    public static TextView title;
    ImageView imageView;

    String identifierUrl;
    String plantNetUrl = "https://my-api.plantnet.org/v2/identify/all?";
    String apiKey = "api-key=2a10vAmLauo1W9nGfh8EHZbye";
    String encodedImageUrl;
    String organ;

    private ExtendedFloatingActionButton fabParseFlower;
    private ExtendedFloatingActionButton fabParseLeaf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //get object
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            item  = (Upload) getIntent().getSerializableExtra("item");
        }
        name = item.getName();
        imageUrl = item.getImageUrl();

        title = findViewById(R.id.text_view_item_name);
        imageView = findViewById(R.id.item_image_view);
        fabParseFlower = findViewById(R.id.fab_parseF);
        fabParseLeaf = findViewById(R.id.fab_parseL);

        title.setText(name);
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_flower)
                .into(imageView);

        //encode url
        try {
            encodedImageUrl = "&images=" + URLEncoder.encode(imageUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        fabParseFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://garden.org/plants/search/text/?q="+name));
//                startActivity(intent);

                organ = "&organs=flower";
                identifierUrl = plantNetUrl + apiKey + encodedImageUrl + organ;

                parseJSON parse = new parseJSON();
                parse.execute(identifierUrl);
            }
        });

        fabParseLeaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                organ = "&organs=leaf";
                identifierUrl = plantNetUrl + apiKey + encodedImageUrl + organ;

                parseJSON parse = new parseJSON();
                parse.execute(identifierUrl);
            }
        });
    }
}