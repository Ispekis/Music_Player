package com.example.mytest;

import android.content.res.AssetFileDescriptor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final boolean[] playing = {false};
        int [] musics = new int[] {R.raw.music, R.raw.music2};
        final MediaPlayer mp = MediaPlayer.create(this, musics[0]);
        ImageView play = (ImageView) this.findViewById(R.id.play_sound);
        TextView ct = (TextView) this.findViewById(R.id.ct);
        SeekBar sb = (SeekBar) this.findViewById(R.id.PlayerSeekBar);
        ImageView next = (ImageView) this.findViewById(R.id.next);
        ImageView previous = (ImageView) this.findViewById(R.id.previous);

        /*ListView playlist = (ListView) this.findViewById(R.id.playlist);
        ArrayList<String> arraylist = new ArrayList<String>();
        Field[] fields = R.raw.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            arraylist.add(fields[i].getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arraylist);
        playlist.setAdapter(adapter);
        playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mp != null) {
                    mp.release();
                }
                int resId = getResources().getIdentifier(arraylist.get(i), "raw", getPackageName());
                mp = MediaPlayer.create(MainActivity.this, resId);
                mp.start();
            }
        });*/

        functions.timer_clock(ct, mp, sb);
        functions.seekbar_event(sb, mp, ct);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < 1)
                    play_next();
            }
            private void play_next() {
                index++;
                AssetFileDescriptor afd = getResources().openRawResourceFd(musics[index]);
                try {
                    mp.reset();
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                    mp.prepare();
                    mp.start();
                    afd.close();
                    sb.setMax(mp.getDuration() / 1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0)
                    play_previous();
            }
            private void play_previous() {
                index--;
                AssetFileDescriptor afd = getResources().openRawResourceFd(musics[index]);
                try {
                    mp.reset();
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                    mp.prepare();
                    mp.start();
                    afd.close();
                    sb.setMax(mp.getDuration() / 1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playing[0]) {
                    sb.setMax(mp.getDuration() / 1000);
                    play.setImageResource(R.drawable.pause);
                    mp.start();
                    playing[0] = true;
                } else {
                    play.setImageResource(R.drawable.play);
                    mp.pause();
                    playing[0] = false;
                }
            }
        });
    }
}