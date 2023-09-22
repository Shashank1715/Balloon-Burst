package com.example.hammergame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView textViewInfo, textViewMyScore, textViewHighestScore;
    private Button buttonPlayAgain,buttonQuitGame;
    int myScore;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textViewInfo= findViewById(R.id.textViewinfo);
        textViewMyScore= findViewById(R.id.textViewMyScore);
        textViewHighestScore= findViewById(R.id.textViewHighestScore);
        buttonPlayAgain= findViewById(R.id.buttonPlayAgain);
        buttonQuitGame= findViewById(R.id.buttonQuitGame);

        myScore= getIntent().getIntExtra("score",0);
        textViewMyScore.setText("Your Score: "+myScore);

        sharedPreferences= this.getSharedPreferences("Score", Context.MODE_PRIVATE);
        int highestScore= sharedPreferences.getInt("highestScore",0);
        if(myScore>= highestScore)
        {
            sharedPreferences.edit().putInt("highestScore",myScore).apply();
            textViewHighestScore.setText("Highest Score: "+myScore);
            textViewInfo.setText("Congratulations You crack the highest score");
        }
        else {
            textViewHighestScore.setText("Highest Score: "+highestScore);
            if((highestScore-myScore)>10)
            {
                textViewInfo.setText("You must be little faster");
            }
            if((highestScore-myScore)>3 && (highestScore-myScore)<=10)
            {
                textViewInfo.setText("Good. You are become faster");
            }
            if((highestScore-myScore)<=3)
            {
                textViewInfo.setText("Excellent");
            }
        }
        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ResultActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonQuitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(ResultActivity.this);
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
        });
    }
}