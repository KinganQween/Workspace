package com.edu.nbl.eshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.nbl.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;


public class ShopFragment extends Fragment{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;//展示商品列表
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @BindView(R.id.tv_load_error)
    TextView tvLoadError;//错误提示
    Unbinder unbinder;

    //商品类型 ，获取全部商品时为空
    private String pageType="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {//创建Fragment的实例
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }




    @Override
    public void onStart() {
        super.onStart();
        //一来就要自动刷新

            refreshLayout.autoRefresh();//自动刷新
        }
    }



