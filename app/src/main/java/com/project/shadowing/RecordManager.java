package com.project.shadowing;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import java.nio.ShortBuffer;
import java.util.Arrays;

public class RecordManager extends Thread {

    PlayActivity playActivity = new PlayActivity();

    static boolean runFlag = false, endFlag, recordCheck = false, recordFlag = false, recordFlag2 = false, playFlag = false;
    int bufferRecordSize = 3200, bufferTrackSize = 3200; // 안드로이드 버퍼사이즈에서 손실없게 딱 맞게 떨어짐 젤 적은 사이즈가 2000이상 1600단위로 잘라서 3200으로 정함
    static short[] bufferStore, bufferTrack;
    static int samplingRate = 44100;
    static int seekTime;
    static int bufferShortSize; // 여기다가 시간 곱해주면됨 16000 sample 당 1초
    ShortBuffer shortBuffer;
    AudioRecord audioRecord;
    AudioTrack audioTrack;
    int count = 0;
    int stopBufferSize;


    public RecordManager(PlayActivity playActivity, int seekTime) {

        this.playActivity = playActivity;
        this.seekTime = (seekTime / 1000) + 1;
        bufferShortSize = samplingRate * this.seekTime;
        bufferStore = new short[bufferRecordSize];
        shortBuffer = ShortBuffer.allocate(bufferShortSize);
        Log.d("Test", "##PlayRecord");
        Log.d("Test", "run1");
        Log.d("Test", "run2");
    }

    public RecordManager(ThreadGroup group, Runnable target) {
        super(group, target);
    }

    public RecordManager(ThreadGroup group, String name) {
        super(group, name);
    }

    public void FlagCheck() {
        if (recordFlag == false) {
            stopBufferSize = shortBuffer.position();
            Log.d("Test", "+++" + stopBufferSize);
            audioRecord.stop();
            recordFlag = true;
        }
    }

    @Override
    public void run() {
        super.run();
        while (endFlag) {
            //while (endFlag) {

            if (recordFlag) {

                //나중에 seek 타임 어떻게 처리할지
                //bufferShortSize = samplingRate * seekTime;
                audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                        samplingRate,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        bufferRecordSize);

                stopBufferSize = bufferShortSize; // 다들으면 버퍼사이즈 전체사이즈로 이동
                audioRecord.startRecording();
                Arrays.fill(shortBuffer.array(), (short) 0);

                Log.d("Test", "record");
                shortBuffer.rewind();
                Log.d("Test", "record" + shortBuffer.position() + "//" + bufferRecordSize + "//" + bufferShortSize);
                while (shortBuffer.position() + bufferRecordSize <= bufferShortSize) {

                    if (count == 0) { //첫 값이 쓰레기값으로 읽힘 읽고 저장안함
                        audioRecord.read(bufferStore, 0, bufferRecordSize);
                        count++;
                    }
                    Log.d("Test", "record" + shortBuffer.position() + "//" + bufferRecordSize + "//" + bufferShortSize);
                    audioRecord.read(bufferStore, 0, bufferRecordSize);
                    shortBuffer.put(bufferStore, 0, bufferRecordSize); // 원래는 retBufferSize -> 2560 인데 짤림 방지로 3200으로 맞춰줌
                    FlagCheck();
                }
                audioRecord.stop();
                recordFlag = false;
            }

            if (playFlag) {

                // 해드폰 이였을때 다르게 해줘야함 . DTMF , STREAM_ALARM good

                shortBuffer.rewind();
                bufferTrack = new short[bufferRecordSize];
                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, // Ring 에러난듯 다음에 찾기
                        samplingRate,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        bufferTrackSize,
                        AudioTrack.MODE_STREAM);

                audioTrack.setVolume(1000);
                Log.d("Test", "!@#" + audioTrack.getMinVolume() + " !@#" + audioTrack.getMaxVolume() + "!@#" + audioTrack.setVolume(1.0f));
                Log.d("Test", "play" + shortBuffer.position() + "//" + bufferShortSize + "//" + bufferTrackSize);
                audioTrack.play();
                while (shortBuffer.position() <= stopBufferSize - bufferTrackSize) { // 버퍼포지션 0,1,2,3..... <= (전체 버퍼 사이즈 - 버퍼트랙 사이즈) -> 버퍼 끝까지만 읽어야됨
                    Log.d("Test", "play" + shortBuffer.position() + "//" + bufferShortSize + "//" + bufferTrackSize);
                    shortBuffer.get(bufferTrack, 0, bufferTrackSize);
                    audioTrack.write(bufferTrack, 0, bufferTrackSize);
                    if (playActivity.stopFlag == true) {//재생중 스탑하면 멈추게
//                        playActivity.btnPlayend();
                        Log.d("Test", "AAABBBCCC");

                        break;
                    }
                }
                playActivity.btnPlayend();
                runFlag = true;

                Log.d("Test", "AAABBBCCCDDD");
                //playActivity.btnPlayend();
                audioTrack.stop();
                playFlag = false;
                recordCheck = true;

            }
        }
    }
}
