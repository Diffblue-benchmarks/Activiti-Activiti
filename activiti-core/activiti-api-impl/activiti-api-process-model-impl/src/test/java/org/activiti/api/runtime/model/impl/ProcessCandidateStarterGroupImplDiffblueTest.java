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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessCandidateStarterGroupImplDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return GroupId is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessCandidateStarterGroupImpl#ProcessCandidateStarterGroupImpl()}
   *   <li>{@link ProcessCandidateStarterGroupImpl#getGroupId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; then return GroupId is 'null'")
  void testGettersAndSetters_thenReturnGroupIdIsNull() {
    // Arrange and Act
    ProcessCandidateStarterGroupImpl actualProcessCandidateStarterGroupImpl = new ProcessCandidateStarterGroupImpl();

    // Assert
    assertNull(actualProcessCandidateStarterGroupImpl.getGroupId());
    assertNull(actualProcessCandidateStarterGroupImpl.getProcessDefinitionId());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return GroupId is {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessCandidateStarterGroupImpl#ProcessCandidateStarterGroupImpl(String, String)}
   *   <li>{@link ProcessCandidateStarterGroupImpl#getGroupId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when '42'; then return GroupId is '42'")
  void testGettersAndSetters_when42_thenReturnGroupIdIs42() {
    // Arrange and Act
    ProcessCandidateStarterGroupImpl actualProcessCandidateStarterGroupImpl = new ProcessCandidateStarterGroupImpl("42",
        "42");

    // Assert
    assertEquals("42", actualProcessCandidateStarterGroupImpl.getGroupId());
    assertEquals("42", actualProcessCandidateStarterGroupImpl.getProcessDefinitionId());
  }

  /**
   * Test {@link ProcessCandidateStarterGroupImpl#equals(Object)}, and
   * {@link ProcessCandidateStarterGroupImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessCandidateStarterGroupImpl#equals(Object)}
   *   <li>{@link ProcessCandidateStarterGroupImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProcessCandidateStarterGroupImpl processCandidateStarterGroupImpl = new ProcessCandidateStarterGroupImpl("42",
        "42");
    ProcessCandidateStarterGroupImpl processCandidateStarterGroupImpl2 = new ProcessCandidateStarterGroupImpl("42",
        "42");

    // Act and Assert
    assertEquals(processCandidateStarterGroupImpl, processCandidateStarterGroupImpl2);
    int expectedHashCodeResult = processCandidateStarterGroupImpl.hashCode();
    assertEquals(expectedHashCodeResult, processCandidateStarterGroupImpl2.hashCode());
  }

  /**
   * Test {@link ProcessCandidateStarterGroupImpl#equals(Object)}, and
   * {@link ProcessCandidateStarterGroupImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessCandidateStarterGroupImpl#equals(Object)}
   *   <li>{@link ProcessCandidateStarterGroupImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProcessCandidateStarterGroupImpl processCandidateStarterGroupImpl = new ProcessCandidateStarterGroupImpl("42",
        "42");

    // Act and Assert
    assertEquals(processCandidateStarterGroupImpl, processCandidateStarterGroupImpl);
    int expectedHashCodeResult = processCandidateStarterGroupImpl.hashCode();
    assertEquals(expectedHashCodeResult, processCandidateStarterGroupImpl.hashCode());
  }

  /**
   * Test {@link ProcessCandidateStarterGroupImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCandidateStarterGroupImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProcessCandidateStarterGroupImpl processCandidateStarterGroupImpl = new ProcessCandidateStarterGroupImpl(
        "Process Definition Id", "42");

    // Act and Assert
    assertNotEquals(processCandidateStarterGroupImpl, new ProcessCandidateStarterGroupImpl("42", "42"));
  }

  /**
   * Test {@link ProcessCandidateStarterGroupImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCandidateStarterGroupImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ProcessCandidateStarterGroupImpl processCandidateStarterGroupImpl = new ProcessCandidateStarterGroupImpl("42",
        "Group Id");

    // Act and Assert
    assertNotEquals(processCandidateStarterGroupImpl, new ProcessCandidateStarterGroupImpl("42", "42"));
  }

  /**
   * Test {@link ProcessCandidateStarterGroupImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCandidateStarterGroupImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProcessCandidateStarterGroupImpl("42", "42"), null);
  }

  /**
   * Test {@link ProcessCandidateStarterGroupImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCandidateStarterGroupImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProcessCandidateStarterGroupImpl("42", "42"),
        "Different type to ProcessCandidateStarterGroupImpl");
  }
}