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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
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
import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
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
import org.activiti.runtime.api.impl.RuntimeReceiveMessagePayloadEventListener;
import org.activiti.runtime.api.impl.RuntimeSignalPayloadEventListener;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterGroupConverter;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterUserConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.activiti.runtime.api.model.impl.ToActivityConverter;
import org.activiti.runtime.api.model.impl.ToSignalConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessRuntimeAutoConfigurationDiffblueTest {
  /**
   * Test {@link ProcessRuntimeAutoConfiguration#signalPayloadEventListener(RuntimeService)}.
   *
   * <ul>
   *   <li>Then return {@link RuntimeSignalPayloadEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#signalPayloadEventListener(RuntimeService)}
   */
  @Test
  @DisplayName(
      "Test signalPayloadEventListener(RuntimeService); then return RuntimeSignalPayloadEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.runtime.api.signal.SignalPayloadEventListener ProcessRuntimeAutoConfiguration.signalPayloadEventListener(RuntimeService)"
  })
  void testSignalPayloadEventListener_thenReturnRuntimeSignalPayloadEventListener() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    // Act and Assert
    assertTrue(
        processRuntimeAutoConfiguration.signalPayloadEventListener(new RuntimeServiceImpl())
            instanceof RuntimeSignalPayloadEventListener);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#receiveMessagePayloadEventListener(RuntimeService,
   * ManagementService)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#receiveMessagePayloadEventListener(RuntimeService,
   * ManagementService)}
   */
  @Test
  @DisplayName("Test receiveMessagePayloadEventListener(RuntimeService, ManagementService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.runtime.api.message.ReceiveMessagePayloadEventListener ProcessRuntimeAutoConfiguration.receiveMessagePayloadEventListener(RuntimeService, ManagementService)"
  })
  void testReceiveMessagePayloadEventListener() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();

    // Act and Assert
    assertTrue(
        processRuntimeAutoConfiguration.receiveMessagePayloadEventListener(
                runtimeService, new ManagementServiceImpl())
            instanceof RuntimeReceiveMessagePayloadEventListener);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List,
   * List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"
  })
  void testProcessRuntimeConfiguration_givenProcessRuntimeEventListener() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act and Assert
    assertTrue(
        processRuntimeAutoConfiguration.processRuntimeConfiguration(
                processRuntimeEventListeners, new ArrayList<>())
            instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List,
   * List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"
  })
  void testProcessRuntimeConfiguration_givenProcessRuntimeEventListener2() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act and Assert
    assertTrue(
        processRuntimeAutoConfiguration.processRuntimeConfiguration(
                processRuntimeEventListeners, new ArrayList<>())
            instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>Given {@link VariableEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List,
   * List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); given VariableEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"
  })
  void testProcessRuntimeConfiguration_givenVariableEventListener() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act and Assert
    assertTrue(
        processRuntimeAutoConfiguration.processRuntimeConfiguration(
                processRuntimeEventListeners, variableEventListeners)
            instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>Given {@link VariableEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List,
   * List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); given VariableEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"
  })
  void testProcessRuntimeConfiguration_givenVariableEventListener2() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act and Assert
    assertTrue(
        processRuntimeAutoConfiguration.processRuntimeConfiguration(
                processRuntimeEventListeners, variableEventListeners)
            instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List,
   * List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"
  })
  void testProcessRuntimeConfiguration_whenArrayList() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertTrue(
        processRuntimeAutoConfiguration.processRuntimeConfiguration(
                processRuntimeEventListeners, new ArrayList<>())
            instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeAutoConfiguration#processRuntimeConfiguration(List,
   * List)}
   */
  @Test
  @DisplayName("Test processRuntimeConfiguration(List, List); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration ProcessRuntimeAutoConfiguration.processRuntimeConfiguration(List, List)"
  })
  void testProcessRuntimeConfiguration_whenNull() {
    // Arrange, Act and Assert
    assertTrue(
        new ProcessRuntimeAutoConfiguration().processRuntimeConfiguration(null, null)
            instanceof ProcessRuntimeConfigurationImpl);
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessStartedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessStartedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)"
  })
  void testRegisterProcessStartedEventListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessStartedEvent>> listeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessStartedEventListenerDelegate(
        runtimeService,
        listeners,
        new ToAPIProcessStartedEventConverter(new APIProcessInstanceConverter()));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessStartedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessStartedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)"
  })
  void testRegisterProcessStartedEventListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessStartedEvent>> listeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessStartedEventListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessStartedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessStartedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessStartedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)"
  })
  void testRegisterProcessStartedEventListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessStartedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessStartedEventListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessStartedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessStartedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessStartedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessStartedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessStartedEventListenerDelegate(RuntimeService, List, ToAPIProcessStartedEventConverter)"
  })
  void testRegisterProcessStartedEventListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessStartedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessStartedEventListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessStartedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessCreatedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessCreatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)"
  })
  void testRegisterProcessCreatedEventListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCreatedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessCreatedEventListenerDelegate(
        runtimeService,
        eventListeners,
        new ToAPIProcessCreatedEventConverter(new APIProcessInstanceConverter()));

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessCreatedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessCreatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)"
  })
  void testRegisterProcessCreatedEventListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCreatedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCreatedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToAPIProcessCreatedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessCreatedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessCreatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)"
  })
  void testRegisterProcessCreatedEventListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCreatedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCreatedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToAPIProcessCreatedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessCreatedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCreatedEventListenerDelegate(RuntimeService,
   * List, ToAPIProcessCreatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCreatedEventListenerDelegate(RuntimeService, List, ToAPIProcessCreatedEventConverter)"
  })
  void testRegisterProcessCreatedEventListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCreatedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCreatedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToAPIProcessCreatedEventConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService,
   * List, ToProcessUpdatedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService,
   * List, ToProcessUpdatedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)"
  })
  void testRegisterProcessUpdatedEventListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessUpdatedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessUpdatedEventListenerDelegate(
        runtimeService,
        eventListeners,
        new ToProcessUpdatedConverter(new APIProcessInstanceConverter()));

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService,
   * List, ToProcessUpdatedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService,
   * List, ToProcessUpdatedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)"
  })
  void testRegisterProcessUpdatedEventListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessUpdatedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessUpdatedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessUpdatedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService,
   * List, ToProcessUpdatedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService,
   * List, ToProcessUpdatedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)"
  })
  void testRegisterProcessUpdatedEventListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessUpdatedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessUpdatedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessUpdatedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService,
   * List, ToProcessUpdatedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessUpdatedEventListenerDelegate(RuntimeService,
   * List, ToProcessUpdatedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessUpdatedEventListenerDelegate(RuntimeService, List, ToProcessUpdatedConverter)"
  })
  void testRegisterProcessUpdatedEventListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessUpdatedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessUpdatedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessUpdatedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService,
   * List, ToProcessSuspendedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService,
   * List, ToProcessSuspendedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)"
  })
  void testRegisterProcessSuspendedEventListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessSuspendedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessSuspendedEventListenerDelegate(
        runtimeService,
        eventListeners,
        new ToProcessSuspendedConverter(new APIProcessInstanceConverter()));

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService,
   * List, ToProcessSuspendedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService,
   * List, ToProcessSuspendedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)"
  })
  void testRegisterProcessSuspendedEventListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessSuspendedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessSuspendedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessSuspendedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService,
   * List, ToProcessSuspendedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService,
   * List, ToProcessSuspendedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)"
  })
  void testRegisterProcessSuspendedEventListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessSuspendedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessSuspendedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessSuspendedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService,
   * List, ToProcessSuspendedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessSuspendedEventListenerDelegate(RuntimeService,
   * List, ToProcessSuspendedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessSuspendedEventListenerDelegate(RuntimeService, List, ToProcessSuspendedConverter)"
  })
  void testRegisterProcessSuspendedEventListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessSuspendedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessSuspendedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessSuspendedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService,
   * List, ToProcessResumedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService,
   * List, ToProcessResumedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)"
  })
  void testRegisterProcessResumedEventListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessResumedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessResumedEventListenerDelegate(
        runtimeService,
        eventListeners,
        new ToProcessResumedConverter(new APIProcessInstanceConverter()));

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService,
   * List, ToProcessResumedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService,
   * List, ToProcessResumedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)"
  })
  void testRegisterProcessResumedEventListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessResumedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessResumedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessResumedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService,
   * List, ToProcessResumedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService,
   * List, ToProcessResumedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)"
  })
  void testRegisterProcessResumedEventListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessResumedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessResumedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessResumedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService,
   * List, ToProcessResumedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessResumedEventListenerDelegate(RuntimeService,
   * List, ToProcessResumedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessResumedEventListenerDelegate(RuntimeService, List, ToProcessResumedConverter)"
  })
  void testRegisterProcessResumedEventListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessResumedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessResumedEventListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessResumedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List,
   * ToProcessCompletedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List,
   * ToProcessCompletedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)"
  })
  void testRegisterProcessCompletedListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessCompletedListenerDelegate(
        runtimeService,
        eventListeners,
        new ToProcessCompletedConverter(new APIProcessInstanceConverter()));

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List,
   * ToProcessCompletedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List,
   * ToProcessCompletedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)"
  })
  void testRegisterProcessCompletedListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCompletedListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List,
   * ToProcessCompletedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List,
   * ToProcessCompletedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)"
  })
  void testRegisterProcessCompletedListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCompletedListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List,
   * ToProcessCompletedConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCompletedListenerDelegate(RuntimeService, List,
   * ToProcessCompletedConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCompletedListenerDelegate(RuntimeService, List, ToProcessCompletedConverter)"
  })
  void testRegisterProcessCompletedListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCompletedListenerDelegate(
            runtimeService,
            eventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService,
   * APIProcessInstanceConverter, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService,
   * APIProcessInstanceConverter, List)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)"
  })
  void testRegisterProcessCancelledListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();
    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessCancelledListenerDelegate(
        runtimeService, processInstanceConverter, eventListeners);

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService,
   * APIProcessInstanceConverter, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService,
   * APIProcessInstanceConverter, List)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)"
  })
  void testRegisterProcessCancelledListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();
    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCancelledListenerDelegate(
            runtimeService, processInstanceConverter, eventListeners)
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService,
   * APIProcessInstanceConverter, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService,
   * APIProcessInstanceConverter, List)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)"
  })
  void testRegisterProcessCancelledListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCancelledListenerDelegate(
            runtimeService, processInstanceConverter, eventListeners)
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService,
   * APIProcessInstanceConverter, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCancelledListenerDelegate(RuntimeService,
   * APIProcessInstanceConverter, List)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCancelledListenerDelegate(RuntimeService, APIProcessInstanceConverter, List)"
  })
  void testRegisterProcessCancelledListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCancelledListenerDelegate(
            runtimeService, processInstanceConverter, eventListeners)
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#bpmnTimerConveter()}.
   *
   * <p>Method under test: {@link ProcessRuntimeAutoConfiguration#bpmnTimerConveter()}
   */
  @Test
  @DisplayName("Test bpmnTimerConveter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BPMNTimerConverter ProcessRuntimeAutoConfiguration.bpmnTimerConveter()"})
  void testBpmnTimerConveter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertFalse(
        new ProcessRuntimeAutoConfiguration().bpmnTimerConveter().isTimerRelatedEvent(null));
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityStartedListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNActivityStartedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerActivityStartedListenerDelegate(
        runtimeService, eventListeners, new ToActivityConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityStartedListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNActivityStartedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerActivityStartedListenerDelegate(
            runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityStartedListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityStartedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityStartedListenerDelegate(
            runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityStartedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityStartedListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityStartedListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityStartedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityStartedListenerDelegate(
            runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityCompletedListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNActivityCompletedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerActivityCompletedListenerDelegate(
        runtimeService, eventListeners, new ToActivityConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityCompletedListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNActivityCompletedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCompletedListenerDelegate(
            runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityCompletedListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityCompletedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCompletedListenerDelegate(
            runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCompletedListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCompletedListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityCompletedListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityCompletedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCompletedListenerDelegate(
            runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityCancelledListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNActivityCancelledEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerActivityCancelledListenerDelegate(
        runtimeService, eventListeners, new ToActivityConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityCancelledListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNActivityCancelledEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCancelledListenerDelegate(
            runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityCancelledListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityCancelledEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCancelledListenerDelegate(
            runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivityCancelledListenerDelegate(RuntimeService, List,
   * ToActivityConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivityCancelledListenerDelegate(RuntimeService, List, ToActivityConverter)"
  })
  void testRegisterActivityCancelledListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNActivityCancelledEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivityCancelledListenerDelegate(
            runtimeService, eventListeners, new ToActivityConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List,
   * ToSignalConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List,
   * ToSignalConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)"
  })
  void testRegisterActivitySignaledListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNSignalReceivedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerActivitySignaledListenerDelegate(
        runtimeService, eventListeners, new ToSignalConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List,
   * ToSignalConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List,
   * ToSignalConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)"
  })
  void testRegisterActivitySignaledListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNSignalReceivedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerActivitySignaledListenerDelegate(
            runtimeService, eventListeners, new ToSignalConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List,
   * ToSignalConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List,
   * ToSignalConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)"
  })
  void testRegisterActivitySignaledListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNSignalReceivedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivitySignaledListenerDelegate(
            runtimeService, eventListeners, new ToSignalConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List,
   * ToSignalConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerActivitySignaledListenerDelegate(RuntimeService, List,
   * ToSignalConverter)}
   */
  @Test
  @DisplayName(
      "Test registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerActivitySignaledListenerDelegate(RuntimeService, List, ToSignalConverter)"
  })
  void testRegisterActivitySignaledListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNSignalReceivedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerActivitySignaledListenerDelegate(
            runtimeService, eventListeners, new ToSignalConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerFiredListenerDelegate_thenArrayListEmpty() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerFiredEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFiredListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerFiredListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerFiredEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFiredListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerFiredListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerFiredEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFiredListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>When {@link RuntimeServiceImpl} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerFiredListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter); when RuntimeServiceImpl (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFiredListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerFiredListenerDelegate_whenRuntimeServiceImpl_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNTimerFiredEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerTimerFiredListenerDelegate(
        runtimeService, eventListeners, new BPMNTimerConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerScheduledListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerTimerScheduledListenerDelegate(
        runtimeService, eventListeners, new BPMNTimerConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerScheduledListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerScheduledListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerScheduledListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerScheduledListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerScheduledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerScheduledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerScheduledListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerScheduledListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerCancelledListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNTimerCancelledEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerTimerCancelledListenerDelegate(
        runtimeService, eventListeners, new BPMNTimerConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerCancelledListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerCancelledEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerCancelledListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerCancelledListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerCancelledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerCancelledListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerCancelledListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerCancelledListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerCancelledListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerCancelledEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerCancelledListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerFailedListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNTimerFailedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerTimerFailedListenerDelegate(
        runtimeService, eventListeners, new BPMNTimerConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerFailedListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerFailedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFailedListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerFailedListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerFailedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFailedListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerFailedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerFailedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerFailedListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerFailedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerFailedListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerExecutedListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNTimerExecutedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerTimerExecutedListenerDelegate(
        runtimeService, eventListeners, new BPMNTimerConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerExecutedListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerExecutedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerExecutedListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerExecutedListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerExecutedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerExecutedListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerExecutedListenerDelegate(RuntimeService, List,
   * BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerExecutedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerExecutedListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerExecutedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerExecutedListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerRetriesDecrementedListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNTimerRetriesDecrementedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerTimerRetriesDecrementedListenerDelegate(
        runtimeService, eventListeners, new BPMNTimerConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerRetriesDecrementedListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNTimerRetriesDecrementedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerTimerRetriesDecrementedListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerRetriesDecrementedListenerDelegate_thenArrayListSizeIsOne()
      throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerRetriesDecrementedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerRetriesDecrementedListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerTimerRetriesDecrementedListenerDelegate(RuntimeService,
   * List, BPMNTimerConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerTimerRetriesDecrementedListenerDelegate(RuntimeService, List, BPMNTimerConverter)"
  })
  void testRegisterTimerRetriesDecrementedListenerDelegate_thenArrayListSizeIsTwo()
      throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNTimerRetriesDecrementedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerTimerRetriesDecrementedListenerDelegate(
            runtimeService, eventListeners, new BPMNTimerConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService,
   * List, BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageSentListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNMessageSentEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerMessageSentListenerDelegate(
        runtimeService, eventListeners, new BPMNMessageConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService,
   * List, BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageSentListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNMessageSentEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSentListenerDelegate(
            runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService,
   * List, BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageSentListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageSentEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSentListenerDelegate(
            runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService,
   * List, BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSentListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSentListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageSentListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageSentEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSentListenerDelegate(
            runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageReceivedListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNMessageReceivedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerMessageReceivedListenerDelegate(
        runtimeService, eventListeners, new BPMNMessageConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageReceivedListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNMessageReceivedEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerMessageReceivedListenerDelegate(
            runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageReceivedListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageReceivedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageReceivedListenerDelegate(
            runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageReceivedListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageReceivedListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageReceivedListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageReceivedEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageReceivedListenerDelegate(
            runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageWaitingListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNMessageWaitingEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerMessageWaitingListenerDelegate(
        runtimeService, eventListeners, new BPMNMessageConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageWaitingListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNMessageWaitingEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerMessageWaitingListenerDelegate(
            runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageWaitingListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageWaitingEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageWaitingListenerDelegate(
            runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageWaitingListenerDelegate(RuntimeService, List,
   * BPMNMessageConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageWaitingListenerDelegate(RuntimeService, List, BPMNMessageConverter)"
  })
  void testRegisterMessageWaitingListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNMessageWaitingEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageWaitingListenerDelegate(
            runtimeService, eventListeners, new BPMNMessageConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService,
   * List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService,
   * List)}
   */
  @Test
  @DisplayName(
      "Test registerSequenceFlowTakenListenerDelegate(RuntimeService, List); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(RuntimeService, List)"
  })
  void testRegisterSequenceFlowTakenListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNSequenceFlowTakenEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(
        runtimeService, eventListeners);

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService,
   * List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService,
   * List)}
   */
  @Test
  @DisplayName(
      "Test registerSequenceFlowTakenListenerDelegate(RuntimeService, List); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(RuntimeService, List)"
  })
  void testRegisterSequenceFlowTakenListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNSequenceFlowTakenEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerSequenceFlowTakenListenerDelegate(runtimeService, eventListeners)
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService,
   * List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService,
   * List)}
   */
  @Test
  @DisplayName(
      "Test registerSequenceFlowTakenListenerDelegate(RuntimeService, List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(RuntimeService, List)"
  })
  void testRegisterSequenceFlowTakenListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNSequenceFlowTakenEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerSequenceFlowTakenListenerDelegate(runtimeService, eventListeners)
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService,
   * List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerSequenceFlowTakenListenerDelegate(RuntimeService,
   * List)}
   */
  @Test
  @DisplayName(
      "Test registerSequenceFlowTakenListenerDelegate(RuntimeService, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerSequenceFlowTakenListenerDelegate(RuntimeService, List)"
  })
  void testRegisterSequenceFlowTakenListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNSequenceFlowTakenEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerSequenceFlowTakenListenerDelegate(runtimeService, eventListeners)
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List,
   * BPMNErrorConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List,
   * BPMNErrorConverter)}
   */
  @Test
  @DisplayName(
      "Test registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)"
  })
  void testRegisterErrorReceviedListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<BPMNElementEventListener<BPMNErrorReceivedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerErrorReceviedListenerDelegate(
        runtimeService, eventListeners, new BPMNErrorConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List,
   * BPMNErrorConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List,
   * BPMNErrorConverter)}
   */
  @Test
  @DisplayName(
      "Test registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)"
  })
  void testRegisterErrorReceviedListenerDelegate_thenArrayListEmpty2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<BPMNElementEventListener<BPMNErrorReceivedEvent>> eventListeners = new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerErrorReceviedListenerDelegate(
            runtimeService, eventListeners, new BPMNErrorConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List,
   * BPMNErrorConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List,
   * BPMNErrorConverter)}
   */
  @Test
  @DisplayName(
      "Test registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)"
  })
  void testRegisterErrorReceviedListenerDelegate_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNErrorReceivedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerErrorReceviedListenerDelegate(
            runtimeService, eventListeners, new BPMNErrorConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List,
   * BPMNErrorConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerErrorReceviedListenerDelegate(RuntimeService, List,
   * BPMNErrorConverter)}
   */
  @Test
  @DisplayName(
      "Test registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerErrorReceviedListenerDelegate(RuntimeService, List, BPMNErrorConverter)"
  })
  void testRegisterErrorReceviedListenerDelegate_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<BPMNElementEventListener<BPMNErrorReceivedEvent>> eventListeners = new ArrayList<>();
    eventListeners.add(mock(BPMNElementEventListener.class));
    eventListeners.add(mock(BPMNElementEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerErrorReceviedListenerDelegate(
            runtimeService, eventListeners, new BPMNErrorConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService,
   * List, MessageSubscriptionConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService,
   * List, MessageSubscriptionConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)"
  })
  void testRegisterMessageSubscriptionCancelledListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerMessageSubscriptionCancelledListenerDelegate(
        runtimeService, eventListeners, new MessageSubscriptionConverter());

    // Assert that nothing has changed
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService,
   * List, MessageSubscriptionConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService,
   * List, MessageSubscriptionConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)"
  })
  void testRegisterMessageSubscriptionCancelledListenerDelegate_thenArrayListEmpty2()
      throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> eventListeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSubscriptionCancelledListenerDelegate(
            runtimeService, eventListeners, new MessageSubscriptionConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(eventListeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService,
   * List, MessageSubscriptionConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService,
   * List, MessageSubscriptionConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)"
  })
  void testRegisterMessageSubscriptionCancelledListenerDelegate_thenArrayListSizeIsOne()
      throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSubscriptionCancelledListenerDelegate(
            runtimeService, eventListeners, new MessageSubscriptionConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService,
   * List, MessageSubscriptionConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerMessageSubscriptionCancelledListenerDelegate(RuntimeService,
   * List, MessageSubscriptionConverter)}
   */
  @Test
  @DisplayName(
      "Test registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerMessageSubscriptionCancelledListenerDelegate(RuntimeService, List, MessageSubscriptionConverter)"
  })
  void testRegisterMessageSubscriptionCancelledListenerDelegate_thenArrayListSizeIsTwo()
      throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<MessageSubscriptionCancelledEvent>> eventListeners =
        new ArrayList<>();
    eventListeners.add(mock(ProcessRuntimeEventListener.class));
    eventListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerMessageSubscriptionCancelledListenerDelegate(
            runtimeService, eventListeners, new MessageSubscriptionConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, eventListeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserAddedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)"
  })
  void testRegisterProcessCandidateStarterUserAddedListenerDelegate() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserAddedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterUserAddedEventConverter(
                new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserAddedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)"
  })
  void testRegisterProcessCandidateStarterUserAddedListenerDelegate2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserAddedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterUserAddedEventConverter(
                new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)"
  })
  void testRegisterProcessCandidateStarterUserAddedListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessCandidateStarterUserAddedListenerDelegate(
        runtimeService,
        listeners,
        new ToAPIProcessCandidateStarterUserAddedEventConverter(
            new APIProcessCandidateStarterUserConverter()));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserAddedEventConverter)"
  })
  void testRegisterProcessCandidateStarterUserAddedListenerDelegate_thenArrayListEmpty2()
      throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserAddedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterUserAddedEventConverter(
                new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)}
   */
  @Test
  @DisplayName(
      "Test processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ToAPIProcessCandidateStarterUserAddedEventConverter ProcessRuntimeAutoConfiguration.processCandidateStarterUserAddedEventConverter(APIProcessCandidateStarterUserConverter)"
  })
  void testProcessCandidateStarterUserAddedEventConverter() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    // Act
    ToAPIProcessCandidateStarterUserAddedEventConverter
        actualProcessCandidateStarterUserAddedEventConverterResult =
            processRuntimeAutoConfiguration.processCandidateStarterUserAddedEventConverter(
                new APIProcessCandidateStarterUserConverter());

    // Assert
    assertFalse(
        actualProcessCandidateStarterUserAddedEventConverterResult
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)"
  })
  void testRegisterProcessCandidateStarterGroupAddedListenerDelegate() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupAddedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterGroupAddedEventConverter(
                new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)"
  })
  void testRegisterProcessCandidateStarterGroupAddedListenerDelegate2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupAddedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterGroupAddedEventConverter(
                new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)"
  })
  void testRegisterProcessCandidateStarterGroupAddedListenerDelegate_thenArrayListEmpty() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>> listeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessCandidateStarterGroupAddedListenerDelegate(
        runtimeService,
        listeners,
        new ToAPIProcessCandidateStarterGroupAddedEventConverter(
            new APIProcessCandidateStarterGroupConverter()));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupAddedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupAddedEventConverter)"
  })
  void testRegisterProcessCandidateStarterGroupAddedListenerDelegate_thenArrayListEmpty2()
      throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>> listeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupAddedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterGroupAddedEventConverter(
                new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName(
      "Test processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ToAPIProcessCandidateStarterGroupAddedEventConverter ProcessRuntimeAutoConfiguration.processCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)"
  })
  void testProcessCandidateStarterGroupAddedEventConverter() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    // Act
    ToAPIProcessCandidateStarterGroupAddedEventConverter
        actualProcessCandidateStarterGroupAddedEventConverterResult =
            processRuntimeAutoConfiguration.processCandidateStarterGroupAddedEventConverter(
                new APIProcessCandidateStarterGroupConverter());

    // Assert
    assertFalse(
        actualProcessCandidateStarterGroupAddedEventConverterResult
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)"
  })
  void testRegisterProcessCandidateStarterUserRemovedListenerDelegate() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserRemovedEvent>> listeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessCandidateStarterUserRemovedListenerDelegate(
        runtimeService,
        listeners,
        new ToAPIProcessCandidateStarterUserRemovedEventConverter(
            new APIProcessCandidateStarterUserConverter()));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)"
  })
  void testRegisterProcessCandidateStarterUserRemovedListenerDelegate2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserRemovedEvent>> listeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserRemovedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterUserRemovedEventConverter(
                new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)"
  })
  void testRegisterProcessCandidateStarterUserRemovedListenerDelegate3() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserRemovedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserRemovedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterUserRemovedEventConverter(
                new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterUserRemovedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterUserRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterUserRemovedEventConverter)"
  })
  void testRegisterProcessCandidateStarterUserRemovedListenerDelegate4() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserRemovedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterUserRemovedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterUserRemovedEventConverter(
                new APIProcessCandidateStarterUserConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}
   */
  @Test
  @DisplayName(
      "Test processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ToAPIProcessCandidateStarterUserRemovedEventConverter ProcessRuntimeAutoConfiguration.processCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)"
  })
  void testProcessCandidateStarterUserRemovedEventConverter() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    // Act
    ToAPIProcessCandidateStarterUserRemovedEventConverter
        actualProcessCandidateStarterUserRemovedEventConverterResult =
            processRuntimeAutoConfiguration.processCandidateStarterUserRemovedEventConverter(
                new APIProcessCandidateStarterUserConverter());

    // Assert
    assertFalse(
        actualProcessCandidateStarterUserRemovedEventConverterResult
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)"
  })
  void testRegisterProcessCandidateStarterGroupRemovedListenerDelegate() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupRemovedEvent>> listeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration.registerProcessCandidateStarterGroupRemovedListenerDelegate(
        runtimeService,
        listeners,
        new ToAPIProcessCandidateStarterGroupRemovedEventConverter(
            new APIProcessCandidateStarterGroupConverter()));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)"
  })
  void testRegisterProcessCandidateStarterGroupRemovedListenerDelegate2() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupRemovedEvent>> listeners =
        new ArrayList<>();

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupRemovedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterGroupRemovedEventConverter(
                new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)"
  })
  void testRegisterProcessCandidateStarterGroupRemovedListenerDelegate3() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupRemovedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupRemovedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterGroupRemovedEventConverter(
                new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService,
   * List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean ProcessRuntimeAutoConfiguration.registerProcessCandidateStarterGroupRemovedListenerDelegate(RuntimeService, List, ToAPIProcessCandidateStarterGroupRemovedEventConverter)"
  })
  void testRegisterProcessCandidateStarterGroupRemovedListenerDelegate4() throws Exception {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupRemovedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    processRuntimeAutoConfiguration
        .registerProcessCandidateStarterGroupRemovedListenerDelegate(
            runtimeService,
            listeners,
            new ToAPIProcessCandidateStarterGroupRemovedEventConverter(
                new APIProcessCandidateStarterGroupConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link
   * ProcessRuntimeAutoConfiguration#processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeAutoConfiguration#processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName(
      "Test processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ToAPIProcessCandidateStarterGroupRemovedEventConverter ProcessRuntimeAutoConfiguration.processCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)"
  })
  void testProcessCandidateStarterGroupRemovedEventConverter() {
    // Arrange
    ProcessRuntimeAutoConfiguration processRuntimeAutoConfiguration =
        new ProcessRuntimeAutoConfiguration();

    // Act
    ToAPIProcessCandidateStarterGroupRemovedEventConverter
        actualProcessCandidateStarterGroupRemovedEventConverterResult =
            processRuntimeAutoConfiguration.processCandidateStarterGroupRemovedEventConverter(
                new APIProcessCandidateStarterGroupConverter());

    // Assert
    assertFalse(
        actualProcessCandidateStarterGroupRemovedEventConverterResult
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }
}
