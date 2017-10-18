package com.edu.nbl.eshop.Activity.me.personinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.edu.nbl.eshop.R;
import com.edu.nbl.eshop.commons.ActivityUtils;
import com.edu.nbl.eshop.commons.RegexUtils;
import com.edu.nbl.eshop.model.CachePreferences;
import com.edu.nbl.eshop.model.User;
import com.edu.nbl.eshop.model.UserResult;
import com.edu.nbl.eshop.network.EasyShopClient;
import com.edu.nbl.eshop.network.UICallBack;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class NickNameActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;//工具条
    @BindView(R.id.et_nickname)
    EditText et_nickname;//更改昵称的编辑框
    @BindView(R.id.btn_save)
    Button btn_save;//确定按钮
    private ActivityUtils activityUtils;
    private String str_nickname;//存储昵称
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);
        //增加返回键
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //按钮点击效果
    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        //拿到用户输入的昵称
        str_nickname=et_nickname.getText().toString();//获取昵称编辑框里更改的昵称
        if (RegexUtils.verifyNickname(str_nickname)!=RegexUtils.VERIFY_SUCCESS){//昵称不符合规范
           //昵称为中文，字母或数字，长度为1~12，一个中文算2个长度
            activityUtils.showToast(R.string.nickname_rules);
            return;
        }
        init();//保存昵称
    }

    private void init() {
        User user= CachePreferences.getUser();//从本地中获取用户
        user.setNick_name(str_nickname);//设置昵称
        Call call= EasyShopClient.getInstance().uploadUser(user);//使用okhttp3修改昵称
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {//主线程失败的回调
                activityUtils.showToast(e.getMessage());//吐司错误信息
            }

            @Override
            public void onResponseUI(Call call, String body) {//主线程成功的回调
                /*
                {
                "code": 1,
                "msg": "success",
                "data": {
                    "username": "xc62",
                    "nickname": "555",
                    "name": "yt59856b15cf394e7b84a7d48447d16098",
                    "uuid": "0F8EC12223174657B2E842076D54C361",
                    "password": "123456"
                        }
                  }
                 */
                UserResult result=new Gson().fromJson(body,UserResult.class);
                //昵称修改失败
                if (result.getCode()!=1){
                    activityUtils.showToast(result.getMessage());
                    return;//修改失败  onResponseUI结束 后续代码不会执行
                }
                //如果修改成功
                User user=result.getData();
                CachePreferences.setUser(user);//存储更改昵称的用户
                activityUtils.showToast("修改成功");
                onBackPressed();//调用返回键 finish()

            }
        });
    }
}
