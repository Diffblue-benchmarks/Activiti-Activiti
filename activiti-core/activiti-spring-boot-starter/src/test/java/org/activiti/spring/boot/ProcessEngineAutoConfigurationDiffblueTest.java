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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
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
import org.activiti.core.common.spring.identity.ActivitiUserGroupManagerImpl;
import org.activiti.core.common.spring.identity.ExtendedInMemoryUserDetailsManager;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.CopyVariablesCalculator;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeploymentHelper;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContextFactory;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.event.EventSubscriptionPayloadMappingProvider;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
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
  @InjectMocks
  private ProcessEngineAutoConfiguration processEngineAutoConfiguration;

  @Mock
  private UserGroupManager userGroupManager;

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}.
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName("Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"})
  void testSpringProcessEngineConfiguration() throws IOException {
    // Arrange
    TransactionAwareDataSourceProxy dataSource = new TransactionAwareDataSourceProxy();
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
    ResourceFinder resourceFinder = new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());
    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ApplicationUpgradeContextService applicationUpgradeContextService = new ApplicationUpgradeContextService("Path", 1,
        true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());

    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers = new ArrayList<>();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult = processEngineAutoConfiguration
        .springProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor, activitiProperties,
            resourceFinder, resourceFinderDescriptors, applicationUpgradeContextService,
            processEngineConfigurationConfigurers, new ArrayList<>());

    // Assert
    Collection<? extends Deployer> defaultDeployers = actualSpringProcessEngineConfigurationResult
        .getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    DataSource dataSource2 = actualSpringProcessEngineConfigurationResult.getDataSource();
    assertTrue(dataSource2 instanceof TransactionAwareDataSourceProxy);
    assertNull(((TransactionAwareDataSourceProxy) dataSource2).getTargetDataSource());
    assertSame(dataSource, dataSource2);
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfigurationResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSpringProcessEngineConfigurationResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}.
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName("Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"})
  void testSpringProcessEngineConfiguration2() throws IOException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    PseudoTransactionManager transactionManager = new PseudoTransactionManager();
    SpringAsyncExecutor springAsyncExecutor = mock(SpringAsyncExecutor.class);
    doNothing().when(springAsyncExecutor).applyConfig(Mockito.<ProcessEngineConfigurationImpl>any());

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
    ResourceFinder resourceFinder = new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());
    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ApplicationUpgradeContextService applicationUpgradeContextService = new ApplicationUpgradeContextService("Path", 1,
        true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());

    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers = new ArrayList<>();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult = processEngineAutoConfiguration
        .springProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor, activitiProperties,
            resourceFinder, resourceFinderDescriptors, applicationUpgradeContextService,
            processEngineConfigurationConfigurers, new ArrayList<>());

    // Assert
    verify(springAsyncExecutor).applyConfig(isA(ProcessEngineConfigurationImpl.class));
    ObjectMapper objectMapper2 = actualSpringProcessEngineConfigurationResult.getObjectMapper();
    JsonFactory factory = objectMapper2.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    AnnotationIntrospector annotationIntrospector = objectMapper2.getDeserializationConfig()
        .getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    SerializerProvider serializerProviderInstance = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof Impl);
    assertSame(objectMapper2, factory.getCodec());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}.
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName("Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"})
  void testSpringProcessEngineConfiguration3() throws IOException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    PseudoTransactionManager transactionManager = new PseudoTransactionManager();
    SpringAsyncExecutor springAsyncExecutor = mock(SpringAsyncExecutor.class);
    doNothing().when(springAsyncExecutor).applyConfig(Mockito.<ProcessEngineConfigurationImpl>any());

    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setAsyncExecutorActivate(false);
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
    ResourceFinder resourceFinder = new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());
    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ApplicationUpgradeContextService applicationUpgradeContextService = new ApplicationUpgradeContextService("Path", 1,
        true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());

    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers = new ArrayList<>();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult = processEngineAutoConfiguration
        .springProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor, activitiProperties,
            resourceFinder, resourceFinderDescriptors, applicationUpgradeContextService,
            processEngineConfigurationConfigurers, new ArrayList<>());

    // Assert
    verify(springAsyncExecutor).applyConfig(isA(ProcessEngineConfigurationImpl.class));
    ProcessValidator processValidator = actualSpringProcessEngineConfigurationResult.getProcessValidator();
    assertTrue(processValidator instanceof ProcessValidatorImpl);
    List<ValidatorSet> validatorSets = processValidator.getValidatorSets();
    assertEquals(1, validatorSets.size());
    ValidatorSet getResult = validatorSets.get(0);
    assertEquals("activiti-spring-boot-starter", getResult.getName());
    assertEquals(1, getResult.getValidators().size());
    assertFalse(actualSpringProcessEngineConfigurationResult.isAsyncExecutorActivate());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}.
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName("Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"})
  void testSpringProcessEngineConfiguration4() throws IOException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    PseudoTransactionManager transactionManager = new PseudoTransactionManager();
    SpringAsyncExecutor springAsyncExecutor = mock(SpringAsyncExecutor.class);
    doNothing().when(springAsyncExecutor).applyConfig(Mockito.<ProcessEngineConfigurationImpl>any());

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
    ResourceFinder resourceFinder = new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());

    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    resourceFinderDescriptors.add(new ProcessDefinitionResourceFinderDescriptor(new ActivitiProperties()));
    resourceFinderDescriptors.add(new ProcessDefinitionResourceFinderDescriptor(new ActivitiProperties()));
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ApplicationUpgradeContextService applicationUpgradeContextService = new ApplicationUpgradeContextService("Path", 1,
        true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());

    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers = new ArrayList<>();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult = processEngineAutoConfiguration
        .springProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor, activitiProperties,
            resourceFinder, resourceFinderDescriptors, applicationUpgradeContextService,
            processEngineConfigurationConfigurers, new ArrayList<>());

    // Assert
    verify(springAsyncExecutor).applyConfig(isA(ProcessEngineConfigurationImpl.class));
    assertEquals(198, actualSpringProcessEngineConfigurationResult.getDeploymentResources().length);
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return DeploymentMode is {@code default}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName("Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List); given 'null'; then return DeploymentMode is 'default'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"})
  void testSpringProcessEngineConfiguration_givenNull_thenReturnDeploymentModeIsDefault() throws IOException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    PseudoTransactionManager transactionManager = new PseudoTransactionManager();
    SpringAsyncExecutor springAsyncExecutor = mock(SpringAsyncExecutor.class);
    doNothing().when(springAsyncExecutor).applyConfig(Mockito.<ProcessEngineConfigurationImpl>any());

    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setAsyncExecutorActivate(true);
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode(null);
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
    ResourceFinder resourceFinder = new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());
    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ApplicationUpgradeContextService applicationUpgradeContextService = new ApplicationUpgradeContextService("Path", 1,
        true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());

    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers = new ArrayList<>();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult = processEngineAutoConfiguration
        .springProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor, activitiProperties,
            resourceFinder, resourceFinderDescriptors, applicationUpgradeContextService,
            processEngineConfigurationConfigurers, new ArrayList<>());

    // Assert
    verify(springAsyncExecutor).applyConfig(isA(ProcessEngineConfigurationImpl.class));
    ObjectMapper objectMapper2 = actualSpringProcessEngineConfigurationResult.getObjectMapper();
    JsonFactory factory = objectMapper2.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    AnnotationIntrospector annotationIntrospector = objectMapper2.getDeserializationConfig()
        .getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    SerializerProvider serializerProviderInstance = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof Impl);
    assertEquals("default", actualSpringProcessEngineConfigurationResult.getDeploymentMode());
    assertSame(objectMapper2, factory.getCodec());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}.
   * <ul>
   *   <li>Then return array length is ninety-nine.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName("Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List); then return array length is ninety-nine")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"})
  void testSpringProcessEngineConfiguration_thenReturnArrayLengthIsNinetyNine() throws IOException {
    // Arrange
    DataSource dataSource = mock(DataSource.class);
    PseudoTransactionManager transactionManager = new PseudoTransactionManager();
    SpringAsyncExecutor springAsyncExecutor = mock(SpringAsyncExecutor.class);
    doNothing().when(springAsyncExecutor).applyConfig(Mockito.<ProcessEngineConfigurationImpl>any());

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
    ResourceFinder resourceFinder = new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());

    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    resourceFinderDescriptors.add(new ProcessDefinitionResourceFinderDescriptor(new ActivitiProperties()));
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ApplicationUpgradeContextService applicationUpgradeContextService = new ApplicationUpgradeContextService("Path", 1,
        true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());

    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers = new ArrayList<>();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult = processEngineAutoConfiguration
        .springProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor, activitiProperties,
            resourceFinder, resourceFinderDescriptors, applicationUpgradeContextService,
            processEngineConfigurationConfigurers, new ArrayList<>());

    // Assert
    verify(springAsyncExecutor).applyConfig(isA(ProcessEngineConfigurationImpl.class));
    assertEquals(99, actualSpringProcessEngineConfigurationResult.getDeploymentResources().length);
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}.
   * <ul>
   *   <li>Then return DefaultDeployers size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}
   */
  @Test
  @DisplayName("Test springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List); then return DefaultDeployers size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "SpringProcessEngineConfiguration ProcessEngineAutoConfiguration.springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)"})
  void testSpringProcessEngineConfiguration_thenReturnDefaultDeployersSizeIsOne() throws IOException {
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
    ResourceFinder resourceFinder = new ResourceFinder(new AnnotationConfigReactiveWebApplicationContext());
    ArrayList<ResourceFinderDescriptor> resourceFinderDescriptors = new ArrayList<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ApplicationUpgradeContextService applicationUpgradeContextService = new ApplicationUpgradeContextService("Path", 1,
        true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());

    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers = new ArrayList<>();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult = processEngineAutoConfiguration
        .springProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor, activitiProperties,
            resourceFinder, resourceFinderDescriptors, applicationUpgradeContextService,
            processEngineConfigurationConfigurers, new ArrayList<>());

    // Assert
    Collection<? extends Deployer> defaultDeployers = actualSpringProcessEngineConfigurationResult
        .getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfigurationResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSpringProcessEngineConfigurationResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)}.
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)}
   */
  @Test
  @DisplayName("Test addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ProcessEngineAutoConfiguration.addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)"})
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
   * Test {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)}.
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)}
   */
  @Test
  @DisplayName("Test addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ProcessEngineAutoConfiguration.addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)"})
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

    SpringProcessEngineConfiguration conf = new SpringProcessEngineConfiguration();
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
   * Test {@link ProcessEngineAutoConfiguration#processDefinitionResourceFinderDescriptor(ActivitiProperties)}.
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processDefinitionResourceFinderDescriptor(ActivitiProperties)}
   */
  @Test
  @DisplayName("Test processDefinitionResourceFinderDescriptor(ActivitiProperties)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessDefinitionResourceFinderDescriptor ProcessEngineAutoConfiguration.processDefinitionResourceFinderDescriptor(ActivitiProperties)"})
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
    ProcessDefinitionResourceFinderDescriptor actualProcessDefinitionResourceFinderDescriptorResult = processEngineAutoConfiguration
        .processDefinitionResourceFinderDescriptor(activitiProperties);

    // Assert
    assertEquals("No process definitions were found for auto-deployment in the location `Process Definition Location"
        + " Prefix`", actualProcessDefinitionResourceFinderDescriptorResult.getMsgForEmptyResources());
    assertEquals("Process Definition Location Prefix",
        actualProcessDefinitionResourceFinderDescriptorResult.getLocationPrefix());
    assertTrue(actualProcessDefinitionResourceFinderDescriptorResult.getLocationSuffixes().isEmpty());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)}.
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)}
   */
  @Test
  @DisplayName("Test processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessExtensionResourceFinderDescriptor ProcessEngineAutoConfiguration.processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)"})
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
    ProcessExtensionResourceFinderDescriptor actualProcessExtensionResourceFinderDescriptorResult = processEngineAutoConfiguration
        .processExtensionResourceFinderDescriptor(activitiProperties, "NOT_DEFINED", "Location Suffix");

    // Assert
    List<String> locationSuffixes = actualProcessExtensionResourceFinderDescriptorResult.getLocationSuffixes();
    assertEquals(1, locationSuffixes.size());
    assertEquals("Location Suffix", locationSuffixes.get(0));
    assertEquals("No process extensions were found for auto-deployment in the location 'Process Definition Location"
        + " Prefix'", actualProcessExtensionResourceFinderDescriptorResult.getMsgForEmptyResources());
    assertEquals("Process Definition Location Prefix",
        actualProcessExtensionResourceFinderDescriptorResult.getLocationPrefix());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)}.
   * <ul>
   *   <li>Then return {@code Location Prefix}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)}
   */
  @Test
  @DisplayName("Test processExtensionResourceFinderDescriptor(ActivitiProperties, String, String); then return 'Location Prefix'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessExtensionResourceFinderDescriptor ProcessEngineAutoConfiguration.processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)"})
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
    ProcessExtensionResourceFinderDescriptor actualProcessExtensionResourceFinderDescriptorResult = processEngineAutoConfiguration
        .processExtensionResourceFinderDescriptor(activitiProperties, "Location Prefix", "Location Suffix");

    // Assert
    assertEquals("Location Prefix", actualProcessExtensionResourceFinderDescriptorResult.getLocationPrefix());
    List<String> locationSuffixes = actualProcessExtensionResourceFinderDescriptorResult.getLocationSuffixes();
    assertEquals(1, locationSuffixes.size());
    assertEquals("Location Suffix", locationSuffixes.get(0));
    assertEquals("No process extensions were found for auto-deployment in the location 'Location Prefix'",
        actualProcessExtensionResourceFinderDescriptorResult.getMsgForEmptyResources());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessDeployedEventProducer ProcessEngineAutoConfiguration.processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"})
  void testProcessDeployedEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<ProcessDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessDeployedEventProducer actualProcessDeployedEventProducerResult = processEngineAutoConfiguration
        .processDeployedEventProducer(repositoryService, converter, listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessDeployedEventProducerResult.isRunning());
    assertTrue(actualProcessDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessDeployedEventProducer ProcessEngineAutoConfiguration.processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"})
  void testProcessDeployedEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<ProcessDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessDeployedEventProducer actualProcessDeployedEventProducerResult = processEngineAutoConfiguration
        .processDeployedEventProducer(repositoryService, converter, listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessDeployedEventProducerResult.isRunning());
    assertTrue(actualProcessDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessDeployedEventProducer ProcessEngineAutoConfiguration.processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"})
  void testProcessDeployedEventProducer_whenArrayList() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    // Act
    ProcessDeployedEventProducer actualProcessDeployedEventProducerResult = processEngineAutoConfiguration
        .processDeployedEventProducer(repositoryService, converter, new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessDeployedEventProducerResult.isRunning());
    assertTrue(actualProcessDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessCandidateStartersEventProducer ProcessEngineAutoConfiguration.processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)"})
  void testProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> candidateStarterUserListeners = new ArrayList<>();
    candidateStarterUserListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducerResult = processEngineAutoConfiguration
        .processCandidateStartersEventProducer(repositoryService, candidateStarterUserListeners, new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducerResult.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessCandidateStartersEventProducer ProcessEngineAutoConfiguration.processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)"})
  void testProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> candidateStarterUserListeners = new ArrayList<>();
    candidateStarterUserListeners.add(mock(ProcessRuntimeEventListener.class));
    candidateStarterUserListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducerResult = processEngineAutoConfiguration
        .processCandidateStartersEventProducer(repositoryService, candidateStarterUserListeners, new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducerResult.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessCandidateStartersEventProducer ProcessEngineAutoConfiguration.processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)"})
  void testProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener3() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> candidateStarterUserListeners = new ArrayList<>();

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>> candidateStarterGroupListeners = new ArrayList<>();
    candidateStarterGroupListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducerResult = processEngineAutoConfiguration
        .processCandidateStartersEventProducer(repositoryService, candidateStarterUserListeners,
            candidateStarterGroupListeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducerResult.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessCandidateStartersEventProducer ProcessEngineAutoConfiguration.processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)"})
  void testProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener4() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> candidateStarterUserListeners = new ArrayList<>();

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>> candidateStarterGroupListeners = new ArrayList<>();
    candidateStarterGroupListeners.add(mock(ProcessRuntimeEventListener.class));
    candidateStarterGroupListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducerResult = processEngineAutoConfiguration
        .processCandidateStartersEventProducer(repositoryService, candidateStarterUserListeners,
            candidateStarterGroupListeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducerResult.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>When {@link RepositoryServiceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); when RepositoryServiceImpl (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProcessCandidateStartersEventProducer ProcessEngineAutoConfiguration.processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)"})
  void testProcessCandidateStartersEventProducer_whenRepositoryServiceImpl() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> candidateStarterUserListeners = new ArrayList<>();

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducerResult = processEngineAutoConfiguration
        .processCandidateStartersEventProducer(repositoryService, candidateStarterUserListeners, new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducerResult.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "StartMessageDeployedEventProducer ProcessEngineAutoConfiguration.startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"})
  void testStartMessageDeployedEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    StartMessageSubscriptionConverter subscriptionConverter = new StartMessageSubscriptionConverter();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<StartMessageDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducerResult = processEngineAutoConfiguration
        .startMessageDeployedEventProducer(repositoryService, managementService, subscriptionConverter, converter,
            listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducerResult.isRunning());
    assertTrue(actualStartMessageDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "StartMessageDeployedEventProducer ProcessEngineAutoConfiguration.startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"})
  void testStartMessageDeployedEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    StartMessageSubscriptionConverter subscriptionConverter = new StartMessageSubscriptionConverter();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<StartMessageDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducerResult = processEngineAutoConfiguration
        .startMessageDeployedEventProducer(repositoryService, managementService, subscriptionConverter, converter,
            listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducerResult.isRunning());
    assertTrue(actualStartMessageDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "StartMessageDeployedEventProducer ProcessEngineAutoConfiguration.startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)"})
  void testStartMessageDeployedEventProducer_whenArrayList() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ManagementServiceImpl managementService = new ManagementServiceImpl();
    StartMessageSubscriptionConverter subscriptionConverter = new StartMessageSubscriptionConverter();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    // Act
    StartMessageDeployedEventProducer actualStartMessageDeployedEventProducerResult = processEngineAutoConfiguration
        .startMessageDeployedEventProducer(repositoryService, managementService, subscriptionConverter, converter,
            new ArrayList<>(), mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualStartMessageDeployedEventProducerResult.isRunning());
    assertTrue(actualStartMessageDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualStartMessageDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#defaultActivityBehaviorFactoryMappingConfigurer(ExtensionsVariablesMappingProvider, ProcessVariablesInitiator, EventSubscriptionPayloadMappingProvider, VariablesPropagator)}.
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#defaultActivityBehaviorFactoryMappingConfigurer(ExtensionsVariablesMappingProvider, ProcessVariablesInitiator, EventSubscriptionPayloadMappingProvider, VariablesPropagator)}
   */
  @Test
  @DisplayName("Test defaultActivityBehaviorFactoryMappingConfigurer(ExtensionsVariablesMappingProvider, ProcessVariablesInitiator, EventSubscriptionPayloadMappingProvider, VariablesPropagator)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "DefaultActivityBehaviorFactoryMappingConfigurer ProcessEngineAutoConfiguration.defaultActivityBehaviorFactoryMappingConfigurer(ExtensionsVariablesMappingProvider, ProcessVariablesInitiator, EventSubscriptionPayloadMappingProvider, VariablesPropagator)"})
  void testDefaultActivityBehaviorFactoryMappingConfigurer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = new DeploymentResourceLoader<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager,
        JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesMappingProvider = new ExtensionsVariablesMappingProvider(
        processExtensionService, expressionResolver, new VariableParsingService(new HashMap<>()));

    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader2 = new DeploymentResourceLoader<>();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService2 = new ProcessExtensionService(processExtensionLoader2,
        new ProcessExtensionResourceReader(objectMapper2, new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    CopyVariablesCalculator variablesCalculator = new CopyVariablesCalculator();
    ExpressionManager expressionManager2 = new ExpressionManager();
    ProcessVariablesInitiator processVariablesInitiator = new ProcessVariablesInitiator(processExtensionService2,
        variableParsingService, variableValidationService, variablesCalculator, new ExpressionResolver(
            expressionManager2, JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class)));

    EventSubscriptionVariablesMappingProvider eventSubscriptionPayloadMappingProvider = new EventSubscriptionVariablesMappingProvider(
        new CopyVariablesCalculator());

    // Act
    DefaultActivityBehaviorFactoryMappingConfigurer actualDefaultActivityBehaviorFactoryMappingConfigurerResult = processEngineAutoConfiguration
        .defaultActivityBehaviorFactoryMappingConfigurer(variablesMappingProvider, processVariablesInitiator,
            eventSubscriptionPayloadMappingProvider, new VariablesPropagator(new CopyVariablesCalculator()));
    SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
    actualDefaultActivityBehaviorFactoryMappingConfigurerResult.configure(processEngineConfiguration);

    // Assert
    ActivityBehaviorFactory activityBehaviorFactory = processEngineConfiguration.getActivityBehaviorFactory();
    assertTrue(((MappingAwareActivityBehaviorFactory) activityBehaviorFactory)
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(((MappingAwareActivityBehaviorFactory) activityBehaviorFactory)
        .getMessagePayloadMappingProviderFactory() instanceof JsonMessagePayloadMappingProviderFactory);
    assertTrue(activityBehaviorFactory instanceof MappingAwareActivityBehaviorFactory);
    assertNull(((MappingAwareActivityBehaviorFactory) activityBehaviorFactory).getExpressionManager());
    assertSame(eventSubscriptionPayloadMappingProvider,
        processEngineConfiguration.getEventSubscriptionPayloadMappingProvider());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ApplicationDeployedEventProducer ProcessEngineAutoConfiguration.applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)"})
  void testApplicationDeployedEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIDeploymentConverter converter = new APIDeploymentConverter();

    ArrayList<ProcessRuntimeEventListener<ApplicationDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ApplicationDeployedEventProducer actualApplicationDeployedEventProducerResult = processEngineAutoConfiguration
        .applicationDeployedEventProducer(repositoryService, converter, listeners,
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualApplicationDeployedEventProducerResult.isRunning());
    assertTrue(actualApplicationDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualApplicationDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ApplicationDeployedEventProducer ProcessEngineAutoConfiguration.applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)"})
  void testApplicationDeployedEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIDeploymentConverter converter = new APIDeploymentConverter();

    ArrayList<ProcessRuntimeEventListener<ApplicationDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ApplicationDeployedEventProducer actualApplicationDeployedEventProducerResult = processEngineAutoConfiguration
        .applicationDeployedEventProducer(repositoryService, converter, listeners,
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualApplicationDeployedEventProducerResult.isRunning());
    assertTrue(actualApplicationDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualApplicationDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ApplicationDeployedEventProducer ProcessEngineAutoConfiguration.applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)"})
  void testApplicationDeployedEventProducer_whenArrayList() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIDeploymentConverter converter = new APIDeploymentConverter();

    // Act
    ApplicationDeployedEventProducer actualApplicationDeployedEventProducerResult = processEngineAutoConfiguration
        .applicationDeployedEventProducer(repositoryService, converter, new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualApplicationDeployedEventProducerResult.isRunning());
    assertTrue(actualApplicationDeployedEventProducerResult.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualApplicationDeployedEventProducerResult.getPhase());
  }

  /**
   * Test {@link ProcessEngineAutoConfiguration#candidateStartersDeploymentConfigurer()}.
   * <p>
   * Method under test: {@link ProcessEngineAutoConfiguration#candidateStartersDeploymentConfigurer()}
   */
  @Test
  @DisplayName("Test candidateStartersDeploymentConfigurer()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "CandidateStartersDeploymentConfigurer ProcessEngineAutoConfiguration.candidateStartersDeploymentConfigurer()"})
  void testCandidateStartersDeploymentConfigurer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    CandidateStartersDeploymentConfigurer actualCandidateStartersDeploymentConfigurerResult = (new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager())))
        .candidateStartersDeploymentConfigurer();
    SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
    actualCandidateStartersDeploymentConfigurerResult.configure(processEngineConfiguration);

    // Assert
    assertTrue(processEngineConfiguration.getBpmnDeploymentHelper() instanceof CandidateStartersDeploymentHelper);
  }
}
