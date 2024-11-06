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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class JSONExceptionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link JSONException#JSONException(String)}
   *   <li>{@link JSONException#getCause()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    JSONException actualJsonException = new JSONException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualJsonException.getMessage());
    assertNull(actualJsonException.getCause());
    assertEquals(0, actualJsonException.getSuppressed().length);
  }

  /**
   * Test {@link JSONException#JSONException(Throwable)}.
   * <p>
   * Method under test: {@link JSONException#JSONException(Throwable)}
   */
  @Test
  public void testNewJSONException() {
    // Arrange
    Throwable t = new Throwable();

    // Act
    JSONException actualJsonException = new JSONException(t);

    // Assert
    assertNull(actualJsonException.getLocalizedMessage());
    assertNull(actualJsonException.getMessage());
    assertEquals(0, actualJsonException.getSuppressed().length);
    assertSame(t, actualJsonException.getCause());
  }
}
