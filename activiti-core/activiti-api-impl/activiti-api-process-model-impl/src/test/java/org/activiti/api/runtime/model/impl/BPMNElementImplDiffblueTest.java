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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BPMNElementImplDiffblueTest {
  /**
   * Test {@link BPMNElementImpl#equals(Object)}, and
   * {@link BPMNElementImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNElementImpl#equals(Object)}
   *   <li>{@link BPMNElementImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    BPMNElementImpl bpmnElementImpl = new BPMNElementImpl();
    bpmnElementImpl.setElementId("42");
    bpmnElementImpl.setProcessDefinitionId("42");
    bpmnElementImpl.setProcessInstanceId("42");

    BPMNElementImpl bpmnElementImpl2 = new BPMNElementImpl();
    bpmnElementImpl2.setElementId("42");
    bpmnElementImpl2.setProcessDefinitionId("42");
    bpmnElementImpl2.setProcessInstanceId("42");

    // Act and Assert
    assertEquals(bpmnElementImpl, bpmnElementImpl2);
    int expectedHashCodeResult = bpmnElementImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnElementImpl2.hashCode());
  }

  /**
   * Test {@link BPMNElementImpl#equals(Object)}, and
   * {@link BPMNElementImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNElementImpl#equals(Object)}
   *   <li>{@link BPMNElementImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BPMNElementImpl bpmnElementImpl = new BPMNElementImpl();
    bpmnElementImpl.setElementId("42");
    bpmnElementImpl.setProcessDefinitionId("42");
    bpmnElementImpl.setProcessInstanceId("42");

    // Act and Assert
    assertEquals(bpmnElementImpl, bpmnElementImpl);
    int expectedHashCodeResult = bpmnElementImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnElementImpl.hashCode());
  }

  /**
   * Test {@link BPMNElementImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNElementImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BPMNActivityImpl bpmnActivityImpl = new BPMNActivityImpl("42", "Activity Name", "Activity Type");
    bpmnActivityImpl.setElementId("42");
    bpmnActivityImpl.setProcessDefinitionId("42");
    bpmnActivityImpl.setProcessInstanceId("42");

    BPMNElementImpl bpmnElementImpl = new BPMNElementImpl();
    bpmnElementImpl.setElementId("42");
    bpmnElementImpl.setProcessDefinitionId("42");
    bpmnElementImpl.setProcessInstanceId("42");

    // Act and Assert
    assertNotEquals(bpmnActivityImpl, bpmnElementImpl);
  }

  /**
   * Test {@link BPMNElementImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNElementImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    BPMNElementImpl bpmnElementImpl = new BPMNElementImpl();
    bpmnElementImpl.setElementId("Element Id");
    bpmnElementImpl.setProcessDefinitionId("42");
    bpmnElementImpl.setProcessInstanceId("42");

    BPMNElementImpl bpmnElementImpl2 = new BPMNElementImpl();
    bpmnElementImpl2.setElementId("42");
    bpmnElementImpl2.setProcessDefinitionId("42");
    bpmnElementImpl2.setProcessInstanceId("42");

    // Act and Assert
    assertNotEquals(bpmnElementImpl, bpmnElementImpl2);
  }

  /**
   * Test {@link BPMNElementImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNElementImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    BPMNElementImpl bpmnElementImpl = new BPMNElementImpl();
    bpmnElementImpl.setElementId("42");
    bpmnElementImpl.setProcessDefinitionId("Process Definition Id");
    bpmnElementImpl.setProcessInstanceId("42");

    BPMNElementImpl bpmnElementImpl2 = new BPMNElementImpl();
    bpmnElementImpl2.setElementId("42");
    bpmnElementImpl2.setProcessDefinitionId("42");
    bpmnElementImpl2.setProcessInstanceId("42");

    // Act and Assert
    assertNotEquals(bpmnElementImpl, bpmnElementImpl2);
  }

  /**
   * Test {@link BPMNElementImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNElementImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    BPMNElementImpl bpmnElementImpl = new BPMNElementImpl();
    bpmnElementImpl.setElementId("42");
    bpmnElementImpl.setProcessDefinitionId("42");
    bpmnElementImpl.setProcessInstanceId("Process Instance Id");

    BPMNElementImpl bpmnElementImpl2 = new BPMNElementImpl();
    bpmnElementImpl2.setElementId("42");
    bpmnElementImpl2.setProcessDefinitionId("42");
    bpmnElementImpl2.setProcessInstanceId("42");

    // Act and Assert
    assertNotEquals(bpmnElementImpl, bpmnElementImpl2);
  }

  /**
   * Test {@link BPMNElementImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNElementImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    BPMNElementImpl bpmnElementImpl = new BPMNElementImpl();
    bpmnElementImpl.setElementId("42");
    bpmnElementImpl.setProcessDefinitionId("42");
    bpmnElementImpl.setProcessInstanceId("42");
    BPMNActivityImpl bpmnActivityImpl = mock(BPMNActivityImpl.class);
    doNothing().when(bpmnActivityImpl).setElementId(Mockito.<String>any());
    doNothing().when(bpmnActivityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(bpmnActivityImpl).setProcessInstanceId(Mockito.<String>any());
    bpmnActivityImpl.setElementId("42");
    bpmnActivityImpl.setProcessDefinitionId("42");
    bpmnActivityImpl.setProcessInstanceId("42");

    // Act and Assert
    assertNotEquals(bpmnElementImpl, bpmnActivityImpl);
  }

  /**
   * Test {@link BPMNElementImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNElementImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    BPMNElementImpl bpmnElementImpl = new BPMNElementImpl();
    bpmnElementImpl.setElementId("42");
    bpmnElementImpl.setProcessDefinitionId("42");
    bpmnElementImpl.setProcessInstanceId("42");

    // Act and Assert
    assertNotEquals(bpmnElementImpl, null);
  }

  /**
   * Test {@link BPMNElementImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNElementImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    BPMNElementImpl bpmnElementImpl = new BPMNElementImpl();
    bpmnElementImpl.setElementId("42");
    bpmnElementImpl.setProcessDefinitionId("42");
    bpmnElementImpl.setProcessInstanceId("42");

    // Act and Assert
    assertNotEquals(bpmnElementImpl, "Different type to BPMNElementImpl");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BPMNElementImpl}
   *   <li>{@link BPMNElementImpl#setElementId(String)}
   *   <li>{@link BPMNElementImpl#setProcessDefinitionId(String)}
   *   <li>{@link BPMNElementImpl#setProcessInstanceId(String)}
   *   <li>{@link BPMNElementImpl#getElementId()}
   *   <li>{@link BPMNElementImpl#getProcessDefinitionId()}
   *   <li>{@link BPMNElementImpl#getProcessInstanceId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    BPMNElementImpl actualBpmnElementImpl = new BPMNElementImpl();
    actualBpmnElementImpl.setElementId("42");
    actualBpmnElementImpl.setProcessDefinitionId("42");
    actualBpmnElementImpl.setProcessInstanceId("42");
    String actualElementId = actualBpmnElementImpl.getElementId();
    String actualProcessDefinitionId = actualBpmnElementImpl.getProcessDefinitionId();

    // Assert that nothing has changed
    assertEquals("42", actualElementId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualBpmnElementImpl.getProcessInstanceId());
  }
}
