package jlcmoore.whatsprivacy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import jlcmoore.whatsprivacy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        inflater.inflate(R.menu.menu_profile_fragment, menu);
        // Associate searchable configuration with the SearchView
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_sync:
                Toast.makeText(this, "Sync Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_exit_questionnaire_save:
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_exit_questionnaire_no_save:
                Toast.makeText(this, "No Save", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     *
     * @param id must be of type EditText
     * @return
     */
    private String getStringFromEditText(@IdRes int id) {
        return ((EditText) findViewById(id)).getText().toString();
    }

    public void submitProfile(View button) {
        String name = getStringFromEditText(R.id.username);
        String groupOne = getStringFromEditText(R.id.groupNameOne);
        if (groupOne.isEmpty()) {
            groupOne = getString(R.string.group1);
        }
        String groupTwo = getStringFromEditText(R.id.groupNameTwo);
        if (groupTwo.isEmpty()) {
            groupTwo = getString(R.string.group2);
        }
        String groupThree = getStringFromEditText(R.id.groupNameThree);
        if (groupThree.isEmpty()) {
            groupThree = getString(R.string.group3);
        }
        String groupFour = getStringFromEditText(R.id.groupNameFour);
        if (groupFour.isEmpty()) {
            groupFour = getString(R.string.group4);
        }
        String[] groups = new String[]{groupOne, groupTwo, groupThree, groupFour};

        if (!name.isEmpty()) {

            // TODO: update controller to correspond to these values and pass to quesions launch questions
            Intent startQuestions = new Intent(this, QuestionsActivity.class);
            startQuestions.putExtra("name", name);
            startQuestions.putExtra("groups", groups);

            startActivity(startQuestions);
            finish();
        }
    }

}
