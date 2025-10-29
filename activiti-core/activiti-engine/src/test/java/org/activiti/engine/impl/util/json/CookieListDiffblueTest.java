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
   * Method under test: {@link CookieList#toJSONObject(String)}
   */
  @Test
  public void testToJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, CookieList.toJSONObject("").length());
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("name=;value=", CookieList.toString(Cookie.toJSONObject("=;")));
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString2() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=;Key=[null]", CookieList.toString(o));
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString3() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("name=;value=;Key={}", CookieList.toString(o));
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString4() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=", CookieList.toString(o));
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString5() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("=", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=;%3d=[null]", CookieList.toString(o));
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString6() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Key", JSONObject.NULL);
    o.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=;Key=[null,null]", CookieList.toString(o));
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString7() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Key", 42);

    // Act and Assert
    assertEquals("name=;value=;Key=[42]", CookieList.toString(o));
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString8() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Key", "42");

    // Act and Assert
    assertEquals("name=;value=;Key=[\"42\"]", CookieList.toString(o));
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString9() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("Key", "");

    // Act and Assert
    assertEquals("name=;value=;Key=[\"\"]", CookieList.toString(o));
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString10() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("Key", (Map) value);

    // Act and Assert
    assertEquals("name=;value=;Key={\"null\":null}", CookieList.toString(o));
  }

  /**
   * Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  public void testToString11() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("Key", (Map) value);

    // Act and Assert
    assertEquals("name=;value=;Key={\"null\":null}", CookieList.toString(o));
  }
}
