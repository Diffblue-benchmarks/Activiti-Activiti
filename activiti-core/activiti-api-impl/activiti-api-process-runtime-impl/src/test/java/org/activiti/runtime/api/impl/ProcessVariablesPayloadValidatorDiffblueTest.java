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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.activiti.api.process.model.payloads.ReceiveMessagePayload;
import org.activiti.api.process.model.payloads.SetProcessVariablesPayload;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.process.model.payloads.StartMessagePayload;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.spring.process.ProcessExtensionService;
import org.activiti.spring.process.model.Extension;
import org.activiti.spring.process.variable.VariableValidationService;
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

@ContextConfiguration(classes = {ProcessVariablesPayloadValidator.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ProcessVariablesPayloadValidatorDiffblueTest {
  @MockBean private DateFormatterProvider dateFormatterProvider;

  @MockBean private ExpressionResolver expressionResolver;

  @MockBean private ProcessExtensionService processExtensionService;

  @Autowired private ProcessVariablesPayloadValidator processVariablesPayloadValidator;

  @MockBean private VariableNameValidator variableNameValidator;

  @MockBean private VariableValidationService variableValidationService;

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload,
   * String)} with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkPayloadVariables(SetProcessVariablesPayload, String)"
  })
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId() {
    // Arrange
    SetProcessVariablesPayload setProcessVariablesPayload =
        new SetProcessVariablesPayload("42", new HashMap<>());

    // Act and Assert
    assertDoesNotThrow(
        () ->
            processVariablesPayloadValidator.checkPayloadVariables(
                setProcessVariablesPayload, null));
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload,
   * String)} with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkPayloadVariables(SetProcessVariablesPayload, String)"
  })
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId2() {
    // Arrange
    SetProcessVariablesPayload setProcessVariablesPayload =
        new SetProcessVariablesPayload("42", null);

    // Act and Assert
    assertDoesNotThrow(
        () ->
            processVariablesPayloadValidator.checkPayloadVariables(
                setProcessVariablesPayload, "42"));
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload,
   * String)} with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkPayloadVariables(SetProcessVariablesPayload, String)"
  })
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId3() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());

    // Act
    processVariablesPayloadValidator.checkPayloadVariables(new SetProcessVariablesPayload(), "42");

    // Assert
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload,
   * String)} with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkPayloadVariables(SetProcessVariablesPayload, String)"
  })
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId4() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkPayloadVariables(
                new SetProcessVariablesPayload(), "42"));
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload,
   * String)} with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkPayloadVariables(SetProcessVariablesPayload, String)"
  })
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId5() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkPayloadVariables(
                new SetProcessVariablesPayload(), "42"));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getProperties();
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload,
   * String)} with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkPayloadVariables(SetProcessVariablesPayload, String)"
  })
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId6() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    SetProcessVariablesPayload setProcessVariablesPayload =
        new SetProcessVariablesPayload("42", variables);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkPayloadVariables(
                setProcessVariablesPayload, "42"));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload,
   * String)} with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkPayloadVariables(SetProcessVariablesPayload, String)"
  })
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId7() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    SetProcessVariablesPayload setProcessVariablesPayload =
        new SetProcessVariablesPayload("42", variables);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processVariablesPayloadValidator.checkPayloadVariables(
                setProcessVariablesPayload, "42"));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload,
   * String)} with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkPayloadVariables(SetProcessVariablesPayload, String)"
  })
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId8() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    SetProcessVariablesPayload setProcessVariablesPayload =
        new SetProcessVariablesPayload("42", variables);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkPayloadVariables(
                setProcessVariablesPayload, "42"));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload,
   * String)} with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkPayloadVariables(SetProcessVariablesPayload, String)"
  })
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId9() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Variables fail type validation: {0}", "Value");
    variables.put("Key", "Value");
    SetProcessVariablesPayload setProcessVariablesPayload =
        new SetProcessVariablesPayload("42", variables);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processVariablesPayloadValidator.checkPayloadVariables(
                setProcessVariablesPayload, "42"));
    verify(expressionResolver, atLeast(1)).containsExpression(isA(Object.class));
    verify(variableNameValidator, atLeast(1)).validate(Mockito.<String>any());
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkStartProcessPayloadVariables(StartProcessPayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartProcessPayloadVariables(StartProcessPayload, String)"
  })
  void testCheckStartProcessPayloadVariables() {
    // Arrange
    StartProcessPayload startProcessPayload =
        new StartProcessPayload("42", "Process Definition Key", "Name", "Business Key", null);

    // Act and Assert
    assertDoesNotThrow(
        () ->
            processVariablesPayloadValidator.checkStartProcessPayloadVariables(
                startProcessPayload, "42"));
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkStartProcessPayloadVariables(StartProcessPayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartProcessPayloadVariables(StartProcessPayload, String)"
  })
  void testCheckStartProcessPayloadVariables2() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkStartProcessPayloadVariables(
                new StartProcessPayload(), "42"));
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkStartProcessPayloadVariables(StartProcessPayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartProcessPayloadVariables(StartProcessPayload, String)"
  })
  void testCheckStartProcessPayloadVariables3() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    StartProcessPayload startProcessPayload =
        new StartProcessPayload("42", "Process Definition Key", "Name", "Business Key", variables);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkStartProcessPayloadVariables(
                startProcessPayload, "42"));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkStartProcessPayloadVariables(StartProcessPayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartProcessPayloadVariables(StartProcessPayload, String)"
  })
  void testCheckStartProcessPayloadVariables4() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    StartProcessPayload startProcessPayload =
        new StartProcessPayload("42", "Process Definition Key", "Name", "Business Key", variables);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkStartProcessPayloadVariables(
                startProcessPayload, "42"));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}.
   *
   * <ul>
   *   <li>Given {@code Variables fail type validation: {0}}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkStartProcessPayloadVariables(StartProcessPayload, String); given 'Variables fail type validation: {0}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartProcessPayloadVariables(StartProcessPayload, String)"
  })
  void testCheckStartProcessPayloadVariables_givenVariablesFailTypeValidation0() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Variables fail type validation: {0}", "Value");
    variables.put("Key", "Value");
    StartProcessPayload startProcessPayload =
        new StartProcessPayload("42", "Process Definition Key", "Name", "Business Key", variables);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processVariablesPayloadValidator.checkStartProcessPayloadVariables(
                startProcessPayload, "42"));
    verify(expressionResolver, atLeast(1)).containsExpression(isA(Object.class));
    verify(variableNameValidator, atLeast(1)).validate(Mockito.<String>any());
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link Extension#getProperties()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkStartProcessPayloadVariables(StartProcessPayload, String); then calls getProperties()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartProcessPayloadVariables(StartProcessPayload, String)"
  })
  void testCheckStartProcessPayloadVariables_thenCallsGetProperties() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkStartProcessPayloadVariables(
                new StartProcessPayload(), "42"));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getProperties();
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkStartProcessPayloadVariables(StartProcessPayload, String); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartProcessPayloadVariables(StartProcessPayload, String)"
  })
  void testCheckStartProcessPayloadVariables_thenDoesNotThrow() {
    // Arrange
    StartProcessPayload startProcessPayload =
        new StartProcessPayload(
            "42", "Process Definition Key", "Name", "Business Key", new HashMap<>());

    // Act and Assert
    assertDoesNotThrow(
        () ->
            processVariablesPayloadValidator.checkStartProcessPayloadVariables(
                startProcessPayload, null));
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkStartProcessPayloadVariables(StartProcessPayload, String); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartProcessPayloadVariables(StartProcessPayload, String)"
  })
  void testCheckStartProcessPayloadVariables_thenThrowIllegalStateException() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    StartProcessPayload startProcessPayload =
        new StartProcessPayload("42", "Process Definition Key", "Name", "Business Key", variables);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processVariablesPayloadValidator.checkStartProcessPayloadVariables(
                startProcessPayload, "42"));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}.
   *
   * <ul>
   *   <li>When {@link StartProcessPayload#StartProcessPayload()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkStartProcessPayloadVariables(StartProcessPayload, String); when StartProcessPayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartProcessPayloadVariables(StartProcessPayload, String)"
  })
  void testCheckStartProcessPayloadVariables_whenStartProcessPayload() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());

    // Act
    processVariablesPayloadValidator.checkStartProcessPayloadVariables(
        new StartProcessPayload(), "42");

    // Assert
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkStartMessagePayloadVariables(StartMessagePayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartMessagePayloadVariables(StartMessagePayload, String)"
  })
  void testCheckStartMessagePayloadVariables() {
    // Arrange
    StartMessagePayload startMessagePayload = new StartMessagePayload("Name", "Business Key", null);

    // Act and Assert
    assertDoesNotThrow(
        () ->
            processVariablesPayloadValidator.checkStartMessagePayloadVariables(
                startMessagePayload, "42"));
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkStartMessagePayloadVariables(StartMessagePayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartMessagePayloadVariables(StartMessagePayload, String)"
  })
  void testCheckStartMessagePayloadVariables2() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkStartMessagePayloadVariables(
                new StartMessagePayload(), "42"));
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkStartMessagePayloadVariables(StartMessagePayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartMessagePayloadVariables(StartMessagePayload, String)"
  })
  void testCheckStartMessagePayloadVariables3() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    StartMessagePayload startMessagePayload =
        new StartMessagePayload("Name", "Business Key", variables);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkStartMessagePayloadVariables(
                startMessagePayload, "42"));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkStartMessagePayloadVariables(StartMessagePayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartMessagePayloadVariables(StartMessagePayload, String)"
  })
  void testCheckStartMessagePayloadVariables4() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    StartMessagePayload startMessagePayload =
        new StartMessagePayload("Name", "Business Key", variables);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkStartMessagePayloadVariables(
                startMessagePayload, "42"));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}.
   *
   * <ul>
   *   <li>Given {@code Variables fail type validation: {0}}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkStartMessagePayloadVariables(StartMessagePayload, String); given 'Variables fail type validation: {0}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartMessagePayloadVariables(StartMessagePayload, String)"
  })
  void testCheckStartMessagePayloadVariables_givenVariablesFailTypeValidation0() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Variables fail type validation: {0}", "Value");
    variables.put("Key", "Value");
    StartMessagePayload startMessagePayload =
        new StartMessagePayload("Name", "Business Key", variables);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processVariablesPayloadValidator.checkStartMessagePayloadVariables(
                startMessagePayload, "42"));
    verify(expressionResolver, atLeast(1)).containsExpression(isA(Object.class));
    verify(variableNameValidator, atLeast(1)).validate(Mockito.<String>any());
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link Extension#getProperties()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkStartMessagePayloadVariables(StartMessagePayload, String); then calls getProperties()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartMessagePayloadVariables(StartMessagePayload, String)"
  })
  void testCheckStartMessagePayloadVariables_thenCallsGetProperties() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkStartMessagePayloadVariables(
                new StartMessagePayload(), "42"));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getProperties();
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkStartMessagePayloadVariables(StartMessagePayload, String); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartMessagePayloadVariables(StartMessagePayload, String)"
  })
  void testCheckStartMessagePayloadVariables_thenDoesNotThrow() {
    // Arrange
    StartMessagePayload startMessagePayload =
        new StartMessagePayload("Name", "Business Key", new HashMap<>());

    // Act and Assert
    assertDoesNotThrow(
        () ->
            processVariablesPayloadValidator.checkStartMessagePayloadVariables(
                startMessagePayload, null));
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkStartMessagePayloadVariables(StartMessagePayload, String); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartMessagePayloadVariables(StartMessagePayload, String)"
  })
  void testCheckStartMessagePayloadVariables_thenThrowIllegalStateException() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    StartMessagePayload startMessagePayload =
        new StartMessagePayload("Name", "Business Key", variables);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processVariablesPayloadValidator.checkStartMessagePayloadVariables(
                startMessagePayload, "42"));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}.
   *
   * <ul>
   *   <li>When {@link StartMessagePayload#StartMessagePayload()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkStartMessagePayloadVariables(StartMessagePayload, String); when StartMessagePayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkStartMessagePayloadVariables(StartMessagePayload, String)"
  })
  void testCheckStartMessagePayloadVariables_whenStartMessagePayload() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());

    // Act
    processVariablesPayloadValidator.checkStartMessagePayloadVariables(
        new StartMessagePayload(), "42");

    // Assert
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)"
  })
  void testCheckReceiveMessagePayloadVariables() {
    // Arrange
    ReceiveMessagePayload receiveMessagePayload =
        new ReceiveMessagePayload("Name", "Correlation Key", null);

    // Act and Assert
    assertDoesNotThrow(
        () ->
            processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(
                receiveMessagePayload, "42"));
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)"
  })
  void testCheckReceiveMessagePayloadVariables2() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(
                new ReceiveMessagePayload(), "42"));
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)"
  })
  void testCheckReceiveMessagePayloadVariables3() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    ReceiveMessagePayload receiveMessagePayload =
        new ReceiveMessagePayload("Name", "Correlation Key", variables);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(
                receiveMessagePayload, "42"));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}
   */
  @Test
  @DisplayName("Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)"
  })
  void testCheckReceiveMessagePayloadVariables4() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    ReceiveMessagePayload receiveMessagePayload =
        new ReceiveMessagePayload("Name", "Correlation Key", variables);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(
                receiveMessagePayload, "42"));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}.
   *
   * <ul>
   *   <li>Given {@code Variables fail type validation: {0}}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String); given 'Variables fail type validation: {0}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)"
  })
  void testCheckReceiveMessagePayloadVariables_givenVariablesFailTypeValidation0() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Variables fail type validation: {0}", "Value");
    variables.put("Key", "Value");
    ReceiveMessagePayload receiveMessagePayload =
        new ReceiveMessagePayload("Name", "Correlation Key", variables);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(
                receiveMessagePayload, "42"));
    verify(expressionResolver, atLeast(1)).containsExpression(isA(Object.class));
    verify(variableNameValidator, atLeast(1)).validate(Mockito.<String>any());
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link Extension#getProperties()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String); then calls getProperties()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)"
  })
  void testCheckReceiveMessagePayloadVariables_thenCallsGetProperties() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(
                new ReceiveMessagePayload(), "42"));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getProperties();
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)"
  })
  void testCheckReceiveMessagePayloadVariables_thenDoesNotThrow() {
    // Arrange
    ReceiveMessagePayload receiveMessagePayload =
        new ReceiveMessagePayload("Name", "Correlation Key", new HashMap<>());

    // Act and Assert
    assertDoesNotThrow(
        () ->
            processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(
                receiveMessagePayload, null));
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)"
  })
  void testCheckReceiveMessagePayloadVariables_thenThrowIllegalStateException() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    ReceiveMessagePayload receiveMessagePayload =
        new ReceiveMessagePayload("Name", "Correlation Key", variables);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(
                receiveMessagePayload, "42"));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}.
   *
   * <ul>
   *   <li>When {@link ReceiveMessagePayload#ReceiveMessagePayload()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   * String)}
   */
  @Test
  @DisplayName(
      "Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String); when ReceiveMessagePayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)"
  })
  void testCheckReceiveMessagePayloadVariables_whenReceiveMessagePayload() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());

    // Act
    processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(
        new ReceiveMessagePayload(), "42");

    // Assert
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName("Test checkSignalPayloadVariables(SignalPayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkSignalPayloadVariables(SignalPayload, String)"
  })
  void testCheckSignalPayloadVariables() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkSignalPayloadVariables(
                new SignalPayload(), "42"));
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName("Test checkSignalPayloadVariables(SignalPayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkSignalPayloadVariables(SignalPayload, String)"
  })
  void testCheckSignalPayloadVariables2() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    SignalPayload signalPayload = new SignalPayload("Name", variables);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> processVariablesPayloadValidator.checkSignalPayloadVariables(signalPayload, "42"));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload,
   * String)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName("Test checkSignalPayloadVariables(SignalPayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkSignalPayloadVariables(SignalPayload, String)"
  })
  void testCheckSignalPayloadVariables3() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    SignalPayload signalPayload = new SignalPayload("Name", variables);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> processVariablesPayloadValidator.checkSignalPayloadVariables(signalPayload, "42"));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload,
   * String)}.
   *
   * <ul>
   *   <li>Given {@link ProcessExtensionService}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkSignalPayloadVariables(SignalPayload, String); given ProcessExtensionService; then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkSignalPayloadVariables(SignalPayload, String)"
  })
  void testCheckSignalPayloadVariables_givenProcessExtensionService_thenDoesNotThrow() {
    // Arrange
    SignalPayload signalPayload = new SignalPayload("Name", new HashMap<>());

    // Act and Assert
    assertDoesNotThrow(
        () -> processVariablesPayloadValidator.checkSignalPayloadVariables(signalPayload, null));
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload,
   * String)}.
   *
   * <ul>
   *   <li>Given {@code Variables fail type validation: {0}}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkSignalPayloadVariables(SignalPayload, String); given 'Variables fail type validation: {0}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkSignalPayloadVariables(SignalPayload, String)"
  })
  void testCheckSignalPayloadVariables_givenVariablesFailTypeValidation0() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Variables fail type validation: {0}", "Value");
    variables.put("Key", "Value");
    SignalPayload signalPayload = new SignalPayload("Name", variables);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processVariablesPayloadValidator.checkSignalPayloadVariables(signalPayload, "42"));
    verify(expressionResolver, atLeast(1)).containsExpression(isA(Object.class));
    verify(variableNameValidator, atLeast(1)).validate(Mockito.<String>any());
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link Extension#getProperties()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkSignalPayloadVariables(SignalPayload, String); then calls getProperties()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkSignalPayloadVariables(SignalPayload, String)"
  })
  void testCheckSignalPayloadVariables_thenCallsGetProperties() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesPayloadValidator.checkSignalPayloadVariables(
                new SignalPayload(), "42"));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getProperties();
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload,
   * String)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkSignalPayloadVariables(SignalPayload, String); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkSignalPayloadVariables(SignalPayload, String)"
  })
  void testCheckSignalPayloadVariables_thenThrowIllegalStateException() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", "Value");
    SignalPayload signalPayload = new SignalPayload("Name", variables);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processVariablesPayloadValidator.checkSignalPayloadVariables(signalPayload, "42"));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(variableNameValidator).validate("Key");
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload,
   * String)}.
   *
   * <ul>
   *   <li>When {@link SignalPayload#SignalPayload()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName("Test checkSignalPayloadVariables(SignalPayload, String); when SignalPayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkSignalPayloadVariables(SignalPayload, String)"
  })
  void testCheckSignalPayloadVariables_whenSignalPayload() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());

    // Act
    processVariablesPayloadValidator.checkSignalPayloadVariables(new SignalPayload(), "42");

    // Assert
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload,
   * String)}.
   *
   * <ul>
   *   <li>When {@link SignalPayload#SignalPayload(String, Map)} with {@code Name} and variables is
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName(
      "Test checkSignalPayloadVariables(SignalPayload, String); when SignalPayload(String, Map) with 'Name' and variables is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessVariablesPayloadValidator.checkSignalPayloadVariables(SignalPayload, String)"
  })
  void testCheckSignalPayloadVariables_whenSignalPayloadWithNameAndVariablesIsNull() {
    // Arrange
    SignalPayload signalPayload = new SignalPayload("Name", null);

    // Act and Assert
    assertDoesNotThrow(
        () -> processVariablesPayloadValidator.checkSignalPayloadVariables(signalPayload, "42"));
  }
}
