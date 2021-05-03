package toolbox;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {
	
	public static JSONObject read(String fileName) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(fileName));
			return (JSONObject) obj;
		} catch(FileNotFoundException fe) {
            System.err.println("Can't open json file!");
            fe.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
}
