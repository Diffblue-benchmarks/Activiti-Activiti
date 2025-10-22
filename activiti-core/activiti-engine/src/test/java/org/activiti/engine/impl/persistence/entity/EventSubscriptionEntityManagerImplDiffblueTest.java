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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
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
  @Mock
  private EventSubscriptionDataManager eventSubscriptionDataManager;

  @InjectMocks
  private EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EventSubscriptionEntityManagerImpl#EventSubscriptionEntityManagerImpl(ProcessEngineConfigurationImpl, EventSubscriptionDataManager)}
   *   <li>{@link EventSubscriptionEntityManagerImpl#setEventSubscriptionDataManager(EventSubscriptionDataManager)}
   *   <li>{@link EventSubscriptionEntityManagerImpl#getDataManager()}
   *   <li>{@link EventSubscriptionEntityManagerImpl#getEventSubscriptionDataManager()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void EventSubscriptionEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, EventSubscriptionDataManager)",
      "DataManager EventSubscriptionEntityManagerImpl.getDataManager()",
      "EventSubscriptionDataManager EventSubscriptionEntityManagerImpl.getEventSubscriptionDataManager()",
      "void EventSubscriptionEntityManagerImpl.setEventSubscriptionDataManager(EventSubscriptionDataManager)"})
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    EventSubscriptionEntityManagerImpl actualEventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));
    MybatisEventSubscriptionDataManager eventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());
    actualEventSubscriptionEntityManagerImpl.setEventSubscriptionDataManager(eventSubscriptionDataManager);
    DataManager<EventSubscriptionEntity> actualDataManager = actualEventSubscriptionEntityManagerImpl.getDataManager();

    // Assert
    assertSame(eventSubscriptionDataManager, actualDataManager);
    assertSame(eventSubscriptionDataManager,
        actualEventSubscriptionEntityManagerImpl.getEventSubscriptionDataManager());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#createCompensateEventSubscription()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#createCompensateEventSubscription()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.createCompensateEventSubscription()"})
  public void testCreateCompensateEventSubscription_thenReturnNull() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.createCompensateEventSubscription()).thenReturn(null);

    // Act
    CompensateEventSubscriptionEntity actualCreateCompensateEventSubscriptionResult = (new EventSubscriptionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventSubscriptionDataManager)).createCompensateEventSubscription();

    // Assert
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
    assertNull(actualCreateCompensateEventSubscriptionResult);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#createMessageEventSubscription()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#createMessageEventSubscription()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.createMessageEventSubscription()"})
  public void testCreateMessageEventSubscription_thenReturnNull() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(null);

    // Act
    MessageEventSubscriptionEntity actualCreateMessageEventSubscriptionResult = (new EventSubscriptionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventSubscriptionDataManager)).createMessageEventSubscription();

    // Assert
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    assertNull(actualCreateMessageEventSubscriptionResult);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#createSignalEventSubscription()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#createSignalEventSubscription()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.createSignalEventSubscription()"})
  public void testCreateSignalEventSubscription_thenReturnNull() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(null);

    // Act
    SignalEventSubscriptionEntity actualCreateSignalEventSubscriptionResult = (new EventSubscriptionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventSubscriptionDataManager)).createSignalEventSubscription();

    // Assert
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    assertNull(actualCreateSignalEventSubscriptionResult);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    Signal signal = new Signal("42", "Name");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent2() {
    // Arrange
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(false);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    Signal signal = new Signal("42", "Name");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent3() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    Signal signal = new Signal("42", "Name");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent4() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doThrow(new ActivitiException("An error occurred")).when(activitiEventDispatcherImpl)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    Signal signal = new Signal("42", "Name");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name",
        signal, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(activitiEventDispatcherImpl).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent5() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenThrow(new ActivitiException("An error occurred"));
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecution()).thenReturn(executionEntityImpl);
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    Signal signal = new Signal("42", "Name");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name",
        signal, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent6() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn(null);
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    Signal signal = new Signal("42", "Name");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent7() {
    // Arrange
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    doThrow(new ActivitiException("An error occurred")).when(signalEventSubscriptionEntityImpl)
        .setConfiguration(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    Signal signal = mock(Signal.class);
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name",
        signal, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setConfiguration(eq("Scope"));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcherImpl} {@link ActivitiEventDispatcherImpl#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent_givenActivitiEventDispatcherImplIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(false);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    Signal signal = new Signal("42", "Name");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent_givenNull() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTenantId(null);

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", null, execution);

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Signal Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <ul>
   *   <li>Then calls {@link Signal#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent_thenCallsGetName() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    doNothing().when(signalEventSubscriptionEntityImpl).setConfiguration(Mockito.<String>any());
    when(signalEventSubscriptionEntityImpl.getExecution()).thenReturn(executionEntityImpl);
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    Signal signal = mock(Signal.class);
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(eq(4));
    verify(signalEventSubscriptionEntityImpl).setConfiguration(eq("Scope"));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setEventSubscriptionCount(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent_thenCallsSetEventSubscriptionCount() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecution()).thenReturn(executionEntityImpl);
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    Signal signal = new Signal("42", "Name");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", signal, execution);

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(eq(4));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then calls {@link ExecutionEntityImpl#setEventSubscriptionCount(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertSignalEvent(String, Signal, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SignalEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertSignalEvent(String, Signal, ExecutionEntity)"})
  public void testInsertSignalEvent_whenNull_thenCallsSetEventSubscriptionCount() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecution()).thenReturn(executionEntityImpl);
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertSignalEvent("Signal Name", null, execution);

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Signal Name"));
    verify(signalEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(eq(4));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"})
  public void testInsertMessageEvent() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Message Name"));
    verify(messageEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"})
  public void testInsertMessageEvent2() {
    // Arrange
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(false);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Message Name"));
    verify(messageEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"})
  public void testInsertMessageEvent3() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doThrow(new ActivitiException("An error occurred")).when(activitiEventDispatcherImpl)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(activitiEventDispatcherImpl).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Message Name"));
    verify(messageEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"})
  public void testInsertMessageEvent4() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenThrow(new ActivitiException("An error occurred"));
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution()).thenReturn(executionEntityImpl);
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Message Name"));
    verify(messageEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"})
  public void testInsertMessageEvent5() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn(null);
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Message Name"));
    verify(messageEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcherImpl} {@link ActivitiEventDispatcherImpl#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"})
  public void testInsertMessageEvent_givenActivitiEventDispatcherImplIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(false);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Message Name"));
    verify(messageEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"})
  public void testInsertMessageEvent_givenNull() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTenantId(null);

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Message Name"));
    verify(messageEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcherImpl#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"})
  public void testInsertMessageEvent_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Message Name"));
    verify(messageEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setEventSubscriptionCount(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertMessageEvent(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertMessageEvent(String, ExecutionEntity)"})
  public void testInsertMessageEvent_thenCallsSetEventSubscriptionCount() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution()).thenReturn(executionEntityImpl);
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    eventSubscriptionEntityManagerImpl.insertMessageEvent("Message Name", execution);

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Message Name"));
    verify(messageEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(eq(4));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    List<EventSubscriptionEntity> eventSubscriptions = execution.getEventSubscriptions();
    assertEquals(1, eventSubscriptions.size());
    assertEquals(1, execution.eventSubscriptions.size());
    assertSame(execution.eventSubscriptions, eventSubscriptions);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"})
  public void testInsertCompensationEvent() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(compensateEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntityImpl);

    // Act
    eventSubscriptionEntityManagerImpl
        .insertCompensationEvent(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntityImpl).getExecution();
    verify(compensateEventSubscriptionEntityImpl).getExecutionId();
    verify(compensateEventSubscriptionEntityImpl).setActivityId(eq("42"));
    verify(compensateEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"})
  public void testInsertCompensationEvent2() {
    // Arrange
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(false);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntityImpl);

    // Act
    eventSubscriptionEntityManagerImpl
        .insertCompensationEvent(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42");

    // Assert
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(compensateEventSubscriptionEntityImpl).getExecutionId();
    verify(compensateEventSubscriptionEntityImpl).setActivityId(eq("42"));
    verify(compensateEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"})
  public void testInsertCompensationEvent3() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(compensateEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntityImpl);

    // Act
    eventSubscriptionEntityManagerImpl
        .insertCompensationEvent(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42");

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntityImpl).getExecution();
    verify(compensateEventSubscriptionEntityImpl).getExecutionId();
    verify(compensateEventSubscriptionEntityImpl).setActivityId(eq("42"));
    verify(compensateEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"})
  public void testInsertCompensationEvent4() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doThrow(new ActivitiException("An error occurred")).when(activitiEventDispatcherImpl)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    doNothing().when(compensateEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl
        .insertCompensationEvent(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42"));
    verify(activitiEventDispatcherImpl).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(compensateEventSubscriptionEntityImpl).setActivityId(eq("42"));
    verify(compensateEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"})
  public void testInsertCompensationEvent5() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenThrow(new ActivitiException("An error occurred"));
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getExecution()).thenReturn(executionEntityImpl);
    when(compensateEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl
        .insertCompensationEvent(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42"));
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntityImpl).getExecution();
    verify(compensateEventSubscriptionEntityImpl).getExecutionId();
    verify(compensateEventSubscriptionEntityImpl).setActivityId(eq("42"));
    verify(compensateEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"})
  public void testInsertCompensationEvent6() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getExecutionId()).thenReturn(null);
    doNothing().when(compensateEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntityImpl);

    // Act
    eventSubscriptionEntityManagerImpl
        .insertCompensationEvent(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42");

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(compensateEventSubscriptionEntityImpl).getExecutionId();
    verify(compensateEventSubscriptionEntityImpl).setActivityId(eq("42"));
    verify(compensateEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcherImpl} {@link ActivitiEventDispatcherImpl#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"})
  public void testInsertCompensationEvent_givenActivitiEventDispatcherImplIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(false);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(compensateEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntityImpl);

    // Act
    eventSubscriptionEntityManagerImpl
        .insertCompensationEvent(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42");

    // Assert
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntityImpl).getExecution();
    verify(compensateEventSubscriptionEntityImpl).getExecutionId();
    verify(compensateEventSubscriptionEntityImpl).setActivityId(eq("42"));
    verify(compensateEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"})
  public void testInsertCompensationEvent_givenNull() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(compensateEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntityImpl);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTenantId(null);

    // Act
    eventSubscriptionEntityManagerImpl.insertCompensationEvent(execution, "42");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntityImpl).getExecution();
    verify(compensateEventSubscriptionEntityImpl).getExecutionId();
    verify(compensateEventSubscriptionEntityImpl).setActivityId(eq("42"));
    verify(compensateEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setEventSubscriptionCount(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#insertCompensationEvent(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CompensateEventSubscriptionEntity EventSubscriptionEntityManagerImpl.insertCompensationEvent(ExecutionEntity, String)"})
  public void testInsertCompensationEvent_thenCallsSetEventSubscriptionCount() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(activitiEventDispatcherImpl).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcherImpl.isEnabled()).thenReturn(true);
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    when(performanceSettings.isEnableExecutionRelationshipCounts()).thenReturn(true);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcherImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getExecution()).thenReturn(executionEntityImpl);
    when(compensateEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(compensateEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(compensateEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createCompensateEventSubscription())
        .thenReturn(compensateEventSubscriptionEntityImpl);

    // Act
    eventSubscriptionEntityManagerImpl
        .insertCompensationEvent(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "42");

    // Assert
    verify(activitiEventDispatcherImpl, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcherImpl).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getPerformanceSettings();
    verify(compensateEventSubscriptionEntityImpl).getExecution();
    verify(compensateEventSubscriptionEntityImpl).getExecutionId();
    verify(compensateEventSubscriptionEntityImpl).setActivityId(eq("42"));
    verify(compensateEventSubscriptionEntityImpl).setExecution(isA(ExecutionEntity.class));
    verify(compensateEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(eq(4));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createCompensateEventSubscription();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)} with {@code EventSubscriptionEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.delete(EventSubscriptionEntity, boolean)"})
  public void testDeleteWithEventSubscriptionEntityBoolean() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).delete(Mockito.<EventSubscriptionEntity>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, eventSubscriptionDataManager);
    CompensateEventSubscriptionEntityImpl entity = mock(CompensateEventSubscriptionEntityImpl.class);
    when(entity.getExecution()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    eventSubscriptionEntityManagerImpl.delete(entity, true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).getExecution();
    verify(entity).getExecutionId();
    verify(eventSubscriptionDataManager).delete(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)} with {@code EventSubscriptionEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.delete(EventSubscriptionEntity, boolean)"})
  public void testDeleteWithEventSubscriptionEntityBoolean2() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).delete(Mockito.<EventSubscriptionEntity>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, eventSubscriptionDataManager);
    CompensateEventSubscriptionEntityImpl entity = mock(CompensateEventSubscriptionEntityImpl.class);
    when(entity.getExecution()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    eventSubscriptionEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).getExecution();
    verify(entity).getExecutionId();
    verify(eventSubscriptionDataManager).delete(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)} with {@code EventSubscriptionEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.delete(EventSubscriptionEntity, boolean)"})
  public void testDeleteWithEventSubscriptionEntityBoolean3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).delete(Mockito.<EventSubscriptionEntity>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, eventSubscriptionDataManager);
    CompensateEventSubscriptionEntityImpl entity = mock(CompensateEventSubscriptionEntityImpl.class);
    when(entity.getExecutionId()).thenReturn(null);

    // Act
    eventSubscriptionEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(entity).getExecutionId();
    verify(eventSubscriptionDataManager).delete(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)} with {@code EventSubscriptionEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.delete(EventSubscriptionEntity, boolean)"})
  public void testDeleteWithEventSubscriptionEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).delete(Mockito.<EventSubscriptionEntity>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, eventSubscriptionDataManager);
    CompensateEventSubscriptionEntityImpl entity = mock(CompensateEventSubscriptionEntityImpl.class);
    when(entity.getExecution()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    eventSubscriptionEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).getExecution();
    verify(entity).getExecutionId();
    verify(eventSubscriptionDataManager).delete(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)} with {@code EventSubscriptionEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setEventSubscriptionCount(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.delete(EventSubscriptionEntity, boolean)"})
  public void testDeleteWithEventSubscriptionEntityBoolean_thenCallsSetEventSubscriptionCount() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).delete(Mockito.<EventSubscriptionEntity>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, eventSubscriptionDataManager);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    CompensateEventSubscriptionEntityImpl entity = mock(CompensateEventSubscriptionEntityImpl.class);
    when(entity.getExecution()).thenReturn(executionEntityImpl);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    eventSubscriptionEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).getExecution();
    verify(entity).getExecutionId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(eq(2));
    verify(eventSubscriptionDataManager).delete(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)} with {@code EventSubscriptionEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setEventSubscriptionCount(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.delete(EventSubscriptionEntity, boolean)"})
  public void testDeleteWithEventSubscriptionEntityBoolean_thenCallsSetEventSubscriptionCount2() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).delete(Mockito.<EventSubscriptionEntity>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, eventSubscriptionDataManager);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setEventSubscriptionCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    CompensateEventSubscriptionEntityImpl entity = mock(CompensateEventSubscriptionEntityImpl.class);
    when(entity.getExecution()).thenReturn(executionEntityImpl);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    eventSubscriptionEntityManagerImpl.delete(entity, false);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).getExecution();
    verify(entity).getExecutionId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setEventSubscriptionCount(eq(2));
    verify(eventSubscriptionDataManager).delete(isA(EventSubscriptionEntity.class));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)} with {@code EventSubscriptionEntity}, {@code boolean}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#delete(EventSubscriptionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.delete(EventSubscriptionEntity, boolean)"})
  public void testDeleteWithEventSubscriptionEntityBoolean_thenThrowActivitiException() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, mock(EventSubscriptionDataManager.class));
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getEventSubscriptionCount()).thenThrow(new ActivitiException("An error occurred"));
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    CompensateEventSubscriptionEntityImpl entity = mock(CompensateEventSubscriptionEntityImpl.class);
    when(entity.getExecution()).thenReturn(executionEntityImpl);
    when(entity.getExecutionId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl.delete(entity, true));
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).getExecution();
    verify(entity).getExecutionId();
    verify(executionEntityImpl).getEventSubscriptionCount();
    verify(executionEntityImpl).isCountEnabled();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId(String)"})
  public void testFindCompensateEventSubscriptionsByExecutionId_givenArrayListAddNull() {
    // Arrange
    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(null);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity> actualFindCompensateEventSubscriptionsByExecutionIdResult = eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByExecutionId("42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("compensate"));
    assertTrue(actualFindCompensateEventSubscriptionsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId(String)"})
  public void testFindCompensateEventSubscriptionsByExecutionId_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<CompensateEventSubscriptionEntity> actualFindCompensateEventSubscriptionsByExecutionIdResult = eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByExecutionId("42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("compensate"));
    assertTrue(actualFindCompensateEventSubscriptionsByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId(String)"})
  public void testFindCompensateEventSubscriptionsByExecutionId_thenReturnSizeIsOne() {
    // Arrange
    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(mock(CompensateEventSubscriptionEntityImpl.class));
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity> actualFindCompensateEventSubscriptionsByExecutionIdResult = eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByExecutionId("42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("compensate"));
    assertEquals(1, actualFindCompensateEventSubscriptionsByExecutionIdResult.size());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId(String)"})
  public void testFindCompensateEventSubscriptionsByExecutionId_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionId("42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("compensate"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"})
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("compensate"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"})
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId2() {
    // Arrange
    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(null);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity> actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult = eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("compensate"));
    assertTrue(actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"})
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId3() {
    // Arrange
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getActivityId()).thenReturn("42");

    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(compensateEventSubscriptionEntityImpl);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity> actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult = eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42");

    // Assert
    verify(compensateEventSubscriptionEntityImpl).getActivityId();
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("compensate"));
    assertEquals(1, actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult.size());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"})
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId4() {
    // Arrange
    CompensateEventSubscriptionEntityImpl compensateEventSubscriptionEntityImpl = mock(
        CompensateEventSubscriptionEntityImpl.class);
    when(compensateEventSubscriptionEntityImpl.getActivityId())
        .thenReturn(CompensateEventSubscriptionEntity.EVENT_TYPE);

    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(compensateEventSubscriptionEntityImpl);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity> actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult = eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42");

    // Assert
    verify(compensateEventSubscriptionEntityImpl).getActivityId();
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("compensate"));
    assertTrue(actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"})
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<CompensateEventSubscriptionEntity> actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult = eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", "42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("compensate"));
    assertTrue(actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByExecutionIdAndActivityId(String, String)"})
  public void testFindCompensateEventSubscriptionsByExecutionIdAndActivityId_whenNull() {
    // Arrange
    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(mock(CompensateEventSubscriptionEntityImpl.class));
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity> actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult = eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByExecutionIdAndActivityId("42", null);

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("compensate"));
    assertEquals(1, actualFindCompensateEventSubscriptionsByExecutionIdAndActivityIdResult.size());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)"})
  public void testFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityId() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByProcessInstanceAndActivityId(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<CompensateEventSubscriptionEntity> actualFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityIdResult = eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId("42", "42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByProcessInstanceAndActivityId(eq("42"), eq("42"),
        eq("compensate"));
    assertTrue(actualFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)"})
  public void testFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityId2() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByProcessInstanceAndActivityId(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId("42", "42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByProcessInstanceAndActivityId(eq("42"), eq("42"),
        eq("compensate"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId(String, String)"})
  public void testFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityId3() {
    // Arrange
    ArrayList<EventSubscriptionEntity> eventSubscriptionEntityList = new ArrayList<>();
    eventSubscriptionEntityList.add(null);
    when(eventSubscriptionDataManager.findEventSubscriptionsByProcessInstanceAndActivityId(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenReturn(eventSubscriptionEntityList);

    // Act
    List<CompensateEventSubscriptionEntity> actualFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityIdResult = eventSubscriptionEntityManagerImpl
        .findCompensateEventSubscriptionsByProcessInstanceIdAndActivityId("42", "42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByProcessInstanceAndActivityId(eq("42"), eq("42"),
        eq("compensate"));
    assertEquals(1, actualFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityIdResult.size());
    assertNull(actualFindCompensateEventSubscriptionsByProcessInstanceIdAndActivityIdResult.get(0));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#addToExecution(EventSubscriptionEntity)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#addToExecution(EventSubscriptionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.addToExecution(EventSubscriptionEntity)"})
  public void testAddToExecution_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));
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
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link EventSubscriptionEntity} {@link EventSubscriptionEntity#getExecution()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#addToExecution(EventSubscriptionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.addToExecution(EventSubscriptionEntity)"})
  public void testAddToExecution_givenNull_whenEventSubscriptionEntityGetExecutionReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));
    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getExecution()).thenReturn(null);

    // Act
    eventSubscriptionEntityManagerImpl.addToExecution(eventSubscriptionEntity);

    // Assert
    verify(eventSubscriptionEntity).getExecution();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findMessageEventSubscriptionsByProcessInstanceAndEventName(String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findMessageEventSubscriptionsByProcessInstanceAndEventName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findMessageEventSubscriptionsByProcessInstanceAndEventName(String, String)"})
  public void testFindMessageEventSubscriptionsByProcessInstanceAndEventName() {
    // Arrange
    when(eventSubscriptionDataManager.findMessageEventSubscriptionsByProcessInstanceAndEventName(Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl
        .findMessageEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name"));
    verify(eventSubscriptionDataManager).findMessageEventSubscriptionsByProcessInstanceAndEventName(eq("42"),
        eq("Event Name"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findMessageEventSubscriptionsByProcessInstanceAndEventName(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findMessageEventSubscriptionsByProcessInstanceAndEventName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findMessageEventSubscriptionsByProcessInstanceAndEventName(String, String)"})
  public void testFindMessageEventSubscriptionsByProcessInstanceAndEventName_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findMessageEventSubscriptionsByProcessInstanceAndEventName(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<MessageEventSubscriptionEntity> actualFindMessageEventSubscriptionsByProcessInstanceAndEventNameResult = eventSubscriptionEntityManagerImpl
        .findMessageEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name");

    // Assert
    verify(eventSubscriptionDataManager).findMessageEventSubscriptionsByProcessInstanceAndEventName(eq("42"),
        eq("Event Name"));
    assertTrue(actualFindMessageEventSubscriptionsByProcessInstanceAndEventNameResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByEventName(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByEventName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByEventName(String, String)"})
  public void testFindSignalEventSubscriptionsByEventName_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByEventName(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<SignalEventSubscriptionEntity> actualFindSignalEventSubscriptionsByEventNameResult = eventSubscriptionEntityManagerImpl
        .findSignalEventSubscriptionsByEventName("Event Name", "42");

    // Assert
    verify(eventSubscriptionDataManager).findSignalEventSubscriptionsByEventName(eq("Event Name"), eq("42"));
    assertTrue(actualFindSignalEventSubscriptionsByEventNameResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByEventName(String, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByEventName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByEventName(String, String)"})
  public void testFindSignalEventSubscriptionsByEventName_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByEventName(Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByEventName("Event Name", "42"));
    verify(eventSubscriptionDataManager).findSignalEventSubscriptionsByEventName(eq("Event Name"), eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByProcessInstanceAndEventName(String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByProcessInstanceAndEventName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByProcessInstanceAndEventName(String, String)"})
  public void testFindSignalEventSubscriptionsByProcessInstanceAndEventName() {
    // Arrange
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByProcessInstanceAndEventName(Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl
        .findSignalEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name"));
    verify(eventSubscriptionDataManager).findSignalEventSubscriptionsByProcessInstanceAndEventName(eq("42"),
        eq("Event Name"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByProcessInstanceAndEventName(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByProcessInstanceAndEventName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByProcessInstanceAndEventName(String, String)"})
  public void testFindSignalEventSubscriptionsByProcessInstanceAndEventName_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByProcessInstanceAndEventName(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<SignalEventSubscriptionEntity> actualFindSignalEventSubscriptionsByProcessInstanceAndEventNameResult = eventSubscriptionEntityManagerImpl
        .findSignalEventSubscriptionsByProcessInstanceAndEventName("42", "Event Name");

    // Assert
    verify(eventSubscriptionDataManager).findSignalEventSubscriptionsByProcessInstanceAndEventName(eq("42"),
        eq("Event Name"));
    assertTrue(actualFindSignalEventSubscriptionsByProcessInstanceAndEventNameResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByNameAndExecution(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByNameAndExecution(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByNameAndExecution(String, String)"})
  public void testFindSignalEventSubscriptionsByNameAndExecution_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByNameAndExecution(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<SignalEventSubscriptionEntity> actualFindSignalEventSubscriptionsByNameAndExecutionResult = eventSubscriptionEntityManagerImpl
        .findSignalEventSubscriptionsByNameAndExecution("Name", "42");

    // Assert
    verify(eventSubscriptionDataManager).findSignalEventSubscriptionsByNameAndExecution(eq("Name"), eq("42"));
    assertTrue(actualFindSignalEventSubscriptionsByNameAndExecutionResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByNameAndExecution(String, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findSignalEventSubscriptionsByNameAndExecution(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByNameAndExecution(String, String)"})
  public void testFindSignalEventSubscriptionsByNameAndExecution_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findSignalEventSubscriptionsByNameAndExecution(Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.findSignalEventSubscriptionsByNameAndExecution("Name", "42"));
    verify(eventSubscriptionDataManager).findSignalEventSubscriptionsByNameAndExecution(eq("Name"), eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecutionAndType(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecutionAndType(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecutionAndType(String, String)"})
  public void testFindEventSubscriptionsByExecutionAndType_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByExecutionAndTypeResult = eventSubscriptionEntityManagerImpl
        .findEventSubscriptionsByExecutionAndType("42", "Type");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("Type"));
    assertTrue(actualFindEventSubscriptionsByExecutionAndTypeResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecutionAndType(String, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecutionAndType(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecutionAndType(String, String)"})
  public void testFindEventSubscriptionsByExecutionAndType_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecutionAndType(Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecutionAndType("42", "Type"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecutionAndType(eq("42"), eq("Type"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByProcessInstanceAndActivityId(String, String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByProcessInstanceAndActivityId(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByProcessInstanceAndActivityId(String, String, String)"})
  public void testFindEventSubscriptionsByProcessInstanceAndActivityId() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByProcessInstanceAndActivityId(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl
        .findEventSubscriptionsByProcessInstanceAndActivityId("42", "42", "Type"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByProcessInstanceAndActivityId(eq("42"), eq("42"),
        eq("Type"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByProcessInstanceAndActivityId(String, String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByProcessInstanceAndActivityId(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByProcessInstanceAndActivityId(String, String, String)"})
  public void testFindEventSubscriptionsByProcessInstanceAndActivityId_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByProcessInstanceAndActivityId(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByProcessInstanceAndActivityIdResult = eventSubscriptionEntityManagerImpl
        .findEventSubscriptionsByProcessInstanceAndActivityId("42", "42", "Type");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByProcessInstanceAndActivityId(eq("42"), eq("42"),
        eq("Type"));
    assertTrue(actualFindEventSubscriptionsByProcessInstanceAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecution(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecution(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecution(String)"})
  public void testFindEventSubscriptionsByExecution_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecution(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByExecutionResult = eventSubscriptionEntityManagerImpl
        .findEventSubscriptionsByExecution("42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecution(eq("42"));
    assertTrue(actualFindEventSubscriptionsByExecutionResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecution(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByExecution(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecution(String)"})
  public void testFindEventSubscriptionsByExecution_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecution(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.findEventSubscriptionsByExecution("42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecution(eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByTypeAndProcessDefinitionId(String, String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByTypeAndProcessDefinitionId(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByTypeAndProcessDefinitionId(String, String, String)"})
  public void testFindEventSubscriptionsByTypeAndProcessDefinitionId() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByTypeAndProcessDefinitionId(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionEntityManagerImpl
        .findEventSubscriptionsByTypeAndProcessDefinitionId("Type", "42", "42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByTypeAndProcessDefinitionId(eq("Type"), eq("42"),
        eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByTypeAndProcessDefinitionId(String, String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByTypeAndProcessDefinitionId(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByTypeAndProcessDefinitionId(String, String, String)"})
  public void testFindEventSubscriptionsByTypeAndProcessDefinitionId_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByTypeAndProcessDefinitionId(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByTypeAndProcessDefinitionIdResult = eventSubscriptionEntityManagerImpl
        .findEventSubscriptionsByTypeAndProcessDefinitionId("Type", "42", "42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByTypeAndProcessDefinitionId(eq("Type"), eq("42"),
        eq("42"));
    assertTrue(actualFindEventSubscriptionsByTypeAndProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByName(String, String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByName(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByName(String, String, String)"})
  public void testFindEventSubscriptionsByName_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByName(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByNameResult = eventSubscriptionEntityManagerImpl
        .findEventSubscriptionsByName("Type", "Event Name", "42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByName(eq("Type"), eq("Event Name"), eq("42"));
    assertTrue(actualFindEventSubscriptionsByNameResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByName(String, String, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByName(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByName(String, String, String)"})
  public void testFindEventSubscriptionsByName_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByName(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.findEventSubscriptionsByName("Type", "Event Name", "42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByName(eq("Type"), eq("Event Name"), eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByNameAndExecution(String, String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByNameAndExecution(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByNameAndExecution(String, String, String)"})
  public void testFindEventSubscriptionsByNameAndExecution_thenReturnEmpty() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByNameAndExecution(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<EventSubscriptionEntity> actualFindEventSubscriptionsByNameAndExecutionResult = eventSubscriptionEntityManagerImpl
        .findEventSubscriptionsByNameAndExecution("Type", "Event Name", "42");

    // Assert
    verify(eventSubscriptionDataManager).findEventSubscriptionsByNameAndExecution(eq("Type"), eq("Event Name"),
        eq("42"));
    assertTrue(actualFindEventSubscriptionsByNameAndExecutionResult.isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByNameAndExecution(String, String, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findEventSubscriptionsByNameAndExecution(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List EventSubscriptionEntityManagerImpl.findEventSubscriptionsByNameAndExecution(String, String, String)"})
  public void testFindEventSubscriptionsByNameAndExecution_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findEventSubscriptionsByNameAndExecution(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.findEventSubscriptionsByNameAndExecution("Type", "Event Name", "42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByNameAndExecution(eq("Type"), eq("Event Name"),
        eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findMessageStartEventSubscriptionByName(String, String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findMessageStartEventSubscriptionByName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.findMessageStartEventSubscriptionByName(String, String)"})
  public void testFindMessageStartEventSubscriptionByName_thenReturnNull() {
    // Arrange
    when(eventSubscriptionDataManager.findMessageStartEventSubscriptionByName(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(null);

    // Act
    MessageEventSubscriptionEntity actualFindMessageStartEventSubscriptionByNameResult = eventSubscriptionEntityManagerImpl
        .findMessageStartEventSubscriptionByName("Message Name", "42");

    // Assert
    verify(eventSubscriptionDataManager).findMessageStartEventSubscriptionByName(eq("Message Name"), eq("42"));
    assertNull(actualFindMessageStartEventSubscriptionByNameResult);
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#findMessageStartEventSubscriptionByName(String, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#findMessageStartEventSubscriptionByName(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageEventSubscriptionEntity EventSubscriptionEntityManagerImpl.findMessageStartEventSubscriptionByName(String, String)"})
  public void testFindMessageStartEventSubscriptionByName_thenThrowActivitiException() {
    // Arrange
    when(eventSubscriptionDataManager.findMessageStartEventSubscriptionByName(Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.findMessageStartEventSubscriptionByName("Message Name", "42"));
    verify(eventSubscriptionDataManager).findMessageStartEventSubscriptionByName(eq("Message Name"), eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#updateEventSubscriptionTenantId(String, String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#updateEventSubscriptionTenantId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.updateEventSubscriptionTenantId(String, String)"})
  public void testUpdateEventSubscriptionTenantId() {
    // Arrange
    doNothing().when(eventSubscriptionDataManager)
        .updateEventSubscriptionTenantId(Mockito.<String>any(), Mockito.<String>any());

    // Act
    eventSubscriptionEntityManagerImpl.updateEventSubscriptionTenantId("42", "42");

    // Assert
    verify(eventSubscriptionDataManager).updateEventSubscriptionTenantId(eq("42"), eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#updateEventSubscriptionTenantId(String, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#updateEventSubscriptionTenantId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.updateEventSubscriptionTenantId(String, String)"})
  public void testUpdateEventSubscriptionTenantId_thenThrowActivitiException() {
    // Arrange
    doThrow(new ActivitiException("An error occurred")).when(eventSubscriptionDataManager)
        .updateEventSubscriptionTenantId(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.updateEventSubscriptionTenantId("42", "42"));
    verify(eventSubscriptionDataManager).updateEventSubscriptionTenantId(eq("42"), eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#deleteEventSubscriptionsForProcessDefinition(String)}.
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#deleteEventSubscriptionsForProcessDefinition(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.deleteEventSubscriptionsForProcessDefinition(String)"})
  public void testDeleteEventSubscriptionsForProcessDefinition() {
    // Arrange
    doNothing().when(eventSubscriptionDataManager).deleteEventSubscriptionsForProcessDefinition(Mockito.<String>any());

    // Act
    eventSubscriptionEntityManagerImpl.deleteEventSubscriptionsForProcessDefinition("42");

    // Assert
    verify(eventSubscriptionDataManager).deleteEventSubscriptionsForProcessDefinition(eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#deleteEventSubscriptionsForProcessDefinition(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#deleteEventSubscriptionsForProcessDefinition(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.deleteEventSubscriptionsForProcessDefinition(String)"})
  public void testDeleteEventSubscriptionsForProcessDefinition_thenThrowActivitiException() {
    // Arrange
    doThrow(new ActivitiException("An error occurred")).when(eventSubscriptionDataManager)
        .deleteEventSubscriptionsForProcessDefinition(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.deleteEventSubscriptionsForProcessDefinition("42"));
    verify(eventSubscriptionDataManager).deleteEventSubscriptionsForProcessDefinition(eq("42"));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#processEventSync(EventSubscriptionEntity, Object)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#processEventSync(EventSubscriptionEntity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionEntityManagerImpl.processEventSync(EventSubscriptionEntity, Object)"})
  public void testProcessEventSync_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventHandlers(new HashMap<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));
    EventSubscriptionEntity eventSubscriptionEntity = mock(EventSubscriptionEntity.class);
    when(eventSubscriptionEntity.getEventType()).thenReturn("Event Type");

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionEntityManagerImpl.processEventSync(eventSubscriptionEntity, JSONObject.NULL));
    verify(eventSubscriptionEntity, atLeast(1)).getEventType();
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#toSignalEventSubscriptionEntityList(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#toSignalEventSubscriptionEntityList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.toSignalEventSubscriptionEntityList(List)"})
  public void testToSignalEventSubscriptionEntityList_givenNull_thenReturnArrayList() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    ArrayList<EventSubscriptionEntity> result = new ArrayList<>();
    result.add(null);

    // Act and Assert
    assertEquals(result, eventSubscriptionEntityManagerImpl.toSignalEventSubscriptionEntityList(result));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#toSignalEventSubscriptionEntityList(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#toSignalEventSubscriptionEntityList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.toSignalEventSubscriptionEntityList(List)"})
  public void testToSignalEventSubscriptionEntityList_whenArrayList_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(eventSubscriptionEntityManagerImpl.toSignalEventSubscriptionEntityList(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#toMessageEventSubscriptionEntityList(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#toMessageEventSubscriptionEntityList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.toMessageEventSubscriptionEntityList(List)"})
  public void testToMessageEventSubscriptionEntityList_givenNull_thenReturnArrayList() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    ArrayList<EventSubscriptionEntity> result = new ArrayList<>();
    result.add(null);

    // Act and Assert
    assertEquals(result, eventSubscriptionEntityManagerImpl.toMessageEventSubscriptionEntityList(result));
  }

  /**
   * Test {@link EventSubscriptionEntityManagerImpl#toMessageEventSubscriptionEntityList(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionEntityManagerImpl#toMessageEventSubscriptionEntityList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EventSubscriptionEntityManagerImpl.toMessageEventSubscriptionEntityList(List)"})
  public void testToMessageEventSubscriptionEntityList_whenArrayList_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfiguration, new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(eventSubscriptionEntityManagerImpl.toMessageEventSubscriptionEntityList(new ArrayList<>()).isEmpty());
  }
}
