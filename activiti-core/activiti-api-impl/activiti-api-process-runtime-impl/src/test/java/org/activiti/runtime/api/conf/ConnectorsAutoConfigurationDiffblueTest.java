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
package org.activiti.runtime.api.conf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.jupiter.api.Test;

class ConnectorsAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link ConnectorsAutoConfiguration#expressionManager(List)}
   */
  @Test
  void testExpressionManager() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ConnectorsAutoConfiguration connectorsAutoConfiguration = new ConnectorsAutoConfiguration();
    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();

    // Act
    ExpressionManager actualExpressionManagerResult = connectorsAutoConfiguration
        .expressionManager(customFunctionProviders);

    // Assert
    assertNull(actualExpressionManagerResult.getBeans());
    List<CustomFunctionProvider> customFunctionProviders2 = actualExpressionManagerResult.getCustomFunctionProviders();
    assertTrue(customFunctionProviders2.isEmpty());
    assertSame(customFunctionProviders, customFunctionProviders2);
  }

  /**
   * Method under test:
   * {@link ConnectorsAutoConfiguration#expressionManager(List)}
   */
  @Test
  void testExpressionManager2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ConnectorsAutoConfiguration connectorsAutoConfiguration = new ConnectorsAutoConfiguration();

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));

    // Act
    ExpressionManager actualExpressionManagerResult = connectorsAutoConfiguration
        .expressionManager(customFunctionProviders);

    // Assert
    assertNull(actualExpressionManagerResult.getBeans());
    List<CustomFunctionProvider> customFunctionProviders2 = actualExpressionManagerResult.getCustomFunctionProviders();
    assertEquals(1, customFunctionProviders2.size());
    assertSame(customFunctionProviders, customFunctionProviders2);
  }

  /**
   * Method under test:
   * {@link ConnectorsAutoConfiguration#expressionManager(List)}
   */
  @Test
  void testExpressionManager3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ConnectorsAutoConfiguration connectorsAutoConfiguration = new ConnectorsAutoConfiguration();

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));
    customFunctionProviders.add(mock(CustomFunctionProvider.class));

    // Act
    ExpressionManager actualExpressionManagerResult = connectorsAutoConfiguration
        .expressionManager(customFunctionProviders);

    // Assert
    assertNull(actualExpressionManagerResult.getBeans());
    assertSame(customFunctionProviders, actualExpressionManagerResult.getCustomFunctionProviders());
  }

  /**
   * Method under test:
   * {@link ConnectorsAutoConfiguration#expressionResolver(ExpressionManager, ObjectMapper)}
   */
  @Test
  void testExpressionResolver() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ConnectorsAutoConfiguration connectorsAutoConfiguration = new ConnectorsAutoConfiguration();
    ExpressionManager expressionManager = new ExpressionManager();

    // Act and Assert
    assertFalse(connectorsAutoConfiguration.expressionResolver(expressionManager, new ObjectMapper())
        .containsExpression("Source"));
  }

  /**
   * Method under test:
   * {@link ConnectorsAutoConfiguration#expressionResolver(ExpressionManager, ObjectMapper)}
   */
  @Test
  void testExpressionResolver2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ConnectorsAutoConfiguration connectorsAutoConfiguration = new ConnectorsAutoConfiguration();
    ExpressionManager expressionManager = mock(ExpressionManager.class);

    // Act and Assert
    assertFalse(connectorsAutoConfiguration.expressionResolver(expressionManager, new ObjectMapper())
        .containsExpression("Source"));
  }
}
