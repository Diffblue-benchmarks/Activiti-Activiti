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
package org.activiti.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ActivitiIllegalArgumentExceptionDiffblueTest {
  /**
   * Test
   * {@link ActivitiIllegalArgumentException#ActivitiIllegalArgumentException(String)}.
   * <ul>
   *   <li>Then return Cause is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiIllegalArgumentException#ActivitiIllegalArgumentException(String)}
   */
  @Test
  public void testNewActivitiIllegalArgumentException_thenReturnCauseIsNull() {
    // Arrange and Act
    ActivitiIllegalArgumentException actualActivitiIllegalArgumentException = new ActivitiIllegalArgumentException(
        "An error occurred");

    // Assert
    assertEquals("An error occurred", actualActivitiIllegalArgumentException.getMessage());
    assertNull(actualActivitiIllegalArgumentException.getCause());
    assertEquals(0, actualActivitiIllegalArgumentException.getSuppressed().length);
  }

  /**
   * Test
   * {@link ActivitiIllegalArgumentException#ActivitiIllegalArgumentException(String, Throwable)}.
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.</li>
   *   <li>Then return Cause is {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiIllegalArgumentException#ActivitiIllegalArgumentException(String, Throwable)}
   */
  @Test
  public void testNewActivitiIllegalArgumentException_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ActivitiIllegalArgumentException actualActivitiIllegalArgumentException = new ActivitiIllegalArgumentException(
        "An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualActivitiIllegalArgumentException.getMessage());
    assertEquals(0, actualActivitiIllegalArgumentException.getSuppressed().length);
    assertSame(cause, actualActivitiIllegalArgumentException.getCause());
  }
}
