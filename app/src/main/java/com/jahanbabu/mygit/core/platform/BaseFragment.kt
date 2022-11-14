package com.jahanbabu.mygit.core.platform


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.jahanbabu.mygit.core.extension.resolveAttribute
import com.jahanbabu.mygit.core.navigation.NavEvent


abstract class BaseFragment<T : ViewBinding>(private val bindingInflater: (layoutInflater:LayoutInflater) -> T) : Fragment() {
    private var _binding : T? = null
    val binding get() = _binding!!
    abstract fun layoutId(): Int

    private lateinit var viewBinding: View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    abstract fun init()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected open fun navigateToId(
        @IdRes id: Int,
        arguments: Bundle? = null,
        options: NavOptions? = null,
        clearGraph: Boolean = false,
        navController: NavController = findNavController()
    ) {
        val navEvent = NavEvent.Id(
            id = id,
            arguments = arguments,
            options = options,
            clearGraph = clearGraph
        )
        navigateTo(navEvent, findNavController())
    }

    protected open fun navigateToAttribute(
        @AttrRes attr: Int,
        arguments: Bundle? = null,
        options: NavOptions? = null,
        clearGraph: Boolean = false,
        navController: NavController = findNavController()
    ) {
        val navEvent = NavEvent.Attribute(
            attr = attr,
            arguments = arguments,
            options = options,
            clearGraph = clearGraph
        )

        navigateTo(navEvent, navController)
    }

    protected open fun navigateForResult(
        @IdRes destinationId: Int,
        results: Map<String, Any>,
        options: NavOptions? = null,
        clearGraph: Boolean = false,
        navController: NavController = findNavController()
    ) {
        val navEvent = NavEvent.Result(
            destinationId = destinationId,
            results = results,
            options = options,
            clearGraph = clearGraph
        )
        navigateTo(navEvent, navController)
    }
    open fun onBackPressed() {
        findNavController().popBackStack()
    }

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    protected open fun navigateTo(navEvent: NavEvent, navController: NavController) {
        if (navEvent.clearBackStack) {
            while (navController.popBackStack()) {
                /*
                 * Pops the backstack until the entire stack is cleared. This is used when root
                 * destination is unknown, for example in library modules.
                 */
            }
        }
        when (navEvent) {
            is NavEvent.Id -> navigate(
                navEvent.id,
                navEvent.arguments,
                navEvent.options,
                navController
            )

            is NavEvent.Attribute -> {
                val id = requireActivity().resolveAttribute(navEvent.attr)
                if (id != Int.MIN_VALUE) {
                    navigate(id, navEvent.arguments, navEvent.options, navController)
                }
            }
            is NavEvent.Result -> {
                navController.getBackStackEntry(navEvent.destinationId).savedStateHandle.apply {
                    navEvent.results.forEach { (key, value) ->
                        set(key, value)
                    }
                }
                navController.navigateUp()
            }
            is NavEvent.Up -> navController.navigateUp()
            is NavEvent.NavControllerEvent -> navigateTo(navEvent.event, navEvent.navController)
        }
    }

    private fun navigate(
        id: Int,
        argument: Bundle?,
        navOptions: NavOptions?,
        navController: NavController
    ) {
        val destination = navController.findDestination(id)
        val action = navController.currentDestination?.getAction(id)
        if (destination != null || action != null) {
            navController.navigate(
                id,
                argument,
                navOptions
            )
        }
    }

    internal fun notify(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

}
