package com.mad.group8.th2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.mad.group8.th2.R; // Replace with your R class reference
import java.util.List;

public class CustomImageSpinnerAdapter extends ArrayAdapter<Integer> {
    private List<Integer> imageResources;
    private Context context;

    public CustomImageSpinnerAdapter(Context context, List<Integer> imageResources) {
        super(context, 0, imageResources);
        this.context = context;
        this.imageResources = imageResources;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createCustomView(position, convertView, parent);
    }

    private View createCustomView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cat_img, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.imageView2);
        imageView.setImageResource(imageResources.get(position));

        return view;
    }
}
