package com.example.api_mvc.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.example.api_mvc.Api.ApiService;
import com.example.api_mvc.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// API usada: https://jsonplaceholder.typicode.com/

public class MainActivity extends AppCompatActivity {

    private Button get,create, update, delete;
    private Retrofit retrofit;
    private ApiService apiService;
    private final String GET_FRAGMENT = "get";
    private final String CREATE_FRAGMENT = "create";
    private final String UPDATE_FRAGMENT = "update";
    private final String DELETE_FRAGMENT = "delete";

    // Classe onCreate somente com os metodos que serão executados
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setaRetrofit();

        localizaComponentesTela();

        criaListenerBotoes();

    }

    // metodo que cria o fragment de acordo com a String do botao passado
    private void criaFragment(String fragment){
        // Interface para interagir com objetos Fragment dentro de uma Activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Para realizar transações de fragmentos na atividade (como adicionar, remover ou substituir um fragmento)
        // usa-se o FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (retrofit == null)
            return;

        // criação do fragment
        Fragment fragmento = null;
        switch (fragment){
            case GET_FRAGMENT:
                fragmento = new GetFragment(apiService);
                break;
            case CREATE_FRAGMENT:
                fragmento = new CreateFragment(apiService);
                break;
            case UPDATE_FRAGMENT:
                fragmento = new UpdateFragment(apiService);
                break;
            case DELETE_FRAGMENT:
                fragmento = new DeleteFragment(apiService);
                break;
        }

        assert fragmento != null;
        // o metodo replace substitui o fragmento passando o id do ViewGroup onde será colocado o fragmento e o fragment em si
        //alem do metodo replace, há o add, remove, attach, dettach
        fragmentTransaction.replace(R.id.fragment_viewGroup, fragmento);
        // é necessário chamar o commit por último para que as transações sejam realizadas
        fragmentTransaction.commit();
    }

    // metodo que cria os botões com listener para os fragments
    private void criaListenerBotoes() {
        get.setOnClickListener(view -> criaFragment(GET_FRAGMENT));

        create.setOnClickListener(view -> criaFragment(CREATE_FRAGMENT));

        update.setOnClickListener(view -> criaFragment(UPDATE_FRAGMENT));

        delete.setOnClickListener(view -> criaFragment(DELETE_FRAGMENT));
    }

    private void localizaComponentesTela() {
        get = findViewById(R.id.bt_get);
        create = findViewById(R.id.bt_create);
        update = findViewById(R.id.bt_update);
        delete = findViewById(R.id.bt_delete);
    }

    private void setaRetrofit() {
        // Retrofit é setado com a url
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.endereco_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Classe ApiService é criada atraves do retrofit
        apiService = retrofit.create(ApiService.class);
    }
}