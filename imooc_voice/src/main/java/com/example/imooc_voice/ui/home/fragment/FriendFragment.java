package com.example.imooc_voice.ui.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imooc_voice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {

    public static FriendFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FriendFragment fragment = new FriendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

}
