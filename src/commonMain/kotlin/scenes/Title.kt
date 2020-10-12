package scenes

import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.image
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import scenes.Level1

class Title() : Scene() {
    override suspend fun Container.sceneInit() {
        val title = resourcesVfs["graphics/title.png"].readBitmap()
        image(title)
        onClick {
            sceneContainer.changeTo<Level1>()
        }
    }
}
