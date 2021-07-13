package com.example.service

import com.example.model.Movie
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class MoviesService() {
    suspend fun getMovies(): List<Movie> {
        return listOf(Movie("Batman", 2021), Movie("Batman Returns", 2020))
    }

    fun getMoviesReactive(): Single<List<Movie>> {
        return Single.just(listOf(Movie("Batman", 2021), Movie("Batman Returns", 2020)));
    }

}
