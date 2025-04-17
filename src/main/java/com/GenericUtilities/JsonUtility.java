package com.GenericUtilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtility {
	public String FetchDataFromJsonFile(String key) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(".src/test/resources/Vtiger_Json.json"));
		JSONObject js=(JSONObject)obj;
		String data =js.get(key).toString();
		return data;
	}
	
}
