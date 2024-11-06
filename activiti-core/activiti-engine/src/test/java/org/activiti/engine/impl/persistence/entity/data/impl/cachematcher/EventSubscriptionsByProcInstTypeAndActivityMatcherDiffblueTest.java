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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.junit.Test;

public class EventSubscriptionsByProcInstTypeAndActivityMatcherDiffblueTest {
  /**
   * Test
   * {@link EventSubscriptionsByProcInstTypeAndActivityMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code Event Type}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubscriptionsByProcInstTypeAndActivityMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithEventSubscriptionEntityObject_givenEventType() {
    // Arrange
    EventSubscriptionsByProcInstTypeAndActivityMatcher eventSubscriptionsByProcInstTypeAndActivityMatcher = new EventSubscriptionsByProcInstTypeAndActivityMatcher();
    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventType()).thenReturn("Event Type");

    // Act
    boolean actualIsRetainedResult = eventSubscriptionsByProcInstTypeAndActivityMatcher
        .isRetained(eventSubscriptionEntity, new HashMap<>());

    // Assert
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test
   * {@link EventSubscriptionsByProcInstTypeAndActivityMatcher#isRetained(EventSubscriptionEntity, Object)}
   * with {@code EventSubscriptionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubscriptionsByProcInstTypeAndActivityMatcher#isRetained(EventSubscriptionEntity, Object)}
   */
  @Test
  public void testIsRetainedWithEventSubscriptionEntityObject_givenNull() {
    // Arrange
    EventSubscriptionsByProcInstTypeAndActivityMatcher eventSubscriptionsByProcInstTypeAndActivityMatcher = new EventSubscriptionsByProcInstTypeAndActivityMatcher();
    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventType()).thenReturn(null);

    // Act
    boolean actualIsRetainedResult = eventSubscriptionsByProcInstTypeAndActivityMatcher
        .isRetained(eventSubscriptionEntity, new HashMap<>());

    // Assert
    verify(eventSubscriptionEntity).getEventType();
    assertFalse(actualIsRetainedResult);
  }
}
