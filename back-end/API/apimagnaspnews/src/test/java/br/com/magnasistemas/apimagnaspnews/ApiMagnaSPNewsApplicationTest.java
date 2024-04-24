package br.com.magnasistemas.apimagnaspnews;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiMagnaSPNewsApplicationTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
   void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void mainMethodShouldStartApplication() {
    	ApiMagnaSPNewsApplication.main(new String[]{});
        assertTrue(outContent.toString().contains("Started ApiMagnaSPNewsApplication"));
    }
}
