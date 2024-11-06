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
package org.activiti.core.el.juel.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TreeBuilderExceptionDiffblueTest {
  /**
   * Test
   * {@link TreeBuilderException#TreeBuilderException(String, int, String, String, String)}.
   * <p>
   * Method under test:
   * {@link TreeBuilderException#TreeBuilderException(String, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new TreeBuilderException(String, int, String, String, String)")
  void testNewTreeBuilderException() {
    // Arrange and Act
    TreeBuilderException actualTreeBuilderException = new TreeBuilderException("Expression", 1, "3", "Expected",
        "An error occurred");

    // Assert
    assertEquals("3", actualTreeBuilderException.getEncountered());
    assertEquals("Error parsing 'Expression': An error occurred", actualTreeBuilderException.getLocalizedMessage());
    assertEquals("Error parsing 'Expression': An error occurred", actualTreeBuilderException.getMessage());
    assertEquals("Expected", actualTreeBuilderException.getExpected());
    assertEquals("Expression", actualTreeBuilderException.getExpression());
    assertNull(actualTreeBuilderException.getCause());
    assertEquals(0, actualTreeBuilderException.getSuppressed().length);
    assertEquals(1, actualTreeBuilderException.getPosition());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TreeBuilderException#getEncountered()}
   *   <li>{@link TreeBuilderException#getExpected()}
   *   <li>{@link TreeBuilderException#getExpression()}
   *   <li>{@link TreeBuilderException#getPosition()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    TreeBuilderException treeBuilderException = new TreeBuilderException("Expression", 1, "3", "Expected",
        "An error occurred");

    // Act
    String actualEncountered = treeBuilderException.getEncountered();
    String actualExpected = treeBuilderException.getExpected();
    String actualExpression = treeBuilderException.getExpression();

    // Assert
    assertEquals("3", actualEncountered);
    assertEquals("Expected", actualExpected);
    assertEquals("Expression", actualExpression);
    assertEquals(1, treeBuilderException.getPosition());
  }
}
