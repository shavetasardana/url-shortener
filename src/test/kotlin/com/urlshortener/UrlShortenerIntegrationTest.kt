package com.urlshortener

import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlShortenerIntegrationTest {

    @Autowired
    lateinit var server: ServletWebServerApplicationContext

    @BeforeEach
    fun setUp() {
        RestAssured.port = server.webServer.port
    }

    @Test
    fun `should create short url and get original url`() {
        val originalUrl = "http://www.test-url-shortener.com"
        val shortUrl = RestAssured.given()
            .body(originalUrl)
            .`when`()
            .post("/url/shorten")
            .then()
            .assertThat().statusCode(200)
            .extract().path<String>("shortUrl")

        RestAssured.given()
            .param("id", shortUrl)
            .`when`()
            .get("/url/resolve")
            .then()
            .assertThat().statusCode(200)
            .body("originalUrl", Matchers.`is`(originalUrl))
    }

    @Test
    fun `should fail for invalid url shorten request`() {
        val invalidUrl = "www.test.com"
        RestAssured.given()
            .body(invalidUrl)
            .`when`()
            .post("/url/shorten")
            .then()
            .assertThat().statusCode(400)
            .body("error", Matchers.`is`("Invalid URL"))
    }
}
