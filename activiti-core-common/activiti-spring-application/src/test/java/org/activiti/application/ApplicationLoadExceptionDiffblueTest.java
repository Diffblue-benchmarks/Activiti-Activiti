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
package org.activiti.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationLoadExceptionDiffblueTest {
  /**
   * Test
   * {@link ApplicationLoadException#ApplicationLoadException(String, Throwable)}.
   * <p>
   * Method under test:
   * {@link ApplicationLoadException#ApplicationLoadException(String, Throwable)}
   */
  @Test
  @DisplayName("Test new ApplicationLoadException(String, Throwable)")
  void testNewApplicationLoadException() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ApplicationLoadException actualApplicationLoadException = new ApplicationLoadException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualApplicationLoadException.getMessage());
    assertEquals(0, actualApplicationLoadException.getSuppressed().length);
    assertSame(cause, actualApplicationLoadException.getCause());
  }
}
