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
package org.activiti.engine.impl.bpmn.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ItemDefinitionDiffblueTest {
  @InjectMocks
  private ItemDefinition itemDefinition;

  /**
   * Test {@link ItemDefinition#ItemDefinition(String, StructureDefinition)}.
   * <p>
   * Method under test:
   * {@link ItemDefinition#ItemDefinition(String, StructureDefinition)}
   */
  @Test
  public void testNewItemDefinition() {
    // Arrange
    SimpleStructureDefinition structure = new SimpleStructureDefinition("42");

    // Act and Assert
    assertSame(structure, (new ItemDefinition("42", structure)).getStructureDefinition());
  }

  /**
   * Test {@link ItemDefinition#ItemDefinition(String, StructureDefinition)}.
   * <ul>
   *   <li>When {@link ClassStructureDefinition}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ItemDefinition#ItemDefinition(String, StructureDefinition)}
   */
  @Test
  public void testNewItemDefinition_whenClassStructureDefinition_thenReturnIdIs42() {
    // Arrange
    ClassStructureDefinition structure = mock(ClassStructureDefinition.class);

    // Act
    ItemDefinition actualItemDefinition = new ItemDefinition("42", structure);

    // Assert
    assertEquals("42", actualItemDefinition.getId());
    assertEquals(ItemKind.Information, actualItemDefinition.getItemKind());
    assertFalse(actualItemDefinition.isCollection());
    assertSame(structure, actualItemDefinition.getStructureDefinition());
  }

  /**
   * Test {@link ItemDefinition#createInstance()}.
   * <ul>
   *   <li>Then StructureInstance return {@link FieldBaseStructureInstance}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ItemDefinition#createInstance()}
   */
  @Test
  public void testCreateInstance_thenStructureInstanceReturnFieldBaseStructureInstance() {
    // Arrange
    SimpleStructureDefinition structure = new SimpleStructureDefinition("42");
    ItemDefinition itemDefinition = new ItemDefinition("42", structure);

    // Act
    ItemInstance actualCreateInstanceResult = itemDefinition.createInstance();

    // Assert
    StructureInstance structureInstance = actualCreateInstanceResult.getStructureInstance();
    assertTrue(structureInstance instanceof FieldBaseStructureInstance);
    assertEquals(0, ((FieldBaseStructureInstance) structureInstance).getFieldSize());
    assertEquals(0, structureInstance.toArray().length);
    assertTrue(((FieldBaseStructureInstance) structureInstance).fieldValues.isEmpty());
    assertSame(itemDefinition, actualCreateInstanceResult.getItem());
    assertSame(structure, ((FieldBaseStructureInstance) structureInstance).structureDefinition);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ItemDefinition#setCollection(boolean)}
   *   <li>{@link ItemDefinition#setItemKind(ItemKind)}
   *   <li>{@link ItemDefinition#getId()}
   *   <li>{@link ItemDefinition#getItemKind()}
   *   <li>{@link ItemDefinition#getStructureDefinition()}
   *   <li>{@link ItemDefinition#isCollection()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    SimpleStructureDefinition structure = new SimpleStructureDefinition("42");
    ItemDefinition itemDefinition = new ItemDefinition("42", structure);

    // Act
    itemDefinition.setCollection(true);
    itemDefinition.setItemKind(ItemKind.Information);
    String actualId = itemDefinition.getId();
    ItemKind actualItemKind = itemDefinition.getItemKind();
    StructureDefinition actualStructureDefinition = itemDefinition.getStructureDefinition();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals(ItemKind.Information, actualItemKind);
    assertTrue(itemDefinition.isCollection());
    assertSame(structure, actualStructureDefinition);
  }
}
