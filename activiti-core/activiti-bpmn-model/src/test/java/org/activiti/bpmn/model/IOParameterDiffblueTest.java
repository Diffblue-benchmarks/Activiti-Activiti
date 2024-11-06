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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class IOParameterDiffblueTest {
  /**
   * Test {@link IOParameter#clone()}.
   * <ul>
   *   <li>Given {@link IOParameter} (default constructor) ExtensionElements is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOParameter#clone()}
   */
  @Test
  public void testClone_givenIOParameterExtensionElementsIsNull_thenReturnIdIsNull() {
    // Arrange
    IOParameter ioParameter = new IOParameter();
    ioParameter.setExtensionElements(null);
    ioParameter.setAttributes(null);

    // Act
    IOParameter actualCloneResult = ioParameter.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getSource());
    assertNull(actualCloneResult.getSourceExpression());
    assertNull(actualCloneResult.getTarget());
    assertNull(actualCloneResult.getTargetExpression());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link IOParameter#clone()}.
   * <ul>
   *   <li>Given {@link IOParameter} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOParameter#clone()}
   */
  @Test
  public void testClone_givenIOParameter_thenReturnIdIsNull() {
    // Arrange and Act
    IOParameter actualCloneResult = (new IOParameter()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getSource());
    assertNull(actualCloneResult.getSourceExpression());
    assertNull(actualCloneResult.getTarget());
    assertNull(actualCloneResult.getTargetExpression());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link IOParameter#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOParameter#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    IOParameter ioParameter = new IOParameter();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    ioParameter.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = ioParameter.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link IOParameter#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOParameter#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    IOParameter ioParameter = new IOParameter();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    ioParameter.addAttribute(attribute);
    ioParameter.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = ioParameter.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link IOParameter#setValues(IOParameter)} with {@code IOParameter}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOParameter#setValues(IOParameter)}
   */
  @Test
  public void testSetValuesWithIOParameter_thenCallsGetName() {
    // Arrange
    IOParameter ioParameter = new IOParameter();
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    IOParameter otherElement = new IOParameter();
    otherElement.addAttribute(attribute);

    // Act
    ioParameter.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link IOParameter}
   *   <li>{@link IOParameter#setSource(String)}
   *   <li>{@link IOParameter#setSourceExpression(String)}
   *   <li>{@link IOParameter#setTarget(String)}
   *   <li>{@link IOParameter#setTargetExpression(String)}
   *   <li>{@link IOParameter#getSource()}
   *   <li>{@link IOParameter#getSourceExpression()}
   *   <li>{@link IOParameter#getTarget()}
   *   <li>{@link IOParameter#getTargetExpression()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    IOParameter actualIoParameter = new IOParameter();
    actualIoParameter.setSource("Source");
    actualIoParameter.setSourceExpression("Source Expression");
    actualIoParameter.setTarget("Target");
    actualIoParameter.setTargetExpression("Target Expression");
    String actualSource = actualIoParameter.getSource();
    String actualSourceExpression = actualIoParameter.getSourceExpression();
    String actualTarget = actualIoParameter.getTarget();

    // Assert that nothing has changed
    assertEquals("Source Expression", actualSourceExpression);
    assertEquals("Source", actualSource);
    assertEquals("Target Expression", actualIoParameter.getTargetExpression());
    assertEquals("Target", actualTarget);
    assertEquals(0, actualIoParameter.getXmlColumnNumber());
    assertEquals(0, actualIoParameter.getXmlRowNumber());
    assertTrue(actualIoParameter.getAttributes().isEmpty());
    assertTrue(actualIoParameter.getExtensionElements().isEmpty());
  }
}
