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
package org.activiti.image.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ActivitiImageExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link ActivitiImageException#ActivitiImageException(String)}
   */
  @Test
  public void testNewActivitiImageException() {
    // Arrange and Act
    ActivitiImageException actualActivitiImageException = new ActivitiImageException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualActivitiImageException.getMessage());
    assertNull(actualActivitiImageException.getCause());
    assertEquals(0, actualActivitiImageException.getSuppressed().length);
  }

  /**
   * Method under test:
   * {@link ActivitiImageException#ActivitiImageException(String, Throwable)}
   */
  @Test
  public void testNewActivitiImageException2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ActivitiImageException actualActivitiImageException = new ActivitiImageException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualActivitiImageException.getMessage());
    assertEquals(0, actualActivitiImageException.getSuppressed().length);
    assertSame(cause, actualActivitiImageException.getCause());
  }
}
