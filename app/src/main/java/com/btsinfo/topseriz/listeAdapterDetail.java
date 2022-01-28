package com.btsinfo.topseriz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class listeAdapterDetail extends ArrayAdapter<Series> {

        private Context context;
        private TextView tvTitreDetail;
        private TextView tvAnneeDetail;
        private ImageView tvUrlAffiche;
        private TextView tvSynopsys;
        private TextView tvGenre;
        private TextView tvActeur;
        private RequestOptions requestOptions;

        public listeAdapterDetail(@NonNull Context context, List<Series> listeSerie){
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
                view = layoutInflater.inflate(R.layout.une_fiche_serie,parent,false);
            }
            else {
                view = convertView;
            }

            uneSerie = getItem(position);
            tvTitreDetail=(TextView) view.findViewById(R.id.tvTitreDetail);
            tvAnneeDetail = (TextView) view.findViewById(R.id.tvAnneeDetail);
            tvUrlAffiche = (ImageView) view.findViewById(R.id.imgSerieDetail);
            tvSynopsys=(TextView) view.findViewById(R.id.tvSynopsys);
            tvGenre=(TextView) view.findViewById(R.id.tvGenre);
            tvActeur=(TextView) view.findViewById(R.id.tvActeur);

            tvTitreDetail.setText(uneSerie.getTitre());
            tvAnneeDetail.setText(uneSerie.getAnnee_production());
            Glide.with(context).load(uneSerie.getAffiche_url()).apply(requestOptions).into(tvUrlAffiche);
            tvSynopsys.setText(uneSerie.getSynopsis());
            tvGenre.setText(uneSerie.getGenre());
            tvActeur.setText(uneSerie.getArtistes());

            return view;
        }

    public long getItemId(int position){
        return  getItem(position).getId();
    }
    }


