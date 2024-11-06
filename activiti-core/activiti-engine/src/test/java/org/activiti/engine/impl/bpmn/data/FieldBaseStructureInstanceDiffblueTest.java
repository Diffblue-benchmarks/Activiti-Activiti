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
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FieldBaseStructureInstanceDiffblueTest {
  @Mock
  private FieldBaseStructureDefinition fieldBaseStructureDefinition;

  @InjectMocks
  private FieldBaseStructureInstance fieldBaseStructureInstance;

  /**
   * Test
   * {@link FieldBaseStructureInstance#FieldBaseStructureInstance(FieldBaseStructureDefinition)}.
   * <p>
   * Method under test:
   * {@link FieldBaseStructureInstance#FieldBaseStructureInstance(FieldBaseStructureDefinition)}
   */
  @Test
  public void testNewFieldBaseStructureInstance() {
    // Arrange and Act
    FieldBaseStructureInstance actualFieldBaseStructureInstance = new FieldBaseStructureInstance(
        new SimpleStructureDefinition("42"));

    // Assert
    assertTrue(((SimpleStructureDefinition) actualFieldBaseStructureInstance.structureDefinition).fieldNames.isEmpty());
    assertTrue(((SimpleStructureDefinition) actualFieldBaseStructureInstance.structureDefinition).fieldTypes.isEmpty());
    assertTrue(actualFieldBaseStructureInstance.fieldValues.isEmpty());
  }

  /**
   * Test {@link FieldBaseStructureInstance#getFieldValue(String)}.
   * <p>
   * Method under test: {@link FieldBaseStructureInstance#getFieldValue(String)}
   */
  @Test
  public void testGetFieldValue() {
    // Arrange, Act and Assert
    assertNull(fieldBaseStructureInstance.getFieldValue("Field Name"));
  }

  /**
   * Test {@link FieldBaseStructureInstance#setFieldValue(String, Object)}.
   * <p>
   * Method under test:
   * {@link FieldBaseStructureInstance#setFieldValue(String, Object)}
   */
  @Test
  public void testSetFieldValue() {
    // Arrange
    Object object = JSONObject.NULL;

    // Act
    fieldBaseStructureInstance.setFieldValue("Field Name", object);

    // Assert
    Map<String, Object> stringObjectMap = fieldBaseStructureInstance.fieldValues;
    assertEquals(1, stringObjectMap.size());
    assertSame(object, stringObjectMap.get("Field Name"));
  }

  /**
   * Test {@link FieldBaseStructureInstance#getFieldSize()}.
   * <ul>
   *   <li>Given {@link SimpleStructureDefinition#SimpleStructureDefinition(String)}
   * with id is {@code 42}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldBaseStructureInstance#getFieldSize()}
   */
  @Test
  public void testGetFieldSize_givenSimpleStructureDefinitionWithIdIs42_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new FieldBaseStructureInstance(new SimpleStructureDefinition("42"))).getFieldSize());
  }

  /**
   * Test {@link FieldBaseStructureInstance#getFieldNameAt(int)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldBaseStructureInstance#getFieldNameAt(int)}
   */
  @Test
  public void testGetFieldNameAt_givenJavaLangObject_thenReturnNull() {
    // Arrange
    Class<Object> classStructure = Object.class;

    // Act and Assert
    assertNull((new FieldBaseStructureInstance(new ClassStructureDefinition(classStructure))).getFieldNameAt(1));
  }

  /**
   * Test {@link FieldBaseStructureInstance#toArray()}.
   * <ul>
   *   <li>Given {@link SimpleStructureDefinition#SimpleStructureDefinition(String)}
   * with id is {@code 42}.</li>
   *   <li>Then return array length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldBaseStructureInstance#toArray()}
   */
  @Test
  public void testToArray_givenSimpleStructureDefinitionWithIdIs42_thenReturnArrayLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new FieldBaseStructureInstance(new SimpleStructureDefinition("42"))).toArray().length);
  }
}
