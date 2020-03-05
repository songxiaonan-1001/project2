package com.example.projecttwo.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.projecttwo.R;
import com.example.projecttwo.base.BaseActivity;
import com.example.projecttwo.interfaces.login.LoginConstract;
import com.example.projecttwo.models.bean.UserBean;
import com.example.projecttwo.presenter.login.LoginPresenter;
import com.example.projecttwo.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginConstract.Presenter> implements LoginConstract.View {
    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

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

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String nickname = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        presenter.login(nickname, password);
    }
}

