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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import org.activiti.api.process.model.payloads.MessageEventPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BPMNMessageImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNMessageImpl#BPMNMessageImpl()}
   *   <li>{@link BPMNMessageImpl#setMessagePayload(MessageEventPayload)}
   *   <li>{@link BPMNMessageImpl#getMessagePayload()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    BPMNMessageImpl actualBpmnMessageImpl = new BPMNMessageImpl();
    MessageEventPayload messagePayload = new MessageEventPayload();
    actualBpmnMessageImpl.setMessagePayload(messagePayload);

    // Assert that nothing has changed
    assertSame(messagePayload, actualBpmnMessageImpl.getMessagePayload());
  }

  /**
   * Test {@link BPMNMessageImpl#BPMNMessageImpl(String)}.
   * <p>
   * Method under test: {@link BPMNMessageImpl#BPMNMessageImpl(String)}
   */
  @Test
  @DisplayName("Test new BPMNMessageImpl(String)")
  void testNewBPMNMessageImpl() {
    // Arrange and Act
    BPMNMessageImpl actualBpmnMessageImpl = new BPMNMessageImpl("42");

    // Assert
    assertEquals("42", actualBpmnMessageImpl.getElementId());
    assertNull(actualBpmnMessageImpl.getProcessDefinitionId());
    assertNull(actualBpmnMessageImpl.getProcessInstanceId());
    assertNull(actualBpmnMessageImpl.getMessagePayload());
  }

  /**
   * Test {@link BPMNMessageImpl#equals(Object)}, and
   * {@link BPMNMessageImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNMessageImpl#equals(Object)}
   *   <li>{@link BPMNMessageImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    BPMNMessageImpl bpmnMessageImpl = new BPMNMessageImpl("42");
    BPMNMessageImpl bpmnMessageImpl2 = new BPMNMessageImpl("42");

    // Act and Assert
    assertEquals(bpmnMessageImpl, bpmnMessageImpl2);
    int expectedHashCodeResult = bpmnMessageImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnMessageImpl2.hashCode());
  }

  /**
   * Test {@link BPMNMessageImpl#equals(Object)}, and
   * {@link BPMNMessageImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNMessageImpl#equals(Object)}
   *   <li>{@link BPMNMessageImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BPMNMessageImpl bpmnMessageImpl = new BPMNMessageImpl("42");

    // Act and Assert
    assertEquals(bpmnMessageImpl, bpmnMessageImpl);
    int expectedHashCodeResult = bpmnMessageImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnMessageImpl.hashCode());
  }

  /**
   * Test {@link BPMNMessageImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNMessageImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BPMNMessageImpl bpmnMessageImpl = new BPMNMessageImpl("Element Id");

    // Act and Assert
    assertNotEquals(bpmnMessageImpl, new BPMNMessageImpl("42"));
  }

  /**
   * Test {@link BPMNMessageImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNMessageImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    BPMNMessageImpl bpmnMessageImpl = new BPMNMessageImpl("42");
    bpmnMessageImpl.setMessagePayload(new MessageEventPayload());

    // Act and Assert
    assertNotEquals(bpmnMessageImpl, new BPMNMessageImpl("42"));
  }

  /**
   * Test {@link BPMNMessageImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNMessageImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    BPMNMessageImpl bpmnMessageImpl = new BPMNMessageImpl("42");
    bpmnMessageImpl.setMessagePayload(mock(MessageEventPayload.class));

    // Act and Assert
    assertNotEquals(bpmnMessageImpl, new BPMNMessageImpl("42"));
  }

  /**
   * Test {@link BPMNMessageImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNMessageImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNMessageImpl("42"), null);
  }

  /**
   * Test {@link BPMNMessageImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNMessageImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNMessageImpl("42"), "Different type to BPMNMessageImpl");
  }

  /**
   * Test {@link BPMNMessageImpl#toString()}.
   * <ul>
   *   <li>Then return {@code BPMNMessageImpl{, elementId='42',
   * messagePayload='null'}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNMessageImpl#toString()}
   */
  @Test
  @DisplayName("Test toString(); then return 'BPMNMessageImpl{, elementId='42', messagePayload='null'}'")
  void testToString_thenReturnBPMNMessageImplElementId42MessagePayloadNull() {
    // Arrange, Act and Assert
    assertEquals("BPMNMessageImpl{, elementId='42', messagePayload='null'}", (new BPMNMessageImpl("42")).toString());
  }
}
