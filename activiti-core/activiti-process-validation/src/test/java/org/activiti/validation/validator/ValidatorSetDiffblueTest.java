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
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidatorSetDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ValidatorSet#ValidatorSet(String)}
   *   <li>{@link ValidatorSet#setName(String)}
   *   <li>{@link ValidatorSet#getName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ValidatorSet actualValidatorSet = new ValidatorSet("Name");
    actualValidatorSet.setName("Name");

    // Assert that nothing has changed
    assertEquals("Name", actualValidatorSet.getName());
  }

  /**
   * Test {@link ValidatorSet#getValidators()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorSet#getValidators()}
   */
  @Test
  @DisplayName("Test getValidators(); then return size is one")
  void testGetValidators_thenReturnSizeIsOne() {
    // Arrange
    ValidatorSet validatorSet = new ValidatorSet("Name");
    validatorSet.addValidator(mock(Validator.class));

    // Act and Assert
    assertEquals(1, validatorSet.getValidators().size());
  }
}
