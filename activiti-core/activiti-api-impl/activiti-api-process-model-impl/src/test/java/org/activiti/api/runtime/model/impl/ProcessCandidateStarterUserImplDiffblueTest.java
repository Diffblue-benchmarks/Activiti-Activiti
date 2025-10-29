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
import org.junit.jupiter.api.Test;

class ProcessCandidateStarterUserImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessCandidateStarterUserImpl#equals(Object)}
   *   <li>{@link ProcessCandidateStarterUserImpl#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessCandidateStarterUserImpl#equals(Object)}
   *   <li>{@link ProcessCandidateStarterUserImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProcessCandidateStarterUserImpl processCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl("42", "42");

    // Act and Assert
    assertEquals(processCandidateStarterUserImpl, processCandidateStarterUserImpl);
    int expectedHashCodeResult = processCandidateStarterUserImpl.hashCode();
    assertEquals(expectedHashCodeResult, processCandidateStarterUserImpl.hashCode());
  }

  /**
   * Method under test: {@link ProcessCandidateStarterUserImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProcessCandidateStarterUserImpl processCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl(
        "Process Definition Id", "42");

    // Act and Assert
    assertNotEquals(processCandidateStarterUserImpl, new ProcessCandidateStarterUserImpl("42", "42"));
  }

  /**
   * Method under test: {@link ProcessCandidateStarterUserImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ProcessCandidateStarterUserImpl processCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl("42",
        "User Id");

    // Act and Assert
    assertNotEquals(processCandidateStarterUserImpl, new ProcessCandidateStarterUserImpl("42", "42"));
  }

  /**
   * Method under test: {@link ProcessCandidateStarterUserImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProcessCandidateStarterUserImpl("42", "42"), null);
  }

  /**
   * Method under test: {@link ProcessCandidateStarterUserImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProcessCandidateStarterUserImpl("42", "42"),
        "Different type to ProcessCandidateStarterUserImpl");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessCandidateStarterUserImpl#ProcessCandidateStarterUserImpl()}
   *   <li>{@link ProcessCandidateStarterUserImpl#getUserId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ProcessCandidateStarterUserImpl actualProcessCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl();
    String actualUserId = actualProcessCandidateStarterUserImpl.getUserId();

    // Assert
    assertNull(actualProcessCandidateStarterUserImpl.getProcessDefinitionId());
    assertNull(actualUserId);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessCandidateStarterUserImpl#ProcessCandidateStarterUserImpl(String, String)}
   *   <li>{@link ProcessCandidateStarterUserImpl#getUserId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    ProcessCandidateStarterUserImpl actualProcessCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl("42",
        "42");
    String actualUserId = actualProcessCandidateStarterUserImpl.getUserId();

    // Assert
    assertEquals("42", actualProcessCandidateStarterUserImpl.getProcessDefinitionId());
    assertEquals("42", actualUserId);
  }
}
