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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivitiWrongDbExceptionDiffblueTest {
  @InjectMocks
  private ActivitiWrongDbException activitiWrongDbException;

  @InjectMocks
  private String string;

  /**
   * Test
   * {@link ActivitiWrongDbException#ActivitiWrongDbException(String, String)}.
   * <p>
   * Method under test:
   * {@link ActivitiWrongDbException#ActivitiWrongDbException(String, String)}
   */
  @Test
  public void testNewActivitiWrongDbException() {
    // Arrange and Act
    ActivitiWrongDbException actualActivitiWrongDbException = new ActivitiWrongDbException("1.0.2", "1.0.2");

    // Assert
    assertEquals("1.0.2", actualActivitiWrongDbException.getDbVersion());
    assertEquals("1.0.2", actualActivitiWrongDbException.getLibraryVersion());
    assertEquals("version mismatch: activiti library version is '1.0.2', db version is 1.0.2 Hint: Set <property"
        + " name=\"databaseSchemaUpdate\" to value=\"true\" or value=\"create-drop\" (use create-drop for testing only!)"
        + " in bean processEngineConfiguration in activiti.cfg.xml for automatic schema creation",
        actualActivitiWrongDbException.getLocalizedMessage());
    assertEquals("version mismatch: activiti library version is '1.0.2', db version is 1.0.2 Hint: Set <property"
        + " name=\"databaseSchemaUpdate\" to value=\"true\" or value=\"create-drop\" (use create-drop for testing only!)"
        + " in bean processEngineConfiguration in activiti.cfg.xml for automatic schema creation",
        actualActivitiWrongDbException.getMessage());
    assertNull(actualActivitiWrongDbException.getCause());
    assertEquals(0, actualActivitiWrongDbException.getSuppressed().length);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiWrongDbException#getDbVersion()}
   *   <li>{@link ActivitiWrongDbException#getLibraryVersion()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiWrongDbException activitiWrongDbException = new ActivitiWrongDbException("1.0.2", "1.0.2");

    // Act
    String actualDbVersion = activitiWrongDbException.getDbVersion();

    // Assert
    assertEquals("1.0.2", actualDbVersion);
    assertEquals("1.0.2", activitiWrongDbException.getLibraryVersion());
  }
}
