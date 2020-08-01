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
import com.example.api_mvc.Classe.Postagem;
import com.example.api_mvc.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateFragment extends Fragment {

    private Retrofit retrofit;
    private TextView resultado;

    public UpdateFragment() {
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
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        setarBotao(view);

        resultado = view.findViewById(R.id.txt_resposta_update);



        return view;
    }

    private void setarBotao(View view) {
        Button button = view.findViewById(R.id.bt_update);
        EditText id = view.findViewById(R.id.editext_id_fragment);
        EditText text_id = view.findViewById(R.id.editext_user_id_fragment);
        EditText text_title = view.findViewById(R.id.editext_title_fragment);
        EditText text_body = view.findViewById(R.id.editext_body_fragment);


        button.setOnClickListener(view1 -> {
            String txt_id = id.getText().toString();
            String txt_text_id = text_id.getText().toString();
            String txt_title = text_title.getText().toString();
            String txt_body = text_body.getText().toString();
            if (txt_id.isEmpty()){
                Toast.makeText(getContext(), "Preencha o Id", Toast.LENGTH_SHORT).show();
                return;
            }
            atualizarPostagem(txt_text_id, txt_title, txt_body, txt_id);
        });
    }

    private void atualizarPostagem(String userId, String title, String body ,String id) {
        Postagem postagem = new Postagem();
        postagem.setBody(body.isEmpty() ? null : body);
        postagem.setTitle(title.isEmpty() ? null : title);
        postagem.setUserId(userId.isEmpty() ? null : userId);

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Postagem> call = apiService.atualizarPostagemPatch(Integer.parseInt(id), postagem);
        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if (response.isSuccessful()){
                    Postagem resposta = response.body();
                    String txt_resposta = "Código: "+ response.code() +" /UserId: " + resposta.getUserId() + " /Id: " + resposta.getId()
                            + " /Title: " + resposta.getTitle() + " /Body: " + resposta.getBody();
                    resultado.setText(txt_resposta);
                }else {

                }
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

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