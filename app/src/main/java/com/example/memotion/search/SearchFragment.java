package com.example.memotion.search;

import android.app.SearchManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.memotion.R;
import com.example.memotion.databinding.FragmentSearchBinding;
import com.example.memotion.search.post.SearchGetResponse;
import com.example.memotion.search.post.SearchGetResult;
import com.example.memotion.search.post.SearchGetService;

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

        searchBinding.searchEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // EditText에 포커스가 주어질 때 이벤트 처리
                    ViewGroup.LayoutParams layoutParams = searchBinding.searchEt.getLayoutParams();

                    layoutParams.width = searchBinding.searchEt.getWidth()-50;
                    searchBinding.searchEt.setLayoutParams(layoutParams);
                    searchBinding.cancel.setVisibility(View.VISIBLE);
                } else {
                    // EditText에서 포커스가 빠져나갈 때 이벤트 처리
//                    ViewGroup.LayoutParams layoutParams = searchBinding.searchEt.getLayoutParams();
//
//                    layoutParams.width = searchBinding.searchEt.getWidth()+50;
//                    searchBinding.searchEt.setLayoutParams(layoutParams);
//                    searchBinding.cancel.setVisibility(View.INVISIBLE);
                }
            }
        });

        if(searchBinding.cancel.getVisibility() == View.VISIBLE) {
            searchBinding.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewGroup.LayoutParams layoutParams = searchBinding.searchEt.getLayoutParams();

                    layoutParams.width = searchBinding.searchEt.getWidth()+50;
                    searchBinding.searchEt.setLayoutParams(layoutParams);
                    searchBinding.cancel.setVisibility(View.INVISIBLE);

                    if(filterState == 0)
                        getSearch("latest", null, null);
                    else
                        getSearch("earliest", null, null);
                }
            });
        }

        searchBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchBinding.searchEt.getText().length() > 0) {
                    executeReverseGeocoding(searchBinding.searchEt.toString());
                    if(filterState == 0) {
                        getSearch("latest", mLat, mLng);
                    } else if(filterState == 1) {
                        getSearch("earliest", mLat, mLng);
                    }
                }
            }
        });

        return searchBinding.getRoot();
    }

    private void setUpSpinnerText() {
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.spinner_item));

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.spinner_item_custom, items);
        searchBinding.spinnerBtn.setAdapter(adapter);
    }

    private void setUpSpinnerHandler() {
        searchBinding.spinnerBtn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0) { // 최신순
                    filterState = 0;
                    searchBinding.frameLayoutLatest.setVisibility(View.INVISIBLE);
                    searchBinding.frameLayoutNew.setVisibility(View.VISIBLE);

//                    if(searchBinding.searchEt.getText().length() > 0) {
//                        executeReverseGeocoding(searchBinding.searchEt.toString());
//                        getSearch("latest", mLat, mLng);
//                    } else {
//                        getSearch("latest", null, null);
//                    }
                } else {    // 오래된순
                    filterState = 1;
                    searchBinding.frameLayoutLatest.setVisibility(View.VISIBLE);
                    searchBinding.frameLayoutNew.setVisibility(View.INVISIBLE);

                    if(searchBinding.searchEt.getText().length() > 0) {
                        executeReverseGeocoding(searchBinding.searchEt.toString());
                        getSearch("earliest", mLat, mLng);
                    } else {
                        getSearch("earliest", null, null);
                    }
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
        getSearch("latest", null, null);
    }

    // 검색 조회
    private void getSearch(String filter, Double latitude, Double longitude) {
        SearchGetService searchGetService = new SearchGetService();
        searchGetService.setSearchGetResult(this);
        searchGetService.getSearch(filter, latitude, longitude);
    }

    @Override
    public void getSearchSuccess(int code, ArrayList<SearchGetResponse.Result> result) {
        Log.d(TAG, "공지사항 조회 성공");

        for (int i=0; i < result.size() - 1; i++) {
            Log.d(TAG, "diary id: " + result.get(i).getDiaryId());
            Log.d(TAG, "keyword: " + result.get(i).getKeyWord());
            Log.d(TAG, "latitude: " + result.get(i).getLatitude());
            Log.d(TAG, "longitude: " + result.get(i).getLongitude());
            Log.d(TAG, "emotion: " + result.get(i).getEmotion());
            Log.d(TAG, "Date: " + result.get(i).getCreatedDate());
        }

        Log.d(TAG, "--------");

        if(filterState == 0) {
            initRecycler_latest(result);
        } else if (filterState == 1) {
            initRecycler_earliest(result);
        }
    }

    @Override
    public void getSearchFailure(int code, String message) {
        Log.d(TAG, "검색 실패");
    }

    private void initRecycler_latest(ArrayList<SearchGetResponse.Result> result) {
        for (int i=0; i < result.size() - 1; i++) {
            Log.d(TAG, "diary id: " + result.get(i).getDiaryId());
            Log.d(TAG, "keyword: " + result.get(i).getKeyWord());
            Log.d(TAG, "latitude: " + result.get(i).getLatitude());
            Log.d(TAG, "longitude: " + result.get(i).getLongitude());
            Log.d(TAG, "emotion: " + result.get(i).getEmotion());
            Log.d(TAG, "Date: " + result.get(i).getCreatedDate());
        }

        searchLatestAdapter.setSearchLatestList(result);
        searchBinding.searchView.setAdapter(searchLatestAdapter);
        searchBinding.searchView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // 클릭 이벤트
    }

    private void initRecycler_earliest(ArrayList<SearchGetResponse.Result> result) {
        searchEarliestAdapter.setSearchEarliestList(result);
        searchBinding.searchView.setAdapter(searchEarliestAdapter);
        searchBinding.searchView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // 클릭 이벤트
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
                }
            }
        }
    }
}