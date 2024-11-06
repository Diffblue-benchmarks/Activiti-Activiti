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
package org.activiti.engine.test.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELResolver;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.junit.Test;

public class MockExpressionManagerDiffblueTest {
  /**
   * Test {@link MockExpressionManager#createElResolver(VariableScope)}.
   * <p>
   * Method under test:
   * {@link MockExpressionManager#createElResolver(VariableScope)}
   */
  @Test
  public void testCreateElResolver() {
    // Arrange
    MockExpressionManager mockExpressionManager = new MockExpressionManager();

    // Act
    ELResolver actualCreateElResolverResult = mockExpressionManager
        .createElResolver(NoExecutionVariableScope.getSharedInstance());

    // Assert
    assertTrue(actualCreateElResolverResult instanceof CompositeELResolver);
    assertFalse(actualCreateElResolverResult.getFeatureDescriptors(null, null).hasNext());
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCreateElResolverResult.getCommonPropertyType(null, null));
  }

  /**
   * Test new {@link MockExpressionManager} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link MockExpressionManager}
   */
  @Test
  public void testNewMockExpressionManager() {
    // Arrange and Act
    MockExpressionManager actualMockExpressionManager = new MockExpressionManager();

    // Assert
    assertNull(actualMockExpressionManager.getCustomFunctionProviders());
    assertNull(actualMockExpressionManager.getBeans());
  }
}
