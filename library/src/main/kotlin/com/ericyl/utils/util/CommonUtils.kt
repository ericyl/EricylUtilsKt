package com.ericyl.utils.util

/**
 * @author ericyl
 */

fun <R1, R2> Boolean.doIsOrNotAction(action: () -> R2, notAction: () -> R1) {
    if (this)
        action()
    else notAction()
}

fun <T, R1, R2> Array<T>.doIsOrNotEmptyAction(emptyAction: (Array<T>) -> R2, notEmptyAction: (Array<T>) -> R1) {
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

inline fun <reified T : Any> T.setByFiled(name: String, value: Any): T {
    val field = this.javaClass.getDeclaredField(name)
    field.isAccessible = true
    field.set(this, value)
    return this
}