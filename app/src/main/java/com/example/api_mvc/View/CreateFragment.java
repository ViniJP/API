package com.example.api_mvc.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_mvc.Api.ApiService;
import com.example.api_mvc.Classe.Postagem;
import com.example.api_mvc.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFragment extends Fragment {

    private ApiService apiService;
    private TextView resultado;
    private EditText text_id, text_user_id, text_title, text_body;
    private Button botao;

    public CreateFragment(ApiService apiService) {
        this.apiService = apiService;
    }

    //Metodo onCreate é chamado antes do onCreateView, inicializa-se os componentes que não precisam da view
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        setarViews(view);

        setarBotao();

        return view;
    }

    private void setarViews(View view) {
        text_id = view.findViewById(R.id.editext_id);
        text_user_id = view.findViewById(R.id.editext_user_id);
        text_title = view.findViewById(R.id.editext_title);
        text_body = view.findViewById(R.id.editext_body);
        botao = view.findViewById(R.id.button_post);
        resultado = view.findViewById(R.id.resultado_post);
    }

    private void setarBotao() {
        botao.setOnClickListener(view1 -> {
            String id = text_id.getText().toString();
            String userid = text_user_id.getText().toString();
            String title = text_title.getText().toString();
            String body = text_body.getText().toString();

            if (id.isEmpty() || userid.isEmpty() || title.isEmpty() || body.isEmpty()){
                Toast.makeText(getContext(), R.string.preencha_campos, Toast.LENGTH_SHORT).show();
                return;
            }

            fazerPostagem(id, userid, title, body);
        });
    }

    private void fazerPostagem(String id, String userId, String title, String body) {
        Postagem postagem = new Postagem(id, title, body, userId);

        Call<Postagem> call = apiService.salvarPostagem(postagem);

        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(@NonNull Call<Postagem> call, @NonNull Response<Postagem> response) {
                if (response.isSuccessful()){
                    Postagem resposta = response.body();
                    assert resposta != null;
                    String txt_resposta = getString(R.string.codigo) + " " + response.code() +
                            "\n" + getString(R.string.userId) + " " + resposta.getUserId() +
                            "\n" +getString(R.string.id) + " " + resposta.getId()
                            + "\n" + getString(R.string.title) + " " + resposta.getTitle() +
                            "\n" + getString(R.string.body) + " " + resposta.getBody();
                    resultado.setText(txt_resposta);
                }else {
                    setaErro();
                    Log.i("erro", "onResponse: erro" + response.code() + " /" + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Postagem> call, @NonNull Throwable t) {
                    setaErro();
                Log.i("erro", "onFailure: erro " + t.getMessage() + " /" + t.getCause());
            }
        });

    }

    private void setaErro() {
        resultado.setText(R.string.houve_erro);
    }
}