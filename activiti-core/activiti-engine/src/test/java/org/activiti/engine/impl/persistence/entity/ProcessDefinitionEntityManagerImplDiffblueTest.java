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
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.ProcessDefinitionQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionDataManager;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessDefinitionEntityManagerImplDiffblueTest {
  @Mock
  private ProcessDefinitionDataManager processDefinitionDataManager;

  @InjectMocks
  private ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessDefinitionEntityManagerImpl#ProcessDefinitionEntityManagerImpl(ProcessEngineConfigurationImpl, ProcessDefinitionDataManager)}
   *   <li>
   * {@link ProcessDefinitionEntityManagerImpl#setProcessDefinitionDataManager(ProcessDefinitionDataManager)}
   *   <li>{@link ProcessDefinitionEntityManagerImpl#getDataManager()}
   *   <li>
   * {@link ProcessDefinitionEntityManagerImpl#getProcessDefinitionDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessDefinitionEntityManagerImpl actualProcessDefinitionEntityManagerImpl = new ProcessDefinitionEntityManagerImpl(
        processEngineConfiguration, new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));
    MybatisProcessDefinitionDataManager processDefinitionDataManager = new MybatisProcessDefinitionDataManager(
        new JtaProcessEngineConfiguration());
    actualProcessDefinitionEntityManagerImpl.setProcessDefinitionDataManager(processDefinitionDataManager);
    DataManager<ProcessDefinitionEntity> actualDataManager = actualProcessDefinitionEntityManagerImpl.getDataManager();

    // Assert that nothing has changed
    assertSame(processDefinitionDataManager, actualDataManager);
    assertSame(processDefinitionDataManager,
        actualProcessDefinitionEntityManagerImpl.getProcessDefinitionDataManager());
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findLatestProcessDefinitionByKey(String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findLatestProcessDefinitionByKey(String)}
   */
  @Test
  public void testFindLatestProcessDefinitionByKey() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinitionEntity actualFindLatestProcessDefinitionByKeyResult = processDefinitionEntityManagerImpl
        .findLatestProcessDefinitionByKey("Process Definition Key");

    // Assert
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey(eq("Process Definition Key"));
    assertSame(processDefinitionEntityImpl, actualFindLatestProcessDefinitionByKeyResult);
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findLatestProcessDefinitionByKeyAndTenantId(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findLatestProcessDefinitionByKeyAndTenantId(String, String)}
   */
  @Test
  public void testFindLatestProcessDefinitionByKeyAndTenantId() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findLatestProcessDefinitionByKeyAndTenantId(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinitionEntity actualFindLatestProcessDefinitionByKeyAndTenantIdResult = processDefinitionEntityManagerImpl
        .findLatestProcessDefinitionByKeyAndTenantId("Process Definition Key", "42");

    // Assert
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKeyAndTenantId(eq("Process Definition Key"),
        eq("42"));
    assertSame(processDefinitionEntityImpl, actualFindLatestProcessDefinitionByKeyAndTenantIdResult);
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#deleteProcessDefinitionsByDeploymentId(String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#deleteProcessDefinitionsByDeploymentId(String)}
   */
  @Test
  public void testDeleteProcessDefinitionsByDeploymentId() {
    // Arrange
    doNothing().when(processDefinitionDataManager).deleteProcessDefinitionsByDeploymentId(Mockito.<String>any());

    // Act
    processDefinitionEntityManagerImpl.deleteProcessDefinitionsByDeploymentId("42");

    // Assert
    verify(processDefinitionDataManager).deleteProcessDefinitionsByDeploymentId(eq("42"));
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionsByQueryCriteria(ProcessDefinitionQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionsByQueryCriteria(ProcessDefinitionQueryImpl, Page)}
   */
  @Test
  public void testFindProcessDefinitionsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionsByQueryCriteria(Mockito.<ProcessDefinitionQueryImpl>any(),
        Mockito.<Page>any())).thenReturn(new ArrayList<>());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl = new ProcessDefinitionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), processDefinitionDataManager);
    ProcessDefinitionQueryImpl processDefinitionQuery = new ProcessDefinitionQueryImpl();

    // Act
    List<ProcessDefinition> actualFindProcessDefinitionsByQueryCriteriaResult = processDefinitionEntityManagerImpl
        .findProcessDefinitionsByQueryCriteria(processDefinitionQuery, new Page(1, 3));

    // Assert
    verify(processDefinitionDataManager).findProcessDefinitionsByQueryCriteria(isA(ProcessDefinitionQueryImpl.class),
        isA(Page.class));
    assertTrue(actualFindProcessDefinitionsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionCountByQueryCriteria(ProcessDefinitionQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionCountByQueryCriteria(ProcessDefinitionQueryImpl)}
   */
  @Test
  public void testFindProcessDefinitionCountByQueryCriteria_thenReturnThree() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager
        .findProcessDefinitionCountByQueryCriteria(Mockito.<ProcessDefinitionQueryImpl>any())).thenReturn(3L);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl = new ProcessDefinitionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    long actualFindProcessDefinitionCountByQueryCriteriaResult = processDefinitionEntityManagerImpl
        .findProcessDefinitionCountByQueryCriteria(new ProcessDefinitionQueryImpl());

    // Assert
    verify(processDefinitionDataManager)
        .findProcessDefinitionCountByQueryCriteria(isA(ProcessDefinitionQueryImpl.class));
    assertEquals(3L, actualFindProcessDefinitionCountByQueryCriteriaResult);
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByDeploymentAndKey(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByDeploymentAndKey(String, String)}
   */
  @Test
  public void testFindProcessDefinitionByDeploymentAndKey() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findProcessDefinitionByDeploymentAndKey(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinitionEntity actualFindProcessDefinitionByDeploymentAndKeyResult = processDefinitionEntityManagerImpl
        .findProcessDefinitionByDeploymentAndKey("42", "Process Definition Key");

    // Assert
    verify(processDefinitionDataManager).findProcessDefinitionByDeploymentAndKey(eq("42"),
        eq("Process Definition Key"));
    assertSame(processDefinitionEntityImpl, actualFindProcessDefinitionByDeploymentAndKeyResult);
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByDeploymentAndKeyAndTenantId(String, String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByDeploymentAndKeyAndTenantId(String, String, String)}
   */
  @Test
  public void testFindProcessDefinitionByDeploymentAndKeyAndTenantId() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findProcessDefinitionByDeploymentAndKeyAndTenantId(Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<String>any())).thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinitionEntity actualFindProcessDefinitionByDeploymentAndKeyAndTenantIdResult = processDefinitionEntityManagerImpl
        .findProcessDefinitionByDeploymentAndKeyAndTenantId("42", "Process Definition Key", "42");

    // Assert
    verify(processDefinitionDataManager).findProcessDefinitionByDeploymentAndKeyAndTenantId(eq("42"),
        eq("Process Definition Key"), eq("42"));
    assertSame(processDefinitionEntityImpl, actualFindProcessDefinitionByDeploymentAndKeyAndTenantIdResult);
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)}
   */
  @Test
  public void testFindProcessDefinitionByKeyAndVersionAndTenantId() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findProcessDefinitionByKeyAndVersionAndTenantId(Mockito.<String>any(),
        Mockito.<Integer>any(), Mockito.<String>any())).thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinition actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult = processDefinitionEntityManagerImpl
        .findProcessDefinitionByKeyAndVersionAndTenantId("Process Definition Key", 1, "42");

    // Assert
    verify(processDefinitionDataManager).findProcessDefinitionByKeyAndVersionAndTenantId(eq("Process Definition Key"),
        eq(1), eq("42"));
    assertSame(processDefinitionEntityImpl, actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult);
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)}
   */
  @Test
  public void testFindProcessDefinitionByKeyAndVersionAndTenantId_whenEmptyString() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findProcessDefinitionByKeyAndVersion(Mockito.<String>any(),
        Mockito.<Integer>any())).thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinition actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult = processDefinitionEntityManagerImpl
        .findProcessDefinitionByKeyAndVersionAndTenantId("Process Definition Key", 1, "");

    // Assert
    verify(processDefinitionDataManager).findProcessDefinitionByKeyAndVersion(eq("Process Definition Key"), eq(1));
    assertSame(processDefinitionEntityImpl, actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult);
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)}
   */
  @Test
  public void testFindProcessDefinitionByKeyAndVersionAndTenantId_whenNull() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findProcessDefinitionByKeyAndVersion(Mockito.<String>any(),
        Mockito.<Integer>any())).thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinition actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult = processDefinitionEntityManagerImpl
        .findProcessDefinitionByKeyAndVersionAndTenantId("Process Definition Key", 1, null);

    // Assert
    verify(processDefinitionDataManager).findProcessDefinitionByKeyAndVersion(eq("Process Definition Key"), eq(1));
    assertSame(processDefinitionEntityImpl, actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult);
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionsByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionsByNativeQuery(Map, int, int)}
   */
  @Test
  public void testFindProcessDefinitionsByNativeQuery_thenReturnEmpty() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionsByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(),
        anyInt())).thenReturn(new ArrayList<>());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl = new ProcessDefinitionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    List<ProcessDefinition> actualFindProcessDefinitionsByNativeQueryResult = processDefinitionEntityManagerImpl
        .findProcessDefinitionsByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(processDefinitionDataManager).findProcessDefinitionsByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindProcessDefinitionsByNativeQueryResult.isEmpty());
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionCountByNativeQuery(Map)}
   */
  @Test
  public void testFindProcessDefinitionCountByNativeQuery_thenReturnThree() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionCountByNativeQuery(Mockito.<Map<String, Object>>any()))
        .thenReturn(3L);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl = new ProcessDefinitionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    long actualFindProcessDefinitionCountByNativeQueryResult = processDefinitionEntityManagerImpl
        .findProcessDefinitionCountByNativeQuery(new HashMap<>());

    // Assert
    verify(processDefinitionDataManager).findProcessDefinitionCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindProcessDefinitionCountByNativeQueryResult);
  }

  /**
   * Test
   * {@link ProcessDefinitionEntityManagerImpl#updateProcessDefinitionTenantIdForDeployment(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionEntityManagerImpl#updateProcessDefinitionTenantIdForDeployment(String, String)}
   */
  @Test
  public void testUpdateProcessDefinitionTenantIdForDeployment() {
    // Arrange
    doNothing().when(processDefinitionDataManager)
        .updateProcessDefinitionTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());

    // Act
    processDefinitionEntityManagerImpl.updateProcessDefinitionTenantIdForDeployment("42", "42");

    // Assert
    verify(processDefinitionDataManager).updateProcessDefinitionTenantIdForDeployment(eq("42"), eq("42"));
  }
}
