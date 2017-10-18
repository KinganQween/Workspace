package com.zhuoxin.easyshop.main.me.persongoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.commons.ActivityUtils;
import com.zhuoxin.easyshop.main.shop.ShopAdapter;
import com.zhuoxin.easyshop.main.shop.ShopView;
import com.zhuoxin.easyshop.main.shop.details.GoodsDetailActivity;
import com.zhuoxin.easyshop.model.GoodInfo;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class PersonGoodsActivity extends MvpActivity<ShopView,PersonGoodsPresenter> implements ShopView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;//工具条
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;//商品列表视图
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;//上下拉刷新容器
    @BindView(R.id.tv_load_error)
    TextView tv_loadError;//网络错误的TextView
    @BindView(R.id.tv_load_empty)
    TextView tv_loadEmpty;//没有此商品的TextView
    @BindString(R.string.load_more_end)//没有更多数据
    String load_more_end;
    private String type="";//商品类型，空值为全部商品
    private ShopAdapter shopAdapter;//商品适配器 直接复用市场页面里的适配器
    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_goods);
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);
        //增加toolbar上的返回键
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //给Menu菜单里的item设置监听器
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        //recyclerView的初始化
        initView();
    }

    private void initView() {
        shopAdapter=new ShopAdapter();
        recyclerView.setAdapter(shopAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));//两列的网格视图
        shopAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(GoodInfo goodInfo) {
                //跳转到我的商品的详情页
                //从不同的页面进入详情页的状态值，0=从市场页面  1=从我的页面进来
                Intent intent= GoodsDetailActivity.getStateIntent(PersonGoodsActivity.this,goodInfo.getUuid(),1);
                startActivity(intent);
            }
        });
        //初始化PtrFrameLayout
        //记录刷新时间
        refreshLayout.setLastUpdateTimeRelateObject(this);
        //设置refereshLayout的背景
        refreshLayout.setBackgroundResource(R.color.recycler_bg);
        //关闭头布局的时间
        refreshLayout.setDurationToCloseHeader(1500);
        //设置下拉刷新和上拉加载的监听
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {//上拉加载
                presenter.loadData(type);//type=""表示所有商品
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {//下拉刷新
                presenter.refreshData(type);//type=""表示所有商品
            }
        });
    }
    //每次进如本页，都要刷新

    @Override
    protected void onStart() {
        super.onStart();
        refreshLayout.autoRefresh();//从其他页过来执行此方法
    }

    //实现toolbar右侧menu菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goods_type,menu);
        return true;
    }
    //类型共有household、electron、dress、toy、book、gift、other 分别对应家用、电子、服饰、玩具、图书、礼品、其它"
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener=new Toolbar.OnMenuItemClickListener() {
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

    @NonNull
    @Override
    public PersonGoodsPresenter createPresenter() {
        return new PersonGoodsPresenter();//构建Presenter实例
    }
    //添加返回箭头的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();//销毁当前页
        }
        return super.onOptionsItemSelected(item);
    }
    //显示刷新
    @Override
    public void showRefresh() {
        tv_loadError.setVisibility(View.GONE);//没有网的页面
        tv_loadEmpty.setVisibility(View.GONE);//没有此商品的页面
    }
    //刷新错误
    @Override
    public void showRefreshError(String msg) {
        refreshLayout.refreshComplete();//停止刷新
        if (shopAdapter.getItemCount()>0){
            activityUtils.showToast(msg);
            return;
        }
        //表示没有数据
       tv_loadEmpty.setVisibility(View.VISIBLE);
    }
    //刷新结束
    @Override
    public void showRefreshEnd() {
        refreshLayout.refreshComplete();//刷新结束
        tv_loadEmpty.setVisibility(View.VISIBLE);//显示一个商品也没有的页面
    }

    @Override
    public void hideRefresh() {
        refreshLayout.refreshComplete();//刷新结束
    }
    @Override
    public void addRefreshData(List<GoodInfo> data) {
        tv_loadEmpty.setVisibility(View.GONE);
        shopAdapter.clear();
        if (data!=null){
            shopAdapter.addData(data);
        }
    }
    //正在加载
    @Override
    public void showLoadMoreLoading() {
        tv_loadError.setVisibility(View.GONE);//没有网
        tv_loadEmpty.setVisibility(View.GONE);//没有此商品
    }
    //上拉加载错误
    @Override
    public void showLoadMoreError(String msg) {
        refreshLayout.refreshComplete();
        if (shopAdapter.getItemCount()>0){
            activityUtils.showToast(msg);
            return;
        }
        tv_loadError.setVisibility(View.VISIBLE);//网络加载错误页面显示
    }
    //加载结束
    @Override
    public void showLoadMoreEnd() {
        refreshLayout.refreshComplete();
        activityUtils.showToast(load_more_end);//没有更多数据
    }
    //隐藏加载
    @Override
    public void hideLoadMore() {
        refreshLayout.refreshComplete();
    }

    @Override
    public void addMoreData(List<GoodInfo> data) {
        shopAdapter.addData(data);//添加要加载的数据
        refreshLayout.refreshComplete();//停止刷新
    }
    //吐司信息
    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
}
}
