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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;

public class ValuedDataObjectDiffblueTest {
  /**
   * Test {@link ValuedDataObject#getValue()}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor) Value is
   * {@code Value}.</li>
   *   <li>Then return {@link BooleanDataObject} (default constructor)
   * {@link ValuedDataObject#value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#getValue()}
   */
  @Test
  public void testGetValue_givenBooleanDataObjectValueIsValue_thenReturnBooleanDataObjectValue() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setValue("Value");

    // Act and Assert
    assertSame(booleanDataObject.value, booleanDataObject.getValue());
  }

  /**
   * Test {@link ValuedDataObject#getValue()}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#getValue()}
   */
  @Test
  public void testGetValue_givenBooleanDataObject_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BooleanDataObject()).getValue());
  }

  /**
   * Test {@link ValuedDataObject#setValues(ValuedDataObject)} with
   * {@code ValuedDataObject}.
   * <ul>
   *   <li>Given {@code Other Element}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#setValues(ValuedDataObject)}
   */
  @Test
  public void testSetValuesWithValuedDataObject_givenOtherElement() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();

    BooleanDataObject otherElement = new BooleanDataObject();
    otherElement.setValue("Other Element");

    // Act
    booleanDataObject.setValues(otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getItemSubjectRef());
  }

  /**
   * Test {@link ValuedDataObject#setValues(ValuedDataObject)} with
   * {@code ValuedDataObject}.
   * <ul>
   *   <li>Given {@code Value}.</li>
   *   <li>Then {@link BooleanDataObject} (default constructor) Id is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#setValues(ValuedDataObject)}
   */
  @Test
  public void testSetValuesWithValuedDataObject_givenValue_thenBooleanDataObjectIdIs42() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();
    DateDataObject otherElement = mock(DateDataObject.class);
    when(otherElement.getValue()).thenReturn("Value");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    ItemDefinition itemDefinition = new ItemDefinition();
    when(otherElement.getItemSubjectRef()).thenReturn(itemDefinition);

    // Act
    booleanDataObject.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement, atLeast(1)).getId();
    verify(otherElement).getItemSubjectRef();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement, atLeast(1)).getName();
    verify(otherElement, atLeast(1)).getValue();
    assertEquals("42", booleanDataObject.getId());
    assertEquals("Documentation", booleanDataObject.getDocumentation());
    assertEquals("Name", booleanDataObject.getName());
    assertSame(itemDefinition, booleanDataObject.getItemSubjectRef());
  }

  /**
   * Test {@link ValuedDataObject#setValues(ValuedDataObject)} with
   * {@code ValuedDataObject}.
   * <ul>
   *   <li>When {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#setValues(ValuedDataObject)}
   */
  @Test
  public void testSetValuesWithValuedDataObject_whenBooleanDataObject() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();
    BooleanDataObject otherElement = new BooleanDataObject();

    // Act
    booleanDataObject.setValues(otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getItemSubjectRef());
  }

  /**
   * Test {@link ValuedDataObject#getType()}.
   * <ul>
   *   <li>Given {@link ItemDefinition} (default constructor) StructureRef is
   * {@code Structure Ref}.</li>
   *   <li>Then return {@code Structure Ref}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#getType()}
   */
  @Test
  public void testGetType_givenItemDefinitionStructureRefIsStructureRef_thenReturnStructureRef() {
    // Arrange
    ItemDefinition itemSubjectRef = new ItemDefinition();
    itemSubjectRef.setStructureRef("Structure Ref");

    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setItemSubjectRef(itemSubjectRef);

    // Act and Assert
    assertEquals("Structure Ref", booleanDataObject.getType());
  }

  /**
   * Test {@link ValuedDataObject#equals(Object)}, and {@link Object#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();

    // Act and Assert
    assertEquals(booleanDataObject, booleanDataObject);
    int expectedHashCodeResult = booleanDataObject.hashCode();
    assertEquals(expectedHashCodeResult, booleanDataObject.hashCode());
  }

  /**
   * Test {@link ValuedDataObject#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BooleanDataObject(), null);
    assertNotEquals(new BooleanDataObject(), mock(DateDataObject.class));
  }

  /**
   * Test {@link ValuedDataObject#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setItemSubjectRef(new ItemDefinition());

    ItemDefinition itemSubjectRef = new ItemDefinition();
    itemSubjectRef.setStructureRef("Structure Ref");

    BooleanDataObject booleanDataObject2 = new BooleanDataObject();
    booleanDataObject2.setItemSubjectRef(itemSubjectRef);

    // Act and Assert
    assertNotEquals(booleanDataObject, booleanDataObject2);
  }

  /**
   * Test {@link ValuedDataObject#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BooleanDataObject(), null);
  }

  /**
   * Test {@link ValuedDataObject#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BooleanDataObject(), "Different type to ValuedDataObject");
  }
}
