package com.fuinha.gradecalculator.models

/**
 * Created by Fuinha on 23/11/2017.
 */
abstract class FeiGradeModel(private val modelInterface: ModelInterface?, weighP1:Double = 0.4, weighP2: Double = 0.6){
    protected val minMedia : Double = 4.95
    protected val p1 : Test = Test(0.0, 0.4)
    protected val p2 : Test = Test(0.0, 0.6)
    protected var p3 : Test = Test(0.0)
    var factor : Double = 1.0
        set(value) {
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
    var hasP1 : Boolean = false
    var hasP2 : Boolean = false
    var hasP3 : Boolean = false
    var needP3 : Boolean = false
    var hasTarget : Boolean = false
    var p3onP1 : Boolean = false
    var approved : Boolean = true

    init {
        p1.weigh = weighP1
        p2.weigh = weighP2
    }

    abstract fun calculateGrade(p1:Test, p2:Test):Double
    abstract fun calculateP2(media:Double):Double
    abstract fun calculateP3(p3:Test, other:Test, media:Double):Double
    abstract fun isReady():Boolean

    open fun calculate(){
        if (hasP2) {

            media = calculateGrade(p1,p2)

            if (hasTarget)
                needP3 = media < targetMedia
            else
                needP3 = media < minMedia
        }
        else {

            if (hasTarget)
                p2.score = calculateP2(targetMedia)
            else
                p2.score = calculateP2(minMedia)

            if (p2.score > 10.0) {
                p2.score = 10.0
                needP3 = true
            }

            media = calculateGrade(p1, p2)
        }

        if (needP3 && !hasP3) {
            var tempMedia : Double = minMedia
            if (hasTarget) tempMedia = targetMedia

            val onP1 = calculateP3(p1,p2,tempMedia)
            val onP2 = calculateP3(p2,p1,tempMedia)

            p3onP1 = onP1 < onP2

            if (p3.score > 10.0) {
                p3.score = 10.0
                approved = false
            } else
                approved = true

            if (p3onP1){
                p3 = Test(onP1, p1.weigh)
                mediaP3 = calculateGrade(p3,p2)
            }
            else {
                p3 = Test(onP2, p2.weigh)
                mediaP3 = calculateGrade(p1,p3)
            }

        }
        else if (needP3 && hasP3){
            var tempMedia : Double = minMedia
            if (hasTarget) tempMedia = targetMedia
            mediaP3 = tempMedia

            val mediaP1 = calculateGrade(p3,p2)
            val mediaP2 = calculateGrade(p1,p3)

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
        hasP1 = true
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