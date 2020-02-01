package com.project.shadowing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    private EarPhoneIntentReceiver myReceiver; // 이어폰


    static DBHelper dbHelper;
    static int position;
    static int cnt;

    boolean focusFlag = false;
    boolean StopFlag = false;
    boolean recordFlag = false;
    boolean playFlag = false;
    boolean btnVolFlag = false;
    boolean btnSubFlag = false;
    boolean startFlag = false;
    ImageButton btnRecord, btnStop, btnPlay, btnVol, btnSub, btnplayhome;
    static MediaPlayer mp;
    SeekBar seekBar, volumebar;
    TextView text, text2; // 시간 나타내주는애들

    FrameLayout volLinear;

    TextView textView;
    //    TextView stateText;
    TextView volumeText;

    static int progr;

    boolean listenCheck = false;
    boolean stopFlag = false;
    boolean endCheck = false;

    boolean isBackPressed = false;

    Sub[] subs0 = new Sub[8];
    Sub[] subs1 = new Sub[4];
    Sub[] subs2 = new Sub[5];
    Sub[] subs3 = new Sub[3];
    Sub[] subs4 = new Sub[4];

    int count = 0;
    int check;

    String ColorYellow = "#ffc03e";

    static final RecordManager[] recordManager = new RecordManager[10];
    static final ArrayList<Sub> Sub_PlayList = new ArrayList<>();

    //TextView textViewSub;
    TextView[] textViewSubNum = new TextView[10];
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        myReceiver = new EarPhoneIntentReceiver();  // 이어폰
        Log.d("Test", "!!");

        cnt = 0;


        scrollView = (ScrollView) findViewById(R.id.scrollView);
        //textViewSub = (TextView) findViewById(R.id.textViewSub);
        textViewSubNum[0] = (TextView) findViewById(R.id.textViewSubNum0);
        textViewSubNum[1] = (TextView) findViewById(R.id.textViewSubNum1);
        textViewSubNum[2] = (TextView) findViewById(R.id.textViewSubNum2);
        textViewSubNum[3] = (TextView) findViewById(R.id.textViewSubNum3);
        textViewSubNum[4] = (TextView) findViewById(R.id.textViewSubNum4);
        textViewSubNum[5] = (TextView) findViewById(R.id.textViewSubNum5);
        textViewSubNum[6] = (TextView) findViewById(R.id.textViewSubNum6);
        textViewSubNum[7] = (TextView) findViewById(R.id.textViewSubNum7);
        textViewSubNum[8] = (TextView) findViewById(R.id.textViewSubNum8);
        textViewSubNum[9] = (TextView) findViewById(R.id.textViewSubNum9);

        position = getIntent().getIntExtra("key", 0);


        subs0[0] = new Sub(0, 0, 0, 3020, "Mom, look at this picture of my classmates.\n");
        subs0[1] = new Sub(0, 3020, 1, 6020, "Oh, is your friend Julia in the picture?\n");
        subs0[2] = new Sub(0, 6020, 2, 8040, "Yes. She's wearing jeans.\n");
        subs0[3] = new Sub(0, 8040, 3, 10080, "You mean the girl with a hair band?\n");
        subs0[4] = new Sub(0, 10080, 4, 14080, "No, she's Nancy. Julia is wearing a necklace.\n");
        subs0[5] = new Sub(0, 14080, 5, 17130, "Oh, I found her. She's so cute.\n");
        subs0[6] = new Sub(0, 17130, 6, 20150, "Angelina is my favorite actress.\n");
        subs0[7] = new Sub(0, 20150, 7, 23170, "She has beautiful big blue eyes.\n");

        subs1[0] = new Sub(1, 0, 0, 2030, "I like her eyes most.\n");
        subs1[1] = new Sub(1, 2030, 1, 5060, "She also has very beautiful skin.\n");
        subs1[2] = new Sub(1, 5060, 2, 9120, "She has a wide mouth, and her lips are quite \n");
        subs1[3] = new Sub(1, 9120, 3, 13140, "Some people don't like her lips, but I like \n");

        subs2[0] = new Sub(2, 0, 0, 2040, "He has long hair.\n");
        subs2[1] = new Sub(2, 2040, 1, 4070, "He is sitting on the chair.\n");
        subs2[2] = new Sub(2, 4070, 2, 6100, "He is wearing a tie.\n");
        subs2[3] = new Sub(2, 6100, 3, 8190, "He is putting on glasses.\n");
        subs2[4] = new Sub(2, 8190, 4, 9270, "Attention, please!\n");

        subs3[0] = new Sub(3, 0, 0, 2030, "We're looking for a child.\n");
        subs3[1] = new Sub(3, 2030, 1, 6060, "Her name is Tiffany and she is five years old.\n");
        subs3[2] = new Sub(3, 6060, 2, 8100, "She has short brown hair.\n");

        subs4[0] = new Sub(4, 0, 0, 5050, "If you find her, please take her to the information center. Thank you.\n");
        subs4[1] = new Sub(4, 5050, 1, 8100, "Oh, my god! You're Tom, aren't you?\n");
        subs4[2] = new Sub(4, 8100, 2, 12120, "Vicky? Long time no see! You've changed a lot.\n");
        subs4[3] = new Sub(4, 12120, 3, 17190, "Have I? I was very thin, but these days I am a little fat.\n");

        Log.d("Test", "##PlayTestCreate");

        btnRecord = (ImageButton) findViewById(R.id.btnRecord);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnStop = (ImageButton) findViewById(R.id.btnStop);
        btnVol = (ImageButton) findViewById(R.id.btnVol);
        volLinear = (FrameLayout) findViewById(R.id.volLinear);
        btnSub = (ImageButton) findViewById(R.id.btnSub);
        btnplayhome = (ImageButton) findViewById(R.id.btnplayhome);

        if (position == 0) { // 처음 클릭시 생성해주는 생성자
            recordManager[position] = new RecordManager(this, subs0[subs0.length - 1].time());


            Log.d("Test", "##PlayTest01");
            mp = MediaPlayer.create(PlayActivity.this, R.raw.music0);

            btnStop.setEnabled(false);
            btnStop.setImageResource(R.drawable.ic_stop_disable);
            btnPlay.setEnabled(false);
            btnPlay.setImageResource(R.drawable.ic_play_disable);

            for (int i = 0; i < subs0.length; i++) {

                textViewSubNum[i].setText(subs0[i].sub);
                final int finalI = i;
                textViewSubNum[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cnt = 0;
                        mp.start();
                        Thread();
                        mp.seekTo(subs0[finalI].start);
                        textView.setVisibility(View.VISIBLE);
                        recordManager[position].recordFlag = true;
                        recordManager[position].endFlag = true;
// 자막 클릭했을때
                        btnRecord.setEnabled(false);
                        btnRecord.setImageResource(R.drawable.ic_rec_on);
                        btnStop.setEnabled(true);
                        btnStop.setImageResource(R.drawable.ic_stop_off);
                        btnPlay.setEnabled(false);
                        btnPlay.setImageResource(R.drawable.ic_play_disable);

//                        stateText.setText("Start");

                        listenCheck = false;
                        endCheck = false;

                        for (int i = 0; i < subs0.length; i++) { // 선택시 색깔 겹침 금지
                            if (textViewSubNum[i] != textViewSubNum[cnt]) {
                                textViewSubNum[i].setTextColor(Color.parseColor("black"));
                            }
                        }

                        cnt = finalI;
                        textViewSubNum[finalI].setTextColor(Color.parseColor(ColorYellow));


                        if (check == 0) { //recordManager[position] 시작이 됬나 안됬나 확인.
                            recordManager[position].start();
                            check++;
                            Log.d("Test", "***01");
                        }

                        if (cnt < subs0.length) {
                            if (subs0[cnt].time() >= mp.getCurrentPosition()) {
                                textView.setText(subs0[cnt].sub());

                                Log.d("Test", "##01@@@@" + cnt);
                                //textView.setTextColor(Color.parseColor("blue"));
                            } else
                                cnt++;
                        }
                    }
                });
            }
        }

        if (position == 1) { // 처음 클릭시 생성해주는 생성자
            recordManager[position] = new RecordManager(this, subs1[subs1.length - 1].time());
            Log.d("Test", "##PlayTest01");
            mp = MediaPlayer.create(PlayActivity.this, R.raw.music1);

            btnStop.setEnabled(false);
            btnStop.setImageResource(R.drawable.ic_stop_disable);
            btnPlay.setEnabled(false);
            btnPlay.setImageResource(R.drawable.ic_play_disable);


            for (int i = 0; i < subs1.length; i++) {

                textViewSubNum[i].setText(subs1[i].sub);
                final int finalI = i;
                textViewSubNum[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setVisibility(View.VISIBLE);
                        cnt = 0;
                        mp.start();
                        Thread();
                        mp.seekTo(subs1[finalI].start);
                        recordManager[position].recordFlag = true;
                        recordManager[position].endFlag = true;

                        btnRecord.setEnabled(false);
                        btnRecord.setImageResource(R.drawable.ic_rec_on);
                        btnStop.setEnabled(true);
                        btnStop.setImageResource(R.drawable.ic_stop_off);
                        btnPlay.setEnabled(false);
                        btnPlay.setImageResource(R.drawable.ic_play_disable);


//                        stateText.setText("Start");

                        listenCheck = false;
                        endCheck = false;
                        cnt = finalI;


                        for (int i = 0; i < subs1.length; i++) { // 선택시 색깔 겹침 금지
                            if (textViewSubNum[i] != textViewSubNum[cnt]) {
                                textViewSubNum[i].setTextColor(Color.parseColor("black"));
                            }
                        }

                        if (check == 0) { //recordManager[position] 시작이 됬나 안됬나 확인.
                            recordManager[position].start();
                            check++;
                            Log.d("Test", "***01");
                        }

                        if (cnt < subs1.length) {
                            if (subs1[cnt].time() >= mp.getCurrentPosition()) {
                                textView.setText(subs1[cnt].sub());

                                Log.d("Test", "##01@@@@" + cnt);
                                //textView.setTextColor(Color.parseColor("blue"));
                            } else
                                cnt++;
                        }
                    }
                });
            }
        }

        if (position == 2) { // 처음 클릭시 생성해주는 생성자
            recordManager[position] = new RecordManager(this, subs2[subs2.length - 1].time());
            Log.d("Test", "##PlayTest01");
            mp = MediaPlayer.create(PlayActivity.this, R.raw.music2);

            btnStop.setEnabled(false);
            btnStop.setImageResource(R.drawable.ic_stop_disable);
            btnPlay.setEnabled(false);
            btnPlay.setImageResource(R.drawable.ic_play_disable);


            for (int i = 0; i < subs2.length; i++) {

                textViewSubNum[i].setText(subs2[i].sub);
                final int finalI = i;
                textViewSubNum[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setVisibility(View.VISIBLE);
                        cnt = 0;
                        mp.start();
                        Thread();
                        mp.seekTo(subs2[finalI].start);
                        recordManager[position].recordFlag = true;
                        recordManager[position].endFlag = true;

                        btnRecord.setEnabled(false);
                        btnRecord.setImageResource(R.drawable.ic_rec_on);
                        btnStop.setEnabled(true);
                        btnStop.setImageResource(R.drawable.ic_stop_off);
                        btnPlay.setEnabled(false);
                        btnPlay.setImageResource(R.drawable.ic_play_disable);


//                        stateText.setText("Start");
                        cnt = finalI;

                        listenCheck = false;
                        endCheck = false;

                        for (int i = 0; i < subs2.length; i++) { // 선택시 색깔 겹침 금지
                            if (textViewSubNum[i] != textViewSubNum[cnt]) {
                                textViewSubNum[i].setTextColor(Color.parseColor("black"));
                            }
                        }

                        if (check == 0) { //recordManager[position] 시작이 됬나 안됬나 확인.
                            recordManager[position].start();
                            check++;
                            Log.d("Test", "***01");
                        }
                        //seekBar.setProgress(mp.getCurrentPosition());

                        if (cnt < subs2.length) {
                            if (subs2[cnt].time() >= mp.getCurrentPosition()) {
                                textView.setText(subs2[cnt].sub());

                                Log.d("Test", "##01@@@@" + cnt);
                                //textView.setTextColor(Color.parseColor("blue"));
                            } else
                                cnt++;
                        }
                    }
                });
            }
        }

        if (position == 3) { // 처음 클릭시 생성해주는 생성자
            recordManager[position] = new RecordManager(this, subs3[subs3.length - 1].time());
            Log.d("Test", "##PlayTest01");
            mp = MediaPlayer.create(PlayActivity.this, R.raw.music3);

            btnStop.setEnabled(false);
            btnStop.setImageResource(R.drawable.ic_stop_disable);
            btnPlay.setEnabled(false);
            btnPlay.setImageResource(R.drawable.ic_play_disable);


            for (int i = 0; i < subs3.length; i++) {

                textViewSubNum[i].setText(subs3[i].sub);
                final int finalI = i;
                textViewSubNum[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setVisibility(View.VISIBLE);
                        cnt = 0;
                        mp.start();
                        Thread();
                        mp.seekTo(subs3[finalI].start);
                        recordManager[position].recordFlag = true;
                        recordManager[position].endFlag = true;

                        btnRecord.setEnabled(false);
                        btnRecord.setImageResource(R.drawable.ic_rec_on);
                        btnStop.setEnabled(true);
                        btnStop.setImageResource(R.drawable.ic_stop_off);
                        btnPlay.setEnabled(false);
                        btnPlay.setImageResource(R.drawable.ic_play_disable);


//                        stateText.setText("Start");
                        cnt = finalI;

                        listenCheck = false;
                        endCheck = false;

                        for (int i = 0; i < subs3.length; i++) { // 선택시 색깔 겹침 금지
                            if (textViewSubNum[i] != textViewSubNum[cnt]) {
                                textViewSubNum[i].setTextColor(Color.parseColor("black"));
                            }
                        }

                        if (check == 0) { //recordManager[position] 시작이 됬나 안됬나 확인.
                            recordManager[position].start();
                            check++;
                            Log.d("Test", "***01");
                        }

                        if (cnt < subs3.length) {
                            if (subs3[cnt].time() >= mp.getCurrentPosition()) {
                                textView.setText(subs3[cnt].sub());

                                Log.d("Test", "##01@@@@" + cnt);
                                //textView.setTextColor(Color.parseColor("blue"));
                            } else
                                cnt++;
                        }
                    }
                });
            }
        }

        if (position == 4) { // 처음 클릭시 생성해주는 생성자
            recordManager[position] = new RecordManager(this, subs4[subs4.length - 1].time());
            Log.d("Test", "##PlayTest01");
            mp = MediaPlayer.create(PlayActivity.this, R.raw.music4);

            btnStop.setEnabled(false);
            btnStop.setImageResource(R.drawable.ic_stop_disable);
            btnPlay.setEnabled(false);
            btnPlay.setImageResource(R.drawable.ic_play_disable);


            for (int i = 0; i < subs4.length; i++) {

                textViewSubNum[i].setText(subs4[i].sub);
                final int finalI = i;
                textViewSubNum[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setVisibility(View.VISIBLE);
                        cnt = 0;
                        mp.start();
                        Thread();
                        mp.seekTo(subs4[finalI].start);
                        recordManager[position].recordFlag = true;
                        recordManager[position].endFlag = true;

                        btnRecord.setEnabled(false);
                        btnRecord.setImageResource(R.drawable.ic_rec_on);
                        btnStop.setEnabled(true);
                        btnStop.setImageResource(R.drawable.ic_stop_off);
                        btnPlay.setEnabled(false);
                        btnPlay.setImageResource(R.drawable.ic_play_disable);

//                        stateText.setText("Start");
                        cnt = finalI;

                        listenCheck = false;
                        endCheck = false;

                        for (int i = 0; i < subs4.length; i++) { // 선택시 색깔 겹침 금지
                            if (textViewSubNum[i] != textViewSubNum[cnt]) {
                                textViewSubNum[i].setTextColor(Color.parseColor("black"));
                            }
                        }

                        if (check == 0) { //recordManager[position] 시작이 됬나 안됬나 확인.
                            recordManager[position].start();
                            check++;
                            Log.d("Test", "***01");
                        }

                        if (cnt < subs4.length) {
                            if (subs4[cnt].time() >= mp.getCurrentPosition()) {
                                textView.setText(subs4[cnt].sub());

                                Log.d("Test", "##01@@@@" + cnt);
                                //textView.setTextColor(Color.parseColor("blue"));
                            } else
                                cnt++;
                        }
                    }
                });
            }
        }


        text = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);

        int m = (mp.getDuration()) / 60000;
        int s = (mp.getDuration()) / 1000;

        String duTime = String.format("%02d:%02d", m, s);
        text2.setText(duTime);
        text2.setTextColor(Color.parseColor("black"));
        textView = (TextView) findViewById(R.id.textView);
        //stateText = (TextView) findViewById(R.id.stateText);
        volumeText = (TextView) findViewById(R.id.volumeText);

        volumebar = (SeekBar) findViewById(R.id.volumebar);
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumebar.setMax(maxVol);
        volumebar.setProgress(currentVol);
        volumeText.setText(String.valueOf(currentVol));

        volumebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                if (fromUser) {
                    volumeText.setText(String.valueOf(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        seekBar = (SeekBar) findViewById(R.id.playbar);
        seekBar.setVisibility(ProgressBar.VISIBLE);
        seekBar.setMax(mp.getDuration()); //mp.getDuration 총 길이를 가져옴


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progr = progress;
                int m = progress / 60000;
                int s = (progress) / 1000;
//여기 쓰레드로 고칠수 없을까?
                if (mp.getDuration() - 200 < mp.getCurrentPosition()) { // 읽는시간이 마지막시간이랑 같으면 초기화 해버리기.
                    Log.d("Test", "()@@");
                    mp.seekTo(0);
                    mp.pause();
                    btnRecord.setEnabled(true);
                    btnRecord.setImageResource(R.drawable.ic_rec_off);
                    btnPlay.setEnabled(true);
                    btnPlay.setImageResource(R.drawable.ic_play_off);
                    textView.setVisibility(View.INVISIBLE);
                    text.setTextColor(Color.parseColor("black"));
                }

                String strTime = String.format("%02d:%02d", m, s);
                text.setText(strTime);

                if (position == 0) { // 자막 지속적으로 표시해 주는곳 mp 쓰레드 만큼
                    if (cnt < subs0.length) { // cnt 버튼 클릭시 초기화되게 할것

                        if ((subs0[cnt].start <= mp.getCurrentPosition()) && (subs0[cnt].time() >= mp.getCurrentPosition())) {
                            //if (subs0[cnt].time() >= mp.getCurrentPosition()) { // sub0[cnt].start
                            Log.d("Test", "@@" + cnt + "gg##" + subs0[cnt].sub());
                            textView.setText(subs0[cnt].sub());
                            textViewSubNum[cnt].setTextColor(Color.parseColor(ColorYellow));
                            textViewSubNum[cnt].setFocusableInTouchMode(true);
                            textViewSubNum[cnt].requestFocus();

                            if (focusFlag == false) { // 포커스 한번만 가져가게 하기
                                textViewSubNum[cnt].setTextColor(Color.parseColor(ColorYellow));
                                textViewSubNum[cnt].setFocusableInTouchMode(true);
                                textViewSubNum[cnt].requestFocus();
                                focusFlag = true;
                                Log.d("TEST", "OO_1");
                            }
                            textViewSubNum[cnt].setFocusableInTouchMode(false); // 포커스 한번만 가져가게 하기

                            if (stopFlag == true) {
                                textView.setText(subs0[0].sub());

                            }
                        } else { // 텍스트 색칠 부분
                            cnt++;
                            focusFlag = false;  // 포커스 한번만 가져가게 하기
                            for (int i = 0; i < subs0.length; i++) { // 재생중 색깔  endFlag에서 처리
                                if (textViewSubNum[i] != textViewSubNum[cnt]) {
                                    textViewSubNum[i].setTextColor(Color.parseColor("black"));
                                }
                            }
                        }
                    }

                    stopFlag = false;
                }


                if (position == 1) { // 자막 지속적으로 표시해 주는곳 mp 쓰레드 만큼
                    if (cnt < subs1.length) { // cnt 버튼 클릭시 초기화되게 할것
                        if (subs1[cnt].time() >= mp.getCurrentPosition()) {
                            Log.d("Test", "@@" + cnt + "gg##" + subs1[cnt].sub());
                            textView.setText(subs1[cnt].sub());
                            textViewSubNum[cnt].setTextColor(Color.parseColor(ColorYellow));
                            textViewSubNum[cnt].setFocusableInTouchMode(true);
                            textViewSubNum[cnt].requestFocus();

                            if (focusFlag == false) { // 포커스 한번만 가져가게 하기
                                textViewSubNum[cnt].setTextColor(Color.parseColor(ColorYellow));
                                textViewSubNum[cnt].setFocusableInTouchMode(true);
                                textViewSubNum[cnt].requestFocus();
                                focusFlag = true;
                                Log.d("TEST", "OO_1");
                            }
                            textViewSubNum[cnt].setFocusableInTouchMode(false); // 포커스 한번만 가져가게 하기

                            if (stopFlag == true) {
                                textView.setText(subs1[0].sub());

                            }
                        } else { // 텍스트 색칠 부분
                            cnt++;
                            focusFlag = false;  // 포커스 한번만 가져가게 하기
                            for (int i = 0; i < subs1.length; i++) { // 재생중 색깔  endFlag에서 처리
                                if (textViewSubNum[i] != textViewSubNum[cnt]) {
                                    textViewSubNum[i].setTextColor(Color.parseColor("black"));
                                }
                            }
                        }
                    }

                    stopFlag = false;
                }

                if (position == 2) { // 자막 지속적으로 표시해 주는곳 mp 쓰레드 만큼
                    if (cnt < subs2.length) { // cnt 버튼 클릭시 초기화되게 할것
                        if (subs2[cnt].time() >= mp.getCurrentPosition()) {
                            Log.d("Test", "@@" + cnt + "gg##" + subs2[cnt].sub());
                            textView.setText(subs2[cnt].sub());
                            textViewSubNum[cnt].setTextColor(Color.parseColor(ColorYellow));
                            textViewSubNum[cnt].setFocusableInTouchMode(true);
                            textViewSubNum[cnt].requestFocus();

                            if (focusFlag == false) { // 포커스 한번만 가져가게 하기
                                textViewSubNum[cnt].setTextColor(Color.parseColor(ColorYellow));
                                textViewSubNum[cnt].setFocusableInTouchMode(true);
                                textViewSubNum[cnt].requestFocus();
                                focusFlag = true;
                                Log.d("TEST", "OO_1");
                            }
                            textViewSubNum[cnt].setFocusableInTouchMode(false); // 포커스 한번만 가져가게 하기

                            if (stopFlag == true) {
                                textView.setText(subs2[0].sub());

                            }
                        } else { // 텍스트 색칠 부분
                            cnt++;
                            focusFlag = false;  // 포커스 한번만 가져가게 하기
                            for (int i = 0; i < subs2.length; i++) { // 재생중 색깔  endFlag에서 처리
                                if (textViewSubNum[i] != textViewSubNum[cnt]) {
                                    textViewSubNum[i].setTextColor(Color.parseColor("black"));
                                }
                            }
                        }
                    }

                    stopFlag = false;
                }

                if (position == 3) { // 자막 지속적으로 표시해 주는곳 mp 쓰레드 만큼
                    if (cnt < subs3.length) { // cnt 버튼 클릭시 초기화되게 할것
                        if (subs3[cnt].time() >= mp.getCurrentPosition()) {
                            Log.d("Test", "@@" + cnt + "gg##" + subs3[cnt].sub());
                            textView.setText(subs3[cnt].sub());
                            textViewSubNum[cnt].setTextColor(Color.parseColor(ColorYellow));
                            textViewSubNum[cnt].setFocusableInTouchMode(true);
                            textViewSubNum[cnt].requestFocus();

                            if (focusFlag == false) { // 포커스 한번만 가져가게 하기
                                textViewSubNum[cnt].setTextColor(Color.parseColor(ColorYellow));
                                textViewSubNum[cnt].setFocusableInTouchMode(true);
                                textViewSubNum[cnt].requestFocus();
                                focusFlag = true;
                                Log.d("TEST", "OO_1");
                            }
                            textViewSubNum[cnt].setFocusableInTouchMode(false); // 포커스 한번만 가져가게 하기

                            if (stopFlag == true) {
                                textView.setText(subs3[0].sub());

                            }
                        } else { // 텍스트 색칠 부분
                            cnt++;
                            focusFlag = false;  // 포커스 한번만 가져가게 하기
                            for (int i = 0; i < subs3.length; i++) { // 재생중 색깔  endFlag에서 처리
                                if (textViewSubNum[i] != textViewSubNum[cnt]) {
                                    textViewSubNum[i].setTextColor(Color.parseColor("black"));
                                }
                            }
                        }
                    }

                    stopFlag = false;
                }

                if (position == 4) { // 자막 지속적으로 표시해 주는곳 mp 쓰레드 만큼
                    if (cnt < subs4.length) { // cnt 버튼 클릭시 초기화되게 할것
                        if (subs4[cnt].time() >= mp.getCurrentPosition()) {
                            Log.d("Test", "@@" + cnt + "gg##" + subs4[cnt].sub());
                            textView.setText(subs4[cnt].sub());
                            textViewSubNum[cnt].setTextColor(Color.parseColor(ColorYellow));
                            textViewSubNum[cnt].setFocusableInTouchMode(true);
                            textViewSubNum[cnt].requestFocus();

                            if (focusFlag == false) { // 포커스 한번만 가져가게 하기
                                textViewSubNum[cnt].setTextColor(Color.parseColor(ColorYellow));
                                textViewSubNum[cnt].setFocusableInTouchMode(true);
                                textViewSubNum[cnt].requestFocus();
                                focusFlag = true;
                                Log.d("TEST", "OO_1");
                            }
                            textViewSubNum[cnt].setFocusableInTouchMode(false); // 포커스 한번만 가져가게 하기

                            if (stopFlag == true) {
                                textView.setText(subs4[0].sub());

                            }
                        } else { // 텍스트 색칠 부분
                            cnt++;
                            focusFlag = false;  // 포커스 한번만 가져가게 하기
                            for (int i = 0; i < subs4.length; i++) { // 재생중 색깔  endFlag에서 처리
                                if (textViewSubNum[i] != textViewSubNum[cnt]) {
                                    textViewSubNum[i].setTextColor(Color.parseColor("black"));
                                }
                            }
                        }
                    }

                    stopFlag = false;
                }

                if (endCheck) {

                    if (position == 0) {
                        for (int i = 0; i < subs0.length; i++) { // 스탑시 검개 칠해지는 부분
                            textViewSubNum[i].setTextColor(Color.parseColor("black"));
                        }

                        btnRecord.setEnabled(true);
                        btnRecord.setImageResource(R.drawable.ic_rec_off);
                        btnPlay.setEnabled(true);
                        btnPlay.setImageResource(R.drawable.ic_play_off);
                        //btnPause.setEnabled(true);
                        btnStop.setEnabled(true);
                        btnStop.setImageResource(R.drawable.ic_stop_off);

                        //RecordManager.recordFlag2 = false;
                        cnt = 0;// 자막 초기화때 필요
                        recordManager[position].recordFlag = false;
                        recordManager[position].playFlag = false;

//                        stateText.setText("stop");

                        textViewSubNum[0].setTextColor(Color.parseColor("black"));
                        if (position == 1) {
                            for (int i = 0; i < subs0.length; i++) {
                                textViewSubNum[i].setEnabled(true);
                            }
                        }

                        endCheck = false;

                        mp.seekTo(0);
                        mp.pause();
                        mp.start();
                        Thread();

                        mp.seekTo(0);
                        mp.pause();
                    }

                    if (position == 1) {
                        for (int i = 0; i < subs1.length; i++) { // 스탑시 검개 칠해지는 부분
                            textViewSubNum[i].setTextColor(Color.parseColor("black"));
                        }

                        btnRecord.setEnabled(true);
                        btnRecord.setImageResource(R.drawable.ic_rec_off);
                        btnPlay.setEnabled(true);
                        btnPlay.setImageResource(R.drawable.ic_play_off);
                        //btnPause.setEnabled(true);
                        btnStop.setEnabled(true);
                        btnStop.setImageResource(R.drawable.ic_stop_off);

                        //RecordManager.recordFlag2 = false;
                        cnt = 0;// 자막 초기화때 필요
                        recordManager[position].recordFlag = false;
                        recordManager[position].playFlag = false;

//                        stateText.setText("stop");

                        textViewSubNum[0].setTextColor(Color.parseColor("black"));
                        if (position == 1) {
                            for (int i = 0; i < subs1.length; i++) {
                                textViewSubNum[i].setEnabled(true);
                            }
                        }

                        endCheck = false;

                        mp.seekTo(0);
                        mp.pause();
                        mp.start();
                        Thread();

                        mp.seekTo(0);
                        mp.pause();
                    }

                    if (position == 2) {
                        for (int i = 0; i < subs2.length; i++) { // 스탑시 검개 칠해지는 부분
                            textViewSubNum[i].setTextColor(Color.parseColor("black"));
                        }

                        btnRecord.setEnabled(true);
                        btnRecord.setImageResource(R.drawable.ic_rec_off);
                        btnPlay.setEnabled(true);
                        btnPlay.setImageResource(R.drawable.ic_play_off);
                        //btnPause.setEnabled(true);
                        btnStop.setEnabled(true);
                        btnStop.setImageResource(R.drawable.ic_stop_off);

                        //RecordManager.recordFlag2 = false;
                        cnt = 0;// 자막 초기화때 필요
                        recordManager[position].recordFlag = false;
                        recordManager[position].playFlag = false;

//                        stateText.setText("stop");

                        textViewSubNum[0].setTextColor(Color.parseColor("black"));
                        if (position == 1) {
                            for (int i = 0; i < subs2.length; i++) {
                                textViewSubNum[i].setEnabled(true);
                            }
                        }

                        endCheck = false;

                        mp.seekTo(0);
                        mp.pause();
                        mp.start();
                        Thread();

                        mp.seekTo(0);
                        mp.pause();
                    }

                    if (position == 3) {
                        for (int i = 0; i < subs3.length; i++) { // 스탑시 검개 칠해지는 부분
                            textViewSubNum[i].setTextColor(Color.parseColor("black"));
                        }

                        btnRecord.setEnabled(true);
                        btnRecord.setImageResource(R.drawable.ic_rec_off);
                        btnPlay.setEnabled(true);
                        btnPlay.setImageResource(R.drawable.ic_play_off);
                        //btnPause.setEnabled(true);
                        btnStop.setEnabled(true);
                        btnStop.setImageResource(R.drawable.ic_stop_off);

                        //RecordManager.recordFlag2 = false;
                        cnt = 0;// 자막 초기화때 필요
                        recordManager[position].recordFlag = false;
                        recordManager[position].playFlag = false;

//                        stateText.setText("stop");

                        textViewSubNum[0].setTextColor(Color.parseColor("black"));
                        if (position == 1) {
                            for (int i = 0; i < subs3.length; i++) {
                                textViewSubNum[i].setEnabled(true);
                            }
                        }

                        endCheck = false;

                        mp.seekTo(0);
                        mp.pause();
                        mp.start();
                        Thread();

                        mp.seekTo(0);
                        mp.pause();
                    }


                    if (position == 4) {
                        for (int i = 0; i < subs4.length; i++) { // 스탑시 검개 칠해지는 부분
                            textViewSubNum[i].setTextColor(Color.parseColor("black"));
                        }

                        btnRecord.setEnabled(true);
                        btnRecord.setImageResource(R.drawable.ic_rec_off);
                        btnPlay.setEnabled(true);
                        btnPlay.setImageResource(R.drawable.ic_play_off);
                        //btnPause.setEnabled(true);
                        btnStop.setEnabled(true);
                        btnStop.setImageResource(R.drawable.ic_stop_off);

                        //RecordManager.recordFlag2 = false;
                        cnt = 0;// 자막 초기화때 필요
                        recordManager[position].recordFlag = false;
                        recordManager[position].playFlag = false;

//                        stateText.setText("stop");

                        textViewSubNum[0].setTextColor(Color.parseColor("black"));
                        if (position == 1) {
                            for (int i = 0; i < subs4.length; i++) {
                                textViewSubNum[i].setEnabled(true);
                            }
                        }

                        endCheck = false;

                        mp.seekTo(0);
                        mp.pause();
                        mp.start();
                        Thread();

                        mp.seekTo(0);
                        mp.pause();
                    }
                    Log.d("Test", "()");
                }

                //*********//
                if (fromUser) {
                    mp.seekTo(progress); //seekTo는 재생 위치를 바뀌주는 것
                    cnt = 0;


                    if (position == 0) {
                        if (cnt < subs0.length) {
                            if (subs0[cnt].time() >= mp.getCurrentPosition()) {
                                textView.setText(subs0[cnt].sub());
                            } else
                                cnt++;
                        }
                    }

                    if (position == 1) { // 유저가 손으로 시간 움직일때 자막 표시
                        if (cnt < subs1.length) {
                            if (subs1[cnt].time() >= mp.getCurrentPosition()) {
                                textView.setText(subs1[cnt].sub());
                            } else
                                cnt++;
                        }
                    }

                    if (position == 2) {
                        if (cnt < subs2.length) {
                            if (subs2[cnt].time() >= mp.getCurrentPosition()) {
                                textView.setText(subs2[cnt].sub());
                            } else
                                cnt++;
                        }
                    }

                    if (position == 3) {
                        if (cnt < subs3.length) {
                            if (subs3[cnt].time() >= mp.getCurrentPosition()) {
                                textView.setText(subs3[cnt].sub());
                            } else
                                cnt++;
                        }
                    }

                    if (position == 4) {
                        if (cnt < subs4.length) {
                            if (subs4[cnt].time() >= mp.getCurrentPosition()) {
                                textView.setText(subs4[cnt].sub());
                            } else
                                cnt++;
                        }
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listenCheck = false;
                endCheck = false;
                mp.start();
                Thread();
                mp.seekTo(progr);
                count = 0;
                recordFlag = true;

                textView.setVisibility(View.VISIBLE);

                text2.setTextColor(Color.parseColor("black"));

                btnStop.setEnabled(true);
                btnStop.setImageResource(R.drawable.ic_stop_off);
                btnRecord.setEnabled(false);
                btnRecord.setImageResource(R.drawable.ic_rec_on);
                btnPlay.setEnabled(false);
                btnPlay.setImageResource(R.drawable.ic_play_disable);
                //btnPause.setEnabled(true);

//                stateText.setText("start");

                if (check == 0) { //recordManager[position] 시작이 됬나 안됬나 확인.
                    recordManager[position].start();
                    check++;
                }
                recordManager[position].recordFlag = true;
                recordManager[position].endFlag = true;
                Log.d("Test", "btnRecord**");
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                stateText.setText("stop");

                stopFlag = true;
                text.setTextColor(Color.parseColor("black"));
                //포지션 1일때 스탑 누르면 다 삭제
                if (recordFlag == true) {

                    btnRecord.setEnabled(true);
                    btnRecord.setImageResource(R.drawable.ic_rec_off);
                    btnPlay.setEnabled(true);
                    btnPlay.setImageResource(R.drawable.ic_play_off);
                    recordFlag = false;
                } else if (!recordFlag) {
                    btnPlay.setEnabled(false);
                    btnPlay.setImageResource(R.drawable.ic_play_disable);
                    btnRecord.setEnabled(true);
                    btnRecord.setImageResource(R.drawable.ic_rec_off);
                }

                if (position == 0) {
                    for (int i = 0; i < subs0.length; i++) {
                        textViewSubNum[i].setEnabled(true);
                        textViewSubNum[i].setTextColor(Color.parseColor("black"));
                        textView.setVisibility(View.INVISIBLE);
                    }
                }

                if (position == 1) {
                    for (int i = 0; i < subs1.length; i++) {
                        textViewSubNum[i].setEnabled(true);
                        textViewSubNum[i].setTextColor(Color.parseColor("black"));
                        textView.setVisibility(View.INVISIBLE);
                    }
                }

                if (position == 2) {
                    for (int i = 0; i < subs2.length; i++) {
                        textViewSubNum[i].setEnabled(true);
                        textViewSubNum[i].setTextColor(Color.parseColor("black"));
                        textView.setVisibility(View.INVISIBLE);
                    }
                }

                if (position == 3) {
                    for (int i = 0; i < subs3.length; i++) {
                        textViewSubNum[i].setEnabled(true);
                        textViewSubNum[i].setTextColor(Color.parseColor("black"));
                        textView.setVisibility(View.INVISIBLE);
                    }
                }

                if (position == 4) {
                    for (int i = 0; i < subs4.length; i++) {
                        textViewSubNum[i].setEnabled(true);
                        textViewSubNum[i].setTextColor(Color.parseColor("black"));
                        textView.setVisibility(View.INVISIBLE);
                    }
                }


                if (!mp.isLooping()) {
                    Log.d("Test", "()@@");
                    mp.seekTo(0);
                    mp.pause();
                }
            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recordManager[position].playFlag = true;


                btnRecord.setEnabled(false);
                btnRecord.setImageResource(R.drawable.ic_rec_disable);
                btnPlay.setImageResource(R.drawable.ic_play_on);

                recordManager[position].runFlag = false;

                playFlag = true;


                if (position == 0) {
                    for (int i = 0; i < subs0.length; i++) {
                        textViewSubNum[i].setEnabled(false);
                        textViewSubNum[i].setTextColor(Color.parseColor("#f1f1f1"));
                        //0125 여기다가 투명화 시키는거 추가할것. 플레이시 잠금 시각적
                    }
                }
                cnt = 0;
//                if (position == 0) {
//                    if (cnt < subs0.length) {
//                        if (subs0[cnt].time() >= mp.getCurrentPosition()) {
//                            textView.setText(subs0[cnt].sub());
//                            Log.d("Test", "##01");
//                        } else
//                            cnt++;
//                    }
//                }

                if (position == 1) {
                    for (int i = 0; i < subs1.length; i++) {
                        textViewSubNum[i].setEnabled(false);
                        textViewSubNum[i].setTextColor(Color.parseColor("#f1f1f1"));
                        //0125 여기다가 투명화 시키는거 추가할것. 플레이시 잠금 시각적
                    }
                }
                cnt = 0;
                if (position == 1) {
                    if (cnt < subs1.length) {
                        if (subs1[cnt].time() >= mp.getCurrentPosition()) {
                            textView.setText(subs1[cnt].sub());
                            Log.d("Test", "##01");
                        } else
                            cnt++;
                    }
                }

                if (position == 2) {
                    for (int i = 0; i < subs2.length; i++) {
                        textViewSubNum[i].setEnabled(false);
                        textViewSubNum[i].setTextColor(Color.parseColor("#f1f1f1"));
                        //0125 여기다가 투명화 시키는거 추가할것. 플레이시 잠금 시각적
                    }
                }
                cnt = 0;
                if (position == 2) {
                    if (cnt < subs2.length) {
                        if (subs2[cnt].time() >= mp.getCurrentPosition()) {
                            textView.setText(subs2[cnt].sub());
                            Log.d("Test", "##01");
                        } else
                            cnt++;
                    }
                }

                if (position == 3) {
                    for (int i = 0; i < subs3.length; i++) {
                        textViewSubNum[i].setEnabled(false);
                        textViewSubNum[i].setTextColor(Color.parseColor("#f1f1f1"));
                        //0125 여기다가 투명화 시키는거 추가할것. 플레이시 잠금 시각적
                    }
                }
                cnt = 0;
                if (position == 3) {
                    if (cnt < subs3.length) {
                        if (subs3[cnt].time() >= mp.getCurrentPosition()) {
                            textView.setText(subs3[cnt].sub());
                            Log.d("Test", "##01");
                        } else
                            cnt++;
                    }
                }

                if (position == 4) {
                    for (int i = 0; i < subs4.length; i++) {
                        textViewSubNum[i].setEnabled(false);
                        textViewSubNum[i].setTextColor(Color.parseColor("#f1f1f1"));
                        //0125 여기다가 투명화 시키는거 추가할것. 플레이시 잠금 시각적
                    }
                }
                cnt = 0;
                if (position == 4) {
                    if (cnt < subs4.length) {
                        if (subs4[cnt].time() >= mp.getCurrentPosition()) {
                            textView.setText(subs4[cnt].sub());
                            Log.d("Test", "##01");
                        } else
                            cnt++;
                    }
                }
            }
        });

        btnVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btnVolFlag) {
                    volumebar.setVisibility(View.VISIBLE);
                    volumeText.setVisibility(View.VISIBLE);
                    volLinear.setVisibility(View.VISIBLE);
                    btnVolFlag = true;
                    btnVol.setImageResource(R.drawable.ic_volume_on);
                } else if (btnVolFlag) {
                    volumebar.setVisibility(View.GONE);
                    volumeText.setVisibility(View.GONE);
                    volLinear.setVisibility(View.INVISIBLE);
                    btnVolFlag = false;
                    btnVol.setImageResource(R.drawable.ic_volume_off);
                }
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //길이별로 해줘야함
                //생각해보기
//                String str = "subs"+position;
//                int subs= intpa

                if (!btnSubFlag) {
                    for (int i = 0; i < subs0.length; i++) {
                        textViewSubNum[i].setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                        btnSubFlag = true;
                        btnSub.setImageResource(R.drawable.ic_lyrics_off);
                        //textViewSubNum[i].setTextColor(Color.parseColor("red"));
                        //0125 여기다가 투명화 시키는거 추가할것. 플레이시 잠금 시각적
                    }
                } else if (btnSubFlag) {
                    for (int i = 0; i < subs0.length; i++) {
                        textViewSubNum[i].setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        btnSubFlag = false;
                        btnSub.setImageResource(R.drawable.ic_lyrics_on);
                    }
                }


            }

        });

        btnplayhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //마시멜로우 이상 버전 권한 체크
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    public void btnPlayend() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // 해당 작업을 처리함

                        if (playFlag) {
                            Log.d("Test", "!!!AAABBBCCCDDD");
                            btnPlay.setImageResource(R.drawable.ic_play_off);
                            playFlag = false;
                        }
                    }
                });
            }
        }).start();



    }


//    @Override
//    public void onResume() { // 이어폰 착용 확인
//        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
//        registerReceiver(myReceiver, filter);
//        super.onResume();
//    }

    @Override // 종료메소드
    protected void onStop() {
        if (!isBackPressed) {
            stopFlag = true;
            isBackPressed = true;
            RecordManager.endFlag = false;
            mp.stop();
            onStop();
            finish();
        } else
            super.onStop();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


//    public void thread() {
//        Runnable task = new Runnable() {
//            public void run() {
//                // 음악이 재생중일때
//                while (!startFlag) {
//                    try {
//                        Thread.sleep(500);
//                        if (recordManager[position].playFlag == false) {
//                            btnPlay.setImageResource(R.drawable.ic_play_off);
//                        }
//                    } catch (InterruptedException e) {
//
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        };
//        Thread thread = new Thread(task);
//        thread.start();
//    }


    public void Thread() {
        Runnable task = new Runnable() {
            public void run() {
                // 음악이 재생중일때
                while (mp.isPlaying()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    seekBar.setProgress(mp.getCurrentPosition());
                }
                endCheck = true;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}