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
package org.activiti.engine.impl.el;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.BeanNameELResolver;
import jakarta.el.BeanNameResolver;
import jakarta.el.ELContext;
import java.util.Map;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class VariableScopeElResolverDiffblueTest {
  /**
   * Method under test:
   * {@link VariableScopeElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertNull(variableScopeElResolver.getValue(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  public void testIsReadOnly() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertTrue(variableScopeElResolver.isReadOnly(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  public void testIsReadOnly2() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertTrue(variableScopeElResolver.isReadOnly(new ParsingElContext(), null, "Property"));
  }

  /**
   * Method under test:
   * {@link VariableScopeElResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    VariableScope variableScope = mock(VariableScope.class);
    doNothing().when(variableScope).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(variableScope.hasVariable(Mockito.<String>any())).thenReturn(true);
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(variableScope);

    // Act
    variableScopeElResolver.setValue(new ParsingElContext(), null, "Property", JSONObject.NULL);

    // Assert
    verify(variableScope).hasVariable(eq("Property"));
    verify(variableScope).setVariable(eq("Property"), isA(Object.class));
  }

  /**
   * Method under test:
   * {@link VariableScopeElResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  public void testGetCommonPropertyType() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act
    Class<?> actualCommonPropertyType = variableScopeElResolver.getCommonPropertyType(new ParsingElContext(),
        JSONObject.NULL);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Method under test:
   * {@link VariableScopeElResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  public void testGetCommonPropertyType2() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act
    Class<?> actualCommonPropertyType = variableScopeElResolver.getCommonPropertyType(
        new ActivitiElContext(new BeanNameELResolver(mock(BeanNameResolver.class))), JSONObject.NULL);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Method under test:
   * {@link VariableScopeElResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  public void testGetFeatureDescriptors() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertNull(variableScopeElResolver.getFeatureDescriptors(new ParsingElContext(), JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link VariableScopeElResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  public void testGetFeatureDescriptors2() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertNull(variableScopeElResolver.getFeatureDescriptors(
        new ActivitiElContext(new BeanNameELResolver(mock(BeanNameResolver.class))), JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link VariableScopeElResolver#getType(ELContext, Object, Object)}
   */
  @Test
  public void testGetType() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act
    Class<?> actualType = variableScopeElResolver.getType(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL);

    // Assert
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Method under test:
   * {@link VariableScopeElResolver#VariableScopeElResolver(VariableScope)}
   */
  @Test
  public void testNewVariableScopeElResolver() {
    // Arrange, Act and Assert
    VariableScope variableScope = (new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance())).variableScope;
    assertTrue(variableScope instanceof NoExecutionVariableScope);
    assertNull(variableScope.getTransientVariables());
    assertNull(variableScope.getTransientVariablesLocal());
    assertNull(variableScope.getVariableInstances());
    assertNull(variableScope.getVariableInstancesLocal());
    assertNull(variableScope.getVariableNamesLocal());
    assertFalse(variableScope.hasVariables());
    assertFalse(variableScope.hasVariablesLocal());
    Map<String, Object> variables = variableScope.getVariables();
    assertTrue(variables.isEmpty());
    assertTrue(variableScope.getVariableNames().isEmpty());
    assertSame(variables, variableScope.getVariablesLocal());
  }
}
