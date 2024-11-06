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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CookieDiffblueTest {
  @InjectMocks
  private Cookie cookie;

  /**
   * Test {@link Cookie#escape(String)}.
   * <ul>
   *   <li>When {@code =;}.</li>
   *   <li>Then return {@code %3d%3b}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cookie#escape(String)}
   */
  @Test
  public void testEscape_whenEqualsSignSemicolon_thenReturn3d3b() {
    // Arrange, Act and Assert
    assertEquals("%3d%3b", Cookie.escape("=;"));
  }

  /**
   * Test {@link Cookie#escape(String)}.
   * <ul>
   *   <li>When {@code String}.</li>
   *   <li>Then return {@code String}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cookie#escape(String)}
   */
  @Test
  public void testEscape_whenString_thenReturnString() {
    // Arrange, Act and Assert
    assertEquals("String", Cookie.escape("String"));
  }

  /**
   * Test {@link Cookie#toJSONObject(String)}.
   * <ul>
   *   <li>When {@code =;=;}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cookie#toJSONObject(String)}
   */
  @Test
  public void testToJSONObject_whenEqualsSignSemicolonEqualsSignSemicolon() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(3, Cookie.toJSONObject("=;=;").length());
  }

  /**
   * Test {@link Cookie#toJSONObject(String)}.
   * <ul>
   *   <li>When {@code =;}.</li>
   *   <li>Then return length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cookie#toJSONObject(String)}
   */
  @Test
  public void testToJSONObject_whenEqualsSignSemicolon_thenReturnLengthIsTwo() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(2, Cookie.toJSONObject("=;").length());
  }

  /**
   * Test {@link Cookie#toJSONObject(String)}.
   * <ul>
   *   <li>When {@code =;secure}.</li>
   *   <li>Then return length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cookie#toJSONObject(String)}
   */
  @Test
  public void testToJSONObject_whenSecure_thenReturnLengthIsThree() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(3, Cookie.toJSONObject("=;secure").length());
  }

  /**
   * Test {@link Cookie#toString(JSONObject)} with {@code JSONObject}.
   * <ul>
   *   <li>Then return {@code =}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJSONObject_thenReturnEqualsSign() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("=", Cookie.toString(Cookie.toJSONObject("=;")));
  }

  /**
   * Test {@link Cookie#unescape(String)}.
   * <p>
   * Method under test: {@link Cookie#unescape(String)}
   */
  @Test
  public void testUnescape() {
    // Arrange, Act and Assert
    assertEquals("foo", Cookie.unescape("foo"));
  }
}
