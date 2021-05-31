package com.darotpeacedude.core.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

/**
 * Get any name
 * @return
 */
fun Any.getName(): String = this::class.qualifiedName!!

/**
 * Hide view
 *
 */
fun View.hide(): Boolean {
    if (this.visibility == View.VISIBLE || this.visibility == View.INVISIBLE) {
        this.visibility = View.GONE
        return true
    }
    return false
}

/**
 * Show view
 *
 */
fun View.show(): Boolean {
    if (this.visibility == View.INVISIBLE || this.visibility == View.GONE) {
        this.visibility = View.VISIBLE
        return true
    }
    return false
}
/**
 * Navigate to destination id
 *
 * @param destinationId
 */
fun Fragment.goto(destinationId: Int) {
    findNavController().navigate(destinationId)
}

/**
 * Navigate to destination id
 *
 * @param destinationId
 */
fun Fragment.goto(destinationId: NavDirections) {
    findNavController().navigate(destinationId)
}

/**
 * Navigate up
 *
 */
fun Fragment.gotoUp() {
    findNavController().navigateUp()
}
