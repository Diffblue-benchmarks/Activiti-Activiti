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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConnectorsAutoConfigurationDiffblueTest {
  /**
   * Test {@link ConnectorsAutoConfiguration#expressionManager(List)}.
   * <ul>
   *   <li>Then return CustomFunctionProviders is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorsAutoConfiguration#expressionManager(List)}
   */
  @Test
  @DisplayName("Test expressionManager(List); then return CustomFunctionProviders is ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ExpressionManager ConnectorsAutoConfiguration.expressionManager(List)"})
  void testExpressionManager_thenReturnCustomFunctionProvidersIsArrayList() {
    // Arrange
    ConnectorsAutoConfiguration connectorsAutoConfiguration = new ConnectorsAutoConfiguration();

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));

    // Act
    ExpressionManager actualExpressionManagerResult = connectorsAutoConfiguration
        .expressionManager(customFunctionProviders);

    // Assert
    assertNull(actualExpressionManagerResult.getBeans());
    assertSame(customFunctionProviders, actualExpressionManagerResult.getCustomFunctionProviders());
  }

  /**
   * Test {@link ConnectorsAutoConfiguration#expressionManager(List)}.
   * <ul>
   *   <li>Then return CustomFunctionProviders is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorsAutoConfiguration#expressionManager(List)}
   */
  @Test
  @DisplayName("Test expressionManager(List); then return CustomFunctionProviders is ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ExpressionManager ConnectorsAutoConfiguration.expressionManager(List)"})
  void testExpressionManager_thenReturnCustomFunctionProvidersIsArrayList2() {
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
   * Test {@link ConnectorsAutoConfiguration#expressionManager(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return CustomFunctionProviders Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorsAutoConfiguration#expressionManager(List)}
   */
  @Test
  @DisplayName("Test expressionManager(List); when ArrayList(); then return CustomFunctionProviders Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ExpressionManager ConnectorsAutoConfiguration.expressionManager(List)"})
  void testExpressionManager_whenArrayList_thenReturnCustomFunctionProvidersEmpty() {
    // Arrange
    ConnectorsAutoConfiguration connectorsAutoConfiguration = new ConnectorsAutoConfiguration();

    // Act
    ExpressionManager actualExpressionManagerResult = connectorsAutoConfiguration.expressionManager(new ArrayList<>());

    // Assert
    assertNull(actualExpressionManagerResult.getBeans());
    assertTrue(actualExpressionManagerResult.getCustomFunctionProviders().isEmpty());
  }

  /**
   * Test {@link ConnectorsAutoConfiguration#expressionResolver(ExpressionManager, ObjectMapper)}.
   * <p>
   * Method under test: {@link ConnectorsAutoConfiguration#expressionResolver(ExpressionManager, ObjectMapper)}
   */
  @Test
  @DisplayName("Test expressionResolver(ExpressionManager, ObjectMapper)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.runtime.api.impl.ExpressionResolver ConnectorsAutoConfiguration.expressionResolver(ExpressionManager, ObjectMapper)"})
  void testExpressionResolver() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ConnectorsAutoConfiguration connectorsAutoConfiguration = new ConnectorsAutoConfiguration();
    ExpressionManager expressionManager = new ExpressionManager();

    // Act and Assert
    assertFalse(connectorsAutoConfiguration
        .expressionResolver(expressionManager, JsonMapper.builder().findAndAddModules().build())
        .containsExpression("Source"));
  }
}
