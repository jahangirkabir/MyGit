package com.jahanbabu.mygit.core.navigation

import android.os.Bundle
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions

sealed class NavEvent(
    val options: NavOptions? = null,
    val clearBackStack: Boolean = false
) {

    open class Id(
        @IdRes val id: Int,
        val arguments: Bundle? = null,
        options: NavOptions? = null,
        clearGraph: Boolean = false
    ) : NavEvent(options, clearGraph)

    open class Attribute(
        @AttrRes val attr: Int,
        val arguments: Bundle? = null,
        options: NavOptions? = null,
        clearGraph: Boolean = false
    ) : NavEvent(options, clearGraph)

    open class Result(
        @IdRes val destinationId: Int,
        val results: Map<String, Any>,
        options: NavOptions? = null,
        clearGraph: Boolean = false
    ) : NavEvent(options, clearGraph)

    object Up : NavEvent()

    open class NavControllerEvent(
        val navController: NavController,
        val event: NavEvent
    ) : NavEvent()
}

fun NavEvent.withNavController(navController: NavController?) =
    if (navController != null) {
        NavEvent.NavControllerEvent(
            navController = navController,
            event = this
        )
    } else this
