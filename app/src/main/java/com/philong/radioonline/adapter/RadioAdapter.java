package com.philong.radioonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.philong.radioonline.R;
import com.philong.radioonline.model.Radio;
import com.philong.radioonline.service.RadioService;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Long on 6/30/2017.
 */

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioViewHolder>{

    public static final String SEND_RADIO = "SEND_RADIO";

    private List<Radio> mRadioList;
    private Context mContext;

    public RadioAdapter(List<Radio> radioList, Context context) {
        mRadioList = radioList;
        mContext = context;
    }

    @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_radio, parent, false);
        return new RadioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RadioViewHolder holder, int position) {
        final Radio radio = mRadioList.get(position);
        Picasso.with(mContext).load(radio.getImage().getUrl()).error(R.drawable.placeholder).into(holder.imgThumbnail);
        holder.txtName.setText(radio.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SEND_RADIO);
                intent1.putExtra("Name", radio.getName());
                intent1.putExtra("Hinh", radio.getImage().getUrl());
                mContext.sendBroadcast(intent1);

                Intent intent = new Intent(mContext, RadioService.class);
                intent.putExtra("Link", radio.getStream()[0].getStream());
                mContext.startService(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRadioList.size();
    }

    public static class RadioViewHolder extends RecyclerView.ViewHolder{

        private TextView txtName;
        private ImageView imgThumbnail;
        private CardView cardView;

        public RadioViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.imgThumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }

}
