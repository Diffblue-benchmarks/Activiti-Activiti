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
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class DateDataObjectDiffblueTest {
  /**
   * Test {@link DateDataObject#clone()}.
   * <ul>
   *   <li>Given {@link DateDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DateDataObject#clone()}
   */
  @Test
  public void testClone_givenDateDataObject() {
    // Arrange and Act
    DateDataObject actualCloneResult = (new DateDataObject()).clone();

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
   * Test {@link DateDataObject#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateDataObject#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    DateDataObject dateDataObject = new DateDataObject();
    dateDataObject.setExtensionElements(extensionElements);

    // Act
    DateDataObject actualCloneResult = dateDataObject.clone();

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
   * Test new {@link DateDataObject} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link DateDataObject}
   */
  @Test
  public void testNewDateDataObject() {
    // Arrange and Act
    DateDataObject actualDateDataObject = new DateDataObject();

    // Assert
    assertNull(actualDateDataObject.getValue());
    assertNull(actualDateDataObject.getId());
    assertNull(actualDateDataObject.getDocumentation());
    assertNull(actualDateDataObject.getName());
    assertNull(actualDateDataObject.getParentContainer());
    assertNull(actualDateDataObject.getItemSubjectRef());
    assertEquals(0, actualDateDataObject.getXmlColumnNumber());
    assertEquals(0, actualDateDataObject.getXmlRowNumber());
    assertTrue(actualDateDataObject.getExecutionListeners().isEmpty());
    assertTrue(actualDateDataObject.getAttributes().isEmpty());
    assertTrue(actualDateDataObject.getExtensionElements().isEmpty());
  }
}
