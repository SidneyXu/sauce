package com.bookislife.sauce.sample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.bookislife.sauce.Sauce;
import com.bookislife.sauce.SauceAndroid;
import com.bookislife.sauce.files.AndroidFileHandle;
import com.bookislife.sauce.providers.ImageProvider;
import com.bookislife.sauce.sample.R;

import java.util.Random;

/**
 * Created by SidneyXu on 2015/10/07.
 */
public class ImageActivity extends AppCompatActivity {

    private Random random = new Random();
    public static final String BASE = "http://i.imgur.com/";
    public static final String EXT = ".jpg";
    public static final String[] URLS = {
            BASE + "CqmBjo5" + EXT, BASE + "zkaAooq" + EXT, BASE + "0gqnEaY" + EXT,
            BASE + "9gbQ7YR" + EXT, BASE + "aFhEEby" + EXT, BASE + "0E2tgV7" + EXT,
            BASE + "P5JLfjk" + EXT, BASE + "nz67a4F" + EXT, BASE + "dFH34N5" + EXT,
            BASE + "FI49ftb" + EXT, BASE + "DvpvklR" + EXT, BASE + "DNKnbG8" + EXT,
            BASE + "yAdbrLp" + EXT, BASE + "55w5Km7" + EXT, BASE + "NIwNTMR" + EXT,
            BASE + "DAl0KB8" + EXT, BASE + "xZLIYFV" + EXT, BASE + "HvTyeh3" + EXT,
            BASE + "Ig9oHCM" + EXT, BASE + "7GUv9qa" + EXT, BASE + "i5vXmXp" + EXT,
            BASE + "glyvuXg" + EXT, BASE + "u6JF6JZ" + EXT, BASE + "ExwR7ap" + EXT,
            BASE + "Q54zMKT" + EXT, BASE + "9t6hLbm" + EXT, BASE + "F8n3Ic6" + EXT,
            BASE + "P5ZRSvT" + EXT, BASE + "jbemFzr" + EXT, BASE + "8B7haIK" + EXT,
            BASE + "aSeTYQr" + EXT, BASE + "OKvWoTh" + EXT, BASE + "zD3gT4Z" + EXT,
            BASE + "z77CaIt" + EXT,
    };

    public String getUrl() {
        String url = URLS[random.nextInt(URLS.length - 1)];
        System.out.println(url);
        return url;
    }

    private SauceAndroid sauceAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        sauceAndroid= (SauceAndroid) Sauce.getPlatform();
        final ImageProvider imageProvider= (ImageProvider) sauceAndroid.getProviders(SauceAndroid.IMAGE_PROVIDER);

        final ImageView imageView = (ImageView) findViewById(R.id.image);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getUrl();
                imageProvider.load((AndroidFileHandle) sauceAndroid.files.internal("404.jpg"));
            }
        });
    }
}
