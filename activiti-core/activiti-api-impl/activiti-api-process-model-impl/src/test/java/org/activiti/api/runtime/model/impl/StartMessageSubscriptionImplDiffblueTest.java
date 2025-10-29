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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.Test;

class StartMessageSubscriptionImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageSubscriptionImpl.Builder#build()}
   *   <li>{@link StartMessageSubscriptionImpl.Builder#withActivityId(String)}
   *   <li>{@link StartMessageSubscriptionImpl.Builder#withConfiguration(String)}
   *   <li>{@link StartMessageSubscriptionImpl.Builder#withCreated(Date)}
   *   <li>{@link StartMessageSubscriptionImpl.Builder#withEventName(String)}
   *   <li>{@link StartMessageSubscriptionImpl.Builder#withId(String)}
   *   <li>
   * {@link StartMessageSubscriptionImpl.Builder#withProcessDefinitionId(String)}
   * </ul>
   */
  @Test
  void testBuilderBuild() {
    // Arrange
    StartMessageSubscriptionImpl.Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    Date created = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    StartMessageSubscriptionImpl actualBuildResult = withConfigurationResult.withCreated(created)
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getActivityId());
    assertEquals("42", actualBuildResult.getId());
    assertEquals("42", actualBuildResult.getProcessDefinitionId());
    assertEquals("Configuration", actualBuildResult.getConfiguration());
    assertEquals("Event Name", actualBuildResult.getEventName());
    assertSame(created, actualBuildResult.getCreated());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageSubscriptionImpl#equals(Object)}
   *   <li>{@link StartMessageSubscriptionImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StartMessageSubscriptionImpl startMessageSubscriptionImpl = new StartMessageSubscriptionImpl();
    StartMessageSubscriptionImpl startMessageSubscriptionImpl2 = new StartMessageSubscriptionImpl();

    // Act and Assert
    assertEquals(startMessageSubscriptionImpl, startMessageSubscriptionImpl2);
    int expectedHashCodeResult = startMessageSubscriptionImpl.hashCode();
    assertEquals(expectedHashCodeResult, startMessageSubscriptionImpl2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageSubscriptionImpl#equals(Object)}
   *   <li>{@link StartMessageSubscriptionImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StartMessageSubscriptionImpl startMessageSubscriptionImpl = new StartMessageSubscriptionImpl();

    // Act and Assert
    assertEquals(startMessageSubscriptionImpl, startMessageSubscriptionImpl);
    int expectedHashCodeResult = startMessageSubscriptionImpl.hashCode();
    assertEquals(expectedHashCodeResult, startMessageSubscriptionImpl.hashCode());
  }

  /**
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StartMessageSubscriptionImpl(), 1);
  }

  /**
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StartMessageSubscriptionImpl(), null);
  }

  /**
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StartMessageSubscriptionImpl(), "Different type to StartMessageSubscriptionImpl");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageSubscriptionImpl#StartMessageSubscriptionImpl()}
   *   <li>
   * {@link StartMessageSubscriptionImpl#builderFrom(StartMessageSubscriptionImpl)}
   *   <li>{@link StartMessageSubscriptionImpl#toString()}
   *   <li>{@link StartMessageSubscriptionImpl#getActivityId()}
   *   <li>{@link StartMessageSubscriptionImpl#getConfiguration()}
   *   <li>{@link StartMessageSubscriptionImpl#getCreated()}
   *   <li>{@link StartMessageSubscriptionImpl#getEventName()}
   *   <li>{@link StartMessageSubscriptionImpl#getId()}
   *   <li>{@link StartMessageSubscriptionImpl#getProcessDefinitionId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    StartMessageSubscriptionImpl actualStartMessageSubscriptionImpl = new StartMessageSubscriptionImpl();
    actualStartMessageSubscriptionImpl.builderFrom(new StartMessageSubscriptionImpl());
    String actualToStringResult = actualStartMessageSubscriptionImpl.toString();
    String actualActivityId = actualStartMessageSubscriptionImpl.getActivityId();
    String actualConfiguration = actualStartMessageSubscriptionImpl.getConfiguration();
    Date actualCreated = actualStartMessageSubscriptionImpl.getCreated();
    String actualEventName = actualStartMessageSubscriptionImpl.getEventName();
    String actualId = actualStartMessageSubscriptionImpl.getId();

    // Assert
    assertEquals("MessageEventSubscriptionImpl [id=null, eventName=null, processDefinitionId=null, configuration=null,"
        + " activityId=null, created=null]", actualToStringResult);
    assertNull(actualActivityId);
    assertNull(actualConfiguration);
    assertNull(actualEventName);
    assertNull(actualId);
    assertNull(actualStartMessageSubscriptionImpl.getProcessDefinitionId());
    assertNull(actualCreated);
  }
}
