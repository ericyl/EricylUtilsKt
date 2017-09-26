package com.ericyl.utils.example.poko

import com.ericyl.utils.util.setByFiled
import org.junit.Assert
import org.junit.Test

/**
 * Created by ericyl on 2017/8/21.
 */
class PoKoTest {
    @Test
    fun test() {
        val test = DataClass().setByFiled("id", 5)
        test.name = "test"
        Assert.assertEquals(DataClass(5, "test").toString(), test.toString())
    }
}