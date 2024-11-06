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
package org.activiti.engine.impl.cfg;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
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
import jakarta.transaction.TransactionManager;
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
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.Transaction;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ActivitiException;
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
import org.activiti.engine.impl.bpmn.parser.handler.CallActivityParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TimerEventDefinitionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.TransactionParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.cfg.jta.JtaTransactionContextFactory;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.JtaTransactionInterceptor;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.engine.parse.BpmnParseHandler;
import org.junit.Test;

public class JtaProcessEngineConfigurationDiffblueTest {
  /**
   * Test new {@link JtaProcessEngineConfiguration} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link JtaProcessEngineConfiguration}
   */
  @Test
  public void testNewJtaProcessEngineConfiguration() throws IOException, MissingResourceException {
    // Arrange and Act
    JtaProcessEngineConfiguration actualJtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Assert
    ObjectMapper objectMapper = actualJtaProcessEngineConfiguration.getObjectMapper();
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
    AccessorNamingStrategy.Provider accessorNaming = deserializationConfig.getAccessorNaming();
    assertTrue(accessorNaming instanceof DefaultAccessorNamingStrategy.Provider);
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    VisibilityChecker<?> visibilityChecker = objectMapper.getVisibilityChecker();
    assertTrue(visibilityChecker instanceof VisibilityChecker.Std);
    PolymorphicTypeValidator polymorphicTypeValidator = objectMapper.getPolymorphicTypeValidator();
    assertTrue(polymorphicTypeValidator instanceof LaissezFaireSubTypeValidator);
    SubtypeResolver subtypeResolver = objectMapper.getSubtypeResolver();
    assertTrue(subtypeResolver instanceof StdSubtypeResolver);
    SerializerFactory serializerFactory = objectMapper.getSerializerFactory();
    assertTrue(serializerFactory instanceof BeanSerializerFactory);
    SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
    assertTrue(serializerProvider instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
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
    DateFormat dateFormat = objectMapper.getDateFormat();
    assertTrue(dateFormat instanceof StdDateFormat);
    Collection<? extends Deployer> defaultDeployers = actualJtaProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    DynamicBpmnService dynamicBpmnService = actualJtaProcessEngineConfiguration.getDynamicBpmnService();
    assertTrue(dynamicBpmnService instanceof DynamicBpmnServiceImpl);
    HistoryService historyService = actualJtaProcessEngineConfiguration.getHistoryService();
    assertTrue(historyService instanceof HistoryServiceImpl);
    ManagementService managementService = actualJtaProcessEngineConfiguration.getManagementService();
    assertTrue(managementService instanceof ManagementServiceImpl);
    RepositoryService repositoryService = actualJtaProcessEngineConfiguration.getRepositoryService();
    assertTrue(repositoryService instanceof RepositoryServiceImpl);
    RuntimeService runtimeService = actualJtaProcessEngineConfiguration.getRuntimeService();
    assertTrue(runtimeService instanceof RuntimeServiceImpl);
    TaskService taskService = actualJtaProcessEngineConfiguration.getTaskService();
    assertTrue(taskService instanceof TaskServiceImpl);
    List<BpmnParseHandler> defaultBpmnParseHandlers = actualJtaProcessEngineConfiguration.getDefaultBpmnParseHandlers();
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
    assertTrue(
        actualJtaProcessEngineConfiguration.getIntegrationContextManager() instanceof IntegrationContextManagerImpl);
    assertTrue(
        actualJtaProcessEngineConfiguration.getIntegrationContextService() instanceof IntegrationContextServiceImpl);
    assertEquals(" ", factory.getRootValueSeparator());
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("", actualJtaProcessEngineConfiguration.getDatabaseCatalog());
    assertEquals("", actualJtaProcessEngineConfiguration.getDatabaseTablePrefix());
    assertEquals("", actualJtaProcessEngineConfiguration.getJdbcPassword());
    assertEquals("@class", actualJtaProcessEngineConfiguration.getJavaClassFieldForJackson());
    TimeZone timeZone = deserializationConfig.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("English (United Kingdom)", locale.getDisplayName());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("GB", locale.getCountry());
    assertEquals("GBR", locale.getISO3Country());
    assertEquals("JSON", factory.getFormatName());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
    assertEquals("UTF-8", actualJtaProcessEngineConfiguration.getXmlEncoding());
    assertEquals("United Kingdom", locale.getDisplayCountry());
    assertEquals("[one of: 'yyyy-MM-dd'T'HH:mm:ss.SSSX', 'EEE, dd MMM yyyy HH:mm:ss zzz' (lenient)]",
        ((StdDateFormat) dateFormat).toPattern());
    assertEquals("activiti@localhost", actualJtaProcessEngineConfiguration.getMailServerDefaultFrom());
    assertEquals("audit", actualJtaProcessEngineConfiguration.getHistory());
    assertEquals("camelContext", actualJtaProcessEngineConfiguration.getDefaultCamelContext());
    Version versionResult = factory.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    Version versionResult2 = objectMapper.version();
    assertEquals("com.fasterxml.jackson.core", versionResult2.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-core/2.17.2", versionResult.toFullString());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult2.toFullString());
    assertEquals("default", actualJtaProcessEngineConfiguration.getProcessEngineName());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("jackson-core", versionResult.getArtifactId());
    assertEquals("jackson-databind", versionResult2.getArtifactId());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", actualJtaProcessEngineConfiguration.getJdbcUrl());
    assertEquals("localhost", actualJtaProcessEngineConfiguration.getMailServerHost());
    assertEquals("org.h2.Driver", actualJtaProcessEngineConfiguration.getJdbcDriver());
    assertEquals("sa", actualJtaProcessEngineConfiguration.getJdbcUsername());
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
    assertNull(actualJtaProcessEngineConfiguration.getTransactionManager());
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
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    assertNull(typeFactory.getClassLoader());
    assertNull(actualJtaProcessEngineConfiguration.getClassLoader());
    assertNull(actualJtaProcessEngineConfiguration.getJpaEntityManagerFactory());
    assertNull(deserializationConfig.getRootName());
    assertNull(serializationConfig.getRootName());
    assertNull(actualJtaProcessEngineConfiguration.getDataSourceJndiName());
    assertNull(actualJtaProcessEngineConfiguration.getDatabaseSchema());
    assertNull(actualJtaProcessEngineConfiguration.getDatabaseType());
    assertNull(actualJtaProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertNull(actualJtaProcessEngineConfiguration.getJdbcPingQuery());
    assertNull(actualJtaProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertNull(actualJtaProcessEngineConfiguration.getMailServerPassword());
    assertNull(actualJtaProcessEngineConfiguration.getMailServerUsername());
    assertNull(actualJtaProcessEngineConfiguration.getMailSessionJndi());
    assertNull(actualJtaProcessEngineConfiguration.getAsyncExecutorLockOwner());
    assertNull(actualJtaProcessEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertNull(dateFormat.getNumberFormat());
    assertNull(dateFormat.getCalendar());
    assertNull(actualJtaProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertNull(actualJtaProcessEngineConfiguration.getCustomFunctionProviders());
    assertNull(actualJtaProcessEngineConfiguration.getAllConfigurators());
    assertNull(actualJtaProcessEngineConfiguration.getConfigurators());
    assertNull(actualJtaProcessEngineConfiguration.getEventListeners());
    assertNull(actualJtaProcessEngineConfiguration.getCustomEventHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getCommandInterceptors());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPostCommandInterceptors());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPreCommandInterceptors());
    assertNull(actualJtaProcessEngineConfiguration.getCustomSessionFactories());
    assertNull(actualJtaProcessEngineConfiguration.getCustomJobHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPostDeployers());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPreDeployers());
    assertNull(actualJtaProcessEngineConfiguration.getDeployers());
    assertNull(actualJtaProcessEngineConfiguration.getResolverFactories());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPostVariableTypes());
    assertNull(actualJtaProcessEngineConfiguration.getCustomPreVariableTypes());
    assertNull(actualJtaProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getPostBpmnParseHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getPreBpmnParseHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getSessionFactories());
    assertNull(actualJtaProcessEngineConfiguration.getBeans());
    assertNull(actualJtaProcessEngineConfiguration.getTypedEventListeners());
    assertNull(actualJtaProcessEngineConfiguration.getEventHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getJobHandlers());
    assertNull(actualJtaProcessEngineConfiguration.getCustomMybatisMappers());
    assertNull(actualJtaProcessEngineConfiguration.getCustomMybatisXMLMappers());
    assertNull(dateFormat.getTimeZone());
    assertNull(actualJtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(actualJtaProcessEngineConfiguration.getDataSource());
    assertNull(actualJtaProcessEngineConfiguration.getIdGeneratorDataSource());
    assertNull(actualJtaProcessEngineConfiguration.getUserGroupManager());
    assertNull(actualJtaProcessEngineConfiguration.getEngineAgendaFactory());
    assertNull(actualJtaProcessEngineConfiguration.getProcessEngineLifecycleListener());
    assertNull(actualJtaProcessEngineConfiguration.getEventDispatcher());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionHelper());
    assertNull(actualJtaProcessEngineConfiguration.getAsyncExecutor());
    assertNull(actualJtaProcessEngineConfiguration.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(actualJtaProcessEngineConfiguration.getJobManager());
    assertNull(actualJtaProcessEngineConfiguration.getListenerNotificationHelper());
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = actualJtaProcessEngineConfiguration
        .getParsedDeploymentBuilderFactory();
    assertNull(parsedDeploymentBuilderFactory.getBpmnParser());
    assertNull(actualJtaProcessEngineConfiguration.getBpmnParser());
    assertNull(actualJtaProcessEngineConfiguration.getActivityBehaviorFactory());
    assertNull(actualJtaProcessEngineConfiguration.getListenerFactory());
    assertNull(actualJtaProcessEngineConfiguration.getBusinessCalendarManager());
    assertNull(actualJtaProcessEngineConfiguration.getBpmnParseFactory());
    BpmnDeployer bpmnDeployer = actualJtaProcessEngineConfiguration.getBpmnDeployer();
    assertNull(bpmnDeployer.getIdGenerator());
    assertNull(actualJtaProcessEngineConfiguration.getIdGenerator());
    assertNull(actualJtaProcessEngineConfiguration.getTransactionContextFactory());
    assertNull(actualJtaProcessEngineConfiguration.getDbSqlSessionFactory());
    assertNull(actualJtaProcessEngineConfiguration.getExpressionManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoryLevel());
    assertNull(actualJtaProcessEngineConfiguration.getHistoryManager());
    assertNull(actualJtaProcessEngineConfiguration.getDefaultCommandConfig());
    assertNull(actualJtaProcessEngineConfiguration.getSchemaCommandConfig());
    assertNull(actualJtaProcessEngineConfiguration.getCommandContextFactory());
    assertNull(((DynamicBpmnServiceImpl) dynamicBpmnService).getCommandExecutor());
    assertNull(((HistoryServiceImpl) historyService).getCommandExecutor());
    assertNull(((ManagementServiceImpl) managementService).getCommandExecutor());
    assertNull(((RepositoryServiceImpl) repositoryService).getCommandExecutor());
    assertNull(((RuntimeServiceImpl) runtimeService).getCommandExecutor());
    assertNull(((TaskServiceImpl) taskService).getCommandExecutor());
    assertNull(actualJtaProcessEngineConfiguration.getCommandExecutor());
    assertNull(actualJtaProcessEngineConfiguration.getCommandInvoker());
    assertNull(actualJtaProcessEngineConfiguration.getDelegateInterceptor());
    assertNull(actualJtaProcessEngineConfiguration.getFailedJobCommandFactory());
    assertNull(actualJtaProcessEngineConfiguration.getKnowledgeBaseCache());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionCache());
    assertNull(actualJtaProcessEngineConfiguration.getDeploymentManager());
    assertNull(actualJtaProcessEngineConfiguration.processDefinitionInfoCache);
    assertNull(actualJtaProcessEngineConfiguration.getAttachmentEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getByteArrayEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getCommentEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getDeadLetterJobEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getDeploymentEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getEventLogEntryEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getEventSubscriptionEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getExecutionEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricActivityInstanceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricDetailEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricProcessInstanceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricVariableInstanceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getIdentityLinkEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getJobEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getModelEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionInfoEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getPropertyEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getResourceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getSuspendedJobEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getTableDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getTaskEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getTimerJobEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getVariableInstanceEntityManager());
    assertNull(actualJtaProcessEngineConfiguration.getAttachmentDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getByteArrayDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getCommentDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getDeadLetterJobDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getDeploymentDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getEventLogEntryDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getEventSubscriptionDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getExecutionDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricDetailDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getIdentityLinkDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getJobDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getModelDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getPropertyDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getResourceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getSuspendedJobDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getTaskDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getTimerJobDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getVariableInstanceDataManager());
    assertNull(actualJtaProcessEngineConfiguration.getScriptingEngines());
    assertNull(actualJtaProcessEngineConfiguration.getProcessInstanceHelper());
    assertNull(actualJtaProcessEngineConfiguration.getVariableTypes());
    assertNull(actualJtaProcessEngineConfiguration.getClock());
    assertNull(actualJtaProcessEngineConfiguration.getProcessValidator());
    assertNull(actualJtaProcessEngineConfiguration.getSqlSessionFactory());
    assertNull(actualJtaProcessEngineConfiguration.getTransactionFactory());
    assertEquals(-1, actualJtaProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertEquals(-1, actualJtaProcessEngineConfiguration.getMaxLengthStringVariableType());
    assertEquals(-1, actualJtaProcessEngineConfiguration.getProcessDefinitionCacheLimit());
    assertEquals(-1, actualJtaProcessEngineConfiguration.processDefinitionInfoCacheLimit);
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertEquals(0, actualJtaProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertEquals(0, actualJtaProcessEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(1, factory.getParserFeatures());
    assertEquals(1, getResult.getHandledTypes().size());
    assertEquals(1, getResult2.getHandledTypes().size());
    assertEquals(1, getResult3.getHandledTypes().size());
    assertEquals(1, getResult4.getHandledTypes().size());
    assertEquals(1, getResult5.getHandledTypes().size());
    assertEquals(1, getResult6.getHandledTypes().size());
    assertEquals(1, actualJtaProcessEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualJtaProcessEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, actualJtaProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertEquals(10, actualJtaProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertEquals(10, actualJtaProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertEquals(100, actualJtaProcessEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, actualJtaProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertEquals(10000, actualJtaProcessEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(10000, actualJtaProcessEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(17, versionResult2.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult2.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, versionResult2.getPatchLevel());
    assertEquals(2, actualJtaProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertEquals(20000, actualJtaProcessEngineConfiguration.getExecutionQueryLimit());
    assertEquals(20000, actualJtaProcessEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, actualJtaProcessEngineConfiguration.getHistoricTaskQueryLimit());
    assertEquals(20000, actualJtaProcessEngineConfiguration.getTaskQueryLimit());
    assertEquals(2079, factory.getGeneratorFeatures());
    assertEquals(21771068, serializationConfig.getSerializationFeatures());
    assertEquals(25, actualJtaProcessEngineConfiguration.getMailServerPort());
    assertEquals(25, actualJtaProcessEngineConfiguration.getBatchSizeProcessInstances());
    assertEquals(25, actualJtaProcessEngineConfiguration.getBatchSizeTasks());
    assertEquals(2500, actualJtaProcessEngineConfiguration.getIdBlockSize());
    assertEquals(3, actualJtaProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertEquals(3, actualJtaProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(300000, actualJtaProcessEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(300000, actualJtaProcessEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(31, factory.getFactoryFeatures());
    assertEquals(473998480, deserializationConfig.getDeserializationFeatures());
    assertEquals(5000L, actualJtaProcessEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualJtaProcessEngineConfiguration.getMyBatisXmlConfigurationStream().read(byteArray));
    assertEquals(60, actualJtaProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertEquals(60000, actualJtaProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(60L, actualJtaProcessEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(70, actualJtaProcessEngineConfiguration.DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
    assertEquals(9999, nodeFactory.getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
    JsonSetter.Value defaultSetterInfo = deserializationConfig.getDefaultSetterInfo();
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getContentNulls());
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getValueNulls());
    assertEquals(DelegateExpressionFieldInjectionMode.MIXED,
        actualJtaProcessEngineConfiguration.getDelegateExpressionFieldInjectionMode());
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
    assertFalse(actualJtaProcessEngineConfiguration.getMailServerUseSSL());
    assertFalse(actualJtaProcessEngineConfiguration.getMailServerUseTLS());
    assertFalse(actualJtaProcessEngineConfiguration.isAsyncExecutorActivate());
    assertFalse(actualJtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertFalse(actualJtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertFalse(actualJtaProcessEngineConfiguration.isJdbcPingEnabled());
    assertFalse(actualJtaProcessEngineConfiguration.isJpaCloseEntityManager());
    assertFalse(actualJtaProcessEngineConfiguration.isJpaHandleTransaction());
    assertFalse(actualJtaProcessEngineConfiguration.isTablePrefixIsSchema());
    PerformanceSettings performanceSettings = actualJtaProcessEngineConfiguration.getPerformanceSettings();
    assertFalse(performanceSettings.isEnableEagerExecutionTreeFetching());
    assertFalse(performanceSettings.isEnableExecutionRelationshipCounts());
    assertFalse(actualJtaProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertFalse(actualJtaProcessEngineConfiguration.isEnableDatabaseEventLogging());
    assertFalse(actualJtaProcessEngineConfiguration.isEnableSafeBpmnXml());
    assertFalse(actualJtaProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertFalse(actualJtaProcessEngineConfiguration.isRollbackDeployment());
    assertFalse(actualJtaProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(serializationConfig.isAnnotationProcessingEnabled());
    assertTrue(((StdDateFormat) dateFormat).isColonIncludedInTimeZone());
    assertTrue(dateFormat.isLenient());
    assertTrue(actualJtaProcessEngineConfiguration.getMailServers().isEmpty());
    assertTrue(actualJtaProcessEngineConfiguration.getMailSessionsJndi().isEmpty());
    assertTrue(actualJtaProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
    Set<Object> registeredModuleIds = objectMapper.getRegisteredModuleIds();
    assertTrue(registeredModuleIds.isEmpty());
    assertTrue(actualJtaProcessEngineConfiguration.isDbHistoryUsed());
    assertTrue(actualJtaProcessEngineConfiguration.isTransactionsExternallyManaged());
    assertTrue(actualJtaProcessEngineConfiguration.isUseClassForNameClassLoading());
    assertTrue(performanceSettings.isEnableLocalization());
    assertTrue(performanceSettings.isValidateExecutionRelationshipCountConfigOnBoot());
    assertTrue(actualJtaProcessEngineConfiguration.isBulkInsertEnabled());
    assertTrue(actualJtaProcessEngineConfiguration.isEnableConfiguratorServiceLoader());
    assertTrue(actualJtaProcessEngineConfiguration.isEnableEventDispatcher());
    assertTrue(actualJtaProcessEngineConfiguration.isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(actualJtaProcessEngineConfiguration.isUsingRelationalDatabase());
    String expectedDatabaseSchemaUpdate = Boolean.FALSE.toString();
    assertEquals(expectedDatabaseSchemaUpdate, actualJtaProcessEngineConfiguration.getDatabaseSchemaUpdate());
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
    assertEquals(ProcessEngineConfigurationImpl.DEFAULT_GENERIC_MAX_LENGTH_STRING,
        actualJtaProcessEngineConfiguration.getMaxLengthString());
    assertEquals(ProcessEngineConfigurationImpl.DEFAULT_WS_SYNC_FACTORY,
        actualJtaProcessEngineConfiguration.getWsSyncFactoryClassName());
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
    assertSame(defaultPropertyInclusion, serializationConfig.getDefaultPropertyInclusion());
    assertSame(defaultSetterInfo, serializationConfig.getDefaultSetterInfo());
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualJtaProcessEngineConfiguration.getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper, factory.getCodec());
    assertSame(parsedDeploymentBuilderFactory, bpmnDeployer.getExParsedDeploymentBuilderFactory());
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
    assertSame(defaultNullValueSerializer, serializerProviderInstance.getDefaultNullValueSerializer());
    assertSame(dateFormat, deserializationConfig.getDateFormat());
    assertSame(dateFormat, serializationConfig.getDateFormat());
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link JtaProcessEngineConfiguration#createTransactionInterceptor()}.
   * <ul>
   *   <li>Then return {@link JtaTransactionInterceptor}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JtaProcessEngineConfiguration#createTransactionInterceptor()}
   */
  @Test
  public void testCreateTransactionInterceptor_thenReturnJtaTransactionInterceptor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    // Act
    CommandInterceptor actualCreateTransactionInterceptorResult = jtaProcessEngineConfiguration
        .createTransactionInterceptor();

    // Assert
    assertTrue(actualCreateTransactionInterceptorResult instanceof JtaTransactionInterceptor);
    assertNull(actualCreateTransactionInterceptorResult.getNext());
  }

  /**
   * Test {@link JtaProcessEngineConfiguration#createTransactionInterceptor()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JtaProcessEngineConfiguration#createTransactionInterceptor()}
   */
  @Test
  public void testCreateTransactionInterceptor_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new JtaProcessEngineConfiguration()).createTransactionInterceptor());
  }

  /**
   * Test {@link JtaProcessEngineConfiguration#initTransactionContextFactory()}.
   * <p>
   * Method under test:
   * {@link JtaProcessEngineConfiguration#initTransactionContextFactory()}
   */
  @Test
  public void testInitTransactionContextFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    jtaProcessEngineConfiguration.initTransactionContextFactory();

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getTransactionContextFactory() instanceof JtaTransactionContextFactory);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link JtaProcessEngineConfiguration#setTransactionManager(TransactionManager)}
   *   <li>{@link JtaProcessEngineConfiguration#getTransactionManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    TransactionManager transactionManager = mock(TransactionManager.class);

    // Act
    jtaProcessEngineConfiguration.setTransactionManager(transactionManager);

    // Assert that nothing has changed
    assertSame(transactionManager, jtaProcessEngineConfiguration.getTransactionManager());
  }
}
