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
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidationErrorDecoratorDiffblueTest {
  /**
   * Test new {@link ValidationErrorDecorator} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ValidationErrorDecorator}
   */
  @Test
  @DisplayName("Test new ValidationErrorDecorator (default constructor)")
  void testNewValidationErrorDecorator() {
    // Arrange, Act and Assert
    assertEquals("Not all who wander are lost",
        (new ValidationErrorDecorator()).resolveMessage("Not all who wander are lost", null));
  }

  /**
   * Test {@link ValidationErrorDecorator#resolveMessage(String, Map)}.
   * <ul>
   *   <li>Given {@link ValidationErrorDecorator#PARAM_PREFIX}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  @DisplayName("Test resolveMessage(String, Map); given PARAM_PREFIX")
  void testResolveMessage_givenParam_prefix() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    HashMap<String, String> params = new HashMap<>();
    params.computeIfPresent(ValidationErrorDecorator.PARAM_PREFIX, mock(BiFunction.class));

    // Act and Assert
    assertEquals("Not all who wander are lost",
        validationErrorDecorator.resolveMessage("Not all who wander are lost", params));
  }

  /**
   * Test {@link ValidationErrorDecorator#resolveMessage(String, Map)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  @DisplayName("Test resolveMessage(String, Map); when empty string; then return empty string")
  void testResolveMessage_whenEmptyString_thenReturnEmptyString() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    // Act and Assert
    assertEquals("", validationErrorDecorator.resolveMessage("", new HashMap<>()));
  }

  /**
   * Test {@link ValidationErrorDecorator#resolveMessage(String, Map)}.
   * <ul>
   *   <li>When {@code Not all who wander are lost}.</li>
   *   <li>Then return {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  @DisplayName("Test resolveMessage(String, Map); when 'Not all who wander are lost'; then return 'Not all who wander are lost'")
  void testResolveMessage_whenNotAllWhoWanderAreLost_thenReturnNotAllWhoWanderAreLost() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    // Act and Assert
    assertEquals("Not all who wander are lost",
        validationErrorDecorator.resolveMessage("Not all who wander are lost", new HashMap<>()));
  }

  /**
   * Test {@link ValidationErrorDecorator#resolveMessage(String, Map)}.
   * <ul>
   *   <li>When {@link ValidationErrorDecorator#PARAM_PREFIX}.</li>
   *   <li>Then return {@link ValidationErrorDecorator#PARAM_PREFIX}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  @DisplayName("Test resolveMessage(String, Map); when PARAM_PREFIX; then return PARAM_PREFIX")
  void testResolveMessage_whenParam_prefix_thenReturnParam_prefix() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    // Act and Assert
    assertEquals(ValidationErrorDecorator.PARAM_PREFIX,
        validationErrorDecorator.resolveMessage(ValidationErrorDecorator.PARAM_PREFIX, new HashMap<>()));
  }
}
