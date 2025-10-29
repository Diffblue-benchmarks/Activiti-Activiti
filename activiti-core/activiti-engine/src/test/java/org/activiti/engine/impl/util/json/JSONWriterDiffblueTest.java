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
   * Method under test: {@link JSONWriter#array()}
   */
  @Test
  public void testArray() throws JSONException {
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
   * Method under test: {@link JSONWriter#array()}
   */
  @Test
  public void testArray2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new PipedWriter())).array());
  }

  /**
   * Method under test: {@link JSONWriter#endArray()}
   */
  @Test
  public void testEndArray() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).endArray());
  }

  /**
   * Method under test: {@link JSONWriter#endObject()}
   */
  @Test
  public void testEndObject() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).endObject());
  }

  /**
   * Method under test: {@link JSONWriter#key(String)}
   */
  @Test
  public void testKey() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).key("foo"));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).key(null));
  }

  /**
   * Method under test: {@link JSONWriter#object()}
   */
  @Test
  public void testObject() throws JSONException {
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
   * Method under test: {@link JSONWriter#object()}
   */
  @Test
  public void testObject2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new PipedWriter())).object());
  }

  /**
   * Method under test: {@link JSONWriter#value(double)}
   */
  @Test
  public void testValue() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(10.0d));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(0.5d));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(1L));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(JSONObject.NULL));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(1));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value("null"));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(null));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(""));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value((Object) 10.0d));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(10.0f));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value((Object) 0.5d));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value((Object) true));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(true));
    assertThrows(JSONException.class, () -> (new JSONWriter(new StringWriter())).value(false));
  }

  /**
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValue2() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(new JSONObject()));
  }

  /**
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValue3() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(new JSONArray()));
  }

  /**
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValue4() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(new HashMap<>()));
  }

  /**
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValue5() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(new ArrayList<>()));
  }

  /**
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValue6() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(HTTP.toJSONObject("https://example.org/example")));
  }

  /**
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValue7() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(jsonObject));
  }

  /**
   * Method under test: {@link JSONWriter#value(Object)}
   */
  @Test
  public void testValue8() throws JSONException {
    // Arrange
    JSONWriter jsonWriter = new JSONWriter(new StringWriter());

    JSONArray jsonArray = new JSONArray();
    jsonArray.put(105, true);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonWriter.value(jsonArray));
  }

  /**
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
}
