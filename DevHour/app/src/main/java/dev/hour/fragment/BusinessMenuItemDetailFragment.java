package dev.hour.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import dev.hour.R;

public class BusinessMenuItemDetailFragment extends Fragment {
    public final static String TAG = "BusinessMenuItemDetail";

    @Override
    public View onCreateView(LayoutInflater lf, ViewGroup vg, Bundle b){
        final View layout = (View) lf.inflate(R.layout.fragment_business_menu_item_detail, vg, false);
        return layout;
    }
}
