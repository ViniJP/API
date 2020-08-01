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

public class CreateFragment extends Fragment {

    private Retrofit retrofit;
    private TextView resultado;

    public CreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setaRetrofit();

    }

    private void setarBotao(View view) {
        EditText text_id = view.findViewById(R.id.editext_id);
        EditText text_user_id = view.findViewById(R.id.editext_user_id);
        EditText text_title = view.findViewById(R.id.editext_title);
        EditText text_body = view.findViewById(R.id.editext_body);
        Button botao = view.findViewById(R.id.button_post);
        resultado = view.findViewById(R.id.resultado_post);

        botao.setOnClickListener(view1 -> {
            String id = text_id.getText().toString();
            String userid = text_user_id.getText().toString();
            String title = text_title.getText().toString();
            String body = text_body.getText().toString();

            if (id.isEmpty() || userid.isEmpty() || title.isEmpty() || body.isEmpty()){
                Toast.makeText(getContext(), "Preencha os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            fazerPostagem(id, userid, title, body);
        });
    }

    private void fazerPostagem(String id, String userId, String title, String body) {
        Postagem postagem = new Postagem(id, title, body, userId);

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Postagem> call = apiService.salvarPostagem(Integer.parseInt(id), postagem);

        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if (response.isSuccessful()){
                    Postagem resposta = response.body();
                    String txt_resposta = "Código: "+ response.code() +" /UserId: " +
                            resposta.getUserId() + " /Id: " + resposta.getId()
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        setarBotao(view);

        return view;
    }
}