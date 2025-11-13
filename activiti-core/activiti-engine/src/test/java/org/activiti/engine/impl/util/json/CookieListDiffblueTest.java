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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CookieListDiffblueTest {
  /**
   * Test {@link CookieList#toJSONObject(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link CookieList#toJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject CookieList.toJSONObject(String)"})
  public void testToJSONObject_whenEmptyString_thenReturnLengthIsZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, CookieList.toJSONObject("").length());
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Given {@code =}.
   *   <li>Then return {@code name=;value=;%3d=[null]}.
   * </ul>
   *
   * <p>Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CookieList.toString(JSONObject)"})
  public void testToStringWithJSONObject_givenEqualsSign_thenReturnNameValue3dNull()
      throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("=", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=;%3d=[null]", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then return {@code name=;,={};value=;%3d=[null]}.
   * </ul>
   *
   * <p>Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CookieList.toString(JSONObject)"})
  public void testToStringWithJSONObject_givenHashMap_thenReturnNameValue3dNull()
      throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put(",", (Map) new HashMap<>());
    o.append("=", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;,={};value=;%3d=[null]", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code name=;value=}.
   * </ul>
   *
   * <p>Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CookieList.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnNameValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("name=;value=", CookieList.toString(Cookie.toJSONObject("=;")));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code name=;value=;%3d=[null]}.
   * </ul>
   *
   * <p>Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CookieList.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnNameValue3dNull() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put(",", JSONObject.NULL);
    o.append("=", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=;%3d=[null]", CookieList.toString(o));
  }

  /**
   * Test {@link CookieList#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code name=;value=;%3d=[null,null]}.
   * </ul>
   *
   * <p>Method under test: {@link CookieList#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String CookieList.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnNameValue3dNullNull() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.append("=", JSONObject.NULL);
    o.put(",", JSONObject.NULL);
    o.append("=", JSONObject.NULL);

    // Act and Assert
    assertEquals("name=;value=;%3d=[null,null]", CookieList.toString(o));
  }
}
