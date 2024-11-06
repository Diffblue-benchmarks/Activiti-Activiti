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
package org.activiti.engine.impl.bpmn.webservice;

import static org.junit.Assert.assertSame;
import org.activiti.engine.impl.bpmn.data.FieldBaseStructureInstance;
import org.activiti.engine.impl.bpmn.data.ItemDefinition;
import org.activiti.engine.impl.bpmn.data.ItemInstance;
import org.activiti.engine.impl.bpmn.data.SimpleStructureDefinition;
import org.junit.Test;

public class MessageInstanceDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageInstance#MessageInstance(MessageDefinition, ItemInstance)}
   *   <li>{@link MessageInstance#getMessage()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    FieldBaseStructureInstance structureInstance = new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    // Act
    MessageInstance actualMessageInstance = new MessageInstance(message, new ItemInstance(item, structureInstance));
    MessageDefinition actualMessage = actualMessageInstance.getMessage();

    // Assert
    ItemInstance itemInstance = actualMessageInstance.item;
    assertSame(structureInstance, itemInstance.getStructureInstance());
    assertSame(item, itemInstance.getItem());
    assertSame(message, actualMessage);
  }

  /**
   * Test {@link MessageInstance#getStructureInstance()}.
   * <p>
   * Method under test: {@link MessageInstance#getStructureInstance()}
   */
  @Test
  public void testGetStructureInstance() {
    // Arrange
    MessageDefinition message = new MessageDefinition("42");
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    FieldBaseStructureInstance structureInstance = new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    // Act and Assert
    assertSame(structureInstance,
        (new MessageInstance(message, new ItemInstance(item, structureInstance))).getStructureInstance());
  }
}
