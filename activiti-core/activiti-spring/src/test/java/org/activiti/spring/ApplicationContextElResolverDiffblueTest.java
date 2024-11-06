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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELContext;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.engine.ActivitiException;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextElResolverDiffblueTest {
  /**
   * Test
   * {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}.
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.addApplicationListener(mock(ApplicationListener.class));
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(applicationContext);

    // Act and Assert
    assertNull(applicationContextElResolver.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test
   * {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@code Base}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  public void testGetValue_whenBase_thenReturnNull() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(
        new AnnotationConfigApplicationContext());

    // Act and Assert
    assertNull(applicationContextElResolver.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test
   * {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  public void testGetValue_whenNull_thenReturnNull() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(
        new AnnotationConfigApplicationContext());

    // Act and Assert
    assertNull(applicationContextElResolver.getValue(new ActivitiElContext(), null, "Property"));
  }

  /**
   * Test
   * {@link ApplicationContextElResolver#isReadOnly(ELContext, Object, Object)}.
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  public void testIsReadOnly() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(
        new AnnotationConfigApplicationContext());

    // Act and Assert
    assertTrue(applicationContextElResolver.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test
   * {@link ApplicationContextElResolver#isReadOnly(ELContext, Object, Object)}.
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  public void testIsReadOnly2() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.addApplicationListener(mock(ApplicationListener.class));
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(applicationContext);

    // Act and Assert
    assertTrue(applicationContextElResolver.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test
   * {@link ApplicationContextElResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  public void testSetValue_thenThrowActivitiException() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = mock(AnnotationConfigApplicationContext.class);
    when(applicationContext.containsBean(Mockito.<String>any())).thenReturn(true);
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(applicationContext);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> applicationContextElResolver.setValue(new ActivitiElContext(), null, "Property", "Value"));
    verify(applicationContext).containsBean(eq("Property"));
  }

  /**
   * Test
   * {@link ApplicationContextElResolver#getCommonPropertyType(ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  public void testGetCommonPropertyType() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(
        new AnnotationConfigApplicationContext());

    // Act
    Class<?> actualCommonPropertyType = applicationContextElResolver.getCommonPropertyType(new ActivitiElContext(),
        "Arg");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test
   * {@link ApplicationContextElResolver#getCommonPropertyType(ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  public void testGetCommonPropertyType2() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.addApplicationListener(mock(ApplicationListener.class));
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(applicationContext);

    // Act
    Class<?> actualCommonPropertyType = applicationContextElResolver.getCommonPropertyType(new ActivitiElContext(),
        "Arg");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test
   * {@link ApplicationContextElResolver#getFeatureDescriptors(ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  public void testGetFeatureDescriptors() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(
        new AnnotationConfigApplicationContext());

    // Act and Assert
    assertNull(applicationContextElResolver.getFeatureDescriptors(new ActivitiElContext(), "Arg"));
  }

  /**
   * Test
   * {@link ApplicationContextElResolver#getFeatureDescriptors(ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  public void testGetFeatureDescriptors2() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.addApplicationListener(mock(ApplicationListener.class));
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(applicationContext);

    // Act and Assert
    assertNull(applicationContextElResolver.getFeatureDescriptors(new ActivitiElContext(), "Arg"));
  }

  /**
   * Test {@link ApplicationContextElResolver#getType(ELContext, Object, Object)}.
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#getType(ELContext, Object, Object)}
   */
  @Test
  public void testGetType() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(
        new AnnotationConfigApplicationContext());

    // Act
    Class<?> actualType = applicationContextElResolver.getType(new ActivitiElContext(), "Arg1", "Arg2");

    // Assert
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Test {@link ApplicationContextElResolver#getType(ELContext, Object, Object)}.
   * <p>
   * Method under test:
   * {@link ApplicationContextElResolver#getType(ELContext, Object, Object)}
   */
  @Test
  public void testGetType2() {
    // Arrange
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.addApplicationListener(mock(ApplicationListener.class));
    ApplicationContextElResolver applicationContextElResolver = new ApplicationContextElResolver(applicationContext);

    // Act
    Class<?> actualType = applicationContextElResolver.getType(new ActivitiElContext(), "Arg1", "Arg2");

    // Assert
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }
}
