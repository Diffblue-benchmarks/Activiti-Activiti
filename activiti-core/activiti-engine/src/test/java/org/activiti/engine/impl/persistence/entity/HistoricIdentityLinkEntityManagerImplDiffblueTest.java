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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricIdentityLinkDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricIdentityLinkEntityManagerImplDiffblueTest {
  @Mock
  private HistoricIdentityLinkDataManager historicIdentityLinkDataManager;

  @InjectMocks
  private HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HistoricIdentityLinkEntityManagerImpl#HistoricIdentityLinkEntityManagerImpl(ProcessEngineConfigurationImpl, HistoricIdentityLinkDataManager)}
   *   <li>
   * {@link HistoricIdentityLinkEntityManagerImpl#setHistoricIdentityLinkDataManager(HistoricIdentityLinkDataManager)}
   *   <li>{@link HistoricIdentityLinkEntityManagerImpl#getDataManager()}
   *   <li>
   * {@link HistoricIdentityLinkEntityManagerImpl#getHistoricIdentityLinkDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoricIdentityLinkEntityManagerImpl actualHistoricIdentityLinkEntityManagerImpl = new HistoricIdentityLinkEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    MybatisHistoricIdentityLinkDataManager historicIdentityLinkDataManager = new MybatisHistoricIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());
    actualHistoricIdentityLinkEntityManagerImpl.setHistoricIdentityLinkDataManager(historicIdentityLinkDataManager);
    DataManager<HistoricIdentityLinkEntity> actualDataManager = actualHistoricIdentityLinkEntityManagerImpl
        .getDataManager();

    // Assert that nothing has changed
    assertSame(historicIdentityLinkDataManager, actualDataManager);
    assertSame(historicIdentityLinkDataManager,
        actualHistoricIdentityLinkEntityManagerImpl.getHistoricIdentityLinkDataManager());
  }

  /**
   * Test
   * {@link HistoricIdentityLinkEntityManagerImpl#findHistoricIdentityLinksByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricIdentityLinkEntityManagerImpl#findHistoricIdentityLinksByTaskId(String)}
   */
  @Test
  public void testFindHistoricIdentityLinksByTaskId() {
    // Arrange
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<HistoricIdentityLinkEntity> actualFindHistoricIdentityLinksByTaskIdResult = historicIdentityLinkEntityManagerImpl
        .findHistoricIdentityLinksByTaskId("42");

    // Assert
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByTaskId(eq("42"));
    assertTrue(actualFindHistoricIdentityLinksByTaskIdResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricIdentityLinkEntityManagerImpl#findHistoricIdentityLinksByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricIdentityLinkEntityManagerImpl#findHistoricIdentityLinksByProcessInstanceId(String)}
   */
  @Test
  public void testFindHistoricIdentityLinksByProcessInstanceId() {
    // Arrange
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<HistoricIdentityLinkEntity> actualFindHistoricIdentityLinksByProcessInstanceIdResult = historicIdentityLinkEntityManagerImpl
        .findHistoricIdentityLinksByProcessInstanceId("42");

    // Assert
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByProcessInstanceId(eq("42"));
    assertTrue(actualFindHistoricIdentityLinksByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByTaskId(String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricIdentityLinksByTaskId_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByTaskId("42");

    // Assert
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByTaskId(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricIdentityLinksByTaskId_thenCallsGetEventDispatcher() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());

    ArrayList<HistoricIdentityLinkEntity> historicIdentityLinkEntityList = new ArrayList<>();
    historicIdentityLinkEntityList.add(new HistoricIdentityLinkEntityImpl());
    doNothing().when(historicIdentityLinkDataManager).delete(Mockito.<HistoricIdentityLinkEntity>any());
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(historicIdentityLinkEntityList);

    // Act
    historicIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(historicIdentityLinkDataManager).delete(isA(HistoricIdentityLinkEntity.class));
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByProcInstance(String)}.
   * <p>
   * Method under test:
   * {@link HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByProcInstance(String)}
   */
  @Test
  public void testDeleteHistoricIdentityLinksByProcInstance() {
    // Arrange
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByProcInstance("42");

    // Assert
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByProcInstance(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricIdentityLinkEntityManagerImpl#deleteHistoricIdentityLinksByProcInstance(String)}
   */
  @Test
  public void testDeleteHistoricIdentityLinksByProcInstance_thenCallsGetEventDispatcher() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());

    ArrayList<HistoricIdentityLinkEntity> historicIdentityLinkEntityList = new ArrayList<>();
    historicIdentityLinkEntityList.add(new HistoricIdentityLinkEntityImpl());
    doNothing().when(historicIdentityLinkDataManager).delete(Mockito.<HistoricIdentityLinkEntity>any());
    when(historicIdentityLinkDataManager.findHistoricIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicIdentityLinkEntityList);

    // Act
    historicIdentityLinkEntityManagerImpl.deleteHistoricIdentityLinksByProcInstance("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(historicIdentityLinkDataManager).delete(isA(HistoricIdentityLinkEntity.class));
    verify(historicIdentityLinkDataManager).findHistoricIdentityLinksByProcessInstanceId(eq("42"));
  }
}
