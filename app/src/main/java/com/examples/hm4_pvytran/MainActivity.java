package com.examples.hm4_pvytran;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int images[] = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5};
    int currentImage;
    private ImageView imageView2;
    private Button prevButton, nextButton, infoButton, prefsButton;
    MediaPlayer mp;
    SoundPool sp;
    int sound_click, sound_panda, sound_bear, sound_lion, sound_squirrel, sound_fox;
    int num_sounds_loaded;
    boolean sounds_loaded;

    @Override
    protected void onCreate(Bundle inBundle) {
        super.onCreate(inBundle);
        setContentView(R.layout.activity_main);

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        prevButton = (Button) findViewById(R.id.prevButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        infoButton = (Button) findViewById(R.id.infoButton);
        prefsButton = (Button) findViewById(R.id.prefsButton);

        if (inBundle != null)
            currentImage = inBundle.getInt("currentImage");
        else
            currentImage = 0;

        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        prefsButton.setOnClickListener(this);

        //Init media player (not started yet)
        //Assets.mp = null;

        num_sounds_loaded = 0;
        sounds_loaded = false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Assets.sp = new SoundPool (6, AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool sounds = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }
        Assets.sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                num_sounds_loaded++;
                if (num_sounds_loaded == 6)
                     sounds_loaded = true; }
        });
        sound_click = Assets.sp.load(this, R.raw.click,1);
        sound_panda = Assets.sp.load(this, R.raw.panda, 1);
        sound_bear = Assets.sp.load(this, R.raw.bear,1);
        sound_lion = Assets.sp.load(this, R.raw.lion, 1);
        sound_squirrel = Assets.sp.load(this, R.raw.squirrel, 1);
        sound_fox = Assets.sp.load(this, R.raw.fox, 1);
    }
    @Override
    public void onClick (View v) {
        switch (v.getId()) {

            case R.id.nextButton:
                if (sounds_loaded)
                    Assets.sp.play(sound_click, 1, 1, 0, 0, 1);
                if (currentImage<4) {
                    currentImage++;
                    imageView2.setImageResource(images[currentImage]); }
                else {
                    currentImage=0;
                    imageView2.setImageResource(images[currentImage]); }
                    break;

            case R.id.prevButton:
                if (sounds_loaded)
                    Assets.sp.play(sound_click, 1, 1, 0, 0, 1);
                if (currentImage>0) {
                    currentImage--;
                    imageView2.setImageResource(images[currentImage]);}
                else {
                    currentImage=4;
                    imageView2.setImageResource(images[currentImage]); }
                    break;

            case R.id.imageView2:
                final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean b = prefs.getBoolean("key_sound_enabled", true);
                if (b == true) {
                    if (sounds_loaded & currentImage == 0) {
                        Assets.sp.play(sound_panda, 1, 1, 0, 0, 1);
                        Toast.makeText(this, "Sound of Panda", Toast.LENGTH_SHORT).show();
                    }
                    if (sounds_loaded & currentImage == 1) {
                        Assets.sp.play(sound_bear, 1, 1, 0, 0, 1);
                        Toast.makeText(this, "Sound of Red Bear", Toast.LENGTH_SHORT).show();
                    }
                    if (sounds_loaded & currentImage == 2) {
                        Assets.sp.play(sound_lion, 1, 1, 0, 0, 1);
                        Toast.makeText(this, "Sound of Lion", Toast.LENGTH_SHORT).show();
                    }
                    if (sounds_loaded & currentImage == 3) {
                        Assets.sp.play(sound_squirrel, 1, 1, 0, 0, 1);
                        Toast.makeText(this, "Sound of Squirrel", Toast.LENGTH_SHORT).show();
                    }
                    if (sounds_loaded & currentImage == 4) {
                        Assets.sp.play(sound_fox, 1, 1, 0, 0, 1);
                        Toast.makeText(this, "Sound of Fox", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.infoButton:
                if (sounds_loaded)
                    Assets.sp.play(sound_click, 1, 1, 0, 0, 1);
                if (currentImage==0) {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Giant_panda"));
                    startActivity(myIntent);}
                if (currentImage==1) {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Red_panda"));
                    startActivity(myIntent);}
                if (currentImage==2) {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Lion"));
                    startActivity(myIntent); }
                if (currentImage==3) {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Squirrel"));
                    startActivity(myIntent); }
                if (currentImage==4) {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Fox"));
                    startActivity(myIntent); }
                break;

            case R.id.prefsButton:
                if (sounds_loaded)
                    Assets.sp.play(sound_click, 1, 1, 0, 0, 1);
                startActivity(new Intent(this, PrefsActivity.class));
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        imageView2.setImageResource(images[currentImage]);

        Assets.mp = null;

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean b = prefs.getBoolean("key_music_enabled", true);
        if (b == true) {
            if (Assets.mp != null) {
                Assets.mp.release();
                Assets.mp = null;
            }
            Assets.mp = MediaPlayer.create(this, R.raw.background);
            Assets.mp.setLooping(true);
            Assets.mp.start();
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outBundle) {
        super.onSaveInstanceState(outBundle);
        outBundle.putInt("currentImage", currentImage);
    }

    @Override
    protected void onPause() {
        if (isFinishing() ) {
            if ( Assets.mp != null) {
                Assets.mp.stop();
                Assets.mp.release();
                Assets.mp = null;
            }
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        finish(); }
                        } )
                .setNegativeButton("No", null)
                .show();
    }
}
