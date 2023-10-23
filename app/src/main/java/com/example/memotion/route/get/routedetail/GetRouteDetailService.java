package com.example.memotion.route.get.routedetail;

import static com.example.memotion.RetrofitClient.getClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRouteDetailService {

    private GetRouteDeatilResult getRouteDeatilResult;

    public void setGetRouteDeatilResult(GetRouteDeatilResult getRouteDeatilResult) {
        this.getRouteDeatilResult = getRouteDeatilResult;
    }
    public void getRouteDetail(Long id){
        GetRouteDetailRetrofitInterface getRouteDetailRetrofitInterface = getClient().create(GetRouteDetailRetrofitInterface.class);

        getRouteDetailRetrofitInterface.getRouteDetail(id).enqueue(new Callback<GetRouteDetailResponse>() {
            @Override
            public void onResponse(Call<GetRouteDetailResponse> call, Response<GetRouteDetailResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        getRouteDeatilResult.getRouteDeatilSuccess(response.body().getCode(), response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRouteDetailResponse> call, Throwable t) {

            }
        });
    }
}
