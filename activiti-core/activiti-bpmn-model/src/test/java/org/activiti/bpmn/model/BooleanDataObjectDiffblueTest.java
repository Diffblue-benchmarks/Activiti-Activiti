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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BooleanDataObjectDiffblueTest {
  /**
   * Test {@link BooleanDataObject#setValue(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then not {@link BooleanDataObject} (default constructor) Value.
   * </ul>
   *
   * <p>Method under test: {@link BooleanDataObject#setValue(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BooleanDataObject.setValue(Object)"})
  public void testSetValue_whenValue_thenNotBooleanDataObjectValue() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();

    // Act
    booleanDataObject.setValue("Value");

    // Assert
    assertFalse((Boolean) booleanDataObject.getValue());
  }

  /**
   * Test {@link BooleanDataObject#clone()}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor) Value is {@code Value}.
   *   <li>Then return not Value.
   * </ul>
   *
   * <p>Method under test: {@link BooleanDataObject#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BooleanDataObject BooleanDataObject.clone()"})
  public void testClone_givenBooleanDataObjectValueIsValue_thenReturnNotValue() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setValue("Value");

    // Act
    BooleanDataObject actualCloneResult = booleanDataObject.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse((Boolean) actualCloneResult.getValue());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BooleanDataObject#clone()}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).
   *   <li>Then return Value is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanDataObject#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BooleanDataObject BooleanDataObject.clone()"})
  public void testClone_givenBooleanDataObject_thenReturnValueIsNull() {
    // Arrange and Act
    BooleanDataObject actualCloneResult = new BooleanDataObject().clone();

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
   * Test new {@link BooleanDataObject} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link BooleanDataObject}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BooleanDataObject.<init>()"})
  public void testNewBooleanDataObject() {
    // Arrange and Act
    BooleanDataObject actualBooleanDataObject = new BooleanDataObject();

    // Assert
    assertNull(actualBooleanDataObject.getValue());
    assertNull(actualBooleanDataObject.getId());
    assertNull(actualBooleanDataObject.getDocumentation());
    assertNull(actualBooleanDataObject.getName());
    assertNull(actualBooleanDataObject.getParentContainer());
    assertNull(actualBooleanDataObject.getItemSubjectRef());
    assertEquals(0, actualBooleanDataObject.getXmlColumnNumber());
    assertEquals(0, actualBooleanDataObject.getXmlRowNumber());
    assertTrue(actualBooleanDataObject.getExecutionListeners().isEmpty());
    assertTrue(actualBooleanDataObject.getAttributes().isEmpty());
    assertTrue(actualBooleanDataObject.getExtensionElements().isEmpty());
  }
}
