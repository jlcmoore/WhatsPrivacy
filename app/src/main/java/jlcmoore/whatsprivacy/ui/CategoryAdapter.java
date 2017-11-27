package jlcmoore.whatsprivacy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import jlcmoore.whatsprivacy.R;

/**
 * Created by jared on 11/12/17.
 */

public class CategoryAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] categories;
    private boolean[] checked;

    public CategoryAdapter(Context context, String[] categories) {
        super(context, -1, categories);
        this.context = context;
        this.categories = categories;
        this.checked = new boolean[categories.length];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        String category = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.summary_category_item,
                    parent, false);
        }
        TextView categoryName = (TextView) convertView.findViewById(R.id.categoryName);
        categoryName.setText(category);

        return convertView;
    }

    @Override
    public String getItem(int position) {
        return categories[position];
    }

}