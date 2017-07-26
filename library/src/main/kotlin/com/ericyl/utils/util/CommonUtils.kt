package com.ericyl.utils.util

/**
 * Created by ericyl on 2017/7/26.
 */
fun <T, R1, R2> Array<T>.doIsOrNotEmptyAction(notEmptyAction: (Array<T>) -> R1, emptyAction: (Array<T>) -> R2) {
    if (isEmpty()) {
        emptyAction(this)
    } else notEmptyAction(this)
}


fun <T, R> Array<T>.doEmptyAction(emptyAction: (Array<T>) -> R): Array<T> {
    if (isEmpty()) {
        emptyAction(this)
    }
    return this
}

fun <T, R> Array<T>.doNotEmptyAction(notEmptyAction: (Array<T>) -> R): Array<T> {
    if (isNotEmpty()) {
        notEmptyAction(this)
    }
    return this
}
