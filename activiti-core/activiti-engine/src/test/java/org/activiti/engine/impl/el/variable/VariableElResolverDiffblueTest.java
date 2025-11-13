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
package org.activiti.engine.impl.el.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.TransientVariableInstance;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VariableElResolverDiffblueTest {
  @Mock private ObjectMapper objectMapper;

  @InjectMocks private VariableElResolver variableElResolver;

  /**
   * Test {@link VariableElResolver#canResolve(String, VariableScope)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableElResolver#canResolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableElResolver.canResolve(String, VariableScope)"})
  public void testCanResolve_givenTrue_thenReturnTrue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    VariableElResolver variableElResolver = new VariableElResolver(objectMapper);

    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    when(variableScope.hasVariable(Mockito.<String>any())).thenReturn(true);

    // Act
    boolean actualCanResolveResult = variableElResolver.canResolve("Property", variableScope);

    // Assert
    verify(variableScope).hasVariable("Property");
    assertTrue(actualCanResolveResult);
  }

  /**
   * Test {@link VariableElResolver#canResolve(String, VariableScope)}.
   *
   * <ul>
   *   <li>When SharedInstance.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableElResolver#canResolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableElResolver.canResolve(String, VariableScope)"})
  public void testCanResolve_whenSharedInstance_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertFalse(
        new VariableElResolver(objectMapper)
            .canResolve("Property", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link VariableElResolver#resolve(String, VariableScope)}.
   *
   * <p>Method under test: {@link VariableElResolver#resolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableElResolver.resolve(String, VariableScope)"})
  public void testResolve() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    VariableElResolver variableElResolver = new VariableElResolver(objectMapper);

    VariableScope variableScope = mock(VariableScope.class);
    DoubleNode valueOfResult = DoubleNode.valueOf(10.0d);
    TransientVariableInstance transientVariableInstance =
        new TransientVariableInstance("Variable Name", valueOfResult);
    when(variableScope.getVariableInstance(Mockito.<String>any()))
        .thenReturn(transientVariableInstance);

    // Act
    Object actualResolveResult = variableElResolver.resolve("Property", variableScope);

    // Assert
    verify(variableScope).getVariableInstance("Property");
    assertSame(valueOfResult, actualResolveResult);
  }

  /**
   * Test {@link VariableElResolver#resolve(String, VariableScope)}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#convertValue(Object, Class)} return {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link VariableElResolver#resolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableElResolver.resolve(String, VariableScope)"})
  public void testResolve_givenObjectMapperConvertValueReturnArrayList_thenReturnArrayList()
      throws IllegalArgumentException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    when(objectMapper.convertValue(Mockito.<Object>any(), eq(List.class))).thenReturn(objectList);

    VariableInstance variableInstance = mock(VariableInstance.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(variableInstance.getValue()).thenReturn(new ArrayNode(nf));
    when(variableInstance.getTypeName()).thenReturn("json");

    VariableScope variableScope = mock(VariableScope.class);
    when(variableScope.getVariableInstance(Mockito.<String>any())).thenReturn(variableInstance);

    // Act
    Object actualResolveResult = variableElResolver.resolve("Property", variableScope);

    // Assert
    verify(objectMapper).convertValue(isA(Object.class), isA(Class.class));
    verify(variableScope).getVariableInstance("Property");
    verify(variableInstance).getTypeName();
    verify(variableInstance).getValue();
    assertTrue(actualResolveResult instanceof List);
    assertTrue(((List<Object>) actualResolveResult).isEmpty());
    assertSame(objectList, actualResolveResult);
  }

  /**
   * Test {@link VariableElResolver#resolve(String, VariableScope)}.
   *
   * <ul>
   *   <li>Given {@link VariableInstance} {@link VariableInstance#getTypeName()} return {@code
   *       longJson}.
   *   <li>Then return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link VariableElResolver#resolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableElResolver.resolve(String, VariableScope)"})
  public void testResolve_givenVariableInstanceGetTypeNameReturnLongJson_thenReturnValueOfTen() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    VariableElResolver variableElResolver = new VariableElResolver(objectMapper);

    VariableInstance variableInstance = mock(VariableInstance.class);
    DoubleNode valueOfResult = DoubleNode.valueOf(10.0d);
    when(variableInstance.getValue()).thenReturn(valueOfResult);
    when(variableInstance.getTypeName()).thenReturn("longJson");

    VariableScope variableScope = mock(VariableScope.class);
    when(variableScope.getVariableInstance(Mockito.<String>any())).thenReturn(variableInstance);

    // Act
    Object actualResolveResult = variableElResolver.resolve("Property", variableScope);

    // Assert
    verify(variableScope).getVariableInstance("Property");
    verify(variableInstance, atLeast(1)).getTypeName();
    verify(variableInstance).getValue();
    assertSame(valueOfResult, actualResolveResult);
  }

  /**
   * Test {@link VariableElResolver#resolve(String, VariableScope)}.
   *
   * <ul>
   *   <li>Given {@link VariableInstance} {@link VariableInstance#getValue()} return {@code json}.
   *   <li>Then return {@code json}.
   * </ul>
   *
   * <p>Method under test: {@link VariableElResolver#resolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableElResolver.resolve(String, VariableScope)"})
  public void testResolve_givenVariableInstanceGetValueReturnJson_thenReturnJson() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    VariableElResolver variableElResolver = new VariableElResolver(objectMapper);

    VariableInstance variableInstance = mock(VariableInstance.class);
    when(variableInstance.getValue()).thenReturn("json");
    when(variableInstance.getTypeName()).thenReturn("json");

    VariableScope variableScope = mock(VariableScope.class);
    when(variableScope.getVariableInstance(Mockito.<String>any())).thenReturn(variableInstance);

    // Act
    Object actualResolveResult = variableElResolver.resolve("Property", variableScope);

    // Assert
    verify(variableScope).getVariableInstance("Property");
    verify(variableInstance).getTypeName();
    verify(variableInstance).getValue();
    assertEquals("json", actualResolveResult);
  }

  /**
   * Test {@link VariableElResolver#resolve(String, VariableScope)}.
   *
   * <ul>
   *   <li>Given {@link VariableInstance} {@link VariableInstance#getValue()} return valueOf ten.
   *   <li>Then return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link VariableElResolver#resolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableElResolver.resolve(String, VariableScope)"})
  public void testResolve_givenVariableInstanceGetValueReturnValueOfTen_thenReturnValueOfTen() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    VariableElResolver variableElResolver = new VariableElResolver(objectMapper);

    VariableInstance variableInstance = mock(VariableInstance.class);
    DoubleNode valueOfResult = DoubleNode.valueOf(10.0d);
    when(variableInstance.getValue()).thenReturn(valueOfResult);
    when(variableInstance.getTypeName()).thenReturn("json");

    VariableScope variableScope = mock(VariableScope.class);
    when(variableScope.getVariableInstance(Mockito.<String>any())).thenReturn(variableInstance);

    // Act
    Object actualResolveResult = variableElResolver.resolve("Property", variableScope);

    // Assert
    verify(variableScope).getVariableInstance("Property");
    verify(variableInstance).getTypeName();
    verify(variableInstance).getValue();
    assertSame(valueOfResult, actualResolveResult);
  }

  /**
   * Test {@link VariableElResolver#resolve(String, VariableScope)}.
   *
   * <ul>
   *   <li>Then return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link VariableElResolver#resolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableElResolver.resolve(String, VariableScope)"})
  public void testResolve_thenReturnList() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    VariableElResolver variableElResolver = new VariableElResolver(objectMapper);

    VariableInstance variableInstance = mock(VariableInstance.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(variableInstance.getValue()).thenReturn(new ArrayNode(nf));
    when(variableInstance.getTypeName()).thenReturn("json");

    VariableScope variableScope = mock(VariableScope.class);
    when(variableScope.getVariableInstance(Mockito.<String>any())).thenReturn(variableInstance);

    // Act
    Object actualResolveResult = variableElResolver.resolve("Property", variableScope);

    // Assert
    verify(variableScope).getVariableInstance("Property");
    verify(variableInstance).getTypeName();
    verify(variableInstance).getValue();
    assertTrue(actualResolveResult instanceof List);
    assertTrue(((List<Object>) actualResolveResult).isEmpty());
  }
}
