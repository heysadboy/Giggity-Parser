/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Varun Kumar
 */
public class Locations {

    ArrayList<String> locations = new ArrayList<String>();

    public Locations() throws ParseException {
        String FILENAME = "microlocations";
        String jsonData = "";

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {

                jsonData += sCurrentLine + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        int i;

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jsonData);
        JSONArray array = (JSONArray) obj;

        for (i = 0; i < array.size(); i++) {

            JSONObject location = (JSONObject) array.get(i);
            String name = location.get("name").toString();
            locations.add(name);
        }

    }

    ArrayList<String> getLocations() {
        return locations;
    }
}
