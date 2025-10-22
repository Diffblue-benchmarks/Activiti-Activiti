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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.api.process.model.events.StartMessageDeployedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.runtime.api.event.impl.StartMessageSubscriptionConverter;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.activiti.spring.StartMessageDeployedEventProducer.DispatchStartMessageDeployedEvents;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class StartMessageDeployedEventProducerDiffblueTest {
  @Mock
  private APIProcessDefinitionConverter aPIProcessDefinitionConverter;

  @Mock
  private ManagementService managementService;

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private StartMessageSubscriptionConverter startMessageSubscriptionConverter;

  @InjectMocks
  private StartMessageDeployedEventProducer startMessageDeployedEventProducer;

  /**
   * Test DispatchStartMessageDeployedEvents {@link DispatchStartMessageDeployedEvents#execute(CommandContext)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DispatchStartMessageDeployedEvents#execute(CommandContext)}
   */
  @Test
  @DisplayName("Test DispatchStartMessageDeployedEvents execute(CommandContext); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Void DispatchStartMessageDeployedEvents.execute(CommandContext)"})
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
   * Test {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void StartMessageDeployedEventProducer.<init>(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"})
  void testNewStartMessageDeployedEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<StartMessageDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducer = new StartMessageDeployedEventProducer(
        repositoryService, managementService, startMessageSubscriptionConverter, aPIProcessDefinitionConverter,
        listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducer.isRunning());
    assertTrue(actualStartMessageDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducer.getPhase());
  }

  /**
   * Test {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void StartMessageDeployedEventProducer.<init>(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"})
  void testNewStartMessageDeployedEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<StartMessageDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducer = new StartMessageDeployedEventProducer(
        repositoryService, managementService, startMessageSubscriptionConverter, aPIProcessDefinitionConverter,
        listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducer.isRunning());
    assertTrue(actualStartMessageDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducer.getPhase());
  }

  /**
   * Test {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageDeployedEventProducer#StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void StartMessageDeployedEventProducer.<init>(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"})
  void testNewStartMessageDeployedEventProducer_whenArrayList() {
    // Arrange and Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducer = new StartMessageDeployedEventProducer(
        repositoryService, managementService, startMessageSubscriptionConverter, aPIProcessDefinitionConverter,
        new ArrayList<>(), mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducer.isRunning());
    assertTrue(actualStartMessageDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducer.getPhase());
  }

  /**
   * Test {@link StartMessageDeployedEventProducer#doStart()}.
   * <ul>
   *   <li>Then calls {@link ManagementService#executeCommand(Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageDeployedEventProducer#doStart()}
   */
  @Test
  @DisplayName("Test doStart(); then calls executeCommand(Command)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartMessageDeployedEventProducer.doStart()"})
  void testDoStart_thenCallsExecuteCommand() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(managementService.executeCommand(Mockito.<Command<Void>>any())).thenReturn(null);
    when(aPIProcessDefinitionConverter.from(Mockito.<Collection<ProcessDefinition>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    startMessageDeployedEventProducer.doStart();

    // Assert
    verify(managementService).executeCommand(isA(Command.class));
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).list();
    verify(aPIProcessDefinitionConverter).from(isA(Collection.class));
  }
}
