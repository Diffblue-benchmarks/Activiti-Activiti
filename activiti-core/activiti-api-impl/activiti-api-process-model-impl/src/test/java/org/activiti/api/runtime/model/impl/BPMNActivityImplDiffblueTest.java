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
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BPMNActivityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNActivityImpl#BPMNActivityImpl()}
   *   <li>{@link BPMNActivityImpl#setActivityName(String)}
   *   <li>{@link BPMNActivityImpl#setActivityType(String)}
   *   <li>{@link BPMNActivityImpl#setExecutionId(String)}
   *   <li>{@link BPMNActivityImpl#toString()}
   *   <li>{@link BPMNActivityImpl#getActivityName()}
   *   <li>{@link BPMNActivityImpl#getActivityType()}
   *   <li>{@link BPMNActivityImpl#getExecutionId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    BPMNActivityImpl actualBpmnActivityImpl = new BPMNActivityImpl();
    actualBpmnActivityImpl.setActivityName("Activity Name");
    actualBpmnActivityImpl.setActivityType("Activity Type");
    actualBpmnActivityImpl.setExecutionId("42");
    actualBpmnActivityImpl.toString();
    String actualActivityName = actualBpmnActivityImpl.getActivityName();
    String actualActivityType = actualBpmnActivityImpl.getActivityType();

    // Assert that nothing has changed
    assertEquals("42", actualBpmnActivityImpl.getExecutionId());
    assertEquals("Activity Name", actualActivityName);
    assertEquals("Activity Type", actualActivityType);
  }

  /**
   * Test {@link BPMNActivityImpl#BPMNActivityImpl(String, String, String)}.
   * <p>
   * Method under test:
   * {@link BPMNActivityImpl#BPMNActivityImpl(String, String, String)}
   */
  @Test
  @DisplayName("Test new BPMNActivityImpl(String, String, String)")
  void testNewBPMNActivityImpl() {
    // Arrange and Act
    BPMNActivityImpl actualBpmnActivityImpl = new BPMNActivityImpl("42", "Activity Name", "Activity Type");

    // Assert
    assertEquals("42", actualBpmnActivityImpl.getElementId());
    assertEquals("Activity Name", actualBpmnActivityImpl.getActivityName());
    assertEquals("Activity Type", actualBpmnActivityImpl.getActivityType());
    assertNull(actualBpmnActivityImpl.getExecutionId());
    assertNull(actualBpmnActivityImpl.getProcessDefinitionId());
    assertNull(actualBpmnActivityImpl.getProcessInstanceId());
  }

  /**
   * Test {@link BPMNActivityImpl#equals(Object)}, and
   * {@link BPMNActivityImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNActivityImpl#equals(Object)}
   *   <li>{@link BPMNActivityImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    BPMNActivityImpl bpmnActivityImpl = new BPMNActivityImpl("42", "Activity Name", "Activity Type");
    BPMNActivityImpl bpmnActivityImpl2 = new BPMNActivityImpl("42", "Activity Name", "Activity Type");

    // Act and Assert
    assertEquals(bpmnActivityImpl, bpmnActivityImpl2);
    int expectedHashCodeResult = bpmnActivityImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnActivityImpl2.hashCode());
  }

  /**
   * Test {@link BPMNActivityImpl#equals(Object)}, and
   * {@link BPMNActivityImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNActivityImpl#equals(Object)}
   *   <li>{@link BPMNActivityImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BPMNActivityImpl bpmnActivityImpl = new BPMNActivityImpl("42", "Activity Name", "Activity Type");

    // Act and Assert
    assertEquals(bpmnActivityImpl, bpmnActivityImpl);
    int expectedHashCodeResult = bpmnActivityImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnActivityImpl.hashCode());
  }

  /**
   * Test {@link BPMNActivityImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNActivityImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BPMNActivityImpl bpmnActivityImpl = new BPMNActivityImpl("Element Id", "Activity Name", "Activity Type");

    // Act and Assert
    assertNotEquals(bpmnActivityImpl, new BPMNActivityImpl("42", "Activity Name", "Activity Type"));
  }

  /**
   * Test {@link BPMNActivityImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNActivityImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    BPMNActivityImpl bpmnActivityImpl = new BPMNActivityImpl("42",
        "org.activiti.api.runtime.model.impl.BPMNActivityImpl", "Activity Type");

    // Act and Assert
    assertNotEquals(bpmnActivityImpl, new BPMNActivityImpl("42", "Activity Name", "Activity Type"));
  }

  /**
   * Test {@link BPMNActivityImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNActivityImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    BPMNActivityImpl bpmnActivityImpl = new BPMNActivityImpl("42", "Activity Name",
        "org.activiti.api.runtime.model.impl.BPMNActivityImpl");

    // Act and Assert
    assertNotEquals(bpmnActivityImpl, new BPMNActivityImpl("42", "Activity Name", "Activity Type"));
  }

  /**
   * Test {@link BPMNActivityImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNActivityImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNActivityImpl("42", "Activity Name", "Activity Type"), mock(BPMNElementImpl.class));
  }

  /**
   * Test {@link BPMNActivityImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNActivityImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    BPMNActivityImpl bpmnActivityImpl = new BPMNActivityImpl("42", "Activity Name", "Activity Type");
    bpmnActivityImpl.setExecutionId("42");

    // Act and Assert
    assertNotEquals(bpmnActivityImpl, new BPMNActivityImpl("42", "Activity Name", "Activity Type"));
  }

  /**
   * Test {@link BPMNActivityImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNActivityImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNActivityImpl("42", "Activity Name", "Activity Type"), null);
  }

  /**
   * Test {@link BPMNActivityImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNActivityImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNActivityImpl("42", "Activity Name", "Activity Type"), "Different type to BPMNActivityImpl");
  }
}
