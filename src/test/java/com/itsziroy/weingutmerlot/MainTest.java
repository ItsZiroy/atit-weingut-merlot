package com.itsziroy.weingutmerlot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {

  @Test
  public void testMainClassCreation() {
    Main main = new Main();
    Assertions.assertInstanceOf(Main.class, main);
  }
}
