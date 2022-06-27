package com.urlshortener.controller

import com.urlshortener.service.UrlShortenerService
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/url")
class UrlShortenerController(val urlShortenerService: UrlShortenerService) {

    @PostMapping("/shorten", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun shortenUrl(
        @RequestBody url: String
    ): ResponseEntity<Map<String, String>> {
        val urlValidator = UrlValidator(arrayOf("http", "https"))
        return if (urlValidator.isValid(url))
            ResponseEntity.ok(mapOf("shortUrl" to urlShortenerService.shortenUrl(url)))
        else
            ResponseEntity.badRequest().body(mapOf("error" to "Invalid URL"))
    }

    @GetMapping("/resolve")
    fun getResolvedUrl(
        @RequestParam id: String
    ): ResponseEntity<Map<String, String>> {

        return urlShortenerService.getOriginalUrl(id)
            ?.let { ResponseEntity.ok(mapOf("originalUrl" to it)) }
            ?: ResponseEntity.notFound().build()
    }
}
