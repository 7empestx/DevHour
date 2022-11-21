package dev.hour.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import dev.hour.R;
import dev.hour.model.DietPreferenceItem;
import dev.hour.model.DietPreferenceItemViewHolder;

public class DietPreferenceArrayAdapter extends ArrayAdapter<DietPreferenceItem> {

    private LayoutInflater inflater;

    public DietPreferenceArrayAdapter(Context context, List<DietPreferenceItem> dietPrefList ) {
        super( context, R.layout.fragment_profile_row, R.id.rowTextView, dietPrefList);
        // Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context) ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Diet pref to display
        DietPreferenceItem dietPref = this.getItem( position );

        // The child views in each row.
        CheckBox checkBox ;
        TextView textView ;

        // Create a new row view
        if ( convertView == null ) {
            convertView = inflater.inflate(R.layout.fragment_profile_row, null);

            // Find the child views.
            textView = (TextView) convertView.findViewById( R.id.rowTextView );
            checkBox = (CheckBox) convertView.findViewById( R.id.CheckBox01 );

            // Optimization: Tag the row with it's child views, so we don't have to
            // call findViewById() later when we reuse the row.
            convertView.setTag( new DietPreferenceItemViewHolder(textView,checkBox) );

            // If CheckBox is toggled, update the diet pref it is tagged with.
            checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    DietPreferenceItem dietPreferenceItem = (DietPreferenceItem) cb.getTag();
                    dietPreferenceItem.setChecked( cb.isChecked() );
                }
            });
        }
        // Reuse existing row view
        else {
            // Because we use a ViewHolder, we avoid having to call findViewById().
            DietPreferenceItemViewHolder viewHolder = (DietPreferenceItemViewHolder) convertView.getTag();
            checkBox = viewHolder.getCheckBox() ;
            textView = viewHolder.getTextView() ;
        }

        // Tag the CheckBox with the Planet it is displaying, so that we can
        // access the dietPref in onClick() when the CheckBox is toggled.
        checkBox.setTag( dietPref );

        // Display diet pref data
        checkBox.setChecked( dietPref.isChecked() );
        textView.setText( dietPref.getName() );

        return convertView;
    }

}

