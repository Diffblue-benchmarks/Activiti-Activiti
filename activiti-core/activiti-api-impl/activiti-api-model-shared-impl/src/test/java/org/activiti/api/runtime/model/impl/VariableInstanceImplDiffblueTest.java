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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class VariableInstanceImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VariableInstanceImpl#VariableInstanceImpl()}
   *   <li>{@link VariableInstanceImpl#setProcessInstanceId(String)}
   *   <li>{@link VariableInstanceImpl#setTaskId(String)}
   *   <li>{@link VariableInstanceImpl#toString()}
   *   <li>{@link VariableInstanceImpl#getName()}
   *   <li>{@link VariableInstanceImpl#getProcessInstanceId()}
   *   <li>{@link VariableInstanceImpl#getTaskId()}
   *   <li>{@link VariableInstanceImpl#getType()}
   *   <li>{@link VariableInstanceImpl#getValue()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    VariableInstanceImpl<Object> actualVariableInstanceImpl = new VariableInstanceImpl<>();
    actualVariableInstanceImpl.setProcessInstanceId("42");
    actualVariableInstanceImpl.setTaskId("42");
    String actualToStringResult = actualVariableInstanceImpl.toString();
    actualVariableInstanceImpl.getName();
    String actualProcessInstanceId = actualVariableInstanceImpl.getProcessInstanceId();
    String actualTaskId = actualVariableInstanceImpl.getTaskId();
    actualVariableInstanceImpl.getType();
    actualVariableInstanceImpl.getValue();

    // Assert that nothing has changed
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("VariableInstanceImpl{name='null', type='null', processInstanceId='42', taskId='42', value='null'}",
        actualToStringResult);
  }

  /**
   * Method under test: {@link VariableInstanceImpl#isTaskVariable()}
   */
  @Test
  void testIsTaskVariable() {
    // Arrange
    VariableInstanceImpl<Object> variableInstanceImpl = new VariableInstanceImpl<>();

    // Act and Assert
    assertFalse(variableInstanceImpl.isTaskVariable());
  }

  /**
   * Method under test: {@link VariableInstanceImpl#isTaskVariable()}
   */
  @Test
  void testIsTaskVariable2() {
    // Arrange
    VariableInstanceImpl<Object> variableInstanceImpl = new VariableInstanceImpl<>();
    variableInstanceImpl.setTaskId("foo");

    // Act and Assert
    assertTrue(variableInstanceImpl.isTaskVariable());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link VariableInstanceImpl#VariableInstanceImpl(String, String, Object, String, String)}
   *   <li>{@link VariableInstanceImpl#setProcessInstanceId(String)}
   *   <li>{@link VariableInstanceImpl#setTaskId(String)}
   *   <li>{@link VariableInstanceImpl#toString()}
   *   <li>{@link VariableInstanceImpl#getName()}
   *   <li>{@link VariableInstanceImpl#getProcessInstanceId()}
   *   <li>{@link VariableInstanceImpl#getTaskId()}
   *   <li>{@link VariableInstanceImpl#getType()}
   *   <li>{@link VariableInstanceImpl#getValue()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    VariableInstanceImpl<Object> actualVariableInstanceImpl = new VariableInstanceImpl<>("Name", "Type", "Value", "42",
        "42");
    actualVariableInstanceImpl.setProcessInstanceId("42");
    actualVariableInstanceImpl.setTaskId("42");
    String actualToStringResult = actualVariableInstanceImpl.toString();
    String actualName = actualVariableInstanceImpl.getName();
    String actualProcessInstanceId = actualVariableInstanceImpl.getProcessInstanceId();
    String actualTaskId = actualVariableInstanceImpl.getTaskId();
    String actualType = actualVariableInstanceImpl.getType();

    // Assert that nothing has changed
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("Name", actualName);
    assertEquals("Type", actualType);
    assertEquals("Value", actualVariableInstanceImpl.getValue());
    assertEquals("VariableInstanceImpl{name='Name', type='Type', processInstanceId='42', taskId='42', value='Value'}",
        actualToStringResult);
  }
}
