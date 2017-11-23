package com.fuinha.gradecalculator.models

/**
 * Created by Fuinha on 15/11/2017.
 */

class SimpleGradeSystem(private val modelInterface: ModelInterface?, weighP1:Double = 0.4, weighP2: Double = 0.6) {
    private val minMedia : Double = 4.95
    private val p1 : Test = Test(0.0, 0.4)
    private val p2 : Test = Test(0.0, 0.6)
    private var p3 : Test = Test(0.0)
    var factor : Double = 1.0
        set(value){
            field = value
            calculate()
        }
    var media : Double = 0.0
    var mediaP3 : Double = 0.0
    var targetMedia : Double = 0.0
        set(value) {
            field = value
            hasTarget = true
            calculate()
        }
    var needP3 : Boolean = false
    var hasP2 : Boolean = false
    var hasP3 : Boolean = false
    var hasTarget : Boolean = false
    var p3onP1 : Boolean = false
    var approved : Boolean = true

    init {
        p1.weigh = weighP1
        p2.weigh = weighP2
    }

    private fun calculate() {
        if (hasP2) {

            media = factor * (p1.getGrade() + p2.getGrade())

            if (hasTarget)
                needP3 = media < targetMedia
            else
                needP3 = media < minMedia
        }
        else {

            if (hasTarget) {
                p2.score = (targetMedia / factor - p1.getGrade()) / p2.weigh
                media = targetMedia
            }
            else {
                p2.score = (minMedia / factor - p1.getGrade()) / p2.weigh
                media = minMedia
            }

            if (p2.score > 10.0) {
                p2.score = 10.0
                needP3 = true
            }
        }

        if (needP3 && !hasP3) {
            var tempMedia : Double = minMedia
            if (hasTarget) tempMedia = targetMedia
            mediaP3 = tempMedia

            val onP1 = (tempMedia / factor - p2.getGrade()) / p1.weigh
            val onP2 = (tempMedia / factor - p1.getGrade()) / p2.weigh


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
            var tempMedia : Double = minMedia
            if (hasTarget) tempMedia = targetMedia
            mediaP3 = tempMedia

            val mediaP1 = factor * (p3.score * p1.weigh + p2.getGrade())
            val mediaP2 = factor * (p1.getGrade() + p3.score * p2.weigh)

            approved = !(mediaP1 < mediaP3 && mediaP2 < mediaP3)

            if (mediaP1 > mediaP2) {
                mediaP3 = mediaP1
                p3onP1 = true
            } else {
                mediaP3 = mediaP2
                p3onP1 = false
            }
        }

        if (modelInterface != null)
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

    fun setP3(score: Double){
        p3.score = score
        hasP3 = true
        calculate()
    }

    fun getP3():Double {
        return p3.score
    }

    override fun toString(): String {
        var result = ""

        result += "(P1: %.2f * %.2f + ".format(p1.score, p1.weigh)
        result += "P2: %.2f * %.2f)*%.2f = ".format(p2.score, p2.weigh, factor)
        result += "Media: %.2f \n\n".format(media)

        if (hasP3 || needP3) {
            result += "Precisou de P3\n"
            if (p3onP1) {
                result += "(P3: %.2f * %.2f + ".format(p3.score, p1.weigh)
                result += "(P2: %.2f * %.2f)*%.2f = ".format(p2.score, p2.weigh, factor)
                result += "Media: %.2f \n\n".format(mediaP3)
            }
            else {
                result += "(P1: %.2f * %.2f + ".format(p1.score, p1.weigh)
                result += "P3: %.2f * %.2f)*%.2f = ".format(p3.score, p2.weigh, factor)
                result += "Media: %.2f \n\n".format(mediaP3)
            }
        }

        if (!approved) {
            result += "Impossível aprovação com esses parametros\n"
            if (hasTarget)
                result += "Media Desejada: %.2f\n\n".format(targetMedia)
        }

        return result
    }
}