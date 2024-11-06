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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
   * Test getters and setters.
   * <p>
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

  /**
   * Test
   * {@link VariableInstanceEntityManagerImpl#create(String, VariableType, Object)}
   * with {@code String}, {@code VariableType}, {@code Object}.
   * <ul>
   *   <li>Then return TypeName is {@code bytes}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#create(String, VariableType, Object)}
   */
  @Test
  public void testCreateWithStringVariableTypeObject_thenReturnTypeNameIsBytes() {
    // Arrange
    when(variableInstanceDataManager.create()).thenReturn(new VariableInstanceEntityImpl());
    ByteArrayType type = new ByteArrayType();

    // Act
    VariableInstanceEntity actualCreateResult = variableInstanceEntityManagerImpl.create("Name", type, null);

    // Assert
    verify(variableInstanceDataManager).create();
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof VariableInstanceEntityImpl);
    assertEquals("bytes", actualCreateResult.getTypeName());
    assertEquals("bytes", ((VariableInstanceEntityImpl) actualCreateResult).typeName);
    assertEquals("var-Name", actualCreateResult.getByteArrayRef().getName());
    assertNull(actualCreateResult.getValue());
    assertNull(actualCreateResult.getCachedValue());
    assertNull(actualCreateResult.getTextValue());
    assertTrue(((Map<Object, Object>) persistentState).isEmpty());
    assertSame(type, actualCreateResult.getType());
  }

  /**
   * Test
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsertWithVariableInstanceEntityBoolean() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsertWithVariableInstanceEntityBoolean2() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsertWithVariableInstanceEntityBoolean3() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsertWithVariableInstanceEntityBoolean4() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsertWithVariableInstanceEntityBoolean5() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsertWithVariableInstanceEntityBoolean_thenCallsDispatchEvent() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link EntityManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsertWithVariableInstanceEntityBoolean_thenCallsFindById() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getVariableCount()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testInsertWithVariableInstanceEntityBoolean_thenCallsGetVariableCount() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByTaskIds_given42_whenHashSetAdd42_thenReturnEmpty() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByTaskIds(Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
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
  }

  /**
   * Test
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByTaskIds_givenFoo_whenHashSetAddFoo_thenReturnEmpty() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByTaskIds(Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
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
  }

  /**
   * Test
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByTaskIds_thenReturnEmpty() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByTaskIds(Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskIdsResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByTaskIds(new HashSet<>());

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByTaskIdsResult.isEmpty());
  }

  /**
   * Test
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByExecutionIds_given42_whenHashSetAdd42_thenReturnEmpty() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByExecutionIds(Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
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
  }

  /**
   * Test
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByExecutionIds_givenFoo_whenHashSetAddFoo() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByExecutionIds(Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
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
  }

  /**
   * Test
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}
   */
  @Test
  public void testFindVariableInstancesByExecutionIds_thenReturnEmpty() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager = mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByExecutionIds(Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl = new VariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionIdsResult = variableInstanceEntityManagerImpl
        .findVariableInstancesByExecutionIds(new HashSet<>());

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByExecutionIdsResult.isEmpty());
  }

  /**
   * Test
   * {@link VariableInstanceEntityManagerImpl#findVariableInstanceByExecutionAndName(String, String)}.
   * <p>
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithVariableInstanceEntityBoolean() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithVariableInstanceEntityBoolean2() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithVariableInstanceEntityBoolean3() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithVariableInstanceEntityBoolean_thenCallsDelete() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithVariableInstanceEntityBoolean_thenCallsDispatchEvent() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessDefinitionId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithVariableInstanceEntityBoolean_thenCallsGetProcessDefinitionId() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   * with {@code VariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithVariableInstanceEntityBoolean_whenFalse_thenCallsDelete() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#createVariableDeleteEvent(VariableInstanceEntity)}.
   * <ul>
   *   <li>Then return {@link ActivitiVariableEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#createVariableDeleteEvent(VariableInstanceEntity)}
   */
  @Test
  public void testCreateVariableDeleteEvent_thenReturnActivitiVariableEventImpl() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}.
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}
   */
  @Test
  public void testDeleteVariableInstanceByTask() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}
   */
  @Test
  public void testDeleteVariableInstanceByTask_givenHashMap() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}.
   * <ul>
   *   <li>Then calls {@link EntityManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}
   */
  @Test
  public void testDeleteVariableInstanceByTask_thenCallsFindById() {
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
   * Test
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessDefinitionId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}
   */
  @Test
  public void testDeleteVariableInstanceByTask_thenCallsGetProcessDefinitionId() {
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
}
