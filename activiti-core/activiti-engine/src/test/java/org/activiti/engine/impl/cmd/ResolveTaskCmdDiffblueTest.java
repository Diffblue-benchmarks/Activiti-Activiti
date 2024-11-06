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
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ResolveTaskCmdDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ResolveTaskCmd#ResolveTaskCmd(String, Map)}
   *   <li>{@link ResolveTaskCmd#getSuspendedTaskException()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ResolveTaskCmd actualResolveTaskCmd = new ResolveTaskCmd("42", new HashMap<>());

    // Assert
    assertEquals("Cannot resolve a suspended task", actualResolveTaskCmd.getSuspendedTaskException());
    assertTrue(actualResolveTaskCmd.variables.isEmpty());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return {@link ResolveTaskCmd#transientVariables} Empty.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ResolveTaskCmd#ResolveTaskCmd(String, Map, Map)}
   *   <li>{@link ResolveTaskCmd#getSuspendedTaskException()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_thenReturnTransientVariablesEmpty() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    ResolveTaskCmd actualResolveTaskCmd = new ResolveTaskCmd("42", variables, new HashMap<>());

    // Assert
    assertEquals("Cannot resolve a suspended task", actualResolveTaskCmd.getSuspendedTaskException());
    assertTrue(actualResolveTaskCmd.transientVariables.isEmpty());
    assertTrue(actualResolveTaskCmd.variables.isEmpty());
  }
}
