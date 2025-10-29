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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventSubscriptionQueryImplDiffblueTest {
  @InjectMocks
  private EventSubscriptionQueryImpl eventSubscriptionQueryImpl;

  /**
   * Method under test:
   * {@link EventSubscriptionQueryImpl#eventSubscriptionId(String)}
   */
  @Test
  public void testEventSubscriptionId() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualEventSubscriptionIdResult = eventSubscriptionQueryImpl.eventSubscriptionId("42");

    // Assert
    assertEquals("42", eventSubscriptionQueryImpl.getEventSubscriptionId());
    assertSame(eventSubscriptionQueryImpl, actualEventSubscriptionIdResult);
  }

  /**
   * Method under test:
   * {@link EventSubscriptionQueryImpl#eventSubscriptionId(String)}
   */
  @Test
  public void testEventSubscriptionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.eventSubscriptionId(null));
  }

  /**
   * Method under test: {@link EventSubscriptionQueryImpl#eventName(String)}
   */
  @Test
  public void testEventName() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualEventNameResult = eventSubscriptionQueryImpl.eventName("Event Name");

    // Assert
    assertEquals("Event Name", eventSubscriptionQueryImpl.getEventName());
    assertSame(eventSubscriptionQueryImpl, actualEventNameResult);
  }

  /**
   * Method under test: {@link EventSubscriptionQueryImpl#eventName(String)}
   */
  @Test
  public void testEventName2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.eventName(null));
  }

  /**
   * Method under test: {@link EventSubscriptionQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualExecutionIdResult = eventSubscriptionQueryImpl.executionId("42");

    // Assert
    assertEquals("42", eventSubscriptionQueryImpl.getExecutionId());
    assertSame(eventSubscriptionQueryImpl, actualExecutionIdResult);
  }

  /**
   * Method under test: {@link EventSubscriptionQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.executionId(null));
  }

  /**
   * Method under test:
   * {@link EventSubscriptionQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualProcessInstanceIdResult = eventSubscriptionQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", eventSubscriptionQueryImpl.getProcessInstanceId());
    assertSame(eventSubscriptionQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link EventSubscriptionQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.processInstanceId(null));
  }

  /**
   * Method under test: {@link EventSubscriptionQueryImpl#activityId(String)}
   */
  @Test
  public void testActivityId() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualActivityIdResult = eventSubscriptionQueryImpl.activityId("42");

    // Assert
    assertEquals("42", eventSubscriptionQueryImpl.getActivityId());
    assertSame(eventSubscriptionQueryImpl, actualActivityIdResult);
  }

  /**
   * Method under test: {@link EventSubscriptionQueryImpl#activityId(String)}
   */
  @Test
  public void testActivityId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.activityId(null));
  }

  /**
   * Method under test: {@link EventSubscriptionQueryImpl#eventType(String)}
   */
  @Test
  public void testEventType() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualEventTypeResult = eventSubscriptionQueryImpl.eventType("Event Type");

    // Assert
    assertEquals("Event Type", eventSubscriptionQueryImpl.getEventType());
    assertSame(eventSubscriptionQueryImpl, actualEventTypeResult);
  }

  /**
   * Method under test: {@link EventSubscriptionQueryImpl#eventType(String)}
   */
  @Test
  public void testEventType2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.eventType(null));
  }
}
