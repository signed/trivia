package com.adaptionsoft.games;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoldenMasterTest {

    private final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    private final PrintStream outBackup = System.out;

    @BeforeEach
    void redirectSystemOutToInMemoryBuffer() {
        PrintStream out = new PrintStream(byteStream);
        System.setOut(out);
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(outBackup);
    }

    @ParameterizedTest
    @MethodSource("seeds")
    void runGoldMasterTests(long seed) throws IOException {
        assertEquals(recordedOutputFor(seed), gameOutputFor(seed));
    }

    @ParameterizedTest
    @MethodSource("seeds")
    @Disabled("should only be executed manually to record golden master")
    void recordGoldenMaster(long seed) throws IOException {
        String s = gameOutputFor(seed);
        Path outputPath = pathToOutputFileFor(seed);
        Files.createDirectories(outputPath.getParent());
        Files.write(outputPath, s.getBytes(Charset.forName("UTF-8")));
    }

    private static LongStream seeds(){
        return LongStream.range(0, 255);
    }

    private String recordedOutputFor(long seed) throws IOException {
        return new String(Files.readAllBytes(pathToOutputFileFor(seed)), "UTF-8");
    }

    private Path pathToOutputFileFor(long seed) {
        return Paths.get("goldenmaster", seed + ".out");
    }

    private String gameOutputFor(long seed) throws UnsupportedEncodingException {
        Random random = new Random(seed);
        GameRunner.runGame(random);

        return byteStream.toString("UTF-8");
    }

}