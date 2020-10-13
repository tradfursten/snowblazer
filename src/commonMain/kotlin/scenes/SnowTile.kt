package scenes

import com.soywiz.korge.view.*

class SnowTile(val posX: Int, val posY: Int, var depth: Double): Container() {
    lateinit var text: Text
    val x_: Int
    val y_: Int
    val s: Sprite
    init {
        x_ = xOffset + (posX*tileSize)
        y_ = yOffset + (posY*tileSize)
        s = sprite(sprites[0][4]).xy(x_, y_)
    }

    fun computeColor(l: Double, r: Double, t: Double, b: Double) {
        val level = getLevel(depth)
        val ll = checkLevel(level, getLevel(l))
        val lr = checkLevel(level, getLevel(r))
        val lt = checkLevel(level, getLevel(t))
        val lb = checkLevel(level, getLevel(b))
        val index = when {
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
        }
        s.playAnimationLooped(sprites[level][index])
    }

    private fun checkLevel(l1: Int, l2: Int): Boolean {
        if (l1 == 0) return l1 < l2
        return l2 < l1
    }

    private fun getLevel(depth: Double): Int {
        return when(depth) {
            in 0.0..25.0 -> 0
            in 25.0..50.0 -> 1
            in 50.0..Double.MAX_VALUE-> 2
            else -> 3
        }
    }
}