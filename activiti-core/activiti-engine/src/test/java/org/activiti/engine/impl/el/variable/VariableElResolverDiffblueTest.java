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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VariableElResolverDiffblueTest {
  @InjectMocks
  private VariableElResolver variableElResolver;

  /**
   * Test {@link VariableElResolver#canResolve(String, VariableScope)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableElResolver#canResolve(String, VariableScope)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableElResolver.canResolve(String, VariableScope)"})
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
   * Method under test: {@link VariableElResolver#canResolve(String, VariableScope)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableElResolver.canResolve(String, VariableScope)"})
  public void testCanResolve_whenSharedInstance_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(variableElResolver.canResolve("Property", NoExecutionVariableScope.getSharedInstance()));
  }
}
