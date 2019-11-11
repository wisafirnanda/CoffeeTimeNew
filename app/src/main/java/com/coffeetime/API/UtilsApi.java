package com.coffeetime.API;

import retrofit2.Retrofit;

public class UtilsApi {

    private static Retrofit retrofit;
    public static final String BASE_URL_API = "http://inshaallahlulus.com/wisa/api/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}

