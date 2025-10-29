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
   * Method under test:
   * {@link VariableElResolver#canResolve(String, VariableScope)}
   */
  @Test
  public void testCanResolve() {
    // Arrange, Act and Assert
    assertFalse(variableElResolver.canResolve("Property", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Method under test:
   * {@link VariableElResolver#canResolve(String, VariableScope)}
   */
  @Test
  public void testCanResolve2() {
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
   * Method under test: {@link VariableElResolver#resolve(String, VariableScope)}
   */
  @Test
  public void testResolve() {
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

  /**
   * Method under test: {@link VariableElResolver#resolve(String, VariableScope)}
   */
  @Test
  public void testResolve2() {
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
}
