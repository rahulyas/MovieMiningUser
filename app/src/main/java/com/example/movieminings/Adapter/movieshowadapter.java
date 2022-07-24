package com.example.movieminings.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieminings.Model.getvideodetails;
import com.example.movieminings.Model.movieitemClickListern;
import com.example.movieminings.R;

import java.util.List;

public class movieshowadapter extends RecyclerView.Adapter<movieshowadapter.MyViewHolder> {

    private Context mContext ;
    private List<getvideodetails> uploads ;
    movieitemClickListern movieItemClickListenerNew;

    public movieshowadapter(Context mContext, List<getvideodetails> uploads, movieitemClickListern listener) {
        this.mContext = mContext;
        this.uploads = uploads;
        movieItemClickListenerNew = listener;


    }

    @NonNull
    @Override
    public movieshowadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // View view ;
        // LayoutInflater mInflater = LayoutInflater.from(mContext);
        // view = mInflater.inflate(R.layout.item_movie,parent,false);
        // return new MyViewHolder(view);
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieshowadapter.MyViewHolder holder, int position) {
        final getvideodetails getVideoDetails = uploads.get(position);

        holder.TvTitle.setText(getVideoDetails.getVideo_name());
        // holder.ImgMovie.setImageResource(getVideoDetails.getVideo_thumb());
        Glide.with(mContext).load(getVideoDetails.getVideo_thumb()).into(holder.ImgMovie);
        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView TvTitle;
        private ImageView ImgMovie;
        ConstraintLayout container;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvTitle = itemView.findViewById(R.id.item_movie_title);
            ImgMovie = itemView.findViewById(R.id.item_movie_img);
            container = itemView.findViewById(R.id.container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    movieItemClickListenerNew.onMovieClick(uploads.get(getAdapterPosition()),ImgMovie);
                }
            });
        }
    }
}
