package com.example.memotion.search;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.memotion.R;
import com.example.memotion.databinding.FragmentSearchBinding;
import com.example.memotion.diary.DiaryDialog;
import com.example.memotion.diary.DiaryRecyclerAdapter;
import com.example.memotion.search.get.SearchGetResponse;
import com.example.memotion.search.get.SearchGetResult;
import com.example.memotion.search.get.SearchGetService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment implements SearchGetResult {

    FragmentSearchBinding searchBinding;
    private SearchLatestAdapter searchLatestAdapter;
    private SearchEarliestAdapter searchEarliestAdapter;
    private static String TAG = "SearchFragment";
    private int filterState = 0; // 0-> 최신 / 1-> 오래된
    EditText searchEditText;
    private Double mLat = 360.0;    // 위도 초기값
    private Double mLng = 360.0;    // 경도 초기값

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        searchEditText = searchBinding.searchEt;

        // 스피너
        setUpSpinnerText();
        setUpSpinnerHandler();

        searchLatestAdapter = new SearchLatestAdapter(this);
        searchEarliestAdapter = new SearchEarliestAdapter(this);

        // 원하는 장소 검색
        searchBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchBinding.searchEt.getText().length() > 0) {
                    executeReverseGeocoding(searchBinding.searchEt.getText().toString());
                }
            }
        });

        return searchBinding.getRoot();
    }

    // 스피너 텍스트를 정하는 것 (최신순 오래된순)
    private void setUpSpinnerText() {
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.spinner_item));

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.spinner_item_custom, items);
        searchBinding.spinnerBtn.setAdapter(adapter);
    }

    // 최신순 클릭하면 그거에 맞는 adapter or 오래된순 클릭하면 그거에 맞는 adapter 보여주는 역할
    private void setUpSpinnerHandler() {
        searchBinding.spinnerBtn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0) { // 최신순
                    filterState = 0;
//                    searchBinding.frameLayoutLatest.setVisibility(View.VISIBLE);
//                    searchBinding.frameLayoutEarliest.setVisibility(View.INVISIBLE);

                    if(searchBinding.searchEt.getText().length() > 0)
                        executeReverseGeocoding(searchBinding.searchEt.toString());
                    else
                        getSearch("latest", null, null);
                } else {    // 오래된순
                    filterState = 1;
//                    searchBinding.frameLayoutLatest.setVisibility(View.INVISIBLE);
//                    searchBinding.frameLayoutEarliest.setVisibility(View.VISIBLE);

                    if(searchBinding.searchEt.getText().length() > 0)
                        executeReverseGeocoding(searchBinding.searchEt.toString());
                    else
                        getSearch("earliest", null, null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // 검색 조회
    private void getSearch(String filter, Double latitude, Double longitude) {
        Log.d(TAG, "위도: " + latitude + ", 경도: " + longitude);
        SearchGetService searchGetService = new SearchGetService();
        searchGetService.setSearchGetResult(this);
        searchGetService.getSearch(filter, latitude, longitude);
    }

    @Override
    public void getSearchSuccess(int code, ArrayList<SearchGetResponse.Result> result) {
        Log.d(TAG, "장소 검색 조회 성공");

        if(filterState == 0) {  // 최신순에 맞는 RecyclerView 띄우기
            initRecycler_latest(result);
        } else if (filterState == 1) {  // 오래된순에 맞는 RecyclerView 띄우기
            initRecycler_earliest(result);
        }
    }

    @Override
    public void getSearchFailure(int code, String message) {
        Log.d(TAG, "검색 실패");
    }

    // 최신순 RecyclerView로 데이터 넘기고 화면 띄우기
    private void initRecycler_latest(ArrayList<SearchGetResponse.Result> result) {
        searchLatestAdapter.setSearchLatestList(result);
        searchBinding.frameLayoutLatest.setVisibility(View.VISIBLE);
        searchBinding.searchView.setAdapter(searchLatestAdapter);
        searchBinding.searchView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // 클릭 이벤트 발생
        searchLatestAdapter.setItemClickListener(new SearchLatestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchGetResponse.Result result) {
                DiaryDialog dialog = new DiaryDialog(getContext(), result);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
    }

    // 오래된순 RecyclerView로 데이터 넘기고 화면 띄우기
    private void initRecycler_earliest(ArrayList<SearchGetResponse.Result> result) {
        searchEarliestAdapter.setSearchEarliestList(result);
        searchBinding.frameLayoutLatest.setVisibility(View.VISIBLE);
        searchBinding.searchView.setAdapter(searchEarliestAdapter);
        searchBinding.searchView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // 클릭 이벤트
        searchEarliestAdapter.setItemClickListener(new SearchEarliestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchGetResponse.Result result) {
                DiaryDialog dialog = new DiaryDialog(getContext(), result);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
    }

    private void executeReverseGeocoding(String str) {
        if(Geocoder.isPresent() && str != null)
            new ReverseGeoTask().execute(str);
    }

    class ReverseGeoTask extends AsyncTask<String, Void, List<Address>> {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        @Override
        protected List<Address> doInBackground(String... strs) {
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocationName(strs[0], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            if (addresses != null) {
                if (addresses.size() == 0) {
                    // 정확한 위치명을 입력하지 않은 경우
                    Toast.makeText(getContext(), "정확한 위치명을 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    Address address = addresses.get(0);
                    mLat = address.getLatitude();
                    mLng = address.getLongitude();

                    if(filterState == 0)
                        getSearch("latest", mLat, mLng);
                    else
                        getSearch("earliest", mLat, mLng);
                }
            }
        }
    }
}