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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PrimitiveStructureDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link PrimitiveStructureDefinition#PrimitiveStructureDefinition(String, Class)}
   *   <li>{@link PrimitiveStructureDefinition#getId()}
   *   <li>{@link PrimitiveStructureDefinition#getPrimitiveClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Class<Object> primitiveClass = Object.class;

    // Act
    PrimitiveStructureDefinition actualPrimitiveStructureDefinition = new PrimitiveStructureDefinition("42",
        primitiveClass);
    String actualId = actualPrimitiveStructureDefinition.getId();
    Class<?> actualPrimitiveClass = actualPrimitiveStructureDefinition.getPrimitiveClass();

    // Assert
    assertEquals("42", actualId);
    Class<Object> expectedPrimitiveClass = Object.class;
    assertEquals(expectedPrimitiveClass, actualPrimitiveClass);
    assertSame(primitiveClass, actualPrimitiveClass);
  }

  /**
   * Test {@link PrimitiveStructureDefinition#createInstance()}.
   * <p>
   * Method under test: {@link PrimitiveStructureDefinition#createInstance()}
   */
  @Test
  public void testCreateInstance() {
    // Arrange
    Class<Object> primitiveClass = Object.class;

    // Act
    StructureInstance actualCreateInstanceResult = (new PrimitiveStructureDefinition("42", primitiveClass))
        .createInstance();

    // Assert
    assertTrue(actualCreateInstanceResult instanceof PrimitiveStructureInstance);
    PrimitiveStructureDefinition primitiveStructureDefinition = ((PrimitiveStructureInstance) actualCreateInstanceResult).definition;
    assertEquals("42", primitiveStructureDefinition.getId());
    assertNull(((PrimitiveStructureInstance) actualCreateInstanceResult).getPrimitive());
    Object[] toArrayResult = actualCreateInstanceResult.toArray();
    assertNull(toArrayResult[0]);
    assertEquals(1, toArrayResult.length);
    Class<Object> expectedPrimitiveClass = Object.class;
    assertEquals(expectedPrimitiveClass, primitiveStructureDefinition.getPrimitiveClass());
  }
}
