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
package org.activiti.spring.process.conf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.runtime.api.impl.ExpressionResolver;
import org.activiti.runtime.api.impl.ExtensionsVariablesMappingProvider;
import org.activiti.spring.process.ProcessExtensionResourceReader;
import org.activiti.spring.process.ProcessExtensionService;
import org.activiti.spring.process.ProcessVariablesInitiator;
import org.activiti.spring.process.model.ProcessExtensionModel;
import org.activiti.spring.process.variable.VariableParsingService;
import org.activiti.spring.process.variable.VariableValidationService;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessExtensionsConfiguratorAutoConfigurationDiffblueTest {
  /**
   * Test
   * {@link ProcessExtensionsConfiguratorAutoConfiguration#ProcessExtensionsConfiguratorAutoConfiguration(ProcessVariablesInitiator)}.
   * <p>
   * Method under test:
   * {@link ProcessExtensionsConfiguratorAutoConfiguration#ProcessExtensionsConfiguratorAutoConfiguration(ProcessVariablesInitiator)}
   */
  @Test
  @DisplayName("Test new ProcessExtensionsConfiguratorAutoConfiguration(ProcessVariablesInitiator)")
  void testNewProcessExtensionsConfiguratorAutoConfiguration() {
    // Arrange
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = new DeploymentResourceLoader<>();
    ObjectMapper objectMapper = new ObjectMapper();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader2 = new DeploymentResourceLoader<>();
    ObjectMapper objectMapper2 = new ObjectMapper();
    ProcessExtensionService processExtensionService2 = new ProcessExtensionService(processExtensionLoader2,
        new ProcessExtensionResourceReader(objectMapper2, new HashMap<>()));

    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager, new ObjectMapper(),
        mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesCalculator = new ExtensionsVariablesMappingProvider(
        processExtensionService2, expressionResolver, new VariableParsingService(new HashMap<>()));

    ExpressionManager expressionManager2 = new ExpressionManager();

    // Act and Assert
    assertEquals(10000,
        (new ProcessExtensionsConfiguratorAutoConfiguration(new ProcessVariablesInitiator(processExtensionService,
            variableParsingService, variableValidationService, variablesCalculator,
            new ExpressionResolver(expressionManager2, new ObjectMapper(), mock(DelegateInterceptor.class)))))
            .getPriority());
  }
}
