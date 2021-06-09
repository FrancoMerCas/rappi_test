package com.franco.moviedb_rappi.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

class MovieDbAPIServiceTest {
    private lateinit var service : MovieDbAPIService
    private lateinit var server : MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDbAPIService::class.java)
    }


    private fun enqueueMockResponse(
        fileName : String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charset.defaultCharset()))
        server.enqueue(mockResponse)
    }

    //Test to movies
    @Test
    fun getPopularMovie_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("get_popular_movies_response.json")
            val responseBody = service.getPopularMovie("es-ES", 1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/movie/popular?language=es-ES&page=1&api_key=dda60bf8e7881795125f6d56ca13c597")
        }
    }

    @Test
    fun getPopularMovie_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("get_popular_movies_response.json")
            val responseBody = service.getPopularMovie("es-ES", 1).body()
            val movieList = responseBody!!.results

            assertThat(movieList.size).isEqualTo(20)
        }
    }

    @Test
    fun getPopularMovie_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("get_popular_movies_response.json")
            val responseBody = service.getPopularMovie("es-ES", 1).body()
            val movieList = responseBody!!.results
            val movie = movieList[0]

            assertThat(movie.adult).isFalse()
            assertThat(movie.backdrop_path).isEqualTo("/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg")
            assertThat(movie.id).isEqualTo(337404)
            assertThat(movie.original_language).isEqualTo("en")
            assertThat(movie.original_title).isEqualTo("Cruella")
            assertThat(movie.poster_path).isEqualTo("/5bsApWAUqb0jeXtHPjKQ6MZOtZJ.jpg")
            assertThat(movie.release_date).isEqualTo("2021-05-26")
            assertThat(movie.title).isEqualTo("Cruella")
        }
    }

    //Test to Tv
    @Test
    fun getPopularTv_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("get_popular_tv_response.json")
            val responseBody = service.getPopularTv( "es-ES", 1).body()
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/tv/popular?language=es-ES&page=1&api_key=dda60bf8e7881795125f6d56ca13c597")
        }
    }

    @Test
    fun getPopularTv_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("get_popular_tv_response.json")
            val responseBody = service.getPopularTv("es-ES", 1).body()
            val tvList = responseBody!!.results

            assertThat(tvList.size).isEqualTo(20)
        }
    }

    @Test
    fun getPopularTv_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("get_popular_tv_response.json")
            val responseBody = service.getPopularTv("es-ES", 1).body()
            val tvList = responseBody!!.results
            val tv = tvList[0]

            assertThat(tv.backdrop_path).isEqualTo("/h48Dpb7ljv8WQvVdyFWVLz64h4G.jpg")
            assertThat(tv.first_air_date).isEqualTo("2016-01-25")
            assertThat(tv.id).isEqualTo(63174)
            assertThat(tv.name).isEqualTo("Lucifer")
            assertThat(tv.original_language).isEqualTo("en")
            assertThat(tv.original_name).isEqualTo("Lucifer")
            assertThat(tv.popularity).isEqualTo(1804.904)
            assertThat(tv.poster_path).isEqualTo("/mzQf0QAs4jz0fDMrzFlZxQvC9KT.jpg")
        }
    }


    //Test to search
    @Test
    fun getSearch_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("get_search_response.json")
            val responseBody = service.getSearch("spider", 1, "es-ES").body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/search/multi?query=spider&page=1&language=es-ES&api_key=dda60bf8e7881795125f6d56ca13c597")
        }
    }

    @Test
    fun getSearch_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("get_search_response.json")
            val responseBody = service.getPopularMovie("es-ES", 1).body()
            val tvList = responseBody!!.results
            assertThat(tvList.size).isEqualTo(20)
        }
    }

    @Test
    fun getSearch_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("get_search_response.json")
            val responseBody = service.getSearch("spider", 1, "es-ES").body()
            val resultList = responseBody!!.results
            val element = resultList[0]

            assertThat(element.adult).isFalse()
            assertThat(element.backdrop_path).isEqualTo("/muth4OYamXf41G2evdrLEg8d3om.jpg")
            assertThat(element.id).isEqualTo(557)
            assertThat(element.media_type).isEqualTo("movie")
            assertThat(element.original_language).isEqualTo("en")
            assertThat(element.original_title).isEqualTo("Spider-Man")
            assertThat(element.popularity).isEqualTo(65.826)
            assertThat(element.poster_path).isEqualTo("/bCDaKZLRkrVVtPNyHxUfKvepW1N.jpg")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}