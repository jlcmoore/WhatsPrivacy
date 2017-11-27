package jlcmoore.whatsprivacy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import jlcmoore.whatsprivacy.R;
import jlcmoore.whatsprivacy.data.AppDatabase;
import jlcmoore.whatsprivacy.data.Domain;
import jlcmoore.whatsprivacy.data.Response;

/**
 * Created by jared on 11/4/17.
 */

public class SummaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        int pid = getIntent().getExtras().getInt("pid");

        AppDatabase surveydb = AppDatabase.getDatabase(getApplicationContext());

        Response[] responses = surveydb.appDao().loadParticipantResponses(pid);
        Domain[] categories = surveydb.appDao().loadDomains();

        // TODO: show the responses
        LinearLayout categoryContainer = (LinearLayout) findViewById(R.id.summaryContainer);
        for (Domain domain : categories) {
            categoryContainer.addView(getCategoryView(domain,
                                        getApplicationContext(),
                                        categoryContainer,
                                        responses));
        }
    }

    private View getCategoryView(Domain domain, Context context, ViewGroup parent, Response[] responses) {
        View view = LayoutInflater.from(context).inflate(R.layout.summary_category_item,
                parent, false);
        LinearLayout responseContainer = (LinearLayout) view.findViewById(R.id.responseContainer);
        TextView categoryName = (TextView) view.findViewById(R.id.categoryName);
        categoryName.setText(domain.name);
        for (Response response : responses) {
            responseContainer.addView(getResponseView(response, context, responseContainer));
        }
        return view;
    }

    private View getResponseView(Response response, Context context, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.summary_response_item,
                parent, false);
        TextView responseName = (TextView) view.findViewById(R.id.questionName);
        //String groupsSelected = response.getResponseGroups();
        // TODO: also need question information
        //responseName.setText(groupsSelected);
        return view;
    }

    public void newParticipant(View button) {
        Intent startMain = new Intent(this, MainActivity.class);
        startActivity(startMain);
        finish();
    }
}
