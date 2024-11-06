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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.CompositeELResolver;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringExpressionManagerDiffblueTest {
  /**
   * Test
   * {@link SpringExpressionManager#SpringExpressionManager(ApplicationContext, Map)}.
   * <p>
   * Method under test:
   * {@link SpringExpressionManager#SpringExpressionManager(ApplicationContext, Map)}
   */
  @Test
  public void testNewSpringExpressionManager() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // Act and Assert
    ApplicationContext applicationContext2 = (new SpringExpressionManager(applicationContext,
        new HashMap<>())).applicationContext;
    Collection<ApplicationListener<?>> applicationListeners = ((AnnotationConfigApplicationContext) applicationContext2)
        .getApplicationListeners();
    assertTrue(applicationListeners instanceof Set);
    assertTrue(applicationContext2 instanceof AnnotationConfigApplicationContext);
    assertTrue(applicationListeners.isEmpty());
    assertEquals(applicationListeners,
        ((AnnotationConfigApplicationContext) applicationContext2).getProtocolResolvers());
  }

  /**
   * Test
   * {@link SpringExpressionManager#SpringExpressionManager(ApplicationContext, Map)}.
   * <p>
   * Method under test:
   * {@link SpringExpressionManager#SpringExpressionManager(ApplicationContext, Map)}
   */
  @Test
  public void testNewSpringExpressionManager2() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.addApplicationListener(mock(ApplicationListener.class));

    // Act and Assert
    ApplicationContext applicationContext2 = (new SpringExpressionManager(applicationContext,
        new HashMap<>())).applicationContext;
    Collection<ApplicationListener<?>> applicationListeners = ((AnnotationConfigApplicationContext) applicationContext2)
        .getApplicationListeners();
    assertEquals(1, applicationListeners.size());
    assertTrue(applicationListeners instanceof Set);
    assertTrue(applicationContext2 instanceof AnnotationConfigApplicationContext);
  }

  /**
   * Test {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}.
   * <p>
   * Method under test:
   * {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  public void testAddBeansResolver() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    SpringExpressionManager springExpressionManager = new SpringExpressionManager(applicationContext, new HashMap<>());
    springExpressionManager.setBeans(null);
    CompositeELResolver elResolver = new CompositeELResolver();

    // Act
    springExpressionManager.addBeansResolver(elResolver);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, elResolver.getCommonPropertyType(null, "Base"));
  }

  /**
   * Test {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}.
   * <p>
   * Method under test:
   * {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  public void testAddBeansResolver2() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.addApplicationListener(mock(ApplicationListener.class));
    SpringExpressionManager springExpressionManager = new SpringExpressionManager(applicationContext, new HashMap<>());
    CompositeELResolver elResolver = new CompositeELResolver();

    // Act
    springExpressionManager.addBeansResolver(elResolver);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, elResolver.getCommonPropertyType(null, "Base"));
  }

  /**
   * Test {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}.
   * <ul>
   *   <li>Then {@link CompositeELResolver} (default constructor) CommonPropertyType
   * {@code null} is {@code Base} is {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  public void testAddBeansResolver_thenCompositeELResolverCommonPropertyTypeNullIsBaseIsObject() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    SpringExpressionManager springExpressionManager = new SpringExpressionManager(applicationContext, new HashMap<>());
    CompositeELResolver elResolver = new CompositeELResolver();

    // Act
    springExpressionManager.addBeansResolver(elResolver);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, elResolver.getCommonPropertyType(null, "Base"));
  }
}
