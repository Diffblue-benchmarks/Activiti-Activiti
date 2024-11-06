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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.impl.bpmn.data.FieldBaseStructureInstance;
import org.activiti.engine.impl.bpmn.data.ItemDefinition;
import org.activiti.engine.impl.bpmn.data.ItemInstance;
import org.activiti.engine.impl.bpmn.data.SimpleStructureDefinition;
import org.activiti.engine.impl.bpmn.data.StructureInstance;
import org.junit.Test;

public class MessageDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageDefinition#MessageDefinition(String)}
   *   <li>{@link MessageDefinition#setItemDefinition(ItemDefinition)}
   *   <li>{@link MessageDefinition#getId()}
   *   <li>{@link MessageDefinition#getItemDefinition()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    MessageDefinition actualMessageDefinition = new MessageDefinition("42");
    ItemDefinition itemDefinition = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    actualMessageDefinition.setItemDefinition(itemDefinition);
    String actualId = actualMessageDefinition.getId();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertSame(itemDefinition, actualMessageDefinition.getItemDefinition());
  }

  /**
   * Test {@link MessageDefinition#createInstance()}.
   * <ul>
   *   <li>Then StructureInstance return {@link FieldBaseStructureInstance}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageDefinition#createInstance()}
   */
  @Test
  public void testCreateInstance_thenStructureInstanceReturnFieldBaseStructureInstance() {
    // Arrange
    MessageDefinition messageDefinition = new MessageDefinition("42");
    ItemDefinition itemDefinition = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    messageDefinition.setItemDefinition(itemDefinition);

    // Act
    MessageInstance actualCreateInstanceResult = messageDefinition.createInstance();

    // Assert
    StructureInstance structureInstance = actualCreateInstanceResult.getStructureInstance();
    assertTrue(structureInstance instanceof FieldBaseStructureInstance);
    assertEquals(0, ((FieldBaseStructureInstance) structureInstance).getFieldSize());
    assertEquals(0, structureInstance.toArray().length);
    ItemInstance itemInstance = actualCreateInstanceResult.item;
    assertSame(itemDefinition, itemInstance.getItem());
    assertSame(messageDefinition, actualCreateInstanceResult.getMessage());
    assertSame(structureInstance, itemInstance.getStructureInstance());
  }

  /**
   * Test {@link MessageDefinition#getStructureDefinition()}.
   * <ul>
   *   <li>Then return
   * {@link SimpleStructureDefinition#SimpleStructureDefinition(String)} with id
   * is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageDefinition#getStructureDefinition()}
   */
  @Test
  public void testGetStructureDefinition_thenReturnSimpleStructureDefinitionWithIdIs42() {
    // Arrange
    MessageDefinition messageDefinition = new MessageDefinition("42");
    SimpleStructureDefinition structure = new SimpleStructureDefinition("42");
    messageDefinition.setItemDefinition(new ItemDefinition("42", structure));

    // Act and Assert
    assertSame(structure, messageDefinition.getStructureDefinition());
  }
}
