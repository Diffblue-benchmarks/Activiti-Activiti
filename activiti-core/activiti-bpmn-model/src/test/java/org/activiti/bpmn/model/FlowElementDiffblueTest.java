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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class FlowElementDiffblueTest {
  /**
   * Test {@link FlowElement#getName()}.
   *
   * <p>Method under test: {@link FlowElement#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String FlowElement.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertNull(new AdhocSubProcess().getName());
  }

  /**
   * Test {@link FlowElement#setName(String)}.
   *
   * <p>Method under test: {@link FlowElement#setName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link FlowElement#getDocumentation()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String FlowElement.getDocumentation()"})
  public void testGetDocumentation() {
    // Arrange, Act and Assert
    assertNull(new AdhocSubProcess().getDocumentation());
  }

  /**
   * Test {@link FlowElement#setDocumentation(String)}.
   *
   * <p>Method under test: {@link FlowElement#setDocumentation(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link FlowElement#getExecutionListeners()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List FlowElement.getExecutionListeners()"})
  public void testGetExecutionListeners() {
    // Arrange, Act and Assert
    assertTrue(new AdhocSubProcess().getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link FlowElement#setExecutionListeners(List)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).
   *   <li>When {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setExecutionListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).
   *   <li>When {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setExecutionListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setExecutionListeners(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link FlowElement#getParentContainer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElementsContainer FlowElement.getParentContainer()"})
  public void testGetParentContainer() {
    // Arrange, Act and Assert
    assertNull(new AdhocSubProcess().getParentContainer());
  }

  /**
   * Test {@link FlowElement#getSubProcess()}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#getSubProcess()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess FlowElement.getSubProcess()"})
  public void testGetSubProcess_givenAdhocSubProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new AdhocSubProcess().getSubProcess());
  }

  /**
   * Test {@link FlowElement#getSubProcess()}.
   *
   * <ul>
   *   <li>Then return {@link SubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#getSubProcess()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
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
    List<FieldExtension> fieldExtensions2 = executionListeners.get(0).getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    FieldExtension getResult = fieldExtensions2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertNull(getResult.getFieldName());
    assertNull(getResult.getStringValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
    List<ActivitiListener> executionListeners2 = otherElement.getExecutionListeners();
    assertEquals(1, executionListeners2.size());
    assertSame(executionListeners, executionListeners2);
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenHashMap42IsArrayList_thenAdhocSubProcessIdIs42() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("Key", new ArrayList<>());

    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenHashMap42IsArrayList_thenAdhocSubProcessIdIs422() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("Key", new ArrayList<>());

    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(stringListMap);

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Attributes is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_thenAdhocSubProcessAttributesIsHashMap() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("Key", extensionAttributeList);

    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    assertEquals(stringListMap, adhocSubProcess.getAttributes());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ExecutionListeners is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_thenAdhocSubProcessExecutionListenersIsArrayList() {
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
    List<ActivitiListener> executionListeners2 = otherElement.getExecutionListeners();
    assertEquals(1, executionListeners2.size());
    assertSame(executionListeners, executionListeners2);
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ExecutionListeners is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_thenAdhocSubProcessExecutionListenersIsArrayList2() {
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
    List<ActivitiListener> executionListeners2 = otherElement.getExecutionListeners();
    assertEquals(1, executionListeners2.size());
    assertSame(executionListeners, executionListeners2);
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_thenAdhocSubProcessIdIs42() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_thenAdhocSubProcessIdIs422() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("Key", new ArrayList<>());

    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_thenAdhocSubProcessIdIs423() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("Key", new ArrayList<>());

    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(stringListMap);

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Then calls {@link FieldExtension#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_thenCallsClone() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.clone()).thenReturn(new FieldExtension());

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExecutionListeners(executionListeners);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert that nothing has changed
    verify(fieldExtension).clone();
    List<ActivitiListener> executionListeners2 = otherElement.getExecutionListeners();
    assertEquals(1, executionListeners2.size());
    assertSame(executionListeners, executionListeners2);
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) ExtensionElements is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_whenAdhocSubProcessExtensionElementsIsHashMap() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExtensionElements(new HashMap<>());
    otherElement.setAttributes(null);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) ExtensionElements is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_whenAdhocSubProcessExtensionElementsIsHashMap2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", new ArrayList<>());

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExtensionElements(extensionElements);
    otherElement.setAttributes(null);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) ExtensionElements is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_whenAdhocSubProcessExtensionElementsIsNull() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExtensionElements(null);
    otherElement.setAttributes(new HashMap<>());

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) ExtensionElements is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_whenAdhocSubProcessExtensionElementsIsNull2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("Key", new ArrayList<>());

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.setExtensionElements(null);
    otherElement.setAttributes(attributes);

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }
}
