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
package org.activiti.spring.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.activiti.api.process.model.events.ApplicationDeployedEvent;
import org.activiti.api.process.model.events.ProcessDeployedEvent;
import org.activiti.api.process.model.events.StartMessageDeployedEvent;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterGroupAddedEvent;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterUserAddedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.CopyVariablesCalculator;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContextFactory;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.event.EventSubscriptionPayloadMappingProvider;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.runtime.api.event.impl.StartMessageSubscriptionConverter;
import org.activiti.runtime.api.impl.EventSubscriptionVariablesMappingProvider;
import org.activiti.runtime.api.impl.ExpressionResolver;
import org.activiti.runtime.api.impl.ExtensionsVariablesMappingProvider;
import org.activiti.runtime.api.impl.JsonMessagePayloadMappingProviderFactory;
import org.activiti.runtime.api.impl.MappingAwareActivityBehaviorFactory;
import org.activiti.runtime.api.model.impl.APIDeploymentConverter;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.activiti.spring.ApplicationDeployedEventProducer;
import org.activiti.spring.ProcessCandidateStartersEventProducer;
import org.activiti.spring.ProcessDeployedEventProducer;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.StartMessageDeployedEventProducer;
import org.activiti.spring.boot.CandidateStartersDeploymentConfigurer.CandidateStartersDeploymentHelper;
import org.activiti.spring.process.ProcessExtensionResourceFinderDescriptor;
import org.activiti.spring.process.ProcessExtensionResourceReader;
import org.activiti.spring.process.ProcessExtensionService;
import org.activiti.spring.process.ProcessVariablesInitiator;
import org.activiti.spring.process.model.ProcessExtensionModel;
import org.activiti.spring.process.variable.VariableParsingService;
import org.activiti.spring.process.variable.VariableValidationService;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.activiti.spring.resources.ResourceFinder;
import org.activiti.spring.resources.ResourceFinderDescriptor;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorImpl;
import org.activiti.validation.validator.ValidatorSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.transaction.PseudoTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(MockitoExtension.class)
class ProcessEngineAutoConfigurationDiffblueTest {
  @InjectMocks private ProcessEngineAutoConfiguration processEngineAutoConfiguration;

  @Mock private UserGroupManager userGroupManager;

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource,
   * PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List,
   * ApplicationUpgradeContextService, List, List)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       ProcessEngineConfigurationConfigurer#configure(SpringProcessEngineConfiguration)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource,
   * PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List,
   * ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName(
      "Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List); then calls configure(SpringProcessEngineConfiguration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"
  })
  void testSpringProcessEngineConfiguration_thenCallsConfigure() throws IOException {
    // Arrange
    TransactionAwareDataSourceProxy dataSource = new TransactionAwareDataSourceProxy();
    PseudoTransactionManager transactionManager = new PseudoTransactionManager();
    SpringAsyncExecutor springAsyncExecutor = new SpringAsyncExecutor();

    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setCustomMybatisMappers(null);
    activitiProperties.setCustomMybatisXMLMappers(null);
    activitiProperties.setUseStrongUuids(false);
    activitiProperties.setDeploymentMode(null);
    activitiProperties.setAsyncExecutorActivate(false);
    ResourceFinder resourceFinder =
        new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());

    ActivitiProperties activitiProperties2 = new ActivitiProperties();
    activitiProperties2.setAsyncExecutorActivate(true);
    activitiProperties2.setCheckProcessDefinitions(true);
    activitiProperties2.setCopyVariablesToLocalForTasks(true);
    activitiProperties2.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties2.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties2.setDatabaseSchema("Database Schema");
    activitiProperties2.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties2.setDbHistoryUsed(true);
    activitiProperties2.setDeploymentMode("Deployment Mode");
    activitiProperties2.setDeploymentName("Deployment Name");
    activitiProperties2.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties2.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties2.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties2.setMailServerHost("localhost");
    activitiProperties2.setMailServerPassword("iloveyou");
    activitiProperties2.setMailServerPort(8080);
    activitiProperties2.setMailServerUseSsl(true);
    activitiProperties2.setMailServerUseTls(true);
    activitiProperties2.setMailServerUserName("janedoe");
    activitiProperties2.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties2.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties2.setSerializePOJOsInVariablesToJson(true);
    activitiProperties2.setUseStrongUuids(true);
    ProcessDefinitionResourceFinderDescriptor processDefinitionResourceFinderDescriptor =
        new ProcessDefinitionResourceFinderDescriptor(activitiProperties2);

    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    resourceFinderDescriptors.add(processDefinitionResourceFinderDescriptor);

    ProcessEngineConfigurationConfigurer processEngineConfigurationConfigurer =
        mock(ProcessEngineConfigurationConfigurer.class);
    doNothing()
        .when(processEngineConfigurationConfigurer)
        .configure(Mockito.<SpringProcessEngineConfiguration>any());

    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers =
        new ArrayList<>();
    processEngineConfigurationConfigurers.add(processEngineConfigurationConfigurer);

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult =
        processEngineAutoConfiguration.springProcessEngineConfiguration(
            dataSource,
            transactionManager,
            springAsyncExecutor,
            activitiProperties,
            resourceFinder,
            resourceFinderDescriptors,
            null,
            processEngineConfigurationConfigurers,
            new ArrayList<>());

    // Assert
    Collection<? extends Deployer> defaultDeployers =
        actualSpringProcessEngineConfigurationResult.getDefaultDeployers();
    assertTrue(defaultDeployers instanceof List);
    assertEquals(1, defaultDeployers.size());
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfigurationResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    assertSame(
        actualSpringProcessEngineConfigurationResult.getBpmnDeploymentHelper(),
        bpmnDeployer.getBpmnDeploymentHelper());
    verify(processEngineConfigurationConfigurer)
        .configure(isA(SpringProcessEngineConfiguration.class));
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource,
   * PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List,
   * ApplicationUpgradeContextService, List, List)}.
   *
   * <ul>
   *   <li>Then DefaultDeployers return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource,
   * PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List,
   * ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName(
      "Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List); then DefaultDeployers return List")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"
  })
  void testSpringProcessEngineConfiguration_thenDefaultDeployersReturnList() throws IOException {
    // Arrange
    TransactionAwareDataSourceProxy dataSource = new TransactionAwareDataSourceProxy();
    PseudoTransactionManager transactionManager = new PseudoTransactionManager();
    SpringAsyncExecutor springAsyncExecutor = new SpringAsyncExecutor();

    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setCustomMybatisMappers(null);
    activitiProperties.setCustomMybatisXMLMappers(null);
    activitiProperties.setUseStrongUuids(false);
    activitiProperties.setDeploymentMode(null);
    activitiProperties.setAsyncExecutorActivate(false);
    ResourceFinder resourceFinder =
        new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());

    ActivitiProperties activitiProperties2 = new ActivitiProperties();
    activitiProperties2.setAsyncExecutorActivate(true);
    activitiProperties2.setCheckProcessDefinitions(true);
    activitiProperties2.setCopyVariablesToLocalForTasks(true);
    activitiProperties2.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties2.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties2.setDatabaseSchema("Database Schema");
    activitiProperties2.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties2.setDbHistoryUsed(true);
    activitiProperties2.setDeploymentMode("Deployment Mode");
    activitiProperties2.setDeploymentName("Deployment Name");
    activitiProperties2.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties2.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties2.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties2.setMailServerHost("localhost");
    activitiProperties2.setMailServerPassword("iloveyou");
    activitiProperties2.setMailServerPort(8080);
    activitiProperties2.setMailServerUseSsl(true);
    activitiProperties2.setMailServerUseTls(true);
    activitiProperties2.setMailServerUserName("janedoe");
    activitiProperties2.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties2.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties2.setSerializePOJOsInVariablesToJson(true);
    activitiProperties2.setUseStrongUuids(true);
    ProcessDefinitionResourceFinderDescriptor processDefinitionResourceFinderDescriptor =
        new ProcessDefinitionResourceFinderDescriptor(activitiProperties2);

    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    resourceFinderDescriptors.add(processDefinitionResourceFinderDescriptor);

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult =
        processEngineAutoConfiguration.springProcessEngineConfiguration(
            dataSource,
            transactionManager,
            springAsyncExecutor,
            activitiProperties,
            resourceFinder,
            resourceFinderDescriptors,
            null,
            null,
            new ArrayList<>());

    // Assert
    Collection<? extends Deployer> defaultDeployers =
        actualSpringProcessEngineConfigurationResult.getDefaultDeployers();
    assertTrue(defaultDeployers instanceof List);
    assertEquals(1, defaultDeployers.size());
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfigurationResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    assertSame(
        actualSpringProcessEngineConfigurationResult.getBpmnDeploymentHelper(),
        bpmnDeployer.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource,
   * PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List,
   * ApplicationUpgradeContextService, List, List)}.
   *
   * <ul>
   *   <li>Then IdGenerator return {@link StrongUuidGenerator}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource,
   * PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List,
   * ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName(
      "Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List); then IdGenerator return StrongUuidGenerator")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"
  })
  void testSpringProcessEngineConfiguration_thenIdGeneratorReturnStrongUuidGenerator()
      throws IOException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    PseudoTransactionManager transactionManager = new PseudoTransactionManager();
    SpringAsyncExecutor springAsyncExecutor = new SpringAsyncExecutor();

    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setAsyncExecutorActivate(true);
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode("Deployment Mode");
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setUseStrongUuids(true);
    ResourceFinder resourceFinder =
        new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());
    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ApplicationUpgradeContextService applicationUpgradeContextService =
        new ApplicationUpgradeContextService(
            "Path", 1, true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());
    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers =
        new ArrayList<>();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult =
        processEngineAutoConfiguration.springProcessEngineConfiguration(
            dataSource,
            transactionManager,
            springAsyncExecutor,
            activitiProperties,
            resourceFinder,
            resourceFinderDescriptors,
            applicationUpgradeContextService,
            processEngineConfigurationConfigurers,
            new ArrayList<>());

    // Assert
    assertTrue(
        actualSpringProcessEngineConfigurationResult.getIdGenerator()
            instanceof StrongUuidGenerator);
    assertEquals(
        "Deployment Mode", actualSpringProcessEngineConfigurationResult.getDeploymentMode());
    assertNull(actualSpringProcessEngineConfigurationResult.getProcessValidator());
    assertTrue(actualSpringProcessEngineConfigurationResult.getCustomMybatisMappers().isEmpty());
    assertTrue(actualSpringProcessEngineConfigurationResult.getCustomMybatisXMLMappers().isEmpty());
    assertTrue(actualSpringProcessEngineConfigurationResult.isAsyncExecutorActivate());
    assertTrue(actualSpringProcessEngineConfigurationResult.isRollbackDeployment());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource,
   * PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List,
   * ApplicationUpgradeContextService, List, List)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource,
   * PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List,
   * ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName(
      "Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"
  })
  void testSpringProcessEngineConfiguration_thenThrowIllegalArgumentException() throws IOException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    PseudoTransactionManager transactionManager = new PseudoTransactionManager();
    SpringAsyncExecutor springAsyncExecutor = new SpringAsyncExecutor();

    ArrayList<String> customMyBatisMappers = new ArrayList<>();
    customMyBatisMappers.add("default");

    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setAsyncExecutorActivate(true);
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(customMyBatisMappers);
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode("Deployment Mode");
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setUseStrongUuids(true);
    ResourceFinder resourceFinder =
        new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());
    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ApplicationUpgradeContextService applicationUpgradeContextService =
        new ApplicationUpgradeContextService(
            "Path", 1, true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());
    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers =
        new ArrayList<>();

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            processEngineAutoConfiguration.springProcessEngineConfiguration(
                dataSource,
                transactionManager,
                springAsyncExecutor,
                activitiProperties,
                resourceFinder,
                resourceFinderDescriptors,
                applicationUpgradeContextService,
                processEngineConfigurationConfigurers,
                new ArrayList<>()));
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties,
   * SpringProcessEngineConfiguration)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties,
   * SpringProcessEngineConfiguration)}
   */
  @Test
  @DisplayName(
      "Test addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessEngineAutoConfiguration.addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)"
  })
  void testAddAsyncPropertyValidator() {
    // Arrange
    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setAsyncExecutorActivate(true);
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode("Deployment Mode");
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setUseStrongUuids(true);
    SpringProcessEngineConfiguration conf = new SpringProcessEngineConfiguration();

    // Act
    processEngineAutoConfiguration.addAsyncPropertyValidator(activitiProperties, conf);

    // Assert that nothing has changed
    assertNull(conf.getProcessValidator());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties,
   * SpringProcessEngineConfiguration)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties,
   * SpringProcessEngineConfiguration)}
   */
  @Test
  @DisplayName(
      "Test addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessEngineAutoConfiguration.addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)"
  })
  void testAddAsyncPropertyValidator2() {
    // Arrange
    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode("Deployment Mode");
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setUseStrongUuids(true);
    activitiProperties.setAsyncExecutorActivate(false);
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    SpringProcessEngineConfiguration conf =
        new SpringProcessEngineConfiguration(
            new ApplicationUpgradeContextService(
                "Path",
                1,
                true,
                objectMapper,
                new AnnotationConfigReactiveWebApplicationContext()));
    conf.setProcessValidator(null);

    // Act
    processEngineAutoConfiguration.addAsyncPropertyValidator(activitiProperties, conf);

    // Assert
    ProcessValidator processValidator = conf.getProcessValidator();
    assertTrue(processValidator instanceof ProcessValidatorImpl);
    List<ValidatorSet> validatorSets = processValidator.getValidatorSets();
    assertEquals(1, validatorSets.size());
    ValidatorSet getResult = validatorSets.get(0);
    assertEquals("activiti-spring-boot-starter", getResult.getName());
    assertEquals(1, getResult.getValidators().size());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties,
   * SpringProcessEngineConfiguration)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties,
   * SpringProcessEngineConfiguration)}
   */
  @Test
  @DisplayName(
      "Test addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessEngineAutoConfiguration.addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)"
  })
  void testAddAsyncPropertyValidator3() {
    // Arrange
    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode("Deployment Mode");
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setUseStrongUuids(true);
    activitiProperties.setAsyncExecutorActivate(false);

    ProcessValidatorImpl processValidator = new ProcessValidatorImpl();
    processValidator.addValidatorSet(new ValidatorSet("activiti-spring-boot-starter"));
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    SpringProcessEngineConfiguration conf =
        new SpringProcessEngineConfiguration(
            new ApplicationUpgradeContextService(
                "Path",
                1,
                true,
                objectMapper,
                new AnnotationConfigReactiveWebApplicationContext()));
    conf.setProcessValidator(processValidator);

    // Act
    processEngineAutoConfiguration.addAsyncPropertyValidator(activitiProperties, conf);

    // Assert
    assertSame(processValidator, conf.getProcessValidator());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties,
   * SpringProcessEngineConfiguration)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessValidator#getValidatorSets()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties,
   * SpringProcessEngineConfiguration)}
   */
  @Test
  @DisplayName(
      "Test addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration); then calls getValidatorSets()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessEngineAutoConfiguration.addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)"
  })
  void testAddAsyncPropertyValidator_thenCallsGetValidatorSets() {
    // Arrange
    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode("Deployment Mode");
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setUseStrongUuids(true);
    activitiProperties.setAsyncExecutorActivate(false);

    ProcessValidator processValidator = mock(ProcessValidator.class);
    when(processValidator.getValidatorSets()).thenReturn(new ArrayList<>());
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    SpringProcessEngineConfiguration conf =
        new SpringProcessEngineConfiguration(
            new ApplicationUpgradeContextService(
                "Path",
                1,
                true,
                objectMapper,
                new AnnotationConfigReactiveWebApplicationContext()));
    conf.setProcessValidator(processValidator);

    // Act
    processEngineAutoConfiguration.addAsyncPropertyValidator(activitiProperties, conf);

    // Assert
    verify(processValidator).getValidatorSets();
  }

  /**
   * Test {@link
   * ProcessEngineAutoConfiguration#processDefinitionResourceFinderDescriptor(ActivitiProperties)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processDefinitionResourceFinderDescriptor(ActivitiProperties)}
   */
  @Test
  @DisplayName("Test processDefinitionResourceFinderDescriptor(ActivitiProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionResourceFinderDescriptor ProcessEngineAutoConfiguration.processDefinitionResourceFinderDescriptor(ActivitiProperties)"
  })
  void testProcessDefinitionResourceFinderDescriptor() {
    // Arrange
    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setAsyncExecutorActivate(true);
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode("Deployment Mode");
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setUseStrongUuids(true);

    // Act
    ProcessDefinitionResourceFinderDescriptor
        actualProcessDefinitionResourceFinderDescriptorResult =
            processEngineAutoConfiguration.processDefinitionResourceFinderDescriptor(
                activitiProperties);

    // Assert
    assertEquals(
        "No process definitions were found for auto-deployment in the location `Process Definition Location"
            + " Prefix`",
        actualProcessDefinitionResourceFinderDescriptorResult.getMsgForEmptyResources());
    assertEquals(
        "Process Definition Location Prefix",
        actualProcessDefinitionResourceFinderDescriptorResult.getLocationPrefix());
    assertTrue(
        actualProcessDefinitionResourceFinderDescriptorResult.getLocationSuffixes().isEmpty());
  }

  /**
   * Test {@link
   * ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties,
   * String, String)}
   */
  @Test
  @DisplayName("Test processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessExtensionResourceFinderDescriptor ProcessEngineAutoConfiguration.processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)"
  })
  void testProcessExtensionResourceFinderDescriptor() {
    // Arrange
    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setAsyncExecutorActivate(true);
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode("Deployment Mode");
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setUseStrongUuids(true);

    // Act
    ProcessExtensionResourceFinderDescriptor actualProcessExtensionResourceFinderDescriptorResult =
        processEngineAutoConfiguration.processExtensionResourceFinderDescriptor(
            activitiProperties, "NOT_DEFINED", "Location Suffix");

    // Assert
    List<String> locationSuffixes =
        actualProcessExtensionResourceFinderDescriptorResult.getLocationSuffixes();
    assertEquals(1, locationSuffixes.size());
    assertEquals("Location Suffix", locationSuffixes.get(0));
    assertEquals(
        "No process extensions were found for auto-deployment in the location 'Process Definition Location"
            + " Prefix'",
        actualProcessExtensionResourceFinderDescriptorResult.getMsgForEmptyResources());
    assertEquals(
        "Process Definition Location Prefix",
        actualProcessExtensionResourceFinderDescriptorResult.getLocationPrefix());
  }

  /**
   * Test {@link
   * ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties,
   * String, String)}.
   *
   * <ul>
   *   <li>Then return {@code Location Prefix}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties,
   * String, String)}
   */
  @Test
  @DisplayName(
      "Test processExtensionResourceFinderDescriptor(ActivitiProperties, String, String); then return 'Location Prefix'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessExtensionResourceFinderDescriptor ProcessEngineAutoConfiguration.processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)"
  })
  void testProcessExtensionResourceFinderDescriptor_thenReturnLocationPrefix() {
    // Arrange
    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setAsyncExecutorActivate(true);
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode("Deployment Mode");
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    activitiProperties.setProcessDefinitionLocationSuffixes(new ArrayList<>());
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setUseStrongUuids(true);

    // Act
    ProcessExtensionResourceFinderDescriptor actualProcessExtensionResourceFinderDescriptorResult =
        processEngineAutoConfiguration.processExtensionResourceFinderDescriptor(
            activitiProperties, "Location Prefix", "Location Suffix");

    // Assert
    assertEquals(
        "Location Prefix",
        actualProcessExtensionResourceFinderDescriptorResult.getLocationPrefix());
    List<String> locationSuffixes =
        actualProcessExtensionResourceFinderDescriptorResult.getLocationSuffixes();
    assertEquals(1, locationSuffixes.size());
    assertEquals("Location Suffix", locationSuffixes.get(0));
    assertEquals(
        "No process extensions were found for auto-deployment in the location 'Location Prefix'",
        actualProcessExtensionResourceFinderDescriptorResult.getMsgForEmptyResources());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService,
   * APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService,
   * APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDeployedEventProducer ProcessEngineAutoConfiguration.processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"
  })
  void testProcessDeployedEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter converter =
        new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<ProcessDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessDeployedEventProducer actualProcessDeployedEventProducerResult =
        processEngineAutoConfiguration.processDeployedEventProducer(
            repositoryService, converter, listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessDeployedEventProducerResult.isRunning());
    assertTrue(actualProcessDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService,
   * APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService,
   * APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDeployedEventProducer ProcessEngineAutoConfiguration.processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"
  })
  void testProcessDeployedEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter converter =
        new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<ProcessDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessDeployedEventProducer actualProcessDeployedEventProducerResult =
        processEngineAutoConfiguration.processDeployedEventProducer(
            repositoryService, converter, listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessDeployedEventProducerResult.isRunning());
    assertTrue(actualProcessDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService,
   * APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService,
   * APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDeployedEventProducer ProcessEngineAutoConfiguration.processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"
  })
  void testProcessDeployedEventProducer_whenArrayList() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter converter =
        new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    // Act
    ProcessDeployedEventProducer actualProcessDeployedEventProducerResult =
        processEngineAutoConfiguration.processDeployedEventProducer(
            repositoryService, converter, new ArrayList<>(), mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessDeployedEventProducerResult.isRunning());
    assertTrue(actualProcessDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link
   * ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List,
   * List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List,
   * List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessCandidateStartersEventProducer ProcessEngineAutoConfiguration.processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)"
  })
  void testProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>>
        candidateStarterUserListeners = new ArrayList<>();
    candidateStarterUserListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducerResult =
        processEngineAutoConfiguration.processCandidateStartersEventProducer(
            repositoryService,
            candidateStarterUserListeners,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducerResult.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducerResult.getPhase());
  }

  /**
   * Test {@link
   * ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List,
   * List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List,
   * List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessCandidateStartersEventProducer ProcessEngineAutoConfiguration.processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)"
  })
  void testProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>>
        candidateStarterUserListeners = new ArrayList<>();
    candidateStarterUserListeners.add(mock(ProcessRuntimeEventListener.class));
    candidateStarterUserListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducerResult =
        processEngineAutoConfiguration.processCandidateStartersEventProducer(
            repositoryService,
            candidateStarterUserListeners,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducerResult.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducerResult.getPhase());
  }

  /**
   * Test {@link
   * ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List,
   * List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List,
   * List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessCandidateStartersEventProducer ProcessEngineAutoConfiguration.processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)"
  })
  void testProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener3() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>>
        candidateStarterUserListeners = new ArrayList<>();

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>>
        candidateStarterGroupListeners = new ArrayList<>();
    candidateStarterGroupListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducerResult =
        processEngineAutoConfiguration.processCandidateStartersEventProducer(
            repositoryService,
            candidateStarterUserListeners,
            candidateStarterGroupListeners,
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducerResult.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducerResult.getPhase());
  }

  /**
   * Test {@link
   * ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List,
   * List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List,
   * List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessCandidateStartersEventProducer ProcessEngineAutoConfiguration.processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)"
  })
  void testProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener4() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>>
        candidateStarterUserListeners = new ArrayList<>();

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>>
        candidateStarterGroupListeners = new ArrayList<>();
    candidateStarterGroupListeners.add(mock(ProcessRuntimeEventListener.class));
    candidateStarterGroupListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducerResult =
        processEngineAutoConfiguration.processCandidateStartersEventProducer(
            repositoryService,
            candidateStarterUserListeners,
            candidateStarterGroupListeners,
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducerResult.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducerResult.getPhase());
  }

  /**
   * Test {@link
   * ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List,
   * List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>When {@link RepositoryServiceImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List,
   * List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); when RepositoryServiceImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessCandidateStartersEventProducer ProcessEngineAutoConfiguration.processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)"
  })
  void testProcessCandidateStartersEventProducer_whenRepositoryServiceImpl() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>>
        candidateStarterUserListeners = new ArrayList<>();

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducerResult =
        processEngineAutoConfiguration.processCandidateStartersEventProducer(
            repositoryService,
            candidateStarterUserListeners,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducerResult.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService,
   * ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List,
   * ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService,
   * ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List,
   * ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StartMessageDeployedEventProducer ProcessEngineAutoConfiguration.startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"
  })
  void testStartMessageDeployedEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    StartMessageSubscriptionConverter subscriptionConverter =
        new StartMessageSubscriptionConverter();
    APIProcessDefinitionConverter converter =
        new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<StartMessageDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducerResult =
        processEngineAutoConfiguration.startMessageDeployedEventProducer(
            repositoryService,
            managementService,
            subscriptionConverter,
            converter,
            listeners,
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducerResult.isRunning());
    assertTrue(actualStartMessageDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService,
   * ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List,
   * ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService,
   * ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List,
   * ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StartMessageDeployedEventProducer ProcessEngineAutoConfiguration.startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"
  })
  void testStartMessageDeployedEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    StartMessageSubscriptionConverter subscriptionConverter =
        new StartMessageSubscriptionConverter();
    APIProcessDefinitionConverter converter =
        new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<StartMessageDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducerResult =
        processEngineAutoConfiguration.startMessageDeployedEventProducer(
            repositoryService,
            managementService,
            subscriptionConverter,
            converter,
            listeners,
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducerResult.isRunning());
    assertTrue(actualStartMessageDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService,
   * ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List,
   * ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService,
   * ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List,
   * ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StartMessageDeployedEventProducer ProcessEngineAutoConfiguration.startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"
  })
  void testStartMessageDeployedEventProducer_whenArrayList() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    StartMessageSubscriptionConverter subscriptionConverter =
        new StartMessageSubscriptionConverter();
    APIProcessDefinitionConverter converter =
        new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducerResult =
        processEngineAutoConfiguration.startMessageDeployedEventProducer(
            repositoryService,
            managementService,
            subscriptionConverter,
            converter,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducerResult.isRunning());
    assertTrue(actualStartMessageDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link
   * ProcessEngineAutoConfiguration#defaultActivityBehaviorFactoryMappingConfigurer(ExtensionsVariablesMappingProvider,
   * ProcessVariablesInitiator, EventSubscriptionPayloadMappingProvider, VariablesPropagator)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#defaultActivityBehaviorFactoryMappingConfigurer(ExtensionsVariablesMappingProvider,
   * ProcessVariablesInitiator, EventSubscriptionPayloadMappingProvider, VariablesPropagator)}
   */
  @Test
  @DisplayName(
      "Test defaultActivityBehaviorFactoryMappingConfigurer(ExtensionsVariablesMappingProvider, ProcessVariablesInitiator, EventSubscriptionPayloadMappingProvider, VariablesPropagator)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultActivityBehaviorFactoryMappingConfigurer ProcessEngineAutoConfiguration.defaultActivityBehaviorFactoryMappingConfigurer(ExtensionsVariablesMappingProvider, ProcessVariablesInitiator, EventSubscriptionPayloadMappingProvider, VariablesPropagator)"
  })
  void testDefaultActivityBehaviorFactoryMappingConfigurer() {
    // Arrange
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader =
        new DeploymentResourceLoader<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    ProcessExtensionService processExtensionService =
        new ProcessExtensionService(processExtensionLoader, processExtensionReader);
    ExpressionManager expressionManager = new ExpressionManager();
    JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();

    ExpressionResolver expressionResolver =
        new ExpressionResolver(expressionManager, mapper, mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesMappingProvider =
        new ExtensionsVariablesMappingProvider(
            processExtensionService,
            expressionResolver,
            new VariableParsingService(new HashMap<>()));
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader2 =
        new DeploymentResourceLoader<>();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionReader2 =
        new ProcessExtensionResourceReader(objectMapper2, new HashMap<>());

    ProcessExtensionService processExtensionService2 =
        new ProcessExtensionService(processExtensionLoader2, processExtensionReader2);
    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService =
        new VariableValidationService(new HashMap<>());
    CopyVariablesCalculator variablesCalculator = new CopyVariablesCalculator();
    ExpressionManager expressionManager2 = new ExpressionManager();
    JsonMapper mapper2 = JsonMapper.builder().findAndAddModules().build();

    ExpressionResolver expressionResolver2 =
        new ExpressionResolver(expressionManager2, mapper2, mock(DelegateInterceptor.class));

    ProcessVariablesInitiator processVariablesInitiator =
        new ProcessVariablesInitiator(
            processExtensionService2,
            variableParsingService,
            variableValidationService,
            variablesCalculator,
            expressionResolver2);
    EventSubscriptionVariablesMappingProvider eventSubscriptionPayloadMappingProvider =
        new EventSubscriptionVariablesMappingProvider(new CopyVariablesCalculator());

    // Act
    DefaultActivityBehaviorFactoryMappingConfigurer
        actualDefaultActivityBehaviorFactoryMappingConfigurerResult =
            processEngineAutoConfiguration.defaultActivityBehaviorFactoryMappingConfigurer(
                variablesMappingProvider,
                processVariablesInitiator,
                eventSubscriptionPayloadMappingProvider,
                new VariablesPropagator(new CopyVariablesCalculator()));
    SpringProcessEngineConfiguration processEngineConfiguration =
        new SpringProcessEngineConfiguration();
    actualDefaultActivityBehaviorFactoryMappingConfigurerResult.configure(
        processEngineConfiguration);

    // Assert
    ActivityBehaviorFactory activityBehaviorFactory =
        processEngineConfiguration.getActivityBehaviorFactory();
    assertTrue(
        ((MappingAwareActivityBehaviorFactory) activityBehaviorFactory)
                .getMessageExecutionContextFactory()
            instanceof DefaultMessageExecutionContextFactory);
    assertTrue(
        ((MappingAwareActivityBehaviorFactory) activityBehaviorFactory)
                .getMessagePayloadMappingProviderFactory()
            instanceof JsonMessagePayloadMappingProviderFactory);
    assertTrue(activityBehaviorFactory instanceof MappingAwareActivityBehaviorFactory);
    assertNull(
        ((MappingAwareActivityBehaviorFactory) activityBehaviorFactory).getExpressionManager());
    assertSame(
        eventSubscriptionPayloadMappingProvider,
        processEngineConfiguration.getEventSubscriptionPayloadMappingProvider());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService,
   * APIDeploymentConverter, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService,
   * APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ApplicationDeployedEventProducer ProcessEngineAutoConfiguration.applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)"
  })
  void testApplicationDeployedEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIDeploymentConverter converter = new APIDeploymentConverter();

    ArrayList<ProcessRuntimeEventListener<ApplicationDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ApplicationDeployedEventProducer actualApplicationDeployedEventProducerResult =
        processEngineAutoConfiguration.applicationDeployedEventProducer(
            repositoryService, converter, listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualApplicationDeployedEventProducerResult.isRunning());
    assertTrue(actualApplicationDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualApplicationDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService,
   * APIDeploymentConverter, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService,
   * APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ApplicationDeployedEventProducer ProcessEngineAutoConfiguration.applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)"
  })
  void testApplicationDeployedEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIDeploymentConverter converter = new APIDeploymentConverter();

    ArrayList<ProcessRuntimeEventListener<ApplicationDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ApplicationDeployedEventProducer actualApplicationDeployedEventProducerResult =
        processEngineAutoConfiguration.applicationDeployedEventProducer(
            repositoryService, converter, listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualApplicationDeployedEventProducerResult.isRunning());
    assertTrue(actualApplicationDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualApplicationDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService,
   * APIDeploymentConverter, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService,
   * APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ApplicationDeployedEventProducer ProcessEngineAutoConfiguration.applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)"
  })
  void testApplicationDeployedEventProducer_whenArrayList() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIDeploymentConverter converter = new APIDeploymentConverter();

    // Act
    ApplicationDeployedEventProducer actualApplicationDeployedEventProducerResult =
        processEngineAutoConfiguration.applicationDeployedEventProducer(
            repositoryService, converter, new ArrayList<>(), mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualApplicationDeployedEventProducerResult.isRunning());
    assertTrue(actualApplicationDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualApplicationDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#candidateStartersDeploymentConfigurer()}.
   *
   * <p>Method under test: {@link
   * ProcessEngineAutoConfiguration#candidateStartersDeploymentConfigurer()}
   */
  @Test
  @DisplayName("Test candidateStartersDeploymentConfigurer()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateStartersDeploymentConfigurer ProcessEngineAutoConfiguration.candidateStartersDeploymentConfigurer()"
  })
  void testCandidateStartersDeploymentConfigurer() {
    // Arrange and Act
    CandidateStartersDeploymentConfigurer actualCandidateStartersDeploymentConfigurerResult =
        processEngineAutoConfiguration.candidateStartersDeploymentConfigurer();
    SpringProcessEngineConfiguration processEngineConfiguration =
        new SpringProcessEngineConfiguration();
    actualCandidateStartersDeploymentConfigurerResult.configure(processEngineConfiguration);

    // Assert
    assertTrue(
        processEngineConfiguration.getBpmnDeploymentHelper()
            instanceof CandidateStartersDeploymentHelper);
  }
}
