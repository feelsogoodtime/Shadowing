package com.project.shadowing;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Recycler_To_Fragment_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnThumbs_UpClickListener {
        public void onThumbs_UpClick(View view, int position);
    }

    public interface OnPreviewClickListener {
        public void onPreviewClick(View view, int position);
    }

    private OnThumbs_UpClickListener onThumbs_UpClickListener;

    private OnPreviewClickListener onPreviewClickListener;

    public static class mViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbs_up;
//        ImageView previewImage;
        ImageView iconImage;
//        TextView titleText;
//        TextView levelText;
//        TextView movieTypeText;
        TextView explainText;

        LinearLayout linearLayout;


        mViewHolder(View view) {


            super(view);

            linearLayout = (LinearLayout) view.findViewById(R.id.fragment1_recycler_view_row_linearlayout_preview);

            thumbs_up = (ImageView) view.findViewById(R.id.fragment1_recycler_view_row_imageview_thumbs_up);
//            previewImage = (ImageView)view.findViewById(R.id.fragment1_recycler_view_row_imageview_preview);
            iconImage = (ImageView)view.findViewById(R.id.fragment1_recycler_view_row_imageview_icon);
//            titleText = (TextView)view.findViewById(R.id.fragment1_recycler_view_row_textview_title);
//            levelText = (TextView)view.findViewById(R.id.fragment1_recycler_view_row_textview_level);
//            movieTypeText = (TextView)view.findViewById(R.id.fragment1_recycler_view_row_textview_movietype);
            explainText = (TextView)view.findViewById(R.id.fragment1_recycler_view_row_textview_explain);
        }
        public ImageView getThumbs_up() {
            return thumbs_up;
        }
        //public ImageView getPreviewImage() { return previewImage; }
        public LinearLayout getPreviewImage() { return linearLayout; }
    }
    private ArrayList<MovieInfo> movieInfoArrayList;

    Recycler_To_Fragment_Adapter(ArrayList<MovieInfo> movieInfoArrayList) {
        this.movieInfoArrayList = movieInfoArrayList;
    }

    Recycler_To_Fragment_Adapter(ArrayList<MovieInfo> movieInfoArrayList, OnThumbs_UpClickListener onThumbsUpClickListener, OnPreviewClickListener onPreviewClickListener) {
        this.movieInfoArrayList = movieInfoArrayList;
        this.onThumbs_UpClickListener = onThumbsUpClickListener;
        this.onPreviewClickListener = onPreviewClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment1_recycler_view_row, viewGroup, false);

        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        final mViewHolder mViewHolder = (mViewHolder) viewHolder;

        mViewHolder.linearLayout.setBackgroundResource(movieInfoArrayList.get(i).previewId);
//        mViewHolder.previewImage.setImageResource(movieInfoArrayList.get(i).previewId);
//        mViewHolder.iconImage.setImageResource(movieInfoArrayList.get(i).iconId);
//        mViewHolder.titleText.setText(movieInfoArrayList.get(i).title);
//        mViewHolder.levelText.setText(movieInfoArrayList.get(i).level);
//        mViewHolder.movieTypeText.setText(movieInfoArrayList.get(i).movieType);
        mViewHolder.explainText.setText(movieInfoArrayList.get(i).explain);

        if(movieInfoArrayList.get(i).isLike == 1) {
            mViewHolder.thumbs_up.setImageResource(R.drawable.ic_like_on);
        } else {
            mViewHolder.thumbs_up.setImageResource(R.drawable.ic_like_off);
        }
        Recycler_To_Fragment_Adapter.mViewHolder holder = (Recycler_To_Fragment_Adapter.mViewHolder)viewHolder;
        holder.getThumbs_up().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onThumbs_UpClickListener.onThumbs_UpClick(view, i);
                if(movieInfoArrayList.get(i).isLike == 1) {
                    mViewHolder.thumbs_up.setImageResource(R.drawable.ic_like_on);
                } else {
                    mViewHolder.thumbs_up.setImageResource(R.drawable.ic_like_off);
                }
            }
        });

        holder.getPreviewImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPreviewClickListener.onPreviewClick(view, i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return movieInfoArrayList.size();
    }
}
