package dev.hour.view.list;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.hour.R;

/**
 * Tags list adapter. Defines the View Holder for the Tag list
 * @author Carlos L. Cuenca
 * @version 1.0.0
 */
public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.TagListItemViewHolder> {

    /// --------------
    /// Private Fields

    private List<String> tagsList = new ArrayList<>();

    @NonNull
    @Override
    public TagListAdapter.TagListItemViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_tag_list_item,
                                parent,false);

        return new TagListAdapter.TagListItemViewHolder(view);

    }

    /**
     * Invoked when the [TagListItemViewHolder] is to be bound to the view.
     * @param holder The [TagListItemViewHolder]
     * @param position The position of the tag in the collection
     */
    @Override
    public void onBindViewHolder(@NonNull TagListAdapter.TagListItemViewHolder holder, int position) {

        final ViewGroup view    = (ViewGroup) holder.itemView   ;
        final String    tag     = tagsList.get(position)        ;

        if(tag != null){

            final TextView title = view.findViewById(R.id.fragment_tag_list_item_title);

            title.setText(tag);

        }

    }

    /**
     * Adds the tag to the List of tags
     * @param tag The tag to add
     */
    @SuppressLint("NotifyDataSetChanged")
    public void addTag(final String tag) {

        this.tagsList.add(tag);

        notifyDataSetChanged();

    }

    /**
     * Retrieves the [String] tags in the list
     * @return List tags
     */
    public List<String> getTags() {

        return this.tagsList;

    }

    /**
     * Returns the amount of items in the list
     * @return int value of the amount of Restaurants in the list
     */
    @Override
    public int getItemCount() {

        return this.tagsList.size();

    }

    /// -------
    /// Classes

    /**
     * The [ViewHolder] instance that holds the view to be bound.
     */
    class TagListItemViewHolder extends RecyclerView.ViewHolder {

        TagListItemViewHolder(View view){
            super(view);
        }

    }

}
