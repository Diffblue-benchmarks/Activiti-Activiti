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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class IntegerDataObjectDiffblueTest {
  /**
   * Test {@link IntegerDataObject#setValue(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then {@link IntegerDataObject} (default constructor) Value intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link IntegerDataObject#setValue(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IntegerDataObject.setValue(Object)"})
  public void testSetValue_whenFortyTwo_thenIntegerDataObjectValueIntValueIsFortyTwo() {
    // Arrange
    IntegerDataObject integerDataObject = new IntegerDataObject();

    // Act
    integerDataObject.setValue(42);

    // Assert
    assertEquals(42, ((Integer) integerDataObject.getValue()).intValue());
  }

  /**
   * Test {@link IntegerDataObject#clone()}.
   *
   * <ul>
   *   <li>Given {@link IntegerDataObject} (default constructor).
   *   <li>Then return Value is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link IntegerDataObject#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IntegerDataObject IntegerDataObject.clone()"})
  public void testClone_givenIntegerDataObject_thenReturnValueIsNull() {
    // Arrange and Act
    IntegerDataObject actualCloneResult = new IntegerDataObject().clone();

    // Assert
    assertNull(actualCloneResult.getValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link IntegerDataObject#clone()}.
   *
   * <ul>
   *   <li>Then return Value intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link IntegerDataObject#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IntegerDataObject IntegerDataObject.clone()"})
  public void testClone_thenReturnValueIntValueIsFortyTwo() {
    // Arrange
    IntegerDataObject integerDataObject = new IntegerDataObject();
    integerDataObject.setValue(42);

    // Act
    IntegerDataObject actualCloneResult = integerDataObject.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertEquals(42, ((Integer) actualCloneResult.getValue()).intValue());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link IntegerDataObject} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link IntegerDataObject}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IntegerDataObject.<init>()"})
  public void testNewIntegerDataObject() {
    // Arrange and Act
    IntegerDataObject actualIntegerDataObject = new IntegerDataObject();

    // Assert
    assertNull(actualIntegerDataObject.getValue());
    assertNull(actualIntegerDataObject.getId());
    assertNull(actualIntegerDataObject.getDocumentation());
    assertNull(actualIntegerDataObject.getName());
    assertNull(actualIntegerDataObject.getParentContainer());
    assertNull(actualIntegerDataObject.getItemSubjectRef());
    assertEquals(0, actualIntegerDataObject.getXmlColumnNumber());
    assertEquals(0, actualIntegerDataObject.getXmlRowNumber());
    assertTrue(actualIntegerDataObject.getExecutionListeners().isEmpty());
    assertTrue(actualIntegerDataObject.getAttributes().isEmpty());
    assertTrue(actualIntegerDataObject.getExtensionElements().isEmpty());
  }
}
