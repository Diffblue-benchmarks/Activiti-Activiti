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
import static org.junit.Assert.assertFalse;
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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.HasExecutionListeners;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.ExecutionQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.ProcessInstanceQueryImpl;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.DeadLetterJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.EventSubscriptionDataManager;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.JobDataManager;
import org.activiti.engine.impl.persistence.entity.data.SuspendedJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.TaskDataManager;
import org.activiti.engine.impl.persistence.entity.data.TimerJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricProcessInstanceDataManager;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExecutionEntityManagerImplDiffblueTest {
  @Mock
  private ExecutionDataManager executionDataManager;

  @InjectMocks
  private ExecutionEntityManagerImpl executionEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutionEntityManagerImpl#ExecutionEntityManagerImpl(ProcessEngineConfigurationImpl, ExecutionDataManager)}
   *   <li>{@link ExecutionEntityManagerImpl#setExecutionDataManager(ExecutionDataManager)}
   *   <li>{@link ExecutionEntityManagerImpl#getDataManager()}
   *   <li>{@link ExecutionEntityManagerImpl#getExecutionDataManager()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, ExecutionDataManager)",
      "DataManager ExecutionEntityManagerImpl.getDataManager()",
      "ExecutionDataManager ExecutionEntityManagerImpl.getExecutionDataManager()",
      "void ExecutionEntityManagerImpl.setExecutionDataManager(ExecutionDataManager)"})
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ExecutionEntityManagerImpl actualExecutionEntityManagerImpl = new ExecutionEntityManagerImpl(
        processEngineConfiguration, new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    MybatisExecutionDataManager executionDataManager = new MybatisExecutionDataManager(
        new JtaProcessEngineConfiguration());
    actualExecutionEntityManagerImpl.setExecutionDataManager(executionDataManager);
    DataManager<ExecutionEntity> actualDataManager = actualExecutionEntityManagerImpl.getDataManager();

    // Assert
    assertSame(executionDataManager, actualDataManager);
    assertSame(executionDataManager, actualExecutionEntityManagerImpl.getExecutionDataManager());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity)} with {@code ExecutionEntity}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity)"})
  public void testDeleteWithExecutionEntity() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executionEntityManagerImpl.delete(entity);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)} with {@code ExecutionEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity, boolean)"})
  public void testDeleteWithExecutionEntityBoolean() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executionEntityManagerImpl.delete(entity, true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)} with {@code ExecutionEntity}, {@code boolean}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity, boolean)"})
  public void testDeleteWithExecutionEntityBoolean2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executionEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)} with {@code ExecutionEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity, boolean)"})
  public void testDeleteWithExecutionEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executionEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)} with {@code ExecutionEntity}, {@code boolean}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity, boolean)"})
  public void testDeleteWithExecutionEntityBoolean_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl entity = mock(ExecutionEntityImpl.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(entity).setDeleted(anyBoolean());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl.delete(entity, true));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(entity).setDeleted(eq(true));
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)} with {@code ExecutionEntity}, {@code boolean}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity, boolean)"})
  public void testDeleteWithExecutionEntityBoolean_whenFalse() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        mock(ProcessEngineConfigurationImpl.class), executionDataManager);
    ExecutionEntityImpl entity = mock(ExecutionEntityImpl.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(entity).setDeleted(anyBoolean());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl.delete(entity, false));
    verify(entity).setDeleted(eq(true));
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity)} with {@code ExecutionEntity}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity)"})
  public void testDeleteWithExecutionEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executionEntityManagerImpl.delete(entity);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity)} with {@code ExecutionEntity}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity)"})
  public void testDeleteWithExecutionEntity_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executionEntityManagerImpl.delete(entity);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity)} with {@code ExecutionEntity}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity)"})
  public void testDeleteWithExecutionEntity_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl entity = mock(ExecutionEntityImpl.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(entity).setDeleted(anyBoolean());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl.delete(entity));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(entity).setDeleted(eq(true));
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findSubProcessInstanceBySuperExecutionId(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findSubProcessInstanceBySuperExecutionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findSubProcessInstanceBySuperExecutionId(String)"})
  public void testFindSubProcessInstanceBySuperExecutionId() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(executionDataManager.findSubProcessInstanceBySuperExecutionId(Mockito.<String>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    // Act
    ExecutionEntity actualFindSubProcessInstanceBySuperExecutionIdResult = executionEntityManagerImpl
        .findSubProcessInstanceBySuperExecutionId("42");

    // Assert
    verify(executionDataManager).findSubProcessInstanceBySuperExecutionId(eq("42"));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualFindSubProcessInstanceBySuperExecutionIdResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findSubProcessInstanceBySuperExecutionId(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findSubProcessInstanceBySuperExecutionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findSubProcessInstanceBySuperExecutionId(String)"})
  public void testFindSubProcessInstanceBySuperExecutionId2() {
    // Arrange
    when(executionDataManager.findSubProcessInstanceBySuperExecutionId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findSubProcessInstanceBySuperExecutionId("42"));
    verify(executionDataManager).findSubProcessInstanceBySuperExecutionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findChildExecutionsByParentExecutionId(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findChildExecutionsByParentExecutionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.findChildExecutionsByParentExecutionId(String)"})
  public void testFindChildExecutionsByParentExecutionId() {
    // Arrange
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findChildExecutionsByParentExecutionId("42"));
    verify(executionDataManager).findChildExecutionsByParentExecutionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findChildExecutionsByParentExecutionId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findChildExecutionsByParentExecutionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.findChildExecutionsByParentExecutionId(String)"})
  public void testFindChildExecutionsByParentExecutionId_thenReturnEmpty() {
    // Arrange
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<ExecutionEntity> actualFindChildExecutionsByParentExecutionIdResult = executionEntityManagerImpl
        .findChildExecutionsByParentExecutionId("42");

    // Assert
    verify(executionDataManager).findChildExecutionsByParentExecutionId(eq("42"));
    assertTrue(actualFindChildExecutionsByParentExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findChildExecutionsByProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findChildExecutionsByProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.findChildExecutionsByProcessInstanceId(String)"})
  public void testFindChildExecutionsByProcessInstanceId() {
    // Arrange
    when(executionDataManager.findChildExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findChildExecutionsByProcessInstanceId("42"));
    verify(executionDataManager).findChildExecutionsByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findChildExecutionsByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findChildExecutionsByProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.findChildExecutionsByProcessInstanceId(String)"})
  public void testFindChildExecutionsByProcessInstanceId_thenReturnEmpty() {
    // Arrange
    when(executionDataManager.findChildExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<ExecutionEntity> actualFindChildExecutionsByProcessInstanceIdResult = executionEntityManagerImpl
        .findChildExecutionsByProcessInstanceId("42");

    // Assert
    verify(executionDataManager).findChildExecutionsByProcessInstanceId(eq("42"));
    assertTrue(actualFindChildExecutionsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List ExecutionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds(String, Collection)"})
  public void testFindExecutionsByParentExecutionAndActivityIds() {
    // Arrange
    when(executionDataManager.findExecutionsByParentExecutionAndActivityIds(Mockito.<String>any(),
        Mockito.<Collection<String>>any())).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds("42", new ArrayList<>()));
    verify(executionDataManager).findExecutionsByParentExecutionAndActivityIds(eq("42"), isA(Collection.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List ExecutionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds(String, Collection)"})
  public void testFindExecutionsByParentExecutionAndActivityIds_given42_whenArrayListAdd42() {
    // Arrange
    when(executionDataManager.findExecutionsByParentExecutionAndActivityIds(Mockito.<String>any(),
        Mockito.<Collection<String>>any())).thenReturn(new ArrayList<>());

    ArrayList<String> activityIds = new ArrayList<>();
    activityIds.add("42");
    activityIds.add("foo");

    // Act
    List<ExecutionEntity> actualFindExecutionsByParentExecutionAndActivityIdsResult = executionEntityManagerImpl
        .findExecutionsByParentExecutionAndActivityIds("42", activityIds);

    // Assert
    verify(executionDataManager).findExecutionsByParentExecutionAndActivityIds(eq("42"), isA(Collection.class));
    assertTrue(actualFindExecutionsByParentExecutionAndActivityIdsResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List ExecutionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds(String, Collection)"})
  public void testFindExecutionsByParentExecutionAndActivityIds_givenFoo_whenArrayListAddFoo() {
    // Arrange
    when(executionDataManager.findExecutionsByParentExecutionAndActivityIds(Mockito.<String>any(),
        Mockito.<Collection<String>>any())).thenReturn(new ArrayList<>());

    ArrayList<String> activityIds = new ArrayList<>();
    activityIds.add("foo");

    // Act
    List<ExecutionEntity> actualFindExecutionsByParentExecutionAndActivityIdsResult = executionEntityManagerImpl
        .findExecutionsByParentExecutionAndActivityIds("42", activityIds);

    // Assert
    verify(executionDataManager).findExecutionsByParentExecutionAndActivityIds(eq("42"), isA(Collection.class));
    assertTrue(actualFindExecutionsByParentExecutionAndActivityIdsResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List ExecutionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds(String, Collection)"})
  public void testFindExecutionsByParentExecutionAndActivityIds_whenArrayList_thenReturnEmpty() {
    // Arrange
    when(executionDataManager.findExecutionsByParentExecutionAndActivityIds(Mockito.<String>any(),
        Mockito.<Collection<String>>any())).thenReturn(new ArrayList<>());

    // Act
    List<ExecutionEntity> actualFindExecutionsByParentExecutionAndActivityIdsResult = executionEntityManagerImpl
        .findExecutionsByParentExecutionAndActivityIds("42", new ArrayList<>());

    // Assert
    verify(executionDataManager).findExecutionsByParentExecutionAndActivityIds(eq("42"), isA(Collection.class));
    assertTrue(actualFindExecutionsByParentExecutionAndActivityIdsResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionCountByQueryCriteria(ExecutionQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findExecutionCountByQueryCriteria(ExecutionQueryImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long ExecutionEntityManagerImpl.findExecutionCountByQueryCriteria(ExecutionQueryImpl)"})
  public void testFindExecutionCountByQueryCriteria_thenReturnThree() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionCountByQueryCriteria(Mockito.<ExecutionQueryImpl>any())).thenReturn(3L);
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    long actualFindExecutionCountByQueryCriteriaResult = executionEntityManagerImpl
        .findExecutionCountByQueryCriteria(new ExecutionQueryImpl());

    // Assert
    verify(executionDataManager).findExecutionCountByQueryCriteria(isA(ExecutionQueryImpl.class));
    assertEquals(3L, actualFindExecutionCountByQueryCriteriaResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByQueryCriteria(ExecutionQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findExecutionsByQueryCriteria(ExecutionQueryImpl, Page)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.findExecutionsByQueryCriteria(ExecutionQueryImpl, Page)"})
  public void testFindExecutionsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByQueryCriteria(Mockito.<ExecutionQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);
    ExecutionQueryImpl executionQuery = new ExecutionQueryImpl();

    // Act
    List<ExecutionEntity> actualFindExecutionsByQueryCriteriaResult = executionEntityManagerImpl
        .findExecutionsByQueryCriteria(executionQuery, new Page(1, 3));

    // Assert
    verify(executionDataManager).findExecutionsByQueryCriteria(isA(ExecutionQueryImpl.class), isA(Page.class));
    assertTrue(actualFindExecutionsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findProcessInstanceCountByQueryCriteria(ProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findProcessInstanceCountByQueryCriteria(ProcessInstanceQueryImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "long ExecutionEntityManagerImpl.findProcessInstanceCountByQueryCriteria(ProcessInstanceQueryImpl)"})
  public void testFindProcessInstanceCountByQueryCriteria_thenReturnThree() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findProcessInstanceCountByQueryCriteria(Mockito.<ProcessInstanceQueryImpl>any()))
        .thenReturn(3L);
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    long actualFindProcessInstanceCountByQueryCriteriaResult = executionEntityManagerImpl
        .findProcessInstanceCountByQueryCriteria(new ProcessInstanceQueryImpl());

    // Assert
    verify(executionDataManager).findProcessInstanceCountByQueryCriteria(isA(ProcessInstanceQueryImpl.class));
    assertEquals(3L, actualFindProcessInstanceCountByQueryCriteriaResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findProcessInstanceByQueryCriteria(ProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findProcessInstanceByQueryCriteria(ProcessInstanceQueryImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.findProcessInstanceByQueryCriteria(ProcessInstanceQueryImpl)"})
  public void testFindProcessInstanceByQueryCriteria_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findProcessInstanceByQueryCriteria(Mockito.<ProcessInstanceQueryImpl>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<ProcessInstance> actualFindProcessInstanceByQueryCriteriaResult = executionEntityManagerImpl
        .findProcessInstanceByQueryCriteria(new ProcessInstanceQueryImpl());

    // Assert
    verify(executionDataManager).findProcessInstanceByQueryCriteria(isA(ProcessInstanceQueryImpl.class));
    assertTrue(actualFindProcessInstanceByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"})
  public void testFindByRootProcessInstanceId() {
    // Arrange
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findByRootProcessInstanceId("42"));
    verify(executionDataManager).findExecutionsByRootProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"})
  public void testFindByRootProcessInstanceId2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(executionEntityImpl)
        .setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntityImpl);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findByRootProcessInstanceId("42"));
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
    verify(executionDataManager).findExecutionsByRootProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"})
  public void testFindByRootProcessInstanceId3() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn(null);
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntityImpl);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    executionEntityManagerImpl.findByRootProcessInstanceId("42");

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
    verify(executionDataManager).findExecutionsByRootProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"})
  public void testFindByRootProcessInstanceId4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn(null);
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntityImpl);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    executionEntityManagerImpl.findByRootProcessInstanceId("42");

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
    verify(executionDataManager).findExecutionsByRootProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"})
  public void testFindByRootProcessInstanceId5() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution()).thenReturn(null);
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntityImpl);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    executionEntityManagerImpl.findByRootProcessInstanceId("42");

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionDataManager).findExecutionsByRootProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getParentId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"})
  public void testFindByRootProcessInstanceId_givenExecutionEntityImplGetParentIdReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn(null);
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntityImpl);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    executionEntityManagerImpl.findByRootProcessInstanceId("42");

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
    verify(executionDataManager).findExecutionsByRootProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#addChildExecution(ExecutionEntity)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"})
  public void testFindByRootProcessInstanceId_thenCallsAddChildExecution() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntityImpl);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    executionEntityManagerImpl.findByRootProcessInstanceId("42");

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
    verify(executionDataManager).findExecutionsByRootProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"})
  public void testFindByRootProcessInstanceId_thenReturnNull() {
    // Arrange
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    ExecutionEntity actualFindByRootProcessInstanceIdResult = executionEntityManagerImpl
        .findByRootProcessInstanceId("42");

    // Assert
    verify(executionDataManager).findExecutionsByRootProcessInstanceId(eq("42"));
    assertNull(actualFindByRootProcessInstanceIdResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"})
  public void testProcessExecutionTree() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn(null);
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act
    executionEntityManagerImpl.processExecutionTree("42", executions);

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getParentId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"})
  public void testProcessExecutionTree_givenExecutionEntityImplGetParentIdReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn(null);
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act
    executionEntityManagerImpl.processExecutionTree("42", executions);

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getProcessInstanceId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"})
  public void testProcessExecutionTree_givenExecutionEntityImplGetProcessInstanceIdReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn(null);
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act
    executionEntityManagerImpl.processExecutionTree("42", executions);

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getSuperExecution()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"})
  public void testProcessExecutionTree_givenExecutionEntityImplGetSuperExecutionReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution()).thenReturn(null);
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act
    executionEntityManagerImpl.processExecutionTree("42", executions);

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#addChildExecution(ExecutionEntity)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"})
  public void testProcessExecutionTree_thenCallsAddChildExecution() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act
    executionEntityManagerImpl.processExecutionTree("42", executions);

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"})
  public void testProcessExecutionTree_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(executionEntityImpl)
        .setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.processExecutionTree("42", executions));
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"})
  public void testProcessExecutionTree_whenArrayList_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(executionEntityManagerImpl.processExecutionTree("42", new ArrayList<>()));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   * <ul>
   *   <li>When {@code Root Process Instance Id}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"})
  public void testProcessExecutionTree_whenRootProcessInstanceId_thenReturnNull() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getSuperExecutionId()).thenReturn("42");
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setParent(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setRootProcessInstance(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getParentId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act
    ExecutionEntity actualProcessExecutionTreeResult = executionEntityManagerImpl
        .processExecutionTree("Root Process Instance Id", executions);

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
    assertNull(actualProcessExecutionTreeResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List ExecutionEntityManagerImpl.findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)"})
  public void testFindProcessInstanceAndVariablesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findProcessInstanceAndVariablesByQueryCriteria(Mockito.<ProcessInstanceQueryImpl>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<ProcessInstance> actualFindProcessInstanceAndVariablesByQueryCriteriaResult = executionEntityManagerImpl
        .findProcessInstanceAndVariablesByQueryCriteria(new ProcessInstanceQueryImpl());

    // Assert
    verify(executionDataManager).findProcessInstanceAndVariablesByQueryCriteria(isA(ProcessInstanceQueryImpl.class));
    assertTrue(actualFindProcessInstanceAndVariablesByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findInactiveExecutionsByProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findInactiveExecutionsByProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ExecutionEntityManagerImpl.findInactiveExecutionsByProcessInstanceId(String)"})
  public void testFindInactiveExecutionsByProcessInstanceId() {
    // Arrange
    when(executionDataManager.findInactiveExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findInactiveExecutionsByProcessInstanceId("42"));
    verify(executionDataManager).findInactiveExecutionsByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findInactiveExecutionsByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then return {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findInactiveExecutionsByProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Collection ExecutionEntityManagerImpl.findInactiveExecutionsByProcessInstanceId(String)"})
  public void testFindInactiveExecutionsByProcessInstanceId_thenReturnList() {
    // Arrange
    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    when(executionDataManager.findInactiveExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);

    // Act
    Collection<ExecutionEntity> actualFindInactiveExecutionsByProcessInstanceIdResult = executionEntityManagerImpl
        .findInactiveExecutionsByProcessInstanceId("42");

    // Assert
    verify(executionDataManager).findInactiveExecutionsByProcessInstanceId(eq("42"));
    assertTrue(actualFindInactiveExecutionsByProcessInstanceIdResult instanceof List);
    assertTrue(actualFindInactiveExecutionsByProcessInstanceIdResult.isEmpty());
    assertSame(executionEntityList, actualFindInactiveExecutionsByProcessInstanceIdResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findInactiveExecutionsByActivityIdAndProcessInstanceId(String, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findInactiveExecutionsByActivityIdAndProcessInstanceId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "Collection ExecutionEntityManagerImpl.findInactiveExecutionsByActivityIdAndProcessInstanceId(String, String)"})
  public void testFindInactiveExecutionsByActivityIdAndProcessInstanceId() {
    // Arrange
    when(executionDataManager.findInactiveExecutionsByActivityIdAndProcessInstanceId(Mockito.<String>any(),
        Mockito.<String>any())).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findInactiveExecutionsByActivityIdAndProcessInstanceId("42", "42"));
    verify(executionDataManager).findInactiveExecutionsByActivityIdAndProcessInstanceId(eq("42"), eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findInactiveExecutionsByActivityIdAndProcessInstanceId(String, String)}.
   * <ul>
   *   <li>Then return {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findInactiveExecutionsByActivityIdAndProcessInstanceId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "Collection ExecutionEntityManagerImpl.findInactiveExecutionsByActivityIdAndProcessInstanceId(String, String)"})
  public void testFindInactiveExecutionsByActivityIdAndProcessInstanceId_thenReturnList() {
    // Arrange
    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    when(executionDataManager.findInactiveExecutionsByActivityIdAndProcessInstanceId(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(executionEntityList);

    // Act
    Collection<ExecutionEntity> actualFindInactiveExecutionsByActivityIdAndProcessInstanceIdResult = executionEntityManagerImpl
        .findInactiveExecutionsByActivityIdAndProcessInstanceId("42", "42");

    // Assert
    verify(executionDataManager).findInactiveExecutionsByActivityIdAndProcessInstanceId(eq("42"), eq("42"));
    assertTrue(actualFindInactiveExecutionsByActivityIdAndProcessInstanceIdResult instanceof List);
    assertTrue(actualFindInactiveExecutionsByActivityIdAndProcessInstanceIdResult.isEmpty());
    assertSame(executionEntityList, actualFindInactiveExecutionsByActivityIdAndProcessInstanceIdResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findExecutionsByNativeQuery(Map, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.findExecutionsByNativeQuery(Map, int, int)"})
  public void testFindExecutionsByNativeQuery_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<Execution> actualFindExecutionsByNativeQueryResult = executionEntityManagerImpl
        .findExecutionsByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(executionDataManager).findExecutionsByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindExecutionsByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findProcessInstanceByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findProcessInstanceByNativeQuery(Map, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.findProcessInstanceByNativeQuery(Map, int, int)"})
  public void testFindProcessInstanceByNativeQuery_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findProcessInstanceByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<ProcessInstance> actualFindProcessInstanceByNativeQueryResult = executionEntityManagerImpl
        .findProcessInstanceByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(executionDataManager).findProcessInstanceByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindProcessInstanceByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findExecutionCountByNativeQuery(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long ExecutionEntityManagerImpl.findExecutionCountByNativeQuery(Map)"})
  public void testFindExecutionCountByNativeQuery_thenReturnThree() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionCountByNativeQuery(Mockito.<Map<String, Object>>any())).thenReturn(3L);
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    long actualFindExecutionCountByNativeQueryResult = executionEntityManagerImpl
        .findExecutionCountByNativeQuery(new HashMap<>());

    // Assert
    verify(executionDataManager).findExecutionCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindExecutionCountByNativeQueryResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"})
  public void testCreateProcessInstanceExecution() {
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
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setAppVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setBusinessKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setCountEnabled(anyBoolean());
    doNothing().when(executionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionName(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setScope(anyBoolean());
    doNothing().when(executionEntityImpl).setStartUserId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    // Act
    executionEntityManagerImpl.createProcessInstanceExecution(new ProcessDefinitionEntityImpl(), "Business Key", "42",
        "Initiator Variable Name");

    // Assert
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey(eq("Business Key"));
    verify(executionEntityImpl).setCountEnabled(eq(true));
    verify(executionEntityImpl).setProcessDefinitionId(isNull());
    verify(executionEntityImpl).setProcessDefinitionKey(isNull());
    verify(executionEntityImpl).setProcessDefinitionName(isNull());
    verify(executionEntityImpl).setProcessDefinitionVersion(eq(0));
    verify(executionEntityImpl).setProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setRootProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setScope(eq(true));
    verify(executionEntityImpl).setStartUserId(isNull());
    verify(executionEntityImpl).setTenantId(eq("42"));
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"})
  public void testCreateProcessInstanceExecution2() {
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
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(activitiEventDispatcher)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setAppVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setBusinessKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setCountEnabled(anyBoolean());
    doNothing().when(executionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionName(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setScope(anyBoolean());
    doNothing().when(executionEntityImpl).setStartUserId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.createProcessInstanceExecution(new ProcessDefinitionEntityImpl(),
            "Business Key", "42", "Initiator Variable Name"));
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey(eq("Business Key"));
    verify(executionEntityImpl).setCountEnabled(eq(true));
    verify(executionEntityImpl).setProcessDefinitionId(isNull());
    verify(executionEntityImpl).setProcessDefinitionKey(isNull());
    verify(executionEntityImpl).setProcessDefinitionName(isNull());
    verify(executionEntityImpl).setProcessDefinitionVersion(eq(0));
    verify(executionEntityImpl).setProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setRootProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setScope(eq(true));
    verify(executionEntityImpl).setStartUserId(isNull());
    verify(executionEntityImpl).setTenantId(eq("42"));
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"})
  public void testCreateProcessInstanceExecution3() {
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
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setAppVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setBusinessKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setCountEnabled(anyBoolean());
    doNothing().when(executionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionName(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setScope(anyBoolean());
    doNothing().when(executionEntityImpl).setStartUserId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    // Act
    executionEntityManagerImpl.createProcessInstanceExecution(new ProcessDefinitionEntityImpl(), "Business Key", "42",
        "Initiator Variable Name");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey(eq("Business Key"));
    verify(executionEntityImpl).setCountEnabled(eq(true));
    verify(executionEntityImpl).setProcessDefinitionId(isNull());
    verify(executionEntityImpl).setProcessDefinitionKey(isNull());
    verify(executionEntityImpl).setProcessDefinitionName(isNull());
    verify(executionEntityImpl).setProcessDefinitionVersion(eq(0));
    verify(executionEntityImpl).setProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setRootProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setScope(eq(true));
    verify(executionEntityImpl).setStartUserId(isNull());
    verify(executionEntityImpl).setTenantId(eq("42"));
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"})
  public void testCreateProcessInstanceExecution4() {
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
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setAppVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setBusinessKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionName(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setScope(anyBoolean());
    doNothing().when(executionEntityImpl).setStartUserId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    // Act
    executionEntityManagerImpl.createProcessInstanceExecution(new ProcessDefinitionEntityImpl(), "Business Key", "42",
        "Initiator Variable Name");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey(eq("Business Key"));
    verify(executionEntityImpl).setProcessDefinitionId(isNull());
    verify(executionEntityImpl).setProcessDefinitionKey(isNull());
    verify(executionEntityImpl).setProcessDefinitionName(isNull());
    verify(executionEntityImpl).setProcessDefinitionVersion(eq(0));
    verify(executionEntityImpl).setProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setRootProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setScope(eq(true));
    verify(executionEntityImpl).setStartUserId(isNull());
    verify(executionEntityImpl).setTenantId(eq("42"));
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"})
  public void testCreateProcessInstanceExecution_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(executionDataManager.create()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl
        .createProcessInstanceExecution(new ProcessDefinitionEntityImpl(), "Business Key", null, null));
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"})
  public void testCreateProcessInstanceExecution_thenCallsDispatchEvent() {
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
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setAppVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setBusinessKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setCountEnabled(anyBoolean());
    doNothing().when(executionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionName(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setScope(anyBoolean());
    doNothing().when(executionEntityImpl).setStartUserId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    // Act
    executionEntityManagerImpl.createProcessInstanceExecution(new ProcessDefinitionEntityImpl(), "Business Key", "42",
        "Initiator Variable Name");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey(eq("Business Key"));
    verify(executionEntityImpl).setCountEnabled(eq(true));
    verify(executionEntityImpl).setProcessDefinitionId(isNull());
    verify(executionEntityImpl).setProcessDefinitionKey(isNull());
    verify(executionEntityImpl).setProcessDefinitionName(isNull());
    verify(executionEntityImpl).setProcessDefinitionVersion(eq(0));
    verify(executionEntityImpl).setProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setRootProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setScope(eq(true));
    verify(executionEntityImpl).setStartUserId(isNull());
    verify(executionEntityImpl).setTenantId(eq("42"));
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"})
  public void testCreateProcessInstanceExecution_whenNull_thenCallsDispatchEvent() {
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
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setAppVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setBusinessKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setCountEnabled(anyBoolean());
    doNothing().when(executionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionName(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setScope(anyBoolean());
    doNothing().when(executionEntityImpl).setStartUserId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    // Act
    executionEntityManagerImpl.createProcessInstanceExecution(new ProcessDefinitionEntityImpl(), "Business Key", null,
        "Initiator Variable Name");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey(eq("Business Key"));
    verify(executionEntityImpl).setCountEnabled(eq(true));
    verify(executionEntityImpl).setProcessDefinitionId(isNull());
    verify(executionEntityImpl).setProcessDefinitionKey(isNull());
    verify(executionEntityImpl).setProcessDefinitionName(isNull());
    verify(executionEntityImpl).setProcessDefinitionVersion(eq(0));
    verify(executionEntityImpl).setProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setRootProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setScope(eq(true));
    verify(executionEntityImpl).setStartUserId(isNull());
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"})
  public void testCreateProcessInstanceExecution_whenNull_thenCallsDispatchEvent2() {
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
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setAppVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setBusinessKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setCountEnabled(anyBoolean());
    doNothing().when(executionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionName(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setScope(anyBoolean());
    doNothing().when(executionEntityImpl).setStartUserId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    // Act
    executionEntityManagerImpl.createProcessInstanceExecution(new ProcessDefinitionEntityImpl(), "Business Key", "42",
        null);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).isEnableExecutionRelationshipCounts();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey(eq("Business Key"));
    verify(executionEntityImpl).setCountEnabled(eq(true));
    verify(executionEntityImpl).setProcessDefinitionId(isNull());
    verify(executionEntityImpl).setProcessDefinitionKey(isNull());
    verify(executionEntityImpl).setProcessDefinitionName(isNull());
    verify(executionEntityImpl).setProcessDefinitionVersion(eq(0));
    verify(executionEntityImpl).setProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setRootProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setScope(eq(true));
    verify(executionEntityImpl).setStartUserId(isNull());
    verify(executionEntityImpl).setTenantId(eq("42"));
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.createChildExecution(ExecutionEntity)"})
  public void testCreateChildExecution() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl parentExecutionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntity actualCreateChildExecutionResult = executionEntityManagerImpl
        .createChildExecution(parentExecutionEntity);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
    assertTrue(actualCreateChildExecutionResult instanceof ExecutionEntityImpl);
    assertNull(actualCreateChildExecutionResult.getAppVersion());
    assertNull(actualCreateChildExecutionResult.getParentId());
    assertNull(actualCreateChildExecutionResult.getProcessDefinitionId());
    assertNull(actualCreateChildExecutionResult.getProcessInstanceId());
    assertNull(actualCreateChildExecutionResult.getRootProcessInstanceId());
    assertNull(actualCreateChildExecutionResult.getParentProcessInstanceId());
    assertNull(actualCreateChildExecutionResult.getProcessDefinitionKey());
    assertNull(actualCreateChildExecutionResult.getCurrentFlowElement());
    assertNull(actualCreateChildExecutionResult.getProcessInstance());
    assertEquals(1, parentExecutionEntity.executions.size());
    assertFalse(((ExecutionEntityImpl) actualCreateChildExecutionResult).isCountEnabled());
    assertTrue(actualCreateChildExecutionResult.isProcessInstanceType());
    assertSame(actualCreateChildExecutionResult,
        ((ExecutionEntityImpl) actualCreateChildExecutionResult).getSourceActivityExecution());
    ExecutionEntityImpl executionEntityImpl = ((ExecutionEntityImpl) actualCreateChildExecutionResult).parent;
    assertSame(executionEntityImpl, actualCreateChildExecutionResult.getParent());
    assertSame(executionEntityImpl, ((ExecutionEntityImpl) actualCreateChildExecutionResult).getParentVariableScope());
    assertSame(executionEntityImpl, parentExecutionEntity.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.createChildExecution(ExecutionEntity)"})
  public void testCreateChildExecution2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(activitiEventDispatcher)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl
        .createChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processEngineConfiguration).getClock();
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.createChildExecution(ExecutionEntity)"})
  public void testCreateChildExecution3() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.create()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(
        mock(ProcessEngineConfigurationImpl.class), executionDataManager);
    ExecutionEntityImpl parentExecutionEntity = mock(ExecutionEntityImpl.class);
    when(parentExecutionEntity.isCountEnabled()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.createChildExecution(parentExecutionEntity));
    verify(parentExecutionEntity).isCountEnabled();
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.createChildExecution(ExecutionEntity)"})
  public void testCreateChildExecution_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl parentExecutionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntity actualCreateChildExecutionResult = executionEntityManagerImpl
        .createChildExecution(parentExecutionEntity);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
    assertTrue(actualCreateChildExecutionResult instanceof ExecutionEntityImpl);
    assertNull(actualCreateChildExecutionResult.getAppVersion());
    assertNull(actualCreateChildExecutionResult.getParentId());
    assertNull(actualCreateChildExecutionResult.getProcessDefinitionId());
    assertNull(actualCreateChildExecutionResult.getProcessInstanceId());
    assertNull(actualCreateChildExecutionResult.getRootProcessInstanceId());
    assertNull(actualCreateChildExecutionResult.getParentProcessInstanceId());
    assertNull(actualCreateChildExecutionResult.getProcessDefinitionKey());
    assertNull(actualCreateChildExecutionResult.getCurrentFlowElement());
    assertNull(actualCreateChildExecutionResult.getProcessInstance());
    assertEquals(1, parentExecutionEntity.executions.size());
    assertFalse(((ExecutionEntityImpl) actualCreateChildExecutionResult).isCountEnabled());
    assertTrue(actualCreateChildExecutionResult.isProcessInstanceType());
    assertSame(actualCreateChildExecutionResult,
        ((ExecutionEntityImpl) actualCreateChildExecutionResult).getSourceActivityExecution());
    ExecutionEntityImpl executionEntityImpl = ((ExecutionEntityImpl) actualCreateChildExecutionResult).parent;
    assertSame(executionEntityImpl, actualCreateChildExecutionResult.getParent());
    assertSame(executionEntityImpl, ((ExecutionEntityImpl) actualCreateChildExecutionResult).getParentVariableScope());
    assertSame(executionEntityImpl, parentExecutionEntity.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.createChildExecution(ExecutionEntity)"})
  public void testCreateChildExecution_givenNull_whenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl parentExecutionEntity = mock(ExecutionEntityImpl.class);
    when(parentExecutionEntity.isCountEnabled()).thenReturn(true);
    when(parentExecutionEntity.getAppVersion()).thenReturn(1);
    when(parentExecutionEntity.getId()).thenReturn("42");
    when(parentExecutionEntity.getParentProcessInstanceId()).thenReturn("42");
    when(parentExecutionEntity.getProcessDefinitionId()).thenReturn("42");
    when(parentExecutionEntity.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(parentExecutionEntity.getProcessInstanceId()).thenReturn("42");
    when(parentExecutionEntity.getRootProcessInstanceId()).thenReturn("42");
    when(parentExecutionEntity.getTenantId()).thenReturn(null);
    doNothing().when(parentExecutionEntity).addChildExecution(Mockito.<ExecutionEntity>any());

    // Act
    ExecutionEntity actualCreateChildExecutionResult = executionEntityManagerImpl
        .createChildExecution(parentExecutionEntity);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(parentExecutionEntity).getId();
    verify(parentExecutionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(parentExecutionEntity).getAppVersion();
    verify(parentExecutionEntity).getParentProcessInstanceId();
    verify(parentExecutionEntity).getProcessDefinitionId();
    verify(parentExecutionEntity).getProcessDefinitionKey();
    verify(parentExecutionEntity, atLeast(1)).getProcessInstanceId();
    verify(parentExecutionEntity).getRootProcessInstanceId();
    verify(parentExecutionEntity).getTenantId();
    verify(parentExecutionEntity).isCountEnabled();
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
    assertTrue(actualCreateChildExecutionResult instanceof ExecutionEntityImpl);
    assertEquals("42", actualCreateChildExecutionResult.getParentId());
    assertEquals("42", actualCreateChildExecutionResult.getProcessDefinitionId());
    assertEquals("42", actualCreateChildExecutionResult.getProcessInstanceId());
    assertEquals("42", actualCreateChildExecutionResult.getRootProcessInstanceId());
    assertEquals("42", actualCreateChildExecutionResult.getParentProcessInstanceId());
    assertEquals("Process Definition Key", actualCreateChildExecutionResult.getProcessDefinitionKey());
    assertEquals(1, actualCreateChildExecutionResult.getAppVersion().intValue());
    assertFalse(actualCreateChildExecutionResult.isProcessInstanceType());
    assertTrue(((ExecutionEntityImpl) actualCreateChildExecutionResult).isCountEnabled());
    assertSame(actualCreateChildExecutionResult,
        ((ExecutionEntityImpl) actualCreateChildExecutionResult).getSourceActivityExecution());
    ExecutionEntityImpl executionEntityImpl = ((ExecutionEntityImpl) actualCreateChildExecutionResult).parent;
    assertSame(executionEntityImpl, actualCreateChildExecutionResult.getParent());
    assertSame(executionEntityImpl, ((ExecutionEntityImpl) actualCreateChildExecutionResult).getParentVariableScope());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}.
   * <ul>
   *   <li>Then return AppVersion is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.createChildExecution(ExecutionEntity)"})
  public void testCreateChildExecution_thenReturnAppVersionIsNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl parentExecutionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntity actualCreateChildExecutionResult = executionEntityManagerImpl
        .createChildExecution(parentExecutionEntity);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
    assertTrue(actualCreateChildExecutionResult instanceof ExecutionEntityImpl);
    assertNull(actualCreateChildExecutionResult.getAppVersion());
    assertNull(actualCreateChildExecutionResult.getParentId());
    assertNull(actualCreateChildExecutionResult.getProcessDefinitionId());
    assertNull(actualCreateChildExecutionResult.getProcessInstanceId());
    assertNull(actualCreateChildExecutionResult.getRootProcessInstanceId());
    assertNull(actualCreateChildExecutionResult.getParentProcessInstanceId());
    assertNull(actualCreateChildExecutionResult.getProcessDefinitionKey());
    assertNull(actualCreateChildExecutionResult.getCurrentFlowElement());
    assertNull(actualCreateChildExecutionResult.getProcessInstance());
    assertEquals(1, parentExecutionEntity.executions.size());
    assertFalse(((ExecutionEntityImpl) actualCreateChildExecutionResult).isCountEnabled());
    assertTrue(actualCreateChildExecutionResult.isProcessInstanceType());
    assertSame(actualCreateChildExecutionResult,
        ((ExecutionEntityImpl) actualCreateChildExecutionResult).getSourceActivityExecution());
    ExecutionEntityImpl executionEntityImpl = ((ExecutionEntityImpl) actualCreateChildExecutionResult).parent;
    assertSame(executionEntityImpl, actualCreateChildExecutionResult.getParent());
    assertSame(executionEntityImpl, ((ExecutionEntityImpl) actualCreateChildExecutionResult).getParentVariableScope());
    assertSame(executionEntityImpl, parentExecutionEntity.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}.
   * <ul>
   *   <li>Then return TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.createChildExecution(ExecutionEntity)"})
  public void testCreateChildExecution_thenReturnTenantIdIs42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        executionDataManager);
    ExecutionEntityImpl parentExecutionEntity = mock(ExecutionEntityImpl.class);
    when(parentExecutionEntity.isCountEnabled()).thenReturn(true);
    when(parentExecutionEntity.getAppVersion()).thenReturn(1);
    when(parentExecutionEntity.getId()).thenReturn("42");
    when(parentExecutionEntity.getParentProcessInstanceId()).thenReturn("42");
    when(parentExecutionEntity.getProcessDefinitionId()).thenReturn("42");
    when(parentExecutionEntity.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(parentExecutionEntity.getProcessInstanceId()).thenReturn("42");
    when(parentExecutionEntity.getRootProcessInstanceId()).thenReturn("42");
    when(parentExecutionEntity.getTenantId()).thenReturn("42");
    doNothing().when(parentExecutionEntity).addChildExecution(Mockito.<ExecutionEntity>any());

    // Act
    ExecutionEntity actualCreateChildExecutionResult = executionEntityManagerImpl
        .createChildExecution(parentExecutionEntity);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(parentExecutionEntity).getId();
    verify(parentExecutionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(parentExecutionEntity).getAppVersion();
    verify(parentExecutionEntity).getParentProcessInstanceId();
    verify(parentExecutionEntity).getProcessDefinitionId();
    verify(parentExecutionEntity).getProcessDefinitionKey();
    verify(parentExecutionEntity, atLeast(1)).getProcessInstanceId();
    verify(parentExecutionEntity).getRootProcessInstanceId();
    verify(parentExecutionEntity, atLeast(1)).getTenantId();
    verify(parentExecutionEntity).isCountEnabled();
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
    assertTrue(actualCreateChildExecutionResult instanceof ExecutionEntityImpl);
    assertEquals("42", actualCreateChildExecutionResult.getParentId());
    assertEquals("42", actualCreateChildExecutionResult.getProcessDefinitionId());
    assertEquals("42", actualCreateChildExecutionResult.getProcessInstanceId());
    assertEquals("42", actualCreateChildExecutionResult.getRootProcessInstanceId());
    assertEquals("42", actualCreateChildExecutionResult.getTenantId());
    assertEquals("42", actualCreateChildExecutionResult.getParentProcessInstanceId());
    assertEquals("Process Definition Key", actualCreateChildExecutionResult.getProcessDefinitionKey());
    assertEquals(1, actualCreateChildExecutionResult.getAppVersion().intValue());
    assertFalse(actualCreateChildExecutionResult.isProcessInstanceType());
    assertTrue(((ExecutionEntityImpl) actualCreateChildExecutionResult).isCountEnabled());
    assertSame(actualCreateChildExecutionResult,
        ((ExecutionEntityImpl) actualCreateChildExecutionResult).getSourceActivityExecution());
    ExecutionEntityImpl executionEntityImpl = ((ExecutionEntityImpl) actualCreateChildExecutionResult).parent;
    assertSame(executionEntityImpl, actualCreateChildExecutionResult.getParent());
    assertSame(executionEntityImpl, ((ExecutionEntityImpl) actualCreateChildExecutionResult).getParentVariableScope());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"})
  public void testCreateSubprocessInstance() {
    // Arrange
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    when(executionDataManager.create()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    ExecutionEntityImpl superExecutionEntity = mock(ExecutionEntityImpl.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(superExecutionEntity)
        .setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(superExecutionEntity.isCountEnabled()).thenReturn(true);
    when(superExecutionEntity.getRootProcessInstanceId()).thenReturn("42");
    when(superExecutionEntity.getTenantId()).thenReturn("42");
    when(superExecutionEntity.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl
        .createSubprocessInstance(processDefinition, superExecutionEntity, "Business Key"));
    verify(processEngineConfigurationImpl).getClock();
    verify(superExecutionEntity).getProcessInstance();
    verify(superExecutionEntity).getRootProcessInstanceId();
    verify(superExecutionEntity, atLeast(1)).getTenantId();
    verify(superExecutionEntity).isCountEnabled();
    verify(superExecutionEntity).setSubProcessInstance(isNull());
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"})
  public void testCreateSubprocessInstance_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(executionDataManager.create()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    ExecutionEntityImpl superExecutionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    superExecutionEntity.setTenantId(null);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl
        .createSubprocessInstance(processDefinition, superExecutionEntity, "Business Key"));
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setActive(boolean)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"})
  public void testCreateSubprocessInstance_thenCallsSetActive() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setActive(anyBoolean());
    doNothing().when(executionEntityImpl).setAppVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setBusinessKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setCountEnabled(anyBoolean());
    doNothing().when(executionEntityImpl).setName(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionKey(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionName(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessDefinitionVersion(Mockito.<Integer>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setScope(anyBoolean());
    doNothing().when(executionEntityImpl).setStartTime(Mockito.<Date>any());
    doNothing().when(executionEntityImpl).setStartUserId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setSuperExecution(Mockito.<ExecutionEntity>any());
    doNothing().when(executionEntityImpl).setTenantId(Mockito.<String>any());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    ExecutionEntityImpl superExecutionEntity = mock(ExecutionEntityImpl.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(superExecutionEntity)
        .setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(superExecutionEntity.isCountEnabled()).thenReturn(true);
    when(superExecutionEntity.getRootProcessInstanceId()).thenReturn("42");
    when(superExecutionEntity.getTenantId()).thenReturn("42");
    when(superExecutionEntity.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl
        .createSubprocessInstance(processDefinition, superExecutionEntity, "Business Key"));
    verify(processEngineConfigurationImpl).getClock();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(executionEntityImpl).getId();
    verify(superExecutionEntity).getProcessInstance();
    verify(superExecutionEntity, atLeast(1)).getRootProcessInstanceId();
    verify(superExecutionEntity, atLeast(1)).getTenantId();
    verify(superExecutionEntity).isCountEnabled();
    verify(executionEntityImpl).setActive(eq(true));
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey(eq("Business Key"));
    verify(executionEntityImpl).setCountEnabled(eq(true));
    verify(executionEntityImpl).setName(isNull());
    verify(executionEntityImpl).setProcessDefinitionId(isNull());
    verify(executionEntityImpl).setProcessDefinitionKey(isNull());
    verify(executionEntityImpl).setProcessDefinitionName(isNull());
    verify(executionEntityImpl).setProcessDefinitionVersion(eq(0));
    verify(executionEntityImpl).setProcessInstanceId(eq("42"));
    verify(executionEntityImpl, atLeast(1)).setRootProcessInstanceId(eq("42"));
    verify(executionEntityImpl).setScope(eq(true));
    verify(executionEntityImpl).setStartTime(isA(Date.class));
    verify(executionEntityImpl).setStartUserId(isNull());
    verify(superExecutionEntity).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setTenantId(eq("42"));
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)}.
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"})
  public void testCreateSubprocessInstance_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(executionDataManager.create()).thenReturn(createWithEmptyRelationshipCollectionsResult);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    ExecutionEntityImpl superExecutionEntity = mock(ExecutionEntityImpl.class);
    when(superExecutionEntity.getId()).thenReturn("42");
    when(superExecutionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(superExecutionEntity).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(superExecutionEntity.isCountEnabled()).thenReturn(true);
    when(superExecutionEntity.getRootProcessInstanceId()).thenReturn("42");
    when(superExecutionEntity.getTenantId()).thenReturn("42");
    when(superExecutionEntity.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    ExecutionEntity actualCreateSubprocessInstanceResult = executionEntityManagerImpl
        .createSubprocessInstance(processDefinition, superExecutionEntity, "Business Key");

    // Assert
    verify(processEngineConfigurationImpl).getClock();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(superExecutionEntity).getId();
    verify(superExecutionEntity).getProcessInstance();
    verify(superExecutionEntity).getProcessInstanceId();
    verify(superExecutionEntity, atLeast(1)).getRootProcessInstanceId();
    verify(superExecutionEntity, atLeast(1)).getTenantId();
    verify(superExecutionEntity).isCountEnabled();
    verify(superExecutionEntity, atLeast(1)).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualCreateSubprocessInstanceResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)}.
   * <ul>
   *   <li>When {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getTenantId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"})
  public void testCreateSubprocessInstance_whenExecutionEntityImplGetTenantIdReturnNull() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(executionDataManager.create()).thenReturn(createWithEmptyRelationshipCollectionsResult);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    ExecutionEntityImpl superExecutionEntity = mock(ExecutionEntityImpl.class);
    when(superExecutionEntity.getId()).thenReturn("42");
    when(superExecutionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(superExecutionEntity).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(superExecutionEntity.isCountEnabled()).thenReturn(true);
    when(superExecutionEntity.getRootProcessInstanceId()).thenReturn("42");
    when(superExecutionEntity.getTenantId()).thenReturn(null);
    when(superExecutionEntity.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    ExecutionEntity actualCreateSubprocessInstanceResult = executionEntityManagerImpl
        .createSubprocessInstance(processDefinition, superExecutionEntity, "Business Key");

    // Assert
    verify(processEngineConfigurationImpl).getClock();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(superExecutionEntity).getId();
    verify(superExecutionEntity).getProcessInstance();
    verify(superExecutionEntity).getProcessInstanceId();
    verify(superExecutionEntity, atLeast(1)).getRootProcessInstanceId();
    verify(superExecutionEntity).getTenantId();
    verify(superExecutionEntity).isCountEnabled();
    verify(superExecutionEntity, atLeast(1)).setSubProcessInstance(Mockito.<ExecutionEntity>any());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualCreateSubprocessInstanceResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#inheritCommonProperties(ExecutionEntity, ExecutionEntity)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getClock()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#inheritCommonProperties(ExecutionEntity, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.inheritCommonProperties(ExecutionEntity, ExecutionEntity)"})
  public void testInheritCommonProperties_thenCallsGetClock() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl parentExecutionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executionEntityManagerImpl.inheritCommonProperties(parentExecutionEntity,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(processEngineConfiguration).getClock();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#inheritCommonProperties(ExecutionEntity, ExecutionEntity)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#inheritCommonProperties(ExecutionEntity, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.inheritCommonProperties(ExecutionEntity, ExecutionEntity)"})
  public void testInheritCommonProperties_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl parentExecutionEntity = mock(ExecutionEntityImpl.class);
    when(parentExecutionEntity.isCountEnabled()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl
        .inheritCommonProperties(parentExecutionEntity, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(parentExecutionEntity).isCountEnabled();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateExecutionTenantIdForDeployment(String, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateExecutionTenantIdForDeployment(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.updateExecutionTenantIdForDeployment(String, String)"})
  public void testUpdateExecutionTenantIdForDeployment() {
    // Arrange
    doNothing().when(executionDataManager)
        .updateExecutionTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());

    // Act
    executionEntityManagerImpl.updateExecutionTenantIdForDeployment("42", "42");

    // Assert
    verify(executionDataManager).updateExecutionTenantIdForDeployment(eq("42"), eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateExecutionTenantIdForDeployment(String, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateExecutionTenantIdForDeployment(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.updateExecutionTenantIdForDeployment(String, String)"})
  public void testUpdateExecutionTenantIdForDeployment2() {
    // Arrange
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(executionDataManager)
        .updateExecutionTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.updateExecutionTenantIdForDeployment("42", "42"));
    verify(executionDataManager).updateExecutionTenantIdForDeployment(eq("42"), eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"})
  public void testDeleteProcessInstancesByProcessDefinition() {
    // Arrange
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstancesByProcessDefinition("42", "Just cause", true));
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"})
  public void testDeleteProcessInstancesByProcessDefinition2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(processEngineConfiguration,
            new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration())));
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfigurationImpl,
        new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(stringList);

    // Act
    executionEntityManagerImpl.deleteProcessInstancesByProcessDefinition("42", "Just cause", true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionDataManager).findById(eq("foo"));
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId(eq("42"));
    verify(taskDataManager).findTasksByProcessInstanceId(isNull());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"})
  public void testDeleteProcessInstancesByProcessDefinition3() {
    // Arrange
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    executionEntityManagerImpl.deleteProcessInstancesByProcessDefinition("42", null, false);

    // Assert
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"})
  public void testDeleteProcessInstancesByProcessDefinition4() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(stringList);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstancesByProcessDefinition("42", null, false));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionDataManager).findById(eq("foo"));
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId(eq("42"));
    verify(taskDataManager).findTasksByProcessInstanceId(isNull());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"})
  public void testDeleteProcessInstancesByProcessDefinition5() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> (new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager))
            .deleteProcessInstancesByProcessDefinition("42", "Just cause", true));
    verify(processEngineConfiguration).getHistoricProcessInstanceEntityManager();
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"})
  public void testDeleteProcessInstancesByProcessDefinition_thenCallsDispatchEvent() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(activitiEventDispatcher)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntityImpl.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(stringList);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> (new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager))
            .deleteProcessInstancesByProcessDefinition("42", "Just cause", true));
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getTaskEntityManager();
    verify(executionEntityImpl).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl).getExecutions();
    verify(executionEntityImpl).getProcessInstance();
    verify(executionDataManager).findById(eq("foo"));
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId(eq("42"));
    verify(taskDataManager).findTasksByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getHistoryManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"})
  public void testDeleteProcessInstancesByProcessDefinition_thenCallsGetHistoryManager() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration())));
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    executionEntityManagerImpl.deleteProcessInstancesByProcessDefinition("42", "Just cause", true);

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessDefinitionId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"})
  public void testDeleteProcessInstancesByProcessDefinition_thenCallsGetProcessDefinitionId() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getId()).thenReturn("42");
    when(executionEntityImpl2.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl2.getProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntityImpl2).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntityImpl2.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl2);
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(stringList);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> (new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager))
            .deleteProcessInstancesByProcessDefinition("42", "Just cause", true));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getTaskEntityManager();
    verify(executionEntityImpl2).getId();
    verify(executionEntityImpl2).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl2).getExecutions();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl2).getProcessInstance();
    verify(executionDataManager).findById(eq("foo"));
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId(eq("42"));
    verify(taskDataManager).findTasksByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"})
  public void testDeleteProcessInstance() {
    // Arrange
    when(processEngineConfigurationImpl.getTaskEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionDataManager).findById(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"})
  public void testDeleteProcessInstance_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionDataManager).findById(eq("42"));
    verify(taskDataManager).findTasksByProcessInstanceId(isNull());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"})
  public void testDeleteProcessInstance_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(executionDataManager).findById(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"})
  public void testDeleteProcessInstance_thenCallsDispatchEvent() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(activitiEventDispatcher)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntityImpl.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionEntityImpl).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl).getExecutions();
    verify(executionEntityImpl).getProcessInstance();
    verify(executionDataManager).findById(eq("42"));
    verify(taskDataManager).findTasksByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessDefinitionId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"})
  public void testDeleteProcessInstance_thenCallsGetProcessDefinitionId() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getId()).thenReturn("42");
    when(executionEntityImpl2.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl2.getProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntityImpl2).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntityImpl2.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl2);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionEntityImpl2).getId();
    verify(executionEntityImpl2).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl2).getExecutions();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl2).getProcessInstance();
    verify(executionDataManager).findById(eq("42"));
    verify(taskDataManager).findTasksByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String, boolean)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteProcessInstanceCascade(ExecutionEntity, String, boolean)"})
  public void testDeleteProcessInstanceCascade() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);

    // Act
    executionEntityManagerImpl
        .deleteProcessInstanceCascade(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause", true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(taskDataManager).findTasksByProcessInstanceId(isNull());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String, boolean)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteProcessInstanceCascade(ExecutionEntity, String, boolean)"})
  public void testDeleteProcessInstanceCascade_thenCallsDispatchEvent() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(activitiEventDispatcher)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getExecutions()).thenReturn(new ArrayList<>());
    when(execution.getProcessInstance()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    doNothing().when(execution).addChildExecution(Mockito.<ExecutionEntity>any());
    execution.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstanceCascade(execution, "Just cause", true));
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(execution).getId();
    verify(execution).addChildExecution(isA(ExecutionEntity.class));
    verify(execution).getExecutions();
    verify(execution).getProcessInstance();
    verify(taskDataManager).findTasksByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String, boolean)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessDefinitionId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteProcessInstanceCascade(ExecutionEntity, String, boolean)"})
  public void testDeleteProcessInstanceCascade_thenCallsGetProcessDefinitionId() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getExecutions()).thenReturn(new ArrayList<>());
    when(execution.getProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(execution).addChildExecution(Mockito.<ExecutionEntity>any());
    execution.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstanceCascade(execution, "Just cause", true));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(execution).getId();
    verify(execution).addChildExecution(isA(ExecutionEntity.class));
    verify(execution).getExecutions();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(execution).getProcessInstance();
    verify(taskDataManager).findTasksByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)"})
  public void testExecuteExecutionListeners() {
    // Arrange
    when(processEngineConfigurationImpl.getListenerNotificationHelper()).thenReturn(new ListenerNotificationHelper());
    AdhocSubProcess elementWithExecutionListeners = new AdhocSubProcess();

    // Act
    executionEntityManagerImpl.executeExecutionListeners(elementWithExecutionListeners,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Event Type");

    // Assert
    verify(processEngineConfigurationImpl).getListenerNotificationHelper();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)}.
   * <ul>
   *   <li>Then calls {@link ListenerNotificationHelper#executeExecutionListeners(HasExecutionListeners, DelegateExecution, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)"})
  public void testExecuteExecutionListeners_thenCallsExecuteExecutionListeners() {
    // Arrange
    ListenerNotificationHelper listenerNotificationHelper = mock(ListenerNotificationHelper.class);
    doNothing().when(listenerNotificationHelper)
        .executeExecutionListeners(Mockito.<HasExecutionListeners>any(), Mockito.<DelegateExecution>any(),
            Mockito.<String>any());
    when(processEngineConfigurationImpl.getListenerNotificationHelper()).thenReturn(listenerNotificationHelper);
    AdhocSubProcess elementWithExecutionListeners = new AdhocSubProcess();

    // Act
    executionEntityManagerImpl.executeExecutionListeners(elementWithExecutionListeners,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Event Type");

    // Assert
    verify(listenerNotificationHelper).executeExecutionListeners(isA(HasExecutionListeners.class),
        isA(DelegateExecution.class), eq("Event Type"));
    verify(processEngineConfigurationImpl).getListenerNotificationHelper();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testDeleteExecutionAndRelatedData() {
    // Arrange
    when(processEngineConfigurationImpl.getPerformanceSettings())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl
        .deleteExecutionAndRelatedData(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testDeleteExecutionAndRelatedData2() {
    // Arrange
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteExecutionAndRelatedData(executionEntity, "Just cause"));
    verify(executionEntity).getId();
    verify(executionEntity).getProcessInstanceId();
    verify(executionEntity).setActive(eq(false));
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(executionEntity).setEnded(eq(true));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testDeleteExecutionAndRelatedData3() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager())
        .thenReturn(new IdentityLinkEntityManagerImpl(new JtaProcessEngineConfiguration(), identityLinkDataManager));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getVariableInstancesLocal())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteExecutionAndRelatedData(executionEntity, "Just cause"));
    verify(executionEntity).getId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(executionEntity).setEnded(eq(true));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testDeleteExecutionAndRelatedData4() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), identityLinkDataManager);

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    JobDataManager jobDataManager2 = mock(JobDataManager.class);
    when(jobDataManager2.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager2);

    SuspendedJobDataManager jobDataManager3 = mock(SuspendedJobDataManager.class);
    when(jobDataManager3.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager3);

    DeadLetterJobDataManager jobDataManager4 = mock(DeadLetterJobDataManager.class);
    when(jobDataManager4.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager4);

    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecution(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    when(processEngineConfigurationImpl.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    when(processEngineConfigurationImpl.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    when(processEngineConfigurationImpl.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    doNothing().when(executionEntity).setDeleted(anyBoolean());
    when(executionEntity.getVariableInstancesLocal()).thenReturn(new HashMap<>());
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act
    executionEntityManagerImpl.deleteExecutionAndRelatedData(executionEntity, "Just cause");

    // Assert
    verify(executionEntity, atLeast(1)).getId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getDeadLetterJobEntityManager();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getEventSubscriptionEntityManager();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getJobEntityManager();
    verify(processEngineConfigurationImpl).getSuspendedJobEntityManager();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(processEngineConfigurationImpl).getTimerJobEntityManager();
    verify(executionEntity).setDeleted(eq(true));
    verify(executionEntity).setEnded(eq(true));
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    verify(jobDataManager4).findJobsByExecutionId(eq("42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecution(eq("42"));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
    verify(jobDataManager2).findJobsByExecutionId(eq("42"));
    verify(jobDataManager3).findJobsByExecutionId(eq("42"));
    verify(taskDataManager).findTasksByExecutionId(eq("42"));
    verify(jobDataManager).findJobsByExecutionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testDeleteExecutionAndRelatedData5() {
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
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), identityLinkDataManager);

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    JobDataManager jobDataManager2 = mock(JobDataManager.class);
    when(jobDataManager2.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager2);

    SuspendedJobDataManager jobDataManager3 = mock(SuspendedJobDataManager.class);
    when(jobDataManager3.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager3);

    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());

    ArrayList<DeadLetterJobEntity> deadLetterJobEntityList = new ArrayList<>();
    deadLetterJobEntityList.add(new DeadLetterJobEntityImpl());
    DeadLetterJobDataManager jobDataManager4 = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager4).delete(Mockito.<DeadLetterJobEntity>any());
    when(jobDataManager4.findJobsByExecutionId(Mockito.<String>any())).thenReturn(deadLetterJobEntityList);
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager4);

    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(processEngineConfiguration2.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManagerImpl);
    when(processEngineConfiguration2.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManagerImpl);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    when(processEngineConfiguration2.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);
    when(processEngineConfiguration2.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfiguration2.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration2,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getVariableInstancesLocal()).thenReturn(new HashMap<>());
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteExecutionAndRelatedData(executionEntity, "Just cause"));
    verify(executionEntity, atLeast(1)).getId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration2).getDeadLetterJobEntityManager();
    verify(processEngineConfiguration2).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2).getHistoryManager();
    verify(processEngineConfiguration2).getIdentityLinkEntityManager();
    verify(processEngineConfiguration2).getJobEntityManager();
    verify(processEngineConfiguration2).getSuspendedJobEntityManager();
    verify(processEngineConfiguration2).getTimerJobEntityManager();
    verify(executionEntity).setEnded(eq(true));
    verify(jobDataManager4).delete(isA(DeadLetterJobEntity.class));
    verify(jobDataManager4).findJobsByExecutionId(eq("42"));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
    verify(jobDataManager2).findJobsByExecutionId(eq("42"));
    verify(jobDataManager3).findJobsByExecutionId(eq("42"));
    verify(jobDataManager).findJobsByExecutionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testDeleteExecutionAndRelatedData6() {
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
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), identityLinkDataManager);

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    JobDataManager jobDataManager2 = mock(JobDataManager.class);
    when(jobDataManager2.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager2);

    SuspendedJobDataManager jobDataManager3 = mock(SuspendedJobDataManager.class);
    when(jobDataManager3.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager3);

    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());

    ArrayList<DeadLetterJobEntity> deadLetterJobEntityList = new ArrayList<>();
    deadLetterJobEntityList.add(new DeadLetterJobEntityImpl());
    DeadLetterJobDataManager jobDataManager4 = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager4).delete(Mockito.<DeadLetterJobEntity>any());
    when(jobDataManager4.findJobsByExecutionId(Mockito.<String>any())).thenReturn(deadLetterJobEntityList);
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager4);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(activitiEventDispatcher)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration2.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManagerImpl);
    when(processEngineConfiguration2.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManagerImpl);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    when(processEngineConfiguration2.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);
    when(processEngineConfiguration2.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfiguration2.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration2,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getVariableInstancesLocal()).thenReturn(new HashMap<>());
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteExecutionAndRelatedData(executionEntity, "Just cause"));
    verify(executionEntity, atLeast(1)).getId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration2).getDeadLetterJobEntityManager();
    verify(processEngineConfiguration2, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2).getHistoryManager();
    verify(processEngineConfiguration2).getIdentityLinkEntityManager();
    verify(processEngineConfiguration2).getJobEntityManager();
    verify(processEngineConfiguration2).getSuspendedJobEntityManager();
    verify(processEngineConfiguration2).getTimerJobEntityManager();
    verify(executionEntity).setEnded(eq(true));
    verify(jobDataManager4).delete(isA(DeadLetterJobEntity.class));
    verify(jobDataManager4).findJobsByExecutionId(eq("42"));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
    verify(jobDataManager2).findJobsByExecutionId(eq("42"));
    verify(jobDataManager3).findJobsByExecutionId(eq("42"));
    verify(jobDataManager).findJobsByExecutionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   * <ul>
   *   <li>Then calls {@link DelegateExecution#getProcessDefinitionId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testDeleteExecutionAndRelatedData_thenCallsGetProcessDefinitionId() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), identityLinkDataManager);

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    JobDataManager jobDataManager2 = mock(JobDataManager.class);
    when(jobDataManager2.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager2);

    SuspendedJobDataManager jobDataManager3 = mock(SuspendedJobDataManager.class);
    when(jobDataManager3.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager3);

    DeadLetterJobDataManager jobDataManager4 = mock(DeadLetterJobDataManager.class);
    when(jobDataManager4.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager4);

    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecution(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    when(processEngineConfigurationImpl.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    when(processEngineConfigurationImpl.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    when(processEngineConfigurationImpl.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getProcessDefinitionId()).thenReturn("42");
    doNothing().when(executionEntity).setDeleted(anyBoolean());
    when(executionEntity.getVariableInstancesLocal()).thenReturn(new HashMap<>());
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act
    executionEntityManagerImpl.deleteExecutionAndRelatedData(executionEntity, "Just cause");

    // Assert
    verify(executionEntity, atLeast(1)).getId();
    verify(executionEntity).getProcessDefinitionId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getDeadLetterJobEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getEventSubscriptionEntityManager();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getJobEntityManager();
    verify(processEngineConfigurationImpl).getSuspendedJobEntityManager();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(processEngineConfigurationImpl).getTimerJobEntityManager();
    verify(executionEntity).setDeleted(eq(true));
    verify(executionEntity).setEnded(eq(true));
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    verify(jobDataManager4).findJobsByExecutionId(eq("42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecution(eq("42"));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
    verify(jobDataManager2).findJobsByExecutionId(eq("42"));
    verify(jobDataManager3).findJobsByExecutionId(eq("42"));
    verify(taskDataManager).findTasksByExecutionId(eq("42"));
    verify(jobDataManager).findJobsByExecutionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   * <ul>
   *   <li>Then calls {@link DelegateExecution#getProcessDefinitionId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testDeleteExecutionAndRelatedData_thenCallsGetProcessDefinitionId2() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), identityLinkDataManager);

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    JobDataManager jobDataManager2 = mock(JobDataManager.class);
    when(jobDataManager2.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager2);

    SuspendedJobDataManager jobDataManager3 = mock(SuspendedJobDataManager.class);
    when(jobDataManager3.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager3);

    DeadLetterJobDataManager jobDataManager4 = mock(DeadLetterJobDataManager.class);
    when(jobDataManager4.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager4);

    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecution(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    when(processEngineConfigurationImpl.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    when(processEngineConfigurationImpl.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    when(processEngineConfigurationImpl.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getProcessDefinitionId()).thenReturn("42");
    doNothing().when(executionEntity).setDeleted(anyBoolean());
    when(executionEntity.getVariableInstancesLocal()).thenReturn(new HashMap<>());
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act
    executionEntityManagerImpl.deleteExecutionAndRelatedData(executionEntity, "Just cause");

    // Assert
    verify(executionEntity, atLeast(1)).getId();
    verify(executionEntity).getProcessDefinitionId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getDeadLetterJobEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getEventSubscriptionEntityManager();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getJobEntityManager();
    verify(processEngineConfigurationImpl).getSuspendedJobEntityManager();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(processEngineConfigurationImpl).getTimerJobEntityManager();
    verify(executionEntity).setDeleted(eq(true));
    verify(executionEntity).setEnded(eq(true));
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    verify(jobDataManager4).findJobsByExecutionId(eq("42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecution(eq("42"));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
    verify(jobDataManager2).findJobsByExecutionId(eq("42"));
    verify(jobDataManager3).findJobsByExecutionId(eq("42"));
    verify(taskDataManager).findTasksByExecutionId(eq("42"));
    verify(jobDataManager).findJobsByExecutionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testCancelExecutionAndRelatedData() {
    // Arrange
    when(processEngineConfigurationImpl.getPerformanceSettings())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> executionEntityManagerImpl
        .cancelExecutionAndRelatedData(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testCancelExecutionAndRelatedData2() {
    // Arrange
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.cancelExecutionAndRelatedData(executionEntity, "Just cause"));
    verify(executionEntity).getId();
    verify(executionEntity).getProcessInstanceId();
    verify(executionEntity).isActive();
    verify(executionEntity).setActive(eq(false));
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(executionEntity).setEnded(eq(true));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testCancelExecutionAndRelatedData3() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager())
        .thenReturn(new IdentityLinkEntityManagerImpl(new JtaProcessEngineConfiguration(), identityLinkDataManager));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getVariableInstancesLocal())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.cancelExecutionAndRelatedData(executionEntity, "Just cause"));
    verify(executionEntity).getId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).isActive();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(executionEntity).setEnded(eq(true));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testCancelExecutionAndRelatedData4() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), identityLinkDataManager);

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    JobDataManager jobDataManager2 = mock(JobDataManager.class);
    when(jobDataManager2.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager2);

    SuspendedJobDataManager jobDataManager3 = mock(SuspendedJobDataManager.class);
    when(jobDataManager3.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager3);

    DeadLetterJobDataManager jobDataManager4 = mock(DeadLetterJobDataManager.class);
    when(jobDataManager4.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager4);

    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecution(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    when(processEngineConfigurationImpl.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    when(processEngineConfigurationImpl.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    when(processEngineConfigurationImpl.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getActivityId()).thenReturn("42");
    when(executionEntity.getProcessDefinitionId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    doNothing().when(executionEntity).setDeleted(anyBoolean());
    when(executionEntity.getVariableInstancesLocal()).thenReturn(new HashMap<>());
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act
    executionEntityManagerImpl.cancelExecutionAndRelatedData(executionEntity, "Just cause");

    // Assert
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(executionEntity, atLeast(1)).getId();
    verify(executionEntity, atLeast(1)).getProcessDefinitionId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).isActive();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getDeadLetterJobEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getEventSubscriptionEntityManager();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getJobEntityManager();
    verify(processEngineConfigurationImpl).getSuspendedJobEntityManager();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(processEngineConfigurationImpl).getTimerJobEntityManager();
    verify(executionEntity).setDeleted(eq(true));
    verify(executionEntity).setEnded(eq(true));
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    verify(jobDataManager4).findJobsByExecutionId(eq("42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecution(eq("42"));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
    verify(jobDataManager2).findJobsByExecutionId(eq("42"));
    verify(jobDataManager3).findJobsByExecutionId(eq("42"));
    verify(taskDataManager).findTasksByExecutionId(eq("42"));
    verify(jobDataManager).findJobsByExecutionId(eq("42"));
    verify(executionEntity).getActivityId();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testCancelExecutionAndRelatedData5() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), identityLinkDataManager);

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    JobDataManager jobDataManager2 = mock(JobDataManager.class);
    when(jobDataManager2.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager2);

    SuspendedJobDataManager jobDataManager3 = mock(SuspendedJobDataManager.class);
    when(jobDataManager3.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager3);

    DeadLetterJobDataManager jobDataManager4 = mock(DeadLetterJobDataManager.class);
    when(jobDataManager4.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager4);

    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecution(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    when(processEngineConfigurationImpl.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    when(processEngineConfigurationImpl.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    when(processEngineConfigurationImpl.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getActivityId()).thenReturn("42");
    when(executionEntity.getProcessDefinitionId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    doNothing().when(executionEntity).setDeleted(anyBoolean());
    when(executionEntity.getVariableInstancesLocal()).thenReturn(new HashMap<>());
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act
    executionEntityManagerImpl.cancelExecutionAndRelatedData(executionEntity, "Just cause");

    // Assert
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(executionEntity, atLeast(1)).getId();
    verify(executionEntity).getProcessDefinitionId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).isActive();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getDeadLetterJobEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getEventSubscriptionEntityManager();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getJobEntityManager();
    verify(processEngineConfigurationImpl).getSuspendedJobEntityManager();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(processEngineConfigurationImpl).getTimerJobEntityManager();
    verify(executionEntity).setDeleted(eq(true));
    verify(executionEntity).setEnded(eq(true));
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    verify(jobDataManager4).findJobsByExecutionId(eq("42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecution(eq("42"));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
    verify(jobDataManager2).findJobsByExecutionId(eq("42"));
    verify(jobDataManager3).findJobsByExecutionId(eq("42"));
    verify(taskDataManager).findTasksByExecutionId(eq("42"));
    verify(jobDataManager).findJobsByExecutionId(eq("42"));
    verify(executionEntity).getActivityId();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testCancelExecutionAndRelatedData6() {
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
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), identityLinkDataManager);

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    JobDataManager jobDataManager2 = mock(JobDataManager.class);
    when(jobDataManager2.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager2);

    SuspendedJobDataManager jobDataManager3 = mock(SuspendedJobDataManager.class);
    when(jobDataManager3.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager3);

    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());

    ArrayList<DeadLetterJobEntity> deadLetterJobEntityList = new ArrayList<>();
    deadLetterJobEntityList.add(new DeadLetterJobEntityImpl());
    DeadLetterJobDataManager jobDataManager4 = mock(DeadLetterJobDataManager.class);
    doNothing().when(jobDataManager4).delete(Mockito.<DeadLetterJobEntity>any());
    when(jobDataManager4.findJobsByExecutionId(Mockito.<String>any())).thenReturn(deadLetterJobEntityList);
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        processEngineConfiguration, jobDataManager4);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration2.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManagerImpl);
    when(processEngineConfiguration2.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManagerImpl);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    when(processEngineConfiguration2.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);
    when(processEngineConfiguration2.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfiguration2.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration2,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getVariableInstancesLocal()).thenReturn(new HashMap<>());
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.cancelExecutionAndRelatedData(executionEntity, "Just cause"));
    verify(executionEntity, atLeast(1)).getId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).isActive();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfiguration2).getDeadLetterJobEntityManager();
    verify(processEngineConfiguration2).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2).getHistoryManager();
    verify(processEngineConfiguration2).getIdentityLinkEntityManager();
    verify(processEngineConfiguration2).getJobEntityManager();
    verify(processEngineConfiguration2).getSuspendedJobEntityManager();
    verify(processEngineConfiguration2).getTimerJobEntityManager();
    verify(executionEntity).setEnded(eq(true));
    verify(jobDataManager4).delete(isA(DeadLetterJobEntity.class));
    verify(jobDataManager4).findJobsByExecutionId(eq("42"));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
    verify(jobDataManager2).findJobsByExecutionId(eq("42"));
    verify(jobDataManager3).findJobsByExecutionId(eq("42"));
    verify(jobDataManager).findJobsByExecutionId(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"})
  public void testCancelExecutionAndRelatedData_thenCallsDispatchEvent() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), identityLinkDataManager);

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager);

    JobDataManager jobDataManager2 = mock(JobDataManager.class);
    when(jobDataManager2.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(new JtaProcessEngineConfiguration(),
        jobDataManager2);

    SuspendedJobDataManager jobDataManager3 = mock(SuspendedJobDataManager.class);
    when(jobDataManager3.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    SuspendedJobEntityManagerImpl suspendedJobEntityManagerImpl = new SuspendedJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager3);

    DeadLetterJobDataManager jobDataManager4 = mock(DeadLetterJobDataManager.class);
    when(jobDataManager4.findJobsByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManagerImpl = new DeadLetterJobEntityManagerImpl(
        new JtaProcessEngineConfiguration(), jobDataManager4);

    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    when(eventSubscriptionDataManager.findEventSubscriptionsByExecution(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);

    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    when(processEngineConfigurationImpl.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    when(processEngineConfigurationImpl.getDeadLetterJobEntityManager()).thenReturn(deadLetterJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getSuspendedJobEntityManager()).thenReturn(suspendedJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    when(processEngineConfigurationImpl.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    PerformanceSettings performanceSettings = mock(PerformanceSettings.class);
    doNothing().when(performanceSettings).setEnableEagerExecutionTreeFetching(anyBoolean());
    doNothing().when(performanceSettings).setEnableExecutionRelationshipCounts(anyBoolean());
    doNothing().when(performanceSettings).setEnableLocalization(anyBoolean());
    doNothing().when(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(anyBoolean());
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getActivityId()).thenReturn("42");
    when(executionEntity.getProcessDefinitionId()).thenReturn("42");
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    doNothing().when(executionEntity).setDeleted(anyBoolean());
    when(executionEntity.getVariableInstancesLocal()).thenReturn(new HashMap<>());
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act
    executionEntityManagerImpl.cancelExecutionAndRelatedData(executionEntity, "Just cause");

    // Assert
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(executionEntity, atLeast(1)).getId();
    verify(executionEntity, atLeast(1)).getProcessDefinitionId();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).isActive();
    verify(executionEntity).setActive(eq(false));
    verify(executionEntity).getVariableInstancesLocal();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(performanceSettings).setEnableEagerExecutionTreeFetching(eq(true));
    verify(performanceSettings).setEnableExecutionRelationshipCounts(eq(true));
    verify(performanceSettings).setEnableLocalization(eq(true));
    verify(performanceSettings).setValidateExecutionRelationshipCountConfigOnBoot(eq(true));
    verify(processEngineConfigurationImpl).getDeadLetterJobEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getEventSubscriptionEntityManager();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getJobEntityManager();
    verify(processEngineConfigurationImpl).getSuspendedJobEntityManager();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(processEngineConfigurationImpl).getTimerJobEntityManager();
    verify(executionEntity).setDeleted(eq(true));
    verify(executionEntity).setEnded(eq(true));
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    verify(jobDataManager4).findJobsByExecutionId(eq("42"));
    verify(eventSubscriptionDataManager).findEventSubscriptionsByExecution(eq("42"));
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
    verify(jobDataManager2).findJobsByExecutionId(eq("42"));
    verify(jobDataManager3).findJobsByExecutionId(eq("42"));
    verify(taskDataManager).findTasksByExecutionId(eq("42"));
    verify(jobDataManager).findJobsByExecutionId(eq("42"));
    verify(executionEntity).getActivityId();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)"})
  public void testDeleteProcessInstanceExecutionEntity() {
    // Arrange
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstanceExecutionEntity("42", "42", "Just cause", true, true));
    verify(executionDataManager).findById(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)"})
  public void testDeleteProcessInstanceExecutionEntity2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isDeleted()).thenReturn(true);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    // Act
    executionEntityManagerImpl.deleteProcessInstanceExecutionEntity("42", "42", "Just cause", true, true);

    // Assert
    verify(executionEntityImpl).isDeleted();
    verify(executionDataManager).findById(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutionEntityManagerImpl.deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)"})
  public void testDeleteProcessInstanceExecutionEntity3() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isDeleted()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstanceExecutionEntity("42", "42", "Just cause", true, true));
    verify(executionEntityImpl).isDeleted();
    verify(executionDataManager).findById(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteChildExecutions(ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#isDeleted()} return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteChildExecutions(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteChildExecutions(ExecutionEntity, String)"})
  public void testDeleteChildExecutions_givenExecutionEntityImplIsDeletedReturnTrue() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isDeleted()).thenReturn(true);
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    executionEntityManagerImpl.deleteChildExecutions(executionEntity, "Just cause");

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
    verify(executionEntityImpl).isDeleted();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteChildExecutions(ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteChildExecutions(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteChildExecutions(ExecutionEntity, String)"})
  public void testDeleteChildExecutions_givenNull() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(null);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    executionEntityManagerImpl.deleteChildExecutions(executionEntity, "Just cause");

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteChildExecutions(ExecutionEntity, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#deleteChildExecutions(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.deleteChildExecutions(ExecutionEntity, String)"})
  public void testDeleteChildExecutions_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isDeleted()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteChildExecutions(executionEntity, "Just cause"));
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
    verify(executionEntityImpl).isDeleted();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelChildExecutions(ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#isDeleted()} return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#cancelChildExecutions(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.cancelChildExecutions(ExecutionEntity, String)"})
  public void testCancelChildExecutions_givenExecutionEntityImplIsDeletedReturnTrue() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isDeleted()).thenReturn(true);
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    executionEntityManagerImpl.cancelChildExecutions(executionEntity, "Just cause");

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
    verify(executionEntityImpl).isDeleted();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelChildExecutions(ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#cancelChildExecutions(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.cancelChildExecutions(ExecutionEntity, String)"})
  public void testCancelChildExecutions_givenNull() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(null);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    executionEntityManagerImpl.cancelChildExecutions(executionEntity, "Just cause");

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelChildExecutions(ExecutionEntity, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#cancelChildExecutions(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.cancelChildExecutions(ExecutionEntity, String)"})
  public void testCancelChildExecutions_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isDeleted()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.cancelChildExecutions(executionEntity, "Just cause"));
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
    verify(executionEntityImpl).isDeleted();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity, List)} with {@code executionEntity}, {@code collectedChildExecution}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.collectChildren(ExecutionEntity, List)"})
  public void testCollectChildrenWithExecutionEntityCollectedChildExecution_givenNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(null);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    executionEntityManagerImpl.collectChildren(executionEntity, new ArrayList<>());

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity, List)} with {@code executionEntity}, {@code collectedChildExecution}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#isDeleted()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.collectChildren(ExecutionEntity, List)"})
  public void testCollectChildrenWithExecutionEntityCollectedChildExecution_thenCallsIsDeleted() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isDeleted()).thenReturn(true);
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    executionEntityManagerImpl.collectChildren(executionEntity, new ArrayList<>());

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
    verify(executionEntityImpl).isDeleted();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity)} with {@code executionEntity}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.collectChildren(ExecutionEntity)"})
  public void testCollectChildrenWithExecutionEntity_givenNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(null);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    List<ExecutionEntity> actualCollectChildrenResult = executionEntityManagerImpl.collectChildren(executionEntity);

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
    assertTrue(actualCollectChildrenResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity)} with {@code executionEntity}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#isDeleted()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.collectChildren(ExecutionEntity)"})
  public void testCollectChildrenWithExecutionEntity_thenCallsIsDeleted() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isDeleted()).thenReturn(true);
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    List<ExecutionEntity> actualCollectChildrenResult = executionEntityManagerImpl.collectChildren(executionEntity);

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
    verify(executionEntityImpl).isDeleted();
    assertTrue(actualCollectChildrenResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findFirstScope(ExecutionEntity)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findFirstScope(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findFirstScope(ExecutionEntity)"})
  public void testFindFirstScope() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setScope(false);

    // Act and Assert
    assertNull(executionEntityManagerImpl.findFirstScope(executionEntity));
    assertTrue(executionEntity.getEventSubscriptions().isEmpty());
    assertTrue(executionEntity.getExecutions().isEmpty());
    assertTrue(executionEntity.getIdentityLinks().isEmpty());
    assertTrue(executionEntity.getJobs().isEmpty());
    assertTrue(executionEntity.getTasks().isEmpty());
    assertTrue(executionEntity.getTimerJobs().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findFirstScope(ExecutionEntity)}.
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findFirstScope(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findFirstScope(ExecutionEntity)"})
  public void testFindFirstScope_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntity actualFindFirstScopeResult = executionEntityManagerImpl.findFirstScope(executionEntity);

    // Assert
    assertSame(executionEntity, actualFindFirstScopeResult);
    List<EventSubscriptionEntity> expectedEventSubscriptions = ((ExecutionEntityImpl) actualFindFirstScopeResult).eventSubscriptions;
    assertSame(expectedEventSubscriptions, executionEntity.getEventSubscriptions());
    List<ExecutionEntityImpl> expectedExecutions = ((ExecutionEntityImpl) actualFindFirstScopeResult).executions;
    assertSame(expectedExecutions, executionEntity.getExecutions());
    List<IdentityLinkEntity> expectedIdentityLinks = ((ExecutionEntityImpl) actualFindFirstScopeResult).identityLinks;
    assertSame(expectedIdentityLinks, executionEntity.getIdentityLinks());
    List<JobEntity> expectedJobs = ((ExecutionEntityImpl) actualFindFirstScopeResult).jobs;
    assertSame(expectedJobs, executionEntity.getJobs());
    List<TaskEntity> expectedTasks = ((ExecutionEntityImpl) actualFindFirstScopeResult).tasks;
    assertSame(expectedTasks, executionEntity.getTasks());
    List<TimerJobEntity> expectedTimerJobs = ((ExecutionEntityImpl) actualFindFirstScopeResult).timerJobs;
    assertSame(expectedTimerJobs, executionEntity.getTimerJobs());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findFirstScope(ExecutionEntity)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findFirstScope(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findFirstScope(ExecutionEntity)"})
  public void testFindFirstScope_whenNull_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertNull((new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()))).findFirstScope(null));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findFirstMultiInstanceRoot(ExecutionEntity)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#findFirstMultiInstanceRoot(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findFirstMultiInstanceRoot(ExecutionEntity)"})
  public void testFindFirstMultiInstanceRoot() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl = new ExecutionEntityManagerImpl(processEngineConfiguration,
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertNull(executionEntityManagerImpl
        .findFirstMultiInstanceRoot(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.updateProcessInstanceLockTime(String)"})
  public void testUpdateProcessInstanceLockTime_thenCallsGetAsyncExecutor() {
    // Arrange
    when(processEngineConfigurationImpl.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    doNothing().when(executionDataManager)
        .updateProcessInstanceLockTime(Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Date>any());

    // Act
    executionEntityManagerImpl.updateProcessInstanceLockTime("42");

    // Assert
    verify(processEngineConfigurationImpl).getAsyncExecutor();
    verify(processEngineConfigurationImpl).getClock();
    verify(executionDataManager).updateProcessInstanceLockTime(eq("42"), isA(Date.class), isA(Date.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.updateProcessInstanceLockTime(String)"})
  public void testUpdateProcessInstanceLockTime_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(processEngineConfigurationImpl.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(executionDataManager)
        .updateProcessInstanceLockTime(Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Date>any());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.updateProcessInstanceLockTime("42"));
    verify(processEngineConfigurationImpl).getAsyncExecutor();
    verify(processEngineConfigurationImpl).getClock();
    verify(executionDataManager).updateProcessInstanceLockTime(eq("42"), isA(Date.class), isA(Date.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#clearProcessInstanceLockTime(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#clearProcessInstanceLockTime(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.clearProcessInstanceLockTime(String)"})
  public void testClearProcessInstanceLockTime() {
    // Arrange
    doNothing().when(executionDataManager).clearProcessInstanceLockTime(Mockito.<String>any());

    // Act
    executionEntityManagerImpl.clearProcessInstanceLockTime("42");

    // Assert
    verify(executionDataManager).clearProcessInstanceLockTime(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#clearProcessInstanceLockTime(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#clearProcessInstanceLockTime(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.clearProcessInstanceLockTime(String)"})
  public void testClearProcessInstanceLockTime_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(executionDataManager)
        .clearProcessInstanceLockTime(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.clearProcessInstanceLockTime("42"));
    verify(executionDataManager).clearProcessInstanceLockTime(eq("42"));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"})
  public void testUpdateProcessInstanceBusinessKey() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    String actualUpdateProcessInstanceBusinessKeyResult = executionEntityManagerImpl
        .updateProcessInstanceBusinessKey(executionEntity, null);

    // Assert
    assertNull(executionEntity.getBusinessKey());
    assertNull(actualUpdateProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"})
  public void testUpdateProcessInstanceBusinessKey2() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    String actualUpdateProcessInstanceBusinessKeyResult = executionEntityManagerImpl
        .updateProcessInstanceBusinessKey(executionEntity, "Business Key");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    assertEquals("Business Key", executionEntity.getBusinessKey());
    assertEquals("Business Key", actualUpdateProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"})
  public void testUpdateProcessInstanceBusinessKey3() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.updateProcessInstanceBusinessKey(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Business Key"));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"})
  public void testUpdateProcessInstanceBusinessKey4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(activitiEventDispatcher)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.updateProcessInstanceBusinessKey(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Business Key"));
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"})
  public void testUpdateProcessInstanceBusinessKey5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    String actualUpdateProcessInstanceBusinessKeyResult = executionEntityManagerImpl
        .updateProcessInstanceBusinessKey(executionEntity, "Business Key");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    assertEquals("Business Key", executionEntity.getBusinessKey());
    assertEquals("Business Key", actualUpdateProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}.
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"})
  public void testUpdateProcessInstanceBusinessKey6() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.updateProcessInstanceBusinessKey(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
            "org.activiti.engine.impl.persistence.CountingExecutionEntity"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"})
  public void testUpdateProcessInstanceBusinessKey_givenFalse() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.isProcessInstanceType()).thenReturn(false);

    // Act
    String actualUpdateProcessInstanceBusinessKeyResult = executionEntityManagerImpl
        .updateProcessInstanceBusinessKey(executionEntity, "Business Key");

    // Assert
    verify(executionEntity).isProcessInstanceType();
    assertNull(actualUpdateProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"})
  public void testUpdateProcessInstanceBusinessKey_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    String actualUpdateProcessInstanceBusinessKeyResult = executionEntityManagerImpl
        .updateProcessInstanceBusinessKey(executionEntity, "Business Key");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    assertEquals("Business Key", executionEntity.getBusinessKey());
    assertEquals("Business Key", actualUpdateProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setBusinessKey(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"})
  public void testUpdateProcessInstanceBusinessKey_thenCallsSetBusinessKey() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(executionEntity)
        .setBusinessKey(Mockito.<String>any());
    when(executionEntity.isProcessInstanceType()).thenReturn(true);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.updateProcessInstanceBusinessKey(executionEntity, "Business Key"));
    verify(executionEntity).isProcessInstanceType();
    verify(executionEntity).setBusinessKey(eq("Business Key"));
  }
}
