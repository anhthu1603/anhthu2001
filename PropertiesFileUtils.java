package com.api.auto.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PropertiesFileUtils {
	//Đường dẫn đến properties files trong folder configuration
	private static String CONFIG_PATH = "./configuration/configs.properties";
	private static String TOKEN_PATH = "./configuration/token.properties";
	
	public static String getPropValue(String key) {
        try{
        	BufferedReader br = new BufferedReader(new FileReader(CONFIG_PATH));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String currentKey = parts[0].trim();
                    String currentValue = parts[1].trim();

                    if (currentKey.equals(key)) {
                        return currentValue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
	
	public static String getTokenValue() {
        try {
        	BufferedReader br = new BufferedReader(new FileReader(TOKEN_PATH));
            return br.readLine();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static void setToken(String token) {
        try {
            FileWriter fileWriter = new FileWriter(TOKEN_PATH);
            fileWriter.write(token);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}