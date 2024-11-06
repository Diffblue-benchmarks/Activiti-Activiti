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
package org.activiti.runtime.api.conf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.events.BPMNActivityCancelledEvent;
import org.activiti.api.process.model.events.BPMNActivityCompletedEvent;
import org.activiti.api.process.model.events.BPMNActivityStartedEvent;
import org.activiti.api.process.model.events.BPMNErrorReceivedEvent;
import org.activiti.api.process.model.events.BPMNMessageReceivedEvent;
import org.activiti.api.process.model.events.BPMNMessageSentEvent;
import org.activiti.api.process.model.events.BPMNMessageWaitingEvent;
import org.activiti.api.process.model.events.BPMNSequenceFlowTakenEvent;
import org.activiti.api.process.model.events.BPMNSignalReceivedEvent;
import org.activiti.api.process.model.events.BPMNTimerCancelledEvent;
import org.activiti.api.process.model.events.BPMNTimerExecutedEvent;
import org.activiti.api.process.model.events.BPMNTimerFailedEvent;
import org.activiti.api.process.model.events.BPMNTimerFiredEvent;
import org.activiti.api.process.model.events.BPMNTimerRetriesDecrementedEvent;
import org.activiti.api.process.model.events.BPMNTimerScheduledEvent;
import org.activiti.api.process.model.events.MessageSubscriptionCancelledEvent;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration;
import org.activiti.api.process.runtime.events.ProcessCancelledEvent;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterGroupAddedEvent;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterGroupRemovedEvent;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterUserAddedEvent;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterUserRemovedEvent;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.ProcessCreatedEvent;
import org.activiti.api.process.runtime.events.ProcessResumedEvent;
import org.activiti.api.process.runtime.events.ProcessStartedEvent;
import org.activiti.api.process.runtime.events.ProcessSuspendedEvent;
import org.activiti.api.process.runtime.events.ProcessUpdatedEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.runtime.api.conf.impl.ProcessRuntimeConfigurationImpl;
import org.activiti.runtime.api.event.impl.BPMNErrorConverter;
import org.activiti.runtime.api.event.impl.BPMNMessageConverter;
import org.activiti.runtime.api.event.impl.BPMNTimerConverter;
import org.activiti.runtime.api.event.impl.MessageSubscriptionConverter;
import org.activiti.runtime.api.event.impl.ProcessCompletedImpl;
import org.activiti.runtime.api.event.impl.ProcessCreatedEventImpl;
import org.activiti.runtime.api.event.impl.ProcessResumedEventImpl;
import org.activiti.runtime.api.event.impl.ProcessStartedEventImpl;
import org.activiti.runtime.api.event.impl.ProcessSuspendedEventImpl;
import org.activiti.runtime.api.event.impl.ProcessUpdatedEventImpl;
import org.activiti.runtime.api.event.impl.ToAPIProcessCandidateStarterGroupAddedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPIProcessCandidateStarterGroupRemovedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPIProcessCandidateStarterUserAddedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPIProcessCandidateStarterUserRemovedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPIProcessCreatedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPIProcessStartedEventConverter;
import org.activiti.runtime.api.event.impl.ToProcessCompletedConverter;
import org.activiti.runtime.api.event.impl.ToProcessResumedConverter;
import org.activiti.runtime.api.event.impl.ToProcessSuspendedConverter;
import org.activiti.runtime.api.event.impl.ToProcessUpdatedConverter;
import org.activiti.runtime.api.impl.EventSubscriptionVariablesMappingProvider;
import org.activiti.runtime.api.impl.ExpressionResolver;
import org.activiti.runtime.api.impl.ExtensionsVariablesMappingProvider;
import org.activiti.runtime.api.impl.ProcessAdminRuntimeImpl;
import org.activiti.runtime.api.impl.ProcessVariablesPayloadValidator;
import org.activiti.runtime.api.impl.RuntimeSignalPayloadEventListener;
import org.activiti.runtime.api.impl.VariableNameValidator;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterGroupConverter;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterUserConverter;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.activiti.runtime.api.model.impl.ToActivityConverter;
import org.activiti.runtime.api.model.impl.ToSignalConverter;
import org.activiti.runtime.api.signal.SignalPayloadEventListener;
import org.activiti.spring.process.ProcessExtensionResourceReader;
import org.activiti.spring.process.ProcessExtensionService;
import org.activiti.spring.process.model.ProcessExtensionModel;
import org.activiti.spring.process.variable.VariableParsingService;
import org.activiti.spring.process.variable.VariableValidationService;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

class ProcessRuntimeAutoConfigurationDiffblueTest {
  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#signalPayloadEventListener(RuntimeService)}.
   * <ul>
   *   <li>Then return {@link RuntimeSignalPayloadEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#signalPayloadEventListener(RuntimeService)}
   */
  @Test
  @DisplayName("Test signalPayloadEventListener(RuntimeService); then return RuntimeSignalPayloadEventListener")
  void testSignalPayloadEventListener_thenReturnRuntimeSignalPayloadEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService).signalEventReceived(Mockito.<String>any(), Mockito.<Map<String, Object>>any());

    // Act
    SignalPayloadEventListener actualSignalPayloadEventListenerResult = processRuntimeAutoConfiguration
        .signalPayloadEventListener(runtimeService);
    actualSignalPayloadEventListenerResult.sendSignal(new SignalPayload());

    // Assert that nothing has changed
    verify(runtimeService).signalEventReceived((String) isNull(), isA(Map.class));
    assertTrue(actualSignalPayloadEventListenerResult instanceof RuntimeSignalPayloadEventListener);
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#eventSubscriptionPayloadMappingProvider(ExtensionsVariablesMappingProvider)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#eventSubscriptionPayloadMappingProvider(ExtensionsVariablesMappingProvider)}
   */
  @Test
  @DisplayName("Test eventSubscriptionPayloadMappingProvider(ExtensionsVariablesMappingProvider)")
  void testEventSubscriptionPayloadMappingProvider() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = new DeploymentResourceLoader<>();
    ObjectMapper objectMapper = new ObjectMapper();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager, new ObjectMapper(),
        mock(DelegateInterceptor.class));

    // Act and Assert
    assertTrue(processRuntimeAutoConfiguration.eventSubscriptionPayloadMappingProvider(
        new ExtensionsVariablesMappingProvider(processExtensionService, expressionResolver,
            new VariableParsingService(new HashMap<>()))) instanceof EventSubscriptionVariablesMappingProvider);
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processAdminRuntime(RepositoryService, APIProcessDefinitionConverter, RuntimeService, APIProcessInstanceConverter, ApplicationEventPublisher, ProcessVariablesPayloadValidator, APIVariableInstanceConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processAdminRuntime(RepositoryService, APIProcessDefinitionConverter, RuntimeService, APIProcessInstanceConverter, ApplicationEventPublisher, ProcessVariablesPayloadValidator, APIVariableInstanceConverter)}
   */
  @Test
  @DisplayName("Test processAdminRuntime(RepositoryService, APIProcessDefinitionConverter, RuntimeService, APIProcessInstanceConverter, ApplicationEventPublisher, ProcessVariablesPayloadValidator, APIVariableInstanceConverter)")
  void testProcessAdminRuntime() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter processDefinitionConverter = new APIProcessDefinitionConverter(
        new RepositoryServiceImpl());
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();
    ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = new DeploymentResourceLoader<>();
    ObjectMapper objectMapper = new ObjectMapper();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    VariableNameValidator variableNameValidator = new VariableNameValidator();
    ExpressionManager expressionManager = new ExpressionManager();
    ProcessVariablesPayloadValidator processVariablesValidator = new ProcessVariablesPayloadValidator(
        dateFormatterProvider, processExtensionService, variableValidationService, variableNameValidator,
        new ExpressionResolver(expressionManager, new ObjectMapper(), mock(DelegateInterceptor.class)));

    // Act and Assert
    assertTrue(processRuntimeAutoConfiguration.processAdminRuntime(repositoryService, processDefinitionConverter,
        runtimeService, processInstanceConverter, eventPublisher, processVariablesValidator,
        new APIVariableInstanceConverter()) instanceof ProcessAdminRuntimeImpl);
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Then return processEventListeners Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); then return processEventListeners Empty")
  void testProcessRuntimeConfiguration_thenReturnProcessEventListenersEmpty() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();

    // Act
    ProcessRuntimeConfiguration actualProcessRuntimeConfigurationResult = processRuntimeAutoConfiguration
        .processRuntimeConfiguration(processRuntimeEventListeners, new ArrayList<>());

    // Assert
    assertTrue(actualProcessRuntimeConfigurationResult instanceof ProcessRuntimeConfigurationImpl);
    assertTrue(actualProcessRuntimeConfigurationResult.processEventListeners().isEmpty());
    assertTrue(actualProcessRuntimeConfigurationResult.variableEventListeners().isEmpty());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Then return processEventListeners is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); then return processEventListeners is ArrayList()")
  void testProcessRuntimeConfiguration_thenReturnProcessEventListenersIsArrayList() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();

    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessRuntimeConfiguration actualProcessRuntimeConfigurationResult = processRuntimeAutoConfiguration
        .processRuntimeConfiguration(processRuntimeEventListeners, new ArrayList<>());

    // Assert
    assertTrue(actualProcessRuntimeConfigurationResult instanceof ProcessRuntimeConfigurationImpl);
    assertTrue(actualProcessRuntimeConfigurationResult.variableEventListeners().isEmpty());
    assertEquals(processRuntimeEventListeners, actualProcessRuntimeConfigurationResult.processEventListeners());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Then return processEventListeners is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); then return processEventListeners is ArrayList()")
  void testProcessRuntimeConfiguration_thenReturnProcessEventListenersIsArrayList2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();

    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessRuntimeConfiguration actualProcessRuntimeConfigurationResult = processRuntimeAutoConfiguration
        .processRuntimeConfiguration(processRuntimeEventListeners, new ArrayList<>());

    // Assert
    assertTrue(actualProcessRuntimeConfigurationResult instanceof ProcessRuntimeConfigurationImpl);
    assertTrue(actualProcessRuntimeConfigurationResult.variableEventListeners().isEmpty());
    assertEquals(processRuntimeEventListeners, actualProcessRuntimeConfigurationResult.processEventListeners());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Then return variableEventListeners is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); then return variableEventListeners is ArrayList()")
  void testProcessRuntimeConfiguration_thenReturnVariableEventListenersIsArrayList() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act
    ProcessRuntimeConfiguration actualProcessRuntimeConfigurationResult = processRuntimeAutoConfiguration
        .processRuntimeConfiguration(processRuntimeEventListeners, variableEventListeners);

    // Assert
    assertTrue(actualProcessRuntimeConfigurationResult instanceof ProcessRuntimeConfigurationImpl);
    assertTrue(actualProcessRuntimeConfigurationResult.processEventListeners().isEmpty());
    assertEquals(variableEventListeners, actualProcessRuntimeConfigurationResult.variableEventListeners());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Then return variableEventListeners is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); then return variableEventListeners is ArrayList()")
  void testProcessRuntimeConfiguration_thenReturnVariableEventListenersIsArrayList2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act
    ProcessRuntimeConfiguration actualProcessRuntimeConfigurationResult = processRuntimeAutoConfiguration
        .processRuntimeConfiguration(processRuntimeEventListeners, variableEventListeners);

    // Assert
    assertTrue(actualProcessRuntimeConfigurationResult instanceof ProcessRuntimeConfigurationImpl);
    assertTrue(actualProcessRuntimeConfigurationResult.processEventListeners().isEmpty());
    assertEquals(variableEventListeners, actualProcessRuntimeConfigurationResult.variableEventListeners());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return processEventListeners Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); when 'null'; then return processEventListeners Empty")
  void testProcessRuntimeConfiguration_whenNull_thenReturnProcessEventListenersEmpty() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ProcessRuntimeConfiguration actualProcessRuntimeConfigurationResult = (new ProcessRuntimeAutoConfiguration())
        .processRuntimeConfiguration(null, null);

    // Assert
    assertTrue(actualProcessRuntimeConfigurationResult instanceof ProcessRuntimeConfigurationImpl);
    assertTrue(actualProcessRuntimeConfigurationResult.processEventListeners().isEmpty());
    assertTrue(actualProcessRuntimeConfigurationResult.variableEventListeners().isEmpty());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#apiProcessStartedEventConverter(APIProcessInstanceConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#apiProcessStartedEventConverter(APIProcessInstanceConverter)}
   */
  @Test
  @DisplayName("Test apiProcessStartedEventConverter(APIProcessInstanceConverter)")
  void testApiProcessStartedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();

    // Act
    ToAPIProcessStartedEventConverter actualApiProcessStartedEventConverterResult = processRuntimeAutoConfiguration
        .apiProcessStartedEventConverter(new APIProcessInstanceConverter());
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.isProcessInstanceType()).thenReturn(true);
    Optional<ProcessStartedEvent> actualFromResult = actualApiProcessStartedEventConverterResult
        .from(new ActivitiProcessStartedEventImpl(executionEntityImpl, new HashMap<>(), true));

    // Assert
    verify(executionEntityImpl).getProcessInstance();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).isProcessInstanceType();
    ProcessStartedEvent getResult = actualFromResult.get();
    ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessStartedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(entity.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(entity.getAppVersion());
    assertNull(entity.getBusinessKey());
    assertNull(entity.getId());
    assertNull(entity.getInitiator());
    assertNull(entity.getName());
    assertNull(entity.getParentId());
    assertNull(entity.getProcessDefinitionId());
    assertNull(entity.getProcessDefinitionKey());
    assertNull(entity.getProcessDefinitionName());
    assertNull(getResult.getNestedProcessDefinitionId());
    assertNull(getResult.getNestedProcessInstanceId());
    assertNull(entity.getCompletedDate());
    assertNull(entity.getStartDate());
    assertEquals(ProcessInstance.ProcessInstanceStatus.CREATED, entity.getStatus());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_STARTED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#apiProcessCreatedEventConverter(APIProcessInstanceConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#apiProcessCreatedEventConverter(APIProcessInstanceConverter)}
   */
  @Test
  @DisplayName("Test apiProcessCreatedEventConverter(APIProcessInstanceConverter)")
  void testApiProcessCreatedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    APIProcessInstanceConverter processInstanceConverter = mock(APIProcessInstanceConverter.class);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(processInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    ToAPIProcessCreatedEventConverter actualApiProcessCreatedEventConverterResult = processRuntimeAutoConfiguration
        .apiProcessCreatedEventConverter(processInstanceConverter);
    Optional<ProcessCreatedEvent> actualFromResult = actualApiProcessCreatedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(processInstanceConverter).from((org.activiti.engine.runtime.ProcessInstance) isNull());
    ProcessCreatedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessCreatedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_CREATED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processInstanceImpl, getResult.getEntity());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processUpdatedConverter(APIProcessInstanceConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processUpdatedConverter(APIProcessInstanceConverter)}
   */
  @Test
  @DisplayName("Test processUpdatedConverter(APIProcessInstanceConverter)")
  void testProcessUpdatedConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    APIProcessInstanceConverter processInstanceConverter = mock(APIProcessInstanceConverter.class);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(processInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    ToProcessUpdatedConverter actualProcessUpdatedConverterResult = processRuntimeAutoConfiguration
        .processUpdatedConverter(processInstanceConverter);
    Optional<ProcessUpdatedEvent> actualFromResult = actualProcessUpdatedConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(processInstanceConverter).from((org.activiti.engine.runtime.ProcessInstance) isNull());
    ProcessUpdatedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessUpdatedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_UPDATED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processInstanceImpl, getResult.getEntity());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processResumedConverter(APIProcessInstanceConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processResumedConverter(APIProcessInstanceConverter)}
   */
  @Test
  @DisplayName("Test processResumedConverter(APIProcessInstanceConverter)")
  void testProcessResumedConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    APIProcessInstanceConverter processInstanceConverter = mock(APIProcessInstanceConverter.class);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(processInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    ToProcessResumedConverter actualProcessResumedConverterResult = processRuntimeAutoConfiguration
        .processResumedConverter(processInstanceConverter);
    Optional<ProcessResumedEvent> actualFromResult = actualProcessResumedConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(processInstanceConverter).from((org.activiti.engine.runtime.ProcessInstance) isNull());
    ProcessResumedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessResumedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_RESUMED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processInstanceImpl, getResult.getEntity());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processSuspendedConverter(APIProcessInstanceConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processSuspendedConverter(APIProcessInstanceConverter)}
   */
  @Test
  @DisplayName("Test processSuspendedConverter(APIProcessInstanceConverter)")
  void testProcessSuspendedConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    APIProcessInstanceConverter processInstanceConverter = mock(APIProcessInstanceConverter.class);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(processInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    ToProcessSuspendedConverter actualProcessSuspendedConverterResult = processRuntimeAutoConfiguration
        .processSuspendedConverter(processInstanceConverter);
    Optional<ProcessSuspendedEvent> actualFromResult = actualProcessSuspendedConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(processInstanceConverter).from((org.activiti.engine.runtime.ProcessInstance) isNull());
    ProcessSuspendedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessSuspendedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_SUSPENDED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processInstanceImpl, getResult.getEntity());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter); given ProcessRuntimeEventListener")
  void testRegisterProcessStartedEventListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessStartedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessStartedEventListenerDelegate(runtimeService, listeners,
            new ToAPIProcessStartedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter); given ProcessRuntimeEventListener")
  void testRegisterProcessStartedEventListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessStartedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessStartedEventListenerDelegate(runtimeService, listeners,
            new ToAPIProcessStartedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter); when ArrayList()")
  void testRegisterProcessStartedEventListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessStartedEvent>> listeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessStartedEventListenerDelegate(runtimeService, listeners,
            new ToAPIProcessStartedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter); given ProcessRuntimeEventListener")
  void testRegisterProcessCreatedEventListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCreatedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCreatedEventListenerDelegate(runtimeService, eventListeners,
            new ToAPIProcessCreatedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter); given ProcessRuntimeEventListener")
  void testRegisterProcessCreatedEventListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCreatedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCreatedEventListenerDelegate(runtimeService, eventListeners,
            new ToAPIProcessCreatedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter); when ArrayList()")
  void testRegisterProcessCreatedEventListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCreatedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCreatedEventListenerDelegate(runtimeService, eventListeners,
            new ToAPIProcessCreatedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter); given ProcessRuntimeEventListener")
  void testRegisterProcessUpdatedEventListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessUpdatedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessUpdatedEventListenerDelegate(runtimeService, eventListeners,
            new ToProcessUpdatedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter); given ProcessRuntimeEventListener")
  void testRegisterProcessUpdatedEventListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessUpdatedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessUpdatedEventListenerDelegate(runtimeService, eventListeners,
            new ToProcessUpdatedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter); when ArrayList()")
  void testRegisterProcessUpdatedEventListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessUpdatedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessUpdatedEventListenerDelegate(runtimeService, eventListeners,
            new ToProcessUpdatedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)")
  void testRegisterProcessSuspendedEventListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessSuspendedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessSuspendedEventListenerDelegate(runtimeService, eventListeners,
            new ToProcessSuspendedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)")
  void testRegisterProcessSuspendedEventListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessSuspendedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessSuspendedEventListenerDelegate(runtimeService, eventListeners,
            new ToProcessSuspendedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter); when ArrayList()")
  void testRegisterProcessSuspendedEventListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessSuspendedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessSuspendedEventListenerDelegate(runtimeService, eventListeners,
            new ToProcessSuspendedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter); given ProcessRuntimeEventListener")
  void testRegisterProcessResumedEventListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessResumedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessResumedEventListenerDelegate(runtimeService, eventListeners,
            new ToProcessResumedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter); given ProcessRuntimeEventListener")
  void testRegisterProcessResumedEventListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessResumedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessResumedEventListenerDelegate(runtimeService, eventListeners,
            new ToProcessResumedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter); when ArrayList()")
  void testRegisterProcessResumedEventListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessResumedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessResumedEventListenerDelegate(runtimeService, eventListeners,
            new ToProcessResumedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processCompletedConverter(APIProcessInstanceConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processCompletedConverter(APIProcessInstanceConverter)}
   */
  @Test
  @DisplayName("Test processCompletedConverter(APIProcessInstanceConverter)")
  void testProcessCompletedConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    APIProcessInstanceConverter processInstanceConverter = mock(APIProcessInstanceConverter.class);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(processInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    ToProcessCompletedConverter actualProcessCompletedConverterResult = processRuntimeAutoConfiguration
        .processCompletedConverter(processInstanceConverter);
    Optional<ProcessCompletedEvent> actualFromResult = actualProcessCompletedConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(processInstanceConverter).from((org.activiti.engine.runtime.ProcessInstance) isNull());
    ProcessCompletedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessCompletedImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_COMPLETED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processInstanceImpl, getResult.getEntity());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter); given ProcessRuntimeEventListener")
  void testRegisterProcessCompletedListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCompletedListenerDelegate(runtimeService, eventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter); given ProcessRuntimeEventListener")
  void testRegisterProcessCompletedListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCompletedListenerDelegate(runtimeService, eventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter); when ArrayList()")
  void testRegisterProcessCompletedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCompletedListenerDelegate(runtimeService, eventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}
   */
  @Test
  @DisplayName("Test registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List); given ProcessRuntimeEventListener")
  void testRegisterProcessCancelledListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCancelledListenerDelegate(runtimeService, processInstanceConverter, eventListeners)
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}
   */
  @Test
  @DisplayName("Test registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List); given ProcessRuntimeEventListener")
  void testRegisterProcessCancelledListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCancelledListenerDelegate(runtimeService, processInstanceConverter, eventListeners)
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}
   */
  @Test
  @DisplayName("Test registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List); when ArrayList()")
  void testRegisterProcessCancelledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCancelledListenerDelegate(runtimeService, processInstanceConverter, new ArrayList<>())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#bpmnTimerConveter()}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#bpmnTimerConveter()}
   */
  @Test
  @DisplayName("Test bpmnTimerConveter()")
  void testBpmnTimerConveter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertFalse((new ProcessRuntimeAutoConfiguration()).bpmnTimerConveter().isTimerRelatedEvent(null));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  void testRegisterActivityStartedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityStartedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityStartedListenerDelegate(runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  void testRegisterActivityStartedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityStartedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityStartedListenerDelegate(runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter); when ArrayList()")
  void testRegisterActivityStartedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNActivityStartedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerActivityStartedListenerDelegate(runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  void testRegisterActivityCompletedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityCompletedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCompletedListenerDelegate(runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  void testRegisterActivityCompletedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityCompletedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCompletedListenerDelegate(runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter); when ArrayList()")
  void testRegisterActivityCompletedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNActivityCompletedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCompletedListenerDelegate(runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  void testRegisterActivityCancelledListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityCancelledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCancelledListenerDelegate(runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  void testRegisterActivityCancelledListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityCancelledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCancelledListenerDelegate(runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter); when ArrayList()")
  void testRegisterActivityCancelledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNActivityCancelledEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCancelledListenerDelegate(runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}
   */
  @Test
  @DisplayName("Test registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter); given BPMNElementEventListener")
  void testRegisterActivitySignaledListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNSignalReceivedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivitySignaledListenerDelegate(runtimeService, eventListeners, new ToSignalConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}
   */
  @Test
  @DisplayName("Test registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter); given BPMNElementEventListener")
  void testRegisterActivitySignaledListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNSignalReceivedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivitySignaledListenerDelegate(runtimeService, eventListeners, new ToSignalConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}
   */
  @Test
  @DisplayName("Test registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter); when ArrayList()")
  void testRegisterActivitySignaledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNSignalReceivedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerActivitySignaledListenerDelegate(runtimeService, eventListeners, new ToSignalConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  void testRegisterTimerFiredListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerFiredEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFiredListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  void testRegisterTimerFiredListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerFiredEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFiredListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls
   * {@link RuntimeServiceImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList(); then calls addEventListener(ActivitiEventListener, ActivitiEventType[])")
  void testRegisterTimerFiredListenerDelegate_whenArrayList_thenCallsAddEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerFiredEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFiredListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  void testRegisterTimerScheduledListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerScheduledListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  void testRegisterTimerScheduledListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerScheduledListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList()")
  void testRegisterTimerScheduledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerScheduledListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  void testRegisterTimerCancelledListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerCancelledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerCancelledListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  void testRegisterTimerCancelledListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerCancelledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerCancelledListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList()")
  void testRegisterTimerCancelledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerCancelledEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerCancelledListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  void testRegisterTimerFailedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerFailedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFailedListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  void testRegisterTimerFailedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerFailedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFailedListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls
   * {@link RuntimeServiceImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList(); then calls addEventListener(ActivitiEventListener, ActivitiEventType[])")
  void testRegisterTimerFailedListenerDelegate_whenArrayList_thenCallsAddEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerFailedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFailedListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  void testRegisterTimerExecutedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerExecutedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerExecutedListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  void testRegisterTimerExecutedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerExecutedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerExecutedListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList()")
  void testRegisterTimerExecutedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerExecutedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerExecutedListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)")
  void testRegisterTimerRetriesDecrementedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerRetriesDecrementedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerRetriesDecrementedListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)")
  void testRegisterTimerRetriesDecrementedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerRetriesDecrementedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerRetriesDecrementedListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList()")
  void testRegisterTimerRetriesDecrementedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerRetriesDecrementedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerRetriesDecrementedListenerDelegate(runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  void testRegisterMessageSentListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageSentEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSentListenerDelegate(runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  void testRegisterMessageSentListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageSentEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSentListenerDelegate(runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls
   * {@link RuntimeServiceImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter); when ArrayList(); then calls addEventListener(ActivitiEventListener, ActivitiEventType[])")
  void testRegisterMessageSentListenerDelegate_whenArrayList_thenCallsAddEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNMessageSentEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSentListenerDelegate(runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  void testRegisterMessageReceivedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageReceivedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageReceivedListenerDelegate(runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  void testRegisterMessageReceivedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageReceivedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageReceivedListenerDelegate(runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter); when ArrayList()")
  void testRegisterMessageReceivedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNMessageReceivedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerMessageReceivedListenerDelegate(runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  void testRegisterMessageWaitingListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageWaitingEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageWaitingListenerDelegate(runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  void testRegisterMessageWaitingListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageWaitingEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageWaitingListenerDelegate(runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter); when ArrayList()")
  void testRegisterMessageWaitingListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNMessageWaitingEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerMessageWaitingListenerDelegate(runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}
   */
  @Test
  @DisplayName("Test registerSequenceFlowTakenListenerDelegate(RuntimeService, List); given BPMNElementEventListener")
  void testRegisterSequenceFlowTakenListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNSequenceFlowTakenEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(runtimeService, eventListeners)
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}
   */
  @Test
  @DisplayName("Test registerSequenceFlowTakenListenerDelegate(RuntimeService, List); given BPMNElementEventListener")
  void testRegisterSequenceFlowTakenListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNSequenceFlowTakenEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(runtimeService, eventListeners)
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}
   */
  @Test
  @DisplayName("Test registerSequenceFlowTakenListenerDelegate(RuntimeService, List); when ArrayList()")
  void testRegisterSequenceFlowTakenListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    // Act
    processRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(runtimeService, new ArrayList<>())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}
   */
  @Test
  @DisplayName("Test registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter); given BPMNElementEventListener")
  void testRegisterErrorReceviedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNErrorReceivedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerErrorReceviedListenerDelegate(runtimeService, eventListeners, new BPMNErrorConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}
   */
  @Test
  @DisplayName("Test registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter); given BPMNElementEventListener")
  void testRegisterErrorReceviedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNErrorReceivedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerErrorReceviedListenerDelegate(runtimeService, eventListeners, new BPMNErrorConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}
   */
  @Test
  @DisplayName("Test registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter); when ArrayList()")
  void testRegisterErrorReceviedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNErrorReceivedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerErrorReceviedListenerDelegate(runtimeService, eventListeners, new BPMNErrorConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)")
  void testRegisterMessageSubscriptionCancelledListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSubscriptionCancelledListenerDelegate(runtimeService, eventListeners,
            new MessageSubscriptionConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)")
  void testRegisterMessageSubscriptionCancelledListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSubscriptionCancelledListenerDelegate(runtimeService, eventListeners,
            new MessageSubscriptionConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter); when ArrayList()")
  void testRegisterMessageSubscriptionCancelledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSubscriptionCancelledListenerDelegate(runtimeService, eventListeners,
            new MessageSubscriptionConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)")
  void testRegisterProcessCandidateStarterUserAddedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserAddedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterUserAddedEventConverter(new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)")
  void testRegisterProcessCandidateStarterUserAddedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserAddedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterUserAddedEventConverter(new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter); when ArrayList()")
  void testRegisterProcessCandidateStarterUserAddedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserAddedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterUserAddedEventConverter(new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)")
  void testProcessCandidateStarterUserAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();

    // Act
    ToAPIProcessCandidateStarterUserAddedEventConverter actualProcessCandidateStarterUserAddedEventConverterResult = processRuntimeAutoConfiguration
        .processCandidateStarterUserAddedEventConverter(new APIProcessCandidateStarterUserConverter());

    // Assert
    assertFalse(actualProcessCandidateStarterUserAddedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)")
  void testProcessCandidateStarterUserAddedEventConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToAPIProcessCandidateStarterUserAddedEventConverter actualProcessCandidateStarterUserAddedEventConverterResult = (new ProcessRuntimeAutoConfiguration())
        .processCandidateStarterUserAddedEventConverter(mock(APIProcessCandidateStarterUserConverter.class));

    // Assert
    assertFalse(actualProcessCandidateStarterUserAddedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)")
  void testRegisterProcessCandidateStarterGroupAddedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupAddedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterGroupAddedEventConverter(new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)")
  void testRegisterProcessCandidateStarterGroupAddedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupAddedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterGroupAddedEventConverter(new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter); when ArrayList()")
  void testRegisterProcessCandidateStarterGroupAddedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>> listeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupAddedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterGroupAddedEventConverter(new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)")
  void testProcessCandidateStarterGroupAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();

    // Act
    ToAPIProcessCandidateStarterGroupAddedEventConverter actualProcessCandidateStarterGroupAddedEventConverterResult = processRuntimeAutoConfiguration
        .processCandidateStarterGroupAddedEventConverter(new APIProcessCandidateStarterGroupConverter());

    // Assert
    assertFalse(actualProcessCandidateStarterGroupAddedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)")
  void testProcessCandidateStarterGroupAddedEventConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToAPIProcessCandidateStarterGroupAddedEventConverter actualProcessCandidateStarterGroupAddedEventConverterResult = (new ProcessRuntimeAutoConfiguration())
        .processCandidateStarterGroupAddedEventConverter(mock(APIProcessCandidateStarterGroupConverter.class));

    // Assert
    assertFalse(actualProcessCandidateStarterGroupAddedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)")
  void testRegisterProcessCandidateStarterUserRemovedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserRemovedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterUserRemovedEventConverter(new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)")
  void testRegisterProcessCandidateStarterUserRemovedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserRemovedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterUserRemovedEventConverter(new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter); when ArrayList()")
  void testRegisterProcessCandidateStarterUserRemovedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserRemovedEvent>> listeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserRemovedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterUserRemovedEventConverter(new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)")
  void testProcessCandidateStarterUserRemovedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();

    // Act
    ToAPIProcessCandidateStarterUserRemovedEventConverter actualProcessCandidateStarterUserRemovedEventConverterResult = processRuntimeAutoConfiguration
        .processCandidateStarterUserRemovedEventConverter(new APIProcessCandidateStarterUserConverter());

    // Assert
    assertFalse(actualProcessCandidateStarterUserRemovedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)")
  void testProcessCandidateStarterUserRemovedEventConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToAPIProcessCandidateStarterUserRemovedEventConverter actualProcessCandidateStarterUserRemovedEventConverterResult = (new ProcessRuntimeAutoConfiguration())
        .processCandidateStarterUserRemovedEventConverter(mock(APIProcessCandidateStarterUserConverter.class));

    // Assert
    assertFalse(actualProcessCandidateStarterUserRemovedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)")
  void testRegisterProcessCandidateStarterGroupRemovedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupRemovedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterGroupRemovedEventConverter(new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)")
  void testRegisterProcessCandidateStarterGroupRemovedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupRemovedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterGroupRemovedEventConverter(new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter); when ArrayList()")
  void testRegisterProcessCandidateStarterGroupRemovedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupRemovedEvent>> listeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupRemovedListenerDelegate(runtimeService, listeners,
            new ToAPIProcessCandidateStarterGroupRemovedEventConverter(new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)")
  void testProcessCandidateStarterGroupRemovedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration = new ProcessRuntimeAutoConfiguration();

    // Act
    ToAPIProcessCandidateStarterGroupRemovedEventConverter actualProcessCandidateStarterGroupRemovedEventConverterResult = processRuntimeAutoConfiguration
        .processCandidateStarterGroupRemovedEventConverter(new APIProcessCandidateStarterGroupConverter());

    // Assert
    assertFalse(actualProcessCandidateStarterGroupRemovedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)")
  void testProcessCandidateStarterGroupRemovedEventConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToAPIProcessCandidateStarterGroupRemovedEventConverter actualProcessCandidateStarterGroupRemovedEventConverterResult = (new ProcessRuntimeAutoConfiguration())
        .processCandidateStarterGroupRemovedEventConverter(mock(APIProcessCandidateStarterGroupConverter.class));

    // Assert
    assertFalse(actualProcessCandidateStarterGroupRemovedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }
}
