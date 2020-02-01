package com.project.shadowing;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean isSelectedLearn;
    private boolean isSelectedMyPage;
    private boolean isSelectedSetting;

    private ImageView learnImage;
    private ImageView mypageImage;
    private ImageView settingImage;
    private ImageView btnhome;

    private TextView mainTopText;


//
//    private TextView learnText;
//    private TextView mypageText;
//    private TextView settingText;

    private Fragment1 fragment1 = new Fragment1();
    private Fragment2 fragment2 = new Fragment2();
    private Fragment3 fragment3 = new Fragment3();
    static PlayActivity playActivity = new PlayActivity();

    static final ArrayList<MovieInfo> movieInfoArrayList_fragment1 = new ArrayList<>();
    static SQLiteDatabase database;
    static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isSelectedLearn = false;
        isSelectedMyPage = false;
        isSelectedSetting = false;

        LinearLayout learn = (LinearLayout) findViewById(R.id.activity_main_linearlayout_learn);
        LinearLayout mypage = (LinearLayout) findViewById(R.id.activity_main_linearlayout_mypage);
        LinearLayout setting = (LinearLayout) findViewById(R.id.activity_main_linearlayout_setting);

        learnImage = (ImageView) findViewById(R.id.activity_main_imageview_learn);
        mypageImage = (ImageView) findViewById(R.id.activity_main_imageview_mypage);
        settingImage = (ImageView) findViewById(R.id.activity_main_imageview_setting);
        btnhome = (ImageView) findViewById(R.id.btnhome);

        mainTopText = (TextView) findViewById(R.id.mainTopText);
//
//        learnText = (TextView) findViewById(R.id.activity_main_textview_learn);
//        mypageText = (TextView) findViewById(R.id.activity_main_textview_mypage);
//        settingText = (TextView) findViewById(R.id.activity_main_textview_setting);

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
            }
        });

        setFragment(1); // 첫 프래그먼트

        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(1);
            }
        });

        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(2);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(3);

            }
        });

        dbHelper = new DBHelper(getApplicationContext(), "movieinfo.db", null, 1);

        // dbHelper.dropTable();
    }

    private void setFragment(int fragmentNumber) { //각각의 버튼별로 프래그먼트 변환

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentNumber) {
            case 1 :
                transaction.replace(R.id.activity_main_framelayout, fragment1);
                transaction.commit();
                iconSelected(fragmentNumber);
                mainTopText.setText("학습하기");
                break;

            case 2 :
                transaction.replace(R.id.activity_main_framelayout, fragment2);
                transaction.commit();
                iconSelected(fragmentNumber);
                mainTopText.setText("마이페이지");
                break;

            case 3 :
                transaction.replace(R.id.activity_main_framelayout, fragment3);
                transaction.commit();
                iconSelected(fragmentNumber);
                mainTopText.setText("설정");
                break;
        }
    }

    private void iconSelected (int iconNumber) {
        switch (iconNumber) {
            case 1 :
                if(isSelectedMyPage) {
                    inActivateMypage();
                } else if (isSelectedSetting) {
                    inActivateSetting();
                } else {
                    activateLearn(true);
                    break;
                }
                activateLearn(false);
                break;

            case 2 :
                if(isSelectedLearn) {
                    inActivateLearn();
                } else if (isSelectedSetting) {
                    inActivateSetting();
                } else {
                    activateMypage(true);
                    break;
                }
                activateMypage(false);
                break;

            case 3 :
                if (isSelectedLearn) {
                    inActivateLearn();
                } else if (isSelectedMyPage) {
                    inActivateMypage();
                } else {
                    activateSetting(true);
                    break;
                }
                activateSetting(false);
                break;
        }
    }

    private void activateLearn (boolean isSame) {
        learnImage.setImageResource(R.drawable.bottom01_on);
//        learnText.setTextColor(getResources().getColor(R.color.selectedColor));



        if(isSame) {
            isSelectedLearn = true;
        } else {
            isSelectedLearn = !isSelectedLearn;
        }
    }

    private void activateMypage (boolean isSame) {
        mypageImage.setImageResource(R.drawable.bottom02_on);
//        mypageText.setTextColor(getResources().getColor(R.color.selectedColor));
        if (isSame) {
            isSelectedMyPage = true;
        } else {
            isSelectedMyPage = !isSelectedMyPage;
        }
    }

    private void activateSetting (boolean isSame) {
        settingImage.setImageResource(R.drawable.bottom03_on);
//        settingText.setTextColor(getResources().getColor(R.color.selectedColor));
        if (isSame) {
            isSelectedSetting = true;
        } else {
            isSelectedSetting = !isSelectedSetting;
        }
    }

    private void inActivateLearn () {
        learnImage.setImageResource(R.drawable.bottom01_off);
//        learnText.setTextColor(getResources().getColor(R.color.notSelectedColor));
        isSelectedLearn = !isSelectedLearn;
    }

    private void inActivateMypage () {
        mypageImage.setImageResource(R.drawable.bottom02_off);
//        mypageText.setTextColor(getResources().getColor(R.color.notSelectedColor));
        isSelectedMyPage = !isSelectedMyPage;
    }

    private void inActivateSetting () {
        settingImage.setImageResource(R.drawable.bottom03_off);
//        settingText.setTextColor(getResources().getColor(R.color.notSelectedColor));
        isSelectedSetting = !isSelectedSetting;
    }

    //마시멜로우 이상 버전 권한 체크
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "RECORD 권한 승인", Toast.LENGTH_LONG).show();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "RECORD 권한 거부.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "RECORD 권한 승인 부여 받지 못함", Toast.LENGTH_LONG).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
