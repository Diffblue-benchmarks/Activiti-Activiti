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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.CompositeELResolver;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.context.ApplicationContext;

public class SpringExpressionManagerDiffblueTest {
  /**
   * Test {@link SpringExpressionManager#SpringExpressionManager(ApplicationContext, Map)}.
   *
   * <p>Method under test: {@link
   * SpringExpressionManager#SpringExpressionManager(ApplicationContext, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringExpressionManager.<init>(ApplicationContext, Map)"})
  public void testNewSpringExpressionManager() {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);

    // Act
    SpringExpressionManager actualSpringExpressionManager =
        new SpringExpressionManager(applicationContext, new HashMap<>());

    // Assert
    assertNull(actualSpringExpressionManager.getCustomFunctionProviders());
    assertTrue(actualSpringExpressionManager.getBeans().isEmpty());
  }

  /**
   * Test {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}.
   *
   * <p>Method under test: {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringExpressionManager.addBeansResolver(CompositeELResolver)"})
  public void testAddBeansResolver() {
    // Arrange
    SpringExpressionManager springExpressionManager =
        new SpringExpressionManager(mock(ApplicationContext.class), null);
    CompositeELResolver elResolver = new CompositeELResolver();

    // Act
    springExpressionManager.addBeansResolver(elResolver);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, elResolver.getCommonPropertyType(null, "Base"));
  }

  /**
   * Test {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}.
   *
   * <ul>
   *   <li>Then {@link CompositeELResolver} (default constructor) CommonPropertyType {@code null} is
   *       {@code Base} is {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SpringExpressionManager.addBeansResolver(CompositeELResolver)"})
  public void testAddBeansResolver_thenCompositeELResolverCommonPropertyTypeNullIsBaseIsObject() {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    SpringExpressionManager springExpressionManager =
        new SpringExpressionManager(applicationContext, new HashMap<>());
    CompositeELResolver elResolver = new CompositeELResolver();

    // Act
    springExpressionManager.addBeansResolver(elResolver);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, elResolver.getCommonPropertyType(null, "Base"));
  }
}
