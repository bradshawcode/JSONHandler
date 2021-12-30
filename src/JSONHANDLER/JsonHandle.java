package JSONHANDLER;
/*
* Name: Michael Bradshaw
* Date: 2020/01/09
* File Name: JsonHandle.java
* Description: Uses json and file manager libraries to save and load a json file.
* */

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException; //Loads all libraries required.
import java.util.ArrayList;
import java.io.*;

//Holds json saving and loading code.
public class JsonHandle { //Date: 2020/01/09

    public static void saveJson(JSONObject jsonFile, String fileName) throws IOException { //Saves the json file. Date: 2020/01/09
        FileWriter file = null;
        try {
            file = new FileWriter(fileName); //gets the filewriter instance.
            StringWriter out = new StringWriter(); //gets a string writer.
            jsonFile.writeJSONString(out); // puts to a json string.
            String jsonText = out.toString(); //gets
            file.write(jsonText); //Writes the file.
            System.out.println("Done Saving Json File: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                assert file != null;
                file.flush(); //Closes and manges
                file.close();


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("Failed to save json file.");
            }
        }

    }

    public static JSONObject loadJson(String fileName) throws IOException { //Date: 2020/01/09
        FileReader loadedFile = null;//Local varables that hold the parser instance and jsonfile
        JSONParser parser = new JSONParser();
        Object jsonFile = null; //Object is for any type of object, eg string, int, ech
        try {
            loadedFile = new FileReader(fileName); //Makes a filereader instance.
            jsonFile = parser.parse(loadedFile); //loads a json file.
            loadedFile.close(); //Closes the file manager after using.
            System.out.println("Done Loading Json File :): " + fileName);
            return (JSONObject) jsonFile; //Returns the json file.
            } catch (FileNotFoundException | ParseException e) { //Looks to see if the file failed if so print his and return null
                System.out.println("Failed to load json file! :(");
                return (JSONObject) null;//returns null if file falls,
            }
    }

    public static String returnLoadedJsonString(String fileName){ //Date: 2021/12/30
        //Load new json file:
        JSONObject newJsonFile;
        try {
            newJsonFile = loadJson(fileName);
            if (newJsonFile != null){ //If the value of the loaded object is not empty.
                String fileString = newJsonFile.toString();
                return fileString;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fileName;  //Returns file name if fail. 
    }
    public static ArrayList<String> getTag(String fileString) { //The string of the file being loaded. Date: 2021/12/30
        char[] allchars = fileString.toCharArray(); //The string converted to char.
        ArrayList<Integer> listID = new ArrayList<Integer>();
        ArrayList<String> jsontag = new ArrayList<String>();
        //fileString.substring(indexi, indexf) Geting a string between to indexs.
        
        int numberChars = 1;
        int starting = 0 ;
        int startingChecked = 0; // Number of chars loops through.

        for (char c : allchars){//Loops through all the characters in the arraylist.
            if (c == '\"')
            { //See if the character is "
                if (!(allchars[numberChars - 1] == '[' || allchars[numberChars - 2] == '\"' || allchars[numberChars-2] == ':' || allchars[numberChars-2] == '[' || allchars[numberChars-1] == ','))
                {
                    if (startingChecked == 0)
                    {
                        starting = numberChars;
                        startingChecked ++;
                    }
                
                    else
                    {
                        if (allchars[numberChars] == ':' && !(allchars[numberChars + 1] == '}' || allchars[numberChars + 1] == ','))
                        {
                            if (numberChars < allchars.length && !(allchars[numberChars] == ',')){
                                listID.add(starting - 1); //Addigngs the index of bounds for the tag.
                                listID.add(numberChars - 1); //edning.
                                startingChecked = 0;}
                        
                        startingChecked = 0;}
                        
                    }
                }
            }
            numberChars++;
        }

        int indexi = 0;
        int indexf = 0;
        int counter = 0;
        int locationofLetter = 0;
        for (int id : listID){
            if (counter == 0){
                indexi = id;
                counter++;
            }
            else{
                indexf = id;
                counter = 0;
                locationofLetter = 0;
                String substring = fileString.substring(indexi, indexf);
                if (substring.contains("]")){
                    for (char character : fileString.substring(indexi, indexf).toCharArray())
                {   
                    if (   (character >= 'a' && character <= 'z')
                        || (character >= 'A' && character <= 'Z'))
                    {
                        locationofLetter = fileString.substring(indexi, indexf).indexOf(String.valueOf(character));
                        break; //Break out of the loop.
                    }
                }
        
                jsontag.add(fileString.substring(indexi + locationofLetter, indexf).replace(",\"","")); 
                }
                else{
                    jsontag.add(fileString.substring(indexi, indexf).replace(",\"","")); 
                }
            } 
        }
        return jsontag; //Returns the json tags.
    }
}