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
import org.junit.Test;

public class ClassStructureDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClassStructureDefinition#ClassStructureDefinition(String, Class)}
   *   <li>{@link ClassStructureDefinition#getFieldSize()}
   *   <li>{@link ClassStructureDefinition#getId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Class<Object> classStructure = Object.class;

    // Act
    ClassStructureDefinition actualClassStructureDefinition = new ClassStructureDefinition("42", classStructure);
    int actualFieldSize = actualClassStructureDefinition.getFieldSize();

    // Assert
    assertEquals("42", actualClassStructureDefinition.getId());
    assertEquals(0, actualFieldSize);
  }

  /**
   * Test {@link ClassStructureDefinition#ClassStructureDefinition(Class)}.
   * <p>
   * Method under test:
   * {@link ClassStructureDefinition#ClassStructureDefinition(Class)}
   */
  @Test
  public void testNewClassStructureDefinition() {
    // Arrange
    Class<Object> classStructure = Object.class;

    // Act
    ClassStructureDefinition actualClassStructureDefinition = new ClassStructureDefinition(classStructure);

    // Assert
    assertEquals("java.lang.Object", actualClassStructureDefinition.getId());
    assertEquals(0, actualClassStructureDefinition.getFieldSize());
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, actualClassStructureDefinition.classStructure);
  }

  /**
   * Test {@link ClassStructureDefinition#getFieldNameAt(int)}.
   * <p>
   * Method under test: {@link ClassStructureDefinition#getFieldNameAt(int)}
   */
  @Test
  public void testGetFieldNameAt() {
    // Arrange
    Class<Object> classStructure = Object.class;

    // Act and Assert
    assertNull((new ClassStructureDefinition(classStructure)).getFieldNameAt(1));
  }

  /**
   * Test {@link ClassStructureDefinition#getFieldTypeAt(int)}.
   * <p>
   * Method under test: {@link ClassStructureDefinition#getFieldTypeAt(int)}
   */
  @Test
  public void testGetFieldTypeAt() {
    // Arrange
    Class<Object> classStructure = Object.class;

    // Act and Assert
    assertNull((new ClassStructureDefinition(classStructure)).getFieldTypeAt(1));
  }

  /**
   * Test {@link ClassStructureDefinition#createInstance()}.
   * <p>
   * Method under test: {@link ClassStructureDefinition#createInstance()}
   */
  @Test
  public void testCreateInstance() {
    // Arrange
    Class<Object> classStructure = Object.class;

    // Act
    StructureInstance actualCreateInstanceResult = (new ClassStructureDefinition(classStructure)).createInstance();

    // Assert
    FieldBaseStructureDefinition fieldBaseStructureDefinition = ((FieldBaseStructureInstance) actualCreateInstanceResult).structureDefinition;
    assertTrue(fieldBaseStructureDefinition instanceof ClassStructureDefinition);
    assertTrue(actualCreateInstanceResult instanceof FieldBaseStructureInstance);
    assertEquals("java.lang.Object", fieldBaseStructureDefinition.getId());
    assertEquals(0, fieldBaseStructureDefinition.getFieldSize());
    assertEquals(0, ((FieldBaseStructureInstance) actualCreateInstanceResult).getFieldSize());
    assertEquals(0, actualCreateInstanceResult.toArray().length);
    assertTrue(((FieldBaseStructureInstance) actualCreateInstanceResult).fieldValues.isEmpty());
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, ((ClassStructureDefinition) fieldBaseStructureDefinition).classStructure);
  }
}
