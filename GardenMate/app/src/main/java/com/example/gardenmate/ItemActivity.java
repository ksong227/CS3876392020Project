package com.example.gardenmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class ItemActivity extends AppCompatActivity {

    String name;
    String url;
    String encodedUrl;
    TextView title;
    public static TextView jsonText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //get extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name  = extras.getString("name");
            url = extras.getString("url");
        }

        title = findViewById(R.id.text_view_item_name);
        jsonText = findViewById(R.id.text_view_json);
        //imageView = findViewById(R.id.item_image_view);

        try {
            encodedUrl = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        title.setText(name);

//        Picasso.with(this)
//                .load(url)
//                .placeholder(R.drawable.ic_loading)
//                .into(imageView);





        Button buttonSearch = findViewById(R.id.button_search);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://garden.org/plants/search/text/?q="+name));
//                startActivity(intent);

                parseJSON parse = new parseJSON();
                parse.execute();
            }
        });
    }


}