package com.example.project_test_1;
import android.support.v7.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_test_1.R;

import java.io.File;
public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer=new MediaPlayer();//MediaPlayer对象
    private boolean isPause=false;//是否暂停
    private File file;//要播放的文件
    private TextView hint;//声明提示信息的文本框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取各功能按钮
        final Button button=(Button)findViewById(R.id.button);//播放
        final Button button1=(Button)findViewById(R.id.button2);//暂停
        final Button button2=(Button)findViewById(R.id.button3);//停止
        hint=(TextView)findViewById(R.id.textView);

        //对MediaPlayer对象添加事件监听，当播放完成时重新开始音乐播放
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play();
            }
        });
        //对播放按钮进行事件监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                if(isPause){
                    button1.setText("pause");
                    isPause=false;
                }
                button1.setEnabled(true);
                button2.setEnabled(true);
                button.setEnabled(true);
            }
        });
        //对暂停、继续按钮添加事件监听器
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()&&!isPause){
                    mediaPlayer.pause();
                    isPause=true;
                    ((Button)v).setText("continue");
                    hint.setText("pause....");
                    button.setEnabled(true);
                }else{
                    mediaPlayer.start();
                    ((Button)v).setText("pause");
                    hint.setText("continue....");
                    button.setEnabled(false);
                }
            }
        });
        //对停止按钮添加事件监听器
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                hint.setText("stop...");
                button1.setEnabled(false);
                button2.setEnabled(false);
                button.setEnabled(true);
            }
        });
    }
    @Override
    protected void onDestroy() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
    }

    //播放音乐的方法
    private void play(){
        try{
            mediaPlayer.reset();//从新设置要播放的音乐
            //           mediaPlayer.setDataSource(file.getAbsolutePath());
//            mediaPlayer.prepare();//预加载音频
            mediaPlayer=MediaPlayer.create(this,R.raw.my);
            mediaPlayer.start();//播放音乐
            hint.setText("Music is starting");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("err",e.getMessage());
        }
        return ;
    }
}