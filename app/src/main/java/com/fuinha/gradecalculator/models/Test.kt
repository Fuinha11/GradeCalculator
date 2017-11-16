package com.fuinha.gradecalculator.models

import java.lang.Math.pow

/**
 * Created by Fuinha on 16/11/2017.
 */

class Test(var score:Double, var weigh:Double = 1.0, var power:Double = 1.0) {
    fun getGrade(): Double {
        return pow((score * weigh), power)
    }
}