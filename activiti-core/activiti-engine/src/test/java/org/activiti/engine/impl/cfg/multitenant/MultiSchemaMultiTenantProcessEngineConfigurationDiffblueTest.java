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
package org.activiti.engine.impl.cfg.multitenant;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
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
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.Transaction;
import org.activiti.bpmn.model.UserTask;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.multitenant.ExecutorPerTenantAsyncExecutor;
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
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.CommandInvoker;
import org.activiti.engine.impl.interceptor.LogInterceptor;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManagerImpl;
import org.activiti.engine.integration.IntegrationContextServiceImpl;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.LoggingCommandInvoker;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MultiSchemaMultiTenantProcessEngineConfigurationDiffblueTest {
  @InjectMocks
  private MultiSchemaMultiTenantProcessEngineConfiguration multiSchemaMultiTenantProcessEngineConfiguration;

  @Mock
  private TenantInfoHolder tenantInfoHolder;

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#MultiSchemaMultiTenantProcessEngineConfiguration(TenantInfoHolder)}.
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#MultiSchemaMultiTenantProcessEngineConfiguration(TenantInfoHolder)}
   */
  @Test
  public void testNewMultiSchemaMultiTenantProcessEngineConfiguration()
      throws IOException, SQLException, MissingResourceException {
    // Arrange and Act
    MultiSchemaMultiTenantProcessEngineConfiguration actualMultiSchemaMultiTenantProcessEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        new DummyTenantInfoHolder());

    // Assert
    ObjectMapper objectMapper = actualMultiSchemaMultiTenantProcessEngineConfiguration.getObjectMapper();
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
    Collection<? extends CommandInterceptor> defaultCommandInterceptors = actualMultiSchemaMultiTenantProcessEngineConfiguration
        .getDefaultCommandInterceptors();
    assertEquals(1, defaultCommandInterceptors.size());
    assertTrue(defaultCommandInterceptors instanceof List);
    Collection<? extends Deployer> defaultDeployers = actualMultiSchemaMultiTenantProcessEngineConfiguration
        .getDefaultDeployers();
    assertEquals(1, defaultDeployers.size());
    assertTrue(defaultDeployers instanceof List);
    TenantInfoHolder tenantInfoHolder = actualMultiSchemaMultiTenantProcessEngineConfiguration.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    DynamicBpmnService dynamicBpmnService = actualMultiSchemaMultiTenantProcessEngineConfiguration
        .getDynamicBpmnService();
    assertTrue(dynamicBpmnService instanceof DynamicBpmnServiceImpl);
    HistoryService historyService = actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoryService();
    assertTrue(historyService instanceof HistoryServiceImpl);
    ManagementService managementService = actualMultiSchemaMultiTenantProcessEngineConfiguration.getManagementService();
    assertTrue(managementService instanceof ManagementServiceImpl);
    RepositoryService repositoryService = actualMultiSchemaMultiTenantProcessEngineConfiguration.getRepositoryService();
    assertTrue(repositoryService instanceof RepositoryServiceImpl);
    RuntimeService runtimeService = actualMultiSchemaMultiTenantProcessEngineConfiguration.getRuntimeService();
    assertTrue(runtimeService instanceof RuntimeServiceImpl);
    TaskService taskService = actualMultiSchemaMultiTenantProcessEngineConfiguration.getTaskService();
    assertTrue(taskService instanceof TaskServiceImpl);
    List<BpmnParseHandler> defaultBpmnParseHandlers = actualMultiSchemaMultiTenantProcessEngineConfiguration
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
    DataSource dataSource = actualMultiSchemaMultiTenantProcessEngineConfiguration.getDataSource();
    assertTrue(dataSource instanceof TenantAwareDataSource);
    CommandInterceptor getResult7 = ((List<? extends CommandInterceptor>) defaultCommandInterceptors).get(0);
    assertTrue(getResult7 instanceof LogInterceptor);
    IdGenerator idGenerator = actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdGenerator();
    assertTrue(idGenerator instanceof StrongUuidGenerator);
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration
        .getIntegrationContextManager() instanceof IntegrationContextManagerImpl);
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration
        .getIntegrationContextService() instanceof IntegrationContextServiceImpl);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertEquals(" ", factory.getRootValueSeparator());
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    Logger parentLogger = dataSource.getParentLogger();
    Logger parent = parentLogger.getParent();
    assertEquals("", parent.getName());
    assertEquals("", actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseCatalog());
    assertEquals("", actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseTablePrefix());
    assertEquals("", actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcPassword());
    assertEquals("@class", actualMultiSchemaMultiTenantProcessEngineConfiguration.getJavaClassFieldForJackson());
    TimeZone timeZone = deserializationConfig.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("English (United Kingdom)", locale.getDisplayName());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("GB", locale.getCountry());
    assertEquals("GBR", locale.getISO3Country());
    Level level = parent.getLevel();
    assertEquals("INFO", level.getLocalizedName());
    assertEquals("INFO", level.getName());
    assertEquals("INFO", level.toString());
    assertEquals("JSON", factory.getFormatName());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
    assertEquals("UTF-8", actualMultiSchemaMultiTenantProcessEngineConfiguration.getXmlEncoding());
    assertEquals("United Kingdom", locale.getDisplayCountry());
    assertEquals("[one of: 'yyyy-MM-dd'T'HH:mm:ss.SSSX', 'EEE, dd MMM yyyy HH:mm:ss zzz' (lenient)]",
        ((StdDateFormat) dateFormat).toPattern());
    assertEquals("activiti@localhost",
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerDefaultFrom());
    assertEquals("audit", actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistory());
    assertEquals("camelContext", actualMultiSchemaMultiTenantProcessEngineConfiguration.getDefaultCamelContext());
    Version versionResult = factory.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    Version versionResult2 = objectMapper.version();
    assertEquals("com.fasterxml.jackson.core", versionResult2.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-core/2.17.2", versionResult.toFullString());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult2.toFullString());
    assertEquals("default", actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessEngineName());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("global", parentLogger.getName());
    assertEquals("jackson-core", versionResult.getArtifactId());
    assertEquals("jackson-databind", versionResult2.getArtifactId());
    assertEquals("jdbc:h2:tcp://localhost/~/activiti",
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcUrl());
    assertEquals("localhost", actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerHost());
    assertEquals("org.activiti.engine.impl.webservice.CxfWebServiceClientFactory",
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getWsSyncFactoryClassName());
    assertEquals("org.h2.Driver", actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcDriver());
    assertEquals("sa", actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcUsername());
    assertEquals("sun.util.logging.resources.logging", level.getResourceBundleName());
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
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getClassLoader());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJpaEntityManagerFactory());
    assertNull(deserializationConfig.getRootName());
    assertNull(serializationConfig.getRootName());
    Handler[] handlers = parent.getHandlers();
    Handler handler = handlers[0];
    assertNull(handler.getEncoding());
    assertNull(parent.getResourceBundleName());
    assertNull(parentLogger.getResourceBundleName());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDataSourceJndiName());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseSchema());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseType());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcPingQuery());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerPassword());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerUsername());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailSessionJndi());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorLockOwner());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdGeneratorDataSourceJndiName());
    assertNull(tenantInfoHolder.getCurrentTenantId());
    assertNull(((DummyTenantInfoHolder) tenantInfoHolder).getCurrentUserId());
    assertNull(dateFormat.getNumberFormat());
    assertNull(dateFormat.getCalendar());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomScriptingEngineClasses());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomFunctionProviders());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAllConfigurators());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getConfigurators());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventListeners());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomEventHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommandInterceptors());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPostCommandInterceptors());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPreCommandInterceptors());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomSessionFactories());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomJobHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPostDeployers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPreDeployers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeployers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getResolverFactories());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPostVariableTypes());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomPreVariableTypes());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomDefaultBpmnParseHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getPostBpmnParseHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getPreBpmnParseHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getSessionFactories());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getBeans());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTypedEventListeners());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventHandlers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJobHandlers());
    assertNull(parent.getResourceBundle());
    assertNull(parentLogger.getResourceBundle());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomMybatisMappers());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCustomMybatisXMLMappers());
    assertNull(dateFormat.getTimeZone());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorThreadPoolQueue());
    assertNull(handler.getFilter());
    assertNull(parent.getFilter());
    assertNull(parentLogger.getFilter());
    assertNull(parentLogger.getLevel());
    assertNull(parent.getParent());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdGeneratorDataSource());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getUserGroupManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEngineAgendaFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessEngineLifecycleListener());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventDispatcher());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionHelper());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorExecuteAsyncRunnableFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJobManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getListenerNotificationHelper());
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = actualMultiSchemaMultiTenantProcessEngineConfiguration
        .getParsedDeploymentBuilderFactory();
    assertNull(parsedDeploymentBuilderFactory.getBpmnParser());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getBpmnParser());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getActivityBehaviorFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getListenerFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getBusinessCalendarManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getBpmnParseFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTransactionContextFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDbSqlSessionFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getExpressionManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoryLevel());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoryManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDefaultCommandConfig());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getSchemaCommandConfig());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommandContextFactory());
    assertNull(((DynamicBpmnServiceImpl) dynamicBpmnService).getCommandExecutor());
    assertNull(((HistoryServiceImpl) historyService).getCommandExecutor());
    assertNull(((ManagementServiceImpl) managementService).getCommandExecutor());
    assertNull(((RepositoryServiceImpl) repositoryService).getCommandExecutor());
    assertNull(((RuntimeServiceImpl) runtimeService).getCommandExecutor());
    assertNull(((TaskServiceImpl) taskService).getCommandExecutor());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommandExecutor());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommandInvoker());
    assertNull(getResult7.getNext());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDelegateInterceptor());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getFailedJobCommandFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getKnowledgeBaseCache());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionCache());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeploymentManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAttachmentEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getByteArrayEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommentEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeadLetterJobEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeploymentEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventLogEntryEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventSubscriptionEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getExecutionEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricActivityInstanceEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricDetailEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricIdentityLinkEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricProcessInstanceEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricTaskInstanceEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricVariableInstanceEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdentityLinkEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJobEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getModelEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionInfoEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getPropertyEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getResourceEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getSuspendedJobEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTableDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTaskEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTimerJobEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getVariableInstanceEntityManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getAttachmentDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getByteArrayDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getCommentDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeadLetterJobDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getDeploymentDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventLogEntryDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getEventSubscriptionDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getExecutionDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricActivityInstanceDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricDetailDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricIdentityLinkDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricProcessInstanceDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricTaskInstanceDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricVariableInstanceDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdentityLinkDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getJobDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getModelDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionInfoDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getPropertyDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getResourceDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getSuspendedJobDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTaskDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTimerJobDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getVariableInstanceDataManager());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getScriptingEngines());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessInstanceHelper());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getVariableTypes());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getClock());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessValidator());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getSqlSessionFactory());
    assertNull(actualMultiSchemaMultiTenantProcessEngineConfiguration.getTransactionFactory());
    assertEquals(-1, actualMultiSchemaMultiTenantProcessEngineConfiguration.getKnowledgeBaseCacheLimit());
    assertEquals(-1, actualMultiSchemaMultiTenantProcessEngineConfiguration.getMaxLengthStringVariableType());
    assertEquals(-1, actualMultiSchemaMultiTenantProcessEngineConfiguration.getProcessDefinitionCacheLimit());
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0, dataSource.getLoginTimeout());
    assertEquals(0, actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertEquals(0, actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertEquals(0, actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertEquals(0, actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertEquals(0, actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertEquals(0, actualMultiSchemaMultiTenantProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertEquals(0,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime());
    assertEquals(0, parentLogger.getHandlers().length);
    assertEquals(1, factory.getParserFeatures());
    assertEquals(1, getResult.getHandledTypes().size());
    assertEquals(1, getResult2.getHandledTypes().size());
    assertEquals(1, getResult3.getHandledTypes().size());
    assertEquals(1, getResult4.getHandledTypes().size());
    assertEquals(1, getResult5.getHandledTypes().size());
    assertEquals(1, getResult6.getHandledTypes().size());
    assertEquals(1,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition());
    assertEquals(1,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition());
    assertEquals(1, handlers.length);
    assertEquals(10, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertEquals(10, actualMultiSchemaMultiTenantProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertEquals(10, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorMaxPoolSize());
    assertEquals(100, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorThreadPoolQueueSize());
    assertEquals(100, actualMultiSchemaMultiTenantProcessEngineConfiguration.getMaxNrOfStatementsInBulkInsert());
    assertEquals(10000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime());
    assertEquals(10000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(17, versionResult2.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult2.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, versionResult2.getPatchLevel());
    assertEquals(2, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorCorePoolSize());
    assertEquals(20000, actualMultiSchemaMultiTenantProcessEngineConfiguration.getExecutionQueryLimit());
    assertEquals(20000, actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricProcessInstancesQueryLimit());
    assertEquals(20000, actualMultiSchemaMultiTenantProcessEngineConfiguration.getHistoricTaskQueryLimit());
    assertEquals(20000, actualMultiSchemaMultiTenantProcessEngineConfiguration.getTaskQueryLimit());
    assertEquals(2079, factory.getGeneratorFeatures());
    assertEquals(21771068, serializationConfig.getSerializationFeatures());
    assertEquals(25, actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerPort());
    assertEquals(25, actualMultiSchemaMultiTenantProcessEngineConfiguration.getBatchSizeProcessInstances());
    assertEquals(25, actualMultiSchemaMultiTenantProcessEngineConfiguration.getBatchSizeTasks());
    assertEquals(2500, actualMultiSchemaMultiTenantProcessEngineConfiguration.getIdBlockSize());
    assertEquals(3, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorNumberOfRetries());
    assertEquals(3, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize());
    assertEquals(300000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis());
    assertEquals(300000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis());
    assertEquals(31, factory.getFactoryFeatures());
    assertEquals(4000, actualMultiSchemaMultiTenantProcessEngineConfiguration.getMaxLengthString());
    assertEquals(473998480, deserializationConfig.getDeserializationFeatures());
    assertEquals(5000L, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorThreadKeepAliveTime());
    byte[] byteArray = new byte[51];
    assertEquals(51,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getMyBatisXmlConfigurationStream().read(byteArray));
    assertEquals(60, actualMultiSchemaMultiTenantProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertEquals(60000,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval());
    assertEquals(60L, actualMultiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown());
    assertEquals(70,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.DEFAULT_MAX_NR_OF_STATEMENTS_BULK_INSERT_SQL_SERVER);
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
    assertEquals(9999, nodeFactory.getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
    JsonSetter.Value defaultSetterInfo = deserializationConfig.getDefaultSetterInfo();
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getContentNulls());
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getValueNulls());
    assertEquals(DelegateExpressionFieldInjectionMode.MIXED,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getDelegateExpressionFieldInjectionMode());
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
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerUseSSL());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServerUseTLS());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isAsyncExecutorActivate());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isJdbcPingEnabled());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isJpaCloseEntityManager());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isJpaHandleTransaction());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isTablePrefixIsSchema());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isTransactionsExternallyManaged());
    PerformanceSettings performanceSettings = actualMultiSchemaMultiTenantProcessEngineConfiguration
        .getPerformanceSettings();
    assertFalse(performanceSettings.isEnableEagerExecutionTreeFetching());
    assertFalse(performanceSettings.isEnableExecutionRelationshipCounts());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isAsyncExecutorIsMessageQueueMode());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isEnableDatabaseEventLogging());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isEnableSafeBpmnXml());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isEnableVerboseExecutionTreeLogging());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isRollbackDeployment());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.isSerializePOJOsInVariablesToJson());
    assertFalse(actualMultiSchemaMultiTenantProcessEngineConfiguration.booted);
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(serializationConfig.isAnnotationProcessingEnabled());
    assertTrue(((StdDateFormat) dateFormat).isColonIncludedInTimeZone());
    assertTrue(dateFormat.isLenient());
    assertTrue(allTenants.isEmpty());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailServers().isEmpty());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.getMailSessionsJndi().isEmpty());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(((TenantAwareDataSource) dataSource).getDataSources().isEmpty());
    Set<Object> registeredModuleIds = objectMapper.getRegisteredModuleIds();
    assertTrue(registeredModuleIds.isEmpty());
    assertTrue(parent.getUseParentHandlers());
    assertTrue(parentLogger.getUseParentHandlers());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.isDbHistoryUsed());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.isUseClassForNameClassLoading());
    assertTrue(performanceSettings.isEnableLocalization());
    assertTrue(performanceSettings.isValidateExecutionRelationshipCountConfigOnBoot());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.isBulkInsertEnabled());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.isEnableConfiguratorServiceLoader());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.isEnableEventDispatcher());
    assertTrue(
        actualMultiSchemaMultiTenantProcessEngineConfiguration.isSerializableVariableTypeTrackDeserializedObjects());
    assertTrue(actualMultiSchemaMultiTenantProcessEngineConfiguration.isUsingRelationalDatabase());
    String expectedDatabaseSchemaUpdate = Boolean.FALSE.toString();
    assertEquals(expectedDatabaseSchemaUpdate,
        actualMultiSchemaMultiTenantProcessEngineConfiguration.getDatabaseSchemaUpdate());
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
    assertSame(level, handler.getLevel());
    BpmnDeployer bpmnDeployer = actualMultiSchemaMultiTenantProcessEngineConfiguration.getBpmnDeployer();
    assertSame(bpmnDeployer, ((List<? extends Deployer>) defaultDeployers).get(0));
    BpmnDeploymentHelper expectedBpmnDeploymentHelper = actualMultiSchemaMultiTenantProcessEngineConfiguration
        .getBpmnDeploymentHelper();
    assertSame(expectedBpmnDeploymentHelper, bpmnDeployer.getBpmnDeploymentHelper());
    assertSame(objectMapper, factory.getCodec());
    assertSame(parsedDeploymentBuilderFactory, bpmnDeployer.getExParsedDeploymentBuilderFactory());
    assertSame(actualMultiSchemaMultiTenantProcessEngineConfiguration.tenantInfoHolder,
        ((TenantAwareDataSource) dataSource).tenantInfoHolder);
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
    assertSame(idGenerator, bpmnDeployer.getIdGenerator());
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<!DOCTYPE c".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#registerTenant(String, DataSource)}.
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#registerTenant(String, DataSource)}
   */
  @Test
  public void testRegisterTenant() {
    // Arrange
    DataSource dataSource = mock(DataSource.class);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.registerTenant("42", dataSource);

    // Assert
    DataSource dataSource2 = multiSchemaMultiTenantProcessEngineConfiguration.getDataSource();
    assertTrue(dataSource2 instanceof TenantAwareDataSource);
    Map<Object, DataSource> dataSources = ((TenantAwareDataSource) dataSource2).getDataSources();
    assertEquals(1, dataSources.size());
    assertSame(dataSource, dataSources.get("42"));
  }

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}
   */
  @Test
  public void testInitAsyncExecutor() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration multiSchemaMultiTenantProcessEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        new DummyTenantInfoHolder());

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    TenantInfoHolder tenantInfoHolder = multiSchemaMultiTenantProcessEngineConfiguration.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    AsyncExecutor asyncExecutor = multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof ExecutorPerTenantAsyncExecutor);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertTrue(allTenants.isEmpty());
    assertTrue(((ExecutorPerTenantAsyncExecutor) asyncExecutor).getTenantIds().isEmpty());
  }

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}
   */
  @Test
  public void testInitAsyncExecutor2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration multiSchemaMultiTenantProcessEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        new DummyTenantInfoHolder());
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    AsyncExecutor asyncExecutor2 = multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor2 instanceof DefaultAsyncJobExecutor);
    assertSame(asyncExecutor, asyncExecutor2);
    assertSame(multiSchemaMultiTenantProcessEngineConfiguration, asyncExecutor2.getProcessEngineConfiguration());
  }

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}
   */
  @Test
  public void testInitAsyncExecutor3() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");
    MultiSchemaMultiTenantProcessEngineConfiguration multiSchemaMultiTenantProcessEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        tenantInfoHolder);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    TenantInfoHolder tenantInfoHolder2 = multiSchemaMultiTenantProcessEngineConfiguration.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder2.getAllTenants();
    assertEquals(1, allTenants.size());
    assertTrue(allTenants instanceof Set);
    AsyncExecutor asyncExecutor = multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof ExecutorPerTenantAsyncExecutor);
    assertTrue(tenantInfoHolder2 instanceof DummyTenantInfoHolder);
    Set<String> tenantIds = ((ExecutorPerTenantAsyncExecutor) asyncExecutor).getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}.
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#initAsyncExecutor()}
   */
  @Test
  public void testInitAsyncExecutor4() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration multiSchemaMultiTenantProcessEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.initAsyncExecutor();

    // Assert
    TenantInfoHolder tenantInfoHolder = multiSchemaMultiTenantProcessEngineConfiguration.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    AsyncExecutor asyncExecutor = multiSchemaMultiTenantProcessEngineConfiguration.getAsyncExecutor();
    assertTrue(asyncExecutor instanceof ExecutorPerTenantAsyncExecutor);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertTrue(allTenants.isEmpty());
    assertTrue(((ExecutorPerTenantAsyncExecutor) asyncExecutor).getTenantIds().isEmpty());
  }

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}.
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}
   */
  @Test
  public void testBuildProcessEngine() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getAllTenants()).thenReturn(new ArrayList<>());

    MultiSchemaMultiTenantProcessEngineConfiguration multiSchemaMultiTenantProcessEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        tenantInfoHolder);
    multiSchemaMultiTenantProcessEngineConfiguration
        .setSqlSessionFactory(new DefaultSqlSessionFactory(new Configuration()));
    multiSchemaMultiTenantProcessEngineConfiguration.setDatabaseType("none");
    multiSchemaMultiTenantProcessEngineConfiguration.registerTenant("42", mock(DataSource.class));

    // Act
    ProcessEngine actualBuildProcessEngineResult = multiSchemaMultiTenantProcessEngineConfiguration
        .buildProcessEngine();

    // Assert
    verify(tenantInfoHolder, atLeast(1)).getAllTenants();
    assertTrue(actualBuildProcessEngineResult instanceof ProcessEngineImpl);
    ProcessEngineConfiguration processEngineConfiguration = actualBuildProcessEngineResult
        .getProcessEngineConfiguration();
    assertTrue(processEngineConfiguration instanceof MultiSchemaMultiTenantProcessEngineConfiguration);
    assertTrue(((MultiSchemaMultiTenantProcessEngineConfiguration) processEngineConfiguration)
        .getCommandInvoker() instanceof CommandInvoker);
    assertNull(((MultiSchemaMultiTenantProcessEngineConfiguration) processEngineConfiguration).getConfigurators());
    assertTrue(((MultiSchemaMultiTenantProcessEngineConfiguration) processEngineConfiguration).getAllConfigurators()
        .isEmpty());
  }

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}.
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#buildProcessEngine()}
   */
  @Test
  public void testBuildProcessEngine2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getAllTenants()).thenReturn(new ArrayList<>());

    MultiSchemaMultiTenantProcessEngineConfiguration multiSchemaMultiTenantProcessEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        tenantInfoHolder);
    multiSchemaMultiTenantProcessEngineConfiguration
        .setSqlSessionFactory(new DefaultSqlSessionFactory(new Configuration()));
    ProcessExecutionLoggerConfigurator configurator = new ProcessExecutionLoggerConfigurator();
    multiSchemaMultiTenantProcessEngineConfiguration.addConfigurator(configurator);
    multiSchemaMultiTenantProcessEngineConfiguration.setDatabaseType("none");
    multiSchemaMultiTenantProcessEngineConfiguration.registerTenant("42", mock(DataSource.class));

    // Act
    ProcessEngine actualBuildProcessEngineResult = multiSchemaMultiTenantProcessEngineConfiguration
        .buildProcessEngine();

    // Assert
    verify(tenantInfoHolder, atLeast(1)).getAllTenants();
    assertTrue(actualBuildProcessEngineResult instanceof ProcessEngineImpl);
    ProcessEngineConfiguration processEngineConfiguration = actualBuildProcessEngineResult
        .getProcessEngineConfiguration();
    assertTrue(processEngineConfiguration instanceof MultiSchemaMultiTenantProcessEngineConfiguration);
    assertTrue(((MultiSchemaMultiTenantProcessEngineConfiguration) processEngineConfiguration)
        .getCommandInvoker() instanceof LoggingCommandInvoker);
    List<ProcessEngineConfigurator> allConfigurators = ((MultiSchemaMultiTenantProcessEngineConfiguration) processEngineConfiguration)
        .getAllConfigurators();
    assertEquals(1, allConfigurators.size());
    ProcessEngineConfigurator getResult = allConfigurators.get(0);
    assertTrue(getResult instanceof ProcessExecutionLoggerConfigurator);
    assertEquals(10000, getResult.getPriority());
    assertEquals(allConfigurators,
        ((MultiSchemaMultiTenantProcessEngineConfiguration) processEngineConfiguration).getConfigurators());
    assertSame(configurator, getResult);
  }

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#createTenantAsyncJobExecutor(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#createTenantAsyncJobExecutor(String)}
   */
  @Test
  public void testCreateTenantAsyncJobExecutor_thenCallsAddTenantAsyncExecutor() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addUser(Mockito.<String>any(), Mockito.<String>any());
    tenantInfoHolder.addUser("Tenant Id", "User Id");
    ExecutorPerTenantAsyncExecutor asyncExecutor = mock(ExecutorPerTenantAsyncExecutor.class);
    doNothing().when(asyncExecutor).addTenantAsyncExecutor(Mockito.<String>any(), anyBoolean());

    MultiSchemaMultiTenantProcessEngineConfiguration multiSchemaMultiTenantProcessEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        tenantInfoHolder);
    multiSchemaMultiTenantProcessEngineConfiguration.setAsyncExecutor(asyncExecutor);

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.createTenantAsyncJobExecutor("42");

    // Assert that nothing has changed
    verify(asyncExecutor).addTenantAsyncExecutor(eq("42"), eq(false));
    verify(tenantInfoHolder).addUser(eq("Tenant Id"), eq("User Id"));
  }

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#createTransactionInterceptor()}.
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#createTransactionInterceptor()}
   */
  @Test
  public void testCreateTransactionInterceptor() {
    // Arrange, Act and Assert
    assertNull((new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder()))
        .createTransactionInterceptor());
  }

  /**
   * Test
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#createTransactionInterceptor()}.
   * <p>
   * Method under test:
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#createTransactionInterceptor()}
   */
  @Test
  public void testCreateTransactionInterceptor2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration multiSchemaMultiTenantProcessEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(multiSchemaMultiTenantProcessEngineConfiguration.createTransactionInterceptor());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#postProcessEngineInitialisation()}
   *   <li>
   * {@link MultiSchemaMultiTenantProcessEngineConfiguration#getUserGroupManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration multiSchemaMultiTenantProcessEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        new DummyTenantInfoHolder());

    // Act
    multiSchemaMultiTenantProcessEngineConfiguration.postProcessEngineInitialisation();

    // Assert that nothing has changed
    assertNull(multiSchemaMultiTenantProcessEngineConfiguration.getUserGroupManager());
  }
}
