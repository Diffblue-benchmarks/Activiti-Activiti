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
package org.activiti.core.common.spring.security.policies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivitiForbiddenExceptionDiffblueTest {
  /**
   * Test {@link ActivitiForbiddenException#ActivitiForbiddenException(String)}.
   * <ul>
   *   <li>When {@code An error occurred}.</li>
   *   <li>Then return Cause is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiForbiddenException#ActivitiForbiddenException(String)}
   */
  @Test
  @DisplayName("Test new ActivitiForbiddenException(String); when 'An error occurred'; then return Cause is 'null'")
  void testNewActivitiForbiddenException_whenAnErrorOccurred_thenReturnCauseIsNull() {
    // Arrange and Act
    ActivitiForbiddenException actualActivitiForbiddenException = new ActivitiForbiddenException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualActivitiForbiddenException.getMessage());
    assertNull(actualActivitiForbiddenException.getCause());
    assertEquals(0, actualActivitiForbiddenException.getSuppressed().length);
  }

  /**
   * Test
   * {@link ActivitiForbiddenException#ActivitiForbiddenException(String, Throwable)}.
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.</li>
   *   <li>Then return Cause is {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiForbiddenException#ActivitiForbiddenException(String, Throwable)}
   */
  @Test
  @DisplayName("Test new ActivitiForbiddenException(String, Throwable); when Throwable(); then return Cause is Throwable()")
  void testNewActivitiForbiddenException_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ActivitiForbiddenException actualActivitiForbiddenException = new ActivitiForbiddenException("An error occurred",
        cause);

    // Assert
    assertEquals("An error occurred", actualActivitiForbiddenException.getMessage());
    assertEquals(0, actualActivitiForbiddenException.getSuppressed().length);
    assertSame(cause, actualActivitiForbiddenException.getCause());
  }
}
