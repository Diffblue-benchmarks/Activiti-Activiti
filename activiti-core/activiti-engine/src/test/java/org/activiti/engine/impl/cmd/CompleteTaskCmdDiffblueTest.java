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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CompleteTaskCmdDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CompleteTaskCmd#CompleteTaskCmd(String, Map)}
   *   <li>{@link CompleteTaskCmd#getSuspendedTaskException()}
   *   <li>{@link CompleteTaskCmd#getTaskVariables()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenHashMap() {
    // Arrange and Act
    CompleteTaskCmd actualCompleteTaskCmd = new CompleteTaskCmd("42", new HashMap<>());
    String actualSuspendedTaskException = actualCompleteTaskCmd.getSuspendedTaskException();

    // Assert
    assertEquals("Cannot complete a suspended task", actualSuspendedTaskException);
    assertNull(actualCompleteTaskCmd.getTaskVariables());
    assertTrue(actualCompleteTaskCmd.variables.isEmpty());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@link CompleteTaskCmd#transientVariables} Empty.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CompleteTaskCmd#CompleteTaskCmd(String, Map, Map)}
   *   <li>{@link CompleteTaskCmd#getSuspendedTaskException()}
   *   <li>{@link CompleteTaskCmd#getTaskVariables()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenHashMap_thenReturnTransientVariablesEmpty() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    CompleteTaskCmd actualCompleteTaskCmd = new CompleteTaskCmd("42", variables, new HashMap<>());
    String actualSuspendedTaskException = actualCompleteTaskCmd.getSuspendedTaskException();

    // Assert
    assertEquals("Cannot complete a suspended task", actualSuspendedTaskException);
    assertNull(actualCompleteTaskCmd.getTaskVariables());
    assertTrue(actualCompleteTaskCmd.transientVariables.isEmpty());
    assertTrue(actualCompleteTaskCmd.variables.isEmpty());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CompleteTaskCmd#CompleteTaskCmd(String, Map, boolean)}
   *   <li>{@link CompleteTaskCmd#getSuspendedTaskException()}
   *   <li>{@link CompleteTaskCmd#getTaskVariables()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenTrue() {
    // Arrange and Act
    CompleteTaskCmd actualCompleteTaskCmd = new CompleteTaskCmd("42", new HashMap<>(), true);
    String actualSuspendedTaskException = actualCompleteTaskCmd.getSuspendedTaskException();

    // Assert
    assertEquals("Cannot complete a suspended task", actualSuspendedTaskException);
    assertNull(actualCompleteTaskCmd.getTaskVariables());
    assertTrue(actualCompleteTaskCmd.variables.isEmpty());
  }
}
