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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class HTTPTokenerDiffblueTest {
  /**
   * Test {@link HTTPTokener#HTTPTokener(String)}.
   * <p>
   * Method under test: {@link HTTPTokener#HTTPTokener(String)}
   */
  @Test
  public void testNewHTTPTokener() throws JSONException {
    // Arrange and Act
    HTTPTokener actualHttpTokener = new HTTPTokener("https://example.org/example");

    // Assert
    boolean actualEndResult = actualHttpTokener.end();
    assertEquals("https://example.org/example", actualHttpTokener.nextToken());
    assertFalse(actualEndResult);
  }

  /**
   * Test {@link HTTPTokener#nextToken()}.
   * <p>
   * Method under test: {@link HTTPTokener#nextToken()}
   */
  @Test
  public void testNextToken() throws JSONException {
    // Arrange
    HTTPTokener httpTokener = new HTTPTokener("https://example.org/example");

    // Act and Assert
    assertEquals("https://example.org/example", httpTokener.nextToken());
    assertTrue(httpTokener.end());
  }
}
