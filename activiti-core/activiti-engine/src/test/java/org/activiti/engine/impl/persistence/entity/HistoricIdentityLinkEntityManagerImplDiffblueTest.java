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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricIdentityLinkDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class HistoricIdentityLinkEntityManagerImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       HistoricIdentityLinkEntityManagerImpl#HistoricIdentityLinkEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       HistoricIdentityLinkDataManager)}
   *   <li>{@link
   *       HistoricIdentityLinkEntityManagerImpl#setHistoricIdentityLinkDataManager(HistoricIdentityLinkDataManager)}
   *   <li>{@link HistoricIdentityLinkEntityManagerImpl#getDataManager()}
   *   <li>{@link HistoricIdentityLinkEntityManagerImpl#getHistoricIdentityLinkDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricIdentityLinkEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, HistoricIdentityLinkDataManager)",
    "DataManager HistoricIdentityLinkEntityManagerImpl.getDataManager()",
    "HistoricIdentityLinkDataManager HistoricIdentityLinkEntityManagerImpl.getHistoricIdentityLinkDataManager()",
    "void HistoricIdentityLinkEntityManagerImpl.setHistoricIdentityLinkDataManager(HistoricIdentityLinkDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoricIdentityLinkEntityManagerImpl actualHistoricIdentityLinkEntityManagerImpl =
        new HistoricIdentityLinkEntityManagerImpl(
            processEngineConfiguration,
            new MybatisHistoricIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    MybatisHistoricIdentityLinkDataManager historicIdentityLinkDataManager =
        new MybatisHistoricIdentityLinkDataManager(new JtaProcessEngineConfiguration());
    actualHistoricIdentityLinkEntityManagerImpl.setHistoricIdentityLinkDataManager(
        historicIdentityLinkDataManager);
    DataManager<HistoricIdentityLinkEntity> actualDataManager =
        actualHistoricIdentityLinkEntityManagerImpl.getDataManager();

    // Assert
    assertSame(historicIdentityLinkDataManager, actualDataManager);
    assertSame(
        historicIdentityLinkDataManager,
        actualHistoricIdentityLinkEntityManagerImpl.getHistoricIdentityLinkDataManager());
  }

  /**
   * Test {@link HistoricIdentityLinkEntityManagerImpl#findHistoricIdentityLinksByTaskId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricIdentityLinkEntityManagerImpl#findHistoricIdentityLinksByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricIdentityLinkEntityManagerImpl.findHistoricIdentityLinksByTaskId(String)"
  })
  public void testFindHistoricIdentityLinksByTaskId() {
    // Arrange
    HistoricIdentityLinkDataManager historicIdentityLinkDataManager =
        mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl =
        new HistoricIdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicIdentityLinkDataManager);

    // Act
    List<HistoricIdentityLinkEntity> actualFindHistoricIdentityLinksByTaskIdResult =
        historicIdentityLinkEntityManagerImpl.findHistoricIdentityLinksByTaskId("42");

    // Assert
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByTaskId("42");
    assertTrue(actualFindHistoricIdentityLinksByTaskIdResult.isEmpty());
  }

  /**
   * Test {@link
   * HistoricIdentityLinkEntityManagerImpl#findHistoricIdentityLinksByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricIdentityLinkEntityManagerImpl#findHistoricIdentityLinksByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricIdentityLinkEntityManagerImpl.findHistoricIdentityLinksByProcessInstanceId(String)"
  })
  public void testFindHistoricIdentityLinksByProcessInstanceId() {
    // Arrange
    HistoricIdentityLinkDataManager historicIdentityLinkDataManager =
        mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByProcessInstanceId(
            Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl =
        new HistoricIdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicIdentityLinkDataManager);

    // Act
    List<HistoricIdentityLinkEntity> actualFindHistoricIdentityLinksByProcessInstanceIdResult =
        historicIdentityLinkEntityManagerImpl.findHistoricIdentityLinksByProcessInstanceId("42");

    // Assert
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByProcessInstanceId("42");
    assertTrue(actualFindHistoricIdentityLinksByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByTaskId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByTaskId(String)"
  })
  public void testDeleteHistoricIdentityLinksByTaskId() {
    // Arrange
    HistoricIdentityLinkDataManager historicIdentityLinkDataManager =
        mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl =
        new HistoricIdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicIdentityLinkDataManager);

    // Act
    historicIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByTaskId("42");

    // Assert
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByTaskId("42");
  }

  /**
   * Test {@link HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByTaskId(String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricIdentityLinkDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByTaskId(String)"
  })
  public void testDeleteHistoricIdentityLinksByTaskId_thenCallsDelete() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ArrayList<HistoricIdentityLinkEntity> historicIdentityLinkEntityList = new ArrayList<>();
    historicIdentityLinkEntityList.add(new HistoricIdentityLinkEntityImpl());

    HistoricIdentityLinkDataManager historicIdentityLinkDataManager =
        mock(HistoricIdentityLinkDataManager.class);
    doNothing()
        .when(historicIdentityLinkDataManager)
        .delete(Mockito.<HistoricIdentityLinkEntity>any());
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(historicIdentityLinkEntityList);

    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl =
        new HistoricIdentityLinkEntityManagerImpl(
            processEngineConfiguration, historicIdentityLinkDataManager);

    // Act
    historicIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByTaskId("42");

    // Assert
    verify(historicIdentityLinkDataManager).delete(isA(HistoricIdentityLinkEntity.class));
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByTaskId("42");
  }

  /**
   * Test {@link
   * HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByProcInstance(String)}.
   *
   * <p>Method under test: {@link
   * HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByProcInstance(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByProcInstance(String)"
  })
  public void testDeleteHistoricIdentityLinksByProcInstance() {
    // Arrange
    HistoricIdentityLinkDataManager historicIdentityLinkDataManager =
        mock(HistoricIdentityLinkDataManager.class);
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByProcessInstanceId(
            Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl =
        new HistoricIdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicIdentityLinkDataManager);

    // Act
    historicIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByProcInstance("42");

    // Assert
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByProcessInstanceId("42");
  }

  /**
   * Test {@link
   * HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByProcInstance(String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricIdentityLinkDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByProcInstance(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByProcInstance(String)"
  })
  public void testDeleteHistoricIdentityLinksByProcInstance_thenCallsDelete() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ArrayList<HistoricIdentityLinkEntity> historicIdentityLinkEntityList = new ArrayList<>();
    historicIdentityLinkEntityList.add(new HistoricIdentityLinkEntityImpl());

    HistoricIdentityLinkDataManager historicIdentityLinkDataManager =
        mock(HistoricIdentityLinkDataManager.class);
    doNothing()
        .when(historicIdentityLinkDataManager)
        .delete(Mockito.<HistoricIdentityLinkEntity>any());
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByProcessInstanceId(
            Mockito.<String>any()))
        .thenReturn(historicIdentityLinkEntityList);

    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl =
        new HistoricIdentityLinkEntityManagerImpl(
            processEngineConfiguration, historicIdentityLinkDataManager);

    // Act
    historicIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByProcInstance("42");

    // Assert
    verify(historicIdentityLinkDataManager).delete(isA(HistoricIdentityLinkEntity.class));
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByProcessInstanceId("42");
  }
}
