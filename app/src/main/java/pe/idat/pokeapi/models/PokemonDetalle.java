package pe.idat.pokeapi.models;

import java.util.List;

public class PokemonDetalle {
    private int id;
    private String name;
    private int height;
    private int weight;
    private Sprites sprites;
    private List<Type> types;

    public PokemonDetalle(int id, String name, int height, int weight, Sprites sprites, List<Type> types) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.sprites = sprites;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public static class Sprites {
        private String front_default;

        public String getFrontDefault() {
            return front_default;
        }

        public void setFrontDefault(String front_default) {
            this.front_default = front_default;
        }
    }

    public static class Type {
        private TypeName type;

        public TypeName getType() {
            return type;
        }

        public void setType(TypeName type) {
            this.type = type;
        }
    }

    public static class TypeName {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
