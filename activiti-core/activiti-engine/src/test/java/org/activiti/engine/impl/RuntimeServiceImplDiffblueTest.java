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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.runtime.ProcessInstanceBuilderImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.DataObject;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.NativeExecutionQuery;
import org.activiti.engine.runtime.NativeProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class RuntimeServiceImplDiffblueTest {
  /**
   * Test {@link RuntimeServiceImpl#deleteProcessInstance(String, String)}.
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RuntimeServiceImpl#deleteProcessInstance(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RuntimeServiceImpl.deleteProcessInstance(String, String)"})
  public void testDeleteProcessInstance_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.deleteProcessInstance("42", "Just cause");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#createExecutionQuery()}.
   * <p>
   * Method under test: {@link RuntimeServiceImpl#createExecutionQuery()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionQuery RuntimeServiceImpl.createExecutionQuery()"})
  public void testCreateExecutionQuery() {
    // Arrange and Act
    ExecutionQuery actualCreateExecutionQueryResult = (new RuntimeServiceImpl()).createExecutionQuery();

    // Assert
    assertTrue(actualCreateExecutionQueryResult instanceof ExecutionQueryImpl);
    assertEquals("RES.ID_ asc", ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getOrderByColumns());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessDefinitionVersion());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getParameter());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getDatabaseType());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getActivityId());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getBusinessKey());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getExecutionId());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getInvolvedUser());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getName());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getNameLike());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getNameLikeIgnoreCase());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getParentId());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessDefinitionCategory());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessDefinitionId());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessDefinitionKey());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessDefinitionName());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessInstanceId());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessInstanceIds());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getRootProcessInstanceId());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getStartedBy());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getSubProcessInstanceId());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getSuperProcessInstanceId());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getTenantId());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getTenantIdLike());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).orderBy);
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).deploymentId);
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).locale);
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getStartedAfter());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getStartedBefore());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getInvolvedGroups());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).deploymentIds);
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getEventSubscriptions());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessDefinitionIds());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessDefinitionKeys());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).nullHandlingOnOrder);
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).resultType);
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).commandContext);
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).commandExecutor);
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getSuspensionState());
    assertNull(((ExecutionQueryImpl) actualCreateExecutionQueryResult).orderProperty);
    assertEquals(0, ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getFirstResult());
    assertEquals(1, ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getFirstRow());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).hasLocalQueryVariableValue());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).hasNonLocalQueryVariableValue());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getOnlyProcessInstances());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isActive());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isExcludeSubprocesses());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isIncludeChildExecutionsWithBusinessKeyQuery());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isOnlyChildExecutions());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isOnlyProcessInstanceExecutions());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isOnlySubProcessExecutions());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isProcessInstancesOnly());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isWithoutTenantId());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).withLocalizationFallback);
    assertTrue(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getQueryVariableValues().isEmpty());
    assertTrue(((ExecutionQueryImpl) actualCreateExecutionQueryResult).orQueryObjects.isEmpty());
    assertEquals(Integer.MAX_VALUE, ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getMaxResults());
  }

  /**
   * Test {@link RuntimeServiceImpl#createNativeExecutionQuery()}.
   * <p>
   * Method under test: {@link RuntimeServiceImpl#createNativeExecutionQuery()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"NativeExecutionQuery RuntimeServiceImpl.createNativeExecutionQuery()"})
  public void testCreateNativeExecutionQuery() {
    // Arrange and Act
    NativeExecutionQuery actualCreateNativeExecutionQueryResult = (new RuntimeServiceImpl())
        .createNativeExecutionQuery();

    // Assert
    assertTrue(actualCreateNativeExecutionQueryResult instanceof NativeExecutionQueryImpl);
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).resultType);
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).commandContext);
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).commandExecutor);
    assertEquals(0, ((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).firstResult);
    assertTrue(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE, ((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).maxResults);
  }

  /**
   * Test {@link RuntimeServiceImpl#createNativeProcessInstanceQuery()}.
   * <p>
   * Method under test: {@link RuntimeServiceImpl#createNativeProcessInstanceQuery()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"NativeProcessInstanceQuery RuntimeServiceImpl.createNativeProcessInstanceQuery()"})
  public void testCreateNativeProcessInstanceQuery() {
    // Arrange and Act
    NativeProcessInstanceQuery actualCreateNativeProcessInstanceQueryResult = (new RuntimeServiceImpl())
        .createNativeProcessInstanceQuery();

    // Assert
    assertTrue(actualCreateNativeProcessInstanceQueryResult instanceof NativeProcessInstanceQueryImpl);
    assertNull(((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).resultType);
    assertNull(((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).commandContext);
    assertNull(((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).commandExecutor);
    assertEquals(0, ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).firstResult);
    assertTrue(
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).maxResults);
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjects(String)} with {@code executionId}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link RuntimeServiceImpl#getDataObjects(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map RuntimeServiceImpl.getDataObjects(String)"})
  public void testGetDataObjectsWithExecutionId_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, DataObject>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, DataObject> actualDataObjects = runtimeServiceImpl.getDataObjects("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualDataObjects.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObject(String, String)} with {@code executionId}, {@code dataObject}.
   * <p>
   * Method under test: {@link RuntimeServiceImpl#getDataObject(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DataObject RuntimeServiceImpl.getDataObject(String, String)"})
  public void testGetDataObjectWithExecutionIdDataObject() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    DataObjectImpl dataObjectImpl = new DataObjectImpl("Name", JSONObject.NULL,
        "The characteristics of someone or something", "Type", "Localized Name", "Localized Description",
        "Data Object Definition Key");

    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DataObject>>any())).thenReturn(dataObjectImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    DataObject actualDataObject = runtimeServiceImpl.getDataObject("42", "Data Object");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(dataObjectImpl, actualDataObject);
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObject(String, String, String, boolean)} with {@code executionId}, {@code dataObjectName}, {@code locale}, {@code withLocalizationFallback}.
   * <p>
   * Method under test: {@link RuntimeServiceImpl#getDataObject(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DataObject RuntimeServiceImpl.getDataObject(String, String, String, boolean)"})
  public void testGetDataObjectWithExecutionIdDataObjectNameLocaleWithLocalizationFallback() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    DataObjectImpl dataObjectImpl = new DataObjectImpl("Name", JSONObject.NULL,
        "The characteristics of someone or something", "Type", "Localized Name", "Localized Description",
        "Data Object Definition Key");

    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DataObject>>any())).thenReturn(dataObjectImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    DataObject actualDataObject = runtimeServiceImpl.getDataObject("42", "Data Object Name", "en", true);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(dataObjectImpl, actualDataObject);
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjectLocal(String, String)} with {@code executionId}, {@code dataObjectName}.
   * <p>
   * Method under test: {@link RuntimeServiceImpl#getDataObjectLocal(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DataObject RuntimeServiceImpl.getDataObjectLocal(String, String)"})
  public void testGetDataObjectLocalWithExecutionIdDataObjectName() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    DataObjectImpl dataObjectImpl = new DataObjectImpl("Name", JSONObject.NULL,
        "The characteristics of someone or something", "Type", "Localized Name", "Localized Description",
        "Data Object Definition Key");

    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DataObject>>any())).thenReturn(dataObjectImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    DataObject actualDataObjectLocal = runtimeServiceImpl.getDataObjectLocal("42", "Data Object Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(dataObjectImpl, actualDataObjectLocal);
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjectLocal(String, String, String, boolean)} with {@code executionId}, {@code dataObjectName}, {@code locale}, {@code withLocalizationFallback}.
   * <p>
   * Method under test: {@link RuntimeServiceImpl#getDataObjectLocal(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DataObject RuntimeServiceImpl.getDataObjectLocal(String, String, String, boolean)"})
  public void testGetDataObjectLocalWithExecutionIdDataObjectNameLocaleWithLocalizationFallback() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    DataObjectImpl dataObjectImpl = new DataObjectImpl("Name", JSONObject.NULL,
        "The characteristics of someone or something", "Type", "Localized Name", "Localized Description",
        "Data Object Definition Key");

    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DataObject>>any())).thenReturn(dataObjectImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    DataObject actualDataObjectLocal = runtimeServiceImpl.getDataObjectLocal("42", "Data Object Name", "en", true);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(dataObjectImpl, actualDataObjectLocal);
  }

  /**
   * Test {@link RuntimeServiceImpl#createProcessInstanceQuery()}.
   * <p>
   * Method under test: {@link RuntimeServiceImpl#createProcessInstanceQuery()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery RuntimeServiceImpl.createProcessInstanceQuery()"})
  public void testCreateProcessInstanceQuery() {
    // Arrange and Act
    ProcessInstanceQuery actualCreateProcessInstanceQueryResult = (new RuntimeServiceImpl())
        .createProcessInstanceQuery();

    // Assert
    assertTrue(actualCreateProcessInstanceQueryResult instanceof ProcessInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getMssqlOrDB2OrderBy());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessDefinitionVersion());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessInstanceVariablesLimit());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getParameter());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getDatabaseType());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getActivityId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getBusinessKey());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getDeploymentId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getExecutionId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getInvolvedUser());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getName());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getNameLike());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getNameLikeIgnoreCase());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getParentId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessDefinitionCategory());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessDefinitionId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessDefinitionKey());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessDefinitionName());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessInstanceId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getRootProcessInstanceId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getStartedBy());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getSubProcessInstanceId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getSuperProcessInstanceId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getTenantId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getTenantIdLike());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).orderBy);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).activityId);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).locale);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getStartedAfter());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getStartedBefore());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getDeploymentIds());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getInvolvedGroups());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getEventSubscriptions());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessDefinitionIds());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessDefinitionKeys());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessInstanceIds());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).nullHandlingOnOrder);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).resultType);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).currentOrQueryObject);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).commandContext);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).commandExecutor);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getSuspensionState());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).orderProperty);
    assertEquals(0, ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getFirstResult());
    assertEquals(1, ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getFirstRow());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).hasLocalQueryVariableValue());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).hasNonLocalQueryVariableValue());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).isExcludeSubprocesses());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
        .isIncludeChildExecutionsWithBusinessKeyQuery());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).isIncludeProcessVariables());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).isOnlyChildExecutions());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).isOnlyProcessInstanceExecutions());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).isOnlySubProcessExecutions());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).isWithoutTenantId());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).inOrStatement);
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).withJobException);
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).withLocalizationFallback);
    assertTrue(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getQueryVariableValues().isEmpty());
    assertTrue(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getOrQueryObjects().isEmpty());
    assertTrue(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getOnlyProcessInstances());
    assertEquals(Integer.MAX_VALUE, ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE,
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getMaxResults());
  }

  /**
   * Test {@link RuntimeServiceImpl#getActiveActivityIds(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link RuntimeServiceImpl#getActiveActivityIds(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List RuntimeServiceImpl.getActiveActivityIds(String)"})
  public void testGetActiveActivityIds_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<List<String>>>any()))
        .thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<String> actualActiveActivityIds = runtimeServiceImpl.getActiveActivityIds("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualActiveActivityIds.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#activateProcessInstanceById(String)}.
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RuntimeServiceImpl#activateProcessInstanceById(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RuntimeServiceImpl.activateProcessInstanceById(String)"})
  public void testActivateProcessInstanceById_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.activateProcessInstanceById("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#executeActivityInAdhocSubProcess(String, String)}.
   * <p>
   * Method under test: {@link RuntimeServiceImpl#executeActivityInAdhocSubProcess(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Execution RuntimeServiceImpl.executeActivityInAdhocSubProcess(String, String)"})
  public void testExecuteActivityInAdhocSubProcess() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Execution>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Execution actualExecuteActivityInAdhocSubProcessResult = runtimeServiceImpl.executeActivityInAdhocSubProcess("42",
        "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualExecuteActivityInAdhocSubProcessResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#completeAdhocSubProcess(String)}.
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RuntimeServiceImpl#completeAdhocSubProcess(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RuntimeServiceImpl.completeAdhocSubProcess(String)"})
  public void testCompleteAdhocSubProcess_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.completeAdhocSubProcess("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#createProcessInstanceBuilder()}.
   * <p>
   * Method under test: {@link RuntimeServiceImpl#createProcessInstanceBuilder()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceBuilder RuntimeServiceImpl.createProcessInstanceBuilder()"})
  public void testCreateProcessInstanceBuilder() {
    // Arrange and Act
    ProcessInstanceBuilder actualCreateProcessInstanceBuilderResult = (new RuntimeServiceImpl())
        .createProcessInstanceBuilder();

    // Assert
    assertTrue(actualCreateProcessInstanceBuilderResult instanceof ProcessInstanceBuilderImpl);
    assertNull(((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getBusinessKey());
    assertNull(((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getMessageName());
    assertNull(((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getProcessDefinitionId());
    assertNull(((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getProcessDefinitionKey());
    assertNull(((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getProcessInstanceName());
    assertNull(((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getTenantId());
    assertNull(((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getTransientVariables());
    assertNull(((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getVariables());
    assertFalse(((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).hasProcessDefinitionIdOrKey());
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstance(ProcessInstanceBuilderImpl)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RuntimeServiceImpl#startProcessInstance(ProcessInstanceBuilderImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.runtime.ProcessInstance RuntimeServiceImpl.startProcessInstance(ProcessInstanceBuilderImpl)"})
  public void testStartProcessInstance_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> runtimeServiceImpl.startProcessInstance(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl())));
  }

  /**
   * Test {@link RuntimeServiceImpl#createProcessInstance(ProcessInstanceBuilderImpl)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RuntimeServiceImpl#createProcessInstance(ProcessInstanceBuilderImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.runtime.ProcessInstance RuntimeServiceImpl.createProcessInstance(ProcessInstanceBuilderImpl)"})
  public void testCreateProcessInstance_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> runtimeServiceImpl.createProcessInstance(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl())));
  }

  /**
   * Test new {@link RuntimeServiceImpl} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link RuntimeServiceImpl}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RuntimeServiceImpl.<init>()"})
  public void testNewRuntimeServiceImpl() {
    // Arrange, Act and Assert
    assertNull((new RuntimeServiceImpl()).getCommandExecutor());
  }
}
