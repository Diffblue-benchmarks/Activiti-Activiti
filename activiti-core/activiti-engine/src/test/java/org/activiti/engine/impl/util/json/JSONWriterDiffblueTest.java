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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(MockitoJUnitRunner.class)
public class JSONWriterDiffblueTest {
  @InjectMocks private JSONWriter jSONWriter;

  @Mock private Writer writer;

  /**
   * Test {@link JSONWriter#JSONWriter(Writer)}.
   *
   * <p>Method under test: {@link JSONWriter#JSONWriter(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONWriter.<init>(Writer)"})
  public void testNewJSONWriter() {
    // Arrange and Act
    JSONWriter actualJsonWriter = new JSONWriter(new StringWriter());

    // Assert
    assertEquals("", actualJsonWriter.writer.toString());
    assertEquals('i', actualJsonWriter.mode);
  }

  /**
   * Test {@link JSONWriter#array()}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter#JSONWriter(Writer)} with w is {@link PipedWriter#PipedWriter()}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#array()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.array()"})
  public void testArray_givenJSONWriterWithWIsPipedWriter_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new PipedWriter()).array());
  }

  /**
   * Test {@link JSONWriter#array()}.
   *
   * <ul>
   *   <li>Then calls {@link Writer#write(String)}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#array()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.array()"})
  public void testArray_thenCallsWrite() throws IOException, JSONException {
    // Arrange
    doThrow(new JSONException("An error occurred")).when(writer).write(Mockito.<String>any());

    // Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.array());
    verify(writer).write("[");
  }

  /**
   * Test {@link JSONWriter#array()}.
   *
   * <ul>
   *   <li>Then {@link JSONWriter#JSONWriter(Writer)} with w is {@link StringWriter#StringWriter()}
   *       {@link JSONWriter#writer} toString is {@code [}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#array()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.array()"})
  public void testArray_thenJSONWriterWithWIsStringWriterWriterToStringIsLeftSquareBracket()
      throws JSONException {
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
   *
   * <p>Method under test: {@link JSONWriter#endArray()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.endArray()"})
  public void testEndArray() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new StringWriter()).endArray());
  }

  /**
   * Test {@link JSONWriter#endObject()}.
   *
   * <p>Method under test: {@link JSONWriter#endObject()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.endObject()"})
  public void testEndObject() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new StringWriter()).endObject());
  }

  /**
   * Test {@link JSONWriter#key(String)}.
   *
   * <ul>
   *   <li>When {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#key(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.key(String)"})
  public void testKey_whenFoo() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new StringWriter()).key("foo"));
  }

  /**
   * Test {@link JSONWriter#key(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#key(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.key(String)"})
  public void testKey_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new StringWriter()).key(null));
  }

  /**
   * Test {@link JSONWriter#object()}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter#JSONWriter(Writer)} with w is {@link PipedWriter#PipedWriter()}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#object()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.object()"})
  public void testObject_givenJSONWriterWithWIsPipedWriter_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new PipedWriter()).object());
  }

  /**
   * Test {@link JSONWriter#object()}.
   *
   * <ul>
   *   <li>Then calls {@link Writer#write(String)}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#object()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.object()"})
  public void testObject_thenCallsWrite() throws IOException, JSONException {
    // Arrange
    doThrow(new JSONException("An error occurred")).when(writer).write(Mockito.<String>any());

    // Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.object());
    verify(writer).write("{");
  }

  /**
   * Test {@link JSONWriter#object()}.
   * <ul>
   *   <li>Then {@link JSONWriter#JSONWriter(Writer)} with w is {@link StringWriter#StringWriter()} {@link JSONWriter#writer} toString is {@code {}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#object()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.object()"})
  public void testObject_thenJSONWriterWithWIsStringWriterWriterToStringIsLeftCurlyBracket()
      throws JSONException {
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
   *
   * <ul>
   *   <li>When {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(boolean)"})
  public void testValueWithB_whenFalse() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new StringWriter()).value(false));
  }

  /**
   * Test {@link JSONWriter#value(boolean)} with {@code b}.
   *
   * <ul>
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(boolean)"})
  public void testValueWithB_whenTrue() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new StringWriter()).value(true));
  }

  /**
   * Test {@link JSONWriter#value(double)} with {@code d}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter#JSONWriter(Writer)} with w is {@link
   *       StringWriter#StringWriter()}.
   *   <li>When {@code 0.5}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(double)"})
  public void testValueWithD_givenJSONWriterWithWIsStringWriter_when05_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new StringWriter()).value(0.5d));
  }

  /**
   * Test {@link JSONWriter#value(double)} with {@code d}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter#JSONWriter(Writer)} with w is {@link
   *       StringWriter#StringWriter()}.
   *   <li>When ten.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(double)"})
  public void testValueWithD_givenJSONWriterWithWIsStringWriter_whenTen_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new StringWriter()).value(10.0d));
  }

  /**
   * Test {@link JSONWriter#value(long)} with {@code l}.
   *
   * <p>Method under test: {@link JSONWriter#value(long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(long)"})
  public void testValueWithL() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new StringWriter()).value(1L));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter#JSONWriter(Writer)} with w is {@link
   *       StringWriter#StringWriter()}.
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJSONWriterWithWIsStringWriter_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(
        JSONException.class, () -> new JSONWriter(new StringWriter()).value(JSONObject.NULL));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter#JSONWriter(Writer)} with w is {@link
   *       StringWriter#StringWriter()}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJSONWriterWithWIsStringWriter_whenNull2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONWriter(new StringWriter()).value(null));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter}.
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJSONWriter_whenArrayList_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(new ArrayList<>()));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter}.
   *   <li>When empty string.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJSONWriter_whenEmptyString_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(""));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter}.
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJSONWriter_whenHashMap_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(new HashMap<>()));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter}.
   *   <li>When {@link JSONArray#JSONArray()}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJSONWriter_whenJSONArray_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(new JSONArray()));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter}.
   *   <li>When {@link JSONObject#JSONObject()}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJSONWriter_whenJSONObject_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(new JSONObject()));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter}.
   *   <li>When {@code null}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJSONWriter_whenNull_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value("null"));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter}.
   *   <li>When one.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJSONWriter_whenOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(1));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONWriter}.
   *   <li>When ten.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJSONWriter_whenTen_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(10.0f));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@code Json String}.
   *   <li>Then calls {@link JSONString#toJSONString()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenJsonString_thenCallsToJSONString() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    JSONString jsonString = mock(JSONString.class);
    when(jsonString.toJSONString()).thenReturn("Json String");

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(jsonString));
    verify(jsonString).toJSONString();
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   * <ul>
   *   <li>Given {@code {}.</li>
   *   <li>When {@link JSONObject#JSONObject()} append {@code {} and {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenLeftCurlyBracket_whenJSONObjectAppendLeftCurlyBracketAndNull()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(jsonObject));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenNull_whenArrayListAddNull_thenThrowJSONException()
      throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(objectList));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenNull_whenHashMapNullIsNull_thenThrowJSONException()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(objectObjectMap));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>Given one hundred five.
   *   <li>When {@link JSONArray#JSONArray()} one hundred five is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_givenOneHundredFive_whenJSONArrayOneHundredFiveIsTrue()
      throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray();
    jsonArray.put(105, true);

    // Act and Assert
    assertThrows(JSONException.class, () -> jSONWriter.value(jsonArray));
  }

  /**
   * Test {@link JSONWriter#value(Object)} with {@code o}.
   *
   * <ul>
   *   <li>When toJSONObject {@code https://example.org/example}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONWriter JSONWriter.value(Object)"})
  public void testValueWithO_whenToJSONObjectHttpsExampleOrgExample_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(
        JSONException.class,
        () -> jSONWriter.value(HTTP.toJSONObject("https://example.org/example")));
  }
}
