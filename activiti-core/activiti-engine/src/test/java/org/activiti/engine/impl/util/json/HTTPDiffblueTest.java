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
import static org.junit.Assert.assertThrows;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HTTPDiffblueTest {
  @InjectMocks
  private HTTP hTTP;

  /**
   * Test {@link HTTP#toJSONObject(String)}.
   * <ul>
   *   <li>When {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HTTP#toJSONObject(String)}
   */
  @Test
  public void testToJSONObject_whenHttpsExampleOrgExample() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(3, HTTP.toJSONObject("https://example.org/example").length());
  }

  /**
   * Test {@link HTTP#toJSONObject(String)}.
   * <ul>
   *   <li>When {@code Status-Code}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HTTP#toJSONObject(String)}
   */
  @Test
  public void testToJSONObject_whenStatusCode() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(3, HTTP.toJSONObject("Status-Code").length());
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   * <p>
   * Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject() throws JSONException {
    // Arrange
    JSONObject o = HTTP.toJSONObject("https://example.org/example");
    o.append("Method", JSONObject.NULL);

    // Act and Assert
    assertEquals("https://example.org/example  \r\n\r\n", HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   * <p>
   * Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject2() throws JSONException {
    // Arrange
    JSONObject o = HTTP.toJSONObject("https://example.org/example");
    o.append("Request-URI", JSONObject.NULL);
    o.append("Method", JSONObject.NULL);

    // Act and Assert
    assertEquals("https://example.org/example  \r\n\r\n", HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Given {@code HTTP-Version}.</li>
   *   <li>Then return {@code [null] "{}" [null] name: value:}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_givenHttpVersion_thenReturnNullNullNameValue() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("HTTP-Version", JSONObject.NULL);
    o.put("Request-URI", (Map) new HashMap<>());
    o.append("Method", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null] \"{}\" [null]\r\nname: \r\nvalue: \r\n\r\n", HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Given {@code Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_givenKey() throws JSONException {
    // Arrange
    JSONObject o = HTTP.toJSONObject("https://example.org/example");
    o.put("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("https://example.org/example  \r\n\r\n", HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Given {@code Status-Code}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_givenStatusCode() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Status-Code", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Method", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>When toJSONObject {@code =;}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_whenToJSONObjectEqualsSignSemicolon() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> HTTP.toString(Cookie.toJSONObject("=;")));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>When toJSONObject {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_whenToJSONObjectHttpsExampleOrgExample() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("https://example.org/example  \r\n\r\n",
        HTTP.toString(HTTP.toJSONObject("https://example.org/example")));
  }
}
