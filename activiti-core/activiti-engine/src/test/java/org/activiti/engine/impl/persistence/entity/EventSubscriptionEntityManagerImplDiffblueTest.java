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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.Signal;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.EventSubscriptionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisEventSubscriptionDataManager;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventSubscriptionEntityManagerImplDiffblueTest {
  @Mock private EventSubscriptionDataManager eventSubscriptionDataManager;

  @InjectMocks private EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl;

  @Mock private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       EventSubscriptionEntityManagerImpl#EventSubscriptionEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       EventSubscriptionDataManager)}
   *   <li>{@link
   *       EventSubscriptionEntityManagerImpl#setEventSubscriptionDataManager(EventSubscriptionDataManager)}
   *   <li>{@link EventSubscriptionEntityManagerImpl#getDataManager()}
   *   <li>{@link EventSubscriptionEntityManagerImpl#getEventSubscriptionDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, EventSubscriptionDataManager)",
    "DataManager EventSubscriptionEntityManagerImpl.getDataManager()",
    "EventSubscriptionDataManager EventSubscriptionEntityManagerImpl.getEventSubscriptionDataManager()",
    "void EventSubscriptionEntityManagerImpl.setEventSubscriptionDataManager(EventSubscriptionDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    EventSubscriptionEntityManagerImpl actualEventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));
    MybatisEventSubscriptionDataManager eventSubscriptionDataManager =
        new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration());
    actualEventSubscriptionEntityManagerImpl.setEventSubscriptionDataManager(
        eventSubscriptionDataManager);
    DataManager<EventSubscriptionEntity> actualDataManager =
        actualEventSubscriptionEntityManagerImpl.getDataManager();

    // Assert
    assertSame(eventSubscriptionDataManager, actualDataManager);
    assertSame(
        eventSubscriptionDataManager,
        actualEventSubscriptionEntityManagerImpl.getEventSubscriptionDataManager());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#createCompensateEventSubscription()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#createCompensateEventSubscription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.createCompensateEventSubscription()"
  })
  public void testCreateCompensateEventSubscription_thenReturnNull() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.createCompensateEventSubscription()).thenReturn(null);
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    CompensateEventSubscriptionEntity actualCreateCompensateEventSubscriptionResult =
        eventSubscriptionEntityManagerImpl.createCompensateEventSubscription();

    // Assert
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
    assertNull(actualCreateCompensateEventSubscriptionResult);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#createMessageEventSubscription()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#createMessageEventSubscription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.createMessageEventSubscription()"
  })
  public void testCreateMessageEventSubscription_thenReturnNull() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(null);
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    MessageEventSubscriptionEntity actualCreateMessageEventSubscriptionResult =
        eventSubscriptionEntityManagerImpl.createMessageEventSubscription();

    // Assert
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    assertNull(actualCreateMessageEventSubscriptionResult);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#createSignalEventSubscription()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#createSignalEventSubscription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.createSignalEventSubscription()"
  })
  public void testCreateSignalEventSubscription_thenReturnNull() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(null);
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    SignalEventSubscriptionEntity actualCreateSignalEventSubscriptionResult =
        eventSubscriptionEntityManagerImpl.createSignalEventSubscription();

    // Assert
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    assertNull(actualCreateSignalEventSubscriptionResult);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent() {
    // Arrange
    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    doThrow(new ActivitiException("An error occurred"))
        .when(signalEventSubscriptionEntity)
        .setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertSignalEvent(
                "Signal Name",
                signal,
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId(null);
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(signalEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getPerformanceSettings())
        .thenThrow(new ActivitiException("An error occurred"));
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId("42");
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent4() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getEventSubscriptions()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(signalEventSubscriptionEntity).getExecution();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId("42");
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(execution).getEventSubscriptions();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent5() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecution())
        .thenThrow(new ActivitiException("An error occurred"));
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(signalEventSubscriptionEntity).getExecution();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId("42");
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent6() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount())
        .thenThrow(new ActivitiException("An error occurred"));
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(signalEventSubscriptionEntity).getExecution();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId("42");
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent7() {
    // Arrange
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenThrow(new ActivitiException("An error occurred"));
    Signal signal = new Signal();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertSignalEvent(
                "Signal Name",
                signal,
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent8() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenThrow(new ActivitiException("An error occurred"));

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);
    Signal signal = new Signal();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertSignalEvent(
                "Signal Name",
                signal,
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(signalEventSubscriptionEntity).setActivityId(null);
    verify(signalEventSubscriptionEntity).setEventName(null);
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(signalEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getEventSubscriptions()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(signalEventSubscriptionEntity).getExecution();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId("42");
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution).getEventSubscriptions();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent_givenNull_whenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn(null);
    when(execution.getEventSubscriptions()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(signalEventSubscriptionEntity).getExecution();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId("42");
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(execution).getCurrentActivityId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution).getEventSubscriptions();
    verify(execution).getProcessDefinitionId();
    verify(execution).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <ul>
   *   <li>Given {@link SignalEventSubscriptionEntity} {@link
   *       SignalEventSubscriptionEntity#getExecutionId()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent_givenSignalEventSubscriptionEntityGetExecutionIdReturnNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn(null);
    doNothing().when(signalEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getEventSubscriptions()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId("42");
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(execution).getEventSubscriptions();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getEventSubscriptions()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(signalEventSubscriptionEntity).getExecution();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId("42");
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution).getEventSubscriptions();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setEventSubscriptionCount(int)}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent_thenCallsSetEventSubscriptionCount() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope("Signal");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getEventSubscriptions()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(signalEventSubscriptionEntity).getExecution();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId("42");
    verify(signalEventSubscriptionEntity).setConfiguration("Signal");
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution).getEventSubscriptions();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getPerformanceSettings()}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent_whenNull_thenCallsGetPerformanceSettings() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", null, execution);

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntity).getExecution();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId(null);
    verify(signalEventSubscriptionEntity).setEventName("Signal Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(signalEventSubscriptionEntity).setTenantId("");
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal,
   * ExecutionEntity)}.
   *
   * <ul>
   *   <li>When {@link Signal#Signal(String, String)} with id is {@code 42} and {@code Name} Scope
   *       is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String,
   * Signal, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"
  })
  public void testInsertSignalEvent_whenSignalWithIdIs42AndNameScopeIsNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    Signal signal = new Signal("42", "Name");
    signal.setScope(null);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId(null);
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(signalEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent() {
    // Arrange
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertMessageEvent(
                "Message Name", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent2() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenThrow(new ActivitiException("An error occurred"));

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertMessageEvent(
                "Message Name", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(messageEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent3() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecutionId())
        .thenThrow(new ActivitiException("An error occurred"));
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertMessageEvent(
                "Message Name", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(messageEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent4() {
    // Arrange
    when(processEngineConfigurationImpl.getPerformanceSettings())
        .thenThrow(new ActivitiException("An error occurred"));
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertMessageEvent(
                "Message Name", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(messageEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent5() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntity).getExecution();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(messageEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent6() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecution())
        .thenThrow(new ActivitiException("An error occurred"));
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertMessageEvent(
                "Message Name", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(messageEventSubscriptionEntity).getExecution();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(messageEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent7() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(false);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(messageEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent8() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntity).getExecution();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(messageEventSubscriptionEntity).setTenantId("");
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent9() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount())
        .thenThrow(new ActivitiException("An error occurred"));
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertMessageEvent(
                "Message Name", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntity).getExecution();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(messageEventSubscriptionEntity).setTenantId("");
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent10() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl =
        mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(activitiEventDispatcherImpl);

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn(null);
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getEventSubscriptions()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId("42");
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(messageEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(execution).getEventSubscriptions();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcherImpl} {@link ActivitiEventDispatcherImpl#isEnabled()}
   *       return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent_givenActivitiEventDispatcherImplIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl =
        mock(ActivitiEventDispatcherImpl.class);
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(false);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(activitiEventDispatcherImpl);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getEventSubscriptions()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntity).getExecution();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId("42");
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(messageEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution).getEventSubscriptions();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent_givenNull_whenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl =
        mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(activitiEventDispatcherImpl);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn(null);
    when(execution.getEventSubscriptions()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntity).getExecution();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId("42");
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(execution).getCurrentActivityId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution).getEventSubscriptions();
    verify(execution).getProcessDefinitionId();
    verify(execution).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcherImpl#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String,
   * ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"
  })
  public void testInsertMessageEvent_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl =
        mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(activitiEventDispatcherImpl);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getEventSubscriptions()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntity).getExecution();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId("42");
    verify(messageEventSubscriptionEntity).setEventName("Message Name");
    verify(messageEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(messageEventSubscriptionEntity).setTenantId("42");
    verify(execution).getCurrentActivityId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution).getEventSubscriptions();
    verify(execution).getProcessDefinitionId();
    verify(execution, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent() {
    // Arrange
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertCompensationEvent(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42"));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent2() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenThrow(new ActivitiException("An error occurred"));

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertCompensationEvent(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42"));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent3() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecutionId())
        .thenThrow(new ActivitiException("An error occurred"));
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertCompensationEvent(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42"));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent4() {
    // Arrange
    when(processEngineConfigurationImpl.getPerformanceSettings())
        .thenThrow(new ActivitiException("An error occurred"));
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertCompensationEvent(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42"));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent5() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(compensateEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    // Act
    eventSubscriptionEntityManagerImpl.insertCompensationEvent(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntity).getExecution();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent6() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecution())
        .thenThrow(new ActivitiException("An error occurred"));
    when(compensateEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertCompensationEvent(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42"));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(compensateEventSubscriptionEntity).getExecution();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent7() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(false);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    // Act
    eventSubscriptionEntityManagerImpl.insertCompensationEvent(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent8() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount())
        .thenThrow(new ActivitiException("An error occurred"));
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(compensateEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.insertCompensationEvent(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42"));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntity).getExecution();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("");
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent9() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl =
        mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(activitiEventDispatcherImpl);

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecutionId()).thenReturn(null);
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getTenantId()).thenReturn("42");

    // Act
    eventSubscriptionEntityManagerImpl.insertCompensationEvent(execution, "42");

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("42");
    verify(execution, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcherImpl} {@link ActivitiEventDispatcherImpl#isEnabled()}
   *       return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent_givenActivitiEventDispatcherImplIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl =
        mock(ActivitiEventDispatcherImpl.class);
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(false);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(activitiEventDispatcherImpl);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(compensateEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getTenantId()).thenReturn("42");

    // Act
    eventSubscriptionEntityManagerImpl.insertCompensationEvent(execution, "42");

    // Assert
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntity).getExecution();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("42");
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent_givenNull() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl =
        mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(activitiEventDispatcherImpl);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(compensateEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getTenantId()).thenReturn(null);

    // Act
    eventSubscriptionEntityManagerImpl.insertCompensationEvent(execution, "42");

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntity).getExecution();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcherImpl#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl =
        mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(activitiEventDispatcherImpl);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(compensateEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getTenantId()).thenReturn("42");

    // Act
    eventSubscriptionEntityManagerImpl.insertCompensationEvent(execution, "42");

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntity).getExecution();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("42");
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(execution, atLeast(1)).getTenantId();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setEventSubscriptionCount(int)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"
  })
  public void testInsertCompensationEvent_thenCallsSetEventSubscriptionCount() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);

    CompensateEventSubscriptionEntity compensateEventSubscriptionEntity =
        mock(CompensateEventSubscriptionEntity.class);
    when(compensateEventSubscriptionEntity.getExecution()).thenReturn(executionEntityImpl);
    when(compensateEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing()
        .when(compensateEventSubscriptionEntity)
        .setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntity).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntity);

    // Act
    eventSubscriptionEntityManagerImpl.insertCompensationEvent(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntity).getExecution();
    verify(compensateEventSubscriptionEntity).getExecutionId();
    verify(compensateEventSubscriptionEntity).setActivityId("42");
    verify(compensateEventSubscriptionEntity).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntity).setTenantId("");
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(4);
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity, boolean)} with
   * {@code EventSubscriptionEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.insert(EventSubscriptionEntity, boolean)"
  })
  public void testInsertWithEventSubscriptionEntityBoolean() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    EventSubscriptionEntity entity = mock(EventSubscriptionEntity.class);
    when(entity.getExecutionId()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.insert(entity, false));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(entity).getExecutionId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity, boolean)} with
   * {@code EventSubscriptionEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.insert(EventSubscriptionEntity, boolean)"
  })
  public void testInsertWithEventSubscriptionEntityBoolean2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    EventSubscriptionEntity entity = mock(EventSubscriptionEntity.class);
    when(entity.getExecutionId()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.insert(entity, true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(entity).getExecutionId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity, boolean)} with
   * {@code EventSubscriptionEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.insert(EventSubscriptionEntity, boolean)"
  })
  public void testInsertWithEventSubscriptionEntityBoolean3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    EventSubscriptionEntity entity = mock(EventSubscriptionEntity.class);
    when(entity.getExecutionId()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.insert(entity, true));
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(entity).getExecutionId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity, boolean)} with
   * {@code EventSubscriptionEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.insert(EventSubscriptionEntity, boolean)"
  })
  public void testInsertWithEventSubscriptionEntityBoolean4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doThrow(new ActivitiException("An error occurred"))
        .when(activitiEventDispatcher)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.insert(mock(EventSubscriptionEntity.class), true));
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity, boolean)} with
   * {@code EventSubscriptionEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.insert(EventSubscriptionEntity, boolean)"
  })
  public void testInsertWithEventSubscriptionEntityBoolean5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    EventSubscriptionEntity entity = mock(EventSubscriptionEntity.class);
    when(entity.getExecutionId()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.insert(entity, true));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(entity).getExecutionId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity, boolean)} with
   * {@code EventSubscriptionEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#insert(EventSubscriptionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.insert(EventSubscriptionEntity, boolean)"
  })
  public void testInsertWithEventSubscriptionEntityBoolean_given42() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    EventSubscriptionEntity entity = mock(EventSubscriptionEntity.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    eventSubscriptionEntityManagerImpl.insert(entity, false);

    // Assert
    verify(entity).getExecutionId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)} with
   * {@code EventSubscriptionEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.delete(EventSubscriptionEntity, boolean)"
  })
  public void testDeleteWithEventSubscriptionEntityBoolean_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    EventSubscriptionEntity entity = mock(EventSubscriptionEntity.class);
    when(entity.getExecution()).thenThrow(new ActivitiException("An error occurred"));
    when(entity.getExecutionId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.delete(entity, false));
    verify(entity).getExecution();
    verify(entity).getExecutionId();
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId(String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionId() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<CompensateEventSubscriptionEntity>
        actualFindCompensateEventSubscriptionsByExecutionIdResult =
            eventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId("42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
    assertTrue(actualFindCompensateEventSubscriptionsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId(String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionId_givenArrayListAddNull() {
    // Arrange
    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(null);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity>
        actualFindCompensateEventSubscriptionsByExecutionIdResult =
            eventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId("42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
    assertTrue(actualFindCompensateEventSubscriptionsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId(String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionId_thenReturnSizeIsOne() {
    // Arrange
    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(mock(CompensateEventSubscriptionEntityImpl.class));
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity>
        actualFindCompensateEventSubscriptionsByExecutionIdResult =
            eventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId("42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
    assertEquals(1, actualFindCompensateEventSubscriptionsByExecutionIdResult.size());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId(String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionId_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId("42"));
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<CompensateEventSubscriptionEntity>
        actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult =
            eventSubscriptionEntityManagerImpl
                .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
    assertTrue(actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId2() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl
                .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42"));
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId3() {
    // Arrange
    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(null);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity>
        actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult =
            eventSubscriptionEntityManagerImpl
                .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
    assertTrue(actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId4() {
    // Arrange
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl =
        mock(CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getActivityId()).thenReturn("42");

    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(compensateEventSubscriptionEntityImpl);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity>
        actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult =
            eventSubscriptionEntityManagerImpl
                .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42");

    // Assert
    verify(compensateEventSubscriptionEntityImpl).getActivityId();
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
    assertEquals(1, actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult.size());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId5() {
    // Arrange
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl =
        mock(CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getActivityId())
        .thenThrow(new ActivitiException("An error occurred"));

    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(compensateEventSubscriptionEntityImpl);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(eventSubscriptionEntityList);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl
                .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42"));
    verify(compensateEventSubscriptionEntityImpl).getActivityId();
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId6() {
    // Arrange
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl =
        mock(CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getActivityId())
        .thenReturn(CompensateEventSubscriptionEntity.EVENT_TYPE);

    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(compensateEventSubscriptionEntityImpl);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity>
        actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult =
            eventSubscriptionEntityManagerImpl
                .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42");

    // Assert
    verify(compensateEventSubscriptionEntityImpl).getActivityId();
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
    assertTrue(actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"
  })
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId_whenNull() {
    // Arrange
    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(mock(CompensateEventSubscriptionEntityImpl.class));
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity>
        actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult =
            eventSubscriptionEntityManagerImpl
                .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", null);

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByExecutionAndType("42", "compensate");
    assertEquals(1, actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult.size());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)"
  })
  public void testFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityId() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByProcessInstanceAndActivityId(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<CompensateEventSubscriptionEntity>
        actualFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityIdResult =
            eventSubscriptionEntityManagerImpl
                .findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId("42", "42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByProcessInstanceAndActivityId("42", "42", "compensate");
    assertTrue(
        actualFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)"
  })
  public void testFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityId2() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByProcessInstanceAndActivityId(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl
                .findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId("42", "42"));
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByProcessInstanceAndActivityId("42", "42", "compensate");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)"
  })
  public void testFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityId3() {
    // Arrange
    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(null);
    when(eventSubscriptionDataManager.findEventSubscriptionsByProcessInstanceAndActivityId(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity>
        actualFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityIdResult =
            eventSubscriptionEntityManagerImpl
                .findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId("42", "42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByProcessInstanceAndActivityId("42", "42", "compensate");
    assertEquals(
        1, actualFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityIdResult.size());
    assertNull(actualFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityIdResult.get(0));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#addToExecution(EventSubscriptionEntity)}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#addToExecution(EventSubscriptionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.addToExecution(EventSubscriptionEntity)"
  })
  public void testAddToExecution_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    eventSubscriptionEntityManagerImpl.addToExecution(eventSubscriptionEntity);

    // Assert
    verify(eventSubscriptionEntity).getExecution();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#addToExecution(EventSubscriptionEntity)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link EventSubscriptionEntity} {@link EventSubscriptionEntity#getExecution()}
   *       return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#addToExecution(EventSubscriptionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.addToExecution(EventSubscriptionEntity)"
  })
  public void testAddToExecution_givenNull_whenEventSubscriptionEntityGetExecutionReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getExecution()).thenReturn(null);

    // Act
    eventSubscriptionEntityManagerImpl.addToExecution(eventSubscriptionEntity);

    // Assert
    verify(eventSubscriptionEntity).getExecution();
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findMessageEventSubscriptionsByProcessInstanceAndEventName(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findMessageEventSubscriptionsByProcessInstanceAndEventName(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findMessageEventSubscriptionsByProcessInstanceAndEventName(String, String)"
  })
  public void testFindMessageEventSubscriptionsByProcessInstanceAndEventName() {
    // Arrange
    when(eventSubscriptionDataManager.findMessageEventSubscriptionsByProcessInstanceAndEventName(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl
                .findMessageEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name"));
    verify(eventSubscriptionDataManager)
        .findMessageEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findMessageEventSubscriptionsByProcessInstanceAndEventName(String,
   * String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findMessageEventSubscriptionsByProcessInstanceAndEventName(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findMessageEventSubscriptionsByProcessInstanceAndEventName(String, String)"
  })
  public void testFindMessageEventSubscriptionsByProcessInstanceAndEventName_thenReturnEmpty() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findMessageEventSubscriptionsByProcessInstanceAndEventName(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<MessageEventSubscriptionEntity>
        actualFindMessageEventSubscriptionsByProcessInstanceAndEventNameResult =
            eventSubscriptionEntityManagerImpl
                .findMessageEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name");

    // Assert
    verify(eventSubscriptionDataManager)
        .findMessageEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name");
    assertTrue(actualFindMessageEventSubscriptionsByProcessInstanceAndEventNameResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByEventName(String,
   * String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByEventName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByEventName(String, String)"
  })
  public void testFindSignalEventSubscriptionsByEventName_thenReturnEmpty() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByEventName(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<SignalEventSubscriptionEntity> actualFindSignalEventSubscriptionsByEventNameResult =
        eventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByEventName(
            "Event Name", "42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findSignalEventSubscriptionsByEventName("Event Name", "42");
    assertTrue(actualFindSignalEventSubscriptionsByEventNameResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByEventName(String,
   * String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByEventName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByEventName(String, String)"
  })
  public void testFindSignalEventSubscriptionsByEventName_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByEventName(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByEventName(
                "Event Name", "42"));
    verify(eventSubscriptionDataManager)
        .findSignalEventSubscriptionsByEventName("Event Name", "42");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByProcessInstanceAndEventName(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByProcessInstanceAndEventName(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByProcessInstanceAndEventName(String, String)"
  })
  public void testFindSignalEventSubscriptionsByProcessInstanceAndEventName() {
    // Arrange
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByProcessInstanceAndEventName(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl
                .findSignalEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name"));
    verify(eventSubscriptionDataManager)
        .findSignalEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByProcessInstanceAndEventName(String,
   * String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByProcessInstanceAndEventName(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByProcessInstanceAndEventName(String, String)"
  })
  public void testFindSignalEventSubscriptionsByProcessInstanceAndEventName_thenReturnEmpty() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByProcessInstanceAndEventName(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<SignalEventSubscriptionEntity>
        actualFindSignalEventSubscriptionsByProcessInstanceAndEventNameResult =
            eventSubscriptionEntityManagerImpl
                .findSignalEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name");

    // Assert
    verify(eventSubscriptionDataManager)
        .findSignalEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name");
    assertTrue(actualFindSignalEventSubscriptionsByProcessInstanceAndEventNameResult.isEmpty());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByNameAndExecution(String,
   * String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByNameAndExecution(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByNameAndExecution(String, String)"
  })
  public void testFindSignalEventSubscriptionsByNameAndExecution_thenReturnEmpty() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByNameAndExecution(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<SignalEventSubscriptionEntity> actualFindSignalEventSubscriptionsByNameAndExecutionResult =
        eventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByNameAndExecution(
            "Name", "42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findSignalEventSubscriptionsByNameAndExecution("Name", "42");
    assertTrue(actualFindSignalEventSubscriptionsByNameAndExecutionResult.isEmpty());
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByNameAndExecution(String,
   * String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByNameAndExecution(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByNameAndExecution(String, String)"
  })
  public void testFindSignalEventSubscriptionsByNameAndExecution_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByNameAndExecution(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByNameAndExecution(
                "Name", "42"));
    verify(eventSubscriptionDataManager)
        .findSignalEventSubscriptionsByNameAndExecution("Name", "42");
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecutionAndType(String,
   * String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecutionAndType(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecutionAndType(String, String)"
  })
  public void testFindEventSubscriptionsByExecutionAndType_thenReturnEmpty() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByExecutionAndTypeResult =
        eventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecutionAndType("42", "Type");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType("42", "Type");
    assertTrue(actualFindEventSubscriptionsByExecutionAndTypeResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecutionAndType(String,
   * String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecutionAndType(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecutionAndType(String, String)"
  })
  public void testFindEventSubscriptionsByExecutionAndType_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecutionAndType(
                "42", "Type"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType("42", "Type");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByProcessInstanceAndActivityId(String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByProcessInstanceAndActivityId(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByProcessInstanceAndActivityId(String, String, String)"
  })
  public void testFindEventSubscriptionsByProcessInstanceAndActivityId() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByProcessInstanceAndActivityId(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.findEventSubscriptionsByProcessInstanceAndActivityId(
                "42", "42", "Type"));
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByProcessInstanceAndActivityId("42", "42", "Type");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByProcessInstanceAndActivityId(String,
   * String, String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByProcessInstanceAndActivityId(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByProcessInstanceAndActivityId(String, String, String)"
  })
  public void testFindEventSubscriptionsByProcessInstanceAndActivityId_thenReturnEmpty() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByProcessInstanceAndActivityId(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByProcessInstanceAndActivityIdResult =
        eventSubscriptionEntityManagerImpl.findEventSubscriptionsByProcessInstanceAndActivityId(
            "42", "42", "Type");

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByProcessInstanceAndActivityId("42", "42", "Type");
    assertTrue(actualFindEventSubscriptionsByProcessInstanceAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecution(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecution(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecution(String)"
  })
  public void testFindEventSubscriptionsByExecution_thenReturnEmpty() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecution(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByExecutionResult =
        eventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecution("42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecution("42");
    assertTrue(actualFindEventSubscriptionsByExecutionResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecution(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecution(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecution(String)"
  })
  public void testFindEventSubscriptionsByExecution_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecution(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecution("42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecution("42");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByTypeAndProcessDefinitionId(String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByTypeAndProcessDefinitionId(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByTypeAndProcessDefinitionId(String, String, String)"
  })
  public void testFindEventSubscriptionsByTypeAndProcessDefinitionId() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByTypeAndProcessDefinitionId(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.findEventSubscriptionsByTypeAndProcessDefinitionId(
                "Type", "42", "42"));
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByTypeAndProcessDefinitionId("Type", "42", "42");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByTypeAndProcessDefinitionId(String,
   * String, String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByTypeAndProcessDefinitionId(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByTypeAndProcessDefinitionId(String, String, String)"
  })
  public void testFindEventSubscriptionsByTypeAndProcessDefinitionId_thenReturnEmpty() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByTypeAndProcessDefinitionId(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByTypeAndProcessDefinitionIdResult =
        eventSubscriptionEntityManagerImpl.findEventSubscriptionsByTypeAndProcessDefinitionId(
            "Type", "42", "42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByTypeAndProcessDefinitionId("Type", "42", "42");
    assertTrue(actualFindEventSubscriptionsByTypeAndProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByName(String, String,
   * String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByName(String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByName(String, String, String)"
  })
  public void testFindEventSubscriptionsByName_thenReturnEmpty() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByName(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByNameResult =
        eventSubscriptionEntityManagerImpl.findEventSubscriptionsByName("Type", "Event Name", "42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByName("Type", "Event Name", "42");
    assertTrue(actualFindEventSubscriptionsByNameResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByName(String, String,
   * String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByName(String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByName(String, String, String)"
  })
  public void testFindEventSubscriptionsByName_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByName(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.findEventSubscriptionsByName(
                "Type", "Event Name", "42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByName("Type", "Event Name", "42");
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByNameAndExecution(String,
   * String, String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByNameAndExecution(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByNameAndExecution(String, String, String)"
  })
  public void testFindEventSubscriptionsByNameAndExecution_thenReturnEmpty() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByNameAndExecution(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByNameAndExecutionResult =
        eventSubscriptionEntityManagerImpl.findEventSubscriptionsByNameAndExecution(
            "Type", "Event Name", "42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByNameAndExecution("Type", "Event Name", "42");
    assertTrue(actualFindEventSubscriptionsByNameAndExecutionResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByNameAndExecution(String,
   * String, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findEventSubscriptionsByNameAndExecution(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByNameAndExecution(String, String, String)"
  })
  public void testFindEventSubscriptionsByNameAndExecution_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByNameAndExecution(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.findEventSubscriptionsByNameAndExecution(
                "Type", "Event Name", "42"));
    verify(eventSubscriptionDataManager)
        .findEventSubscriptionsByNameAndExecution("Type", "Event Name", "42");
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findMessageStartEventSubscriptionByName(String,
   * String)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findMessageStartEventSubscriptionByName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.findMessageStartEventSubscriptionByName(String, String)"
  })
  public void testFindMessageStartEventSubscriptionByName_thenReturnNull() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findMessageStartEventSubscriptionByName(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(null);
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    MessageEventSubscriptionEntity actualFindMessageStartEventSubscriptionByNameResult =
        eventSubscriptionEntityManagerImpl.findMessageStartEventSubscriptionByName(
            "Message Name", "42");

    // Assert
    verify(eventSubscriptionDataManager)
        .findMessageStartEventSubscriptionByName("Message Name", "42");
    assertNull(actualFindMessageStartEventSubscriptionByNameResult);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findMessageStartEventSubscriptionByName(String,
   * String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#findMessageStartEventSubscriptionByName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.findMessageStartEventSubscriptionByName(String, String)"
  })
  public void testFindMessageStartEventSubscriptionByName_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findMessageStartEventSubscriptionByName(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.findMessageStartEventSubscriptionByName(
                "Message Name", "42"));
    verify(eventSubscriptionDataManager)
        .findMessageStartEventSubscriptionByName("Message Name", "42");
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#updateEventSubscriptionTenantId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#updateEventSubscriptionTenantId(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.updateEventSubscriptionTenantId(String, String)"
  })
  public void testUpdateEventSubscriptionTenantId() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing()
        .when(eventSubscriptionDataManager)
        .updateEventSubscriptionTenantId(Mockito.<String>any(), Mockito.<String>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    eventSubscriptionEntityManagerImpl.updateEventSubscriptionTenantId("42", "42");

    // Assert
    verify(eventSubscriptionDataManager).updateEventSubscriptionTenantId("42", "42");
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#updateEventSubscriptionTenantId(String,
   * String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#updateEventSubscriptionTenantId(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.updateEventSubscriptionTenantId(String, String)"
  })
  public void testUpdateEventSubscriptionTenantId_thenThrowActivitiException() {
    // Arrange
    doThrow(new ActivitiException("An error occurred"))
        .when(eventSubscriptionDataManager)
        .updateEventSubscriptionTenantId(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.updateEventSubscriptionTenantId("42", "42"));
    verify(eventSubscriptionDataManager).updateEventSubscriptionTenantId("42", "42");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#deleteEventSubscriptionsForProcessDefinition(String)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#deleteEventSubscriptionsForProcessDefinition(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.deleteEventSubscriptionsForProcessDefinition(String)"
  })
  public void testDeleteEventSubscriptionsForProcessDefinition() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing()
        .when(eventSubscriptionDataManager)
        .deleteEventSubscriptionsForProcessDefinition(Mockito.<String>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    // Act
    eventSubscriptionEntityManagerImpl.deleteEventSubscriptionsForProcessDefinition("42");

    // Assert
    verify(eventSubscriptionDataManager).deleteEventSubscriptionsForProcessDefinition("42");
  }

  /**
   * Test {@link
   * EventSubscriptionEntityManagerImpl#deleteEventSubscriptionsForProcessDefinition(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#deleteEventSubscriptionsForProcessDefinition(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.deleteEventSubscriptionsForProcessDefinition(String)"
  })
  public void testDeleteEventSubscriptionsForProcessDefinition_thenThrowActivitiException() {
    // Arrange
    doThrow(new ActivitiException("An error occurred"))
        .when(eventSubscriptionDataManager)
        .deleteEventSubscriptionsForProcessDefinition(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.deleteEventSubscriptionsForProcessDefinition("42"));
    verify(eventSubscriptionDataManager).deleteEventSubscriptionsForProcessDefinition("42");
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#processEventSync(EventSubscriptionEntity,
   * Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#processEventSync(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionEntityManagerImpl.processEventSync(EventSubscriptionEntity, Object)"
  })
  public void testProcessEventSync_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventHandlers(new HashMap<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventType()).thenReturn("Event Type");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionEntityManagerImpl.processEventSync(
                eventSubscriptionEntity, JSONObject.NULL));
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#toSignalEventSubscriptionEntityList(List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#toSignalEventSubscriptionEntityList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.toSignalEventSubscriptionEntityList(List)"
  })
  public void testToSignalEventSubscriptionEntityList_givenNull_thenReturnArrayList() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    ArrayList<EventSubscriptionEntity> result = new ArrayList<>();
    result.add(null);

    // Act
    List<SignalEventSubscriptionEntity> actualToSignalEventSubscriptionEntityListResult =
        eventSubscriptionEntityManagerImpl.toSignalEventSubscriptionEntityList(result);

    // Assert
    assertEquals(result, actualToSignalEventSubscriptionEntityListResult);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#toSignalEventSubscriptionEntityList(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#toSignalEventSubscriptionEntityList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.toSignalEventSubscriptionEntityList(List)"
  })
  public void testToSignalEventSubscriptionEntityList_whenArrayList_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(
        eventSubscriptionEntityManagerImpl
            .toSignalEventSubscriptionEntityList(new ArrayList<>())
            .isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#toMessageEventSubscriptionEntityList(List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#toMessageEventSubscriptionEntityList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.toMessageEventSubscriptionEntityList(List)"
  })
  public void testToMessageEventSubscriptionEntityList_givenNull_thenReturnArrayList() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    ArrayList<EventSubscriptionEntity> result = new ArrayList<>();
    result.add(null);

    // Act
    List<MessageEventSubscriptionEntity> actualToMessageEventSubscriptionEntityListResult =
        eventSubscriptionEntityManagerImpl.toMessageEventSubscriptionEntityList(result);

    // Assert
    assertEquals(result, actualToMessageEventSubscriptionEntityListResult);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#toMessageEventSubscriptionEntityList(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionEntityManagerImpl#toMessageEventSubscriptionEntityList(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List EventSubscriptionEntityManagerImpl.toMessageEventSubscriptionEntityList(List)"
  })
  public void testToMessageEventSubscriptionEntityList_whenArrayList_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(
        eventSubscriptionEntityManagerImpl
            .toMessageEventSubscriptionEntityList(new ArrayList<>())
            .isEmpty());
  }
}
