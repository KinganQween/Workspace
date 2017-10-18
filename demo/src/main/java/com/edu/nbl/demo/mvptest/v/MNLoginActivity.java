package com.edu.nbl.demo.mvptest.v;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.nbl.demo.R;
import com.edu.nbl.demo.mvptest.p.LoginPresent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MNLoginActivity extends AppCompatActivity implements  IMNLoginView{

    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.psd)
    EditText mPsd;
    @BindView(R.id.login)
    TextView mLogin;
    private LoginPresent mLoginPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnlogin);
        ButterKnife.bind(this);
        mLoginPresent=new LoginPresent(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        mLoginPresent.login();
    }

    @Override
    public void onSuccessfulShow(String string) {
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show();

    }



    @Override
    public void failedShow(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUserName() {
        return mName.getText().toString().trim();
    }

    @Override
    public String getUserPsd() {
        return mPsd.getText().toString().trim();
    }
}
