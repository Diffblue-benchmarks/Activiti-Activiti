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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisIdentityLinkDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class IdentityLinkEntityManagerImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       IdentityLinkEntityManagerImpl#IdentityLinkEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       IdentityLinkDataManager)}
   *   <li>{@link IdentityLinkEntityManagerImpl#setIdentityLinkDataManager(IdentityLinkDataManager)}
   *   <li>{@link IdentityLinkEntityManagerImpl#getDataManager()}
   *   <li>{@link IdentityLinkEntityManagerImpl#getIdentityLinkDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IdentityLinkEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, IdentityLinkDataManager)",
    "DataManager IdentityLinkEntityManagerImpl.getDataManager()",
    "IdentityLinkDataManager IdentityLinkEntityManagerImpl.getIdentityLinkDataManager()",
    "void IdentityLinkEntityManagerImpl.setIdentityLinkDataManager(IdentityLinkDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    IdentityLinkEntityManagerImpl actualIdentityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            processEngineConfiguration,
            new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    MybatisIdentityLinkDataManager identityLinkDataManager =
        new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration());
    actualIdentityLinkEntityManagerImpl.setIdentityLinkDataManager(identityLinkDataManager);
    DataManager<IdentityLinkEntity> actualDataManager =
        actualIdentityLinkEntityManagerImpl.getDataManager();

    // Assert
    assertSame(identityLinkDataManager, actualDataManager);
    assertSame(
        identityLinkDataManager, actualIdentityLinkEntityManagerImpl.getIdentityLinkDataManager());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#insert(IdentityLinkEntity, boolean)} with {@code
   * IdentityLinkEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getEventDispatcher()}.
   * </ul>
   *
   * <p>Method under test: {@link IdentityLinkEntityManagerImpl#insert(IdentityLinkEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IdentityLinkEntityManagerImpl.insert(IdentityLinkEntity, boolean)"})
  public void testInsertWithIdentityLinkEntityBoolean_thenCallsGetEventDispatcher() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    MybatisIdentityLinkDataManager identityLinkDataManager =
        mock(MybatisIdentityLinkDataManager.class);
    doNothing().when(identityLinkDataManager).insert(Mockito.<IdentityLinkEntity>any());

    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(processEngineConfiguration, identityLinkDataManager);

    // Act
    identityLinkEntityManagerImpl.insert(new IdentityLinkEntityImpl(), false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryManager();
    verify(identityLinkDataManager).insert(isA(IdentityLinkEntity.class));
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#insert(IdentityLinkEntity, boolean)} with {@code
   * IdentityLinkEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getEventDispatcher()}.
   * </ul>
   *
   * <p>Method under test: {@link IdentityLinkEntityManagerImpl#insert(IdentityLinkEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IdentityLinkEntityManagerImpl.insert(IdentityLinkEntity, boolean)"})
  public void testInsertWithIdentityLinkEntityBoolean_thenCallsGetEventDispatcher2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    MybatisIdentityLinkDataManager identityLinkDataManager =
        mock(MybatisIdentityLinkDataManager.class);
    doNothing().when(identityLinkDataManager).insert(Mockito.<IdentityLinkEntity>any());

    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(processEngineConfiguration, identityLinkDataManager);

    // Act
    identityLinkEntityManagerImpl.insert(new IdentityLinkEntityImpl(), true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryManager();
    verify(identityLinkDataManager).insert(isA(IdentityLinkEntity.class));
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#deleteIdentityLink(IdentityLinkEntity, boolean)} with
   * {@code identityLink}, {@code cascadeHistory}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#deleteIdentityLink(IdentityLinkEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IdentityLinkEntityManagerImpl.deleteIdentityLink(IdentityLinkEntity, boolean)"
  })
  public void testDeleteIdentityLinkWithIdentityLinkCascadeHistory() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    MybatisIdentityLinkDataManager identityLinkDataManager =
        mock(MybatisIdentityLinkDataManager.class);
    doNothing().when(identityLinkDataManager).delete(Mockito.<IdentityLinkEntity>any());

    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(processEngineConfiguration, identityLinkDataManager);

    // Act
    identityLinkEntityManagerImpl.deleteIdentityLink(new IdentityLinkEntityImpl(), false);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(identityLinkDataManager).delete(isA(IdentityLinkEntity.class));
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#deleteIdentityLink(IdentityLinkEntity, boolean)} with
   * {@code identityLink}, {@code cascadeHistory}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#deleteIdentityLink(IdentityLinkEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IdentityLinkEntityManagerImpl.deleteIdentityLink(IdentityLinkEntity, boolean)"
  })
  public void testDeleteIdentityLinkWithIdentityLinkCascadeHistory2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    MybatisIdentityLinkDataManager identityLinkDataManager =
        mock(MybatisIdentityLinkDataManager.class);
    doNothing().when(identityLinkDataManager).delete(Mockito.<IdentityLinkEntity>any());

    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(processEngineConfiguration, identityLinkDataManager);

    // Act
    identityLinkEntityManagerImpl.deleteIdentityLink(new IdentityLinkEntityImpl(), false);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(identityLinkDataManager).delete(isA(IdentityLinkEntity.class));
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#deleteIdentityLink(IdentityLinkEntity, boolean)} with
   * {@code identityLink}, {@code cascadeHistory}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#deleteIdentityLink(IdentityLinkEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IdentityLinkEntityManagerImpl.deleteIdentityLink(IdentityLinkEntity, boolean)"
  })
  public void testDeleteIdentityLinkWithIdentityLinkCascadeHistory_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    MybatisIdentityLinkDataManager identityLinkDataManager =
        mock(MybatisIdentityLinkDataManager.class);
    doNothing().when(identityLinkDataManager).delete(Mockito.<IdentityLinkEntity>any());

    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(processEngineConfiguration, identityLinkDataManager);

    // Act
    identityLinkEntityManagerImpl.deleteIdentityLink(new IdentityLinkEntityImpl(), false);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(identityLinkDataManager).delete(isA(IdentityLinkEntity.class));
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#deleteIdentityLink(IdentityLinkEntity, boolean)} with
   * {@code identityLink}, {@code cascadeHistory}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoryManager()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#deleteIdentityLink(IdentityLinkEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IdentityLinkEntityManagerImpl.deleteIdentityLink(IdentityLinkEntity, boolean)"
  })
  public void testDeleteIdentityLinkWithIdentityLinkCascadeHistory_thenCallsGetHistoryManager() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    MybatisIdentityLinkDataManager identityLinkDataManager =
        mock(MybatisIdentityLinkDataManager.class);
    doNothing().when(identityLinkDataManager).delete(Mockito.<IdentityLinkEntity>any());

    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(processEngineConfiguration, identityLinkDataManager);

    // Act
    identityLinkEntityManagerImpl.deleteIdentityLink(new IdentityLinkEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryManager();
    verify(identityLinkDataManager).delete(isA(IdentityLinkEntity.class));
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#findIdentityLinksByTaskId(String)}.
   *
   * <p>Method under test: {@link IdentityLinkEntityManagerImpl#findIdentityLinksByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List IdentityLinkEntityManagerImpl.findIdentityLinksByTaskId(String)"})
  public void testFindIdentityLinksByTaskId() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinksByTaskIdResult =
        identityLinkEntityManagerImpl.findIdentityLinksByTaskId("42");

    // Assert
    verify(identityLinkDataManager).findIdentityLinksByTaskId("42");
    assertTrue(actualFindIdentityLinksByTaskIdResult.isEmpty());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#findIdentityLinksByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinksByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List IdentityLinkEntityManagerImpl.findIdentityLinksByProcessInstanceId(String)"
  })
  public void testFindIdentityLinksByProcessInstanceId() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinksByProcessInstanceIdResult =
        identityLinkEntityManagerImpl.findIdentityLinksByProcessInstanceId("42");

    // Assert
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId("42");
    assertTrue(actualFindIdentityLinksByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#findIdentityLinksByProcessDefinitionId(String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinksByProcessDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List IdentityLinkEntityManagerImpl.findIdentityLinksByProcessDefinitionId(String)"
  })
  public void testFindIdentityLinksByProcessDefinitionId() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinksByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinksByProcessDefinitionIdResult =
        identityLinkEntityManagerImpl.findIdentityLinksByProcessDefinitionId("42");

    // Assert
    verify(identityLinkDataManager).findIdentityLinksByProcessDefinitionId("42");
    assertTrue(actualFindIdentityLinksByProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#findIdentityLinkByTaskUserGroupAndType(String,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinkByTaskUserGroupAndType(String, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List IdentityLinkEntityManagerImpl.findIdentityLinkByTaskUserGroupAndType(String, String, String, String)"
  })
  public void testFindIdentityLinkByTaskUserGroupAndType() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinkByTaskUserGroupAndType(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinkByTaskUserGroupAndTypeResult =
        identityLinkEntityManagerImpl.findIdentityLinkByTaskUserGroupAndType(
            "42", "42", "42", "Type");

    // Assert
    verify(identityLinkDataManager)
        .findIdentityLinkByTaskUserGroupAndType("42", "42", "42", "Type");
    assertTrue(actualFindIdentityLinkByTaskUserGroupAndTypeResult.isEmpty());
  }

  /**
   * Test {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinkByProcessInstanceUserGroupAndType(String, String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinkByProcessInstanceUserGroupAndType(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List IdentityLinkEntityManagerImpl.findIdentityLinkByProcessInstanceUserGroupAndType(String, String, String, String)"
  })
  public void testFindIdentityLinkByProcessInstanceUserGroupAndType() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinkByProcessInstanceUserGroupAndType(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinkByProcessInstanceUserGroupAndTypeResult =
        identityLinkEntityManagerImpl.findIdentityLinkByProcessInstanceUserGroupAndType(
            "42", "42", "42", "Type");

    // Assert
    verify(identityLinkDataManager)
        .findIdentityLinkByProcessInstanceUserGroupAndType("42", "42", "42", "Type");
    assertTrue(actualFindIdentityLinkByProcessInstanceUserGroupAndTypeResult.isEmpty());
  }

  /**
   * Test {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinkByProcessDefinitionUserAndGroup(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinkByProcessDefinitionUserAndGroup(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List IdentityLinkEntityManagerImpl.findIdentityLinkByProcessDefinitionUserAndGroup(String, String, String)"
  })
  public void testFindIdentityLinkByProcessDefinitionUserAndGroup() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinkByProcessDefinitionUserAndGroup(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinkByProcessDefinitionUserAndGroupResult =
        identityLinkEntityManagerImpl.findIdentityLinkByProcessDefinitionUserAndGroup(
            "42", "42", "42");

    // Assert
    verify(identityLinkDataManager)
        .findIdentityLinkByProcessDefinitionUserAndGroup("42", "42", "42");
    assertTrue(actualFindIdentityLinkByProcessDefinitionUserAndGroupResult.isEmpty());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#deleteIdentityLinksByProcDef(String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#deleteIdentityLinksByProcDef(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IdentityLinkEntityManagerImpl.deleteIdentityLinksByProcDef(String)"})
  public void testDeleteIdentityLinksByProcDef() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    doNothing().when(identityLinkDataManager).deleteIdentityLinksByProcDef(Mockito.<String>any());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);

    // Act
    identityLinkEntityManagerImpl.deleteIdentityLinksByProcDef("42");

    // Assert
    verify(identityLinkDataManager).deleteIdentityLinksByProcDef("42");
  }
}
