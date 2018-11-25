package com.glhf.bomberball;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

public class Maze {

    private String title;
    private int height;
    private int width;
    private Vector2[] positionStart;
    private Vector2 positionEnd;
    private GameObject[][] tab;
    private static Gson gson;

    public Maze() {
        title = "Classic";
        height = 11;
        width = 13;
        positionStart = new Vector2[4];
        positionStart[0]= new Vector2(1,1);
        positionStart[1]= new Vector2(1,10);
        positionStart[2]= new Vector2(12,1);
        positionStart[3]= new Vector2(12,10);
        positionEnd = new Vector2(6,5);
        tab = new GameObject[width][height];
        tab[0][0] = new Bomb(0,0,Textures.get("badlogic"), 1);
        tab[0][1] = new ActiveEnemy(1,0,Textures.get("badlogic"), 3);
        //tab[0][2] = new NumberBombBoost();
        if(gson==null)createGson();
    }


    public void draw(SpriteBatch batch){

    }


    public static Maze fromJsonFile(String filename) {
        Maze m;
        if(gson==null)createGson();
        try {
            m = gson.fromJson(new FileReader(new File(filename)), Maze.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("ERROR : "+e.getMessage());
        }
        return m;
    }

    public static Maze fromJson(String str) {
        return gson.fromJson(str, Maze.class);
    }

    public String toJson(){
        return gson.toJson(this);
    }

    private static void createGson() {
        gson = new GsonBuilder()
                .registerTypeAdapter(GameObject.class, new PropertyBasedInterfaceMarshal())
                .registerTypeAdapter(Bomb.class, new PropertyBasedInterfaceMarshal()).create();
    }

    public static class PropertyBasedInterfaceMarshal implements JsonSerializer<Object>, JsonDeserializer<Object> {
        private static Gson gson = new Gson();
        private static final String CLASS_META_KEY = "_class";

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObj = (JsonObject) jsonElement;
            String className = jsonObj.get(CLASS_META_KEY).getAsString();
            try {
                Class<?> clz = Class.forName(className);
                return gson.fromJson(jsonElement, clz);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }


        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            System.out.println(object);
            JsonElement jsonEle = gson.toJsonTree(object);
            jsonEle.getAsJsonObject().addProperty(CLASS_META_KEY, object.getClass().getCanonicalName());
            return jsonEle;
        }

    }
}
