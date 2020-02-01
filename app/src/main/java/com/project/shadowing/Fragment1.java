package com.project.shadowing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.project.shadowing.Fragment2.addToMypage;
import static com.project.shadowing.Fragment2.deleteToMypage;
import static com.project.shadowing.MainActivity.dbHelper;
import static com.project.shadowing.MainActivity.movieInfoArrayList_fragment1;


public class Fragment1 extends Fragment {

    Intent intent;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView thumbs_up;

    int key = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        recyclerView = rootView.findViewById(R.id.fragment1_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        thumbs_up = (ImageView)rootView.findViewById(R.id.fragment1_recycler_view_row_imageview_thumbs_up);


        // movieInfoArrayList_fragment1.add(new MovieInfo(preview, iconId, title, level, movietype, explain))
        // 위의 방식으로 리사이클러뷰에 데이터 추가

        movieInfoArrayList_fragment1.clear();

        String sql = "select key_, islike, previewid, level, explain_ from movieinfo_fragment1";

        dbHelper.insertToArrayList(sql, 1);


        Recycler_To_Fragment_Adapter recycler_to_fragment_adapter = new Recycler_To_Fragment_Adapter(movieInfoArrayList_fragment1,
                new Recycler_To_Fragment_Adapter.OnThumbs_UpClickListener() {
            @Override
            public void onThumbs_UpClick(View view, int position) {
                if (movieInfoArrayList_fragment1.get(position).isLike == 1) {
                    movieInfoArrayList_fragment1.get(position).isLike = movieInfoArrayList_fragment1.get(position).isLike * -1;
                    deleteToMypage(movieInfoArrayList_fragment1.get(position), position);
                    dbHelper.update("update movieinfo_fragment1 set islike=-1 where key_=" + (position ));
                } else {
                    movieInfoArrayList_fragment1.get(position).isLike = movieInfoArrayList_fragment1.get(position).isLike * -1;
                    addToMypage(movieInfoArrayList_fragment1.get(position), position);
                    dbHelper.update("update movieinfo_fragment1 set islike=1 where key_=" + (position));
                }
            }
        }, new Recycler_To_Fragment_Adapter.OnPreviewClickListener() {
            @Override
            public void onPreviewClick(View view, int position) {
                Log.d("!!","recycleView"+position);
                Intent intent = new Intent(getContext(), PlayActivity.class);
                intent.putExtra("key", movieInfoArrayList_fragment1.get(position).key);
                intent.addFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(recycler_to_fragment_adapter);
        return rootView;
    }
}
