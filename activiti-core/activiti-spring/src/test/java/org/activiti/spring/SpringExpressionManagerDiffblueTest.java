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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.CompositeELResolver;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ProtocolResolver;

public class SpringExpressionManagerDiffblueTest {
  /**
   * Test {@link SpringExpressionManager#SpringExpressionManager(ApplicationContext, Map)}.
   * <p>
   * Method under test: {@link SpringExpressionManager#SpringExpressionManager(ApplicationContext, Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SpringExpressionManager.<init>(ApplicationContext, Map)"})
  public void testNewSpringExpressionManager() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // Act
    SpringExpressionManager actualSpringExpressionManager = new SpringExpressionManager(applicationContext,
        new HashMap<>());

    // Assert
    ApplicationContext applicationContext2 = actualSpringExpressionManager.applicationContext;
    Collection<ApplicationListener<?>> applicationListeners = ((AnnotationConfigApplicationContext) applicationContext2)
        .getApplicationListeners();
    assertTrue(applicationListeners instanceof Set);
    Collection<ProtocolResolver> protocolResolvers = ((AnnotationConfigApplicationContext) applicationContext2)
        .getProtocolResolvers();
    assertTrue(protocolResolvers instanceof Set);
    ConfigurableListableBeanFactory beanFactory = ((AnnotationConfigApplicationContext) applicationContext2)
        .getBeanFactory();
    assertTrue(beanFactory instanceof DefaultListableBeanFactory);
    assertTrue(applicationContext2 instanceof AnnotationConfigApplicationContext);
    assertTrue(applicationContext2.getEnvironment() instanceof StandardEnvironment);
    assertEquals("", applicationContext2.getApplicationName());
    assertNull(actualSpringExpressionManager.getCustomFunctionProviders());
    assertNull(applicationContext2.getParentBeanFactory());
    assertNull(applicationContext2.getParent());
    assertEquals(0L, applicationContext2.getStartupDate());
    assertEquals(5, applicationContext2.getBeanDefinitionCount());
    assertEquals(5, applicationContext2.getBeanDefinitionNames().length);
    assertFalse(((AnnotationConfigApplicationContext) applicationContext2).isActive());
    assertFalse(((AnnotationConfigApplicationContext) applicationContext2).isRunning());
    assertTrue(applicationListeners.isEmpty());
    assertTrue(protocolResolvers.isEmpty());
    assertTrue(((AnnotationConfigApplicationContext) applicationContext2).getBeanFactoryPostProcessors().isEmpty());
    assertTrue(actualSpringExpressionManager.getBeans().isEmpty());
    assertSame(beanFactory, ((AnnotationConfigApplicationContext) applicationContext2).getDefaultListableBeanFactory());
  }

  /**
   * Test {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}.
   * <p>
   * Method under test: {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SpringExpressionManager.addBeansResolver(CompositeELResolver)"})
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
   * <ul>
   *   <li>Then {@link CompositeELResolver} (default constructor) CommonPropertyType {@code null} is {@code Base} is {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SpringExpressionManager.addBeansResolver(CompositeELResolver)"})
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
