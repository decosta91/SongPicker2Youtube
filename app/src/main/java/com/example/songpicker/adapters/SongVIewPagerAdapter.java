package com.example.songpicker.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.songpicker.FragmentHelp;
import com.example.songpicker.fragments.AllArtistFragment;
import com.example.songpicker.fragments.AllGenresFragment;
import com.example.songpicker.fragments.AllPlaylistFragment;
import com.example.songpicker.fragments.AllSongsFragment;
import com.example.songpicker.fragments.YoutubeFragment;

/**
 * Created by StanoevskiDejan on 6/27/2016.
 */

public class SongVIewPagerAdapter extends FragmentStatePagerAdapter{
  //  private AllSongsFragment allSongsFragment;
   // private AllGenresFragment allGenresFragment;
    //private AllArtistFragment allArtistFragment;
   // private AllPlaylistFragment allPlaylistFragment;
    private YoutubeFragment youtubeFragment;
    private FragmentHelp f1,f2,f3,f4;

    public SongVIewPagerAdapter(FragmentManager fm) {
        super(fm);
       // allArtistFragment=new AllArtistFragment();
        //allSongsFragment=new AllSongsFragment();
        //allPlaylistFragment=new AllPlaylistFragment();
        //allGenresFragment=new AllGenresFragment();
        youtubeFragment=new YoutubeFragment();
        f1=new FragmentHelp();
        f2=new FragmentHelp();
        f3=new FragmentHelp();
        f4=new FragmentHelp();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return f1;//allArtistFragment;
            case 1: return f2;//allSongsFragment;
            case 2: return f3;//allPlaylistFragment;
            case 3: return f4;//allGenresFragment;
            case 4: return youtubeFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }


}
