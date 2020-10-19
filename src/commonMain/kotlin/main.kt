import com.soywiz.korau.sound.NativeSoundChannel
import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korau.sound.readMusic
import com.soywiz.korge.*
import com.soywiz.korge.scene.Module
import com.soywiz.korge.scene.Scene
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.ScaleMode
import com.soywiz.korma.geom.SizeInt
import scenes.GameOver
import scenes.Level1
import scenes.Title
import kotlin.reflect.KClass

suspend fun main() = Korge(Korge.Config(module = MyModule))

object MyModule : Module() {
	override val mainScene: KClass<out Scene> = Title::class
	override val title: String
		get() = "SnowBlazer"
	override val windowSize: SizeInt
		get() = SizeInt(1280, 720)
	override val size: SizeInt
		get() = SizeInt(640, 320)
	override val scaleMode: ScaleMode
		get() = ScaleMode.COVER
	override val bgcolor: RGBA
		get() = Colors.BLACK


	override suspend fun AsyncInjector.configure() {
		mapInstance(Dependency(resourcesVfs["sounds/Snow Blazer2.mp3"]
				.readMusic()
				.play(PlaybackTimes.INFINITE)))
		mapPrototype { Title(get()) }
		mapPrototype { Level1(get()) }
		mapPrototype { GameOver() }
	}
}

class Dependency(var channel: NativeSoundChannel)
