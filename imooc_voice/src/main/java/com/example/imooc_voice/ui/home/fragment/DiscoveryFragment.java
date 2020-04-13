package com.example.imooc_voice.ui.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.imooc_voice.R;
import com.example.lib_image_loader.loader.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoveryFragment extends Fragment {

    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.iv_normal)
    ImageView ivNormal;
    @BindView(R.id.iv_circle)
    ImageView ivCircle;
    @BindView(R.id.iv_blur)
    ImageView ivBlur;
    Unbinder unbinder;
    @BindView(R.id.ll_body)
    LinearLayout llBody;

    public static DiscoveryFragment newInstance() {

        Bundle args = new Bundle();

        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        String detailIvUrl = "http://cdn.zjzapp.com/image/d9872ebc-d151-391c-9196-7b7547838f43.png";
        ImageLoader.getInstance().loadImage(detailIvUrl,llBody, ivDetail, R.drawable.loading_01);
        String imageUrl = "http://cdn.zjzapp.com/5ce3fbcc-3111-3150-85a9-6c2256676a45.jpg";
        ImageLoader.getInstance().loadImage(imageUrl,ivNormal,R.drawable.loading_01);
        ImageLoader.getInstance().loadCircleImage(imageUrl,ivCircle,R.drawable.loading_01);
        ImageLoader.getInstance().loadBlurImage(imageUrl,ivBlur,R.drawable.loading_01);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
