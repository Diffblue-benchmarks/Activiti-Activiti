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
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricActivityInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricActivityInstanceDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class HistoricActivityInstanceEntityManagerImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       HistoricActivityInstanceEntityManagerImpl#HistoricActivityInstanceEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       HistoricActivityInstanceDataManager)}
   *   <li>{@link
   *       HistoricActivityInstanceEntityManagerImpl#setHistoricActivityInstanceDataManager(HistoricActivityInstanceDataManager)}
   *   <li>{@link HistoricActivityInstanceEntityManagerImpl#getDataManager()}
   *   <li>{@link
   *       HistoricActivityInstanceEntityManagerImpl#getHistoricActivityInstanceDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricActivityInstanceEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, HistoricActivityInstanceDataManager)",
    "DataManager HistoricActivityInstanceEntityManagerImpl.getDataManager()",
    "HistoricActivityInstanceDataManager HistoricActivityInstanceEntityManagerImpl.getHistoricActivityInstanceDataManager()",
    "void HistoricActivityInstanceEntityManagerImpl.setHistoricActivityInstanceDataManager(HistoricActivityInstanceDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoricActivityInstanceEntityManagerImpl actualHistoricActivityInstanceEntityManagerImpl =
        new HistoricActivityInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration()));
    MybatisHistoricActivityInstanceDataManager historicActivityInstanceDataManager =
        new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration());
    actualHistoricActivityInstanceEntityManagerImpl.setHistoricActivityInstanceDataManager(
        historicActivityInstanceDataManager);
    DataManager<HistoricActivityInstanceEntity> actualDataManager =
        actualHistoricActivityInstanceEntityManagerImpl.getDataManager();

    // Assert
    assertSame(historicActivityInstanceDataManager, actualDataManager);
    assertSame(
        historicActivityInstanceDataManager,
        actualHistoricActivityInstanceEntityManagerImpl.getHistoricActivityInstanceDataManager());
  }

  /**
   * Test {@link
   * HistoricActivityInstanceEntityManagerImpl#findUnfinishedHistoricActivityInstancesByExecutionAndActivityId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * HistoricActivityInstanceEntityManagerImpl#findUnfinishedHistoricActivityInstancesByExecutionAndActivityId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricActivityInstanceEntityManagerImpl.findUnfinishedHistoricActivityInstancesByExecutionAndActivityId(String, String)"
  })
  public void testFindUnfinishedHistoricActivityInstancesByExecutionAndActivityId() {
    // Arrange
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager =
        mock(HistoricActivityInstanceDataManager.class);
    when(historicActivityInstanceDataManager
            .findUnfinishedHistoricActivityInstancesByExecutionAndActivityId(
                Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        new HistoricActivityInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicActivityInstanceDataManager);

    // Act
    List<HistoricActivityInstanceEntity>
        actualFindUnfinishedHistoricActivityInstancesByExecutionAndActivityIdResult =
            historicActivityInstanceEntityManagerImpl
                .findUnfinishedHistoricActivityInstancesByExecutionAndActivityId("42", "42");

    // Assert
    verify(historicActivityInstanceDataManager)
        .findUnfinishedHistoricActivityInstancesByExecutionAndActivityId("42", "42");
    assertTrue(
        actualFindUnfinishedHistoricActivityInstancesByExecutionAndActivityIdResult.isEmpty());
  }

  /**
   * Test {@link
   * HistoricActivityInstanceEntityManagerImpl#findUnfinishedHistoricActivityInstancesByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricActivityInstanceEntityManagerImpl#findUnfinishedHistoricActivityInstancesByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricActivityInstanceEntityManagerImpl.findUnfinishedHistoricActivityInstancesByProcessInstanceId(String)"
  })
  public void testFindUnfinishedHistoricActivityInstancesByProcessInstanceId() {
    // Arrange
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager =
        mock(HistoricActivityInstanceDataManager.class);
    when(historicActivityInstanceDataManager
            .findUnfinishedHistoricActivityInstancesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        new HistoricActivityInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicActivityInstanceDataManager);

    // Act
    List<HistoricActivityInstanceEntity>
        actualFindUnfinishedHistoricActivityInstancesByProcessInstanceIdResult =
            historicActivityInstanceEntityManagerImpl
                .findUnfinishedHistoricActivityInstancesByProcessInstanceId("42");

    // Assert
    verify(historicActivityInstanceDataManager)
        .findUnfinishedHistoricActivityInstancesByProcessInstanceId("42");
    assertTrue(actualFindUnfinishedHistoricActivityInstancesByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link
   * HistoricActivityInstanceEntityManagerImpl#deleteHistoricActivityInstancesByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricActivityInstanceEntityManagerImpl#deleteHistoricActivityInstancesByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricActivityInstanceEntityManagerImpl.deleteHistoricActivityInstancesByProcessInstanceId(String)"
  })
  public void testDeleteHistoricActivityInstancesByProcessInstanceId() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        new HistoricActivityInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act
    historicActivityInstanceEntityManagerImpl.deleteHistoricActivityInstancesByProcessInstanceId(
        "42");

    // Assert
    verify(processEngineConfiguration).getHistoryManager();
  }

  /**
   * Test {@link
   * HistoricActivityInstanceEntityManagerImpl#deleteHistoricActivityInstancesByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricActivityInstanceEntityManagerImpl#deleteHistoricActivityInstancesByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricActivityInstanceEntityManagerImpl.deleteHistoricActivityInstancesByProcessInstanceId(String)"
  })
  public void testDeleteHistoricActivityInstancesByProcessInstanceId2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    HistoricActivityInstanceDataManager historicActivityInstanceDataManager =
        mock(HistoricActivityInstanceDataManager.class);
    doNothing()
        .when(historicActivityInstanceDataManager)
        .deleteHistoricActivityInstancesByProcessInstanceId(Mockito.<String>any());

    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        new HistoricActivityInstanceEntityManagerImpl(
            processEngineConfiguration, historicActivityInstanceDataManager);

    // Act
    historicActivityInstanceEntityManagerImpl.deleteHistoricActivityInstancesByProcessInstanceId(
        "42");

    // Assert
    verify(processEngineConfiguration).getHistoryManager();
    verify(historicActivityInstanceDataManager)
        .deleteHistoricActivityInstancesByProcessInstanceId("42");
  }

  /**
   * Test {@link
   * HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstanceCountByQueryCriteria(HistoricActivityInstanceQueryImpl)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstanceCountByQueryCriteria(HistoricActivityInstanceQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long HistoricActivityInstanceEntityManagerImpl.findHistoricActivityInstanceCountByQueryCriteria(HistoricActivityInstanceQueryImpl)"
  })
  public void testFindHistoricActivityInstanceCountByQueryCriteria_thenReturnThree() {
    // Arrange
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager =
        mock(HistoricActivityInstanceDataManager.class);
    when(historicActivityInstanceDataManager.findHistoricActivityInstanceCountByQueryCriteria(
            Mockito.<HistoricActivityInstanceQueryImpl>any()))
        .thenReturn(3L);
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        new HistoricActivityInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicActivityInstanceDataManager);

    // Act
    long actualFindHistoricActivityInstanceCountByQueryCriteriaResult =
        historicActivityInstanceEntityManagerImpl.findHistoricActivityInstanceCountByQueryCriteria(
            new HistoricActivityInstanceQueryImpl());

    // Assert
    verify(historicActivityInstanceDataManager)
        .findHistoricActivityInstanceCountByQueryCriteria(
            isA(HistoricActivityInstanceQueryImpl.class));
    assertEquals(3L, actualFindHistoricActivityInstanceCountByQueryCriteriaResult);
  }

  /**
   * Test {@link
   * HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstancesByQueryCriteria(HistoricActivityInstanceQueryImpl,
   * Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstancesByQueryCriteria(HistoricActivityInstanceQueryImpl,
   * Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricActivityInstanceEntityManagerImpl.findHistoricActivityInstancesByQueryCriteria(HistoricActivityInstanceQueryImpl, Page)"
  })
  public void testFindHistoricActivityInstancesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager =
        mock(HistoricActivityInstanceDataManager.class);
    when(historicActivityInstanceDataManager.findHistoricActivityInstancesByQueryCriteria(
            Mockito.<HistoricActivityInstanceQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        new HistoricActivityInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicActivityInstanceDataManager);
    HistoricActivityInstanceQueryImpl historicActivityInstanceQuery =
        new HistoricActivityInstanceQueryImpl();

    // Act
    List<HistoricActivityInstance> actualFindHistoricActivityInstancesByQueryCriteriaResult =
        historicActivityInstanceEntityManagerImpl.findHistoricActivityInstancesByQueryCriteria(
            historicActivityInstanceQuery, new Page(1, 3));

    // Assert
    verify(historicActivityInstanceDataManager)
        .findHistoricActivityInstancesByQueryCriteria(
            isA(HistoricActivityInstanceQueryImpl.class), isA(Page.class));
    assertTrue(actualFindHistoricActivityInstancesByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link
   * HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstancesByNativeQuery(Map, int,
   * int)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstancesByNativeQuery(Map, int,
   * int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricActivityInstanceEntityManagerImpl.findHistoricActivityInstancesByNativeQuery(Map, int, int)"
  })
  public void testFindHistoricActivityInstancesByNativeQuery_thenReturnEmpty() {
    // Arrange
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager =
        mock(HistoricActivityInstanceDataManager.class);
    when(historicActivityInstanceDataManager.findHistoricActivityInstancesByNativeQuery(
            Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        new HistoricActivityInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicActivityInstanceDataManager);

    // Act
    List<HistoricActivityInstance> actualFindHistoricActivityInstancesByNativeQueryResult =
        historicActivityInstanceEntityManagerImpl.findHistoricActivityInstancesByNativeQuery(
            new HashMap<>(), 1, 3);

    // Assert
    verify(historicActivityInstanceDataManager)
        .findHistoricActivityInstancesByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindHistoricActivityInstancesByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link
   * HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstanceCountByNativeQuery(Map)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstanceCountByNativeQuery(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long HistoricActivityInstanceEntityManagerImpl.findHistoricActivityInstanceCountByNativeQuery(Map)"
  })
  public void testFindHistoricActivityInstanceCountByNativeQuery_thenReturnThree() {
    // Arrange
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager =
        mock(HistoricActivityInstanceDataManager.class);
    when(historicActivityInstanceDataManager.findHistoricActivityInstanceCountByNativeQuery(
            Mockito.<Map<String, Object>>any()))
        .thenReturn(3L);
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        new HistoricActivityInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicActivityInstanceDataManager);

    // Act
    long actualFindHistoricActivityInstanceCountByNativeQueryResult =
        historicActivityInstanceEntityManagerImpl.findHistoricActivityInstanceCountByNativeQuery(
            new HashMap<>());

    // Assert
    verify(historicActivityInstanceDataManager)
        .findHistoricActivityInstanceCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindHistoricActivityInstanceCountByNativeQueryResult);
  }
}
