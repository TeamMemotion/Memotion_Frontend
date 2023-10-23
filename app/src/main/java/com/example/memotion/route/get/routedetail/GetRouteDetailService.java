package com.example.memotion.route.get.routedetail;

import static com.example.memotion.RetrofitClient.getClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRouteDetailService {

    private GetRouteDetailResult getRouteDetailResult;

    public void setGetRouteDeatilResult(GetRouteDetailResult getRouteDetailResult) {
        this.getRouteDetailResult = getRouteDetailResult;
    }
    public void getRouteDetail(Long id){
        GetRouteDetailRetrofitInterface getRouteDetailRetrofitInterface = getClient().create(GetRouteDetailRetrofitInterface.class);

        getRouteDetailRetrofitInterface.getRouteDetail(id).enqueue(new Callback<GetRouteDetailResponse>() {
            @Override
            public void onResponse(Call<GetRouteDetailResponse> call, Response<GetRouteDetailResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        getRouteDetailResult.getRouteDeatilSuccess(response.body().getCode(), response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRouteDetailResponse> call, Throwable t) {

            }
        });
    }
}
