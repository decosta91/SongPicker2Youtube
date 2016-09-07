package com.example.songpicker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.songpicker.R;
import com.example.songpicker.adapters.YoutubeAdapter;
import com.example.songpicker.utils.ImageLoader.ImageCache;
import com.example.songpicker.utils.ImageLoader.ImageExtractor;
import com.example.songpicker.utils.ImageLoader.ImageFetcher;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by StanoevskiDejan on 9/5/2016.
 */
public class YoutubeFragment extends Fragment {
    YoutubeAdapter youtubeAdapter;
    YouTube youTube;
    int mImageThumbSize;
    ImageExtractor mImageExtractor;
    ImageFetcher mImageFetcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageThumbSize = (int) (getResources().getDisplayMetrics().density *
                getResources().getDimensionPixelSize(R.dimen.album_art_small_art));

        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(getActivity(),
                        getString(R.string.SMALL_THUMBS_CACHE_FOLDER));

        cacheParams.setMemCacheSizePercent(0.125f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageExtractor = new ImageExtractor(getActivity(), mImageThumbSize, getString(R.string.SMALL_THUMBS_CACHE_FOLDER));
        mImageExtractor.setLoadingImage(R.drawable.song_art);
        mImageExtractor.addImageCache(getActivity().getFragmentManager(), cacheParams);
        mImageExtractor.setImageFadeIn(true);

        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize, mImageThumbSize);
        mImageFetcher.setLoadingImage(R.drawable.song_art);
        mImageFetcher.addImageCache(getActivity().getFragmentManager(), cacheParams);
        mImageFetcher.setImageFadeIn(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=
                inflater.inflate(R.layout.recyclerview_songs,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView=(RecyclerView)getView().findViewById(R.id.songs_recyclerview);
        youtubeAdapter=new YoutubeAdapter(getContext());
        youtubeAdapter.setImageExtractor(mImageExtractor);
        youtubeAdapter.setImageFetcher(mImageFetcher);
        recyclerView.setAdapter(youtubeAdapter);

        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        youTube= new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("SongPicker2").build();

        searchYoutube(youTube);
    }

    private void searchYoutube(YouTube youTube) {
        try {
            Log.i("Youtube","Enter in try");
            YouTube.Search.List search = youTube.search().list("id,snippet");
            search.setKey("AIzaSyAZMy8jqMPolJqLJLMCoDkYkYLjihN8J7c");
            search.setQ("music");
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(25l);
            SearchListResponse searchResponse = search.execute();
            Log.i("YoutubeResponse",searchResponse.toString());
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
              //  prettyPrint(searchResultList.iterator(), queryTerm);
            /*    Iterator<SearchResult> searchResultIterator=searchResultList.iterator();
                while (searchResultIterator.hasNext()){
                    SearchResult singleVideo = searchResultIterator.next();
                    ResourceId rId = singleVideo.getId();

                    if (rId.getKind().equals("youtube#video")){
                        Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

                        Log.i("YoutubeInfo","VideoID: "+rId.getVideoId());
                        Log.i("YoutubeInfo","Title: "+singleVideo.getSnippet().getTitle());
                        Log.i("YoutubeInfo","Thumbnail: "+thumbnail.getUrl());
                        Log.i("YoutubeInfo","Thumbnail: "+thumbnail.getUrl());
                    }

                }*/
                youtubeAdapter.addItems(searchResultList);
            }
        } catch (IOException e) {
            Log.e("Youtube",e.toString());
            e.printStackTrace();
        }


    }
}
