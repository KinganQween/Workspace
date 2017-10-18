package com.edu.nbl.eshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.nbl.eshop.R;
import com.pkmmte.view.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MeFragment extends Fragment {
    @BindView(R.id.iv_user_head)//用户头像
    CircularImageView iv_UserHead;
    @BindView(R.id.tv_login)//"登录|注册"
    TextView tv_Login;
    @BindView(R.id.tv_person_info)//个人信息
    TextView tv_PersonInfo;
    @BindView(R.id.tv_person_goods)//我的商品
    TextView tv_PersonGoods;
    @BindView(R.id.tv_person_upload)//商品上传
    TextView tv_PersonUpload;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}