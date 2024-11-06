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
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class PrimitiveStructureInstanceDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return Primitive is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link PrimitiveStructureInstance#PrimitiveStructureInstance(PrimitiveStructureDefinition)}
   *   <li>{@link PrimitiveStructureInstance#getPrimitive()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_thenReturnPrimitiveIsNull() {
    // Arrange
    Class<Object> primitiveClass = Object.class;

    // Act
    PrimitiveStructureInstance actualPrimitiveStructureInstance = new PrimitiveStructureInstance(
        new PrimitiveStructureDefinition("42", primitiveClass));
    Object actualPrimitive = actualPrimitiveStructureInstance.getPrimitive();

    // Assert
    PrimitiveStructureDefinition primitiveStructureDefinition = actualPrimitiveStructureInstance.definition;
    assertEquals("42", primitiveStructureDefinition.getId());
    assertNull(actualPrimitive);
    Class<Object> expectedPrimitiveClass = Object.class;
    Class<?> primitiveClass2 = primitiveStructureDefinition.getPrimitiveClass();
    assertEquals(expectedPrimitiveClass, primitiveClass2);
    assertSame(primitiveClass, primitiveClass2);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return Primitive is {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link PrimitiveStructureInstance#PrimitiveStructureInstance(PrimitiveStructureDefinition, Object)}
   *   <li>{@link PrimitiveStructureInstance#getPrimitive()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenNull_thenReturnPrimitiveIsNull() {
    // Arrange
    Class<Object> primitiveClass = Object.class;
    Object object = JSONObject.NULL;

    // Act
    PrimitiveStructureInstance actualPrimitiveStructureInstance = new PrimitiveStructureInstance(
        new PrimitiveStructureDefinition("42", primitiveClass), object);
    Object actualPrimitive = actualPrimitiveStructureInstance.getPrimitive();

    // Assert
    PrimitiveStructureDefinition primitiveStructureDefinition = actualPrimitiveStructureInstance.definition;
    assertEquals("42", primitiveStructureDefinition.getId());
    Class<Object> expectedPrimitiveClass = Object.class;
    Class<?> primitiveClass2 = primitiveStructureDefinition.getPrimitiveClass();
    assertEquals(expectedPrimitiveClass, primitiveClass2);
    assertSame(primitiveClass, primitiveClass2);
    assertSame(object, actualPrimitive);
  }

  /**
   * Test {@link PrimitiveStructureInstance#toArray()}.
   * <p>
   * Method under test: {@link PrimitiveStructureInstance#toArray()}
   */
  @Test
  public void testToArray() {
    // Arrange
    Class<Object> primitiveClass = Object.class;

    // Act
    Object[] actualToArrayResult = (new PrimitiveStructureInstance(
        new PrimitiveStructureDefinition("42", primitiveClass))).toArray();

    // Assert
    assertNull(actualToArrayResult[0]);
    assertEquals(1, actualToArrayResult.length);
  }

  /**
   * Test {@link PrimitiveStructureInstance#loadFrom(Object[])}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When array of {@link Object} with {@link JSONObject#NULL}.</li>
   *   <li>Then array length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link PrimitiveStructureInstance#loadFrom(Object[])}
   */
  @Test
  public void testLoadFrom_givenJavaLangObject_whenArrayOfObjectWithNull_thenArrayLengthIsOne() {
    // Arrange
    Class<Object> primitiveClass = Object.class;
    PrimitiveStructureInstance primitiveStructureInstance = new PrimitiveStructureInstance(
        new PrimitiveStructureDefinition("42", primitiveClass));

    // Act
    primitiveStructureInstance.loadFrom(new Object[]{JSONObject.NULL});

    // Assert
    assertEquals(1, primitiveStructureInstance.toArray().length);
  }

  /**
   * Test {@link PrimitiveStructureInstance#loadFrom(Object[])}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When empty array of {@link Object}.</li>
   *   <li>Then array length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link PrimitiveStructureInstance#loadFrom(Object[])}
   */
  @Test
  public void testLoadFrom_givenJavaLangObject_whenEmptyArrayOfObject_thenArrayLengthIsOne() {
    // Arrange
    Class<Object> primitiveClass = Object.class;
    PrimitiveStructureInstance primitiveStructureInstance = new PrimitiveStructureInstance(
        new PrimitiveStructureDefinition("42", primitiveClass));

    // Act
    primitiveStructureInstance.loadFrom(new Object[]{});

    // Assert that nothing has changed
    assertEquals(1, primitiveStructureInstance.toArray().length);
  }
}
