package com.edu.nbl.eshop.Activity.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.nbl.eshop.R;
import com.edu.nbl.eshop.commons.ActivityUtils;
import com.edu.nbl.eshop.model.GoodInfo;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public  class ShopFragment extends MvpFragment<ShopView,ShopPresenter> implements ShopView{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;//展示商品列表
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @BindView(R.id.tv_load_error)
    TextView tvLoadError;//错误提示
    Unbinder unbinder;
    private ActivityUtils activityUtils;//持有Fragment的实例
    private ShopAdapter shopAdapter;//商品列表适配器
    //商品类型 ，获取全部商品时为空
    private String pageType="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {//创建Fragment的实例
        super.onCreate(savedInstanceState);
        activityUtils=new ActivityUtils(this);//持有Fragment的实例
        shopAdapter=new ShopAdapter();//构建适配器
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public ShopPresenter createPresenter() {
        return new ShopPresenter();//获取ShopPresenter的实例
    }
    //此方法执行在onCreateView后面
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();//初始化一些视图
    }

    private void initView() {
        //初始化RecyclerView
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));//两列的网格视图
        shopAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {//item点击效果
            @Override
            public void onItemClickListener(GoodInfo goodInfo) {
                // TODO: 2017/7/17 0017 跳转到详情页
                //从不同的页面进入详情页的状态值，0=从市场页面  1=从我的页面进来
//                Intent intent=GoodsDetailActivity.getStateIntent(getContext(),goodInfo.getUuid(),0);
//                startActivity(intent);
            }
        });
        recyclerview.setAdapter(shopAdapter);//设置适配器
        //使用本对象作为key，用来记录上一次刷新的事件,如果两次下拉刷新间隔太近，不会触发刷新放
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setBackgroundResource(R.color.recycler_bg);//设置刷新背景色
        refreshLayout.setDurationToCloseHeader(1500);//关闭头布局的时间
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {//上拉加载
                presenter.loadData(pageType);//加载更多时触发  type=""
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {//下拉刷新
                presenter.refreshData(pageType);//刷新时触发 type=""
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //一来就要自动刷新
        if (shopAdapter.getItemCount()==0) {//没数据才刷新
            refreshLayout.autoRefresh();//自动刷新
        }
    }
    @OnClick(R.id.tv_load_error)
    public void onClick(){
        refreshLayout.autoRefresh();//自动刷新
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //视图接口相关实现
    @Override
    public void showRefresh() {
        tvLoadError.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshError(String msg) {
        //停止刷新
        refreshLayout.refreshComplete();
        if (shopAdapter.getItemCount()>0){
            activityUtils.showToast(msg);//吐错误信息
            return;
        }
        tvLoadError.setVisibility(View.GONE);//隐藏网络错误布局
    }

    @Override
    public void showRefreshEnd() {
        activityUtils.showToast(getResources().getString(R.string.refresh_more_end));//没有新商品的
        refreshLayout.refreshComplete();//刷新结束
    }

    @Override
    public void hideRefresh() {
        refreshLayout.refreshComplete();//停止刷新
    }
    //下拉刷新添加数据
    @Override
    public void addRefreshData(List<GoodInfo> data) {
        //数据清空
        shopAdapter.clear();
        if (data!=null){
            shopAdapter.addData(data);//将数据添加到适配器集合中
        }
    }
    //上拉加载
    @Override
    public void showLoadMoreLoading() {
        tvLoadError.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMoreError(String msg) {
        //停止加载
        refreshLayout.refreshComplete();
        if (shopAdapter.getItemCount()>0){
            activityUtils.showToast(msg);//吐错误信息
            return;
        }
        tvLoadError.setVisibility(View.GONE);//隐藏网络错误布局
    }

    @Override
    public void showLoadMoreEnd() {
        refreshLayout.refreshComplete();//加载结束
    }

    @Override
    public void hideLoadMore() {
        refreshLayout.refreshComplete();//加载结束
    }
    //上拉加载更多数据
    @Override
    public void addMoreData(List<GoodInfo> data) {
        shopAdapter.addData(data);//每次添加20条商品
    }
    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }
}
