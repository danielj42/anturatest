package org.daniel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.List;

public class FindFilename {
  
  public static void main(String args[]) {
    try {
      String filename = args[0];
      
      if (!filename.contains(".")) {
        System.err.println("Incorrect filename, no dot in filename. Please pass a correct filename in the form \"myfile.txt\"");
      } else {
        searchFile(filename);
      }
    } catch (ArrayIndexOutOfBoundsException aioobe) {
      System.err.println("No program argument passed. Please pass a filename argument in the form \"myfile.txt\"");
    }
  }
  
  private static void searchFile(String filename) {
    int pos = filename.indexOf(".");
    String name = filename.substring(0, pos);
    int counter = 0;
    
    try {
      List<String> allLines = Files.readAllLines(Paths.get(filename));

      for (String line : allLines) {
        int lastIndex = 0;
        while(lastIndex != -1) {
          lastIndex = line.indexOf(name, lastIndex);
          if(lastIndex != -1){
              counter ++;
              lastIndex += name.length();
          }
        }
      }
      
      System.out.println("Found string \"" + name + "\" " + counter + " times");
    } catch (NoSuchFileException n) {
      System.err.println("Could not find file \"" + filename + "\"\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}