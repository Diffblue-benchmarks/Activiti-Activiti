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
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.activiti.validation.validator.ValidatorSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessValidatorFactoryDiffblueTest {
  /**
   * Test {@link ProcessValidatorFactory#createDefaultProcessValidator()}.
   * <p>
   * Method under test:
   * {@link ProcessValidatorFactory#createDefaultProcessValidator()}
   */
  @Test
  @DisplayName("Test createDefaultProcessValidator()")
  void testCreateDefaultProcessValidator() {
    // Arrange and Act
    ProcessValidator actualCreateDefaultProcessValidatorResult = (new ProcessValidatorFactory())
        .createDefaultProcessValidator();

    // Assert
    assertTrue(actualCreateDefaultProcessValidatorResult instanceof ProcessValidatorImpl);
    List<ValidatorSet> validatorSets = actualCreateDefaultProcessValidatorResult.getValidatorSets();
    assertEquals(1, validatorSets.size());
    ValidatorSet getResult = validatorSets.get(0);
    assertEquals("activiti-executable-process", getResult.getName());
    assertEquals(26, getResult.getValidators().size());
  }
}
