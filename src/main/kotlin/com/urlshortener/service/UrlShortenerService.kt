package com.urlshortener.service

import com.google.common.hash.Hashing
import com.urlshortener.model.UrlInfo
import com.urlshortener.repository.UrlInfoRepository
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Service
class UrlShortenerService(
    val urlInfoRepository: UrlInfoRepository
) {
    companion object {
        const val domain = "https://dkb/"
    }

    fun shortenUrl(url: String): String {
        return urlInfoRepository.findByOriginalUrl(url)
            ?.shortUrl
            ?: run {
                createShortUrl(url)
            }
    }

    private fun createShortUrl(url: String): String {
        val shortUrl = Hashing.murmur3_32_fixed().hashString(url, StandardCharsets.UTF_8).toString()
        val urlInfo = urlInfoRepository.save(
            UrlInfo(
                shortUrl = domain.plus(shortUrl),
                originalUrl = url
            )
        )
        return urlInfo.shortUrl
    }

    fun getOriginalUrl(shortUrl: String): String? {
        return urlInfoRepository.findByShortUrl(shortUrl)?.originalUrl
    }
}
