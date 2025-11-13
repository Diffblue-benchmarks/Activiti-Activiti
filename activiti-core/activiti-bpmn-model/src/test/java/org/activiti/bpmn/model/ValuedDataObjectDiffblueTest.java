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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ValuedDataObjectDiffblueTest {
  /**
   * Test {@link ValuedDataObject#getValue()}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor) Value is {@code Value}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ValuedDataObject.getValue()"})
  public void testGetValue_givenBooleanDataObjectValueIsValue_thenReturnFalse() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setValue("Value");

    // Act
    Object actualValue = booleanDataObject.getValue();

    // Assert
    assertFalse((Boolean) actualValue);
    assertSame(booleanDataObject.value, actualValue);
  }

  /**
   * Test {@link ValuedDataObject#getValue()}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#getValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ValuedDataObject.getValue()"})
  public void testGetValue_givenBooleanDataObject_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new BooleanDataObject().getValue());
  }

  /**
   * Test {@link ValuedDataObject#setValues(ValuedDataObject)} with {@code ValuedDataObject}.
   *
   * <ul>
   *   <li>Given {@code Other Element}.
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#setValues(ValuedDataObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuedDataObject.setValues(ValuedDataObject)"})
  public void testSetValuesWithValuedDataObject_givenOtherElement() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();

    BooleanDataObject otherElement = new BooleanDataObject();
    otherElement.setValue("Other Element");

    // Act
    booleanDataObject.setValues(otherElement);

    // Assert that nothing has changed
    assertFalse((Boolean) otherElement.getValue());
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link ValuedDataObject#setValues(ValuedDataObject)} with {@code ValuedDataObject}.
   *
   * <ul>
   *   <li>Then {@link BooleanDataObject} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#setValues(ValuedDataObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuedDataObject.setValues(ValuedDataObject)"})
  public void testSetValuesWithValuedDataObject_thenBooleanDataObjectIdIs42() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    DateDataObject otherElement = mock(DateDataObject.class);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getValue()).thenReturn("Value");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
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
    assertEquals(1, booleanDataObject.getExecutionListeners().size());
    assertFalse((Boolean) booleanDataObject.getValue());
    assertSame(itemDefinition, booleanDataObject.getItemSubjectRef());
  }

  /**
   * Test {@link ValuedDataObject#setValues(ValuedDataObject)} with {@code ValuedDataObject}.
   *
   * <ul>
   *   <li>When {@link BooleanDataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#setValues(ValuedDataObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuedDataObject.setValues(ValuedDataObject)"})
  public void testSetValuesWithValuedDataObject_whenBooleanDataObject() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();
    BooleanDataObject otherElement = new BooleanDataObject();

    // Act
    booleanDataObject.setValues(otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link ValuedDataObject#getType()}.
   *
   * <ul>
   *   <li>Given {@link ItemDefinition} (default constructor) StructureRef is {@code Structure Ref}.
   *   <li>Then return {@code Structure Ref}.
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#getType()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ValuedDataObject.getType()"})
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
   * Test {@link ValuedDataObject#equals(Object)}, and {@link ValuedDataObject#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ValuedDataObject.equals(Object)"})
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
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ValuedDataObject.equals(Object)"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BooleanDataObject(), null);
  }

  /**
   * Test {@link ValuedDataObject#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ValuedDataObject.equals(Object)"})
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
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ValuedDataObject.equals(Object)"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BooleanDataObject(), null);
  }

  /**
   * Test {@link ValuedDataObject#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ValuedDataObject#equals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ValuedDataObject.equals(Object)"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BooleanDataObject(), "Different type to ValuedDataObject");
  }
}
