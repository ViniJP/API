package com.example.api_mvc.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.example.api_mvc.R;

// API usada: https://jsonplaceholder.typicode.com/

public class MainActivity extends AppCompatActivity {

    private Button get,create, update, delete;

    // Classe onCreate somente com os metodos que serão executados
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localizaComponentesTela();

        //criaFragment("get");

        criaListenerBotoes();

    }

    // metodo que cria o fragment de acordo com a String do botao passado
    private void criaFragment(String fragment){
        // Interface para interagir com objetos Fragment dentro de uma Activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Para realizar transações de fragmentos na atividade (como adicionar, remover ou substituir um fragmento)
        // usa-se o FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // criação do fragment
        Fragment fragmento = null;
        switch (fragment){
            case "get":
                fragmento = new GetFragment();
                break;
            case "create":
                fragmento = new CreateFragment();
                break;
            case "update":
                fragmento = new UpdateFragment();
                break;
            case "delete":
                fragmento = new DeleteFragment();
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
        get.setOnClickListener(view -> criaFragment("get"));

        create.setOnClickListener(view -> criaFragment("create"));

        update.setOnClickListener(view -> criaFragment("update"));

        delete.setOnClickListener(view -> criaFragment("delete"));
    }

    private void localizaComponentesTela() {
        get = findViewById(R.id.bt_get);
        create = findViewById(R.id.bt_create);
        update = findViewById(R.id.bt_update);
        delete = findViewById(R.id.bt_delete);
    }
}