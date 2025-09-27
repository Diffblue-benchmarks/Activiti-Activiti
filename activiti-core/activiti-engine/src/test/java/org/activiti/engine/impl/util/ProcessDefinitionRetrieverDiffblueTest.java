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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionDataManager;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessDefinitionRetrieverDiffblueTest {
  @Mock private DeploymentManager deploymentManager;

  @InjectMocks private ProcessDefinitionRetriever processDefinitionRetriever;

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinition actualProcessDefinition =
        processDefinitionRetriever.getProcessDefinition("42", "Process Definition Key");

    // Assert
    verify(deploymentManager).findDeployedProcessDefinitionById("42");
    assertSame(processDefinitionEntityImpl, actualProcessDefinition);
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition2() {
    // Arrange
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> processDefinitionRetriever.getProcessDefinition("42", "Process Definition Key"));
    verify(deploymentManager).findDeployedProcessDefinitionById("42");
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition3() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(deploymentManager.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any()))
        .thenReturn(null);

    // Act
    ProcessDefinition actualProcessDefinition =
        processDefinitionRetriever.getProcessDefinition("42", "Process Definition Key");

    // Assert
    verify(deploymentManager).findDeployedLatestProcessDefinitionByKey("Process Definition Key");
    verify(deploymentManager).findDeployedProcessDefinitionById("42");
    assertSame(processDefinitionEntityImpl, actualProcessDefinition);
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition4() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(deploymentManager.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinition actualProcessDefinition =
        processDefinitionRetriever.getProcessDefinition(null, "Process Definition Key");

    // Assert
    verify(deploymentManager).findDeployedLatestProcessDefinitionByKey("Process Definition Key");
    assertSame(processDefinitionEntityImpl, actualProcessDefinition);
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition5() {
    // Arrange
    when(deploymentManager.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> processDefinitionRetriever.getProcessDefinition(null, "Process Definition Key"));
    verify(deploymentManager).findDeployedLatestProcessDefinitionByKey("Process Definition Key");
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition6() {
    // Arrange
    when(deploymentManager.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(null);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processDefinitionRetriever.getProcessDefinition(null, "Process Definition Key"));
    verify(deploymentManager).findDeployedLatestProcessDefinitionByKey("Process Definition Key");
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition7() {
    // Arrange
    DeploymentManager deploymentCache = mock(DeploymentManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(deploymentCache.findDeployedLatestProcessDefinitionByKeyAndTenantId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    when(deploymentCache.findDeployedProcessDefinitionById(Mockito.<String>any())).thenReturn(null);
    ProcessDefinitionRetriever processDefinitionRetriever =
        new ProcessDefinitionRetriever("42", deploymentCache);

    // Act
    ProcessDefinition actualProcessDefinition =
        processDefinitionRetriever.getProcessDefinition("42", "Process Definition Key");

    // Assert
    verify(deploymentCache)
        .findDeployedLatestProcessDefinitionByKeyAndTenantId("Process Definition Key", "42");
    verify(deploymentCache).findDeployedProcessDefinitionById("42");
    assertSame(processDefinitionEntityImpl, actualProcessDefinition);
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition8() {
    // Arrange
    DeploymentManager deploymentCache = mock(DeploymentManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(deploymentCache.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    when(deploymentCache.findDeployedProcessDefinitionById(Mockito.<String>any())).thenReturn(null);
    ProcessDefinitionRetriever processDefinitionRetriever =
        new ProcessDefinitionRetriever("", deploymentCache);

    // Act
    ProcessDefinition actualProcessDefinition =
        processDefinitionRetriever.getProcessDefinition("42", "Process Definition Key");

    // Assert
    verify(deploymentCache).findDeployedLatestProcessDefinitionByKey("Process Definition Key");
    verify(deploymentCache).findDeployedProcessDefinitionById("42");
    assertSame(processDefinitionEntityImpl, actualProcessDefinition);
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Deployer}.
   *   <li>When {@code null}.
   *   <li>Then calls {@link ProcessDefinitionEntityImpl#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition_givenArrayListAddDeployer_whenNull_thenCallsGetId() {
    // Arrange
    ArrayList<Deployer> deployers = new ArrayList<>();
    deployers.add(mock(Deployer.class));

    ProcessDefinitionEntityImpl processDefinitionEntityImpl =
        mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    DeploymentManager deploymentCache = new DeploymentManager();
    deploymentCache.setProcessDefinitionEntityManager(processDefinitionEntityManager);
    deploymentCache.setDeployers(deployers);
    ProcessDefinitionRetriever processDefinitionRetriever =
        new ProcessDefinitionRetriever(null, deploymentCache);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> processDefinitionRetriever.getProcessDefinition(null, "Process Definition Key"));
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey("Process Definition Key");
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any()))
        .thenReturn(null);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processDefinitionRetriever.getProcessDefinition("42", null));
    verify(deploymentManager).findDeployedProcessDefinitionById("42");
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition ProcessDefinitionRetriever.getProcessDefinition(String, String)"
  })
  public void testGetProcessDefinition_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> processDefinitionRetriever.getProcessDefinition(null, null));
  }
}
