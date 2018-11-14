package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException{

        Sandwich parsedSandwichData = new Sandwich();
        JSONObject sandwichJSON = new JSONObject(json);
        JSONObject sandwichName = sandwichJSON.getJSONObject("name");
        JSONArray sandwichOtherNames = sandwichName.getJSONArray("alsoKnownAs");
        JSONArray sandwichIngredients = sandwichJSON.getJSONArray("ingredients");
        parsedSandwichData.setMainName(sandwichName.optString("mainName"));
        parsedSandwichData.setDescription(sandwichJSON.optString("description"));
        parsedSandwichData.setPlaceOfOrigin("placeOfOrigin");
        parsedSandwichData.setImage(sandwichJSON.optString("image"));
        List<String> otherNames = null;
        List<String> ingredients = null;
        int ingredientsLength = sandwichIngredients.length();
        int otherNamesLength = sandwichOtherNames.length();
        if(otherNamesLength > 0){
            otherNames = new ArrayList<>();
            for(int i = 0 ; i < otherNamesLength ; i++){
                otherNames.add(sandwichOtherNames.optString(i));
            }
        }
        if(ingredientsLength > 0){
            ingredients = new ArrayList<>();
            for(int i = 0 ; i < ingredientsLength ; i++){
                ingredients.add(sandwichIngredients.optString(i));
            }
        }

        parsedSandwichData.setAlsoKnownAs(otherNames);
        parsedSandwichData.setIngredients(ingredients);

        Log.v("Sandwich" , parsedSandwichData.getMainName());
        Log.v("Sandwich" , parsedSandwichData.getDescription());

        return parsedSandwichData;
    }
}
