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
package org.activiti.api.process.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.Test;

class TimerPayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TimerPayload#equals(Object)}
   *   <li>{@link TimerPayload#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    // Act and Assert
    assertEquals(timerPayload, timerPayload);
    int expectedHashCodeResult = timerPayload.hashCode();
    assertEquals(expectedHashCodeResult, timerPayload.hashCode());
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    TimerPayload timerPayload2 = new TimerPayload();
    timerPayload2.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setExceptionMessage("An error occurred");
    timerPayload2.setMaxIterations(3);
    timerPayload2.setRepeat("Repeat");
    timerPayload2.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, timerPayload2);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    TimerPayload timerPayload2 = new TimerPayload();
    timerPayload2.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setExceptionMessage("An error occurred");
    timerPayload2.setMaxIterations(3);
    timerPayload2.setRepeat("Repeat");
    timerPayload2.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, timerPayload2);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(null);
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    TimerPayload timerPayload2 = new TimerPayload();
    timerPayload2.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setExceptionMessage("An error occurred");
    timerPayload2.setMaxIterations(3);
    timerPayload2.setRepeat("Repeat");
    timerPayload2.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, timerPayload2);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    TimerPayload timerPayload2 = new TimerPayload();
    timerPayload2.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setExceptionMessage("An error occurred");
    timerPayload2.setMaxIterations(3);
    timerPayload2.setRepeat("Repeat");
    timerPayload2.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, timerPayload2);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(null);
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    TimerPayload timerPayload2 = new TimerPayload();
    timerPayload2.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setExceptionMessage("An error occurred");
    timerPayload2.setMaxIterations(3);
    timerPayload2.setRepeat("Repeat");
    timerPayload2.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, timerPayload2);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("e5034fc9-f7b4-46aa-b6e3-141cd9ce84d9");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    TimerPayload timerPayload2 = new TimerPayload();
    timerPayload2.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setExceptionMessage("An error occurred");
    timerPayload2.setMaxIterations(3);
    timerPayload2.setRepeat("Repeat");
    timerPayload2.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, timerPayload2);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage(null);
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    TimerPayload timerPayload2 = new TimerPayload();
    timerPayload2.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setExceptionMessage("An error occurred");
    timerPayload2.setMaxIterations(3);
    timerPayload2.setRepeat("Repeat");
    timerPayload2.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, timerPayload2);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(null);
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    TimerPayload timerPayload2 = new TimerPayload();
    timerPayload2.setDuedate(null);
    timerPayload2.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setExceptionMessage("An error occurred");
    timerPayload2.setMaxIterations(3);
    timerPayload2.setRepeat("Repeat");
    timerPayload2.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, timerPayload2);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(null);
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    TimerPayload timerPayload2 = new TimerPayload();
    timerPayload2.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setEndDate(null);
    timerPayload2.setExceptionMessage("An error occurred");
    timerPayload2.setMaxIterations(3);
    timerPayload2.setRepeat("Repeat");
    timerPayload2.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, timerPayload2);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage(null);
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    TimerPayload timerPayload2 = new TimerPayload();
    timerPayload2.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload2.setExceptionMessage(null);
    timerPayload2.setMaxIterations(3);
    timerPayload2.setRepeat("Repeat");
    timerPayload2.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, timerPayload2);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, null);
  }

  /**
   * Method under test: {@link TimerPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    timerPayload.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);

    // Act and Assert
    assertNotEquals(timerPayload, "Different type to TimerPayload");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TimerPayload#setDuedate(Date)}
   *   <li>{@link TimerPayload#setEndDate(Date)}
   *   <li>{@link TimerPayload#setExceptionMessage(String)}
   *   <li>{@link TimerPayload#setMaxIterations(int)}
   *   <li>{@link TimerPayload#setRepeat(String)}
   *   <li>{@link TimerPayload#setRetries(int)}
   *   <li>{@link TimerPayload#getDuedate()}
   *   <li>{@link TimerPayload#getEndDate()}
   *   <li>{@link TimerPayload#getExceptionMessage()}
   *   <li>{@link TimerPayload#getId()}
   *   <li>{@link TimerPayload#getMaxIterations()}
   *   <li>{@link TimerPayload#getRepeat()}
   *   <li>{@link TimerPayload#getRetries()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    TimerPayload timerPayload = new TimerPayload();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    timerPayload.setDuedate(dueDate);
    Date endDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    timerPayload.setEndDate(endDate);
    timerPayload.setExceptionMessage("An error occurred");
    timerPayload.setMaxIterations(3);
    timerPayload.setRepeat("Repeat");
    timerPayload.setRetries(1);
    Date actualDuedate = timerPayload.getDuedate();
    Date actualEndDate = timerPayload.getEndDate();
    String actualExceptionMessage = timerPayload.getExceptionMessage();
    timerPayload.getId();
    int actualMaxIterations = timerPayload.getMaxIterations();
    String actualRepeat = timerPayload.getRepeat();

    // Assert that nothing has changed
    assertEquals("An error occurred", actualExceptionMessage);
    assertEquals("Repeat", actualRepeat);
    assertEquals(1, timerPayload.getRetries());
    assertEquals(3, actualMaxIterations);
    assertSame(dueDate, actualDuedate);
    assertSame(endDate, actualEndDate);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link TimerPayload}
   */
  @Test
  void testNewTimerPayload() {
    // Arrange and Act
    TimerPayload actualTimerPayload = new TimerPayload();

    // Assert
    assertNull(actualTimerPayload.getExceptionMessage());
    assertNull(actualTimerPayload.getRepeat());
    assertNull(actualTimerPayload.getDuedate());
    assertNull(actualTimerPayload.getEndDate());
    assertEquals(0, actualTimerPayload.getMaxIterations());
    assertEquals(0, actualTimerPayload.getRetries());
  }
}
