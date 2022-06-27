package com.urlshortener.repository

import com.urlshortener.model.UrlInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlInfoRepository : JpaRepository<UrlInfo, Long> {
    fun findByOriginalUrl(originalUrl: String): UrlInfo?
    fun findByShortUrl(shortUrl: String): UrlInfo?
}
