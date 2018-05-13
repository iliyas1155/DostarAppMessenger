package com.example.madina.dostarapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madina.dostarapp.Items.Course;
import com.example.madina.dostarapp.Utils.DeveloperKey;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import static com.example.madina.dostarapp.CoursesActivity.getSelectedCourse;

public class CourseActivity extends SampleActivity implements YouTubePlayer.OnInitializedListener {

    Course course;

    TextView descTextView;
    YouTubePlayerFragment youtubeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        descTextView = findViewById(R.id.course_description);

        course = getSelectedCourse();

        descTextView.setText(course.desc);

        initYoutubeFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setTitle(course.name);
    }

    private void initYoutubeFragment() {
        youtubeFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youtubeFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            String url = course.url;
            int startIndex = url.indexOf("=");
            int endIndex = url.indexOf("&");
            if(!url.contains("&")){
                endIndex = url.length();
            }
            url = url.substring(startIndex + 1, endIndex);

            player.cueVideo(url);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Cannot load the video.", Toast.LENGTH_SHORT).show();
    }
}
