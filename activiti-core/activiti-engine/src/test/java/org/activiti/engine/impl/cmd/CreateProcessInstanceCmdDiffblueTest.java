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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.runtime.ProcessInstanceBuilderImpl;
import org.activiti.engine.impl.util.ProcessInstanceHelper;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.mockito.Mockito;

public class CreateProcessInstanceCmdDiffblueTest {
  /**
   * Test
   * {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(String, String, String, Map)}.
   * <p>
   * Method under test:
   * {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(String, String, String, Map)}
   */
  @Test
  public void testNewCreateProcessInstanceCmd() {
    // Arrange, Act and Assert
    assertTrue((new CreateProcessInstanceCmd("Process Definition Key", "42", "Business Key", new HashMap<>())).variables
        .isEmpty());
    assertTrue(
        (new CreateProcessInstanceCmd("Process Definition Key", "42", "Business Key", new HashMap<>(), "42")).variables
            .isEmpty());
  }

  /**
   * Test
   * {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(ProcessInstanceBuilderImpl)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>Then return {@link CreateProcessInstanceCmd#tenantId} is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(ProcessInstanceBuilderImpl)}
   */
  @Test
  public void testNewCreateProcessInstanceCmd_givenEmptyString_thenReturnTenantIdIsEmptyString() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilder = mock(ProcessInstanceBuilderImpl.class);
    when(processInstanceBuilder.getBusinessKey()).thenReturn("Business Key");
    when(processInstanceBuilder.getProcessDefinitionId()).thenReturn("42");
    when(processInstanceBuilder.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(processInstanceBuilder.getProcessInstanceName()).thenReturn("Process Instance Name");
    when(processInstanceBuilder.getTenantId()).thenReturn("");
    when(processInstanceBuilder.getTransientVariables()).thenReturn(new HashMap<>());
    when(processInstanceBuilder.getVariables()).thenReturn(new HashMap<>());
    when(processInstanceBuilder.processDefinitionId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));
    processInstanceBuilder.processDefinitionId("42");

    // Act
    CreateProcessInstanceCmd actualCreateProcessInstanceCmd = new CreateProcessInstanceCmd(processInstanceBuilder);
    DeploymentManager deploymentManager = mock(DeploymentManager.class);
    when(deploymentManager.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any())).thenReturn(null);
    ProcessInstanceHelper processInstanceHelper = mock(ProcessInstanceHelper.class);
    when(processInstanceHelper.createProcessInstance(Mockito.<ProcessDefinition>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(jtaProcessEngineConfiguration.getProcessInstanceHelper()).thenReturn(processInstanceHelper);
    when(jtaProcessEngineConfiguration.getDeploymentManager()).thenReturn(deploymentManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);
    actualCreateProcessInstanceCmd.execute(commandContext);

    // Assert
    verify(jtaProcessEngineConfiguration).getDeploymentManager();
    verify(jtaProcessEngineConfiguration).getProcessInstanceHelper();
    verify(commandContext, atLeast(1)).getProcessEngineConfiguration();
    verify(deploymentManager).findDeployedLatestProcessDefinitionByKey(eq("Process Definition Key"));
    verify(deploymentManager).findDeployedProcessDefinitionById(eq("42"));
    verify(processInstanceBuilder).getBusinessKey();
    verify(processInstanceBuilder).getProcessDefinitionId();
    verify(processInstanceBuilder).getProcessDefinitionKey();
    verify(processInstanceBuilder).getProcessInstanceName();
    verify(processInstanceBuilder).getTenantId();
    verify(processInstanceBuilder).getTransientVariables();
    verify(processInstanceBuilder).getVariables();
    verify(processInstanceBuilder).processDefinitionId(eq("42"));
    verify(processInstanceHelper).createProcessInstance(isA(ProcessDefinition.class), eq("Business Key"),
        eq("Process Instance Name"), isA(Map.class), isA(Map.class));
    assertEquals("", actualCreateProcessInstanceCmd.tenantId);
    assertEquals("Business Key", actualCreateProcessInstanceCmd.businessKey);
    assertEquals("Process Definition Key", actualCreateProcessInstanceCmd.processDefinitionKey);
    assertEquals("Process Instance Name", actualCreateProcessInstanceCmd.processInstanceName);
    assertTrue(actualCreateProcessInstanceCmd.transientVariables.isEmpty());
    assertTrue(actualCreateProcessInstanceCmd.variables.isEmpty());
  }

  /**
   * Test
   * {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(ProcessInstanceBuilderImpl)}.
   * <ul>
   *   <li>Then return {@link CreateProcessInstanceCmd#businessKey} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(ProcessInstanceBuilderImpl)}
   */
  @Test
  public void testNewCreateProcessInstanceCmd_thenReturnBusinessKeyIsNull() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilder = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilder.processDefinitionId("42");

    // Act
    CreateProcessInstanceCmd actualCreateProcessInstanceCmd = new CreateProcessInstanceCmd(processInstanceBuilder);
    DeploymentManager deploymentManager = mock(DeploymentManager.class);
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    ProcessInstanceHelper processInstanceHelper = mock(ProcessInstanceHelper.class);
    when(processInstanceHelper.createProcessInstance(Mockito.<ProcessDefinition>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(jtaProcessEngineConfiguration.getProcessInstanceHelper()).thenReturn(processInstanceHelper);
    when(jtaProcessEngineConfiguration.getDeploymentManager()).thenReturn(deploymentManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);
    actualCreateProcessInstanceCmd.execute(commandContext);

    // Assert
    verify(jtaProcessEngineConfiguration).getDeploymentManager();
    verify(jtaProcessEngineConfiguration).getProcessInstanceHelper();
    verify(commandContext, atLeast(1)).getProcessEngineConfiguration();
    verify(deploymentManager).findDeployedProcessDefinitionById(eq("42"));
    verify(processInstanceHelper).createProcessInstance(isA(ProcessDefinition.class), isNull(), isNull(), isNull(),
        isNull());
    assertNull(actualCreateProcessInstanceCmd.businessKey);
    assertNull(actualCreateProcessInstanceCmd.processDefinitionKey);
    assertNull(actualCreateProcessInstanceCmd.processInstanceName);
    assertNull(actualCreateProcessInstanceCmd.tenantId);
    assertNull(actualCreateProcessInstanceCmd.transientVariables);
    assertNull(actualCreateProcessInstanceCmd.variables);
  }

  /**
   * Test
   * {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(ProcessInstanceBuilderImpl)}.
   * <ul>
   *   <li>Then return {@link CreateProcessInstanceCmd#tenantId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(ProcessInstanceBuilderImpl)}
   */
  @Test
  public void testNewCreateProcessInstanceCmd_thenReturnTenantIdIs42() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilder = mock(ProcessInstanceBuilderImpl.class);
    when(processInstanceBuilder.getBusinessKey()).thenReturn("Business Key");
    when(processInstanceBuilder.getProcessDefinitionId()).thenReturn("42");
    when(processInstanceBuilder.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(processInstanceBuilder.getProcessInstanceName()).thenReturn("Process Instance Name");
    when(processInstanceBuilder.getTenantId()).thenReturn("42");
    when(processInstanceBuilder.getTransientVariables()).thenReturn(new HashMap<>());
    when(processInstanceBuilder.getVariables()).thenReturn(new HashMap<>());
    when(processInstanceBuilder.processDefinitionId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));
    processInstanceBuilder.processDefinitionId("42");

    // Act
    CreateProcessInstanceCmd actualCreateProcessInstanceCmd = new CreateProcessInstanceCmd(processInstanceBuilder);
    DeploymentManager deploymentManager = mock(DeploymentManager.class);
    when(deploymentManager.findDeployedLatestProcessDefinitionByKeyAndTenantId(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ProcessDefinitionEntityImpl());
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any())).thenReturn(null);
    ProcessInstanceHelper processInstanceHelper = mock(ProcessInstanceHelper.class);
    when(processInstanceHelper.createProcessInstance(Mockito.<ProcessDefinition>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(jtaProcessEngineConfiguration.getProcessInstanceHelper()).thenReturn(processInstanceHelper);
    when(jtaProcessEngineConfiguration.getDeploymentManager()).thenReturn(deploymentManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);
    actualCreateProcessInstanceCmd.execute(commandContext);

    // Assert
    verify(jtaProcessEngineConfiguration).getDeploymentManager();
    verify(jtaProcessEngineConfiguration).getProcessInstanceHelper();
    verify(commandContext, atLeast(1)).getProcessEngineConfiguration();
    verify(deploymentManager).findDeployedLatestProcessDefinitionByKeyAndTenantId(eq("Process Definition Key"),
        eq("42"));
    verify(deploymentManager).findDeployedProcessDefinitionById(eq("42"));
    verify(processInstanceBuilder).getBusinessKey();
    verify(processInstanceBuilder).getProcessDefinitionId();
    verify(processInstanceBuilder).getProcessDefinitionKey();
    verify(processInstanceBuilder).getProcessInstanceName();
    verify(processInstanceBuilder).getTenantId();
    verify(processInstanceBuilder).getTransientVariables();
    verify(processInstanceBuilder).getVariables();
    verify(processInstanceBuilder).processDefinitionId(eq("42"));
    verify(processInstanceHelper).createProcessInstance(isA(ProcessDefinition.class), eq("Business Key"),
        eq("Process Instance Name"), isA(Map.class), isA(Map.class));
    assertEquals("42", actualCreateProcessInstanceCmd.tenantId);
    assertEquals("Business Key", actualCreateProcessInstanceCmd.businessKey);
    assertEquals("Process Definition Key", actualCreateProcessInstanceCmd.processDefinitionKey);
    assertEquals("Process Instance Name", actualCreateProcessInstanceCmd.processInstanceName);
    assertTrue(actualCreateProcessInstanceCmd.transientVariables.isEmpty());
    assertTrue(actualCreateProcessInstanceCmd.variables.isEmpty());
  }
}
