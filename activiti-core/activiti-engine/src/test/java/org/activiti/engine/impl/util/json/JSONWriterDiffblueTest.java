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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import java.io.PipedWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;

public class JSONWriterDiffblueTest {
  /**
   * Test {@link JSONWriter#JSONWriter(Writer)}.
   * <p>
   * Method under test: {@link JSONWriter#JSONWriter(Writer)}
   */
  @Test
  public void testNewJSONWriter() {
    // Arrange and Act
    JSONWriter actualJsonWriter = new JSONWriter(new StringWriter());

    // Assert
    assertEquals("", actualJsonWriter.writer.toString());
    assertEquals('i', actualJsonWriter.mode);
  }

  /**
   * Test {@link JSONWriter#array()}.
   * <ul>
   *   <li>Given {@link JSONWriter#JSONWriter(Writer)} with w is
   * {@link PipedWriter#PipedWriter()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#array()}
   */
  @Test
  public void testArray_givenJSONWriterWithWIsPipedWriter_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new PipedWriter())).array());
  }

  /**
   * Test {@link JSONWriter#array()}.
   * <ul>
   *   <li>Then {@link JSONWriter#JSONWriter(Writer)} with w is
   * {@link StringWriter#StringWriter()} {@link JSONWriter#writer} toString is
   * {@code [}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#array()}
   */
  @Test
  public void testArray_thenJSONWriterWithWIsStringWriterWriterToStringIsLeftSquareBracket() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act
    JSONWriter actualArrayResult = jsonWriter.array();

    // Assert
    assertEquals("[", jsonWriter.writer.toString());
    assertEquals('a', jsonWriter.mode);
    assertSame(jsonWriter, actualArrayResult);
  }

  /**
   * Test {@link JSONWriter#endArray()}.
   * <p>
   * Method under test: {@link JSONWriter#endArray()}
   */
  @Test
  public void testEndArray() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).endArray());
  }

  /**
   * Test {@link JSONWriter#endObject()}.
   * <p>
   * Method under test: {@link JSONWriter#endObject()}
   */
  @Test
  public void testEndObject() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).endObject());
  }

  /**
   * Test {@link JSONWriter#key(String)}.
   * <ul>
   *   <li>When {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#key(String)}
   */
  @Test
  public void testKey_whenFoo() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).key("foo"));
  }

  /**
   * Test {@link JSONWriter#key(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#key(String)}
   */
  @Test
  public void testKey_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).key(null));
  }

  /**
   * Test {@link JSONWriter#object()}.
   * <ul>
   *   <li>Given {@link JSONWriter#JSONWriter(Writer)} with w is
   * {@link PipedWriter#PipedWriter()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#object()}
   */
  @Test
  public void testObject_givenJSONWriterWithWIsPipedWriter_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new PipedWriter())).object());
  }

  /**
   * Test {@link JSONWriter#object()}.
   * <ul>
   *   <li>Then {@link JSONWriter#JSONWriter(Writer)} with w is
   * {@link StringWriter#StringWriter()} {@link JSONWriter#writer} toString is
   * {@code {}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#object()}
   */
  @Test
  public void testObject_thenJSONWriterWithWIsStringWriterWriterToStringIsLeftCurlyBracket() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act
    JSONWriter actualObjectResult = jsonWriter.object();

    // Assert
    assertEquals("{", jsonWriter.writer.toString());
    assertEquals('k', jsonWriter.mode);
    assertSame(jsonWriter, actualObjectResult);
  }

  /**
   * Test {@link JSONWriter#value(boolean)} with {@code b}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(boolean)}
   */
  @Test
  public void testValueWithB_whenFalse() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(false));
  }

  /**
   * Test {@link JSONWriter#value(boolean)} with {@code b}.
   * <ul>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(boolean)}
   */
  @Test
  public void testValueWithB_whenTrue() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(true));
  }

  /**
   * Test {@link JSONWriter#value(double)} with {@code d}.
   * <ul>
   *   <li>When {@code 0.5}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(double)}
   */
  @Test
  public void testValueWithD_when05_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(0.5d));
  }

  /**
   * Test {@link JSONWriter#value(double)} with {@code d}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(double)}
   */
  @Test
  public void testValueWithD_whenTen_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(10.0d));
  }

  /**
   * Test {@link JSONWriter#value(long)} with {@code l}.
   * <p>
   * Method under test: {@link JSONWriter#value(long)}
   */
  @Test
  public void testValueWithL() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(1L));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>Given {@code {}.</li>
   *   <li>When {@link JSONObject#JSONObject()} append {@code {} and {@link
   * JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_givenLeftCurlyBracket_whenJSONObjectAppendLeftCurlyBracketAndNull() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(jsonObject));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>Given one hundred five.</li>
   *   <li>When {@link JSONArray#JSONArray()} one hundred five is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_givenOneHundredFive_whenJSONArrayOneHundredFiveIsTrue() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    JSONArray jsonArray = new JSONArray();
    jsonArray.put(105, true);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(jsonArray));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When {@code 0.5}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_when05_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value((Object) 0.5d));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_whenArrayList_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(new ArrayList<>()));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_whenEmptyString_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(""));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_whenHashMap_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(new HashMap<>()));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_whenJSONArray_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(new JSONArray()));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_whenJSONObject_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(new JSONObject()));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(JSONObject.NULL));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value("null"));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(null));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_whenOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(1));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_whenTen_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value((Object) 10.0d));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(10.0f));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When toJSONObject {@code https://example.org/example}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_whenToJSONObjectHttpsExampleOrgExample_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(HTTP.toJSONObject("https://example.org/example")));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValueWithO_whenTrue_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value((Object) true));
  }
}
