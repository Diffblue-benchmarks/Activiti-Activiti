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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.activiti.api.task.model.payloads.CreateTaskVariablePayload;
import org.activiti.api.task.model.payloads.UpdateTaskVariablePayload;
import org.activiti.common.util.DateFormatterProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskVariablesPayloadValidator.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TaskVariablesPayloadValidatorDiffblueTest {
  @MockBean
  private DateFormatterProvider dateFormatterProvider;

  @Autowired
  private TaskVariablesPayloadValidator taskVariablesPayloadValidator;

  @MockBean
  private VariableNameValidator variableNameValidator;

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  void testHandleCreateTaskVariablePayload() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    CreateTaskVariablePayload createTaskVariablePayload = new CreateTaskVariablePayload();

    // Act
    CreateTaskVariablePayload actualHandleCreateTaskVariablePayloadResult = taskVariablesPayloadValidator
        .handleCreateTaskVariablePayload(createTaskVariablePayload);

    // Assert
    verify(variableNameValidator).validate(isNull());
    assertSame(createTaskVariablePayload, actualHandleCreateTaskVariablePayloadResult);
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  void testHandleCreateTaskVariablePayload2() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> taskVariablesPayloadValidator.handleCreateTaskVariablePayload(new CreateTaskVariablePayload()));
    verify(variableNameValidator).validate(isNull());
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  void testHandleCreateTaskVariablePayload3() throws DateTimeException {
    // Arrange
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenReturn(fromResult);
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    CreateTaskVariablePayload createTaskVariablePayload = new CreateTaskVariablePayload("42",
        "Variable has not a valid name: ", "Value");

    // Act
    CreateTaskVariablePayload actualHandleCreateTaskVariablePayloadResult = taskVariablesPayloadValidator
        .handleCreateTaskVariablePayload(createTaskVariablePayload);

    // Assert
    verify(dateFormatterProvider).parse(eq("Value"));
    verify(variableNameValidator).validate(eq("Variable has not a valid name: "));
    assertSame(createTaskVariablePayload, actualHandleCreateTaskVariablePayloadResult);
    assertSame(fromResult, createTaskVariablePayload.getValue());
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  void testHandleCreateTaskVariablePayload4() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
        new CreateTaskVariablePayload("42", "Variable has not a valid name: ", "Value")));
    verify(variableNameValidator).validate(eq("Variable has not a valid name: "));
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  void testHandleCreateTaskVariablePayload5() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any()))
        .thenThrow(new DateTimeException("Variable has not a valid name: "));
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    CreateTaskVariablePayload createTaskVariablePayload = new CreateTaskVariablePayload("42",
        "Variable has not a valid name: ", "Value");

    // Act
    CreateTaskVariablePayload actualHandleCreateTaskVariablePayloadResult = taskVariablesPayloadValidator
        .handleCreateTaskVariablePayload(createTaskVariablePayload);

    // Assert
    verify(dateFormatterProvider).parse(eq("Value"));
    verify(variableNameValidator).validate(eq("Variable has not a valid name: "));
    assertEquals("Value", createTaskVariablePayload.getValue());
    assertSame(createTaskVariablePayload, actualHandleCreateTaskVariablePayloadResult);
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  void testHandleCreateTaskVariablePayload6() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any()))
        .thenThrow(new IllegalStateException("Variable has not a valid name: "));
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
        new CreateTaskVariablePayload("42", "Variable has not a valid name: ", "Value")));
    verify(dateFormatterProvider).parse(eq("Value"));
    verify(variableNameValidator).validate(eq("Variable has not a valid name: "));
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  void testHandleUpdateTaskVariablePayload() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    UpdateTaskVariablePayload updateTaskVariablePayload = new UpdateTaskVariablePayload();

    // Act
    UpdateTaskVariablePayload actualHandleUpdateTaskVariablePayloadResult = taskVariablesPayloadValidator
        .handleUpdateTaskVariablePayload(updateTaskVariablePayload);

    // Assert
    verify(variableNameValidator).validate(isNull());
    assertSame(updateTaskVariablePayload, actualHandleUpdateTaskVariablePayloadResult);
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  void testHandleUpdateTaskVariablePayload2() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(new UpdateTaskVariablePayload()));
    verify(variableNameValidator).validate(isNull());
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  void testHandleUpdateTaskVariablePayload3() throws DateTimeException {
    // Arrange
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenReturn(fromResult);
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    UpdateTaskVariablePayload updateTaskVariablePayload = new UpdateTaskVariablePayload("42",
        "You cannot update a variable with not a valid name: ", "Value");

    // Act
    UpdateTaskVariablePayload actualHandleUpdateTaskVariablePayloadResult = taskVariablesPayloadValidator
        .handleUpdateTaskVariablePayload(updateTaskVariablePayload);

    // Assert
    verify(dateFormatterProvider).parse(eq("Value"));
    verify(variableNameValidator).validate(eq("You cannot update a variable with not a valid name: "));
    assertSame(updateTaskVariablePayload, actualHandleUpdateTaskVariablePayloadResult);
    assertSame(fromResult, updateTaskVariablePayload.getValue());
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  void testHandleUpdateTaskVariablePayload4() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
        new UpdateTaskVariablePayload("42", "You cannot update a variable with not a valid name: ", "Value")));
    verify(variableNameValidator).validate(eq("You cannot update a variable with not a valid name: "));
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  void testHandleUpdateTaskVariablePayload5() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any()))
        .thenThrow(new DateTimeException("You cannot update a variable with not a valid name: "));
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    UpdateTaskVariablePayload updateTaskVariablePayload = new UpdateTaskVariablePayload("42",
        "You cannot update a variable with not a valid name: ", "Value");

    // Act
    UpdateTaskVariablePayload actualHandleUpdateTaskVariablePayloadResult = taskVariablesPayloadValidator
        .handleUpdateTaskVariablePayload(updateTaskVariablePayload);

    // Assert
    verify(dateFormatterProvider).parse(eq("Value"));
    verify(variableNameValidator).validate(eq("You cannot update a variable with not a valid name: "));
    assertEquals("Value", updateTaskVariablePayload.getValue());
    assertSame(updateTaskVariablePayload, actualHandleUpdateTaskVariablePayloadResult);
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  void testHandleUpdateTaskVariablePayload6() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any()))
        .thenThrow(new IllegalStateException("You cannot update a variable with not a valid name: "));
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
        new UpdateTaskVariablePayload("42", "You cannot update a variable with not a valid name: ", "Value")));
    verify(dateFormatterProvider).parse(eq("Value"));
    verify(variableNameValidator).validate(eq("You cannot update a variable with not a valid name: "));
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  void testHandlePayloadVariables() {
    // Arrange
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any())).thenReturn(new HashSet<>());
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult = taskVariablesPayloadValidator
        .handlePayloadVariables(variables);

    // Assert
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertTrue(variables.isEmpty());
    assertTrue(actualHandlePayloadVariablesResult.isEmpty());
    assertSame(variables, actualHandlePayloadVariablesResult);
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  void testHandlePayloadVariables2() {
    // Arrange
    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any())).thenReturn(stringSet);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> taskVariablesPayloadValidator.handlePayloadVariables(new HashMap<>()));
    verify(variableNameValidator).validateVariables(isA(Map.class));
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  void testHandlePayloadVariables3() throws DateTimeException {
    // Arrange
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenReturn(fromResult);
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any())).thenReturn(new HashSet<>());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult = taskVariablesPayloadValidator
        .handlePayloadVariables(variables);

    // Assert
    verify(dateFormatterProvider).parse(eq("42"));
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertEquals(1, variables.size());
    assertSame(variables, actualHandlePayloadVariablesResult);
    assertSame(fromResult, variables.get("foo"));
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  void testHandlePayloadVariables4() throws DateTimeException {
    // Arrange
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenReturn(fromResult);
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any())).thenReturn(new HashSet<>());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("42", "42");
    variables.put("foo", "42");

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult = taskVariablesPayloadValidator
        .handlePayloadVariables(variables);

    // Assert
    verify(dateFormatterProvider, atLeast(1)).parse(eq("42"));
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertEquals(2, variables.size());
    assertSame(variables, actualHandlePayloadVariablesResult);
    assertSame(fromResult, variables.get("42"));
    assertSame(fromResult, variables.get("foo"));
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  void testHandlePayloadVariables5() {
    // Arrange
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any())).thenReturn(new HashSet<>());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", 1);

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult = taskVariablesPayloadValidator
        .handlePayloadVariables(variables);

    // Assert
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertEquals(1, variables.size());
    assertEquals(1, actualHandlePayloadVariablesResult.size());
    assertTrue(variables.containsKey("foo"));
    assertTrue(actualHandlePayloadVariablesResult.containsKey("foo"));
    assertSame(variables, actualHandlePayloadVariablesResult);
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  void testHandlePayloadVariables6() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenThrow(new DateTimeException("foo"));
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any())).thenReturn(new HashSet<>());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult = taskVariablesPayloadValidator
        .handlePayloadVariables(variables);

    // Assert
    verify(dateFormatterProvider).parse(eq("42"));
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertEquals(1, variables.size());
    assertEquals("42", variables.get("foo"));
    assertEquals(1, actualHandlePayloadVariablesResult.size());
    assertEquals("42", actualHandlePayloadVariablesResult.get("foo"));
    assertSame(variables, actualHandlePayloadVariablesResult);
  }

  /**
   * Method under test:
   * {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  void testHandlePayloadVariables7() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any())).thenReturn(new HashSet<>());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskVariablesPayloadValidator.handlePayloadVariables(variables));
    verify(dateFormatterProvider).parse(eq("42"));
    verify(variableNameValidator).validateVariables(isA(Map.class));
  }
}
