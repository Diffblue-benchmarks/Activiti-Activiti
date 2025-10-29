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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

public class GetTaskDataObjectsCmdDiffblueTest {
  /**
   * Method under test:
   * {@link GetTaskDataObjectsCmd#GetTaskDataObjectsCmd(String, Collection)}
   */
  @Test
  public void testNewGetTaskDataObjectsCmd() {
    // Arrange and Act
    GetTaskDataObjectsCmd actualGetTaskDataObjectsCmd = new GetTaskDataObjectsCmd("42", new ArrayList<>());

    // Assert
    Collection<String> collection = actualGetTaskDataObjectsCmd.variableNames;
    assertTrue(collection instanceof List);
    assertEquals("42", actualGetTaskDataObjectsCmd.taskId);
    assertNull(actualGetTaskDataObjectsCmd.locale);
    assertFalse(actualGetTaskDataObjectsCmd.withLocalizationFallback);
    assertTrue(collection.isEmpty());
  }

  /**
   * Method under test:
   * {@link GetTaskDataObjectsCmd#GetTaskDataObjectsCmd(String, Collection, String, boolean)}
   */
  @Test
  public void testNewGetTaskDataObjectsCmd2() {
    // Arrange and Act
    GetTaskDataObjectsCmd actualGetTaskDataObjectsCmd = new GetTaskDataObjectsCmd("42", new ArrayList<>(), "en", true);

    // Assert
    Collection<String> collection = actualGetTaskDataObjectsCmd.variableNames;
    assertTrue(collection instanceof List);
    assertEquals("42", actualGetTaskDataObjectsCmd.taskId);
    assertEquals("en", actualGetTaskDataObjectsCmd.locale);
    assertTrue(collection.isEmpty());
    assertTrue(actualGetTaskDataObjectsCmd.withLocalizationFallback);
  }
}
