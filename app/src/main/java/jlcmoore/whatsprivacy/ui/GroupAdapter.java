package jlcmoore.whatsprivacy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import jlcmoore.whatsprivacy.R;

/**
 * Created by jared on 11/12/17.
 */

public class GroupAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] groups;
    private boolean[] checked;

    public GroupAdapter(Context context, String[] groups) {
        super(context, -1, groups);
        this.context = context;
        this.groups = groups;
        this.checked = new boolean[groups.length];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        String group = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item,
                    parent, false);
        }
        TextView groupName = (TextView) convertView.findViewById(R.id.groupName);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        groupName.setText(group);
        checkBox.setChecked(checked[position]);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeChecked(position);
            }
        });
        return convertView;
    }

    @Override
    public String getItem(int position) {
        return groups[position];
    }

    public boolean isChecked(int position) {
        return checked[position];
    }

    public void clearChecks() {
        for (int i = 0; i < checked.length; i++) {
            checked[i] = false;
        }
        notifyDataSetChanged();
    }

    public void changeChecked(int position) {
        checked[position] = !checked[position];
        Toast.makeText(getContext(), Arrays.toString(checked), Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }
}