package com.edu.nbl.newsprojectdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.nbl.newsprojectdemo.R;
import com.edu.nbl.newsprojectdemo.activity.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 17-10-10.
 */

public class MyFragment extends Fragment {
    @BindView(R.id.my_head)
    CircleImageView mMyHead;
    Unbinder unbinder;
    @BindView(R.id.my_register)
    TextView mMyRegister;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_my, container, false);

        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_head, R.id.my_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_head:
            case R.id.my_register:
                Intent mIntent = new Intent();
                mIntent.setClass(getContext(), RegisterActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
