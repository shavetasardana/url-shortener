package com.urlshortener.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.urlshortener.model.UrlInfo
import com.urlshortener.repository.UrlInfoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class UrlShortenerServiceTest {

    companion object {
        val urlInfo = UrlInfo(
            shortUrl = "http://shortUrl/1",
            originalUrl = "http://www.test-url-shortener.com"
        )
    }

    private lateinit var urlShortenerService: UrlShortenerService

    @Mock
    private lateinit var urlInfoRepository: UrlInfoRepository

    @BeforeEach
    fun setUp() {

        urlShortenerService =
            UrlShortenerService(
                urlInfoRepository
            )
    }

    @Test
    fun `should shorten the URL when matching URL doesnt exist in DB`() {
        whenever(urlInfoRepository.save(any<UrlInfo>())).thenReturn(urlInfo)
        val result = urlShortenerService.shortenUrl(urlInfo.originalUrl)
        assertEquals(urlInfo.shortUrl, result)
        verify(urlInfoRepository, times(1)).findByOriginalUrl(any())
        verify(urlInfoRepository, times(1)).save(any())
    }

    @Test
    fun `should return the shorten URL when matching URL exist in DB`() {
        whenever(urlInfoRepository.findByOriginalUrl(any())).thenReturn(urlInfo)
        val result = urlShortenerService.shortenUrl(urlInfo.originalUrl)
        assertEquals(urlInfo.shortUrl, result)
        verify(urlInfoRepository, times(1)).findByOriginalUrl(any())
        verify(urlInfoRepository, never()).save(any())
    }

    @Test
    fun `should return short URL when matching URL found in DB`() {
        whenever(urlInfoRepository.findByShortUrl(any())).thenReturn(urlInfo)
        val result = urlShortenerService.getOriginalUrl(urlInfo.shortUrl)
        assertEquals(urlInfo.originalUrl, result)
        verify(urlInfoRepository, times(1)).findByShortUrl(any())
    }
}
