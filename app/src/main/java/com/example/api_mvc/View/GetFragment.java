package com.example.api_mvc.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.api_mvc.ApiService;
import com.example.api_mvc.Classe.Pics;
import com.example.api_mvc.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetFragment extends Fragment {

    private Retrofit retrofit;
    private PicAdapter picAdapter;
    private ArrayList<Pics> listPics;

    public GetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setaRetrofit();

        recuperarInfo();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get, container, false);

        setaRecycler(view);

        return view;
    }


    private void setaRecycler(View view) {
        picAdapter = new PicAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_fragment_get);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(picAdapter);

    }



    private void setaRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void recuperarInfo() {
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Pics>> call = apiService.recuperarInfo();
        call.enqueue(new Callback<List<Pics>>() {
            @Override
            public void onResponse(Call<List<Pics>> call, Response<List<Pics>> response) {
                if (response.isSuccessful()){
                    ArrayList<Pics> list = (ArrayList<Pics>) response.body();
                    picAdapter.setPics(list);
                    Log.i("Pics", "onResponse: ok");
                } else {
                    Log.i("Pics", "not Successfull" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Pics>> call, Throwable t) {
                Log.i("Pics", "onFailure" + t.getMessage());
            }
        });
    }


}