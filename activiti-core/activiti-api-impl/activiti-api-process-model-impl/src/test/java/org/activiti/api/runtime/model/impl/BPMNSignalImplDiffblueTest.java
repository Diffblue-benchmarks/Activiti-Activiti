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
import org.activiti.api.process.model.payloads.SignalPayload;
import org.junit.jupiter.api.Test;

class BPMNSignalImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNSignalImpl#equals(Object)}
   *   <li>{@link BPMNSignalImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    BPMNSignalImpl bpmnSignalImpl = new BPMNSignalImpl("42");
    BPMNSignalImpl bpmnSignalImpl2 = new BPMNSignalImpl("42");

    // Act and Assert
    assertEquals(bpmnSignalImpl, bpmnSignalImpl2);
    int expectedHashCodeResult = bpmnSignalImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnSignalImpl2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNSignalImpl#equals(Object)}
   *   <li>{@link BPMNSignalImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BPMNSignalImpl bpmnSignalImpl = new BPMNSignalImpl("42");

    // Act and Assert
    assertEquals(bpmnSignalImpl, bpmnSignalImpl);
    int expectedHashCodeResult = bpmnSignalImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnSignalImpl.hashCode());
  }

  /**
   * Method under test: {@link BPMNSignalImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BPMNSignalImpl bpmnSignalImpl = new BPMNSignalImpl("Element Id");

    // Act and Assert
    assertNotEquals(bpmnSignalImpl, new BPMNSignalImpl("42"));
  }

  /**
   * Method under test: {@link BPMNSignalImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    BPMNSignalImpl bpmnSignalImpl = new BPMNSignalImpl("42");
    bpmnSignalImpl.setSignalPayload(new SignalPayload());

    // Act and Assert
    assertNotEquals(bpmnSignalImpl, new BPMNSignalImpl("42"));
  }

  /**
   * Method under test: {@link BPMNSignalImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    BPMNSignalImpl bpmnSignalImpl = new BPMNSignalImpl("42");
    bpmnSignalImpl.setSignalPayload(mock(SignalPayload.class));

    // Act and Assert
    assertNotEquals(bpmnSignalImpl, new BPMNSignalImpl("42"));
  }

  /**
   * Method under test: {@link BPMNSignalImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNSignalImpl("42"), null);
  }

  /**
   * Method under test: {@link BPMNSignalImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNSignalImpl("42"), "Different type to BPMNSignalImpl");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNSignalImpl#BPMNSignalImpl()}
   *   <li>{@link BPMNSignalImpl#setSignalPayload(SignalPayload)}
   *   <li>{@link BPMNSignalImpl#getSignalPayload()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    BPMNSignalImpl actualBpmnSignalImpl = new BPMNSignalImpl();
    SignalPayload signalPayload = new SignalPayload();
    actualBpmnSignalImpl.setSignalPayload(signalPayload);

    // Assert that nothing has changed
    assertSame(signalPayload, actualBpmnSignalImpl.getSignalPayload());
  }

  /**
   * Method under test: {@link BPMNSignalImpl#BPMNSignalImpl(String)}
   */
  @Test
  void testNewBPMNSignalImpl() {
    // Arrange and Act
    BPMNSignalImpl actualBpmnSignalImpl = new BPMNSignalImpl("42");

    // Assert
    assertEquals("42", actualBpmnSignalImpl.getElementId());
    assertNull(actualBpmnSignalImpl.getProcessDefinitionId());
    assertNull(actualBpmnSignalImpl.getProcessInstanceId());
    assertNull(actualBpmnSignalImpl.getSignalPayload());
  }
}
