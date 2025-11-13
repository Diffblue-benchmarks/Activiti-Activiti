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
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.ProcessDefinitionQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionDataManager;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ProcessDefinitionEntityManagerImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       ProcessDefinitionEntityManagerImpl#ProcessDefinitionEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       ProcessDefinitionDataManager)}
   *   <li>{@link
   *       ProcessDefinitionEntityManagerImpl#setProcessDefinitionDataManager(ProcessDefinitionDataManager)}
   *   <li>{@link ProcessDefinitionEntityManagerImpl#getDataManager()}
   *   <li>{@link ProcessDefinitionEntityManagerImpl#getProcessDefinitionDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, ProcessDefinitionDataManager)",
    "DataManager ProcessDefinitionEntityManagerImpl.getDataManager()",
    "ProcessDefinitionDataManager ProcessDefinitionEntityManagerImpl.getProcessDefinitionDataManager()",
    "void ProcessDefinitionEntityManagerImpl.setProcessDefinitionDataManager(ProcessDefinitionDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessDefinitionEntityManagerImpl actualProcessDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            processEngineConfiguration,
            new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));
    MybatisProcessDefinitionDataManager processDefinitionDataManager =
        new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration());
    actualProcessDefinitionEntityManagerImpl.setProcessDefinitionDataManager(
        processDefinitionDataManager);
    DataManager<ProcessDefinitionEntity> actualDataManager =
        actualProcessDefinitionEntityManagerImpl.getDataManager();

    // Assert
    assertSame(processDefinitionDataManager, actualDataManager);
    assertSame(
        processDefinitionDataManager,
        actualProcessDefinitionEntityManagerImpl.getProcessDefinitionDataManager());
  }

  /**
   * Test {@link ProcessDefinitionEntityManagerImpl#findLatestProcessDefinitionByKey(String)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findLatestProcessDefinitionByKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionEntity ProcessDefinitionEntityManagerImpl.findLatestProcessDefinitionByKey(String)"
  })
  public void testFindLatestProcessDefinitionByKey() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    ProcessDefinitionEntity actualFindLatestProcessDefinitionByKeyResult =
        processDefinitionEntityManagerImpl.findLatestProcessDefinitionByKey(
            "Process Definition Key");

    // Assert
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey("Process Definition Key");
    assertSame(processDefinitionEntityImpl, actualFindLatestProcessDefinitionByKeyResult);
  }

  /**
   * Test {@link
   * ProcessDefinitionEntityManagerImpl#findLatestProcessDefinitionByKeyAndTenantId(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findLatestProcessDefinitionByKeyAndTenantId(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionEntity ProcessDefinitionEntityManagerImpl.findLatestProcessDefinitionByKeyAndTenantId(String, String)"
  })
  public void testFindLatestProcessDefinitionByKeyAndTenantId() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findLatestProcessDefinitionByKeyAndTenantId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    ProcessDefinitionEntity actualFindLatestProcessDefinitionByKeyAndTenantIdResult =
        processDefinitionEntityManagerImpl.findLatestProcessDefinitionByKeyAndTenantId(
            "Process Definition Key", "42");

    // Assert
    verify(processDefinitionDataManager)
        .findLatestProcessDefinitionByKeyAndTenantId("Process Definition Key", "42");
    assertSame(
        processDefinitionEntityImpl, actualFindLatestProcessDefinitionByKeyAndTenantIdResult);
  }

  /**
   * Test {@link ProcessDefinitionEntityManagerImpl#deleteProcessDefinitionsByDeploymentId(String)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#deleteProcessDefinitionsByDeploymentId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionEntityManagerImpl.deleteProcessDefinitionsByDeploymentId(String)"
  })
  public void testDeleteProcessDefinitionsByDeploymentId() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    doNothing()
        .when(processDefinitionDataManager)
        .deleteProcessDefinitionsByDeploymentId(Mockito.<String>any());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    processDefinitionEntityManagerImpl.deleteProcessDefinitionsByDeploymentId("42");

    // Assert
    verify(processDefinitionDataManager).deleteProcessDefinitionsByDeploymentId("42");
  }

  /**
   * Test {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionsByQueryCriteria(ProcessDefinitionQueryImpl,
   * Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionsByQueryCriteria(ProcessDefinitionQueryImpl,
   * Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ProcessDefinitionEntityManagerImpl.findProcessDefinitionsByQueryCriteria(ProcessDefinitionQueryImpl, Page)"
  })
  public void testFindProcessDefinitionsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionsByQueryCriteria(
            Mockito.<ProcessDefinitionQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);
    ProcessDefinitionQueryImpl processDefinitionQuery = new ProcessDefinitionQueryImpl();

    // Act
    List<ProcessDefinition> actualFindProcessDefinitionsByQueryCriteriaResult =
        processDefinitionEntityManagerImpl.findProcessDefinitionsByQueryCriteria(
            processDefinitionQuery, new Page(1, 3));

    // Assert
    verify(processDefinitionDataManager)
        .findProcessDefinitionsByQueryCriteria(
            isA(ProcessDefinitionQueryImpl.class), isA(Page.class));
    assertTrue(actualFindProcessDefinitionsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionCountByQueryCriteria(ProcessDefinitionQueryImpl)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionCountByQueryCriteria(ProcessDefinitionQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long ProcessDefinitionEntityManagerImpl.findProcessDefinitionCountByQueryCriteria(ProcessDefinitionQueryImpl)"
  })
  public void testFindProcessDefinitionCountByQueryCriteria_thenReturnThree() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionCountByQueryCriteria(
            Mockito.<ProcessDefinitionQueryImpl>any()))
        .thenReturn(3L);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    long actualFindProcessDefinitionCountByQueryCriteriaResult =
        processDefinitionEntityManagerImpl.findProcessDefinitionCountByQueryCriteria(
            new ProcessDefinitionQueryImpl());

    // Assert
    verify(processDefinitionDataManager)
        .findProcessDefinitionCountByQueryCriteria(isA(ProcessDefinitionQueryImpl.class));
    assertEquals(3L, actualFindProcessDefinitionCountByQueryCriteriaResult);
  }

  /**
   * Test {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionByDeploymentAndKey(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionByDeploymentAndKey(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionEntity ProcessDefinitionEntityManagerImpl.findProcessDefinitionByDeploymentAndKey(String, String)"
  })
  public void testFindProcessDefinitionByDeploymentAndKey() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findProcessDefinitionByDeploymentAndKey(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    ProcessDefinitionEntity actualFindProcessDefinitionByDeploymentAndKeyResult =
        processDefinitionEntityManagerImpl.findProcessDefinitionByDeploymentAndKey(
            "42", "Process Definition Key");

    // Assert
    verify(processDefinitionDataManager)
        .findProcessDefinitionByDeploymentAndKey("42", "Process Definition Key");
    assertSame(processDefinitionEntityImpl, actualFindProcessDefinitionByDeploymentAndKeyResult);
  }

  /**
   * Test {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionByDeploymentAndKeyAndTenantId(String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionByDeploymentAndKeyAndTenantId(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionEntity ProcessDefinitionEntityManagerImpl.findProcessDefinitionByDeploymentAndKeyAndTenantId(String, String, String)"
  })
  public void testFindProcessDefinitionByDeploymentAndKeyAndTenantId() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findProcessDefinitionByDeploymentAndKeyAndTenantId(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    ProcessDefinitionEntity actualFindProcessDefinitionByDeploymentAndKeyAndTenantIdResult =
        processDefinitionEntityManagerImpl.findProcessDefinitionByDeploymentAndKeyAndTenantId(
            "42", "Process Definition Key", "42");

    // Assert
    verify(processDefinitionDataManager)
        .findProcessDefinitionByDeploymentAndKeyAndTenantId("42", "Process Definition Key", "42");
    assertSame(
        processDefinitionEntityImpl,
        actualFindProcessDefinitionByDeploymentAndKeyAndTenantIdResult);
  }

  /**
   * Test {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String,
   * Integer, String)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String,
   * Integer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionEntityManagerImpl.findProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)"
  })
  public void testFindProcessDefinitionByKeyAndVersionAndTenantId() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findProcessDefinitionByKeyAndVersionAndTenantId(
            Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    ProcessDefinition actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult =
        processDefinitionEntityManagerImpl.findProcessDefinitionByKeyAndVersionAndTenantId(
            "Process Definition Key", 1, "42");

    // Assert
    verify(processDefinitionDataManager)
        .findProcessDefinitionByKeyAndVersionAndTenantId("Process Definition Key", 1, "42");
    assertSame(
        processDefinitionEntityImpl, actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult);
  }

  /**
   * Test {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String,
   * Integer, String)}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String,
   * Integer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionEntityManagerImpl.findProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)"
  })
  public void testFindProcessDefinitionByKeyAndVersionAndTenantId_whenEmptyString() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findProcessDefinitionByKeyAndVersion(
            Mockito.<String>any(), Mockito.<Integer>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    ProcessDefinition actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult =
        processDefinitionEntityManagerImpl.findProcessDefinitionByKeyAndVersionAndTenantId(
            "Process Definition Key", 1, "");

    // Assert
    verify(processDefinitionDataManager)
        .findProcessDefinitionByKeyAndVersion("Process Definition Key", 1);
    assertSame(
        processDefinitionEntityImpl, actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult);
  }

  /**
   * Test {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String,
   * Integer, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionByKeyAndVersionAndTenantId(String,
   * Integer, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionEntityManagerImpl.findProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)"
  })
  public void testFindProcessDefinitionByKeyAndVersionAndTenantId_whenNull() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findProcessDefinitionByKeyAndVersion(
            Mockito.<String>any(), Mockito.<Integer>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    ProcessDefinition actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult =
        processDefinitionEntityManagerImpl.findProcessDefinitionByKeyAndVersionAndTenantId(
            "Process Definition Key", 1, null);

    // Assert
    verify(processDefinitionDataManager)
        .findProcessDefinitionByKeyAndVersion("Process Definition Key", 1);
    assertSame(
        processDefinitionEntityImpl, actualFindProcessDefinitionByKeyAndVersionAndTenantIdResult);
  }

  /**
   * Test {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionsByNativeQuery(Map, int,
   * int)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionsByNativeQuery(Map, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ProcessDefinitionEntityManagerImpl.findProcessDefinitionsByNativeQuery(Map, int, int)"
  })
  public void testFindProcessDefinitionsByNativeQuery_thenReturnEmpty() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionsByNativeQuery(
            Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    List<ProcessDefinition> actualFindProcessDefinitionsByNativeQueryResult =
        processDefinitionEntityManagerImpl.findProcessDefinitionsByNativeQuery(
            new HashMap<>(), 1, 3);

    // Assert
    verify(processDefinitionDataManager)
        .findProcessDefinitionsByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindProcessDefinitionsByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link ProcessDefinitionEntityManagerImpl#findProcessDefinitionCountByNativeQuery(Map)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#findProcessDefinitionCountByNativeQuery(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long ProcessDefinitionEntityManagerImpl.findProcessDefinitionCountByNativeQuery(Map)"
  })
  public void testFindProcessDefinitionCountByNativeQuery_thenReturnThree() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionCountByNativeQuery(
            Mockito.<Map<String, Object>>any()))
        .thenReturn(3L);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    long actualFindProcessDefinitionCountByNativeQueryResult =
        processDefinitionEntityManagerImpl.findProcessDefinitionCountByNativeQuery(new HashMap<>());

    // Assert
    verify(processDefinitionDataManager).findProcessDefinitionCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindProcessDefinitionCountByNativeQueryResult);
  }

  /**
   * Test {@link
   * ProcessDefinitionEntityManagerImpl#updateProcessDefinitionTenantIdForDeployment(String,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionEntityManagerImpl#updateProcessDefinitionTenantIdForDeployment(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionEntityManagerImpl.updateProcessDefinitionTenantIdForDeployment(String, String)"
  })
  public void testUpdateProcessDefinitionTenantIdForDeployment() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    doNothing()
        .when(processDefinitionDataManager)
        .updateProcessDefinitionTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    // Act
    processDefinitionEntityManagerImpl.updateProcessDefinitionTenantIdForDeployment("42", "42");

    // Assert
    verify(processDefinitionDataManager).updateProcessDefinitionTenantIdForDeployment("42", "42");
  }
}
