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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.HistoricProcessInstanceQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricProcessInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricActivityInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricDetailDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricProcessInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricTaskInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricVariableInstanceDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricProcessInstanceEntityManagerImplDiffblueTest {
  @InjectMocks
  private HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Mock
  private HistoricProcessInstanceDataManager historicProcessInstanceDataManager;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HistoricProcessInstanceEntityManagerImpl#HistoricProcessInstanceEntityManagerImpl(ProcessEngineConfigurationImpl, HistoricProcessInstanceDataManager)}
   *   <li>{@link HistoricProcessInstanceEntityManagerImpl#setHistoricProcessInstanceDataManager(HistoricProcessInstanceDataManager)}
   *   <li>{@link HistoricProcessInstanceEntityManagerImpl#getDataManager()}
   *   <li>{@link HistoricProcessInstanceEntityManagerImpl#getHistoricProcessInstanceDataManager()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void HistoricProcessInstanceEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, HistoricProcessInstanceDataManager)",
      "DataManager HistoricProcessInstanceEntityManagerImpl.getDataManager()",
      "HistoricProcessInstanceDataManager HistoricProcessInstanceEntityManagerImpl.getHistoricProcessInstanceDataManager()",
      "void HistoricProcessInstanceEntityManagerImpl.setHistoricProcessInstanceDataManager(HistoricProcessInstanceDataManager)"})
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

    // Assert
    assertSame(historicProcessInstanceDataManager, actualDataManager);
    assertSame(historicProcessInstanceDataManager,
        actualHistoricProcessInstanceEntityManagerImpl.getHistoricProcessInstanceDataManager());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#create(ExecutionEntity)} with {@code ExecutionEntity}.
   * <ul>
   *   <li>Then PersistentState return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#create(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricProcessInstanceEntity HistoricProcessInstanceEntityManagerImpl.create(ExecutionEntity)"})
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
    assertEquals(12, ((Map<String, Object>) persistentState).size());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(((Map<String, Object>) persistentState).containsKey("businessKey"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deploymentId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("endStateName"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("name"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(actualCreateResult.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}.
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void HistoricProcessInstanceEntityManagerImpl.deleteHistoricProcessInstanceByProcessDefinitionId(String)"})
  public void testDeleteHistoricProcessInstanceByProcessDefinitionId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act
    historicProcessInstanceEntityManagerImpl.deleteHistoricProcessInstanceByProcessDefinitionId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}.
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void HistoricProcessInstanceEntityManagerImpl.deleteHistoricProcessInstanceByProcessDefinitionId(String)"})
  public void testDeleteHistoricProcessInstanceByProcessDefinitionId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
    when(historicProcessInstanceDataManager.findHistoricProcessInstanceIdsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicProcessInstanceEntityManagerImpl.deleteHistoricProcessInstanceByProcessDefinitionId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicProcessInstanceDataManager).findHistoricProcessInstanceIdsByProcessDefinitionId(eq("42"));
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}.
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#deleteHistoricProcessInstanceByProcessDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void HistoricProcessInstanceEntityManagerImpl.deleteHistoricProcessInstanceByProcessDefinitionId(String)"})
  public void testDeleteHistoricProcessInstanceByProcessDefinitionId3() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration,
        new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    ProcessEngineConfigurationImpl processEngineConfiguration2 = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration2.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        processEngineConfiguration2,
        new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration()));

    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl = new HistoricIdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicIdentityLinkDataManager);

    CommentEntityManager commentEntityManager = mock(CommentEntityManager.class);
    doNothing().when(commentEntityManager).deleteCommentsByProcessInstanceId(Mockito.<String>any());
    when(processEngineConfigurationImpl.getCommentEntityManager()).thenReturn(commentEntityManager);
    when(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration())));
    when(processEngineConfigurationImpl.getHistoricActivityInstanceEntityManager())
        .thenReturn(historicActivityInstanceEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricVariableInstanceEntityManager())
        .thenReturn(historicVariableInstanceEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricDetailEntityManager())
        .thenReturn(new HistoricDetailEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricDetailDataManager(new JtaProcessEngineConfiguration())));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

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
    verify(processEngineConfiguration2).getHistoryManager();
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(commentEntityManager).deleteCommentsByProcessInstanceId(eq("foo"));
    verify(historicProcessInstanceDataManager).delete(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).findById(eq("foo"));
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByProcessInstanceId(eq("foo"));
    verify(historicProcessInstanceDataManager).findHistoricProcessInstanceIdsByProcessDefinitionId(eq("42"));
    verify(historicProcessInstanceDataManager).findHistoricProcessInstancesBySuperProcessInstanceId(eq("foo"));
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#delete(String)} with {@code historicProcessInstanceId}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getCommentEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#delete(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HistoricProcessInstanceEntityManagerImpl.delete(String)"})
  public void testDeleteWithHistoricProcessInstanceId_thenCallsGetCommentEntityManager() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration,
        new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    ProcessEngineConfigurationImpl processEngineConfiguration2 = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration2.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        processEngineConfiguration2,
        new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration()));

    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl = new HistoricIdentityLinkEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicIdentityLinkDataManager);

    CommentEntityManager commentEntityManager = mock(CommentEntityManager.class);
    doNothing().when(commentEntityManager).deleteCommentsByProcessInstanceId(Mockito.<String>any());
    when(processEngineConfigurationImpl.getCommentEntityManager()).thenReturn(commentEntityManager);
    when(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration())));
    when(processEngineConfigurationImpl.getHistoricActivityInstanceEntityManager())
        .thenReturn(historicActivityInstanceEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricVariableInstanceEntityManager())
        .thenReturn(historicVariableInstanceEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricDetailEntityManager())
        .thenReturn(new HistoricDetailEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricDetailDataManager(new JtaProcessEngineConfiguration())));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
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
    verify(processEngineConfiguration2).getHistoryManager();
    verify(processEngineConfiguration).getHistoryManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(commentEntityManager).deleteCommentsByProcessInstanceId(eq("42"));
    verify(historicProcessInstanceDataManager).delete(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByProcessInstanceId(eq("42"));
    verify(historicProcessInstanceDataManager).findHistoricProcessInstancesBySuperProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#delete(String)} with {@code historicProcessInstanceId}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getHistoryManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#delete(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HistoricProcessInstanceEntityManagerImpl.delete(String)"})
  public void testDeleteWithHistoricProcessInstanceId_thenCallsGetHistoryManager() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act
    historicProcessInstanceEntityManagerImpl.delete("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstanceCountByQueryCriteria(HistoricProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstanceCountByQueryCriteria(HistoricProcessInstanceQueryImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "long HistoricProcessInstanceEntityManagerImpl.findHistoricProcessInstanceCountByQueryCriteria(HistoricProcessInstanceQueryImpl)"})
  public void testFindHistoricProcessInstanceCountByQueryCriteria_thenReturnZero() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertEquals(0L, historicProcessInstanceEntityManagerImpl
        .findHistoricProcessInstanceCountByQueryCriteria(new HistoricProcessInstanceQueryImpl()));
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesByQueryCriteria(HistoricProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesByQueryCriteria(HistoricProcessInstanceQueryImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List HistoricProcessInstanceEntityManagerImpl.findHistoricProcessInstancesByQueryCriteria(HistoricProcessInstanceQueryImpl)"})
  public void testFindHistoricProcessInstancesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(historicProcessInstanceEntityManagerImpl
        .findHistoricProcessInstancesByQueryCriteria(new HistoricProcessInstanceQueryImpl())
        .isEmpty());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesAndVariablesByQueryCriteria(HistoricProcessInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesAndVariablesByQueryCriteria(HistoricProcessInstanceQueryImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List HistoricProcessInstanceEntityManagerImpl.findHistoricProcessInstancesAndVariablesByQueryCriteria(HistoricProcessInstanceQueryImpl)"})
  public void testFindHistoricProcessInstancesAndVariablesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(historicProcessInstanceEntityManagerImpl
        .findHistoricProcessInstancesAndVariablesByQueryCriteria(new HistoricProcessInstanceQueryImpl())
        .isEmpty());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstancesByNativeQuery(Map, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List HistoricProcessInstanceEntityManagerImpl.findHistoricProcessInstancesByNativeQuery(Map, int, int)"})
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
   * Test {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstanceCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricProcessInstanceEntityManagerImpl#findHistoricProcessInstanceCountByNativeQuery(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "long HistoricProcessInstanceEntityManagerImpl.findHistoricProcessInstanceCountByNativeQuery(Map)"})
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
