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
package org.activiti.spring;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
import javax.sql.DataSource;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.Transaction;
import org.activiti.bpmn.model.UserTask;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.activiti.core.el.CustomFunctionProvider;
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
import org.activiti.engine.impl.cfg.DelegateExpressionFieldInjectionMode;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.cfg.TransactionPropagation;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.TransactionContextInterceptor;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.spring.autodeployment.DefaultAutoDeploymentStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {
    "/org/activiti/spring/test/transaction/SpringTransactionIntegrationTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SpringProcessEngineConfigurationDiffblueTest {
  @Autowired
  private SpringProcessEngineConfiguration springProcessEngineConfiguration;

  /**
   * Test
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration()}.
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration()}
   */
  @Test
  public void testNewSpringProcessEngineConfiguration() throws IOException, MissingResourceException {
    // Arrange and Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfiguration = new SpringProcessEngineConfiguration();

    // Assert
    ObjectMapper objectMapper = actualSpringProcessEngineConfiguration.getObjectMapper();
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
    Collection<? extends Deployer> defaultDeployers = actualSpringProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    DynamicBpmnService dynamicBpmnService = actualSpringProcessEngineConfiguration.getDynamicBpmnService();
    assertTrue(dynamicBpmnService instanceof DynamicBpmnServiceImpl);
    HistoryService historyService = actualSpringProcessEngineConfiguration.getHistoryService();
    assertTrue(historyService instanceof HistoryServiceImpl);
    ManagementService managementService = actualSpringProcessEngineConfiguration.getManagementService();
    assertTrue(managementService instanceof ManagementServiceImpl);
    RepositoryService repositoryService = actualSpringProcessEngineConfiguration.getRepositoryService();
    assertTrue(repositoryService instanceof RepositoryServiceImpl);
    RuntimeService runtimeService = actualSpringProcessEngineConfiguration.getRuntimeService();
    assertTrue(runtimeService instanceof RuntimeServiceImpl);
    TaskService taskService = actualSpringProcessEngineConfiguration.getTaskService();
    assertTrue(taskService instanceof TaskServiceImpl);
    List<BpmnParseHandler> defaultBpmnParseHandlers = actualSpringProcessEngineConfiguration
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
    assertTrue(
        actualSpringProcessEngineConfiguration.getIntegrationContextManager() instanceof IntegrationContextManagerImpl);
    assertTrue(
        actualSpringProcessEngineConfiguration.getIntegrationContextService() instanceof IntegrationContextServiceImpl);
    assertEquals(" ", factory.getRootValueSeparator());
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("", actualSpringProcessEngineConfiguration.getDatabaseCatalog());
    assertEquals("", actualSpringProcessEngineConfiguration.getDatabaseTablePrefix());
    assertEquals("", actualSpringProcessEngineConfiguration.getJdbcPassword());
    assertEquals("@class", actualSpringProcessEngineConfiguration.getJavaClassFieldForJackson());
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
    assertEquals("SpringAutoDeployment", actualSpringProcessEngineConfiguration.getDeploymentName());
    assertEquals("UTC", timeZone.getID());
    assertEquals("UTF-8", actualSpringProcessEngineConfiguration.getXmlEncoding());
    assertEquals("United Kingdom", locale.getDisplayCountry());
    assertEquals("[one of: 'yyyy-MM-dd'T'HH:mm:ss.SSSX', 'EEE, dd MMM yyyy HH:mm:ss zzz' (lenient)]",
        ((StdDateFormat) dateFormat).toPattern());
    assertEquals("activiti@localhost", actualSpringProcessEngineConfiguration.getMailServerDefaultFrom());
    assertEquals("audit", actualSpringProcessEngineConfiguration.getHistory());
    assertEquals("camelContext", actualSpringProcessEngineConfiguration.getDefaultCamelContext());
    Version versionResult = factory.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    Version versionResult2 = objectMapper.version();
    assertEquals("com.fasterxml.jackson.core", versionResult2.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-core/2.17.2", versionResult.toFullString());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult2.toFullString());
    assertEquals("default", actualSpringProcessEngineConfiguration.getProcessEngineName());
    assertEquals("default", actualSpringProcessEngineConfiguration.getDeploymentMode());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("jackson-core", versionResult.getArtifactId());
    assertEquals("jackson-databind", versionResult2.getArtifactId());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", actualSpringProcessEngineConfiguration.getJdbcUrl());
    assertEquals("localhost", actualSpringProcessEngineConfiguration.getMailServerHost());
    assertEquals("org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        actualSpringProcessEngineConfiguration.getWsSyncFactoryClassName());
    assertEquals("org.h2.Driver", actualSpringProcessEngineConfiguration.getJdbcDriver());
    assertEquals("sa", actualSpringProcessEngineConfiguration.getJdbcUsername());
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
    assertNull(actualSpringProcessEngineConfiguration.getClassLoader());
    assertNull(actualSpringProcessEngineConfiguration.transactionSynchronizationAdapterOrder);
    assertNull(actualSpringProcessEngineConfiguration.getJpaEntityManagerFactory());
    assertNull(deserializationConfig.getRootName());
    assertNull(serializationConfig.getRootName());
    assertNull(actualSpringProcessEngineConfiguration.getDataSourceJndiName());
    assertNull(actualSpringProcessEngineConfiguration.getDatabaseSchema());
    assertNull(actualSpringProcessEngineConfiguration.getDatabaseType());
    assertNull(actualSpringProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertNull(actualSpringProcessEngineConfiguration.getJdbcPingQuery());
    assertNull(actualSpringProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertNull(actualSpringProcessEngineConfiguration.getMailServerPassword());
    assertNull(actualSpringProcessEngineConfiguration.getMailServerUsername());
    assertNull(actualSpringProcessEngineConfiguration.getMailSessionJndi());
    assertNull(actualSpringProcessEngineConfiguration.getAsyncExecutorLockOwner());
    assertNull(actualSpringProcessEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertNull(dateFormat.getNumberFormat());
    assertNull(dateFormat.getCalendar());
    assertNull(actualSpringProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertNull(actualSpringProcessEngineConfiguration.getCustomFunctionProviders());
    assertNull(actualSpringProcessEngineConfiguration.getAllConfigurators());
    assertNull(actualSpringProcessEngineConfiguration.getConfigurators());
    assertNull(actualSpringProcessEngineConfiguration.getEventListeners());
    assertNull(actualSpringProcessEngineConfiguration.getCustomEventHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getCommandInterceptors());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPostCommandInterceptors());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPreCommandInterceptors());
    assertNull(actualSpringProcessEngineConfiguration.getCustomSessionFactories());
    assertNull(actualSpringProcessEngineConfiguration.getCustomJobHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPostDeployers());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPreDeployers());
    assertNull(actualSpringProcessEngineConfiguration.getDeployers());
    assertNull(actualSpringProcessEngineConfiguration.getResolverFactories());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPostVariableTypes());
    assertNull(actualSpringProcessEngineConfiguration.getCustomPreVariableTypes());
    assertNull(actualSpringProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getPostBpmnParseHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getPreBpmnParseHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getSessionFactories());
    assertNull(actualSpringProcessEngineConfiguration.getBeans());
    assertNull(actualSpringProcessEngineConfiguration.getTypedEventListeners());
    assertNull(actualSpringProcessEngineConfiguration.getEventHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getJobHandlers());
    assertNull(actualSpringProcessEngineConfiguration.getCustomMybatisMappers());
    assertNull(actualSpringProcessEngineConfiguration.getCustomMybatisXMLMappers());
    assertNull(dateFormat.getTimeZone());
    assertNull(actualSpringProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(actualSpringProcessEngineConfiguration.getDataSource());
    assertNull(actualSpringProcessEngineConfiguration.getIdGeneratorDataSource());
    assertNull(actualSpringProcessEngineConfiguration.getUserGroupManager());
    assertNull(actualSpringProcessEngineConfiguration.getEngineAgendaFactory());
    assertNull(actualSpringProcessEngineConfiguration.getProcessEngineLifecycleListener());
    assertNull(actualSpringProcessEngineConfiguration.getEventDispatcher());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionHelper());
    assertNull(actualSpringProcessEngineConfiguration.getAsyncExecutor());
    assertNull(actualSpringProcessEngineConfiguration.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(actualSpringProcessEngineConfiguration.getJobManager());
    assertNull(actualSpringProcessEngineConfiguration.getListenerNotificationHelper());
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = actualSpringProcessEngineConfiguration
        .getParsedDeploymentBuilderFactory();
    assertNull(parsedDeploymentBuilderFactory.getBpmnParser());
    assertNull(actualSpringProcessEngineConfiguration.getBpmnParser());
    assertNull(actualSpringProcessEngineConfiguration.getActivityBehaviorFactory());
    assertNull(actualSpringProcessEngineConfiguration.getListenerFactory());
    assertNull(actualSpringProcessEngineConfiguration.getBusinessCalendarManager());
    assertNull(actualSpringProcessEngineConfiguration.getBpmnParseFactory());
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfiguration.getBpmnDeployer();
    assertNull(bpmnDeployer.getIdGenerator());
    assertNull(actualSpringProcessEngineConfiguration.getIdGenerator());
    assertNull(actualSpringProcessEngineConfiguration.getTransactionContextFactory());
    assertNull(actualSpringProcessEngineConfiguration.getDbSqlSessionFactory());
    assertNull(actualSpringProcessEngineConfiguration.getExpressionManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoryLevel());
    assertNull(actualSpringProcessEngineConfiguration.getHistoryManager());
    assertNull(actualSpringProcessEngineConfiguration.getDefaultCommandConfig());
    assertNull(actualSpringProcessEngineConfiguration.getSchemaCommandConfig());
    assertNull(actualSpringProcessEngineConfiguration.getCommandContextFactory());
    assertNull(((DynamicBpmnServiceImpl) dynamicBpmnService).getCommandExecutor());
    assertNull(((HistoryServiceImpl) historyService).getCommandExecutor());
    assertNull(((ManagementServiceImpl) managementService).getCommandExecutor());
    assertNull(((RepositoryServiceImpl) repositoryService).getCommandExecutor());
    assertNull(((RuntimeServiceImpl) runtimeService).getCommandExecutor());
    assertNull(((TaskServiceImpl) taskService).getCommandExecutor());
    assertNull(actualSpringProcessEngineConfiguration.getCommandExecutor());
    assertNull(actualSpringProcessEngineConfiguration.getCommandInvoker());
    assertNull(actualSpringProcessEngineConfiguration.getDelegateInterceptor());
    assertNull(actualSpringProcessEngineConfiguration.getFailedJobCommandFactory());
    assertNull(actualSpringProcessEngineConfiguration.getKnowledgeBaseCache());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionCache());
    assertNull(actualSpringProcessEngineConfiguration.getDeploymentManager());
    assertNull(actualSpringProcessEngineConfiguration.getAttachmentEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getByteArrayEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getCommentEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getDeadLetterJobEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getDeploymentEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getEventLogEntryEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getEventSubscriptionEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getExecutionEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricActivityInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricDetailEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricProcessInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricVariableInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getIdentityLinkEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getJobEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getModelEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionInfoEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getPropertyEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getResourceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getSuspendedJobEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getTableDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getTaskEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getTimerJobEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getVariableInstanceEntityManager());
    assertNull(actualSpringProcessEngineConfiguration.getAttachmentDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getByteArrayDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getCommentDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getDeadLetterJobDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getDeploymentDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getEventLogEntryDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getEventSubscriptionDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getExecutionDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricDetailDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getIdentityLinkDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getJobDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getModelDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getPropertyDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getResourceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getSuspendedJobDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getTaskDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getTimerJobDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getVariableInstanceDataManager());
    assertNull(actualSpringProcessEngineConfiguration.getScriptingEngines());
    assertNull(actualSpringProcessEngineConfiguration.getProcessInstanceHelper());
    assertNull(actualSpringProcessEngineConfiguration.getVariableTypes());
    assertNull(actualSpringProcessEngineConfiguration.getClock());
    assertNull(actualSpringProcessEngineConfiguration.getProcessValidator());
    assertNull(actualSpringProcessEngineConfiguration.getSqlSessionFactory());
    assertNull(actualSpringProcessEngineConfiguration.getTransactionFactory());
    assertNull(actualSpringProcessEngineConfiguration.getApplicationContext());
    assertNull(actualSpringProcessEngineConfiguration.getTransactionManager());
    assertEquals(-1, actualSpringProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertEquals(-1, actualSpringProcessEngineConfiguration.getMaxLengthStringVariableType());
    assertEquals(-1, actualSpringProcessEngineConfiguration.getProcessDefinitionCacheLimit());
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertEquals(0, actualSpringProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertEquals(0, actualSpringProcessEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(0, actualSpringProcessEngineConfiguration.getDeploymentResources().length);
    assertEquals(1, factory.getParserFeatures());
    assertEquals(1, getResult.getHandledTypes().size());
    assertEquals(1, getResult2.getHandledTypes().size());
    assertEquals(1, getResult3.getHandledTypes().size());
    assertEquals(1, getResult4.getHandledTypes().size());
    assertEquals(1, getResult5.getHandledTypes().size());
    assertEquals(1, getResult6.getHandledTypes().size());
    assertEquals(1, actualSpringProcessEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualSpringProcessEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(10, actualSpringProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertEquals(10, actualSpringProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertEquals(10, actualSpringProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertEquals(100, actualSpringProcessEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, actualSpringProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertEquals(10000, actualSpringProcessEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(10000, actualSpringProcessEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(17, versionResult2.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult2.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, versionResult2.getPatchLevel());
    assertEquals(2, actualSpringProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertEquals(20000, actualSpringProcessEngineConfiguration.getExecutionQueryLimit());
    assertEquals(20000, actualSpringProcessEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, actualSpringProcessEngineConfiguration.getHistoricTaskQueryLimit());
    assertEquals(20000, actualSpringProcessEngineConfiguration.getTaskQueryLimit());
    assertEquals(2079, factory.getGeneratorFeatures());
    assertEquals(21771068, serializationConfig.getSerializationFeatures());
    assertEquals(25, actualSpringProcessEngineConfiguration.getMailServerPort());
    assertEquals(25, actualSpringProcessEngineConfiguration.getBatchSizeProcessInstances());
    assertEquals(25, actualSpringProcessEngineConfiguration.getBatchSizeTasks());
    assertEquals(2500, actualSpringProcessEngineConfiguration.getIdBlockSize());
    assertEquals(3, actualSpringProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertEquals(3, actualSpringProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(300000, actualSpringProcessEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(300000, actualSpringProcessEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(31, factory.getFactoryFeatures());
    assertEquals(4000, actualSpringProcessEngineConfiguration.getMaxLengthString());
    assertEquals(473998480, deserializationConfig.getDeserializationFeatures());
    assertEquals(5000L, actualSpringProcessEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualSpringProcessEngineConfiguration.getMyBatisXmlConfigurationStream().read(byteArray));
    assertEquals(60, actualSpringProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertEquals(60000, actualSpringProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(60L, actualSpringProcessEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(70, actualSpringProcessEngineConfiguration.DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
    assertEquals(9999, nodeFactory.getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
    JsonSetter.Value defaultSetterInfo = deserializationConfig.getDefaultSetterInfo();
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getContentNulls());
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getValueNulls());
    assertEquals(DelegateExpressionFieldInjectionMode.MIXED,
        actualSpringProcessEngineConfiguration.getDelegateExpressionFieldInjectionMode());
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
    assertFalse(actualSpringProcessEngineConfiguration.getMailServerUseSSL());
    assertFalse(actualSpringProcessEngineConfiguration.getMailServerUseTLS());
    assertFalse(actualSpringProcessEngineConfiguration.isAsyncExecutorActivate());
    assertFalse(actualSpringProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertFalse(actualSpringProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertFalse(actualSpringProcessEngineConfiguration.isJdbcPingEnabled());
    assertFalse(actualSpringProcessEngineConfiguration.isJpaCloseEntityManager());
    assertFalse(actualSpringProcessEngineConfiguration.isJpaHandleTransaction());
    assertFalse(actualSpringProcessEngineConfiguration.isTablePrefixIsSchema());
    PerformanceSettings performanceSettings = actualSpringProcessEngineConfiguration.getPerformanceSettings();
    assertFalse(performanceSettings.isEnableEagerExecutionTreeFetching());
    assertFalse(performanceSettings.isEnableExecutionRelationshipCounts());
    assertFalse(actualSpringProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertFalse(actualSpringProcessEngineConfiguration.isEnableDatabaseEventLogging());
    assertFalse(actualSpringProcessEngineConfiguration.isEnableSafeBpmnXml());
    assertFalse(actualSpringProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertFalse(actualSpringProcessEngineConfiguration.isRollbackDeployment());
    assertFalse(actualSpringProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(serializationConfig.isAnnotationProcessingEnabled());
    assertTrue(((StdDateFormat) dateFormat).isColonIncludedInTimeZone());
    assertTrue(dateFormat.isLenient());
    assertTrue(actualSpringProcessEngineConfiguration.getMailServers().isEmpty());
    assertTrue(actualSpringProcessEngineConfiguration.getMailSessionsJndi().isEmpty());
    assertTrue(actualSpringProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
    Set<Object> registeredModuleIds = objectMapper.getRegisteredModuleIds();
    assertTrue(registeredModuleIds.isEmpty());
    assertTrue(actualSpringProcessEngineConfiguration.isDbHistoryUsed());
    assertTrue(actualSpringProcessEngineConfiguration.isTransactionsExternallyManaged());
    assertTrue(actualSpringProcessEngineConfiguration.isUseClassForNameClassLoading());
    assertTrue(performanceSettings.isEnableLocalization());
    assertTrue(performanceSettings.isValidateExecutionRelationshipCountConfigOnBoot());
    assertTrue(actualSpringProcessEngineConfiguration.isBulkInsertEnabled());
    assertTrue(actualSpringProcessEngineConfiguration.isEnableConfiguratorServiceLoader());
    assertTrue(actualSpringProcessEngineConfiguration.isEnableEventDispatcher());
    assertTrue(actualSpringProcessEngineConfiguration.isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(actualSpringProcessEngineConfiguration.isUsingRelationalDatabase());
    String expectedDatabaseSchemaUpdate = Boolean.FALSE.toString();
    assertEquals(expectedDatabaseSchemaUpdate, actualSpringProcessEngineConfiguration.getDatabaseSchemaUpdate());
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
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSpringProcessEngineConfiguration
        .getBpmnDeploymentHelper();
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
   * Test
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}.
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}
   */
  @Test
  public void testNewSpringProcessEngineConfiguration2() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfiguration = new SpringProcessEngineConfiguration(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));

    // Assert
    ObjectMapper objectMapper2 = actualSpringProcessEngineConfiguration.getObjectMapper();
    JsonFactory factory = objectMapper2.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper2.getDeserializationConfig();
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    SerializerProvider serializerProviderInstance = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    Collection<? extends Deployer> defaultDeployers = actualSpringProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    SerializationConfig serializationConfig = objectMapper2.getSerializationConfig();
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    Locale locale = deserializationConfig.getLocale();
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfiguration.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSpringProcessEngineConfiguration
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper2, factory.getCodec());
    assertSame(factory, objectMapper2.getJsonFactory());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
  }

  /**
   * Test
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}.
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}
   */
  @Test
  public void testNewSpringProcessEngineConfiguration3() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);

    // Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfiguration = new SpringProcessEngineConfiguration(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));

    // Assert
    ObjectMapper objectMapper2 = actualSpringProcessEngineConfiguration.getObjectMapper();
    JsonFactory factory = objectMapper2.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper2.getDeserializationConfig();
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    SerializerProvider serializerProviderInstance = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    Collection<? extends Deployer> defaultDeployers = actualSpringProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    SerializationConfig serializationConfig = objectMapper2.getSerializationConfig();
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    Locale locale = deserializationConfig.getLocale();
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfiguration.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSpringProcessEngineConfiguration
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper2, factory.getCodec());
    assertSame(factory, objectMapper2.getJsonFactory());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
  }

  /**
   * Test
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}
   */
  @Test
  public void testNewSpringProcessEngineConfiguration_thenThrowActivitiException() {
    // Arrange
    ApplicationUpgradeContextService applicationUpgradeContextService = mock(ApplicationUpgradeContextService.class);
    when(applicationUpgradeContextService.isRollbackDeployment()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> new SpringProcessEngineConfiguration(applicationUpgradeContextService));
    verify(applicationUpgradeContextService).isRollbackDeployment();
  }

  /**
   * Test
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return not RollbackDeployment.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration(ApplicationUpgradeContextService)}
   */
  @Test
  public void testNewSpringProcessEngineConfiguration_whenNull_thenReturnNotRollbackDeployment() {
    // Arrange and Act
    SpringProcessEngineConfiguration actualSpringProcessEngineConfiguration = new SpringProcessEngineConfiguration(
        null);

    // Assert
    ObjectMapper objectMapper = actualSpringProcessEngineConfiguration.getObjectMapper();
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    Collection<? extends Deployer> defaultDeployers = actualSpringProcessEngineConfiguration.getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    assertFalse(actualSpringProcessEngineConfiguration.isRollbackDeployment());
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    Locale locale = deserializationConfig.getLocale();
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    BpmnDeployer bpmnDeployer = actualSpringProcessEngineConfiguration.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualSpringProcessEngineConfiguration
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper.getJsonFactory());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#initDefaultCommandConfig()}.
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#initDefaultCommandConfig()}
   */
  @Test
  public void testInitDefaultCommandConfig() {
    // Arrange and Act
    springProcessEngineConfiguration.initDefaultCommandConfig();

    // Assert
    CommandConfig defaultCommandConfig = springProcessEngineConfiguration.getDefaultCommandConfig();
    assertEquals(TransactionPropagation.REQUIRED, defaultCommandConfig.getTransactionPropagation());
    assertTrue(defaultCommandConfig.isContextReusePossible());
  }

  /**
   * Test
   * {@link SpringProcessEngineConfiguration#initTransactionContextFactory()}.
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#initTransactionContextFactory()}
   */
  @Test
  public void testInitTransactionContextFactory() {
    // Arrange
    SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
    springProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    springProcessEngineConfiguration.initTransactionContextFactory();

    // Assert that nothing has changed
    assertNull(springProcessEngineConfiguration.getTransactionContextFactory());
  }

  /**
   * Test
   * {@link SpringProcessEngineConfiguration#initTransactionContextFactory()}.
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#initTransactionContextFactory()}
   */
  @Test
  public void testInitTransactionContextFactory2() {
    // Arrange
    SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    springProcessEngineConfiguration.setTransactionManager(transactionManager);
    springProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    springProcessEngineConfiguration.initTransactionContextFactory();

    // Assert
    Collection<? extends CommandInterceptor> defaultCommandInterceptors = springProcessEngineConfiguration
        .getDefaultCommandInterceptors();
    assertEquals(3, defaultCommandInterceptors.size());
    assertTrue(defaultCommandInterceptors instanceof List);
    CommandInterceptor getResult = ((List<? extends CommandInterceptor>) defaultCommandInterceptors).get(2);
    assertTrue(getResult instanceof TransactionContextInterceptor);
    TransactionContextFactory transactionContextFactory = springProcessEngineConfiguration
        .getTransactionContextFactory();
    assertTrue(transactionContextFactory instanceof SpringTransactionContextFactory);
    assertNull(((SpringTransactionContextFactory) transactionContextFactory).transactionSynchronizationAdapterOrder);
    assertNull(getResult.getNext());
    assertSame(transactionManager, ((SpringTransactionContextFactory) transactionContextFactory).transactionManager);
    assertSame(transactionContextFactory, ((TransactionContextInterceptor) getResult).getTransactionContextFactory());
  }

  /**
   * Test
   * {@link SpringProcessEngineConfiguration#initTransactionContextFactory()}.
   * <ul>
   *   <li>Given
   * {@link SpringProcessEngineConfiguration#SpringProcessEngineConfiguration()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#initTransactionContextFactory()}
   */
  @Test
  public void testInitTransactionContextFactory_givenSpringProcessEngineConfiguration() {
    // Arrange
    SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();

    // Act
    springProcessEngineConfiguration.initTransactionContextFactory();

    // Assert that nothing has changed
    assertNull(springProcessEngineConfiguration.getTransactionContextFactory());
  }

  /**
   * Test {@link SpringProcessEngineConfiguration#setDataSource(DataSource)}.
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#setDataSource(DataSource)}
   */
  @Test
  public void testSetDataSource() {
    // Arrange, Act and Assert
    assertSame(springProcessEngineConfiguration,
        springProcessEngineConfiguration.setDataSource(mock(DataSource.class)));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link SpringProcessEngineConfiguration#setApplicationContext(ApplicationContext)}
   *   <li>{@link SpringProcessEngineConfiguration#setDeploymentMode(String)}
   *   <li>{@link SpringProcessEngineConfiguration#setDeploymentName(String)}
   *   <li>
   * {@link SpringProcessEngineConfiguration#setDeploymentResources(Resource[])}
   *   <li>
   * {@link SpringProcessEngineConfiguration#setTransactionManager(PlatformTransactionManager)}
   *   <li>
   * {@link SpringProcessEngineConfiguration#setTransactionSynchronizationAdapterOrder(Integer)}
   *   <li>{@link SpringProcessEngineConfiguration#getApplicationContext()}
   *   <li>{@link SpringProcessEngineConfiguration#getDeploymentMode()}
   *   <li>{@link SpringProcessEngineConfiguration#getDeploymentName()}
   *   <li>{@link SpringProcessEngineConfiguration#getDeploymentResources()}
   *   <li>{@link SpringProcessEngineConfiguration#getTransactionManager()}
   *   <li>{@link SpringProcessEngineConfiguration#getUserGroupManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() throws UnsupportedEncodingException, BeansException {
    // Arrange
    SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // Act
    springProcessEngineConfiguration.setApplicationContext(applicationContext);
    springProcessEngineConfiguration.setDeploymentMode("Deployment Mode");
    springProcessEngineConfiguration.setDeploymentName("Deployment Name");
    Resource[] deploymentResources = new Resource[]{new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))};
    springProcessEngineConfiguration.setDeploymentResources(deploymentResources);
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    springProcessEngineConfiguration.setTransactionManager(transactionManager);
    springProcessEngineConfiguration.setTransactionSynchronizationAdapterOrder(1);
    ApplicationContext actualApplicationContext = springProcessEngineConfiguration.getApplicationContext();
    String actualDeploymentMode = springProcessEngineConfiguration.getDeploymentMode();
    String actualDeploymentName = springProcessEngineConfiguration.getDeploymentName();
    Resource[] actualDeploymentResources = springProcessEngineConfiguration.getDeploymentResources();
    PlatformTransactionManager actualTransactionManager = springProcessEngineConfiguration.getTransactionManager();
    springProcessEngineConfiguration.getUserGroupManager();

    // Assert that nothing has changed
    assertEquals("Deployment Mode", actualDeploymentMode);
    assertEquals("Deployment Name", actualDeploymentName);
    assertSame(applicationContext, actualApplicationContext);
    assertSame(transactionManager, actualTransactionManager);
    assertSame(deploymentResources, actualDeploymentResources);
  }

  /**
   * Test
   * {@link SpringProcessEngineConfiguration#getAutoDeploymentStrategy(String)}.
   * <ul>
   *   <li>When {@code default}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#getAutoDeploymentStrategy(String)}
   */
  @Test
  public void testGetAutoDeploymentStrategy_whenDefault() {
    // Arrange, Act and Assert
    assertTrue(
        springProcessEngineConfiguration.getAutoDeploymentStrategy("default") instanceof DefaultAutoDeploymentStrategy);
  }

  /**
   * Test
   * {@link SpringProcessEngineConfiguration#getAutoDeploymentStrategy(String)}.
   * <ul>
   *   <li>When {@code Mode}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SpringProcessEngineConfiguration#getAutoDeploymentStrategy(String)}
   */
  @Test
  public void testGetAutoDeploymentStrategy_whenMode() {
    // Arrange, Act and Assert
    assertTrue(
        springProcessEngineConfiguration.getAutoDeploymentStrategy("Mode") instanceof DefaultAutoDeploymentStrategy);
  }
}
