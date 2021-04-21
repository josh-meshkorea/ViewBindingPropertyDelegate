@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionLazyAnyBindings")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

@JvmName("lazyViewBindingAny")
public inline fun <reified T : ViewBinding> Any.lazyViewBinding(crossinline viewProvider: (Any) -> View) =
    lazyViewBinding(T::class.java) { viewProvider(it) }

@JvmName("lazyViewBindingAny")
public fun <T : ViewBinding> Any.lazyViewBinding(
    viewBindingClass: Class<T>,
    viewProvider: (Any) -> View
): ViewBindingProperty<RecyclerView.ViewHolder, T> {
    return lazyViewBinding<Any, T> { ViewBindingCache.getBind(viewBindingClass).bind(viewProvider(it)) }
}
