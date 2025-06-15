package com.miextension

import android.content.Context
import com.lagradost.cloudstream3.*
import com.miextension.animeflv.AnimeFLV

@Plugin
class Main {
    fun load(context: Context) {
        registerMainAPI(AnimeFLV())
    }
}
