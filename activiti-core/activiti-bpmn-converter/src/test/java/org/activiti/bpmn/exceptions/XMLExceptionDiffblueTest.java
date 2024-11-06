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
package org.activiti.bpmn.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class XMLExceptionDiffblueTest {
  /**
   * Test {@link XMLException#XMLException(String)}.
   * <ul>
   *   <li>When {@code An error occurred}.</li>
   *   <li>Then return Cause is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLException#XMLException(String)}
   */
  @Test
  @DisplayName("Test new XMLException(String); when 'An error occurred'; then return Cause is 'null'")
  void testNewXMLException_whenAnErrorOccurred_thenReturnCauseIsNull() {
    // Arrange and Act
    XMLException actualXmlException = new XMLException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualXmlException.getMessage());
    assertNull(actualXmlException.getCause());
    assertEquals(0, actualXmlException.getSuppressed().length);
  }

  /**
   * Test {@link XMLException#XMLException(String, Throwable)}.
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.</li>
   *   <li>Then return Cause is {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLException#XMLException(String, Throwable)}
   */
  @Test
  @DisplayName("Test new XMLException(String, Throwable); when Throwable(); then return Cause is Throwable()")
  void testNewXMLException_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable t = new Throwable();

    // Act
    XMLException actualXmlException = new XMLException("An error occurred", t);

    // Assert
    assertEquals("An error occurred", actualXmlException.getMessage());
    assertEquals(0, actualXmlException.getSuppressed().length);
    assertSame(t, actualXmlException.getCause());
  }
}
