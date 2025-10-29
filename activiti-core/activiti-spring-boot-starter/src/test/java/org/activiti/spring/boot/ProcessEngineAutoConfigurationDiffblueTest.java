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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
import javax.sql.DataSource;
import org.activiti.api.process.model.events.ApplicationDeployedEvent;
import org.activiti.api.process.model.events.ProcessDeployedEvent;
import org.activiti.api.process.model.events.StartMessageDeployedEvent;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterGroupAddedEvent;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterUserAddedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.Transaction;
import org.activiti.bpmn.model.UserTask;
import org.activiti.core.common.spring.identity.ActivitiUserGroupManagerImpl;
import org.activiti.core.common.spring.identity.ExtendedInMemoryUserDetailsManager;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.CopyVariablesCalculator;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeploymentHelper;
import org.activiti.engine.impl.bpmn.deployer.ParsedDeploymentBuilderFactory;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContextFactory;
import org.activiti.engine.impl.bpmn.parser.handler.BoundaryEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.BusinessRuleParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.CallActivityParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TimerEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TransactionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.cfg.DelegateExpressionFieldInjectionMode;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.event.EventSubscriptionPayloadMappingProvider;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.engine.impl.interceptor.LogInterceptor;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.engine.parse.BpmnParseHandler;
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
import org.activiti.spring.SpringTransactionInterceptor;
import org.activiti.spring.StartMessageDeployedEventProducer;
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
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.transaction.PseudoTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

class ProcessEngineAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#springProcessEngineConfiguration(DataSource, PlatformTransactionManager, SpringAsyncExecutor, ActivitiProperties, ResourceFinder, List, ApplicationUpgradeContextService, List, List)}
   */
  @Test
  void testSpringProcessEngineConfiguration() throws IOException, MissingResourceException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ActivitiUserGroupManagerImpl userGroupManager = new ActivitiUserGroupManagerImpl(
        new ExtendedInMemoryUserDetailsManager());
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        userGroupManager);
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
    ObjectMapper objectMapper = new ObjectMapper();
    ApplicationUpgradeContextService applicationUpgradeContextService = new ApplicationUpgradeContextService("Path", 1,
        true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());

    ArrayList<ProcessEngineConfigurationConfigurer> processEngineConfigurationConfigurers = new ArrayList<>();
    ArrayList<ProcessEngineConfigurator> processEngineConfigurators = new ArrayList<>();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfigurationResult = processEngineAutoConfiguration
        .springProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor, activitiProperties,
            resourceFinder, resourceFinderDescriptors, applicationUpgradeContextService,
            processEngineConfigurationConfigurers, processEngineConfigurators);

    // Assert
    ObjectMapper objectMapper2 = actualSpringProcessEngineConfigurationResult.getObjectMapper();
    SerializationConfig serializationConfig = objectMapper2.getSerializationConfig();
    assertTrue(serializationConfig.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = objectMapper2.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper2.getDeserializationConfig();
    ContextAttributes attributes = deserializationConfig.getAttributes();
    assertTrue(attributes instanceof ContextAttributes.Impl);
    CacheProvider cacheProvider = deserializationConfig.getCacheProvider();
    assertTrue(cacheProvider instanceof DefaultCacheProvider);
    DeserializationContext deserializationContext = objectMapper2.getDeserializationContext();
    DeserializerFactory factory2 = deserializationContext.getFactory();
    assertTrue(factory2 instanceof BeanDeserializerFactory);
    assertTrue(deserializationContext instanceof DefaultDeserializationContext.Impl);
    ClassIntrospector classIntrospector = deserializationConfig.getClassIntrospector();
    assertTrue(classIntrospector instanceof BasicClassIntrospector);
    AccessorNamingStrategy.Provider accessorNaming = deserializationConfig.getAccessorNaming();
    assertTrue(accessorNaming instanceof DefaultAccessorNamingStrategy.Provider);
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    VisibilityChecker<?> visibilityChecker = objectMapper2.getVisibilityChecker();
    assertTrue(visibilityChecker instanceof VisibilityChecker.Std);
    PolymorphicTypeValidator polymorphicTypeValidator = objectMapper2.getPolymorphicTypeValidator();
    assertTrue(polymorphicTypeValidator instanceof LaissezFaireSubTypeValidator);
    SubtypeResolver subtypeResolver = objectMapper2.getSubtypeResolver();
    assertTrue(subtypeResolver instanceof StdSubtypeResolver);
    SerializerFactory serializerFactory = objectMapper2.getSerializerFactory();
    assertTrue(serializerFactory instanceof BeanSerializerFactory);
    SerializerProvider serializerProvider = objectMapper2.getSerializerProvider();
    assertTrue(serializerProvider instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProviderInstance = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    JsonSerializer<Object> defaultNullKeySerializer = serializerProvider.getDefaultNullKeySerializer();
    assertTrue(defaultNullKeySerializer instanceof FailingSerializer);
    JsonSerializer<Object> defaultNullValueSerializer = serializerProvider.getDefaultNullValueSerializer();
    assertTrue(defaultNullValueSerializer instanceof NullSerializer);
    DeserializerFactoryConfig factoryConfig = ((BeanDeserializerFactory) factory2).getFactoryConfig();
    Iterable<Deserializers> deserializersResult = factoryConfig.deserializers();
    assertTrue(deserializersResult instanceof ArrayIterator);
    SerializerFactoryConfig factoryConfig2 = ((BeanSerializerFactory) serializerFactory).getFactoryConfig();
    Iterable<Serializers> serializersResult = factoryConfig2.serializers();
    assertTrue(serializersResult instanceof ArrayIterator);
    DateFormat dateFormat = objectMapper2.getDateFormat();
    assertTrue(dateFormat instanceof StdDateFormat);
    Collection<? extends CommandInterceptor> defaultCommandInterceptors = actualSpringProcessEngineConfigurationResult
        .getDefaultCommandInterceptors();
    assertEquals(2, defaultCommandInterceptors.size());
    assertTrue(defaultCommandInterceptors instanceof List);
    Collection<? extends Deployer> defaultDeployers = actualSpringProcessEngineConfigurationResult
        .getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    DynamicBpmnService dynamicBpmnService = actualSpringProcessEngineConfigurationResult.getDynamicBpmnService();
    assertTrue(dynamicBpmnService instanceof DynamicBpmnServiceImpl);
    HistoryService historyService = actualSpringProcessEngineConfigurationResult.getHistoryService();
    assertTrue(historyService instanceof HistoryServiceImpl);
    ManagementService managementService = actualSpringProcessEngineConfigurationResult.getManagementService();
    assertTrue(managementService instanceof ManagementServiceImpl);
    RepositoryService repositoryService = actualSpringProcessEngineConfigurationResult.getRepositoryService();
    assertTrue(repositoryService instanceof RepositoryServiceImpl);
    RuntimeService runtimeService = actualSpringProcessEngineConfigurationResult.getRuntimeService();
    assertTrue(runtimeService instanceof RuntimeServiceImpl);
    TaskService taskService = actualSpringProcessEngineConfigurationResult.getTaskService();
    assertTrue(taskService instanceof TaskServiceImpl);
    List<BpmnParseHandler> defaultBpmnParseHandlers = actualSpringProcessEngineConfigurationResult
        .getDefaultBpmnParseHandlers();
    assertEquals(30, defaultBpmnParseHandlers.size());
    BpmnParseHandler getResult = defaultBpmnParseHandlers.get(0);
    assertTrue(getResult instanceof BoundaryEventParseHandler);
    BpmnParseHandler getResult2 = defaultBpmnParseHandlers.get(1);
    assertTrue(getResult2 instanceof BusinessRuleParseHandler);
    BpmnParseHandler getResult3 = defaultBpmnParseHandlers.get(2);
    assertTrue(getResult3 instanceof CallActivityParseHandler);
    BpmnParseHandler getResult4 = defaultBpmnParseHandlers.get(27);
    assertTrue(getResult4 instanceof TimerEventDefinitionParseHandler);
    BpmnParseHandler getResult5 = defaultBpmnParseHandlers.get(28);
    assertTrue(getResult5 instanceof TransactionParseHandler);
    BpmnParseHandler getResult6 = defaultBpmnParseHandlers.get(29);
    assertTrue(getResult6 instanceof UserTaskParseHandler);
    CommandInterceptor getResult7 = ((List<? extends CommandInterceptor>) defaultCommandInterceptors).get(0);
    assertTrue(getResult7 instanceof LogInterceptor);
    IdGenerator idGenerator = actualSpringProcessEngineConfigurationResult.getIdGenerator();
    assertTrue(idGenerator instanceof StrongUuidGenerator);
    assertTrue(actualSpringProcessEngineConfigurationResult
        .getIntegrationContextManager() instanceof IntegrationContextManagerImpl);
    assertTrue(actualSpringProcessEngineConfigurationResult
        .getIntegrationContextService() instanceof IntegrationContextServiceImpl);
    CommandInterceptor getResult8 = ((List<? extends CommandInterceptor>) defaultCommandInterceptors).get(1);
    assertTrue(getResult8 instanceof SpringTransactionInterceptor);
    assertEquals(" ", factory.getRootValueSeparator());
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getISO3Country());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("", actualSpringProcessEngineConfigurationResult.getDatabaseCatalog());
    assertEquals("", actualSpringProcessEngineConfigurationResult.getDatabaseTablePrefix());
    assertEquals("", actualSpringProcessEngineConfigurationResult.getJdbcPassword());
    assertEquals("2020-03-01", actualSpringProcessEngineConfigurationResult.getDatabaseSchemaUpdate());
    TimeZone timeZone = deserializationConfig.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("Database Schema", actualSpringProcessEngineConfigurationResult.getDatabaseSchema());
    assertEquals("Deployment Mode", actualSpringProcessEngineConfigurationResult.getDeploymentMode());
    assertEquals("Deployment Name", actualSpringProcessEngineConfigurationResult.getDeploymentName());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("English", locale.getDisplayName());
    assertEquals("JSON", factory.getFormatName());
    assertEquals("Java Class Field For Jackson",
        actualSpringProcessEngineConfigurationResult.getJavaClassFieldForJackson());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
    assertEquals("UTF-8", actualSpringProcessEngineConfigurationResult.getXmlEncoding());
    assertEquals("[one of: 'yyyy-MM-dd'T'HH:mm:ss.SSSX', 'EEE, dd MMM yyyy HH:mm:ss zzz' (lenient)]",
        ((StdDateFormat) dateFormat).toPattern());
    assertEquals("audit", actualSpringProcessEngineConfigurationResult.getHistory());
    assertEquals("camelContext", actualSpringProcessEngineConfigurationResult.getDefaultCamelContext());
    Version versionResult = factory.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    Version versionResult2 = objectMapper2.version();
    assertEquals("com.fasterxml.jackson.core", versionResult2.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-core/2.17.2", versionResult.toFullString());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult2.toFullString());
    assertEquals("default", actualSpringProcessEngineConfigurationResult.getProcessEngineName());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("iloveyou", actualSpringProcessEngineConfigurationResult.getMailServerPassword());
    assertEquals("jackson-core", versionResult.getArtifactId());
    assertEquals("jackson-databind", versionResult2.getArtifactId());
    assertEquals("jane.doe@example.org", actualSpringProcessEngineConfigurationResult.getMailServerDefaultFrom());
    assertEquals("janedoe", actualSpringProcessEngineConfigurationResult.getMailServerUsername());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", actualSpringProcessEngineConfigurationResult.getJdbcUrl());
    assertEquals("localhost", actualSpringProcessEngineConfigurationResult.getMailServerHost());
    assertEquals("org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        actualSpringProcessEngineConfigurationResult.getWsSyncFactoryClassName());
    assertEquals("org.h2.Driver", actualSpringProcessEngineConfigurationResult.getJdbcDriver());
    assertEquals("sa", actualSpringProcessEngineConfigurationResult.getJdbcUsername());
    assertEquals('=', base64Variant.getPaddingChar());
    assertNull(serializerProvider.getGenerator());
    assertNull(serializerProviderInstance.getGenerator());
    assertNull(deserializationContext.getParser());
    assertNull(factory.getCharacterEscapes());
    assertNull(factory.getInputDecorator());
    assertNull(factory.getOutputDecorator());
    assertNull(deserializationContext.getConfig());
    assertNull(objectMapper2.getInjectableValues());
    assertNull(deserializationContext.getContextualType());
    assertNull(defaultNullKeySerializer.getDelegatee());
    assertNull(defaultNullValueSerializer.getDelegatee());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(serializationConfig.getFullRootName());
    assertNull(objectMapper2.getPropertyNamingStrategy());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(serializationConfig.getPropertyNamingStrategy());
    assertNull(serializerProvider.getConfig());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getFilterProvider());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(serializationConfig.getDefaultMergeable());
    assertNull(factory.getFormatReadFeatureType());
    assertNull(factory.getFormatWriteFeatureType());
    JsonInclude.Value defaultPropertyInclusion = deserializationConfig.getDefaultPropertyInclusion();
    assertNull(defaultPropertyInclusion.getContentFilter());
    assertNull(defaultPropertyInclusion.getValueFilter());
    assertNull(deserializationContext.getActiveView());
    assertNull(serializerProvider.getActiveView());
    assertNull(serializerProviderInstance.getActiveView());
    assertNull(deserializationConfig.getActiveView());
    assertNull(serializationConfig.getActiveView());
    TypeFactory typeFactory = objectMapper2.getTypeFactory();
    assertNull(typeFactory.getClassLoader());
    assertNull(actualSpringProcessEngineConfigurationResult.getClassLoader());
    assertNull(actualSpringProcessEngineConfigurationResult.getJpaEntityManagerFactory());
    assertNull(deserializationConfig.getRootName());
    assertNull(serializationConfig.getRootName());
    assertNull(actualSpringProcessEngineConfigurationResult.getDataSourceJndiName());
    assertNull(actualSpringProcessEngineConfigurationResult.getDatabaseType());
    assertNull(actualSpringProcessEngineConfigurationResult.getDatabaseWildcardEscapeCharacter());
    assertNull(actualSpringProcessEngineConfigurationResult.getJdbcPingQuery());
    assertNull(actualSpringProcessEngineConfigurationResult.getJpaPersistenceUnitName());
    assertNull(actualSpringProcessEngineConfigurationResult.getMailSessionJndi());
    assertNull(actualSpringProcessEngineConfigurationResult.getAsyncExecutorLockOwner());
    assertNull(actualSpringProcessEngineConfigurationResult.getIdGeneratorDataSourceJndiName());
    assertNull(dateFormat.getNumberFormat());
    assertNull(dateFormat.getCalendar());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomScriptingEngineClasses());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomFunctionProviders());
    assertNull(actualSpringProcessEngineConfigurationResult.getAllConfigurators());
    assertNull(actualSpringProcessEngineConfigurationResult.getEventListeners());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomEventHandlers());
    assertNull(actualSpringProcessEngineConfigurationResult.getCommandInterceptors());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomPostCommandInterceptors());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomPreCommandInterceptors());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomSessionFactories());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomJobHandlers());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomPostDeployers());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomPreDeployers());
    assertNull(actualSpringProcessEngineConfigurationResult.getDeployers());
    assertNull(actualSpringProcessEngineConfigurationResult.getResolverFactories());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomPostVariableTypes());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomPreVariableTypes());
    assertNull(actualSpringProcessEngineConfigurationResult.getCustomDefaultBpmnParseHandlers());
    assertNull(actualSpringProcessEngineConfigurationResult.getPostBpmnParseHandlers());
    assertNull(actualSpringProcessEngineConfigurationResult.getPreBpmnParseHandlers());
    assertNull(actualSpringProcessEngineConfigurationResult.getSessionFactories());
    assertNull(actualSpringProcessEngineConfigurationResult.getBeans());
    assertNull(actualSpringProcessEngineConfigurationResult.getTypedEventListeners());
    assertNull(actualSpringProcessEngineConfigurationResult.getEventHandlers());
    assertNull(actualSpringProcessEngineConfigurationResult.getJobHandlers());
    assertNull(dateFormat.getTimeZone());
    assertNull(actualSpringProcessEngineConfigurationResult.getAsyncExecutorThreadPoolQueue());
    assertNull(actualSpringProcessEngineConfigurationResult.getIdGeneratorDataSource());
    assertNull(actualSpringProcessEngineConfigurationResult.getEngineAgendaFactory());
    assertNull(actualSpringProcessEngineConfigurationResult.getProcessEngineLifecycleListener());
    assertNull(actualSpringProcessEngineConfigurationResult.getEventDispatcher());
    assertNull(actualSpringProcessEngineConfigurationResult.getProcessDefinitionHelper());
    assertNull(actualSpringProcessEngineConfigurationResult.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(actualSpringProcessEngineConfigurationResult.getJobManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getListenerNotificationHelper());
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = actualSpringProcessEngineConfigurationResult
        .getParsedDeploymentBuilderFactory();
    assertNull(parsedDeploymentBuilderFactory.getBpmnParser());
    assertNull(actualSpringProcessEngineConfigurationResult.getBpmnParser());
    assertNull(actualSpringProcessEngineConfigurationResult.getActivityBehaviorFactory());
    assertNull(actualSpringProcessEngineConfigurationResult.getListenerFactory());
    assertNull(actualSpringProcessEngineConfigurationResult.getBusinessCalendarManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getBpmnParseFactory());
    assertNull(actualSpringProcessEngineConfigurationResult.getTransactionContextFactory());
    assertNull(actualSpringProcessEngineConfigurationResult.getDbSqlSessionFactory());
    assertNull(actualSpringProcessEngineConfigurationResult.getExpressionManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoryManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getDefaultCommandConfig());
    assertNull(actualSpringProcessEngineConfigurationResult.getSchemaCommandConfig());
    assertNull(actualSpringProcessEngineConfigurationResult.getCommandContextFactory());
    assertNull(((DynamicBpmnServiceImpl) dynamicBpmnService).getCommandExecutor());
    assertNull(((HistoryServiceImpl) historyService).getCommandExecutor());
    assertNull(((ManagementServiceImpl) managementService).getCommandExecutor());
    assertNull(((RepositoryServiceImpl) repositoryService).getCommandExecutor());
    assertNull(((RuntimeServiceImpl) runtimeService).getCommandExecutor());
    assertNull(((TaskServiceImpl) taskService).getCommandExecutor());
    assertNull(actualSpringProcessEngineConfigurationResult.getCommandExecutor());
    assertNull(actualSpringProcessEngineConfigurationResult.getCommandInvoker());
    assertNull(getResult7.getNext());
    assertNull(getResult8.getNext());
    assertNull(actualSpringProcessEngineConfigurationResult.getDelegateInterceptor());
    assertNull(actualSpringProcessEngineConfigurationResult.getFailedJobCommandFactory());
    assertNull(actualSpringProcessEngineConfigurationResult.getKnowledgeBaseCache());
    assertNull(actualSpringProcessEngineConfigurationResult.getProcessDefinitionCache());
    assertNull(actualSpringProcessEngineConfigurationResult.getDeploymentManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getAttachmentEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getByteArrayEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getCommentEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getDeadLetterJobEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getDeploymentEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getEventLogEntryEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getEventSubscriptionEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getExecutionEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricActivityInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricDetailEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricIdentityLinkEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricProcessInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricTaskInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricVariableInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getIdentityLinkEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getJobEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getModelEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getProcessDefinitionEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getProcessDefinitionInfoEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getPropertyEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getResourceEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getSuspendedJobEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getTableDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getTaskEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getTimerJobEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getVariableInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getAttachmentDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getByteArrayDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getCommentDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getDeadLetterJobDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getDeploymentDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getEventLogEntryDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getEventSubscriptionDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getExecutionDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricActivityInstanceDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricDetailDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricIdentityLinkDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricProcessInstanceDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricTaskInstanceDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getHistoricVariableInstanceDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getIdentityLinkDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getJobDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getModelDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getProcessDefinitionDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getProcessDefinitionInfoDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getPropertyDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getResourceDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getSuspendedJobDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getTaskDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getTimerJobDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getVariableInstanceDataManager());
    assertNull(actualSpringProcessEngineConfigurationResult.getScriptingEngines());
    assertNull(actualSpringProcessEngineConfigurationResult.getProcessInstanceHelper());
    assertNull(actualSpringProcessEngineConfigurationResult.getVariableTypes());
    assertNull(actualSpringProcessEngineConfigurationResult.getClock());
    assertNull(actualSpringProcessEngineConfigurationResult.getProcessValidator());
    assertNull(actualSpringProcessEngineConfigurationResult.getSqlSessionFactory());
    assertNull(actualSpringProcessEngineConfigurationResult.getTransactionFactory());
    assertNull(actualSpringProcessEngineConfigurationResult.getApplicationContext());
    assertEquals(-1, actualSpringProcessEngineConfigurationResult.getKnowledgeBaseCacheLimit());
    assertEquals(-1, actualSpringProcessEngineConfigurationResult.getMaxLengthStringVariableType());
    assertEquals(-1, actualSpringProcessEngineConfigurationResult.getProcessDefinitionCacheLimit());
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0, actualSpringProcessEngineConfigurationResult.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, actualSpringProcessEngineConfigurationResult.getJdbcMaxActiveConnections());
    assertEquals(0, actualSpringProcessEngineConfigurationResult.getJdbcMaxCheckoutTime());
    assertEquals(0, actualSpringProcessEngineConfigurationResult.getJdbcMaxIdleConnections());
    assertEquals(0, actualSpringProcessEngineConfigurationResult.getJdbcMaxWaitTime());
    assertEquals(0, actualSpringProcessEngineConfigurationResult.getJdbcPingConnectionNotUsedFor());
    assertEquals(0, actualSpringProcessEngineConfigurationResult.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(0, actualSpringProcessEngineConfigurationResult.getDeploymentResources().length);
    assertEquals(1, factory.getParserFeatures());
    assertEquals(1, getResult.getHandledTypes().size());
    assertEquals(1, getResult2.getHandledTypes().size());
    assertEquals(1, getResult3.getHandledTypes().size());
    assertEquals(1, getResult4.getHandledTypes().size());
    assertEquals(1, getResult5.getHandledTypes().size());
    assertEquals(1, getResult6.getHandledTypes().size());
    assertEquals(1, actualSpringProcessEngineConfigurationResult.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualSpringProcessEngineConfigurationResult.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, actualSpringProcessEngineConfigurationResult.getAsyncFailedJobWaitTime());
    assertEquals(10, actualSpringProcessEngineConfigurationResult.getDefaultFailedJobWaitTime());
    assertEquals(10, springAsyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(10, actualSpringProcessEngineConfigurationResult.getAsyncExecutorMaxPoolSize());
    assertEquals(100, actualSpringProcessEngineConfigurationResult.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, actualSpringProcessEngineConfigurationResult.getMaxNrOfStatementsInBulkInsert());
    assertEquals(10000, actualSpringProcessEngineConfigurationResult.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(10000, actualSpringProcessEngineConfigurationResult.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(17, versionResult2.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult2.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, versionResult2.getPatchLevel());
    assertEquals(2, actualSpringProcessEngineConfigurationResult.getAsyncExecutorCorePoolSize());
    assertEquals(20000, actualSpringProcessEngineConfigurationResult.getExecutionQueryLimit());
    assertEquals(20000, actualSpringProcessEngineConfigurationResult.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, actualSpringProcessEngineConfigurationResult.getHistoricTaskQueryLimit());
    assertEquals(20000, actualSpringProcessEngineConfigurationResult.getTaskQueryLimit());
    assertEquals(2079, factory.getGeneratorFeatures());
    assertEquals(21771068, serializationConfig.getSerializationFeatures());
    assertEquals(25, actualSpringProcessEngineConfigurationResult.getBatchSizeProcessInstances());
    assertEquals(25, actualSpringProcessEngineConfigurationResult.getBatchSizeTasks());
    assertEquals(2500, actualSpringProcessEngineConfigurationResult.getIdBlockSize());
    assertEquals(3, actualSpringProcessEngineConfigurationResult.getAsyncExecutorNumberOfRetries());
    assertEquals(3, actualSpringProcessEngineConfigurationResult.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(300000, actualSpringProcessEngineConfigurationResult.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(300000, actualSpringProcessEngineConfigurationResult.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(31, factory.getFactoryFeatures());
    assertEquals(4000, actualSpringProcessEngineConfigurationResult.getMaxLengthString());
    assertEquals(473998480, deserializationConfig.getDeserializationFeatures());
    assertEquals(5000L, actualSpringProcessEngineConfigurationResult.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualSpringProcessEngineConfigurationResult.getMyBatisXmlConfigurationStream().read(byteArray));
    assertEquals(60, actualSpringProcessEngineConfigurationResult.getLockTimeAsyncJobWaitTime());
    assertEquals(60000, actualSpringProcessEngineConfigurationResult.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(60L, actualSpringProcessEngineConfigurationResult.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(70, actualSpringProcessEngineConfigurationResult.DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    assertEquals(8080, actualSpringProcessEngineConfigurationResult.getMailServerPort());
    JsonNodeFactory nodeFactory = objectMapper2.getNodeFactory();
    assertEquals(9999, nodeFactory.getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
    JsonSetter.Value defaultSetterInfo = deserializationConfig.getDefaultSetterInfo();
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getContentNulls());
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getValueNulls());
    assertEquals(DelegateExpressionFieldInjectionMode.MIXED,
        actualSpringProcessEngineConfigurationResult.getDelegateExpressionFieldInjectionMode());
    assertEquals(HistoryLevel.NONE, actualSpringProcessEngineConfigurationResult.getHistoryLevel());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult2.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult2.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(versionResult2.isUnknownVersion());
    assertFalse(defaultNullKeySerializer.isUnwrappingSerializer());
    assertFalse(defaultNullValueSerializer.isUnwrappingSerializer());
    assertFalse(factoryConfig.hasAbstractTypeResolvers());
    assertFalse(factoryConfig.hasDeserializerModifiers());
    assertFalse(factoryConfig.hasDeserializers());
    assertFalse(factoryConfig.hasValueInstantiators());
    assertFalse(deserializationConfig.hasExplicitTimeZone());
    assertFalse(serializationConfig.hasExplicitTimeZone());
    assertFalse(factoryConfig2.hasKeySerializers());
    assertFalse(factoryConfig2.hasSerializerModifiers());
    assertFalse(factoryConfig2.hasSerializers());
    assertFalse(((ArrayIterator<Deserializers>) deserializersResult).hasNext());
    assertFalse(((ArrayIterator<Serializers>) serializersResult).hasNext());
    assertFalse(locale.hasExtensions());
    assertFalse(actualSpringProcessEngineConfigurationResult.isEnableProcessDefinitionInfoCache());
    assertFalse(actualSpringProcessEngineConfigurationResult.isJdbcPingEnabled());
    assertFalse(actualSpringProcessEngineConfigurationResult.isJpaCloseEntityManager());
    assertFalse(actualSpringProcessEngineConfigurationResult.isJpaHandleTransaction());
    assertFalse(actualSpringProcessEngineConfigurationResult.isTablePrefixIsSchema());
    PerformanceSettings performanceSettings = actualSpringProcessEngineConfigurationResult.getPerformanceSettings();
    assertFalse(performanceSettings.isEnableEagerExecutionTreeFetching());
    assertFalse(performanceSettings.isEnableExecutionRelationshipCounts());
    assertFalse(actualSpringProcessEngineConfigurationResult.isAsyncExecutorIsMessageQueueMode());
    assertFalse(actualSpringProcessEngineConfigurationResult.isEnableDatabaseEventLogging());
    assertFalse(actualSpringProcessEngineConfigurationResult.isEnableSafeBpmnXml());
    assertFalse(actualSpringProcessEngineConfigurationResult.isEnableVerboseExecutionTreeLogging());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(serializationConfig.isAnnotationProcessingEnabled());
    assertTrue(((StdDateFormat) dateFormat).isColonIncludedInTimeZone());
    assertTrue(dateFormat.isLenient());
    List<ProcessEngineConfigurator> configurators = actualSpringProcessEngineConfigurationResult.getConfigurators();
    assertTrue(configurators.isEmpty());
    assertTrue(actualSpringProcessEngineConfigurationResult.getMailServers().isEmpty());
    assertTrue(actualSpringProcessEngineConfigurationResult.getMailSessionsJndi().isEmpty());
    assertTrue(actualSpringProcessEngineConfigurationResult.getWsOverridenEndpointAddresses().isEmpty());
    Set<Object> registeredModuleIds = objectMapper2.getRegisteredModuleIds();
    assertTrue(registeredModuleIds.isEmpty());
    assertTrue(actualSpringProcessEngineConfigurationResult.getCustomMybatisMappers().isEmpty());
    assertTrue(actualSpringProcessEngineConfigurationResult.getCustomMybatisXMLMappers().isEmpty());
    assertTrue(actualSpringProcessEngineConfigurationResult.getMailServerUseSSL());
    assertTrue(actualSpringProcessEngineConfigurationResult.getMailServerUseTLS());
    assertTrue(actualSpringProcessEngineConfigurationResult.isAsyncExecutorActivate());
    assertTrue(actualSpringProcessEngineConfigurationResult.isCopyVariablesToLocalForTasks());
    assertTrue(actualSpringProcessEngineConfigurationResult.isDbHistoryUsed());
    assertTrue(actualSpringProcessEngineConfigurationResult.isTransactionsExternallyManaged());
    assertTrue(actualSpringProcessEngineConfigurationResult.isUseClassForNameClassLoading());
    assertTrue(performanceSettings.isEnableLocalization());
    assertTrue(performanceSettings.isValidateExecutionRelationshipCountConfigOnBoot());
    assertTrue(actualSpringProcessEngineConfigurationResult.isBulkInsertEnabled());
    assertTrue(actualSpringProcessEngineConfigurationResult.isEnableConfiguratorServiceLoader());
    assertTrue(actualSpringProcessEngineConfigurationResult.isEnableEventDispatcher());
    assertTrue(actualSpringProcessEngineConfigurationResult.isRollbackDeployment());
    assertTrue(actualSpringProcessEngineConfigurationResult.isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(actualSpringProcessEngineConfigurationResult.isSerializePOJOsInVariablesToJson());
    assertTrue(actualSpringProcessEngineConfigurationResult.isUsingRelationalDatabase());
    Class<BoundaryEvent> expectedHandledType = BoundaryEvent.class;
    assertEquals(expectedHandledType, ((BoundaryEventParseHandler) getResult).getHandledType());
    Class<BusinessRuleTask> expectedHandledType2 = BusinessRuleTask.class;
    assertEquals(expectedHandledType2, ((BusinessRuleParseHandler) getResult2).getHandledType());
    Class<CallActivity> expectedHandledType3 = CallActivity.class;
    assertEquals(expectedHandledType3, ((CallActivityParseHandler) getResult3).getHandledType());
    Class<TimerEventDefinition> expectedHandledType4 = TimerEventDefinition.class;
    assertEquals(expectedHandledType4, ((TimerEventDefinitionParseHandler) getResult4).getHandledType());
    Class<Transaction> expectedHandledType5 = Transaction.class;
    assertEquals(expectedHandledType5, ((TransactionParseHandler) getResult5).getHandledType());
    Class<UserTask> expectedHandledType6 = UserTask.class;
    assertEquals(expectedHandledType6, ((UserTaskParseHandler) getResult6).getHandledType());
    assertEquals(Integer.MAX_VALUE, base64Variant.getMaxLineLength());
    assertEquals('=', base64Variant.getPaddingByte());
    assertSame(processEngineConfigurators, configurators);
    assertSame(userGroupManager, actualSpringProcessEngineConfigurationResult.getUserGroupManager());
    assertSame(springAsyncExecutor, actualSpringProcessEngineConfigurationResult.getAsyncExecutor());
    assertSame(transactionManager, actualSpringProcessEngineConfigurationResult.getTransactionManager());
    assertSame(dataSource, actualSpringProcessEngineConfigurationResult.getDataSource());
    assertSame(nodeFactory, deserializationConfig.getNodeFactory());
    assertSame(registeredModuleIds, locale.getExtensionKeys());
    assertSame(registeredModuleIds, locale.getUnicodeLocaleAttributes());
    assertSame(registeredModuleIds, locale.getUnicodeLocaleKeys());
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    assertSame(typeFactory, serializerProviderInstance.getTypeFactory());
    assertSame(typeFactory, deserializationConfig.getTypeFactory());
    assertSame(typeFactory, serializationConfig.getTypeFactory());
    assertSame(versionResult2, annotationIntrospector.version());
    assertSame(base64Variant, serializationConfig.getBase64Variant());
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    assertSame(timeZone, serializerProviderInstance.getTimeZone());
    assertSame(timeZone, serializationConfig.getTimeZone());
    assertSame(defaultPropertyInclusion, serializationConfig.getDefaultPropertyInclusion());
    assertSame(defaultSetterInfo, serializationConfig.getDefaultSetterInfo());
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfigurationResult.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSpringProcessEngineConfigurationResult
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper2, factory.getCodec());
    assertSame(parsedDeploymentBuilderFactory, bpmnDeployer.getExParsedDeploymentBuilderFactory());
    assertSame(factory, objectMapper2.getJsonFactory());
    assertSame(attributes, serializationConfig.getAttributes());
    assertSame(cacheProvider, serializationConfig.getCacheProvider());
    assertSame(classIntrospector, serializationConfig.getClassIntrospector());
    assertSame(accessorNaming, serializationConfig.getAccessorNaming());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
    assertSame(visibilityChecker, deserializationConfig.getDefaultVisibilityChecker());
    assertSame(visibilityChecker, serializationConfig.getDefaultVisibilityChecker());
    assertSame(polymorphicTypeValidator, deserializationConfig.getPolymorphicTypeValidator());
    assertSame(polymorphicTypeValidator, serializationConfig.getPolymorphicTypeValidator());
    assertSame(subtypeResolver, deserializationConfig.getSubtypeResolver());
    assertSame(subtypeResolver, serializationConfig.getSubtypeResolver());
    assertSame(defaultNullKeySerializer, serializerProviderInstance.getDefaultNullKeySerializer());
    assertSame(defaultNullValueSerializer, serializerProviderInstance.getDefaultNullValueSerializer());
    assertSame(dateFormat, deserializationConfig.getDateFormat());
    assertSame(dateFormat, serializationConfig.getDateFormat());
    assertSame(idGenerator, bpmnDeployer.getIdGenerator());
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)}
   */
  @Test
  void testAddAsyncPropertyValidator() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)}
   */
  @Test
  void testAddAsyncPropertyValidator2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#addAsyncPropertyValidator(ActivitiProperties, SpringProcessEngineConfiguration)}
   */
  @Test
  void testAddAsyncPropertyValidator3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(mock(ExtendedInMemoryUserDetailsManager.class)));

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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processDefinitionResourceFinderDescriptor(ActivitiProperties)}
   */
  @Test
  void testProcessDefinitionResourceFinderDescriptor() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

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
    ArrayList<String> processDefinitionLocationSuffixes = new ArrayList<>();
    activitiProperties.setProcessDefinitionLocationSuffixes(processDefinitionLocationSuffixes);
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
    List<String> locationSuffixes = actualProcessDefinitionResourceFinderDescriptorResult.getLocationSuffixes();
    assertTrue(locationSuffixes.isEmpty());
    assertSame(processDefinitionLocationSuffixes, locationSuffixes);
  }

  /**
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processDefinitionResourceFinderDescriptor(ActivitiProperties)}
   */
  @Test
  void testProcessDefinitionResourceFinderDescriptor2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(mock(ExtendedInMemoryUserDetailsManager.class)));

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
    ArrayList<String> processDefinitionLocationSuffixes = new ArrayList<>();
    activitiProperties.setProcessDefinitionLocationSuffixes(processDefinitionLocationSuffixes);
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
    List<String> locationSuffixes = actualProcessDefinitionResourceFinderDescriptorResult.getLocationSuffixes();
    assertTrue(locationSuffixes.isEmpty());
    assertSame(processDefinitionLocationSuffixes, locationSuffixes);
  }

  /**
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)}
   */
  @Test
  void testProcessExtensionResourceFinderDescriptor() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)}
   */
  @Test
  void testProcessExtensionResourceFinderDescriptor2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processExtensionResourceFinderDescriptor(ActivitiProperties, String, String)}
   */
  @Test
  void testProcessExtensionResourceFinderDescriptor3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(mock(ExtendedInMemoryUserDetailsManager.class)));

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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testProcessDeployedEventProducer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testProcessDeployedEventProducer2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testProcessDeployedEventProducer3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}
   */
  @Test
  void testProcessCandidateStartersEventProducer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}
   */
  @Test
  void testProcessCandidateStartersEventProducer2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}
   */
  @Test
  void testProcessCandidateStartersEventProducer3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}
   */
  @Test
  void testProcessCandidateStartersEventProducer4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#processCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher)}
   */
  @Test
  void testProcessCandidateStartersEventProducer5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testStartMessageDeployedEventProducer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testStartMessageDeployedEventProducer2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#startMessageDeployedEventProducer(RepositoryService, ManagementService, StartMessageSubscriptionConverter, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testStartMessageDeployedEventProducer3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#defaultActivityBehaviorFactoryMappingConfigurer(ExtensionsVariablesMappingProvider, ProcessVariablesInitiator, EventSubscriptionPayloadMappingProvider, VariablesPropagator)}
   */
  @Test
  void testDefaultActivityBehaviorFactoryMappingConfigurer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = new DeploymentResourceLoader<>();
    ObjectMapper objectMapper = new ObjectMapper();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager, new ObjectMapper(),
        mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesMappingProvider = new ExtensionsVariablesMappingProvider(
        processExtensionService, expressionResolver, new VariableParsingService(new HashMap<>()));

    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader2 = new DeploymentResourceLoader<>();
    ObjectMapper objectMapper2 = new ObjectMapper();
    ProcessExtensionService processExtensionService2 = new ProcessExtensionService(processExtensionLoader2,
        new ProcessExtensionResourceReader(objectMapper2, new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    CopyVariablesCalculator variablesCalculator = new CopyVariablesCalculator();
    ExpressionManager expressionManager2 = new ExpressionManager();
    ProcessVariablesInitiator processVariablesInitiator = new ProcessVariablesInitiator(processExtensionService2,
        variableParsingService, variableValidationService, variablesCalculator,
        new ExpressionResolver(expressionManager2, new ObjectMapper(), mock(DelegateInterceptor.class)));

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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testApplicationDeployedEventProducer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testApplicationDeployedEventProducer2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#applicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testApplicationDeployedEventProducer3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));
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
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#candidateStartersDeploymentConfigurer()}
   */
  @Test
  void testCandidateStartersDeploymentConfigurer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    CandidateStartersDeploymentConfigurer actualCandidateStartersDeploymentConfigurerResult = (new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager())))
        .candidateStartersDeploymentConfigurer();
    SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
    actualCandidateStartersDeploymentConfigurerResult.configure(processEngineConfiguration);

    // Assert
    assertTrue(processEngineConfiguration
        .getBpmnDeploymentHelper() instanceof CandidateStartersDeploymentConfigurer.CandidateStartersDeploymentHelper);
  }

  /**
   * Method under test:
   * {@link ProcessEngineAutoConfiguration#candidateStartersDeploymentConfigurer()}
   */
  @Test
  void testCandidateStartersDeploymentConfigurer2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    CandidateStartersDeploymentConfigurer actualCandidateStartersDeploymentConfigurerResult = (new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(mock(ExtendedInMemoryUserDetailsManager.class))))
        .candidateStartersDeploymentConfigurer();
    SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
    actualCandidateStartersDeploymentConfigurerResult.configure(processEngineConfiguration);

    // Assert
    assertTrue(processEngineConfiguration
        .getBpmnDeploymentHelper() instanceof CandidateStartersDeploymentConfigurer.CandidateStartersDeploymentHelper);
  }
}
