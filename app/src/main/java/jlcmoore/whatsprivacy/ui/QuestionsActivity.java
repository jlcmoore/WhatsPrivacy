package jlcmoore.whatsprivacy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jlcmoore.whatsprivacy.data.Domain;
import jlcmoore.whatsprivacy.data.QuestionIterator;
import jlcmoore.whatsprivacy.R;
import jlcmoore.whatsprivacy.data.AppDatabase;
import jlcmoore.whatsprivacy.data.Participant;
import jlcmoore.whatsprivacy.data.Question;
import jlcmoore.whatsprivacy.data.Response;
import jlcmoore.whatsprivacy.data.Scenario;

public class QuestionsActivity extends AppCompatActivity {
    private List<Response> responses;
    // index into currentQuestions
    private Question currentQuestion;
    // list of categories to follow when indexing into currentQuestions
    private Participant participant;
    private AppDatabase surveydb;
    private QuestionIterator questionIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        responses = new ArrayList<>();
        // TODO: Main thread queries?
        surveydb = AppDatabase.getDatabase(getApplicationContext());

        setContentView(R.layout.activity_questions);

        // Get profile information from main
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String[] groupNames = extras.getStringArray("groups");
        int maxID = surveydb.appDao().getMaxParticipantID();
        // this plus one is important!
        participant = new Participant(maxID + 1, extras.getString("name"),
                                        extras.getStringArray("groups"));
        surveydb.appDao().insertParticipant(participant);

        // generate new iteration of sample_questions for user
        Question[] questions = surveydb.appDao().loadQuestions();
        Domain[] domains = surveydb.appDao().loadDomains();
        Scenario[] scenarios = surveydb.appDao().loadScenarios();
        questionIterator = new QuestionIterator(questions, scenarios, domains);

        // set up groups
        ListView groups = (ListView) findViewById(R.id.groupsContainer);
        GroupAdapter adapter = new GroupAdapter(this, groupNames);
        groups.setAdapter(adapter);

        groups.setOnItemClickListener(selectGroupHandler);
        groups.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        setupNewQuestion();
    }

    private AdapterView.OnItemClickListener selectGroupHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // TODO: Do something in response to the click
            GroupAdapter groups = (GroupAdapter) parent.getAdapter();
            groups.changeChecked(position);
        }
    };

    /**
     * Only call if sample_questions are left
     */
    private void setupNewQuestion() {
        if (questionIterator.hasNext()) {
            // TODO: add more affordances for view change
            // TODO: investigate order of calling next, fencepost with constructor?
            Question nextQuestion = questionIterator.next();
            TextView question = findViewById(R.id.singleQuestion);
            question.setText(nextQuestion.question);
            if (currentQuestion == null || currentQuestion.domain != nextQuestion.domain) {
                // TODO: signal domain change
                String newDomainName = questionIterator.getCurrentDomainDescription();
            } else if (currentQuestion == null || currentQuestion.scenario != nextQuestion.scenario) {
                // TODO: signal scenario change
                String newScenarioName = questionIterator.getCurrentScenarioDescription();
            }
            currentQuestion = nextQuestion;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        getMenuInflater().inflate(R.menu.menu_questions_fragment, menu);
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

    public void submitQuestion(View button) {
        // save the current responses
        Response current = new Response(currentQuestion.id,
                                        participant.id,
                                        getGroupsChecked());
        responses.add(current);
        if (questionIterator.hasNext()) {
            setupNewQuestion();
        } else {
            // TODO: Only save info after user has answered all sample_questions?
            saveUserResponses();
            Intent startQuestionsFinished = new Intent(this, QuestionsFinishedActivity.class);
            startQuestionsFinished.putExtra("pid", participant.id);
            startActivity(startQuestionsFinished);
            finish();
        }
    }

    private void saveUserResponses() {
        surveydb.appDao().insertResponses(responses);
    }

    private Set<Integer> getGroupsChecked() {
        Set<Integer> groups = new HashSet<>();
        ListView groupContainer = (ListView) findViewById(R.id.groupsContainer);
        GroupAdapter adapter = (GroupAdapter) groupContainer.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.isChecked(i)) {
                    groups.add(i);
                }
            }
            // TODO: Not sure if this is the correct place
            adapter.clearChecks();
        }
        return groups;
    }
}
