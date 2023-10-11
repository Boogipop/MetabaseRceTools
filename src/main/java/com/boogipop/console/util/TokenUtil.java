package com.boogipop.console.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenUtil {
    public static String GetToken(String url,String protocol)  {
        String properties="";
        if(protocol.equals("https")) {
             properties = HttpUtils.sendHttpsGetRequest(url);
        }
        else {
            properties=HttpUtils.sendHttpGetRequest(url);
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(properties).getAsJsonObject();
        try {
            String token = jsonObject.get("setup-token").getAsString();
            return token;
        }
        catch (Exception error){
            try {
                throw new Exception("JSON PARSE ERROR");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean IsVul(String input){
        String regexPattern = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
           return true;
        } else {
            return false;
        }
    }
}
