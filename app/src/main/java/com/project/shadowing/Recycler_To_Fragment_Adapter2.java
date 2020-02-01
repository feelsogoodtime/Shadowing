package com.project.shadowing;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Recycler_To_Fragment_Adapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public static class mViewHolder extends RecyclerView.ViewHolder {

//        ImageView previewImage;
        //ImageView iconImage;
        //TextView titleText;
        TextView levelText;
        //TextView movieTypeText;
        TextView explainText;
        LinearLayout linearLayout;

        mViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.fragment2_recycler_view_row_linearlayout_preview);
//            previewImage = (ImageView) view.findViewById(R.id.fragment2_recycler_view_row_imageview_preview);
//          iconImage = (ImageView)view.findViewById(R.id.fragment2_recycler_view_row_imageview_icon);
//          titleText = (TextView)view.findViewById(R.id.fragment2_recycler_view_row_textview_title);
//          levelText = (TextView)view.findViewById(R.id.fragment2_recycler_view_row_textview_level);
//          movieTypeText = (TextView)view.findViewById(R.id.fragment2_recycler_view_row_textview_movietype);
            explainText = (TextView) view.findViewById(R.id.fragment2_recycler_view_row_textview_explain);
        }
        public LinearLayout getPreviewImage() { return linearLayout; }

//        public ImageView getPreviewImage() { return previewImage; }
    }


    private ArrayList<MovieInfo> movieInfoArrayList;

    Recycler_To_Fragment_Adapter2(ArrayList<MovieInfo> movieInfoArrayList, OnItemClickListener onItemClickListener) {
        this.movieInfoArrayList = movieInfoArrayList;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment2_recycler_view_row, viewGroup, false);

        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        final mViewHolder mViewHolder = (mViewHolder) viewHolder;

        mViewHolder.linearLayout.setBackgroundResource(movieInfoArrayList.get(i).previewId);

//        mViewHolder.previewImage.setImageResource(movieInfoArrayList.get(i).previewId);
//        mViewHolder.levelText.setText(movieInfoArrayList.get(i).level);
        mViewHolder.explainText.setText(movieInfoArrayList.get(i).explain);

        Recycler_To_Fragment_Adapter2.mViewHolder holder = (Recycler_To_Fragment_Adapter2.mViewHolder) viewHolder;
        holder.getPreviewImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieInfoArrayList.size();
    }
}
