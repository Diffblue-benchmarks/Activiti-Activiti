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
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class EventSubscriptionsByExecutionIdMatcherDiffblueTest {
  /**
   * Test
   * {@link EventSubscriptionsByExecutionIdMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   * <p>
   * Method under test:
   * {@link EventSubscriptionsByExecutionIdMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithEventSubscriptionEntityObject() {
    // Arrange
    EventSubscriptionsByExecutionIdMatcher eventSubscriptionsByExecutionIdMatcher = new EventSubscriptionsByExecutionIdMatcher();
    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getExecutionId()).thenReturn(null);

    // Act
    boolean actualIsRetainedResult = eventSubscriptionsByExecutionIdMatcher.isRetained(eventSubscriptionEntity,
        JSONObject.NULL);

    // Assert
    verify(eventSubscriptionEntity).getExecutionId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test
   * {@link EventSubscriptionsByExecutionIdMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubscriptionsByExecutionIdMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithEventSubscriptionEntityObject_given42_when42_thenReturnTrue() {
    // Arrange
    EventSubscriptionsByExecutionIdMatcher eventSubscriptionsByExecutionIdMatcher = new EventSubscriptionsByExecutionIdMatcher();
    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = eventSubscriptionsByExecutionIdMatcher.isRetained(eventSubscriptionEntity, "42");

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getExecutionId();
    assertTrue(actualIsRetainedResult);
  }

  /**
   * Test
   * {@link EventSubscriptionsByExecutionIdMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   * <ul>
   *   <li>When {@code Parameter}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubscriptionsByExecutionIdMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithEventSubscriptionEntityObject_whenParameter_thenReturnFalse() {
    // Arrange
    EventSubscriptionsByExecutionIdMatcher eventSubscriptionsByExecutionIdMatcher = new EventSubscriptionsByExecutionIdMatcher();
    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = eventSubscriptionsByExecutionIdMatcher.isRetained(eventSubscriptionEntity,
        "Parameter");

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getExecutionId();
    assertFalse(actualIsRetainedResult);
  }
}
