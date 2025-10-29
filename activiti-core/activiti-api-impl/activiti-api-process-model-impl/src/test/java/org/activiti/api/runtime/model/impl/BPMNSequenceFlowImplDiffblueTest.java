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

class BPMNSequenceFlowImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNSequenceFlowImpl#equals(Object)}
   *   <li>{@link BPMNSequenceFlowImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    BPMNSequenceFlowImpl bpmnSequenceFlowImpl = new BPMNSequenceFlowImpl("42", "42", "42");
    BPMNSequenceFlowImpl bpmnSequenceFlowImpl2 = new BPMNSequenceFlowImpl("42", "42", "42");

    // Act and Assert
    assertEquals(bpmnSequenceFlowImpl, bpmnSequenceFlowImpl2);
    int expectedHashCodeResult = bpmnSequenceFlowImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnSequenceFlowImpl2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNSequenceFlowImpl#equals(Object)}
   *   <li>{@link BPMNSequenceFlowImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BPMNSequenceFlowImpl bpmnSequenceFlowImpl = new BPMNSequenceFlowImpl("42", "42", "42");

    // Act and Assert
    assertEquals(bpmnSequenceFlowImpl, bpmnSequenceFlowImpl);
    int expectedHashCodeResult = bpmnSequenceFlowImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnSequenceFlowImpl.hashCode());
  }

  /**
   * Method under test: {@link BPMNSequenceFlowImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BPMNSequenceFlowImpl bpmnSequenceFlowImpl = new BPMNSequenceFlowImpl("Element Id", "42", "42");

    // Act and Assert
    assertNotEquals(bpmnSequenceFlowImpl, new BPMNSequenceFlowImpl("42", "42", "42"));
  }

  /**
   * Method under test: {@link BPMNSequenceFlowImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    BPMNSequenceFlowImpl bpmnSequenceFlowImpl = new BPMNSequenceFlowImpl("42", "Source Activity Element Id", "42");

    // Act and Assert
    assertNotEquals(bpmnSequenceFlowImpl, new BPMNSequenceFlowImpl("42", "42", "42"));
  }

  /**
   * Method under test: {@link BPMNSequenceFlowImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    BPMNSequenceFlowImpl bpmnSequenceFlowImpl = new BPMNSequenceFlowImpl("42", "42", "Target Activity Element Id");

    // Act and Assert
    assertNotEquals(bpmnSequenceFlowImpl, new BPMNSequenceFlowImpl("42", "42", "42"));
  }

  /**
   * Method under test: {@link BPMNSequenceFlowImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    BPMNSequenceFlowImpl bpmnSequenceFlowImpl = new BPMNSequenceFlowImpl("42", "42", "42");
    bpmnSequenceFlowImpl.setSourceActivityName("Source Activity Name");

    // Act and Assert
    assertNotEquals(bpmnSequenceFlowImpl, new BPMNSequenceFlowImpl("42", "42", "42"));
  }

  /**
   * Method under test: {@link BPMNSequenceFlowImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    BPMNSequenceFlowImpl bpmnSequenceFlowImpl = new BPMNSequenceFlowImpl("42", "42", "42");
    bpmnSequenceFlowImpl.setSourceActivityType("Source Activity Type");

    // Act and Assert
    assertNotEquals(bpmnSequenceFlowImpl, new BPMNSequenceFlowImpl("42", "42", "42"));
  }

  /**
   * Method under test: {@link BPMNSequenceFlowImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    BPMNSequenceFlowImpl bpmnSequenceFlowImpl = new BPMNSequenceFlowImpl("42", "42", "42");
    bpmnSequenceFlowImpl.setTargetActivityName("Target Activity Name");

    // Act and Assert
    assertNotEquals(bpmnSequenceFlowImpl, new BPMNSequenceFlowImpl("42", "42", "42"));
  }

  /**
   * Method under test: {@link BPMNSequenceFlowImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    BPMNSequenceFlowImpl bpmnSequenceFlowImpl = new BPMNSequenceFlowImpl("42", "42", "42");
    bpmnSequenceFlowImpl.setTargetActivityType("Target Activity Type");

    // Act and Assert
    assertNotEquals(bpmnSequenceFlowImpl, new BPMNSequenceFlowImpl("42", "42", "42"));
  }

  /**
   * Method under test: {@link BPMNSequenceFlowImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNSequenceFlowImpl("42", "42", "42"), null);
  }

  /**
   * Method under test: {@link BPMNSequenceFlowImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNSequenceFlowImpl("42", "42", "42"), "Different type to BPMNSequenceFlowImpl");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNSequenceFlowImpl#BPMNSequenceFlowImpl()}
   *   <li>{@link BPMNSequenceFlowImpl#setSourceActivityName(String)}
   *   <li>{@link BPMNSequenceFlowImpl#setSourceActivityType(String)}
   *   <li>{@link BPMNSequenceFlowImpl#setTargetActivityName(String)}
   *   <li>{@link BPMNSequenceFlowImpl#setTargetActivityType(String)}
   *   <li>{@link BPMNSequenceFlowImpl#toString()}
   *   <li>{@link BPMNSequenceFlowImpl#getSourceActivityElementId()}
   *   <li>{@link BPMNSequenceFlowImpl#getSourceActivityName()}
   *   <li>{@link BPMNSequenceFlowImpl#getSourceActivityType()}
   *   <li>{@link BPMNSequenceFlowImpl#getTargetActivityElementId()}
   *   <li>{@link BPMNSequenceFlowImpl#getTargetActivityName()}
   *   <li>{@link BPMNSequenceFlowImpl#getTargetActivityType()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    BPMNSequenceFlowImpl actualBpmnSequenceFlowImpl = new BPMNSequenceFlowImpl();
    actualBpmnSequenceFlowImpl.setSourceActivityName("Source Activity Name");
    actualBpmnSequenceFlowImpl.setSourceActivityType("Source Activity Type");
    actualBpmnSequenceFlowImpl.setTargetActivityName("Target Activity Name");
    actualBpmnSequenceFlowImpl.setTargetActivityType("Target Activity Type");
    String actualToStringResult = actualBpmnSequenceFlowImpl.toString();
    actualBpmnSequenceFlowImpl.getSourceActivityElementId();
    String actualSourceActivityName = actualBpmnSequenceFlowImpl.getSourceActivityName();
    String actualSourceActivityType = actualBpmnSequenceFlowImpl.getSourceActivityType();
    actualBpmnSequenceFlowImpl.getTargetActivityElementId();
    String actualTargetActivityName = actualBpmnSequenceFlowImpl.getTargetActivityName();

    // Assert that nothing has changed
    assertEquals("SequenceFlowImpl{sourceActivityElementId='null', sourceActivityName='Source Activity Name',"
        + " sourceActivityType='Source Activity Type', targetActivityElementId='null', targetActivityName='Target"
        + " Activity Name', targetActivityType='Target Activity Type'}", actualToStringResult);
    assertEquals("Source Activity Name", actualSourceActivityName);
    assertEquals("Source Activity Type", actualSourceActivityType);
    assertEquals("Target Activity Name", actualTargetActivityName);
    assertEquals("Target Activity Type", actualBpmnSequenceFlowImpl.getTargetActivityType());
  }

  /**
   * Method under test:
   * {@link BPMNSequenceFlowImpl#BPMNSequenceFlowImpl(String, String, String)}
   */
  @Test
  void testNewBPMNSequenceFlowImpl() {
    // Arrange and Act
    BPMNSequenceFlowImpl actualBpmnSequenceFlowImpl = new BPMNSequenceFlowImpl("42", "42", "42");

    // Assert
    assertEquals("42", actualBpmnSequenceFlowImpl.getElementId());
    assertEquals("42", actualBpmnSequenceFlowImpl.getSourceActivityElementId());
    assertEquals("42", actualBpmnSequenceFlowImpl.getTargetActivityElementId());
    assertNull(actualBpmnSequenceFlowImpl.getProcessDefinitionId());
    assertNull(actualBpmnSequenceFlowImpl.getProcessInstanceId());
    assertNull(actualBpmnSequenceFlowImpl.getSourceActivityName());
    assertNull(actualBpmnSequenceFlowImpl.getSourceActivityType());
    assertNull(actualBpmnSequenceFlowImpl.getTargetActivityName());
    assertNull(actualBpmnSequenceFlowImpl.getTargetActivityType());
  }
}
