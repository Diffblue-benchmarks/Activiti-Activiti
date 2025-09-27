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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.runtime.ProcessInstanceBuilderImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.DataObject;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.NativeExecutionQuery;
import org.activiti.engine.runtime.NativeProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Event;
import org.activiti.engine.task.IdentityLink;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RuntimeServiceImplDiffblueTest {
  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByKey(String)} with {@code
   * processDefinitionKey}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance RuntimeServiceImpl.startProcessInstanceByKey(String)"})
  public void testStartProcessInstanceByKeyWithProcessDefinitionKey() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyResult =
        runtimeServiceImpl.startProcessInstanceByKey("Process Definition Key");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByKeyResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByKey(String, String)} with {@code
   * processDefinitionKey}, {@code businessKey}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByKey(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByKey(String, String)"
  })
  public void testStartProcessInstanceByKeyWithProcessDefinitionKeyBusinessKey() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyResult =
        runtimeServiceImpl.startProcessInstanceByKey("Process Definition Key", "Business Key");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByKeyResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByKey(String, String, Map)} with {@code
   * processDefinitionKey}, {@code businessKey}, {@code variables}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByKey(String, String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByKey(String, String, Map)"
  })
  public void testStartProcessInstanceByKeyWithProcessDefinitionKeyBusinessKeyVariables() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyResult =
        runtimeServiceImpl.startProcessInstanceByKey(
            "Process Definition Key", "Business Key", new HashMap<>());

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByKeyResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByKey(String, Map)} with {@code
   * processDefinitionKey}, {@code variables}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByKey(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance RuntimeServiceImpl.startProcessInstanceByKey(String, Map)"})
  public void testStartProcessInstanceByKeyWithProcessDefinitionKeyVariables() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyResult =
        runtimeServiceImpl.startProcessInstanceByKey("Process Definition Key", new HashMap<>());

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByKeyResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByKeyAndTenantId(String, String, String)}
   * with {@code processDefinitionKey}, {@code businessKey}, {@code tenantId}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByKeyAndTenantId(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByKeyAndTenantId(String, String, String)"
  })
  public void
      testStartProcessInstanceByKeyAndTenantIdWithProcessDefinitionKeyBusinessKeyTenantId() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyAndTenantIdResult =
        runtimeServiceImpl.startProcessInstanceByKeyAndTenantId(
            "Process Definition Key", "Business Key", "42");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult,
        actualStartProcessInstanceByKeyAndTenantIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByKeyAndTenantId(String, String, Map,
   * String)} with {@code processDefinitionKey}, {@code businessKey}, {@code variables}, {@code
   * tenantId}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByKeyAndTenantId(String,
   * String, Map, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByKeyAndTenantId(String, String, Map, String)"
  })
  public void
      testStartProcessInstanceByKeyAndTenantIdWithProcessDefinitionKeyBusinessKeyVariablesTenantId() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyAndTenantIdResult =
        runtimeServiceImpl.startProcessInstanceByKeyAndTenantId(
            "Process Definition Key", "Business Key", new HashMap<>(), "42");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult,
        actualStartProcessInstanceByKeyAndTenantIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByKeyAndTenantId(String, String)} with
   * {@code processDefinitionKey}, {@code tenantId}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByKeyAndTenantId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByKeyAndTenantId(String, String)"
  })
  public void testStartProcessInstanceByKeyAndTenantIdWithProcessDefinitionKeyTenantId() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyAndTenantIdResult =
        runtimeServiceImpl.startProcessInstanceByKeyAndTenantId("Process Definition Key", "42");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult,
        actualStartProcessInstanceByKeyAndTenantIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByKeyAndTenantId(String, Map, String)} with
   * {@code processDefinitionKey}, {@code variables}, {@code tenantId}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByKeyAndTenantId(String,
   * Map, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByKeyAndTenantId(String, Map, String)"
  })
  public void testStartProcessInstanceByKeyAndTenantIdWithProcessDefinitionKeyVariablesTenantId() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyAndTenantIdResult =
        runtimeServiceImpl.startProcessInstanceByKeyAndTenantId(
            "Process Definition Key", new HashMap<>(), "42");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult,
        actualStartProcessInstanceByKeyAndTenantIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceById(String)} with {@code
   * processDefinitionId}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceById(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance RuntimeServiceImpl.startProcessInstanceById(String)"})
  public void testStartProcessInstanceByIdWithProcessDefinitionId() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByIdResult =
        runtimeServiceImpl.startProcessInstanceById("42");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceById(String, String)} with {@code
   * processDefinitionId}, {@code businessKey}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceById(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance RuntimeServiceImpl.startProcessInstanceById(String, String)"})
  public void testStartProcessInstanceByIdWithProcessDefinitionIdBusinessKey() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByIdResult =
        runtimeServiceImpl.startProcessInstanceById("42", "Business Key");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceById(String, String, Map)} with {@code
   * processDefinitionId}, {@code businessKey}, {@code variables}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceById(String, String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceById(String, String, Map)"
  })
  public void testStartProcessInstanceByIdWithProcessDefinitionIdBusinessKeyVariables() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByIdResult =
        runtimeServiceImpl.startProcessInstanceById("42", "Business Key", new HashMap<>());

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceById(String, Map)} with {@code
   * processDefinitionId}, {@code variables}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceById(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance RuntimeServiceImpl.startProcessInstanceById(String, Map)"})
  public void testStartProcessInstanceByIdWithProcessDefinitionIdVariables() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByIdResult =
        runtimeServiceImpl.startProcessInstanceById("42", new HashMap<>());

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#deleteProcessInstance(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#deleteProcessInstance(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.deleteProcessInstance(String, String)"})
  public void testDeleteProcessInstance_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
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
   *
   * <p>Method under test: {@link RuntimeServiceImpl#createExecutionQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery RuntimeServiceImpl.createExecutionQuery()"})
  public void testCreateExecutionQuery() {
    // Arrange and Act
    ExecutionQuery actualCreateExecutionQueryResult =
        new RuntimeServiceImpl().createExecutionQuery();

    // Assert
    assertTrue(actualCreateExecutionQueryResult instanceof ExecutionQueryImpl);
    assertEquals(
        "RES.ID_ asc", ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getOrderBy());
    assertEquals(
        "RES.ID_ asc", ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getOrderByColumns());
    assertNull(
        ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessDefinitionVersion());
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
    assertNull(
        ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getProcessDefinitionCategory());
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
    assertFalse(
        ((ExecutionQueryImpl) actualCreateExecutionQueryResult).hasLocalQueryVariableValue());
    assertFalse(
        ((ExecutionQueryImpl) actualCreateExecutionQueryResult).hasNonLocalQueryVariableValue());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).getOnlyProcessInstances());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isActive());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isExcludeSubprocesses());
    assertFalse(
        ((ExecutionQueryImpl) actualCreateExecutionQueryResult)
            .isIncludeChildExecutionsWithBusinessKeyQuery());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isOnlyChildExecutions());
    assertFalse(
        ((ExecutionQueryImpl) actualCreateExecutionQueryResult).isOnlyProcessInstanceExecutions());
    assertFalse(
        ((ExecutionQueryImpl) actualCreateExecutionQueryResult).isOnlySubProcessExecutions());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isProcessInstancesOnly());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).isWithoutTenantId());
    assertFalse(((ExecutionQueryImpl) actualCreateExecutionQueryResult).withLocalizationFallback);
    assertTrue(
        ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getQueryVariableValues().isEmpty());
    assertTrue(((ExecutionQueryImpl) actualCreateExecutionQueryResult).orQueryObjects.isEmpty());
    assertEquals(
        Integer.MAX_VALUE, ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getLastRow());
    assertEquals(
        Integer.MAX_VALUE, ((ExecutionQueryImpl) actualCreateExecutionQueryResult).getMaxResults());
  }

  /**
   * Test {@link RuntimeServiceImpl#createNativeExecutionQuery()}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#createNativeExecutionQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"NativeExecutionQuery RuntimeServiceImpl.createNativeExecutionQuery()"})
  public void testCreateNativeExecutionQuery() {
    // Arrange and Act
    NativeExecutionQuery actualCreateNativeExecutionQueryResult =
        new RuntimeServiceImpl().createNativeExecutionQuery();

    // Assert
    assertTrue(actualCreateNativeExecutionQueryResult instanceof NativeExecutionQueryImpl);
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).resultType);
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).commandContext);
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).commandExecutor);
    assertEquals(
        0, ((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).firstResult);
    assertTrue(
        ((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult)
            .getParameters()
            .isEmpty());
    assertEquals(
        Integer.MAX_VALUE,
        ((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).maxResults);
  }

  /**
   * Test {@link RuntimeServiceImpl#createNativeProcessInstanceQuery()}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#createNativeProcessInstanceQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "NativeProcessInstanceQuery RuntimeServiceImpl.createNativeProcessInstanceQuery()"
  })
  public void testCreateNativeProcessInstanceQuery() {
    // Arrange and Act
    NativeProcessInstanceQuery actualCreateNativeProcessInstanceQueryResult =
        new RuntimeServiceImpl().createNativeProcessInstanceQuery();

    // Assert
    assertTrue(
        actualCreateNativeProcessInstanceQueryResult instanceof NativeProcessInstanceQueryImpl);
    assertNull(
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).resultType);
    assertNull(
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult)
            .commandContext);
    assertNull(
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult)
            .commandExecutor);
    assertEquals(
        0,
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult)
            .firstResult);
    assertTrue(
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult)
            .getParameters()
            .isEmpty());
    assertEquals(
        Integer.MAX_VALUE,
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).maxResults);
  }

  /**
   * Test {@link RuntimeServiceImpl#updateBusinessKey(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#updateBusinessKey(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.updateBusinessKey(String, String)"})
  public void testUpdateBusinessKey_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.updateBusinessKey("42", "Business Key");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariables(String, Collection)} with {@code executionId},
   * {@code variableNames}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariables(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getVariables(String, Collection)"})
  public void testGetVariablesWithExecutionIdVariableNames_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, Object>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, Object> actualVariables = runtimeServiceImpl.getVariables("42", new ArrayList<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualVariables.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariables(String)} with {@code executionId}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariables(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getVariables(String)"})
  public void testGetVariablesWithExecutionId_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, Object>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, Object> actualVariables = runtimeServiceImpl.getVariables("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualVariables.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariableInstances(String, Collection)} with {@code
   * executionId}, {@code variableNames}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariableInstances(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getVariableInstances(String, Collection)"})
  public void testGetVariableInstancesWithExecutionIdVariableNames_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, VariableInstance>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, VariableInstance> actualVariableInstances =
        runtimeServiceImpl.getVariableInstances("42", new ArrayList<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualVariableInstances.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariableInstances(String)} with {@code executionId}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariableInstances(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getVariableInstances(String)"})
  public void testGetVariableInstancesWithExecutionId_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, VariableInstance>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, VariableInstance> actualVariableInstances =
        runtimeServiceImpl.getVariableInstances("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualVariableInstances.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariablesLocal(String, Collection)} with {@code executionId},
   * {@code variableNames}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariablesLocal(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getVariablesLocal(String, Collection)"})
  public void testGetVariablesLocalWithExecutionIdVariableNames_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, Object>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, Object> actualVariablesLocal =
        runtimeServiceImpl.getVariablesLocal("42", new ArrayList<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualVariablesLocal.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariablesLocal(String)} with {@code executionId}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariablesLocal(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getVariablesLocal(String)"})
  public void testGetVariablesLocalWithExecutionId_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, Object>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, Object> actualVariablesLocal = runtimeServiceImpl.getVariablesLocal("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualVariablesLocal.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariableInstancesLocal(String, Collection)} with {@code
   * executionId}, {@code variableNames}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariableInstancesLocal(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getVariableInstancesLocal(String, Collection)"})
  public void testGetVariableInstancesLocalWithExecutionIdVariableNames_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, VariableInstance>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, VariableInstance> actualVariableInstancesLocal =
        runtimeServiceImpl.getVariableInstancesLocal("42", new ArrayList<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualVariableInstancesLocal.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariableInstancesLocal(String)} with {@code executionId}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariableInstancesLocal(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getVariableInstancesLocal(String)"})
  public void testGetVariableInstancesLocalWithExecutionId_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, VariableInstance>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, VariableInstance> actualVariableInstancesLocal =
        runtimeServiceImpl.getVariableInstancesLocal("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualVariableInstancesLocal.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariable(String, String, Class)} with {@code executionId},
   * {@code variableName}, {@code variableClass}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariable(String, String, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RuntimeServiceImpl.getVariable(String, String, Class)"})
  public void testGetVariableWithExecutionIdVariableNameVariableClass_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);
    Class<Object> variableClass = Object.class;

    // Act
    runtimeServiceImpl.getVariable("42", "Variable Name", variableClass);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariable(String, String)} with {@code executionId}, {@code
   * variableName}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariable(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RuntimeServiceImpl.getVariable(String, String)"})
  public void testGetVariableWithExecutionIdVariableName_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.getVariable("42", "Variable Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#hasVariable(String, String)}.
   *
   * <ul>
   *   <li>Given {@link CommandContextInterceptor} {@link
   *       CommandContextInterceptor#execute(CommandConfig, Command)} return {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#hasVariable(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RuntimeServiceImpl.hasVariable(String, String)"})
  public void testHasVariable_givenCommandContextInterceptorExecuteReturnTrue_thenReturnTrue() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Boolean>>any()))
        .thenReturn(true);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    boolean actualHasVariableResult = runtimeServiceImpl.hasVariable("42", "Variable Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualHasVariableResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#hasVariable(String, String)}.
   *
   * <ul>
   *   <li>Given {@link CommandExecutorImpl} {@link CommandExecutorImpl#execute(Command)} return
   *       {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#hasVariable(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RuntimeServiceImpl.hasVariable(String, String)"})
  public void testHasVariable_givenCommandExecutorImplExecuteReturnFalse_thenReturnFalse() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    when(commandExecutor.execute(Mockito.<Command<Boolean>>any())).thenReturn(false);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    boolean actualHasVariableResult = runtimeServiceImpl.hasVariable("42", "Variable Name");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertFalse(actualHasVariableResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariableLocal(String, String, Class)} with {@code
   * executionId}, {@code variableName}, {@code variableClass}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariableLocal(String, String, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RuntimeServiceImpl.getVariableLocal(String, String, Class)"})
  public void testGetVariableLocalWithExecutionIdVariableNameVariableClass_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);
    Class<Object> variableClass = Object.class;

    // Act
    runtimeServiceImpl.getVariableLocal("42", "Variable Name", variableClass);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariableLocal(String, String)} with {@code executionId},
   * {@code variableName}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariableLocal(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RuntimeServiceImpl.getVariableLocal(String, String)"})
  public void testGetVariableLocalWithExecutionIdVariableName_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.getVariableLocal("42", "Variable Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#hasVariableLocal(String, String)}.
   *
   * <ul>
   *   <li>Given {@link CommandExecutorImpl} {@link CommandExecutorImpl#execute(Command)} return
   *       {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#hasVariableLocal(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RuntimeServiceImpl.hasVariableLocal(String, String)"})
  public void testHasVariableLocal_givenCommandExecutorImplExecuteReturnFalse_thenReturnFalse() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    when(commandExecutor.execute(Mockito.<Command<Boolean>>any())).thenReturn(false);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    boolean actualHasVariableLocalResult =
        runtimeServiceImpl.hasVariableLocal("42", "Variable Name");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertFalse(actualHasVariableLocalResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#hasVariableLocal(String, String)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#hasVariableLocal(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RuntimeServiceImpl.hasVariableLocal(String, String)"})
  public void testHasVariableLocal_thenReturnTrue() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Boolean>>any()))
        .thenReturn(true);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    boolean actualHasVariableLocalResult =
        runtimeServiceImpl.hasVariableLocal("42", "Variable Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualHasVariableLocalResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#setVariable(String, String, Object)}.
   *
   * <ul>
   *   <li>Given {@link CommandContextInterceptor} {@link
   *       CommandContextInterceptor#execute(CommandConfig, Command)} return {@link
   *       JSONObject#NULL}.
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#setVariable(String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.setVariable(String, String, Object)"})
  public void testSetVariable_givenCommandContextInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.setVariable("42", "Variable Name", JSONObject.NULL);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#setVariable(String, String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#setVariable(String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.setVariable(String, String, Object)"})
  public void testSetVariable_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new RuntimeServiceImpl().setVariable("42", null, JSONObject.NULL));
  }

  /**
   * Test {@link RuntimeServiceImpl#setVariableLocal(String, String, Object)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#setVariableLocal(String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.setVariableLocal(String, String, Object)"})
  public void testSetVariableLocal_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.setVariableLocal("42", "Variable Name", JSONObject.NULL);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#setVariableLocal(String, String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#setVariableLocal(String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.setVariableLocal(String, String, Object)"})
  public void testSetVariableLocal_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new RuntimeServiceImpl().setVariableLocal("42", null, JSONObject.NULL));
  }

  /**
   * Test {@link RuntimeServiceImpl#setVariables(String, Map)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#setVariables(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.setVariables(String, Map)"})
  public void testSetVariables_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.setVariables("42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#setVariablesLocal(String, Map)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#setVariablesLocal(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.setVariablesLocal(String, Map)"})
  public void testSetVariablesLocal_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.setVariablesLocal("42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#removeVariable(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#removeVariable(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.removeVariable(String, String)"})
  public void testRemoveVariable_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.removeVariable("42", "Variable Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#removeVariableLocal(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#removeVariableLocal(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.removeVariableLocal(String, String)"})
  public void testRemoveVariableLocal_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.removeVariableLocal("42", "Variable Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#removeVariables(String, Collection)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#removeVariables(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.removeVariables(String, Collection)"})
  public void testRemoveVariables_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.removeVariables("42", new ArrayList<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#removeVariablesLocal(String, Collection)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#removeVariablesLocal(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.removeVariablesLocal(String, Collection)"})
  public void testRemoveVariablesLocal_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.removeVariablesLocal("42", new ArrayList<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariableInstanceLocal(String, String)}.
   *
   * <ul>
   *   <li>Then return {@link VariableInstanceEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariableInstanceLocal(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "VariableInstance RuntimeServiceImpl.getVariableInstanceLocal(String, String)"
  })
  public void testGetVariableInstanceLocal_thenReturnVariableInstanceEntityImpl() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<VariableInstance>>any()))
        .thenReturn(variableInstanceEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    VariableInstance actualVariableInstanceLocal =
        runtimeServiceImpl.getVariableInstanceLocal("42", "Variable Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(variableInstanceEntityImpl, actualVariableInstanceLocal);
  }

  /**
   * Test {@link RuntimeServiceImpl#getVariableInstance(String, String)}.
   *
   * <ul>
   *   <li>Then return {@link VariableInstanceEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getVariableInstance(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableInstance RuntimeServiceImpl.getVariableInstance(String, String)"})
  public void testGetVariableInstance_thenReturnVariableInstanceEntityImpl() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<VariableInstance>>any()))
        .thenReturn(variableInstanceEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    VariableInstance actualVariableInstance =
        runtimeServiceImpl.getVariableInstance("42", "Variable Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(variableInstanceEntityImpl, actualVariableInstance);
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjects(String, Collection, String, boolean)} with {@code
   * executionId}, {@code dataObjectNames}, {@code locale}, {@code withLocalizationFallback}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObjects(String, Collection, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getDataObjects(String, Collection, String, boolean)"})
  public void testGetDataObjectsWithExecutionIdDataObjectNamesLocaleWithLocalizationFallback() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, DataObject>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, DataObject> actualDataObjects =
        runtimeServiceImpl.getDataObjects("42", new ArrayList<>(), "en", true);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualDataObjects.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjects(String, Collection)} with {@code executionId},
   * {@code dataObjectNames}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObjects(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getDataObjects(String, Collection)"})
  public void testGetDataObjectsWithExecutionIdDataObjectNames_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, DataObject>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, DataObject> actualDataObjects =
        runtimeServiceImpl.getDataObjects("42", new ArrayList<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualDataObjects.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjects(String, String, boolean)} with {@code
   * executionId}, {@code locale}, {@code withLocalizationFallback}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObjects(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getDataObjects(String, String, boolean)"})
  public void testGetDataObjectsWithExecutionIdLocaleWithLocalizationFallback_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, DataObject>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, DataObject> actualDataObjects = runtimeServiceImpl.getDataObjects("42", "en", true);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualDataObjects.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjects(String)} with {@code executionId}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObjects(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getDataObjects(String)"})
  public void testGetDataObjectsWithExecutionId_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, DataObject>>>any()))
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
   * Test {@link RuntimeServiceImpl#getDataObjectsLocal(String, Collection, String, boolean)} with
   * {@code executionId}, {@code dataObjectNames}, {@code locale}, {@code withLocalizationFallback}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObjectsLocal(String, Collection, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map RuntimeServiceImpl.getDataObjectsLocal(String, Collection, String, boolean)"
  })
  public void
      testGetDataObjectsLocalWithExecutionIdDataObjectNamesLocaleWithLocalizationFallback() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, DataObject>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, DataObject> actualDataObjectsLocal =
        runtimeServiceImpl.getDataObjectsLocal("42", new ArrayList<>(), "en", true);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualDataObjectsLocal.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjectsLocal(String, Collection)} with {@code
   * executionId}, {@code dataObjects}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObjectsLocal(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getDataObjectsLocal(String, Collection)"})
  public void testGetDataObjectsLocalWithExecutionIdDataObjects_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, DataObject>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, DataObject> actualDataObjectsLocal =
        runtimeServiceImpl.getDataObjectsLocal("42", new ArrayList<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualDataObjectsLocal.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjectsLocal(String, String, boolean)} with {@code
   * executionId}, {@code locale}, {@code withLocalizationFallback}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObjectsLocal(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getDataObjectsLocal(String, String, boolean)"})
  public void testGetDataObjectsLocalWithExecutionIdLocaleWithLocalizationFallback() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, DataObject>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, DataObject> actualDataObjectsLocal =
        runtimeServiceImpl.getDataObjectsLocal("42", "en", true);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualDataObjectsLocal.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjectsLocal(String)} with {@code executionId}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObjectsLocal(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map RuntimeServiceImpl.getDataObjectsLocal(String)"})
  public void testGetDataObjectsLocalWithExecutionId_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<Map<String, DataObject>>>any()))
        .thenReturn(new HashMap<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Map<String, DataObject> actualDataObjectsLocal = runtimeServiceImpl.getDataObjectsLocal("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualDataObjectsLocal.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObject(String, String)} with {@code executionId}, {@code
   * dataObject}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObject(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DataObject RuntimeServiceImpl.getDataObject(String, String)"})
  public void testGetDataObjectWithExecutionIdDataObject() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    DataObjectImpl dataObjectImpl =
        new DataObjectImpl(
            "Name",
            JSONObject.NULL,
            "The characteristics of someone or something",
            "Type",
            "Localized Name",
            "Localized Description",
            "Data Object Definition Key");
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DataObject>>any()))
        .thenReturn(dataObjectImpl);
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
   * Test {@link RuntimeServiceImpl#getDataObject(String, String, String, boolean)} with {@code
   * executionId}, {@code dataObjectName}, {@code locale}, {@code withLocalizationFallback}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObject(String, String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DataObject RuntimeServiceImpl.getDataObject(String, String, String, boolean)"
  })
  public void testGetDataObjectWithExecutionIdDataObjectNameLocaleWithLocalizationFallback() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    DataObjectImpl dataObjectImpl =
        new DataObjectImpl(
            "Name",
            JSONObject.NULL,
            "The characteristics of someone or something",
            "Type",
            "Localized Name",
            "Localized Description",
            "Data Object Definition Key");
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DataObject>>any()))
        .thenReturn(dataObjectImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    DataObject actualDataObject =
        runtimeServiceImpl.getDataObject("42", "Data Object Name", "en", true);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(dataObjectImpl, actualDataObject);
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjectLocal(String, String)} with {@code executionId},
   * {@code dataObjectName}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObjectLocal(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DataObject RuntimeServiceImpl.getDataObjectLocal(String, String)"})
  public void testGetDataObjectLocalWithExecutionIdDataObjectName() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    DataObjectImpl dataObjectImpl =
        new DataObjectImpl(
            "Name",
            JSONObject.NULL,
            "The characteristics of someone or something",
            "Type",
            "Localized Name",
            "Localized Description",
            "Data Object Definition Key");
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DataObject>>any()))
        .thenReturn(dataObjectImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    DataObject actualDataObjectLocal =
        runtimeServiceImpl.getDataObjectLocal("42", "Data Object Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(dataObjectImpl, actualDataObjectLocal);
  }

  /**
   * Test {@link RuntimeServiceImpl#getDataObjectLocal(String, String, String, boolean)} with {@code
   * executionId}, {@code dataObjectName}, {@code locale}, {@code withLocalizationFallback}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getDataObjectLocal(String, String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DataObject RuntimeServiceImpl.getDataObjectLocal(String, String, String, boolean)"
  })
  public void testGetDataObjectLocalWithExecutionIdDataObjectNameLocaleWithLocalizationFallback() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    DataObjectImpl dataObjectImpl =
        new DataObjectImpl(
            "Name",
            JSONObject.NULL,
            "The characteristics of someone or something",
            "Type",
            "Localized Name",
            "Localized Description",
            "Data Object Definition Key");
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DataObject>>any()))
        .thenReturn(dataObjectImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    DataObject actualDataObjectLocal =
        runtimeServiceImpl.getDataObjectLocal("42", "Data Object Name", "en", true);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(dataObjectImpl, actualDataObjectLocal);
  }

  /**
   * Test {@link RuntimeServiceImpl#signal(String, Map)} with {@code executionId}, {@code
   * processVariables}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signal(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.signal(String, Map)"})
  public void testSignalWithExecutionIdProcessVariables_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signal("42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#signal(String)} with {@code executionId}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signal(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.signal(String)"})
  public void testSignalWithExecutionId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signal("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#trigger(String, Map, Map)} with {@code executionId}, {@code
   * processVariables}, {@code transientVariables}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#trigger(String, Map, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.trigger(String, Map, Map)"})
  public void testTriggerWithExecutionIdProcessVariablesTransientVariables_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);
    HashMap<String, Object> processVariables = new HashMap<>();

    // Act
    runtimeServiceImpl.trigger("42", processVariables, new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#trigger(String, Map)} with {@code executionId}, {@code
   * processVariables}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#trigger(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.trigger(String, Map)"})
  public void testTriggerWithExecutionIdProcessVariables_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.trigger("42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#trigger(String)} with {@code executionId}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#trigger(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.trigger(String)"})
  public void testTriggerWithExecutionId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.trigger("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#addParticipantUser(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#addParticipantUser(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.addParticipantUser(String, String)"})
  public void testAddParticipantUser_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.addParticipantUser("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#addParticipantGroup(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#addParticipantGroup(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.addParticipantGroup(String, String)"})
  public void testAddParticipantGroup_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.addParticipantGroup("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#deleteParticipantUser(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#deleteParticipantUser(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.deleteParticipantUser(String, String)"})
  public void testDeleteParticipantUser_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.deleteParticipantUser("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#deleteParticipantGroup(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#deleteParticipantGroup(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.deleteParticipantGroup(String, String)"})
  public void testDeleteParticipantGroup_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.deleteParticipantGroup("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#getIdentityLinksForProcessInstance(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getIdentityLinksForProcessInstance(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List RuntimeServiceImpl.getIdentityLinksForProcessInstance(String)"})
  public void testGetIdentityLinksForProcessInstance_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<List<IdentityLink>>>any()))
        .thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<IdentityLink> actualIdentityLinksForProcessInstance =
        runtimeServiceImpl.getIdentityLinksForProcessInstance("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualIdentityLinksForProcessInstance.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#createProcessInstanceQuery()}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#createProcessInstanceQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceQuery RuntimeServiceImpl.createProcessInstanceQuery()"})
  public void testCreateProcessInstanceQuery() {
    // Arrange and Act
    ProcessInstanceQuery actualCreateProcessInstanceQueryResult =
        new RuntimeServiceImpl().createProcessInstanceQuery();

    // Assert
    assertTrue(actualCreateProcessInstanceQueryResult instanceof ProcessInstanceQueryImpl);
    assertEquals(
        "RES.ID_ asc",
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getOrderBy());
    assertEquals(
        "RES.ID_ asc",
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getOrderByColumns());
    assertEquals(
        "TEMPRES_ID_ asc",
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getMssqlOrDB2OrderBy());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getProcessDefinitionVersion());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getProcessInstanceVariablesLimit());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getParameter());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getDatabaseType());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getActivityId());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getBusinessKey());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getDeploymentId());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getExecutionId());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getInvolvedUser());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getName());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getNameLike());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getNameLikeIgnoreCase());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getParentId());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getProcessDefinitionCategory());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getProcessDefinitionId());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getProcessDefinitionKey());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getProcessDefinitionName());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getProcessInstanceId());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getRootProcessInstanceId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getStartedBy());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getSubProcessInstanceId());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getSuperProcessInstanceId());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getTenantId());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getTenantIdLike());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).orderBy);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).activityId);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).locale);
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getStartedAfter());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getStartedBefore());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getDeploymentIds());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getInvolvedGroups());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getEventSubscriptions());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getProcessDefinitionIds());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getProcessDefinitionKeys());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getProcessInstanceIds());
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).nullHandlingOnOrder);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).resultType);
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).currentOrQueryObject);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).commandContext);
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).commandExecutor);
    assertNull(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getSuspensionState());
    assertNull(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).orderProperty);
    assertEquals(
        0, ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getFirstResult());
    assertEquals(
        1, ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getFirstRow());
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .hasLocalQueryVariableValue());
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .hasNonLocalQueryVariableValue());
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .isExcludeSubprocesses());
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .isIncludeChildExecutionsWithBusinessKeyQuery());
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .isIncludeProcessVariables());
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .isOnlyChildExecutions());
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .isOnlyProcessInstanceExecutions());
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .isOnlySubProcessExecutions());
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).isWithoutTenantId());
    assertFalse(((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).inOrStatement);
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).withJobException);
    assertFalse(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .withLocalizationFallback);
    assertTrue(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getQueryVariableValues()
            .isEmpty());
    assertTrue(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getOrQueryObjects()
            .isEmpty());
    assertTrue(
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult)
            .getOnlyProcessInstances());
    assertEquals(
        Integer.MAX_VALUE,
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getLastRow());
    assertEquals(
        Integer.MAX_VALUE,
        ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).getMaxResults());
  }

  /**
   * Test {@link RuntimeServiceImpl#getActiveActivityIds(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getActiveActivityIds(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   * Test {@link RuntimeServiceImpl#suspendProcessInstanceById(String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#suspendProcessInstanceById(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.suspendProcessInstanceById(String)"})
  public void testSuspendProcessInstanceById_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.suspendProcessInstanceById("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#activateProcessInstanceById(String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#activateProcessInstanceById(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.activateProcessInstanceById(String)"})
  public void testActivateProcessInstanceById_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.activateProcessInstanceById("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByMessage(String)} with {@code messageName}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByMessage(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance RuntimeServiceImpl.startProcessInstanceByMessage(String)"})
  public void testStartProcessInstanceByMessageWithMessageName() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageResult =
        runtimeServiceImpl.startProcessInstanceByMessage("Message Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByMessageResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByMessage(String, String)} with {@code
   * messageName}, {@code businessKey}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByMessage(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByMessage(String, String)"
  })
  public void testStartProcessInstanceByMessageWithMessageNameBusinessKey() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageResult =
        runtimeServiceImpl.startProcessInstanceByMessage("Message Name", "Business Key");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByMessageResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByMessage(String, String, Map)} with {@code
   * messageName}, {@code businessKey}, {@code processVariables}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByMessage(String, String,
   * Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByMessage(String, String, Map)"
  })
  public void testStartProcessInstanceByMessageWithMessageNameBusinessKeyProcessVariables() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageResult =
        runtimeServiceImpl.startProcessInstanceByMessage(
            "Message Name", "Business Key", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByMessageResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByMessage(String, Map)} with {@code
   * messageName}, {@code processVariables}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startProcessInstanceByMessage(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByMessage(String, Map)"
  })
  public void testStartProcessInstanceByMessageWithMessageNameProcessVariables() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageResult =
        runtimeServiceImpl.startProcessInstanceByMessage("Message Name", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByMessageResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByMessageAndTenantId(String, String, Map,
   * String)} with {@code messageName}, {@code businessKey}, {@code processVariables}, {@code
   * tenantId}.
   *
   * <p>Method under test: {@link
   * RuntimeServiceImpl#startProcessInstanceByMessageAndTenantId(String, String, Map, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByMessageAndTenantId(String, String, Map, String)"
  })
  public void
      testStartProcessInstanceByMessageAndTenantIdWithMessageNameBusinessKeyProcessVariablesTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageAndTenantIdResult =
        runtimeServiceImpl.startProcessInstanceByMessageAndTenantId(
            "Message Name", "Business Key", new HashMap<>(), "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult,
        actualStartProcessInstanceByMessageAndTenantIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByMessageAndTenantId(String, String,
   * String)} with {@code messageName}, {@code businessKey}, {@code tenantId}.
   *
   * <p>Method under test: {@link
   * RuntimeServiceImpl#startProcessInstanceByMessageAndTenantId(String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByMessageAndTenantId(String, String, String)"
  })
  public void testStartProcessInstanceByMessageAndTenantIdWithMessageNameBusinessKeyTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageAndTenantIdResult =
        runtimeServiceImpl.startProcessInstanceByMessageAndTenantId(
            "Message Name", "Business Key", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult,
        actualStartProcessInstanceByMessageAndTenantIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByMessageAndTenantId(String, Map, String)}
   * with {@code messageName}, {@code processVariables}, {@code tenantId}.
   *
   * <p>Method under test: {@link
   * RuntimeServiceImpl#startProcessInstanceByMessageAndTenantId(String, Map, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByMessageAndTenantId(String, Map, String)"
  })
  public void
      testStartProcessInstanceByMessageAndTenantIdWithMessageNameProcessVariablesTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageAndTenantIdResult =
        runtimeServiceImpl.startProcessInstanceByMessageAndTenantId(
            "Message Name", new HashMap<>(), "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult,
        actualStartProcessInstanceByMessageAndTenantIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstanceByMessageAndTenantId(String, String)} with
   * {@code messageName}, {@code tenantId}.
   *
   * <p>Method under test: {@link
   * RuntimeServiceImpl#startProcessInstanceByMessageAndTenantId(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstanceByMessageAndTenantId(String, String)"
  })
  public void testStartProcessInstanceByMessageAndTenantIdWithMessageNameTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageAndTenantIdResult =
        runtimeServiceImpl.startProcessInstanceByMessageAndTenantId("Message Name", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult,
        actualStartProcessInstanceByMessageAndTenantIdResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#signalEventReceived(String, String, Map)} with {@code
   * signalName}, {@code executionId}, {@code processVariables}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signalEventReceived(String, String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.signalEventReceived(String, String, Map)"})
  public void testSignalEventReceivedWithSignalNameExecutionIdProcessVariables() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceived("Signal Name", "42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#signalEventReceived(String, String)} with {@code signalName},
   * {@code executionId}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signalEventReceived(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.signalEventReceived(String, String)"})
  public void testSignalEventReceivedWithSignalNameExecutionId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceived("Signal Name", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#signalEventReceived(String, Map)} with {@code signalName},
   * {@code processVariables}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signalEventReceived(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.signalEventReceived(String, Map)"})
  public void testSignalEventReceivedWithSignalNameProcessVariables_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceived("Signal Name", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#signalEventReceived(String)} with {@code signalName}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signalEventReceived(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.signalEventReceived(String)"})
  public void testSignalEventReceivedWithSignalName_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceived("Signal Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#signalEventReceivedWithTenantId(String, Map, String)} with
   * {@code signalName}, {@code processVariables}, {@code tenantId}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signalEventReceivedWithTenantId(String, Map,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RuntimeServiceImpl.signalEventReceivedWithTenantId(String, Map, String)"
  })
  public void testSignalEventReceivedWithTenantIdWithSignalNameProcessVariablesTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceivedWithTenantId("Signal Name", new HashMap<>(), "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#signalEventReceivedWithTenantId(String, String)} with {@code
   * signalName}, {@code tenantId}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signalEventReceivedWithTenantId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.signalEventReceivedWithTenantId(String, String)"})
  public void testSignalEventReceivedWithTenantIdWithSignalNameTenantId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceivedWithTenantId("Signal Name", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#signalEventReceivedAsync(String, String)} with {@code
   * signalName}, {@code executionId}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signalEventReceivedAsync(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.signalEventReceivedAsync(String, String)"})
  public void testSignalEventReceivedAsyncWithSignalNameExecutionId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceivedAsync("Signal Name", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#signalEventReceivedAsync(String)} with {@code signalName}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signalEventReceivedAsync(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.signalEventReceivedAsync(String)"})
  public void testSignalEventReceivedAsyncWithSignalName_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceivedAsync("Signal Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#signalEventReceivedAsyncWithTenantId(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#signalEventReceivedAsyncWithTenantId(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RuntimeServiceImpl.signalEventReceivedAsyncWithTenantId(String, String)"
  })
  public void testSignalEventReceivedAsyncWithTenantId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceivedAsyncWithTenantId("Signal Name", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#messageEventReceived(String, String, Map)} with {@code
   * messageName}, {@code executionId}, {@code processVariables}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#messageEventReceived(String, String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.messageEventReceived(String, String, Map)"})
  public void testMessageEventReceivedWithMessageNameExecutionIdProcessVariables() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.messageEventReceived("Message Name", "42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#messageEventReceived(String, String)} with {@code messageName},
   * {@code executionId}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#messageEventReceived(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.messageEventReceived(String, String)"})
  public void testMessageEventReceivedWithMessageNameExecutionId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.messageEventReceived("Message Name", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#messageEventReceivedAsync(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#messageEventReceivedAsync(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.messageEventReceivedAsync(String, String)"})
  public void testMessageEventReceivedAsync_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.messageEventReceivedAsync("Message Name", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   * with {@code listenerToAdd}, {@code types}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#addEventListener(ActivitiEventListener,
   * ActivitiEventType[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RuntimeServiceImpl.addEventListener(ActivitiEventListener, ActivitiEventType[])"
  })
  public void testAddEventListenerWithListenerToAddTypes_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.addEventListener(
        new BaseEntityEventListener(true), ActivitiEventType.ENTITY_CREATED);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#addEventListener(ActivitiEventListener)} with {@code
   * listenerToAdd}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#addEventListener(ActivitiEventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.addEventListener(ActivitiEventListener)"})
  public void testAddEventListenerWithListenerToAdd_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.addEventListener(new BaseEntityEventListener(true));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#removeEventListener(ActivitiEventListener)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#removeEventListener(ActivitiEventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.removeEventListener(ActivitiEventListener)"})
  public void testRemoveEventListener_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.removeEventListener(new BaseEntityEventListener(true));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#dispatchEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#dispatchEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.dispatchEvent(ActivitiEvent)"})
  public void testDispatchEvent_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.dispatchEvent(new ActivitiActivityCancelledEventImpl());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#setProcessInstanceName(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#setProcessInstanceName(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.setProcessInstanceName(String, String)"})
  public void testSetProcessInstanceName_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.setProcessInstanceName("42", "Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RuntimeServiceImpl#getProcessInstanceEvents(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#getProcessInstanceEvents(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List RuntimeServiceImpl.getProcessInstanceEvents(String)"})
  public void testGetProcessInstanceEvents_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<List<Event>>>any()))
        .thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<Event> actualProcessInstanceEvents = runtimeServiceImpl.getProcessInstanceEvents("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualProcessInstanceEvents.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#getEnabledActivitiesFromAdhocSubProcess(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * RuntimeServiceImpl#getEnabledActivitiesFromAdhocSubProcess(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List RuntimeServiceImpl.getEnabledActivitiesFromAdhocSubProcess(String)"})
  public void testGetEnabledActivitiesFromAdhocSubProcess_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<List<FlowNode>>>any()))
        .thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<FlowNode> actualEnabledActivitiesFromAdhocSubProcess =
        runtimeServiceImpl.getEnabledActivitiesFromAdhocSubProcess("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualEnabledActivitiesFromAdhocSubProcess.isEmpty());
  }

  /**
   * Test {@link RuntimeServiceImpl#executeActivityInAdhocSubProcess(String, String)}.
   *
   * <p>Method under test: {@link RuntimeServiceImpl#executeActivityInAdhocSubProcess(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Execution RuntimeServiceImpl.executeActivityInAdhocSubProcess(String, String)"
  })
  public void testExecuteActivityInAdhocSubProcess() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Execution>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Execution actualExecuteActivityInAdhocSubProcessResult =
        runtimeServiceImpl.executeActivityInAdhocSubProcess("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult, actualExecuteActivityInAdhocSubProcessResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#completeAdhocSubProcess(String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#completeAdhocSubProcess(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.completeAdhocSubProcess(String)"})
  public void testCompleteAdhocSubProcess_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
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
   *
   * <p>Method under test: {@link RuntimeServiceImpl#createProcessInstanceBuilder()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder RuntimeServiceImpl.createProcessInstanceBuilder()"})
  public void testCreateProcessInstanceBuilder() {
    // Arrange and Act
    ProcessInstanceBuilder actualCreateProcessInstanceBuilderResult =
        new RuntimeServiceImpl().createProcessInstanceBuilder();

    // Assert
    assertTrue(actualCreateProcessInstanceBuilderResult instanceof ProcessInstanceBuilderImpl);
    assertNull(
        ((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getBusinessKey());
    assertNull(
        ((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getMessageName());
    assertNull(
        ((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult)
            .getProcessDefinitionId());
    assertNull(
        ((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult)
            .getProcessDefinitionKey());
    assertNull(
        ((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult)
            .getProcessInstanceName());
    assertNull(
        ((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getTenantId());
    assertNull(
        ((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult)
            .getTransientVariables());
    assertNull(
        ((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult).getVariables());
    assertFalse(
        ((ProcessInstanceBuilderImpl) actualCreateProcessInstanceBuilderResult)
            .hasProcessDefinitionIdOrKey());
  }

  /**
   * Test {@link RuntimeServiceImpl#startCreatedProcessInstance(ProcessInstance, Map)}.
   *
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeServiceImpl#startCreatedProcessInstance(ProcessInstance,
   * Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startCreatedProcessInstance(ProcessInstance, Map)"
  })
  public void testStartCreatedProcessInstance_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    CommandExecutorImpl commandExecutor = mock(CommandExecutorImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);
    ExecutionEntityImpl createdProcessInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ProcessInstance actualStartCreatedProcessInstanceResult =
        runtimeServiceImpl.startCreatedProcessInstance(createdProcessInstance, new HashMap<>());

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertSame(
        createWithEmptyRelationshipCollectionsResult, actualStartCreatedProcessInstanceResult);
  }

  /**
   * Test {@link RuntimeServiceImpl#startProcessInstance(ProcessInstanceBuilderImpl)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RuntimeServiceImpl#startProcessInstance(ProcessInstanceBuilderImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.startProcessInstance(ProcessInstanceBuilderImpl)"
  })
  public void testStartProcessInstance_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            runtimeServiceImpl.startProcessInstance(
                new ProcessInstanceBuilderImpl(new RuntimeServiceImpl())));
  }

  /**
   * Test {@link RuntimeServiceImpl#createProcessInstance(ProcessInstanceBuilderImpl)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RuntimeServiceImpl#createProcessInstance(ProcessInstanceBuilderImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance RuntimeServiceImpl.createProcessInstance(ProcessInstanceBuilderImpl)"
  })
  public void testCreateProcessInstance_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            runtimeServiceImpl.createProcessInstance(
                new ProcessInstanceBuilderImpl(new RuntimeServiceImpl())));
  }

  /**
   * Test new {@link RuntimeServiceImpl} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link RuntimeServiceImpl}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RuntimeServiceImpl.<init>()"})
  public void testNewRuntimeServiceImpl() {
    // Arrange, Act and Assert
    assertNull(new RuntimeServiceImpl().getCommandExecutor());
  }
}
