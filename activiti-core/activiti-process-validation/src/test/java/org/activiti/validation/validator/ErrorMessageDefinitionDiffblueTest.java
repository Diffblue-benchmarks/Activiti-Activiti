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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorMessageDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ErrorMessageDefinition}
   *   <li>{@link ErrorMessageDefinition#setDescription(String)}
   *   <li>{@link ErrorMessageDefinition#setProblem(String)}
   *   <li>{@link ErrorMessageDefinition#getDescription()}
   *   <li>{@link ErrorMessageDefinition#getProblem()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ErrorMessageDefinition actualErrorMessageDefinition = new ErrorMessageDefinition();
    actualErrorMessageDefinition.setDescription("The characteristics of someone or something");
    actualErrorMessageDefinition.setProblem("Problem");
    String actualDescription = actualErrorMessageDefinition.getDescription();

    // Assert that nothing has changed
    assertEquals("Problem", actualErrorMessageDefinition.getProblem());
    assertEquals("The characteristics of someone or something", actualDescription);
  }
}