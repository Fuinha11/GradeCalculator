package com.fuinha.gradecalculator

/**
 * Created by Fuinha on 15/11/2017.
 */
class SimpleGradeSystem {
    private var p1 : Prova = Prova(0.0, 0.4)
    private var p2 : Prova = Prova(0.0, 0.6)
    private var p3 : Prova = Prova(0.0)
    private var factor : Double = 1.0
    private var media : Double = 0.0
    private var minMedia : Double = 4.95
    private var needP3 : Boolean = false

    fun setP1(nota: Double){
        p1.setNota(nota)
        calculate()
    }

    private fun calculate() {

    }


    class Prova(private var nota:Double, private var peso:Double = 1.0) {
        fun getScore(): Double {
            return nota * peso
        }

        fun setNota(nota: Double){
            this.nota = nota
        }

    }
}