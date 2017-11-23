package com.fuinha.gradecalculator.utils

import com.fuinha.gradecalculator.models.FeiGradeModel
import com.fuinha.gradecalculator.models.FeiPhysicsGrade
import com.fuinha.gradecalculator.models.FeiSimpleGrade
import com.fuinha.gradecalculator.models.ModelInterface

/**
 * Created by Fuinha on 23/11/2017.
 */

val FeiSimpleSystem:Int = 0
val FeiSimpleSystemEven:Int = 1
val FeiPhysicsSystem:Int = 2

class FeiGradeSystems() {

    companion object {
        fun getSystem(type:Int, modelInterface: ModelInterface):FeiGradeModel{
            when(type){
                0 -> return FeiSimpleGrade(modelInterface, 0.4, 0.6)
                1 -> return FeiSimpleGrade(modelInterface, 0.5, 0.5)
                2 -> return FeiPhysicsGrade(modelInterface, 0.4, 0.6)
                else -> return FeiSimpleGrade(modelInterface)
            }
        }
    }
}