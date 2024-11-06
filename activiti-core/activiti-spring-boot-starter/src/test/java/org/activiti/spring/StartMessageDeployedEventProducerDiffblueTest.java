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
package org.activiti.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.events.StartMessageDeployedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.runtime.api.event.impl.StartMessageSubscriptionConverter;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.activiti.spring.StartMessageDeployedEventProducer.DispatchStartMessageDeployedEvents;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class StartMessageDeployedEventProducerDiffblueTest {
  /**
   * Test DispatchStartMessageDeployedEvents
   * {@link DispatchStartMessageDeployedEvents#execute(CommandContext)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEventProducer.DispatchStartMessageDeployedEvents#execute(CommandContext)}
   */
  @Test
  @DisplayName("Test DispatchStartMessageDeployedEvents execute(CommandContext); when 'null'; then return 'null'")
  void testDispatchStartMessageDeployedEventsExecute_whenNull_thenReturnNull() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    StartMessageSubscriptionConverter subscriptionConverter = new StartMessageSubscriptionConverter();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());
    StartMessageDeployedEventProducer startMessageDeployedEventProducer = new StartMessageDeployedEventProducer(
        repositoryService, managementService, subscriptionConverter, converter, new ArrayList<>(),
        mock(ApplicationEventPublisher.class));

    // Act and Assert
    assertNull(
        (startMessageDeployedEventProducer.new DispatchStartMessageDeployedEvents(new ArrayList<>())).execute(null));
  }

  /**
   * Test
   * {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  void testNewStartMessageDeployedEventProducer_givenProcessRuntimeEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    StartMessageSubscriptionConverter subscriptionConverter = new StartMessageSubscriptionConverter();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<StartMessageDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducer = new StartMessageDeployedEventProducer(
        repositoryService, managementService, subscriptionConverter, converter, listeners,
        mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducer.isRunning());
    assertTrue(actualStartMessageDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducer.getPhase());
  }

  /**
   * Test
   * {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  void testNewStartMessageDeployedEventProducer_givenProcessRuntimeEventListener2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    StartMessageSubscriptionConverter subscriptionConverter = new StartMessageSubscriptionConverter();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<StartMessageDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducer = new StartMessageDeployedEventProducer(
        repositoryService, managementService, subscriptionConverter, converter, listeners,
        mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducer.isRunning());
    assertTrue(actualStartMessageDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducer.getPhase());
  }

  /**
   * Test
   * {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); when ArrayList()")
  void testNewStartMessageDeployedEventProducer_whenArrayList() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    StartMessageSubscriptionConverter subscriptionConverter = new StartMessageSubscriptionConverter();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducer = new StartMessageDeployedEventProducer(
        repositoryService, managementService, subscriptionConverter, converter, new ArrayList<>(),
        mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducer.isRunning());
    assertTrue(actualStartMessageDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducer.getPhase());
  }
}
