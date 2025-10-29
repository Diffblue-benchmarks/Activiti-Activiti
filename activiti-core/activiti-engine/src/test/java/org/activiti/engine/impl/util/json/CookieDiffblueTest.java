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
   * Method under test: {@link Cookie#escape(String)}
   */
  @Test
  public void testEscape() {
    // Arrange, Act and Assert
    assertEquals("String", Cookie.escape("String"));
    assertEquals("%3d%3b", Cookie.escape("=;"));
  }

  /**
   * Method under test: {@link Cookie#toJSONObject(String)}
   */
  @Test
  public void testToJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(2, Cookie.toJSONObject("=;").length());
    assertEquals(3, Cookie.toJSONObject("=;=;").length());
    assertEquals(3, Cookie.toJSONObject("=;secure").length());
  }

  /**
   * Method under test: {@link Cookie#toString(JSONObject)}
   */
  @Test
  public void testToString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("=", Cookie.toString(Cookie.toJSONObject("=;")));
  }

  /**
   * Method under test: {@link Cookie#unescape(String)}
   */
  @Test
  public void testUnescape() {
    // Arrange, Act and Assert
    assertEquals("foo", Cookie.unescape("foo"));
  }
}
