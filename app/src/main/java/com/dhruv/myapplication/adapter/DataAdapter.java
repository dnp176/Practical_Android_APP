package com.dhruv.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dhruv.myapplication.R;
import com.dhruv.myapplication.helper.PojoMore;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class DataAdapter  extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private Context mContext ;
    private List<PojoMore> mData ;
    RequestOptions option;


    public DataAdapter(Context mContext, List<PojoMore> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().fitCenter();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.data_item,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;

        viewHolder.materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(mData.get(viewHolder.getAdapterPosition()).getId());
                Toast.makeText(mContext,"ID NUMBER IS " + number,Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(mContext)
                .load(mData
                        .get(position
                        ).getImage_url())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.circular_indicator.setVisibility(View.GONE);
                        return false;
                    }
                }).apply(option).fitCenter().into(holder.img_thumbnail);

        holder.name.setText(mData.get(position).getName());
        holder.subject.setText(mData.get(position).getSubject());
        holder.place.setText("• " +mData.get(position).getQualification());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView img_thumbnail;
        ConstraintLayout view_container;
        CircularProgressIndicator circular_indicator;
        MaterialTextView name,subject,place;
        MaterialButton materialButton;


        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            name = itemView.findViewById(R.id.name);
            subject = itemView.findViewById(R.id.subject);
            place = itemView.findViewById(R.id.place);
            materialButton = itemView.findViewById(R.id.materialButton);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);
            circular_indicator = itemView.findViewById(R.id.circular_indicator);

        }
    }
}
