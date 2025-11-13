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
package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.api.process.model.IntegrationContext;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.runtime.shared.security.PrincipalGroupsProvider;
import org.activiti.api.runtime.shared.security.PrincipalRolesProvider;
import org.activiti.api.runtime.shared.security.SecurityContextPrincipalProvider;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.core.common.spring.security.AuthenticationPrincipalIdentityProvider;
import org.activiti.core.common.spring.security.LocalSpringSecurityManager;
import org.activiti.core.common.spring.security.policies.ProcessSecurityPoliciesManagerImpl;
import org.activiti.core.common.spring.security.policies.SecurityPoliciesProcessDefinitionRestrictionApplier;
import org.activiti.core.common.spring.security.policies.SecurityPoliciesProcessInstanceRestrictionApplier;
import org.activiti.core.common.spring.security.policies.conf.SecurityPoliciesProperties;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.runtime.api.conf.impl.ProcessRuntimeConfigurationImpl;
import org.activiti.runtime.api.impl.ExpressionResolver;
import org.activiti.runtime.api.impl.ProcessRuntimeImpl;
import org.activiti.runtime.api.impl.ProcessVariablesPayloadValidator;
import org.activiti.runtime.api.impl.VariableNameValidator;
import org.activiti.runtime.api.model.impl.APIDeploymentConverter;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.activiti.spring.process.ProcessExtensionResourceReader;
import org.activiti.spring.process.ProcessExtensionService;
import org.activiti.spring.process.variable.VariableValidationService;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

class DemoApplicationDiffblueTest {
  /**
   * Test {@link DemoApplication#processFile(String)}.
   *
   * <ul>
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#processFile(String)}
   */
  @Test
  @DisplayName("Test processFile(String); then return a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String DemoApplication.processFile(String)"})
  void testProcessFile_thenReturnAString() {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.start(Mockito.<StartProcessPayload>any()))
        .thenReturn(new ProcessInstanceImpl());

    // Act
    String actualProcessFileResult =
        new DemoApplication(processRuntime).processFile("Not all who wander are lost");

    // Assert
    verify(processRuntime).start(isA(StartProcessPayload.class));
    assertEquals(
        ">>> Created Process Instance: ProcessInstance{id='null', name='null', processDefinitionId='null',"
            + " processDefinitionKey='null', parentId='null', initiator='null', startDate=null, completedDate=null,"
            + " businessKey='null', status=null, processDefinitionVersion='null', processDefinitionName='null'}",
        actualProcessFileResult);
  }

  /**
   * Test {@link DemoApplication#getProcessDefinition()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#getProcessDefinition()}
   */
  @Test
  @DisplayName("Test getProcessDefinition(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List DemoApplication.getProcessDefinition()"})
  void testGetProcessDefinition_thenReturnEmpty() {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    // Act
    List<ProcessDefinition> actualProcessDefinition =
        new DemoApplication(processRuntime).getProcessDefinition();

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    assertTrue(actualProcessDefinition.isEmpty());
  }

  /**
   * Test {@link DemoApplication#processTextConnector()}.
   *
   * <p>Method under test: {@link DemoApplication#processTextConnector()}
   */
  @Test
  @DisplayName("Test processTextConnector()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Connector DemoApplication.processTextConnector()"})
  void testProcessTextConnector() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter processDefinitionConverter =
        new APIProcessDefinitionConverter(new RepositoryServiceImpl());
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityContextPrincipalProvider securityContextPrincipalProvider =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));
    SecurityPoliciesProperties securityPoliciesProperties = new SecurityPoliciesProperties();
    SecurityPoliciesProcessDefinitionRestrictionApplier processDefinitionRestrictionApplier =
        new SecurityPoliciesProcessDefinitionRestrictionApplier();

    ProcessSecurityPoliciesManagerImpl securityPoliciesManager =
        new ProcessSecurityPoliciesManagerImpl(
            securityManager,
            securityPoliciesProperties,
            processDefinitionRestrictionApplier,
            new SecurityPoliciesProcessInstanceRestrictionApplier());
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    APIDeploymentConverter deploymentConverter = new APIDeploymentConverter();
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();
    ProcessRuntimeConfigurationImpl configuration =
        new ProcessRuntimeConfigurationImpl(processRuntimeEventListeners, new ArrayList<>());
    ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    ProcessExtensionService processExtensionService =
        new ProcessExtensionService(
            new DeploymentResourceLoader<>(), mock(ProcessExtensionResourceReader.class));
    VariableValidationService variableValidationService =
        new VariableValidationService(new HashMap<>());
    VariableNameValidator variableNameValidator = new VariableNameValidator();
    ExpressionManager expressionManager = new ExpressionManager();
    JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();

    ExpressionResolver expressionResolver =
        new ExpressionResolver(expressionManager, mapper, mock(DelegateInterceptor.class));

    ProcessVariablesPayloadValidator processVariablesValidator =
        new ProcessVariablesPayloadValidator(
            dateFormatterProvider,
            processExtensionService,
            variableValidationService,
            variableNameValidator,
            expressionResolver);
    SecurityContextPrincipalProvider securityContextPrincipalProvider2 =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager2 =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider2,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));

    ProcessRuntimeImpl processRuntime =
        new ProcessRuntimeImpl(
            repositoryService,
            processDefinitionConverter,
            runtimeService,
            taskService,
            securityPoliciesManager,
            processInstanceConverter,
            variableInstanceConverter,
            deploymentConverter,
            configuration,
            eventPublisher,
            processVariablesValidator,
            securityManager2);

    // Act
    Connector actualProcessTextConnectorResult =
        new DemoApplication(processRuntime).processTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariable("fileContent", "Value");
    IntegrationContext actualApplyResult =
        actualProcessTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertNull(integrationContextImpl.getProcessDefinitionVersion());
    assertNull(integrationContextImpl.getAppVersion());
    assertNull(integrationContextImpl.getBusinessKey());
    assertNull(integrationContextImpl.getClientId());
    assertNull(integrationContextImpl.getClientName());
    assertNull(integrationContextImpl.getClientType());
    assertNull(integrationContextImpl.getConnectorType());
    assertNull(integrationContextImpl.getExecutionId());
    assertNull(integrationContextImpl.getParentProcessInstanceId());
    assertNull(integrationContextImpl.getProcessDefinitionId());
    assertNull(integrationContextImpl.getProcessDefinitionKey());
    assertNull(integrationContextImpl.getProcessInstanceId());
    assertNull(integrationContextImpl.getRootProcessInstanceId());
    assertSame(integrationContextImpl, actualApplyResult);
  }

  /**
   * Test {@link DemoApplication#processTextConnector()}.
   *
   * <p>Method under test: {@link DemoApplication#processTextConnector()}
   */
  @Test
  @DisplayName("Test processTextConnector()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Connector DemoApplication.processTextConnector()"})
  void testProcessTextConnector2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter processDefinitionConverter =
        new APIProcessDefinitionConverter(new RepositoryServiceImpl());
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityContextPrincipalProvider securityContextPrincipalProvider =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));
    SecurityPoliciesProperties securityPoliciesProperties = new SecurityPoliciesProperties();
    SecurityPoliciesProcessDefinitionRestrictionApplier processDefinitionRestrictionApplier =
        new SecurityPoliciesProcessDefinitionRestrictionApplier();

    ProcessSecurityPoliciesManagerImpl securityPoliciesManager =
        new ProcessSecurityPoliciesManagerImpl(
            securityManager,
            securityPoliciesProperties,
            processDefinitionRestrictionApplier,
            new SecurityPoliciesProcessInstanceRestrictionApplier());
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    APIDeploymentConverter deploymentConverter = new APIDeploymentConverter();
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();
    ProcessRuntimeConfigurationImpl configuration =
        new ProcessRuntimeConfigurationImpl(processRuntimeEventListeners, new ArrayList<>());
    ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    ProcessExtensionService processExtensionService =
        new ProcessExtensionService(
            new DeploymentResourceLoader<>(), mock(ProcessExtensionResourceReader.class));
    VariableValidationService variableValidationService =
        new VariableValidationService(new HashMap<>());
    VariableNameValidator variableNameValidator = new VariableNameValidator();
    ExpressionManager expressionManager = new ExpressionManager();
    JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();

    ExpressionResolver expressionResolver =
        new ExpressionResolver(expressionManager, mapper, mock(DelegateInterceptor.class));

    ProcessVariablesPayloadValidator processVariablesValidator =
        new ProcessVariablesPayloadValidator(
            dateFormatterProvider,
            processExtensionService,
            variableValidationService,
            variableNameValidator,
            expressionResolver);
    SecurityContextPrincipalProvider securityContextPrincipalProvider2 =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager2 =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider2,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));

    ProcessRuntimeImpl processRuntime =
        new ProcessRuntimeImpl(
            repositoryService,
            processDefinitionConverter,
            runtimeService,
            taskService,
            securityPoliciesManager,
            processInstanceConverter,
            variableInstanceConverter,
            deploymentConverter,
            configuration,
            eventPublisher,
            processVariablesValidator,
            securityManager2);

    // Act
    Connector actualProcessTextConnectorResult =
        new DemoApplication(processRuntime).processTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariable("fileContent", "activiti");
    IntegrationContext actualApplyResult =
        actualProcessTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertNull(integrationContextImpl.getProcessDefinitionVersion());
    assertNull(integrationContextImpl.getAppVersion());
    assertNull(integrationContextImpl.getBusinessKey());
    assertNull(integrationContextImpl.getClientId());
    assertNull(integrationContextImpl.getClientName());
    assertNull(integrationContextImpl.getClientType());
    assertNull(integrationContextImpl.getConnectorType());
    assertNull(integrationContextImpl.getExecutionId());
    assertNull(integrationContextImpl.getParentProcessInstanceId());
    assertNull(integrationContextImpl.getProcessDefinitionId());
    assertNull(integrationContextImpl.getProcessDefinitionKey());
    assertNull(integrationContextImpl.getProcessInstanceId());
    assertNull(integrationContextImpl.getRootProcessInstanceId());
    assertSame(integrationContextImpl, actualApplyResult);
  }

  /**
   * Test {@link DemoApplication#tagTextConnector()}.
   *
   * <p>Method under test: {@link DemoApplication#tagTextConnector()}
   */
  @Test
  @DisplayName("Test tagTextConnector()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Connector DemoApplication.tagTextConnector()"})
  void testTagTextConnector() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter processDefinitionConverter =
        new APIProcessDefinitionConverter(new RepositoryServiceImpl());
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityContextPrincipalProvider securityContextPrincipalProvider =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));
    SecurityPoliciesProperties securityPoliciesProperties = new SecurityPoliciesProperties();
    SecurityPoliciesProcessDefinitionRestrictionApplier processDefinitionRestrictionApplier =
        new SecurityPoliciesProcessDefinitionRestrictionApplier();

    ProcessSecurityPoliciesManagerImpl securityPoliciesManager =
        new ProcessSecurityPoliciesManagerImpl(
            securityManager,
            securityPoliciesProperties,
            processDefinitionRestrictionApplier,
            new SecurityPoliciesProcessInstanceRestrictionApplier());
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    APIDeploymentConverter deploymentConverter = new APIDeploymentConverter();
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();
    ProcessRuntimeConfigurationImpl configuration =
        new ProcessRuntimeConfigurationImpl(processRuntimeEventListeners, new ArrayList<>());
    ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    ProcessExtensionService processExtensionService =
        new ProcessExtensionService(
            new DeploymentResourceLoader<>(), mock(ProcessExtensionResourceReader.class));
    VariableValidationService variableValidationService =
        new VariableValidationService(new HashMap<>());
    VariableNameValidator variableNameValidator = new VariableNameValidator();
    ExpressionManager expressionManager = new ExpressionManager();
    JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();

    ExpressionResolver expressionResolver =
        new ExpressionResolver(expressionManager, mapper, mock(DelegateInterceptor.class));

    ProcessVariablesPayloadValidator processVariablesValidator =
        new ProcessVariablesPayloadValidator(
            dateFormatterProvider,
            processExtensionService,
            variableValidationService,
            variableNameValidator,
            expressionResolver);
    SecurityContextPrincipalProvider securityContextPrincipalProvider2 =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager2 =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider2,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));

    ProcessRuntimeImpl processRuntime =
        new ProcessRuntimeImpl(
            repositoryService,
            processDefinitionConverter,
            runtimeService,
            taskService,
            securityPoliciesManager,
            processInstanceConverter,
            variableInstanceConverter,
            deploymentConverter,
            configuration,
            eventPublisher,
            processVariablesValidator,
            securityManager2);

    // Act
    Connector actualTagTextConnectorResult = new DemoApplication(processRuntime).tagTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    IntegrationContext actualApplyResult =
        actualTagTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertSame(integrationContextImpl, actualApplyResult);
  }

  /**
   * Test {@link DemoApplication#discardTextConnector()}.
   *
   * <p>Method under test: {@link DemoApplication#discardTextConnector()}
   */
  @Test
  @DisplayName("Test discardTextConnector()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Connector DemoApplication.discardTextConnector()"})
  void testDiscardTextConnector() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter processDefinitionConverter =
        new APIProcessDefinitionConverter(new RepositoryServiceImpl());
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityContextPrincipalProvider securityContextPrincipalProvider =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));
    SecurityPoliciesProperties securityPoliciesProperties = new SecurityPoliciesProperties();
    SecurityPoliciesProcessDefinitionRestrictionApplier processDefinitionRestrictionApplier =
        new SecurityPoliciesProcessDefinitionRestrictionApplier();

    ProcessSecurityPoliciesManagerImpl securityPoliciesManager =
        new ProcessSecurityPoliciesManagerImpl(
            securityManager,
            securityPoliciesProperties,
            processDefinitionRestrictionApplier,
            new SecurityPoliciesProcessInstanceRestrictionApplier());
    APIProcessInstanceConverter processInstanceConverter = new APIProcessInstanceConverter();
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    APIDeploymentConverter deploymentConverter = new APIDeploymentConverter();
    ArrayList<ProcessRuntimeEventListener<?>> processRuntimeEventListeners = new ArrayList<>();
    ProcessRuntimeConfigurationImpl configuration =
        new ProcessRuntimeConfigurationImpl(processRuntimeEventListeners, new ArrayList<>());
    ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    ProcessExtensionService processExtensionService =
        new ProcessExtensionService(
            new DeploymentResourceLoader<>(), mock(ProcessExtensionResourceReader.class));
    VariableValidationService variableValidationService =
        new VariableValidationService(new HashMap<>());
    VariableNameValidator variableNameValidator = new VariableNameValidator();
    ExpressionManager expressionManager = new ExpressionManager();
    JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();

    ExpressionResolver expressionResolver =
        new ExpressionResolver(expressionManager, mapper, mock(DelegateInterceptor.class));

    ProcessVariablesPayloadValidator processVariablesValidator =
        new ProcessVariablesPayloadValidator(
            dateFormatterProvider,
            processExtensionService,
            variableValidationService,
            variableNameValidator,
            expressionResolver);
    SecurityContextPrincipalProvider securityContextPrincipalProvider2 =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager2 =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider2,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));

    ProcessRuntimeImpl processRuntime =
        new ProcessRuntimeImpl(
            repositoryService,
            processDefinitionConverter,
            runtimeService,
            taskService,
            securityPoliciesManager,
            processInstanceConverter,
            variableInstanceConverter,
            deploymentConverter,
            configuration,
            eventPublisher,
            processVariablesValidator,
            securityManager2);

    // Act
    Connector actualDiscardTextConnectorResult =
        new DemoApplication(processRuntime).discardTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    IntegrationContext actualApplyResult =
        actualDiscardTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertSame(integrationContextImpl, actualApplyResult);
  }
}
