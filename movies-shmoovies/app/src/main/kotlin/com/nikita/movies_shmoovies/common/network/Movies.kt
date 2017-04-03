package com.nikita.movies_shmoovies.common.network

import com.nikita.movies_shmoovies.info.Genre

data class Movie(val id: String,
                 val title: String,
                 val overview: String,
                 val release_date: String,
                 val poster_path: String?,
                 val backdrop_path: String?,
                 val vote_average : String)

data class MovieDetail(
        val id: String,
        val title: String,
        val overview: String?,
        val poster_path: String?,
        val backdrop_path: String?,
        val genres: List<Genre>,
        val release_date: String?,
        val original_title: String?,
        val status: String?,
        val original_language: String?,
        val runtime: String?,
        val budget: String?,
        val revenue: String?,
        val homepage: String?
)