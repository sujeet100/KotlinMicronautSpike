package com.example.service

import com.example.model.Movie
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class MoviesServiceTest : ShouldSpec({
    context("get movies") {
        should("return movies") {
            val moviesService = MoviesService()
            val movies = moviesService.getMovies()
            movies shouldBe listOf(Movie("Batman", 2021), Movie("Batman Returns", 2020))
        }
    }
})
