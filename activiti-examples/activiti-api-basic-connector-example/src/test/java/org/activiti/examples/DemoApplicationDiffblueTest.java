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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.HashMap;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.IntegrationContext;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.payloads.GetVariablesPayload;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.runtime.shared.security.PrincipalGroupsProvider;
import org.activiti.api.runtime.shared.security.PrincipalRolesProvider;
import org.activiti.api.runtime.shared.security.SecurityContextPrincipalProvider;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.GetTaskVariablesPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.core.common.spring.security.AuthenticationPrincipalIdentityProvider;
import org.activiti.core.common.spring.security.LocalSpringSecurityManager;
import org.activiti.core.common.spring.security.policies.ProcessSecurityPoliciesManagerImpl;
import org.activiti.core.common.spring.security.policies.SecurityPoliciesProcessDefinitionRestrictionApplier;
import org.activiti.core.common.spring.security.policies.SecurityPoliciesProcessInstanceRestrictionApplier;
import org.activiti.core.common.spring.security.policies.conf.SecurityPoliciesProperties;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.runtime.api.conf.impl.ProcessRuntimeConfigurationImpl;
import org.activiti.runtime.api.conf.impl.TaskRuntimeConfigurationImpl;
import org.activiti.runtime.api.impl.ExpressionResolver;
import org.activiti.runtime.api.impl.ProcessRuntimeImpl;
import org.activiti.runtime.api.impl.ProcessVariablesPayloadValidator;
import org.activiti.runtime.api.impl.TaskRuntimeHelper;
import org.activiti.runtime.api.impl.TaskRuntimeImpl;
import org.activiti.runtime.api.impl.TaskVariablesPayloadValidator;
import org.activiti.runtime.api.impl.VariableNameValidator;
import org.activiti.runtime.api.model.impl.APIDeploymentConverter;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.activiti.runtime.api.model.impl.APITaskConverter;
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

@ExtendWith(MockitoExtension.class)
class DemoApplicationDiffblueTest {
  @InjectMocks private DemoApplication demoApplication;

  @Mock private ProcessRuntime processRuntime;

  @Mock private SecurityUtil securityUtil;

  @Mock private TaskRuntime taskRuntime;

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ProcessDefinitionImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName(
      "Test commandLineRunner(); given ArrayList() add ProcessDefinitionImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"
  })
  void testCommandLineRunner_givenArrayListAddProcessDefinitionImpl() throws Exception {
    // Arrange
    ArrayList<ProcessDefinition> content = new ArrayList<>();
    content.add(new ProcessDefinitionImpl());
    PageImpl<ProcessDefinition> pageImpl = new PageImpl<>(content, 1000);
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any()))
        .thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any()))
        .thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any())).thenReturn(pageImpl);
    when(taskRuntime.tasks(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    String[] args = new String[] {"Args"};

    // Act
    demoApplication.commandLineRunner().run(args);

    // Assert that nothing has changed
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(securityUtil).logInAs("reviewer");
    assertArrayEquals(new String[] {"Args"}, args);
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TaskImpl#TaskImpl()}.
   *   <li>Then calls {@link TaskRuntime#complete(CompleteTaskPayload)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName(
      "Test commandLineRunner(); given ArrayList() add TaskImpl(); then calls complete(CompleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"
  })
  void testCommandLineRunner_givenArrayListAddTaskImpl_thenCallsComplete() throws Exception {
    // Arrange
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any()))
        .thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any()))
        .thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl());
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any()))
        .thenReturn(new ArrayList<>());
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    String[] args = new String[] {"Args"};

    // Act
    demoApplication.commandLineRunner().run(args);

    // Assert that nothing has changed
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).complete(isA(CompleteTaskPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime).variables(isA(GetTaskVariablesPayload.class));
    verify(securityUtil).logInAs("reviewer");
    assertArrayEquals(new String[] {"Args"}, args);
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TaskImpl#TaskImpl()}.
   *   <li>Then calls {@link TaskRuntime#complete(CompleteTaskPayload)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName(
      "Test commandLineRunner(); given ArrayList() add TaskImpl(); then calls complete(CompleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"
  })
  void testCommandLineRunner_givenArrayListAddTaskImpl_thenCallsComplete2() throws Exception {
    // Arrange
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any()))
        .thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any()))
        .thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl());
    content.add(new TaskImpl());
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any()))
        .thenReturn(new ArrayList<>());
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    String[] args = new String[] {"Args"};

    // Act
    demoApplication.commandLineRunner().run(args);

    // Assert that nothing has changed
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime, atLeast(1)).complete(Mockito.<CompleteTaskPayload>any());
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime, atLeast(1)).variables(Mockito.<GetTaskVariablesPayload>any());
    verify(securityUtil).logInAs("reviewer");
    assertArrayEquals(new String[] {"Args"}, args);
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       VariableInstanceImpl#VariableInstanceImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName("Test commandLineRunner(); given ArrayList() add VariableInstanceImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"
  })
  void testCommandLineRunner_givenArrayListAddVariableInstanceImpl() throws Exception {
    // Arrange
    ArrayList<VariableInstance> variableInstanceList = new ArrayList<>();
    variableInstanceList.add(new VariableInstanceImpl<>());
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any()))
        .thenReturn(variableInstanceList);
    when(processRuntime.start(Mockito.<StartProcessPayload>any()))
        .thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    when(taskRuntime.tasks(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    String[] args = new String[] {"Args"};

    // Act
    demoApplication.commandLineRunner().run(args);

    // Assert that nothing has changed
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(securityUtil).logInAs("reviewer");
    assertArrayEquals(new String[] {"Args"}, args);
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       VariableInstanceImpl#VariableInstanceImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName("Test commandLineRunner(); given ArrayList() add VariableInstanceImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"
  })
  void testCommandLineRunner_givenArrayListAddVariableInstanceImpl2() throws Exception {
    // Arrange
    ArrayList<VariableInstance> variableInstanceList = new ArrayList<>();
    variableInstanceList.add(new VariableInstanceImpl<>());
    variableInstanceList.add(new VariableInstanceImpl<>());
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any()))
        .thenReturn(variableInstanceList);
    when(processRuntime.start(Mockito.<StartProcessPayload>any()))
        .thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    when(taskRuntime.tasks(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    String[] args = new String[] {"Args"};

    // Act
    demoApplication.commandLineRunner().run(args);

    // Assert that nothing has changed
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(securityUtil).logInAs("reviewer");
    assertArrayEquals(new String[] {"Args"}, args);
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       VariableInstanceImpl#VariableInstanceImpl()}.
   *   <li>Then calls {@link TaskRuntime#complete(CompleteTaskPayload)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName(
      "Test commandLineRunner(); given ArrayList() add VariableInstanceImpl(); then calls complete(CompleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"
  })
  void testCommandLineRunner_givenArrayListAddVariableInstanceImpl_thenCallsComplete()
      throws Exception {
    // Arrange
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any()))
        .thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any()))
        .thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl());
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);

    ArrayList<VariableInstance> variableInstanceList = new ArrayList<>();
    variableInstanceList.add(new VariableInstanceImpl<>());
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any()))
        .thenReturn(variableInstanceList);
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    String[] args = new String[] {"Args"};

    // Act
    demoApplication.commandLineRunner().run(args);

    // Assert that nothing has changed
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).complete(isA(CompleteTaskPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime).variables(isA(GetTaskVariablesPayload.class));
    verify(securityUtil).logInAs("reviewer");
    assertArrayEquals(new String[] {"Args"}, args);
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       VariableInstanceImpl#VariableInstanceImpl()}.
   *   <li>Then calls {@link TaskRuntime#complete(CompleteTaskPayload)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName(
      "Test commandLineRunner(); given ArrayList() add VariableInstanceImpl(); then calls complete(CompleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"
  })
  void testCommandLineRunner_givenArrayListAddVariableInstanceImpl_thenCallsComplete2()
      throws Exception {
    // Arrange
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any()))
        .thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any()))
        .thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl());
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);

    ArrayList<VariableInstance> variableInstanceList = new ArrayList<>();
    variableInstanceList.add(new VariableInstanceImpl<>());
    variableInstanceList.add(new VariableInstanceImpl<>());
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any()))
        .thenReturn(variableInstanceList);
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    String[] args = new String[] {"Args"};

    // Act
    demoApplication.commandLineRunner().run(args);

    // Assert that nothing has changed
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).complete(isA(CompleteTaskPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime).variables(isA(GetTaskVariablesPayload.class));
    verify(securityUtil).logInAs("reviewer");
    assertArrayEquals(new String[] {"Args"}, args);
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   *
   * <ul>
   *   <li>Then array of {@link String} with {@code Args}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName("Test commandLineRunner(); then array of String with 'Args'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"
  })
  void testCommandLineRunner_thenArrayOfStringWithArgs() throws Exception {
    // Arrange
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any()))
        .thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any()))
        .thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    when(taskRuntime.tasks(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    String[] args = new String[] {"Args"};

    // Act
    demoApplication.commandLineRunner().run(args);

    // Assert that nothing has changed
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(securityUtil).logInAs("reviewer");
    assertArrayEquals(new String[] {"Args"}, args);
  }

  /**
   * Test {@link DemoApplication#getMovieDesc()}.
   *
   * <ul>
   *   <li>Then return apply {@link IntegrationContextImpl} (default constructor) is {@link
   *       IntegrationContextImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#getMovieDesc()}
   */
  @Test
  @DisplayName(
      "Test getMovieDesc(); then return apply IntegrationContextImpl (default constructor) is IntegrationContextImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Connector DemoApplication.getMovieDesc()"})
  void testGetMovieDesc_thenReturnApplyIntegrationContextImplIsIntegrationContextImpl() {
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
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityContextPrincipalProvider securityContextPrincipalProvider3 =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager3 =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider3,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter2 = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration2 =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());
    TaskServiceImpl taskService3 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 = new APITaskConverter(mock(TaskService.class));
    SecurityContextPrincipalProvider securityContextPrincipalProvider4 =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager4 =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider4,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));
    DateFormatterProvider dateFormatterProvider2 = mock(DateFormatterProvider.class);
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider2, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(
            taskService3, taskConverter2, securityManager4, taskVariablesValidator);

    TaskRuntimeImpl taskRuntime =
        new TaskRuntimeImpl(
            taskService2,
            securityManager3,
            taskConverter,
            variableInstanceConverter2,
            configuration2,
            taskRuntimeHelper);

    DemoApplication demoApplication =
        new DemoApplication(processRuntime, taskRuntime, new SecurityUtil());

    // Act
    Connector actualMovieDesc = demoApplication.getMovieDesc();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    IntegrationContext actualApplyResult = actualMovieDesc.apply(integrationContextImpl);

    // Assert
    assertSame(integrationContextImpl, actualApplyResult);
  }

  /**
   * Test {@link DemoApplication#getMovieDescUUIDs()}.
   *
   * <p>Method under test: {@link DemoApplication#getMovieDescUUIDs()}
   */
  @Test
  @DisplayName("Test getMovieDescUUIDs()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Connector DemoApplication.getMovieDescUUIDs()"})
  void testGetMovieDescUUIDs() {
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
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityContextPrincipalProvider securityContextPrincipalProvider3 =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager3 =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider3,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter2 = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration2 =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());
    TaskServiceImpl taskService3 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 = new APITaskConverter(mock(TaskService.class));
    SecurityContextPrincipalProvider securityContextPrincipalProvider4 =
        mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager4 =
        new LocalSpringSecurityManager(
            securityContextPrincipalProvider4,
            new AuthenticationPrincipalIdentityProvider(),
            mock(PrincipalGroupsProvider.class),
            mock(PrincipalRolesProvider.class));
    DateFormatterProvider dateFormatterProvider2 = mock(DateFormatterProvider.class);
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider2, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(
            taskService3, taskConverter2, securityManager4, taskVariablesValidator);

    TaskRuntimeImpl taskRuntime =
        new TaskRuntimeImpl(
            taskService2,
            securityManager3,
            taskConverter,
            variableInstanceConverter2,
            configuration2,
            taskRuntimeHelper);

    DemoApplication demoApplication =
        new DemoApplication(processRuntime, taskRuntime, new SecurityUtil());

    // Act
    Connector actualMovieDescUUIDs = demoApplication.getMovieDescUUIDs();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    IntegrationContext actualApplyResult = actualMovieDescUUIDs.apply(integrationContextImpl);

    // Assert
    assertSame(integrationContextImpl, actualApplyResult);
  }
}
