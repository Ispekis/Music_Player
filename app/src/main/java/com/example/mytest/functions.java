package com.example.mytest;

import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class functions {

    public static String time_with_seconds(int seconds) {
        int minutes = 0;
        if (seconds >= 60) {
            while (seconds >= 60) {
                minutes += 1;
                seconds -= 60;
            }
        }
        String result = String.valueOf(minutes) + ":" + String.format("%02d", seconds);
        return String.valueOf(result);
    }
    public static void timer_clock(TextView ct, MediaPlayer mp, SeekBar sb) {
        final Handler handler = new Handler();
        Timer tick = new Timer(false);
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ct.setText(time_with_seconds(mp.getCurrentPosition() / 1000));
                        sb.setProgress(mp.getCurrentPosition() / 1000);
                    }
                });
            }
        };
        tick.scheduleAtFixedRate(timertask, 500, 500);
    }
    public static void seekbar_event(SeekBar sb, MediaPlayer mp, TextView ct) {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b == true) {
                    mp.seekTo(i * 1000);
                    ct.setText(time_with_seconds(mp.getCurrentPosition() / 1000));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
