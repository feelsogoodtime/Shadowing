package com.project.shadowing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static com.project.shadowing.MainActivity.dbHelper;


public class Fragment2 extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    static ArrayList<MovieInfo> movieInfoArrayList_fragment2 = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        recyclerView = rootView.findViewById(R.id.fragment2_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        movieInfoArrayList_fragment2.clear();

        String sql = "select key_, islike, previewid, level, explain_ from movieinfo_fragment2";

        dbHelper.insertToArrayList(sql, 2);

        Recycler_To_Fragment_Adapter2 recycler_to_fragment_adapter = new Recycler_To_Fragment_Adapter2(movieInfoArrayList_fragment2, (new Recycler_To_Fragment_Adapter2.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), PlayActivity.class);
                intent.putExtra("key", movieInfoArrayList_fragment2.get(position).key);
                startActivity(intent);
            }
        }));
        recyclerView.setAdapter(recycler_to_fragment_adapter);


        return rootView;
    }

    public static void addToMypage(MovieInfo movieInfo, int position) {
        movieInfoArrayList_fragment2.remove(movieInfo);
        dbHelper.delete("delete from movieinfo_fragment2 where key_=" + (position));
        movieInfoArrayList_fragment2.add(movieInfo);
        String sql = "insert into movieinfo_fragment2(key_, islike, previewid, level, explain_) values(?, ?, ?, ?, ?)";
        Object[] parmas = {movieInfo.key, movieInfo.isLike, movieInfo.previewId, movieInfo.level, movieInfo.explain};
        dbHelper.insert(sql, parmas);
    }

    public static void deleteToMypage(MovieInfo movieInfo, int position) {
        movieInfoArrayList_fragment2.remove(movieInfo);
        dbHelper.delete("delete from movieinfo_fragment2 where key_=" + (position));
    }
}
