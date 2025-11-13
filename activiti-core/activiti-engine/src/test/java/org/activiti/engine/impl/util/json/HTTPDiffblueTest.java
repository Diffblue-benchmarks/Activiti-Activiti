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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HTTPDiffblueTest {
  /**
   * Test {@link HTTP#toJSONObject(String)}.
   *
   * <ul>
   *   <li>When {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link HTTP#toJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject HTTP.toJSONObject(String)"})
  public void testToJSONObject_whenHttpsExampleOrgExample() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(3, HTTP.toJSONObject("https://example.org/example").length());
  }

  /**
   * Test {@link HTTP#toJSONObject(String)}.
   *
   * <ul>
   *   <li>When {@code Status-Code}.
   * </ul>
   *
   * <p>Method under test: {@link HTTP#toJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject HTTP.toJSONObject(String)"})
  public void testToJSONObject_whenStatusCode() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(3, HTTP.toJSONObject("Status-Code").length());
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   *
   * <p>Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTP.toString(JSONObject)"})
  public void testToStringWithJSONObject() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Method", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   *
   * <p>Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTP.toString(JSONObject)"})
  public void testToStringWithJSONObject2() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Method", JSONObject.NULL);
    o.append("HTTP-Version", JSONObject.NULL);
    o.append("Reason-Phrase", JSONObject.NULL);
    o.append("Status-Code", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null] [null] [null]\r\nname: \r\nvalue: \r\n\r\n", HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   *
   * <p>Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTP.toString(JSONObject)"})
  public void testToStringWithJSONObject3() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Request-URI", JSONObject.NULL);
    o.append("HTTP-Version", JSONObject.NULL);
    o.append("Reason-Phrase", JSONObject.NULL);
    o.append("Status-Code", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null] [null] [null]\r\nname: \r\nvalue: \r\n\r\n", HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then return {@code {} [null] [null] name: value:}.
   * </ul>
   *
   * <p>Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTP.toString(JSONObject)"})
  public void testToStringWithJSONObject_givenHashMap_thenReturnNullNullNameValue()
      throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("HTTP-Version", (Map) new HashMap<>());
    o.append("Reason-Phrase", JSONObject.NULL);
    o.append("Status-Code", JSONObject.NULL);

    // Act and Assert
    assertEquals("{} [null] [null]\r\nname: \r\nvalue: \r\n\r\n", HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When toJSONObject {@code =;} {@code Key} is {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTP.toString(JSONObject)"})
  public void testToStringWithJSONObject_givenKey_whenToJSONObjectEqualsSignSemicolonKeyIsNull()
      throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("Key", JSONObject.NULL);
    o.append("HTTP-Version", JSONObject.NULL);
    o.append("Reason-Phrase", JSONObject.NULL);
    o.append("Status-Code", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null] [null] [null]\r\nname: \r\nvalue: \r\n\r\n", HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTP.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnHttpsExampleOrgExample() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "https://example.org/example  \r\n\r\n",
        HTTP.toString(HTTP.toJSONObject("https://example.org/example")));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code [null] [null] [null] name: value:}.
   * </ul>
   *
   * <p>Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTP.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnNullNullNullNameValue() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("HTTP-Version", JSONObject.NULL);
    o.append("Reason-Phrase", JSONObject.NULL);
    o.append("Status-Code", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null] [null] [null]\r\nname: \r\nvalue: \r\n\r\n", HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code [null] [null,null] [null] name: value:}.
   * </ul>
   *
   * <p>Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTP.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnNullNullNullNullNameValue()
      throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Status-Code", JSONObject.NULL);
    o.append("HTTP-Version", JSONObject.NULL);
    o.append("Reason-Phrase", JSONObject.NULL);
    o.append("Status-Code", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null] [null,null] [null]\r\nname: \r\nvalue: \r\n\r\n", HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTP.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Status-Code", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> HTTP.toString(o));
  }

  /**
   * Test {@link HTTP#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>When toJSONObject {@code =;}.
   * </ul>
   *
   * <p>Method under test: {@link HTTP#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTP.toString(JSONObject)"})
  public void testToStringWithJSONObject_whenToJSONObjectEqualsSignSemicolon()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> HTTP.toString(Cookie.toJSONObject("=;")));
  }
}
