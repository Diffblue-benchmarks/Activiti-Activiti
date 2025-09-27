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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class GatewayDiffblueTest {
  /**
   * Test {@link Gateway#getDefaultFlow()}.
   *
   * <p>Method under test: {@link Gateway#getDefaultFlow()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Gateway.getDefaultFlow()"})
  public void testGetDefaultFlow() {
    // Arrange, Act and Assert
    assertNull(new ComplexGateway().getDefaultFlow());
  }

  /**
   * Test {@link Gateway#setDefaultFlow(String)}.
   *
   * <p>Method under test: {@link Gateway#setDefaultFlow(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Gateway.setDefaultFlow(String)"})
  public void testSetDefaultFlow() {
    // Arrange
    ComplexGateway complexGateway = new ComplexGateway();

    // Act
    complexGateway.setDefaultFlow("Default Flow");

    // Assert
    assertEquals("Default Flow", complexGateway.getDefaultFlow());
  }

  /**
   * Test {@link Gateway#setValues(Gateway)} with {@code Gateway}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then {@link ComplexGateway} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Gateway#setValues(Gateway)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Gateway.setValues(Gateway)"})
  public void testSetValuesWithGateway_givenNull_thenComplexGatewayIdIs42() {
    // Arrange
    ComplexGateway complexGateway = new ComplexGateway();

    EventGateway otherElement = mock(EventGateway.class);
    when(otherElement.getExecutionListeners()).thenReturn(null);
    when(otherElement.getAttributes()).thenReturn(null);
    when(otherElement.getExtensionElements()).thenReturn(null);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");

    // Act
    complexGateway.setValues(otherElement);

    // Assert
    verify(otherElement).getAttributes();
    verify(otherElement).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(otherElement).getDefaultFlow();
    assertEquals("42", complexGateway.getId());
    assertEquals("Default Flow", complexGateway.getDefaultFlow());
    assertEquals("Documentation", complexGateway.getDocumentation());
    assertEquals("Name", complexGateway.getName());
    assertFalse(complexGateway.isExclusive());
    assertTrue(complexGateway.isAsynchronous());
    assertTrue(complexGateway.isNotExclusive());
  }

  /**
   * Test {@link Gateway#setValues(Gateway)} with {@code Gateway}.
   *
   * <ul>
   *   <li>When {@link ComplexGateway} (default constructor).
   *   <li>Then not {@link ComplexGateway} (default constructor) Asynchronous.
   * </ul>
   *
   * <p>Method under test: {@link Gateway#setValues(Gateway)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Gateway.setValues(Gateway)"})
  public void testSetValuesWithGateway_whenComplexGateway_thenNotComplexGatewayAsynchronous() {
    // Arrange
    ComplexGateway complexGateway = new ComplexGateway();
    ComplexGateway otherElement = new ComplexGateway();

    // Act
    complexGateway.setValues((Gateway) otherElement);

    // Assert that nothing has changed
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
  }
}
