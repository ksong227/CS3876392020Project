package com.example.gardenmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Browser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Tab2Fragment extends Fragment {

    public static TextView textView;
    public static WebView webView;
    public static ExtendedFloatingActionButton fabSearch;
    private String url1 = "https://en.wikipedia.org/wiki/Special:Search?search=";
    private String url2 = "https://garden.org/plants/search/text/?q=";

    public static Upload item;

    public Tab2Fragment() {
        // Required empty public constructor
    }

    public static Tab2Fragment newInstance(Upload item) {
        Tab2Fragment fragment = new Tab2Fragment();
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
        final View view =  inflater.inflate(R.layout.fragment_tab2, container, false);

        textView = view.findViewById(R.id.text_view_warning);
        webView = view.findViewById(R.id.web_view);
        fabSearch = view.findViewById(R.id.fab_search);

        if (item.getIdentified())
        {
            textView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            fabSearch.setVisibility(View.VISIBLE);

            url1 = url1+item.getName();

            webView.setWebViewClient(new Browser());
            webView.loadUrl(url1);

            fabSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url2+item.getName()));
                    startActivity(intent);
                }
            });
        }

        return view;
    }

    public static class Browser extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}