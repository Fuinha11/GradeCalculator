package com.fuinha.gradecalculator

import android.util.Log
import com.fuinha.gradecalculator.models.SimpleGradeSystem
import org.junit.Assert
import org.junit.Test

/**
 * Created by Fuinha on 16/11/2017.
 */

class SimpleGradeTest{
    @Test
    fun firstTest(){
        val gradeSystem = SimpleGradeSystem(null)
        gradeSystem.setP1(4.5)
        gradeSystem.setP2(4.5)
        gradeSystem.factor = 1.1
        Assert.assertEquals(4.95, gradeSystem.media, 0.01)
        Assert.assertEquals(false, gradeSystem.needP3)
    }

    @Test
    fun secondTest(){
        val gradeSystem = SimpleGradeSystem(null)
        gradeSystem.setP1(0.0)
        gradeSystem.setP2(4.5)
        gradeSystem.factor = 1.1
        Assert.assertEquals(4.95, gradeSystem.media, 0.01)
        Assert.assertEquals(4.5, gradeSystem.getP3(), 0.01)
        Assert.assertEquals(true, gradeSystem.needP3)
    }
}