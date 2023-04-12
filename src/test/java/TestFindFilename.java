import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import org.junit.jupiter.api.*;
import org.daniel.FindFilename;

public class TestFindFilename {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;
  
  FindFilename findFilename = new FindFilename();
  
  @BeforeEach
  public void setUpStreams() {
      System.setOut(new PrintStream(outContent));
      System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  public void restoreStreams() {
      System.setOut(originalOut);
      System.setErr(originalErr);
  }
  
  @Test
  public void testFileNameMissing () {
    findFilename.main(new String[0]);
    Assertions.assertEquals("No program argument passed. Please pass a filename argument in the form \"myfile.txt\"", errContent.toString().trim());
  }
  
  @Test
  public void testFilenameFormatShouldFail () {
    findFilename.main(new String[] { "filnamn" });
    Assertions.assertEquals("Incorrect filename, no dot in filename. Please pass a correct filename in the form \"myfile.txt\"", errContent.toString().trim());
  }
  
  @Test
  public void testFileShouldNotExist () {
    findFilename.main(new String[] { "filnamn.txt" });
    Assertions.assertEquals("Could not find file \"filnamn.txt\"", errContent.toString().trim());
  }
  
  @Test
  public void testMultipleOccurrencesPerRow () {
    findFilename.main(new String[] { "myfile.txt" });
    Assertions.assertEquals("Found string \"myfile\" 42 times", outContent.toString().trim());
  }
  
  @Test
  public void testNoOccurrences () {
    findFilename.main(new String[] { "myemptyfile.txt" });
    Assertions.assertEquals("Found string \"myemptyfile\" 0 times", outContent.toString().trim());
  }
}