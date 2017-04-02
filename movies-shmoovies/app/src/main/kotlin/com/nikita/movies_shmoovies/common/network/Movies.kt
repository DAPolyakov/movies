package com.nikita.movies_shmoovies.common.network

data class Movie(val id: String,
                 val title: String,
                 val overview: String,
                 val release_date: String,
                 val poster_path: String?,
                 val backdrop_path: String?,
                 val vote_average : String)