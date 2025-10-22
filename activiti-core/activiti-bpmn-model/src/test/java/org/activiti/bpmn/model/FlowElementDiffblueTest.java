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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class FlowElementDiffblueTest {
  /**
   * Test {@link FlowElement#getName()}.
   * <p>
   * Method under test: {@link FlowElement#getName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String FlowElement.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getName());
  }

  /**
   * Test {@link FlowElement#setName(String)}.
   * <p>
   * Method under test: {@link FlowElement#setName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setName(String)"})
  public void testSetName() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setName("Name");

    // Assert
    assertEquals("Name", adhocSubProcess.getName());
  }

  /**
   * Test {@link FlowElement#getDocumentation()}.
   * <p>
   * Method under test: {@link FlowElement#getDocumentation()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String FlowElement.getDocumentation()"})
  public void testGetDocumentation() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getDocumentation());
  }

  /**
   * Test {@link FlowElement#setDocumentation(String)}.
   * <p>
   * Method under test: {@link FlowElement#setDocumentation(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setDocumentation(String)"})
  public void testSetDocumentation() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setDocumentation("Documentation");

    // Assert
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
  }

  /**
   * Test {@link FlowElement#getExecutionListeners()}.
   * <p>
   * Method under test: {@link FlowElement#getExecutionListeners()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List FlowElement.getExecutionListeners()"})
  public void testGetExecutionListeners() {
    // Arrange, Act and Assert
    assertTrue((new AdhocSubProcess()).getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link FlowElement#setExecutionListeners(List)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setExecutionListeners(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setExecutionListeners(List)"})
  public void testSetExecutionListeners_givenActivitiListener_whenArrayListAddActivitiListener() {
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
   * Test {@link FlowElement#setExecutionListeners(List)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setExecutionListeners(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setExecutionListeners(List)"})
  public void testSetExecutionListeners_givenActivitiListener_whenArrayListAddActivitiListener2() {
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
   * Test {@link FlowElement#setExecutionListeners(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setExecutionListeners(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setExecutionListeners(List)"})
  public void testSetExecutionListeners_whenArrayList() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();

    // Act
    adhocSubProcess.setExecutionListeners(executionListeners);

    // Assert
    assertSame(executionListeners, adhocSubProcess.getExecutionListeners());
  }

  /**
   * Test {@link FlowElement#getParentContainer()}.
   * <p>
   * Method under test: {@link FlowElement#getParentContainer()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"FlowElementsContainer FlowElement.getParentContainer()"})
  public void testGetParentContainer() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getParentContainer());
  }

  /**
   * Test {@link FlowElement#getSubProcess()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#getSubProcess()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SubProcess FlowElement.getSubProcess()"})
  public void testGetSubProcess_givenAdhocSubProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getSubProcess());
  }

  /**
   * Test {@link FlowElement#getSubProcess()}.
   * <ul>
   *   <li>Then return {@link SubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#getSubProcess()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SubProcess FlowElement.getSubProcess()"})
  public void testGetSubProcess_thenReturnSubProcess() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    SubProcess parentContainer = new SubProcess();
    adhocSubProcess.setParentContainer(parentContainer);

    // Act and Assert
    assertSame(parentContainer, adhocSubProcess.getSubProcess());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement() {
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
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement2() {
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
    List<ActivitiListener> executionListeners = adhocSubProcess.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    assertSame(activitiListener2, executionListeners.get(0));
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenActivitiListenerFieldExtensionsIsArrayList() {
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

    // Assert that nothing has changed
    assertSame(executionListeners, otherElement.getExecutionListeners());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenActivitiListenerFieldExtensionsIsNull() {
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

    // Assert that nothing has changed
    assertSame(executionListeners, otherElement.getExecutionListeners());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenArrayListAddFieldExtension() {
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

    // Assert that nothing has changed
    assertSame(executionListeners, otherElement.getExecutionListeners());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenArrayList_thenAdhocSubProcessIdIs42() {
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
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenHashMap42IsArrayList_thenAdhocSubProcessIdIs42() {
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
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenHashMap42IsArrayList_thenAdhocSubProcessIdIs422() {
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
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenHashMapFooIsArrayList() {
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
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenHashMapFooIsArrayList2() {
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
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_thenAdhocSubProcessIdIsNull() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExecutionListeners(null);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert that nothing has changed
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExecutionListeners());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_whenAdhocSubProcess() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    AdhocSubProcess otherElement = new AdhocSubProcess();

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) ExtensionElements is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_whenAdhocSubProcessExtensionElementsIsNull() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExtensionElements(null);
    otherElement.setAttributes(null);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }
}
