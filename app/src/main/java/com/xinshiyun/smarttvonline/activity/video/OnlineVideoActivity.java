package com.xinshiyun.smarttvonline.activity.video;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinshiyun.smarttvonline.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.Option;
import tcking.github.com.giraffeplayer2.PlayerManager;
import tcking.github.com.giraffeplayer2.VideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static java.lang.System.currentTimeMillis;

public class OnlineVideoActivity extends AppCompatActivity {

    @BindView(R.id.online_video_view)
    VideoView videoView;

    public static final int AR_ASPECT_FIT_PARENT = 0; // without clip
    public static final int AR_ASPECT_FILL_PARENT = 1; // may clip
    public static final int AR_ASPECT_WRAP_CONTENT = 2;
    public static final int AR_MATCH_PARENT = 3;
    public static final int AR_16_9_FIT_PARENT = 4;
    public static final int AR_4_3_FIT_PARENT = 5;
    private String testUrl;

    @BindView(R.id.tv_fast_show)
    TextView tvFastShow;
    @BindView(R.id.layout_fast_show)
    RelativeLayout layoutFastShow;

    @BindView(R.id.tv_img_pause)
    ImageView tvImagePause;

    private boolean isFirst = true;
    private int longRight = 0;
    private int currentPosition = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            layoutFastShow.setVisibility(View.VISIBLE);
            if (msg.arg1 == 1001) {
                tvFastShow.setText("快退" + ((int) msg.obj * 20 / 1000) + "s");

                videoView.getMediaController().setProgress(((videoView.getPlayer().getCurrentPosition() - (int) msg.obj * 20)));
//                alreadyPlayerTime.setText(TimeUtils.secToTime((int) (player.getCurrentPosition()-(long)msg.obj*20)/1000));
            } else {
                tvFastShow.setText("快进" + ((int) msg.obj * 20 / 1000) + "s");
                videoView.getMediaController().setProgress(((videoView.getPlayer().getCurrentPosition() + (int) msg.obj * 20)));
//                alreadyPlayerTime.setText(TimeUtils.secToTime((int) (player.getCurrentPosition()+(long)msg.obj*20 )/1000));
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_video);

        ButterKnife.bind(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        GiraffePlayer.debug = true;//show logs
        PlayerManager.getInstance().getDefaultVideoInfo().addOption(Option.create(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "multiple_requests", 1L));
        PlayerManager.getInstance().getDefaultVideoInfo().addOption(Option.create(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1L));
        PlayerManager.getInstance().getDefaultVideoInfo().addOption(Option.create(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec_mpeg4", 1L));
        initView();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
            } else {
                Toast.makeText(this, "please grant read permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
//        testUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//        testUrl = "file:///sdcard/tmp/o.mp4" //test local file;
//        testUrl = "https://tungsten.aaplimg.com/VOD/bipbop_adv_example_v2/master.m3u8"; //test live stream;
//        testUrl = "http://playertest.longtailvideo.com/adaptive/oceans_aes/oceans_aes.m3u8"; //test live stream;


        testUrl = "http://static.videoincloud.com/tiaojie/video/course/20160926/dongguan1.mp4"; //test live stream;
//        testUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks"; //test live stream;

        videoView.setVideoPath(testUrl);
//        videoView.getPlayer().setDisplayModel(DISPLAY_FULL_WINDOW);
        videoView.getPlayer().aspectRatio(AR_ASPECT_FILL_PARENT);
        videoView.getMediaController().show(8000);
        videoView.getPlayer().start();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        PlayerManager.getInstance().onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (PlayerManager.getInstance().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            //模拟器测试时键盘中的的Enter键，模拟ok键（推荐TV开发中使用蓝叠模拟器）
            case KeyEvent.KEYCODE_ENTER:
                Log.e("key", "你按下Enter/OK键");
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                Log.e("key", "你按下中间键");
                //如果是录像
                if (testUrl.startsWith("http")) {
                    if (!videoView.getMediaController().isShowing()) {
                        videoView.getMediaController().show(5000);
                    } else {

                        videoView.getMediaController().setPauseState();
//                        videoView.getMediaController().hide(true);
                    }
                } else {
                    //如果是直播
                    videoView.getMediaController().hide(true);
                }
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
                Log.e("key", "你按下下方向键");
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                Log.e("key", "你按下左方向键");

                if (!videoView.getMediaController().isShowing()) {
                    setPlayControlState();
                    return super.onKeyDown(keyCode, event);
                }

                if (isFirst) {
                    videoView.getPlayer().pause();
                    longRight = (int) currentTimeMillis();
                    currentPosition = videoView.getPlayer().getCurrentPosition();
                    isFirst = false;
                }

                int timeLeft = Long.valueOf(System.currentTimeMillis() - longRight).intValue();


                Message messageLeft = Message.obtain();
                messageLeft.arg1 = 1001;
                messageLeft.obj = timeLeft;
                handler.sendMessage(messageLeft);

                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                Log.e("key", "你按下右方向键");


                if (!videoView.getMediaController().isShowing()) {
                    setPlayControlState();
                    return super.onKeyDown(keyCode, event);
                }

                Log.e("key", "你按下右方向键");
                if (isFirst) {
                    videoView.getPlayer().pause();
                    longRight = (int) currentTimeMillis();
                    currentPosition = videoView.getPlayer().getCurrentPosition();
                    isFirst = false;
                }

                int timeRight = Long.valueOf(System.currentTimeMillis() - longRight).intValue();


                Message messageRight = Message.obtain();
                messageRight.arg1 = 1002;
                messageRight.obj = timeRight;
                handler.sendMessage(messageRight);

                break;

            case KeyEvent.KEYCODE_DPAD_UP:
                Log.e("key", "你按下上方向键");
                break;
            case KeyEvent.KEYCODE_BACK:
                Log.e("key", "你按下了返回键");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        videoView.getMediaController().setTvFast(false);
        switch (keyCode) {
            //模拟器测试时键盘中的的Enter键，模拟ok键（推荐TV开发中使用蓝叠模拟器）
            case KeyEvent.KEYCODE_ENTER:
                Log.e("key", "你松开了Enter/OK键");
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                Log.e("key", "你松开了中间键");
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
                Log.e("key", "你松开了下方向键");
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                Log.e("key", "你松开了左方向键");
                layoutFastShow.setVisibility(View.GONE);
                if (!isFirst) {
                    int time = Long.valueOf(System.currentTimeMillis() - longRight).intValue();

                    Log.e("key", "您按了---" + (int) time / 1000 + "秒");

                    videoView.getPlayer().seekTo(currentPosition - time * 20);
//                    continuePlay();
                    videoView.getPlayer().start();
                    isFirst = true;
                }

                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                Log.e("key", "你松开了右方向键");
                layoutFastShow.setVisibility(View.GONE);
                if (!isFirst) {
                    Log.e("key", "你松开了右方向键");
                    int time = Long.valueOf(System.currentTimeMillis() - longRight).intValue();

                    Log.e("key", "您按了---" + (int) time / 1000 + "秒");

                    videoView.getPlayer().seekTo(currentPosition + time * 20);
//                    continuePlay();
                    videoView.getPlayer().start();
                    isFirst = true;
                }
                break;

            case KeyEvent.KEYCODE_DPAD_UP:
                Log.e("key", "你松开了上方向键");


                break;
        }

        return super.onKeyUp(keyCode, event);
    }


    private void setPlayControlState() {
        if (videoView.getMediaController().isShowing()) {
            videoView.getMediaController().hide(true);
        } else {
            videoView.getMediaController().show(5000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("tv","--------> onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("tv","--------> onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("tv","--------> onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("tv","--------> onDestroy()");
    }
}
