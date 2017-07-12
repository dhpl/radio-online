package com.philong.radioonline.util;

import com.philong.radioonline.model.Radio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Long on 6/30/2017.
 */

public interface ResponeRadio {

    @GET("countries/us/stations")
    Call<List<Radio>> getRadioCountries(@Query("page") int page, @Query("per_page") int per_page, @Query("token") String token);

}
