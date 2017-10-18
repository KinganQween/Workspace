package com.zhuoxin.easyshop.user.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.commons.ActivityUtils;
import com.zhuoxin.easyshop.components.ProgressDialogFragment;
import com.zhuoxin.easyshop.main.MainActivity;
import com.zhuoxin.easyshop.user.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText et_Username;
    @BindView(R.id.et_pwd)
    EditText et_Pwd;
    @BindView(R.id.btn_login)
    Button btn_Login;
    @BindView(R.id.tv_register)
    TextView tv_Register;
    private ActivityUtils activityUtils;
    private String username;//登录用户名
    private String password;//登录密码
    private ProgressDialogFragment dialogFragment;//登录进度条
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);
        //初始化
        init();
    }
    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }
    //初始化
    private void init() {
        //初始化toolbar的返回键
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //监听EditText里内容
        et_Username.addTextChangedListener(textWatcher);//监听用户名
        et_Pwd.addTextChangedListener(textWatcher);//监听密码
    }
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        //当文本内容改变时监听
        @Override
        public void afterTextChanged(Editable editable) {
            username=et_Username.getText().toString();//获取用户名
            password=et_Pwd.getText().toString();//获取密码
            //判断用户名和密码是否为空
            //(TextUtils.isEmpty(username)||TextUtils.isEmpty(password))：
            // 表示用户名或密码任意一个为空返回true
            boolean canLogin=(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password));
            btn_Login.setEnabled(canLogin);//设置按钮可点击
        }
    };

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login://点击登录
                presenter.login(username,password);//调用业务类的登录方法
                break;
            case R.id.tv_register:
                //跳转到注册页面
                activityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }

    @Override
    public void showPrb() {
        if (dialogFragment==null){//当为空时
            dialogFragment=new ProgressDialogFragment();//构建实例
        }
        if (dialogFragment.isVisible()){
            return;//显示过了不用再显示了
        }
        dialogFragment.show(getSupportFragmentManager(),"dialogFreagment");
    }

    @Override
    public void hidePrb() {
        dialogFragment.dismiss();//隐藏progressbar

    }

    @Override
    public void loginSuccess() {
        activityUtils.startActivity(MainActivity.class);//跳转到市场页面
        finish();
    }
    @Override
    public void loginFailed() {//登录失败 置空用户名和密码
        et_Username.setText("");
        et_Pwd.setText("");
    }
    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);//吐司
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //点击返回键  销毁当前页
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
