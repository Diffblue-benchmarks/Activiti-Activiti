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

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskElResolverDiffblueTest {
  @InjectMocks
  private TaskElResolver taskElResolver;

  /**
   * Test {@link TaskElResolver#resolve(String, VariableScope)}.
   * <ul>
   *   <li>When {@link ExecutionEntityImpl}.</li>
   *   <li>Then return {@link ExecutionEntityImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskElResolver#resolve(String, VariableScope)}
   */
  @Test
  public void testResolve_whenExecutionEntityImpl_thenReturnExecutionEntityImpl() {
    // Arrange
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);

    // Act and Assert
    assertSame(variableScope, taskElResolver.resolve("Property", variableScope));
  }

  /**
   * Test {@link TaskElResolver#resolve(String, VariableScope)}.
   * <ul>
   *   <li>When SharedInstance.</li>
   *   <li>Then return SharedInstance.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskElResolver#resolve(String, VariableScope)}
   */
  @Test
  public void testResolve_whenSharedInstance_thenReturnSharedInstance() {
    // Arrange
    NoExecutionVariableScope variableScope = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertSame(variableScope, taskElResolver.resolve("Property", variableScope));
  }
}
