package com.london.touristapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.london.touristapp.Activities.Detail;
import com.london.touristapp.Constants;
import com.london.touristapp.Model.ModelAttraction;
import com.london.touristapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {

    private ArrayList<ModelAttraction> mData;
    private LayoutInflater mInflater;

    private  Context mContext;

    // data is passed into the constructor
    public AttractionAdapter(Context context, ArrayList<ModelAttraction> data) {
        this.mInflater = LayoutInflater.from(context);
        mContext=context;
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lyt_attraction ,parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {

        final ModelAttraction data = mData.get(position);
        holder.txtPlaceName.setText(data.getName());
        Picasso.get().load(data.getImageLink()).placeholder(R.drawable.img_placeholder).into(holder.imgPlace);


       holder.btnDetail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               // This Method run When Click On Item


               Intent intent=new Intent(mContext, Detail.class);

               // Putting Extra Data for Detail Class to Show
               intent.putExtra(Constants.EXTRA_NAME,data.getName());
               intent.putExtra(Constants.EXTRA_DETAIL,data.getDesc());
               intent.putExtra(Constants.EXTRA_ID,data.getID());
               intent.putExtra(Constants.EXTRA_IMAGE,data.getImageLink());
               intent.putExtra(Constants.EXTRA_TICKET_LINK,data.getTicketLink());

               mContext.startActivity(intent);


           }
       });


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtPlaceName;
        ImageView imgPlace;
        Button btnDetail;

        ViewHolder(View itemView)
        {
            super(itemView);
            txtPlaceName=itemView.findViewById(R.id.txtPlaceName);
            imgPlace = itemView.findViewById(R.id.image);
            btnDetail=itemView.findViewById(R.id.btnDetail);
        }

    }

}