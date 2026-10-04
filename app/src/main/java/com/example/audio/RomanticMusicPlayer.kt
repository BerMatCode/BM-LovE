package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

/**
 * Procedural romantic melody synthesizer (Romantic music box / soft piano).
 * Completely self-contained, offline, graceful and beautiful.
 */
object RomanticMusicPlayer {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _volume = MutableStateFlow(0.7f)
    val volume = _volume.asStateFlow()

    private var audioTrack: AudioTrack? = null
    private var playbackJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    // Note frequencies in Hz (Gentle romantic melody in C / G / Am / F)
    private val NOTES = mapOf(
        "C4" to 261.63,
        "D4" to 293.66,
        "E4" to 329.63,
        "F4" to 349.23,
        "G4" to 392.00,
        "A4" to 440.00,
        "B4" to 493.88,
        "C5" to 523.25,
        "D5" to 587.33,
        "E5" to 659.25,
        "G5" to 783.99,
        "A5" to 880.00,
        "REST" to 0.0
    )

    // Romantic music box melody (pitch, durationMs)
    private val MELODY = listOf(
        Pair("E5", 400), Pair("D5", 400), Pair("C5", 400), Pair("G4", 400),
        Pair("A4", 400), Pair("C5", 400), Pair("E5", 600), Pair("D5", 600),
        Pair("C5", 400), Pair("D5", 400), Pair("E5", 400), Pair("G4", 400),
        Pair("A4", 600), Pair("G4", 600), Pair("REST", 300),
        // Part 2 - High romance
        Pair("G5", 400), Pair("E5", 400), Pair("D5", 400), Pair("C5", 400),
        Pair("A4", 400), Pair("C5", 400), Pair("D5", 600), Pair("REST", 200),
        Pair("E5", 400), Pair("G5", 400), Pair("A5", 600), Pair("G5", 400),
        Pair("E5", 400), Pair("D5", 400), Pair("C5", 800), Pair("REST", 600)
    )

    private const val SAMPLE_RATE = 22050

    fun togglePlay() {
        if (_isPlaying.value) {
            pause()
        } else {
            play()
        }
    }

    fun play() {
        if (_isPlaying.value) return
        _isPlaying.value = true

        playbackJob?.cancel()
        playbackJob = scope.launch {
            try {
                val minBufSize = AudioTrack.getMinBufferSize(
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )

                audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(SAMPLE_RATE)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(minBufSize * 2)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                audioTrack?.setVolume(_volume.value)
                audioTrack?.play()

                while (isActive && _isPlaying.value) {
                    for (note in MELODY) {
                        if (!isActive || !_isPlaying.value) break

                        val freq = NOTES[note.first] ?: 0.0
                        val durationMs = note.second
                        val pcmData = generateRomanticTone(freq, durationMs)

                        audioTrack?.write(pcmData, 0, pcmData.size)
                    }
                    delay(300) // gentle pause between melody loops
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cleanUpTrack()
            }
        }
    }

    fun pause() {
        _isPlaying.value = false
        playbackJob?.cancel()
        playbackJob = null
        cleanUpTrack()
    }

    fun setVolume(vol: Float) {
        val clamped = vol.coerceIn(0f, 1f)
        _volume.value = clamped
        audioTrack?.setVolume(clamped)
    }

    private fun cleanUpTrack() {
        try {
            audioTrack?.pause()
            audioTrack?.flush()
            audioTrack?.stop()
            audioTrack?.release()
        } catch (_: Exception) {}
        audioTrack = null
    }

    /**
     * Synthesizes warm music box / acoustic chime with gentle decay
     */
    private fun generateRomanticTone(freq: Double, durationMs: Int): ShortArray {
        val numSamples = (SAMPLE_RATE * (durationMs / 1000.0)).toInt()
        val samples = ShortArray(numSamples)

        if (freq == 0.0) {
            return samples // silence
        }

        val twoPiF = 2.0 * PI * freq
        val decayRate = 3.0 / (durationMs / 1000.0)

        for (i in 0 until numSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            // Music box chime: fundamental + gentle 2nd and 3rd harmonics + exponential decay
            val fundamental = sin(twoPiF * t)
            val harmonic2 = 0.35 * sin(2.0 * twoPiF * t)
            val harmonic3 = 0.15 * sin(3.0 * twoPiF * t)
            val envelope = exp(-decayRate * t)

            val sampleVal = (fundamental + harmonic2 + harmonic3) * envelope * 24000.0
            samples[i] = sampleVal.coerceIn(-32768.0, 32767.0).toInt().toShort()
        }
        return samples
    }
}
