package com.glhf.bomberball;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;

public class Config {
    private static Config config;
    private static Gson gson;
    private static RandomAccessFile file;
    private HashMap<Integer, String> inputs;

    public Config() {
        inputs = new HashMap<Integer, String>();
    }

    public static void load(){
        gson = new Gson();
        try {
            config = gson.fromJson(new FileReader(new File(Constants.PATH_CONFIG_FILE)), Config.class);
            file = new RandomAccessFile(new File(Constants.PATH_CONFIG_FILE), "rw");

        } catch (FileNotFoundException e)   { e.printStackTrace();
        }
    }

    public static HashMap<Integer, String> getInputs(){
        return config.inputs;
    }

    public static void setInput(int k, String m){
        config.inputs.put(k, m);
        try {
            file.seek(0);
            file.writeUTF(gson.toJson(config));
        } catch (IOException e) { e.printStackTrace(); }
    }
}