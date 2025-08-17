package pe.idat.pokeapi.models;
import pe.idat.pokeapi.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.bumptech.glide.Glide;

import java.util.List;


import pe.idat.pokeapi.pokeapi.PokeApiServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailActivity extends AppCompatActivity {

    private static final String TAG = "PokemonDetailActivity";

    private TextView nombreTextView, pokemonInfoTextView;
    private TextView numeroTextView;
    private TextView typeTextView;
    private ImageView pokemonImageView;
    private ProgressBar progressBar;
    private CardView cardView;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        // Referencias UI
        nombreTextView = findViewById(R.id.Nombre);
        numeroTextView = findViewById(R.id.Numero);
        typeTextView = findViewById(R.id.Type);
        pokemonImageView = findViewById(R.id.Pokemon);
        pokemonInfoTextView = findViewById(R.id.PokemonInfo);
        progressBar = findViewById(R.id.progressBar);
        cardView = findViewById(R.id.cardView);

        // Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Extras del intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String pokemonName = extras.getString("pokemon_name");
            String pokemonUrl = extras.getString("pokemon_url");

            if (pokemonName != null && pokemonUrl != null) {
                nombreTextView.setText(pokemonName);
                obtenerDetallesPokemon(pokemonUrl);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Error: No se recibió la URL o el nombre del Pokémon.", Toast.LENGTH_LONG).show();
            }
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Error: No se recibieron datos.", Toast.LENGTH_LONG).show();
        }
    }

    private void obtenerDetallesPokemon(String url) {
        PokeApiServices service = retrofit.create(PokeApiServices.class);
        Call<PokemonDetalle> pokemonDetailCall = service.obtenerDetallePokemon(url);

        pokemonDetailCall.enqueue(new Callback<PokemonDetalle>() {
            @Override
            public void onResponse(Call<PokemonDetalle> call, Response<PokemonDetalle> response) {
                progressBar.setVisibility(View.GONE); // ocultar cargando siempre
                if (response.isSuccessful()) {
                    PokemonDetalle pokemonDetail = response.body();
                    if (pokemonDetail != null) {
                        cardView.setVisibility(View.VISIBLE); // mostrar datos

                        numeroTextView.setText("N.º Pokédex: " + pokemonDetail.getId());
                        pokemonInfoTextView.setText("Altura: " + pokemonDetail.getHeight() + " | Peso: " + pokemonDetail.getWeight());

                        // Tipos
                        StringBuilder tipos = new StringBuilder();
                        List<PokemonDetalle.Type> pokemonTypes = pokemonDetail.getTypes();
                        for (int i = 0; i < pokemonTypes.size(); i++) {
                            tipos.append(pokemonTypes.get(i).getType().getName());
                            if (i < pokemonTypes.size() - 1) {
                                tipos.append(", ");
                            }
                        }
                        typeTextView.setText("Tipo: " + tipos.toString());

                        // Imagen
                        Glide.with(PokemonDetailActivity.this)
                                .load(pokemonDetail.getSprites().getFrontDefault())
                                .into(pokemonImageView);
                    }
                } else {
                    Log.e(TAG, "onResponse error: " + response.errorBody());
                    Toast.makeText(PokemonDetailActivity.this, "Error al cargar detalles.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonDetalle> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // ocultar cargando
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(PokemonDetailActivity.this, "Error de red. No se pudieron cargar los detalles.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
