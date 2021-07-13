package com.example.controller

import com.example.model.Movie
import com.example.service.MoviesService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@Controller
class MoviesController(private val moviesService: MoviesService) {
    @Get("/movies")
    suspend fun getMovies(): List<Movie> = coroutineScope {
        val movies = async { moviesService.getMovies() }
        movies.await()
    }
}
