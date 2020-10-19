package scenes

import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.text

class GameOver() : Scene() {
    override suspend fun Container.sceneInit() {
        text("GAME OVER", font = font) {
            x = 320.0
            y = 160.0
        }
    }
}
