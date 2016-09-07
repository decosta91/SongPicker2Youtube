package com.example.songpicker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.songpicker.R;
import com.example.songpicker.utils.ImageLoader.ImageExtractor;
import com.example.songpicker.utils.ImageLoader.ImageFetcher;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StanoevskiDejan on 9/5/2016.
 */
public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    List<SearchResult> list;
    private ImageExtractor mExtractor;
    private ImageFetcher mImageFetcher;
    List<Video> videoList;

    public YoutubeAdapter(Context context) {
        layoutInflater=LayoutInflater.from(context);
        list=new ArrayList<>();
    }

    public void setImageExtractor(ImageExtractor extractor) {
        this.mExtractor = extractor;
    }

    public void setImageFetcher(ImageFetcher mImageFetcher) {
        this.mImageFetcher = mImageFetcher;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.youtube_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchResult searchResult=list.get(position);
        holder.setData(searchResult,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<SearchResult> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void setVideoItems(List<Video> list){
        this.videoList=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ytTitle;
        TextView ytArtist;
        TextView ytViewCount;
        ImageView ytImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ytArtist=(TextView) itemView.findViewById(R.id.ytArtist);
            ytTitle=(TextView)itemView.findViewById(R.id.ytTitle);
            ytViewCount=(TextView)itemView.findViewById(R.id.ytViewCount);
            ytImage=(ImageView)itemView.findViewById(R.id.ytImageView);
        }

        public void setData(SearchResult searchResult, int position) {
            ytTitle.setText(searchResult.getSnippet().getTitle());
            ytArtist.setText(searchResult.getSnippet().getThumbnails().getMedium().getUrl());
          //  searchResult
          //  mExtractor.loadImage(searchResult.getSnippet().getThumbnails().getDefault().getUrl(),this.albumArt);
            mImageFetcher.loadImage(searchResult.getSnippet().getThumbnails().getMedium().getUrl(),this.ytImage);
            for(Video video:videoList) {
                if(video.getId().equals(searchResult.getId().getVideoId()))
                ytViewCount.setText(video.getContentDetails().getDuration());
            }

        }
    }
}
