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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class VariableInstanceImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceImpl.<init>()",
    "void VariableInstanceImpl.<init>(String, String, Object, String, String)",
    "String VariableInstanceImpl.getName()",
    "String VariableInstanceImpl.getProcessInstanceId()",
    "String VariableInstanceImpl.getTaskId()",
    "String VariableInstanceImpl.getType()",
    "Object VariableInstanceImpl.getValue()",
    "void VariableInstanceImpl.setProcessInstanceId(String)",
    "void VariableInstanceImpl.setTaskId(String)",
    "String VariableInstanceImpl.toString()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    VariableInstanceImpl<Object> actualVariableInstanceImpl = new VariableInstanceImpl<>();
    actualVariableInstanceImpl.setProcessInstanceId("42");
    actualVariableInstanceImpl.setTaskId("42");
    String actualToStringResult = actualVariableInstanceImpl.toString();
    String actualName = actualVariableInstanceImpl.getName();
    String actualProcessInstanceId = actualVariableInstanceImpl.getProcessInstanceId();
    String actualTaskId = actualVariableInstanceImpl.getTaskId();
    String actualType = actualVariableInstanceImpl.getType();

    // Assert
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals(
        "VariableInstanceImpl{name='null', type='null', processInstanceId='42', taskId='42', value='null'}",
        actualToStringResult);
    assertNull(actualVariableInstanceImpl.getValue());
    assertNull(actualName);
    assertNull(actualType);
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then return {@code Name}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link VariableInstanceImpl#VariableInstanceImpl(String, String, Object, String, String)}
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
  @DisplayName("Test getters and setters; when 'Name'; then return 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VariableInstanceImpl.<init>()",
    "void VariableInstanceImpl.<init>(String, String, Object, String, String)",
    "String VariableInstanceImpl.getName()",
    "String VariableInstanceImpl.getProcessInstanceId()",
    "String VariableInstanceImpl.getTaskId()",
    "String VariableInstanceImpl.getType()",
    "Object VariableInstanceImpl.getValue()",
    "void VariableInstanceImpl.setProcessInstanceId(String)",
    "void VariableInstanceImpl.setTaskId(String)",
    "String VariableInstanceImpl.toString()"
  })
  void testGettersAndSetters_whenName_thenReturnName() {
    // Arrange and Act
    VariableInstanceImpl<Object> actualVariableInstanceImpl =
        new VariableInstanceImpl<>("Name", "Type", "Value", "42", "42");
    actualVariableInstanceImpl.setProcessInstanceId("42");
    actualVariableInstanceImpl.setTaskId("42");
    String actualToStringResult = actualVariableInstanceImpl.toString();
    String actualName = actualVariableInstanceImpl.getName();
    String actualProcessInstanceId = actualVariableInstanceImpl.getProcessInstanceId();
    String actualTaskId = actualVariableInstanceImpl.getTaskId();
    String actualType = actualVariableInstanceImpl.getType();

    // Assert
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("Name", actualName);
    assertEquals("Type", actualType);
    assertEquals("Value", actualVariableInstanceImpl.getValue());
    assertEquals(
        "VariableInstanceImpl{name='Name', type='Type', processInstanceId='42', taskId='42', value='Value'}",
        actualToStringResult);
  }

  /**
   * Test {@link VariableInstanceImpl#isTaskVariable()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceImpl#VariableInstanceImpl()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceImpl#isTaskVariable()}
   */
  @Test
  @DisplayName("Test isTaskVariable(); given VariableInstanceImpl(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableInstanceImpl.isTaskVariable()"})
  void testIsTaskVariable_givenVariableInstanceImpl_thenReturnFalse() {
    // Arrange
    VariableInstanceImpl<Object> variableInstanceImpl = new VariableInstanceImpl<>();

    // Act and Assert
    assertFalse(variableInstanceImpl.isTaskVariable());
  }

  /**
   * Test {@link VariableInstanceImpl#isTaskVariable()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableInstanceImpl#isTaskVariable()}
   */
  @Test
  @DisplayName("Test isTaskVariable(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableInstanceImpl.isTaskVariable()"})
  void testIsTaskVariable_thenReturnTrue() {
    // Arrange
    VariableInstanceImpl<Object> variableInstanceImpl =
        new VariableInstanceImpl<>("Name", "Type", "Value", "42", "42");

    // Act and Assert
    assertTrue(variableInstanceImpl.isTaskVariable());
  }
}
