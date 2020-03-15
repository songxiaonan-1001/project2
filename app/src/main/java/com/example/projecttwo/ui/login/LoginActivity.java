package com.example.projecttwo.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecttwo.R;
import com.example.projecttwo.base.BaseActivity;
import com.example.projecttwo.interfaces.login.LoginConstract;
import com.example.projecttwo.models.bean.UserBean;
import com.example.projecttwo.presenter.login.LoginPresenter;
import com.example.projecttwo.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登陆页
 */
public class LoginActivity extends BaseActivity<LoginConstract.Presenter> implements LoginConstract.View {
    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.txt_regist)
    TextView txtRegist;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected LoginConstract.Presenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void loginReturn(UserBean result) {
        //登录成功以后存入sp
        SpUtils.getInstance().setValue("token", result.getData().getToken());
        finish();
    }

    @OnClick({R.id.btn_login, R.id.txt_regist})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String nickname = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                presenter.login(nickname, password);
                break;
            case R.id.txt_regist:
                Toast.makeText(this, "注册", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

