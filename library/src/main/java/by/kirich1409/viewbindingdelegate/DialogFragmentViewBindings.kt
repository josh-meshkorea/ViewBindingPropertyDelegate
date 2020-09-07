@file:Suppress("RedundantVisibilityModifier", "unused")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.DialogFragmentViewBinder


@PublishedApi
internal class DialogFragmentViewBindingProperty<F : DialogFragment, T : ViewBinding>(
    viewBinder: (F) -> T
) : ViewBindingProperty<F, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
        return if (thisRef.view == null) thisRef.viewLifecycleOwner else thisRef
    }
}

/**
 * Create new [ViewBinding] associated with the [DialogFragment]
 */
@JvmName("viewBindingDialogFragment")
public fun <F : DialogFragment, T : ViewBinding> DialogFragment.dialogViewBinding(
    viewBinder: (F) -> T
): ViewBindingProperty<F, T> {
    return DialogFragmentViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [DialogFragment]'s view
 *
 * @param viewBindingRootId Id of the root view from your custom view
 */
@JvmName("viewBindingDialogFragment")
public inline fun <reified T : ViewBinding> DialogFragment.dialogViewBinding(
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<DialogFragment, T> {
    return dialogViewBinding(DialogFragmentViewBinder(T::class.java, viewBindingRootId)::bind)
}

/**
 * Create new [ViewBinding] associated with the [DialogFragment]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 */
@JvmName("viewBindingDialogFragment")
public inline fun <F : DialogFragment, T : ViewBinding> DialogFragment.dialogViewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (F) -> View
): ViewBindingProperty<F, T> {
    return DialogFragmentViewBindingProperty { fragment -> vbFactory(viewProvider(fragment)) }
}

/**
 * Create new [ViewBinding] associated with the [DialogFragment][this]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Id of the root view from your custom view
 */
@Suppress("unused")
@JvmName("viewBindingDialogFragment")
public inline fun <T : ViewBinding> DialogFragment.dialogViewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<DialogFragment, T> {
    return viewBinding(vbFactory) { fragment: DialogFragment ->
        fragment.dialog!!.window!!.decorView.findViewById(viewBindingRootId)
    }
}
