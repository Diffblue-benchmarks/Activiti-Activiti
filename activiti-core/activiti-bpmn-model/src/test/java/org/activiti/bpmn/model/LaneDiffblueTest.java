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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class LaneDiffblueTest {
  /**
   * Test {@link Lane#clone()}.
   * <ul>
   *   <li>Given {@link Lane} (default constructor) ExtensionElements is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Lane#clone()}
   */
  @Test
  public void testClone_givenLaneExtensionElementsIsNull_thenReturnIdIsNull() {
    // Arrange
    Lane lane = new Lane();
    lane.setExtensionElements(null);
    lane.setAttributes(null);

    // Act
    Lane actualCloneResult = lane.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getFlowReferences().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Lane#clone()}.
   * <ul>
   *   <li>Given {@link Lane} (default constructor) FlowReferences is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Lane#clone()}
   */
  @Test
  public void testClone_givenLaneFlowReferencesIsNull_thenReturnIdIsNull() {
    // Arrange
    Lane lane = new Lane();
    lane.setFlowReferences(null);

    // Act
    Lane actualCloneResult = lane.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getFlowReferences().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Lane#clone()}.
   * <ul>
   *   <li>Given {@link Lane} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Lane#clone()}
   */
  @Test
  public void testClone_givenLane_thenReturnIdIsNull() {
    // Arrange and Act
    Lane actualCloneResult = (new Lane()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getFlowReferences().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Lane#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Lane#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    Lane lane = new Lane();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    lane.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = lane.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Lane#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Lane#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    Lane lane = new Lane();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    lane.addAttribute(attribute);
    lane.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = lane.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Lane#setValues(Lane)} with {@code Lane}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute} {@link ExtensionAttribute#getName()}
   * return {@code Name}.</li>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Lane#setValues(Lane)}
   */
  @Test
  public void testSetValuesWithLane_givenExtensionAttributeGetNameReturnName_thenCallsGetName() {
    // Arrange
    Lane lane = new Lane();
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    Lane otherElement = new Lane();
    otherElement.addAttribute(attribute);

    // Act
    lane.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Lane}
   *   <li>{@link Lane#setFlowReferences(List)}
   *   <li>{@link Lane#setName(String)}
   *   <li>{@link Lane#setParentProcess(Process)}
   *   <li>{@link Lane#getFlowReferences()}
   *   <li>{@link Lane#getName()}
   *   <li>{@link Lane#getParentProcess()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Lane actualLane = new Lane();
    ArrayList<String> flowReferences = new ArrayList<>();
    actualLane.setFlowReferences(flowReferences);
    actualLane.setName("Name");
    Process parentProcess = new Process();
    actualLane.setParentProcess(parentProcess);
    List<String> actualFlowReferences = actualLane.getFlowReferences();
    String actualName = actualLane.getName();
    Process actualParentProcess = actualLane.getParentProcess();

    // Assert that nothing has changed
    assertEquals("Name", actualName);
    assertEquals(0, actualLane.getXmlColumnNumber());
    assertEquals(0, actualLane.getXmlRowNumber());
    assertTrue(actualFlowReferences.isEmpty());
    assertTrue(actualLane.getAttributes().isEmpty());
    assertTrue(actualLane.getExtensionElements().isEmpty());
    assertSame(flowReferences, actualFlowReferences);
    assertSame(parentProcess, actualParentProcess);
  }
}
