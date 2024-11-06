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
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CookieListDiffblueTest {
  @InjectMocks
  private CookieList cookieList;

  /**
   * Test {@link CookieList#toJSONObject(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toJSONObject(String)}
   */
  @Test
  public void testToJSONObject_whenEmptyString_thenReturnLengthIsZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, CookieList.toJSONObject("").length());
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return {@code name=;value=;Key=["42"]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_given42_thenReturnNameValueKey42() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Key", "42");

    // Act and Assert
    assertEquals("name=;value=;Key=[\"42\"]", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>Then return {@code name=;value=;Key=[""]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_givenEmptyString_thenReturnNameValueKey() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Key", "");

    // Act and Assert
    assertEquals("name=;value=;Key=[\"\"]", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Given {@code =}.</li>
   *   <li>Then return {@code name=;value=;%3d=[null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_givenEqualsSign_thenReturnNameValue3dNull() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("=", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=;%3d=[null]", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Given forty-two.</li>
   *   <li>Then return {@code name=;value=;Key=[42]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_givenFortyTwo_thenReturnNameValueKey42() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Key", 42);

    // Act and Assert
    assertEquals("name=;value=;Key=[42]", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_givenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("Key", (Map) value);

    // Act and Assert
    assertEquals("name=;value=;Key={\"null\":null}", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code name=;value=;Key={"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_givenHashMapNullIsNull_thenReturnNameValueKeyNullNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("Key", (Map) value);

    // Act and Assert
    assertEquals("name=;value=;Key={\"null\":null}", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code name=;value=;Key={}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_givenHashMap_thenReturnNameValueKey() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("name=;value=;Key={}", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Then return {@code name=;value=;Key=[null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_thenReturnNameValueKeyNull() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=;Key=[null]", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Then return {@code name=;value=;Key=[null,null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_thenReturnNameValueKeyNullNull() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Key", JSONObject.NULL);
    o.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=;Key=[null,null]", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>When toJSONObject {@code =;}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_whenToJSONObjectEqualsSignSemicolon() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("name=;value=", CookieList.toString(Cookie.toJSONObject("=;")));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>When toJSONObject {@code =;} {@code Key} is {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_whenToJSONObjectEqualsSignSemicolonKeyIsNull() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=", CookieList.toString(o));
  }
}
