package com.example.memotion.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.R;
import com.example.memotion.account.auth.email.EmailPostRequest;
import com.example.memotion.account.auth.email.EmailPostResult;
import com.example.memotion.account.auth.email.EmailPostService;
import com.example.memotion.account.auth.signup.SignUpPostRequest;
import com.example.memotion.account.auth.signup.SignUpPostResponse;
import com.example.memotion.account.auth.signup.SignUpPostResult;
import com.example.memotion.account.auth.signup.SignUpPostService;
import com.example.memotion.account.login.LoginActivity;
import com.example.memotion.databinding.ActivitySignUpBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements EmailPostResult, SignUpPostResult {
    private Pattern emailValidation = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private String checkNum = "";
    private String email = "";
    private String name = "";
    private String password = "";
    private String image = null;


    ActivitySignUpBinding signUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());

        signUpBinding.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!signUpBinding.etInputName.getText().toString().equals("") && !signUpBinding.etInputEmail.getText().toString().equals("")) {
                    email = signUpBinding.etInputEmail.getText().toString();
                    name = signUpBinding.etInputName.getText().toString();
                    password = signUpBinding.etInputPassword.getText().toString();

                    signUp();
                } else {
                    Log.d("error", "==========");
                }
            }
        });

        signUpBinding.btnSendToCheckMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!signUpBinding.etInputEmail.getText().toString().equals("")) {
                    emailAuthentication();
                } else {
                    signUpBinding.tvEmailAuthenticationCheck.setText("이메일을 작성해주세요.");
                    signUpBinding.tvEmailAuthenticationCheck.setTextColor(Color.parseColor("#E15F81"));
                }
            }
        });

        signUpBinding.etInputEmail.addTextChangedListener(new TextWatcher() {
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

        signUpBinding.etInputPasswordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //text가 변경되기 전 호출
                //charSequence에는 변경 전 문자열이 담겨 있음.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPassword();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //text가 변경된 후 호출
                //charSequence에는 변경 후의 문자열이 담겨있음
            }
        });

        signUpBinding.emailAuthenticateNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //text가 변경되기 전 호출
                //charSequence에는 변경 전 문자열이 담겨 있음.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAuthenticateNum();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //text가 변경된 후 호출
                //charSequence에는 변경 후의 문자열이 담겨있음
            }
            
        });
    }

    //이메일 인증번호 api
    private EmailPostRequest emailPostRequest() {
        email = signUpBinding.etInputEmail.getText().toString();
        Log.d("이메일3: " , email);
        return new EmailPostRequest(email);
    }
    private void emailAuthentication() {
        EmailPostService emailPostService = new EmailPostService();
        emailPostService.setEmailPostResult(this);
        emailPostService.emailAuthentication(emailPostRequest());
    }
    @Override
    public void emailSuccess(int code, String result) {
        Log.d("이메일 인증번호: " , result);
        checkNum = result;
        signUpBinding.tvEmailAuthenticationCheck.setText("작성하신 메일로 인증번호가 전송되었습니다.");
        signUpBinding.tvEmailAuthenticationCheck.setTextColor(Color.parseColor("#E15F81"));
    }

    @Override
    public void emailFailure(int code, String message) {
        Log.d("이메일 응답 실패: ", message);
    }

    //회원가입 api
    private SignUpPostRequest signUpPostRequest() {
        return new SignUpPostRequest(name, email, password, image);
    }
    
    private void signUp() {
        SignUpPostService signUpPostService = new SignUpPostService();
        signUpPostService.setSignUpPostResult(this);
        signUpPostService.signUp(signUpPostRequest());
    }

    @Override
    public void signUpSuccess(int code, SignUpPostResponse.Result result) {
        Log.d("member id: ", result.getMemberId().toString());
        Log.d("email: ", result.getEmail());

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void signUpFailure(int code, String message) {
        Log.d("회원가입 실패: ", message);
    }
    
    
    //이메일 형식 체크
    private boolean checkEmail() {
        String email = signUpBinding.etInputEmail.getText().toString().trim();  //공백 제거
        Matcher matcher = emailValidation.matcher(email);
        if(matcher.find()) {
            signUpBinding.tvEmailCheck.setText("");
            return true;
        } else {
            signUpBinding.tvEmailCheck.setText("이메일을 입력하세요.");
            return false;
        }
    }

    //비밀번호 일치 체크
    private boolean checkPassword() {
        String password = signUpBinding.etInputPasswordCheck.getText().toString().trim(); //공백제거
        String checkPassword = signUpBinding.etInputPassword.getText().toString().trim();

        if(password.equals(checkPassword)) {
            signUpBinding.tvPasswordCheck.setText("");
            signUpBinding.etInputPasswordCheck.setTextColor(Color.parseColor("#FF000000"));
            signUpBinding.etInputPasswordCheck.setBackgroundResource(R.drawable.sign_up_edit_text_box);
            return true;
        } else {
            signUpBinding.tvPasswordCheck.setText("패스워드가 같지 않습니다.");
            signUpBinding.etInputPasswordCheck.setTextColor(Color.parseColor("#E15F81"));
            signUpBinding.etInputPasswordCheck.setBackgroundResource(R.drawable.sign_up_edit_text_box_pink);
            return false;
        }
    }

    //이메일 인증번호 체크
    private boolean checkAuthenticateNum() {
        String check = signUpBinding.emailAuthenticateNum.getText().toString().trim(); //공백제거
        String password = signUpBinding.etInputPasswordCheck.getText().toString().trim(); //공백제거
        String checkPassword = signUpBinding.etInputPassword.getText().toString().trim();

        if(checkNum.equals(check) && password.equals(checkPassword)) {
            if(!password.isEmpty() && !check.isEmpty()) {
                signUpBinding.btnOK.setEnabled(true);
                signUpBinding.btnOK.setBackgroundResource(R.drawable.btn_pink_background);
                return true;
            } else {
                signUpBinding.btnOK.setEnabled(false);
                signUpBinding.btnOK.setBackgroundResource(R.drawable.btn_background_grey_color);
                return false;
            }
        } else {
            signUpBinding.btnOK.setEnabled(false);
            signUpBinding.btnOK.setBackgroundResource(R.drawable.btn_background_grey_color);
            return false;
        }
    }

    
}
