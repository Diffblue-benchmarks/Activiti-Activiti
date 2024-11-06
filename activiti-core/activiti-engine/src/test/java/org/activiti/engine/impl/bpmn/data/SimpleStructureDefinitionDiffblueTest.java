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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SimpleStructureDefinitionDiffblueTest {
  @InjectMocks
  private SimpleStructureDefinition simpleStructureDefinition;

  @InjectMocks
  private String string;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleStructureDefinition#SimpleStructureDefinition(String)}
   *   <li>{@link SimpleStructureDefinition#getId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    SimpleStructureDefinition actualSimpleStructureDefinition = new SimpleStructureDefinition("42");

    // Assert
    assertEquals("42", actualSimpleStructureDefinition.getId());
    assertTrue(actualSimpleStructureDefinition.fieldNames.isEmpty());
    assertTrue(actualSimpleStructureDefinition.fieldTypes.isEmpty());
  }

  /**
   * Test {@link SimpleStructureDefinition#getFieldSize()}.
   * <p>
   * Method under test: {@link SimpleStructureDefinition#getFieldSize()}
   */
  @Test
  public void testGetFieldSize() {
    // Arrange, Act and Assert
    assertEquals(0, (new SimpleStructureDefinition("42")).getFieldSize());
  }

  /**
   * Test {@link SimpleStructureDefinition#setFieldName(int, String, Class)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then {@link SimpleStructureDefinition}
   * {@link SimpleStructureDefinition#fieldNames} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleStructureDefinition#setFieldName(int, String, Class)}
   */
  @Test
  public void testSetFieldName_whenOne_thenSimpleStructureDefinitionFieldNamesSizeIsTwo() {
    // Arrange
    Class<Object> type = Object.class;

    // Act
    simpleStructureDefinition.setFieldName(1, "Field Name", type);

    // Assert
    List<String> stringList = simpleStructureDefinition.fieldNames;
    assertEquals(2, stringList.size());
    assertEquals("Field Name", stringList.get(1));
    List<Class<?>> resultClassList = simpleStructureDefinition.fieldTypes;
    assertEquals(2, resultClassList.size());
    assertNull(resultClassList.get(0));
    assertNull(stringList.get(0));
    assertEquals(2, simpleStructureDefinition.getFieldSize());
    Class<Object> expectedGetResult = Object.class;
    assertEquals(expectedGetResult, resultClassList.get(1));
  }

  /**
   * Test {@link SimpleStructureDefinition#createInstance()}.
   * <p>
   * Method under test: {@link SimpleStructureDefinition#createInstance()}
   */
  @Test
  public void testCreateInstance() {
    // Arrange and Act
    StructureInstance actualCreateInstanceResult = (new SimpleStructureDefinition("42")).createInstance();

    // Assert
    assertTrue(actualCreateInstanceResult instanceof FieldBaseStructureInstance);
    FieldBaseStructureDefinition fieldBaseStructureDefinition = ((FieldBaseStructureInstance) actualCreateInstanceResult).structureDefinition;
    assertTrue(fieldBaseStructureDefinition instanceof SimpleStructureDefinition);
    assertEquals("42", fieldBaseStructureDefinition.getId());
    assertEquals(0, fieldBaseStructureDefinition.getFieldSize());
    assertEquals(0, ((FieldBaseStructureInstance) actualCreateInstanceResult).getFieldSize());
    assertEquals(0, actualCreateInstanceResult.toArray().length);
    assertTrue(((SimpleStructureDefinition) fieldBaseStructureDefinition).fieldNames.isEmpty());
    assertTrue(((SimpleStructureDefinition) fieldBaseStructureDefinition).fieldTypes.isEmpty());
    assertTrue(((FieldBaseStructureInstance) actualCreateInstanceResult).fieldValues.isEmpty());
  }
}
