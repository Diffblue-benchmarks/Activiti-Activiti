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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.HistoricProcessInstanceQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.CommentDataManager;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricActivityInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricProcessInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricVariableInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricDetailDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricProcessInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricTaskInstanceDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricProcessInstanceEntityManagerImplDiffblueTest {
  @Mock
  private HistoricProcessInstanceDataManager historicProcessInstanceDataManager;

  @InjectMocks
  private HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HistoricProcessInstanceEntityManagerImpl#HistoricProcessInstanceEntityManagerImpl(ProcessEngineConfigurationImpl, HistoricProcessInstanceDataManager)}
   *   <li>
   * {@link HistoricProcessInstanceEntityManagerImpl#setHistoricProcessInstanceDataManager(HistoricProcessInstanceDataManager)}
   *   <li>{@link HistoricProcessInstanceEntityManagerImpl#getDataManager()}
   *   <li>
   * {@link HistoricProcessInstanceEntityManagerImpl#getHistoricProcessInstanceDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoricProcessInstanceEntityManagerImpl actualHistoricProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));
    MybatisHistoricProcessInstanceDataManager historicProcessInstanceDataManager = new MybatisHistoricProcessInstanceDataManager(
        new JtaProcessEngineConfiguration());
    actualHistoricProcessInstanceEntityManagerImpl
        .setHistoricProcessInstanceDataManager(historicProcessInstanceDataManager);
    DataManager<HistoricProcessInstanceEntity> actualDataManager = actualHistoricProcessInstanceEntityManagerImpl
        .getDataManager();

    // Assert that nothing has changed
    assertSame(historicProcessInstanceDataManager, actualDataManager);
    assertSame(historicProcessInstanceDataManager,
        actualHistoricProcessInstanceEntityManagerImpl.getHistoricProcessInstanceDataManager());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#create(ExecutionEntity)}
   * with {@code ExecutionEntity}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#create(ExecutionEntity)}
   */
  @Test
  public void testCreateWithExecutionEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act
    HistoricProcessInstanceEntity actualCreateResult = historicProcessInstanceEntityManagerImpl
        .create(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricProcessInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getProcessDefinitionVersion());
    assertNull(actualCreateResult.getDurationInMillis());
    assertEquals(12, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("businessKey"));
    assertNull(((Map<String, Object>) persistentState).get("deploymentId"));
    assertNull(((Map<String, Object>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) persistentState).get("endStateName"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(((Map<String, Object>) persistentState).get("processDefinitionId"));
    assertNull(actualCreateResult.getBusinessKey());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getEndActivityId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getStartActivityId());
    assertNull(actualCreateResult.getStartUserId());
    assertNull(actualCreateResult.getSuperProcessInstanceId());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getProcessDefinitionKey());
    assertNull(actualCreateResult.getProcessDefinitionName());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedDescription());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).description);
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).name);
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getQueryVariables());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).queryVariables);
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#create(ExecutionEntity)}
   * with {@code ExecutionEntity}.
   * <ul>
   *   <li>Then PersistentState return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#create(ExecutionEntity)}
   */
  @Test
  public void testCreateWithExecutionEntity_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act
    HistoricProcessInstanceEntity actualCreateResult = historicProcessInstanceEntityManagerImpl
        .create(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricProcessInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getProcessDefinitionVersion());
    assertNull(actualCreateResult.getDurationInMillis());
    assertEquals(12, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("businessKey"));
    assertNull(((Map<String, Object>) persistentState).get("deploymentId"));
    assertNull(((Map<String, Object>) persistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) persistentState).get("endStateName"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(((Map<String, Object>) persistentState).get("processDefinitionId"));
    assertNull(actualCreateResult.getBusinessKey());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getEndActivityId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getStartActivityId());
    assertNull(actualCreateResult.getStartUserId());
    assertNull(actualCreateResult.getSuperProcessInstanceId());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getProcessDefinitionKey());
    assertNull(actualCreateResult.getProcessDefinitionName());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedDescription());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).getLocalizedName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).description);
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).name);
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getQueryVariables());
    assertNull(((HistoricProcessInstanceEntityImpl) actualCreateResult).queryVariables);
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
  }

  /**
   * Test
   * {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}
   */
  @Test
  public void testDeleteHistoricProcessInstanceByProcessDefinitionId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act
    historicProcessInstanceEntityManagerImpl.deleteHistoricProcessInstanceByProcessDefinitionId("42");

    // Assert that nothing has changed
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}
   */
  @Test
  public void testDeleteHistoricProcessInstanceByProcessDefinitionId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(historicProcessInstanceDataManager.findHistoricProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicProcessInstanceEntityManagerImpl.deleteHistoricProcessInstanceByProcessDefinitionId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicProcessInstanceDataManager).findHistoricProcessInstanceIdsByProcessDefinitionId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}
   */
  @Test
  public void testDeleteHistoricProcessInstanceByProcessDefinitionId3() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfigurationImpl, historicVariableInstanceDataManager);

    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = mock(
        HistoricActivityInstanceDataManager.class);
    doNothing().when(historicActivityInstanceDataManager)
        .deleteHistoricActivityInstancesByProcessInstanceId(Mockito.<String>any());
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        processEngineConfigurationImpl, historicActivityInstanceDataManager);

    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl = new HistoricIdentityLinkEntityManagerImpl(
        processEngineConfigurationImpl, historicIdentityLinkDataManager);

    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).deleteCommentsByProcessInstanceId(Mockito.<String>any());
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfigurationImpl,
        commentDataManager);

    when(processEngineConfigurationImpl.getCommentEntityManager()).thenReturn(commentEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricTaskInstanceDataManager(processEngineConfigurationImpl)));
    when(processEngineConfigurationImpl.getHistoricActivityInstanceEntityManager())
        .thenReturn(historicActivityInstanceEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricVariableInstanceEntityManager())
        .thenReturn(historicVariableInstanceEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricDetailEntityManager())
        .thenReturn(new HistoricDetailEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricDetailDataManager(processEngineConfigurationImpl)));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    when(historicProcessInstanceDataManager.findHistoricProcessInstancesBySuperProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    doNothing().when(historicProcessInstanceDataManager).delete(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    when(historicProcessInstanceDataManager.findHistoricProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(stringList);

    // Act
    historicProcessInstanceEntityManagerImpl.deleteHistoricProcessInstanceByProcessDefinitionId("42");

    // Assert
    verify(processEngineConfigurationImpl).getCommentEntityManager();
    verify(processEngineConfigurationImpl).getHistoricActivityInstanceEntityManager();
    verify(processEngineConfigurationImpl).getHistoricDetailEntityManager();
    verify(processEngineConfigurationImpl).getHistoricIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(processEngineConfigurationImpl).getHistoricVariableInstanceEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(commentDataManager).deleteCommentsByProcessInstanceId(eq("foo"));
    verify(historicProcessInstanceDataManager).delete(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).findById(eq("foo"));
    verify(historicActivityInstanceDataManager).deleteHistoricActivityInstancesByProcessInstanceId(eq("foo"));
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByProcessInstanceId(eq("foo"));
    verify(historicProcessInstanceDataManager).findHistoricProcessInstanceIdsByProcessDefinitionId(eq("42"));
    verify(historicProcessInstanceDataManager).findHistoricProcessInstancesBySuperProcessInstanceId(eq("foo"));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByProcessInstanceId(eq("foo"));
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#delete(String)} with
   * {@code historicProcessInstanceId}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#delete(String)}
   */
  @Test
  public void testDeleteWithHistoricProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act
    historicProcessInstanceEntityManagerImpl.delete("42");

    // Assert that nothing has changed
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#delete(String)} with
   * {@code historicProcessInstanceId}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#delete(String)}
   */
  @Test
  public void testDeleteWithHistoricProcessInstanceId_thenCallsGetCommentEntityManager() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfigurationImpl, historicVariableInstanceDataManager);

    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = mock(
        HistoricActivityInstanceDataManager.class);
    doNothing().when(historicActivityInstanceDataManager)
        .deleteHistoricActivityInstancesByProcessInstanceId(Mockito.<String>any());
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        processEngineConfigurationImpl, historicActivityInstanceDataManager);

    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl = new HistoricIdentityLinkEntityManagerImpl(
        processEngineConfigurationImpl, historicIdentityLinkDataManager);

    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).deleteCommentsByProcessInstanceId(Mockito.<String>any());
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfigurationImpl,
        commentDataManager);

    when(processEngineConfigurationImpl.getCommentEntityManager()).thenReturn(commentEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricTaskInstanceDataManager(processEngineConfigurationImpl)));
    when(processEngineConfigurationImpl.getHistoricActivityInstanceEntityManager())
        .thenReturn(historicActivityInstanceEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricVariableInstanceEntityManager())
        .thenReturn(historicVariableInstanceEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricDetailEntityManager())
        .thenReturn(new HistoricDetailEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricDetailDataManager(processEngineConfigurationImpl)));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(historicProcessInstanceDataManager.findHistoricProcessInstancesBySuperProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    doNothing().when(historicProcessInstanceDataManager).delete(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());

    // Act
    historicProcessInstanceEntityManagerImpl.delete("42");

    // Assert
    verify(processEngineConfigurationImpl).getCommentEntityManager();
    verify(processEngineConfigurationImpl).getHistoricActivityInstanceEntityManager();
    verify(processEngineConfigurationImpl).getHistoricDetailEntityManager();
    verify(processEngineConfigurationImpl).getHistoricIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(processEngineConfigurationImpl).getHistoricVariableInstanceEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(commentDataManager).deleteCommentsByProcessInstanceId(eq("42"));
    verify(historicProcessInstanceDataManager).delete(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
    verify(historicActivityInstanceDataManager).deleteHistoricActivityInstancesByProcessInstanceId(eq("42"));
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByProcessInstanceId(eq("42"));
    verify(historicProcessInstanceDataManager).findHistoricProcessInstancesBySuperProcessInstanceId(eq("42"));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstanceCountByQueryCriteria(HistoricProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstanceCountByQueryCriteria(HistoricProcessInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricProcessInstanceCountByQueryCriteria_thenReturnZero() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertEquals(0L, historicProcessInstanceEntityManagerImpl
        .findHistoricProcessInstanceCountByQueryCriteria(new HistoricProcessInstanceQueryImpl()));
  }

  /**
   * Test
   * {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesByQueryCriteria(HistoricProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesByQueryCriteria(HistoricProcessInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricProcessInstancesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(historicProcessInstanceEntityManagerImpl
        .findHistoricProcessInstancesByQueryCriteria(new HistoricProcessInstanceQueryImpl())
        .isEmpty());
  }

  /**
   * Test
   * {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesAndVariablesByQueryCriteria(HistoricProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesAndVariablesByQueryCriteria(HistoricProcessInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricProcessInstancesAndVariablesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(historicProcessInstanceEntityManagerImpl
        .findHistoricProcessInstancesAndVariablesByQueryCriteria(new HistoricProcessInstanceQueryImpl())
        .isEmpty());
  }

  /**
   * Test
   * {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesByNativeQuery(Map, int, int)}
   */
  @Test
  public void testFindHistoricProcessInstancesByNativeQuery_thenReturnEmpty() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager
        .findHistoricProcessInstancesByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    // Act
    List<HistoricProcessInstance> actualFindHistoricProcessInstancesByNativeQueryResult = historicProcessInstanceEntityManagerImpl
        .findHistoricProcessInstancesByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(historicProcessInstanceDataManager).findHistoricProcessInstancesByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindHistoricProcessInstancesByNativeQueryResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstanceCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstanceCountByNativeQuery(Map)}
   */
  @Test
  public void testFindHistoricProcessInstanceCountByNativeQuery_thenReturnThree() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager
        .findHistoricProcessInstanceCountByNativeQuery(Mockito.<Map<String, Object>>any())).thenReturn(3L);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    // Act
    long actualFindHistoricProcessInstanceCountByNativeQueryResult = historicProcessInstanceEntityManagerImpl
        .findHistoricProcessInstanceCountByNativeQuery(new HashMap<>());

    // Assert
    verify(historicProcessInstanceDataManager).findHistoricProcessInstanceCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindHistoricProcessInstanceCountByNativeQueryResult);
  }
}
