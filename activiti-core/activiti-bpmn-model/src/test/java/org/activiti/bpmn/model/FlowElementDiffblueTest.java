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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

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
    SequenceFlow sequenceFlow = new SequenceFlow("Source Ref", "Target Ref");
    SubProcess parentContainer = new SubProcess();
    sequenceFlow.setParentContainer(parentContainer);

    // Act and Assert
    assertSame(parentContainer, sequenceFlow.getSubProcess());
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

    SequenceFlow otherElement = new SequenceFlow("Source Ref", "Target Ref");
    otherElement.setExtensionElements(null);
    otherElement.setAttributes(null);

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert that nothing has changed
    assertTrue(adhocSubProcess.getExecutionListeners().isEmpty());
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
  public void testSetValuesWithFlowElement2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    SequenceFlow otherElement = new SequenceFlow("Source Ref", "Target Ref");
    otherElement.setExecutionListeners(null);

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert that nothing has changed
    assertTrue(adhocSubProcess.getExecutionListeners().isEmpty());
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
  public void testSetValuesWithFlowElement3() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(new ArrayList<>());

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    SequenceFlow otherElement = new SequenceFlow("Source Ref", "Target Ref");
    otherElement.setExecutionListeners(executionListeners);

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    List<ActivitiListener> executionListeners2 = adhocSubProcess.getExecutionListeners();
    assertEquals(1, executionListeners2.size());
    ActivitiListener getResult = executionListeners2.get(0);
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
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement4() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    SequenceFlow otherElement = new SequenceFlow("Source Ref", "Target Ref");
    otherElement.setExecutionListeners(executionListeners);

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    List<ActivitiListener> executionListeners2 = adhocSubProcess.getExecutionListeners();
    assertEquals(1, executionListeners2.size());
    List<FieldExtension> fieldExtensions2 = executionListeners2.get(0).getFieldExtensions();
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

    SequenceFlow otherElement = new SequenceFlow("Source Ref", "Target Ref");
    otherElement.setExecutionListeners(executionListeners);

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert
    List<ActivitiListener> executionListeners2 = adhocSubProcess.getExecutionListeners();
    assertEquals(1, executionListeners2.size());
    ActivitiListener getResult = executionListeners2.get(0);
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
   *
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenExtensionAttributeWithName() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.addAttribute(new ExtensionAttribute("Name"));

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with name is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenExtensionAttributeWithNameIs42() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    AdhocSubProcess otherElement = new AdhocSubProcess();
    otherElement.addAttribute(new ExtensionAttribute("42"));
    otherElement.addAttribute(new ExtensionAttribute("Name"));

    // Act
    adhocSubProcess.setValues((FlowElement) otherElement);

    // Assert that nothing has changed
    assertTrue(otherElement.getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link FlowElement#setValues(FlowElement)} with {@code FlowElement}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElement#setValues(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElement.setValues(FlowElement)"})
  public void testSetValuesWithFlowElement_givenHashMapFooIsArrayList() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("foo", new ArrayList<>());

    SequenceFlow otherElement = new SequenceFlow("Source Ref", "Target Ref");
    otherElement.setExtensionElements(null);
    otherElement.setAttributes(attributes);

    // Act
    adhocSubProcess.setValues(otherElement);

    // Assert that nothing has changed
    assertTrue(adhocSubProcess.getExecutionListeners().isEmpty());
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
}
