package br.com.magportal.apimagspnewscadastro;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.magportal.apimagspnewscadastro.ApiMagSPNewsCadastroApplication;

class ApiMagnaSPNewsApplicationCadastroTest {

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
    	ApiMagSPNewsCadastroApplication.main(new String[]{});
        assertTrue(outContent.toString().contains("Started ApiMagSPNewsCadastroApplication"));
    }
}
