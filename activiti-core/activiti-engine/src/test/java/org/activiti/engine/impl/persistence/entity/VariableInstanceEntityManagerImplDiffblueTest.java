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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
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
import org.activiti.engine.impl.variable.CustomObjectType;
import org.activiti.engine.impl.variable.VariableType;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VariableInstanceEntityManagerImplDiffblueTest {
  @Mock private VariableInstanceDataManager variableInstanceDataManager;

  @InjectMocks private VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       VariableInstanceEntityManagerImpl#VariableInstanceEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       VariableInstanceDataManager)}
   *   <li>{@link
   *       VariableInstanceEntityManagerImpl#setVariableInstanceDataManager(VariableInstanceDataManager)}
   *   <li>{@link VariableInstanceEntityManagerImpl#getDataManager()}
   *   <li>{@link VariableInstanceEntityManagerImpl#getVariableInstanceDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, VariableInstanceDataManager)",
    "DataManager VariableInstanceEntityManagerImpl.getDataManager()",
    "VariableInstanceDataManager VariableInstanceEntityManagerImpl.getVariableInstanceDataManager()",
    "void VariableInstanceEntityManagerImpl.setVariableInstanceDataManager(VariableInstanceDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    VariableInstanceEntityManagerImpl actualVariableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    MybatisVariableInstanceDataManager variableInstanceDataManager =
        new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration());
    actualVariableInstanceEntityManagerImpl.setVariableInstanceDataManager(
        variableInstanceDataManager);
    DataManager<VariableInstanceEntity> actualDataManager =
        actualVariableInstanceEntityManagerImpl.getDataManager();

    // Assert
    assertSame(variableInstanceDataManager, actualDataManager);
    assertSame(
        variableInstanceDataManager,
        actualVariableInstanceEntityManagerImpl.getVariableInstanceDataManager());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#create(String, VariableType, Object)} with {@code
   * String}, {@code VariableType}, {@code Object}.
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#create(String, VariableType,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "VariableInstanceEntity VariableInstanceEntityManagerImpl.create(String, VariableType, Object)"
  })
  public void testCreateWithStringVariableTypeObject() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    when(variableInstanceDataManager.create()).thenReturn(variableInstanceEntityImpl);
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    // Act
    VariableInstanceEntity actualCreateResult =
        variableInstanceEntityManagerImpl.create("Name", new ByteArrayType(), null);

    // Assert
    verify(variableInstanceDataManager).create();
    assertSame(variableInstanceEntityImpl, actualCreateResult);
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#create(String, VariableType, Object)} with {@code
   * String}, {@code VariableType}, {@code Object}.
   *
   * <ul>
   *   <li>Then return {@link VariableInstanceEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#create(String, VariableType,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "VariableInstanceEntity VariableInstanceEntityManagerImpl.create(String, VariableType, Object)"
  })
  public void testCreateWithStringVariableTypeObject_thenReturnVariableInstanceEntityImpl() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    when(variableInstanceDataManager.create()).thenReturn(variableInstanceEntityImpl);

    // Act
    VariableInstanceEntity actualCreateResult =
        variableInstanceEntityManagerImpl.create("Name", new BigDecimalType(), JSONObject.NULL);

    // Assert
    verify(variableInstanceDataManager).create();
    assertSame(variableInstanceEntityImpl, actualCreateResult);
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)} with
   * {@code VariableInstanceEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.insert(VariableInstanceEntity, boolean)"
  })
  public void testInsertWithVariableInstanceEntityBoolean() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());

    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration, variableInstanceDataManager);

    // Act
    variableInstanceEntityManagerImpl.insert(new VariableInstanceEntityImpl(), true);

    // Assert
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)} with
   * {@code VariableInstanceEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.insert(VariableInstanceEntity, boolean)"
  })
  public void testInsertWithVariableInstanceEntityBoolean2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());

    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration, variableInstanceDataManager);

    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, true);

    // Assert
    verify(entity).getExecutionId();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)} with
   * {@code VariableInstanceEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.insert(VariableInstanceEntity, boolean)"
  })
  public void testInsertWithVariableInstanceEntityBoolean3() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());

    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration, variableInstanceDataManager);

    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(entity, atLeast(1)).getExecutionId();
    verify(executionDataManager).findById("42");
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)} with
   * {@code VariableInstanceEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.insert(VariableInstanceEntity, boolean)"
  })
  public void testInsertWithVariableInstanceEntityBoolean4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());

    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration, variableInstanceDataManager);

    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(executionEntityManager).findById("42");
    verify(entity, atLeast(1)).getExecutionId();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)} with
   * {@code VariableInstanceEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.insert(VariableInstanceEntity, boolean)"
  })
  public void testInsertWithVariableInstanceEntityBoolean_thenCallsDispatchEvent() {
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

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());

    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
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
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(executionEntityManager).findById("42");
    verify(entity, atLeast(1)).getExecutionId();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)} with
   * {@code VariableInstanceEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.insert(VariableInstanceEntity, boolean)"
  })
  public void testInsertWithVariableInstanceEntityBoolean_thenCallsFindById() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());

    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration, variableInstanceDataManager);

    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(executionEntityManager).findById("42");
    verify(entity, atLeast(1)).getExecutionId();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)} with
   * {@code VariableInstanceEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getVariableCount()}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.insert(VariableInstanceEntity, boolean)"
  })
  public void testInsertWithVariableInstanceEntityBoolean_thenCallsGetVariableCount() {
    // Arrange
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
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(executionEntityManagerImpl);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());

    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration, variableInstanceDataManager);

    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(executionEntityImpl).getVariableCount();
    verify(executionEntityImpl).isCountEnabled();
    verify(executionEntityImpl).setVariableCount(4);
    verify(entity, atLeast(1)).getExecutionId();
    verify(executionDataManager).findById("42");
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity, boolean)} with
   * {@code VariableInstanceEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>When {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#insert(VariableInstanceEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.insert(VariableInstanceEntity, boolean)"
  })
  public void testInsertWithVariableInstanceEntityBoolean_whenFalse() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(mock(ActivitiEventDispatcher.class));
    when(processEngineConfiguration.setEventDispatcher(Mockito.<ActivitiEventDispatcher>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).insert(Mockito.<VariableInstanceEntity>any());

    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration, variableInstanceDataManager);

    VariableInstanceEntityImpl entity = mock(VariableInstanceEntityImpl.class);
    when(entity.getExecutionId()).thenReturn("42");

    // Act
    variableInstanceEntityManagerImpl.insert(entity, false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(processEngineConfiguration).setEventDispatcher(isA(ActivitiEventDispatcher.class));
    verify(executionEntityManager).findById("42");
    verify(entity, atLeast(1)).getExecutionId();
    verify(variableInstanceDataManager).insert(isA(VariableInstanceEntity.class));
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskId(String)}.
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByTaskId(String)"
  })
  public void testFindVariableInstancesByTaskId() {
    // Arrange
    when(variableInstanceDataManager.findVariableInstancesByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskIdResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByTaskId("42");

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskId("42");
    assertTrue(actualFindVariableInstancesByTaskIdResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List VariableInstanceEntityManagerImpl.findVariableInstancesByTaskIds(Set)"})
  public void testFindVariableInstancesByTaskIds_given42_whenHashSetAdd42_thenReturnEmpty() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByTaskIds(Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    HashSet<String> taskIds = new HashSet<>();
    taskIds.add("42");
    taskIds.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskIdsResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByTaskIds(taskIds);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByTaskIdsResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List VariableInstanceEntityManagerImpl.findVariableInstancesByTaskIds(Set)"})
  public void testFindVariableInstancesByTaskIds_givenFoo_whenHashSetAddFoo_thenReturnEmpty() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByTaskIds(Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    HashSet<String> taskIds = new HashSet<>();
    taskIds.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskIdsResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByTaskIds(taskIds);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByTaskIdsResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}.
   *
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByTaskIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List VariableInstanceEntityManagerImpl.findVariableInstancesByTaskIds(Set)"})
  public void testFindVariableInstancesByTaskIds_whenHashSet_thenReturnEmpty() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByTaskIds(Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskIdsResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByTaskIds(new HashSet<>());

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByTaskIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByTaskIdsResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionId(String)}.
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByExecutionId(String)"
  })
  public void testFindVariableInstancesByExecutionId() {
    // Arrange
    when(variableInstanceDataManager.findVariableInstancesByExecutionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionIdResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByExecutionId("42");

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionId("42");
    assertTrue(actualFindVariableInstancesByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByExecutionIds(Set)"
  })
  public void testFindVariableInstancesByExecutionIds_given42_whenHashSetAdd42_thenReturnEmpty() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByExecutionIds(
            Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    HashSet<String> executionIds = new HashSet<>();
    executionIds.add("42");
    executionIds.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionIdsResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByExecutionIds(executionIds);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByExecutionIdsResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByExecutionIds(Set)"
  })
  public void testFindVariableInstancesByExecutionIds_givenFoo_whenHashSetAddFoo() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByExecutionIds(
            Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    HashSet<String> executionIds = new HashSet<>();
    executionIds.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionIdsResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByExecutionIds(executionIds);

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByExecutionIdsResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}.
   *
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByExecutionIds(Set)"
  })
  public void testFindVariableInstancesByExecutionIds_whenHashSet_thenReturnEmpty() {
    // Arrange
    VariableInstanceDataManager variableInstanceDataManager =
        mock(VariableInstanceDataManager.class);
    when(variableInstanceDataManager.findVariableInstancesByExecutionIds(
            Mockito.<Set<String>>any()))
        .thenReturn(new ArrayList<>());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionIdsResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByExecutionIds(new HashSet<>());

    // Assert
    verify(variableInstanceDataManager).findVariableInstancesByExecutionIds(isA(Set.class));
    assertTrue(actualFindVariableInstancesByExecutionIdsResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstanceByExecutionAndName(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstanceByExecutionAndName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "VariableInstanceEntity VariableInstanceEntityManagerImpl.findVariableInstanceByExecutionAndName(String, String)"
  })
  public void testFindVariableInstanceByExecutionAndName() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    when(variableInstanceDataManager.findVariableInstanceByExecutionAndName(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(variableInstanceEntityImpl);

    // Act
    VariableInstanceEntity actualFindVariableInstanceByExecutionAndNameResult =
        variableInstanceEntityManagerImpl.findVariableInstanceByExecutionAndName(
            "42", "Variable Name");

    // Assert
    verify(variableInstanceDataManager)
        .findVariableInstanceByExecutionAndName("42", "Variable Name");
    assertSame(variableInstanceEntityImpl, actualFindVariableInstanceByExecutionAndNameResult);
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionAndNames(String,
   * Collection)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionAndNames(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByExecutionAndNames(String, Collection)"
  })
  public void testFindVariableInstancesByExecutionAndNames_given42_whenArrayListAdd42() {
    // Arrange
    when(variableInstanceDataManager.findVariableInstancesByExecutionAndNames(
            Mockito.<String>any(), Mockito.<Collection<String>>any()))
        .thenReturn(new ArrayList<>());

    ArrayList<String> names = new ArrayList<>();
    names.add("42");
    names.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionAndNamesResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByExecutionAndNames("42", names);

    // Assert
    verify(variableInstanceDataManager)
        .findVariableInstancesByExecutionAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByExecutionAndNamesResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionAndNames(String,
   * Collection)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionAndNames(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByExecutionAndNames(String, Collection)"
  })
  public void testFindVariableInstancesByExecutionAndNames_givenFoo_whenArrayListAddFoo() {
    // Arrange
    when(variableInstanceDataManager.findVariableInstancesByExecutionAndNames(
            Mockito.<String>any(), Mockito.<Collection<String>>any()))
        .thenReturn(new ArrayList<>());

    ArrayList<String> names = new ArrayList<>();
    names.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionAndNamesResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByExecutionAndNames("42", names);

    // Assert
    verify(variableInstanceDataManager)
        .findVariableInstancesByExecutionAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByExecutionAndNamesResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionAndNames(String,
   * Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByExecutionAndNames(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByExecutionAndNames(String, Collection)"
  })
  public void testFindVariableInstancesByExecutionAndNames_whenArrayList() {
    // Arrange
    when(variableInstanceDataManager.findVariableInstancesByExecutionAndNames(
            Mockito.<String>any(), Mockito.<Collection<String>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByExecutionAndNamesResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByExecutionAndNames(
            "42", new ArrayList<>());

    // Assert
    verify(variableInstanceDataManager)
        .findVariableInstancesByExecutionAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByExecutionAndNamesResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstanceByTaskAndName(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstanceByTaskAndName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "VariableInstanceEntity VariableInstanceEntityManagerImpl.findVariableInstanceByTaskAndName(String, String)"
  })
  public void testFindVariableInstanceByTaskAndName() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    when(variableInstanceDataManager.findVariableInstanceByTaskAndName(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(variableInstanceEntityImpl);

    // Act
    VariableInstanceEntity actualFindVariableInstanceByTaskAndNameResult =
        variableInstanceEntityManagerImpl.findVariableInstanceByTaskAndName("42", "Variable Name");

    // Assert
    verify(variableInstanceDataManager).findVariableInstanceByTaskAndName("42", "Variable Name");
    assertSame(variableInstanceEntityImpl, actualFindVariableInstanceByTaskAndNameResult);
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskAndNames(String,
   * Collection)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByTaskAndNames(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByTaskAndNames(String, Collection)"
  })
  public void testFindVariableInstancesByTaskAndNames_given42_whenArrayListAdd42() {
    // Arrange
    when(variableInstanceDataManager.findVariableInstancesByTaskAndNames(
            Mockito.<String>any(), Mockito.<Collection<String>>any()))
        .thenReturn(new ArrayList<>());

    ArrayList<String> names = new ArrayList<>();
    names.add("42");
    names.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskAndNamesResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByTaskAndNames("42", names);

    // Assert
    verify(variableInstanceDataManager)
        .findVariableInstancesByTaskAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByTaskAndNamesResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskAndNames(String,
   * Collection)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByTaskAndNames(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByTaskAndNames(String, Collection)"
  })
  public void testFindVariableInstancesByTaskAndNames_givenFoo_whenArrayListAddFoo() {
    // Arrange
    when(variableInstanceDataManager.findVariableInstancesByTaskAndNames(
            Mockito.<String>any(), Mockito.<Collection<String>>any()))
        .thenReturn(new ArrayList<>());

    ArrayList<String> names = new ArrayList<>();
    names.add("foo");

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskAndNamesResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByTaskAndNames("42", names);

    // Assert
    verify(variableInstanceDataManager)
        .findVariableInstancesByTaskAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByTaskAndNamesResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#findVariableInstancesByTaskAndNames(String,
   * Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#findVariableInstancesByTaskAndNames(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableInstanceEntityManagerImpl.findVariableInstancesByTaskAndNames(String, Collection)"
  })
  public void testFindVariableInstancesByTaskAndNames_whenArrayList() {
    // Arrange
    when(variableInstanceDataManager.findVariableInstancesByTaskAndNames(
            Mockito.<String>any(), Mockito.<Collection<String>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<VariableInstanceEntity> actualFindVariableInstancesByTaskAndNamesResult =
        variableInstanceEntityManagerImpl.findVariableInstancesByTaskAndNames(
            "42", new ArrayList<>());

    // Assert
    verify(variableInstanceDataManager)
        .findVariableInstancesByTaskAndNames(eq("42"), isA(Collection.class));
    assertTrue(actualFindVariableInstancesByTaskAndNamesResult.isEmpty());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)} with
   * {@code VariableInstanceEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.delete(VariableInstanceEntity, boolean)"
  })
  public void testDeleteWithVariableInstanceEntityBoolean() {
    // Arrange
    MybatisVariableInstanceDataManager variableInstanceDataManager =
        mock(MybatisVariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    VariableInstanceEntityImpl entity = new VariableInstanceEntityImpl();
    entity.setBytes(null);
    entity.setExecutionId(null);
    entity.setProcessInstanceId(null);
    Class<Object> theClass = Object.class;
    entity.setType(new CustomObjectType("jpa-entity", theClass));

    // Act
    variableInstanceEntityManagerImpl.delete(entity, false);

    // Assert
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity, boolean)} with
   * {@code VariableInstanceEntity}, {@code boolean}.
   *
   * <p>Method under test: {@link VariableInstanceEntityManagerImpl#delete(VariableInstanceEntity,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.delete(VariableInstanceEntity, boolean)"
  })
  public void testDeleteWithVariableInstanceEntityBoolean2() {
    // Arrange
    MybatisVariableInstanceDataManager variableInstanceDataManager =
        mock(MybatisVariableInstanceDataManager.class);
    doNothing().when(variableInstanceDataManager).delete(Mockito.<VariableInstanceEntity>any());
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), variableInstanceDataManager);

    VariableInstanceEntityImpl entity = new VariableInstanceEntityImpl();
    entity.setBytes(null);
    entity.setExecutionId("42");
    entity.setProcessInstanceId(null);
    Class<Object> theClass = Object.class;
    entity.setType(new CustomObjectType("jpa-entity", theClass));

    // Act
    variableInstanceEntityManagerImpl.delete(entity, false);

    // Assert
    verify(variableInstanceDataManager).delete(isA(VariableInstanceEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link
   * VariableInstanceEntityManagerImpl#createVariableDeleteEvent(VariableInstanceEntity)}.
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#createVariableDeleteEvent(VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiVariableEvent VariableInstanceEntityManagerImpl.createVariableDeleteEvent(VariableInstanceEntity)"
  })
  public void testCreateVariableDeleteEvent() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();
    variableInstance.setProcessInstanceId(null);
    Class<Object> theClass = Object.class;
    CustomObjectType type = new CustomObjectType("jpa-entity", theClass);
    variableInstance.setType(type);

    // Act
    ActivitiVariableEvent actualCreateVariableDeleteEventResult =
        variableInstanceEntityManagerImpl.createVariableDeleteEvent(variableInstance);

    // Assert
    assertTrue(actualCreateVariableDeleteEventResult instanceof ActivitiVariableEventImpl);
    assertSame(type, actualCreateVariableDeleteEventResult.getVariableType());
  }

  /**
   * Test {@link
   * VariableInstanceEntityManagerImpl#createVariableDeleteEvent(VariableInstanceEntity)}.
   *
   * <ul>
   *   <li>Then return VariableType is {@link BigDecimalType} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#createVariableDeleteEvent(VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiVariableEvent VariableInstanceEntityManagerImpl.createVariableDeleteEvent(VariableInstanceEntity)"
  })
  public void testCreateVariableDeleteEvent_thenReturnVariableTypeIsBigDecimalType() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();
    BigDecimalType type = new BigDecimalType();
    variableInstance.setType(type);

    // Act
    ActivitiVariableEvent actualCreateVariableDeleteEventResult =
        variableInstanceEntityManagerImpl.createVariableDeleteEvent(variableInstance);

    // Assert
    assertTrue(actualCreateVariableDeleteEventResult instanceof ActivitiVariableEventImpl);
    assertSame(type, actualCreateVariableDeleteEventResult.getVariableType());
  }

  /**
   * Test {@link
   * VariableInstanceEntityManagerImpl#createVariableDeleteEvent(VariableInstanceEntity)}.
   *
   * <ul>
   *   <li>Then return VariableType is {@link ByteArrayType} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#createVariableDeleteEvent(VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiVariableEvent VariableInstanceEntityManagerImpl.createVariableDeleteEvent(VariableInstanceEntity)"
  })
  public void testCreateVariableDeleteEvent_thenReturnVariableTypeIsByteArrayType() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();
    ByteArrayType type = new ByteArrayType();
    variableInstance.setType(type);

    // Act
    ActivitiVariableEvent actualCreateVariableDeleteEventResult =
        variableInstanceEntityManagerImpl.createVariableDeleteEvent(variableInstance);

    // Assert
    assertTrue(actualCreateVariableDeleteEventResult instanceof ActivitiVariableEventImpl);
    assertSame(type, actualCreateVariableDeleteEventResult.getVariableType());
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.deleteVariableInstanceByTask(TaskEntity)"
  })
  public void testDeleteVariableInstanceByTask_givenHashMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    TaskEntity task = mock(TaskEntity.class);
    when(task.getVariableInstanceEntities()).thenReturn(new HashMap<>());

    // Act
    variableInstanceEntityManagerImpl.deleteVariableInstanceByTask(task);

    // Assert
    verify(task).getVariableInstanceEntities();
  }

  /**
   * Test {@link VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableInstanceEntityManagerImpl#deleteVariableInstanceByTask(TaskEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceEntityManagerImpl.deleteVariableInstanceByTask(TaskEntity)"
  })
  public void testDeleteVariableInstanceByTask_givenNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManagerImpl =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    TaskEntity task = mock(TaskEntity.class);
    when(task.getVariableInstanceEntities()).thenReturn(null);

    // Act
    variableInstanceEntityManagerImpl.deleteVariableInstanceByTask(task);

    // Assert
    verify(task).getVariableInstanceEntities();
  }
}
