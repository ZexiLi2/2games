package com.example.multijocs;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;


public class Memory extends AppCompatActivity {
    ImageButton btn01, btn02, btn03, btn04, btn05, btn06, btn07, btn08, btn09, btn10, btn11, btn12, btn13, btn14, btn15, btn16;
    ImageButton[] table = new ImageButton[16];
    TextView txtPoints;
    Button btnRestart, btnBack;

    int punts;
    int ancerts;

    int [] imatges;
    int bg;

    ArrayList<Integer> arrayRandomize;
    ImageButton first;
    int numFirst, numSecond;
    boolean block = false;
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mem), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        btnRestart = findViewById(R.id.buttonrestart);
        btnBack = findViewById(R.id.button2);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    private void loadTable() {
        btn01 = findViewById(R.id.ButtonOne);
        btn02 = findViewById(R.id.ButtonTwo);
        btn03 = findViewById(R.id.ButtonThree);
        btn04 = findViewById(R.id.ButtonFour);
        btn05 = findViewById(R.id.ButtonFive);
        btn06 = findViewById(R.id.ButtonSix);
        btn07 = findViewById(R.id.ButtonSeven);
        btn08 = findViewById(R.id.ButtonEight);
        btn09 = findViewById(R.id.ButtonNine);
        btn10 = findViewById(R.id.ButtonTen);
        btn11 = findViewById(R.id.ButtonEleven);
        btn12 = findViewById(R.id.ButtonTwelve);
        btn13 = findViewById(R.id.ButtonThirteen);
        btn14 = findViewById(R.id.ButtonFourteen);
        btn15 = findViewById(R.id.ButtonFifteen);
        btn16 = findViewById(R.id.ButtonSixteen);

        table[0] = btn01;
        table[1] = btn02;
        table[2] = btn03;
        table[3] = btn04;
        table[4] = btn05;
        table[5] = btn06;
        table[6] = btn07;
        table[7] = btn08;
        table[8] = btn09;
        table[9] = btn10;
        table[10] = btn11;
        table[11] = btn12;
        table[12] = btn13;
        table[13] = btn14;
        table[14] = btn15;
        table[15] = btn16;
    }

    /*
     * segons
     * https://www.unilabs.es/blog/habitos-saludables/alimentos-beneficiosos-cerebro-memoria/
     * aquests aliments són beneficiosos per la memòria :D
     */

    private void loadImg(){
        imatges = new int[]{
                R.drawable.choco,
                R.drawable.nuts,
                R.drawable.macadamia,
                R.drawable.rice,
                R.drawable.tea,
                R.drawable.berry,
                R.drawable.spinach,
                R.drawable.egg
        };
        bg = R.drawable.fondo2;
    }

    public void loadTxt() {
        txtPoints=findViewById(R.id.textView);
        punts = 0;
        ancerts = 0;
        txtPoints.setText("Score: " + punts);
    }

    private ArrayList<Integer> mix(int length){
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < length*2;i++){
            result.add(i % length);
        }
        Collections.shuffle(result);
        return result;
    }

    private void check(int i, final ImageButton imgb){
        if (first == null) {
            first = imgb;
            first.setScaleType(ImageView.ScaleType.CENTER_CROP);
            first.setImageResource(imatges[arrayRandomize.get(i)]);
            first.setEnabled(false);
            numFirst = arrayRandomize.get(i);
        } else {
            block = true;
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageResource(imatges[arrayRandomize.get(i)]);
            imgb.setEnabled(false);
            numSecond = arrayRandomize.get(i);
            if (numFirst == numSecond) {
                first = null;
                block = false;
                ancerts++;
                punts++;
                txtPoints.setText("Score: " + punts);
                if (ancerts == imatges.length) {
                    if (punts >= 0){
                        Toast toast = Toast.makeText(getApplicationContext(), "You won!", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "You have dementia!", Toast.LENGTH_LONG);
                        toast.show();
                    }

                }
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        first.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        first.setImageResource(bg);
                        first.setEnabled(true);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(bg);
                        imgb.setEnabled(true);
                        block = false;
                        first = null;
                        punts--;
                        txtPoints.setText("Score: " + punts);
                    }
                }, 1000);
            }
        }
    }

    private  void init() {
        loadTable();
        loadImg();
        loadTxt();
        arrayRandomize = mix(imatges.length);
        for(int i = 0; i<table.length;i++) {
            table[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            table[i].setImageResource(bg);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<table.length;i++) {
                    table[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    table[i].setImageResource(bg);
                }
            }
        }, 500);
        for (int i = 0; i<table.length;i++) {
            final int j = i;
            table[i].setEnabled(true);
            table[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!block)
                        check(j, table[j]);
                }
            });
        }

    }
}
