package com.example.api_mvc.Api;

import com.example.api_mvc.Classe.Pics;
import com.example.api_mvc.Classe.Postagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // Get busca dados na web, (endereço da API)
    @GET("photos")
    Call<List<Pics>> recuperarInfo();

    // @POST Atualiza o objeto passado por completo
    @POST("posts")
    Call<Postagem> salvarPostagem(@Body Postagem postagem);

    // Atualiza só o alterado
    // pode-se adicionar um Path no metodo recuperarInfo(@Path int id) em que o nome é a int passada e
    // inserida entre chaves no @GET("photos/{id}")
    // O @body é o objeto a ser alterado (que será convertido em JSON e enviado)
    @PATCH("posts/{id}")
    Call<Postagem> atualizarPostagemPatch(@Path("id")int id, @Body Postagem postagem);

    // Formato em que o objeto é enviado como um url e não json
    @FormUrlEncoded
    @PATCH("posts/{id}")
    Call<Postagem> atualizarPostagemPatch(@Path("id")int id,
                                          @Field("userId") String userId, //  @Field("userId") -> campo no banco de dados
                                          @Field("title") String title, // String title -> tipo e variavel que será passada
                                          @Field("body") String body);

    // Atualiza por completo
    @PUT("posts/{id}")
    Call<Postagem> atualizarPostagem(@Path("id")int id, @Body Postagem postagem);

    // Deleta o objeto por completo e não tem retorno
    @DELETE("posts/{id}")
    Call<Void> deletePostagem(@Path("id") int id);
}
