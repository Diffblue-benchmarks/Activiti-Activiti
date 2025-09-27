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

public class LongDataObjectDiffblueTest {
  /**
   * Test {@link LongDataObject#setValue(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then {@link LongDataObject} (default constructor) Value longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link LongDataObject#setValue(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LongDataObject.setValue(Object)"})
  public void testSetValue_whenFortyTwo_thenLongDataObjectValueLongValueIsFortyTwo() {
    // Arrange
    LongDataObject longDataObject = new LongDataObject();

    // Act
    longDataObject.setValue(42);

    // Assert
    assertEquals(42L, ((Long) longDataObject.getValue()).longValue());
  }

  /**
   * Test {@link LongDataObject#clone()}.
   *
   * <ul>
   *   <li>Given {@link LongDataObject} (default constructor) Value is forty-two.
   *   <li>Then return Value longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link LongDataObject#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LongDataObject LongDataObject.clone()"})
  public void testClone_givenLongDataObjectValueIsFortyTwo_thenReturnValueLongValueIsFortyTwo() {
    // Arrange
    LongDataObject longDataObject = new LongDataObject();
    longDataObject.setValue(42);

    // Act
    LongDataObject actualCloneResult = longDataObject.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertEquals(42L, ((Long) actualCloneResult.getValue()).longValue());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link LongDataObject#clone()}.
   *
   * <ul>
   *   <li>Given {@link LongDataObject} (default constructor).
   *   <li>Then return Value is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LongDataObject#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"LongDataObject LongDataObject.clone()"})
  public void testClone_givenLongDataObject_thenReturnValueIsNull() {
    // Arrange and Act
    LongDataObject actualCloneResult = new LongDataObject().clone();

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
   * Test new {@link LongDataObject} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link LongDataObject}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LongDataObject.<init>()"})
  public void testNewLongDataObject() {
    // Arrange and Act
    LongDataObject actualLongDataObject = new LongDataObject();

    // Assert
    assertNull(actualLongDataObject.getValue());
    assertNull(actualLongDataObject.getId());
    assertNull(actualLongDataObject.getDocumentation());
    assertNull(actualLongDataObject.getName());
    assertNull(actualLongDataObject.getParentContainer());
    assertNull(actualLongDataObject.getItemSubjectRef());
    assertEquals(0, actualLongDataObject.getXmlColumnNumber());
    assertEquals(0, actualLongDataObject.getXmlRowNumber());
    assertTrue(actualLongDataObject.getExecutionListeners().isEmpty());
    assertTrue(actualLongDataObject.getAttributes().isEmpty());
    assertTrue(actualLongDataObject.getExtensionElements().isEmpty());
  }
}
