package com.example.hayden.heritagemalta2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.hayden.heritagemalta2.R.styleable.View;

/**
 * Created by N0589776 on 21/03/2017.
 */

public class MuseumListAdapter extends ArrayAdapter<Museum> {

    public MuseumListAdapter(Context context, ArrayList<Museum> museums){
        super(context,0,museums);


    }
    @Override
    public android.view.View getView(int position, View convertView, ViewGroup parent ){
        Museum museum  = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.museum_row,parent,false);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.txtMusTitle);
            ImageView musPic = (ImageView)convertView.findViewById(R.id.imgMusImg);
            txtTitle.setText(museum.Name);
           Picasso.with(getContext()).load(museum.PictureURL).into(musPic);
            return convertView;
        }
        return convertView;
    }
}
