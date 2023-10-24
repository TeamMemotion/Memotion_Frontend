package com.example.memotion.route.patch.routedetail;

public interface PatchRouteDetailResult {
    void patchRouteDetailSuccess(int code, PatchRouteDetailResponse.Result result);
    void patchRouteDetailFailure(int code, String message);
}
