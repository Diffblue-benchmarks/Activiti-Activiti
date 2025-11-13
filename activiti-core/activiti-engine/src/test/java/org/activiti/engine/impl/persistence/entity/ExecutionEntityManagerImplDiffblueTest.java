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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.TaskDataManager;
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
  @Mock private ExecutionDataManager executionDataManager;

  @InjectMocks private ExecutionEntityManagerImpl executionEntityManagerImpl;

  @Mock private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       ExecutionEntityManagerImpl#ExecutionEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       ExecutionDataManager)}
   *   <li>{@link ExecutionEntityManagerImpl#setExecutionDataManager(ExecutionDataManager)}
   *   <li>{@link ExecutionEntityManagerImpl#getDataManager()}
   *   <li>{@link ExecutionEntityManagerImpl#getExecutionDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, ExecutionDataManager)",
    "DataManager ExecutionEntityManagerImpl.getDataManager()",
    "ExecutionDataManager ExecutionEntityManagerImpl.getExecutionDataManager()",
    "void ExecutionEntityManagerImpl.setExecutionDataManager(ExecutionDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ExecutionEntityManagerImpl actualExecutionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    MybatisExecutionDataManager executionDataManager =
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration());
    actualExecutionEntityManagerImpl.setExecutionDataManager(executionDataManager);
    DataManager<ExecutionEntity> actualDataManager =
        actualExecutionEntityManagerImpl.getDataManager();

    // Assert
    assertSame(executionDataManager, actualDataManager);
    assertSame(executionDataManager, actualExecutionEntityManagerImpl.getExecutionDataManager());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)} with {@code
   * ExecutionEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity, boolean)"})
  public void testDeleteWithExecutionEntityBoolean_thenCallsDelete() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());

    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager);
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executionEntityManagerImpl.delete(entity, true);

    // Assert
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)} with {@code
   * ExecutionEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link MybatisExecutionDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity, boolean)"})
  public void testDeleteWithExecutionEntityBoolean_thenCallsDelete2() {
    // Arrange
    MybatisExecutionDataManager executionDataManager = mock(MybatisExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executionEntityManagerImpl.delete(entity, false);

    // Assert
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#delete(ExecutionEntity)} with {@code ExecutionEntity}.
   *
   * <ul>
   *   <li>Then createWithEmptyRelationshipCollections Deleted.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#delete(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.delete(ExecutionEntity)"})
  public void testDeleteWithExecutionEntity_thenCreateWithEmptyRelationshipCollectionsDeleted() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).delete(Mockito.<ExecutionEntity>any());

    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager);
    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    executionEntityManagerImpl.delete(entity);

    // Assert
    verify(executionDataManager).delete(isA(ExecutionEntity.class));
    assertTrue(entity.isDeleted());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findSubProcessInstanceBySuperExecutionId(String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findSubProcessInstanceBySuperExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findSubProcessInstanceBySuperExecutionId(String)"
  })
  public void testFindSubProcessInstanceBySuperExecutionId() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(executionDataManager.findSubProcessInstanceBySuperExecutionId(Mockito.<String>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    ExecutionEntity actualFindSubProcessInstanceBySuperExecutionIdResult =
        executionEntityManagerImpl.findSubProcessInstanceBySuperExecutionId("42");

    // Assert
    verify(executionDataManager).findSubProcessInstanceBySuperExecutionId("42");
    assertSame(
        createWithEmptyRelationshipCollectionsResult,
        actualFindSubProcessInstanceBySuperExecutionIdResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findSubProcessInstanceBySuperExecutionId(String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findSubProcessInstanceBySuperExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findSubProcessInstanceBySuperExecutionId(String)"
  })
  public void testFindSubProcessInstanceBySuperExecutionId2() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.findSubProcessInstanceBySuperExecutionId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findSubProcessInstanceBySuperExecutionId("42"));
    verify(executionDataManager).findSubProcessInstanceBySuperExecutionId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findChildExecutionsByParentExecutionId(String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findChildExecutionsByParentExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findChildExecutionsByParentExecutionId(String)"
  })
  public void testFindChildExecutionsByParentExecutionId() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findChildExecutionsByParentExecutionId("42"));
    verify(executionDataManager).findChildExecutionsByParentExecutionId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findChildExecutionsByParentExecutionId(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findChildExecutionsByParentExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findChildExecutionsByParentExecutionId(String)"
  })
  public void testFindChildExecutionsByParentExecutionId_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByParentExecutionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<ExecutionEntity> actualFindChildExecutionsByParentExecutionIdResult =
        executionEntityManagerImpl.findChildExecutionsByParentExecutionId("42");

    // Assert
    verify(executionDataManager).findChildExecutionsByParentExecutionId("42");
    assertTrue(actualFindChildExecutionsByParentExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findChildExecutionsByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findChildExecutionsByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findChildExecutionsByProcessInstanceId(String)"
  })
  public void testFindChildExecutionsByProcessInstanceId() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.findChildExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findChildExecutionsByProcessInstanceId("42"));
    verify(executionDataManager).findChildExecutionsByProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findChildExecutionsByProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findChildExecutionsByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findChildExecutionsByProcessInstanceId(String)"
  })
  public void testFindChildExecutionsByProcessInstanceId_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findChildExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<ExecutionEntity> actualFindChildExecutionsByProcessInstanceIdResult =
        executionEntityManagerImpl.findChildExecutionsByProcessInstanceId("42");

    // Assert
    verify(executionDataManager).findChildExecutionsByProcessInstanceId("42");
    assertTrue(actualFindChildExecutionsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String,
   * Collection)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds(String, Collection)"
  })
  public void testFindExecutionsByParentExecutionAndActivityIds() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.findExecutionsByParentExecutionAndActivityIds(
            Mockito.<String>any(), Mockito.<Collection<String>>any()))
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds(
                "42", new ArrayList<>()));
    verify(executionDataManager)
        .findExecutionsByParentExecutionAndActivityIds(eq("42"), isA(Collection.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String,
   * Collection)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds(String, Collection)"
  })
  public void testFindExecutionsByParentExecutionAndActivityIds_given42_whenArrayListAdd42() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByParentExecutionAndActivityIds(
            Mockito.<String>any(), Mockito.<Collection<String>>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ArrayList<String> activityIds = new ArrayList<>();
    activityIds.add("42");
    activityIds.add("foo");

    // Act
    List<ExecutionEntity> actualFindExecutionsByParentExecutionAndActivityIdsResult =
        executionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds("42", activityIds);

    // Assert
    verify(executionDataManager)
        .findExecutionsByParentExecutionAndActivityIds(eq("42"), isA(Collection.class));
    assertTrue(actualFindExecutionsByParentExecutionAndActivityIdsResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String,
   * Collection)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds(String, Collection)"
  })
  public void testFindExecutionsByParentExecutionAndActivityIds_givenFoo_whenArrayListAddFoo() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByParentExecutionAndActivityIds(
            Mockito.<String>any(), Mockito.<Collection<String>>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    ArrayList<String> activityIds = new ArrayList<>();
    activityIds.add("foo");

    // Act
    List<ExecutionEntity> actualFindExecutionsByParentExecutionAndActivityIdsResult =
        executionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds("42", activityIds);

    // Assert
    verify(executionDataManager)
        .findExecutionsByParentExecutionAndActivityIds(eq("42"), isA(Collection.class));
    assertTrue(actualFindExecutionsByParentExecutionAndActivityIdsResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String,
   * Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findExecutionsByParentExecutionAndActivityIds(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds(String, Collection)"
  })
  public void testFindExecutionsByParentExecutionAndActivityIds_whenArrayList_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByParentExecutionAndActivityIds(
            Mockito.<String>any(), Mockito.<Collection<String>>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<ExecutionEntity> actualFindExecutionsByParentExecutionAndActivityIdsResult =
        executionEntityManagerImpl.findExecutionsByParentExecutionAndActivityIds(
            "42", new ArrayList<>());

    // Assert
    verify(executionDataManager)
        .findExecutionsByParentExecutionAndActivityIds(eq("42"), isA(Collection.class));
    assertTrue(actualFindExecutionsByParentExecutionAndActivityIdsResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionCountByQueryCriteria(ExecutionQueryImpl)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findExecutionCountByQueryCriteria(ExecutionQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long ExecutionEntityManagerImpl.findExecutionCountByQueryCriteria(ExecutionQueryImpl)"
  })
  public void testFindExecutionCountByQueryCriteria_thenReturnThree() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionCountByQueryCriteria(Mockito.<ExecutionQueryImpl>any()))
        .thenReturn(3L);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    long actualFindExecutionCountByQueryCriteriaResult =
        executionEntityManagerImpl.findExecutionCountByQueryCriteria(new ExecutionQueryImpl());

    // Assert
    verify(executionDataManager).findExecutionCountByQueryCriteria(isA(ExecutionQueryImpl.class));
    assertEquals(3L, actualFindExecutionCountByQueryCriteriaResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByQueryCriteria(ExecutionQueryImpl,
   * Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findExecutionsByQueryCriteria(ExecutionQueryImpl, Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findExecutionsByQueryCriteria(ExecutionQueryImpl, Page)"
  })
  public void testFindExecutionsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByQueryCriteria(
            Mockito.<ExecutionQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);
    ExecutionQueryImpl executionQuery = new ExecutionQueryImpl();

    // Act
    List<ExecutionEntity> actualFindExecutionsByQueryCriteriaResult =
        executionEntityManagerImpl.findExecutionsByQueryCriteria(executionQuery, new Page(1, 3));

    // Assert
    verify(executionDataManager)
        .findExecutionsByQueryCriteria(isA(ExecutionQueryImpl.class), isA(Page.class));
    assertTrue(actualFindExecutionsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link
   * ExecutionEntityManagerImpl#findProcessInstanceCountByQueryCriteria(ProcessInstanceQueryImpl)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findProcessInstanceCountByQueryCriteria(ProcessInstanceQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long ExecutionEntityManagerImpl.findProcessInstanceCountByQueryCriteria(ProcessInstanceQueryImpl)"
  })
  public void testFindProcessInstanceCountByQueryCriteria_thenReturnThree() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findProcessInstanceCountByQueryCriteria(
            Mockito.<ProcessInstanceQueryImpl>any()))
        .thenReturn(3L);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    long actualFindProcessInstanceCountByQueryCriteriaResult =
        executionEntityManagerImpl.findProcessInstanceCountByQueryCriteria(
            new ProcessInstanceQueryImpl());

    // Assert
    verify(executionDataManager)
        .findProcessInstanceCountByQueryCriteria(isA(ProcessInstanceQueryImpl.class));
    assertEquals(3L, actualFindProcessInstanceCountByQueryCriteriaResult);
  }

  /**
   * Test {@link
   * ExecutionEntityManagerImpl#findProcessInstanceByQueryCriteria(ProcessInstanceQueryImpl)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findProcessInstanceByQueryCriteria(ProcessInstanceQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findProcessInstanceByQueryCriteria(ProcessInstanceQueryImpl)"
  })
  public void testFindProcessInstanceByQueryCriteria_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findProcessInstanceByQueryCriteria(
            Mockito.<ProcessInstanceQueryImpl>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<ProcessInstance> actualFindProcessInstanceByQueryCriteriaResult =
        executionEntityManagerImpl.findProcessInstanceByQueryCriteria(
            new ProcessInstanceQueryImpl());

    // Assert
    verify(executionDataManager)
        .findProcessInstanceByQueryCriteria(isA(ProcessInstanceQueryImpl.class));
    assertTrue(actualFindProcessInstanceByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
  public void testFindByRootProcessInstanceId() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntityImpl.getRootProcessInstanceId())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntityImpl);

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findByRootProcessInstanceId("42"));
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getRootProcessInstanceId();
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
  public void testFindByRootProcessInstanceId2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionEntityImpl)
        .setRootProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getId()).thenReturn("42");

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(executionEntityImpl);

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findByRootProcessInstanceId("42"));
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
  public void testFindByRootProcessInstanceId3() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionEntityImpl)
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
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
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
  public void testFindByRootProcessInstanceId4() {
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

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
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
  public void testFindByRootProcessInstanceId5() {
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

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
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
  public void testFindByRootProcessInstanceId6() {
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

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
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
  public void testFindByRootProcessInstanceId7() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findByRootProcessInstanceId("42"));
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getParentId()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

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
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#addChildExecution(ExecutionEntity)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

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
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
  public void testFindByRootProcessInstanceId_thenReturnNull() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    ExecutionEntity actualFindByRootProcessInstanceIdResult =
        executionEntityManagerImpl.findByRootProcessInstanceId("42");

    // Assert
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("42");
    assertNull(actualFindByRootProcessInstanceIdResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>When {@code Root Process Instance Id}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findByRootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findByRootProcessInstanceId(String)"
  })
  public void testFindByRootProcessInstanceId_whenRootProcessInstanceId_thenReturnNull() {
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByRootProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    ExecutionEntity actualFindByRootProcessInstanceIdResult =
        executionEntityManagerImpl.findByRootProcessInstanceId("Root Process Instance Id");

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
    verify(executionDataManager).findExecutionsByRootProcessInstanceId("Root Process Instance Id");
    assertNull(actualFindByRootProcessInstanceIdResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"
  })
  public void testProcessExecutionTree() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntityImpl.getRootProcessInstanceId())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionEntityImpl.getId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setParentId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    executionEntityImpl.setRootProcessInstanceId("Executions");
    executionEntityImpl.setProcessInstanceId("Executions");
    executionEntityImpl.setParentId("Executions");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.processExecutionTree("42", executions));
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getRootProcessInstanceId();
    verify(executionEntityImpl).setParentId("Executions");
    verify(executionEntityImpl).setProcessInstanceId("Executions");
    verify(executionEntityImpl).setRootProcessInstanceId("Executions");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"
  })
  public void testProcessExecutionTree2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionEntityImpl)
        .setRootProcessInstance(Mockito.<ExecutionEntity>any());
    when(executionEntityImpl.getRootProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getId()).thenReturn("42");
    doNothing().when(executionEntityImpl).setParentId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    executionEntityImpl.setRootProcessInstanceId("Executions");
    executionEntityImpl.setProcessInstanceId("Executions");
    executionEntityImpl.setParentId("Executions");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.processExecutionTree("42", executions));
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).setParentId("Executions");
    verify(executionEntityImpl).setProcessInstanceId("Executions");
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstanceId("Executions");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"
  })
  public void testProcessExecutionTree3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionEntityImpl)
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
    doNothing().when(executionEntityImpl).setParentId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    executionEntityImpl.setRootProcessInstanceId("Executions");
    executionEntityImpl.setProcessInstanceId("Executions");
    executionEntityImpl.setParentId("Executions");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.processExecutionTree("42", executions));
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setParentId("Executions");
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstanceId("Executions");
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstanceId("Executions");
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"
  })
  public void testProcessExecutionTree4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

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
    doNothing().when(executionEntityImpl).setParentId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    executionEntityImpl.setRootProcessInstanceId("Executions");
    executionEntityImpl.setProcessInstanceId("Executions");
    executionEntityImpl.setParentId("Executions");

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
    verify(executionEntityImpl).setParentId("Executions");
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstanceId("Executions");
    verify(executionEntityImpl).setRootProcessInstanceId("Executions");
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getParentId()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"
  })
  public void testProcessExecutionTree_givenExecutionEntityImplGetParentIdReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

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
    doNothing().when(executionEntityImpl).setParentId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    executionEntityImpl.setRootProcessInstanceId("Executions");
    executionEntityImpl.setProcessInstanceId("Executions");
    executionEntityImpl.setParentId("Executions");

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
    verify(executionEntityImpl).setParentId("Executions");
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstanceId("Executions");
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstanceId("Executions");
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getProcessInstanceId()}
   *       return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"
  })
  public void testProcessExecutionTree_givenExecutionEntityImplGetProcessInstanceIdReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

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
    doNothing().when(executionEntityImpl).setParentId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    executionEntityImpl.setRootProcessInstanceId("Executions");
    executionEntityImpl.setProcessInstanceId("Executions");
    executionEntityImpl.setParentId("Executions");

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
    verify(executionEntityImpl).setParentId("Executions");
    verify(executionEntityImpl).setProcessInstanceId("Executions");
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstanceId("Executions");
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getSuperExecution()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"
  })
  public void testProcessExecutionTree_givenExecutionEntityImplGetSuperExecutionReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

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
    doNothing().when(executionEntityImpl).setParentId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    executionEntityImpl.setRootProcessInstanceId("Executions");
    executionEntityImpl.setProcessInstanceId("Executions");
    executionEntityImpl.setParentId("Executions");

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
    verify(executionEntityImpl).setParentId("Executions");
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstanceId("Executions");
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstanceId("Executions");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#addChildExecution(ExecutionEntity)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"
  })
  public void testProcessExecutionTree_thenCallsAddChildExecution() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

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
    doNothing().when(executionEntityImpl).setParentId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    executionEntityImpl.setRootProcessInstanceId("Executions");
    executionEntityImpl.setProcessInstanceId("Executions");
    executionEntityImpl.setParentId("Executions");

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
    verify(executionEntityImpl).setParentId("Executions");
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstanceId("Executions");
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstanceId("Executions");
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"
  })
  public void testProcessExecutionTree_whenArrayList_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertNull(executionEntityManagerImpl.processExecutionTree("42", new ArrayList<>()));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}.
   *
   * <ul>
   *   <li>When {@code Root Process Instance Id}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#processExecutionTree(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.processExecutionTree(String, List)"
  })
  public void testProcessExecutionTree_whenRootProcessInstanceId_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

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
    doNothing().when(executionEntityImpl).setParentId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(executionEntityImpl).setRootProcessInstanceId(Mockito.<String>any());
    executionEntityImpl.setRootProcessInstanceId("Executions");
    executionEntityImpl.setProcessInstanceId("Executions");
    executionEntityImpl.setParentId("Executions");

    ArrayList<ExecutionEntity> executions = new ArrayList<>();
    executions.add(executionEntityImpl);

    // Act
    ExecutionEntity actualProcessExecutionTreeResult =
        executionEntityManagerImpl.processExecutionTree("Root Process Instance Id", executions);

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl, atLeast(1)).getParentId();
    verify(executionEntityImpl, atLeast(1)).getProcessInstanceId();
    verify(executionEntityImpl, atLeast(1)).getRootProcessInstanceId();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).getSuperExecutionId();
    verify(executionEntityImpl).setParent(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setParentId("Executions");
    verify(executionEntityImpl).setProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setProcessInstanceId("Executions");
    verify(executionEntityImpl).setRootProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setRootProcessInstanceId("Executions");
    verify(executionEntityImpl).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
    assertNull(actualProcessExecutionTreeResult);
  }

  /**
   * Test {@link
   * ExecutionEntityManagerImpl#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl)"
  })
  public void testFindProcessInstanceAndVariablesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findProcessInstanceAndVariablesByQueryCriteria(
            Mockito.<ProcessInstanceQueryImpl>any()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<ProcessInstance> actualFindProcessInstanceAndVariablesByQueryCriteriaResult =
        executionEntityManagerImpl.findProcessInstanceAndVariablesByQueryCriteria(
            new ProcessInstanceQueryImpl());

    // Assert
    verify(executionDataManager)
        .findProcessInstanceAndVariablesByQueryCriteria(isA(ProcessInstanceQueryImpl.class));
    assertTrue(actualFindProcessInstanceAndVariablesByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findInactiveExecutionsByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findInactiveExecutionsByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection ExecutionEntityManagerImpl.findInactiveExecutionsByProcessInstanceId(String)"
  })
  public void testFindInactiveExecutionsByProcessInstanceId() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.findInactiveExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.findInactiveExecutionsByProcessInstanceId("42"));
    verify(executionDataManager).findInactiveExecutionsByProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findInactiveExecutionsByProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>Then return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findInactiveExecutionsByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection ExecutionEntityManagerImpl.findInactiveExecutionsByProcessInstanceId(String)"
  })
  public void testFindInactiveExecutionsByProcessInstanceId_thenReturnList() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    when(executionDataManager.findInactiveExecutionsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    Collection<ExecutionEntity> actualFindInactiveExecutionsByProcessInstanceIdResult =
        executionEntityManagerImpl.findInactiveExecutionsByProcessInstanceId("42");

    // Assert
    verify(executionDataManager).findInactiveExecutionsByProcessInstanceId("42");
    assertTrue(actualFindInactiveExecutionsByProcessInstanceIdResult instanceof List);
    assertTrue(actualFindInactiveExecutionsByProcessInstanceIdResult.isEmpty());
    assertSame(executionEntityList, actualFindInactiveExecutionsByProcessInstanceIdResult);
  }

  /**
   * Test {@link
   * ExecutionEntityManagerImpl#findInactiveExecutionsByActivityIdAndProcessInstanceId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findInactiveExecutionsByActivityIdAndProcessInstanceId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection ExecutionEntityManagerImpl.findInactiveExecutionsByActivityIdAndProcessInstanceId(String, String)"
  })
  public void testFindInactiveExecutionsByActivityIdAndProcessInstanceId() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.findInactiveExecutionsByActivityIdAndProcessInstanceId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.findInactiveExecutionsByActivityIdAndProcessInstanceId(
                "42", "42"));
    verify(executionDataManager).findInactiveExecutionsByActivityIdAndProcessInstanceId("42", "42");
  }

  /**
   * Test {@link
   * ExecutionEntityManagerImpl#findInactiveExecutionsByActivityIdAndProcessInstanceId(String,
   * String)}.
   *
   * <ul>
   *   <li>Then return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findInactiveExecutionsByActivityIdAndProcessInstanceId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Collection ExecutionEntityManagerImpl.findInactiveExecutionsByActivityIdAndProcessInstanceId(String, String)"
  })
  public void testFindInactiveExecutionsByActivityIdAndProcessInstanceId_thenReturnList() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    when(executionDataManager.findInactiveExecutionsByActivityIdAndProcessInstanceId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(executionEntityList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    Collection<ExecutionEntity> actualFindInactiveExecutionsByActivityIdAndProcessInstanceIdResult =
        executionEntityManagerImpl.findInactiveExecutionsByActivityIdAndProcessInstanceId(
            "42", "42");

    // Assert
    verify(executionDataManager).findInactiveExecutionsByActivityIdAndProcessInstanceId("42", "42");
    assertTrue(actualFindInactiveExecutionsByActivityIdAndProcessInstanceIdResult instanceof List);
    assertTrue(actualFindInactiveExecutionsByActivityIdAndProcessInstanceIdResult.isEmpty());
    assertSame(
        executionEntityList, actualFindInactiveExecutionsByActivityIdAndProcessInstanceIdResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionsByNativeQuery(Map, int, int)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findExecutionsByNativeQuery(Map, int,
   * int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.findExecutionsByNativeQuery(Map, int, int)"})
  public void testFindExecutionsByNativeQuery_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionsByNativeQuery(
            Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<Execution> actualFindExecutionsByNativeQueryResult =
        executionEntityManagerImpl.findExecutionsByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(executionDataManager).findExecutionsByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindExecutionsByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findProcessInstanceByNativeQuery(Map, int, int)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findProcessInstanceByNativeQuery(Map,
   * int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExecutionEntityManagerImpl.findProcessInstanceByNativeQuery(Map, int, int)"
  })
  public void testFindProcessInstanceByNativeQuery_thenReturnEmpty() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findProcessInstanceByNativeQuery(
            Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    List<ProcessInstance> actualFindProcessInstanceByNativeQueryResult =
        executionEntityManagerImpl.findProcessInstanceByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(executionDataManager).findProcessInstanceByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindProcessInstanceByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findExecutionCountByNativeQuery(Map)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findExecutionCountByNativeQuery(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ExecutionEntityManagerImpl.findExecutionCountByNativeQuery(Map)"})
  public void testFindExecutionCountByNativeQuery_thenReturnThree() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findExecutionCountByNativeQuery(Mockito.<Map<String, Object>>any()))
        .thenReturn(3L);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    long actualFindExecutionCountByNativeQueryResult =
        executionEntityManagerImpl.findExecutionCountByNativeQuery(new HashMap<>());

    // Assert
    verify(executionDataManager).findExecutionCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindExecutionCountByNativeQueryResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getId()).thenReturn("42");
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager);

    // Act
    executionEntityManagerImpl.createProcessInstanceExecution(
        new ProcessDefinitionEntityImpl(), "Business Key", "42", "Initiator Variable Name");

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey("Business Key");
    verify(executionEntityImpl).setProcessDefinitionId(null);
    verify(executionEntityImpl).setProcessDefinitionKey(null);
    verify(executionEntityImpl).setProcessDefinitionName(null);
    verify(executionEntityImpl).setProcessDefinitionVersion(0);
    verify(executionEntityImpl).setProcessInstanceId("42");
    verify(executionEntityImpl).setRootProcessInstanceId("42");
    verify(executionEntityImpl).setScope(true);
    verify(executionEntityImpl).setStartUserId(null);
    verify(executionEntityImpl).setTenantId("42");
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntityImpl.getProcessInstanceId())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionEntityImpl.getId()).thenReturn("42");
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createProcessInstanceExecution(
                new ProcessDefinitionEntityImpl(),
                "Business Key",
                "42",
                "Initiator Variable Name"));
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey("Business Key");
    verify(executionEntityImpl).setProcessDefinitionId(null);
    verify(executionEntityImpl).setProcessDefinitionKey(null);
    verify(executionEntityImpl).setProcessDefinitionName(null);
    verify(executionEntityImpl).setProcessDefinitionVersion(0);
    verify(executionEntityImpl).setProcessInstanceId("42");
    verify(executionEntityImpl).setRootProcessInstanceId("42");
    verify(executionEntityImpl).setScope(true);
    verify(executionEntityImpl).setStartUserId(null);
    verify(executionEntityImpl).setTenantId("42");
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setCountEnabled(anyBoolean());
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getId()).thenReturn("42");
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager);

    // Act
    executionEntityManagerImpl.createProcessInstanceExecution(
        new ProcessDefinitionEntityImpl(), "Business Key", "42", "Initiator Variable Name");

    // Assert
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey("Business Key");
    verify(executionEntityImpl).setCountEnabled(true);
    verify(executionEntityImpl).setProcessDefinitionId(null);
    verify(executionEntityImpl).setProcessDefinitionKey(null);
    verify(executionEntityImpl).setProcessDefinitionName(null);
    verify(executionEntityImpl).setProcessDefinitionVersion(0);
    verify(executionEntityImpl).setProcessInstanceId("42");
    verify(executionEntityImpl).setRootProcessInstanceId("42");
    verify(executionEntityImpl).setScope(true);
    verify(executionEntityImpl).setStartUserId(null);
    verify(executionEntityImpl).setTenantId("42");
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionEntityImpl)
        .setCountEnabled(anyBoolean());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createProcessInstanceExecution(
                new ProcessDefinitionEntityImpl(),
                "Business Key",
                "42",
                "Initiator Variable Name"));
    verify(executionEntityImpl).setCountEnabled(true);
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution5() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setCountEnabled(anyBoolean());
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getId()).thenReturn("42");
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager);

    // Act
    executionEntityManagerImpl.createProcessInstanceExecution(
        new ProcessDefinitionEntityImpl(), "Business Key", "42", "Initiator Variable Name");

    // Assert
    verify(eventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(eventDispatcher).isEnabled();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey("Business Key");
    verify(executionEntityImpl).setCountEnabled(true);
    verify(executionEntityImpl).setProcessDefinitionId(null);
    verify(executionEntityImpl).setProcessDefinitionKey(null);
    verify(executionEntityImpl).setProcessDefinitionName(null);
    verify(executionEntityImpl).setProcessDefinitionVersion(0);
    verify(executionEntityImpl).setProcessInstanceId("42");
    verify(executionEntityImpl).setRootProcessInstanceId("42");
    verify(executionEntityImpl).setScope(true);
    verify(executionEntityImpl).setStartUserId(null);
    verify(executionEntityImpl).setTenantId("42");
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution6() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(eventDispatcher)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    doNothing().when(executionEntityImpl).setCountEnabled(anyBoolean());
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getId()).thenReturn("42");
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

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createProcessInstanceExecution(
                new ProcessDefinitionEntityImpl(),
                "Business Key",
                "42",
                "Initiator Variable Name"));
    verify(eventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(eventDispatcher).isEnabled();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey("Business Key");
    verify(executionEntityImpl).setCountEnabled(true);
    verify(executionEntityImpl).setProcessDefinitionId(null);
    verify(executionEntityImpl).setProcessDefinitionKey(null);
    verify(executionEntityImpl).setProcessDefinitionName(null);
    verify(executionEntityImpl).setProcessDefinitionVersion(0);
    verify(executionEntityImpl).setProcessInstanceId("42");
    verify(executionEntityImpl).setRootProcessInstanceId("42");
    verify(executionEntityImpl).setScope(true);
    verify(executionEntityImpl).setStartUserId(null);
    verify(executionEntityImpl).setTenantId("42");
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution7() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.create()).thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createProcessInstanceExecution(
                new ProcessDefinitionEntityImpl(),
                "Business Key",
                "42",
                "Initiator Variable Name"));
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution8() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getPerformanceSettings())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionDataManager.create())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createProcessInstanceExecution(
                new ProcessDefinitionEntityImpl(),
                "Business Key",
                "42",
                "Initiator Variable Name"));
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution9() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionDataManager)
        .insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createProcessInstanceExecution(
                new ProcessDefinitionEntityImpl(),
                "Business Key",
                "42",
                "Initiator Variable Name"));
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution10() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createProcessInstanceExecution(
                new ProcessDefinitionEntityImpl(),
                "Business Key",
                "42",
                "Initiator Variable Name"));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution11() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(false);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    when(processEngineConfigurationImpl.getPerformanceSettings()).thenReturn(performanceSettings);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionEntityImpl)
        .setProcessDefinitionId(Mockito.<String>any());
    when(executionDataManager.create()).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createProcessInstanceExecution(
                new ProcessDefinitionEntityImpl(),
                "Business Key",
                "42",
                "Initiator Variable Name"));
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl).setProcessDefinitionId(null);
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution12() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
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
    executionEntityManagerImpl.createProcessInstanceExecution(
        new ProcessDefinitionEntityImpl(), "Business Key", "42", "Initiator Variable Name");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey("Business Key");
    verify(executionEntityImpl).setCountEnabled(true);
    verify(executionEntityImpl).setProcessDefinitionId(null);
    verify(executionEntityImpl).setProcessDefinitionKey(null);
    verify(executionEntityImpl).setProcessDefinitionName(null);
    verify(executionEntityImpl).setProcessDefinitionVersion(0);
    verify(executionEntityImpl).setProcessInstanceId("42");
    verify(executionEntityImpl).setRootProcessInstanceId("42");
    verify(executionEntityImpl).setScope(true);
    verify(executionEntityImpl).setStartUserId(null);
    verify(executionEntityImpl).setTenantId("42");
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution13() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
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
    executionEntityManagerImpl.createProcessInstanceExecution(
        new ProcessDefinitionEntityImpl(), "Business Key", null, "Initiator Variable Name");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey("Business Key");
    verify(executionEntityImpl).setCountEnabled(true);
    verify(executionEntityImpl).setProcessDefinitionId(null);
    verify(executionEntityImpl).setProcessDefinitionKey(null);
    verify(executionEntityImpl).setProcessDefinitionName(null);
    verify(executionEntityImpl).setProcessDefinitionVersion(0);
    verify(executionEntityImpl).setProcessInstanceId("42");
    verify(executionEntityImpl).setRootProcessInstanceId("42");
    verify(executionEntityImpl).setScope(true);
    verify(executionEntityImpl).setStartUserId(null);
    verify(executionEntityImpl).setVariable(eq("Initiator Variable Name"), isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createProcessInstanceExecution(ProcessDefinition, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createProcessInstanceExecution(ProcessDefinition, String, String, String)"
  })
  public void testCreateProcessInstanceExecution14() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
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
    executionEntityManagerImpl.createProcessInstanceExecution(
        new ProcessDefinitionEntityImpl(), "Business Key", "42", null);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getPerformanceSettings();
    verify(executionEntityImpl, atLeast(1)).getId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessInstanceId();
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey("Business Key");
    verify(executionEntityImpl).setCountEnabled(true);
    verify(executionEntityImpl).setProcessDefinitionId(null);
    verify(executionEntityImpl).setProcessDefinitionKey(null);
    verify(executionEntityImpl).setProcessDefinitionName(null);
    verify(executionEntityImpl).setProcessDefinitionVersion(0);
    verify(executionEntityImpl).setProcessInstanceId("42");
    verify(executionEntityImpl).setRootProcessInstanceId("42");
    verify(executionEntityImpl).setScope(true);
    verify(executionEntityImpl).setStartUserId(null);
    verify(executionEntityImpl).setTenantId("42");
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#createChildExecution(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createChildExecution(ExecutionEntity)"
  })
  public void testCreateChildExecution_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl parentExecutionEntity = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(parentExecutionEntity.isCountEnabled())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.createChildExecution(parentExecutionEntity));
    verify(parentExecutionEntity).isCountEnabled();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition,
   * ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"
  })
  public void testCreateSubprocessInstance() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    ExecutionEntityImpl superExecutionEntity = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(superExecutionEntity.isCountEnabled())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createSubprocessInstance(
                processDefinition, superExecutionEntity, "Business Key"));
    verify(superExecutionEntity).isCountEnabled();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition,
   * ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"
  })
  public void testCreateSubprocessInstance2() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.create()).thenThrow(new ActivitiObjectNotFoundException(objectClass));
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createSubprocessInstance(
                processDefinition,
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
                "Business Key"));
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition,
   * ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"
  })
  public void testCreateSubprocessInstance3() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getClock())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionDataManager.create())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createSubprocessInstance(
                processDefinition,
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
                "Business Key"));
    verify(processEngineConfigurationImpl).getClock();
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition,
   * ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"
  })
  public void testCreateSubprocessInstance4() {
    // Arrange
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    when(executionDataManager.create())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    ExecutionEntityImpl superExecutionEntity = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(superExecutionEntity)
        .setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(superExecutionEntity.isCountEnabled()).thenReturn(true);
    when(superExecutionEntity.getRootProcessInstanceId()).thenReturn("42");
    when(superExecutionEntity.getTenantId()).thenReturn("42");
    when(superExecutionEntity.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createSubprocessInstance(
                processDefinition, superExecutionEntity, "Business Key"));
    verify(processEngineConfigurationImpl).getClock();
    verify(superExecutionEntity).getProcessInstance();
    verify(superExecutionEntity).getRootProcessInstanceId();
    verify(superExecutionEntity, atLeast(1)).getTenantId();
    verify(superExecutionEntity).isCountEnabled();
    verify(superExecutionEntity).setSubProcessInstance(isNull());
    verify(executionDataManager).create();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition,
   * ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"
  })
  public void testCreateSubprocessInstance5() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    doNothing().when(executionDataManager).insert(Mockito.<ExecutionEntity>any());
    when(executionDataManager.create())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
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

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createSubprocessInstance(
                processDefinition, superExecutionEntity, "Business Key"));
    verify(processEngineConfigurationImpl).getClock();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(superExecutionEntity).getId();
    verify(superExecutionEntity).getProcessInstance();
    verify(superExecutionEntity).getProcessInstanceId();
    verify(superExecutionEntity, atLeast(1)).getRootProcessInstanceId();
    verify(superExecutionEntity, atLeast(1)).getTenantId();
    verify(superExecutionEntity).isCountEnabled();
    verify(superExecutionEntity).setSubProcessInstance(isNull());
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition,
   * ExecutionEntity, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setActive(boolean)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#createSubprocessInstance(ProcessDefinition, ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.createSubprocessInstance(ProcessDefinition, ExecutionEntity, String)"
  })
  public void testCreateSubprocessInstance_thenCallsSetActive() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
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
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(superExecutionEntity)
        .setSubProcessInstance(Mockito.<ExecutionEntity>any());
    when(superExecutionEntity.isCountEnabled()).thenReturn(true);
    when(superExecutionEntity.getRootProcessInstanceId()).thenReturn("42");
    when(superExecutionEntity.getTenantId()).thenReturn("42");
    when(superExecutionEntity.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.createSubprocessInstance(
                processDefinition, superExecutionEntity, "Business Key"));
    verify(processEngineConfigurationImpl).getClock();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(executionEntityImpl).getId();
    verify(superExecutionEntity).getProcessInstance();
    verify(superExecutionEntity, atLeast(1)).getRootProcessInstanceId();
    verify(superExecutionEntity, atLeast(1)).getTenantId();
    verify(superExecutionEntity).isCountEnabled();
    verify(executionEntityImpl).setActive(true);
    verify(executionEntityImpl).setAppVersion(isNull());
    verify(executionEntityImpl).setBusinessKey("Business Key");
    verify(executionEntityImpl).setCountEnabled(true);
    verify(executionEntityImpl).setName(null);
    verify(executionEntityImpl).setProcessDefinitionId(null);
    verify(executionEntityImpl).setProcessDefinitionKey(null);
    verify(executionEntityImpl).setProcessDefinitionName(null);
    verify(executionEntityImpl).setProcessDefinitionVersion(0);
    verify(executionEntityImpl).setProcessInstanceId("42");
    verify(executionEntityImpl, atLeast(1)).setRootProcessInstanceId("42");
    verify(executionEntityImpl).setScope(true);
    verify(executionEntityImpl).setStartTime(isA(Date.class));
    verify(executionEntityImpl).setStartUserId(null);
    verify(superExecutionEntity).setSubProcessInstance(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setSuperExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl).setTenantId("42");
    verify(executionDataManager).create();
    verify(executionDataManager).insert(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#inheritCommonProperties(ExecutionEntity,
   * ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#inheritCommonProperties(ExecutionEntity, ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.inheritCommonProperties(ExecutionEntity, ExecutionEntity)"
  })
  public void testInheritCommonProperties_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl parentExecutionEntity = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(parentExecutionEntity.isCountEnabled())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.inheritCommonProperties(
                parentExecutionEntity,
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(parentExecutionEntity).isCountEnabled();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateExecutionTenantIdForDeployment(String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateExecutionTenantIdForDeployment(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.updateExecutionTenantIdForDeployment(String, String)"
  })
  public void testUpdateExecutionTenantIdForDeployment() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing()
        .when(executionDataManager)
        .updateExecutionTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    executionEntityManagerImpl.updateExecutionTenantIdForDeployment("42", "42");

    // Assert
    verify(executionDataManager).updateExecutionTenantIdForDeployment("42", "42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateExecutionTenantIdForDeployment(String, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateExecutionTenantIdForDeployment(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.updateExecutionTenantIdForDeployment(String, String)"
  })
  public void testUpdateExecutionTenantIdForDeployment2() {
    // Arrange
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionDataManager)
        .updateExecutionTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.updateExecutionTenantIdForDeployment("42", "42"));
    verify(executionDataManager).updateExecutionTenantIdForDeployment("42", "42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String,
   * String, boolean)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"
  })
  public void testDeleteProcessInstancesByProcessDefinition() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    Class<Object> objectClass = Object.class;
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(stringList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(
                "42", "Just cause", true));
    verify(executionDataManager).findById("foo");
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String,
   * String, boolean)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"
  })
  public void testDeleteProcessInstancesByProcessDefinition2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(stringList);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(
                "42", "Just cause", true));
    verify(executionDataManager).findById("foo");
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String,
   * String, boolean)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"
  })
  public void testDeleteProcessInstancesByProcessDefinition3() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(
                "42", "Just cause", true));
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String,
   * String, boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getHistoryManager()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstancesByProcessDefinition(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstancesByProcessDefinition(String, String, boolean)"
  })
  public void testDeleteProcessInstancesByProcessDefinition_thenCallsGetHistoryManager() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(
            new HistoricProcessInstanceEntityManagerImpl(
                processEngineConfigurationImpl,
                new MybatisHistoricProcessInstanceDataManager(
                    new JtaProcessEngineConfiguration())));
    when(executionDataManager.findProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    executionEntityManagerImpl.deleteProcessInstancesByProcessDefinition("42", "Just cause", true);

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(executionDataManager).findProcessInstanceIdsByProcessDefinitionId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"
  })
  public void testDeleteProcessInstance() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(executionDataManager).findById("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"
  })
  public void testDeleteProcessInstance2() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getTaskEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionDataManager).findById("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"
  })
  public void testDeleteProcessInstance3() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl =
        new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(), taskDataManager);
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionDataManager).findById("42");
    verify(taskDataManager).findTasksByProcessInstanceId(null);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"
  })
  public void testDeleteProcessInstance4() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl =
        new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(), taskDataManager);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntityImpl.getId()).thenThrow(new ActivitiObjectNotFoundException(objectClass));

    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getId()).thenReturn("42");
    when(executionEntityImpl2.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl2.getProcessInstance()).thenReturn(executionEntityImpl);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl2);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionEntityImpl2).getId();
    verify(executionEntityImpl).getId();
    verify(executionEntityImpl2).getExecutions();
    verify(executionEntityImpl2).getProcessInstance();
    verify(executionDataManager).findById("42");
    verify(taskDataManager).findTasksByProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionDataManager} {@link ExecutionDataManager#findById(String)} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"
  })
  public void testDeleteProcessInstance_givenExecutionDataManagerFindByIdReturnNull() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(executionDataManager).findById("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"
  })
  public void testDeleteProcessInstance_thenCallsIsEnabled() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl =
        new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(), taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntityImpl.getProcessDefinitionId())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getId()).thenReturn("42");
    when(executionEntityImpl2.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl2.getProcessInstance()).thenReturn(executionEntityImpl);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl2);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionEntityImpl2).getId();
    verify(executionEntityImpl2).getExecutions();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl2).getProcessInstance();
    verify(executionDataManager).findById("42");
    verify(taskDataManager).findTasksByProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#isMultiInstanceRoot()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"
  })
  public void testDeleteProcessInstance_thenCallsIsMultiInstanceRoot() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntity.getExecutions())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);

    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.addChildExecution(executionEntity);

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", "Just cause", true));
    verify(executionEntity).getExecutions();
    verify(executionEntity).isMultiInstanceRoot();
    verify(executionDataManager).findById("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String, boolean)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteProcessInstance(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstance(String, String, boolean)"
  })
  public void testDeleteProcessInstance_whenNull_thenCallsIsEnabled() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl =
        new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(), taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntityImpl.getProcessDefinitionId())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getId()).thenReturn("42");
    when(executionEntityImpl2.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl2.getProcessInstance()).thenReturn(executionEntityImpl);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl2);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteProcessInstance("42", null, true));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getTaskEntityManager();
    verify(executionEntityImpl2).getId();
    verify(executionEntityImpl2).getExecutions();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl2).getProcessInstance();
    verify(executionDataManager).findById("42");
    verify(taskDataManager).findTasksByProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String,
   * boolean)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstanceCascade(ExecutionEntity, String, boolean)"
  })
  public void testDeleteProcessInstanceCascade() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl =
        new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(), taskDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    Class<Object> objectClass = Object.class;
    when(processEngineConfiguration.getEventDispatcher())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfiguration.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteProcessInstanceCascade(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause", true));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getTaskEntityManager();
    verify(taskDataManager).findTasksByProcessInstanceId(null);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String,
   * boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getTaskEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstanceCascade(ExecutionEntity, String, boolean)"
  })
  public void testDeleteProcessInstanceCascade_thenCallsGetTaskEntityManager() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getTaskEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteProcessInstanceCascade(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause", true));
    verify(processEngineConfigurationImpl).getTaskEntityManager();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String,
   * boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstanceCascade(ExecutionEntity, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstanceCascade(ExecutionEntity, String, boolean)"
  })
  public void testDeleteProcessInstanceCascade_thenCallsIsEnabled() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl =
        new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(), taskDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getTaskEntityManager()).thenReturn(taskEntityManagerImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act
    executionEntityManagerImpl.deleteProcessInstanceCascade(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause", true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getTaskEntityManager();
    verify(taskDataManager).findTasksByProcessInstanceId(null);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners,
   * ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners, ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)"
  })
  public void testExecuteExecutionListeners() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getListenerNotificationHelper())
        .thenReturn(new ListenerNotificationHelper());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    AdhocSubProcess elementWithExecutionListeners = new AdhocSubProcess();

    // Act
    executionEntityManagerImpl.executeExecutionListeners(
        elementWithExecutionListeners,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Event Type");

    // Assert
    verify(processEngineConfiguration).getListenerNotificationHelper();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners,
   * ExecutionEntity, String)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners, ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)"
  })
  public void testExecuteExecutionListeners_givenNull() {
    // Arrange
    when(processEngineConfigurationImpl.getListenerNotificationHelper())
        .thenReturn(new ListenerNotificationHelper());

    AdhocSubProcess elementWithExecutionListeners = new AdhocSubProcess();
    elementWithExecutionListeners.setExecutionListeners(null);

    // Act
    executionEntityManagerImpl.executeExecutionListeners(
        elementWithExecutionListeners,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Event Type");

    // Assert
    verify(processEngineConfigurationImpl).getListenerNotificationHelper();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners,
   * ExecutionEntity, String)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       ListenerNotificationHelper#executeExecutionListeners(HasExecutionListeners,
   *       DelegateExecution, String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners, ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)"
  })
  public void testExecuteExecutionListeners_thenCallsExecuteExecutionListeners() {
    // Arrange
    ListenerNotificationHelper listenerNotificationHelper = mock(ListenerNotificationHelper.class);
    doNothing()
        .when(listenerNotificationHelper)
        .executeExecutionListeners(
            Mockito.<HasExecutionListeners>any(),
            Mockito.<DelegateExecution>any(),
            Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getListenerNotificationHelper())
        .thenReturn(listenerNotificationHelper);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    HasExecutionListeners elementWithExecutionListeners = mock(HasExecutionListeners.class);

    // Act
    executionEntityManagerImpl.executeExecutionListeners(
        elementWithExecutionListeners,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Event Type");

    // Assert
    verify(listenerNotificationHelper)
        .executeExecutionListeners(
            isA(HasExecutionListeners.class), isA(DelegateExecution.class), eq("Event Type"));
    verify(processEngineConfiguration).getListenerNotificationHelper();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners,
   * ExecutionEntity, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#executeExecutionListeners(HasExecutionListeners, ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.executeExecutionListeners(HasExecutionListeners, ExecutionEntity, String)"
  })
  public void testExecuteExecutionListeners_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getListenerNotificationHelper())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    AdhocSubProcess elementWithExecutionListeners = new AdhocSubProcess();

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.executeExecutionListeners(
                elementWithExecutionListeners,
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
                "Event Type"));
    verify(processEngineConfigurationImpl).getListenerNotificationHelper();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"
  })
  public void testDeleteExecutionAndRelatedData() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    Class<Object> objectClass = Object.class;
    when(processEngineConfiguration.getPerformanceSettings())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteExecutionAndRelatedData(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause"));
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfiguration).getPerformanceSettings();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"
  })
  public void testDeleteExecutionAndRelatedData2() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntity.getIdentityLinkCount())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionEntity.isCountEnabled()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteExecutionAndRelatedData(
                executionEntity, "Just cause"));
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntity).getId();
    verify(executionEntity).getIdentityLinkCount();
    verify(executionEntity).getProcessInstanceId();
    verify(executionEntity).isCountEnabled();
    verify(executionEntity).setActive(false);
    verify(executionEntity).setEnded(true);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"
  })
  public void testDeleteExecutionAndRelatedData3() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    Class<Object> objectClass = Object.class;
    when(processEngineConfiguration.getIdentityLinkEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getIdentityLinkCount()).thenReturn(1);
    when(executionEntity.isCountEnabled()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteExecutionAndRelatedData(
                executionEntity, "Just cause"));
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfiguration).getIdentityLinkEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntity).getId();
    verify(executionEntity).getIdentityLinkCount();
    verify(executionEntity).getProcessInstanceId();
    verify(executionEntity).isCountEnabled();
    verify(executionEntity).setActive(false);
    verify(executionEntity).setEnded(true);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"
  })
  public void testDeleteExecutionAndRelatedData4() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(false);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    Class<Object> objectClass = Object.class;
    when(processEngineConfiguration.getIdentityLinkEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteExecutionAndRelatedData(
                executionEntity, "Just cause"));
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfiguration).getIdentityLinkEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntity).getId();
    verify(executionEntity).getProcessInstanceId();
    verify(executionEntity).setActive(false);
    verify(executionEntity).setEnded(true);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getVariableCount()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteExecutionAndRelatedData(ExecutionEntity, String)"
  })
  public void testDeleteExecutionAndRelatedData_thenCallsGetVariableCount() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getIdentityLinkEntityManager())
        .thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntity.getVariableInstancesLocal())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionEntity.getIdentityLinkCount()).thenReturn(1);
    when(executionEntity.getVariableCount()).thenReturn(3);
    when(executionEntity.isCountEnabled()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteExecutionAndRelatedData(
                executionEntity, "Just cause"));
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfiguration).getIdentityLinkEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntity).getId();
    verify(executionEntity).getIdentityLinkCount();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).getVariableCount();
    verify(executionEntity).isCountEnabled();
    verify(executionEntity).setActive(false);
    verify(executionEntity).setEnded(true);
    verify(executionEntity).getVariableInstancesLocal();
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"
  })
  public void testCancelExecutionAndRelatedData() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    Class<Object> objectClass = Object.class;
    when(processEngineConfiguration.getPerformanceSettings())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.cancelExecutionAndRelatedData(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause"));
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfiguration).getPerformanceSettings();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"
  })
  public void testCancelExecutionAndRelatedData2() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntity.getIdentityLinkCount())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isCountEnabled()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.cancelExecutionAndRelatedData(
                executionEntity, "Just cause"));
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntity).getId();
    verify(executionEntity).getIdentityLinkCount();
    verify(executionEntity).getProcessInstanceId();
    verify(executionEntity).isActive();
    verify(executionEntity).isCountEnabled();
    verify(executionEntity).setActive(false);
    verify(executionEntity).setEnded(true);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"
  })
  public void testCancelExecutionAndRelatedData3() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    Class<Object> objectClass = Object.class;
    when(processEngineConfiguration.getIdentityLinkEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getIdentityLinkCount()).thenReturn(1);
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isCountEnabled()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.cancelExecutionAndRelatedData(
                executionEntity, "Just cause"));
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfiguration).getIdentityLinkEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntity).getId();
    verify(executionEntity).getIdentityLinkCount();
    verify(executionEntity).getProcessInstanceId();
    verify(executionEntity).isActive();
    verify(executionEntity).isCountEnabled();
    verify(executionEntity).setActive(false);
    verify(executionEntity).setEnded(true);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"
  })
  public void testCancelExecutionAndRelatedData4() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(false);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    Class<Object> objectClass = Object.class;
    when(processEngineConfiguration.getIdentityLinkEntityManager())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.cancelExecutionAndRelatedData(
                executionEntity, "Just cause"));
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfiguration).getIdentityLinkEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntity).getId();
    verify(executionEntity).getProcessInstanceId();
    verify(executionEntity).isActive();
    verify(executionEntity).setActive(false);
    verify(executionEntity).setEnded(true);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getVariableCount()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.cancelExecutionAndRelatedData(ExecutionEntity, String)"
  })
  public void testCancelExecutionAndRelatedData_thenCallsGetVariableCount() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getIdentityLinkEntityManager())
        .thenReturn(identityLinkEntityManagerImpl);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntity.getVariableInstancesLocal())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(executionEntity.getIdentityLinkCount()).thenReturn(1);
    when(executionEntity.getVariableCount()).thenReturn(3);
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.isCountEnabled()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");
    doNothing().when(executionEntity).setActive(anyBoolean());
    doNothing().when(executionEntity).setEnded(anyBoolean());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.cancelExecutionAndRelatedData(
                executionEntity, "Just cause"));
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfiguration).getIdentityLinkEntityManager();
    verify(processEngineConfiguration).getPerformanceSettings();
    verify(executionEntity).getId();
    verify(executionEntity).getIdentityLinkCount();
    verify(executionEntity, atLeast(1)).getProcessInstanceId();
    verify(executionEntity).getVariableCount();
    verify(executionEntity).isActive();
    verify(executionEntity).isCountEnabled();
    verify(executionEntity).setActive(false);
    verify(executionEntity).setEnded(true);
    verify(executionEntity).getVariableInstancesLocal();
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String,
   * String, boolean, boolean)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String,
   * boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)"
  })
  public void testDeleteProcessInstanceExecutionEntity() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteProcessInstanceExecutionEntity(
                "42", "42", "Just cause", true, true));
    verify(executionDataManager).findById("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String,
   * String, boolean, boolean)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String,
   * boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)"
  })
  public void testDeleteProcessInstanceExecutionEntity2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isDeleted()).thenReturn(true);

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    executionEntityManagerImpl.deleteProcessInstanceExecutionEntity(
        "42", "42", "Just cause", true, true);

    // Assert
    verify(executionEntityImpl).isDeleted();
    verify(executionDataManager).findById("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String,
   * String, boolean, boolean)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String,
   * boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)"
  })
  public void testDeleteProcessInstanceExecutionEntity3() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntityImpl.isDeleted())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteProcessInstanceExecutionEntity(
                "42", "42", "Just cause", true, true));
    verify(executionEntityImpl).isDeleted();
    verify(executionDataManager).findById("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String,
   * String, boolean, boolean)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String,
   * boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)"
  })
  public void testDeleteProcessInstanceExecutionEntity4() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteProcessInstanceExecutionEntity(
                "42", "42", "Just cause", true, true));
    verify(executionDataManager).findById("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String,
   * String, boolean, boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getExecutions()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#deleteProcessInstanceExecutionEntity(String, String, String,
   * boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteProcessInstanceExecutionEntity(String, String, String, boolean, boolean)"
  })
  public void testDeleteProcessInstanceExecutionEntity_thenCallsGetExecutions() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntityImpl.isDeleted())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.isDeleted()).thenReturn(false);
    when(executionEntityImpl2.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntityImpl2.getSubProcessInstance()).thenReturn(executionEntityImpl);

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any())).thenReturn(executionEntityImpl2);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.deleteProcessInstanceExecutionEntity(
                "42", "42", "Just cause", true, true));
    verify(executionEntityImpl2, atLeast(1)).getExecutions();
    verify(executionEntityImpl2).getSubProcessInstance();
    verify(executionEntityImpl2).isDeleted();
    verify(executionEntityImpl).isDeleted();
    verify(executionDataManager).findById("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#deleteChildExecutions(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteChildExecutions(ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteChildExecutions(ExecutionEntity, String)"
  })
  public void testDeleteChildExecutions() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setDeleted(true);

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance())
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
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
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteChildExecutions(ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteChildExecutions(ExecutionEntity, String)"
  })
  public void testDeleteChildExecutions_givenNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

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
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#deleteChildExecutions(ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.deleteChildExecutions(ExecutionEntity, String)"
  })
  public void testDeleteChildExecutions_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntityImpl.isDeleted())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.deleteChildExecutions(executionEntity, "Just cause"));
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
    verify(executionEntityImpl).isDeleted();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#cancelChildExecutions(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#cancelChildExecutions(ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.cancelChildExecutions(ExecutionEntity, String)"
  })
  public void testCancelChildExecutions() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setDeleted(true);

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance())
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
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
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#cancelChildExecutions(ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.cancelChildExecutions(ExecutionEntity, String)"
  })
  public void testCancelChildExecutions_givenNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

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
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#cancelChildExecutions(ExecutionEntity,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityManagerImpl.cancelChildExecutions(ExecutionEntity, String)"
  })
  public void testCancelChildExecutions_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    when(executionEntityImpl.isDeleted())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.cancelChildExecutions(executionEntity, "Just cause"));
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
    verify(executionEntityImpl).isDeleted();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity, List)} with {@code
   * executionEntity}, {@code collectedChildExecution}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.collectChildren(ExecutionEntity, List)"})
  public void testCollectChildrenWithExecutionEntityCollectedChildExecution_givenNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
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
   * Test {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity, List)} with {@code
   * executionEntity}, {@code collectedChildExecution}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#isDeleted()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.collectChildren(ExecutionEntity, List)"})
  public void testCollectChildrenWithExecutionEntityCollectedChildExecution_thenCallsIsDeleted() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isDeleted()).thenReturn(true);
    doNothing().when(executionEntityImpl).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntityImpl.addChildExecution(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    when(executionEntity.getSubProcessInstance()).thenReturn(executionEntityImpl);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    executionEntityManagerImpl.collectChildren(executionEntity, new ArrayList<>());

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntityImpl).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getExecutions();
    verify(executionEntity).getSubProcessInstance();
    verify(executionEntityImpl).isDeleted();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity)} with {@code
   * executionEntity}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#collectChildren(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityManagerImpl.collectChildren(ExecutionEntity)"})
  public void testCollectChildrenWithExecutionEntity_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl subProcessInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    subProcessInstance.setDeleted(true);

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setSubProcessInstance(subProcessInstance);

    // Act and Assert
    assertTrue(executionEntityManagerImpl.collectChildren(executionEntity).isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findFirstScope(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findFirstScope(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findFirstScope(ExecutionEntity)"})
  public void testFindFirstScope_givenFalse_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
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
   *
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#findFirstScope(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntity ExecutionEntityManagerImpl.findFirstScope(ExecutionEntity)"})
  public void testFindFirstScope_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntity actualFindFirstScopeResult =
        executionEntityManagerImpl.findFirstScope(executionEntity);

    // Assert
    assertSame(executionEntity, actualFindFirstScopeResult);
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstScopeResult).eventSubscriptions,
        executionEntity.getEventSubscriptions());
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstScopeResult).executions,
        executionEntity.getExecutions());
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstScopeResult).identityLinks,
        executionEntity.getIdentityLinks());
    assertSame(((ExecutionEntityImpl) actualFindFirstScopeResult).jobs, executionEntity.getJobs());
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstScopeResult).tasks, executionEntity.getTasks());
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstScopeResult).timerJobs,
        executionEntity.getTimerJobs());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findFirstMultiInstanceRoot(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findFirstMultiInstanceRoot(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findFirstMultiInstanceRoot(ExecutionEntity)"
  })
  public void testFindFirstMultiInstanceRoot_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setMultiInstanceRoot(true);

    // Act
    ExecutionEntity actualFindFirstMultiInstanceRootResult =
        executionEntityManagerImpl.findFirstMultiInstanceRoot(executionEntity);

    // Assert
    assertSame(executionEntity, actualFindFirstMultiInstanceRootResult);
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstMultiInstanceRootResult).eventSubscriptions,
        executionEntity.getEventSubscriptions());
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstMultiInstanceRootResult).executions,
        executionEntity.getExecutions());
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstMultiInstanceRootResult).identityLinks,
        executionEntity.getIdentityLinks());
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstMultiInstanceRootResult).jobs,
        executionEntity.getJobs());
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstMultiInstanceRootResult).tasks,
        executionEntity.getTasks());
    assertSame(
        ((ExecutionEntityImpl) actualFindFirstMultiInstanceRootResult).timerJobs,
        executionEntity.getTimerJobs());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#findFirstMultiInstanceRoot(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#findFirstMultiInstanceRoot(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionEntityManagerImpl.findFirstMultiInstanceRoot(ExecutionEntity)"
  })
  public void testFindFirstMultiInstanceRoot_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertNull(executionEntityManagerImpl.findFirstMultiInstanceRoot(executionEntity));
    assertTrue(executionEntity.getEventSubscriptions().isEmpty());
    assertTrue(executionEntity.getExecutions().isEmpty());
    assertTrue(executionEntity.getIdentityLinks().isEmpty());
    assertTrue(executionEntity.getJobs().isEmpty());
    assertTrue(executionEntity.getTasks().isEmpty());
    assertTrue(executionEntity.getTimerJobs().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.updateProcessInstanceLockTime(String)"})
  public void testUpdateProcessInstanceLockTime() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    Class<Object> objectClass = Object.class;
    when(processEngineConfiguration.getAsyncExecutor())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.updateProcessInstanceLockTime("42"));
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.updateProcessInstanceLockTime(String)"})
  public void testUpdateProcessInstanceLockTime2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing()
        .when(executionDataManager)
        .updateProcessInstanceLockTime(
            Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Date>any());

    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(processEngineConfiguration, executionDataManager);

    // Act
    executionEntityManagerImpl.updateProcessInstanceLockTime("42");

    // Assert
    verify(processEngineConfiguration).getAsyncExecutor();
    verify(processEngineConfiguration).getClock();
    verify(executionDataManager)
        .updateProcessInstanceLockTime(eq("42"), isA(Date.class), isA(Date.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.updateProcessInstanceLockTime(String)"})
  public void testUpdateProcessInstanceLockTime3() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getClock())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.updateProcessInstanceLockTime("42"));
    verify(processEngineConfigurationImpl).getClock();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getAsyncExecutor()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#updateProcessInstanceLockTime(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.updateProcessInstanceLockTime(String)"})
  public void testUpdateProcessInstanceLockTime_thenCallsGetAsyncExecutor() {
    // Arrange
    when(processEngineConfigurationImpl.getAsyncExecutor())
        .thenReturn(new DefaultAsyncJobExecutor());
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionDataManager)
        .updateProcessInstanceLockTime(
            Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Date>any());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.updateProcessInstanceLockTime("42"));
    verify(processEngineConfigurationImpl).getAsyncExecutor();
    verify(processEngineConfigurationImpl).getClock();
    verify(executionDataManager)
        .updateProcessInstanceLockTime(eq("42"), isA(Date.class), isA(Date.class));
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#clearProcessInstanceLockTime(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#clearProcessInstanceLockTime(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.clearProcessInstanceLockTime(String)"})
  public void testClearProcessInstanceLockTime() {
    // Arrange
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    doNothing().when(executionDataManager).clearProcessInstanceLockTime(Mockito.<String>any());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    // Act
    executionEntityManagerImpl.clearProcessInstanceLockTime("42");

    // Assert
    verify(executionDataManager).clearProcessInstanceLockTime("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#clearProcessInstanceLockTime(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityManagerImpl#clearProcessInstanceLockTime(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityManagerImpl.clearProcessInstanceLockTime(String)"})
  public void testClearProcessInstanceLockTime_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionDataManager)
        .clearProcessInstanceLockTime(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> executionEntityManagerImpl.clearProcessInstanceLockTime("42"));
    verify(executionDataManager).clearProcessInstanceLockTime("42");
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"
  })
  public void testUpdateProcessInstanceBusinessKey() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    String actualUpdateProcessInstanceBusinessKeyResult =
        executionEntityManagerImpl.updateProcessInstanceBusinessKey(executionEntity, null);

    // Assert
    assertNull(executionEntity.getBusinessKey());
    assertNull(actualUpdateProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"
  })
  public void testUpdateProcessInstanceBusinessKey2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    String actualUpdateProcessInstanceBusinessKeyResult =
        executionEntityManagerImpl.updateProcessInstanceBusinessKey(
            executionEntity, "Business Key");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryManager();
    assertEquals("Business Key", executionEntity.getBusinessKey());
    assertEquals("Business Key", actualUpdateProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"
  })
  public void testUpdateProcessInstanceBusinessKey3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    Class<Object> objectClass = Object.class;
    when(processEngineConfiguration.getEventDispatcher())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.updateProcessInstanceBusinessKey(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Business Key"));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryManager();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"
  })
  public void testUpdateProcessInstanceBusinessKey4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    String actualUpdateProcessInstanceBusinessKeyResult =
        executionEntityManagerImpl.updateProcessInstanceBusinessKey(
            executionEntity, "Business Key");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryManager();
    assertEquals("Business Key", executionEntity.getBusinessKey());
    assertEquals("Business Key", actualUpdateProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"
  })
  public void testUpdateProcessInstanceBusinessKey5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(activitiEventDispatcher)
        .dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.updateProcessInstanceBusinessKey(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Business Key"));
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryManager();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity,
   * String)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"
  })
  public void testUpdateProcessInstanceBusinessKey6() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    String actualUpdateProcessInstanceBusinessKeyResult =
        executionEntityManagerImpl.updateProcessInstanceBusinessKey(
            executionEntity, "Business Key");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryManager();
    assertEquals("Business Key", executionEntity.getBusinessKey());
    assertEquals("Business Key", actualUpdateProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity,
   * String)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"
  })
  public void testUpdateProcessInstanceBusinessKey_givenFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.isProcessInstanceType()).thenReturn(false);

    // Act
    String actualUpdateProcessInstanceBusinessKeyResult =
        executionEntityManagerImpl.updateProcessInstanceBusinessKey(
            executionEntity, "Business Key");

    // Assert
    verify(executionEntity).isProcessInstanceType();
    assertNull(actualUpdateProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getHistoryManager()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"
  })
  public void testUpdateProcessInstanceBusinessKey_thenCallsGetHistoryManager() {
    // Arrange
    Class<Object> objectClass = Object.class;
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenThrow(new ActivitiObjectNotFoundException(objectClass));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.updateProcessInstanceBusinessKey(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Business Key"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#setBusinessKey(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionEntityManagerImpl#updateProcessInstanceBusinessKey(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ExecutionEntityManagerImpl.updateProcessInstanceBusinessKey(ExecutionEntity, String)"
  })
  public void testUpdateProcessInstanceBusinessKey_thenCallsSetBusinessKey() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    Class<Object> objectClass = Object.class;
    doThrow(new ActivitiObjectNotFoundException(objectClass))
        .when(executionEntity)
        .setBusinessKey(Mockito.<String>any());
    when(executionEntity.isProcessInstanceType()).thenReturn(true);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            executionEntityManagerImpl.updateProcessInstanceBusinessKey(
                executionEntity, "Business Key"));
    verify(executionEntity).isProcessInstanceType();
    verify(executionEntity).setBusinessKey("Business Key");
  }
}
