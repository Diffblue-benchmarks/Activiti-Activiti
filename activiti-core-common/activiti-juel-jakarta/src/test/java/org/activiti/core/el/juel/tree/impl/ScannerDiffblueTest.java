/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.core.el.juel.tree.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ScannerDiffblueTest {
  /**
   * Method under test: {@link Scanner.ExtensionToken#ExtensionToken(String)}
   */
  @Test
  void testExtensionTokenNewExtensionToken() {
    // Arrange and Act
    Scanner.ExtensionToken actualExtensionToken = new Scanner.ExtensionToken("Image");

    // Assert
    assertEquals("Image", actualExtensionToken.getImage());
    assertEquals(5, actualExtensionToken.getSize());
    assertEquals(Scanner.Symbol.EXTENSION, actualExtensionToken.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#isDigit(char)}
   */
  @Test
  void testIsDigit() {
    // Arrange, Act and Assert
    assertFalse((new Scanner("Input")).isDigit('A'));
    assertTrue((new Scanner("Input")).isDigit('0'));
    assertFalse((new Scanner("Input")).isDigit('/'));
  }

  /**
   * Method under test: {@link Scanner#keyword(String)}
   */
  @Test
  void testKeyword() {
    // Arrange, Act and Assert
    assertNull((new Scanner("Input")).keyword("foo"));
  }

  /**
   * Method under test: {@link Scanner#fixed(Scanner.Symbol)}
   */
  @Test
  void testFixed() {
    // Arrange and Act
    Scanner.Token actualFixedResult = (new Scanner("Input")).fixed(Scanner.Symbol.EOF);

    // Assert
    assertNull(actualFixedResult.getImage());
    assertEquals(0, actualFixedResult.getSize());
    assertEquals(Scanner.Symbol.EOF, actualFixedResult.getSymbol());
  }

  /**
   * Method under test:
   * {@link Scanner.ScanException#ScanException(int, String, String)}
   */
  @Test
  void testScanExceptionNewScanException() {
    // Arrange and Act
    Scanner.ScanException actualScanException = new Scanner.ScanException(1, "3", "Expected");

    // Assert
    assertEquals("3", actualScanException.encountered);
    assertEquals("Expected", actualScanException.expected);
    assertEquals("lexical error at position 1, encountered 3, expected Expected",
        actualScanException.getLocalizedMessage());
    assertEquals("lexical error at position 1, encountered 3, expected Expected", actualScanException.getMessage());
    assertNull(actualScanException.getCause());
    assertEquals(0, actualScanException.getSuppressed().length);
    assertEquals(1, actualScanException.position);
  }

  /**
   * Method under test: {@link Scanner.Symbol#toString()}
   */
  @Test
  void testSymbolToString() {
    // Arrange, Act and Assert
    assertEquals("<EOF>", Scanner.Symbol.EOF.toString());
    assertEquals("'+'", Scanner.Symbol.PLUS.toString());
  }

  /**
   * Method under test: {@link Scanner#token(Scanner.Symbol, String, int)}
   */
  @Test
  void testToken() {
    // Arrange and Act
    Scanner.Token actualTokenResult = (new Scanner("Input")).token(Scanner.Symbol.EOF, "42", 3);

    // Assert
    assertEquals("42", actualTokenResult.getImage());
    assertEquals(3, actualTokenResult.getSize());
    assertEquals(Scanner.Symbol.EOF, actualTokenResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#isEval()}
   */
  @Test
  void testIsEval() {
    // Arrange, Act and Assert
    assertFalse((new Scanner("Input")).isEval());
  }

  /**
   * Method under test: {@link Scanner#nextText()}
   */
  @Test
  void testNextText() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("Input");

    // Act
    Scanner.Token actualNextTextResult = scanner.nextText();

    // Assert
    assertEquals("Input", scanner.builder.toString());
    assertEquals("Input", actualNextTextResult.getImage());
    assertEquals(5, actualNextTextResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextTextResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextText()}
   */
  @Test
  void testNextText2() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextTextResult = (new Scanner("#{")).nextText();

    // Assert
    assertEquals("", actualNextTextResult.getImage());
    assertEquals(0, actualNextTextResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextTextResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextString()}
   */
  @Test
  void testNextString() throws Scanner.ScanException {
    // Arrange, Act and Assert
    assertThrows(Scanner.ScanException.class, () -> (new Scanner("Input")).nextString());
  }

  /**
   * Method under test: {@link Scanner#nextString()}
   */
  @Test
  void testNextString2() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextStringResult = (new Scanner("&&")).nextString();

    // Assert
    assertEquals("", actualNextStringResult.getImage());
    assertEquals(2, actualNextStringResult.getSize());
    assertEquals(Scanner.Symbol.STRING, actualNextStringResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextNumber()}
   */
  @Test
  void testNextNumber() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextNumberResult = (new Scanner("Input")).nextNumber();

    // Assert
    assertEquals("", actualNextNumberResult.getImage());
    assertEquals(0, actualNextNumberResult.getSize());
    assertEquals(Scanner.Symbol.INTEGER, actualNextNumberResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextNumber()}
   */
  @Test
  void testNextNumber2() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextNumberResult = (new Scanner("!")).nextNumber();

    // Assert
    assertEquals("", actualNextNumberResult.getImage());
    assertEquals(0, actualNextNumberResult.getSize());
    assertEquals(Scanner.Symbol.INTEGER, actualNextNumberResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextNumber()}
   */
  @Test
  void testNextNumber3() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextNumberResult = (new Scanner("42")).nextNumber();

    // Assert
    assertEquals("42", actualNextNumberResult.getImage());
    assertEquals(2, actualNextNumberResult.getSize());
    assertEquals(Scanner.Symbol.INTEGER, actualNextNumberResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextNumber()}
   */
  @Test
  void testNextNumber4() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextNumberResult = (new Scanner(".")).nextNumber();

    // Assert
    assertEquals(".", actualNextNumberResult.getImage());
    assertEquals(1, actualNextNumberResult.getSize());
    assertEquals(Scanner.Symbol.FLOAT, actualNextNumberResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("Input")).nextEval();

    // Assert
    assertEquals("Input", actualNextEvalResult.getImage());
    assertEquals(5, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.IDENTIFIER, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval2() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("!")).nextEval();

    // Assert
    assertEquals("!", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.NOT, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval3() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("42")).nextEval();

    // Assert
    assertEquals("42", actualNextEvalResult.getImage());
    assertEquals(2, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.INTEGER, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval4() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("!=")).nextEval();

    // Assert
    assertEquals("!=", actualNextEvalResult.getImage());
    assertEquals(2, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.NE, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval5() throws Scanner.ScanException {
    // Arrange, Act and Assert
    assertThrows(Scanner.ScanException.class, () -> (new Scanner("#{")).nextEval());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval6() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("${")).nextEval();

    // Assert
    assertEquals("$", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.IDENTIFIER, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval7() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("%")).nextEval();

    // Assert
    assertEquals("%", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.MOD, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval8() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("&&")).nextEval();

    // Assert
    assertEquals("&&", actualNextEvalResult.getImage());
    assertEquals(2, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.AND, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval9() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("(")).nextEval();

    // Assert
    assertEquals("(", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.LPAREN, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval10() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner(")")).nextEval();

    // Assert
    assertEquals(")", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.RPAREN, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval11() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("*")).nextEval();

    // Assert
    assertEquals("*", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.MUL, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval12() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("+")).nextEval();

    // Assert
    assertEquals("+", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.PLUS, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval13() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner(",")).nextEval();

    // Assert
    assertEquals(",", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.COMMA, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval14() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("-")).nextEval();

    // Assert
    assertEquals("-", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.MINUS, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval15() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner(".")).nextEval();

    // Assert
    assertEquals(".", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.DOT, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval16() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("/")).nextEval();

    // Assert
    assertEquals("/", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.DIV, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval17() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner(":")).nextEval();

    // Assert
    assertEquals(":", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.COLON, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval18() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("<")).nextEval();

    // Assert
    assertEquals("<", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.LT, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval19() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("42Input")).nextEval();

    // Assert
    assertEquals("42", actualNextEvalResult.getImage());
    assertEquals(2, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.INTEGER, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval20() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("42.")).nextEval();

    // Assert
    assertEquals("42.", actualNextEvalResult.getImage());
    assertEquals(3, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.FLOAT, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  void testNextEval21() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner(".42")).nextEval();

    // Assert
    assertEquals(".42", actualNextEvalResult.getImage());
    assertEquals(3, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.FLOAT, actualNextEvalResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextToken()}
   */
  @Test
  void testNextToken() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("Input");

    // Act
    Scanner.Token actualNextTokenResult = scanner.nextToken();

    // Assert
    assertEquals("Input", scanner.builder.toString());
    assertEquals("Input", actualNextTokenResult.getImage());
    assertEquals(5, actualNextTokenResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextTokenResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextToken()}
   */
  @Test
  void testNextToken2() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("!");

    // Act
    Scanner.Token actualNextTokenResult = scanner.nextToken();

    // Assert
    assertEquals("!", scanner.builder.toString());
    assertEquals("!", actualNextTokenResult.getImage());
    assertEquals(1, actualNextTokenResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextTokenResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextToken()}
   */
  @Test
  void testNextToken3() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextTokenResult = (new Scanner("#{")).nextToken();

    // Assert
    assertEquals("#{", actualNextTokenResult.getImage());
    assertEquals(2, actualNextTokenResult.getSize());
    assertEquals(Scanner.Symbol.START_EVAL_DEFERRED, actualNextTokenResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextToken()}
   */
  @Test
  void testNextToken4() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextTokenResult = (new Scanner("${")).nextToken();

    // Assert
    assertEquals("${", actualNextTokenResult.getImage());
    assertEquals(2, actualNextTokenResult.getSize());
    assertEquals(Scanner.Symbol.START_EVAL_DYNAMIC, actualNextTokenResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#nextToken()}
   */
  @Test
  void testNextToken5() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("Input#{");

    // Act
    Scanner.Token actualNextTokenResult = scanner.nextToken();

    // Assert
    assertEquals("Input", scanner.builder.toString());
    assertEquals("Input", actualNextTokenResult.getImage());
    assertEquals(5, actualNextTokenResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextTokenResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#next()}
   */
  @Test
  void testNext() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("Input");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("Input", scanner.builder.toString());
    assertEquals("Input", actualNextResult.getImage());
    assertEquals(5, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#next()}
   */
  @Test
  void testNext2() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("!");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("!", scanner.builder.toString());
    assertEquals("!", actualNextResult.getImage());
    assertEquals(1, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextResult.getSymbol());
  }

  /**
   * Method under test: {@link Scanner#next()}
   */
  @Test
  void testNext3() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertNull(actualNextResult.getImage());
    assertEquals(0, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.EOF, actualNextResult.getSymbol());
    assertTrue(scanner.isEval());
  }

  /**
   * Method under test: {@link Scanner#next()}
   */
  @Test
  void testNext4() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("#{");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("#{", actualNextResult.getImage());
    assertEquals(2, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.START_EVAL_DEFERRED, actualNextResult.getSymbol());
    assertTrue(scanner.isEval());
  }

  /**
   * Method under test: {@link Scanner#next()}
   */
  @Test
  void testNext5() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("${");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("${", actualNextResult.getImage());
    assertEquals(2, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.START_EVAL_DYNAMIC, actualNextResult.getSymbol());
    assertTrue(scanner.isEval());
  }

  /**
   * Method under test: {@link Scanner#next()}
   */
  @Test
  void testNext6() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("Input#{");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("Input", scanner.builder.toString());
    assertEquals("Input", actualNextResult.getImage());
    assertEquals(5, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextResult.getSymbol());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Scanner#Scanner(String)}
   *   <li>{@link Scanner#getInput()}
   *   <li>{@link Scanner#getPosition()}
   *   <li>{@link Scanner#getToken()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    Scanner actualScanner = new Scanner("Input");
    String actualInput = actualScanner.getInput();
    int actualPosition = actualScanner.getPosition();
    Scanner.Token actualToken = actualScanner.getToken();

    // Assert
    assertEquals("", actualScanner.builder.toString());
    assertEquals("Input", actualInput);
    assertNull(actualToken);
    assertEquals(0, actualPosition);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Scanner.Token#Token(Scanner.Symbol, String, int)}
   *   <li>{@link Scanner.Token#toString()}
   *   <li>{@link Scanner.Token#getImage()}
   *   <li>{@link Scanner.Token#getSize()}
   *   <li>{@link Scanner.Token#getSymbol()}
   * </ul>
   */
  @Test
  void testTokenGettersAndSetters() {
    // Arrange and Act
    Scanner.Token actualToken = new Scanner.Token(Scanner.Symbol.EOF, "Image", 3);
    String actualToStringResult = actualToken.toString();
    String actualImage = actualToken.getImage();
    int actualSize = actualToken.getSize();

    // Assert
    assertEquals("<EOF>", actualToStringResult);
    assertEquals("Image", actualImage);
    assertEquals(3, actualSize);
    assertEquals(Scanner.Symbol.EOF, actualToken.getSymbol());
  }

  /**
   * Method under test: {@link Scanner.Token#Token(Scanner.Symbol, String)}
   */
  @Test
  void testTokenNewToken() {
    // Arrange and Act
    Scanner.Token actualToken = new Scanner.Token(Scanner.Symbol.EOF, "Image");

    // Assert
    assertEquals("Image", actualToken.getImage());
    assertEquals(5, actualToken.getSize());
    assertEquals(Scanner.Symbol.EOF, actualToken.getSymbol());
  }
}
