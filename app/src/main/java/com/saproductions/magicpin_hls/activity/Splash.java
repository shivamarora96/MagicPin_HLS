package com.saproductions.magicpin_hls.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.saproductions.magicpin_hls.R;

public class Splash extends AppCompatActivity {

    Button next;
    boolean autoloadVal = false;
    RadioButton autoload;
    RadioGroup rg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getSupportActionBar().hide();

        rg = (RadioGroup)findViewById(R.id.splash_rg);


        next = (Button)findViewById(R.id.splash_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = rg.getCheckedRadioButtonId();
                autoload = (RadioButton)findViewById(id);
                if(autoload == null)
                {
                    Toast.makeText(getApplicationContext(), "Kindly Select AutoLoad Please!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if("YES".equalsIgnoreCase(autoload.getText()+""))
                    autoloadVal = true;
                else
                    autoloadVal = false;

                Intent i = new Intent(Splash.this, HomeActivity.class);
                i.putExtra("AL" , autoloadVal);
                startActivity(i);
                Splash.this.finish();

            }
        });


    }
}
