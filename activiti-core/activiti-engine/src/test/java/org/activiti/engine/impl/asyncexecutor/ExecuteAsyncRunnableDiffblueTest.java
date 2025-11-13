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
package org.activiti.engine.impl.asyncexecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude.Value;
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
import com.fasterxml.jackson.databind.cfg.DatatypeFeatures;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy.Provider;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.Transaction;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ActivitiOptimisticLockingException;
import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeploymentHelper;
import org.activiti.engine.impl.bpmn.deployer.ParsedDeploymentBuilderFactory;
import org.activiti.engine.impl.bpmn.parser.handler.BoundaryEventParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.BusinessRuleParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TransactionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.DelegateExpressionFieldInjectionMode;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ExecuteAsyncRunnableDiffblueTest {
  /**
   * Test {@link ExecuteAsyncRunnable#ExecuteAsyncRunnable(String, ProcessEngineConfigurationImpl)}.
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#ExecuteAsyncRunnable(String,
   * ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.<init>(String, ProcessEngineConfigurationImpl)"})
  public void testNewExecuteAsyncRunnable() throws IOException, MissingResourceException {
    // Arrange and Act
    ExecuteAsyncRunnable actualExecuteAsyncRunnable =
        new ExecuteAsyncRunnable("42", new JtaProcessEngineConfiguration());

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        actualExecuteAsyncRunnable.processEngineConfiguration;
    ObjectMapper objectMapper = processEngineConfigurationImpl.getObjectMapper();
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertTrue(serializationConfig.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    ContextAttributes attributes = deserializationConfig.getAttributes();
    assertTrue(attributes instanceof ContextAttributes.Impl);
    CacheProvider cacheProvider = deserializationConfig.getCacheProvider();
    assertTrue(cacheProvider instanceof DefaultCacheProvider);
    DeserializationContext deserializationContext = objectMapper.getDeserializationContext();
    DeserializerFactory factory2 = deserializationContext.getFactory();
    assertTrue(factory2 instanceof BeanDeserializerFactory);
    assertTrue(deserializationContext instanceof DefaultDeserializationContext.Impl);
    ClassIntrospector classIntrospector = deserializationConfig.getClassIntrospector();
    assertTrue(classIntrospector instanceof BasicClassIntrospector);
    Provider accessorNaming = deserializationConfig.getAccessorNaming();
    assertTrue(accessorNaming instanceof DefaultAccessorNamingStrategy.Provider);
    AnnotationIntrospector annotationIntrospector =
        deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    VisibilityChecker<?> visibilityChecker = objectMapper.getVisibilityChecker();
    assertTrue(visibilityChecker instanceof Std);
    PolymorphicTypeValidator polymorphicTypeValidator = objectMapper.getPolymorphicTypeValidator();
    assertTrue(polymorphicTypeValidator instanceof LaissezFaireSubTypeValidator);
    SubtypeResolver subtypeResolver = objectMapper.getSubtypeResolver();
    assertTrue(subtypeResolver instanceof StdSubtypeResolver);
    SerializerFactory serializerFactory = objectMapper.getSerializerFactory();
    assertTrue(serializerFactory instanceof BeanSerializerFactory);
    SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
    assertTrue(serializerProvider instanceof Impl);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof Impl);
    JsonSerializer<Object> defaultNullKeySerializer =
        serializerProvider.getDefaultNullKeySerializer();
    assertTrue(defaultNullKeySerializer instanceof FailingSerializer);
    JsonSerializer<Object> defaultNullValueSerializer =
        serializerProvider.getDefaultNullValueSerializer();
    assertTrue(defaultNullValueSerializer instanceof NullSerializer);
    DeserializerFactoryConfig factoryConfig =
        ((BeanDeserializerFactory) factory2).getFactoryConfig();
    Iterable<Deserializers> deserializersResult = factoryConfig.deserializers();
    assertTrue(deserializersResult instanceof ArrayIterator);
    SerializerFactoryConfig factoryConfig2 =
        ((BeanSerializerFactory) serializerFactory).getFactoryConfig();
    Iterable<Serializers> serializersResult = factoryConfig2.serializers();
    assertTrue(serializersResult instanceof ArrayIterator);
    DateFormat dateFormat = objectMapper.getDateFormat();
    assertTrue(dateFormat instanceof StdDateFormat);
    Collection<? extends Deployer> defaultDeployers =
        processEngineConfigurationImpl.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    DynamicBpmnService dynamicBpmnService = processEngineConfigurationImpl.getDynamicBpmnService();
    assertTrue(dynamicBpmnService instanceof DynamicBpmnServiceImpl);
    HistoryService historyService = processEngineConfigurationImpl.getHistoryService();
    assertTrue(historyService instanceof HistoryServiceImpl);
    ManagementService managementService = processEngineConfigurationImpl.getManagementService();
    assertTrue(managementService instanceof ManagementServiceImpl);
    RepositoryService repositoryService = processEngineConfigurationImpl.getRepositoryService();
    assertTrue(repositoryService instanceof RepositoryServiceImpl);
    RuntimeService runtimeService = processEngineConfigurationImpl.getRuntimeService();
    assertTrue(runtimeService instanceof RuntimeServiceImpl);
    TaskService taskService = processEngineConfigurationImpl.getTaskService();
    assertTrue(taskService instanceof TaskServiceImpl);
    List<BpmnParseHandler> defaultBpmnParseHandlers =
        processEngineConfigurationImpl.getDefaultBpmnParseHandlers();
    assertEquals(30, defaultBpmnParseHandlers.size());
    BpmnParseHandler getResult = defaultBpmnParseHandlers.get(0);
    assertTrue(getResult instanceof BoundaryEventParseHandler);
    BpmnParseHandler getResult2 = defaultBpmnParseHandlers.get(1);
    assertTrue(getResult2 instanceof BusinessRuleParseHandler);
    BpmnParseHandler getResult3 = defaultBpmnParseHandlers.get(28);
    assertTrue(getResult3 instanceof TransactionParseHandler);
    BpmnParseHandler getResult4 = defaultBpmnParseHandlers.get(29);
    assertTrue(getResult4 instanceof UserTaskParseHandler);
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(
        processEngineConfigurationImpl.getIntegrationContextManager()
            instanceof IntegrationContextManagerImpl);
    assertTrue(
        processEngineConfigurationImpl.getIntegrationContextService()
            instanceof IntegrationContextServiceImpl);
    assertEquals(" ", factory.getRootValueSeparator());
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getISO3Country());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("", processEngineConfigurationImpl.getDatabaseCatalog());
    assertEquals("", processEngineConfigurationImpl.getDatabaseTablePrefix());
    assertEquals("", processEngineConfigurationImpl.getJdbcPassword());
    assertEquals("42", actualExecuteAsyncRunnable.jobId);
    assertEquals("@class", processEngineConfigurationImpl.getJavaClassFieldForJackson());
    TimeZone timeZone = deserializationConfig.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("English", locale.getDisplayName());
    assertEquals("JSON", factory.getFormatName());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
    assertEquals("UTF-8", processEngineConfigurationImpl.getXmlEncoding());
    assertEquals(
        "[one of: 'yyyy-MM-dd'T'HH:mm:ss.SSSX', 'EEE, dd MMM yyyy HH:mm:ss zzz' (lenient)]",
        ((StdDateFormat) dateFormat).toPattern());
    assertEquals("activiti@localhost", processEngineConfigurationImpl.getMailServerDefaultFrom());
    assertEquals("audit", processEngineConfigurationImpl.getHistory());
    assertEquals("camelContext", processEngineConfigurationImpl.getDefaultCamelContext());
    Version versionResult = factory.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    Version versionResult2 = objectMapper.version();
    assertEquals("com.fasterxml.jackson.core", versionResult2.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-core/2.17.2", versionResult.toFullString());
    assertEquals(
        "com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult2.toFullString());
    assertEquals("default", processEngineConfigurationImpl.getProcessEngineName());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("jackson-core", versionResult.getArtifactId());
    assertEquals("jackson-databind", versionResult2.getArtifactId());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", processEngineConfigurationImpl.getJdbcUrl());
    assertEquals("localhost", processEngineConfigurationImpl.getMailServerHost());
    assertEquals(
        "org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        processEngineConfigurationImpl.getWsSyncFactoryClassName());
    assertEquals("org.h2.Driver", processEngineConfigurationImpl.getJdbcDriver());
    assertEquals("sa", processEngineConfigurationImpl.getJdbcUsername());
    assertEquals('=', base64Variant.getPaddingChar());
    assertNull(serializerProvider.getGenerator());
    assertNull(serializerProviderInstance.getGenerator());
    assertNull(deserializationContext.getParser());
    assertNull(factory.getCharacterEscapes());
    assertNull(factory.getInputDecorator());
    assertNull(factory.getOutputDecorator());
    assertNull(deserializationContext.getConfig());
    assertNull(objectMapper.getInjectableValues());
    assertNull(deserializationContext.getContextualType());
    assertNull(defaultNullKeySerializer.getDelegatee());
    assertNull(defaultNullValueSerializer.getDelegatee());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(serializationConfig.getFullRootName());
    assertNull(objectMapper.getPropertyNamingStrategy());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(serializationConfig.getPropertyNamingStrategy());
    assertNull(serializerProvider.getConfig());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getFilterProvider());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(
        ((JtaProcessEngineConfiguration) processEngineConfigurationImpl).getTransactionManager());
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(serializationConfig.getDefaultMergeable());
    assertNull(factory.getFormatReadFeatureType());
    assertNull(factory.getFormatWriteFeatureType());
    Value defaultPropertyInclusion = deserializationConfig.getDefaultPropertyInclusion();
    assertNull(defaultPropertyInclusion.getContentFilter());
    assertNull(defaultPropertyInclusion.getValueFilter());
    assertNull(deserializationContext.getActiveView());
    assertNull(serializerProvider.getActiveView());
    assertNull(serializerProviderInstance.getActiveView());
    assertNull(deserializationConfig.getActiveView());
    assertNull(serializationConfig.getActiveView());
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    assertNull(typeFactory.getClassLoader());
    assertNull(processEngineConfigurationImpl.getClassLoader());
    assertNull(processEngineConfigurationImpl.getJpaEntityManagerFactory());
    assertNull(deserializationConfig.getRootName());
    assertNull(serializationConfig.getRootName());
    assertNull(processEngineConfigurationImpl.getDataSourceJndiName());
    assertNull(processEngineConfigurationImpl.getDatabaseSchema());
    assertNull(processEngineConfigurationImpl.getDatabaseType());
    assertNull(processEngineConfigurationImpl.getDatabaseWildcardEscapeCharacter());
    assertNull(processEngineConfigurationImpl.getJdbcPingQuery());
    assertNull(processEngineConfigurationImpl.getJpaPersistenceUnitName());
    assertNull(processEngineConfigurationImpl.getMailServerPassword());
    assertNull(processEngineConfigurationImpl.getMailServerUsername());
    assertNull(processEngineConfigurationImpl.getMailSessionJndi());
    assertNull(processEngineConfigurationImpl.getAsyncExecutorLockOwner());
    assertNull(processEngineConfigurationImpl.getIdGeneratorDataSourceJndiName());
    assertNull(dateFormat.getNumberFormat());
    assertNull(dateFormat.getCalendar());
    assertNull(processEngineConfigurationImpl.getCustomScriptingEngineClasses());
    assertNull(processEngineConfigurationImpl.getCustomFunctionProviders());
    assertNull(processEngineConfigurationImpl.getAllConfigurators());
    assertNull(processEngineConfigurationImpl.getConfigurators());
    assertNull(processEngineConfigurationImpl.getEventListeners());
    assertNull(processEngineConfigurationImpl.getCustomEventHandlers());
    assertNull(processEngineConfigurationImpl.getCommandInterceptors());
    assertNull(processEngineConfigurationImpl.getCustomPostCommandInterceptors());
    assertNull(processEngineConfigurationImpl.getCustomPreCommandInterceptors());
    assertNull(processEngineConfigurationImpl.getCustomSessionFactories());
    assertNull(processEngineConfigurationImpl.getCustomJobHandlers());
    assertNull(processEngineConfigurationImpl.getCustomPostDeployers());
    assertNull(processEngineConfigurationImpl.getCustomPreDeployers());
    assertNull(processEngineConfigurationImpl.getDeployers());
    assertNull(processEngineConfigurationImpl.getResolverFactories());
    assertNull(processEngineConfigurationImpl.getCustomPostVariableTypes());
    assertNull(processEngineConfigurationImpl.getCustomPreVariableTypes());
    assertNull(processEngineConfigurationImpl.getCustomDefaultBpmnParseHandlers());
    assertNull(processEngineConfigurationImpl.getPostBpmnParseHandlers());
    assertNull(processEngineConfigurationImpl.getPreBpmnParseHandlers());
    assertNull(processEngineConfigurationImpl.getSessionFactories());
    assertNull(processEngineConfigurationImpl.getBeans());
    assertNull(processEngineConfigurationImpl.getTypedEventListeners());
    assertNull(processEngineConfigurationImpl.getEventHandlers());
    assertNull(processEngineConfigurationImpl.getJobHandlers());
    assertNull(processEngineConfigurationImpl.getCustomMybatisMappers());
    assertNull(processEngineConfigurationImpl.getCustomMybatisXMLMappers());
    assertNull(dateFormat.getTimeZone());
    assertNull(processEngineConfigurationImpl.getAsyncExecutorThreadPoolQueue());
    assertNull(processEngineConfigurationImpl.getDataSource());
    assertNull(processEngineConfigurationImpl.getIdGeneratorDataSource());
    assertNull(processEngineConfigurationImpl.getUserGroupManager());
    assertNull(processEngineConfigurationImpl.getEngineAgendaFactory());
    assertNull(processEngineConfigurationImpl.getProcessEngineLifecycleListener());
    assertNull(processEngineConfigurationImpl.getEventDispatcher());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionHelper());
    assertNull(processEngineConfigurationImpl.getAsyncExecutor());
    assertNull(processEngineConfigurationImpl.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(processEngineConfigurationImpl.getJobManager());
    assertNull(processEngineConfigurationImpl.getListenerNotificationHelper());
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        processEngineConfigurationImpl.getParsedDeploymentBuilderFactory();
    assertNull(parsedDeploymentBuilderFactory.getBpmnParser());
    assertNull(processEngineConfigurationImpl.getBpmnParser());
    assertNull(processEngineConfigurationImpl.getActivityBehaviorFactory());
    assertNull(processEngineConfigurationImpl.getListenerFactory());
    assertNull(processEngineConfigurationImpl.getBusinessCalendarManager());
    assertNull(processEngineConfigurationImpl.getBpmnParseFactory());
    BpmnDeployer bpmnDeployer = processEngineConfigurationImpl.getBpmnDeployer();
    assertNull(bpmnDeployer.getIdGenerator());
    assertNull(processEngineConfigurationImpl.getIdGenerator());
    assertNull(processEngineConfigurationImpl.getTransactionContextFactory());
    assertNull(processEngineConfigurationImpl.getDbSqlSessionFactory());
    assertNull(processEngineConfigurationImpl.getExpressionManager());
    assertNull(processEngineConfigurationImpl.getHistoryLevel());
    assertNull(processEngineConfigurationImpl.getHistoryManager());
    assertNull(processEngineConfigurationImpl.getDefaultCommandConfig());
    assertNull(processEngineConfigurationImpl.getSchemaCommandConfig());
    assertNull(processEngineConfigurationImpl.getCommandContextFactory());
    assertNull(((DynamicBpmnServiceImpl) dynamicBpmnService).getCommandExecutor());
    assertNull(((HistoryServiceImpl) historyService).getCommandExecutor());
    assertNull(((ManagementServiceImpl) managementService).getCommandExecutor());
    assertNull(((RepositoryServiceImpl) repositoryService).getCommandExecutor());
    assertNull(((RuntimeServiceImpl) runtimeService).getCommandExecutor());
    assertNull(((TaskServiceImpl) taskService).getCommandExecutor());
    assertNull(processEngineConfigurationImpl.getCommandExecutor());
    assertNull(processEngineConfigurationImpl.getCommandInvoker());
    assertNull(processEngineConfigurationImpl.getDelegateInterceptor());
    assertNull(processEngineConfigurationImpl.getFailedJobCommandFactory());
    assertNull(processEngineConfigurationImpl.getKnowledgeBaseCache());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionCache());
    assertNull(processEngineConfigurationImpl.getDeploymentManager());
    assertNull(processEngineConfigurationImpl.getAttachmentEntityManager());
    assertNull(processEngineConfigurationImpl.getByteArrayEntityManager());
    assertNull(processEngineConfigurationImpl.getCommentEntityManager());
    assertNull(processEngineConfigurationImpl.getDeadLetterJobEntityManager());
    assertNull(processEngineConfigurationImpl.getDeploymentEntityManager());
    assertNull(processEngineConfigurationImpl.getEventLogEntryEntityManager());
    assertNull(processEngineConfigurationImpl.getEventSubscriptionEntityManager());
    assertNull(processEngineConfigurationImpl.getExecutionEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricActivityInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricDetailEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getHistoricVariableInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getIdentityLinkEntityManager());
    assertNull(processEngineConfigurationImpl.getJobEntityManager());
    assertNull(processEngineConfigurationImpl.getModelEntityManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionEntityManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionInfoEntityManager());
    assertNull(processEngineConfigurationImpl.getPropertyEntityManager());
    assertNull(processEngineConfigurationImpl.getResourceEntityManager());
    assertNull(processEngineConfigurationImpl.getSuspendedJobEntityManager());
    assertNull(processEngineConfigurationImpl.getTableDataManager());
    assertNull(processEngineConfigurationImpl.getTaskEntityManager());
    assertNull(processEngineConfigurationImpl.getTimerJobEntityManager());
    assertNull(processEngineConfigurationImpl.getVariableInstanceEntityManager());
    assertNull(processEngineConfigurationImpl.getAttachmentDataManager());
    assertNull(processEngineConfigurationImpl.getByteArrayDataManager());
    assertNull(processEngineConfigurationImpl.getCommentDataManager());
    assertNull(processEngineConfigurationImpl.getDeadLetterJobDataManager());
    assertNull(processEngineConfigurationImpl.getDeploymentDataManager());
    assertNull(processEngineConfigurationImpl.getEventLogEntryDataManager());
    assertNull(processEngineConfigurationImpl.getEventSubscriptionDataManager());
    assertNull(processEngineConfigurationImpl.getExecutionDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricActivityInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricDetailDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricIdentityLinkDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricProcessInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricTaskInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getHistoricVariableInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getIdentityLinkDataManager());
    assertNull(processEngineConfigurationImpl.getJobDataManager());
    assertNull(processEngineConfigurationImpl.getModelDataManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionDataManager());
    assertNull(processEngineConfigurationImpl.getProcessDefinitionInfoDataManager());
    assertNull(processEngineConfigurationImpl.getPropertyDataManager());
    assertNull(processEngineConfigurationImpl.getResourceDataManager());
    assertNull(processEngineConfigurationImpl.getSuspendedJobDataManager());
    assertNull(processEngineConfigurationImpl.getTaskDataManager());
    assertNull(processEngineConfigurationImpl.getTimerJobDataManager());
    assertNull(processEngineConfigurationImpl.getVariableInstanceDataManager());
    assertNull(processEngineConfigurationImpl.getScriptingEngines());
    assertNull(processEngineConfigurationImpl.getProcessInstanceHelper());
    assertNull(processEngineConfigurationImpl.getVariableTypes());
    assertNull(processEngineConfigurationImpl.getClock());
    assertNull(actualExecuteAsyncRunnable.job);
    assertNull(processEngineConfigurationImpl.getProcessValidator());
    assertNull(processEngineConfigurationImpl.getSqlSessionFactory());
    assertNull(processEngineConfigurationImpl.getTransactionFactory());
    assertEquals(-1, processEngineConfigurationImpl.getKnowledgeBaseCacheLimit());
    assertEquals(-1, processEngineConfigurationImpl.getMaxLengthStringVariableType());
    assertEquals(-1, processEngineConfigurationImpl.getProcessDefinitionCacheLimit());
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0, processEngineConfigurationImpl.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxActiveConnections());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxCheckoutTime());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxIdleConnections());
    assertEquals(0, processEngineConfigurationImpl.getJdbcMaxWaitTime());
    assertEquals(0, processEngineConfigurationImpl.getJdbcPingConnectionNotUsedFor());
    assertEquals(0, processEngineConfigurationImpl.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(1, factory.getParserFeatures());
    Collection<Class<? extends BaseElement>> handledTypes = getResult.getHandledTypes();
    assertEquals(1, handledTypes.size());
    Collection<Class<? extends BaseElement>> handledTypes2 = getResult2.getHandledTypes();
    assertEquals(1, handledTypes2.size());
    Collection<Class<? extends BaseElement>> handledTypes3 = getResult3.getHandledTypes();
    assertEquals(1, handledTypes3.size());
    Collection<Class<? extends BaseElement>> handledTypes4 = getResult4.getHandledTypes();
    assertEquals(1, handledTypes4.size());
    assertEquals(1, processEngineConfigurationImpl.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, processEngineConfigurationImpl.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, processEngineConfigurationImpl.getAsyncFailedJobWaitTime());
    assertEquals(10, processEngineConfigurationImpl.getDefaultFailedJobWaitTime());
    assertEquals(10, processEngineConfigurationImpl.getAsyncExecutorMaxPoolSize());
    assertEquals(100, processEngineConfigurationImpl.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, processEngineConfigurationImpl.getMaxNrOfStatementsInBulkInsert());
    assertEquals(
        10000, processEngineConfigurationImpl.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(
        10000, processEngineConfigurationImpl.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(17, versionResult2.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult2.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, versionResult2.getPatchLevel());
    assertEquals(2, processEngineConfigurationImpl.getAsyncExecutorCorePoolSize());
    assertEquals(20000, processEngineConfigurationImpl.getExecutionQueryLimit());
    assertEquals(20000, processEngineConfigurationImpl.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, processEngineConfigurationImpl.getHistoricTaskQueryLimit());
    assertEquals(20000, processEngineConfigurationImpl.getTaskQueryLimit());
    assertEquals(2079, factory.getGeneratorFeatures());
    assertEquals(21771068, serializationConfig.getSerializationFeatures());
    assertEquals(25, processEngineConfigurationImpl.getMailServerPort());
    assertEquals(25, processEngineConfigurationImpl.getBatchSizeProcessInstances());
    assertEquals(25, processEngineConfigurationImpl.getBatchSizeTasks());
    assertEquals(2500, processEngineConfigurationImpl.getIdBlockSize());
    assertEquals(3, processEngineConfigurationImpl.getAsyncExecutorNumberOfRetries());
    assertEquals(3, processEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(300000, processEngineConfigurationImpl.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(300000, processEngineConfigurationImpl.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(31, factory.getFactoryFeatures());
    assertEquals(4000, processEngineConfigurationImpl.getMaxLengthString());
    assertEquals(473998480, deserializationConfig.getDeserializationFeatures());
    assertEquals(5000L, processEngineConfigurationImpl.getAsyncExecutorThreadKeepAliveTime());
    assertEquals(
        51, processEngineConfigurationImpl.getMyBatisXmlConfigurationStream().read(new byte[51]));
    assertEquals(60, processEngineConfigurationImpl.getLockTimeAsyncJobWaitTime());
    assertEquals(60000, processEngineConfigurationImpl.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(60L, processEngineConfigurationImpl.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(
        70,
        ((JtaProcessEngineConfiguration) processEngineConfigurationImpl)
            .DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
    assertEquals(9999, nodeFactory.getMaxElementIndexForInsert());
    assertEquals(Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
    JsonSetter.Value defaultSetterInfo = deserializationConfig.getDefaultSetterInfo();
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getContentNulls());
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getValueNulls());
    assertEquals(
        DelegateExpressionFieldInjectionMode.MIXED,
        processEngineConfigurationImpl.getDelegateExpressionFieldInjectionMode());
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
    assertFalse(processEngineConfigurationImpl.getMailServerUseSSL());
    assertFalse(processEngineConfigurationImpl.getMailServerUseTLS());
    assertFalse(processEngineConfigurationImpl.isAsyncExecutorActivate());
    assertFalse(processEngineConfigurationImpl.isCopyVariablesToLocalForTasks());
    assertFalse(processEngineConfigurationImpl.isEnableProcessDefinitionInfoCache());
    assertFalse(processEngineConfigurationImpl.isJdbcPingEnabled());
    assertFalse(processEngineConfigurationImpl.isJpaCloseEntityManager());
    assertFalse(processEngineConfigurationImpl.isJpaHandleTransaction());
    assertFalse(processEngineConfigurationImpl.isTablePrefixIsSchema());
    PerformanceSettings performanceSettings =
        processEngineConfigurationImpl.getPerformanceSettings();
    assertFalse(performanceSettings.isEnableEagerExecutionTreeFetching());
    assertFalse(performanceSettings.isEnableExecutionRelationshipCounts());
    assertFalse(processEngineConfigurationImpl.isAsyncExecutorIsMessageQueueMode());
    assertFalse(processEngineConfigurationImpl.isEnableDatabaseEventLogging());
    assertFalse(processEngineConfigurationImpl.isEnableSafeBpmnXml());
    assertFalse(processEngineConfigurationImpl.isEnableVerboseExecutionTreeLogging());
    assertFalse(processEngineConfigurationImpl.isRollbackDeployment());
    assertFalse(processEngineConfigurationImpl.isSerializePOJOsInVariablesToJson());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(serializationConfig.isAnnotationProcessingEnabled());
    assertTrue(((StdDateFormat) dateFormat).isColonIncludedInTimeZone());
    assertTrue(handledTypes.contains(BoundaryEvent.class));
    assertTrue(handledTypes2.contains(BusinessRuleTask.class));
    assertTrue(handledTypes3.contains(Transaction.class));
    assertTrue(handledTypes4.contains(UserTask.class));
    assertTrue(processEngineConfigurationImpl.getMailServers().isEmpty());
    assertTrue(processEngineConfigurationImpl.getMailSessionsJndi().isEmpty());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    Set<Object> registeredModuleIds = objectMapper.getRegisteredModuleIds();
    assertTrue(registeredModuleIds.isEmpty());
    assertTrue(processEngineConfigurationImpl.isDbHistoryUsed());
    assertTrue(processEngineConfigurationImpl.isTransactionsExternallyManaged());
    assertTrue(processEngineConfigurationImpl.isUseClassForNameClassLoading());
    assertTrue(performanceSettings.isEnableLocalization());
    assertTrue(performanceSettings.isValidateExecutionRelationshipCountConfigOnBoot());
    assertTrue(processEngineConfigurationImpl.isBulkInsertEnabled());
    assertTrue(processEngineConfigurationImpl.isEnableConfiguratorServiceLoader());
    assertTrue(processEngineConfigurationImpl.isEnableEventDispatcher());
    assertTrue(processEngineConfigurationImpl.isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(processEngineConfigurationImpl.isUsingRelationalDatabase());
    assertEquals(
        Boolean.FALSE.toString(), processEngineConfigurationImpl.getDatabaseSchemaUpdate());
    Class<BoundaryEvent> expectedHandledType = BoundaryEvent.class;
    assertEquals(expectedHandledType, ((BoundaryEventParseHandler) getResult).getHandledType());
    Class<BusinessRuleTask> expectedHandledType2 = BusinessRuleTask.class;
    assertEquals(expectedHandledType2, ((BusinessRuleParseHandler) getResult2).getHandledType());
    Class<Transaction> expectedHandledType3 = Transaction.class;
    assertEquals(expectedHandledType3, ((TransactionParseHandler) getResult3).getHandledType());
    Class<UserTask> expectedHandledType4 = UserTask.class;
    assertEquals(expectedHandledType4, ((UserTaskParseHandler) getResult4).getHandledType());
    assertEquals(Integer.MAX_VALUE, base64Variant.getMaxLineLength());
    assertEquals('=', base64Variant.getPaddingByte());
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
    DatatypeFeatures datatypeFeatures = deserializationConfig.getDatatypeFeatures();
    assertSame(datatypeFeatures, serializerProviderInstance.getDatatypeFeatures());
    assertSame(datatypeFeatures, serializationConfig.getDatatypeFeatures());
    assertSame(defaultPropertyInclusion, serializationConfig.getDefaultPropertyInclusion());
    assertSame(defaultSetterInfo, serializationConfig.getDefaultSetterInfo());
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper bpmnDeploymentHelper =
        processEngineConfigurationImpl.getBpmnDeploymentHelper();
    assertSame(bpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(
        processEngineConfigurationImpl.getCachingAndArtifactsManager(),
        bpmnDeployer.getCachingAndArtifcatsManager());
    assertSame(
        processEngineConfigurationImpl.getEventSubscriptionManager(),
        bpmnDeploymentHelper.getEventSubscriptionManager());
    assertSame(objectMapper, factory.getCodec());
    assertSame(parsedDeploymentBuilderFactory, bpmnDeployer.getExParsedDeploymentBuilderFactory());
    assertSame(
        processEngineConfigurationImpl.getTimerManager(), bpmnDeploymentHelper.getTimerManager());
    assertSame(factory, objectMapper.getJsonFactory());
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
    assertSame(
        defaultNullValueSerializer, serializerProviderInstance.getDefaultNullValueSerializer());
    assertSame(dateFormat, deserializationConfig.getDateFormat());
    assertSame(dateFormat, serializationConfig.getDateFormat());
  }

  /**
   * Test {@link ExecuteAsyncRunnable#ExecuteAsyncRunnable(Job, ProcessEngineConfigurationImpl)}.
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#ExecuteAsyncRunnable(Job,
   * ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.<init>(Job, ProcessEngineConfigurationImpl)"})
  public void testNewExecuteAsyncRunnable2() {
    // Arrange
    DeadLetterJobEntityImpl job = new DeadLetterJobEntityImpl();

    // Act
    ExecuteAsyncRunnable actualExecuteAsyncRunnable =
        new ExecuteAsyncRunnable(job, new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(
        actualExecuteAsyncRunnable.processEngineConfiguration
            instanceof JtaProcessEngineConfiguration);
    assertTrue(actualExecuteAsyncRunnable.job instanceof DeadLetterJobEntityImpl);
    assertNull(actualExecuteAsyncRunnable.jobId);
  }

  /**
   * Test {@link ExecuteAsyncRunnable#executeJob()}.
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#executeJob()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.executeJob()"})
  public void testExecuteJob() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getCommandExecutor())
        .thenThrow(new ActivitiOptimisticLockingException("An error occurred"));
    ExecuteAsyncRunnable executeAsyncRunnable =
        new ExecuteAsyncRunnable("42", processEngineConfiguration);

    // Act and Assert
    assertThrows(ActivitiOptimisticLockingException.class, () -> executeAsyncRunnable.executeJob());
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
  }

  /**
   * Test {@link ExecuteAsyncRunnable#executeJob()}.
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#executeJob()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.executeJob()"})
  public void testExecuteJob2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(mock(CommandConfig.class), mock(CommandInterceptor.class));
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    ExecuteAsyncRunnable executeAsyncRunnable =
        new ExecuteAsyncRunnable("42", processEngineConfiguration);

    // Act
    executeAsyncRunnable.executeJob();

    // Assert
    verify(processEngineConfiguration).getCommandExecutor();
  }

  /**
   * Test {@link ExecuteAsyncRunnable#executeJob()}.
   *
   * <ul>
   *   <li>Then calls {@link CommandExecutor#execute(Command)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#executeJob()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.executeJob()"})
  public void testExecuteJob_thenCallsExecute() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenThrow(new ActivitiOptimisticLockingException("An error occurred"));

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutor);
    ExecuteAsyncRunnable executeAsyncRunnable =
        new ExecuteAsyncRunnable("42", processEngineConfiguration);

    // Act and Assert
    assertThrows(ActivitiOptimisticLockingException.class, () -> executeAsyncRunnable.executeJob());
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(commandExecutor, atLeast(1)).execute(Mockito.<Command<Object>>any());
  }

  /**
   * Test {@link ExecuteAsyncRunnable#executeJob()}.
   *
   * <ul>
   *   <li>Then calls {@link CommandConfig#isContextReusePossible()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#executeJob()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.executeJob()"})
  public void testExecuteJob_thenCallsIsContextReusePossible() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.isContextReusePossible())
        .thenThrow(new ActivitiOptimisticLockingException("An error occurred"));
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    ExecuteAsyncRunnable executeAsyncRunnable =
        new ExecuteAsyncRunnable("42", processEngineConfiguration);

    // Act and Assert
    assertThrows(ActivitiOptimisticLockingException.class, () -> executeAsyncRunnable.executeJob());
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(defaultConfig, atLeast(1)).isContextReusePossible();
  }

  /**
   * Test {@link ExecuteAsyncRunnable#unlockJobIfNeeded()}.
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#unlockJobIfNeeded()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.unlockJobIfNeeded()"})
  public void testUnlockJobIfNeeded() {
    // Arrange
    DeadLetterJobEntityImpl job = mock(DeadLetterJobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");
    ExecuteAsyncRunnable executeAsyncRunnable = new ExecuteAsyncRunnable(job, null);

    // Act
    executeAsyncRunnable.unlockJobIfNeeded();

    // Assert
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
  }

  /**
   * Test {@link ExecuteAsyncRunnable#unlockJobIfNeeded()}.
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#unlockJobIfNeeded()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.unlockJobIfNeeded()"})
  public void testUnlockJobIfNeeded2() {
    // Arrange
    DeadLetterJobEntityImpl job = mock(DeadLetterJobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecuteAsyncRunnable executeAsyncRunnable =
        new ExecuteAsyncRunnable(job, processEngineConfiguration);

    // Act
    executeAsyncRunnable.unlockJobIfNeeded();

    // Assert
    verify(job, atLeast(1)).getId();
    verify(job).isExclusive();
  }

  /**
   * Test {@link ExecuteAsyncRunnable#unlockJobIfNeeded()}.
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#unlockJobIfNeeded()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.unlockJobIfNeeded()"})
  public void testUnlockJobIfNeeded3() {
    // Arrange
    DeadLetterJobEntityImpl job = mock(DeadLetterJobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(mock(CommandConfig.class), mock(CommandInterceptor.class));
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecuteAsyncRunnable executeAsyncRunnable =
        new ExecuteAsyncRunnable(job, processEngineConfiguration);

    // Act
    executeAsyncRunnable.unlockJobIfNeeded();

    // Assert
    verify(job).getId();
    verify(job).isExclusive();
  }

  /**
   * Test {@link ExecuteAsyncRunnable#unlockJobIfNeeded()}.
   *
   * <ul>
   *   <li>Then calls {@link CommandExecutor#execute(Command)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#unlockJobIfNeeded()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.unlockJobIfNeeded()"})
  public void testUnlockJobIfNeeded_thenCallsExecute() {
    // Arrange
    DeadLetterJobEntityImpl job = mock(DeadLetterJobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenThrow(new ActivitiOptimisticLockingException("An error occurred"));

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecuteAsyncRunnable executeAsyncRunnable =
        new ExecuteAsyncRunnable(job, processEngineConfiguration);

    // Act
    executeAsyncRunnable.unlockJobIfNeeded();

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    verify(job).getId();
    verify(job).isExclusive();
  }

  /**
   * Test {@link ExecuteAsyncRunnable#unlockJobIfNeeded()}.
   *
   * <ul>
   *   <li>Then calls {@link CommandConfig#isContextReusePossible()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#unlockJobIfNeeded()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.unlockJobIfNeeded()"})
  public void testUnlockJobIfNeeded_thenCallsIsContextReusePossible() {
    // Arrange
    DeadLetterJobEntityImpl job = mock(DeadLetterJobEntityImpl.class);
    when(job.isExclusive()).thenReturn(true);
    when(job.getId()).thenReturn("42");

    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.isContextReusePossible())
        .thenThrow(new ActivitiOptimisticLockingException("An error occurred"));
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecuteAsyncRunnable executeAsyncRunnable =
        new ExecuteAsyncRunnable(job, processEngineConfiguration);

    // Act
    executeAsyncRunnable.unlockJobIfNeeded();

    // Assert
    verify(defaultConfig).isContextReusePossible();
    verify(job).getId();
    verify(job).isExclusive();
  }

  /**
   * Test {@link ExecuteAsyncRunnable#unacquireJob()}.
   *
   * <ul>
   *   <li>Given {@link CommandInterceptor} {@link CommandInterceptor#execute(CommandConfig,
   *       Command)} return {@code null}.
   *   <li>Then calls {@link CommandInterceptor#execute(CommandConfig, Command)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#unacquireJob()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.unacquireJob()"})
  public void testUnacquireJob_givenCommandInterceptorExecuteReturnNull_thenCallsExecute() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Void>>any()))
        .thenReturn(null);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);
    ExecuteAsyncRunnable executeAsyncRunnable =
        new ExecuteAsyncRunnable("42", processEngineConfiguration);

    // Act
    executeAsyncRunnable.unacquireJob();

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ExecuteAsyncRunnable#handleFailedJob(Throwable)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getCommandExecutor()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteAsyncRunnable#handleFailedJob(Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteAsyncRunnable.handleFailedJob(Throwable)"})
  public void testHandleFailedJob_thenCallsGetCommandExecutor() {
    // Arrange
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    ExecuteAsyncRunnable executeAsyncRunnable =
        new ExecuteAsyncRunnable("42", processEngineConfiguration);

    // Act
    executeAsyncRunnable.handleFailedJob(new Throwable());

    // Assert
    verify(processEngineConfiguration).getCommandExecutor();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }
}
