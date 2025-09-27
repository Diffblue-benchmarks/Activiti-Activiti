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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CookieDiffblueTest {
  /**
   * Test {@link Cookie#escape(String)}.
   *
   * <ul>
   *   <li>When {@code =;}.
   *   <li>Then return {@code %3d%3b}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#escape(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.escape(String)"})
  public void testEscape_whenEqualsSignSemicolon_thenReturn3d3b() {
    // Arrange, Act and Assert
    assertEquals("%3d%3b", Cookie.escape("=;"));
  }

  /**
   * Test {@link Cookie#escape(String)}.
   *
   * <ul>
   *   <li>When {@code String}.
   *   <li>Then return {@code String}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#escape(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.escape(String)"})
  public void testEscape_whenString_thenReturnString() {
    // Arrange, Act and Assert
    assertEquals("String", Cookie.escape("String"));
  }

  /**
   * Test {@link Cookie#toJSONObject(String)}.
   *
   * <ul>
   *   <li>When {@code =;=;}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject Cookie.toJSONObject(String)"})
  public void testToJSONObject_whenEqualsSignSemicolonEqualsSignSemicolon() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(3, Cookie.toJSONObject("=;=;").length());
  }

  /**
   * Test {@link Cookie#toJSONObject(String)}.
   *
   * <ul>
   *   <li>When {@code =;}.
   *   <li>Then return length is two.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject Cookie.toJSONObject(String)"})
  public void testToJSONObject_whenEqualsSignSemicolon_thenReturnLengthIsTwo()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(2, Cookie.toJSONObject("=;").length());
  }

  /**
   * Test {@link Cookie#toJSONObject(String)}.
   *
   * <ul>
   *   <li>When {@code name=;}.
   *   <li>Then return length is two.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject Cookie.toJSONObject(String)"})
  public void testToJSONObject_whenName_thenReturnLengthIsTwo() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(2, Cookie.toJSONObject("name=;").length());
  }

  /**
   * Test {@link Cookie#toJSONObject(String)}.
   *
   * <ul>
   *   <li>When {@code =;secure}.
   *   <li>Then return length is three.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject Cookie.toJSONObject(String)"})
  public void testToJSONObject_whenSecure_thenReturnLengthIsThree() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(3, Cookie.toJSONObject("=;secure").length());
  }

  /**
   * Test {@link Cookie#toString(JSONObject)} with {@code JSONObject}.
   *
   * <p>Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.toString(JSONObject)"})
  public void testToStringWithJSONObject() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("name", (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]=", Cookie.toString(o));
  }

  /**
   * Test {@link Cookie#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code =;domain=true}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnDomainTrue() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("domain", true);

    // Act and Assert
    assertEquals("=;domain=true", Cookie.toString(o));
  }

  /**
   * Test {@link Cookie#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code =;expires=true}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnExpiresTrue() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("expires", true);

    // Act and Assert
    assertEquals("=;expires=true", Cookie.toString(o));
  }

  /**
   * Test {@link Cookie#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code {}=}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnLeftCurlyBracketRightCurlyBracketEqualsSign()
      throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("name", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}=", Cookie.toString(o));
  }

  /**
   * Test {@link Cookie#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code =;path=true}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnPathTrue() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("path", true);

    // Act and Assert
    assertEquals("=;path=true", Cookie.toString(o));
  }

  /**
   * Test {@link Cookie#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code =;secure}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnSecure() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("secure", true);

    // Act and Assert
    assertEquals("=;secure", Cookie.toString(o));
  }

  /**
   * Test {@link Cookie#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>Then return {@code true=}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.toString(JSONObject)"})
  public void testToStringWithJSONObject_thenReturnTrue() throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("name", true);

    // Act and Assert
    assertEquals("true=", Cookie.toString(o));
  }

  /**
   * Test {@link Cookie#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>When toJSONObject {@code =;}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.toString(JSONObject)"})
  public void testToStringWithJSONObject_whenToJSONObjectEqualsSignSemicolon()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("=", Cookie.toString(Cookie.toJSONObject("=;")));
  }

  /**
   * Test {@link Cookie#toString(JSONObject)} with {@code JSONObject}.
   *
   * <ul>
   *   <li>When toJSONObject {@code =;} {@code secure} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.toString(JSONObject)"})
  public void testToStringWithJSONObject_whenToJSONObjectEqualsSignSemicolonSecureIsArrayList()
      throws JSONException {
    // Arrange
    JSONObject o = Cookie.toJSONObject("=;");
    o.put("secure", (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("=", Cookie.toString(o));
  }

  /**
   * Test {@link Cookie#unescape(String)}.
   *
   * <p>Method under test: {@link Cookie#unescape(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Cookie.unescape(String)"})
  public void testUnescape() {
    // Arrange, Act and Assert
    assertEquals("foo", Cookie.unescape("foo"));
  }
}
