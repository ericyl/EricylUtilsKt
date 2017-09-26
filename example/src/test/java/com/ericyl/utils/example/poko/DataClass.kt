package com.ericyl.utils.example.poko

import com.ericyl.utils.example.annotaions.PoKo

/**
 * Created by ericyl on 2017/8/21.
 */

@PoKo
internal data class DataClass(val id: Int = 0, var name: String? = null)