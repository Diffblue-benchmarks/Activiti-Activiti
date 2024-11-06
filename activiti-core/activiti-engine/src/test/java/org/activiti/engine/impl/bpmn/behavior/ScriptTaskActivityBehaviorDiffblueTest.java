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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ScriptTaskActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link ScriptTaskActivityBehavior#ScriptTaskActivityBehavior(String, String, String)}.
   * <p>
   * Method under test:
   * {@link ScriptTaskActivityBehavior#ScriptTaskActivityBehavior(String, String, String)}
   */
  @Test
  public void testNewScriptTaskActivityBehavior() {
    // Arrange and Act
    ScriptTaskActivityBehavior actualScriptTaskActivityBehavior = new ScriptTaskActivityBehavior("Script", "en",
        "Result Variable");

    // Assert
    assertEquals("Result Variable", actualScriptTaskActivityBehavior.resultVariable);
    assertEquals("Script", actualScriptTaskActivityBehavior.script);
    assertEquals("en", actualScriptTaskActivityBehavior.language);
    assertNull(actualScriptTaskActivityBehavior.scriptTaskId);
    assertNull(actualScriptTaskActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualScriptTaskActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualScriptTaskActivityBehavior.hasMultiInstanceCharacteristics());
    assertFalse(actualScriptTaskActivityBehavior.storeScriptVariables);
  }

  /**
   * Test
   * {@link ScriptTaskActivityBehavior#ScriptTaskActivityBehavior(String, String, String, String, boolean)}.
   * <p>
   * Method under test:
   * {@link ScriptTaskActivityBehavior#ScriptTaskActivityBehavior(String, String, String, String, boolean)}
   */
  @Test
  public void testNewScriptTaskActivityBehavior2() {
    // Arrange and Act
    ScriptTaskActivityBehavior actualScriptTaskActivityBehavior = new ScriptTaskActivityBehavior("42", "Script", "en",
        "Result Variable", true);

    // Assert
    assertEquals("42", actualScriptTaskActivityBehavior.scriptTaskId);
    assertEquals("Result Variable", actualScriptTaskActivityBehavior.resultVariable);
    assertEquals("Script", actualScriptTaskActivityBehavior.script);
    assertEquals("en", actualScriptTaskActivityBehavior.language);
    assertNull(actualScriptTaskActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualScriptTaskActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualScriptTaskActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(actualScriptTaskActivityBehavior.storeScriptVariables);
  }
}
