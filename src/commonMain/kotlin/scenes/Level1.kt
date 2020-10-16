package scenes

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
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.ObservableProperty
import com.soywiz.korio.file.std.resourcesVfs
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

class Level1() : Scene() {
    private lateinit var bgMusic: NativeSoundChannel
    private lateinit var background: Image
    var start = false
    override suspend fun Container.sceneInit() {
        font = resourcesVfs["clear_sans.fnt"].readBitmapFont()
        tiles = resourcesVfs["graphics/tileset_2.png"].readBitmap()
        background = image(resourcesVfs["graphics/background.png"].readBitmap()) {
            smoothing = false
        }

        val level1TL = SpriteAnimation(tiles,16, 16, 0,0,1,1,0)
        val level1TC = SpriteAnimation(tiles,16, 16, 0,16,1,1,0)
        val level1TR = SpriteAnimation(tiles,16, 16, 0,32,1,1,0)
        val level1CL = SpriteAnimation(tiles,16, 16, 16,0,1,1,0)
        val level1CC = SpriteAnimation(tiles,16, 16, 16,16,1,1,0)
        val level1CR = SpriteAnimation(tiles,16, 16, 16,32,1,1,0)
        val level1DL = SpriteAnimation(tiles,16, 16, 32,0,1,1,0)
        val level1DC = SpriteAnimation(tiles,16, 16, 32,16,1,1,0)
        val level1DR = SpriteAnimation(tiles,16, 16, 32,32,1,1,0)
        val level2TL = SpriteAnimation(tiles,16, 16, 0,48,1,1,0)
        val level2TC = SpriteAnimation(tiles,16, 16, 0,64,1,1,0)
        val level2TR = SpriteAnimation(tiles,16, 16, 0,80,1,1,0)
        val level2CL = SpriteAnimation(tiles,16, 16, 16,48,1,1,0)
        val level2CC = SpriteAnimation(tiles,16, 16, 16,64,1,1,0)
        val level2CR = SpriteAnimation(tiles,16, 16, 16,80,1,1,0)
        val level2DL = SpriteAnimation(tiles,16, 16, 32,48,1,1,0)
        val level2DC = SpriteAnimation(tiles,16, 16, 32,64,1,1,0)
        val level2DR = SpriteAnimation(tiles,16, 16, 32,80,1,1,0)
        val level3TL = SpriteAnimation(tiles,16, 16, 0,96,1,1,0)
        val level3TC = SpriteAnimation(tiles,16, 16, 0,112,1,1,0)
        val level3TR = SpriteAnimation(tiles,16, 16, 0,128,1,1,0)
        val level3CL = SpriteAnimation(tiles,16, 16, 16,96,1,1,0)
        val level3CC = SpriteAnimation(tiles,16, 16, 16,112,1,1,0)
        val level3CR = SpriteAnimation(tiles,16, 16, 16,128,1,1,0)
        val level3DL = SpriteAnimation(tiles,16, 16, 32,96,1,1,0)
        val level3DC = SpriteAnimation(tiles,16, 16, 32,112,1,1,0)
        val level3DR = SpriteAnimation(tiles,16, 16, 32,128,1,1,0)
        val level4TL = SpriteAnimation(tiles,16, 16, 0,144,1,1,0)
        val level4TC = SpriteAnimation(tiles,16, 16, 0,160,1,1,0)
        val level4TR = SpriteAnimation(tiles,16, 16, 0,176,1,1,0)
        val level4CL = SpriteAnimation(tiles,16, 16, 16,144,1,1,0)
        val level4CC = SpriteAnimation(tiles,16, 16, 16,160,1,1,0)
        val level4CR = SpriteAnimation(tiles,16, 16, 16,176,1,1,0)
        val level4DL = SpriteAnimation(tiles,16, 16, 32,144,1,1,0)
        val level4DC = SpriteAnimation(tiles,16, 16, 32,160,1,1,0)
        val level4DR = SpriteAnimation(tiles,16, 16, 32,176,1,1,0)

        sprites = arrayOf( arrayOf(level1TL, level1TC, level1TR, level1CL, level1CC, level1CR, level1DL, level1DC, level1DR),
                arrayOf(level2TL, level2TC, level2TR, level2CL, level2CC, level2CR, level2DL, level2DC, level2DR),
                arrayOf(level3TL, level3TC, level3TR, level3CL, level3CC, level3CR, level3DL, level3DC, level3DR),
                arrayOf(level4TL, level4TC, level4TR, level4CL, level4CC, level4CR, level4DL, level4DC, level4DR))

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
                    tile.depth = 0.0; // max(snow.depth - 10.0,0.0)
                    if (tile.posX > 0) snowTiles[tile.posY][tile.posX-1].depth = 0.0
                    if (tile.posX < nrCols - 1) snowTiles[tile.posY][tile.posX+1].depth = 0.0
                    if (tile.posY > 0) snowTiles[tile.posY-1][tile.posX].depth = 0.0
                    if (tile.posY < nrRows - 1) snowTiles[tile.posY+1][tile.posX].depth = 0.0
                    tile.computeColor(0.0, 0.0, 0.0, 0.0)
                }
                addChild(tile)
            }
        }

        val p = ParticleEmitter()
        p.emitterType = ParticleEmitter.Type.GRAVITY
        p.angle = 10.0
        p.speed = 600.0
        p.speedVariance = 100.0
        p.rotationStart = 0.0
        p.rotationEnd = 0.0
        p.texture = resourcesVfs["graphics/snow.png"].readBitmap().slice()

        val emitter = resourcesVfs["particles/particles.pex"].readParticle()
        particleEmitter(emitter)
                .position(views.virtualWidth * 0.5, -(views.virtualHeight * 0.75))

    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        bgMusic = resourcesVfs["sounds/sc.mp3"].readMusic().playForever()
        bgMusic.volume = 0.0
        sceneContainer.tween(bgMusic::volume[0.8], time = 1.5.seconds)
        start = true
    }
}
