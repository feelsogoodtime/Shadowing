package com.project.shadowing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

public class EarPhoneIntentReceiver extends BroadcastReceiver {


    public void onReceive(Context ctx, Intent intent) {



        String str = " ";

        AudioManager audioManager = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Log.d("111", "Headset is unplugged");
                    Toast.makeText(ctx, "이어폰이 착용하면 더 좋습니다 !.", Toast.LENGTH_LONG).show();

                    break;
                case 1:
                    Log.d("111", "Headset is plugged");
                    Toast.makeText(ctx, "이어폰이 정상적으로 인식 되었습니다.", Toast.LENGTH_LONG).show();

                    break;
                default:
                    Log.d("111", "I have no idea what the headset state is");
            }
        }
    }
}
