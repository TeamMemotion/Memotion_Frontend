package com.example.memotion.account.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.MainActivity;
import com.example.memotion.R;
import com.example.memotion.account.FindPwdActivity;
import com.example.memotion.account.SignUpActivity;
import com.example.memotion.databinding.ActivityLoginBinding;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
    private ActivityLoginBinding loginBinding;
    private LoginService loginService;

    private Pattern emailValidation = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        loginBinding.etEmailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //text가 변경되기 전 호출
                //charSequence에는 변경 전 문자열이 담겨 있음.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEmail();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //text가 변경된 후 호출
                //charSequence에는 변경 후의 문자열이 담겨있음
            }
        });

        /** KakoSDK init */

        KakaoSdk.init(this, this.getString(R.string.kakao_native_key));

        // 카카오톡이 설치되어 있는지 확인하는 메소드 : 카카오에서 제공하는 콜백 객체 활용
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달되면 로그인이 성공한 것, 토큰이 전달되지 않았다면 로그인 실패
                if(throwable != null) {
                    Log.d(TAG, "로그인 실패 : " + throwable.getMessage().toString());
                }
                else if(oAuthToken != null) {
                    Log.d(TAG, "로그인 성공");
                    updateKakaoLoginUi();
                }
                return null;
            }
        };

        loginBinding.btnKakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {         // 카카오톡이 설치된 경우 : 카카오톡에서 계정 정보 가져오기
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                } else
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);    // 카카오톡이 설치되어있지 않은 경우 : 카카오 계정으로 로그인
            }
        });

        loginBinding.tbFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindPwdActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        loginBinding.tbSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 로그인이 되어있는 경우
                if(user != null) {
                    String email = user.getKakaoAccount().getEmail();
                    String image = user.getKakaoAccount().getProfile().getProfileImageUrl();
                    String nickname = user.getKakaoAccount().getProfile().getNickname();

                    Log.d(TAG, "nickname : " + nickname); // 유저 아이디
                    Log.d(TAG, "email : " + email); // 유저 이메일
                    Log.d(TAG, "profile_img : " + image); // 유저 프로필 이미지

                    if(email != null) {
                        LoginRequest.KakaoLogin request = new LoginRequest.KakaoLogin(email, image, nickname);
                        loginService.kakaoLogin(request);
                    } else { // email이 null인 경우 : 이메일 동의 거부
                        loginBinding.loginError.setText("이메일 동의가 거부되었습니다.");
                        loginBinding.loginError.setVisibility(View.VISIBLE);
                    }
                } else{
                    // 로그인 되어있지 않은 경우
                    Log.d(TAG, "로그인 되어있지 않음");
                    loginBinding.loginError.setText("카카오톡에 로그인 되어있지 않습니다.");
                    loginBinding.loginError.setVisibility(View.VISIBLE);
                }

                return null;
            }
        });
    }

    private boolean checkEmail() {
        String email = loginBinding.etEmailId.getText().toString().trim();  //공백 제거
        Matcher matcher = emailValidation.matcher(email);
        if(matcher.find()) {
            loginBinding.tvEmailCheck.setText("");
            return true;
        } else {
            loginBinding.tvEmailCheck.setText("이메일을 입력하세요.");
            return false;
        }
    }
}