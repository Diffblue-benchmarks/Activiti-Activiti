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
package org.activiti.api.runtime.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnprocessableEntityExceptionDiffblueTest {
  /**
   * Test
   * {@link UnprocessableEntityException#UnprocessableEntityException(String)}.
   * <p>
   * Method under test:
   * {@link UnprocessableEntityException#UnprocessableEntityException(String)}
   */
  @Test
  @DisplayName("Test new UnprocessableEntityException(String)")
  void testNewUnprocessableEntityException() {
    // Arrange and Act
    UnprocessableEntityException actualUnprocessableEntityException = new UnprocessableEntityException(
        "An error occurred");

    // Assert
    assertEquals("An error occurred", actualUnprocessableEntityException.getMessage());
    assertNull(actualUnprocessableEntityException.getCause());
    assertEquals(0, actualUnprocessableEntityException.getSuppressed().length);
  }
}
