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
package org.activiti.engine.impl.util.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JSONTokenerDiffblueTest {
  /**
   * Test {@link JSONTokener#JSONTokener(Reader)}.
   *
   * <ul>
   *   <li>When {@link StringReader#StringReader(String)} with {@code foo}.
   *   <li>Then return not end.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#JSONTokener(Reader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONTokener.<init>(Reader)"})
  public void testNewJSONTokener_whenStringReaderWithFoo_thenReturnNotEnd() {
    // Arrange, Act and Assert
    assertFalse(new JSONTokener(new StringReader("foo")).end());
  }

  /**
   * Test {@link JSONTokener#back()}.
   *
   * <p>Method under test: {@link JSONTokener#back()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONTokener.back()"})
  public void testBack() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONTokener("foo").back());
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   *
   * <ul>
   *   <li>When {@code 0}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_when0_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, JSONTokener.dehexchar('0'));
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   *
   * <ul>
   *   <li>When {@code A}.
   *   <li>Then return ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_whenA_thenReturnTen() {
    // Arrange, Act and Assert
    assertEquals(10, JSONTokener.dehexchar('A'));
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   *
   * <ul>
   *   <li>When {@code a}.
   *   <li>Then return ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_whenA_thenReturnTen2() {
    // Arrange, Act and Assert
    assertEquals(10, JSONTokener.dehexchar('a'));
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   *
   * <ul>
   *   <li>When {@code G}.
   *   <li>Then return minus one.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_whenG_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(-1, JSONTokener.dehexchar('G'));
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   *
   * <ul>
   *   <li>When {@code g}.
   *   <li>Then return minus one.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_whenG_thenReturnMinusOne2() {
    // Arrange, Act and Assert
    assertEquals(-1, JSONTokener.dehexchar('g'));
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then return minus one.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_whenSlash_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(-1, JSONTokener.dehexchar('/'));
  }

  /**
   * Test {@link JSONTokener#end()}.
   *
   * <p>Method under test: {@link JSONTokener#end()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONTokener.end()"})
  public void testEnd() {
    // Arrange, Act and Assert
    assertFalse(new JSONTokener("foo").end());
  }

  /**
   * Test {@link JSONTokener#more()}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is empty string.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#more()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONTokener.more()"})
  public void testMore_givenJSONTokenerWithSIsEmptyString_thenReturnFalse() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("");

    // Act and Assert
    assertFalse(jsonTokener.more());
    assertTrue(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#more()}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.
   *   <li>Then not {@link JSONTokener#JSONTokener(String)} with s is {@code foo} end.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#more()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONTokener.more()"})
  public void testMore_givenJSONTokenerWithSIsFoo_thenNotJSONTokenerWithSIsFooEnd()
      throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act
    boolean actualMoreResult = jsonTokener.more();

    // Assert
    assertFalse(jsonTokener.end());
    assertTrue(actualMoreResult);
  }

  /**
   * Test {@link JSONTokener#more()}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#more()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONTokener.more()"})
  public void testMore_thenThrowJSONException() throws JSONException {
    // Arrange
    Paths.get(System.getProperty("java.io.tmpdir"), "foo");

    // Act and Assert
    assertThrows(
        JSONException.class, () -> new JSONTokener(new FileReader(new FileDescriptor())).more());
  }

  /**
   * Test {@link JSONTokener#next(char)} with {@code c}.
   *
   * <ul>
   *   <li>Given {@link FileReader#FileReader(FileDescriptor)} with {@link
   *       FileDescriptor#FileDescriptor()}.
   *   <li>When space.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next(char)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"char JSONTokener.next(char)"})
  public void testNextWithC_givenFileReaderWithFileDescriptor_whenSpace_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(
        JSONException.class, () -> new JSONTokener(new FileReader(new FileDescriptor())).next(' '));
  }

  /**
   * Test {@link JSONTokener#next(char)} with {@code c}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is empty string.
   *   <li>When {@code A}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next(char)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"char JSONTokener.next(char)"})
  public void testNextWithC_givenJSONTokenerWithSIsEmptyString_whenA_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONTokener("").next('A'));
  }

  /**
   * Test {@link JSONTokener#next(char)} with {@code c}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.
   *   <li>When {@code A}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next(char)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"char JSONTokener.next(char)"})
  public void testNextWithC_givenJSONTokenerWithSIsFoo_whenA_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONTokener("foo").next('A'));
  }

  /**
   * Test {@link JSONTokener#next(char)} with {@code c}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.
   *   <li>When {@code f}.
   *   <li>Then return {@code f}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next(char)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"char JSONTokener.next(char)"})
  public void testNextWithC_givenJSONTokenerWithSIsFoo_whenF_thenReturnF() throws JSONException {
    // Arrange, Act and Assert
    assertEquals('f', new JSONTokener("foo").next('f'));
  }

  /**
   * Test {@link JSONTokener#next(int)} with {@code n}.
   *
   * <ul>
   *   <li>Given {@link FileReader#FileReader(FileDescriptor)} with {@link
   *       FileDescriptor#FileDescriptor()}.
   *   <li>When two.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JSONTokener.next(int)"})
  public void testNextWithN_givenFileReaderWithFileDescriptor_whenTwo_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(
        JSONException.class, () -> new JSONTokener(new FileReader(new FileDescriptor())).next(2));
  }

  /**
   * Test {@link JSONTokener#next(int)} with {@code n}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.
   *   <li>When four.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JSONTokener.next(int)"})
  public void testNextWithN_givenJSONTokenerWithSIsFoo_whenFour_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONTokener("foo").next(4));
  }

  /**
   * Test {@link JSONTokener#next(int)} with {@code n}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.
   *   <li>When one.
   *   <li>Then return {@code f}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JSONTokener.next(int)"})
  public void testNextWithN_givenJSONTokenerWithSIsFoo_whenOne_thenReturnF() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("f", new JSONTokener("foo").next(1));
  }

  /**
   * Test {@link JSONTokener#next(int)} with {@code n}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.
   *   <li>When zero.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JSONTokener.next(int)"})
  public void testNextWithN_givenJSONTokenerWithSIsFoo_whenZero_thenReturnEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", new JSONTokener("foo").next(0));
  }

  /**
   * Test {@link JSONTokener#next()}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is empty string.
   *   <li>Then return null.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"char JSONTokener.next()"})
  public void testNext_givenJSONTokenerWithSIsEmptyString_thenReturnNull() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("");

    // Act and Assert
    assertEquals('\u0000', jsonTokener.next());
    assertTrue(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#next()}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.
   *   <li>Then return {@code f}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"char JSONTokener.next()"})
  public void testNext_givenJSONTokenerWithSIsFoo_thenReturnF() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals('f', jsonTokener.next());
    assertFalse(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#next()}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#next()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"char JSONTokener.next()"})
  public void testNext_thenThrowJSONException() throws JSONException {
    // Arrange
    Paths.get(System.getProperty("java.io.tmpdir"), "foo");

    // Act and Assert
    assertThrows(
        JSONException.class, () -> new JSONTokener(new FileReader(new FileDescriptor())).next());
  }

  /**
   * Test {@link JSONTokener#nextClean()}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is empty string.
   *   <li>Then return null.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#nextClean()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"char JSONTokener.nextClean()"})
  public void testNextClean_givenJSONTokenerWithSIsEmptyString_thenReturnNull()
      throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("");

    // Act and Assert
    assertEquals('\u0000', jsonTokener.nextClean());
    assertTrue(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#nextClean()}.
   *
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.
   *   <li>Then return {@code f}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#nextClean()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"char JSONTokener.nextClean()"})
  public void testNextClean_givenJSONTokenerWithSIsFoo_thenReturnF() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals('f', jsonTokener.nextClean());
    assertFalse(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#nextClean()}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONTokener#nextClean()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"char JSONTokener.nextClean()"})
  public void testNextClean_thenThrowJSONException() throws JSONException {
    // Arrange
    Paths.get(System.getProperty("java.io.tmpdir"), "foo");

    // Act and Assert
    assertThrows(
        JSONException.class,
        () -> new JSONTokener(new FileReader(new FileDescriptor())).nextClean());
  }

  /**
   * Test {@link JSONTokener#toString()}.
   *
   * <p>Method under test: {@link JSONTokener#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String JSONTokener.toString()"})
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals(" at 0 [character 1 line 1]", new JSONTokener("foo").toString());
  }
}
