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

public class EventSubscriptionsByNameMatcherDiffblueTest {
  /**
   * Test {@link EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)} with
   * {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean EventSubscriptionsByNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject() {
    // Arrange
    EventSubscriptionsByNameMatcher eventSubscriptionsByNameMatcher =
        new EventSubscriptionsByNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getTenantId()).thenReturn("");
    when(eventSubscriptionEntity.getEventName()).thenReturn("Parameter");
    when(eventSubscriptionEntity.getEventType()).thenReturn("Parameter");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("eventType", "Parameter");
    objectObjectMap.put("eventName", "Parameter");
    objectObjectMap.put("tenantId", "");

    // Act
    boolean actualIsRetainedResult =
        eventSubscriptionsByNameMatcher.isRetained(eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity).getTenantId();
    assertTrue(actualIsRetainedResult);
  }

  /**
   * Test {@link EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)} with
   * {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean EventSubscriptionsByNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject2() {
    // Arrange
    EventSubscriptionsByNameMatcher eventSubscriptionsByNameMatcher =
        new EventSubscriptionsByNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getTenantId()).thenReturn("  ");
    when(eventSubscriptionEntity.getEventName()).thenReturn("Parameter");
    when(eventSubscriptionEntity.getEventType()).thenReturn("Parameter");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("eventType", "Parameter");
    objectObjectMap.put("eventName", "Parameter");
    objectObjectMap.put("tenantId", "");

    // Act
    boolean actualIsRetainedResult =
        eventSubscriptionsByNameMatcher.isRetained(eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity, atLeast(1)).getTenantId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)} with
   * {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean EventSubscriptionsByNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject3() {
    // Arrange
    EventSubscriptionsByNameMatcher eventSubscriptionsByNameMatcher =
        new EventSubscriptionsByNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getTenantId()).thenReturn(null);
    when(eventSubscriptionEntity.getEventName()).thenReturn("Parameter");
    when(eventSubscriptionEntity.getEventType()).thenReturn("Parameter");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("eventType", "Parameter");
    objectObjectMap.put("eventName", "Parameter");
    objectObjectMap.put("tenantId", "");

    // Act
    boolean actualIsRetainedResult =
        eventSubscriptionsByNameMatcher.isRetained(eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity, atLeast(1)).getTenantId();
    assertTrue(actualIsRetainedResult);
  }

  /**
   * Test {@link EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)} with
   * {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean EventSubscriptionsByNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject4() {
    // Arrange
    EventSubscriptionsByNameMatcher eventSubscriptionsByNameMatcher =
        new EventSubscriptionsByNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventName()).thenReturn(null);
    when(eventSubscriptionEntity.getEventType()).thenReturn("Parameter");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("eventType", "Parameter");
    objectObjectMap.put("eventName", "Parameter");
    objectObjectMap.put("tenantId", "");

    // Act
    boolean actualIsRetainedResult =
        eventSubscriptionsByNameMatcher.isRetained(eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)} with
   * {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean EventSubscriptionsByNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject5() {
    // Arrange
    EventSubscriptionsByNameMatcher eventSubscriptionsByNameMatcher =
        new EventSubscriptionsByNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventType()).thenReturn(null);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("eventType", "Parameter");
    objectObjectMap.put("eventName", "Parameter");
    objectObjectMap.put("tenantId", "");

    // Act
    boolean actualIsRetainedResult =
        eventSubscriptionsByNameMatcher.isRetained(eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)} with
   * {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean EventSubscriptionsByNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject6() {
    // Arrange
    EventSubscriptionsByNameMatcher eventSubscriptionsByNameMatcher =
        new EventSubscriptionsByNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getTenantId()).thenReturn("");
    when(eventSubscriptionEntity.getEventName()).thenReturn("Parameter");
    when(eventSubscriptionEntity.getEventType()).thenReturn("Parameter");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("eventType", "Parameter");
    objectObjectMap.put("eventName", "Parameter");
    objectObjectMap.put("Parameter", "");

    // Act
    boolean actualIsRetainedResult =
        eventSubscriptionsByNameMatcher.isRetained(eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity).getTenantId();
    assertTrue(actualIsRetainedResult);
  }

  /**
   * Test {@link EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)} with
   * {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean EventSubscriptionsByNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject7() {
    // Arrange
    EventSubscriptionsByNameMatcher eventSubscriptionsByNameMatcher =
        new EventSubscriptionsByNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getTenantId()).thenReturn("  ");
    when(eventSubscriptionEntity.getEventName()).thenReturn("Parameter");
    when(eventSubscriptionEntity.getEventType()).thenReturn("Parameter");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("eventType", "Parameter");
    objectObjectMap.put("eventName", "Parameter");
    objectObjectMap.put("tenantId", "  ");

    // Act
    boolean actualIsRetainedResult =
        eventSubscriptionsByNameMatcher.isRetained(eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity, atLeast(1)).getTenantId();
    assertTrue(actualIsRetainedResult);
  }

  /**
   * Test {@link EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)} with
   * {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Event Name}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean EventSubscriptionsByNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_givenEventName() {
    // Arrange
    EventSubscriptionsByNameMatcher eventSubscriptionsByNameMatcher =
        new EventSubscriptionsByNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventName()).thenReturn("Event Name");
    when(eventSubscriptionEntity.getEventType()).thenReturn("Parameter");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("eventType", "Parameter");
    objectObjectMap.put("eventName", "Parameter");
    objectObjectMap.put("tenantId", "");

    // Act
    boolean actualIsRetainedResult =
        eventSubscriptionsByNameMatcher.isRetained(eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)} with
   * {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Event Type}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean EventSubscriptionsByNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_givenEventType() {
    // Arrange
    EventSubscriptionsByNameMatcher eventSubscriptionsByNameMatcher =
        new EventSubscriptionsByNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventType()).thenReturn("Event Type");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("eventType", "Parameter");
    objectObjectMap.put("eventName", "Parameter");
    objectObjectMap.put("tenantId", "");

    // Act
    boolean actualIsRetainedResult =
        eventSubscriptionsByNameMatcher.isRetained(eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)} with
   * {@code EventSubscriptionEntity}, {@code Object}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()} {@code tenantId} is {@code eventType}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionsByNameMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean EventSubscriptionsByNameMatcher.isRetained(EventSubscriptionEntity, Object)"
  })
  public void testIsRetainedWithEventSubscriptionEntityObject_whenHashMapTenantIdIsEventType() {
    // Arrange
    EventSubscriptionsByNameMatcher eventSubscriptionsByNameMatcher =
        new EventSubscriptionsByNameMatcher();

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getTenantId()).thenReturn("");
    when(eventSubscriptionEntity.getEventName()).thenReturn("Parameter");
    when(eventSubscriptionEntity.getEventType()).thenReturn("Parameter");

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("eventType", "Parameter");
    objectObjectMap.put("eventName", "Parameter");
    objectObjectMap.put("tenantId", "eventType");

    // Act
    boolean actualIsRetainedResult =
        eventSubscriptionsByNameMatcher.isRetained(eventSubscriptionEntity, objectObjectMap);

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventName();
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    verify(eventSubscriptionEntity, atLeast(1)).getTenantId();
    assertFalse(actualIsRetainedResult);
  }
}
