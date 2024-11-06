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
import org.activiti.core.el.juel.tree.impl.Scanner.ExtensionToken;
import org.activiti.core.el.juel.tree.impl.Scanner.ScanException;
import org.activiti.core.el.juel.tree.impl.Scanner.Symbol;
import org.activiti.core.el.juel.tree.impl.Scanner.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScannerDiffblueTest {
  /**
   * Test ExtensionToken {@link ExtensionToken#ExtensionToken(String)}.
   * <p>
   * Method under test: {@link Scanner.ExtensionToken#ExtensionToken(String)}
   */
  @Test
  @DisplayName("Test ExtensionToken new ExtensionToken(String)")
  void testExtensionTokenNewExtensionToken() {
    // Arrange and Act
    Scanner.ExtensionToken actualExtensionToken = new Scanner.ExtensionToken("Image");

    // Assert
    assertEquals("Image", actualExtensionToken.getImage());
    assertEquals(5, actualExtensionToken.getSize());
    assertEquals(Scanner.Symbol.EXTENSION, actualExtensionToken.getSymbol());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Scanner#Scanner(String)}
   *   <li>{@link Scanner#getInput()}
   *   <li>{@link Scanner#getPosition()}
   *   <li>{@link Scanner#getToken()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
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
   * Test {@link Scanner#isDigit(char)}.
   * <ul>
   *   <li>When {@code 0}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#isDigit(char)}
   */
  @Test
  @DisplayName("Test isDigit(char); when '0'; then return 'true'")
  void testIsDigit_when0_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new Scanner("Input")).isDigit('0'));
  }

  /**
   * Test {@link Scanner#isDigit(char)}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#isDigit(char)}
   */
  @Test
  @DisplayName("Test isDigit(char); when 'A'; then return 'false'")
  void testIsDigit_whenA_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new Scanner("Input")).isDigit('A'));
  }

  /**
   * Test {@link Scanner#isDigit(char)}.
   * <ul>
   *   <li>When {@code /}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#isDigit(char)}
   */
  @Test
  @DisplayName("Test isDigit(char); when '/'; then return 'false'")
  void testIsDigit_whenSlash_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new Scanner("Input")).isDigit('/'));
  }

  /**
   * Test {@link Scanner#keyword(String)}.
   * <p>
   * Method under test: {@link Scanner#keyword(String)}
   */
  @Test
  @DisplayName("Test keyword(String)")
  void testKeyword() {
    // Arrange, Act and Assert
    assertNull((new Scanner("Input")).keyword("foo"));
  }

  /**
   * Test {@link Scanner#fixed(Symbol)}.
   * <p>
   * Method under test: {@link Scanner#fixed(Scanner.Symbol)}
   */
  @Test
  @DisplayName("Test fixed(Symbol)")
  void testFixed() {
    // Arrange and Act
    Scanner.Token actualFixedResult = (new Scanner("Input")).fixed(Scanner.Symbol.EOF);

    // Assert
    assertNull(actualFixedResult.getImage());
    assertEquals(0, actualFixedResult.getSize());
    assertEquals(Scanner.Symbol.EOF, actualFixedResult.getSymbol());
  }

  /**
   * Test ScanException {@link ScanException#ScanException(int, String, String)}.
   * <p>
   * Method under test:
   * {@link Scanner.ScanException#ScanException(int, String, String)}
   */
  @Test
  @DisplayName("Test ScanException new ScanException(int, String, String)")
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
   * Test Symbol {@link Symbol#toString()}.
   * <ul>
   *   <li>Given {@code EOF}.</li>
   *   <li>Then return {@code <EOF>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner.Symbol#toString()}
   */
  @Test
  @DisplayName("Test Symbol toString(); given 'EOF'; then return '<EOF>'")
  void testSymbolToString_givenEof_thenReturnEof() {
    // Arrange, Act and Assert
    assertEquals("<EOF>", Scanner.Symbol.EOF.toString());
  }

  /**
   * Test Symbol {@link Symbol#toString()}.
   * <ul>
   *   <li>Given {@code PLUS}.</li>
   *   <li>Then return {@code '+'}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner.Symbol#toString()}
   */
  @Test
  @DisplayName("Test Symbol toString(); given 'PLUS'; then return ''+''")
  void testSymbolToString_givenPlus_thenReturnApostrophePlusSignApostrophe() {
    // Arrange, Act and Assert
    assertEquals("'+'", Scanner.Symbol.PLUS.toString());
  }

  /**
   * Test {@link Scanner#token(Symbol, String, int)}.
   * <p>
   * Method under test: {@link Scanner#token(Scanner.Symbol, String, int)}
   */
  @Test
  @DisplayName("Test token(Symbol, String, int)")
  void testToken() {
    // Arrange and Act
    Scanner.Token actualTokenResult = (new Scanner("Input")).token(Scanner.Symbol.EOF, "42", 3);

    // Assert
    assertEquals("42", actualTokenResult.getImage());
    assertEquals(3, actualTokenResult.getSize());
    assertEquals(Scanner.Symbol.EOF, actualTokenResult.getSymbol());
  }

  /**
   * Test {@link Scanner#isEval()}.
   * <p>
   * Method under test: {@link Scanner#isEval()}
   */
  @Test
  @DisplayName("Test isEval()")
  void testIsEval() {
    // Arrange, Act and Assert
    assertFalse((new Scanner("Input")).isEval());
  }

  /**
   * Test {@link Scanner#nextText()}.
   * <p>
   * Method under test: {@link Scanner#nextText()}
   */
  @Test
  @DisplayName("Test nextText()")
  void testNextText() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("#{");

    // Act
    Scanner.Token actualNextTextResult = scanner.nextText();

    // Assert
    assertEquals("", scanner.builder.toString());
    assertEquals("", actualNextTextResult.getImage());
    assertEquals(0, actualNextTextResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextTextResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextText()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with {@code Input}.</li>
   *   <li>Then {@link Scanner#Scanner(String)} with {@code Input}
   * {@link Scanner#builder} toString is {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextText()}
   */
  @Test
  @DisplayName("Test nextText(); given Scanner(String) with 'Input'; then Scanner(String) with 'Input' builder toString is 'Input'")
  void testNextText_givenScannerWithInput_thenScannerWithInputBuilderToStringIsInput() throws Scanner.ScanException {
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
   * Test {@link Scanner#nextString()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with {@code Input}.</li>
   *   <li>Then throw {@link ScanException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextString()}
   */
  @Test
  @DisplayName("Test nextString(); given Scanner(String) with 'Input'; then throw ScanException")
  void testNextString_givenScannerWithInput_thenThrowScanException() throws Scanner.ScanException {
    // Arrange, Act and Assert
    assertThrows(Scanner.ScanException.class, () -> (new Scanner("Input")).nextString());
  }

  /**
   * Test {@link Scanner#nextString()}.
   * <ul>
   *   <li>Then return Image is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextString()}
   */
  @Test
  @DisplayName("Test nextString(); then return Image is empty string")
  void testNextString_thenReturnImageIsEmptyString() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextStringResult = (new Scanner("&&")).nextString();

    // Assert
    assertEquals("", actualNextStringResult.getImage());
    assertEquals(2, actualNextStringResult.getSize());
    assertEquals(Scanner.Symbol.STRING, actualNextStringResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextNumber()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code 42}.</li>
   *   <li>Then return Image is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextNumber()}
   */
  @Test
  @DisplayName("Test nextNumber(); given Scanner(String) with input is '42'; then return Image is '42'")
  void testNextNumber_givenScannerWithInputIs42_thenReturnImageIs42() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextNumberResult = (new Scanner("42")).nextNumber();

    // Assert
    assertEquals("42", actualNextNumberResult.getImage());
    assertEquals(2, actualNextNumberResult.getSize());
    assertEquals(Scanner.Symbol.INTEGER, actualNextNumberResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextNumber()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code .}.</li>
   *   <li>Then return Image is {@code .}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextNumber()}
   */
  @Test
  @DisplayName("Test nextNumber(); given Scanner(String) with input is '.'; then return Image is '.'")
  void testNextNumber_givenScannerWithInputIsDot_thenReturnImageIsDot() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextNumberResult = (new Scanner(".")).nextNumber();

    // Assert
    assertEquals(".", actualNextNumberResult.getImage());
    assertEquals(1, actualNextNumberResult.getSize());
    assertEquals(Scanner.Symbol.FLOAT, actualNextNumberResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextNumber()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code !}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextNumber()}
   */
  @Test
  @DisplayName("Test nextNumber(); given Scanner(String) with input is '!'")
  void testNextNumber_givenScannerWithInputIsExclamationMark() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextNumberResult = (new Scanner("!")).nextNumber();

    // Assert
    assertEquals("", actualNextNumberResult.getImage());
    assertEquals(0, actualNextNumberResult.getSize());
    assertEquals(Scanner.Symbol.INTEGER, actualNextNumberResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextNumber()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with {@code Input}.</li>
   *   <li>Then return Image is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextNumber()}
   */
  @Test
  @DisplayName("Test nextNumber(); given Scanner(String) with 'Input'; then return Image is empty string")
  void testNextNumber_givenScannerWithInput_thenReturnImageIsEmptyString() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextNumberResult = (new Scanner("Input")).nextNumber();

    // Assert
    assertEquals("", actualNextNumberResult.getImage());
    assertEquals(0, actualNextNumberResult.getSize());
    assertEquals(Scanner.Symbol.INTEGER, actualNextNumberResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code 42Input}.</li>
   *   <li>Then return Image is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '42Input'; then return Image is '42'")
  void testNextEval_givenScannerWithInputIs42Input_thenReturnImageIs42() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("42Input")).nextEval();

    // Assert
    assertEquals("42", actualNextEvalResult.getImage());
    assertEquals(2, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.INTEGER, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code 42}.</li>
   *   <li>Then return Image is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '42'; then return Image is '42'")
  void testNextEval_givenScannerWithInputIs42_thenReturnImageIs42() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("42")).nextEval();

    // Assert
    assertEquals("42", actualNextEvalResult.getImage());
    assertEquals(2, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.INTEGER, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code 42.}.</li>
   *   <li>Then return Image is {@code 42.}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '42.'; then return Image is '42.'")
  void testNextEval_givenScannerWithInputIs42_thenReturnImageIs422() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("42.")).nextEval();

    // Assert
    assertEquals("42.", actualNextEvalResult.getImage());
    assertEquals(3, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.FLOAT, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code .42}.</li>
   *   <li>Then return Image is {@code .42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '.42'; then return Image is '.42'")
  void testNextEval_givenScannerWithInputIs42_thenReturnImageIs423() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner(".42")).nextEval();

    // Assert
    assertEquals(".42", actualNextEvalResult.getImage());
    assertEquals(3, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.FLOAT, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code *}.</li>
   *   <li>Then return Image is {@code *}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '*'; then return Image is '*'")
  void testNextEval_givenScannerWithInputIsAsterisk_thenReturnImageIsAsterisk() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("*")).nextEval();

    // Assert
    assertEquals("*", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.MUL, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code :}.</li>
   *   <li>Then return Image is {@code :}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is ':'; then return Image is ':'")
  void testNextEval_givenScannerWithInputIsColon_thenReturnImageIsColon() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner(":")).nextEval();

    // Assert
    assertEquals(":", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.COLON, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code ,}.</li>
   *   <li>Then return Image is {@code ,}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is ','; then return Image is ','")
  void testNextEval_givenScannerWithInputIsComma_thenReturnImageIsComma() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner(",")).nextEval();

    // Assert
    assertEquals(",", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.COMMA, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code -}.</li>
   *   <li>Then return Image is {@code -}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '-'; then return Image is '-'")
  void testNextEval_givenScannerWithInputIsDash_thenReturnImageIsDash() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("-")).nextEval();

    // Assert
    assertEquals("-", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.MINUS, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code .}.</li>
   *   <li>Then return Image is {@code .}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '.'; then return Image is '.'")
  void testNextEval_givenScannerWithInputIsDot_thenReturnImageIsDot() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner(".")).nextEval();

    // Assert
    assertEquals(".", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.DOT, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code <}.</li>
   *   <li>Then return Image is {@code <}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '<'; then return Image is '<'")
  void testNextEval_givenScannerWithInputIsLessThanSign_thenReturnImageIsLessThanSign() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("<")).nextEval();

    // Assert
    assertEquals("<", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.LT, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code %}.</li>
   *   <li>Then return Image is {@code %}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '%'; then return Image is '%'")
  void testNextEval_givenScannerWithInputIsPercentSign_thenReturnImageIsPercentSign() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("%")).nextEval();

    // Assert
    assertEquals("%", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.MOD, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code +}.</li>
   *   <li>Then return Image is {@code +}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '+'; then return Image is '+'")
  void testNextEval_givenScannerWithInputIsPlusSign_thenReturnImageIsPlusSign() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("+")).nextEval();

    // Assert
    assertEquals("+", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.PLUS, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with input is {@code /}.</li>
   *   <li>Then return Image is {@code /}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with input is '/'; then return Image is '/'")
  void testNextEval_givenScannerWithInputIsSlash_thenReturnImageIsSlash() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("/")).nextEval();

    // Assert
    assertEquals("/", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.DIV, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with {@code Input}.</li>
   *   <li>Then return Image is {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); given Scanner(String) with 'Input'; then return Image is 'Input'")
  void testNextEval_givenScannerWithInput_thenReturnImageIsInput() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("Input")).nextEval();

    // Assert
    assertEquals("Input", actualNextEvalResult.getImage());
    assertEquals(5, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.IDENTIFIER, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Then return Image is {@code &&}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); then return Image is '&&'")
  void testNextEval_thenReturnImageIsAmpersandAmpersand() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("&&")).nextEval();

    // Assert
    assertEquals("&&", actualNextEvalResult.getImage());
    assertEquals(2, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.AND, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Then return Image is {@code $}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); then return Image is '$'")
  void testNextEval_thenReturnImageIsDollarSign() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("${")).nextEval();

    // Assert
    assertEquals("$", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.IDENTIFIER, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Then return Image is {@code !}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); then return Image is '!'")
  void testNextEval_thenReturnImageIsExclamationMark() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("!")).nextEval();

    // Assert
    assertEquals("!", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.NOT, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Then return Image is {@code !=}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); then return Image is '!='")
  void testNextEval_thenReturnImageIsExclamationMarkEqualsSign() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("!=")).nextEval();

    // Assert
    assertEquals("!=", actualNextEvalResult.getImage());
    assertEquals(2, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.NE, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Then return Image is {@code (}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); then return Image is '('")
  void testNextEval_thenReturnImageIsLeftParenthesis() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner("(")).nextEval();

    // Assert
    assertEquals("(", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.LPAREN, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Then return Image is {@code )}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); then return Image is ')'")
  void testNextEval_thenReturnImageIsRightParenthesis() throws Scanner.ScanException {
    // Arrange and Act
    Scanner.Token actualNextEvalResult = (new Scanner(")")).nextEval();

    // Assert
    assertEquals(")", actualNextEvalResult.getImage());
    assertEquals(1, actualNextEvalResult.getSize());
    assertEquals(Scanner.Symbol.RPAREN, actualNextEvalResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextEval()}.
   * <ul>
   *   <li>Then throw {@link ScanException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextEval()}
   */
  @Test
  @DisplayName("Test nextEval(); then throw ScanException")
  void testNextEval_thenThrowScanException() throws Scanner.ScanException {
    // Arrange, Act and Assert
    assertThrows(Scanner.ScanException.class, () -> (new Scanner("#{")).nextEval());
  }

  /**
   * Test {@link Scanner#nextToken()}.
   * <p>
   * Method under test: {@link Scanner#nextToken()}
   */
  @Test
  @DisplayName("Test nextToken()")
  void testNextToken() throws Scanner.ScanException {
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
   * Test {@link Scanner#nextToken()}.
   * <p>
   * Method under test: {@link Scanner#nextToken()}
   */
  @Test
  @DisplayName("Test nextToken()")
  void testNextToken2() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("#{");

    // Act
    Scanner.Token actualNextTokenResult = scanner.nextToken();

    // Assert
    assertEquals("", scanner.builder.toString());
    assertEquals("#{", actualNextTokenResult.getImage());
    assertEquals(2, actualNextTokenResult.getSize());
    assertEquals(Scanner.Symbol.START_EVAL_DEFERRED, actualNextTokenResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextToken()}.
   * <p>
   * Method under test: {@link Scanner#nextToken()}
   */
  @Test
  @DisplayName("Test nextToken()")
  void testNextToken3() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("${");

    // Act
    Scanner.Token actualNextTokenResult = scanner.nextToken();

    // Assert
    assertEquals("", scanner.builder.toString());
    assertEquals("${", actualNextTokenResult.getImage());
    assertEquals(2, actualNextTokenResult.getSize());
    assertEquals(Scanner.Symbol.START_EVAL_DYNAMIC, actualNextTokenResult.getSymbol());
  }

  /**
   * Test {@link Scanner#nextToken()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with {@code Input}.</li>
   *   <li>Then {@link Scanner#Scanner(String)} with {@code Input}
   * {@link Scanner#builder} toString is {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextToken()}
   */
  @Test
  @DisplayName("Test nextToken(); given Scanner(String) with 'Input'; then Scanner(String) with 'Input' builder toString is 'Input'")
  void testNextToken_givenScannerWithInput_thenScannerWithInputBuilderToStringIsInput() throws Scanner.ScanException {
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
   * Test {@link Scanner#nextToken()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with {@code Input#{}.</li>
   *   <li>Then {@link Scanner#Scanner(String)} with {@code Input#{} {@link
   * Scanner#builder} toString is {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#nextToken()}
   */
  @Test
  @DisplayName("Test nextToken(); given Scanner(String) with 'Input#{'; then Scanner(String) with 'Input#{' builder toString is 'Input'")
  void testNextToken_givenScannerWithInput_thenScannerWithInputBuilderToStringIsInput2() throws Scanner.ScanException {
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
   * Test {@link Scanner#next()}.
   * <p>
   * Method under test: {@link Scanner#next()}
   */
  @Test
  @DisplayName("Test next()")
  void testNext() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("#{");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("", scanner.builder.toString());
    assertEquals("#{", actualNextResult.getImage());
    assertEquals(2, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.START_EVAL_DEFERRED, actualNextResult.getSymbol());
    assertTrue(scanner.isEval());
  }

  /**
   * Test {@link Scanner#next()}.
   * <p>
   * Method under test: {@link Scanner#next()}
   */
  @Test
  @DisplayName("Test next()")
  void testNext2() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("${");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("", scanner.builder.toString());
    assertEquals("${", actualNextResult.getImage());
    assertEquals(2, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.START_EVAL_DYNAMIC, actualNextResult.getSymbol());
    assertTrue(scanner.isEval());
  }

  /**
   * Test {@link Scanner#next()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with {@code Input}.</li>
   *   <li>Then {@link Scanner#Scanner(String)} with {@code Input}
   * {@link Scanner#builder} toString is {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#next()}
   */
  @Test
  @DisplayName("Test next(); given Scanner(String) with 'Input'; then Scanner(String) with 'Input' builder toString is 'Input'")
  void testNext_givenScannerWithInput_thenScannerWithInputBuilderToStringIsInput() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("Input");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("Input", scanner.builder.toString());
    assertEquals("Input", actualNextResult.getImage());
    assertEquals(5, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextResult.getSymbol());
    assertFalse(scanner.isEval());
  }

  /**
   * Test {@link Scanner#next()}.
   * <ul>
   *   <li>Given {@link Scanner#Scanner(String)} with {@code Input#{}.</li>
   *   <li>Then {@link Scanner#Scanner(String)} with {@code Input#{} {@link
   * Scanner#builder} toString is {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#next()}
   */
  @Test
  @DisplayName("Test next(); given Scanner(String) with 'Input#{'; then Scanner(String) with 'Input#{' builder toString is 'Input'")
  void testNext_givenScannerWithInput_thenScannerWithInputBuilderToStringIsInput2() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("Input#{");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("Input", scanner.builder.toString());
    assertEquals("Input", actualNextResult.getImage());
    assertEquals(5, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextResult.getSymbol());
    assertFalse(scanner.isEval());
  }

  /**
   * Test {@link Scanner#next()}.
   * <ul>
   *   <li>Then {@link Scanner#Scanner(String)} with input is empty string
   * {@link Scanner#builder} toString is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#next()}
   */
  @Test
  @DisplayName("Test next(); then Scanner(String) with input is empty string builder toString is empty string")
  void testNext_thenScannerWithInputIsEmptyStringBuilderToStringIsEmptyString() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("", scanner.builder.toString());
    assertNull(actualNextResult.getImage());
    assertEquals(0, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.EOF, actualNextResult.getSymbol());
    assertTrue(scanner.isEval());
  }

  /**
   * Test {@link Scanner#next()}.
   * <ul>
   *   <li>Then {@link Scanner#Scanner(String)} with input is {@code !}
   * {@link Scanner#builder} toString is {@code !}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Scanner#next()}
   */
  @Test
  @DisplayName("Test next(); then Scanner(String) with input is '!' builder toString is '!'")
  void testNext_thenScannerWithInputIsExclamationMarkBuilderToStringIsExclamationMark() throws Scanner.ScanException {
    // Arrange
    Scanner scanner = new Scanner("!");

    // Act
    Scanner.Token actualNextResult = scanner.next();

    // Assert
    assertEquals("!", scanner.builder.toString());
    assertEquals("!", actualNextResult.getImage());
    assertEquals(1, actualNextResult.getSize());
    assertEquals(Scanner.Symbol.TEXT, actualNextResult.getSymbol());
    assertFalse(scanner.isEval());
  }

  /**
   * Test Token getters and setters.
   * <p>
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
  @DisplayName("Test Token getters and setters")
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
   * Test Token {@link Token#Token(Symbol, String)}.
   * <p>
   * Method under test: {@link Scanner.Token#Token(Scanner.Symbol, String)}
   */
  @Test
  @DisplayName("Test Token new Token(Symbol, String)")
  void testTokenNewToken() {
    // Arrange and Act
    Scanner.Token actualToken = new Scanner.Token(Scanner.Symbol.EOF, "Image");

    // Assert
    assertEquals("Image", actualToken.getImage());
    assertEquals(5, actualToken.getSize());
    assertEquals(Scanner.Symbol.EOF, actualToken.getSymbol());
  }
}
