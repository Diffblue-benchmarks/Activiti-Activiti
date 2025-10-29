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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class FlowNodeDiffblueTest {
  /**
   * Method under test: {@link FlowNode#isAsynchronous()}
   */
  @Test
  public void testIsAsynchronous() {
    // Arrange, Act and Assert
    assertFalse((new AdhocSubProcess()).isAsynchronous());
  }

  /**
   * Method under test: {@link FlowNode#isAsynchronous()}
   */
  @Test
  public void testIsAsynchronous2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setAsynchronous(true);

    // Act and Assert
    assertTrue(adhocSubProcess.isAsynchronous());
  }

  /**
   * Method under test: {@link FlowNode#setAsynchronous(boolean)}
   */
  @Test
  public void testSetAsynchronous() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setAsynchronous(true);

    // Assert
    assertTrue(adhocSubProcess.isAsynchronous());
  }

  /**
   * Method under test: {@link FlowNode#isExclusive()}
   */
  @Test
  public void testIsExclusive() {
    // Arrange, Act and Assert
    assertTrue((new AdhocSubProcess()).isExclusive());
  }

  /**
   * Method under test: {@link FlowNode#isExclusive()}
   */
  @Test
  public void testIsExclusive2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setExclusive(false);

    // Act and Assert
    assertFalse(adhocSubProcess.isExclusive());
  }

  /**
   * Method under test: {@link FlowNode#setExclusive(boolean)}
   */
  @Test
  public void testSetExclusive() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setExclusive(true);

    // Assert
    assertFalse(adhocSubProcess.isNotExclusive());
    assertTrue(adhocSubProcess.isExclusive());
  }

  /**
   * Method under test: {@link FlowNode#setExclusive(boolean)}
   */
  @Test
  public void testSetExclusive2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setExclusive(false);

    // Assert
    assertFalse(adhocSubProcess.isExclusive());
    assertTrue(adhocSubProcess.isNotExclusive());
  }

  /**
   * Method under test: {@link FlowNode#isNotExclusive()}
   */
  @Test
  public void testIsNotExclusive() {
    // Arrange, Act and Assert
    assertFalse((new AdhocSubProcess()).isNotExclusive());
  }

  /**
   * Method under test: {@link FlowNode#isNotExclusive()}
   */
  @Test
  public void testIsNotExclusive2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setNotExclusive(true);

    // Act and Assert
    assertTrue(adhocSubProcess.isNotExclusive());
  }

  /**
   * Method under test: {@link FlowNode#setNotExclusive(boolean)}
   */
  @Test
  public void testSetNotExclusive() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setNotExclusive(true);

    // Assert
    assertFalse(adhocSubProcess.isExclusive());
    assertTrue(adhocSubProcess.isNotExclusive());
  }

  /**
   * Method under test: {@link FlowNode#getBehavior()}
   */
  @Test
  public void testGetBehavior() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getBehavior());
  }

  /**
   * Method under test: {@link FlowNode#setBehavior(Object)}
   */
  @Test
  public void testSetBehavior() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setBehavior("Behavior");

    // Assert
    assertEquals("Behavior", adhocSubProcess.getBehavior());
  }

  /**
   * Method under test: {@link FlowNode#getIncomingFlows()}
   */
  @Test
  public void testGetIncomingFlows() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    List<SequenceFlow> actualIncomingFlows = adhocSubProcess.getIncomingFlows();

    // Assert
    assertTrue(actualIncomingFlows.isEmpty());
    assertSame(adhocSubProcess.incomingFlows, actualIncomingFlows);
  }

  /**
   * Method under test: {@link FlowNode#setIncomingFlows(List)}
   */
  @Test
  public void testSetIncomingFlows() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<SequenceFlow> incomingFlows = new ArrayList<>();

    // Act
    adhocSubProcess.setIncomingFlows(incomingFlows);

    // Assert
    assertSame(incomingFlows, adhocSubProcess.getIncomingFlows());
  }

  /**
   * Method under test: {@link FlowNode#setIncomingFlows(List)}
   */
  @Test
  public void testSetIncomingFlows2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<SequenceFlow> incomingFlows = new ArrayList<>();
    incomingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    // Act
    adhocSubProcess.setIncomingFlows(incomingFlows);

    // Assert
    assertSame(incomingFlows, adhocSubProcess.getIncomingFlows());
  }

  /**
   * Method under test: {@link FlowNode#setIncomingFlows(List)}
   */
  @Test
  public void testSetIncomingFlows3() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<SequenceFlow> incomingFlows = new ArrayList<>();
    incomingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));
    incomingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    // Act
    adhocSubProcess.setIncomingFlows(incomingFlows);

    // Assert
    assertSame(incomingFlows, adhocSubProcess.getIncomingFlows());
  }

  /**
   * Method under test: {@link FlowNode#getOutgoingFlows()}
   */
  @Test
  public void testGetOutgoingFlows() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    List<SequenceFlow> actualOutgoingFlows = adhocSubProcess.getOutgoingFlows();

    // Assert
    assertTrue(actualOutgoingFlows.isEmpty());
    assertSame(adhocSubProcess.outgoingFlows, actualOutgoingFlows);
  }

  /**
   * Method under test: {@link FlowNode#setOutgoingFlows(List)}
   */
  @Test
  public void testSetOutgoingFlows() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();

    // Act
    adhocSubProcess.setOutgoingFlows(outgoingFlows);

    // Assert
    assertSame(outgoingFlows, adhocSubProcess.getOutgoingFlows());
  }

  /**
   * Method under test: {@link FlowNode#setOutgoingFlows(List)}
   */
  @Test
  public void testSetOutgoingFlows2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    // Act
    adhocSubProcess.setOutgoingFlows(outgoingFlows);

    // Assert
    assertSame(outgoingFlows, adhocSubProcess.getOutgoingFlows());
  }

  /**
   * Method under test: {@link FlowNode#setOutgoingFlows(List)}
   */
  @Test
  public void testSetOutgoingFlows3() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    // Act
    adhocSubProcess.setOutgoingFlows(outgoingFlows);

    // Assert
    assertSame(outgoingFlows, adhocSubProcess.getOutgoingFlows());
  }

  /**
   * Method under test: {@link FlowNode#setValues(FlowNode)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    AdhocSubProcess otherNode = new AdhocSubProcess();

    // Act
    adhocSubProcess.setValues((FlowNode) otherNode);

    // Assert
    assertNull(otherNode.getId());
    assertNull(otherNode.getDocumentation());
    assertNull(otherNode.getName());
    assertFalse(otherNode.isAsynchronous());
    assertFalse(otherNode.isNotExclusive());
    assertTrue(otherNode.isExclusive());
  }

  /**
   * Method under test: {@link FlowNode#setValues(FlowNode)}
   */
  @Test
  public void testSetValues2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    BoundaryEvent otherNode = mock(BoundaryEvent.class);
    when(otherNode.isAsynchronous()).thenReturn(true);
    when(otherNode.isNotExclusive()).thenReturn(true);
    when(otherNode.getId()).thenReturn("42");
    when(otherNode.getDocumentation()).thenReturn("Documentation");
    when(otherNode.getName()).thenReturn("Name");
    when(otherNode.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherNode.getAttributes()).thenReturn(new HashMap<>());
    when(otherNode.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherNode);

    // Assert
    verify(otherNode, atLeast(1)).getAttributes();
    verify(otherNode, atLeast(1)).getExtensionElements();
    verify(otherNode).getId();
    verify(otherNode).getDocumentation();
    verify(otherNode, atLeast(1)).getExecutionListeners();
    verify(otherNode).getName();
    verify(otherNode).isAsynchronous();
    verify(otherNode).isNotExclusive();
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    assertFalse(adhocSubProcess.isExclusive());
    assertTrue(adhocSubProcess.isAsynchronous());
    assertTrue(adhocSubProcess.isNotExclusive());
  }
}
