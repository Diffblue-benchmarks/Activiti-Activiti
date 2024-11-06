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
   * Test {@link ProcessInitiatorELResolver#canResolve(String, VariableScope)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInitiatorELResolver#canResolve(String, VariableScope)}
   */
  @Test
  public void testCanResolve_whenCreateWithEmptyRelationshipCollections_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(processInitiatorELResolver.canResolve("initiator",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ProcessInitiatorELResolver#canResolve(String, VariableScope)}.
   * <ul>
   *   <li>When {@link ExecutionEntityImpl}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInitiatorELResolver#canResolve(String, VariableScope)}
   */
  @Test
  public void testCanResolve_whenExecutionEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(processInitiatorELResolver.canResolve("Property", mock(ExecutionEntityImpl.class)));
  }

  /**
   * Test {@link ProcessInitiatorELResolver#canResolve(String, VariableScope)}.
   * <ul>
   *   <li>When {@code initiator}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInitiatorELResolver#canResolve(String, VariableScope)}
   */
  @Test
  public void testCanResolve_whenInitiator_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(processInitiatorELResolver.canResolve("initiator", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link ProcessInitiatorELResolver#canResolve(String, VariableScope)}.
   * <ul>
   *   <li>When {@code Property}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInitiatorELResolver#canResolve(String, VariableScope)}
   */
  @Test
  public void testCanResolve_whenProperty_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(processInitiatorELResolver.canResolve("Property", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link ProcessInitiatorELResolver#resolve(String, VariableScope)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessInstance()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInitiatorELResolver#resolve(String, VariableScope)}
   */
  @Test
  public void testResolve_thenCallsGetProcessInstance() {
    // Arrange
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    when(variableScope.getProcessInstance()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Object actualResolveResult = processInitiatorELResolver.resolve("Property", variableScope);

    // Assert
    verify(variableScope).getProcessInstance();
    assertNull(actualResolveResult);
  }

  /**
   * Test {@link ProcessInitiatorELResolver#resolve(String, VariableScope)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInitiatorELResolver#resolve(String, VariableScope)}
   */
  @Test
  public void testResolve_whenCreateWithEmptyRelationshipCollections_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        processInitiatorELResolver.resolve("Property", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }
}
