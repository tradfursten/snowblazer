package scenes

import Dependency
import com.soywiz.klock.seconds
import com.soywiz.klock.timesPerSecond
import com.soywiz.korau.sound.NativeSound
import com.soywiz.korau.sound.NativeSoundChannel
import com.soywiz.korau.sound.readMusic
import com.soywiz.korge.input.onUp
import com.soywiz.korge.particle.ParticleEmitter
import com.soywiz.korge.particle.particleEmitter
import com.soywiz.korge.particle.readParticle
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.slice
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.ObservableProperty
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import kotlinx.coroutines.GlobalScope
import kotlin.properties.Delegates
import kotlin.random.Random
var font: BitmapFont by Delegates.notNull()

val tileSize = 16
val snowRate = 0.2
val xOffset = 192
val yOffset = 110

val nrRows = 10
val nrCols = 16
val score = ObservableProperty(0)
lateinit var sprites: Array<Array<SpriteAnimation>>
lateinit var tiles: Bitmap

class Level1(val dependency: Dependency) : Scene() {
    private lateinit var bgMusic: NativeSoundChannel
    private lateinit var background: Image
    var start = false
    override suspend fun Container.sceneInit() {
        //font = resourcesVfs["lunchds.fnt"].readBitmapFont()
        tiles = resourcesVfs["graphics/tileset_2.png"].readBitmap()
        background = image(resourcesVfs["graphics/background.png"].readBitmap()) {
            smoothing = false
        }

        sprites = SpriteLoader().loadSprites()


        val snowTiles = Array(nrRows) {i -> Array(nrCols) {j -> SnowTile(j, i, 0.0)} }
        for(y in 0 until nrRows) {
            for(x in 0 until nrCols) {
                val tile = snowTiles[y][x]
                tile.addFixedUpdater(60.timesPerSecond) {
                    if (Random.nextFloat() < snowRate && start) {
                        this.depth = this.depth + snowRate
                    }
                   this.computeColor(
                            if (this.posX == 0) -1.0 else snowTiles[this.posY][this.posX-1].depth,
                            if (this.posX == nrCols - 1) -1.0 else snowTiles[this.posY][this.posX+1].depth,
                            if (this.posY == 0) -1.0 else snowTiles[this.posY - 1][this.posX].depth,
                            if (this.posY == nrRows - 1) -1.0 else snowTiles[this.posY + 1][this.posX].depth)
               }

                tile.onUp {
                    if (tile.level != 3) {
                        score.update(score.value + 1)
                        tile.clearSnow()
                        if (tile.posX > 0) snowTiles[tile.posY][tile.posX - 1].clearSnow()
                        if (tile.posX < nrCols - 1) snowTiles[tile.posY][tile.posX + 1].clearSnow()
                        if (tile.posY > 0) snowTiles[tile.posY - 1][tile.posX].clearSnow()
                        if (tile.posY < nrRows - 1) snowTiles[tile.posY + 1][tile.posX].clearSnow()
                    }
                }
                addChild(tile)
            }
        }

        this.addFixedUpdater(60.timesPerSecond) {
           if( snowTiles.all { it.all { it.level == 3}}) {
               launchImmediately {
                   sceneContainer.changeTo<GameOver>()
               }
           }
        }

        text(score.value.toString(), font = font) {
            x = 120.0
            y = 20.0
            score {
                text = it.toString()
            }
        }

        val emitter = resourcesVfs["particles/particles.pex"].readParticle()
        particleEmitter(emitter)
                .position(views.virtualWidth * 0.5, -(views.virtualHeight * 0.75))

    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        start = true
    }
}
