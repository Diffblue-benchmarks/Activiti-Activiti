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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
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
import org.activiti.runtime.api.impl.RuntimeReceiveMessagePayloadEventListener;
import org.activiti.runtime.api.impl.RuntimeSignalPayloadEventListener;
import org.activiti.runtime.api.impl.VariableNameValidator;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterGroupConverter;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterUserConverter;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.activiti.runtime.api.model.impl.ToActivityConverter;
import org.activiti.runtime.api.model.impl.ToSignalConverter;
import org.activiti.spring.process.ProcessExtensionResourceReader;
import org.activiti.spring.process.ProcessExtensionService;
import org.activiti.spring.process.model.ProcessExtensionModel;
import org.activiti.spring.process.variable.VariableParsingService;
import org.activiti.spring.process.variable.VariableValidationService;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class ProcessRuntimeAutoConfigurationDiffblueTest {
  @InjectMocks
  private ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration;

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#signalPayloadEventListener(RuntimeService)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#signalPayloadEventListener(RuntimeService)}
   */
  @Test
  @DisplayName("Test signalPayloadEventListener(RuntimeService)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.runtime.api.signal.SignalPayloadEventListener ProcessRuntimeAutoConfiguration.signalPayloadEventListener(RuntimeService)"})
  void testSignalPayloadEventListener() {
    // Arrange, Act and Assert
    assertTrue(processRuntimeAutoConfiguration
        .signalPayloadEventListener(new RuntimeServiceImpl()) instanceof RuntimeSignalPayloadEventListener);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#receiveMessagePayloadEventListener(RuntimeService, ManagementService)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#receiveMessagePayloadEventListener(RuntimeService, ManagementService)}
   */
  @Test
  @DisplayName("Test receiveMessagePayloadEventListener(RuntimeService, ManagementService)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.runtime.api.message.ReceiveMessagePayloadEventListener ProcessRuntimeAutoConfiguration.receiveMessagePayloadEventListener(RuntimeService, ManagementService)"})
  void testReceiveMessagePayloadEventListener() {
    // Arrange
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();

    // Act and Assert
    assertTrue(processRuntimeAutoConfiguration.receiveMessagePayloadEventListener(runtimeService,
        new ManagementServiceImpl()) instanceof RuntimeReceiveMessagePayloadEventListener);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#eventSubscriptionPayloadMappingProvider(ExtensionsVariablesMappingProvider)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#eventSubscriptionPayloadMappingProvider(ExtensionsVariablesMappingProvider)}
   */
  @Test
  @DisplayName("Test eventSubscriptionPayloadMappingProvider(ExtensionsVariablesMappingProvider)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.engine.impl.event.EventSubscriptionPayloadMappingProvider ProcessRuntimeAutoConfiguration.eventSubscriptionPayloadMappingProvider(ExtensionsVariablesMappingProvider)"})
  void testEventSubscriptionPayloadMappingProvider() {
    // Arrange
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = new DeploymentResourceLoader<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager,
        JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class));

    // Act and Assert
    assertTrue(processRuntimeAutoConfiguration.eventSubscriptionPayloadMappingProvider(
        new ExtensionsVariablesMappingProvider(processExtensionService, expressionResolver,
            new VariableParsingService(new HashMap<>()))) instanceof EventSubscriptionVariablesMappingProvider);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processAdminRuntime(RepositoryService, APIProcessDefinitionConverter, RuntimeService, APIProcessInstanceConverter, ApplicationEventPublisher, ProcessVariablesPayloadValidator, APIVariableInstanceConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processAdminRuntime(RepositoryService, APIProcessDefinitionConverter, RuntimeService, APIProcessInstanceConverter, ApplicationEventPublisher, ProcessVariablesPayloadValidator, APIVariableInstanceConverter)}
   */
  @Test
  @DisplayName("Test processAdminRuntime(RepositoryService, APIProcessDefinitionConverter, RuntimeService, APIProcessInstanceConverter, ApplicationEventPublisher, ProcessVariablesPayloadValidator, APIVariableInstanceConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.process.runtime.ProcessAdminRuntime ProcessRuntimeAutoConfiguration.processAdminRuntime(RepositoryService, APIProcessDefinitionConverter, RuntimeService, APIProcessInstanceConverter, ApplicationEventPublisher, ProcessVariablesPayloadValidator, APIVariableInstanceConverter)"})
  void testProcessAdminRuntime() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter processDefinitionConverter = new APIProcessDefinitionConverter(
        new RepositoryServiceImpl());
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();
    ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = new DeploymentResourceLoader<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    VariableNameValidator variableNameValidator = new VariableNameValidator();
    ExpressionManager expressionManager = new ExpressionManager();
    ProcessVariablesPayloadValidator processVariablesValidator = new ProcessVariablesPayloadValidator(
        dateFormatterProvider, processExtensionService, variableValidationService, variableNameValidator,
        new ExpressionResolver(expressionManager, JsonMapper.builder().findAndAddModules().build(),
            mock(DelegateInterceptor.class)));

    // Act and Assert
    assertTrue(processRuntimeAutoConfiguration.processAdminRuntime(repositoryService, processDefinitionConverter,
        runtimeService, processInstanceConverter, eventPublisher, processVariablesValidator,
        new APIVariableInstanceConverter()) instanceof ProcessAdminRuntimeImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"})
  void testProcessRuntimeConfiguration_givenProcessRuntimeEventListener() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act and Assert
    assertTrue(processRuntimeAutoConfiguration.processRuntimeConfiguration(processRuntimeEventListeners,
        new ArrayList<>()) instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"})
  void testProcessRuntimeConfiguration_givenProcessRuntimeEventListener2() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act and Assert
    assertTrue(processRuntimeAutoConfiguration.processRuntimeConfiguration(processRuntimeEventListeners,
        new ArrayList<>()) instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Given {@link VariableEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); given VariableEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"})
  void testProcessRuntimeConfiguration_givenVariableEventListener() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act and Assert
    assertTrue(processRuntimeAutoConfiguration.processRuntimeConfiguration(processRuntimeEventListeners,
        variableEventListeners) instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Given {@link VariableEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); given VariableEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"})
  void testProcessRuntimeConfiguration_givenVariableEventListener2() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act and Assert
    assertTrue(processRuntimeAutoConfiguration.processRuntimeConfiguration(processRuntimeEventListeners,
        variableEventListeners) instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"})
  void testProcessRuntimeConfiguration_whenArrayList() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertTrue(processRuntimeAutoConfiguration.processRuntimeConfiguration(processRuntimeEventListeners,
        new ArrayList<>()) instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"})
  void testProcessRuntimeConfiguration_whenNull() {
    // Arrange, Act and Assert
    assertTrue(processRuntimeAutoConfiguration.processRuntimeConfiguration(null,
        null) instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)"})
  void testRegisterProcessStartedEventListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)"})
  void testRegisterProcessStartedEventListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)"})
  void testRegisterProcessStartedEventListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)"})
  void testRegisterProcessCreatedEventListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)"})
  void testRegisterProcessCreatedEventListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)"})
  void testRegisterProcessCreatedEventListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)"})
  void testRegisterProcessUpdatedEventListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)"})
  void testRegisterProcessUpdatedEventListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)"})
  void testRegisterProcessUpdatedEventListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)"})
  void testRegisterProcessSuspendedEventListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)"})
  void testRegisterProcessSuspendedEventListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)"})
  void testRegisterProcessSuspendedEventListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)"})
  void testRegisterProcessResumedEventListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)"})
  void testRegisterProcessResumedEventListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)"})
  void testRegisterProcessResumedEventListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)"})
  void testRegisterProcessCompletedListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)"})
  void testRegisterProcessCompletedListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)"})
  void testRegisterProcessCompletedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}
   */
  @Test
  @DisplayName("Test registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)"})
  void testRegisterProcessCancelledListenerDelegate_givenProcessRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}
   */
  @Test
  @DisplayName("Test registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)"})
  void testRegisterProcessCancelledListenerDelegate_givenProcessRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)}
   */
  @Test
  @DisplayName("Test registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)"})
  void testRegisterProcessCancelledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Method under test: {@link ProcessRuntimeAutoConfiguration#bpmnTimerConveter()}
   */
  @Test
  @DisplayName("Test bpmnTimerConveter()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BPMNTimerConverter ProcessRuntimeAutoConfiguration.bpmnTimerConveter()"})
  void testBpmnTimerConveter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertFalse((new ProcessRuntimeAutoConfiguration()).bpmnTimerConveter().isTimerRelatedEvent(null));
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)"})
  void testRegisterActivityStartedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)"})
  void testRegisterActivityStartedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)"})
  void testRegisterActivityStartedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)"})
  void testRegisterActivityCompletedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)"})
  void testRegisterActivityCompletedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)"})
  void testRegisterActivityCompletedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)"})
  void testRegisterActivityCancelledListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)"})
  void testRegisterActivityCancelledListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)}
   */
  @Test
  @DisplayName("Test registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)"})
  void testRegisterActivityCancelledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}
   */
  @Test
  @DisplayName("Test registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)"})
  void testRegisterActivitySignaledListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}
   */
  @Test
  @DisplayName("Test registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)"})
  void testRegisterActivitySignaledListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)}
   */
  @Test
  @DisplayName("Test registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)"})
  void testRegisterActivitySignaledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerFiredListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerFiredListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link RuntimeServiceImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList(); then calls addEventListener(ActivitiEventListener, ActivitiEventType[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerFiredListenerDelegate_whenArrayList_thenCallsAddEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerScheduledListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerScheduledListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerScheduledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerCancelledListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerCancelledListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerCancelledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerFailedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerFailedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link RuntimeServiceImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList(); then calls addEventListener(ActivitiEventListener, ActivitiEventType[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerFailedListenerDelegate_whenArrayList_thenCallsAddEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerExecutedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerExecutedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerExecutedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerRetriesDecrementedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerRetriesDecrementedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName("Test registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"})
  void testRegisterTimerRetriesDecrementedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)"})
  void testRegisterMessageSentListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)"})
  void testRegisterMessageSentListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link RuntimeServiceImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter); when ArrayList(); then calls addEventListener(ActivitiEventListener, ActivitiEventType[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)"})
  void testRegisterMessageSentListenerDelegate_whenArrayList_thenCallsAddEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)"})
  void testRegisterMessageReceivedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)"})
  void testRegisterMessageReceivedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)"})
  void testRegisterMessageReceivedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)"})
  void testRegisterMessageWaitingListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)"})
  void testRegisterMessageWaitingListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)}
   */
  @Test
  @DisplayName("Test registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)"})
  void testRegisterMessageWaitingListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}
   */
  @Test
  @DisplayName("Test registerSequenceFlowTakenListenerDelegate(RuntimeService, List); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(RuntimeService, List)"})
  void testRegisterSequenceFlowTakenListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}
   */
  @Test
  @DisplayName("Test registerSequenceFlowTakenListenerDelegate(RuntimeService, List); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(RuntimeService, List)"})
  void testRegisterSequenceFlowTakenListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService, List)}
   */
  @Test
  @DisplayName("Test registerSequenceFlowTakenListenerDelegate(RuntimeService, List); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(RuntimeService, List)"})
  void testRegisterSequenceFlowTakenListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}
   */
  @Test
  @DisplayName("Test registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)"})
  void testRegisterErrorReceviedListenerDelegate_givenBPMNElementEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}
   */
  @Test
  @DisplayName("Test registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter); given BPMNElementEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)"})
  void testRegisterErrorReceviedListenerDelegate_givenBPMNElementEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)}
   */
  @Test
  @DisplayName("Test registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)"})
  void testRegisterErrorReceviedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)"})
  void testRegisterMessageSubscriptionCancelledListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)"})
  void testRegisterMessageSubscriptionCancelledListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)}
   */
  @Test
  @DisplayName("Test registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)"})
  void testRegisterMessageSubscriptionCancelledListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)"})
  void testRegisterProcessCandidateStarterUserAddedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)"})
  void testRegisterProcessCandidateStarterUserAddedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)"})
  void testRegisterProcessCandidateStarterUserAddedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ToAPIProcessCandidateStarterUserAddedEventConverter ProcessRuntimeAutoConfiguration.processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)"})
  void testProcessCandidateStarterUserAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)"})
  void testRegisterProcessCandidateStarterGroupAddedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)"})
  void testRegisterProcessCandidateStarterGroupAddedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)"})
  void testRegisterProcessCandidateStarterGroupAddedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ToAPIProcessCandidateStarterGroupAddedEventConverter ProcessRuntimeAutoConfiguration.processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)"})
  void testProcessCandidateStarterGroupAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)"})
  void testRegisterProcessCandidateStarterUserRemovedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)"})
  void testRegisterProcessCandidateStarterUserRemovedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)"})
  void testRegisterProcessCandidateStarterUserRemovedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ToAPIProcessCandidateStarterUserRemovedEventConverter ProcessRuntimeAutoConfiguration.processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)"})
  void testProcessCandidateStarterUserRemovedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)"})
  void testRegisterProcessCandidateStarterGroupRemovedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)"})
  void testRegisterProcessCandidateStarterGroupRemovedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   */
  @Test
  @DisplayName("Test registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)"})
  void testRegisterProcessCandidateStarterGroupRemovedListenerDelegate_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   * <p>
   * Method under test: {@link ProcessRuntimeAutoConfiguration#processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName("Test processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ToAPIProcessCandidateStarterGroupRemovedEventConverter ProcessRuntimeAutoConfiguration.processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)"})
  void testProcessCandidateStarterGroupRemovedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
}
