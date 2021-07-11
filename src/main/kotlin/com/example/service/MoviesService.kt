package com.example.service

import com.example.model.Movie
import javax.inject.Singleton

@Singleton
class MoviesService {
    suspend fun getMovies(): List<Movie> {
        return listOf(Movie("Batman", 2021), Movie("Batman Returns", 2020))
    }

}
