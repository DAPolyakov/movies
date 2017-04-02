package com.nikita.movies_shmoovies.common.network

import com.nikita.movies_shmoovies.info.Genre

data class ListResponse<out T>(val page: Int,
                               val results: List<T>,
                               val total_pages: Int,
                               val total_results: Int)

data class ListResponseInfo(
        val id: String,
        val title: String,
        val overview: String?,
        val poster_path: String?,
        val backdrop_path: String?,
        val genres: List<Genre>,
        val release_date: String?,
        val original_title : String?,
        val status : String?,
        val original_language: String?,
        val runtime: String?,
        val budget: String?,
        val revenue: String?,
        val homepage: String?
)
