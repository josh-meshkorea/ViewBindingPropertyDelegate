@file:Suppress("unused")
@file:JvmName("viewBindingLayoutContainer")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import kotlinx.android.extensions.LayoutContainer

/**
 * Create new [ViewBinding] associated with the [LayoutContainer]
 */
@JvmName("viewBindingLayoutContainer")
fun <LC : LayoutContainer, T : ViewBinding> LC.viewBinding(viewBinder: (LC) -> T): ViewBindingProperty<LC, T> {
    return LazyViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [LayoutContainer]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the [LayoutContainer]. By default call [LayoutContainer.containerView]
 */
@JvmName("viewBindingLayoutContainer")
inline fun <LC : LayoutContainer, T : ViewBinding> LC.viewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (LC) -> View = { it.containerView ?: throw NullPointerException() }
): ViewBindingProperty<LC, T> {
    return viewBinding { layoutContainer: LC -> vbFactory(viewProvider(layoutContainer)) }
}

/**
 * Create new [ViewBinding] associated with the [LayoutContainer]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@JvmName("viewBindingLayoutContainer")
inline fun <LC : LayoutContainer, T : ViewBinding> LC.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<LC, T> {
    return viewBinding { layoutContainer: LC ->
        vbFactory(layoutContainer.containerView?.findViewById(viewBindingRootId) ?: throw NullPointerException())
    }
}
