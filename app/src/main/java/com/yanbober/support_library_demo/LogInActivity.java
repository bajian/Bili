package com.yanbober.support_library_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanbober.support_library_demo.db.Settings;
import com.yanbober.support_library_demo.persistence.SettingConstant;
import com.yanbober.support_library_demo.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by hgx on 2015/8/6.
 */
public class LogInActivity extends Activity {


    @Bind(R.id.left_tv)
    TextView mLeftTv;
    @Bind(R.id.goBack)
    ImageView mGoBack;
    @Bind(R.id.menuButton)
    ImageView mMenuButton;
    @Bind(R.id.mainTitile)
    TextView mMainTitile;
    @Bind(R.id.right_btn)
    ImageView mRightBtn;
    @Bind(R.id.right_tv)
    TextView mRightTv;

    @Bind(R.id.ic_left)
    ImageView mIcLeft;
    @Bind(R.id.ic_right)
    ImageView mIcRight;
    @Bind(R.id.iv_user)
    ImageView mIvUser;
    @Bind(R.id.et_login_username)
    EditText mEtLoginUsername;
    @Bind(R.id.username_line)
    View mUsernameLine;
    @Bind(R.id.iv_pwd)
    ImageView mIvPwd;
    @Bind(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @Bind(R.id.psw_line)
    View mPswLine;
    @Bind(R.id.btn_register)
    Button mBtnRegister;
    @Bind(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ini();
    }

    private void ini() {
        mMainTitile.setText("用户登录");
        mEtLoginUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mIcLeft.setImageResource(R.mipmap.ic_22);
                    mIcRight.setImageResource(R.mipmap.ic_33);
                    mIvUser.setImageResource(R.mipmap.ic_login_username_hover);
                    mIvPwd.setImageResource(R.mipmap.ic_login_password_default);
                    mUsernameLine.setBackgroundColor(getResources().getColor(R.color.md_pink_300));
                    mPswLine.setBackgroundColor(getResources().getColor(R.color.black_02));
                }
            }
        });
        mEtLoginPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mIcLeft.setImageResource(R.mipmap.ic_22_hide);
                    mIcRight.setImageResource(R.mipmap.ic_33_hide);
                    mIvUser.setImageResource(R.mipmap.ic_login_username_default);
                    mIvPwd.setImageResource(R.mipmap.ic_login_password_hover);
                    mUsernameLine.setBackgroundColor(getResources().getColor(R.color.black_02));
                    mPswLine.setBackgroundColor(getResources().getColor(R.color.md_pink_300));
                }
            }
        });


    }

    public void login(View v) {
        String username = mEtLoginUsername.getText().toString().trim();
        String pwd = mEtLoginPwd.getText().toString().trim();
        if ("bajian".equals(username) && "334945804".equals(pwd)) {
            Settings.set(SettingConstant.SIGNALCODE, "bajian");
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            LogInActivity.this.overridePendingTransition(R.anim.push_left_in, R.anim.push_bottom_out);
            finish();
        } else {
            ToastUtil.showShortTime(LogInActivity.this, "账号或密码错误");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
