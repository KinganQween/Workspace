package com.edu.nbl.eshop.Activity.me.persongoods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.edu.nbl.eshop.Activity.shop.ShopAdapter;
import com.edu.nbl.eshop.Activity.shop.ShopView;
import com.edu.nbl.eshop.R;
import com.edu.nbl.eshop.commons.ActivityUtils;
import com.edu.nbl.eshop.model.GoodInfo;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class PersonGoodsActivity extends MvpActivity<ShopView,PersonGoodsPresenter> implements ShopView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout mRefreshLayout;
    @BindView(R.id.tv_load_error)
    TextView mTvLoadError;
    @BindView(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    String load_more_end;
    private String type="";//商品类型，空值为全部商品
    private ActivityUtils mActivityUtils;
    private ShopAdapter mShopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_goods);
        ButterKnife.bind(this);
        mActivityUtils=new ActivityUtils(this);
        //增加toolbar上的返回键
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //给Menu菜单里的item设置监听器
        mToolbar.setOnMenuItemClickListener(mOnMenuItemClickListener);
        //recyclerView的初始化
        initView();

    }

    @NonNull
    @Override
    public PersonGoodsPresenter createPresenter() {
        return null;
    }

    private void initView() {
        mShopAdapter=new ShopAdapter();
        mRecyclerView.setAdapter(mShopAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mShopAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(GoodInfo goodInfo) {
                //跳转到我的商品的详情页
                //从不同的页面进入详情页的状态值，0=从市场页面  1=从我的页面进来
//                Intent intent= GoodsDetailActivity.getStateIntent(PersonGoodsActivity.this,goodInfo.getUuid(),1);
//                startActivity(intent);
            }
        });
    }
//    //初始化PtrFrameLayout
//    //记录刷新时间
//        mRefreshLayout.setLastUpdateTimeRelateObject(this);
//    //设置refereshLayout的背景
//        mRefreshLayout.setBackgroundResource(R.color.recycler_bg);
//    //关闭头布局的时间
//        mRefreshLayout.setDurationToCloseHeader(1500);
//    //设置下拉刷新和上拉加载的监听
//    mRefreshLayout.setPtrHandler(new PtrDefaultHandler2() {
//        @Override
//        public void onLoadMoreBegin(PtrFrameLayout frame) {//上拉加载
//            presenter.loadData(type);//type=""表示所有商品
//        }
//
//        @Override
//        public void onRefreshBegin(PtrFrameLayout frame) {//下拉刷新
//            presenter.refreshData(type);//type=""表示所有商品
//        }
//    });
//}
    //每次进如本页，都要刷新

    @Override
    protected void onStart() {
        super.onStart();
        mRefreshLayout.autoRefresh();//从其他页过来执行此方法
    }
    //实现toolbar右侧menu菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goods_type,menu);
        return true;
    }
    private Toolbar.OnMenuItemClickListener mOnMenuItemClickListener=new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_household://家用
                    presenter.refreshData("household");
                    break;
                case R.id.menu_electron://电子
                    presenter.refreshData("electron");
                    break;
                case R.id.menu_dress://服饰
                    presenter.refreshData("dress");
                    break;
                case R.id.menu_toy://玩具
                    presenter.refreshData("toy");
                    break;
                case R.id.menu_book://图书
                    presenter.refreshData("book");
                    break;
                case R.id.menu_gift://礼物
                    presenter.refreshData("gift");
                    break;
                case R.id.menu_other://其他
                    presenter.refreshData("other");
                    break;

            }
            return false;
        }
    };
    //添加返回箭头的监听

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();//返回当前页
        }
        return super.onOptionsItemSelected(item);
    }
    //显示刷新
    @Override
    public void showRefresh() {
        mTvLoadError.setVisibility(View.GONE);//没有网的页面
        mTvLoadEmpty.setVisibility(View.GONE);//没有此商品的页面
    }
    //刷新错误
    @Override
    public void showRefreshError(String msg) {
        mRefreshLayout.refreshComplete();//停止刷新
        if (mShopAdapter.getItemCount()>0){
            mActivityUtils.showToast(msg);
            return;
        }
        //表示没有数据
        mTvLoadEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRefreshEnd() {
        mRefreshLayout.refreshComplete();//刷新结束
        mTvLoadEmpty.setVisibility(View.VISIBLE);//显示一个商品也没有的页面
    }

    @Override
    public void hideRefresh() {
        mRefreshLayout.refreshComplete();//刷新结束
    }

    @Override
    public void addRefreshData(List<GoodInfo> data) {
        mTvLoadEmpty.setVisibility(View.GONE);
        mShopAdapter.clear();
        if (data!=null){
            mShopAdapter.addData(data);
        }
    }

    //正在加载
    @Override
    public void showLoadMoreLoading() {
        mTvLoadError.setVisibility(View.GONE);//没有网
        mTvLoadEmpty.setVisibility(View.GONE);//没有此商品
    }
    //上拉加载错误
    @Override
    public void showLoadMoreError(String msg) {
        mRefreshLayout.refreshComplete();
        if (mShopAdapter.getItemCount()>0){
            mActivityUtils.showToast(msg);
            return;
        }
        mTvLoadError.setVisibility(View.VISIBLE);//网络加载错误页面显示
    }
    //加载结束
    @Override
    public void showLoadMoreEnd() {
        mRefreshLayout.refreshComplete();
        mActivityUtils.showToast(load_more_end);//没有更多数据
    }

    //隐藏加载
    @Override
    public void hideLoadMore() {
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void addMoreData(List<GoodInfo> data) {
        mShopAdapter.addData(data);//添加要加载的数据
        mRefreshLayout.refreshComplete();//停止刷新
    }
    //吐司信息
    @Override
    public void showMessage(String msg) {
        mActivityUtils.showToast(msg);
    }
}
