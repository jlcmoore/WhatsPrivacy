package jlcmoore.whatsprivacy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import jlcmoore.whatsprivacy.R;

/**
 * Created by jared on 11/12/17.
 */

public class QuestionsFinishedActivity extends AppCompatActivity {
    private int lastPid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_finished);
        lastPid = getIntent().getExtras().getInt("pid");
    }

    public void continueToSummary(View button) {
        Intent startSummary = new Intent(this, SummaryActivity.class);
        startSummary.putExtra("pid", lastPid);
        startActivity(startSummary);
        finish();
    }
}
