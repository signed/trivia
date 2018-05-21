package com.adaptionsoft.games

import com.adaptionsoft.games.trivia.runner.runGame
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.stream.LongStream.range
import kotlin.streams.toList

internal class GoldenMasterTest {

    private val byteStream = ByteArrayOutputStream()
    private val outBackup = System.out

    @BeforeEach
    fun redirectSystemOutToInMemoryBuffer() {
        val out = PrintStream(byteStream)
        System.setOut(out)
    }

    @AfterEach
    fun restoreSystemOut() {
        System.setOut(outBackup)
    }

    @ParameterizedTest
    @MethodSource("seeds")
    fun runGoldMasterTests(seed: Long) {
        assertEquals(recordedOutputFor(seed), gameOutputFor(seed))
    }

    @ParameterizedTest
    @MethodSource("seeds")
    @Disabled("should only be executed manually to record golden master")
    fun recordGoldenMaster(seed: Long) {
        val s = gameOutputFor(seed)
        val outputPath = pathToOutputFileFor(seed)
        Files.createDirectories(outputPath.parent)
        Files.write(outputPath, s.toByteArray(Charset.forName("UTF-8")))
    }

    companion object {
        @JvmStatic
        fun seeds(): List<Long> {
            return range(0, 255).toList()
        }
    }

    private fun recordedOutputFor(seed: Long): String {
        return String(Files.readAllBytes(pathToOutputFileFor(seed)), Charset.forName("UTF-8"))
    }

    private fun pathToOutputFileFor(seed: Long): Path {
        return Paths.get("goldenmaster", seed.toString() + ".out")
    }

    private fun gameOutputFor(seed: Long): String {
        val random = Random(seed)
        runGame(random)
        return byteStream.toString("UTF-8")
    }

}
