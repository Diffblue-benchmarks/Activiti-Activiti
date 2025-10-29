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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.ValuedDataObject;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.runtime.ProcessInstanceBuilderImpl;
import org.activiti.engine.impl.util.ProcessInstanceHelper;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.mockito.Mockito;

public class StartProcessInstanceCmdDiffblueTest {
  /**
   * Method under test:
   * {@link StartProcessInstanceCmd#StartProcessInstanceCmd(String, String, String, Map)}
   */
  @Test
  public void testNewStartProcessInstanceCmd() {
    // Arrange and Act
    StartProcessInstanceCmd<Object> actualStartProcessInstanceCmd = new StartProcessInstanceCmd<>(
        "Process Definition Key", "42", "Business Key", new HashMap<>());

    // Assert
    assertTrue(actualStartProcessInstanceCmd.variables.isEmpty());
  }

  /**
   * Method under test:
   * {@link StartProcessInstanceCmd#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects() {
    // Arrange
    StartProcessInstanceCmd<Object> startProcessInstanceCmd = new StartProcessInstanceCmd<>(
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));

    // Act and Assert
    assertTrue(startProcessInstanceCmd.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link StartProcessInstanceCmd#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects2() {
    // Arrange
    StartProcessInstanceCmd<Object> startProcessInstanceCmd = new StartProcessInstanceCmd<>(
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));

    // Act and Assert
    assertTrue(startProcessInstanceCmd.processDataObjects(null).isEmpty());
  }

  /**
   * Method under test:
   * {@link StartProcessInstanceCmd#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects3() {
    // Arrange
    StartProcessInstanceCmd<Object> startProcessInstanceCmd = new StartProcessInstanceCmd<>(
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));

    LinkedHashSet<ValuedDataObject> dataObjects = new LinkedHashSet<>();
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult = startProcessInstanceCmd.processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Method under test:
   * {@link StartProcessInstanceCmd#StartProcessInstanceCmd(String, String, String, Map, String)}
   */
  @Test
  public void testNewStartProcessInstanceCmd2() {
    // Arrange and Act
    StartProcessInstanceCmd<Object> actualStartProcessInstanceCmd = new StartProcessInstanceCmd<>(
        "Process Definition Key", "42", "Business Key", new HashMap<>(), "42");

    // Assert
    assertTrue(actualStartProcessInstanceCmd.variables.isEmpty());
  }

  /**
   * Method under test:
   * {@link StartProcessInstanceCmd#StartProcessInstanceCmd(ProcessInstanceBuilderImpl)}
   */
  @Test
  public void testNewStartProcessInstanceCmd3() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilder = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilder.processDefinitionId("42");

    // Act
    StartProcessInstanceCmd<Object> actualStartProcessInstanceCmd = new StartProcessInstanceCmd<>(
        processInstanceBuilder);
    DeploymentManager deploymentManager = mock(DeploymentManager.class);
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    ProcessInstanceHelper processInstanceHelper = mock(ProcessInstanceHelper.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(processInstanceHelper.createAndStartProcessInstance(Mockito.<ProcessDefinition>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(jtaProcessEngineConfiguration.getProcessInstanceHelper()).thenReturn(processInstanceHelper);
    when(jtaProcessEngineConfiguration.getDeploymentManager()).thenReturn(deploymentManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);
    ProcessInstance actualExecuteResult = actualStartProcessInstanceCmd.execute(commandContext);

    // Assert
    verify(jtaProcessEngineConfiguration).getDeploymentManager();
    verify(jtaProcessEngineConfiguration).getProcessInstanceHelper();
    verify(commandContext, atLeast(1)).getProcessEngineConfiguration();
    verify(deploymentManager).findDeployedProcessDefinitionById(eq("42"));
    verify(processInstanceHelper).createAndStartProcessInstance(isA(ProcessDefinition.class), isNull(), isNull(),
        isNull(), isNull());
    assertEquals("42", actualStartProcessInstanceCmd.processDefinitionId);
    assertNull(actualStartProcessInstanceCmd.businessKey);
    assertNull(actualStartProcessInstanceCmd.processDefinitionKey);
    assertNull(actualStartProcessInstanceCmd.processInstanceName);
    assertNull(actualStartProcessInstanceCmd.tenantId);
    assertNull(actualStartProcessInstanceCmd.transientVariables);
    assertNull(actualStartProcessInstanceCmd.variables);
    assertSame(createWithEmptyRelationshipCollectionsResult, actualExecuteResult);
  }

  /**
   * Method under test:
   * {@link StartProcessInstanceCmd#StartProcessInstanceCmd(ProcessInstanceBuilderImpl)}
   */
  @Test
  public void testNewStartProcessInstanceCmd4() {
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
    StartProcessInstanceCmd<Object> actualStartProcessInstanceCmd = new StartProcessInstanceCmd<>(
        processInstanceBuilder);
    DeploymentManager deploymentManager = mock(DeploymentManager.class);
    when(deploymentManager.findDeployedLatestProcessDefinitionByKeyAndTenantId(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ProcessDefinitionEntityImpl());
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any())).thenReturn(null);
    ProcessInstanceHelper processInstanceHelper = mock(ProcessInstanceHelper.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(processInstanceHelper.createAndStartProcessInstance(Mockito.<ProcessDefinition>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(jtaProcessEngineConfiguration.getProcessInstanceHelper()).thenReturn(processInstanceHelper);
    when(jtaProcessEngineConfiguration.getDeploymentManager()).thenReturn(deploymentManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);
    ProcessInstance actualExecuteResult = actualStartProcessInstanceCmd.execute(commandContext);

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
    verify(processInstanceHelper).createAndStartProcessInstance(isA(ProcessDefinition.class), eq("Business Key"),
        eq("Process Instance Name"), isA(Map.class), isA(Map.class));
    assertEquals("42", actualStartProcessInstanceCmd.processDefinitionId);
    assertEquals("42", actualStartProcessInstanceCmd.tenantId);
    assertEquals("Business Key", actualStartProcessInstanceCmd.businessKey);
    assertEquals("Process Definition Key", actualStartProcessInstanceCmd.processDefinitionKey);
    assertEquals("Process Instance Name", actualStartProcessInstanceCmd.processInstanceName);
    assertTrue(actualStartProcessInstanceCmd.transientVariables.isEmpty());
    assertTrue(actualStartProcessInstanceCmd.variables.isEmpty());
    assertSame(createWithEmptyRelationshipCollectionsResult, actualExecuteResult);
  }

  /**
   * Method under test:
   * {@link StartProcessInstanceCmd#StartProcessInstanceCmd(ProcessInstanceBuilderImpl)}
   */
  @Test
  public void testNewStartProcessInstanceCmd5() {
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
    StartProcessInstanceCmd<Object> actualStartProcessInstanceCmd = new StartProcessInstanceCmd<>(
        processInstanceBuilder);
    DeploymentManager deploymentManager = mock(DeploymentManager.class);
    when(deploymentManager.findDeployedLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    when(deploymentManager.findDeployedProcessDefinitionById(Mockito.<String>any())).thenReturn(null);
    ProcessInstanceHelper processInstanceHelper = mock(ProcessInstanceHelper.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(processInstanceHelper.createAndStartProcessInstance(Mockito.<ProcessDefinition>any(), Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(jtaProcessEngineConfiguration.getProcessInstanceHelper()).thenReturn(processInstanceHelper);
    when(jtaProcessEngineConfiguration.getDeploymentManager()).thenReturn(deploymentManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);
    ProcessInstance actualExecuteResult = actualStartProcessInstanceCmd.execute(commandContext);

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
    verify(processInstanceHelper).createAndStartProcessInstance(isA(ProcessDefinition.class), eq("Business Key"),
        eq("Process Instance Name"), isA(Map.class), isA(Map.class));
    assertEquals("", actualStartProcessInstanceCmd.tenantId);
    assertEquals("42", actualStartProcessInstanceCmd.processDefinitionId);
    assertEquals("Business Key", actualStartProcessInstanceCmd.businessKey);
    assertEquals("Process Definition Key", actualStartProcessInstanceCmd.processDefinitionKey);
    assertEquals("Process Instance Name", actualStartProcessInstanceCmd.processInstanceName);
    assertTrue(actualStartProcessInstanceCmd.transientVariables.isEmpty());
    assertTrue(actualStartProcessInstanceCmd.variables.isEmpty());
    assertSame(createWithEmptyRelationshipCollectionsResult, actualExecuteResult);
  }
}
