package com.multistream

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.extractors.*
import com.lagradost.cloudstream3.utils.*

class MultiStream : MainAPI() {
    override var name = "MultiStream"
    override var mainUrl = "https://example.com"
    override val supportedTypes = setOf(TvType.Movie)

    override suspend fun search(query: String): List<SearchResponse>? {
        return listOf(
            MovieSearchResponse(
                if (getLang() == "es") "Película falsa" else "Fake Movie",
                "https://example.com/fake_movie",
                this.name,
                TvType.Movie,
                "https://example.com/imagen.jpg"
            )
        )
    }

    override suspend fun load(url: String): LoadResponse? {
        val lang = getLang()
        return MovieLoadResponse(
            if (lang == "es") "Película falsa" else "Fake Movie",
            if (lang == "es") "Descripción falsa" else "Fake description",
            url,
            this.name,
            TvType.Movie,
            listOf(
                ExtractorLink(
                    this.name,
                    if (lang == "es") "Servidor Falso" else "Fake Server",
                    "https://example.com/video.mp4",
                    true
                )
            )
        )
    }
}
