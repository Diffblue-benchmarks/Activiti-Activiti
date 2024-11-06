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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ErrorDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Error#Error(String, String, String)}
   *   <li>{@link Error#setErrorCode(String)}
   *   <li>{@link Error#setId(String)}
   *   <li>{@link Error#setName(String)}
   *   <li>{@link Error#getErrorCode()}
   *   <li>{@link Error#getId()}
   *   <li>{@link Error#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Error actualError = new Error("42", "Name", "An error occurred");
    actualError.setErrorCode("An error occurred");
    actualError.setId("42");
    actualError.setName("Name");
    String actualErrorCode = actualError.getErrorCode();
    String actualId = actualError.getId();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("An error occurred", actualErrorCode);
    assertEquals("Name", actualError.getName());
  }
}
