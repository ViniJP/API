package com.example.api_mvc.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_mvc.Api.ApiService;
import com.example.api_mvc.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteFragment extends Fragment {

    private TextView resposta;
    private ApiService apiService;
    private EditText id;

    public DeleteFragment(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        resposta = view.findViewById(R.id.txt_resposta_delete);
        id = view.findViewById(R.id.editext_id_fragment_delete);
        setarBotao(view);

        return view;
    }

    private void setarBotao(View view){
        Button button = view.findViewById(R.id.bt_delete_fragment);

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
        Call<Void> call = apiService.deletePostagem(Integer.parseInt(id));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()){
                    String s = "Código: " + (response.code() == 200 ? "200. Deletado com sucesso." : response.code());
                    resposta.setText(s);

                }else {
                    setaErro();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                setaErro();
            }
        });
    }

    private void setaErro() {
        resposta.setText(R.string.houve_erro);
    }
}