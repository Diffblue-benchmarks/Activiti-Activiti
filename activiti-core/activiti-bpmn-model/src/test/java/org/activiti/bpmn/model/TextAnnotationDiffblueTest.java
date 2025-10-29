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

public class TextAnnotationDiffblueTest {
  /**
   * Method under test: {@link TextAnnotation#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    TextAnnotation actualCloneResult = (new TextAnnotation()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getText());
    assertNull(actualCloneResult.getTextFormat());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link TextAnnotation#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    TextAnnotation textAnnotation = new TextAnnotation();
    textAnnotation.setExtensionElements(extensionElements);

    // Act
    TextAnnotation actualCloneResult = textAnnotation.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getText());
    assertNull(actualCloneResult.getTextFormat());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link TextAnnotation#setValues(TextAnnotation)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    TextAnnotation textAnnotation = new TextAnnotation();
    textAnnotation.addExtensionElement(extensionElement);

    // Act
    textAnnotation.setValues(new TextAnnotation());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TextAnnotation}
   *   <li>{@link TextAnnotation#setText(String)}
   *   <li>{@link TextAnnotation#setTextFormat(String)}
   *   <li>{@link TextAnnotation#getText()}
   *   <li>{@link TextAnnotation#getTextFormat()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    TextAnnotation actualTextAnnotation = new TextAnnotation();
    actualTextAnnotation.setText("Text");
    actualTextAnnotation.setTextFormat("Text Format");
    String actualText = actualTextAnnotation.getText();

    // Assert that nothing has changed
    assertEquals("Text Format", actualTextAnnotation.getTextFormat());
    assertEquals("Text", actualText);
    assertEquals(0, actualTextAnnotation.getXmlColumnNumber());
    assertEquals(0, actualTextAnnotation.getXmlRowNumber());
    assertTrue(actualTextAnnotation.getAttributes().isEmpty());
    assertTrue(actualTextAnnotation.getExtensionElements().isEmpty());
  }
}
