@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionLayoutContainerBindings")

package by.kirich1409.viewbindingdelegate

import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache
import kotlinx.android.extensions.LayoutContainer

/**
 * Create new [ViewBinding] associated with the [LayoutContainer]
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingLayoutContainer")
public inline fun <reified T : ViewBinding> LayoutContainer.viewBinding() = viewBinding(T::class.java)

/**
 * Create new [ViewBinding] associated with the [LayoutContainer]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingLayoutContainer")
public fun <T : ViewBinding> LayoutContainer.viewBinding(
    viewBindingClass: Class<T>,
): ViewBindingProperty<LayoutContainer, T> {
    return viewBinding {
        ViewBindingCache.getBind(viewBindingClass).bind(containerView ?: throw NullPointerException())
    }
}
