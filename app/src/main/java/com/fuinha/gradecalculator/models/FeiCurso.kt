package com.fuinha.gradecalculator.models

/**
 * Created by Fuinha on 23/11/2017.
 */
class FeiCurso(val id:Int, val name:String){
    var numberOfCiclos = 0
    val subjects:ArrayList<FeiSubject> = ArrayList()
}