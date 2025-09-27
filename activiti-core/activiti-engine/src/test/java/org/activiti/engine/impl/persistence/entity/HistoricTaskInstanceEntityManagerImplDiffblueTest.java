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
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricTaskInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricTaskInstanceDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricTaskInstanceEntityManagerImplDiffblueTest {
  @Mock private HistoricTaskInstanceDataManager historicTaskInstanceDataManager;

  @InjectMocks private HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl;

  @Mock private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       HistoricTaskInstanceEntityManagerImpl#HistoricTaskInstanceEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       HistoricTaskInstanceDataManager)}
   *   <li>{@link
   *       HistoricTaskInstanceEntityManagerImpl#setHistoricTaskInstanceDataManager(HistoricTaskInstanceDataManager)}
   *   <li>{@link HistoricTaskInstanceEntityManagerImpl#getDataManager()}
   *   <li>{@link HistoricTaskInstanceEntityManagerImpl#getHistoricTaskInstanceDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricTaskInstanceEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, HistoricTaskInstanceDataManager)",
    "DataManager HistoricTaskInstanceEntityManagerImpl.getDataManager()",
    "HistoricTaskInstanceDataManager HistoricTaskInstanceEntityManagerImpl.getHistoricTaskInstanceDataManager()",
    "void HistoricTaskInstanceEntityManagerImpl.setHistoricTaskInstanceDataManager(HistoricTaskInstanceDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoricTaskInstanceEntityManagerImpl actualHistoricTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));
    MybatisHistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration());
    actualHistoricTaskInstanceEntityManagerImpl.setHistoricTaskInstanceDataManager(
        historicTaskInstanceDataManager);
    DataManager<HistoricTaskInstanceEntity> actualDataManager =
        actualHistoricTaskInstanceEntityManagerImpl.getDataManager();

    // Assert
    assertSame(historicTaskInstanceDataManager, actualDataManager);
    assertSame(
        historicTaskInstanceDataManager,
        actualHistoricTaskInstanceEntityManagerImpl.getHistoricTaskInstanceDataManager());
  }

  /**
   * Test {@link
   * HistoricTaskInstanceEntityManagerImpl#deleteHistoricTaskInstancesByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricTaskInstanceEntityManagerImpl#deleteHistoricTaskInstancesByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricTaskInstanceEntityManagerImpl.deleteHistoricTaskInstancesByProcessInstanceId(String)"
  })
  public void testDeleteHistoricTaskInstancesByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act
    historicTaskInstanceEntityManagerImpl.deleteHistoricTaskInstancesByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link
   * HistoricTaskInstanceEntityManagerImpl#deleteHistoricTaskInstancesByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricTaskInstanceEntityManagerImpl#deleteHistoricTaskInstancesByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricTaskInstanceEntityManagerImpl.deleteHistoricTaskInstancesByProcessInstanceId(String)"
  })
  public void testDeleteHistoricTaskInstancesByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.AUDIT));
    when(historicTaskInstanceDataManager.findHistoricTaskInstanceByProcessInstanceId(
            Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicTaskInstanceEntityManagerImpl.deleteHistoricTaskInstancesByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicTaskInstanceDataManager).findHistoricTaskInstanceByProcessInstanceId("42");
  }

  /**
   * Test {@link
   * HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstanceCountByQueryCriteria(HistoricTaskInstanceQueryImpl)}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstanceCountByQueryCriteria(HistoricTaskInstanceQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long HistoricTaskInstanceEntityManagerImpl.findHistoricTaskInstanceCountByQueryCriteria(HistoricTaskInstanceQueryImpl)"
  })
  public void testFindHistoricTaskInstanceCountByQueryCriteria_thenReturnZero() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoryManager(
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertEquals(
        0L,
        historicTaskInstanceEntityManagerImpl.findHistoricTaskInstanceCountByQueryCriteria(
            new HistoricTaskInstanceQueryImpl()));
  }

  /**
   * Test {@link
   * HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesByQueryCriteria(HistoricTaskInstanceQueryImpl)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesByQueryCriteria(HistoricTaskInstanceQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricTaskInstanceEntityManagerImpl.findHistoricTaskInstancesByQueryCriteria(HistoricTaskInstanceQueryImpl)"
  })
  public void testFindHistoricTaskInstancesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoryManager(
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(
        historicTaskInstanceEntityManagerImpl
            .findHistoricTaskInstancesByQueryCriteria(new HistoricTaskInstanceQueryImpl())
            .isEmpty());
  }

  /**
   * Test {@link
   * HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesAndVariablesByQueryCriteria(HistoricTaskInstanceQueryImpl)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesAndVariablesByQueryCriteria(HistoricTaskInstanceQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricTaskInstanceEntityManagerImpl.findHistoricTaskInstancesAndVariablesByQueryCriteria(HistoricTaskInstanceQueryImpl)"
  })
  public void testFindHistoricTaskInstancesAndVariablesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoryManager(
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(
        historicTaskInstanceEntityManagerImpl
            .findHistoricTaskInstancesAndVariablesByQueryCriteria(
                new HistoricTaskInstanceQueryImpl())
            .isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceEntityManagerImpl#delete(String)} with {@code id}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getHistoryManager()}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityManagerImpl#delete(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricTaskInstanceEntityManagerImpl.delete(String)"})
  public void testDeleteWithId_thenCallsGetHistoryManager() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act
    historicTaskInstanceEntityManagerImpl.delete("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link HistoricTaskInstanceEntityManagerImpl#delete(String)} with {@code id}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoryManager()}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceEntityManagerImpl#delete(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricTaskInstanceEntityManagerImpl.delete(String)"})
  public void testDeleteWithId_thenCallsGetHistoryManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);

    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            processEngineConfiguration, historicTaskInstanceDataManager);

    // Act
    historicTaskInstanceEntityManagerImpl.delete("42");

    // Assert
    verify(processEngineConfiguration).getHistoryManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesByNativeQuery(Map,
   * int, int)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstancesByNativeQuery(Map, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricTaskInstanceEntityManagerImpl.findHistoricTaskInstancesByNativeQuery(Map, int, int)"
  })
  public void testFindHistoricTaskInstancesByNativeQuery_thenReturnEmpty() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findHistoricTaskInstancesByNativeQuery(
            Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    // Act
    List<HistoricTaskInstance> actualFindHistoricTaskInstancesByNativeQueryResult =
        historicTaskInstanceEntityManagerImpl.findHistoricTaskInstancesByNativeQuery(
            new HashMap<>(), 1, 3);

    // Assert
    verify(historicTaskInstanceDataManager)
        .findHistoricTaskInstancesByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindHistoricTaskInstancesByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link
   * HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstanceCountByNativeQuery(Map)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricTaskInstanceEntityManagerImpl#findHistoricTaskInstanceCountByNativeQuery(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long HistoricTaskInstanceEntityManagerImpl.findHistoricTaskInstanceCountByNativeQuery(Map)"
  })
  public void testFindHistoricTaskInstanceCountByNativeQuery_thenReturnThree() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findHistoricTaskInstanceCountByNativeQuery(
            Mockito.<Map<String, Object>>any()))
        .thenReturn(3L);
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    // Act
    long actualFindHistoricTaskInstanceCountByNativeQueryResult =
        historicTaskInstanceEntityManagerImpl.findHistoricTaskInstanceCountByNativeQuery(
            new HashMap<>());

    // Assert
    verify(historicTaskInstanceDataManager)
        .findHistoricTaskInstanceCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindHistoricTaskInstanceCountByNativeQueryResult);
  }
}
