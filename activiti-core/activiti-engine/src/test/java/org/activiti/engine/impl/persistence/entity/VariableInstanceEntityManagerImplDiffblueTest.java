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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiVariableEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.delegate.event.impl.ActivitiVariableEventImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.VariableInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisVariableInstanceDataManager;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.ByteArrayType;
import org.activiti.engine.impl.variable.VariableType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VariableInstanceEntityManagerImplDiffblueTest {
  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Mock
  private VariableInstanceDataManager variableInstanceDataManager;

  @InjectMocks
  private VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl;

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#create(String, VariableType, Object)}
   */
  @Test
  public void testCreate() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    when(variableInstanceDataManager.create()).thenReturn(variableInstanceEntityImpl);

    // Act
    VariableInstanceEntity actualCreateResult = variableInstanceEntityManagerImpl.create("Name", new BigDecimalType(),
        JSONObject.NULL);

    // Assert
    verify(variableInstanceDataManager).create();
    assertSame(variableInstanceEntityImpl, actualCreateResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#create(String, VariableType, Object)}
   */
  @Test
  public void testCreate2() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    when(variableInstanceDataManager.create()).thenReturn(variableInstanceEntityImpl);

    // Act
    VariableInstanceEntity actualCreateResult = variableInstanceEntityManagerImpl.create("Name", new ByteArrayType(),
        null);

    // Assert
    verify(variableInstanceDataManager).create();
    assertSame(variableInstanceEntityImpl, actualCreateResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsert() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);

    // Act
    variableInstanceEntityManagerImpl.insert(new VariableInstanceEntityImpl(), true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsert2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);

    // Act
    variableInstanceEntityManagerImpl.insert(new VariableInstanceEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsert3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);

    // Act
    variableInstanceEntityManagerImpl.insert(new VariableInstanceEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsert4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity, atLeast(1)).getExecutionId();
    verify(executionDataManager).findById(eq("42"));
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsert5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getVariableCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setVariableCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityImpl).getVariableCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setVariableCount(eq(4));
    verify(entity, atLeast(1)).getExecutionId();
    verify(executionDataManager).findById(eq("42"));
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsert6() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager).findById(eq("42"));
    verify(entity, atLeast(1)).getExecutionId();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsert7() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
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
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(entity).getExecutionId();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsert8() {
    // Arrange
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
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(mock(ActivitiEventDispatcher.class));
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, false);

    // Assert
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(executionEntityManager).findById(eq("42"));
    verify(entity, atLeast(1)).getExecutionId();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskId(String)}
   */
  @Test
  public void testFindVariableInstancesByTaskId() {
    // Arrange
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByTaskId(Mockito.<String>any()))
        .thenReturn(variableInstanceEntityList);

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskIdResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByTaskId("42");

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskId(eq("42"));
    assertTrue(actualFindVariableInstancesByTaskIdResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByTaskIdResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByTaskIds() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByTaskIds(Mockito.<Set<String>>any()))
        .thenReturn(variableInstanceEntityList);
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskIdsResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByTaskIds(new HashSet<>());

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByTaskIdsResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByTaskIdsResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByTaskIds2() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByTaskIds(Mockito.<Set<String>>any()))
        .thenReturn(variableInstanceEntityList);
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    HashSet<String> taskIds = new HashSet<>();
    taskIds.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskIdsResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByTaskIds(taskIds);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByTaskIdsResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByTaskIdsResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByTaskIds3() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByTaskIds(Mockito.<Set<String>>any()))
        .thenReturn(variableInstanceEntityList);
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    HashSet<String> taskIds = new HashSet<>();
    taskIds.add("42");
    taskIds.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskIdsResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByTaskIds(taskIds);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByTaskIdsResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByTaskIdsResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionId(String)}
   */
  @Test
  public void testFindVariableInstancesByExecutionId() {
    // Arrange
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByExecutionId(Mockito.<String>any()))
        .thenReturn(variableInstanceEntityList);

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionIdResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByExecutionId("42");

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionId(eq("42"));
    assertTrue(actualFindVariableInstancesByExecutionIdResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByExecutionIdResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByExecutionIds() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByExecutionIds(Mockito.<Set<String>>any()))
        .thenReturn(variableInstanceEntityList);
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionIdsResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByExecutionIds(new HashSet<>());

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByExecutionIdsResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByExecutionIdsResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByExecutionIds2() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByExecutionIds(Mockito.<Set<String>>any()))
        .thenReturn(variableInstanceEntityList);
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    HashSet<String> executionIds = new HashSet<>();
    executionIds.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionIdsResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByExecutionIds(executionIds);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByExecutionIdsResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByExecutionIdsResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByExecutionIds3() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByExecutionIds(Mockito.<Set<String>>any()))
        .thenReturn(variableInstanceEntityList);
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    HashSet<String> executionIds = new HashSet<>();
    executionIds.add("42");
    executionIds.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionIdsResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByExecutionIds(executionIds);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByExecutionIdsResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByExecutionIdsResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstanceByExecutionAndName(String, String)}
   */
  @Test
  public void testFindVariableInstanceByExecutionAndName() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    when(variableInstanceDataManager.findVariableInstanceByExecutionAndName(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(variableInstanceEntityImpl);

    // Act
    VariableInstanceEntity actualFindVariableInstanceByExecutionAndNameResult = variableInstanceEntityManagerImpl
        .findVariableInstanceByExecutionAndName("42", "Variable Name");

    // Assert
    verify(variableInstanceDataManager).findVariableInstanceByExecutionAndName(eq("42"), eq("Variable Name"));
    assertSame(variableInstanceEntityImpl, actualFindVariableInstanceByExecutionAndNameResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionAndNames(String, Collection)}
   */
  @Test
  public void testFindVariableInstancesByExecutionAndNames() {
    // Arrange
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByExecutionAndNames(Mockito.<String>any(),
        Mockito.<Collection<String>>any())).thenReturn(variableInstanceEntityList);

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionAndNamesResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByExecutionAndNames("42", new ArrayList<>());

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByExecutionAndNamesResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByExecutionAndNamesResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionAndNames(String, Collection)}
   */
  @Test
  public void testFindVariableInstancesByExecutionAndNames2() {
    // Arrange
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByExecutionAndNames(Mockito.<String>any(),
        Mockito.<Collection<String>>any())).thenReturn(variableInstanceEntityList);

    ArrayList<String> names = new ArrayList<>();
    names.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionAndNamesResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByExecutionAndNames("42", names);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByExecutionAndNamesResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByExecutionAndNamesResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionAndNames(String, Collection)}
   */
  @Test
  public void testFindVariableInstancesByExecutionAndNames3() {
    // Arrange
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByExecutionAndNames(Mockito.<String>any(),
        Mockito.<Collection<String>>any())).thenReturn(variableInstanceEntityList);

    ArrayList<String> names = new ArrayList<>();
    names.add("42");
    names.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionAndNamesResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByExecutionAndNames("42", names);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByExecutionAndNamesResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByExecutionAndNamesResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstanceByTaskAndName(String, String)}
   */
  @Test
  public void testFindVariableInstanceByTaskAndName() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    when(variableInstanceDataManager.findVariableInstanceByTaskAndName(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(variableInstanceEntityImpl);

    // Act
    VariableInstanceEntity actualFindVariableInstanceByTaskAndNameResult = variableInstanceEntityManagerImpl
        .findVariableInstanceByTaskAndName("42", "Variable Name");

    // Assert
    verify(variableInstanceDataManager).findVariableInstanceByTaskAndName(eq("42"), eq("Variable Name"));
    assertSame(variableInstanceEntityImpl, actualFindVariableInstanceByTaskAndNameResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskAndNames(String, Collection)}
   */
  @Test
  public void testFindVariableInstancesByTaskAndNames() {
    // Arrange
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByTaskAndNames(Mockito.<String>any(),
        Mockito.<Collection<String>>any())).thenReturn(variableInstanceEntityList);

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskAndNamesResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByTaskAndNames("42", new ArrayList<>());

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByTaskAndNamesResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByTaskAndNamesResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskAndNames(String, Collection)}
   */
  @Test
  public void testFindVariableInstancesByTaskAndNames2() {
    // Arrange
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByTaskAndNames(Mockito.<String>any(),
        Mockito.<Collection<String>>any())).thenReturn(variableInstanceEntityList);

    ArrayList<String> names = new ArrayList<>();
    names.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskAndNamesResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByTaskAndNames("42", names);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByTaskAndNamesResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByTaskAndNamesResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskAndNames(String, Collection)}
   */
  @Test
  public void testFindVariableInstancesByTaskAndNames3() {
    // Arrange
    ArrayList<VariableInstanceEntity> variableInstanceEntityList = new ArrayList<>();
    when(variableInstanceDataManager.findVariableInstancesByTaskAndNames(Mockito.<String>any(),
        Mockito.<Collection<String>>any())).thenReturn(variableInstanceEntityList);

    ArrayList<String> names = new ArrayList<>();
    names.add("42");
    names.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskAndNamesResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByTaskAndNames("42", names);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByTaskAndNamesResult.isEmpty());
    assertSame(variableInstanceEntityList, actualFindVariableInstancesByTaskAndNamesResult);
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDelete() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getValue()).thenReturn(JSONObject.NULL);
    when(entity.getName()).thenReturn("Name");
    when(entity.getTaskId()).thenReturn("42");
    when(entity.getExecutionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");
    when(entity.getByteArrayRef()).thenReturn(new ByteArrayRef());
    when(entity.getType()).thenReturn(new BigDecimalType());
    doNothing().when(entity).setDeleted(anyBoolean());

    // Act
    variableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).setDeleted(eq(true));
    verify(entity).getByteArrayRef();
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getName();
    verify(entity, atLeast(1)).getProcessInstanceId();
    verify(entity).getTaskId();
    verify(entity, atLeast(1)).getType();
    verify(entity).getValue();
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
    verify(executionDataManager, atLeast(1)).findById(eq("42"));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDelete2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getVariableCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setVariableCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getValue()).thenReturn(JSONObject.NULL);
    when(entity.getName()).thenReturn("Name");
    when(entity.getTaskId()).thenReturn("42");
    when(entity.getExecutionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");
    when(entity.getByteArrayRef()).thenReturn(new ByteArrayRef());
    when(entity.getType()).thenReturn(new BigDecimalType());
    doNothing().when(entity).setDeleted(anyBoolean());

    // Act
    variableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).setDeleted(eq(true));
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getVariableCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setVariableCount(eq(2));
    verify(entity).getByteArrayRef();
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getName();
    verify(entity, atLeast(1)).getProcessInstanceId();
    verify(entity).getTaskId();
    verify(entity, atLeast(1)).getType();
    verify(entity).getValue();
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
    verify(executionDataManager, atLeast(1)).findById(eq("42"));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDelete3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getValue()).thenReturn(JSONObject.NULL);
    when(entity.getName()).thenReturn("Name");
    when(entity.getTaskId()).thenReturn("42");
    when(entity.getExecutionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");
    when(entity.getByteArrayRef()).thenReturn(new ByteArrayRef());
    when(entity.getType()).thenReturn(new BigDecimalType());
    doNothing().when(entity).setDeleted(anyBoolean());

    // Act
    variableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).setDeleted(eq(true));
    verify(executionEntityManager, atLeast(1)).findById(eq("42"));
    verify(entity).getByteArrayRef();
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getName();
    verify(entity, atLeast(1)).getProcessInstanceId();
    verify(entity).getTaskId();
    verify(entity, atLeast(1)).getType();
    verify(entity).getValue();
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDelete4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
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
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getValue()).thenReturn(JSONObject.NULL);
    when(entity.getName()).thenReturn("Name");
    when(entity.getTaskId()).thenReturn("42");
    when(entity.getExecutionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");
    when(entity.getByteArrayRef()).thenReturn(new ByteArrayRef());
    when(entity.getType()).thenReturn(new BigDecimalType());
    doNothing().when(entity).setDeleted(anyBoolean());

    // Act
    variableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(entity).setDeleted(eq(true));
    verify(executionEntityManager).findById(eq("42"));
    verify(entity).getByteArrayRef();
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getName();
    verify(entity, atLeast(1)).getProcessInstanceId();
    verify(entity).getTaskId();
    verify(entity, atLeast(1)).getType();
    verify(entity).getValue();
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDelete5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
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
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");
    when(entity.getByteArrayRef()).thenReturn(new ByteArrayRef());
    doNothing().when(entity).setDeleted(anyBoolean());

    // Act
    variableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).setDeleted(eq(true));
    verify(executionEntityManager).findById(eq("42"));
    verify(entity).getByteArrayRef();
    verify(entity, atLeast(1)).getExecutionId();
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDelete6() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
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
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getValue()).thenReturn(JSONObject.NULL);
    when(entity.getName()).thenReturn("Name");
    when(entity.getTaskId()).thenReturn("42");
    when(entity.getExecutionId()).thenReturn("42");
    when(entity.getProcessInstanceId()).thenReturn("42");
    when(entity.getByteArrayRef()).thenReturn(byteArrayRef);
    when(entity.getType()).thenReturn(new BigDecimalType());
    doNothing().when(entity).setDeleted(anyBoolean());

    // Act
    variableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).setDeleted(eq(true));
    verify(byteArrayRef).delete();
    verify(executionEntityManager, atLeast(1)).findById(eq("42"));
    verify(entity).getByteArrayRef();
    verify(entity, atLeast(1)).getExecutionId();
    verify(entity).getName();
    verify(entity, atLeast(1)).getProcessInstanceId();
    verify(entity).getTaskId();
    verify(entity, atLeast(1)).getType();
    verify(entity).getValue();
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDelete7() {
    // Arrange
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
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(mock(ActivitiEventDispatcher.class));
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");
    when(entity.getByteArrayRef()).thenReturn(byteArrayRef);
    doNothing().when(entity).setDeleted(anyBoolean());

    // Act
    variableInstanceEntityManagerImpl.delete(entity, false);

    // Assert
    verify(performanceSettings, atLeast(1)).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(entity).setDeleted(eq(true));
    verify(byteArrayRef).delete();
    verify(executionEntityManager).findById(eq("42"));
    verify(entity).getByteArrayRef();
    verify(entity, atLeast(1)).getExecutionId();
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#createVariableDeleteEvent(VariableInstanceEntity)}
   */
  @Test
  public void testCreateVariableDeleteEvent() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();
    BigDecimalType type = new BigDecimalType();
    variableInstance.setType(type);

    // Act
    ActivitiVariableEvent actualCreateVariableDeleteEventResult = variableInstanceEntityManagerImpl
        .createVariableDeleteEvent(variableInstance);

    // Assert
    assertTrue(actualCreateVariableDeleteEventResult instanceof ActivitiVariableEventImpl);
    assertNull(actualCreateVariableDeleteEventResult.getVariableValue());
    assertNull(actualCreateVariableDeleteEventResult.getProcessDefinitionId());
    assertNull(actualCreateVariableDeleteEventResult.getProcessInstanceId());
    assertNull(actualCreateVariableDeleteEventResult.getExecutionId());
    assertNull(actualCreateVariableDeleteEventResult.getTaskId());
    assertNull(actualCreateVariableDeleteEventResult.getVariableName());
    assertNull(((ActivitiVariableEventImpl) actualCreateVariableDeleteEventResult).getReason());
    assertEquals(ActivitiEventType.VARIABLE_DELETED, actualCreateVariableDeleteEventResult.getType());
    assertSame(type, actualCreateVariableDeleteEventResult.getVariableType());
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}
   */
  @Test
  public void testDeleteVariableInstanceByTask() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    TaskEntity task = mock(TaskEntity.class);
    when(task.getVariableInstanceEntities()).thenReturn(new HashMap<>());

    // Act
    variableInstanceEntityManagerImpl.deleteVariableInstanceByTask(task);

    // Assert that nothing has changed
    verify(task).getVariableInstanceEntities();
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}
   */
  @Test
  public void testDeleteVariableInstanceByTask2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl variableInstanceEntityImpl = mock(VariableInstanceEntityImpl.class);
    when(variableInstanceEntityImpl.getValue()).thenReturn(JSONObject.NULL);
    when(variableInstanceEntityImpl.getName()).thenReturn("Name");
    when(variableInstanceEntityImpl.getTaskId()).thenReturn("42");
    when(variableInstanceEntityImpl.getExecutionId()).thenReturn("42");
    when(variableInstanceEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(variableInstanceEntityImpl.getByteArrayRef()).thenReturn(new ByteArrayRef());
    when(variableInstanceEntityImpl.getType()).thenReturn(new BigDecimalType());
    doNothing().when(variableInstanceEntityImpl).setDeleted(anyBoolean());

    HashMap<String, VariableInstanceEntity> stringVariableInstanceEntityMap = new HashMap<>();
    stringVariableInstanceEntityMap.put("foo", variableInstanceEntityImpl);
    TaskEntity task = mock(TaskEntity.class);
    when(task.getVariableInstanceEntities()).thenReturn(stringVariableInstanceEntityMap);

    // Act
    variableInstanceEntityManagerImpl.deleteVariableInstanceByTask(task);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(variableInstanceEntityImpl).setDeleted(eq(true));
    verify(task).getVariableInstanceEntities();
    verify(variableInstanceEntityImpl).getByteArrayRef();
    verify(variableInstanceEntityImpl, atLeast(1)).getExecutionId();
    verify(variableInstanceEntityImpl).getName();
    verify(variableInstanceEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(variableInstanceEntityImpl).getTaskId();
    verify(variableInstanceEntityImpl, atLeast(1)).getType();
    verify(variableInstanceEntityImpl).getValue();
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
    verify(executionDataManager, atLeast(1)).findById(eq("42"));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}
   */
  @Test
  public void testDeleteVariableInstanceByTask3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getVariableCount()).thenReturn(3);
    doNothing().when(executionEntityImpl).setVariableCount(anyInt());
    when(executionEntityImpl.isCountEnabled()).thenReturn(true);
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl variableInstanceEntityImpl = mock(VariableInstanceEntityImpl.class);
    when(variableInstanceEntityImpl.getValue()).thenReturn(JSONObject.NULL);
    when(variableInstanceEntityImpl.getName()).thenReturn("Name");
    when(variableInstanceEntityImpl.getTaskId()).thenReturn("42");
    when(variableInstanceEntityImpl.getExecutionId()).thenReturn("42");
    when(variableInstanceEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(variableInstanceEntityImpl.getByteArrayRef()).thenReturn(new ByteArrayRef());
    when(variableInstanceEntityImpl.getType()).thenReturn(new BigDecimalType());
    doNothing().when(variableInstanceEntityImpl).setDeleted(anyBoolean());

    HashMap<String, VariableInstanceEntity> stringVariableInstanceEntityMap = new HashMap<>();
    stringVariableInstanceEntityMap.put("foo", variableInstanceEntityImpl);
    TaskEntity task = mock(TaskEntity.class);
    when(task.getVariableInstanceEntities()).thenReturn(stringVariableInstanceEntityMap);

    // Act
    variableInstanceEntityManagerImpl.deleteVariableInstanceByTask(task);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(variableInstanceEntityImpl).setDeleted(eq(true));
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getVariableCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setVariableCount(eq(2));
    verify(task).getVariableInstanceEntities();
    verify(variableInstanceEntityImpl).getByteArrayRef();
    verify(variableInstanceEntityImpl, atLeast(1)).getExecutionId();
    verify(variableInstanceEntityImpl).getName();
    verify(variableInstanceEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(variableInstanceEntityImpl).getTaskId();
    verify(variableInstanceEntityImpl, atLeast(1)).getType();
    verify(variableInstanceEntityImpl).getValue();
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
    verify(executionDataManager, atLeast(1)).findById(eq("42"));
  }

  /**
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}
   */
  @Test
  public void testDeleteVariableInstanceByTask4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, variableInstanceDataManager);
    VariableInstanceEntityImpl variableInstanceEntityImpl = mock(VariableInstanceEntityImpl.class);
    when(variableInstanceEntityImpl.getValue()).thenReturn(JSONObject.NULL);
    when(variableInstanceEntityImpl.getName()).thenReturn("Name");
    when(variableInstanceEntityImpl.getTaskId()).thenReturn("42");
    when(variableInstanceEntityImpl.getExecutionId()).thenReturn("42");
    when(variableInstanceEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(variableInstanceEntityImpl.getByteArrayRef()).thenReturn(new ByteArrayRef());
    when(variableInstanceEntityImpl.getType()).thenReturn(new BigDecimalType());
    doNothing().when(variableInstanceEntityImpl).setDeleted(anyBoolean());

    HashMap<String, VariableInstanceEntity> stringVariableInstanceEntityMap = new HashMap<>();
    stringVariableInstanceEntityMap.put("foo", variableInstanceEntityImpl);
    TaskEntity task = mock(TaskEntity.class);
    when(task.getVariableInstanceEntities()).thenReturn(stringVariableInstanceEntityMap);

    // Act
    variableInstanceEntityManagerImpl.deleteVariableInstanceByTask(task);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(variableInstanceEntityImpl).setDeleted(eq(true));
    verify(executionEntityManager, atLeast(1)).findById(eq("42"));
    verify(task).getVariableInstanceEntities();
    verify(variableInstanceEntityImpl).getByteArrayRef();
    verify(variableInstanceEntityImpl, atLeast(1)).getExecutionId();
    verify(variableInstanceEntityImpl).getName();
    verify(variableInstanceEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(variableInstanceEntityImpl).getTaskId();
    verify(variableInstanceEntityImpl, atLeast(1)).getType();
    verify(variableInstanceEntityImpl).getValue();
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link VariableInstanceEntityManagerImpl#VariableInstanceEntityManagerImpl(ProcessEngineConfigurationImpl, VariableInstanceDataManager)}
   *   <li>
   * {@link VariableInstanceEntityManagerImpl#setVariableInstanceDataManager(VariableInstanceDataManager)}
   *   <li>{@link VariableInstanceEntityManagerImpl#getDataManager()}
   *   <li>
   * {@link VariableInstanceEntityManagerImpl#getVariableInstanceDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    VariableInstanceEntityManagerImpl actualVariableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    MybatisVariableInstanceDataManager variableInstanceDataManager = new MybatisVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());
    actualVariableInstanceEntityManagerImpl.setVariableInstanceDataManager(variableInstanceDataManager);
    DataManager<VariableInstanceEntity> actualDataManager = actualVariableInstanceEntityManagerImpl.getDataManager();

    // Assert that nothing has changed
    assertSame(variableInstanceDataManager, actualDataManager);
    assertSame(variableInstanceDataManager, actualVariableInstanceEntityManagerImpl.getVariableInstanceDataManager());
  }
}
