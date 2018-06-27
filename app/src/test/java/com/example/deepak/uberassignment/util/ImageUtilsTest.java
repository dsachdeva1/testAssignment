package com.example.deepak.uberassignment.util;

import android.util.Log;

import com.example.deepak.uberassignment.model.ImageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ImageUtilsTest {

    @Test
    public void createUrlTest() {

        ImageItem imgItem = new ImageItem();
        imgItem.setServer("abc");
        imgItem.setFarm(1);
        imgItem.setSecret("12kd");
        imgItem.setId("23");

        String origUrl = "http://farm1.static.flickr.com/abc/23_12kd.jpg";

        String url = ImageUtils.createUrl(imgItem);

        assertEquals(origUrl, (url));

    }

    @Test
    public void testImageListFromJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.KEY_SERVER, "sda");
            jsonObject.put(Constants.KEY_ID, "34");
            jsonObject.put(Constants.KEY_SECRET, "sda");
            jsonObject.put(Constants.KEY_FARM, 3);
            jsonObject.put(Constants.KEY_OWNER, "34df");


            ImageItem imgItem = ImageUtils.getImageItemFromJsonObj(jsonObject);
            assertEquals(imgItem.getFarm(), 3);
            assertEquals(imgItem.getSecret(), "sda");
            assertEquals(imgItem.getServer(),"sda");
            assertEquals(imgItem.getOwner(), "34df");
            assertEquals(imgItem.getId(), "34");



        } catch (JSONException e) {

        }


    }
}
