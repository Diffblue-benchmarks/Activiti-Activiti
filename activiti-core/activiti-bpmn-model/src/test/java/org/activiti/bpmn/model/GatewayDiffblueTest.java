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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;

public class GatewayDiffblueTest {
  /**
   * Test {@link Gateway#getDefaultFlow()}.
   * <p>
   * Method under test: {@link Gateway#getDefaultFlow()}
   */
  @Test
  public void testGetDefaultFlow() {
    // Arrange, Act and Assert
    assertNull((new ComplexGateway()).getDefaultFlow());
  }

  /**
   * Test {@link Gateway#setDefaultFlow(String)}.
   * <p>
   * Method under test: {@link Gateway#setDefaultFlow(String)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then {@link ComplexGateway} (default constructor) Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Gateway#setValues(Gateway)}
   */
  @Test
  public void testSetValuesWithGateway_givenTrue_thenComplexGatewayIdIs42() {
    // Arrange
    ComplexGateway complexGateway = new ComplexGateway();
    EventGateway otherElement = mock(EventGateway.class);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    complexGateway.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
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
   * <ul>
   *   <li>When {@link ComplexGateway} (default constructor).</li>
   *   <li>Then {@link ComplexGateway} (default constructor) Id is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Gateway#setValues(Gateway)}
   */
  @Test
  public void testSetValuesWithGateway_whenComplexGateway_thenComplexGatewayIdIsNull() {
    // Arrange
    ComplexGateway complexGateway = new ComplexGateway();
    ComplexGateway otherElement = new ComplexGateway();

    // Act
    complexGateway.setValues((Gateway) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getDefaultFlow());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
  }
}
