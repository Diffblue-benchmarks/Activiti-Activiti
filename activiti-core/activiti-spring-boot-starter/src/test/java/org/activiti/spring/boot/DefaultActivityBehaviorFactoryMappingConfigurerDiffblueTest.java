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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import org.activiti.engine.impl.bpmn.behavior.CopyVariablesCalculator;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContextFactory;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.event.EventSubscriptionPayloadMappingProvider;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.runtime.api.impl.EventSubscriptionVariablesMappingProvider;
import org.activiti.runtime.api.impl.ExpressionResolver;
import org.activiti.runtime.api.impl.ExtensionsVariablesMappingProvider;
import org.activiti.runtime.api.impl.JsonMessagePayloadMappingProviderFactory;
import org.activiti.runtime.api.impl.MappingAwareActivityBehaviorFactory;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.process.ProcessExtensionResourceReader;
import org.activiti.spring.process.ProcessExtensionService;
import org.activiti.spring.process.ProcessVariablesInitiator;
import org.activiti.spring.process.model.ProcessExtensionModel;
import org.activiti.spring.process.variable.VariableParsingService;
import org.activiti.spring.process.variable.VariableValidationService;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultActivityBehaviorFactoryMappingConfigurerDiffblueTest {
  /**
   * Test
   * {@link DefaultActivityBehaviorFactoryMappingConfigurer#configure(SpringProcessEngineConfiguration)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactoryMappingConfigurer#configure(SpringProcessEngineConfiguration)}
   */
  @Test
  @DisplayName("Test configure(SpringProcessEngineConfiguration)")
  void testConfigure() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
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
    DefaultActivityBehaviorFactoryMappingConfigurer defaultActivityBehaviorFactoryMappingConfigurer = new DefaultActivityBehaviorFactoryMappingConfigurer(
        variablesMappingProvider, processVariablesInitiator, eventSubscriptionPayloadMappingProvider,
        new VariablesPropagator(new CopyVariablesCalculator()));
    SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();

    // Act
    defaultActivityBehaviorFactoryMappingConfigurer.configure(processEngineConfiguration);

    // Assert
    ActivityBehaviorFactory activityBehaviorFactory = processEngineConfiguration.getActivityBehaviorFactory();
    assertTrue(((MappingAwareActivityBehaviorFactory) activityBehaviorFactory)
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    EventSubscriptionPayloadMappingProvider eventSubscriptionPayloadMappingProvider2 = processEngineConfiguration
        .getEventSubscriptionPayloadMappingProvider();
    assertTrue(eventSubscriptionPayloadMappingProvider2 instanceof EventSubscriptionVariablesMappingProvider);
    assertTrue(((MappingAwareActivityBehaviorFactory) activityBehaviorFactory)
        .getMessagePayloadMappingProviderFactory() instanceof JsonMessagePayloadMappingProviderFactory);
    assertTrue(activityBehaviorFactory instanceof MappingAwareActivityBehaviorFactory);
    assertNull(((MappingAwareActivityBehaviorFactory) activityBehaviorFactory).getExpressionManager());
    assertSame(eventSubscriptionPayloadMappingProvider, eventSubscriptionPayloadMappingProvider2);
  }
}
