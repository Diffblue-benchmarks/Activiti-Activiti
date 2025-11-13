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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContextFactory;
import org.activiti.engine.impl.event.EventSubscriptionPayloadMappingProvider;
import org.activiti.runtime.api.impl.ExtensionsVariablesMappingProvider;
import org.activiti.runtime.api.impl.JsonMessagePayloadMappingProviderFactory;
import org.activiti.runtime.api.impl.MappingAwareActivityBehaviorFactory;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.process.ProcessVariablesInitiator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultActivityBehaviorFactoryMappingConfigurer.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class DefaultActivityBehaviorFactoryMappingConfigurerDiffblueTest {
  @Autowired
  private DefaultActivityBehaviorFactoryMappingConfigurer
      defaultActivityBehaviorFactoryMappingConfigurer;

  @MockBean private EventSubscriptionPayloadMappingProvider eventSubscriptionPayloadMappingProvider;

  @MockBean private ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider;

  @MockBean private ProcessVariablesInitiator processVariablesInitiator;

  @MockBean private VariablesPropagator variablesPropagator;

  /**
   * Test {@link
   * DefaultActivityBehaviorFactoryMappingConfigurer#configure(SpringProcessEngineConfiguration)}.
   *
   * <p>Method under test: {@link
   * DefaultActivityBehaviorFactoryMappingConfigurer#configure(SpringProcessEngineConfiguration)}
   */
  @Test
  @DisplayName("Test configure(SpringProcessEngineConfiguration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultActivityBehaviorFactoryMappingConfigurer.configure(SpringProcessEngineConfiguration)"
  })
  void testConfigure() {
    // Arrange
    SpringProcessEngineConfiguration processEngineConfiguration =
        new SpringProcessEngineConfiguration();

    // Act
    defaultActivityBehaviorFactoryMappingConfigurer.configure(processEngineConfiguration);

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
  }
}
