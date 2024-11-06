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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
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
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.AttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.CommentDataManager;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricTaskInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricVariableInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricDetailDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricTaskInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricVariableInstanceDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricTaskInstanceEntityManagerImplDiffblueTest {
  @Mock
  private HistoricTaskInstanceDataManager historicTaskInstanceDataManager;

  @InjectMocks
  private HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HistoricTaskInstanceEntityManagerImpl#HistoricTaskInstanceEntityManagerImpl(ProcessEngineConfigurationImpl, HistoricTaskInstanceDataManager)}
   *   <li>
   * {@link HistoricTaskInstanceEntityManagerImpl#setHistoricTaskInstanceDataManager(HistoricTaskInstanceDataManager)}
   *   <li>{@link HistoricTaskInstanceEntityManagerImpl#getDataManager()}
   *   <li>
   * {@link HistoricTaskInstanceEntityManagerImpl#getHistoricTaskInstanceDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoricTaskInstanceEntityManagerImpl actualHistoricTaskInstanceEntityManagerImpl = new HistoricTaskInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));
    MybatisHistoricTaskInstanceDataManager historicTaskInstanceDataManager = new MybatisHistoricTaskInstanceDataManager(
        new JtaProcessEngineConfiguration());
    actualHistoricTaskInstanceEntityManagerImpl.setHistoricTaskInstanceDataManager(historicTaskInstanceDataManager);
    DataManager<HistoricTaskInstanceEntity> actualDataManager = actualHistoricTaskInstanceEntityManagerImpl
        .getDataManager();

    // Assert that nothing has changed
    assertSame(historicTaskInstanceDataManager, actualDataManager);
    assertSame(historicTaskInstanceDataManager,
        actualHistoricTaskInstanceEntityManagerImpl.getHistoricTaskInstanceDataManager());
  }

  /**
   * Test
   * {@link HistoricTaskInstanceEntityManagerImpl#deleteHistoricTaskInstancesByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#deleteHistoricTaskInstancesByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricTaskInstancesByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act
    historicTaskInstanceEntityManagerImpl.deleteHistoricTaskInstancesByProcessInstanceId("42");

    // Assert that nothing has changed
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link HistoricTaskInstanceEntityManagerImpl#deleteHistoricTaskInstancesByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#deleteHistoricTaskInstancesByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricTaskInstancesByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.AUDIT));
    when(historicTaskInstanceDataManager.findHistoricTaskInstanceByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicTaskInstanceEntityManagerImpl.deleteHistoricTaskInstancesByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicTaskInstanceDataManager).findHistoricTaskInstanceByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricTaskInstanceEntityManagerImpl#deleteHistoricTaskInstancesByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#deleteHistoricTaskInstancesByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricTaskInstancesByProcessInstanceId3() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfigurationImpl, historicVariableInstanceDataManager);

    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).deleteCommentsByTaskId(Mockito.<String>any());
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfigurationImpl,
        commentDataManager);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    when(attachmentDataManager.findAttachmentsByTaskId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfigurationImpl, attachmentDataManager);

    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl = new HistoricIdentityLinkEntityManagerImpl(
        processEngineConfigurationImpl, historicIdentityLinkDataManager);

    when(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getAttachmentEntityManager()).thenReturn(attachmentEntityManagerImpl);
    when(processEngineConfigurationImpl.getCommentEntityManager()).thenReturn(commentEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricVariableInstanceEntityManager())
        .thenReturn(historicVariableInstanceEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricDetailEntityManager())
        .thenReturn(new HistoricDetailEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricDetailDataManager(processEngineConfigurationImpl)));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.AUDIT));

    ArrayList<HistoricTaskInstanceEntity> historicTaskInstanceEntityList = new ArrayList<>();
    historicTaskInstanceEntityList.add(new HistoricTaskInstanceEntityImpl());
    doNothing().when(historicTaskInstanceDataManager).delete(Mockito.<HistoricTaskInstanceEntity>any());
    when(historicTaskInstanceDataManager.findHistoricTasksByParentTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(historicTaskInstanceDataManager.findHistoricTaskInstanceByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicTaskInstanceEntityList);

    // Act
    historicTaskInstanceEntityManagerImpl.deleteHistoricTaskInstancesByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getAttachmentEntityManager();
    verify(processEngineConfigurationImpl).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoricDetailEntityManager();
    verify(processEngineConfigurationImpl).getHistoricIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getHistoricVariableInstanceEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(attachmentDataManager).findAttachmentsByTaskId(isNull());
    verify(commentDataManager).deleteCommentsByTaskId(isNull());
    verify(historicTaskInstanceDataManager).delete(isA(HistoricTaskInstanceEntity.class));
    verify(historicTaskInstanceDataManager).findById(isNull());
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByTaskId(isNull());
    verify(historicTaskInstanceDataManager).findHistoricTaskInstanceByProcessInstanceId(eq("42"));
    verify(historicTaskInstanceDataManager).findHistoricTasksByParentTaskId(isNull());
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId(isNull());
  }

  /**
   * Test
   * {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstanceCountByQueryCriteria(HistoricTaskInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstanceCountByQueryCriteria(HistoricTaskInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricTaskInstanceCountByQueryCriteria_thenReturnZero() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl = new HistoricTaskInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertEquals(0L, historicTaskInstanceEntityManagerImpl
        .findHistoricTaskInstanceCountByQueryCriteria(new HistoricTaskInstanceQueryImpl()));
  }

  /**
   * Test
   * {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesByQueryCriteria(HistoricTaskInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesByQueryCriteria(HistoricTaskInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricTaskInstancesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl = new HistoricTaskInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(historicTaskInstanceEntityManagerImpl
        .findHistoricTaskInstancesByQueryCriteria(new HistoricTaskInstanceQueryImpl())
        .isEmpty());
  }

  /**
   * Test
   * {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesAndVariablesByQueryCriteria(HistoricTaskInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesAndVariablesByQueryCriteria(HistoricTaskInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricTaskInstancesAndVariablesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl = new HistoricTaskInstanceEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(historicTaskInstanceEntityManagerImpl
        .findHistoricTaskInstancesAndVariablesByQueryCriteria(new HistoricTaskInstanceQueryImpl())
        .isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityManagerImpl#delete(String)} with
   * {@code id}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#delete(String)}
   */
  @Test
  public void testDeleteWithId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act
    historicTaskInstanceEntityManagerImpl.delete("42");

    // Assert that nothing has changed
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link HistoricTaskInstanceEntityManagerImpl#delete(String)} with
   * {@code id}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#delete(String)}
   */
  @Test
  public void testDeleteWithId_thenCallsGetAttachmentEntityManager() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfigurationImpl, historicVariableInstanceDataManager);

    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).deleteCommentsByTaskId(Mockito.<String>any());
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfigurationImpl,
        commentDataManager);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    when(attachmentDataManager.findAttachmentsByTaskId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfigurationImpl, attachmentDataManager);

    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl = new HistoricIdentityLinkEntityManagerImpl(
        processEngineConfigurationImpl, historicIdentityLinkDataManager);

    when(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getAttachmentEntityManager()).thenReturn(attachmentEntityManagerImpl);
    when(processEngineConfigurationImpl.getCommentEntityManager()).thenReturn(commentEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricVariableInstanceEntityManager())
        .thenReturn(historicVariableInstanceEntityManagerImpl);
    when(processEngineConfigurationImpl.getHistoricDetailEntityManager())
        .thenReturn(new HistoricDetailEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricDetailDataManager(processEngineConfigurationImpl)));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    doNothing().when(historicTaskInstanceDataManager).delete(Mockito.<HistoricTaskInstanceEntity>any());
    when(historicTaskInstanceDataManager.findHistoricTasksByParentTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());

    // Act
    historicTaskInstanceEntityManagerImpl.delete("42");

    // Assert
    verify(processEngineConfigurationImpl).getAttachmentEntityManager();
    verify(processEngineConfigurationImpl).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoricDetailEntityManager();
    verify(processEngineConfigurationImpl).getHistoricIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getHistoricVariableInstanceEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(attachmentDataManager).findAttachmentsByTaskId(eq("42"));
    verify(commentDataManager).deleteCommentsByTaskId(eq("42"));
    verify(historicTaskInstanceDataManager).delete(isA(HistoricTaskInstanceEntity.class));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByTaskId(eq("42"));
    verify(historicTaskInstanceDataManager).findHistoricTasksByParentTaskId(isNull());
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricTaskInstanceEntityManagerImpl#deleteInternal(String, HistoricTaskInstanceEntity)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getAttachmentEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#deleteInternal(String, HistoricTaskInstanceEntity)}
   */
  @Test
  public void testDeleteInternal_thenCallsGetAttachmentEntityManager() {
    // Arrange
    CommentEntityManager commentEntityManager = mock(CommentEntityManager.class);
    doNothing().when(commentEntityManager).deleteCommentsByTaskId(Mockito.<String>any());
    AttachmentEntityManager attachmentEntityManager = mock(AttachmentEntityManager.class);
    doNothing().when(attachmentEntityManager).deleteAttachmentsByTaskId(Mockito.<String>any());
    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl = new HistoricIdentityLinkEntityManagerImpl(
        processEngineConfigurationImpl, historicIdentityLinkDataManager);

    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManagerImpl);
    when(processEngineConfigurationImpl.getAttachmentEntityManager()).thenReturn(attachmentEntityManager);
    when(processEngineConfigurationImpl.getCommentEntityManager()).thenReturn(commentEntityManager);
    when(processEngineConfigurationImpl.getHistoricVariableInstanceEntityManager())
        .thenReturn(new HistoricVariableInstanceEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricVariableInstanceDataManager(processEngineConfigurationImpl)));
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));
    when(processEngineConfigurationImpl.getHistoricDetailEntityManager())
        .thenReturn(new HistoricDetailEntityManagerImpl(processEngineConfigurationImpl,
            new MybatisHistoricDetailDataManager(processEngineConfigurationImpl)));
    doNothing().when(historicTaskInstanceDataManager).delete(Mockito.<HistoricTaskInstanceEntity>any());
    when(historicTaskInstanceDataManager.findHistoricTasksByParentTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicTaskInstanceEntityManagerImpl.deleteInternal("42", new HistoricTaskInstanceEntityImpl());

    // Assert
    verify(processEngineConfigurationImpl).getAttachmentEntityManager();
    verify(processEngineConfigurationImpl).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoricDetailEntityManager();
    verify(processEngineConfigurationImpl).getHistoricIdentityLinkEntityManager();
    verify(processEngineConfigurationImpl).getHistoricVariableInstanceEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(attachmentEntityManager).deleteAttachmentsByTaskId(eq("42"));
    verify(commentEntityManager).deleteCommentsByTaskId(eq("42"));
    verify(historicTaskInstanceDataManager).delete(isA(HistoricTaskInstanceEntity.class));
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByTaskId(eq("42"));
    verify(historicTaskInstanceDataManager).findHistoricTasksByParentTaskId(isNull());
  }

  /**
   * Test
   * {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesByNativeQuery(Map, int, int)}
   */
  @Test
  public void testFindHistoricTaskInstancesByNativeQuery_thenReturnEmpty() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findHistoricTaskInstancesByNativeQuery(Mockito.<Map<String, Object>>any(),
        anyInt(), anyInt())).thenReturn(new ArrayList<>());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl = new HistoricTaskInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    // Act
    List<HistoricTaskInstance> actualFindHistoricTaskInstancesByNativeQueryResult = historicTaskInstanceEntityManagerImpl
        .findHistoricTaskInstancesByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(historicTaskInstanceDataManager).findHistoricTaskInstancesByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindHistoricTaskInstancesByNativeQueryResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstanceCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstanceCountByNativeQuery(Map)}
   */
  @Test
  public void testFindHistoricTaskInstanceCountByNativeQuery_thenReturnThree() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findHistoricTaskInstanceCountByNativeQuery(Mockito.<Map<String, Object>>any()))
        .thenReturn(3L);
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl = new HistoricTaskInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    // Act
    long actualFindHistoricTaskInstanceCountByNativeQueryResult = historicTaskInstanceEntityManagerImpl
        .findHistoricTaskInstanceCountByNativeQuery(new HashMap<>());

    // Assert
    verify(historicTaskInstanceDataManager).findHistoricTaskInstanceCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindHistoricTaskInstanceCountByNativeQueryResult);
  }
}
