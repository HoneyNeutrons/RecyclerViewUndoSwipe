package com.honeyneutrons.undoswipe;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class AboutActivity extends AppCompatActivity {


    private final int currentapiVersion = Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_about);
        TextView tvLogo=(TextView)findViewById(R.id.tvLogo);
        TextView tvHn=(TextView)findViewById(R.id.tvhn);

        assert tvLogo!=null;

        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
          this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        }

        assert  tvHn!=null;
        tvHn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/1/communities/116776760846214921756"));
                startActivity(browserIntent);
            }
        });
    }



   }
