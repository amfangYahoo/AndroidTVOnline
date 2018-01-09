package tcking.github.com.giraffeplayer2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.tcking.giraffeplayer2.R;

/**
 * Created by tcking on 2017
 */

public class PlayerActivity extends BasePlayerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giraffe_player_activity);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        VideoInfo videoInfo = intent.getParcelableExtra("__video_info__");
        if (videoInfo == null) {
            finish();
            return;
        }
        PlayerManager.getInstance().releaseByFingerprint(videoInfo.getFingerprint());
        VideoView videoView = (VideoView) findViewById(R.id.video_view);
        videoView.videoInfo(videoInfo);
        PlayerManager.getInstance().getPlayer(videoView).start();
    }

}
