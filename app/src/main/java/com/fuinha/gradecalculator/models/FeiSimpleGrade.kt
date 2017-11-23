package com.fuinha.gradecalculator.models

/**
 * Created by Fuinha on 23/11/2017.
 */

class FeiSimpleGrade(modelInterface: ModelInterface?, weighP1:Double = 0.4, weighP2: Double = 0.6) : FeiGradeModel(modelInterface, weighP1, weighP2) {
    override fun isReady(): Boolean {
        return hasP1
    }

    override fun calculateP2(media: Double): Double {
        return (media / factor - p1.getGrade()) / p2.weigh
    }

    override fun calculateP3(p3: Test, other: Test, media: Double): Double {
        return (media / factor - other.getGrade()) / p3.weigh
    }

    override fun calculateGrade(p1: Test, p2: Test): Double {
        return factor * (p1.getGrade() + p2.getGrade())
    }
}