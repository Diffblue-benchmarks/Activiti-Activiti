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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JSONTokenerDiffblueTest {
  /**
   * Test {@link JSONTokener#JSONTokener(Reader)}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then return not end.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#JSONTokener(Reader)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONTokener.<init>(Reader)"})
  public void testNewJSONTokener_whenA_thenReturnNotEnd() {
    // Arrange, Act and Assert
    assertFalse(
        (new JSONTokener(new InputStreamReader(new ByteArrayInputStream(new byte[]{'A', 1, 'A', 1, 'A', 1, 'A', 1}))))
            .end());
  }

  /**
   * Test {@link JSONTokener#JSONTokener(Reader)}.
   * <ul>
   *   <li>When {@link StringReader#StringReader(String)} with {@code foo}.</li>
   *   <li>Then return not end.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#JSONTokener(Reader)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONTokener.<init>(Reader)"})
  public void testNewJSONTokener_whenStringReaderWithFoo_thenReturnNotEnd() {
    // Arrange, Act and Assert
    assertFalse((new JSONTokener(new StringReader("foo"))).end());
  }

  /**
   * Test {@link JSONTokener#back()}.
   * <p>
   * Method under test: {@link JSONTokener#back()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONTokener.back()"})
  public void testBack() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener("foo")).back());
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   * <ul>
   *   <li>When {@code 0}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_when0_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, JSONTokener.dehexchar('0'));
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then return ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_whenA_thenReturnTen() {
    // Arrange, Act and Assert
    assertEquals(10, JSONTokener.dehexchar('A'));
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   * <ul>
   *   <li>When {@code a}.</li>
   *   <li>Then return ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_whenA_thenReturnTen2() {
    // Arrange, Act and Assert
    assertEquals(10, JSONTokener.dehexchar('a'));
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   * <ul>
   *   <li>When {@code G}.</li>
   *   <li>Then return minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_whenG_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(-1, JSONTokener.dehexchar('G'));
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   * <ul>
   *   <li>When {@code g}.</li>
   *   <li>Then return minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_whenG_thenReturnMinusOne2() {
    // Arrange, Act and Assert
    assertEquals(-1, JSONTokener.dehexchar('g'));
  }

  /**
   * Test {@link JSONTokener#dehexchar(char)}.
   * <ul>
   *   <li>When {@code /}.</li>
   *   <li>Then return minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int JSONTokener.dehexchar(char)"})
  public void testDehexchar_whenSlash_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(-1, JSONTokener.dehexchar('/'));
  }

  /**
   * Test {@link JSONTokener#end()}.
   * <p>
   * Method under test: {@link JSONTokener#end()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JSONTokener.end()"})
  public void testEnd() {
    // Arrange, Act and Assert
    assertFalse((new JSONTokener("foo")).end());
  }

  /**
   * Test {@link JSONTokener#more()}.
   * <ul>
   *   <li>Given {@link FileReader#FileReader(FileDescriptor)} with {@link FileDescriptor#FileDescriptor()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#more()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JSONTokener.more()"})
  public void testMore_givenFileReaderWithFileDescriptor_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).more());
  }

  /**
   * Test {@link JSONTokener#more()}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is empty string.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#more()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>Then not {@link JSONTokener#JSONTokener(String)} with s is {@code foo} end.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#more()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JSONTokener.more()"})
  public void testMore_givenJSONTokenerWithSIsFoo_thenNotJSONTokenerWithSIsFooEnd() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act
    boolean actualMoreResult = jsonTokener.more();

    // Assert
    assertFalse(jsonTokener.end());
    assertTrue(actualMoreResult);
  }

  /**
   * Test {@link JSONTokener#next(char)} with {@code c}.
   * <ul>
   *   <li>Given {@link FileReader#FileReader(FileDescriptor)} with {@link FileDescriptor#FileDescriptor()}.</li>
   *   <li>When {@code A}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.next(char)"})
  public void testNextWithC_givenFileReaderWithFileDescriptor_whenA_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).next('A'));
  }

  /**
   * Test {@link JSONTokener#next(char)} with {@code c}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is empty string.</li>
   *   <li>When {@code A}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.next(char)"})
  public void testNextWithC_givenJSONTokenerWithSIsEmptyString_whenA_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener("")).next('A'));
  }

  /**
   * Test {@link JSONTokener#next(char)} with {@code c}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>When {@code A}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.next(char)"})
  public void testNextWithC_givenJSONTokenerWithSIsFoo_whenA_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener("foo")).next('A'));
  }

  /**
   * Test {@link JSONTokener#next(char)} with {@code c}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>When {@code f}.</li>
   *   <li>Then return {@code f}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.next(char)"})
  public void testNextWithC_givenJSONTokenerWithSIsFoo_whenF_thenReturnF() throws JSONException {
    // Arrange, Act and Assert
    assertEquals('f', (new JSONTokener("foo")).next('f'));
  }

  /**
   * Test {@link JSONTokener#next(int)} with {@code n}.
   * <ul>
   *   <li>Given {@link FileReader#FileReader(FileDescriptor)} with {@link FileDescriptor#FileDescriptor()}.</li>
   *   <li>When one.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.next(int)"})
  public void testNextWithN_givenFileReaderWithFileDescriptor_whenOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).next(1));
  }

  /**
   * Test {@link JSONTokener#next(int)} with {@code n}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is empty string.</li>
   *   <li>When one.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.next(int)"})
  public void testNextWithN_givenJSONTokenerWithSIsEmptyString_whenOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener("")).next(1));
  }

  /**
   * Test {@link JSONTokener#next(int)} with {@code n}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code f}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.next(int)"})
  public void testNextWithN_givenJSONTokenerWithSIsFoo_whenOne_thenReturnF() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("f", (new JSONTokener("foo")).next(1));
  }

  /**
   * Test {@link JSONTokener#next(int)} with {@code n}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>When zero.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.next(int)"})
  public void testNextWithN_givenJSONTokenerWithSIsFoo_whenZero_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", (new JSONTokener("foo")).next(0));
  }

  /**
   * Test {@link JSONTokener#next()}.
   * <ul>
   *   <li>Given {@link FileReader#FileReader(FileDescriptor)} with {@link FileDescriptor#FileDescriptor()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.next()"})
  public void testNext_givenFileReaderWithFileDescriptor_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).next());
  }

  /**
   * Test {@link JSONTokener#next()}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is empty string.</li>
   *   <li>Then return null.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>Then return {@code f}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#next()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.next()"})
  public void testNext_givenJSONTokenerWithSIsFoo_thenReturnF() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals('f', jsonTokener.next());
    assertFalse(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#nextClean()}.
   * <ul>
   *   <li>Given {@link FileReader#FileReader(FileDescriptor)} with {@link FileDescriptor#FileDescriptor()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextClean()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.nextClean()"})
  public void testNextClean_givenFileReaderWithFileDescriptor_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).nextClean());
  }

  /**
   * Test {@link JSONTokener#nextClean()}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is empty string.</li>
   *   <li>Then return null.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextClean()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.nextClean()"})
  public void testNextClean_givenJSONTokenerWithSIsEmptyString_thenReturnNull() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("");

    // Act and Assert
    assertEquals('\u0000', jsonTokener.nextClean());
    assertTrue(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#nextClean()}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>Then return {@code f}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextClean()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.nextClean()"})
  public void testNextClean_givenJSONTokenerWithSIsFoo_thenReturnF() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals('f', jsonTokener.nextClean());
    assertFalse(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#nextString(char)}.
   * <p>
   * Method under test: {@link JSONTokener#nextString(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.nextString(char)"})
  public void testNextString() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(",:]}/\\\"[{;=#")).nextString('A'));
  }

  /**
   * Test {@link JSONTokener#nextString(char)}.
   * <ul>
   *   <li>Given {@link FileReader#FileReader(FileDescriptor)} with {@link FileDescriptor#FileDescriptor()}.</li>
   *   <li>When {@code A}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextString(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.nextString(char)"})
  public void testNextString_givenFileReaderWithFileDescriptor_whenA_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).nextString('A'));
  }

  /**
   * Test {@link JSONTokener#nextString(char)}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>When {@code A}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextString(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.nextString(char)"})
  public void testNextString_givenJSONTokenerWithSIsFoo_whenA_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener("foo")).nextString('A'));
  }

  /**
   * Test {@link JSONTokener#nextString(char)}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>When {@code f}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextString(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.nextString(char)"})
  public void testNextString_givenJSONTokenerWithSIsFoo_whenF_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", (new JSONTokener("foo")).nextString('f'));
  }

  /**
   * Test {@link JSONTokener#nextTo(char)} with {@code d}.
   * <ul>
   *   <li>Given {@link FileReader#FileReader(FileDescriptor)} with {@link FileDescriptor#FileDescriptor()}.</li>
   *   <li>When {@code A}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextTo(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.nextTo(char)"})
  public void testNextToWithD_givenFileReaderWithFileDescriptor_whenA_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).nextTo('A'));
  }

  /**
   * Test {@link JSONTokener#nextTo(char)} with {@code d}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>When {@code A}.</li>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextTo(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.nextTo(char)"})
  public void testNextToWithD_givenJSONTokenerWithSIsFoo_whenA_thenReturnFoo() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals("foo", jsonTokener.nextTo('A'));
    assertTrue(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#nextTo(char)} with {@code d}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>When {@code f}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextTo(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.nextTo(char)"})
  public void testNextToWithD_givenJSONTokenerWithSIsFoo_whenF_thenReturnEmptyString() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals("", jsonTokener.nextTo('f'));
    assertFalse(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#nextValue()}.
   * <p>
   * Method under test: {@link JSONTokener#nextValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object JSONTokener.nextValue()"})
  public void testNextValue() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(",:]}/\\\"[{;=#")).nextValue());
  }

  /**
   * Test {@link JSONTokener#nextValue()}.
   * <ul>
   *   <li>Given {@link FileReader#FileReader(FileDescriptor)} with {@link FileDescriptor#FileDescriptor()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object JSONTokener.nextValue()"})
  public void testNextValue_givenFileReaderWithFileDescriptor_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).nextValue());
  }

  /**
   * Test {@link JSONTokener#nextValue()}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code 42}.</li>
   *   <li>Then return intValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object JSONTokener.nextValue()"})
  public void testNextValue_givenJSONTokenerWithSIs42_thenReturnIntValueIsFortyTwo() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, ((Integer) (new JSONTokener("42")).nextValue()).intValue());
  }

  /**
   * Test {@link JSONTokener#nextValue()}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is empty string.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object JSONTokener.nextValue()"})
  public void testNextValue_givenJSONTokenerWithSIsEmptyString_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener("")).nextValue());
  }

  /**
   * Test {@link JSONTokener#nextValue()}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@link Boolean#FALSE} toString.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object JSONTokener.nextValue()"})
  public void testNextValue_givenJSONTokenerWithSIsFalseToString_thenReturnFalse() throws JSONException {
    // Arrange, Act and Assert
    assertFalse((Boolean) (new JSONTokener(Boolean.FALSE.toString())).nextValue());
  }

  /**
   * Test {@link JSONTokener#nextValue()}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object JSONTokener.nextValue()"})
  public void testNextValue_givenJSONTokenerWithSIsFoo_thenReturnFoo() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("foo", (new JSONTokener("foo")).nextValue());
  }

  /**
   * Test {@link JSONTokener#nextValue()}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@link Boolean#TRUE} toString.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#nextValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object JSONTokener.nextValue()"})
  public void testNextValue_givenJSONTokenerWithSIsTrueToString_thenReturnTrue() throws JSONException {
    // Arrange, Act and Assert
    assertTrue((Boolean) (new JSONTokener(Boolean.TRUE.toString())).nextValue());
  }

  /**
   * Test {@link JSONTokener#skipTo(char)}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>When {@code A}.</li>
   *   <li>Then return null.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#skipTo(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.skipTo(char)"})
  public void testSkipTo_givenJSONTokenerWithSIsFoo_whenA_thenReturnNull() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals('\u0000', jsonTokener.skipTo('A'));
    assertTrue(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#skipTo(char)}.
   * <ul>
   *   <li>Given {@link JSONTokener#JSONTokener(String)} with s is {@code foo}.</li>
   *   <li>When {@code f}.</li>
   *   <li>Then return {@code f}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONTokener#skipTo(char)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"char JSONTokener.skipTo(char)"})
  public void testSkipTo_givenJSONTokenerWithSIsFoo_whenF_thenReturnF() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals('f', jsonTokener.skipTo('f'));
    assertFalse(jsonTokener.end());
  }

  /**
   * Test {@link JSONTokener#toString()}.
   * <p>
   * Method under test: {@link JSONTokener#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String JSONTokener.toString()"})
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals(" at 0 [character 1 line 1]", (new JSONTokener("foo")).toString());
  }
}
