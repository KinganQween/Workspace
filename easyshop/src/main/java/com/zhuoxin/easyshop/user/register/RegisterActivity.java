package com.zhuoxin.easyshop.user.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.commons.ActivityUtils;
import com.zhuoxin.easyshop.commons.RegexUtils;
import com.zhuoxin.easyshop.components.ProgressDialogFragment;
import com.zhuoxin.easyshop.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends MvpActivity<RegisterView,RegisterPresenter> implements RegisterView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText et_Username;
    @BindView(R.id.et_pwd)
    EditText et_Pwd;
    @BindView(R.id.et_pwdAgain)
    EditText et_PwdAgain;
    @BindView(R.id.btn_register)
    Button btn_Register;
    private ActivityUtils activityUtils;//工具  实现activity的跳转和存储
    private String username;//注册的用户名
    private String password;//注册的密码
    private String pwd_again;//密码确认
    private ProgressDialogFragment dialogFragment;//注册过程中的圆形进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //初始化
        init();
    }

    @NonNull
    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }
    private void init() {
        activityUtils=new ActivityUtils(this);
        //初始化toolbar
        //添加返回图标
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_Username.addTextChangedListener(textWatcher);
        et_Pwd.addTextChangedListener(textWatcher);
        et_PwdAgain.addTextChangedListener(textWatcher);
    }
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        }
        //监听EditText文本改变之后的操作  eidtText只要一改变内容就监听的到
        @Override
        public void afterTextChanged(Editable editable) {
            username=et_Username.getText().toString();
            password=et_Pwd.getText().toString();
            pwd_again=et_PwdAgain.getText().toString();
            //验证注册按钮
            boolean canNoRegister=(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)||TextUtils.isEmpty(pwd_again));
            boolean canRegister=!canNoRegister;//canRegister:为true可点击  为false不可点检
            btn_Register.setEnabled(canRegister);
        }
    };
    //点击按钮验证 用户名和密码
    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        //账号为中文，字母或数字，长度为4~20，一个中文算2个长度
        if (RegexUtils.verifyUsername(username)!=RegexUtils.VERIFY_SUCCESS){//用正则表达式验证用户名  表示没有匹配上
            activityUtils.showToast(R.string.username_rules);//吐司：“账号为中文，字母或数字，长度为4~20，一个中文算2个长度”
            return;
            //密码以数字或字母开头，长度在6~18之间，只能包含字符、数字和下划线
        }else if(RegexUtils.verifyPassword(password)!=RegexUtils.VERIFY_SUCCESS){//密码没有匹配上
            activityUtils.showToast(R.string.password_rules);
            return;
        }else if (!TextUtils.equals(password,pwd_again)){//两次输入的密码不同
            activityUtils.showToast(R.string.username_equal_pwd);//吐司：两次输入的密码不同！
            return;
        }
        //发送注册请求
        presenter.register(username,password);//实现注册业务
    }
    //视图接口的实现
    @Override
    public void showPrb() {//显示进度条
        //圆形进度条
         if (dialogFragment==null){
          dialogFragment=new ProgressDialogFragment();
        }
        if (dialogFragment.isVisible()){//如果进度条已经显示，则跳出
            return;
        }
        //显示进度条
        dialogFragment.show(getSupportFragmentManager(),"dialogFragment");
    }
    @Override
    public void hidePrb() {
        dialogFragment.dismiss();
    }
    @Override
    public void registerSuccess() {//注册成功  跳转到市场页面
        Intent intent=new Intent(this, MainActivity.class);//跳转到MainAcitivity中
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);//清空老的任务栈  将MainAcitivity放到新的任务栈中
        startActivity(intent);
    }

    @Override
    public void registerFailed() {//注册失败
        et_Username.setText("");
        et_Pwd.setText("");
        et_PwdAgain.setText("");
    }

    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }
    //点击回退健销毁当前页
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
