package com.example.api_mvc.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.api_mvc.Api.ApiService;
import com.example.api_mvc.Classe.Pics;
import com.example.api_mvc.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFragment extends Fragment {

    private PicAdapter picAdapter;
    private ApiService apiService;

    public GetFragment(ApiService apiService ){
        this.apiService = apiService;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    private void recuperarInfo() {
        // Inicia a chamada para reciperar info, o metodo recuperarInfo foi criado na classe ApiService
        Call<List<Pics>> call = apiService.recuperarInfo();
        // call.enqueue() inicia uma chamada assincrona, call.execute() inicia uma chamada sincrona
        call.enqueue(new Callback<List<Pics>>() {
            @Override
            public void onResponse(@NonNull Call<List<Pics>> call, @NonNull Response<List<Pics>> response) {
                if (response.isSuccessful()){
                    ArrayList<Pics> list = (ArrayList<Pics>) response.body();
                    picAdapter.setPics(list);
                    Log.i("Pics", "onResponse: ok");
                } else {
                    setaErro();
                    Log.i("Pics", "not Successfull" + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Pics>> call, @NonNull Throwable t) {
                setaErro();
                Log.i("Pics", "onFailure" + t.getMessage());
            }
        });
    }

    private void setaErro() {
        Toast.makeText(getContext(), R.string.houve_erro, Toast.LENGTH_SHORT).show();
    }


}