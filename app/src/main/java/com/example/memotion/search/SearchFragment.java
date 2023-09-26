package com.example.memotion.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import com.example.memotion.R;
import com.example.memotion.databinding.FragmentSearchBinding;

import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    FragmentSearchBinding searchBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false);

        // 스피너
        setUpSpinnerText();
        setUpSpinnerHandler();

        return searchBinding.getRoot();
    }

    private void setUpSpinnerText() {
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.spinner_item));

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.spinner_item_custom, items);
        searchBinding.spinnerBtn.setAdapter(adapter);
    }

    private void setUpSpinnerHandler() {
        searchBinding.spinnerBtn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0) { // 최신순
                    searchBinding.frameLayoutLatest.setVisibility(View.INVISIBLE);
                    searchBinding.frameLayoutNew.setVisibility(View.VISIBLE);
                } else {    // 오래된순

                    searchBinding.frameLayoutLatest.setVisibility(View.VISIBLE);
                    searchBinding.frameLayoutNew.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}