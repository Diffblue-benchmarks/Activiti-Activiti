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
package org.activiti.engine.impl.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class NoOpServiceTaskDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link NoOpServiceTask}
   *   <li>{@link NoOpServiceTask#setName(Expression)}
   *   <li>{@link NoOpServiceTask#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    NoOpServiceTask actualNoOpServiceTask = new NoOpServiceTask();
    FixedValue name = new FixedValue(JSONObject.NULL);
    actualNoOpServiceTask.setName(name);
    Expression actualName = actualNoOpServiceTask.getName();

    // Assert that nothing has changed
    assertTrue(actualName instanceof FixedValue);
    assertSame(name, actualName);
  }
}
