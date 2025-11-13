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
package org.activiti.validation.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidationErrorDecoratorDiffblueTest {
  @Mock private Map<String, ErrorMessageDefinition> map;

  @InjectMocks private ValidationErrorDecorator validationErrorDecorator;

  /**
   * Test new {@link ValidationErrorDecorator} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ValidationErrorDecorator}
   */
  @Test
  @DisplayName("Test new ValidationErrorDecorator (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValidationErrorDecorator.<init>()"})
  void testNewValidationErrorDecorator() {
    // Arrange, Act and Assert
    assertEquals(
        "Not all who wander are lost",
        new ValidationErrorDecorator().resolveMessage("Not all who wander are lost", null));
  }

  /**
   * Test {@link ValidationErrorDecorator#decorate(ValidationError)}.
   *
   * <p>Method under test: {@link ValidationErrorDecorator#decorate(ValidationError)}
   */
  @Test
  @DisplayName("Test decorate(ValidationError)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValidationErrorDecorator.decorate(ValidationError)"})
  void testDecorate() {
    // Arrange
    ErrorMessageDefinition errorMessageDefinition = new ErrorMessageDefinition();
    errorMessageDefinition.setDescription("The characteristics of someone or something");
    when(map.get(Mockito.<Object>any())).thenReturn(errorMessageDefinition);

    ValidationError error = new ValidationError();
    error.setActivityId("42");
    error.setActivityName("Activity Name");
    error.setDefaultDescription("Default Description");
    error.setKey("Key");
    error.setParams(new HashMap<>());
    error.setProblem("Problem");
    error.setProcessDefinitionId("42");
    error.setProcessDefinitionName("Process Definition Name");
    error.setValidatorSetName("Validator Set Name");
    error.setWarning(true);
    error.setXmlColumnNumber(10);
    error.setXmlLineNumber(2);

    // Act
    validationErrorDecorator.decorate(error);

    // Assert
    verify(map, atLeast(1)).get(isA(Object.class));
    assertEquals("The characteristics of someone or something", error.getDefaultDescription());
    assertNull(error.getProblem());
  }

  /**
   * Test {@link ValidationErrorDecorator#decorate(ValidationError)}.
   *
   * <ul>
   *   <li>Then {@link ValidationError} (default constructor) Problem is empty string.
   * </ul>
   *
   * <p>Method under test: {@link ValidationErrorDecorator#decorate(ValidationError)}
   */
  @Test
  @DisplayName(
      "Test decorate(ValidationError); then ValidationError (default constructor) Problem is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValidationErrorDecorator.decorate(ValidationError)"})
  void testDecorate_thenValidationErrorProblemIsEmptyString() {
    // Arrange
    ErrorMessageDefinition errorMessageDefinition = new ErrorMessageDefinition();
    errorMessageDefinition.setProblem("");
    when(map.get(Mockito.<Object>any())).thenReturn(errorMessageDefinition);

    ValidationError error = new ValidationError();
    error.setActivityId("42");
    error.setActivityName("Activity Name");
    error.setDefaultDescription("Default Description");
    error.setKey("Key");
    error.setParams(new HashMap<>());
    error.setProblem("Problem");
    error.setProcessDefinitionId("42");
    error.setProcessDefinitionName("Process Definition Name");
    error.setValidatorSetName("Validator Set Name");
    error.setWarning(true);
    error.setXmlColumnNumber(10);
    error.setXmlLineNumber(2);

    // Act
    validationErrorDecorator.decorate(error);

    // Assert
    verify(map, atLeast(1)).get(isA(Object.class));
    assertEquals("", error.getProblem());
    assertNull(error.getDefaultDescription());
  }

  /**
   * Test {@link ValidationErrorDecorator#decorate(ValidationError)}.
   *
   * <ul>
   *   <li>Then {@link ValidationError} (default constructor) Problem is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ValidationErrorDecorator#decorate(ValidationError)}
   */
  @Test
  @DisplayName(
      "Test decorate(ValidationError); then ValidationError (default constructor) Problem is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValidationErrorDecorator.decorate(ValidationError)"})
  void testDecorate_thenValidationErrorProblemIsNull() {
    // Arrange
    when(map.get(Mockito.<Object>any())).thenReturn(new ErrorMessageDefinition());

    ValidationError error = new ValidationError();
    error.setActivityId("42");
    error.setActivityName("Activity Name");
    error.setDefaultDescription("Default Description");
    error.setKey("Key");
    error.setParams(new HashMap<>());
    error.setProblem("Problem");
    error.setProcessDefinitionId("42");
    error.setProcessDefinitionName("Process Definition Name");
    error.setValidatorSetName("Validator Set Name");
    error.setWarning(true);
    error.setXmlColumnNumber(10);
    error.setXmlLineNumber(2);

    // Act
    validationErrorDecorator.decorate(error);

    // Assert
    verify(map, atLeast(1)).get(isA(Object.class));
    assertNull(error.getDefaultDescription());
    assertNull(error.getProblem());
  }

  /**
   * Test {@link ValidationErrorDecorator#decorate(ValidationError)}.
   *
   * <ul>
   *   <li>Then {@link ValidationError} (default constructor) Problem is {@link
   *       ValidationErrorDecorator#PARAM_PREFIX}.
   * </ul>
   *
   * <p>Method under test: {@link ValidationErrorDecorator#decorate(ValidationError)}
   */
  @Test
  @DisplayName(
      "Test decorate(ValidationError); then ValidationError (default constructor) Problem is PARAM_PREFIX")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValidationErrorDecorator.decorate(ValidationError)"})
  void testDecorate_thenValidationErrorProblemIsParam_prefix() {
    // Arrange
    ErrorMessageDefinition errorMessageDefinition = new ErrorMessageDefinition();
    errorMessageDefinition.setProblem(ValidationErrorDecorator.PARAM_PREFIX);
    when(map.get(Mockito.<Object>any())).thenReturn(errorMessageDefinition);

    ValidationError error = new ValidationError();
    error.setActivityId("42");
    error.setActivityName("Activity Name");
    error.setDefaultDescription("Default Description");
    error.setKey("Key");
    error.setParams(new HashMap<>());
    error.setProblem("Problem");
    error.setProcessDefinitionId("42");
    error.setProcessDefinitionName("Process Definition Name");
    error.setValidatorSetName("Validator Set Name");
    error.setWarning(true);
    error.setXmlColumnNumber(10);
    error.setXmlLineNumber(2);

    // Act
    validationErrorDecorator.decorate(error);

    // Assert
    verify(map, atLeast(1)).get(isA(Object.class));
    assertNull(error.getDefaultDescription());
    assertEquals(ValidationErrorDecorator.PARAM_PREFIX, error.getProblem());
  }

  /**
   * Test {@link ValidationErrorDecorator#resolveMessage(String, Map)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  @DisplayName("Test resolveMessage(String, Map); when empty string; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ValidationErrorDecorator.resolveMessage(String, Map)"})
  void testResolveMessage_whenEmptyString_thenReturnEmptyString() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    // Act and Assert
    assertEquals("", validationErrorDecorator.resolveMessage("", new HashMap<>()));
  }

  /**
   * Test {@link ValidationErrorDecorator#resolveMessage(String, Map)}.
   *
   * <ul>
   *   <li>When {@code Not all who wander are lost}.
   *   <li>Then return {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  @DisplayName(
      "Test resolveMessage(String, Map); when 'Not all who wander are lost'; then return 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ValidationErrorDecorator.resolveMessage(String, Map)"})
  void testResolveMessage_whenNotAllWhoWanderAreLost_thenReturnNotAllWhoWanderAreLost() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    // Act and Assert
    assertEquals(
        "Not all who wander are lost",
        validationErrorDecorator.resolveMessage("Not all who wander are lost", new HashMap<>()));
  }

  /**
   * Test {@link ValidationErrorDecorator#resolveMessage(String, Map)}.
   *
   * <ul>
   *   <li>When {@link ValidationErrorDecorator#PARAM_PREFIX}.
   *   <li>Then return {@link ValidationErrorDecorator#PARAM_PREFIX}.
   * </ul>
   *
   * <p>Method under test: {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  @DisplayName("Test resolveMessage(String, Map); when PARAM_PREFIX; then return PARAM_PREFIX")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ValidationErrorDecorator.resolveMessage(String, Map)"})
  void testResolveMessage_whenParam_prefix_thenReturnParam_prefix() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    // Act and Assert
    assertEquals(
        ValidationErrorDecorator.PARAM_PREFIX,
        validationErrorDecorator.resolveMessage(
            ValidationErrorDecorator.PARAM_PREFIX, new HashMap<>()));
  }
}
