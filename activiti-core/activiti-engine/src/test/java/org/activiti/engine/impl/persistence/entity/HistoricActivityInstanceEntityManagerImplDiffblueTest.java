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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricActivityInstanceEntityManagerImplDiffblueTest {
  @Mock
  private HistoricActivityInstanceDataManager historicActivityInstanceDataManager;

  @InjectMocks
  private HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HistoricActivityInstanceEntityManagerImpl#HistoricActivityInstanceEntityManagerImpl(ProcessEngineConfigurationImpl, HistoricActivityInstanceDataManager)}
   *   <li>
   * {@link HistoricActivityInstanceEntityManagerImpl#setHistoricActivityInstanceDataManager(HistoricActivityInstanceDataManager)}
   *   <li>{@link HistoricActivityInstanceEntityManagerImpl#getDataManager()}
   *   <li>
   * {@link HistoricActivityInstanceEntityManagerImpl#getHistoricActivityInstanceDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoricActivityInstanceEntityManagerImpl actualHistoricActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        processEngineConfiguration,
        new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration()));
    MybatisHistoricActivityInstanceDataManager historicActivityInstanceDataManager = new MybatisHistoricActivityInstanceDataManager(
        new JtaProcessEngineConfiguration());
    actualHistoricActivityInstanceEntityManagerImpl
        .setHistoricActivityInstanceDataManager(historicActivityInstanceDataManager);
    DataManager<HistoricActivityInstanceEntity> actualDataManager = actualHistoricActivityInstanceEntityManagerImpl
        .getDataManager();

    // Assert that nothing has changed
    assertSame(historicActivityInstanceDataManager, actualDataManager);
    assertSame(historicActivityInstanceDataManager,
        actualHistoricActivityInstanceEntityManagerImpl.getHistoricActivityInstanceDataManager());
  }

  /**
   * Test
   * {@link HistoricActivityInstanceEntityManagerImpl#findUnfinishedHistoricActivityInstancesByExecutionAndActivityId(String, String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceEntityManagerImpl#findUnfinishedHistoricActivityInstancesByExecutionAndActivityId(String, String)}
   */
  @Test
  public void testFindUnfinishedHistoricActivityInstancesByExecutionAndActivityId() {
    // Arrange
    when(historicActivityInstanceDataManager
        .findUnfinishedHistoricActivityInstancesByExecutionAndActivityId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<HistoricActivityInstanceEntity> actualFindUnfinishedHistoricActivityInstancesByExecutionAndActivityIdResult = historicActivityInstanceEntityManagerImpl
        .findUnfinishedHistoricActivityInstancesByExecutionAndActivityId("42", "42");

    // Assert
    verify(historicActivityInstanceDataManager)
        .findUnfinishedHistoricActivityInstancesByExecutionAndActivityId(eq("42"), eq("42"));
    assertTrue(actualFindUnfinishedHistoricActivityInstancesByExecutionAndActivityIdResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricActivityInstanceEntityManagerImpl#findUnfinishedHistoricActivityInstancesByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceEntityManagerImpl#findUnfinishedHistoricActivityInstancesByProcessInstanceId(String)}
   */
  @Test
  public void testFindUnfinishedHistoricActivityInstancesByProcessInstanceId() {
    // Arrange
    when(historicActivityInstanceDataManager
        .findUnfinishedHistoricActivityInstancesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<HistoricActivityInstanceEntity> actualFindUnfinishedHistoricActivityInstancesByProcessInstanceIdResult = historicActivityInstanceEntityManagerImpl
        .findUnfinishedHistoricActivityInstancesByProcessInstanceId("42");

    // Assert
    verify(historicActivityInstanceDataManager).findUnfinishedHistoricActivityInstancesByProcessInstanceId(eq("42"));
    assertTrue(actualFindUnfinishedHistoricActivityInstancesByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricActivityInstanceEntityManagerImpl#deleteHistoricActivityInstancesByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceEntityManagerImpl#deleteHistoricActivityInstancesByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricActivityInstancesByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act
    historicActivityInstanceEntityManagerImpl.deleteHistoricActivityInstancesByProcessInstanceId("42");

    // Assert that nothing has changed
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link HistoricActivityInstanceEntityManagerImpl#deleteHistoricActivityInstancesByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceEntityManagerImpl#deleteHistoricActivityInstancesByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricActivityInstancesByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    doNothing().when(historicActivityInstanceDataManager)
        .deleteHistoricActivityInstancesByProcessInstanceId(Mockito.<String>any());

    // Act
    historicActivityInstanceEntityManagerImpl.deleteHistoricActivityInstancesByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicActivityInstanceDataManager).deleteHistoricActivityInstancesByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstanceCountByQueryCriteria(HistoricActivityInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstanceCountByQueryCriteria(HistoricActivityInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricActivityInstanceCountByQueryCriteria_thenReturnThree() {
    // Arrange
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = mock(
        HistoricActivityInstanceDataManager.class);
    when(historicActivityInstanceDataManager
        .findHistoricActivityInstanceCountByQueryCriteria(Mockito.<HistoricActivityInstanceQueryImpl>any()))
        .thenReturn(3L);
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicActivityInstanceDataManager);

    // Act
    long actualFindHistoricActivityInstanceCountByQueryCriteriaResult = historicActivityInstanceEntityManagerImpl
        .findHistoricActivityInstanceCountByQueryCriteria(new HistoricActivityInstanceQueryImpl());

    // Assert
    verify(historicActivityInstanceDataManager)
        .findHistoricActivityInstanceCountByQueryCriteria(isA(HistoricActivityInstanceQueryImpl.class));
    assertEquals(3L, actualFindHistoricActivityInstanceCountByQueryCriteriaResult);
  }

  /**
   * Test
   * {@link HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstancesByQueryCriteria(HistoricActivityInstanceQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstancesByQueryCriteria(HistoricActivityInstanceQueryImpl, Page)}
   */
  @Test
  public void testFindHistoricActivityInstancesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = mock(
        HistoricActivityInstanceDataManager.class);
    when(historicActivityInstanceDataManager.findHistoricActivityInstancesByQueryCriteria(
        Mockito.<HistoricActivityInstanceQueryImpl>any(), Mockito.<Page>any())).thenReturn(new ArrayList<>());
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicActivityInstanceDataManager);
    HistoricActivityInstanceQueryImpl historicActivityInstanceQuery = new HistoricActivityInstanceQueryImpl();

    // Act
    List<HistoricActivityInstance> actualFindHistoricActivityInstancesByQueryCriteriaResult = historicActivityInstanceEntityManagerImpl
        .findHistoricActivityInstancesByQueryCriteria(historicActivityInstanceQuery, new Page(1, 3));

    // Assert
    verify(historicActivityInstanceDataManager)
        .findHistoricActivityInstancesByQueryCriteria(isA(HistoricActivityInstanceQueryImpl.class), isA(Page.class));
    assertTrue(actualFindHistoricActivityInstancesByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstancesByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstancesByNativeQuery(Map, int, int)}
   */
  @Test
  public void testFindHistoricActivityInstancesByNativeQuery_thenReturnEmpty() {
    // Arrange
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = mock(
        HistoricActivityInstanceDataManager.class);
    when(historicActivityInstanceDataManager
        .findHistoricActivityInstancesByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicActivityInstanceDataManager);

    // Act
    List<HistoricActivityInstance> actualFindHistoricActivityInstancesByNativeQueryResult = historicActivityInstanceEntityManagerImpl
        .findHistoricActivityInstancesByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(historicActivityInstanceDataManager).findHistoricActivityInstancesByNativeQuery(isA(Map.class), eq(1),
        eq(3));
    assertTrue(actualFindHistoricActivityInstancesByNativeQueryResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstanceCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceEntityManagerImpl#findHistoricActivityInstanceCountByNativeQuery(Map)}
   */
  @Test
  public void testFindHistoricActivityInstanceCountByNativeQuery_thenReturnThree() {
    // Arrange
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = mock(
        HistoricActivityInstanceDataManager.class);
    when(historicActivityInstanceDataManager
        .findHistoricActivityInstanceCountByNativeQuery(Mockito.<Map<String, Object>>any())).thenReturn(3L);
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicActivityInstanceDataManager);

    // Act
    long actualFindHistoricActivityInstanceCountByNativeQueryResult = historicActivityInstanceEntityManagerImpl
        .findHistoricActivityInstanceCountByNativeQuery(new HashMap<>());

    // Assert
    verify(historicActivityInstanceDataManager).findHistoricActivityInstanceCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindHistoricActivityInstanceCountByNativeQueryResult);
  }
}
