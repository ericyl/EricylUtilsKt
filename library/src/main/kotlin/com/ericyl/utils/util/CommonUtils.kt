package com.ericyl.utils.util

/**
 * Created by ericyl on 2017/7/26.
 */
fun <T, R1, R2> Array<T>.doIsOrNotEmptyAction(notEmptyAction: (Array<T>) -> R1, emptyAction: (Array<T>) -> R2) {
    if (isEmpty())
        emptyAction(this)
    else notEmptyAction(this)
}


fun <T, R> Array<T>.doEmptyAction(action: (Array<T>) -> R): Array<T> {
    if (isEmpty())
        action(this)
    return this
}

fun <T, R> Array<T>.doNotEmptyAction(action: (Array<T>) -> R): Array<T> {
    if (isNotEmpty())
        action(this)
    return this
}

inline fun <reified T: Any>T.setByFiled(name: String, value: Any): T{
    val field  = this.javaClass.getDeclaredField(name)
    field.isAccessible = true
    field.set(this, value)
    return this
}