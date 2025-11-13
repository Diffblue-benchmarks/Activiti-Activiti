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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HTTPTokenerDiffblueTest {
  /**
   * Test {@link HTTPTokener#HTTPTokener(String)}.
   *
   * <p>Method under test: {@link HTTPTokener#HTTPTokener(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HTTPTokener.<init>(String)"})
  public void testNewHTTPTokener() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "https://example.org/example", new HTTPTokener("https://example.org/example").nextToken());
  }

  /**
   * Test {@link HTTPTokener#nextToken()}.
   *
   * <p>Method under test: {@link HTTPTokener#nextToken()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HTTPTokener.nextToken()"})
  public void testNextToken() throws JSONException {
    // Arrange
    HTTPTokener httpTokener = new HTTPTokener("https://example.org/example");

    // Act and Assert
    assertEquals("https://example.org/example", httpTokener.nextToken());
    assertTrue(httpTokener.end());
  }
}
