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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.activiti.api.process.model.IntegrationContext;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.DefaultDirectoryScanner;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

@ExtendWith(MockitoExtension.class)
class DemoApplicationDiffblueTest {
  @InjectMocks private DemoApplication demoApplication;

  @Mock private ProcessRuntime processRuntime;

  @Mock private SecurityUtil securityUtil;

  /**
   * Test {@link DemoApplication#run(String[])}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ProcessDefinitionImpl} (default
   *       constructor).
   *   <li>Then calls {@link ProcessRuntime#processDefinitions(Pageable)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#run(String[])}
   */
  @Test
  @DisplayName(
      "Test run(String[]); given ArrayList() add ProcessDefinitionImpl (default constructor); then calls processDefinitions(Pageable)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.run(String[])"})
  void testRun_givenArrayListAddProcessDefinitionImpl_thenCallsProcessDefinitions() {
    // Arrange
    ArrayList<ProcessDefinition> content = new ArrayList<>();
    content.add(new ProcessDefinitionImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(content, 1000));
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    demoApplication.run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs("system");
  }

  /**
   * Test {@link DemoApplication#run(String[])}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessRuntime#processDefinitions(Pageable)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#run(String[])}
   */
  @Test
  @DisplayName("Test run(String[]); then calls processDefinitions(Pageable)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.run(String[])"})
  void testRun_thenCallsProcessDefinitions() {
    // Arrange
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    demoApplication.run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs("system");
  }

  /**
   * Test {@link DemoApplication#fileReadingMessageSource()}.
   *
   * <p>Method under test: {@link DemoApplication#fileReadingMessageSource()}
   */
  @Test
  @DisplayName("Test fileReadingMessageSource()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MessageSource DemoApplication.fileReadingMessageSource()"})
  void testFileReadingMessageSource() {
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
    DemoApplication demoApplication = new DemoApplication(processRuntime, new SecurityUtil());

    // Act
    MessageSource<File> actualFileReadingMessageSourceResult =
        demoApplication.fileReadingMessageSource();
    Message<File> actualReceiveResult = actualFileReadingMessageSourceResult.receive();

    // Assert
    assertTrue(
        ((FileReadingMessageSource) actualFileReadingMessageSourceResult).getScanner()
            instanceof DefaultDirectoryScanner);
    assertTrue(actualFileReadingMessageSourceResult instanceof FileReadingMessageSource);
    assertTrue(actualReceiveResult instanceof GenericMessage);
    assertEquals(
        "file:inbound-channel-adapter",
        ((FileReadingMessageSource) actualFileReadingMessageSourceResult).getComponentType());
    assertNull(((FileReadingMessageSource) actualFileReadingMessageSourceResult).getBeanName());
    assertNull(
        ((FileReadingMessageSource) actualFileReadingMessageSourceResult).getComponentName());
    assertNull(((FileReadingMessageSource) actualFileReadingMessageSourceResult).getManagedName());
    assertNull(((FileReadingMessageSource) actualFileReadingMessageSourceResult).getManagedType());
    MessageHeaders headers = actualReceiveResult.getHeaders();
    assertEquals(5, headers.size());
    assertEquals(
        IntegrationPatternType.inbound_channel_adapter,
        actualFileReadingMessageSourceResult.getIntegrationPatternType());
    assertFalse(((FileReadingMessageSource) actualFileReadingMessageSourceResult).isRunning());
    assertFalse(
        ((FileReadingMessageSource) actualFileReadingMessageSourceResult).isUseWatchService());
    assertFalse(((FileReadingMessageSource) actualFileReadingMessageSourceResult).isObserved());
    assertTrue(
        ((FileReadingMessageSource) actualFileReadingMessageSourceResult).isLoggingEnabled());
    assertTrue(headers.containsKey("file_name"));
    assertTrue(headers.containsKey("file_originalFile"));
    assertTrue(headers.containsKey("file_relativePath"));
    assertTrue(headers.containsKey(MessageHeaders.ID));
    assertTrue(headers.containsKey(MessageHeaders.TIMESTAMP));
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
    DemoApplication demoApplication = new DemoApplication(processRuntime, new SecurityUtil());

    // Act
    demoApplication.processTextConnector();

    // Assert that nothing has changed
    assertTrue(demoApplication.fileChannel() instanceof DirectChannel);
    assertTrue(demoApplication.fileReadingMessageSource() instanceof FileReadingMessageSource);
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
    DemoApplication demoApplication = new DemoApplication(processRuntime, new SecurityUtil());

    // Act
    Connector actualProcessTextConnectorResult = demoApplication.processTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariable("content", "Value");
    IntegrationContext actualApplyResult =
        actualProcessTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertTrue(demoApplication.fileChannel() instanceof DirectChannel);
    assertTrue(demoApplication.fileReadingMessageSource() instanceof FileReadingMessageSource);
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
  void testProcessTextConnector3() {
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
    DemoApplication demoApplication = new DemoApplication(processRuntime, new SecurityUtil());

    // Act
    Connector actualProcessTextConnectorResult = demoApplication.processTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariable("content", "activiti");
    IntegrationContext actualApplyResult =
        actualProcessTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertTrue(demoApplication.fileChannel() instanceof DirectChannel);
    assertTrue(demoApplication.fileReadingMessageSource() instanceof FileReadingMessageSource);
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
    DemoApplication demoApplication = new DemoApplication(processRuntime, new SecurityUtil());

    // Act
    Connector actualTagTextConnectorResult = demoApplication.tagTextConnector();
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
    DemoApplication demoApplication = new DemoApplication(processRuntime, new SecurityUtil());

    // Act
    Connector actualDiscardTextConnectorResult = demoApplication.discardTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    IntegrationContext actualApplyResult =
        actualDiscardTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertSame(integrationContextImpl, actualApplyResult);
  }
}
