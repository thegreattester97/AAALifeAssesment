package com.api.utils;


import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
	

    public static Object[][] loadJson(String path) throws Exception {
        String json = new String(Files.readAllBytes(Paths.get(path)));
        JSONArray arr = new JSONArray(json);
        List<Object[]> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            list.add(new Object[]{ arr.getJSONObject(i) });
        }
        return list.toArray(new Object[0][]);
    }
    public static Object[][] filterJson(String path, String prefix) throws Exception {
        String json = new String(Files.readAllBytes(Paths.get(path)));
        JSONArray array = new JSONArray(json);
        // Parse Through - Iterate 
        List<Object[]> filtered = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            if (obj.getString("type").startsWith(prefix)) {
                filtered.add(new Object[]{obj});
            }
        }
        return filtered.toArray(new Object[0][]);
    }
}