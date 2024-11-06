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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessVariablesPayloadValidator.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProcessVariablesPayloadValidatorDiffblueTest {
  @MockBean
  private DateFormatterProvider dateFormatterProvider;

  @MockBean
  private ExpressionResolver expressionResolver;

  @MockBean
  private ProcessExtensionService processExtensionService;

  @Autowired
  private ProcessVariablesPayloadValidator processVariablesPayloadValidator;

  @MockBean
  private VariableNameValidator variableNameValidator;

  @MockBean
  private VariableValidationService variableValidationService;

  /**
   * Test
   * {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   * with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName("Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(new Extension());

    // Act
    processVariablesPayloadValidator.checkPayloadVariables(new SetProcessVariablesPayload(), "42");

    // Assert
    verify(processExtensionService).getExtensionsForId(eq("42"));
  }

  /**
   * Test
   * {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   * with {@code setProcessVariablesPayload}, {@code processDefinitionId}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesPayloadValidator#checkPayloadVariables(SetProcessVariablesPayload, String)}
   */
  @Test
  @DisplayName("Test checkPayloadVariables(SetProcessVariablesPayload, String) with 'setProcessVariablesPayload', 'processDefinitionId'")
  void testCheckPayloadVariablesWithSetProcessVariablesPayloadProcessDefinitionId2() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> processVariablesPayloadValidator.checkPayloadVariables(new SetProcessVariablesPayload(), "42"));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getProperties();
  }

  /**
   * Test
   * {@link ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload, String)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload, String)}
   */
  @Test
  @DisplayName("Test checkStartProcessPayloadVariables(StartProcessPayload, String)")
  void testCheckStartProcessPayloadVariables() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(new Extension());

    // Act
    processVariablesPayloadValidator.checkStartProcessPayloadVariables(new StartProcessPayload(), "42");

    // Assert
    verify(processExtensionService).getExtensionsForId(eq("42"));
  }

  /**
   * Test
   * {@link ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesPayloadValidator#checkStartProcessPayloadVariables(StartProcessPayload, String)}
   */
  @Test
  @DisplayName("Test checkStartProcessPayloadVariables(StartProcessPayload, String); then throw ActivitiException")
  void testCheckStartProcessPayloadVariables_thenThrowActivitiException() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> processVariablesPayloadValidator.checkStartProcessPayloadVariables(new StartProcessPayload(), "42"));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getProperties();
  }

  /**
   * Test
   * {@link ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload, String)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload, String)}
   */
  @Test
  @DisplayName("Test checkStartMessagePayloadVariables(StartMessagePayload, String)")
  void testCheckStartMessagePayloadVariables() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(new Extension());

    // Act
    processVariablesPayloadValidator.checkStartMessagePayloadVariables(new StartMessagePayload(), "42");

    // Assert
    verify(processExtensionService).getExtensionsForId(eq("42"));
  }

  /**
   * Test
   * {@link ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesPayloadValidator#checkStartMessagePayloadVariables(StartMessagePayload, String)}
   */
  @Test
  @DisplayName("Test checkStartMessagePayloadVariables(StartMessagePayload, String); then throw ActivitiException")
  void testCheckStartMessagePayloadVariables_thenThrowActivitiException() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> processVariablesPayloadValidator.checkStartMessagePayloadVariables(new StartMessagePayload(), "42"));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getProperties();
  }

  /**
   * Test
   * {@link ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)}
   */
  @Test
  @DisplayName("Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)")
  void testCheckReceiveMessagePayloadVariables() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(new Extension());

    // Act
    processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(new ReceiveMessagePayload(), "42");

    // Assert
    verify(processExtensionService).getExtensionsForId(eq("42"));
  }

  /**
   * Test
   * {@link ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)}
   */
  @Test
  @DisplayName("Test checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String); then throw ActivitiException")
  void testCheckReceiveMessagePayloadVariables_thenThrowActivitiException() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> processVariablesPayloadValidator.checkReceiveMessagePayloadVariables(new ReceiveMessagePayload(), "42"));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getProperties();
  }

  /**
   * Test
   * {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName("Test checkSignalPayloadVariables(SignalPayload, String)")
  void testCheckSignalPayloadVariables() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(new Extension());

    // Act
    processVariablesPayloadValidator.checkSignalPayloadVariables(new SignalPayload(), "42");

    // Assert
    verify(processExtensionService).getExtensionsForId(eq("42"));
  }

  /**
   * Test
   * {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}
   */
  @Test
  @DisplayName("Test checkSignalPayloadVariables(SignalPayload, String); then throw ActivitiException")
  void testCheckSignalPayloadVariables_thenThrowActivitiException() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> processVariablesPayloadValidator.checkSignalPayloadVariables(new SignalPayload(), "42"));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getProperties();
  }
}
