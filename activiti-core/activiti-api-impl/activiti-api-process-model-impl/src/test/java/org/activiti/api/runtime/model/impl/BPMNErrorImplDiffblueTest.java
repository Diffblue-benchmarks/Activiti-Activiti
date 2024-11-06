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

class BPMNErrorImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNErrorImpl#BPMNErrorImpl()}
   *   <li>{@link BPMNErrorImpl#setErrorCode(String)}
   *   <li>{@link BPMNErrorImpl#setErrorId(String)}
   *   <li>{@link BPMNErrorImpl#toString()}
   *   <li>{@link BPMNErrorImpl#getErrorCode()}
   *   <li>{@link BPMNErrorImpl#getErrorId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    BPMNErrorImpl actualBpmnErrorImpl = new BPMNErrorImpl();
    actualBpmnErrorImpl.setErrorCode("An error occurred");
    actualBpmnErrorImpl.setErrorId("An error occurred");
    String actualToStringResult = actualBpmnErrorImpl.toString();
    String actualErrorCode = actualBpmnErrorImpl.getErrorCode();

    // Assert that nothing has changed
    assertEquals("An error occurred", actualErrorCode);
    assertEquals("An error occurred", actualBpmnErrorImpl.getErrorId());
    assertEquals(
        "BPMNActivityImpl{activityName='null', activityType='null', elementId='null', errorId='An error occurred',"
            + " errorCode='An error occurred'}",
        actualToStringResult);
  }

  /**
   * Test {@link BPMNErrorImpl#BPMNErrorImpl(String)}.
   * <p>
   * Method under test: {@link BPMNErrorImpl#BPMNErrorImpl(String)}
   */
  @Test
  @DisplayName("Test new BPMNErrorImpl(String)")
  void testNewBPMNErrorImpl() {
    // Arrange and Act
    BPMNErrorImpl actualBpmnErrorImpl = new BPMNErrorImpl("42");

    // Assert
    assertEquals("42", actualBpmnErrorImpl.getElementId());
    assertNull(actualBpmnErrorImpl.getActivityName());
    assertNull(actualBpmnErrorImpl.getActivityType());
    assertNull(actualBpmnErrorImpl.getExecutionId());
    assertNull(actualBpmnErrorImpl.getProcessDefinitionId());
    assertNull(actualBpmnErrorImpl.getProcessInstanceId());
    assertNull(actualBpmnErrorImpl.getErrorCode());
    assertNull(actualBpmnErrorImpl.getErrorId());
  }

  /**
   * Test {@link BPMNErrorImpl#BPMNErrorImpl(String, String, String)}.
   * <p>
   * Method under test:
   * {@link BPMNErrorImpl#BPMNErrorImpl(String, String, String)}
   */
  @Test
  @DisplayName("Test new BPMNErrorImpl(String, String, String)")
  void testNewBPMNErrorImpl2() {
    // Arrange and Act
    BPMNErrorImpl actualBpmnErrorImpl = new BPMNErrorImpl("42", "Activity Name", "Activity Type");

    // Assert
    assertEquals("42", actualBpmnErrorImpl.getElementId());
    assertEquals("Activity Name", actualBpmnErrorImpl.getActivityName());
    assertEquals("Activity Type", actualBpmnErrorImpl.getActivityType());
    assertNull(actualBpmnErrorImpl.getExecutionId());
    assertNull(actualBpmnErrorImpl.getProcessDefinitionId());
    assertNull(actualBpmnErrorImpl.getProcessInstanceId());
    assertNull(actualBpmnErrorImpl.getErrorCode());
    assertNull(actualBpmnErrorImpl.getErrorId());
  }

  /**
   * Test {@link BPMNErrorImpl#equals(Object)}, and
   * {@link BPMNErrorImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNErrorImpl#equals(Object)}
   *   <li>{@link BPMNErrorImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    BPMNErrorImpl bpmnErrorImpl = new BPMNErrorImpl("42");
    BPMNErrorImpl bpmnErrorImpl2 = new BPMNErrorImpl("42");

    // Act and Assert
    assertEquals(bpmnErrorImpl, bpmnErrorImpl2);
    int expectedHashCodeResult = bpmnErrorImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnErrorImpl2.hashCode());
  }

  /**
   * Test {@link BPMNErrorImpl#equals(Object)}, and
   * {@link BPMNErrorImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNErrorImpl#equals(Object)}
   *   <li>{@link BPMNErrorImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BPMNErrorImpl bpmnErrorImpl = new BPMNErrorImpl("42");

    // Act and Assert
    assertEquals(bpmnErrorImpl, bpmnErrorImpl);
    int expectedHashCodeResult = bpmnErrorImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnErrorImpl.hashCode());
  }

  /**
   * Test {@link BPMNErrorImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNErrorImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BPMNErrorImpl bpmnErrorImpl = new BPMNErrorImpl("Element Id");

    // Act and Assert
    assertNotEquals(bpmnErrorImpl, new BPMNErrorImpl("42"));
  }

  /**
   * Test {@link BPMNErrorImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNErrorImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    BPMNErrorImpl bpmnErrorImpl = new BPMNErrorImpl("42", "Activity Name", "Activity Type");

    // Act and Assert
    assertNotEquals(bpmnErrorImpl, new BPMNErrorImpl("42"));
  }

  /**
   * Test {@link BPMNErrorImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNErrorImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    BPMNErrorImpl bpmnErrorImpl = new BPMNErrorImpl("42");
    bpmnErrorImpl.setErrorCode("An error occurred");

    // Act and Assert
    assertNotEquals(bpmnErrorImpl, new BPMNErrorImpl("42"));
  }

  /**
   * Test {@link BPMNErrorImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNErrorImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    BPMNErrorImpl bpmnErrorImpl = new BPMNErrorImpl("42");
    bpmnErrorImpl.setErrorId("An error occurred");

    // Act and Assert
    assertNotEquals(bpmnErrorImpl, new BPMNErrorImpl("42"));
  }

  /**
   * Test {@link BPMNErrorImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNErrorImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    BPMNErrorImpl bpmnErrorImpl = new BPMNErrorImpl("42");
    bpmnErrorImpl.setActivityType("Activity Type");

    // Act and Assert
    assertNotEquals(bpmnErrorImpl, new BPMNErrorImpl("42"));
  }

  /**
   * Test {@link BPMNErrorImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNErrorImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNErrorImpl("42"), null);
  }

  /**
   * Test {@link BPMNErrorImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNErrorImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNErrorImpl("42"), "Different type to BPMNErrorImpl");
  }
}
