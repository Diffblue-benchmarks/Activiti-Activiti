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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import java.util.Map;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VariableScopeElResolverDiffblueTest {
  @Mock private VariableScope variableScope;

  @InjectMocks private VariableScopeElResolver variableScopeElResolver;

  /**
   * Test {@link VariableScopeElResolver#VariableScopeElResolver(VariableScope)}.
   *
   * <p>Method under test: {@link VariableScopeElResolver#VariableScopeElResolver(VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableScopeElResolver.<init>(VariableScope)"})
  public void testNewVariableScopeElResolver() {
    // Arrange, Act and Assert
    VariableScope variableScope =
        new VariableScopeElResolver(NoExecutionVariableScope.getSharedInstance()).variableScope;
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
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableScopeElResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue_thenReturnNull() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver =
        new VariableScopeElResolver(NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertNull(
        variableScopeElResolver.getValue(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link VariableScope} {@link VariableScope#hasVariable(String)} return {@code
   *       true}.
   *   <li>When {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableScopeElResolver.isReadOnly(ELContext, Object, Object)"})
  public void testIsReadOnly_givenVariableScopeHasVariableReturnTrue_whenNull_thenReturnFalse() {
    // Arrange
    when(variableScope.hasVariable(Mockito.<String>any())).thenReturn(true);

    // Act
    boolean actualIsReadOnlyResult =
        variableScopeElResolver.isReadOnly(new ParsingElContext(), null, "Property");

    // Assert
    verify(variableScope).hasVariable("Property");
    assertFalse(actualIsReadOnlyResult);
  }

  /**
   * Test {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableScopeElResolver.isReadOnly(ELContext, Object, Object)"})
  public void testIsReadOnly_thenReturnTrue() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver =
        new VariableScopeElResolver(NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertTrue(variableScopeElResolver.isReadOnly(new ParsingElContext(), null, "Property"));
  }

  /**
   * Test {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeElResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableScopeElResolver.isReadOnly(ELContext, Object, Object)"})
  public void testIsReadOnly_whenNull_thenReturnTrue() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver =
        new VariableScopeElResolver(NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertTrue(
        variableScopeElResolver.isReadOnly(
            new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeElResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link VariableScope} {@link VariableScope#hasVariable(String)} return {@code
   *       false}.
   *   <li>Then calls {@link VariableScope#hasVariable(String)}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeElResolver#setValue(ELContext, Object, Object,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableScopeElResolver.setValue(ELContext, Object, Object, Object)"})
  public void testSetValue_givenVariableScopeHasVariableReturnFalse_thenCallsHasVariable() {
    // Arrange
    when(variableScope.hasVariable(Mockito.<String>any())).thenReturn(false);

    // Act
    variableScopeElResolver.setValue(new ParsingElContext(), null, "Property", JSONObject.NULL);

    // Assert
    verify(variableScope).hasVariable("Property");
  }

  /**
   * Test {@link VariableScopeElResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link VariableScope} {@link VariableScope#setVariable(String, Object)} does
   *       nothing.
   *   <li>Then calls {@link VariableScope#setVariable(String, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeElResolver#setValue(ELContext, Object, Object,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableScopeElResolver.setValue(ELContext, Object, Object, Object)"})
  public void testSetValue_givenVariableScopeSetVariableDoesNothing_thenCallsSetVariable() {
    // Arrange
    doNothing().when(variableScope).setVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(variableScope.hasVariable(Mockito.<String>any())).thenReturn(true);

    // Act
    variableScopeElResolver.setValue(new ParsingElContext(), null, "Property", JSONObject.NULL);

    // Assert
    verify(variableScope).hasVariable("Property");
    verify(variableScope).setVariable(eq("Property"), isA(Object.class));
  }

  /**
   * Test {@link VariableScopeElResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <p>Method under test: {@link VariableScopeElResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class VariableScopeElResolver.getCommonPropertyType(ELContext, Object)"})
  public void testGetCommonPropertyType() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver =
        new VariableScopeElResolver(NoExecutionVariableScope.getSharedInstance());

    // Act
    Class<?> actualCommonPropertyType =
        variableScopeElResolver.getCommonPropertyType(new ParsingElContext(), JSONObject.NULL);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link VariableScopeElResolver#getFeatureDescriptors(ELContext, Object)}.
   *
   * <p>Method under test: {@link VariableScopeElResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Iterator VariableScopeElResolver.getFeatureDescriptors(ELContext, Object)"
  })
  public void testGetFeatureDescriptors() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver =
        new VariableScopeElResolver(NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertNull(
        variableScopeElResolver.getFeatureDescriptors(new ParsingElContext(), JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeElResolver#getType(ELContext, Object, Object)}.
   *
   * <p>Method under test: {@link VariableScopeElResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class VariableScopeElResolver.getType(ELContext, Object, Object)"})
  public void testGetType() {
    // Arrange
    VariableScopeElResolver variableScopeElResolver =
        new VariableScopeElResolver(NoExecutionVariableScope.getSharedInstance());

    // Act
    Class<?> actualType =
        variableScopeElResolver.getType(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL);

    // Assert
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }
}
