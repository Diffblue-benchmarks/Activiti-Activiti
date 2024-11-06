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
   * Test {@link EventSubscriptionQueryImpl#eventSubscriptionId(String)}.
   * <ul>
   *   <li>Then {@link EventSubscriptionQueryImpl} EventSubscriptionId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubscriptionQueryImpl#eventSubscriptionId(String)}
   */
  @Test
  public void testEventSubscriptionId_thenEventSubscriptionQueryImplEventSubscriptionIdIs42() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualEventSubscriptionIdResult = eventSubscriptionQueryImpl.eventSubscriptionId("42");

    // Assert
    assertEquals("42", eventSubscriptionQueryImpl.getEventSubscriptionId());
    assertSame(eventSubscriptionQueryImpl, actualEventSubscriptionIdResult);
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#eventSubscriptionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubscriptionQueryImpl#eventSubscriptionId(String)}
   */
  @Test
  public void testEventSubscriptionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.eventSubscriptionId(null));
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#eventName(String)}.
   * <ul>
   *   <li>When {@code Event Name}.</li>
   *   <li>Then {@link EventSubscriptionQueryImpl} EventName is
   * {@code Event Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionQueryImpl#eventName(String)}
   */
  @Test
  public void testEventName_whenEventName_thenEventSubscriptionQueryImplEventNameIsEventName() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualEventNameResult = eventSubscriptionQueryImpl.eventName("Event Name");

    // Assert
    assertEquals("Event Name", eventSubscriptionQueryImpl.getEventName());
    assertSame(eventSubscriptionQueryImpl, actualEventNameResult);
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#eventName(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionQueryImpl#eventName(String)}
   */
  @Test
  public void testEventName_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.eventName(null));
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link EventSubscriptionQueryImpl} ExecutionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId_when42_thenEventSubscriptionQueryImplExecutionIdIs42() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualExecutionIdResult = eventSubscriptionQueryImpl.executionId("42");

    // Assert
    assertEquals("42", eventSubscriptionQueryImpl.getExecutionId());
    assertSame(eventSubscriptionQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.executionId(null));
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link EventSubscriptionQueryImpl} ProcessInstanceId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubscriptionQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId_when42_thenEventSubscriptionQueryImplProcessInstanceIdIs42() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualProcessInstanceIdResult = eventSubscriptionQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", eventSubscriptionQueryImpl.getProcessInstanceId());
    assertSame(eventSubscriptionQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubscriptionQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.processInstanceId(null));
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#activityId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link EventSubscriptionQueryImpl} ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionQueryImpl#activityId(String)}
   */
  @Test
  public void testActivityId_when42_thenEventSubscriptionQueryImplActivityIdIs42() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualActivityIdResult = eventSubscriptionQueryImpl.activityId("42");

    // Assert
    assertEquals("42", eventSubscriptionQueryImpl.getActivityId());
    assertSame(eventSubscriptionQueryImpl, actualActivityIdResult);
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#activityId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionQueryImpl#activityId(String)}
   */
  @Test
  public void testActivityId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.activityId(null));
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#eventType(String)}.
   * <ul>
   *   <li>When {@code Event Type}.</li>
   *   <li>Then {@link EventSubscriptionQueryImpl} EventType is
   * {@code Event Type}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionQueryImpl#eventType(String)}
   */
  @Test
  public void testEventType_whenEventType_thenEventSubscriptionQueryImplEventTypeIsEventType() {
    // Arrange and Act
    EventSubscriptionQueryImpl actualEventTypeResult = eventSubscriptionQueryImpl.eventType("Event Type");

    // Assert
    assertEquals("Event Type", eventSubscriptionQueryImpl.getEventType());
    assertSame(eventSubscriptionQueryImpl, actualEventTypeResult);
  }

  /**
   * Test {@link EventSubscriptionQueryImpl#eventType(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionQueryImpl#eventType(String)}
   */
  @Test
  public void testEventType_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> eventSubscriptionQueryImpl.eventType(null));
  }
}
