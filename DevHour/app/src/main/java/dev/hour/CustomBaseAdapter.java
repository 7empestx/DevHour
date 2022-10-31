package dev.hour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String[] restaurantList;
    int[] listViewIcons;
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, String [] restaurantList, int [] listViewIcons){
        this.context = ctx;
        this.restaurantList = restaurantList;
        this.listViewIcons = listViewIcons;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return restaurantList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_list_view, null);

        TextView txtView = (TextView) view.findViewById(R.id.textView);
        ImageView listViewIcon = (ImageView) view.findViewById(R.id.listViewIcon);
        txtView.setText(restaurantList[i]);
        listViewIcon.setImageResource(listViewIcons[0]);
        return view;
    }
}
