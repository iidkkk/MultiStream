package com.miextension.animeflv

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.MainAPI

class AnimeFLV : MainAPI() {
    override var mainUrl = "https://www3.animeflv.net"
    override var name = "AnimeFLV"
    override val hasMainPage = true
    override val supportedTypes = setOf(TvType.Anime)

    // Aquí implementaremos búsqueda, carga de episodios, etc.
}
