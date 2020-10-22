package scenes

import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.SpriteAnimation

class SpriteLoader {
    fun loadSprites(): Array<Array<SpriteAnimation>> {
        val level1 = arrayOf(
                readSprite(0,0),
                readSprite(0, 1),
                readSprite(0, 2),
                readSprite(1,0),
                readSprite(1, 1),
                readSprite(1, 2),
                readSprite(2,0),
                readSprite(2, 1),
                readSprite(2, 2),
                readSprite(3,0),
                readSprite(3, 1),
                readSprite(3, 2),
                readSprite(4,0),
                readSprite(4, 1),
                readSprite(5,0),
                readSprite(5, 1)
        )
        val level2 = arrayOf(
                readSprite(0,3),
                readSprite(0, 4),
                readSprite(0, 5),
                readSprite(1,3),
                readSprite(1, 4),
                readSprite(1, 5),
                readSprite(2,3),
                readSprite(2, 4),
                readSprite(2, 5),
                readSprite(3,3),
                readSprite(3, 4),
                readSprite(3, 5),
                readSprite(4,3),
                readSprite(4, 4),
                readSprite(5,3),
                readSprite(5, 4)
        )
        val level3 = arrayOf(
                readSprite(0, 6),
                readSprite(0, 7),
                readSprite(0, 8),
                readSprite(1, 6),
                readSprite(1, 7),
                readSprite(1, 8),
                readSprite(2, 6),
                readSprite(2, 7),
                readSprite(2, 8),
                readSprite(3, 6),
                readSprite(3, 7),
                readSprite(3, 8),
                readSprite(4, 6),
                readSprite(4, 7),
                readSprite(5, 6),
                readSprite(5, 7)
        )
        val level4 = arrayOf(
                readSprite(0, 9),
                readSprite(0, 10),
                readSprite(0, 11),
                readSprite(1, 9),
                readSprite(1, 10),
                readSprite(1, 11),
                readSprite(2, 9),
                readSprite(2, 10),
                readSprite(2, 11),
                readSprite(3, 9),
                readSprite(3, 10),
                readSprite(3, 11),
                readSprite(4, 9),
                readSprite(4, 10),
                readSprite(5, 9),
                readSprite(5, 10)
        )
         return arrayOf( level1, level2, level3, level4)

    }

    private fun readSprite(row: Int, col: Int):SpriteAnimation {
        return SpriteAnimation(tiles,16, 16, row * 16,col*16,1,1,0)
    }
}