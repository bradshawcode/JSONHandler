package JSONHANDLER;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException; //Loads all libraries required.
import org.json.simple.JSONArray;


public class Main {

    public static void loopThroughArray(ArrayList<Object> array){
        for (Object arrayobj : array){ 
            System.out.println(arrayobj);
        }
    }

    public static void decodeJson(){
        JSONParser parser = new JSONParser();
        String s = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";

        try{
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray)obj;

            System.out.println("The 2nd element of array");
            System.out.println(array.get(1));
            System.out.println();

            JSONObject obj2 = (JSONObject)array.get(1);
            System.out.println("Field \"1\"");
            System.out.println(obj2.get("1"));

            s = "{}";
            obj = parser.parse(s);
            System.out.println(obj);

            s = "[5,]";
            obj = parser.parse(s);
            System.out.println(obj);

            s = "[5,,2]";
            obj = parser.parse(s);
            System.out.println(obj);
        }catch(ParseException pe) {

            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }
    }

    public static void main(String[] args) throws IOException {
        //Object bob = 2; //Holds anything, may be good with a arraylist or dic.

        ArrayList<String> numbers = new ArrayList<String>();
        for (int i = 1; i < 150000; i++){
            numbers.add(Integer.toString(i));
        }
        
        //Makes the json.
        JSONObject obj = new JSONObject(); //Json.
        obj.put("Name", "Mike");
        obj.put("Age", "17");
        obj.put("Values", String.valueOf(numbers));
        //saves the json string to a json file
        JsonHandle.saveJson(obj, "savedFile.json"); 
        StringWriter out = new StringWriter(); //gets a new string.
        //System.out.println(out); //Should print nothing, empty string.
        obj.writeJSONString(out); // writes the json data to the string.
        //System.out.println("The data inside the saved json file are: " + out); // Prints out the data.

        String jsonString = JsonHandle.returnLoadedJsonString("loadFile.json");
        //Get each json tag:
        ArrayList<String> jsonTag = new ArrayList<String>();
        jsonTag = JsonHandle.getTag(jsonString);
        System.out.println("Tags: " + jsonTag);

        jsonString = JsonHandle.returnLoadedJsonString("savedFile.json");
        //Get each json tag:
        jsonTag = new ArrayList<String>();
        jsonTag = JsonHandle.getTag(jsonString);
        System.out.println("Tags: " + jsonTag);
        }
    }
