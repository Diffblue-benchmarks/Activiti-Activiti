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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.process.model.payloads.TimerPayload;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BPMNTimerImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNTimerImpl#equals(Object)}
   *   <li>{@link BPMNTimerImpl#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNTimerImpl#equals(Object)}
   *   <li>{@link BPMNTimerImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BPMNTimerImpl bpmnTimerImpl = new BPMNTimerImpl("42");

    // Act and Assert
    assertEquals(bpmnTimerImpl, bpmnTimerImpl);
    int expectedHashCodeResult = bpmnTimerImpl.hashCode();
    assertEquals(expectedHashCodeResult, bpmnTimerImpl.hashCode());
  }

  /**
   * Method under test: {@link BPMNTimerImpl#toString()}
   */
  @Test
  void testToString() {
    // Arrange
    TimerPayload timerPayload = mock(TimerPayload.class);
    doNothing().when(timerPayload).setDuedate(Mockito.<Date>any());
    doNothing().when(timerPayload).setEndDate(Mockito.<Date>any());
    doNothing().when(timerPayload).setExceptionMessage(Mockito.<String>any());
    doNothing().when(timerPayload).setMaxIterations(anyInt());
    doNothing().when(timerPayload).setRepeat(Mockito.<String>any());
    doNothing().when(timerPayload).setRetries(anyInt());
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    BPMNTimerImpl bpmnTimerImpl = new BPMNTimerImpl("42");
    bpmnTimerImpl.setTimerPayload(timerPayload);

    // Act
    bpmnTimerImpl.toString();

    // Assert
    verify(timerPayload).setDuedate(isA(Date.class));
    verify(timerPayload).setEndDate(isA(Date.class));
    verify(timerPayload).setExceptionMessage(eq("An error occurred"));
    verify(timerPayload).setMaxIterations(eq(3));
    verify(timerPayload).setRepeat(eq("Repeat"));
    verify(timerPayload).setRetries(eq(1));
  }

  /**
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BPMNTimerImpl bpmnTimerImpl = new BPMNTimerImpl("Element Id");

    // Act and Assert
    assertNotEquals(bpmnTimerImpl, new BPMNTimerImpl("42"));
  }

  /**
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNTimerImpl("42"), mock(BPMNActivityImpl.class));
  }

  /**
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
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
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
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
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNTimerImpl("42"), null);
  }

  /**
   * Method under test: {@link BPMNTimerImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BPMNTimerImpl("42"), "Different type to BPMNTimerImpl");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BPMNTimerImpl#BPMNTimerImpl()}
   *   <li>{@link BPMNTimerImpl#setTimerPayload(TimerPayload)}
   *   <li>{@link BPMNTimerImpl#getTimerPayload()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link BPMNTimerImpl#BPMNTimerImpl(String)}
   */
  @Test
  void testNewBPMNTimerImpl() {
    // Arrange and Act
    BPMNTimerImpl actualBpmnTimerImpl = new BPMNTimerImpl("42");

    // Assert
    assertEquals("42", actualBpmnTimerImpl.getElementId());
    assertNull(actualBpmnTimerImpl.getProcessDefinitionId());
    assertNull(actualBpmnTimerImpl.getProcessInstanceId());
    assertNull(actualBpmnTimerImpl.getTimerPayload());
  }
}
