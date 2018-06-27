package com.example.deepak.uberassignment.repository;

import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;

import com.example.deepak.uberassignment.loader.FetchImageDataAsyncTask;
import com.example.deepak.uberassignment.model.ImageItem;
import com.example.deepak.uberassignment.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ImageRepository {

    private MutableLiveData<List<ImageItem>> liveImageList;


    public ImageRepository() {
        liveImageList = new MutableLiveData<>();
        List<ImageItem> imgList = new ArrayList<>();
        liveImageList.postValue(imgList);

    }


    public MutableLiveData<List<ImageItem>> getImgLiveList(String text) {
        if(liveImageList.getValue() == null || liveImageList.getValue().size() == 0) {
            //AsyncTask will be called only once for configuration changed.. and data will be saved till app got killed.
            //We can use DB as well to store the data.
            if(!TextUtils.isEmpty(text)) {
                String url = Constants.API_HOST+text;
                new FetchImageDataAsyncTask(url + text, text, liveImageList, true).execute();
            }
        }
        return liveImageList;
    }

    public void loadMoreData(String text, boolean loadNewData, int pageNum) {
//i can append the page num in url here.. to fetch the next page...as well
        String url = Constants.API_HOST+text+"&page="+pageNum;
        new FetchImageDataAsyncTask(url, text, liveImageList, loadNewData).execute();
    }



}
