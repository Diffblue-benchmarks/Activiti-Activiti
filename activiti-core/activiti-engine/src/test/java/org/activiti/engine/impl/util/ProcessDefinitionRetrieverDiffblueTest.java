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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessDefinitionRetrieverDiffblueTest {
  @Mock
  private DeploymentManager deploymentManager;

  @InjectMocks
  private ProcessDefinitionRetriever processDefinitionRetriever;

  @InjectMocks
  private String string;

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  public void testGetProcessDefinition() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinition actualProcessDefinition = processDefinitionRetriever.getProcessDefinition("42",
        "Process Definition Key");

    // Assert
    verify(deploymentManager).findDeployedProcessDefinitionById(eq("42"));
    assertSame(processDefinitionEntityImpl, actualProcessDefinition);
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  public void testGetProcessDefinition2() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(deploymentManager.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any())).thenReturn(null);

    // Act
    ProcessDefinition actualProcessDefinition = processDefinitionRetriever.getProcessDefinition("42",
        "Process Definition Key");

    // Assert
    verify(deploymentManager).findDeployedLatestProcessDefinitionByKey(eq("Process Definition Key"));
    verify(deploymentManager).findDeployedProcessDefinitionById(eq("42"));
    assertSame(processDefinitionEntityImpl, actualProcessDefinition);
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  public void testGetProcessDefinition3() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(deploymentManager.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);

    // Act
    ProcessDefinition actualProcessDefinition = processDefinitionRetriever.getProcessDefinition(null,
        "Process Definition Key");

    // Assert
    verify(deploymentManager).findDeployedLatestProcessDefinitionByKey(eq("Process Definition Key"));
    assertSame(processDefinitionEntityImpl, actualProcessDefinition);
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  public void testGetProcessDefinition4() {
    // Arrange
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processDefinitionRetriever.getProcessDefinition("42", "Process Definition Key"));
    verify(deploymentManager).findDeployedProcessDefinitionById(eq("42"));
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  public void testGetProcessDefinition5() {
    // Arrange
    when(deploymentManager.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processDefinitionRetriever.getProcessDefinition(null, "Process Definition Key"));
    verify(deploymentManager).findDeployedLatestProcessDefinitionByKey(eq("Process Definition Key"));
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  public void testGetProcessDefinition6() {
    // Arrange
    when(deploymentManager.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any())).thenReturn(null);
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processDefinitionRetriever.getProcessDefinition("42", "Process Definition Key"));
    verify(deploymentManager).findDeployedLatestProcessDefinitionByKey(eq("Process Definition Key"));
    verify(deploymentManager).findDeployedProcessDefinitionById(eq("42"));
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  public void testGetProcessDefinition7() {
    // Arrange
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processDefinitionRetriever.getProcessDefinition("42", null));
    verify(deploymentManager).findDeployedProcessDefinitionById(eq("42"));
  }

  /**
   * Test {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessDefinitionRetriever#getProcessDefinition(String, String)}
   */
  @Test
  public void testGetProcessDefinition_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionRetriever.getProcessDefinition(null, null));
  }
}
