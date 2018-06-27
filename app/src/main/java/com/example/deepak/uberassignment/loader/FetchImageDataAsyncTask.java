package com.example.deepak.uberassignment.loader;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.deepak.uberassignment.model.ImageItem;
import com.example.deepak.uberassignment.util.ImageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.deepak.uberassignment.util.Constants.KEY_PHOTOS;


public class FetchImageDataAsyncTask extends AsyncTask<Void, Void, List<ImageItem>> {

    private static final String TAG = FetchImageDataAsyncTask.class.getName();
    private MutableLiveData<List<ImageItem>> mutableLiveDataMsgList;
    private String mText;
    private String mUrl;
    private ArrayList<ImageItem> imageItems;
    private boolean isLoadNewData;
    private int pageNum;
    private int totalPages;

    public FetchImageDataAsyncTask(String url, String queryText, MutableLiveData<List<ImageItem>> msgList, boolean loadNewData) {
        mutableLiveDataMsgList = msgList;
        mText = queryText;
        List<ImageItem> imageList = msgList.getValue();
        if(imageList != null && imageList.size() > 0) {
            ImageItem imgItem = imageList.get(imageList.size() - 1);
            totalPages = imgItem.getTotalPages();
            pageNum = imgItem.getPageNum()+1;
        }
        mUrl = url;
        imageItems = new ArrayList<>();
        isLoadNewData = loadNewData;
    }

    @Override
    protected List<ImageItem> doInBackground(Void... voids) {
        try {
            if(pageNum > totalPages) {
                return null;
            }
            Log.d("TAG","url is "+mUrl);
            String responseString =  downloadData(mUrl);
            try {
                JSONObject jsonObject = new JSONObject(responseString);
                JSONObject jsonPhotos = jsonObject.optJSONObject(KEY_PHOTOS);

                imageItems.addAll(ImageUtils.getImgeList(jsonPhotos));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("TAG","response is "+responseString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageItems;
    }

    @Override
    protected void onPostExecute(List<ImageItem> imageItemsList) {
        super.onPostExecute(imageItemsList);

        if(imageItemsList == null)
            return;

        if (mutableLiveDataMsgList.hasActiveObservers()) {
            List<ImageItem> origList = mutableLiveDataMsgList.getValue();

            if(isLoadNewData) {
                origList.clear();
            }
                origList.addAll(imageItemsList);

            mutableLiveDataMsgList.postValue(origList);
        }
    }


    private String downloadData(String urlString) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

}
