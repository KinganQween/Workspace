package com.edu.nbl.eshop.User.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.edu.nbl.eshop.Activity.MainActivity;
import com.edu.nbl.eshop.R;
import com.edu.nbl.eshop.User.register.RegisterActivity;
import com.edu.nbl.eshop.commons.ActivityUtils;
import com.edu.nbl.eshop.commons.RegexUtils;
import com.edu.nbl.eshop.components.ProgressDialogFragment;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    private Unbinder mBind;
    private ProgressDialogFragment mDialogFragment;
    private ActivityUtils mActivityUtils;
    private  String username;
    private String userpsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mBind = ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEtUsername.addTextChangedListener(textListener);
        mEtPwd.addTextChangedListener(textListener);
        mActivityUtils=new ActivityUtils(this);
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    TextWatcher textListener=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            username=mEtUsername.getText().toString().trim();
            userpsd=mEtPwd.getText().toString().trim();
            if (RegexUtils.verifyPassword(username) == 0
                    &&RegexUtils.verifyPassword(userpsd)==0){
                mBtnLogin.setEnabled(true);
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                presenter.login(username,userpsd);

                break;
            case R.id.tv_register:
                mActivityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }

    @Override
    public void showPrb() {
        if (mDialogFragment==null){//当为空时
            mDialogFragment=new ProgressDialogFragment();//构建实例
        }
        if (mDialogFragment.isVisible()){
            return;//现实过了不显示

        }
        mDialogFragment.show(getSupportFragmentManager(),"mDialogFragment");
    }

    @Override
    public void hidePrb() {
    mDialogFragment.dismiss();//影藏progress
    }

    @Override
    public void loginSuccess() {
        mActivityUtils.startActivity(MainActivity.class);//跳转到市场页面
        finish();
    }

    @Override
    public void loginFailed() {
    mEtUsername.setText("");
    mEtPwd.setText("");
    }

    @Override
    public void showMsg(String msg) {
        mActivityUtils.showToast(msg);
    }
}
