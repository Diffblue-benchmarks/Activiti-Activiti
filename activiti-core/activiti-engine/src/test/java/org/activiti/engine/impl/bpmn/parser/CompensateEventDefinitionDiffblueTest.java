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
package org.activiti.engine.impl.bpmn.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CompensateEventDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CompensateEventDefinition}
   *   <li>{@link CompensateEventDefinition#setActivityRef(String)}
   *   <li>{@link CompensateEventDefinition#setWaitForCompletion(boolean)}
   *   <li>{@link CompensateEventDefinition#getActivityRef()}
   *   <li>{@link CompensateEventDefinition#isWaitForCompletion()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    CompensateEventDefinition actualCompensateEventDefinition = new CompensateEventDefinition();
    actualCompensateEventDefinition.setActivityRef("Activity Ref");
    actualCompensateEventDefinition.setWaitForCompletion(true);
    String actualActivityRef = actualCompensateEventDefinition.getActivityRef();

    // Assert that nothing has changed
    assertEquals("Activity Ref", actualActivityRef);
    assertTrue(actualCompensateEventDefinition.isWaitForCompletion());
  }
}
