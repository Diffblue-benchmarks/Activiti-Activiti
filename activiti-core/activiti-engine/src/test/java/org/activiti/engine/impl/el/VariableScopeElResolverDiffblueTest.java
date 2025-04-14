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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import java.util.Map;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.VariableScopeImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class VariableScopeElResolverDiffblueTest {
  /**
   * Test {@link VariableScopeElResolver#VariableScopeElResolver(VariableScope)}.
   * <p>
   * Method under test: {@link VariableScopeElResolver#VariableScopeElResolver(VariableScope)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeElResolver.<init>(VariableScope)"})
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

  /**
   * Test {@link VariableScopeElResolver#getValue(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link VariableScopeElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeElResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertNull(variableScopeElResolver.getValue(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeElResolver.isReadOnly(ELContext, Object, Object)"})
  public void testIsReadOnly_thenReturnTrue() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertTrue(variableScopeElResolver.isReadOnly(new ParsingElContext(), null, "Property"));
  }

  /**
   * Test {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeElResolver.isReadOnly(ELContext, Object, Object)"})
  public void testIsReadOnly_whenNull_thenReturnTrue() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertTrue(variableScopeElResolver.isReadOnly(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeElResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link VariableScopeImpl#setVariable(String, Object)} does nothing.</li>
   *   <li>Then calls {@link ELContext#putContext(Class, Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeElResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeElResolver.setValue(ELContext, Object, Object, Object)"})
  public void testSetValue_givenExecutionEntityImplSetVariableDoesNothing_thenCallsPutContext() {
    // Arrange
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    doNothing().when(variableScope).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(variableScope.hasVariable(Mockito.<String>any())).thenReturn(true);
    doNothing().when(variableScope).addChildExecution(Mockito.<ExecutionEntity>any());
    variableScope.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(variableScope);
    ELContext context = mock(ELContext.class);
    doNothing().when(context).putContext(Mockito.<Class<Object>>any(), Mockito.<Object>any());
    Class<Object> key = Object.class;
    context.putContext(key, JSONObject.NULL);

    // Act
    variableScopeElResolver.setValue(context, null, "Property", JSONObject.NULL);

    // Assert
    verify(context).putContext(isA(Class.class), isA(Object.class));
    verify(variableScope).addChildExecution(isA(ExecutionEntity.class));
    verify(variableScope).hasVariable(eq("Property"));
    verify(variableScope).setVariable(eq("Property"), isA(Object.class));
  }

  /**
   * Test {@link VariableScopeElResolver#getCommonPropertyType(ELContext, Object)}.
   * <p>
   * Method under test: {@link VariableScopeElResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Class VariableScopeElResolver.getCommonPropertyType(ELContext, Object)"})
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
   * Test {@link VariableScopeElResolver#getFeatureDescriptors(ELContext, Object)}.
   * <p>
   * Method under test: {@link VariableScopeElResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Iterator VariableScopeElResolver.getFeatureDescriptors(ELContext, Object)"})
  public void testGetFeatureDescriptors() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver = new VariableScopeElResolver(
        NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertNull(variableScopeElResolver.getFeatureDescriptors(new ParsingElContext(), JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeElResolver#getType(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link VariableScopeElResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Class VariableScopeElResolver.getType(ELContext, Object, Object)"})
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
}
