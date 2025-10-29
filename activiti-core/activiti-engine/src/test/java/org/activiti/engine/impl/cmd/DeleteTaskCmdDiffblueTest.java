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

public class DeleteTaskCmdDiffblueTest {
  /**
   * Method under test:
   * {@link DeleteTaskCmd#DeleteTaskCmd(String, String, boolean)}
   */
  @Test
  public void testNewDeleteTaskCmd() {
    // Arrange and Act
    DeleteTaskCmd actualDeleteTaskCmd = new DeleteTaskCmd("42", "Just cause", true);

    // Assert
    assertEquals("42", actualDeleteTaskCmd.taskId);
    assertEquals("Just cause", actualDeleteTaskCmd.deleteReason);
    assertNull(actualDeleteTaskCmd.taskIds);
    assertFalse(actualDeleteTaskCmd.cancel);
    assertTrue(actualDeleteTaskCmd.cascade);
  }

  /**
   * Method under test:
   * {@link DeleteTaskCmd#DeleteTaskCmd(String, String, boolean, boolean)}
   */
  @Test
  public void testNewDeleteTaskCmd2() {
    // Arrange and Act
    DeleteTaskCmd actualDeleteTaskCmd = new DeleteTaskCmd("42", "Just cause", true, true);

    // Assert
    assertEquals("42", actualDeleteTaskCmd.taskId);
    assertEquals("Just cause", actualDeleteTaskCmd.deleteReason);
    assertNull(actualDeleteTaskCmd.taskIds);
    assertTrue(actualDeleteTaskCmd.cancel);
    assertTrue(actualDeleteTaskCmd.cascade);
  }

  /**
   * Method under test:
   * {@link DeleteTaskCmd#DeleteTaskCmd(Collection, String, boolean)}
   */
  @Test
  public void testNewDeleteTaskCmd3() {
    // Arrange and Act
    DeleteTaskCmd actualDeleteTaskCmd = new DeleteTaskCmd(new ArrayList<>(), "Just cause", true);

    // Assert
    Collection<String> collection = actualDeleteTaskCmd.taskIds;
    assertTrue(collection instanceof List);
    assertEquals("Just cause", actualDeleteTaskCmd.deleteReason);
    assertNull(actualDeleteTaskCmd.taskId);
    assertFalse(actualDeleteTaskCmd.cancel);
    assertTrue(collection.isEmpty());
    assertTrue(actualDeleteTaskCmd.cascade);
  }

  /**
   * Method under test:
   * {@link DeleteTaskCmd#DeleteTaskCmd(Collection, String, boolean, boolean)}
   */
  @Test
  public void testNewDeleteTaskCmd4() {
    // Arrange and Act
    DeleteTaskCmd actualDeleteTaskCmd = new DeleteTaskCmd(new ArrayList<>(), "Just cause", true, true);

    // Assert
    Collection<String> collection = actualDeleteTaskCmd.taskIds;
    assertTrue(collection instanceof List);
    assertEquals("Just cause", actualDeleteTaskCmd.deleteReason);
    assertNull(actualDeleteTaskCmd.taskId);
    assertTrue(collection.isEmpty());
    assertTrue(actualDeleteTaskCmd.cancel);
    assertTrue(actualDeleteTaskCmd.cascade);
  }
}
