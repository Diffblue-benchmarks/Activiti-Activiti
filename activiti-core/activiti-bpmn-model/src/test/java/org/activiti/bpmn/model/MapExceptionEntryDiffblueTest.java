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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MapExceptionEntryDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MapExceptionEntry#MapExceptionEntry(String, String, boolean)}
   *   <li>{@link MapExceptionEntry#setAndChildren(boolean)}
   *   <li>{@link MapExceptionEntry#setClassName(String)}
   *   <li>{@link MapExceptionEntry#setErrorCode(String)}
   *   <li>{@link MapExceptionEntry#getClassName()}
   *   <li>{@link MapExceptionEntry#getErrorCode()}
   *   <li>{@link MapExceptionEntry#isAndChildren()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    MapExceptionEntry actualMapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);
    actualMapExceptionEntry.setAndChildren(true);
    actualMapExceptionEntry.setClassName("Class Name");
    actualMapExceptionEntry.setErrorCode("An error occurred");
    String actualClassName = actualMapExceptionEntry.getClassName();
    String actualErrorCode = actualMapExceptionEntry.getErrorCode();

    // Assert that nothing has changed
    assertEquals("An error occurred", actualErrorCode);
    assertEquals("Class Name", actualClassName);
    assertTrue(actualMapExceptionEntry.isAndChildren());
  }
}
