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
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationEntryDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ApplicationEntry#ApplicationEntry(String, FileContent)}
   *   <li>{@link ApplicationEntry#getFileContent()}
   *   <li>{@link ApplicationEntry#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() throws UnsupportedEncodingException {
    // Arrange
    FileContent fileContent = new FileContent("Name", "AXAXAXAX".getBytes("UTF-8"));

    // Act
    ApplicationEntry actualApplicationEntry = new ApplicationEntry("Type", fileContent);
    FileContent actualFileContent = actualApplicationEntry.getFileContent();

    // Assert
    assertEquals("Type", actualApplicationEntry.getType());
    assertSame(fileContent, actualFileContent);
  }
}
