package com.example.deepak.uberassignment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.deepak.uberassignment.R;
import com.example.deepak.uberassignment.model.ImageItem;
import com.example.deepak.uberassignment.util.ImageLoader;
import com.example.deepak.uberassignment.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageItemViewHolder> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<ImageItem> imageItemList;
    private static ImageLoader imageLoader;

    public ImageListAdapter(Context context, List<ImageItem> list) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        imageItemList = list;
        if(imageItemList == null) {
            imageItemList = new ArrayList<>();
        }

    imageLoader = ImageLoader.getInstance(mContext);
    }

    @NonNull
    @Override
    public ImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflateView(R.layout.row_image_item, parent, false);

        return new ImageItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    private View inflateView(int resource, ViewGroup root, boolean attachToRoot) {
       return mInflater.inflate(resource, root, attachToRoot);
   }

    @Override
    public void onBindViewHolder(@NonNull ImageItemViewHolder holder, int position) {
        ImageItem messageItem = imageItemList.get(position);
        holder.bindMsgData(messageItem);
    }

    public void setImageItemList(List<ImageItem> msgList) {
        //udpate list item
        if(imageItemList == null) {
            imageItemList = new ArrayList<>();
        }
        int size = imageItemList.size();

        imageItemList = msgList;

        int newSize = imageItemList.size();

        if(newSize > size) {
            notifyItemRangeChanged(size, imageItemList.size());

        } else {
            notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        if(imageItemList == null)
            return 0;
        return imageItemList.size();
    }

    static class ImageItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        public ImageItemViewHolder(View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
        }

        private void bindMsgData(ImageItem messageItem) {
            imageLoader.displayImage(ImageUtils.createUrl(messageItem), imgItem);
        }
    }
}
