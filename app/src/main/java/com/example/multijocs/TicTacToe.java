package com.example.multijocs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.Random;

public class TicTacToe extends AppCompatActivity {
    TextView txt_result;
    Button reset, exit;
    Integer[] buttons;

    int[]table= new int[]{
            0,0,0,
            0,0,0,
            0,0,0
    };

    int state = 0;
    int fitxesPosades = 0;
    int turn = 1;
    int[] positionWin = new int[]{
            -1,-1,-1,
            -1,-1,-1,
            -1,-1,-1
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tictactoe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tac), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_result=findViewById(R.id.EndTextW);
        txt_result.setVisibility(View.INVISIBLE);
        reset=findViewById(R.id.buttonRestarted);
        exit = findViewById(R.id.buttonExit);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        buttons= new Integer[]{
                R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6,
                R.id.button7, R.id.button8, R.id.button9
        };

    }

    public void setPiece(View view) {
        if (state == 0) {
            turn = 1;
            int numButton = Arrays.asList(buttons).indexOf(view.getId());

            if (table[numButton] == 0) {

                view.setBackgroundResource(R.drawable.x);
                Log.e("position", String.valueOf(numButton));
                table[numButton] = 1;
                state = testState();
                fitxesPosades++;
                endGame();
                if (state == 0) {
                    turn = -1;
                    bot();
                    state = testState();
                    fitxesPosades++;
                    endGame();
                }
            }
        }
    }

    public void bot(){
        Random random = new Random();
        int pos = random.nextInt(table.length);
        while (table[pos] != 0){
            pos = random.nextInt(table.length);
        }
        Button btn = (Button) findViewById(buttons[pos]);
        btn.setBackgroundResource(R.drawable.oo);
        table[pos] = -1;
    }

    public int testState() {
        /*if (fitxesPosades < 9) {
            return 0;
        } else {
            return 2;
        }*/
        int newState = 0;
        if(Math.abs(table[0] + table[1] + table[2]) == 3){
            positionWin = new int[]{0,1,2};
            newState = turn;
        }else if (Math.abs(table[3] + table[4] + table[5]) == 3){
            positionWin = new int[]{3,4,5};
            newState = turn;
        }
        else if (Math.abs(table[6] + table[7] + table[8]) == 3){
            positionWin = new int[]{6,7,8};
            newState = turn;
        }
        else if (Math.abs(table[0] + table[3] + table[6]) == 3){
            positionWin = new int[]{0,3,6};
            newState = turn;
        }
        else if (Math.abs(table[1] + table[4] + table[7]) == 3){
            positionWin = new int[]{1,4,7};
            newState = turn;
        }
        else if (Math.abs(table[2] + table[5] + table[8]) == 3){
            positionWin = new int[]{2,5,8};
            newState = turn;
        }
        else if (Math.abs(table[0] + table[4] + table[8]) == 3){
            positionWin = new int[]{0,4,8};
            newState = turn;
        }
        else if (Math.abs(table[2] + table[4] + table[6]) == 3){
            positionWin = new int[]{2,4,6};
            newState = turn;
        }
        else if ( fitxesPosades == 9) {
            newState = 2;
        }
        return  newState;
    }

    public void endGame() {

        if (state == 1 || state == -1) {
            if (state == 1) {
                txt_result.setVisibility(View.VISIBLE);
                txt_result.setTextColor(Color.parseColor("#00b017"));
            } else if (state == -1) {
                txt_result.setVisibility(View.VISIBLE);
                txt_result.setText("You Lost!");
                txt_result.setTextColor(Color.RED);

            }
            for (int i = 0; i < positionWin.length; i++) {
                Button button = findViewById(buttons[positionWin[i]]);
                button.setBackgroundResource(R.drawable.starplus);
            }
            if (state == 2) {
                txt_result.setVisibility(View.VISIBLE);
                txt_result.setText("It's a tie!");
                txt_result.setTextColor(Color.YELLOW);
            }
        }
    }
}
