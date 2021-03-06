package scenes

import com.soywiz.korge.view.*

class SnowTile(val posX: Int, val posY: Int, var depth: Double): Container() {
    val x_: Int
    val y_: Int
    val s: Sprite
    var level: Int
    var i: Int
    init {
        x_ = xOffset + (posX*tileSize)// + posX
        y_ = yOffset + (posY*tileSize)// + posY
        level = 0
        i = 4
        s = sprite(sprites[level][i])
                .xy(x_, y_)
        s.smoothing = false
    }

    fun computeColor(l: Double, r: Double, t: Double, b: Double) {
        val newLevel = getLevel(depth)
        val ll = checkLevel(newLevel, getLevel(l))
        val lr = checkLevel(newLevel, getLevel(r))
        val lt = checkLevel(newLevel, getLevel(t))
        val lb = checkLevel(newLevel, getLevel(b))
        /*val index = when {
            ll -> when {
                lt -> 0
                lb -> 6
                else -> 3
            }
            lr -> when {
                lt -> 2
                lb -> 8
                else -> 5
            }
            lt -> 1
            lb -> 7
            else -> 4
        } */
        val index = getIndex(ll, lr, lt, lb)
        if (level != newLevel || i != index) {
            level = newLevel
            i = index
            s.playAnimationLooped(sprites[level][i])
        }
    }

    private fun getIndex(l: Boolean, r: Boolean,t: Boolean,b: Boolean): Int {
        if (l && r && t && b) {
            return 11
        } else if (l && r && !t && !b) {
            return 10
        } else if (t && b && !l && !r) {
            return 9
        } else if (l) {
           return if (t && !b) 0
           else if (b && !t) 6
           else 3
       } else if (r && !l) {
           return if (t && !b) 2
           else if (b && !t) 8
           else 5
       } else if (t) {
            return 1
        } else if (b) {
            return 7
        } else {
            return 4
        }
    }

    private fun checkLevel(l1: Int, l2: Int): Boolean {
        if (l1 == 0) return l1 < l2
        return l2 < l1
    }

    private fun getLevel(depth: Double): Int {
        return when(depth) {
            in 0.0..24.0 -> 0
            in 25.0..49.0 -> 1
            in 50.0..99.0-> 2
            in 100.0..Double.MAX_VALUE-> 3
            else -> 0
        }
    }

    fun clearSnow() {
        if (level != 3) depth = 0.0
    }
}