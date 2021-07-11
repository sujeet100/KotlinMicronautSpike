package com.example.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import matchers.matchJson

@MicronautTest
class MoviesIntegrationTest(
    @Client("/") private val client: HttpClient,
    private val objectMapper: ObjectMapper
) : ShouldSpec({

    context("/movies") {
        should("return all the movies") {
            val response = client.toBlocking().exchange(
                GET<Any>("/movies"),
                String::class.java
            )
            response.status shouldBe HttpStatus.OK
            response.body()!! should matchJson(
                """
                [
                  {
                    "name": "Batman",
                    "year": 2021
                  },
                  {
                    "name": "Batman Returns",
                    "year": 2020
                  }
                ]
            """.trimIndent(), objectMapper
            )
        }
    }
})
