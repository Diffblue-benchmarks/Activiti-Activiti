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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessInitiatorELResolverDiffblueTest {
  @InjectMocks
  private ProcessInitiatorELResolver processInitiatorELResolver;

  /**
   * Method under test:
   * {@link ProcessInitiatorELResolver#canResolve(String, VariableScope)}
   */
  @Test
  public void testCanResolve() {
    // Arrange, Act and Assert
    assertFalse(processInitiatorELResolver.canResolve("Property", NoExecutionVariableScope.getSharedInstance()));
    assertFalse(processInitiatorELResolver.canResolve("initiator", NoExecutionVariableScope.getSharedInstance()));
    assertFalse(processInitiatorELResolver.canResolve("Property", mock(ExecutionEntityImpl.class)));
    assertTrue(processInitiatorELResolver.canResolve("initiator",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link ProcessInitiatorELResolver#resolve(String, VariableScope)}
   */
  @Test
  public void testResolve() {
    // Arrange, Act and Assert
    assertNull(
        processInitiatorELResolver.resolve("Property", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link ProcessInitiatorELResolver#resolve(String, VariableScope)}
   */
  @Test
  public void testResolve2() {
    // Arrange
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    when(variableScope.getProcessInstance()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Object actualResolveResult = processInitiatorELResolver.resolve("Property", variableScope);

    // Assert
    verify(variableScope).getProcessInstance();
    assertNull(actualResolveResult);
  }
}
