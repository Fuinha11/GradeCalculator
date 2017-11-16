package com.fuinha.gradecalculator.models

/**
 * Created by Fuinha on 15/11/2017.
 */
class SimpleGradeSystem(private val modelInterface: ModelInterface) {
    private val minMedia : Double = 4.95
    private var p1 : Test = Test(0.0, 0.4)
    private var p2 : Test = Test(0.0, 0.6)
    private var p3 : Test = Test(0.0)
    var factor : Double = 1.0
        set(value){
            field = value
            calculate()
        }
    var media : Double = 0.0
    var targetMedia : Double = 0.0
        set(value) {
            field = value
            hasTarget = true
        }
    var needP3 : Boolean = false
    var hasP2 : Boolean = false
    var hasP3 : Boolean = false
    var hasTarget : Boolean = false
    var p3onP1 : Boolean = false
    var approved : Boolean = true

    private fun calculate() {
        if (hasP2) {

            media = factor * (p1.getGrade() + p2.getGrade())

            if (hasTarget)
                needP3 = media < targetMedia
            else
                needP3 = media < minMedia

        }
        else {

            if (hasTarget)
                p2.score = (targetMedia / factor - p1.getGrade()) / p2.weigh
            else
                p2.score = (minMedia / factor - p1.getGrade()) / p2.weigh

            if (p2.score > 10.0) {
                p2.score = 10.0
                needP3 = true
            }
        }

        if (needP3 && !hasP3) {
            val onP1 = (minMedia / factor - p2.getGrade()) / p1.weigh
            val onP2 = (minMedia / factor - p1.getGrade()) / p2.weigh

            p3onP1 = onP1 < onP2

            if (p3onP1)
                p3 = Test(onP1, p1.weigh)
            else
                p3 = Test(onP2, p2.weigh)

            if (p3.score > 10.0) {
                p3.score = 10.0
                approved = false
            } else
                approved = true

        }
        else if (needP3 && hasP3){
            val mediaP1 = factor * (p3.score * p1.weigh + p2.getGrade())
            val mediaP2 = factor * (p1.getGrade() + p3.score * p2.weigh)

            if (mediaP1 > minMedia && mediaP2 > minMedia)
                approved = false
            else if (mediaP1 > mediaP2) {
                media = mediaP1
                approved = true
                p3onP1 = true
            } else {
                approved = true
                p3onP1 = false
            }
        }

        modelInterface.modelUpdated()
    }

    fun setP1(score: Double){
        p1.score = score
        calculate()
    }

    fun getP1():Double {
        return p1.score
    }

    fun setP2(score: Double){
        p2.score = score
        hasP2 = true
        calculate()
    }

    fun getP2():Double {
        return p2.score
    }

    fun getP3():Double {
        hasP3 = true
        return p3.score
    }
}