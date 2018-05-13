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

    TextView titleTextView, descTextView, textTextView;
    YouTubePlayerFragment youtubeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        titleTextView = findViewById(R.id.course_title);
        descTextView = findViewById(R.id.course_description);
        textTextView = findViewById(R.id.course_text);

        Course course = getSelectedCourse();

        titleTextView.setText(course.name);
        descTextView.setText(course.desc);
        textTextView.setText(course.text);

        initYoutubeFragment();
    }

    private void initYoutubeFragment() {
        youtubeFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youtubeFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("nCgQDjiotG0"); // todo: Add dynamic link.
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Cannot load the video.", Toast.LENGTH_SHORT).show();
    }
}
