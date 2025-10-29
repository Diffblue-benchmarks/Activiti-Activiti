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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.io.UTF32Reader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Test;

public class JSONTokenerDiffblueTest {
  /**
   * Method under test: {@link JSONTokener#back()}
   */
  @Test
  public void testBack() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener("foo")).back());
  }

  /**
   * Method under test: {@link JSONTokener#dehexchar(char)}
   */
  @Test
  public void testDehexchar() {
    // Arrange, Act and Assert
    assertEquals(10, JSONTokener.dehexchar('A'));
    assertEquals(0, JSONTokener.dehexchar('0'));
    assertEquals(-1, JSONTokener.dehexchar('/'));
    assertEquals(-1, JSONTokener.dehexchar('G'));
    assertEquals(10, JSONTokener.dehexchar('a'));
    assertEquals(-1, JSONTokener.dehexchar('g'));
  }

  /**
   * Method under test: {@link JSONTokener#end()}
   */
  @Test
  public void testEnd() {
    // Arrange, Act and Assert
    assertFalse((new JSONTokener("foo")).end());
  }

  /**
   * Method under test: {@link JSONTokener#more()}
   */
  @Test
  public void testMore() throws JSONException {
    // Arrange, Act and Assert
    assertTrue((new JSONTokener("foo")).more());
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).more());
  }

  /**
   * Method under test: {@link JSONTokener#more()}
   */
  @Test
  public void testMore2() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("");

    // Act and Assert
    assertFalse(jsonTokener.more());
    assertTrue(jsonTokener.end());
  }

  /**
   * Method under test: {@link JSONTokener#next()}
   */
  @Test
  public void testNext() throws JSONException {
    // Arrange, Act and Assert
    assertEquals('f', (new JSONTokener("foo")).next());
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).next());
    assertThrows(JSONException.class, () -> (new JSONTokener("foo")).next('A'));
    assertThrows(JSONException.class, () -> (new JSONTokener("")).next('A'));
    assertEquals('f', (new JSONTokener("foo")).next('f'));
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).next('A'));
    assertEquals("f", (new JSONTokener("foo")).next(1));
    assertThrows(JSONException.class, () -> (new JSONTokener("")).next(1));
    assertEquals("", (new JSONTokener("foo")).next(0));
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).next(1));
  }

  /**
   * Method under test: {@link JSONTokener#next()}
   */
  @Test
  public void testNext2() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("");

    // Act and Assert
    assertEquals('\u0000', jsonTokener.next());
    assertTrue(jsonTokener.end());
  }

  /**
   * Method under test: {@link JSONTokener#nextClean()}
   */
  @Test
  public void testNextClean() throws JSONException {
    // Arrange, Act and Assert
    assertEquals('f', (new JSONTokener("foo")).nextClean());
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).nextClean());
  }

  /**
   * Method under test: {@link JSONTokener#nextClean()}
   */
  @Test
  public void testNextClean2() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("");

    // Act and Assert
    assertEquals('\u0000', jsonTokener.nextClean());
    assertTrue(jsonTokener.end());
  }

  /**
   * Method under test: {@link JSONTokener#nextString(char)}
   */
  @Test
  public void testNextString() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener("foo")).nextString('A'));
    assertThrows(JSONException.class, () -> (new JSONTokener(",:]}/\\\"[{;=#")).nextString('A'));
    assertEquals("", (new JSONTokener("foo")).nextString('f'));
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).nextString('A'));
  }

  /**
   * Method under test: {@link JSONTokener#nextTo(char)}
   */
  @Test
  public void testNextTo() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals("foo", jsonTokener.nextTo('A'));
    assertTrue(jsonTokener.end());
  }

  /**
   * Method under test: {@link JSONTokener#nextTo(char)}
   */
  @Test
  public void testNextTo2() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals("", jsonTokener.nextTo('f'));
    assertFalse(jsonTokener.end());
  }

  /**
   * Method under test: {@link JSONTokener#nextTo(char)}
   */
  @Test
  public void testNextTo3() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).nextTo('A'));
  }

  /**
   * Method under test: {@link JSONTokener#nextTo(String)}
   */
  @Test
  public void testNextTo4() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("foo", (new JSONTokener("foo")).nextTo("Delimiters"));
  }

  /**
   * Method under test: {@link JSONTokener#nextTo(String)}
   */
  @Test
  public void testNextTo5() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("h", (new HTTPTokener("https://example.org/example")).nextTo("Delimiters"));
  }

  /**
   * Method under test: {@link JSONTokener#nextTo(String)}
   */
  @Test
  public void testNextTo6() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class,
        () -> (new JSONTokener(new FileReader(new FileDescriptor()))).nextTo("Delimiters"));
  }

  /**
   * Method under test: {@link JSONTokener#nextValue()}
   */
  @Test
  public void testNextValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("foo", (new JSONTokener("foo")).nextValue());
    assertThrows(JSONException.class, () -> (new JSONTokener(",:]}/\\\"[{;=#")).nextValue());
    assertThrows(JSONException.class, () -> (new JSONTokener("")).nextValue());
    assertThrows(JSONException.class, () -> (new JSONTokener(new FileReader(new FileDescriptor()))).nextValue());
  }

  /**
   * Method under test: {@link JSONTokener#skipTo(char)}
   */
  @Test
  public void testSkipTo() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals('\u0000', jsonTokener.skipTo('A'));
    assertTrue(jsonTokener.end());
  }

  /**
   * Method under test: {@link JSONTokener#skipTo(char)}
   */
  @Test
  public void testSkipTo2() throws JSONException {
    // Arrange
    JSONTokener jsonTokener = new JSONTokener("foo");

    // Act and Assert
    assertEquals('f', jsonTokener.skipTo('f'));
    assertFalse(jsonTokener.end());
  }

  /**
   * Method under test: {@link JSONTokener#syntaxError(String)}
   */
  @Test
  public void testSyntaxError() {
    // Arrange and Act
    JSONException actualSyntaxErrorResult = (new JSONTokener("foo")).syntaxError("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost at 0 [character 1 line 1]",
        actualSyntaxErrorResult.getLocalizedMessage());
    assertEquals("Not all who wander are lost at 0 [character 1 line 1]", actualSyntaxErrorResult.getMessage());
    assertNull(actualSyntaxErrorResult.getCause());
    assertEquals(0, actualSyntaxErrorResult.getSuppressed().length);
  }

  /**
   * Method under test: {@link JSONTokener#syntaxError(String)}
   */
  @Test
  public void testSyntaxError2() {
    // Arrange
    UTF32Reader reader = mock(UTF32Reader.class);
    when(reader.markSupported()).thenReturn(true);

    // Act
    JSONException actualSyntaxErrorResult = (new JSONTokener(reader)).syntaxError("Not all who wander are lost");

    // Assert
    verify(reader).markSupported();
    assertEquals("Not all who wander are lost at 0 [character 1 line 1]",
        actualSyntaxErrorResult.getLocalizedMessage());
    assertEquals("Not all who wander are lost at 0 [character 1 line 1]", actualSyntaxErrorResult.getMessage());
    assertNull(actualSyntaxErrorResult.getCause());
    assertEquals(0, actualSyntaxErrorResult.getSuppressed().length);
  }

  /**
   * Method under test: {@link JSONTokener#JSONTokener(Reader)}
   */
  @Test
  public void testNewJSONTokener() {
    // Arrange, Act and Assert
    assertFalse((new JSONTokener(new StringReader("foo"))).end());
    assertFalse(
        (new JSONTokener(new InputStreamReader(new ByteArrayInputStream(new byte[]{'A', 1, 'A', 1, 'A', 1, 'A', 1}))))
            .end());
    assertFalse((new JSONTokener(new InputStreamReader(mock(DataInputStream.class)))).end());
    assertFalse((new JSONTokener("foo")).end());
  }

  /**
   * Method under test: {@link JSONTokener#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals(" at 0 [character 1 line 1]", (new JSONTokener("foo")).toString());
  }
}
