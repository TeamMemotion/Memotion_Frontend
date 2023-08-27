package com.example.memotion.account.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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


public class LoginActivity extends AppCompatActivity implements LoginResult {
    private String TAG = "LoginActivity";
    private ActivityLoginBinding loginBinding;
    private LoginService loginService;

    private String email = "";
    private String password = "";
    private SharedPreferences preferences;

    private Pattern emailValidation = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity", "로그인 버튼 클릭");
                email = loginBinding.etEmailId.getText().toString();
                password = loginBinding.etPassword.getText().toString();

                login();
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

        loginBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkNull();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

    //로그인 api
    private LoginRequest loginRequest() {
        email = loginBinding.etEmailId.getText().toString();
        password = loginBinding.etPassword.getText().toString();
        return new LoginRequest(email, password);
    }

    private void login() {
        LoginService loginService1 = new LoginService();
        loginService1.setLoginResult(this);
        loginService1.login(loginRequest());
    }

    @Override
    public void loginSuccess(int code, LoginResponse.Result result) {
        Log.d("accessToken: ", result.getAccessToken());
        Log.d("refreshToken: ", result.getRefreshToken());

        //토큰 저장
        preferences = getSharedPreferences("token", MODE_PRIVATE); //accessToken이름으로 이 앱 내에서 사용
        SharedPreferences.Editor editor = preferences.edit(); //sharedPreferences를 제어할 editor를 선언
        editor.putString("accessToken", result.getAccessToken());
        editor.putString("refreshToken", result.getRefreshToken());
        editor.commit();

        Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailure(int code, String message) {
        if(code == 500){
            if(message.contains("[must be a well-formed email address]")) {
                Toast.makeText(getApplicationContext(), "이메일 형식이 잘못 되었습니다.", Toast.LENGTH_SHORT).show();
            } else if (message.contains("[비밀번호는 8~20자 영문 대/소문자, 숫자를 사용하세요.]")) {
                Toast.makeText(getApplicationContext(), "비밀번호 형식이 잘못 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } else if (code == 2001) {
            if(message.equals("잘못된 이메일 입니다.")) {
                Toast.makeText(getApplicationContext(), "존재하지 않는 회원입니다.", Toast.LENGTH_SHORT).show();
            } else if (message.equals("잘못된 비밀번호 입니다.")) {
                Toast.makeText(getApplicationContext(), "잘못된 비밀번호 입니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("로그인 응답 실패: ", message);
        }
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

    private boolean checkNull() {
        String email = loginBinding.etEmailId.getText().toString().trim();  //공백 제거
        String password = loginBinding.etPassword.getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty()) {
            loginBinding.btnLogin.setEnabled(true);
            loginBinding.btnLogin.setBackgroundResource(R.drawable.btn_pink_background);
            return true;
        } else {
            loginBinding.btnLogin.setEnabled(false);
            loginBinding.btnLogin.setBackgroundResource(R.drawable.btn_background_grey_color);
            return false;
        }
    }


}