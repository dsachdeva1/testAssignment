package com.example.deepak.uberassignment.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.deepak.uberassignment.model.ImageItem;
import com.example.deepak.uberassignment.repository.ImageRepository;

import java.util.List;

public class GetImageViewModel extends ViewModel {

    // Here communication between Repository, View MOdel & asyncTask can be happened through listers..
    private ImageRepository imgRepository;
    public GetImageViewModel() {

        imgRepository = new ImageRepository();

    }

    public MutableLiveData<List<ImageItem>> getImgLiveList(String text) {

    return imgRepository.getImgLiveList(text);
    }

   public void loadMoreData(String text, boolean loadNewData, int page) {
        imgRepository.loadMoreData(text,loadNewData, page+1);
   }


}
