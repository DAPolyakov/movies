package com.nikita.movies_shmoovies.common.network

data class TvShow(val id: String,
                  val name: String,
                  val overview: String,
                  val first_air_date: String,
                  val poster_path: String,
                  val vote_average: String)