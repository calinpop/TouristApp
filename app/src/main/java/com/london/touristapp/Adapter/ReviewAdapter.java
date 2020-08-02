package com.london.touristapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.london.touristapp.Model.ModelAttraction;
import com.london.touristapp.Model.ModelReview;
import com.london.touristapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<ModelReview> mData;
    private LayoutInflater mInflater;

    private  Context mContext;

    // data is passed into the constructor
    public ReviewAdapter(Context context, ArrayList<ModelReview> data) {
        this.mInflater = LayoutInflater.from(context);
        mContext=context;
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lyt_reviews ,parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {

        final ModelReview data = mData.get(position);
        holder.txtPubEmail.setText("By : "+data.getPubEmail());
        holder.txtReview.setText(data.getReview());

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtPubEmail;
        TextView txtReview;

        ViewHolder(View itemView)
        {
            super(itemView);
            txtPubEmail=itemView.findViewById(R.id.txtPubEmail);
            txtReview = itemView.findViewById(R.id.txtReview);
        }

    }

}