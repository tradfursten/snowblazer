import com.soywiz.korge.*
import com.soywiz.korge.scene.Module
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.async.ObservableProperty
import com.soywiz.korma.geom.ScaleMode
import com.soywiz.korma.geom.SizeInt
import scenes.Level1
import scenes.Title
import kotlin.properties.Delegates
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
		mapPrototype { Title() }
		mapPrototype { Level1() }
	}
}