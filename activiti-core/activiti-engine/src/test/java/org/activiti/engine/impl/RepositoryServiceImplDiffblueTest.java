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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.transaction.TransactionManager;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.JtaRetryInterceptor;
import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.NativeDeploymentQuery;
import org.activiti.engine.repository.NativeModelQuery;
import org.activiti.engine.repository.NativeProcessDefinitionQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.IdentityLink;
import org.junit.Test;
import org.mockito.Mockito;

public class RepositoryServiceImplDiffblueTest {
  /**
   * Test {@link RepositoryServiceImpl#createDeployment()}.
   * <ul>
   *   <li>Given {@link CommandInterceptor}
   * {@link CommandInterceptor#execute(CommandConfig, Command)} return
   * {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#createDeployment()}
   */
  @Test
  public void testCreateDeployment_givenCommandInterceptorExecuteReturnNull_thenReturnNull() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    DeploymentBuilder actualCreateDeploymentResult = repositoryServiceImpl.createDeployment();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertNull(actualCreateDeploymentResult);
  }

  /**
   * Test {@link RepositoryServiceImpl#deleteDeployment(String, boolean)} with
   * {@code deploymentId}, {@code cascade}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#deleteDeployment(String, boolean)}
   */
  @Test
  public void testDeleteDeploymentWithDeploymentIdCascade_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteDeployment("42", true);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#deleteDeployment(String)} with
   * {@code deploymentId}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#deleteDeployment(String)}
   */
  @Test
  public void testDeleteDeploymentWithDeploymentId_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteDeployment("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#setDeploymentCategory(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#setDeploymentCategory(String, String)}
   */
  @Test
  public void testSetDeploymentCategory_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.setDeploymentCategory("42", "Category");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#setDeploymentKey(String, String)}.
   * <ul>
   *   <li>Given {@link CommandInterceptor}
   * {@link CommandInterceptor#execute(CommandConfig, Command)} return
   * {@link JSONObject#NULL}.</li>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#setDeploymentKey(String, String)}
   */
  @Test
  public void testSetDeploymentKey_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.setDeploymentKey("42", "Key");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#deleteDeploymentCascade(String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#deleteDeploymentCascade(String)}
   */
  @Test
  public void testDeleteDeploymentCascade_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteDeploymentCascade("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#createProcessDefinitionQuery()}.
   * <ul>
   *   <li>Then {@link AbstractQuery#commandExecutor} return
   * {@link CommandExecutorImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#createProcessDefinitionQuery()}
   */
  @Test
  public void testCreateProcessDefinitionQuery_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    repositoryServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    ProcessDefinitionQuery actualCreateProcessDefinitionQueryResult = repositoryServiceImpl
        .createProcessDefinitionQuery();

    // Assert
    assertTrue(actualCreateProcessDefinitionQueryResult instanceof ProcessDefinitionQueryImpl);
    CommandExecutor commandExecutor = ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractQuery<ProcessDefinitionQuery, ProcessDefinition>) actualCreateProcessDefinitionQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, repositoryServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link RepositoryServiceImpl#createProcessDefinitionQuery()}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#createProcessDefinitionQuery()}
   */
  @Test
  public void testCreateProcessDefinitionQuery_thenReturnOrderByIsResIdAsc() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();

    // Act
    ProcessDefinitionQuery actualCreateProcessDefinitionQueryResult = repositoryServiceImpl
        .createProcessDefinitionQuery();

    // Assert
    assertTrue(actualCreateProcessDefinitionQueryResult instanceof ProcessDefinitionQueryImpl);
    assertEquals("RES.ID_ asc", ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getOrderByColumns());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getVersion());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getVersionGt());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getVersionGte());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getVersionLt());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getVersionLte());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getParameter());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getDatabaseType());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getAuthorizationUserId());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getCategory());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getCategoryLike());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getCategoryNotEquals());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getDeploymentId());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getEventSubscriptionName());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getEventSubscriptionType());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getId());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getIdOrKey());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getKey());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getKeyLike());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getName());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getNameLike());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getProcDefId());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getResourceName());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getResourceNameLike());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getTenantId());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getTenantIdLike());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).orderBy);
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getAuthorizationGroups());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getDeploymentIds());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getIds());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getKeys());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).nullHandlingOnOrder);
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).resultType);
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).commandContext);
    assertNull(repositoryServiceImpl.getCommandExecutor());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).commandExecutor);
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getSuspensionState());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).orderProperty);
    assertEquals(0, ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getFirstResult());
    assertEquals(1, ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getFirstRow());
    assertFalse(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).isLatest());
    assertFalse(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE,
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE,
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getMaxResults());
  }

  /**
   * Test {@link RepositoryServiceImpl#createNativeProcessDefinitionQuery()}.
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#createNativeProcessDefinitionQuery()}
   */
  @Test
  public void testCreateNativeProcessDefinitionQuery() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    repositoryServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    NativeProcessDefinitionQuery actualCreateNativeProcessDefinitionQueryResult = repositoryServiceImpl
        .createNativeProcessDefinitionQuery();

    // Assert
    assertTrue(actualCreateNativeProcessDefinitionQueryResult instanceof NativeProcessDefinitionQueryImpl);
    CommandExecutor commandExecutor = ((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractNativeQuery<NativeProcessDefinitionQuery, ProcessDefinition>) actualCreateNativeProcessDefinitionQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, repositoryServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link RepositoryServiceImpl#createNativeProcessDefinitionQuery()}.
   * <ul>
   *   <li>Then return {@link AbstractNativeQuery#resultType} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#createNativeProcessDefinitionQuery()}
   */
  @Test
  public void testCreateNativeProcessDefinitionQuery_thenReturnResultTypeIsNull() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();

    // Act
    NativeProcessDefinitionQuery actualCreateNativeProcessDefinitionQueryResult = repositoryServiceImpl
        .createNativeProcessDefinitionQuery();

    // Assert
    assertTrue(actualCreateNativeProcessDefinitionQueryResult instanceof NativeProcessDefinitionQueryImpl);
    assertNull(((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult).resultType);
    assertNull(((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult).commandContext);
    assertNull(repositoryServiceImpl.getCommandExecutor());
    assertNull(((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult).commandExecutor);
    assertEquals(0, ((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult).firstResult);
    assertTrue(
        ((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult).maxResults);
  }

  /**
   * Test {@link RepositoryServiceImpl#getDeploymentResourceNames(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#getDeploymentResourceNames(String)}
   */
  @Test
  public void testGetDeploymentResourceNames_thenReturnEmpty() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<String> actualDeploymentResourceNames = repositoryServiceImpl.getDeploymentResourceNames("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualDeploymentResourceNames.isEmpty());
  }

  /**
   * Test {@link RepositoryServiceImpl#getResourceAsStream(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#getResourceAsStream(String, String)}
   */
  @Test
  public void testGetResourceAsStream_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(mock(DataInputStream.class));
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.getResourceAsStream("42", "Resource Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#changeDeploymentTenantId(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#changeDeploymentTenantId(String, String)}
   */
  @Test
  public void testChangeDeploymentTenantId_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.changeDeploymentTenantId("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#createDeploymentQuery()}.
   * <ul>
   *   <li>Given {@link RepositoryServiceImpl} (default constructor).</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#createDeploymentQuery()}
   */
  @Test
  public void testCreateDeploymentQuery_givenRepositoryServiceImpl_thenReturnOrderByIsResIdAsc() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();

    // Act
    DeploymentQuery actualCreateDeploymentQueryResult = repositoryServiceImpl.createDeploymentQuery();

    // Assert
    assertTrue(actualCreateDeploymentQueryResult instanceof DeploymentQueryImpl);
    assertEquals("RES.ID_ asc", ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getOrderByColumns());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getParameter());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getDatabaseType());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getCategory());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getCategoryNotEquals());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getDeploymentId());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getName());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getNameLike());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getProcessDefinitionKey());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getProcessDefinitionKeyLike());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getTenantId());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getTenantIdLike());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).orderBy);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).categoryLike);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).key);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).keyLike);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).nullHandlingOnOrder);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).resultType);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).commandContext);
    assertNull(repositoryServiceImpl.getCommandExecutor());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).commandExecutor);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).orderProperty);
    assertEquals(0, ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getFirstResult());
    assertEquals(1, ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getFirstRow());
    assertFalse(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).isLatestVersion());
    assertFalse(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).isWithoutTenantId());
    assertFalse(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).latest);
    assertEquals(Integer.MAX_VALUE, ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getMaxResults());
  }

  /**
   * Test {@link RepositoryServiceImpl#createDeploymentQuery()}.
   * <ul>
   *   <li>Then {@link AbstractQuery#commandExecutor} return
   * {@link CommandExecutorImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#createDeploymentQuery()}
   */
  @Test
  public void testCreateDeploymentQuery_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    repositoryServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    DeploymentQuery actualCreateDeploymentQueryResult = repositoryServiceImpl.createDeploymentQuery();

    // Assert
    assertTrue(actualCreateDeploymentQueryResult instanceof DeploymentQueryImpl);
    CommandExecutor commandExecutor = ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractQuery<DeploymentQuery, Deployment>) actualCreateDeploymentQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, repositoryServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link RepositoryServiceImpl#createNativeDeploymentQuery()}.
   * <ul>
   *   <li>Then {@link AbstractNativeQuery#commandExecutor} return
   * {@link CommandExecutorImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#createNativeDeploymentQuery()}
   */
  @Test
  public void testCreateNativeDeploymentQuery_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    repositoryServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    NativeDeploymentQuery actualCreateNativeDeploymentQueryResult = repositoryServiceImpl.createNativeDeploymentQuery();

    // Assert
    assertTrue(actualCreateNativeDeploymentQueryResult instanceof NativeDeploymentQueryImpl);
    CommandExecutor commandExecutor = ((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractNativeQuery<NativeDeploymentQuery, Deployment>) actualCreateNativeDeploymentQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, repositoryServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link RepositoryServiceImpl#createNativeDeploymentQuery()}.
   * <ul>
   *   <li>Then return {@link AbstractNativeQuery#resultType} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#createNativeDeploymentQuery()}
   */
  @Test
  public void testCreateNativeDeploymentQuery_thenReturnResultTypeIsNull() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();

    // Act
    NativeDeploymentQuery actualCreateNativeDeploymentQueryResult = repositoryServiceImpl.createNativeDeploymentQuery();

    // Assert
    assertTrue(actualCreateNativeDeploymentQueryResult instanceof NativeDeploymentQueryImpl);
    assertNull(((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).resultType);
    assertNull(((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).commandContext);
    assertNull(repositoryServiceImpl.getCommandExecutor());
    assertNull(((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).commandExecutor);
    assertEquals(0, ((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).firstResult);
    assertTrue(((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE, ((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).maxResults);
  }

  /**
   * Test {@link RepositoryServiceImpl#getProcessDefinition(String)}.
   * <ul>
   *   <li>Then return {@link ProcessDefinitionEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#getProcessDefinition(String)}
   */
  @Test
  public void testGetProcessDefinition_thenReturnProcessDefinitionEntityImpl() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(processDefinitionEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessDefinition actualProcessDefinition = repositoryServiceImpl.getProcessDefinition("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(processDefinitionEntityImpl, actualProcessDefinition);
  }

  /**
   * Test {@link RepositoryServiceImpl#getBpmnModel(String)}.
   * <ul>
   *   <li>Then return {@link BpmnModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#getBpmnModel(String)}
   */
  @Test
  public void testGetBpmnModel_thenReturnBpmnModel() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    BpmnModel bpmnModel = new BpmnModel();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(bpmnModel);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    BpmnModel actualBpmnModel = repositoryServiceImpl.getBpmnModel("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(bpmnModel, actualBpmnModel);
  }

  /**
   * Test {@link RepositoryServiceImpl#getDeployedProcessDefinition(String)}.
   * <ul>
   *   <li>Then return {@link ProcessDefinitionEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#getDeployedProcessDefinition(String)}
   */
  @Test
  public void testGetDeployedProcessDefinition_thenReturnProcessDefinitionEntityImpl() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(processDefinitionEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessDefinition actualDeployedProcessDefinition = repositoryServiceImpl.getDeployedProcessDefinition("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(processDefinitionEntityImpl, actualDeployedProcessDefinition);
  }

  /**
   * Test {@link RepositoryServiceImpl#isProcessDefinitionSuspended(String)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#isProcessDefinitionSuspended(String)}
   */
  @Test
  public void testIsProcessDefinitionSuspended_thenReturnFalse() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(false);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    boolean actualIsProcessDefinitionSuspendedResult = repositoryServiceImpl.isProcessDefinitionSuspended("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertFalse(actualIsProcessDefinitionSuspendedResult);
  }

  /**
   * Test {@link RepositoryServiceImpl#isProcessDefinitionSuspended(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#isProcessDefinitionSuspended(String)}
   */
  @Test
  public void testIsProcessDefinitionSuspended_thenReturnTrue() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(true);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    boolean actualIsProcessDefinitionSuspendedResult = repositoryServiceImpl.isProcessDefinitionSuspended("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualIsProcessDefinitionSuspendedResult);
  }

  /**
   * Test {@link RepositoryServiceImpl#suspendProcessDefinitionById(String)} with
   * {@code processDefinitionId}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#suspendProcessDefinitionById(String)}
   */
  @Test
  public void testSuspendProcessDefinitionByIdWithProcessDefinitionId_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.suspendProcessDefinitionById("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test
   * {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String, String)}
   * with {@code processDefinitionKey}, {@code tenantId}.
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String, String)}
   */
  @Test
  public void testSuspendProcessDefinitionByKeyWithProcessDefinitionKeyTenantId() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.suspendProcessDefinitionByKey("Process Definition Key", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String)} with
   * {@code processDefinitionKey}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String)}
   */
  @Test
  public void testSuspendProcessDefinitionByKeyWithProcessDefinitionKey_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.suspendProcessDefinitionByKey("Process Definition Key");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#activateProcessDefinitionById(String)} with
   * {@code processDefinitionId}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#activateProcessDefinitionById(String)}
   */
  @Test
  public void testActivateProcessDefinitionByIdWithProcessDefinitionId_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.activateProcessDefinitionById("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test
   * {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String, String)}
   * with {@code processDefinitionKey}, {@code tenantId}.
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String, String)}
   */
  @Test
  public void testActivateProcessDefinitionByKeyWithProcessDefinitionKeyTenantId() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.activateProcessDefinitionByKey("Process Definition Key", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String)}
   * with {@code processDefinitionKey}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String)}
   */
  @Test
  public void testActivateProcessDefinitionByKeyWithProcessDefinitionKey_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.activateProcessDefinitionByKey("Process Definition Key");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test
   * {@link RepositoryServiceImpl#setProcessDefinitionCategory(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#setProcessDefinitionCategory(String, String)}
   */
  @Test
  public void testSetProcessDefinitionCategory_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.setProcessDefinitionCategory("42", "Category");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#getProcessModel(String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#getProcessModel(String)}
   */
  @Test
  public void testGetProcessModel_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(mock(DataInputStream.class));
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.getProcessModel("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#newModel()}.
   * <ul>
   *   <li>Then return {@link ModelEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#newModel()}
   */
  @Test
  public void testNewModel_thenReturnModelEntityImpl() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    ModelEntityImpl modelEntityImpl = new ModelEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(modelEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Model actualNewModelResult = repositoryServiceImpl.newModel();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(modelEntityImpl, actualNewModelResult);
  }

  /**
   * Test {@link RepositoryServiceImpl#deleteModel(String)}.
   * <ul>
   *   <li>Given {@link CommandInterceptor}
   * {@link CommandInterceptor#execute(CommandConfig, Command)} return
   * {@link JSONObject#NULL}.</li>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#deleteModel(String)}
   */
  @Test
  public void testDeleteModel_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteModel("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#addModelEditorSource(String, byte[])}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#addModelEditorSource(String, byte[])}
   */
  @Test
  public void testAddModelEditorSource_thenCallsExecute() throws UnsupportedEncodingException {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.addModelEditorSource("42", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#addModelEditorSourceExtra(String, byte[])}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#addModelEditorSourceExtra(String, byte[])}
   */
  @Test
  public void testAddModelEditorSourceExtra_thenCallsExecute() throws UnsupportedEncodingException {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.addModelEditorSourceExtra("42", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#createModelQuery()}.
   * <ul>
   *   <li>Given {@link RepositoryServiceImpl} (default constructor).</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#createModelQuery()}
   */
  @Test
  public void testCreateModelQuery_givenRepositoryServiceImpl_thenReturnOrderByIsResIdAsc() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();

    // Act
    ModelQuery actualCreateModelQueryResult = repositoryServiceImpl.createModelQuery();

    // Assert
    assertTrue(actualCreateModelQueryResult instanceof ModelQueryImpl);
    assertEquals("RES.ID_ asc", ((ModelQueryImpl) actualCreateModelQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((ModelQueryImpl) actualCreateModelQueryResult).getOrderByColumns());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getVersion());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getParameter());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getDatabaseType());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getCategory());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getCategoryLike());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getCategoryNotEquals());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getDeploymentId());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getId());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getKey());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getName());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getNameLike());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getTenantId());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).getTenantIdLike());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).orderBy);
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).nullHandlingOnOrder);
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).resultType);
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).commandContext);
    assertNull(repositoryServiceImpl.getCommandExecutor());
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).commandExecutor);
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).orderProperty);
    assertEquals(0, ((ModelQueryImpl) actualCreateModelQueryResult).getFirstResult());
    assertEquals(1, ((ModelQueryImpl) actualCreateModelQueryResult).getFirstRow());
    assertFalse(((ModelQueryImpl) actualCreateModelQueryResult).isDeployed());
    assertFalse(((ModelQueryImpl) actualCreateModelQueryResult).isLatest());
    assertFalse(((ModelQueryImpl) actualCreateModelQueryResult).isNotDeployed());
    assertFalse(((ModelQueryImpl) actualCreateModelQueryResult).isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, ((ModelQueryImpl) actualCreateModelQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((ModelQueryImpl) actualCreateModelQueryResult).getMaxResults());
  }

  /**
   * Test {@link RepositoryServiceImpl#createModelQuery()}.
   * <ul>
   *   <li>Then {@link AbstractQuery#commandExecutor} return
   * {@link CommandExecutorImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#createModelQuery()}
   */
  @Test
  public void testCreateModelQuery_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    repositoryServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    ModelQuery actualCreateModelQueryResult = repositoryServiceImpl.createModelQuery();

    // Assert
    assertTrue(actualCreateModelQueryResult instanceof ModelQueryImpl);
    CommandExecutor commandExecutor = ((ModelQueryImpl) actualCreateModelQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractQuery<ModelQuery, Model>) actualCreateModelQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, repositoryServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link RepositoryServiceImpl#createNativeModelQuery()}.
   * <ul>
   *   <li>Given {@link RepositoryServiceImpl} (default constructor).</li>
   *   <li>Then return {@link AbstractNativeQuery#resultType} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#createNativeModelQuery()}
   */
  @Test
  public void testCreateNativeModelQuery_givenRepositoryServiceImpl_thenReturnResultTypeIsNull() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();

    // Act
    NativeModelQuery actualCreateNativeModelQueryResult = repositoryServiceImpl.createNativeModelQuery();

    // Assert
    assertTrue(actualCreateNativeModelQueryResult instanceof NativeModelQueryImpl);
    assertNull(((NativeModelQueryImpl) actualCreateNativeModelQueryResult).resultType);
    assertNull(((NativeModelQueryImpl) actualCreateNativeModelQueryResult).commandContext);
    assertNull(repositoryServiceImpl.getCommandExecutor());
    assertNull(((NativeModelQueryImpl) actualCreateNativeModelQueryResult).commandExecutor);
    assertEquals(0, ((NativeModelQueryImpl) actualCreateNativeModelQueryResult).firstResult);
    assertTrue(((NativeModelQueryImpl) actualCreateNativeModelQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE, ((NativeModelQueryImpl) actualCreateNativeModelQueryResult).maxResults);
  }

  /**
   * Test {@link RepositoryServiceImpl#createNativeModelQuery()}.
   * <ul>
   *   <li>Then {@link AbstractNativeQuery#commandExecutor} return
   * {@link CommandExecutorImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#createNativeModelQuery()}
   */
  @Test
  public void testCreateNativeModelQuery_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));
    repositoryServiceImpl.setCommandExecutor(new CommandExecutorImpl(defaultConfig, first));

    // Act
    NativeModelQuery actualCreateNativeModelQueryResult = repositoryServiceImpl.createNativeModelQuery();

    // Assert
    assertTrue(actualCreateNativeModelQueryResult instanceof NativeModelQueryImpl);
    CommandExecutor commandExecutor = ((NativeModelQueryImpl) actualCreateNativeModelQueryResult).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(defaultConfig, commandExecutor.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
    CommandExecutor expectedCommandExecutor = ((AbstractNativeQuery<NativeModelQuery, Model>) actualCreateNativeModelQueryResult).commandExecutor;
    assertSame(expectedCommandExecutor, repositoryServiceImpl.getCommandExecutor());
  }

  /**
   * Test {@link RepositoryServiceImpl#getModel(String)}.
   * <ul>
   *   <li>Then return {@link ModelEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#getModel(String)}
   */
  @Test
  public void testGetModel_thenReturnModelEntityImpl() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    ModelEntityImpl modelEntityImpl = new ModelEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(modelEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    Model actualModel = repositoryServiceImpl.getModel("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(modelEntityImpl, actualModel);
  }

  /**
   * Test {@link RepositoryServiceImpl#getModelEditorSource(String)}.
   * <ul>
   *   <li>Then return {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryServiceImpl#getModelEditorSource(String)}
   */
  @Test
  public void testGetModelEditorSource_thenReturnAxaxaxaxBytesIsUtf8() throws UnsupportedEncodingException {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<byte[]>>any())).thenReturn("AXAXAXAX".getBytes("UTF-8"));

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    byte[] actualModelEditorSource = repositoryServiceImpl.getModelEditorSource("42");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualModelEditorSource);
  }

  /**
   * Test {@link RepositoryServiceImpl#getModelEditorSourceExtra(String)}.
   * <ul>
   *   <li>Then return {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#getModelEditorSourceExtra(String)}
   */
  @Test
  public void testGetModelEditorSourceExtra_thenReturnAxaxaxaxBytesIsUtf8() throws UnsupportedEncodingException {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<byte[]>>any())).thenReturn("AXAXAXAX".getBytes("UTF-8"));

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    byte[] actualModelEditorSourceExtra = repositoryServiceImpl.getModelEditorSourceExtra("42");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualModelEditorSourceExtra);
  }

  /**
   * Test {@link RepositoryServiceImpl#addCandidateStarterUser(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#addCandidateStarterUser(String, String)}
   */
  @Test
  public void testAddCandidateStarterUser_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.addCandidateStarterUser("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#addCandidateStarterGroup(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#addCandidateStarterGroup(String, String)}
   */
  @Test
  public void testAddCandidateStarterGroup_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.addCandidateStarterGroup("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test
   * {@link RepositoryServiceImpl#deleteCandidateStarterGroup(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#deleteCandidateStarterGroup(String, String)}
   */
  @Test
  public void testDeleteCandidateStarterGroup_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteCandidateStarterGroup("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test
   * {@link RepositoryServiceImpl#deleteCandidateStarterUser(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommandInterceptor#execute(CommandConfig, Command)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#deleteCandidateStarterUser(String, String)}
   */
  @Test
  public void testDeleteCandidateStarterUser_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteCandidateStarterUser("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test
   * {@link RepositoryServiceImpl#getIdentityLinksForProcessDefinition(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RepositoryServiceImpl#getIdentityLinksForProcessDefinition(String)}
   */
  @Test
  public void testGetIdentityLinksForProcessDefinition_thenReturnEmpty() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(mock(CommandConfig.class), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<IdentityLink> actualIdentityLinksForProcessDefinition = repositoryServiceImpl
        .getIdentityLinksForProcessDefinition("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualIdentityLinksForProcessDefinition.isEmpty());
  }

  /**
   * Test new {@link RepositoryServiceImpl} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link RepositoryServiceImpl}
   */
  @Test
  public void testNewRepositoryServiceImpl() {
    // Arrange, Act and Assert
    assertNull((new RepositoryServiceImpl()).getCommandExecutor());
  }
}
