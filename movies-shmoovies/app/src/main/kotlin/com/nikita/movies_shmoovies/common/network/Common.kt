package com.nikita.movies_shmoovies.common.network

import com.nikita.movies_shmoovies.info.Genre

data class ListResponse<out T>(val page: Int,
                               val results: List<T>,
                               val total_pages: Int,
                               val total_results: Int)

data class ListResponseInfo<out T>(
        val results: T
)
