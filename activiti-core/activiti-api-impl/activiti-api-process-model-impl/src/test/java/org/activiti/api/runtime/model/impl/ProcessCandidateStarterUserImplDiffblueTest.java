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

class ProcessCandidateStarterUserImplDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return ProcessDefinitionId is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessCandidateStarterUserImpl#ProcessCandidateStarterUserImpl()}
   *   <li>{@link ProcessCandidateStarterUserImpl#getUserId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; then return ProcessDefinitionId is 'null'")
  void testGettersAndSetters_thenReturnProcessDefinitionIdIsNull() {
    // Arrange and Act
    ProcessCandidateStarterUserImpl actualProcessCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl();
    String actualUserId = actualProcessCandidateStarterUserImpl.getUserId();

    // Assert
    assertNull(actualProcessCandidateStarterUserImpl.getProcessDefinitionId());
    assertNull(actualUserId);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessCandidateStarterUserImpl#ProcessCandidateStarterUserImpl(String, String)}
   *   <li>{@link ProcessCandidateStarterUserImpl#getUserId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when '42'; then return ProcessDefinitionId is '42'")
  void testGettersAndSetters_when42_thenReturnProcessDefinitionIdIs42() {
    // Arrange and Act
    ProcessCandidateStarterUserImpl actualProcessCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl("42",
        "42");
    String actualUserId = actualProcessCandidateStarterUserImpl.getUserId();

    // Assert
    assertEquals("42", actualProcessCandidateStarterUserImpl.getProcessDefinitionId());
    assertEquals("42", actualUserId);
  }

  /**
   * Test {@link ProcessCandidateStarterUserImpl#equals(Object)}, and
   * {@link ProcessCandidateStarterUserImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessCandidateStarterUserImpl#equals(Object)}
   *   <li>{@link ProcessCandidateStarterUserImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProcessCandidateStarterUserImpl processCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl("42", "42");
    ProcessCandidateStarterUserImpl processCandidateStarterUserImpl2 = new ProcessCandidateStarterUserImpl("42", "42");

    // Act and Assert
    assertEquals(processCandidateStarterUserImpl, processCandidateStarterUserImpl2);
    int expectedHashCodeResult = processCandidateStarterUserImpl.hashCode();
    assertEquals(expectedHashCodeResult, processCandidateStarterUserImpl2.hashCode());
  }

  /**
   * Test {@link ProcessCandidateStarterUserImpl#equals(Object)}, and
   * {@link ProcessCandidateStarterUserImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessCandidateStarterUserImpl#equals(Object)}
   *   <li>{@link ProcessCandidateStarterUserImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProcessCandidateStarterUserImpl processCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl("42", "42");

    // Act and Assert
    assertEquals(processCandidateStarterUserImpl, processCandidateStarterUserImpl);
    int expectedHashCodeResult = processCandidateStarterUserImpl.hashCode();
    assertEquals(expectedHashCodeResult, processCandidateStarterUserImpl.hashCode());
  }

  /**
   * Test {@link ProcessCandidateStarterUserImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCandidateStarterUserImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProcessCandidateStarterUserImpl processCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl(
        "Process Definition Id", "42");

    // Act and Assert
    assertNotEquals(processCandidateStarterUserImpl, new ProcessCandidateStarterUserImpl("42", "42"));
  }

  /**
   * Test {@link ProcessCandidateStarterUserImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCandidateStarterUserImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ProcessCandidateStarterUserImpl processCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl("42",
        "User Id");

    // Act and Assert
    assertNotEquals(processCandidateStarterUserImpl, new ProcessCandidateStarterUserImpl("42", "42"));
  }

  /**
   * Test {@link ProcessCandidateStarterUserImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCandidateStarterUserImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProcessCandidateStarterUserImpl("42", "42"), null);
  }

  /**
   * Test {@link ProcessCandidateStarterUserImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessCandidateStarterUserImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProcessCandidateStarterUserImpl("42", "42"),
        "Different type to ProcessCandidateStarterUserImpl");
  }
}
