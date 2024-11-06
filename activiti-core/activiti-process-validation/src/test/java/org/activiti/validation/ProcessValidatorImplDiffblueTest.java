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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.validation.validator.Validator;
import org.activiti.validation.validator.ValidatorSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessValidatorImplDiffblueTest {
  /**
   * Test new {@link ProcessValidatorImpl} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ProcessValidatorImpl}
   */
  @Test
  @DisplayName("Test new ProcessValidatorImpl (default constructor)")
  void testNewProcessValidatorImpl() {
    // Arrange, Act and Assert
    assertNull((new ProcessValidatorImpl()).getValidatorSets());
  }

  /**
   * Test {@link ProcessValidatorImpl#validate(BpmnModel)}.
   * <ul>
   *   <li>Given {@link ProcessValidatorImpl} (default constructor) ValidatorSets is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessValidatorImpl#validate(BpmnModel)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel); given ProcessValidatorImpl (default constructor) ValidatorSets is ArrayList(); then return Empty")
  void testValidate_givenProcessValidatorImplValidatorSetsIsArrayList_thenReturnEmpty() {
    // Arrange
    ProcessValidatorImpl processValidatorImpl = new ProcessValidatorImpl();
    processValidatorImpl.setValidatorSets(new ArrayList<>());

    // Act and Assert
    assertTrue(processValidatorImpl.validate(new BpmnModel()).isEmpty());
  }

  /**
   * Test {@link ProcessValidatorImpl#validate(BpmnModel)}.
   * <ul>
   *   <li>Given {@link Validator} {@link Validator#validate(BpmnModel, List)} does
   * nothing.</li>
   *   <li>Then calls {@link Validator#validate(BpmnModel, List)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessValidatorImpl#validate(BpmnModel)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel); given Validator validate(BpmnModel, List) does nothing; then calls validate(BpmnModel, List)")
  void testValidate_givenValidatorValidateDoesNothing_thenCallsValidate() {
    // Arrange
    Validator validator = mock(Validator.class);
    doNothing().when(validator).validate(Mockito.<BpmnModel>any(), Mockito.<List<ValidationError>>any());

    ValidatorSet validatorSet = new ValidatorSet("42");
    validatorSet.addValidator(mock(Validator.class));
    validatorSet.addValidator(validator);

    ProcessValidatorImpl processValidatorImpl = new ProcessValidatorImpl();
    processValidatorImpl.addValidatorSet(validatorSet);

    // Act
    List<ValidationError> actualValidateResult = processValidatorImpl.validate(new BpmnModel());

    // Assert
    verify(validator).validate(isA(BpmnModel.class), isA(List.class));
    assertTrue(actualValidateResult.isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessValidatorImpl#setValidatorSets(List)}
   *   <li>{@link ProcessValidatorImpl#getValidatorSets()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ProcessValidatorImpl processValidatorImpl = new ProcessValidatorImpl();
    ArrayList<ValidatorSet> validatorSets = new ArrayList<>();

    // Act
    processValidatorImpl.setValidatorSets(validatorSets);
    List<ValidatorSet> actualValidatorSets = processValidatorImpl.getValidatorSets();

    // Assert that nothing has changed
    assertTrue(actualValidatorSets.isEmpty());
    assertSame(validatorSets, actualValidatorSets);
  }

  /**
   * Test {@link ProcessValidatorImpl#addValidatorSet(ValidatorSet)}.
   * <ul>
   *   <li>Given {@link ProcessValidatorImpl} (default constructor) ValidatorSets is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessValidatorImpl#addValidatorSet(ValidatorSet)}
   */
  @Test
  @DisplayName("Test addValidatorSet(ValidatorSet); given ProcessValidatorImpl (default constructor) ValidatorSets is 'null'")
  void testAddValidatorSet_givenProcessValidatorImplValidatorSetsIsNull() {
    // Arrange
    ProcessValidatorImpl processValidatorImpl = new ProcessValidatorImpl();
    processValidatorImpl.setValidatorSets(null);
    ValidatorSet validatorSet = new ValidatorSet("Name");

    // Act
    processValidatorImpl.addValidatorSet(validatorSet);

    // Assert
    List<ValidatorSet> validatorSets = processValidatorImpl.getValidatorSets();
    assertEquals(1, validatorSets.size());
    assertSame(validatorSet, validatorSets.get(0));
  }

  /**
   * Test {@link ProcessValidatorImpl#addValidatorSet(ValidatorSet)}.
   * <ul>
   *   <li>Given {@link Validator}.</li>
   *   <li>When {@link ValidatorSet#ValidatorSet(String)} with {@code Name}
   * addValidator {@link Validator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessValidatorImpl#addValidatorSet(ValidatorSet)}
   */
  @Test
  @DisplayName("Test addValidatorSet(ValidatorSet); given Validator; when ValidatorSet(String) with 'Name' addValidator Validator")
  void testAddValidatorSet_givenValidator_whenValidatorSetWithNameAddValidatorValidator() {
    // Arrange
    ProcessValidatorImpl processValidatorImpl = new ProcessValidatorImpl();

    ValidatorSet validatorSet = new ValidatorSet("Name");
    validatorSet.addValidator(mock(Validator.class));

    // Act
    processValidatorImpl.addValidatorSet(validatorSet);

    // Assert
    List<ValidatorSet> validatorSets = processValidatorImpl.getValidatorSets();
    assertEquals(1, validatorSets.size());
    assertSame(validatorSet, validatorSets.get(0));
  }

  /**
   * Test {@link ProcessValidatorImpl#addValidatorSet(ValidatorSet)}.
   * <ul>
   *   <li>Then {@link ProcessValidatorImpl} (default constructor) ValidatorSets is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessValidatorImpl#addValidatorSet(ValidatorSet)}
   */
  @Test
  @DisplayName("Test addValidatorSet(ValidatorSet); then ProcessValidatorImpl (default constructor) ValidatorSets is ArrayList()")
  void testAddValidatorSet_thenProcessValidatorImplValidatorSetsIsArrayList() {
    // Arrange
    ProcessValidatorImpl processValidatorImpl = new ProcessValidatorImpl();
    ArrayList<ValidatorSet> validatorSets = new ArrayList<>();
    processValidatorImpl.setValidatorSets(validatorSets);
    ValidatorSet validatorSet = new ValidatorSet("Name");

    // Act
    processValidatorImpl.addValidatorSet(validatorSet);

    // Assert
    List<ValidatorSet> validatorSets2 = processValidatorImpl.getValidatorSets();
    assertEquals(1, validatorSets2.size());
    assertSame(validatorSets, validatorSets2);
    assertSame(validatorSet, validatorSets2.get(0));
  }
}
