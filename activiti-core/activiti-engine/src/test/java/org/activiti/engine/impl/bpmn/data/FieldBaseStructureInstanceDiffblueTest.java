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
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(MockitoJUnitRunner.class)
public class FieldBaseStructureInstanceDiffblueTest {
  @InjectMocks private FieldBaseStructureInstance fieldBaseStructureInstance;

  /**
   * Test {@link
   * FieldBaseStructureInstance#FieldBaseStructureInstance(FieldBaseStructureDefinition)}.
   *
   * <p>Method under test: {@link
   * FieldBaseStructureInstance#FieldBaseStructureInstance(FieldBaseStructureDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FieldBaseStructureInstance.<init>(FieldBaseStructureDefinition)"})
  public void testNewFieldBaseStructureInstance() {
    // Arrange and Act
    FieldBaseStructureInstance actualFieldBaseStructureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    // Assert
    assertTrue(
        ((SimpleStructureDefinition) actualFieldBaseStructureInstance.structureDefinition)
            .fieldNames.isEmpty());
    assertTrue(
        ((SimpleStructureDefinition) actualFieldBaseStructureInstance.structureDefinition)
            .fieldTypes.isEmpty());
    assertTrue(actualFieldBaseStructureInstance.fieldValues.isEmpty());
  }

  /**
   * Test {@link FieldBaseStructureInstance#getFieldValue(String)}.
   *
   * <p>Method under test: {@link FieldBaseStructureInstance#getFieldValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object FieldBaseStructureInstance.getFieldValue(String)"})
  public void testGetFieldValue() {
    // Arrange, Act and Assert
    assertNull(fieldBaseStructureInstance.getFieldValue("Field Name"));
  }

  /**
   * Test {@link FieldBaseStructureInstance#setFieldValue(String, Object)}.
   *
   * <p>Method under test: {@link FieldBaseStructureInstance#setFieldValue(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FieldBaseStructureInstance.setFieldValue(String, Object)"})
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
   *
   * <ul>
   *   <li>Given {@link SimpleStructureDefinition#SimpleStructureDefinition(String)} with id is
   *       {@code 42}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link FieldBaseStructureInstance#getFieldSize()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int FieldBaseStructureInstance.getFieldSize()"})
  public void testGetFieldSize_givenSimpleStructureDefinitionWithIdIs42_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(
        0, new FieldBaseStructureInstance(new SimpleStructureDefinition("42")).getFieldSize());
  }

  /**
   * Test {@link FieldBaseStructureInstance#getFieldNameAt(int)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FieldBaseStructureInstance#getFieldNameAt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String FieldBaseStructureInstance.getFieldNameAt(int)"})
  public void testGetFieldNameAt_givenJavaLangObject_thenReturnNull() {
    // Arrange
    Class<Object> classStructure = Object.class;

    // Act and Assert
    assertNull(
        new FieldBaseStructureInstance(new ClassStructureDefinition(classStructure))
            .getFieldNameAt(1));
  }

  /**
   * Test {@link FieldBaseStructureInstance#toArray()}.
   *
   * <ul>
   *   <li>Given {@link SimpleStructureDefinition#SimpleStructureDefinition(String)} with id is
   *       {@code 42}.
   *   <li>Then return array length is zero.
   * </ul>
   *
   * <p>Method under test: {@link FieldBaseStructureInstance#toArray()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object[] FieldBaseStructureInstance.toArray()"})
  public void testToArray_givenSimpleStructureDefinitionWithIdIs42_thenReturnArrayLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(
        0, new FieldBaseStructureInstance(new SimpleStructureDefinition("42")).toArray().length);
  }

  /**
   * Test {@link FieldBaseStructureInstance#loadFrom(Object[])}.
   *
   * <p>Method under test: {@link FieldBaseStructureInstance#loadFrom(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FieldBaseStructureInstance.loadFrom(Object[])"})
  public void testLoadFrom() {
    // Arrange
    FieldBaseStructureInstance fieldBaseStructureInstance =
        new FieldBaseStructureInstance(new SimpleStructureDefinition("42"));

    // Act
    fieldBaseStructureInstance.loadFrom(new Object[] {JSONObject.NULL});

    // Assert that nothing has changed
    assertTrue(fieldBaseStructureInstance.fieldValues.isEmpty());
  }

  /**
   * Test {@link FieldBaseStructureInstance#loadFrom(Object[])}.
   *
   * <p>Method under test: {@link FieldBaseStructureInstance#loadFrom(Object[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FieldBaseStructureInstance.loadFrom(Object[])"})
  public void testLoadFrom2() {
    // Arrange
    ClassStructureDefinition structureDefinition = mock(ClassStructureDefinition.class);
    when(structureDefinition.getFieldNameAt(anyInt())).thenReturn("Field Name At");
    when(structureDefinition.getFieldSize()).thenReturn(3);
    FieldBaseStructureInstance fieldBaseStructureInstance =
        new FieldBaseStructureInstance(structureDefinition);

    // Act
    fieldBaseStructureInstance.loadFrom(
        new Object[] {JSONObject.NULL, JSONObject.NULL, JSONObject.NULL});

    // Assert
    verify(structureDefinition, atLeast(1)).getFieldNameAt(anyInt());
    verify(structureDefinition).getFieldSize();
    assertEquals(1, fieldBaseStructureInstance.fieldValues.size());
    assertEquals(3, fieldBaseStructureInstance.toArray().length);
  }
}
