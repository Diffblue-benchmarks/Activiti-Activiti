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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Map;
import org.activiti.api.process.model.payloads.ReceiveMessagePayload;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.persistence.entity.CompensateEventSubscriptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RuntimeReceiveMessagePayloadEventListener.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RuntimeReceiveMessagePayloadEventListenerDiffblueTest {
  @MockBean
  private ManagementService managementService;

  @Autowired
  private RuntimeReceiveMessagePayloadEventListener runtimeReceiveMessagePayloadEventListener;

  @MockBean
  private RuntimeService runtimeService;

  /**
   * Test
   * {@link RuntimeReceiveMessagePayloadEventListener#receiveMessage(ReceiveMessagePayload)}.
   * <ul>
   *   <li>Given {@link RuntimeService}.</li>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RuntimeReceiveMessagePayloadEventListener#receiveMessage(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test receiveMessage(ReceiveMessagePayload); given RuntimeService; then throw ActivitiObjectNotFoundException")
  void testReceiveMessage_givenRuntimeService_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(managementService.executeCommand(Mockito.<Command<EventSubscriptionEntity>>any())).thenReturn(null);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> runtimeReceiveMessagePayloadEventListener.receiveMessage(new ReceiveMessagePayload()));
    verify(managementService).executeCommand(isA(Command.class));
  }

  /**
   * Test
   * {@link RuntimeReceiveMessagePayloadEventListener#receiveMessage(ReceiveMessagePayload)}.
   * <ul>
   *   <li>Then calls
   * {@link RuntimeService#messageEventReceived(String, String, Map)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RuntimeReceiveMessagePayloadEventListener#receiveMessage(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test receiveMessage(ReceiveMessagePayload); then calls messageEventReceived(String, String, Map)")
  void testReceiveMessage_thenCallsMessageEventReceived() {
    // Arrange
    doNothing().when(runtimeService)
        .messageEventReceived(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getConfiguration()).thenReturn(null);
    when(compensateEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    when(managementService.executeCommand(Mockito.<Command<EventSubscriptionEntity>>any()))
        .thenReturn(compensateEventSubscriptionEntityImpl);

    // Act
    runtimeReceiveMessagePayloadEventListener.receiveMessage(new ReceiveMessagePayload());

    // Assert
    verify(managementService).executeCommand(isA(Command.class));
    verify(runtimeService).messageEventReceived(isNull(), eq("42"), isA(Map.class));
    verify(compensateEventSubscriptionEntityImpl).getConfiguration();
    verify(compensateEventSubscriptionEntityImpl).getExecutionId();
  }

  /**
   * Test
   * {@link RuntimeReceiveMessagePayloadEventListener#receiveMessage(ReceiveMessagePayload)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RuntimeReceiveMessagePayloadEventListener#receiveMessage(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test receiveMessage(ReceiveMessagePayload); then throw ActivitiObjectNotFoundException")
  void testReceiveMessage_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getConfiguration()).thenReturn("Configuration");
    when(managementService.executeCommand(Mockito.<Command<EventSubscriptionEntity>>any()))
        .thenReturn(compensateEventSubscriptionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> runtimeReceiveMessagePayloadEventListener.receiveMessage(new ReceiveMessagePayload()));
    verify(managementService).executeCommand(isA(Command.class));
    verify(compensateEventSubscriptionEntityImpl).getConfiguration();
  }
}
