package scenes

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.interpolation.Easing
import com.soywiz.klock.seconds
import com.soywiz.korio.async.delay

class Title() : Scene() {
    private lateinit var title: Image
    override suspend fun Container.sceneInit() {
        title = image(resourcesVfs["graphics/title.png"].readBitmap()) {
            scale = 5.0
            smoothing = false
            y = 20.0
            x = 640.0
        }
        onClick {
            sceneContainer.changeTo<Level1>()
        }
    }
    override suspend fun sceneAfterInit() {
        delay(2.seconds)
        title.tween(title::x[640, -1200], time = TimeSpan(8000.0), easing = Easing.LINEAR)
        delay(1.seconds)
        title.scale = 20.0
        title.x = -1200.0
        title.tween(title::scale[20.0, 2.0],
                title::x[-1200.0, 100.0],
                time = 4.seconds,
                easing = Easing.LINEAR)
    }
}
