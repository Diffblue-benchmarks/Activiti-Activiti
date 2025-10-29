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
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class FlowElementDiffblueTest {
  /**
   * Method under test: {@link FlowElement#getName()}
   */
  @Test
  public void testGetName() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getName());
  }

  /**
   * Method under test: {@link FlowElement#setName(String)}
   */
  @Test
  public void testSetName() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setName("Name");

    // Assert
    assertEquals("Name", adhocSubProcess.getName());
  }

  /**
   * Method under test: {@link FlowElement#getDocumentation()}
   */
  @Test
  public void testGetDocumentation() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getDocumentation());
  }

  /**
   * Method under test: {@link FlowElement#setDocumentation(String)}
   */
  @Test
  public void testSetDocumentation() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setDocumentation("Documentation");

    // Assert
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
  }

  /**
   * Method under test: {@link FlowElement#getExecutionListeners()}
   */
  @Test
  public void testGetExecutionListeners() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    List<ActivitiListener> actualExecutionListeners = adhocSubProcess.getExecutionListeners();

    // Assert
    assertTrue(actualExecutionListeners.isEmpty());
    assertSame(adhocSubProcess.executionListeners, actualExecutionListeners);
  }

  /**
   * Method under test: {@link FlowElement#setExecutionListeners(List)}
   */
  @Test
  public void testSetExecutionListeners() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();

    // Act
    adhocSubProcess.setExecutionListeners(executionListeners);

    // Assert
    assertSame(executionListeners, adhocSubProcess.getExecutionListeners());
  }

  /**
   * Method under test: {@link FlowElement#setExecutionListeners(List)}
   */
  @Test
  public void testSetExecutionListeners2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(new ActivitiListener());

    // Act
    adhocSubProcess.setExecutionListeners(executionListeners);

    // Assert
    assertSame(executionListeners, adhocSubProcess.getExecutionListeners());
  }

  /**
   * Method under test: {@link FlowElement#setExecutionListeners(List)}
   */
  @Test
  public void testSetExecutionListeners3() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(new ActivitiListener());
    executionListeners.add(new ActivitiListener());

    // Act
    adhocSubProcess.setExecutionListeners(executionListeners);

    // Assert
    assertSame(executionListeners, adhocSubProcess.getExecutionListeners());
  }

  /**
   * Method under test: {@link FlowElement#getParentContainer()}
   */
  @Test
  public void testGetParentContainer() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getParentContainer());
  }

  /**
   * Method under test: {@link FlowElement#getSubProcess()}
   */
  @Test
  public void testGetSubProcess() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getSubProcess());
  }

  /**
   * Method under test: {@link FlowElement#getSubProcess()}
   */
  @Test
  public void testGetSubProcess2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    SubProcess parentContainer = new SubProcess();
    adhocSubProcess.setParentContainer(parentContainer);

    // Act and Assert
    assertSame(parentContainer, adhocSubProcess.getSubProcess());
  }

  /**
   * Method under test:
   * {@link FlowElement#setParentContainer(FlowElementsContainer)}
   */
  @Test
  public void testSetParentContainer() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    AdhocSubProcess parentContainer = new AdhocSubProcess();

    // Act
    adhocSubProcess.setParentContainer(parentContainer);

    // Assert
    assertNull(parentContainer.getParentContainer());
    assertNull(parentContainer.getSubProcess());
  }

  /**
   * Method under test:
   * {@link FlowElement#setParentContainer(FlowElementsContainer)}
   */
  @Test
  public void testSetParentContainer2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    EventSubProcess parentContainer = mock(EventSubProcess.class);

    // Act
    adhocSubProcess.setParentContainer(parentContainer);

    // Assert
    assertSame(parentContainer, adhocSubProcess.getParentContainer());
    assertSame(parentContainer, adhocSubProcess.getSubProcess());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    AdhocSubProcess otherElement = new AdhocSubProcess();

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExtensionElements(null);
    otherElement.setAttributes(null);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues3() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExecutionListeners(null);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExecutionListeners());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues4() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(null);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExecutionListeners(executionListeners);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertSame(executionListeners, otherElement.getExecutionListeners());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues5() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(new ArrayList<>());

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExecutionListeners(executionListeners);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertSame(executionListeners, otherElement.getExecutionListeners());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues6() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExecutionListeners(executionListeners);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertSame(executionListeners, otherElement.getExecutionListeners());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues7() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    assertTrue(adhocSubProcess.getExecutionListeners().isEmpty());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues8() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());
    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    List<ActivitiListener> executionListeners = adhocSubProcess.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    ActivitiListener getResult = executionListeners.get(0);
    assertNull(getResult.getInstance());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getEvent());
    assertNull(getResult.getImplementation());
    assertNull(getResult.getImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues9() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());
    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    assertTrue(adhocSubProcess.getExecutionListeners().isEmpty());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues10() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("foo", new ArrayList<>());
    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    assertTrue(adhocSubProcess.getExecutionListeners().isEmpty());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues11() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());
    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(stringListMap);

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    assertTrue(adhocSubProcess.getExecutionListeners().isEmpty());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues12() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("foo", new ArrayList<>());
    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(stringListMap);

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    assertTrue(adhocSubProcess.getExecutionListeners().isEmpty());
  }

  /**
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  public void testSetValues13() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);
    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    List<ActivitiListener> executionListeners = adhocSubProcess.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    assertSame(activitiListener2, executionListeners.get(0));
  }
}
