package com.nikita.movies_shmoovies.common.network

import com.nikita.movies_shmoovies.BuildConfig
import com.nikita.movies_shmoovies.info.ListActors
import com.nikita.movies_shmoovies.posters.PostersFragment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
  @GET("/3/movie/upcoming")
  fun getUpcoming(@Query("api_key") apiKey: String,
                  @Query("page") page : Int): Call<ListResponse<Movie>>

  @GET("/3/movie/popular")
  fun getPopular(@Query("api_key") apiKey: String,
                 @Query("page") page : Int): Call<ListResponse<Movie>>

  @GET("/3/movie/top_rated")
  fun getTop(@Query("api_key") apiKey: String): Call<ListResponse<Movie>>

  @GET("/3/movie/{id}")
  fun getDetails(@Path("id") id: String, @Query("api_key") apiKey: String): Call<MovieDetail>

  @GET("/3/movie/{id}/credits")
  fun getCredits(@Path("id") id : String, @Query("api_key") apiKey: String): Call<ListActors>


}

class MoviesService(api: MoviesApi): BaseService<MoviesApi>(api) {
  fun getUpcoming() = api.getUpcoming(BuildConfig.API_KEY, PostersFragment.page).executeUnsafe()
  fun getPopular() = api.getPopular(BuildConfig.API_KEY, PostersFragment.page).executeUnsafe()
  fun getTop() = api.getTop(BuildConfig.API_KEY).executeUnsafe()
  fun getDetails(id : String) = api.getDetails(id, BuildConfig.API_KEY).executeUnsafe()
  fun getCredits(id : String) = api.getCredits(id, BuildConfig.API_KEY).executeUnsafe()
}