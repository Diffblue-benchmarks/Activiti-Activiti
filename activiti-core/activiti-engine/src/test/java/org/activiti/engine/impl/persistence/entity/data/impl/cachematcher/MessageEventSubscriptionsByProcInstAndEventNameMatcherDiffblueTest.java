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

public class MessageEventSubscriptionsByProcInstAndEventNameMatcherDiffblueTest {
  /**
   * Test {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)} with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject() {
    // Arrange
    MessageEventSubscriptionsByProcInstAndEventNameMatcher
        messageEventSubscriptionsByProcInstAndEventNameMatcher =
            new MessageEventSubscriptionsByProcInstAndEventNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getProcessInstanceId()).thenReturn(null);
    when(eventSubscriptionEntity.getEventName()).thenReturn("Param");
    when(eventSubscriptionEntity.getEventType()).thenReturn("message");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Param");
    objectObjectMap.put("eventName", "Param");

    // Act
    boolean actualIsRetainedResult =
        messageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity).getProcessInstanceId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)} with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject2() {
    // Arrange
    MessageEventSubscriptionsByProcInstAndEventNameMatcher
        messageEventSubscriptionsByProcInstAndEventNameMatcher =
            new MessageEventSubscriptionsByProcInstAndEventNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventName()).thenReturn(null);
    when(eventSubscriptionEntity.getEventType()).thenReturn("message");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Param");
    objectObjectMap.put("eventName", "Param");

    // Act
    boolean actualIsRetainedResult =
        messageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)} with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject3() {
    // Arrange
    MessageEventSubscriptionsByProcInstAndEventNameMatcher
        messageEventSubscriptionsByProcInstAndEventNameMatcher =
            new MessageEventSubscriptionsByProcInstAndEventNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventType()).thenReturn(null);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Param");
    objectObjectMap.put("eventName", "Param");

    // Act
    boolean actualIsRetainedResult =
        messageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)} with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_given42() {
    // Arrange
    MessageEventSubscriptionsByProcInstAndEventNameMatcher
        messageEventSubscriptionsByProcInstAndEventNameMatcher =
            new MessageEventSubscriptionsByProcInstAndEventNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getProcessInstanceId()).thenReturn("42");
    when(eventSubscriptionEntity.getEventName()).thenReturn("Param");
    when(eventSubscriptionEntity.getEventType()).thenReturn("message");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Param");
    objectObjectMap.put("eventName", "Param");

    // Act
    boolean actualIsRetainedResult =
        messageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity, atLeast(1)).getProcessInstanceId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)} with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Event Name}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_givenEventName() {
    // Arrange
    MessageEventSubscriptionsByProcInstAndEventNameMatcher
        messageEventSubscriptionsByProcInstAndEventNameMatcher =
            new MessageEventSubscriptionsByProcInstAndEventNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventName()).thenReturn("Event Name");
    when(eventSubscriptionEntity.getEventType()).thenReturn("message");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Param");
    objectObjectMap.put("eventName", "Param");

    // Act
    boolean actualIsRetainedResult =
        messageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)} with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Event Type}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_givenEventType() {
    // Arrange
    MessageEventSubscriptionsByProcInstAndEventNameMatcher
        messageEventSubscriptionsByProcInstAndEventNameMatcher =
            new MessageEventSubscriptionsByProcInstAndEventNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventType()).thenReturn("Event Type");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Param");
    objectObjectMap.put("eventName", "Param");

    // Act
    boolean actualIsRetainedResult =
        messageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)} with {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MessageEventSubscriptionsByProcInstAndEventNameMatcher#isRetained(EventSubscriptionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_thenReturnTrue() {
    // Arrange
    MessageEventSubscriptionsByProcInstAndEventNameMatcher
        messageEventSubscriptionsByProcInstAndEventNameMatcher =
            new MessageEventSubscriptionsByProcInstAndEventNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getProcessInstanceId()).thenReturn("Param");
    when(eventSubscriptionEntity.getEventName()).thenReturn("Param");
    when(eventSubscriptionEntity.getEventType()).thenReturn("message");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("processInstanceId", "Param");
    objectObjectMap.put("eventName", "Param");

    // Act
    boolean actualIsRetainedResult =
        messageEventSubscriptionsByProcInstAndEventNameMatcher.isRetained(
            eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualIsRetainedResult);
  }
}
