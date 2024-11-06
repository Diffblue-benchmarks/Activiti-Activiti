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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.persistence.entity.SuspensionState.SuspensionStateImpl;
import org.activiti.engine.impl.persistence.entity.SuspensionState.SuspensionStateUtil;
import org.junit.Test;

public class SuspensionStateDiffblueTest {
  /**
   * Test SuspensionStateImpl {@link SuspensionStateImpl#equals(Object)}, and
   * {@link SuspensionStateImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SuspensionState.SuspensionStateImpl#equals(Object)}
   *   <li>{@link SuspensionState.SuspensionStateImpl#hashCode()}
   * </ul>
   */
  @Test
  public void testSuspensionStateImplEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SuspensionState.SuspensionStateImpl suspensionStateImpl = new SuspensionState.SuspensionStateImpl(1, "String");
    SuspensionState.SuspensionStateImpl suspensionStateImpl2 = new SuspensionState.SuspensionStateImpl(1, "String");

    // Act and Assert
    assertEquals(suspensionStateImpl, suspensionStateImpl2);
    int expectedHashCodeResult = suspensionStateImpl.hashCode();
    assertEquals(expectedHashCodeResult, suspensionStateImpl2.hashCode());
  }

  /**
   * Test SuspensionStateImpl {@link SuspensionStateImpl#equals(Object)}, and
   * {@link SuspensionStateImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SuspensionState.SuspensionStateImpl#equals(Object)}
   *   <li>{@link SuspensionState.SuspensionStateImpl#hashCode()}
   * </ul>
   */
  @Test
  public void testSuspensionStateImplEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SuspensionState.SuspensionStateImpl suspensionStateImpl = new SuspensionState.SuspensionStateImpl(1, "String");

    // Act and Assert
    assertEquals(suspensionStateImpl, suspensionStateImpl);
    int expectedHashCodeResult = suspensionStateImpl.hashCode();
    assertEquals(expectedHashCodeResult, suspensionStateImpl.hashCode());
  }

  /**
   * Test SuspensionStateImpl {@link SuspensionStateImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspensionState.SuspensionStateImpl#equals(Object)}
   */
  @Test
  public void testSuspensionStateImplEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SuspensionState.SuspensionStateImpl suspensionStateImpl = new SuspensionState.SuspensionStateImpl(0, "String");

    // Act and Assert
    assertNotEquals(suspensionStateImpl, new SuspensionState.SuspensionStateImpl(1, "String"));
  }

  /**
   * Test SuspensionStateImpl {@link SuspensionStateImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspensionState.SuspensionStateImpl#equals(Object)}
   */
  @Test
  public void testSuspensionStateImplEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SuspensionState.SuspensionStateImpl(1, "String"), null);
  }

  /**
   * Test SuspensionStateImpl {@link SuspensionStateImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspensionState.SuspensionStateImpl#equals(Object)}
   */
  @Test
  public void testSuspensionStateImplEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SuspensionState.SuspensionStateImpl(1, "String"), "Different type to SuspensionStateImpl");
  }

  /**
   * Test SuspensionStateImpl getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link SuspensionState.SuspensionStateImpl#SuspensionStateImpl(int, String)}
   *   <li>{@link SuspensionState.SuspensionStateImpl#getStateCode()}
   *   <li>{@link SuspensionState.SuspensionStateImpl#toString()}
   * </ul>
   */
  @Test
  public void testSuspensionStateImplGettersAndSetters() {
    // Arrange and Act
    SuspensionState.SuspensionStateImpl actualSuspensionStateImpl = new SuspensionState.SuspensionStateImpl(1,
        "String");
    int actualStateCode = actualSuspensionStateImpl.getStateCode();

    // Assert
    assertEquals("String", actualSuspensionStateImpl.toString());
    assertEquals(1, actualStateCode);
  }

  /**
   * Test SuspensionStateUtil
   * {@link SuspensionStateUtil#setSuspensionState(ExecutionEntity, SuspensionState)}
   * with {@code executionEntity}, {@code state}.
   * <p>
   * Method under test:
   * {@link SuspensionState.SuspensionStateUtil#setSuspensionState(ExecutionEntity, SuspensionState)}
   */
  @Test
  public void testSuspensionStateUtilSetSuspensionStateWithExecutionEntityState() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> SuspensionState.SuspensionStateUtil
        .setSuspensionState(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), SuspensionState.ACTIVE));
  }

  /**
   * Test SuspensionStateUtil
   * {@link SuspensionStateUtil#setSuspensionState(ExecutionEntity, SuspensionState)}
   * with {@code executionEntity}, {@code state}.
   * <p>
   * Method under test:
   * {@link SuspensionState.SuspensionStateUtil#setSuspensionState(ExecutionEntity, SuspensionState)}
   */
  @Test
  public void testSuspensionStateUtilSetSuspensionStateWithExecutionEntityState2() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    SuspensionState state = mock(SuspensionState.class);
    when(state.getStateCode()).thenReturn(0);

    // Act
    SuspensionState.SuspensionStateUtil.setSuspensionState(executionEntity, state);

    // Assert
    verify(state, atLeast(1)).getStateCode();
    assertEquals(0, executionEntity.getSuspensionState());
  }

  /**
   * Test SuspensionStateUtil
   * {@link SuspensionStateUtil#setSuspensionState(ExecutionEntity, SuspensionState)}
   * with {@code executionEntity}, {@code state}.
   * <p>
   * Method under test:
   * {@link SuspensionState.SuspensionStateUtil#setSuspensionState(ExecutionEntity, SuspensionState)}
   */
  @Test
  public void testSuspensionStateUtilSetSuspensionStateWithExecutionEntityState3() {
    // Arrange
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    SuspensionState state = mock(SuspensionState.class);
    when(state.getStateCode()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> SuspensionState.SuspensionStateUtil.setSuspensionState(executionEntity, state));
    verify(state).getStateCode();
  }

  /**
   * Test SuspensionStateUtil
   * {@link SuspensionStateUtil#setSuspensionState(ProcessDefinitionEntity, SuspensionState)}
   * with {@code processDefinitionEntity}, {@code state}.
   * <p>
   * Method under test:
   * {@link SuspensionState.SuspensionStateUtil#setSuspensionState(ProcessDefinitionEntity, SuspensionState)}
   */
  @Test
  public void testSuspensionStateUtilSetSuspensionStateWithProcessDefinitionEntityState() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> SuspensionState.SuspensionStateUtil
        .setSuspensionState(new ProcessDefinitionEntityImpl(), SuspensionState.ACTIVE));
  }

  /**
   * Test SuspensionStateUtil
   * {@link SuspensionStateUtil#setSuspensionState(ProcessDefinitionEntity, SuspensionState)}
   * with {@code processDefinitionEntity}, {@code state}.
   * <p>
   * Method under test:
   * {@link SuspensionState.SuspensionStateUtil#setSuspensionState(ProcessDefinitionEntity, SuspensionState)}
   */
  @Test
  public void testSuspensionStateUtilSetSuspensionStateWithProcessDefinitionEntityState2() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    SuspensionState state = mock(SuspensionState.class);
    when(state.getStateCode()).thenReturn(0);

    // Act
    SuspensionState.SuspensionStateUtil.setSuspensionState(processDefinitionEntity, state);

    // Assert
    verify(state, atLeast(1)).getStateCode();
    Object persistentState = processDefinitionEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(2, ((Map<String, Integer>) persistentState).size());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("suspensionState").intValue());
    assertEquals(0, processDefinitionEntity.getSuspensionState());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("category"));
  }

  /**
   * Test SuspensionStateUtil
   * {@link SuspensionStateUtil#setSuspensionState(ProcessDefinitionEntity, SuspensionState)}
   * with {@code processDefinitionEntity}, {@code state}.
   * <p>
   * Method under test:
   * {@link SuspensionState.SuspensionStateUtil#setSuspensionState(ProcessDefinitionEntity, SuspensionState)}
   */
  @Test
  public void testSuspensionStateUtilSetSuspensionStateWithProcessDefinitionEntityState3() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    SuspensionState state = mock(SuspensionState.class);
    when(state.getStateCode()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> SuspensionState.SuspensionStateUtil.setSuspensionState(processDefinitionEntity, state));
    verify(state).getStateCode();
  }
}
