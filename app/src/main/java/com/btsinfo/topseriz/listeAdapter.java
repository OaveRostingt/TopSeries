package com.btsinfo.topseriz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class listeAdapter extends ArrayAdapter<Series> implements Filterable {
    private Context context;
    private TextView tvTitre;
    private TextView tvAnnee;
    private ImageView tvUrlAffiche;
    private RequestOptions requestOptions;

    public listeAdapter(@NonNull Context context, List<Series> listeSerie){
        super(context, -1, listeSerie);
        this.context=context;
        requestOptions=new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.cerise)
                .error(R.drawable.cerise);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        Series uneSerie;
        view = null;

        if (convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.serie,parent,false);
        }
        else {
            view = convertView;
        }

        uneSerie = getItem(position);
        tvTitre=(TextView) view.findViewById(R.id.tvTitre);
        tvAnnee = (TextView) view.findViewById(R.id.tvAnnee);
        tvUrlAffiche = (ImageView) view.findViewById(R.id.imgSerie);

        tvTitre.setText(uneSerie.getTitre());
        tvAnnee.setText(uneSerie.getAnnee_production());
        Glide.with(context).load(uneSerie.getAffiche_url()).apply(requestOptions).into(tvUrlAffiche);

        return view;
    }
    public long getItemId(int position){
        return  getItem(position).getId();
    }


}
