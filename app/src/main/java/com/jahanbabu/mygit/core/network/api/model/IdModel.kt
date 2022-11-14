package com.jahanbabu.mygit.core.network.api.model

import androidx.annotation.Keep

// Models must be data class
@Keep
interface IdModel<Id> {

    val id: Id
}
