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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskVariablesPayloadValidator.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class TaskVariablesPayloadValidatorDiffblueTest {
  @MockBean private DateFormatterProvider dateFormatterProvider;

  @Autowired private TaskVariablesPayloadValidator taskVariablesPayloadValidator;

  @MockBean private VariableNameValidator variableNameValidator;

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleCreateTaskVariablePayload(CreateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskVariablePayload TaskVariablesPayloadValidator.handleCreateTaskVariablePayload(CreateTaskVariablePayload)"
  })
  void testHandleCreateTaskVariablePayload() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any()))
        .thenThrow(new DateTimeException("Variable has not a valid name: "));
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    CreateTaskVariablePayload createTaskVariablePayload =
        new CreateTaskVariablePayload("42", "Variable has not a valid name: ", "Value");

    // Act
    CreateTaskVariablePayload actualHandleCreateTaskVariablePayloadResult =
        taskVariablesPayloadValidator.handleCreateTaskVariablePayload(createTaskVariablePayload);

    // Assert
    verify(dateFormatterProvider).parse("Value");
    verify(variableNameValidator).validate("Variable has not a valid name: ");
    assertEquals("Value", createTaskVariablePayload.getValue());
    assertSame(createTaskVariablePayload, actualHandleCreateTaskVariablePayloadResult);
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleCreateTaskVariablePayload(CreateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskVariablePayload TaskVariablesPayloadValidator.handleCreateTaskVariablePayload(CreateTaskVariablePayload)"
  })
  void testHandleCreateTaskVariablePayload2() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenThrow(new IllegalStateException());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    CreateTaskVariablePayload createTaskVariablePayload =
        new CreateTaskVariablePayload("42", "Variable has not a valid name: ", "Value");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
                createTaskVariablePayload));
    verify(dateFormatterProvider).parse("Value");
    verify(variableNameValidator).validate("Variable has not a valid name: ");
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleCreateTaskVariablePayload(CreateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskVariablePayload TaskVariablesPayloadValidator.handleCreateTaskVariablePayload(CreateTaskVariablePayload)"
  })
  void testHandleCreateTaskVariablePayload3() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(false);
    CreateTaskVariablePayload createTaskVariablePayload =
        new CreateTaskVariablePayload("42", "Variable has not a valid name: ", "Value");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
                createTaskVariablePayload));
    verify(variableNameValidator).validate("Variable has not a valid name: ");
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleCreateTaskVariablePayload(CreateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskVariablePayload TaskVariablesPayloadValidator.handleCreateTaskVariablePayload(CreateTaskVariablePayload)"
  })
  void testHandleCreateTaskVariablePayload4() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(false);
    CreateTaskVariablePayload createTaskVariablePayload =
        new CreateTaskVariablePayload("42", null, "Value");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
                createTaskVariablePayload));
    verify(variableNameValidator).validate(null);
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleCreateTaskVariablePayload(CreateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskVariablePayload TaskVariablesPayloadValidator.handleCreateTaskVariablePayload(CreateTaskVariablePayload)"
  })
  void testHandleCreateTaskVariablePayload5() {
    // Arrange
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesPayloadValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
                new CreateTaskVariablePayload()));
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleCreateTaskVariablePayload(CreateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskVariablePayload TaskVariablesPayloadValidator.handleCreateTaskVariablePayload(CreateTaskVariablePayload)"
  })
  void testHandleCreateTaskVariablePayload6() {
    // Arrange
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesPayloadValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());
    CreateTaskVariablePayload createTaskVariablePayload =
        new CreateTaskVariablePayload("42", "not empty", "42");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
                createTaskVariablePayload));
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleCreateTaskVariablePayload(CreateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskVariablePayload TaskVariablesPayloadValidator.handleCreateTaskVariablePayload(CreateTaskVariablePayload)"
  })
  void testHandleCreateTaskVariablePayload7() {
    // Arrange
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesPayloadValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());
    CreateTaskVariablePayload createTaskVariablePayload =
        new CreateTaskVariablePayload("42", "Name", "42");

    // Act
    CreateTaskVariablePayload actualHandleCreateTaskVariablePayloadResult =
        taskVariablesPayloadValidator.handleCreateTaskVariablePayload(createTaskVariablePayload);

    // Assert
    assertEquals("42", createTaskVariablePayload.getValue());
    assertSame(createTaskVariablePayload, actualHandleCreateTaskVariablePayloadResult);
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then {@link CreateTaskVariablePayload#CreateTaskVariablePayload()} Value is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test handleCreateTaskVariablePayload(CreateTaskVariablePayload); then CreateTaskVariablePayload() Value is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskVariablePayload TaskVariablesPayloadValidator.handleCreateTaskVariablePayload(CreateTaskVariablePayload)"
  })
  void testHandleCreateTaskVariablePayload_thenCreateTaskVariablePayloadValueIsNull() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    CreateTaskVariablePayload createTaskVariablePayload = new CreateTaskVariablePayload();

    // Act
    CreateTaskVariablePayload actualHandleCreateTaskVariablePayloadResult =
        taskVariablesPayloadValidator.handleCreateTaskVariablePayload(createTaskVariablePayload);

    // Assert
    verify(variableNameValidator).validate(null);
    assertNull(createTaskVariablePayload.getValue());
    assertSame(createTaskVariablePayload, actualHandleCreateTaskVariablePayloadResult);
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then return TaskId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test handleCreateTaskVariablePayload(CreateTaskVariablePayload); then return TaskId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskVariablePayload TaskVariablesPayloadValidator.handleCreateTaskVariablePayload(CreateTaskVariablePayload)"
  })
  void testHandleCreateTaskVariablePayload_thenReturnTaskIdIs42() throws DateTimeException {
    // Arrange
    Date fromResult =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenReturn(fromResult);
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    CreateTaskVariablePayload createTaskVariablePayload =
        new CreateTaskVariablePayload("42", "Variable has not a valid name: ", "Value");

    // Act
    CreateTaskVariablePayload actualHandleCreateTaskVariablePayloadResult =
        taskVariablesPayloadValidator.handleCreateTaskVariablePayload(createTaskVariablePayload);

    // Assert
    verify(dateFormatterProvider).parse("Value");
    verify(variableNameValidator).validate("Variable has not a valid name: ");
    assertEquals("42", actualHandleCreateTaskVariablePayloadResult.getTaskId());
    assertEquals(
        "Variable has not a valid name: ", actualHandleCreateTaskVariablePayloadResult.getName());
    assertSame(fromResult, createTaskVariablePayload.getValue());
    assertSame(fromResult, actualHandleCreateTaskVariablePayloadResult.getValue());
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then throw {@link DateTimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleCreateTaskVariablePayload(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test handleCreateTaskVariablePayload(CreateTaskVariablePayload); then throw DateTimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskVariablePayload TaskVariablesPayloadValidator.handleCreateTaskVariablePayload(CreateTaskVariablePayload)"
  })
  void testHandleCreateTaskVariablePayload_thenThrowDateTimeException() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any()))
        .thenThrow(new DateTimeException("Variable has not a valid name: "));

    // Act and Assert
    assertThrows(
        DateTimeException.class,
        () ->
            taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
                new CreateTaskVariablePayload()));
    verify(variableNameValidator).validate(null);
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UpdateTaskVariablePayload TaskVariablesPayloadValidator.handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)"
  })
  void testHandleUpdateTaskVariablePayload() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any()))
        .thenThrow(new DateTimeException("You cannot update a variable with not a valid name: "));
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    UpdateTaskVariablePayload updateTaskVariablePayload =
        new UpdateTaskVariablePayload(
            "42", "You cannot update a variable with not a valid name: ", "Value");

    // Act
    UpdateTaskVariablePayload actualHandleUpdateTaskVariablePayloadResult =
        taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(updateTaskVariablePayload);

    // Assert
    verify(dateFormatterProvider).parse("Value");
    verify(variableNameValidator).validate("You cannot update a variable with not a valid name: ");
    assertEquals("Value", updateTaskVariablePayload.getValue());
    assertSame(updateTaskVariablePayload, actualHandleUpdateTaskVariablePayloadResult);
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UpdateTaskVariablePayload TaskVariablesPayloadValidator.handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)"
  })
  void testHandleUpdateTaskVariablePayload2() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenThrow(new IllegalStateException());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    UpdateTaskVariablePayload updateTaskVariablePayload =
        new UpdateTaskVariablePayload(
            "42", "You cannot update a variable with not a valid name: ", "Value");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
                updateTaskVariablePayload));
    verify(dateFormatterProvider).parse("Value");
    verify(variableNameValidator).validate("You cannot update a variable with not a valid name: ");
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UpdateTaskVariablePayload TaskVariablesPayloadValidator.handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)"
  })
  void testHandleUpdateTaskVariablePayload3() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(false);
    UpdateTaskVariablePayload updateTaskVariablePayload =
        new UpdateTaskVariablePayload(
            "42", "You cannot update a variable with not a valid name: ", "Value");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
                updateTaskVariablePayload));
    verify(variableNameValidator).validate("You cannot update a variable with not a valid name: ");
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UpdateTaskVariablePayload TaskVariablesPayloadValidator.handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)"
  })
  void testHandleUpdateTaskVariablePayload4() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(false);
    UpdateTaskVariablePayload updateTaskVariablePayload =
        new UpdateTaskVariablePayload("42", null, "Value");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
                updateTaskVariablePayload));
    verify(variableNameValidator).validate(null);
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UpdateTaskVariablePayload TaskVariablesPayloadValidator.handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)"
  })
  void testHandleUpdateTaskVariablePayload5() {
    // Arrange
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesPayloadValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
                new UpdateTaskVariablePayload()));
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UpdateTaskVariablePayload TaskVariablesPayloadValidator.handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)"
  })
  void testHandleUpdateTaskVariablePayload6() {
    // Arrange
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesPayloadValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());
    UpdateTaskVariablePayload updateTaskVariablePayload =
        new UpdateTaskVariablePayload("42", "not empty", "42");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
                updateTaskVariablePayload));
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UpdateTaskVariablePayload TaskVariablesPayloadValidator.handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)"
  })
  void testHandleUpdateTaskVariablePayload7() {
    // Arrange
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesPayloadValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());
    UpdateTaskVariablePayload updateTaskVariablePayload =
        new UpdateTaskVariablePayload("42", "Name", "42");

    // Act
    UpdateTaskVariablePayload actualHandleUpdateTaskVariablePayloadResult =
        taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(updateTaskVariablePayload);

    // Assert
    assertEquals("42", updateTaskVariablePayload.getValue());
    assertSame(updateTaskVariablePayload, actualHandleUpdateTaskVariablePayloadResult);
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then return TaskId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test handleUpdateTaskVariablePayload(UpdateTaskVariablePayload); then return TaskId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UpdateTaskVariablePayload TaskVariablesPayloadValidator.handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)"
  })
  void testHandleUpdateTaskVariablePayload_thenReturnTaskIdIs42() throws DateTimeException {
    // Arrange
    Date fromResult =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenReturn(fromResult);
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    UpdateTaskVariablePayload updateTaskVariablePayload =
        new UpdateTaskVariablePayload(
            "42", "You cannot update a variable with not a valid name: ", "Value");

    // Act
    UpdateTaskVariablePayload actualHandleUpdateTaskVariablePayloadResult =
        taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(updateTaskVariablePayload);

    // Assert
    verify(dateFormatterProvider).parse("Value");
    verify(variableNameValidator).validate("You cannot update a variable with not a valid name: ");
    assertEquals("42", actualHandleUpdateTaskVariablePayloadResult.getTaskId());
    assertEquals(
        "You cannot update a variable with not a valid name: ",
        actualHandleUpdateTaskVariablePayloadResult.getName());
    assertSame(fromResult, updateTaskVariablePayload.getValue());
    assertSame(fromResult, actualHandleUpdateTaskVariablePayloadResult.getValue());
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then throw {@link DateTimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test handleUpdateTaskVariablePayload(UpdateTaskVariablePayload); then throw DateTimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UpdateTaskVariablePayload TaskVariablesPayloadValidator.handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)"
  })
  void testHandleUpdateTaskVariablePayload_thenThrowDateTimeException() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any()))
        .thenThrow(new DateTimeException("You cannot update a variable with not a valid name: "));

    // Act and Assert
    assertThrows(
        DateTimeException.class,
        () ->
            taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
                new UpdateTaskVariablePayload()));
    verify(variableNameValidator).validate(null);
  }

  /**
   * Test {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then {@link UpdateTaskVariablePayload#UpdateTaskVariablePayload()} Value is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test handleUpdateTaskVariablePayload(UpdateTaskVariablePayload); then UpdateTaskVariablePayload() Value is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "UpdateTaskVariablePayload TaskVariablesPayloadValidator.handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)"
  })
  void testHandleUpdateTaskVariablePayload_thenUpdateTaskVariablePayloadValueIsNull() {
    // Arrange
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    UpdateTaskVariablePayload updateTaskVariablePayload = new UpdateTaskVariablePayload();

    // Act
    UpdateTaskVariablePayload actualHandleUpdateTaskVariablePayloadResult =
        taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(updateTaskVariablePayload);

    // Assert
    verify(variableNameValidator).validate(null);
    assertNull(updateTaskVariablePayload.getValue());
    assertSame(updateTaskVariablePayload, actualHandleUpdateTaskVariablePayloadResult);
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName("Test handlePayloadVariables(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any()))
        .thenThrow(new DateTimeException("foo"));
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashSet<>());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult =
        taskVariablesPayloadValidator.handlePayloadVariables(variables);

    // Assert
    verify(dateFormatterProvider).parse("42");
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertEquals(1, variables.size());
    assertEquals("42", variables.get("foo"));
    assertSame(variables, actualHandlePayloadVariablesResult);
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName("Test handlePayloadVariables(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables2() throws DateTimeException {
    // Arrange
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenThrow(new IllegalStateException());
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashSet<>());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskVariablesPayloadValidator.handlePayloadVariables(variables));
    verify(dateFormatterProvider).parse("42");
    verify(variableNameValidator).validateVariables(isA(Map.class));
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName("Test handlePayloadVariables(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables3() {
    // Arrange
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesPayloadValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    // Act and Assert
    assertTrue(taskVariablesPayloadValidator.handlePayloadVariables(new HashMap<>()).isEmpty());
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName("Test handlePayloadVariables(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables4() {
    // Arrange
    VariableNameValidator variableNameValidator = mock(VariableNameValidator.class);
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashSet<>());
    TaskVariablesPayloadValidator taskVariablesPayloadValidator =
        new TaskVariablesPayloadValidator(
            new DateFormatterProvider("2020-03-01"), variableNameValidator);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult =
        taskVariablesPayloadValidator.handlePayloadVariables(variables);

    // Assert
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertEquals(1, variables.size());
    assertEquals("42", variables.get("foo"));
    assertSame(variables, actualHandlePayloadVariablesResult);
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <ul>
   *   <li>Given {@link HashSet#HashSet()} add {@code foo}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName(
      "Test handlePayloadVariables(Map); given HashSet() add 'foo'; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables_givenHashSetAddFoo_thenThrowIllegalStateException() {
    // Arrange
    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(stringSet);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskVariablesPayloadValidator.handlePayloadVariables(new HashMap<>()));
    verify(variableNameValidator).validateVariables(isA(Map.class));
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>Then {@link HashMap#HashMap()} {@code foo} intValue is one.
   * </ul>
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName("Test handlePayloadVariables(Map); given one; then HashMap() 'foo' intValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables_givenOne_thenHashMapFooIntValueIsOne() {
    // Arrange
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashSet<>());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", 1);

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult =
        taskVariablesPayloadValidator.handlePayloadVariables(variables);

    // Assert
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertEquals(1, variables.size());
    assertEquals(1, ((Integer) variables.get("foo")).intValue());
    assertSame(variables, actualHandlePayloadVariablesResult);
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <ul>
   *   <li>Given {@link VariableNameValidator}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName(
      "Test handlePayloadVariables(Map); given VariableNameValidator; when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables_givenVariableNameValidator_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(taskVariablesPayloadValidator.handlePayloadVariables(null));
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName("Test handlePayloadVariables(Map); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables_thenReturnSizeIsOne() throws DateTimeException {
    // Arrange
    Date fromResult =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenReturn(fromResult);
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashSet<>());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult =
        taskVariablesPayloadValidator.handlePayloadVariables(variables);

    // Assert
    verify(dateFormatterProvider).parse("42");
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertEquals(1, variables.size());
    assertEquals(1, actualHandlePayloadVariablesResult.size());
    assertSame(fromResult, variables.get("foo"));
    assertSame(fromResult, actualHandlePayloadVariablesResult.get("foo"));
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <ul>
   *   <li>Then throw {@link DateTimeException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName("Test handlePayloadVariables(Map); then throw DateTimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables_thenThrowDateTimeException() {
    // Arrange
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any()))
        .thenThrow(new DateTimeException("foo"));

    // Act and Assert
    assertThrows(
        DateTimeException.class,
        () -> taskVariablesPayloadValidator.handlePayloadVariables(new HashMap<>()));
    verify(variableNameValidator).validateVariables(isA(Map.class));
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.
   *   <li>Then {@link HashMap#HashMap()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName(
      "Test handlePayloadVariables(Map); when HashMap() '42' is '42'; then HashMap() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables_whenHashMap42Is42_thenHashMapSizeIsTwo()
      throws DateTimeException {
    // Arrange
    Date fromResult =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(dateFormatterProvider.parse(Mockito.<String>any())).thenReturn(fromResult);
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashSet<>());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("42", "42");
    variables.put("foo", "42");

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult =
        taskVariablesPayloadValidator.handlePayloadVariables(variables);

    // Assert
    verify(dateFormatterProvider, atLeast(1)).parse("42");
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertEquals(2, variables.size());
    assertEquals(2, actualHandlePayloadVariablesResult.size());
    assertTrue(variables.containsKey("foo"));
    assertTrue(actualHandlePayloadVariablesResult.containsKey("foo"));
    assertSame(fromResult, variables.get("42"));
    assertSame(fromResult, actualHandlePayloadVariablesResult.get("42"));
  }

  /**
   * Test {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskVariablesPayloadValidator#handlePayloadVariables(Map)}
   */
  @Test
  @DisplayName("Test handlePayloadVariables(Map); when HashMap(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskVariablesPayloadValidator.handlePayloadVariables(Map)"})
  void testHandlePayloadVariables_whenHashMap_thenReturnEmpty() {
    // Arrange
    when(variableNameValidator.validateVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashSet<>());

    // Act
    Map<String, Object> actualHandlePayloadVariablesResult =
        taskVariablesPayloadValidator.handlePayloadVariables(new HashMap<>());

    // Assert
    verify(variableNameValidator).validateVariables(isA(Map.class));
    assertTrue(actualHandlePayloadVariablesResult.isEmpty());
  }
}
