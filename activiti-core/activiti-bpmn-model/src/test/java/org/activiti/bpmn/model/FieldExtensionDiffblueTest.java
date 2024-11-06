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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class FieldExtensionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link FieldExtension}
   *   <li>{@link FieldExtension#setExpression(String)}
   *   <li>{@link FieldExtension#setFieldName(String)}
   *   <li>{@link FieldExtension#setStringValue(String)}
   *   <li>{@link FieldExtension#getExpression()}
   *   <li>{@link FieldExtension#getFieldName()}
   *   <li>{@link FieldExtension#getStringValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    FieldExtension actualFieldExtension = new FieldExtension();
    actualFieldExtension.setExpression("Expression");
    actualFieldExtension.setFieldName("Field Name");
    actualFieldExtension.setStringValue("42");
    String actualExpression = actualFieldExtension.getExpression();
    String actualFieldName = actualFieldExtension.getFieldName();

    // Assert that nothing has changed
    assertEquals("42", actualFieldExtension.getStringValue());
    assertEquals("Expression", actualExpression);
    assertEquals("Field Name", actualFieldName);
    assertEquals(0, actualFieldExtension.getXmlColumnNumber());
    assertEquals(0, actualFieldExtension.getXmlRowNumber());
    assertTrue(actualFieldExtension.getAttributes().isEmpty());
    assertTrue(actualFieldExtension.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link FieldExtension#clone()}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldExtension#clone()}
   */
  @Test
  public void testClone_givenFieldExtension() {
    // Arrange and Act
    FieldExtension actualCloneResult = (new FieldExtension()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getExpression());
    assertNull(actualCloneResult.getFieldName());
    assertNull(actualCloneResult.getStringValue());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link FieldExtension#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldExtension#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExtensionElements(extensionElements);

    // Act
    FieldExtension actualCloneResult = fieldExtension.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getExpression());
    assertNull(actualCloneResult.getFieldName());
    assertNull(actualCloneResult.getStringValue());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link FieldExtension#setValues(FieldExtension)} with
   * {@code otherExtension}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldExtension#setValues(FieldExtension)}
   */
  @Test
  public void testSetValuesWithOtherExtension_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.addExtensionElement(extensionElement);

    // Act
    fieldExtension.setValues(new FieldExtension());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }
}
