package com.example.api_mvc.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_mvc.ApiService;
import com.example.api_mvc.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteFragment extends Fragment {

    private TextView resposta;
    private Retrofit retrofit;

    public DeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setaRetrofit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        resposta = view.findViewById(R.id.txt_resposta_delete);
        setarBotao(view);

        return view;
    }

    private void setarBotao(View view){
        Button button = view.findViewById(R.id.bt_delete_fragment);
        EditText id = view.findViewById(R.id.editext_id_fragment_delete);


        button.setOnClickListener(view1 -> {
            String txt_id = id.getText().toString();
            if (txt_id.isEmpty()){
                Toast.makeText(getContext(), "Preencha o Id", Toast.LENGTH_SHORT).show();
                return;
            }
            deletarPostagem(txt_id);
        });
    }

    private void deletarPostagem(String id){
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Void> call = apiService.deletePostagem(Integer.parseInt(id));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    String s = "Código: " + response.code() + " " + response.message();
                    resposta.setText(s);

                }else {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void setaRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}