package com.fuinha.gradecalculator.models

import com.fuinha.gradecalculator.utils.FeiGradeSystems

/**
 * Created by Fuinha on 23/11/2017.
 */

class FeiSubject(val id:Int, val name:String, val ciclo:Int, val system:Int) {
    fun getGradeSystem(modelInterface: ModelInterface):FeiGradeModel{
        return FeiGradeSystems.getSystem(system, modelInterface)
    }
}