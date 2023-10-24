package com.example.memotion.route.get.routedetailList;

import static com.example.memotion.RetrofitClient.getClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRouteDetailListService {

    private GetRouteDetailListResult getRouteDetailListResult;

    public void setGetRouteDetailResult(GetRouteDetailListResult getRouteDetailListResult) {
        this.getRouteDetailListResult = getRouteDetailListResult;
    }
    public void getRouteDetailList(Long id){
        GetRouteDetailListRetrofitInterface getRouteDetailListRetrofitInterface = getClient().create(GetRouteDetailListRetrofitInterface.class);

        getRouteDetailListRetrofitInterface.getRouteDetailList(id).enqueue(new Callback<GetRouteDetailListResponse>() {
            @Override
            public void onResponse(Call<GetRouteDetailListResponse> call, Response<GetRouteDetailListResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        getRouteDetailListResult.getRouteDetailListSuccess(response.body().getCode(), response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRouteDetailListResponse> call, Throwable t) {

            }
        });
    }
}
