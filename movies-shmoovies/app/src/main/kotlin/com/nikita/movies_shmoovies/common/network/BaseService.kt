package com.nikita.movies_shmoovies.common.network

import com.squareup.moshi.JsonReader
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseService<out Api>(protected val api: Api) {

  protected fun <T> Call<T>.executeUnsafe(): T {
    val response = execute()
    if (response.isSuccessful) {
      return response.body()
    } else {
      throw extractError(response)
    }
  }
}

private fun <T> extractError(response: Response<T>): Exception {
  val httpException = HttpException(response)
  val reader = JsonReader.of(response.errorBody().source())
  reader.beginObject()
  while (reader.hasNext()) {
    val name = reader.nextName()
    if (name == "status_message") {
      return ApiError(reader.nextString(), httpException)
    }
  }
  reader.endObject()
  return httpException
}

class ApiError(message: String, causeException: Throwable? = null) : RuntimeException(message, causeException)