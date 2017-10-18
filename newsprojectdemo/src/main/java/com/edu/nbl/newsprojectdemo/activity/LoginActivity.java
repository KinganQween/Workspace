package com.edu.nbl.newsprojectdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.nbl.newsprojectdemo.R;
import com.edu.nbl.newsprojectdemo.utils.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.edu.nbl.newsprojectdemo.R.mipmap.user;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_username)
    EditText mLoginUsername;
    @BindView(R.id.login_mailbox)
    EditText mLoginMailbox;
    @BindView(R.id.login_password)
    EditText mLoginPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        login();
    }

    public void login() {
        //获取用户信息
        //trim去空格
        final String mUserName = mLoginUsername.getText().toString().trim();
        String mUserEmail = mLoginMailbox.getText().toString().trim();
        final String mUserPassWord = mLoginPassword.getText().toString().trim();
        //非空判断
        if (mUserName.length() == 0) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mUserEmail.length() == 0) {
            Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mUserPassWord.length() == 0) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (CommonUtil.verifyEmail(mUserEmail)) {
            Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (CommonUtil.verifyPassword(mUserPassWord)) {
            Toast.makeText(this, "密码格式不正确，6-16位", Toast.LENGTH_SHORT).show();
            return;
        }
        //注册
        String url = "user_register?ver=1&uid=" + user + "&email=" + mUserEmail + "&pwd=" + mUserPassWord;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request mRequest = new Request.Builder()
                .url(url)
                .build();
        Call mCall = mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("TAG",response+"");
                JSONObject mJSONObject = null;
                try {
                    mJSONObject = new JSONObject(String.valueOf(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (mJSONObject.getInt("status") == 0) {
                        mJSONObject = mJSONObject.getJSONObject("data");
                        int mResult1 = mJSONObject.getInt("result");
                        if (mResult1 == 0) {
                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent mIntent = new Intent();
                            Bundle mBundle = new Bundle();
                            mBundle.putString("userName", mUserName);
                            mBundle.putString("pwd", mUserPassWord);
                            mIntent.putExtras(mBundle);
                            setResult(1, mIntent);
                            LoginActivity.this.finish();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}
