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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisIdentityLinkDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IdentityLinkEntityManagerImplDiffblueTest {
  @Mock
  private IdentityLinkDataManager identityLinkDataManager;

  @InjectMocks
  private IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link IdentityLinkEntityManagerImpl#IdentityLinkEntityManagerImpl(ProcessEngineConfigurationImpl, IdentityLinkDataManager)}
   *   <li>
   * {@link IdentityLinkEntityManagerImpl#setIdentityLinkDataManager(IdentityLinkDataManager)}
   *   <li>{@link IdentityLinkEntityManagerImpl#getDataManager()}
   *   <li>{@link IdentityLinkEntityManagerImpl#getIdentityLinkDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    IdentityLinkEntityManagerImpl actualIdentityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        processEngineConfiguration, new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    MybatisIdentityLinkDataManager identityLinkDataManager = new MybatisIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());
    actualIdentityLinkEntityManagerImpl.setIdentityLinkDataManager(identityLinkDataManager);
    DataManager<IdentityLinkEntity> actualDataManager = actualIdentityLinkEntityManagerImpl.getDataManager();

    // Assert that nothing has changed
    assertSame(identityLinkDataManager, actualDataManager);
    assertSame(identityLinkDataManager, actualIdentityLinkEntityManagerImpl.getIdentityLinkDataManager());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#findIdentityLinksByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinksByTaskId(String)}
   */
  @Test
  public void testFindIdentityLinksByTaskId() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinksByTaskId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinksByTaskIdResult = identityLinkEntityManagerImpl
        .findIdentityLinksByTaskId("42");

    // Assert
    verify(identityLinkDataManager).findIdentityLinksByTaskId(eq("42"));
    assertTrue(actualFindIdentityLinksByTaskIdResult.isEmpty());
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinksByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinksByProcessInstanceId(String)}
   */
  @Test
  public void testFindIdentityLinksByProcessInstanceId() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinksByProcessInstanceIdResult = identityLinkEntityManagerImpl
        .findIdentityLinksByProcessInstanceId("42");

    // Assert
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId(eq("42"));
    assertTrue(actualFindIdentityLinksByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinksByProcessDefinitionId(String)}.
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinksByProcessDefinitionId(String)}
   */
  @Test
  public void testFindIdentityLinksByProcessDefinitionId() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinksByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinksByProcessDefinitionIdResult = identityLinkEntityManagerImpl
        .findIdentityLinksByProcessDefinitionId("42");

    // Assert
    verify(identityLinkDataManager).findIdentityLinksByProcessDefinitionId(eq("42"));
    assertTrue(actualFindIdentityLinksByProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinkByTaskUserGroupAndType(String, String, String, String)}.
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinkByTaskUserGroupAndType(String, String, String, String)}
   */
  @Test
  public void testFindIdentityLinkByTaskUserGroupAndType() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinkByTaskUserGroupAndType(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinkByTaskUserGroupAndTypeResult = identityLinkEntityManagerImpl
        .findIdentityLinkByTaskUserGroupAndType("42", "42", "42", "Type");

    // Assert
    verify(identityLinkDataManager).findIdentityLinkByTaskUserGroupAndType(eq("42"), eq("42"), eq("42"), eq("Type"));
    assertTrue(actualFindIdentityLinkByTaskUserGroupAndTypeResult.isEmpty());
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinkByProcessInstanceUserGroupAndType(String, String, String, String)}.
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinkByProcessInstanceUserGroupAndType(String, String, String, String)}
   */
  @Test
  public void testFindIdentityLinkByProcessInstanceUserGroupAndType() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinkByProcessInstanceUserGroupAndType(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinkByProcessInstanceUserGroupAndTypeResult = identityLinkEntityManagerImpl
        .findIdentityLinkByProcessInstanceUserGroupAndType("42", "42", "42", "Type");

    // Assert
    verify(identityLinkDataManager).findIdentityLinkByProcessInstanceUserGroupAndType(eq("42"), eq("42"), eq("42"),
        eq("Type"));
    assertTrue(actualFindIdentityLinkByProcessInstanceUserGroupAndTypeResult.isEmpty());
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinkByProcessDefinitionUserAndGroup(String, String, String)}.
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#findIdentityLinkByProcessDefinitionUserAndGroup(String, String, String)}
   */
  @Test
  public void testFindIdentityLinkByProcessDefinitionUserAndGroup() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinkByProcessDefinitionUserAndGroup(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinkByProcessDefinitionUserAndGroupResult = identityLinkEntityManagerImpl
        .findIdentityLinkByProcessDefinitionUserAndGroup("42", "42", "42");

    // Assert
    verify(identityLinkDataManager).findIdentityLinkByProcessDefinitionUserAndGroup(eq("42"), eq("42"), eq("42"));
    assertTrue(actualFindIdentityLinkByProcessDefinitionUserAndGroupResult.isEmpty());
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#involveUser(ExecutionEntity, String, String)}.
   * <ul>
   *   <li>Then createWithEmptyRelationshipCollections
   * {@link ExecutionEntityImpl#identityLinks} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#involveUser(ExecutionEntity, String, String)}
   */
  @Test
  public void testInvolveUser_thenCreateWithEmptyRelationshipCollectionsIdentityLinksSizeIsOne() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    doNothing().when(identityLinkDataManager).insert(Mockito.<IdentityLinkEntity>any());
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    when(identityLinkDataManager.create()).thenReturn(identityLinkEntityImpl);
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    IdentityLinkEntity actualInvolveUserResult = identityLinkEntityManagerImpl.involveUser(executionEntity, "42",
        "Type");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(identityLinkDataManager).create();
    verify(identityLinkDataManager).insert(isA(IdentityLinkEntity.class));
    List<IdentityLinkEntity> identityLinkEntityList = executionEntity.identityLinks;
    assertEquals(1, identityLinkEntityList.size());
    assertSame(identityLinkEntityImpl, identityLinkEntityList.get(0));
    assertSame(identityLinkEntityImpl, actualInvolveUserResult);
    ExecutionEntity expectedSourceActivityExecution = ((IdentityLinkEntityImpl) actualInvolveUserResult).processInstance;
    assertSame(expectedSourceActivityExecution, executionEntity.getSourceActivityExecution());
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#addCandidateUser(TaskEntity, String)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#addCandidateUser(TaskEntity, String)}
   */
  @Test
  public void testAddCandidateUser_thenCallsGetEventDispatcher() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    doNothing().when(identityLinkDataManager).insert(Mockito.<IdentityLinkEntity>any());
    when(identityLinkDataManager.create()).thenReturn(new IdentityLinkEntityImpl());
    TaskEntity taskEntity = mock(TaskEntity.class);
    when(taskEntity.getProcessInstance()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(taskEntity.getExecutionId()).thenReturn("42");
    when(taskEntity.getId()).thenReturn("42");
    when(taskEntity.getProcessDefinitionId()).thenReturn("42");
    when(taskEntity.getProcessInstanceId()).thenReturn("42");
    when(taskEntity.getIdentityLinks()).thenReturn(new ArrayList<>());

    // Act
    identityLinkEntityManagerImpl.addCandidateUser(taskEntity, "42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(taskEntity).getIdentityLinks();
    verify(taskEntity).getProcessInstance();
    verify(identityLinkDataManager, atLeast(1)).create();
    verify(identityLinkDataManager, atLeast(1)).insert(isA(IdentityLinkEntity.class));
    verify(taskEntity, atLeast(1)).getExecutionId();
    verify(taskEntity).getId();
    verify(taskEntity, atLeast(1)).getProcessDefinitionId();
    verify(taskEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#addCandidateGroup(TaskEntity, String)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#addCandidateGroup(TaskEntity, String)}
   */
  @Test
  public void testAddCandidateGroup_thenCallsGetEventDispatcher() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    doNothing().when(identityLinkDataManager).insert(Mockito.<IdentityLinkEntity>any());
    when(identityLinkDataManager.create()).thenReturn(new IdentityLinkEntityImpl());
    TaskEntity taskEntity = mock(TaskEntity.class);
    when(taskEntity.getExecutionId()).thenReturn("42");
    when(taskEntity.getId()).thenReturn("42");
    when(taskEntity.getProcessDefinitionId()).thenReturn("42");
    when(taskEntity.getProcessInstanceId()).thenReturn("42");
    when(taskEntity.getIdentityLinks()).thenReturn(new ArrayList<>());

    // Act
    identityLinkEntityManagerImpl.addCandidateGroup(taskEntity, "42");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(taskEntity).getIdentityLinks();
    verify(identityLinkDataManager).create();
    verify(identityLinkDataManager).insert(isA(IdentityLinkEntity.class));
    verify(taskEntity, atLeast(1)).getExecutionId();
    verify(taskEntity).getId();
    verify(taskEntity, atLeast(1)).getProcessDefinitionId();
    verify(taskEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#addGroupIdentityLink(TaskEntity, String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#addGroupIdentityLink(TaskEntity, String, String)}
   */
  @Test
  public void testAddGroupIdentityLink_thenCallsGetEventDispatcher() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    doNothing().when(identityLinkDataManager).insert(Mockito.<IdentityLinkEntity>any());
    when(identityLinkDataManager.create()).thenReturn(new IdentityLinkEntityImpl());
    TaskEntity taskEntity = mock(TaskEntity.class);
    when(taskEntity.getExecutionId()).thenReturn("42");
    when(taskEntity.getId()).thenReturn("42");
    when(taskEntity.getProcessDefinitionId()).thenReturn("42");
    when(taskEntity.getProcessInstanceId()).thenReturn("42");
    when(taskEntity.getIdentityLinks()).thenReturn(new ArrayList<>());

    // Act
    identityLinkEntityManagerImpl.addGroupIdentityLink(taskEntity, "42", "Identity Link Type");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(taskEntity).getIdentityLinks();
    verify(identityLinkDataManager).create();
    verify(identityLinkDataManager).insert(isA(IdentityLinkEntity.class));
    verify(taskEntity, atLeast(1)).getExecutionId();
    verify(taskEntity).getId();
    verify(taskEntity, atLeast(1)).getProcessDefinitionId();
    verify(taskEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#addUserIdentityLink(TaskEntity, String, String)}
   * with {@code taskEntity}, {@code userId}, {@code identityLinkType}.
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#addUserIdentityLink(TaskEntity, String, String)}
   */
  @Test
  public void testAddUserIdentityLinkWithTaskEntityUserIdIdentityLinkType() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    doNothing().when(identityLinkDataManager).insert(Mockito.<IdentityLinkEntity>any());
    when(identityLinkDataManager.create()).thenReturn(new IdentityLinkEntityImpl());
    TaskEntity taskEntity = mock(TaskEntity.class);
    when(taskEntity.getProcessInstance()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(taskEntity.getExecutionId()).thenReturn("42");
    when(taskEntity.getId()).thenReturn("42");
    when(taskEntity.getProcessDefinitionId()).thenReturn("42");
    when(taskEntity.getProcessInstanceId()).thenReturn("42");
    when(taskEntity.getIdentityLinks()).thenReturn(new ArrayList<>());

    // Act
    identityLinkEntityManagerImpl.addUserIdentityLink(taskEntity, "42", "Identity Link Type");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(taskEntity).getIdentityLinks();
    verify(taskEntity).getProcessInstance();
    verify(identityLinkDataManager, atLeast(1)).create();
    verify(identityLinkDataManager, atLeast(1)).insert(isA(IdentityLinkEntity.class));
    verify(taskEntity, atLeast(1)).getExecutionId();
    verify(taskEntity).getId();
    verify(taskEntity, atLeast(1)).getProcessDefinitionId();
    verify(taskEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#addUserIdentityLink(TaskEntity, String, String, byte[])}
   * with {@code taskEntity}, {@code userId}, {@code identityLinkType},
   * {@code details}.
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#addUserIdentityLink(TaskEntity, String, String, byte[])}
   */
  @Test
  public void testAddUserIdentityLinkWithTaskEntityUserIdIdentityLinkTypeDetails() throws UnsupportedEncodingException {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    doNothing().when(identityLinkDataManager).insert(Mockito.<IdentityLinkEntity>any());
    when(identityLinkDataManager.create()).thenReturn(new IdentityLinkEntityImpl());
    TaskEntity taskEntity = mock(TaskEntity.class);
    when(taskEntity.getProcessInstance()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(taskEntity.getExecutionId()).thenReturn("42");
    when(taskEntity.getId()).thenReturn("42");
    when(taskEntity.getProcessDefinitionId()).thenReturn("42");
    when(taskEntity.getProcessInstanceId()).thenReturn("42");
    when(taskEntity.getIdentityLinks()).thenReturn(new ArrayList<>());

    // Act
    identityLinkEntityManagerImpl.addUserIdentityLink(taskEntity, "42", "Identity Link Type",
        "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(taskEntity).getIdentityLinks();
    verify(taskEntity).getProcessInstance();
    verify(identityLinkDataManager, atLeast(1)).create();
    verify(identityLinkDataManager, atLeast(1)).insert(isA(IdentityLinkEntity.class));
    verify(taskEntity, atLeast(1)).getExecutionId();
    verify(taskEntity).getId();
    verify(taskEntity, atLeast(1)).getProcessDefinitionId();
    verify(taskEntity, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test
   * {@link IdentityLinkEntityManagerImpl#deleteIdentityLinksByProcDef(String)}.
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityManagerImpl#deleteIdentityLinksByProcDef(String)}
   */
  @Test
  public void testDeleteIdentityLinksByProcDef() {
    // Arrange
    doNothing().when(identityLinkDataManager).deleteIdentityLinksByProcDef(Mockito.<String>any());

    // Act
    identityLinkEntityManagerImpl.deleteIdentityLinksByProcDef("42");

    // Assert
    verify(identityLinkDataManager).deleteIdentityLinksByProcDef(eq("42"));
  }
}
