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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitiImageExceptionDiffblueTest {
  /**
   * Test {@link ActivitiImageException#ActivitiImageException(String)}.
   *
   * <ul>
   *   <li>When {@code An error occurred}.
   *   <li>Then return Cause is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiImageException#ActivitiImageException(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiImageException.<init>(String)",
    "void ActivitiImageException.<init>(String, Throwable)"
  })
  public void testNewActivitiImageException_whenAnErrorOccurred_thenReturnCauseIsNull() {
    // Arrange and Act
    ActivitiImageException actualActivitiImageException =
        new ActivitiImageException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualActivitiImageException.getMessage());
    assertNull(actualActivitiImageException.getCause());
    assertEquals(0, actualActivitiImageException.getSuppressed().length);
  }

  /**
   * Test {@link ActivitiImageException#ActivitiImageException(String, Throwable)}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   *   <li>Then return Cause is {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiImageException#ActivitiImageException(String, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiImageException.<init>(String)",
    "void ActivitiImageException.<init>(String, Throwable)"
  })
  public void testNewActivitiImageException_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ActivitiImageException actualActivitiImageException =
        new ActivitiImageException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualActivitiImageException.getMessage());
    assertEquals(0, actualActivitiImageException.getSuppressed().length);
    assertSame(cause, actualActivitiImageException.getCause());
  }
}
