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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
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
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RepositoryServiceImplDiffblueTest {
  /**
   * Test {@link RepositoryServiceImpl#createDeployment()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#createDeployment()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder RepositoryServiceImpl.createDeployment()"})
  public void testCreateDeployment_thenReturnNull() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<DeploymentBuilder>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    DeploymentBuilder actualCreateDeploymentResult = repositoryServiceImpl.createDeployment();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertNull(actualCreateDeploymentResult);
  }

  /**
   * Test {@link RepositoryServiceImpl#deleteDeployment(String, boolean)} with {@code deploymentId},
   * {@code cascade}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#deleteDeployment(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.deleteDeployment(String, boolean)"})
  public void testDeleteDeploymentWithDeploymentIdCascade_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteDeployment("42", true);

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#deleteDeployment(String)} with {@code deploymentId}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#deleteDeployment(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.deleteDeployment(String)"})
  public void testDeleteDeploymentWithDeploymentId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteDeployment("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#setDeploymentCategory(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#setDeploymentCategory(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.setDeploymentCategory(String, String)"})
  public void testSetDeploymentCategory_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.setDeploymentCategory("42", "Category");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#setDeploymentKey(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#setDeploymentKey(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.setDeploymentKey(String, String)"})
  public void testSetDeploymentKey_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.setDeploymentKey("42", "Key");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#deleteDeploymentCascade(String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#deleteDeploymentCascade(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.deleteDeploymentCascade(String)"})
  public void testDeleteDeploymentCascade_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteDeploymentCascade("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#createProcessDefinitionQuery()}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#createProcessDefinitionQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessDefinitionQuery RepositoryServiceImpl.createProcessDefinitionQuery()"})
  public void testCreateProcessDefinitionQuery() {
    // Arrange and Act
    ProcessDefinitionQuery actualCreateProcessDefinitionQueryResult =
        new RepositoryServiceImpl().createProcessDefinitionQuery();

    // Assert
    assertTrue(actualCreateProcessDefinitionQueryResult instanceof ProcessDefinitionQueryImpl);
    assertEquals(
        "RES.ID_ asc",
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getOrderBy());
    assertEquals(
        "RES.ID_ asc",
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult)
            .getOrderByColumns());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getVersion());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getVersionGt());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getVersionGte());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getVersionLt());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getVersionLte());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getParameter());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getDatabaseType());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult)
            .getAuthorizationUserId());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getCategory());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getCategoryLike());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult)
            .getCategoryNotEquals());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getDeploymentId());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult)
            .getEventSubscriptionName());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult)
            .getEventSubscriptionType());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getId());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getIdOrKey());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getKey());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getKeyLike());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getName());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getNameLike());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getProcDefId());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getResourceName());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult)
            .getResourceNameLike());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getTenantId());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getTenantIdLike());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).orderBy);
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult)
            .getAuthorizationGroups());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getDeploymentIds());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getIds());
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getKeys());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult)
            .nullHandlingOnOrder);
    assertNull(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).resultType);
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).commandContext);
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).commandExecutor);
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult)
            .getSuspensionState());
    assertNull(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).orderProperty);
    assertEquals(
        0,
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getFirstResult());
    assertEquals(
        1, ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getFirstRow());
    assertFalse(((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).isLatest());
    assertFalse(
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult)
            .isWithoutTenantId());
    assertEquals(
        Integer.MAX_VALUE,
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getLastRow());
    assertEquals(
        Integer.MAX_VALUE,
        ((ProcessDefinitionQueryImpl) actualCreateProcessDefinitionQueryResult).getMaxResults());
  }

  /**
   * Test {@link RepositoryServiceImpl#createNativeProcessDefinitionQuery()}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#createNativeProcessDefinitionQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "NativeProcessDefinitionQuery RepositoryServiceImpl.createNativeProcessDefinitionQuery()"
  })
  public void testCreateNativeProcessDefinitionQuery() {
    // Arrange and Act
    NativeProcessDefinitionQuery actualCreateNativeProcessDefinitionQueryResult =
        new RepositoryServiceImpl().createNativeProcessDefinitionQuery();

    // Assert
    assertTrue(
        actualCreateNativeProcessDefinitionQueryResult instanceof NativeProcessDefinitionQueryImpl);
    assertNull(
        ((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult)
            .resultType);
    assertNull(
        ((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult)
            .commandContext);
    assertNull(
        ((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult)
            .commandExecutor);
    assertEquals(
        0,
        ((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult)
            .firstResult);
    assertTrue(
        ((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult)
            .getParameters()
            .isEmpty());
    assertEquals(
        Integer.MAX_VALUE,
        ((NativeProcessDefinitionQueryImpl) actualCreateNativeProcessDefinitionQueryResult)
            .maxResults);
  }

  /**
   * Test {@link RepositoryServiceImpl#getDeploymentResourceNames(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#getDeploymentResourceNames(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List RepositoryServiceImpl.getDeploymentResourceNames(String)"})
  public void testGetDeploymentResourceNames_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<List<Object>>>any()))
        .thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<String> actualDeploymentResourceNames =
        repositoryServiceImpl.getDeploymentResourceNames("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualDeploymentResourceNames.isEmpty());
  }

  /**
   * Test {@link RepositoryServiceImpl#getResourceAsStream(String, String)}.
   *
   * <ul>
   *   <li>Then return read is eight.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#getResourceAsStream(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"InputStream RepositoryServiceImpl.getResourceAsStream(String, String)"})
  public void testGetResourceAsStream_thenReturnReadIsEight() throws IOException {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<InputStream>>any()))
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    InputStream actualResourceAsStream =
        repositoryServiceImpl.getResourceAsStream("42", "Resource Name");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    byte[] byteArray = new byte[8];
    assertEquals(8, actualResourceAsStream.read(byteArray));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link RepositoryServiceImpl#changeDeploymentTenantId(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#changeDeploymentTenantId(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.changeDeploymentTenantId(String, String)"})
  public void testChangeDeploymentTenantId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.changeDeploymentTenantId("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#createDeploymentQuery()}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#createDeploymentQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQuery RepositoryServiceImpl.createDeploymentQuery()"})
  public void testCreateDeploymentQuery() {
    // Arrange and Act
    DeploymentQuery actualCreateDeploymentQueryResult =
        new RepositoryServiceImpl().createDeploymentQuery();

    // Assert
    assertTrue(actualCreateDeploymentQueryResult instanceof DeploymentQueryImpl);
    assertEquals(
        "RES.ID_ asc", ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getOrderBy());
    assertEquals(
        "RES.ID_ asc",
        ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getOrderByColumns());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getParameter());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getDatabaseType());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getCategory());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getCategoryNotEquals());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getDeploymentId());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getName());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getNameLike());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getProcessDefinitionKey());
    assertNull(
        ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getProcessDefinitionKeyLike());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getTenantId());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getTenantIdLike());
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).orderBy);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).categoryLike);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).key);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).keyLike);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).nullHandlingOnOrder);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).resultType);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).commandContext);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).commandExecutor);
    assertNull(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).orderProperty);
    assertEquals(0, ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getFirstResult());
    assertEquals(1, ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getFirstRow());
    assertFalse(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).isLatestVersion());
    assertFalse(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).isWithoutTenantId());
    assertFalse(((DeploymentQueryImpl) actualCreateDeploymentQueryResult).latest);
    assertEquals(
        Integer.MAX_VALUE, ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getLastRow());
    assertEquals(
        Integer.MAX_VALUE,
        ((DeploymentQueryImpl) actualCreateDeploymentQueryResult).getMaxResults());
  }

  /**
   * Test {@link RepositoryServiceImpl#createNativeDeploymentQuery()}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#createNativeDeploymentQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"NativeDeploymentQuery RepositoryServiceImpl.createNativeDeploymentQuery()"})
  public void testCreateNativeDeploymentQuery() {
    // Arrange and Act
    NativeDeploymentQuery actualCreateNativeDeploymentQueryResult =
        new RepositoryServiceImpl().createNativeDeploymentQuery();

    // Assert
    assertTrue(actualCreateNativeDeploymentQueryResult instanceof NativeDeploymentQueryImpl);
    assertNull(((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).resultType);
    assertNull(
        ((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).commandContext);
    assertNull(
        ((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).commandExecutor);
    assertEquals(
        0, ((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).firstResult);
    assertTrue(
        ((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult)
            .getParameters()
            .isEmpty());
    assertEquals(
        Integer.MAX_VALUE,
        ((NativeDeploymentQueryImpl) actualCreateNativeDeploymentQueryResult).maxResults);
  }

  /**
   * Test {@link RepositoryServiceImpl#getProcessDefinition(String)}.
   *
   * <ul>
   *   <li>Then return {@link ProcessDefinitionEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#getProcessDefinition(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessDefinition RepositoryServiceImpl.getProcessDefinition(String)"})
  public void testGetProcessDefinition_thenReturnProcessDefinitionEntityImpl() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessDefinition>>any()))
        .thenReturn(processDefinitionEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

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
   *
   * <ul>
   *   <li>Then return createOneTaskBpmnModel.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#getBpmnModel(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnModel RepositoryServiceImpl.getBpmnModel(String)"})
  public void testGetBpmnModel_thenReturnCreateOneTaskBpmnModel() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    BpmnModel createOneTaskBpmnModelResult = TestProcessUtil.createOneTaskBpmnModel();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<BpmnModel>>any()))
        .thenReturn(createOneTaskBpmnModelResult);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    BpmnModel actualBpmnModel = repositoryServiceImpl.getBpmnModel("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createOneTaskBpmnModelResult, actualBpmnModel);
  }

  /**
   * Test {@link RepositoryServiceImpl#getDeployedProcessDefinition(String)}.
   *
   * <ul>
   *   <li>Then return {@link ProcessDefinitionEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#getDeployedProcessDefinition(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinition RepositoryServiceImpl.getDeployedProcessDefinition(String)"
  })
  public void testGetDeployedProcessDefinition_thenReturnProcessDefinitionEntityImpl() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ProcessDefinition>>any()))
        .thenReturn(processDefinitionEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    ProcessDefinition actualDeployedProcessDefinition =
        repositoryServiceImpl.getDeployedProcessDefinition("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(processDefinitionEntityImpl, actualDeployedProcessDefinition);
  }

  /**
   * Test {@link RepositoryServiceImpl#isProcessDefinitionSuspended(String)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#isProcessDefinitionSuspended(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RepositoryServiceImpl.isProcessDefinitionSuspended(String)"})
  public void testIsProcessDefinitionSuspended_thenReturnFalse() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Boolean>>any())).thenReturn(false);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    boolean actualIsProcessDefinitionSuspendedResult =
        repositoryServiceImpl.isProcessDefinitionSuspended("42");

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertFalse(actualIsProcessDefinitionSuspendedResult);
  }

  /**
   * Test {@link RepositoryServiceImpl#isProcessDefinitionSuspended(String)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#isProcessDefinitionSuspended(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RepositoryServiceImpl.isProcessDefinitionSuspended(String)"})
  public void testIsProcessDefinitionSuspended_thenReturnTrue() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Boolean>>any()))
        .thenReturn(true);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    boolean actualIsProcessDefinitionSuspendedResult =
        repositoryServiceImpl.isProcessDefinitionSuspended("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualIsProcessDefinitionSuspendedResult);
  }

  /**
   * Test {@link RepositoryServiceImpl#suspendProcessDefinitionById(String, boolean, Date)} with
   * {@code processDefinitionId}, {@code suspendProcessInstances}, {@code suspensionDate}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#suspendProcessDefinitionById(String,
   * boolean, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RepositoryServiceImpl.suspendProcessDefinitionById(String, boolean, Date)"
  })
  public void
      testSuspendProcessDefinitionByIdWithProcessDefinitionIdSuspendProcessInstancesSuspensionDate() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.suspendProcessDefinitionById(
        "42",
        true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#suspendProcessDefinitionById(String)} with {@code
   * processDefinitionId}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#suspendProcessDefinitionById(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.suspendProcessDefinitionById(String)"})
  public void testSuspendProcessDefinitionByIdWithProcessDefinitionId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.suspendProcessDefinitionById("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String, boolean, Date)} with
   * {@code processDefinitionKey}, {@code suspendProcessInstances}, {@code suspensionDate}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String,
   * boolean, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RepositoryServiceImpl.suspendProcessDefinitionByKey(String, boolean, Date)"
  })
  public void
      testSuspendProcessDefinitionByKeyWithProcessDefinitionKeySuspendProcessInstancesSuspensionDate() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.suspendProcessDefinitionByKey(
        "Process Definition Key",
        true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String, boolean, Date, String)}
   * with {@code processDefinitionKey}, {@code suspendProcessInstances}, {@code suspensionDate},
   * {@code tenantId}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String,
   * boolean, Date, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RepositoryServiceImpl.suspendProcessDefinitionByKey(String, boolean, Date, String)"
  })
  public void
      testSuspendProcessDefinitionByKeyWithProcessDefinitionKeySuspendProcessInstancesSuspensionDateTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.suspendProcessDefinitionByKey(
        "Process Definition Key",
        true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()),
        "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String, String)} with {@code
   * processDefinitionKey}, {@code tenantId}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.suspendProcessDefinitionByKey(String, String)"})
  public void testSuspendProcessDefinitionByKeyWithProcessDefinitionKeyTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.suspendProcessDefinitionByKey("Process Definition Key", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String)} with {@code
   * processDefinitionKey}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#suspendProcessDefinitionByKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.suspendProcessDefinitionByKey(String)"})
  public void testSuspendProcessDefinitionByKeyWithProcessDefinitionKey_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.suspendProcessDefinitionByKey("Process Definition Key");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#activateProcessDefinitionById(String, boolean, Date)} with
   * {@code processDefinitionId}, {@code activateProcessInstances}, {@code activationDate}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#activateProcessDefinitionById(String,
   * boolean, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RepositoryServiceImpl.activateProcessDefinitionById(String, boolean, Date)"
  })
  public void
      testActivateProcessDefinitionByIdWithProcessDefinitionIdActivateProcessInstancesActivationDate() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.activateProcessDefinitionById(
        "42",
        true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#activateProcessDefinitionById(String)} with {@code
   * processDefinitionId}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#activateProcessDefinitionById(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.activateProcessDefinitionById(String)"})
  public void testActivateProcessDefinitionByIdWithProcessDefinitionId_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.activateProcessDefinitionById("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String, boolean, Date)} with
   * {@code processDefinitionKey}, {@code activateProcessInstances}, {@code activationDate}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String,
   * boolean, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RepositoryServiceImpl.activateProcessDefinitionByKey(String, boolean, Date)"
  })
  public void
      testActivateProcessDefinitionByKeyWithProcessDefinitionKeyActivateProcessInstancesActivationDate() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.activateProcessDefinitionByKey(
        "Process Definition Key",
        true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String, boolean, Date,
   * String)} with {@code processDefinitionKey}, {@code activateProcessInstances}, {@code
   * activationDate}, {@code tenantId}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String,
   * boolean, Date, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RepositoryServiceImpl.activateProcessDefinitionByKey(String, boolean, Date, String)"
  })
  public void
      testActivateProcessDefinitionByKeyWithProcessDefinitionKeyActivateProcessInstancesActivationDateTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.activateProcessDefinitionByKey(
        "Process Definition Key",
        true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()),
        "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String, String)} with {@code
   * processDefinitionKey}, {@code tenantId}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.activateProcessDefinitionByKey(String, String)"})
  public void testActivateProcessDefinitionByKeyWithProcessDefinitionKeyTenantId() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.activateProcessDefinitionByKey("Process Definition Key", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String)} with {@code
   * processDefinitionKey}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#activateProcessDefinitionByKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.activateProcessDefinitionByKey(String)"})
  public void testActivateProcessDefinitionByKeyWithProcessDefinitionKey_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.activateProcessDefinitionByKey("Process Definition Key");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#setProcessDefinitionCategory(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#setProcessDefinitionCategory(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.setProcessDefinitionCategory(String, String)"})
  public void testSetProcessDefinitionCategory_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.setProcessDefinitionCategory("42", "Category");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#getProcessModel(String)}.
   *
   * <ul>
   *   <li>Then return read is eight.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#getProcessModel(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"InputStream RepositoryServiceImpl.getProcessModel(String)"})
  public void testGetProcessModel_thenReturnReadIsEight() throws IOException {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<InputStream>>any()))
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    InputStream actualProcessModel = repositoryServiceImpl.getProcessModel("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    byte[] byteArray = new byte[8];
    assertEquals(8, actualProcessModel.read(byteArray));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link RepositoryServiceImpl#newModel()}.
   *
   * <ul>
   *   <li>Then return {@link ModelEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#newModel()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Model RepositoryServiceImpl.newModel()"})
  public void testNewModel_thenReturnModelEntityImpl() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ModelEntityImpl modelEntityImpl = new ModelEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Model>>any()))
        .thenReturn(modelEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

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
   *
   * <ul>
   *   <li>Given {@link CommandContextInterceptor} {@link
   *       CommandContextInterceptor#execute(CommandConfig, Command)} return {@code null}.
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#deleteModel(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.deleteModel(String)"})
  public void testDeleteModel_givenCommandContextInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteModel("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#addModelEditorSource(String, byte[])}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#addModelEditorSource(String, byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.addModelEditorSource(String, byte[])"})
  public void testAddModelEditorSource_thenCallsExecute() throws UnsupportedEncodingException {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.addModelEditorSource("42", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#addModelEditorSourceExtra(String, byte[])}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#addModelEditorSourceExtra(String, byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.addModelEditorSourceExtra(String, byte[])"})
  public void testAddModelEditorSourceExtra_thenCallsExecute() throws UnsupportedEncodingException {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.addModelEditorSourceExtra("42", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#createModelQuery()}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#createModelQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ModelQuery RepositoryServiceImpl.createModelQuery()"})
  public void testCreateModelQuery() {
    // Arrange and Act
    ModelQuery actualCreateModelQueryResult = new RepositoryServiceImpl().createModelQuery();

    // Assert
    assertTrue(actualCreateModelQueryResult instanceof ModelQueryImpl);
    assertEquals("RES.ID_ asc", ((ModelQueryImpl) actualCreateModelQueryResult).getOrderBy());
    assertEquals(
        "RES.ID_ asc", ((ModelQueryImpl) actualCreateModelQueryResult).getOrderByColumns());
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
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).commandExecutor);
    assertNull(((ModelQueryImpl) actualCreateModelQueryResult).orderProperty);
    assertEquals(0, ((ModelQueryImpl) actualCreateModelQueryResult).getFirstResult());
    assertEquals(1, ((ModelQueryImpl) actualCreateModelQueryResult).getFirstRow());
    assertFalse(((ModelQueryImpl) actualCreateModelQueryResult).isDeployed());
    assertFalse(((ModelQueryImpl) actualCreateModelQueryResult).isLatest());
    assertFalse(((ModelQueryImpl) actualCreateModelQueryResult).isNotDeployed());
    assertFalse(((ModelQueryImpl) actualCreateModelQueryResult).isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, ((ModelQueryImpl) actualCreateModelQueryResult).getLastRow());
    assertEquals(
        Integer.MAX_VALUE, ((ModelQueryImpl) actualCreateModelQueryResult).getMaxResults());
  }

  /**
   * Test {@link RepositoryServiceImpl#createNativeModelQuery()}.
   *
   * <p>Method under test: {@link RepositoryServiceImpl#createNativeModelQuery()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"NativeModelQuery RepositoryServiceImpl.createNativeModelQuery()"})
  public void testCreateNativeModelQuery() {
    // Arrange and Act
    NativeModelQuery actualCreateNativeModelQueryResult =
        new RepositoryServiceImpl().createNativeModelQuery();

    // Assert
    assertTrue(actualCreateNativeModelQueryResult instanceof NativeModelQueryImpl);
    assertNull(((NativeModelQueryImpl) actualCreateNativeModelQueryResult).resultType);
    assertNull(((NativeModelQueryImpl) actualCreateNativeModelQueryResult).commandContext);
    assertNull(((NativeModelQueryImpl) actualCreateNativeModelQueryResult).commandExecutor);
    assertEquals(0, ((NativeModelQueryImpl) actualCreateNativeModelQueryResult).firstResult);
    assertTrue(
        ((NativeModelQueryImpl) actualCreateNativeModelQueryResult).getParameters().isEmpty());
    assertEquals(
        Integer.MAX_VALUE, ((NativeModelQueryImpl) actualCreateNativeModelQueryResult).maxResults);
  }

  /**
   * Test {@link RepositoryServiceImpl#getModel(String)}.
   *
   * <ul>
   *   <li>Then return {@link ModelEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#getModel(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Model RepositoryServiceImpl.getModel(String)"})
  public void testGetModel_thenReturnModelEntityImpl() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ModelEntityImpl modelEntityImpl = new ModelEntityImpl();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<ModelEntity>>any()))
        .thenReturn(modelEntityImpl);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

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
   *
   * <ul>
   *   <li>Then return {@code AXAXAXAX} Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#getModelEditorSource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] RepositoryServiceImpl.getModelEditorSource(String)"})
  public void testGetModelEditorSource_thenReturnAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<byte[]>>any()))
        .thenReturn("AXAXAXAX".getBytes("UTF-8"));
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    byte[] actualModelEditorSource = repositoryServiceImpl.getModelEditorSource("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualModelEditorSource);
  }

  /**
   * Test {@link RepositoryServiceImpl#getModelEditorSourceExtra(String)}.
   *
   * <ul>
   *   <li>Then return {@code AXAXAXAX} Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#getModelEditorSourceExtra(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] RepositoryServiceImpl.getModelEditorSourceExtra(String)"})
  public void testGetModelEditorSourceExtra_thenReturnAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<byte[]>>any()))
        .thenReturn("AXAXAXAX".getBytes("UTF-8"));
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    byte[] actualModelEditorSourceExtra = repositoryServiceImpl.getModelEditorSourceExtra("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualModelEditorSourceExtra);
  }

  /**
   * Test {@link RepositoryServiceImpl#addCandidateStarterUser(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#addCandidateStarterUser(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.addCandidateStarterUser(String, String)"})
  public void testAddCandidateStarterUser_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.addCandidateStarterUser("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#addCandidateStarterGroup(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#addCandidateStarterGroup(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.addCandidateStarterGroup(String, String)"})
  public void testAddCandidateStarterGroup_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.addCandidateStarterGroup("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#deleteCandidateStarterGroup(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#deleteCandidateStarterGroup(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.deleteCandidateStarterGroup(String, String)"})
  public void testDeleteCandidateStarterGroup_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteCandidateStarterGroup("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#deleteCandidateStarterUser(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContextInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryServiceImpl#deleteCandidateStarterUser(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.deleteCandidateStarterUser(String, String)"})
  public void testDeleteCandidateStarterUser_thenCallsExecute() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    repositoryServiceImpl.deleteCandidateStarterUser("42", "42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link RepositoryServiceImpl#getIdentityLinksForProcessDefinition(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * RepositoryServiceImpl#getIdentityLinksForProcessDefinition(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List RepositoryServiceImpl.getIdentityLinksForProcessDefinition(String)"})
  public void testGetIdentityLinksForProcessDefinition_thenReturnEmpty() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<List<IdentityLink>>>any()))
        .thenReturn(new ArrayList<>());
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    RepositoryServiceImpl repositoryServiceImpl = new RepositoryServiceImpl();
    repositoryServiceImpl.setCommandExecutor(commandExecutor);

    // Act
    List<IdentityLink> actualIdentityLinksForProcessDefinition =
        repositoryServiceImpl.getIdentityLinksForProcessDefinition("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertTrue(actualIdentityLinksForProcessDefinition.isEmpty());
  }

  /**
   * Test new {@link RepositoryServiceImpl} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link RepositoryServiceImpl}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RepositoryServiceImpl.<init>()"})
  public void testNewRepositoryServiceImpl() {
    // Arrange, Act and Assert
    assertNull(new RepositoryServiceImpl().getCommandExecutor());
  }
}
