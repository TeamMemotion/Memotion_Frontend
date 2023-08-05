package com.example.memotion.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.memotion.R;
import com.example.memotion.account.auth.findPwd.FindPwdPostRequest;
import com.example.memotion.account.auth.findPwd.FindPwdPostResult;
import com.example.memotion.account.auth.findPwd.FindPwdPostService;
import com.example.memotion.databinding.FragmentFindPwdBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FindPwdFragment extends Fragment implements FindPwdPostResult {
    private Pattern emailValidation = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private String email = "";

    FragmentFindPwdBinding findPwdBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        findPwdBinding = FragmentFindPwdBinding.inflate(inflater, container, false);

        findPwdBinding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = findPwdBinding.etInputEmail.getText().toString();
                Log.d("이메일:: " , email);
                findPwd();
            }
        });

        findPwdBinding.etInputEmail.addTextChangedListener(new TextWatcher() {
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

        return findPwdBinding.getRoot();
    }

    //비밀번호 찾기 api
    private FindPwdPostRequest findPwdPostRequest() {
        email = findPwdBinding.etInputEmail.getText().toString();
        Log.d("이메일 ::: ", email);
        return new FindPwdPostRequest(email);
    }

    private void findPwd() {
        FindPwdPostService findPwdPostService = new FindPwdPostService();
        findPwdPostService.setFindPwdPostResult(this);
        findPwdPostService.findPwd(findPwdPostRequest());
    }

    @Override
    public void findPwdSuccess(int code, String result) {
        Log.d("임시 비밀번호: ", result);

        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        //((FindPwdActivity)getActivity()).replaceFragment(1);

        FindPwdOkFragment fragment = new FindPwdOkFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment).commit();
    }

    @Override
    public void findPwdFailure(int code, String message) {
        if(code == 2001 && message.equals("해당하는 회원을 찾을 수 없습니다.")) {
            findPwdBinding.tvEmailCheck.setText("해당하는 회원을 찾을 수 없습니다.");
        } else {
            Log.d("이메일 응답 실패: ", message);
        }
    }


    //이메일 형식 체크
    private boolean checkEmail() {
        String email = findPwdBinding.etInputEmail.getText().toString().trim();  //공백 제거
        Matcher matcher = emailValidation.matcher(email);
        if(matcher.find()) {
            findPwdBinding.tvEmailCheck.setText("");
            findPwdBinding.btnOk.setEnabled(true);
            findPwdBinding.btnOk.setBackgroundResource(R.drawable.btn_pink_background);
            return true;
        } else {
            findPwdBinding.tvEmailCheck.setText("이메일을 입력하세요.");
            findPwdBinding.btnOk.setEnabled(false);
            findPwdBinding.btnOk.setBackgroundResource(R.drawable.btn_background_grey_color);
            return false;
        }
    }


}
