package com.example.deepak.uberassignment.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.deepak.uberassignment.R;
import com.example.deepak.uberassignment.adapter.ImageListAdapter;
import com.example.deepak.uberassignment.model.ImageItem;
import com.example.deepak.uberassignment.ui.EndlessRecyclerViewScrollListener;
import com.example.deepak.uberassignment.viewmodel.GetImageViewModel;

import java.util.List;

public class ImageListFragment extends Fragment {

    public static final String TAG = ImageListFragment.class.getName();


    private EndlessRecyclerViewScrollListener mScrollListener;
    private RecyclerView mRecyclerView;
    private GetImageViewModel getImageViewModel;
    private ImageListAdapter mImageListAdapter;
    private EditText etQuery;
    private Button btnSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_list_fragment, container, false);
        initView(view);

        return view;
    }

    public static ImageListFragment newInstance() {
        return new ImageListFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        setData();

    }


    private void setData() {

        mImageListAdapter = new ImageListAdapter(getActivity(), getImageViewModel.getImgLiveList("").getValue());

        mRecyclerView.setAdapter(mImageListAdapter);
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.reyclerview_message_list);
        etQuery = view.findViewById(R.id.et_query);
        etQuery.setSelection(etQuery.getText().length());
        btnSearch = view.findViewById(R.id.btnSearch);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageViewModel.loadMoreData(etQuery.getText().toString(), true, 0);
                mRecyclerView.getRecycledViewPool().clear();

            }
        });

        mScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("TAG","loading more data"+page);
                getImageViewModel.loadMoreData(etQuery.getText().toString(), false, page);
            }
        };

        mRecyclerView.addOnScrollListener(mScrollListener);

    }

    /**
     * ButtonViewModel, and has observer to listen to changes in data
     */
    private void initViewModel() {

        getImageViewModel = ViewModelProviders.of(this).get(GetImageViewModel.class);
        getImageViewModel.getImgLiveList(etQuery.getText().toString()).observe(this, new Observer<List<ImageItem>>() {
            @Override
            public void onChanged(@Nullable List<ImageItem> msgList) {

                Log.d(TAG, "List updated with " + msgList.toString());

                updateView(msgList);
            }
        });
    }

    private void updateView(final List<ImageItem> msgList) {
        mImageListAdapter.setImageItemList(msgList);

    }
}
