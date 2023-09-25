package com.example.memotion.arcamera.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface PlacesService {

    @GET("nearbysearch/json")
    Call<NearbyPlacesResponse> nearbyPlaces(
            @Query("key") String apiKey,
            @Query("location") String location,
            @Query("radius") int radiusInMeters,
            @Query("type") String placeType
    );

    class Factory {
        private static final String ROOT_URL = "https://maps.googleapis.com/maps/api/place/";

        public static PlacesService create() {
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build();
            GsonConverterFactory converterFactory = GsonConverterFactory.create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .client(okHttpClient)
                    .addConverterFactory(converterFactory)
                    .build();
            return retrofit.create(PlacesService.class);
        }
    }
}
