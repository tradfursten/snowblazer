package scenes

import Dependency
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
import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korau.sound.readMusic
import com.soywiz.korge.input.onUp
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.font.readTtfFont
import com.soywiz.korim.vector.paint.LinearGradientPaint
import com.soywiz.korio.async.delay

class Title(val dependency: Dependency) : Scene() {
    private lateinit var title: Image
    private lateinit var t: Text

    private lateinit var skipText: Text


    override suspend fun Container.sceneInit() {
        val fontTTF = resourcesVfs["lunchds.ttf"].readTtfFont()
        font = BitmapFont(
                fontTTF, 64.0,
                paint = LinearGradientPaint(0, 0, 0, 50).add(0.0, Colors["#f6fcff"]).add(1.0, Colors["#99e7e4"])
        )
        skipText = text("SKIP", font = font) {
            x = 500.0
            y = 250.0
            alpha = 0.0
        }
        skipText.onUp {
            sceneContainer.changeTo<Level1>()
        }
        title = image(resourcesVfs["graphics/title.png"].readBitmap()) {
            scale = 5.0
            smoothing = false
            y = 20.0
            x = 640.0
        }

        t = text("", font = font) {
            x = 120.0
            y = 120.0
        }
    }

    override suspend fun sceneAfterInit() {
        delay(2.seconds)
        skipText.tween(skipText::alpha[0.0, 1.0], time= TimeSpan(500.0), easing = Easing.LINEAR)
        title.tween(title::x[640, -1200], time = TimeSpan(5000.0), easing = Easing.LINEAR)
        delay(1.seconds)
        title.scale = 20.0
        title.x = -1200.0
        title.tween(title::scale[20.0, 2.0],
                title::x[-1200.0, 100.0],
                time = 3.seconds,
                easing = Easing.LINEAR)
        t._text = """WISE MAN TELL A TALE LATE AT NIGHT OF 
A GREAT LAND THAT EACH WINTER GOT 
COVERD BY THE MIGHTY GIFTS OF THE GODS."""
        delay(4.seconds)
        t._text = """
            Every day was a peril for the inhabitants
            fighting the ever falling snow
            trying to get to work
        """.toUpperCase().trimIndent()

        skipText._text = "START"
    }
}
