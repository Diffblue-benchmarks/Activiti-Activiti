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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionService;

class ProcessVariablesMapSerializerDiffblueTest {
  /**
   * Test
   * {@link ProcessVariablesMapSerializer#ProcessVariablesMapSerializer(ConversionService)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesMapSerializer#ProcessVariablesMapSerializer(ConversionService)}
   */
  @Test
  @DisplayName("Test new ProcessVariablesMapSerializer(ConversionService)")
  void testNewProcessVariablesMapSerializer() {
    // Arrange and Act
    ProcessVariablesMapSerializer actualProcessVariablesMapSerializer = new ProcessVariablesMapSerializer(
        new ApplicationConversionService());

    // Assert
    assertNull(actualProcessVariablesMapSerializer.getDelegatee());
    assertFalse(actualProcessVariablesMapSerializer.isUnwrappingSerializer());
  }
}
