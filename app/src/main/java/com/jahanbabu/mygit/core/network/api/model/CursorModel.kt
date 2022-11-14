package com.jahanbabu.mygit.core.network.api.model

import androidx.annotation.Keep

@Keep
interface CursorModel<Entity> {

    val items: List<Entity>

    val cursor: Long?
}
