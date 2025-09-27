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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.engine.ActivitiException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class ApplicationContextElResolverDiffblueTest {
  /**
   * Test {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}.
   *
   * <p>Method under test: {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ApplicationContextElResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue() {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.containsBean(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(applicationContext);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> applicationContextElResolver.getValue(new ActivitiElContext(), null, "Property"));
    verify(applicationContext).containsBean("Property");
  }

  /**
   * Test {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}.
   *
   * <p>Method under test: {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ApplicationContextElResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue2() throws BeansException {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getBean(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    when(applicationContext.containsBean(Mockito.<String>any())).thenReturn(true);
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(applicationContext);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> applicationContextElResolver.getValue(new ActivitiElContext(), null, "Property"));
    verify(applicationContext).containsBean("Property");
    verify(applicationContext).getBean("Property");
  }

  /**
   * Test {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ApplicationContext} {@link ApplicationContext#containsBean(String)} return
   *       {@code false}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ApplicationContextElResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue_givenApplicationContextContainsBeanReturnFalse_thenReturnNull() {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.containsBean(Mockito.<String>any())).thenReturn(false);
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(applicationContext);
    ActivitiElContext context = new ActivitiElContext();

    // Act
    Object actualValue = applicationContextElResolver.getValue(context, null, "Property");

    // Assert
    verify(applicationContext).containsBean("Property");
    assertNull(actualValue);
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ApplicationContext} {@link ApplicationContext#getBean(String)} return {@code
   *       Bean}.
   *   <li>When {@code null}.
   *   <li>Then return {@code Bean}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ApplicationContextElResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue_givenApplicationContextGetBeanReturnBean_whenNull_thenReturnBean()
      throws BeansException {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getBean(Mockito.<String>any())).thenReturn("Bean");
    when(applicationContext.containsBean(Mockito.<String>any())).thenReturn(true);
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(applicationContext);
    ActivitiElContext context = new ActivitiElContext();

    // Act
    Object actualValue = applicationContextElResolver.getValue(context, null, "Property");

    // Assert
    verify(applicationContext).containsBean("Property");
    verify(applicationContext).getBean("Property");
    assertEquals("Bean", actualValue);
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>When {@code Base}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationContextElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ApplicationContextElResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue_whenBase_thenReturnNull() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(mock(ApplicationContext.class));
    ActivitiElContext context = new ActivitiElContext();

    // Act and Assert
    assertNull(applicationContextElResolver.getValue(context, "Base", "Property"));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link ApplicationContextElResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <p>Method under test: {@link ApplicationContextElResolver#isReadOnly(ELContext, Object,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ApplicationContextElResolver.isReadOnly(ELContext, Object, Object)"})
  public void testIsReadOnly() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(mock(ApplicationContext.class));

    // Act and Assert
    assertTrue(
        applicationContextElResolver.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ApplicationContextElResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link ApplicationContextElResolver#setValue(ELContext, Object, Object,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ApplicationContextElResolver.setValue(ELContext, Object, Object, Object)"
  })
  public void testSetValue() {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.containsBean(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(applicationContext);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            applicationContextElResolver.setValue(
                new ActivitiElContext(), null, "Property", "Value"));
    verify(applicationContext).containsBean("Property");
  }

  /**
   * Test {@link ApplicationContextElResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ApplicationContext} {@link ApplicationContext#containsBean(String)} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationContextElResolver#setValue(ELContext, Object, Object,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ApplicationContextElResolver.setValue(ELContext, Object, Object, Object)"
  })
  public void testSetValue_givenApplicationContextContainsBeanReturnFalse() {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.containsBean(Mockito.<String>any())).thenReturn(false);
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(applicationContext);

    // Act
    applicationContextElResolver.setValue(new ActivitiElContext(), null, "Property", "Value");

    // Assert
    verify(applicationContext).containsBean("Property");
  }

  /**
   * Test {@link ApplicationContextElResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ApplicationContext} {@link ApplicationContext#containsBean(String)} return
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationContextElResolver#setValue(ELContext, Object, Object,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ApplicationContextElResolver.setValue(ELContext, Object, Object, Object)"
  })
  public void testSetValue_givenApplicationContextContainsBeanReturnTrue() {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.containsBean(Mockito.<String>any())).thenReturn(true);
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(applicationContext);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            applicationContextElResolver.setValue(
                new ActivitiElContext(), null, "Property", "Value"));
    verify(applicationContext).containsBean("Property");
  }

  /**
   * Test {@link ApplicationContextElResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <p>Method under test: {@link ApplicationContextElResolver#getCommonPropertyType(ELContext,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class ApplicationContextElResolver.getCommonPropertyType(ELContext, Object)"})
  public void testGetCommonPropertyType() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(mock(ApplicationContext.class));

    // Act
    Class<?> actualCommonPropertyType =
        applicationContextElResolver.getCommonPropertyType(new ActivitiElContext(), "Arg");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link ApplicationContextElResolver#getFeatureDescriptors(ELContext, Object)}.
   *
   * <p>Method under test: {@link ApplicationContextElResolver#getFeatureDescriptors(ELContext,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Iterator ApplicationContextElResolver.getFeatureDescriptors(ELContext, Object)"
  })
  public void testGetFeatureDescriptors() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(mock(ApplicationContext.class));

    // Act and Assert
    assertNull(applicationContextElResolver.getFeatureDescriptors(new ActivitiElContext(), "Arg"));
  }

  /**
   * Test {@link ApplicationContextElResolver#getType(ELContext, Object, Object)}.
   *
   * <p>Method under test: {@link ApplicationContextElResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class ApplicationContextElResolver.getType(ELContext, Object, Object)"})
  public void testGetType() {
    // Arrange
    ApplicationContextElResolver applicationContextElResolver =
        new ApplicationContextElResolver(mock(ApplicationContext.class));

    // Act
    Class<?> actualType =
        applicationContextElResolver.getType(new ActivitiElContext(), "Arg1", "Arg2");

    // Assert
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }
}
