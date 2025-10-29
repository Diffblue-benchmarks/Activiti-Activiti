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
import jakarta.transaction.TransactionManager;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.JtaRetryInterceptor;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.runtime.ProcessInstanceBuilderImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.NativeExecutionQuery;
import org.activiti.engine.runtime.NativeProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RuntimeServiceImplDiffblueTest {
  @InjectMocks
  private RuntimeServiceImpl runtimeServiceImpl;

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstanceByKey(String, String, Map)}
   */
  @Test
  public void testStartProcessInstanceByKey() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyResult = runtimeServiceImpl
        .startProcessInstanceByKey("Process Definition Key", "Business Key", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByKeyResult);
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstanceByKey(String, Map)}
   */
  @Test
  public void testStartProcessInstanceByKey2() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyResult = runtimeServiceImpl
        .startProcessInstanceByKey("Process Definition Key", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByKeyResult);
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstanceByKeyAndTenantId(String, String, Map, String)}
   */
  @Test
  public void testStartProcessInstanceByKeyAndTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyAndTenantIdResult = runtimeServiceImpl
        .startProcessInstanceByKeyAndTenantId("Process Definition Key", "Business Key", new HashMap<>(), "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByKeyAndTenantIdResult);
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstanceByKeyAndTenantId(String, Map, String)}
   */
  @Test
  public void testStartProcessInstanceByKeyAndTenantId2() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByKeyAndTenantIdResult = runtimeServiceImpl
        .startProcessInstanceByKeyAndTenantId("Process Definition Key", new HashMap<>(), "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByKeyAndTenantIdResult);
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstanceById(String, String, Map)}
   */
  @Test
  public void testStartProcessInstanceById() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByIdResult = runtimeServiceImpl.startProcessInstanceById("42",
        "Business Key", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByIdResult);
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstanceById(String, Map)}
   */
  @Test
  public void testStartProcessInstanceById2() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByIdResult = runtimeServiceImpl.startProcessInstanceById("42",
        new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByIdResult);
  }

  /**
   * Method under test: {@link RuntimeServiceImpl#createExecutionQuery()}
   */
  @Test
  public void testCreateExecutionQuery() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();

    // Act
    ExecutionQuery actualCreateExecutionQueryResult = runtimeServiceImpl.createExecutionQuery();

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
    assertNull(runtimeServiceImpl.getCommandExecutor());
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
   * Method under test: {@link RuntimeServiceImpl#createExecutionQuery()}
   */
  @Test
  public void testCreateExecutionQuery2() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    runtimeServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    ExecutionQuery actualCreateExecutionQueryResult = runtimeServiceImpl.createExecutionQuery();

    // Assert
    assertTrue(actualCreateExecutionQueryResult instanceof ExecutionQueryImpl);
    CommandExecutor commandExecutor = ((ExecutionQueryImpl) actualCreateExecutionQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
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
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractQuery<ExecutionQuery, Execution>) actualCreateExecutionQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, runtimeServiceImpl.getCommandExecutor());
  }

  /**
   * Method under test: {@link RuntimeServiceImpl#createNativeExecutionQuery()}
   */
  @Test
  public void testCreateNativeExecutionQuery() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();

    // Act
    NativeExecutionQuery actualCreateNativeExecutionQueryResult = runtimeServiceImpl.createNativeExecutionQuery();

    // Assert
    assertTrue(actualCreateNativeExecutionQueryResult instanceof NativeExecutionQueryImpl);
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).resultType);
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).commandContext);
    assertNull(runtimeServiceImpl.getCommandExecutor());
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).commandExecutor);
    assertEquals(0, ((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).firstResult);
    assertTrue(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE, ((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).maxResults);
  }

  /**
   * Method under test: {@link RuntimeServiceImpl#createNativeExecutionQuery()}
   */
  @Test
  public void testCreateNativeExecutionQuery2() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    runtimeServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    NativeExecutionQuery actualCreateNativeExecutionQueryResult = runtimeServiceImpl.createNativeExecutionQuery();

    // Assert
    assertTrue(actualCreateNativeExecutionQueryResult instanceof NativeExecutionQueryImpl);
    CommandExecutor commandExecutor = ((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).resultType);
    assertNull(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).commandContext);
    assertEquals(0, ((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).firstResult);
    assertTrue(((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE, ((NativeExecutionQueryImpl) actualCreateNativeExecutionQueryResult).maxResults);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractNativeQuery<NativeExecutionQuery, Execution>) actualCreateNativeExecutionQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, runtimeServiceImpl.getCommandExecutor());
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#createNativeProcessInstanceQuery()}
   */
  @Test
  public void testCreateNativeProcessInstanceQuery() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();

    // Act
    NativeProcessInstanceQuery actualCreateNativeProcessInstanceQueryResult = runtimeServiceImpl
        .createNativeProcessInstanceQuery();

    // Assert
    assertTrue(actualCreateNativeProcessInstanceQueryResult instanceof NativeProcessInstanceQueryImpl);
    assertNull(((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).resultType);
    assertNull(((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).commandContext);
    assertNull(runtimeServiceImpl.getCommandExecutor());
    assertNull(((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).commandExecutor);
    assertEquals(0, ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).firstResult);
    assertTrue(
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).maxResults);
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#createNativeProcessInstanceQuery()}
   */
  @Test
  public void testCreateNativeProcessInstanceQuery2() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    runtimeServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    NativeProcessInstanceQuery actualCreateNativeProcessInstanceQueryResult = runtimeServiceImpl
        .createNativeProcessInstanceQuery();

    // Assert
    assertTrue(actualCreateNativeProcessInstanceQueryResult instanceof NativeProcessInstanceQueryImpl);
    CommandExecutor commandExecutor = ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertNull(((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).resultType);
    assertNull(((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).commandContext);
    assertEquals(0, ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).firstResult);
    assertTrue(
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeProcessInstanceQueryImpl) actualCreateNativeProcessInstanceQueryResult).maxResults);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractNativeQuery<NativeProcessInstanceQuery, ProcessInstance>) actualCreateNativeProcessInstanceQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, runtimeServiceImpl.getCommandExecutor());
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#setVariable(String, String, Object)}
   */
  @Test
  public void testSetVariable() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> runtimeServiceImpl.setVariable("42", null, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#setVariableLocal(String, String, Object)}
   */
  @Test
  public void testSetVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> runtimeServiceImpl.setVariableLocal("42", null, JSONObject.NULL));
  }

  /**
   * Method under test: {@link RuntimeServiceImpl#setVariables(String, Map)}
   */
  @Test
  public void testSetVariables() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.setVariables("42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Method under test: {@link RuntimeServiceImpl#setVariablesLocal(String, Map)}
   */
  @Test
  public void testSetVariablesLocal() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.setVariablesLocal("42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Method under test: {@link RuntimeServiceImpl#signal(String, Map)}
   */
  @Test
  public void testSignal() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signal("42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Method under test: {@link RuntimeServiceImpl#trigger(String, Map)}
   */
  @Test
  public void testTrigger() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.trigger("42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Method under test: {@link RuntimeServiceImpl#trigger(String, Map, Map)}
   */
  @Test
  public void testTrigger2() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
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
   * Method under test: {@link RuntimeServiceImpl#createProcessInstanceQuery()}
   */
  @Test
  public void testCreateProcessInstanceQuery() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();

    // Act
    ProcessInstanceQuery actualCreateProcessInstanceQueryResult = runtimeServiceImpl.createProcessInstanceQuery();

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
    assertNull(runtimeServiceImpl.getCommandExecutor());
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
   * Method under test: {@link RuntimeServiceImpl#createProcessInstanceQuery()}
   */
  @Test
  public void testCreateProcessInstanceQuery2() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    runtimeServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    ProcessInstanceQuery actualCreateProcessInstanceQueryResult = runtimeServiceImpl.createProcessInstanceQuery();

    // Assert
    assertTrue(actualCreateProcessInstanceQueryResult instanceof ProcessInstanceQueryImpl);
    CommandExecutor commandExecutor = ((ProcessInstanceQueryImpl) actualCreateProcessInstanceQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
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
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractQuery<ProcessInstanceQuery, ProcessInstance>) actualCreateProcessInstanceQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, runtimeServiceImpl.getCommandExecutor());
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstanceByMessage(String, String, Map)}
   */
  @Test
  public void testStartProcessInstanceByMessage() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageResult = runtimeServiceImpl
        .startProcessInstanceByMessage("Message Name", "Business Key", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByMessageResult);
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstanceByMessage(String, Map)}
   */
  @Test
  public void testStartProcessInstanceByMessage2() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageResult = runtimeServiceImpl
        .startProcessInstanceByMessage("Message Name", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByMessageResult);
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstanceByMessageAndTenantId(String, String, Map, String)}
   */
  @Test
  public void testStartProcessInstanceByMessageAndTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageAndTenantIdResult = runtimeServiceImpl
        .startProcessInstanceByMessageAndTenantId("Message Name", "Business Key", new HashMap<>(), "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByMessageAndTenantIdResult);
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstanceByMessageAndTenantId(String, Map, String)}
   */
  @Test
  public void testStartProcessInstanceByMessageAndTenantId2() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessInstance>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessInstance actualStartProcessInstanceByMessageAndTenantIdResult = runtimeServiceImpl
        .startProcessInstanceByMessageAndTenantId("Message Name", new HashMap<>(), "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartProcessInstanceByMessageAndTenantIdResult);
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#signalEventReceived(String, String, Map)}
   */
  @Test
  public void testSignalEventReceived() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceived("Signal Name", "42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#signalEventReceived(String, Map)}
   */
  @Test
  public void testSignalEventReceived2() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceived("Signal Name", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#signalEventReceivedWithTenantId(String, Map, String)}
   */
  @Test
  public void testSignalEventReceivedWithTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.signalEventReceivedWithTenantId("Signal Name", new HashMap<>(), "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#messageEventReceived(String, String, Map)}
   */
  @Test
  public void testMessageEventReceived() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any())).thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    runtimeServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    runtimeServiceImpl.messageEventReceived("Message Name", "42", new HashMap<>());

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Method under test: {@link RuntimeServiceImpl#createProcessInstanceBuilder()}
   */
  @Test
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
   * Method under test: {@link RuntimeServiceImpl#createProcessInstanceBuilder()}
   */
  @Test
  public void testCreateProcessInstanceBuilder2() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    runtimeServiceImpl.setCommandExecutor(
        new CommandExecutorImpl(defaultConfig, new JtaRetryInterceptor(mock(TransactionManager.class))));

    // Act
    ProcessInstanceBuilder actualCreateProcessInstanceBuilderResult = runtimeServiceImpl.createProcessInstanceBuilder();

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
   * Method under test:
   * {@link RuntimeServiceImpl#startProcessInstance(ProcessInstanceBuilderImpl)}
   */
  @Test
  public void testStartProcessInstance() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> runtimeServiceImpl.startProcessInstance(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl())));
  }

  /**
   * Method under test:
   * {@link RuntimeServiceImpl#createProcessInstance(ProcessInstanceBuilderImpl)}
   */
  @Test
  public void testCreateProcessInstance() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> runtimeServiceImpl.createProcessInstance(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl())));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link RuntimeServiceImpl}
   */
  @Test
  public void testNewRuntimeServiceImpl() {
    // Arrange, Act and Assert
    assertNull((new RuntimeServiceImpl()).getCommandExecutor());
  }
}
