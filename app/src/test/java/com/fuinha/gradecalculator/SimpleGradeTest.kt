package com.fuinha.gradecalculator

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
//        gradeSystem.setP1(1.5)
        gradeSystem.setP2(0.0)
        gradeSystem.factor = 1.1
        println(gradeSystem.toString())
        Assert.assertEquals(4.95, gradeSystem.media, 0.01)
        Assert.assertEquals(true, gradeSystem.needP3)
    }
}