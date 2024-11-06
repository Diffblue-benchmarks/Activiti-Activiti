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
package org.activiti.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidationErrorDiffblueTest {
  /**
   * Test {@link ValidationError#toString()}.
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString()")
  void testToString() {
    // Arrange
    ValidationError validationError = new ValidationError();
    validationError.setDefaultDescription("Default Description");
    validationError.setProblem("Problem");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setProcessDefinitionId(null);
    validationError.setProcessDefinitionName(null);
    validationError.setActivityId(null);
    validationError.setActivityName(null);
    validationError.setXmlLineNumber(0);
    validationError.setXmlColumnNumber(0);
    validationError.setKey(null);
    validationError.setParams(null);

    // Act and Assert
    assertEquals("[Validation set: 'Validator Set Name' | Problem: 'Problem'] : Default Description - [Extra info : ]",
        validationError.toString());
  }

  /**
   * Test {@link ValidationError#toString()}.
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString()")
  void testToString2() {
    // Arrange
    ValidationError validationError = new ValidationError();
    validationError.setDefaultDescription("Default Description");
    validationError.setProblem("Problem");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setProcessDefinitionId(null);
    validationError.setProcessDefinitionName(null);
    validationError.setActivityId(null);
    validationError.setActivityName(null);
    validationError.setXmlLineNumber(1);
    validationError.setXmlColumnNumber(0);
    validationError.setKey(null);
    validationError.setParams(null);

    // Act and Assert
    assertEquals("[Validation set: 'Validator Set Name' | Problem: 'Problem'] : Default Description - [Extra info : ]",
        validationError.toString());
  }

  /**
   * Test {@link ValidationError#toString()}.
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString()")
  void testToString3() {
    // Arrange
    ValidationError validationError = new ValidationError();
    validationError.setProcessDefinitionId("42");

    // Act and Assert
    assertEquals("[Validation set: 'null' | Problem: 'null'] : null - [Extra info : processDefinitionId = 42]",
        validationError.toString());
  }

  /**
   * Test {@link ValidationError#toString()}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is
   * {@code foo}.</li>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString(); given ValidationError (default constructor) ActivityId is 'foo'; then return a string")
  void testToString_givenValidationErrorActivityIdIsFoo_thenReturnAString() {
    // Arrange
    ValidationError validationError = new ValidationError();
    validationError.setDefaultDescription("Default Description");
    validationError.setProblem("Problem");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setProcessDefinitionId(null);
    validationError.setProcessDefinitionName(null);
    validationError.setActivityId("foo");
    validationError.setActivityName(null);
    validationError.setXmlLineNumber(0);
    validationError.setXmlColumnNumber(0);
    validationError.setKey(null);
    validationError.setParams(null);

    // Act and Assert
    assertEquals("[Validation set: 'Validator Set Name' | Problem: 'Problem'] : Default Description - [Extra info : id"
        + " = foo | ]", validationError.toString());
  }

  /**
   * Test {@link ValidationError#toString()}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is
   * {@code foo}.</li>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString(); given ValidationError (default constructor) ActivityId is 'foo'; then return a string")
  void testToString_givenValidationErrorActivityIdIsFoo_thenReturnAString2() {
    // Arrange
    ValidationError validationError = new ValidationError();
    validationError.setDefaultDescription("Default Description");
    validationError.setProblem("Problem");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setProcessDefinitionId(null);
    validationError.setProcessDefinitionName(null);
    validationError.setActivityId("foo");
    validationError.setActivityName("foo");
    validationError.setXmlLineNumber(0);
    validationError.setXmlColumnNumber(0);
    validationError.setKey(null);
    validationError.setParams(null);

    // Act and Assert
    assertEquals("[Validation set: 'Validator Set Name' | Problem: 'Problem'] : Default Description - [Extra info : id"
        + " = foo |  | activityName = foo | ]", validationError.toString());
  }

  /**
   * Test {@link ValidationError#toString()}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityName is
   * {@code foo}.</li>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString(); given ValidationError (default constructor) ActivityName is 'foo'; then return a string")
  void testToString_givenValidationErrorActivityNameIsFoo_thenReturnAString() {
    // Arrange
    ValidationError validationError = new ValidationError();
    validationError.setDefaultDescription("Default Description");
    validationError.setProblem("Problem");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setProcessDefinitionId(null);
    validationError.setProcessDefinitionName(null);
    validationError.setActivityId(null);
    validationError.setActivityName("foo");
    validationError.setXmlLineNumber(0);
    validationError.setXmlColumnNumber(0);
    validationError.setKey(null);
    validationError.setParams(null);

    // Act and Assert
    assertEquals("[Validation set: 'Validator Set Name' | Problem: 'Problem'] : Default Description - [Extra info :"
        + " activityName = foo | ]", validationError.toString());
  }

  /**
   * Test {@link ValidationError#toString()}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) Key is
   * {@code foo}.</li>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString(); given ValidationError (default constructor) Key is 'foo'; then return a string")
  void testToString_givenValidationErrorKeyIsFoo_thenReturnAString() {
    // Arrange
    ValidationError validationError = new ValidationError();
    validationError.setDefaultDescription("Default Description");
    validationError.setProblem("Problem");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setProcessDefinitionId(null);
    validationError.setProcessDefinitionName(null);
    validationError.setActivityId(null);
    validationError.setActivityName(null);
    validationError.setXmlLineNumber(0);
    validationError.setXmlColumnNumber(0);
    validationError.setKey("foo");
    validationError.setParams(null);

    // Act and Assert
    assertEquals("[Validation set: 'Validator Set Name' | Problem: 'Problem'] : Default Description - [Extra info :  ("
        + " key: foo )]", validationError.toString());
  }

  /**
   * Test {@link ValidationError#toString()}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) Key is
   * {@code foo}.</li>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString(); given ValidationError (default constructor) Key is 'foo'; then return a string")
  void testToString_givenValidationErrorKeyIsFoo_thenReturnAString2() {
    // Arrange
    ValidationError validationError = new ValidationError();
    validationError.setDefaultDescription("Default Description");
    validationError.setProblem("Problem");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setProcessDefinitionId(null);
    validationError.setProcessDefinitionName(null);
    validationError.setActivityId(null);
    validationError.setActivityName("foo");
    validationError.setXmlLineNumber(0);
    validationError.setXmlColumnNumber(0);
    validationError.setKey("foo");
    validationError.setParams(null);

    // Act and Assert
    assertEquals("[Validation set: 'Validator Set Name' | Problem: 'Problem'] : Default Description - [Extra info :"
        + " activityName = foo |  |  ( key: foo )]", validationError.toString());
  }

  /**
   * Test {@link ValidationError#toString()}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ProcessDefinitionName
   * is {@code - [Extra info :}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString(); given ValidationError (default constructor) ProcessDefinitionName is '- [Extra info :'")
  void testToString_givenValidationErrorProcessDefinitionNameIsExtraInfo() {
    // Arrange
    ValidationError validationError = new ValidationError();
    validationError.setProcessDefinitionName(" - [Extra info : ");

    // Act and Assert
    assertEquals("[Validation set: 'null' | Problem: 'null'] : null - [Extra info : processDefinitionName =  - [Extra"
        + " info :  | ]", validationError.toString());
  }

  /**
   * Test {@link ValidationError#toString()}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) XmlColumnNumber is
   * one.</li>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString(); given ValidationError (default constructor) XmlColumnNumber is one; then return a string")
  void testToString_givenValidationErrorXmlColumnNumberIsOne_thenReturnAString() {
    // Arrange
    ValidationError validationError = new ValidationError();
    validationError.setDefaultDescription("Default Description");
    validationError.setProblem("Problem");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setProcessDefinitionId(null);
    validationError.setProcessDefinitionName(null);
    validationError.setActivityId(null);
    validationError.setActivityName(null);
    validationError.setXmlLineNumber(1);
    validationError.setXmlColumnNumber(1);
    validationError.setKey(null);
    validationError.setParams(null);

    // Act and Assert
    assertEquals("[Validation set: 'Validator Set Name' | Problem: 'Problem'] : Default Description - [Extra info :  ("
        + " line: 1, column: 1)]", validationError.toString());
  }

  /**
   * Test {@link ValidationError#toString()}.
   * <ul>
   *   <li>Then return
   * {@code [Validation set: 'null' | Problem: 'null'] : null - [Extra info : ]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidationError#toString()}
   */
  @Test
  @DisplayName("Test toString(); then return '[Validation set: 'null' | Problem: 'null'] : null - [Extra info : ]'")
  void testToString_thenReturnValidationSetNullProblemNullNullExtraInfo() {
    // Arrange, Act and Assert
    assertEquals("[Validation set: 'null' | Problem: 'null'] : null - [Extra info : ]",
        (new ValidationError()).toString());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ValidationError}
   *   <li>{@link ValidationError#setActivityId(String)}
   *   <li>{@link ValidationError#setActivityName(String)}
   *   <li>{@link ValidationError#setDefaultDescription(String)}
   *   <li>{@link ValidationError#setKey(String)}
   *   <li>{@link ValidationError#setParams(Map)}
   *   <li>{@link ValidationError#setProblem(String)}
   *   <li>{@link ValidationError#setProcessDefinitionId(String)}
   *   <li>{@link ValidationError#setProcessDefinitionName(String)}
   *   <li>{@link ValidationError#setValidatorSetName(String)}
   *   <li>{@link ValidationError#setWarning(boolean)}
   *   <li>{@link ValidationError#setXmlColumnNumber(int)}
   *   <li>{@link ValidationError#setXmlLineNumber(int)}
   *   <li>{@link ValidationError#getActivityId()}
   *   <li>{@link ValidationError#getActivityName()}
   *   <li>{@link ValidationError#getDefaultDescription()}
   *   <li>{@link ValidationError#getKey()}
   *   <li>{@link ValidationError#getParams()}
   *   <li>{@link ValidationError#getProblem()}
   *   <li>{@link ValidationError#getProcessDefinitionId()}
   *   <li>{@link ValidationError#getProcessDefinitionName()}
   *   <li>{@link ValidationError#getValidatorSetName()}
   *   <li>{@link ValidationError#getXmlColumnNumber()}
   *   <li>{@link ValidationError#getXmlLineNumber()}
   *   <li>{@link ValidationError#isWarning()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ValidationError actualValidationError = new ValidationError();
    actualValidationError.setActivityId("42");
    actualValidationError.setActivityName("Activity Name");
    actualValidationError.setDefaultDescription("Default Description");
    actualValidationError.setKey("Key");
    HashMap<String, String> params = new HashMap<>();
    actualValidationError.setParams(params);
    actualValidationError.setProblem("Problem");
    actualValidationError.setProcessDefinitionId("42");
    actualValidationError.setProcessDefinitionName("Process Definition Name");
    actualValidationError.setValidatorSetName("Validator Set Name");
    actualValidationError.setWarning(true);
    actualValidationError.setXmlColumnNumber(10);
    actualValidationError.setXmlLineNumber(2);
    String actualActivityId = actualValidationError.getActivityId();
    String actualActivityName = actualValidationError.getActivityName();
    String actualDefaultDescription = actualValidationError.getDefaultDescription();
    String actualKey = actualValidationError.getKey();
    Map<String, String> actualParams = actualValidationError.getParams();
    String actualProblem = actualValidationError.getProblem();
    String actualProcessDefinitionId = actualValidationError.getProcessDefinitionId();
    String actualProcessDefinitionName = actualValidationError.getProcessDefinitionName();
    String actualValidatorSetName = actualValidationError.getValidatorSetName();
    int actualXmlColumnNumber = actualValidationError.getXmlColumnNumber();
    int actualXmlLineNumber = actualValidationError.getXmlLineNumber();
    boolean actualIsWarningResult = actualValidationError.isWarning();

    // Assert that nothing has changed
    assertEquals("42", actualActivityId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("Activity Name", actualActivityName);
    assertEquals("Default Description", actualDefaultDescription);
    assertEquals("Key", actualKey);
    assertEquals("Problem", actualProblem);
    assertEquals("Process Definition Name", actualProcessDefinitionName);
    assertEquals("Validator Set Name", actualValidatorSetName);
    assertEquals(10, actualXmlColumnNumber);
    assertEquals(2, actualXmlLineNumber);
    assertTrue(actualParams.isEmpty());
    assertTrue(actualIsWarningResult);
    assertSame(params, actualParams);
  }
}
