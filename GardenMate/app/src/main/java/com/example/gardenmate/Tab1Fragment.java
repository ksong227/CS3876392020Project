package com.example.gardenmate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Tab1Fragment extends Fragment {

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

    public static Upload item;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    public static Tab1Fragment newInstance(Upload item) {
        Tab1Fragment fragment = new Tab1Fragment();
        Bundle args = new Bundle();
        args.putSerializable("Upload", item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item = (Upload) getArguments().getSerializable("Upload");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_tab1, container, false);

        //Log.i("TAG", "DEBUG FRAG NAME: "+item.getName());

        name = item.getName();
        imageUrl = item.getImageUrl();

        title = view.findViewById(R.id.text_view_item_name);
        imageView = view.findViewById(R.id.item_image_view);
        fabParseFlower = view.findViewById(R.id.fab_parseF);
        fabParseLeaf = view.findViewById(R.id.fab_parseL);

        title.setText(name);
        Picasso.with(getContext())
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
                organ = "&organs=flower";
                identifierUrl = plantNetUrl + apiKey + encodedImageUrl + organ;

                IdentifyTask parse = new IdentifyTask(getContext());
                parse.execute(identifierUrl);
            }
        });

        fabParseLeaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                organ = "&organs=leaf";
                identifierUrl = plantNetUrl + apiKey + encodedImageUrl + organ;

                IdentifyTask parse = new IdentifyTask(getContext());
                parse.execute(identifierUrl);
            }
        });

        return view;
    }
}