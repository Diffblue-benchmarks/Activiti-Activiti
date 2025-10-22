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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SequenceFlowDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SequenceFlow#SequenceFlow()}
   *   <li>{@link SequenceFlow#setConditionExpression(String)}
   *   <li>{@link SequenceFlow#setSkipExpression(String)}
   *   <li>{@link SequenceFlow#setSourceFlowElement(FlowElement)}
   *   <li>{@link SequenceFlow#setSourceRef(String)}
   *   <li>{@link SequenceFlow#setTargetFlowElement(FlowElement)}
   *   <li>{@link SequenceFlow#setTargetRef(String)}
   *   <li>{@link SequenceFlow#setWaypoints(List)}
   *   <li>{@link SequenceFlow#toString()}
   *   <li>{@link SequenceFlow#getConditionExpression()}
   *   <li>{@link SequenceFlow#getSkipExpression()}
   *   <li>{@link SequenceFlow#getSourceFlowElement()}
   *   <li>{@link SequenceFlow#getSourceRef()}
   *   <li>{@link SequenceFlow#getTargetFlowElement()}
   *   <li>{@link SequenceFlow#getTargetRef()}
   *   <li>{@link SequenceFlow#getWaypoints()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SequenceFlow.<init>()", "void SequenceFlow.<init>(String, String)",
      "String SequenceFlow.getConditionExpression()", "String SequenceFlow.getSkipExpression()",
      "FlowElement SequenceFlow.getSourceFlowElement()", "String SequenceFlow.getSourceRef()",
      "FlowElement SequenceFlow.getTargetFlowElement()", "String SequenceFlow.getTargetRef()",
      "List SequenceFlow.getWaypoints()", "void SequenceFlow.setConditionExpression(String)",
      "void SequenceFlow.setSkipExpression(String)", "void SequenceFlow.setSourceFlowElement(FlowElement)",
      "void SequenceFlow.setSourceRef(String)", "void SequenceFlow.setTargetFlowElement(FlowElement)",
      "void SequenceFlow.setTargetRef(String)", "void SequenceFlow.setWaypoints(List)",
      "String SequenceFlow.toString()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    SequenceFlow actualSequenceFlow = new SequenceFlow();
    actualSequenceFlow.setConditionExpression("Condition Expression");
    actualSequenceFlow.setSkipExpression("Skip Expression");
    AdhocSubProcess sourceFlowElement = new AdhocSubProcess();
    actualSequenceFlow.setSourceFlowElement(sourceFlowElement);
    actualSequenceFlow.setSourceRef("Source Ref");
    AdhocSubProcess targetFlowElement = new AdhocSubProcess();
    actualSequenceFlow.setTargetFlowElement(targetFlowElement);
    actualSequenceFlow.setTargetRef("Target Ref");
    ArrayList<Integer> waypoints = new ArrayList<>();
    actualSequenceFlow.setWaypoints(waypoints);
    String actualToStringResult = actualSequenceFlow.toString();
    String actualConditionExpression = actualSequenceFlow.getConditionExpression();
    String actualSkipExpression = actualSequenceFlow.getSkipExpression();
    FlowElement actualSourceFlowElement = actualSequenceFlow.getSourceFlowElement();
    String actualSourceRef = actualSequenceFlow.getSourceRef();
    FlowElement actualTargetFlowElement = actualSequenceFlow.getTargetFlowElement();
    String actualTargetRef = actualSequenceFlow.getTargetRef();
    List<Integer> actualWaypoints = actualSequenceFlow.getWaypoints();

    // Assert
    assertEquals("Condition Expression", actualConditionExpression);
    assertEquals("Skip Expression", actualSkipExpression);
    assertEquals("Source Ref --> Target Ref", actualToStringResult);
    assertEquals("Source Ref", actualSourceRef);
    assertEquals("Target Ref", actualTargetRef);
    assertNull(actualSequenceFlow.getId());
    assertNull(actualSequenceFlow.getDocumentation());
    assertNull(actualSequenceFlow.getName());
    assertNull(actualSequenceFlow.getParentContainer());
    assertEquals(0, actualSequenceFlow.getXmlColumnNumber());
    assertEquals(0, actualSequenceFlow.getXmlRowNumber());
    assertTrue(actualSequenceFlow.getExecutionListeners().isEmpty());
    assertTrue(actualWaypoints.isEmpty());
    assertTrue(actualSequenceFlow.getAttributes().isEmpty());
    assertTrue(actualSequenceFlow.getExtensionElements().isEmpty());
    assertSame(waypoints, actualWaypoints);
    assertSame(sourceFlowElement, actualSourceFlowElement);
    assertSame(targetFlowElement, actualTargetFlowElement);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Source Ref}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SequenceFlow#SequenceFlow(String, String)}
   *   <li>{@link SequenceFlow#setConditionExpression(String)}
   *   <li>{@link SequenceFlow#setSkipExpression(String)}
   *   <li>{@link SequenceFlow#setSourceFlowElement(FlowElement)}
   *   <li>{@link SequenceFlow#setSourceRef(String)}
   *   <li>{@link SequenceFlow#setTargetFlowElement(FlowElement)}
   *   <li>{@link SequenceFlow#setTargetRef(String)}
   *   <li>{@link SequenceFlow#setWaypoints(List)}
   *   <li>{@link SequenceFlow#toString()}
   *   <li>{@link SequenceFlow#getConditionExpression()}
   *   <li>{@link SequenceFlow#getSkipExpression()}
   *   <li>{@link SequenceFlow#getSourceFlowElement()}
   *   <li>{@link SequenceFlow#getSourceRef()}
   *   <li>{@link SequenceFlow#getTargetFlowElement()}
   *   <li>{@link SequenceFlow#getTargetRef()}
   *   <li>{@link SequenceFlow#getWaypoints()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SequenceFlow.<init>()", "void SequenceFlow.<init>(String, String)",
      "String SequenceFlow.getConditionExpression()", "String SequenceFlow.getSkipExpression()",
      "FlowElement SequenceFlow.getSourceFlowElement()", "String SequenceFlow.getSourceRef()",
      "FlowElement SequenceFlow.getTargetFlowElement()", "String SequenceFlow.getTargetRef()",
      "List SequenceFlow.getWaypoints()", "void SequenceFlow.setConditionExpression(String)",
      "void SequenceFlow.setSkipExpression(String)", "void SequenceFlow.setSourceFlowElement(FlowElement)",
      "void SequenceFlow.setSourceRef(String)", "void SequenceFlow.setTargetFlowElement(FlowElement)",
      "void SequenceFlow.setTargetRef(String)", "void SequenceFlow.setWaypoints(List)",
      "String SequenceFlow.toString()"})
  public void testGettersAndSetters_whenSourceRef() {
    // Arrange and Act
    SequenceFlow actualSequenceFlow = new SequenceFlow("Source Ref", "Target Ref");
    actualSequenceFlow.setConditionExpression("Condition Expression");
    actualSequenceFlow.setSkipExpression("Skip Expression");
    AdhocSubProcess sourceFlowElement = new AdhocSubProcess();
    actualSequenceFlow.setSourceFlowElement(sourceFlowElement);
    actualSequenceFlow.setSourceRef("Source Ref");
    AdhocSubProcess targetFlowElement = new AdhocSubProcess();
    actualSequenceFlow.setTargetFlowElement(targetFlowElement);
    actualSequenceFlow.setTargetRef("Target Ref");
    ArrayList<Integer> waypoints = new ArrayList<>();
    actualSequenceFlow.setWaypoints(waypoints);
    String actualToStringResult = actualSequenceFlow.toString();
    String actualConditionExpression = actualSequenceFlow.getConditionExpression();
    String actualSkipExpression = actualSequenceFlow.getSkipExpression();
    FlowElement actualSourceFlowElement = actualSequenceFlow.getSourceFlowElement();
    String actualSourceRef = actualSequenceFlow.getSourceRef();
    FlowElement actualTargetFlowElement = actualSequenceFlow.getTargetFlowElement();
    String actualTargetRef = actualSequenceFlow.getTargetRef();
    List<Integer> actualWaypoints = actualSequenceFlow.getWaypoints();

    // Assert
    assertEquals("Condition Expression", actualConditionExpression);
    assertEquals("Skip Expression", actualSkipExpression);
    assertEquals("Source Ref --> Target Ref", actualToStringResult);
    assertEquals("Source Ref", actualSourceRef);
    assertEquals("Target Ref", actualTargetRef);
    assertNull(actualSequenceFlow.getId());
    assertNull(actualSequenceFlow.getDocumentation());
    assertNull(actualSequenceFlow.getName());
    assertNull(actualSequenceFlow.getParentContainer());
    assertEquals(0, actualSequenceFlow.getXmlColumnNumber());
    assertEquals(0, actualSequenceFlow.getXmlRowNumber());
    assertTrue(actualSequenceFlow.getExecutionListeners().isEmpty());
    assertTrue(actualWaypoints.isEmpty());
    assertTrue(actualSequenceFlow.getAttributes().isEmpty());
    assertTrue(actualSequenceFlow.getExtensionElements().isEmpty());
    assertSame(waypoints, actualWaypoints);
    assertSame(sourceFlowElement, actualSourceFlowElement);
    assertSame(targetFlowElement, actualTargetFlowElement);
  }

  /**
   * Test {@link SequenceFlow#clone()}.
   * <p>
   * Method under test: {@link SequenceFlow#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SequenceFlow SequenceFlow.clone()"})
  public void testClone() {
    // Arrange and Act
    SequenceFlow actualCloneResult = (new SequenceFlow("Source Ref", "Target Ref")).clone();

    // Assert
    assertEquals("Source Ref", actualCloneResult.getSourceRef());
    assertEquals("Target Ref", actualCloneResult.getTargetRef());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getConditionExpression());
    assertNull(actualCloneResult.getSkipExpression());
    assertNull(actualCloneResult.getSourceFlowElement());
    assertNull(actualCloneResult.getTargetFlowElement());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getWaypoints().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }
}
