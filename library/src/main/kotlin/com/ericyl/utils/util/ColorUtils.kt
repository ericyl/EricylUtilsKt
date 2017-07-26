package com.ericyl.utils.util

import java.util.*

private object ColorUtils {

    const val COLOR_TRANSPARENT = 0x00000000
    const val COLOR_LIGHT_RED_BRIGHT = 0xffff5252.toInt()
    const val COLOR_RED_BRIGHT = 0xffff1744.toInt()
    const val COLOR_DARK_RED_BRIGHT = 0xffd50000.toInt()

    const val COLOR_LIGHT_RED = 0xffe57373.toInt()
    const val COLOR_RED = 0xfff44336.toInt()
    const val COLOR_DARK_RED = 0xffd32f2f.toInt()

    const val COLOR_LIGHT_PINK_BRIGHT = 0xffff4081.toInt()
    const val COLOR_PINK_BRIGHT = 0xfff50057.toInt()
    const val COLOR_DARK_PINK_BRIGHT = 0xffc51162.toInt()

    const val COLOR_LIGHT_PINK = 0xfff06292.toInt()
    const val COLOR_PINK = 0xffe91e63.toInt()
    const val COLOR_DARK_PINK = 0xffc2185b.toInt()

    const val COLOR_LIGHT_PURPLE_BRIGHT = 0xffe040fb.toInt()
    const val COLOR_PURPLE_BRIGHT = 0xffd500f9.toInt()
    const val COLOR_DARK_PURPLE_BRIGHT = 0xffaa00ff.toInt()

    const val COLOR_LIGHT_PURPLE = 0xffba68c8.toInt()
    const val COLOR_PURPLE = 0xff9c27b0.toInt()
    const val COLOR_DARK_PURPLE = 0xff7b1fa2.toInt()

    const val COLOR_LIGHT_DEEP_PURPLE_BRIGHT = 0xff7c4dff.toInt()
    const val COLOR_DEEP_PURPLE_BRIGHT = 0xff651fff.toInt()
    const val COLOR_DARK_DEEP_PURPLE_BRIGHT = 0xff6200ea.toInt()

    const val COLOR_LIGHT_DEEP_PURPLE = 0xff9575cd.toInt()
    const val COLOR_DEEP_PURPLE = 0xff673ab7.toInt()
    const val COLOR_DARK_DEEP_PURPLE = 0xff512da8.toInt()

    const val COLOR_LIGHT_INDIGO_BRIGHT = 0xff536dfe.toInt()
    const val COLOR_INDIGO_BRIGHT = 0xff3d5afe.toInt()
    const val COLOR_DARK_INDIGO_BRIGHT = 0xff304ffe.toInt()

    const val COLOR_LIGHT_INDIGO = 0xff7986cb.toInt()
    const val COLOR_INDIGO = 0xff3f51b5.toInt()
    const val COLOR_DARK_INDIGO = 0xff303f9f.toInt()

    const val COLOR_LIGHT_BLUE_BRIGHT = 0xff448aff.toInt()
    const val COLOR_BLUE_BRIGHT = 0xff2979ff.toInt()
    const val COLOR_DARK_BLUE_BRIGHT = 0xff2962ff.toInt()

    const val COLOR_LIGHT_BLUE = 0xff64b5f6.toInt()
    const val COLOR_BLUE = 0xff2196f3.toInt()
    const val COLOR_DARK_BLUE = 0xff1976d2.toInt()

    const val COLOR_LIGHT_BLUE_L_BRIGHT = 0xff40c4ff.toInt()
    const val COLOR_BLUE_L_BRIGHT = 0xff00b0ff.toInt()
    const val COLOR_DARK_BLUE_L_BRIGHT = 0xff0091ea.toInt()

    const val COLOR_LIGHT_BLUE_L = 0xff4fc3f7.toInt()
    const val COLOR_BLUE_L = 0xff03a9f4.toInt()
    const val COLOR_DARK_BLUE_L = 0xff0288d1.toInt()

    const val COLOR_LIGHT_CYAN_BRIGHT = 0xff18ffff.toInt()
    const val COLOR_CYAN_BRIGHT = 0xff00e5ff.toInt()
    const val COLOR_DARK_CYAN_BRIGHT = 0xff00b8d4.toInt()

    const val COLOR_LIGHT_CYAN = 0xff4dd0e1.toInt()
    const val COLOR_CYAN = 0xff00bcd4.toInt()
    const val COLOR_DARK_CYAN = 0xff0097a7.toInt()

    const val COLOR_LIGHT_TEAL_BRIGHT = 0xff64ffda.toInt()
    const val COLOR_TEAL_BRIGHT = 0xff1de9b6.toInt()
    const val COLOR_DARK_TEAL_BRIGHT = 0xff00bfa5.toInt()

    const val COLOR_LIGHT_TEAL = 0xff4db6ac.toInt()
    const val COLOR_TEAL = 0xff009688.toInt()
    const val COLOR_DARK_TEAL = 0xff00796b.toInt()

    const val COLOR_LIGHT_GREEN_BRIGHT = 0xff69f0ae.toInt()
    const val COLOR_GREEN_BRIGHT = 0xff00e676.toInt()
    const val COLOR_DARK_GREEN_BRIGHT = 0xff00c853.toInt()

    const val COLOR_LIGHT_GREEN = 0xff81c784.toInt()
    const val COLOR_GREEN = 0xff4caf50.toInt()
    const val COLOR_DARK_GREEN = 0xff388e3c.toInt()

    const val COLOR_LIGHT_GREEN_L_BRIGHT = 0xffb2ff59.toInt()
    const val COLOR_GREEN_L_BRIGHT = 0xff76ff03.toInt()
    const val COLOR_DARK_GREEN_L_BRIGHT = 0xff64dd17.toInt()

    const val COLOR_LIGHT_GREEN_L = 0xffaed581.toInt()
    const val COLOR_GREEN_L = 0xff8bc34a.toInt()
    const val COLOR_DARK_GREEN_L = 0xff689f38.toInt()

    const val COLOR_LIGHT_LIME_BRIGHT = 0xffeeff41.toInt()
    const val COLOR_LIME_BRIGHT = 0xffc6ff00.toInt()
    const val COLOR_DARK_LIME_BRIGHT = 0xffaeea00.toInt()

    const val COLOR_LIGHT_LIME = 0xffdce775.toInt()
    const val COLOR_LIME = 0xffcddc39.toInt()
    const val COLOR_DARK_LIME = 0xffafb42b.toInt()

    const val COLOR_LIGHT_YELLOW_BRIGHT = 0xffffff00.toInt()
    const val COLOR_YELLOW_BRIGHT = 0xffffea00.toInt()
    const val COLOR_DARK_YELLOW_BRIGHT = 0xffffd600.toInt()

    const val COLOR_LIGHT_YELLOW = 0xfffff176.toInt()
    const val COLOR_YELLOW = 0xffffeb3b.toInt()
    const val COLOR_DARK_YELLOW = 0xfffbc02d.toInt()

    const val COLOR_LIGHT_AMBER_BRIGHT = 0xffffd740.toInt()
    const val COLOR_AMBER_BRIGHT = 0xffffc400.toInt()
    const val COLOR_DARK_AMBER_BRIGHT = 0xffffab00.toInt()

    const val COLOR_LIGHT_AMBER = 0xffffd54f.toInt()
    const val COLOR_AMBER = 0xffffc107.toInt()
    const val COLOR_DARK_AMBER = 0xffffa000.toInt()

    const val COLOR_LIGHT_ORANGE_BRIGHT = 0xffffab40.toInt()
    const val COLOR_ORANGE_BRIGHT = 0xffff9100.toInt()
    const val COLOR_DARK_ORANGE_BRIGHT = 0xffff6d00.toInt()

    const val COLOR_LIGHT_ORANGE = 0xffffb74d.toInt()
    const val COLOR_ORANGE = 0xffff9800.toInt()
    const val COLOR_DARK_ORANGE = 0xfff57c00.toInt()

    const val COLOR_LIGHT_DEEP_ORANGE_BRIGHT = 0xffff6e40.toInt()
    const val COLOR_DEEP_ORANGE_BRIGHT = 0xffff3d00.toInt()
    const val COLOR_DARK_DEEP_ORANGE_BRIGHT = 0xffdd2c00.toInt()

    const val COLOR_LIGHT_DEEP_ORANGE = 0xffff8a65.toInt()
    const val COLOR_DEEP_ORANGE = 0xffff5722.toInt()
    const val COLOR_DARK_DEEP_ORANGE = 0xffe64a19.toInt()

    const val COLOR_LIGHT_BROWN = 0xffa1887f.toInt()
    const val COLOR_BROWN = 0xff795548.toInt()
    const val COLOR_DARK_BROWN = 0xff5d4037.toInt()

    const val COLOR_LIGHT_GREY = 0xffe0e0e0.toInt()
    const val COLOR_GREY = 0xff9e9e9e.toInt()
    const val COLOR_DARK_GREY = 0xff616161.toInt()

    const val COLOR_LIGHT_BLUE_GREY = 0xff90a4ae.toInt()
    const val COLOR_BLUE_GREY = 0xff607d8b.toInt()
    const val COLOR_DARK_BLUE_GREY = 0xff455a64.toInt()

    const val COLOR_LIGHT_GRAY = 0xffefefef.toInt()
    const val COLOR_GRAY = 0xffcecece.toInt()
    const val COLOR_DARK_GRAY = 0xffc8c8c8.toInt()

    const val COLOR_WHITE = 0xffffffff.toInt()
    const val COLOR_BLACK = 0xff000000.toInt()

    internal val COLORS = intArrayOf(COLOR_BLUE, COLOR_ORANGE, COLOR_GREEN, COLOR_RED, COLOR_GREY, COLOR_PURPLE, COLOR_PINK, COLOR_INDIGO, COLOR_TEAL, COLOR_BROWN, COLOR_AMBER, COLOR_YELLOW, COLOR_CYAN, COLOR_PINK, COLOR_LIME)

}

fun randomColor(): Int = ColorUtils.COLORS[Random().nextInt(ColorUtils.COLORS.size)]