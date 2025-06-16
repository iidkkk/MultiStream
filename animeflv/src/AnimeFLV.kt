package com.multistream.animeflv

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.loadExtractor
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class AnimeFLV : MainAPI() {
    override var name = "AnimeFLV"
    override var mainUrl = "https://www3.animeflv.net"
    override val supportedTypes = setOf(TvType.Anime)

    override suspend fun search(query: String): List<SearchResponse> {
        val url = "$mainUrl/buscar/$query"
        val doc = app.get(url).document
        val items = doc.select(".Anime.alt.B").map {
            val title = it.selectFirst(".Title")?.text() ?: return@map null
            val link = it.selectFirst("a")?.attr("href") ?: return@map null
            val poster = it.selectFirst("img")?.attr("src") ?: ""
            newAnimeSearchResponse(title, "$mainUrl$link") {
                this.posterUrl = poster
            }
        }
        return items.filterNotNull()
    }

    override suspend fun load(url: String): LoadResponse {
        val doc = app.get(url).document
        val title = doc.selectFirst(".Ficha.fchlt h1")?.text() ?: "Sin título"
        val poster = doc.selectFirst(".Image img")?.attr("src")
        val description = doc.selectFirst(".Description")?.text() ?: ""
        val episodes = doc.select("#episodeList li").mapNotNull { ep ->
            val epNum = ep.attr("data-episode")
            val epUrl = ep.selectFirst("a")?.attr("href")
            if (epUrl != null)
                Episode("$mainUrl$epUrl", epNum)
            else null
        }.reversed()

        return newAnimeLoadResponse(title, url, TvType.Anime) {
            this.posterUrl = poster
            this.plot = description
            addEpisodes(DubStatus.Subbed, episodes)
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val doc = app.get(data).document
        val iframe = doc.selectFirst("iframe")?.attr("src") ?: return false
        return loadExtractor(iframe, data, subtitleCallback, callback)
    }
}
