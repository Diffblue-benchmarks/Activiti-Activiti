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
   * Method under test: {@link ValuedDataObject#getValue()}
   */
  @Test
  public void testGetValue() {
    // Arrange, Act and Assert
    assertNull((new BooleanDataObject()).getValue());
  }

  /**
   * Method under test: {@link ValuedDataObject#getValue()}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setValue("Value");

    // Act and Assert
    assertSame(booleanDataObject.value, booleanDataObject.getValue());
  }

  /**
   * Method under test: {@link ValuedDataObject#setValues(ValuedDataObject)}
   */
  @Test
  public void testSetValues() {
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
   * Method under test: {@link ValuedDataObject#setValues(ValuedDataObject)}
   */
  @Test
  public void testSetValues2() {
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
   * Method under test: {@link ValuedDataObject#setValues(ValuedDataObject)}
   */
  @Test
  public void testSetValues3() {
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
   * Method under test: {@link ValuedDataObject#getType()}
   */
  @Test
  public void testGetType() {
    // Arrange
    ItemDefinition itemSubjectRef = new ItemDefinition();
    itemSubjectRef.setStructureRef("Structure Ref");

    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setItemSubjectRef(itemSubjectRef);

    // Act and Assert
    assertEquals("Structure Ref", booleanDataObject.getType());
  }

  /**
   * Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BooleanDataObject(), null);
    assertNotEquals(new BooleanDataObject(), mock(DateDataObject.class));
  }

  /**
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
   * Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BooleanDataObject(), null);
  }

  /**
   * Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BooleanDataObject(), "Different type to ValuedDataObject");
  }
}
