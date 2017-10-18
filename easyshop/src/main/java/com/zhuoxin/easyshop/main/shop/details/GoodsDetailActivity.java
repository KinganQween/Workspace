package com.zhuoxin.easyshop.main.shop.details;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.apphx.model.repository.DefaultLocalUsersRepo;
import com.feicuiedu.apphx.presentation.chat.HxChatActivity;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.commons.ActivityUtils;
import com.zhuoxin.easyshop.commons.ConvertUser;
import com.zhuoxin.easyshop.components.ProgressDialogFragment;
import com.zhuoxin.easyshop.model.AvaterLoadOptions;
import com.zhuoxin.easyshop.model.CachePreferences;
import com.zhuoxin.easyshop.model.GoodsDetail;
import com.zhuoxin.easyshop.model.User;
import com.zhuoxin.easyshop.network.EasyShopApi;
import com.zhuoxin.easyshop.user.login.LoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class GoodsDetailActivity extends MvpActivity<GoodsDetailView,GoodsDetailPresenter> implements GoodsDetailView{
    public static final String UUID = "uuid";//商品标示码
    //从不同的页面进入详情页的状态值，0=从市场页面  1=从我的页面进来
    public static final String STATE = "state";
    @BindView(R.id.tv_goods_delete)//我的商品页面进入详情页时有删除按钮
    TextView tvGoodsDelete;
    @BindView(R.id.toolbar)//工具条
    Toolbar toolbar;
    @BindView(R.id.viewpager)//商品页面轮播图
    ViewPager viewpager;
    @BindView(R.id.indicator)//轮播图下面的圆点指示器
    CircleIndicator indicator;
    @BindView(R.id.tv_detail_name)//商品名称
    TextView tvDetailName;
    @BindView(R.id.tv_detail_price)//商品价格
    TextView tvDetailPrice;
    @BindView(R.id.tv_detail_master)//发布者
    TextView tvDetailMaster;
    @BindView(R.id.tv_detail_describe)//商品描述
    TextView tvDetailDescribe;
    @BindView(R.id.btn_detail_message)//发消息按钮
    Button btnDetailMessage;
    @BindView(R.id.tv_goods_error)//商品删除，找不到此商品显示的错误页面
    TextView tvGoodsError;
    private String str_uuid;//商品的uuid
    private int state;//从哪个页面过来的
    private ArrayList<ImageView> list;//存放轮播图图片的ImageView集合
    private ArrayList<String> list_uri;//存放图片路径的集合
    private GoodsDetailAdapter adapter;//构建轮播图的适配器
    private ActivityUtils activityUtils;//工具类  存储activity和吐司
    private User goods_user;//获取商品详情里的用户
    private ProgressDialogFragment progressDialogFragment;//显示圆形progressbar
    public static Intent getStateIntent(Context context, String uuid, int state) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(UUID, uuid);
        //从不同的页面进入详情页的状态值，0=从市场页面  1=从我的页面进来
        intent.putExtra(STATE, state);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        //初始化
        activityUtils=new ActivityUtils(this);
        //添加返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list=new ArrayList<>();
        list_uri=new ArrayList<>();
        adapter=new GoodsDetailAdapter(list);
        adapter.setOnItemClickListener(new GoodsDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                // TODO: 2017/7/19 0019 点击图片跳转到只显示图片的商品详情页
                Intent intent=GoodsDetailInfoActivity.getStartIntent(GoodsDetailActivity.this,list_uri);
                startActivity(intent);
            }
        });
        viewpager.setAdapter(adapter);//设置适配器
        init();
    }
    @NonNull
    @Override
    public GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter();//使用mosby.mvp框架创建GoodsDetailPresenter实例
    }

    private void init() {
        //获取uuid
        str_uuid=getIntent().getStringExtra(UUID);
        //获取是从哪个页面过来的
        //从不同的页面进入详情页的状态值，0=从市场页面  1=从我的页面进来
        state=getIntent().getIntExtra(STATE,0);
        if (state==1){//1=来自我的页面
            tvGoodsDelete.setVisibility(View.VISIBLE);//显示"删除"按钮
            btnDetailMessage.setVisibility(View.GONE);//隐藏"发消息"按钮
        }
        // TODO: 2017/7/19 0019 通过presenter获取商品详情信息
        presenter.getData(str_uuid);//使用presenter处理业务
    }
    @OnClick({R.id.tv_goods_delete, R.id.btn_detail_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_delete:
                //弹出一个Dialog 进行删除商品的操作
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle(R.string.goods_title_delete);//商品删除
                builder.setMessage(R.string.goods_info_delete);//是否删除该商品？
                //设置确定和取消的监听
                //删除
                builder.setPositiveButton(R.string.goods_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            presenter.delete(str_uuid);//根据商品的uuid来删除商品
                    }
                });
                //取消
                builder.setNegativeButton(R.string.popu_cancle,null);
                //显示Dialog
                builder.create().show();//创建并显示Dialog
                break;
            //如果没有登录进入LoginActivity，登陆后进入商家聊天页面
            case R.id.btn_detail_message://发消息按钮
                if (CachePreferences.getUser().getName()==null){//没有登录过,跳转登录页面
                    activityUtils.startActivity(LoginActivity.class);
                    return;
                }
                //自己不能给自己发消息
                if (goods_user.getHx_Id().equals(CachePreferences.getUser().getHx_Id())){
                    activityUtils.showToast("这个商品是在自己发布的哦!");
                    return;
                }
                //存储商家
                DefaultLocalUsersRepo.getInstance(this).save(ConvertUser.convert(goods_user));
                //跳转到环信聊天页面
                startActivity(HxChatActivity.getStartIntent(GoodsDetailActivity.this,goods_user.getHx_Id()));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){//返回箭头的id
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //试图借相关实现
    @Override
    public void showProgress() {//显示圆形进度
        if (progressDialogFragment==null){//如果progressDialogFragment为空，构建progressDialogFragment实例
            progressDialogFragment=new ProgressDialogFragment();
        }
        if (progressDialogFragment.isVisible()){//如果progressDialogFragment显示,直接返回
            return;
        }
        progressDialogFragment.show(getSupportFragmentManager(),"fragment_progress_dialog");
    }
    @Override
    public void hideProgress() {//销毁progressbar
        progressDialogFragment.dismiss();
    }
    //放置图片地址
    @Override
    public void setImageData(ArrayList<String> arrayList) {
        list_uri=arrayList;//图片地址的集合
        for(int i=0;i<arrayList.size();i++){//根据有几个图片地址构建几个ImageView
            ImageView view=new ImageView(this);
            //view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //http://wx.feicuiedu.com:9094//images/D3228118230A430B77/5606FF8F1FCDC34144907D/F99E38F09A.JPEG
            ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL+list_uri.get(i),view, AvaterLoadOptions.build_item());
            //添加到图片控件的集合中
            list.add(view);//viewpager适配器中的ImageView类型的集合
        }
        adapter.notifyDataSetChanged();//更新适配器
        //创建圆点指示器
        indicator.setViewPager(viewpager);

    }
    //将用户名和商品描述等信息放到对应的控件上
    @Override
    public void setData(GoodsDetail data, User goods_user) {
        //数据展示
        this.goods_user=goods_user;//发布此商品的用户信息
        tvDetailName.setText(data.getName());//商品名称
        tvDetailPrice.setText("商品价格:"+getString(R.string.goods_money,data.getPrice()));//2222￥
        tvDetailMaster.setText("发布者:"+goods_user.getNick_name());//发布者的昵称
        tvDetailDescribe.setText("商品描述:\n"+data.getDescription());//商品的描述
    }

    @Override
    public void showError() {
        tvGoodsError.setVisibility(View.VISIBLE);//如果获取数据错误显示错误页面
        toolbar.setTitle("商品过期不存在");
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);//吐司错误信息
    }
    @Override
    public void deleteEnd() {
        finish();
    }
}
