package com.edu.nbl.newsprojectdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.nbl.newsprojectdemo.R;
import com.edu.nbl.newsprojectdemo.biz.UrlUtils;
import com.edu.nbl.newsprojectdemo.utils.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.mailbox)
    EditText mMailbox;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.btn_password)
    Button mBtnPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tb_register)
    Toolbar mTbRegister;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(mTbRegister);
        ActionBar mActionBar=getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
initView();
    }

    private void initView() {
    }

    @OnClick({R.id.btn_register, R.id.btn_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case android.R.id.home:
                RegisterActivity.this.finish();
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_password:
                break;
            case R.id.btn_login:
                Intent mIntent = new Intent();
                mIntent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    private void register() {
        //获取用户名数据
        String mUserName = mUsername.getText().toString().trim();
        String mPwd = mPassword.getText().toString().trim();
        //非空判断
        if (mUserName.length()==0){
            Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPwd.length()==0){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (CommonUtil.verifyEmail(mPwd)){
            Toast.makeText(this,"密码格式不正确，6-16位",Toast.LENGTH_SHORT).show();
            return;
        }
        String url = UrlUtils.APP_URL+"user_login?ver=1&uid="+mUserName+"&pwd="+mPwd+"&device=0";
        OkHttpClient mOkHttpClient=new OkHttpClient();
        Request mRequest=new Request.Builder()
                .url(url)
                .build();
        Call mCall=mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject mJSONObject = new JSONObject();
                try {
                    if (mJSONObject.getInt("status")==0){
                        mJSONObject= mJSONObject.getJSONObject("data");
                        int mResult = mJSONObject.getInt("result");
                        if(mResult==0){
                            String mToken = mJSONObject.getString("token");//用户令牌
                            Bundle mBundle = new Bundle();
                            Intent mIntent = new Intent();
                            mIntent.putExtras(mBundle);
                            setResult(1,mIntent);
                            RegisterActivity.this.finish();
                }
            }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
    }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle mBundle = data.getExtras();
        String mUserName = mBundle.getString("userName");
        String mPwd = mBundle.getString("pwd");
        //将用户名和密码回显
        mUsername.setText(mUserName);
        mPassword.setText(mPwd);

    }
}