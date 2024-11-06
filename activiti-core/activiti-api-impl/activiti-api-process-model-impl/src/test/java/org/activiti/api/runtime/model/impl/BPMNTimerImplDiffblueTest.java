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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.process.model.payloads.TimerPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BPMNTimerImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNTimerImpl#BPMNTimerImpl()}
   *   <li>{@link BPMNTimerImpl#setTimerPayload(TimerPayload)}
   *   <li>{@link BPMNTimerImpl#getTimerPayload()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    BPMNTimerImpl actualBpmnTimerImpl = new BPMNTimerImpl();
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);
    actualBpmnTimerImpl.setTimerPayload(timerPayload);

    // Assert that nothing has changed
    assertSame(timerPayload, actualBpmnTimerImpl.getTimerPayload());
  }

  /**
   * Test {@link BPMNTimerImpl#BPMNTimerImpl(String)}.
   * <p>
   * Method under test: {@link BPMNTimerImpl#BPMNTimerImpl(String)}
   */
  @Test
  @DisplayName("Test new BPMNTimerImpl(String)")
  void testNewBPMNTimerImpl() {
    // Arrange and Act
    BPMNTimerImpl actualBpmnTimerImpl = new BPMNTimerImpl("42");

    // Assert
    assertEquals("42", actualBpmnTimerImpl.getElementId());
    assertNull(actualBpmnTimerImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerImpl.getProcessInstanceId());
    assertNull(actualBpmnTimerImpl.getTimerPayload());
  }

  /**
   * Test {@link BPMNTimerImpl#equals(Object)}, and
   * {@link BPMNTimerImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNTimerImpl#equals(Object)}
   *   <li>{@link BPMNTimerImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    BPMNTimerImpl bpmnTimerImpl = new BPMNTimerImpl("42");
    BPMNTimerImpl bpmnTimerImpl2 = new BPMNTimerImpl("42");

    // Act and Assert
    assertEquals(bpmnTimerImpl, bpmnTimerImpl2);
    int expectedHashCodeResult = bpmnTimerImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnTimerImpl2.hashCode());
  }

  /**
   * Test {@link BPMNTimerImpl#equals(Object)}, and
   * {@link BPMNTimerImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNTimerImpl#equals(Object)}
   *   <li>{@link BPMNTimerImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BPMNTimerImpl bpmnTimerImpl = new BPMNTimerImpl("42");

    // Act and Assert
    assertEquals(bpmnTimerImpl, bpmnTimerImpl);
    int expectedHashCodeResult = bpmnTimerImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnTimerImpl.hashCode());
  }

  /**
   * Test {@link BPMNTimerImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BPMNTimerImpl bpmnTimerImpl = new BPMNTimerImpl("Element Id");

    // Act and Assert
    assertNotEquals(bpmnTimerImpl, new BPMNTimerImpl("42"));
  }

  /**
   * Test {@link BPMNTimerImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNTimerImpl("42"), mock(BPMNActivityImpl.class));
  }

  /**
   * Test {@link BPMNTimerImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    BPMNTimerImpl bpmnTimerImpl = new BPMNTimerImpl("42");
    bpmnTimerImpl.setTimerPayload(timerPayload);

    // Act and Assert
    assertNotEquals(bpmnTimerImpl, new BPMNTimerImpl("42"));
  }

  /**
   * Test {@link BPMNTimerImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    BPMNTimerImpl bpmnTimerImpl = new BPMNTimerImpl("42");

    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    BPMNTimerImpl bpmnTimerImpl2 = new BPMNTimerImpl("42");
    bpmnTimerImpl2.setTimerPayload(timerPayload);

    // Act and Assert
    assertNotEquals(bpmnTimerImpl, bpmnTimerImpl2);
  }

  /**
   * Test {@link BPMNTimerImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNTimerImpl("42"), null);
  }

  /**
   * Test {@link BPMNTimerImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNTimerImpl("42"), "Different type to BPMNTimerImpl");
  }

  /**
   * Test {@link BPMNTimerImpl#toString()}.
   * <ul>
   *   <li>Then return {@code BPMNActivityImpl{, elementId='42',
   * timerPayload='null'}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BPMNTimerImpl#toString()}
   */
  @Test
  @DisplayName("Test toString(); then return 'BPMNActivityImpl{, elementId='42', timerPayload='null'}'")
  void testToString_thenReturnBPMNActivityImplElementId42TimerPayloadNull() {
    // Arrange, Act and Assert
    assertEquals("BPMNActivityImpl{, elementId='42', timerPayload='null'}", (new BPMNTimerImpl("42")).toString());
  }
}
