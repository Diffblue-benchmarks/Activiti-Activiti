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
package org.activiti.engine.impl.scripting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class VariableScopeResolverDiffblueTest {
  /**
   * Test {@link VariableScopeResolver#containsKey(Object)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#hasVariable(String)} return
   *       {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeResolver#containsKey(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableScopeResolver.containsKey(Object)"})
  public void testContainsKey_givenExecutionEntityImplHasVariableReturnFalse_thenReturnFalse() {
    // Arrange
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    when(variableScope.hasVariable(Mockito.<String>any())).thenReturn(false);
    doNothing().when(variableScope).addChildExecution(Mockito.<ExecutionEntity>any());
    variableScope.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    VariableScopeResolver variableScopeResolver =
        new VariableScopeResolver(new JtaProcessEngineConfiguration(), variableScope);

    // Act
    boolean actualContainsKeyResult = variableScopeResolver.containsKey("Key");

    // Assert
    verify(variableScope).addChildExecution(isA(ExecutionEntity.class));
    verify(variableScope).hasVariable("Key");
    assertFalse(actualContainsKeyResult);
  }

  /**
   * Test {@link VariableScopeResolver#containsKey(Object)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#hasVariable(String)} return
   *       {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeResolver#containsKey(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableScopeResolver.containsKey(Object)"})
  public void testContainsKey_givenExecutionEntityImplHasVariableReturnTrue_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    when(variableScope.hasVariable(Mockito.<String>any())).thenReturn(true);
    doNothing().when(variableScope).addChildExecution(Mockito.<ExecutionEntity>any());
    variableScope.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    VariableScopeResolver variableScopeResolver =
        new VariableScopeResolver(new JtaProcessEngineConfiguration(), variableScope);

    // Act
    boolean actualContainsKeyResult = variableScopeResolver.containsKey("Key");

    // Assert
    verify(variableScope).addChildExecution(isA(ExecutionEntity.class));
    verify(variableScope).hasVariable("Key");
    assertTrue(actualContainsKeyResult);
  }

  /**
   * Test {@link VariableScopeResolver#containsKey(Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeResolver#containsKey(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableScopeResolver.containsKey(Object)"})
  public void testContainsKey_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    when(variableScope.hasVariable(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    doNothing().when(variableScope).addChildExecution(Mockito.<ExecutionEntity>any());
    variableScope.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    VariableScopeResolver variableScopeResolver =
        new VariableScopeResolver(new JtaProcessEngineConfiguration(), variableScope);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> variableScopeResolver.containsKey("Key"));
    verify(variableScope).addChildExecution(isA(ExecutionEntity.class));
    verify(variableScope).hasVariable("Key");
  }

  /**
   * Test {@link VariableScopeResolver#containsKey(Object)}.
   *
   * <ul>
   *   <li>When {@code execution}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeResolver#containsKey(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableScopeResolver.containsKey(Object)"})
  public void testContainsKey_whenExecution_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    doNothing().when(variableScope).addChildExecution(Mockito.<ExecutionEntity>any());
    variableScope.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    VariableScopeResolver variableScopeResolver =
        new VariableScopeResolver(new JtaProcessEngineConfiguration(), variableScope);

    // Act
    boolean actualContainsKeyResult = variableScopeResolver.containsKey("execution");

    // Assert
    verify(variableScope).addChildExecution(isA(ExecutionEntity.class));
    assertTrue(actualContainsKeyResult);
  }

  /**
   * Test {@link VariableScopeResolver#containsKey(Object)}.
   *
   * <ul>
   *   <li>When {@code formService}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeResolver#containsKey(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableScopeResolver.containsKey(Object)"})
  public void testContainsKey_whenFormService_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    doNothing().when(variableScope).addChildExecution(Mockito.<ExecutionEntity>any());
    variableScope.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    VariableScopeResolver variableScopeResolver =
        new VariableScopeResolver(new JtaProcessEngineConfiguration(), variableScope);

    // Act
    boolean actualContainsKeyResult = variableScopeResolver.containsKey("formService");

    // Assert
    verify(variableScope).addChildExecution(isA(ExecutionEntity.class));
    assertTrue(actualContainsKeyResult);
  }

  /**
   * Test {@link VariableScopeResolver#get(Object)}.
   *
   * <ul>
   *   <li>Then return {@link RuntimeServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeResolver#get(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableScopeResolver.get(Object)"})
  public void testGet_thenReturnRuntimeServiceImpl() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    Object actualGetResult =
        new VariableScopeResolver(
                processEngineConfiguration,
                ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .get("runtimeService");

    // Assert
    assertTrue(actualGetResult instanceof RuntimeServiceImpl);
    assertNull(((RuntimeServiceImpl) actualGetResult).getCommandExecutor());
  }

  /**
   * Test {@link VariableScopeResolver#get(Object)}.
   *
   * <ul>
   *   <li>Then return {@link TaskServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeResolver#get(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableScopeResolver.get(Object)"})
  public void testGet_thenReturnTaskServiceImpl() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    Object actualGetResult =
        new VariableScopeResolver(
                processEngineConfiguration,
                ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .get("taskService");

    // Assert
    assertTrue(actualGetResult instanceof TaskServiceImpl);
    assertNull(((TaskServiceImpl) actualGetResult).getCommandExecutor());
  }

  /**
   * Test {@link VariableScopeResolver#get(Object)}.
   *
   * <ul>
   *   <li>When {@code processEngineConfiguration}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableScopeResolver#get(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object VariableScopeResolver.get(Object)"})
  public void testGet_whenProcessEngineConfiguration_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new VariableScopeResolver(
                null, ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .get("processEngineConfiguration"));
  }
}
