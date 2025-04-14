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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SignalDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SignalDefinition}
   *   <li>{@link SignalDefinition#setId(String)}
   *   <li>{@link SignalDefinition#setName(String)}
   *   <li>{@link SignalDefinition#getId()}
   *   <li>{@link SignalDefinition#getName()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SignalDefinition.<init>()", "String SignalDefinition.getId()",
      "String SignalDefinition.getName()", "void SignalDefinition.setId(String)",
      "void SignalDefinition.setName(String)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    SignalDefinition actualSignalDefinition = new SignalDefinition();
    actualSignalDefinition.setId("42");
    actualSignalDefinition.setName("Name");
    String actualId = actualSignalDefinition.getId();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Name", actualSignalDefinition.getName());
  }
}
