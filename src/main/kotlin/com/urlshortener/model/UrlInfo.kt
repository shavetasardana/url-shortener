package com.urlshortener.model

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Table

@Entity
@Table(name = "url_info")
data class UrlInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val Id: Long? = null,
    val shortUrl: String,
    val originalUrl: String,
    var createdAt: Instant? = null
) {
    @PrePersist
    fun prePersist() {
        createdAt = Instant.now()
    }
}
