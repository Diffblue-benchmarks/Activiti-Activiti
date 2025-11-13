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
package org.activiti.engine.impl.persistence.entity.data.impl.cachematcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SignalEventSubscriptionByNameAndExecutionMatcherDiffblueTest {
  /**
   * Test {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SignalEventSubscriptionByNameAndExecutionMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject() {
    // Arrange
    SignalEventSubscriptionByNameAndExecutionMatcher
        signalEventSubscriptionByNameAndExecutionMatcher =
            new SignalEventSubscriptionByNameAndExecutionMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventName()).thenReturn(null);
    when(eventSubscriptionEntity.getExecutionId()).thenReturn("Parameter");
    when(eventSubscriptionEntity.getEventType()).thenReturn("signal");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("eventName", "Parameter");

    // Act
    boolean actualIsRetainedResult =
        signalEventSubscriptionByNameAndExecutionMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity, atLeast(1)).getExecutionId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SignalEventSubscriptionByNameAndExecutionMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject2() {
    // Arrange
    SignalEventSubscriptionByNameAndExecutionMatcher
        signalEventSubscriptionByNameAndExecutionMatcher =
            new SignalEventSubscriptionByNameAndExecutionMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getExecutionId()).thenReturn(null);
    when(eventSubscriptionEntity.getEventType()).thenReturn("signal");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("eventName", "Parameter");

    // Act
    boolean actualIsRetainedResult =
        signalEventSubscriptionByNameAndExecutionMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity).getExecutionId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SignalEventSubscriptionByNameAndExecutionMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject3() {
    // Arrange
    SignalEventSubscriptionByNameAndExecutionMatcher
        signalEventSubscriptionByNameAndExecutionMatcher =
            new SignalEventSubscriptionByNameAndExecutionMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventType()).thenReturn(null);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("eventName", "Parameter");

    // Act
    boolean actualIsRetainedResult =
        signalEventSubscriptionByNameAndExecutionMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SignalEventSubscriptionByNameAndExecutionMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_given42() {
    // Arrange
    SignalEventSubscriptionByNameAndExecutionMatcher
        signalEventSubscriptionByNameAndExecutionMatcher =
            new SignalEventSubscriptionByNameAndExecutionMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getExecutionId()).thenReturn("42");
    when(eventSubscriptionEntity.getEventType()).thenReturn("signal");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("eventName", "Parameter");

    // Act
    boolean actualIsRetainedResult =
        signalEventSubscriptionByNameAndExecutionMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity, atLeast(1)).getExecutionId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Event Name}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SignalEventSubscriptionByNameAndExecutionMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_givenEventName() {
    // Arrange
    SignalEventSubscriptionByNameAndExecutionMatcher
        signalEventSubscriptionByNameAndExecutionMatcher =
            new SignalEventSubscriptionByNameAndExecutionMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventName()).thenReturn("Event Name");
    when(eventSubscriptionEntity.getExecutionId()).thenReturn("Parameter");
    when(eventSubscriptionEntity.getEventType()).thenReturn("signal");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("eventName", "Parameter");

    // Act
    boolean actualIsRetainedResult =
        signalEventSubscriptionByNameAndExecutionMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity, atLeast(1)).getExecutionId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Event Type}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SignalEventSubscriptionByNameAndExecutionMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_givenEventType() {
    // Arrange
    SignalEventSubscriptionByNameAndExecutionMatcher
        signalEventSubscriptionByNameAndExecutionMatcher =
            new SignalEventSubscriptionByNameAndExecutionMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventType()).thenReturn("Event Type");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("eventName", "Parameter");

    // Act
    boolean actualIsRetainedResult =
        signalEventSubscriptionByNameAndExecutionMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalEventSubscriptionByNameAndExecutionMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SignalEventSubscriptionByNameAndExecutionMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_thenReturnTrue() {
    // Arrange
    SignalEventSubscriptionByNameAndExecutionMatcher
        signalEventSubscriptionByNameAndExecutionMatcher =
            new SignalEventSubscriptionByNameAndExecutionMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventName()).thenReturn("Parameter");
    when(eventSubscriptionEntity.getExecutionId()).thenReturn("Parameter");
    when(eventSubscriptionEntity.getEventType()).thenReturn("signal");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("eventName", "Parameter");

    // Act
    boolean actualIsRetainedResult =
        signalEventSubscriptionByNameAndExecutionMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity, atLeast(1)).getExecutionId();
    assertTrue(actualIsRetainedResult);
  }
}
