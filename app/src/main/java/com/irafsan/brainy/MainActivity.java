package com.irafsan.brainy;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button playButton;
    private TextView timerView,puzzleView,resultView,scoreView;
    private ArrayList<Integer> choices = new ArrayList<>();
    private int answer;
    private int score = 0;
    private int level = 0;
    private CountDownTimer timer;

    public void playAgain(View view){

        score = 0;
        level = 0;
        timerView.setText("30s");
        scoreView.setText("0/0");
        resultView.setText("");
        playButton.setVisibility(View.INVISIBLE);
        generatePuzzle();
    }

    public void makeAnswer(View view){

        if(view.getTag().toString().equals(Integer.toString(answer))){
            score++;
            resultView.setText("Correct");
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(400);
        }
        else{
            resultView.setText("Wrong");
        }

        level++;
        scoreView.setText(Integer.toString(score)+"/"+Integer.toString(level));
        generatePuzzle();
    }

    private void populateList(int a, int b){

        puzzleView.setText(Integer.toString(a) + " + " + Integer.toString(b));
        choices.clear();

        for(int i=0; i<4; i++){

            if(i==answer){
                choices.add((a+b));
            }
            else{

                Random random = new Random();
                int wrong = 1+random.nextInt(41);

                while(wrong == (a+b)){
                    wrong = 1+random.nextInt(41);
                }

                choices.add(wrong);
            }
        }
    }

    private void generatePuzzle(){

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        Random random = new Random();
        int a = 1+random.nextInt(21);
        int b = 1+random.nextInt(21);
        answer = random.nextInt(4);

        populateList(a,b);

        button0.setText(Integer.toString(choices.get(0)));
        button1.setText(Integer.toString(choices.get(1)));
        button2.setText(Integer.toString(choices.get(2)));
        button3.setText(Integer.toString(choices.get(3)));

        if(timer != null){
            timer.cancel();
        }

        setTimer();
    }

    private void setTimer(){

        timer = new CountDownTimer(10000,1000){

            public void onTick(long millisUntilFinished) {
                timerView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            public void onFinish() {
                playButton.setVisibility(View.VISIBLE);
                timerView.setText("0s");
                resultView.setText("Your scores:" + String.valueOf(score));
            }

        };

        timer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.playButton);
        timerView = (TextView) findViewById(R.id.timerView);
        puzzleView = (TextView) findViewById(R.id.puzzleView);
        resultView = (TextView) findViewById(R.id.resultView);
        scoreView = (TextView) findViewById(R.id.scoreView);
        generatePuzzle();
    }
}
