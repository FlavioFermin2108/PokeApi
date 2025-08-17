package pe.idat.pokeapi.pokeapi;


import pe.idat.pokeapi.models.PokemonDetalle;
import pe.idat.pokeapi.models.PokemonRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PokeApiServices {
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);

    @GET
    Call<PokemonDetalle> obtenerDetallePokemon(@Url String url);
}
