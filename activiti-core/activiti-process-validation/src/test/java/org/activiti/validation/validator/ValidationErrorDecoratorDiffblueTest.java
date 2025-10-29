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
import org.junit.jupiter.api.Test;

class ValidationErrorDecoratorDiffblueTest {
  /**
   * Method under test: default or parameterless constructor of
   * {@link ValidationErrorDecorator}
   */
  @Test
  void testNewValidationErrorDecorator() {
    // Arrange, Act and Assert
    assertEquals("Not all who wander are lost",
        (new ValidationErrorDecorator()).resolveMessage("Not all who wander are lost", null));
  }

  /**
   * Method under test:
   * {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  void testResolveMessage() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    // Act and Assert
    assertEquals("Not all who wander are lost",
        validationErrorDecorator.resolveMessage("Not all who wander are lost", new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  void testResolveMessage2() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    // Act and Assert
    assertEquals(ValidationErrorDecorator.PARAM_PREFIX,
        validationErrorDecorator.resolveMessage(ValidationErrorDecorator.PARAM_PREFIX, new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  void testResolveMessage3() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    // Act and Assert
    assertEquals("", validationErrorDecorator.resolveMessage("", new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link ValidationErrorDecorator#resolveMessage(String, Map)}
   */
  @Test
  void testResolveMessage4() {
    // Arrange
    ValidationErrorDecorator validationErrorDecorator = new ValidationErrorDecorator();

    HashMap<String, String> params = new HashMap<>();
    params.computeIfPresent(ValidationErrorDecorator.PARAM_PREFIX, mock(BiFunction.class));

    // Act and Assert
    assertEquals("Not all who wander are lost",
        validationErrorDecorator.resolveMessage("Not all who wander are lost", params));
  }
}
