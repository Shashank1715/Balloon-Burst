package com.example.hammergame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTime, textViewScore, textViewCountDown;
    private GridLayout gridLayout;
    private ImageView ballon1, ballon2, ballon3, ballon4, ballon5, ballon6, ballon7, ballon8, ballon9;
    int score = 0;
    Runnable runnable;
    Handler handler;
    ImageView[] balloonsArray;
    MediaPlayer mediaPlayer;
    boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTime = findViewById(R.id.textViewTime);
        textViewCountDown = findViewById(R.id.textViewCountDown);
        textViewScore = findViewById(R.id.textViewScore);
        ballon1 = findViewById(R.id.ballon1);
        ballon2 = findViewById(R.id.ballon2);
        ballon3 = findViewById(R.id.ballon3);
        ballon4 = findViewById(R.id.ballon4);
        ballon5 = findViewById(R.id.ballon5);
        ballon6 = findViewById(R.id.ballon6);
        ballon7 = findViewById(R.id.ballon7);
        ballon8 = findViewById(R.id.ballon8);
        ballon9 = findViewById(R.id.ballon9);
        gridLayout = findViewById(R.id.gridlayout);

        mediaPlayer = MediaPlayer.create(this, R.raw.balloon_sound);
        balloonsArray = new ImageView[]{ballon1, ballon2, ballon3, ballon4, ballon5, ballon6, ballon7, ballon8, ballon9};

        new CountDownTimer(6000, 1000) {

            @Override
            public void onTick(long l) {
                textViewCountDown.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                balloonsControl();

                new CountDownTimer(31000, 1000) {

                    @Override
                    public void onTick(long l) {
                        textViewTime.setText("Remaining Time: " + l / 1000);
                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                        finish();
                    }
                }.start();
            }
        }.start();
    }

    public void increaseScoreByOne(View view) {
        score++;
        textViewScore.setText("Score : " + score);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
        mediaPlayer.start();
        if (view.getId() == ballon1.getId()) {
            ballon1.setImageResource(R.drawable.boom);
        }
        if (view.getId() == ballon2.getId()) {
            ballon2.setImageResource(R.drawable.boom);
        }
        if (view.getId() == ballon3.getId()) {
            ballon3.setImageResource(R.drawable.boom);
        }
        if (view.getId() == ballon4.getId()) {
            ballon4.setImageResource(R.drawable.boom);
        }
        if (view.getId() == ballon5.getId()) {
            ballon5.setImageResource(R.drawable.boom);
        }
        if (view.getId() == ballon6.getId()) {
            ballon6.setImageResource(R.drawable.boom);
        }
        if (view.getId() == ballon7.getId()) {
            ballon7.setImageResource(R.drawable.boom);
        }
        if (view.getId() == ballon8.getId()) {
            ballon8.setImageResource(R.drawable.boom);
        }
        if (view.getId() == ballon9.getId()) {
            ballon9.setImageResource(R.drawable.boom);
        }
    }

    public void balloonsControl() {
        textViewCountDown.setVisibility(View.INVISIBLE);
        textViewTime.setVisibility(View.VISIBLE);
        textViewScore.setVisibility(View.VISIBLE);


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView ballon : balloonsArray) {
                    ballon.setVisibility(View.INVISIBLE);
                    ballon.setImageResource(R.drawable.ballons);
                }
                gridLayout.setVisibility(View.VISIBLE);
                Random random = new Random();
                int i = random.nextInt(balloonsArray.length);
                balloonsArray[i].setVisibility(View.VISIBLE);
                if (score <= 2) {
                    handler.postDelayed(runnable, 2000);
                } else if (score > 2 && score <= 6) {
                    handler.postDelayed(runnable, 1500);
                } else if (score > 6 && score <= 13) {
                    handler.postDelayed(runnable, 1000);
                } else if (score > 13) {
                    handler.postDelayed(runnable, 500);
                }
//                handler.postDelayed(runnable,500);

            }
        };
        handler.post(runnable);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.volume) {
            if (!status) {
                mediaPlayer.setVolume(0, 0);
                item.setIcon(R.drawable.volume_off);
                status = true;
            } else {
                mediaPlayer.setVolume(1, 1);
                item.setIcon(R.drawable.volume_up);
                status = false;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Balloon Burst");
        builder.setMessage("Are you sure to Quit the game?");
        builder.setCancelable(false);
        builder.setNegativeButton("Quit Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }
}