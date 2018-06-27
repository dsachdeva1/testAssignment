package com.example.deepak.uberassignment.util;
import android.util.Log;

import com.example.deepak.uberassignment.model.ImageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.deepak.uberassignment.util.Constants.KEY_PAGES;

public class ImageUtils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {

            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                //Read byte from input stream

                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;

                //Write byte from output stream
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static String createUrl(ImageItem imageItem) {

        StringBuilder stringBuilder = new StringBuilder("http://farm");
        stringBuilder.append(imageItem.getFarm())
                .append(".static.flickr.com/")
                .append(imageItem.getServer())
                .append("/")
                .append(imageItem.getId())
                .append("_")
                .append(imageItem.getSecret())
                .append(".jpg");

        Log.d("TAG","Created url is "+stringBuilder.toString());

        return stringBuilder.toString();
        //http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
    }

    public static List<ImageItem> getImgeList(JSONObject jsonPhotos) {
        List<ImageItem> imageItems = new ArrayList<>();

     try {
         JSONArray jsonPhotoArray = jsonPhotos.optJSONArray(Constants.KEY_PHOTO);

         for(int i = 0; i< jsonPhotoArray.length();i++) {
            JSONObject jsonPhotoObj = jsonPhotoArray.getJSONObject(i);
            ImageItem imgItem = getImageItemFromJsonObj(jsonPhotoObj);
            imgItem.setPageNum(jsonPhotos.getInt(Constants.KEY_PAGE));
            imgItem.setTotalPages(jsonPhotos.getInt(KEY_PAGES));
            imageItems.add(imgItem);
            Log.d("TAG", "Image item is " + imgItem.getOwner());
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return imageItems;

    }

    public static ImageItem getImageItemFromJsonObj(JSONObject jsonPhotoObj) throws JSONException {
        ImageItem imgItem = new ImageItem();
        imgItem.setFarm(jsonPhotoObj.getInt(Constants.KEY_FARM));
        imgItem.setId(jsonPhotoObj.getString(Constants.KEY_ID));
        imgItem.setOwner(jsonPhotoObj.getString(Constants.KEY_OWNER));
        imgItem.setSecret(jsonPhotoObj.getString(Constants.KEY_SECRET));
        imgItem.setServer(jsonPhotoObj.getString(Constants.KEY_SERVER));

        return imgItem;
    }
}