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
import static org.mockito.Mockito.mock;
import jakarta.el.CompositeELResolver;
import java.util.HashMap;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringExpressionManagerDiffblueTest {
  /**
   * Method under test:
   * {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  public void testAddBeansResolver() {
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

  /**
   * Method under test:
   * {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  public void testAddBeansResolver2() {
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
   * Method under test:
   * {@link SpringExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  public void testAddBeansResolver3() {
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
}
