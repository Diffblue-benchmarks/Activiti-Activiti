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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.CustomObjectType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VariableElResolverDiffblueTest {
  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private VariableElResolver variableElResolver;

  /**
   * Test {@link VariableElResolver#canResolve(String, VariableScope)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableElResolver#canResolve(String, VariableScope)}
   */
  @Test
  public void testCanResolve_givenTrue_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    when(variableScope.hasVariable(Mockito.<String>any())).thenReturn(true);

    // Act
    boolean actualCanResolveResult = variableElResolver.canResolve("Property", variableScope);

    // Assert
    verify(variableScope).hasVariable(eq("Property"));
    assertTrue(actualCanResolveResult);
  }

  /**
   * Test {@link VariableElResolver#canResolve(String, VariableScope)}.
   * <ul>
   *   <li>When SharedInstance.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableElResolver#canResolve(String, VariableScope)}
   */
  @Test
  public void testCanResolve_whenSharedInstance_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(variableElResolver.canResolve("Property", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link VariableElResolver#resolve(String, VariableScope)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableElResolver#resolve(String, VariableScope)}
   */
  @Test
  public void testResolve_givenJavaLangObject_thenReturnNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    Class<Object> theClass = Object.class;
    variableInstanceEntityImpl.setType(new CustomObjectType("json", theClass));
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    when(variableScope.getVariableInstance(Mockito.<String>any())).thenReturn(variableInstanceEntityImpl);

    // Act
    Object actualResolveResult = variableElResolver.resolve("Property", variableScope);

    // Assert
    verify(variableScope).getVariableInstance(eq("Property"));
    assertNull(actualResolveResult);
  }

  /**
   * Test {@link VariableElResolver#resolve(String, VariableScope)}.
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) Type is
   * {@link BigDecimalType} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableElResolver#resolve(String, VariableScope)}
   */
  @Test
  public void testResolve_givenVariableInstanceEntityImplTypeIsBigDecimalType_thenReturnNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BigDecimalType());
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    when(variableScope.getVariableInstance(Mockito.<String>any())).thenReturn(variableInstanceEntityImpl);

    // Act
    Object actualResolveResult = variableElResolver.resolve("Property", variableScope);

    // Assert
    verify(variableScope).getVariableInstance(eq("Property"));
    assertNull(actualResolveResult);
  }
}
