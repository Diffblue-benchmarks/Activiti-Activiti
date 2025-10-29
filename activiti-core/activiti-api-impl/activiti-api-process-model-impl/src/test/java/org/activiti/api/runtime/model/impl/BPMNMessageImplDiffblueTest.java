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
import org.junit.jupiter.api.Test;

class BPMNMessageImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNMessageImpl#equals(Object)}
   *   <li>{@link BPMNMessageImpl#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNMessageImpl#equals(Object)}
   *   <li>{@link BPMNMessageImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BPMNMessageImpl bpmnMessageImpl = new BPMNMessageImpl("42");

    // Act and Assert
    assertEquals(bpmnMessageImpl, bpmnMessageImpl);
    int expectedHashCodeResult = bpmnMessageImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnMessageImpl.hashCode());
  }

  /**
   * Method under test: {@link BPMNMessageImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BPMNMessageImpl bpmnMessageImpl = new BPMNMessageImpl("Element Id");

    // Act and Assert
    assertNotEquals(bpmnMessageImpl, new BPMNMessageImpl("42"));
  }

  /**
   * Method under test: {@link BPMNMessageImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    BPMNMessageImpl bpmnMessageImpl = new BPMNMessageImpl("42");
    bpmnMessageImpl.setMessagePayload(new MessageEventPayload());

    // Act and Assert
    assertNotEquals(bpmnMessageImpl, new BPMNMessageImpl("42"));
  }

  /**
   * Method under test: {@link BPMNMessageImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    BPMNMessageImpl bpmnMessageImpl = new BPMNMessageImpl("42");
    bpmnMessageImpl.setMessagePayload(mock(MessageEventPayload.class));

    // Act and Assert
    assertNotEquals(bpmnMessageImpl, new BPMNMessageImpl("42"));
  }

  /**
   * Method under test: {@link BPMNMessageImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNMessageImpl("42"), null);
  }

  /**
   * Method under test: {@link BPMNMessageImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNMessageImpl("42"), "Different type to BPMNMessageImpl");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNMessageImpl#BPMNMessageImpl()}
   *   <li>{@link BPMNMessageImpl#setMessagePayload(MessageEventPayload)}
   *   <li>{@link BPMNMessageImpl#getMessagePayload()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    BPMNMessageImpl actualBpmnMessageImpl = new BPMNMessageImpl();
    MessageEventPayload messagePayload = new MessageEventPayload();
    actualBpmnMessageImpl.setMessagePayload(messagePayload);

    // Assert that nothing has changed
    assertSame(messagePayload, actualBpmnMessageImpl.getMessagePayload());
  }

  /**
   * Method under test: {@link BPMNMessageImpl#BPMNMessageImpl(String)}
   */
  @Test
  void testNewBPMNMessageImpl() {
    // Arrange and Act
    BPMNMessageImpl actualBpmnMessageImpl = new BPMNMessageImpl("42");

    // Assert
    assertEquals("42", actualBpmnMessageImpl.getElementId());
    assertNull(actualBpmnMessageImpl.getProcessDefinitionId());
    assertNull(actualBpmnMessageImpl.getProcessInstanceId());
    assertNull(actualBpmnMessageImpl.getMessagePayload());
  }
}
