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
import org.activiti.engine.impl.bpmn.behavior.CopyVariablesCalculator;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.junit.Test;

public class TriggerCmdDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TriggerCmd#TriggerCmd(String, Map)}
   *   <li>{@link TriggerCmd#getSuspendedExceptionMessage()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    TriggerCmd actualTriggerCmd = new TriggerCmd("42", new HashMap<>());

    // Assert
    assertEquals("Cannot trigger an execution that is suspended", actualTriggerCmd.getSuspendedExceptionMessage());
    assertTrue(actualTriggerCmd.processVariables.isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TriggerCmd#TriggerCmd(String, Map, Map)}
   *   <li>{@link TriggerCmd#getSuspendedExceptionMessage()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters2() {
    // Arrange
    HashMap<String, Object> processVariables = new HashMap<>();

    // Act
    TriggerCmd actualTriggerCmd = new TriggerCmd("42", processVariables, new HashMap<>());

    // Assert
    assertEquals("Cannot trigger an execution that is suspended", actualTriggerCmd.getSuspendedExceptionMessage());
    assertTrue(actualTriggerCmd.processVariables.isEmpty());
    assertTrue(actualTriggerCmd.transientVariables.isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TriggerCmd#TriggerCmd(String, Map, VariablesPropagator)}
   *   <li>{@link TriggerCmd#getSuspendedExceptionMessage()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters3() {
    // Arrange
    HashMap<String, Object> availableVariables = new HashMap<>();

    // Act and Assert
    assertEquals("Cannot trigger an execution that is suspended",
        (new TriggerCmd("42", availableVariables, new VariablesPropagator(new CopyVariablesCalculator())))
            .getSuspendedExceptionMessage());
  }
}
