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
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class BpmnModelDiffblueTest {
  /**
   * Test {@link BpmnModel#getDefinitionsAttributeValue(String, String)}.
   * <p>
   * Method under test:
   * {@link BpmnModel#getDefinitionsAttributeValue(String, String)}
   */
  @Test
  public void testGetDefinitionsAttributeValue() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    attribute.setNamespace("Namespace");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertNull(bpmnModel.getDefinitionsAttributeValue("Namespace", "Name"));
  }

  /**
   * Test {@link BpmnModel#getDefinitionsAttributeValue(String, String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>When {@code Namespace}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#getDefinitionsAttributeValue(String, String)}
   */
  @Test
  public void testGetDefinitionsAttributeValue_givenBpmnModel_whenNamespace_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getDefinitionsAttributeValue("Namespace", "Name"));
  }

  /**
   * Test {@link BpmnModel#getDefinitionsAttributeValue(String, String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#getDefinitionsAttributeValue(String, String)}
   */
  @Test
  public void testGetDefinitionsAttributeValue_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(bpmnModel.getDefinitionsAttributeValue("Namespace", "Name"));
  }

  /**
   * Test {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}.
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) DefinitionsAttributes
   * {@code Name} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddDefinitionsAttribute_thenBpmnModelDefinitionsAttributesNameSizeIsOne() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    // Act
    bpmnModel.addDefinitionsAttribute(attribute);

    // Assert
    Map<String, List<ExtensionAttribute>> definitionsAttributes = bpmnModel.getDefinitionsAttributes();
    assertEquals(1, definitionsAttributes.size());
    List<ExtensionAttribute> getResult = definitionsAttributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}.
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) DefinitionsAttributes
   * {@code Name} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddDefinitionsAttribute_thenBpmnModelDefinitionsAttributesNameSizeIsTwo() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    bpmnModel.addDefinitionsAttribute(attribute);
    ExtensionAttribute attribute2 = new ExtensionAttribute("Name");

    // Act
    bpmnModel.addDefinitionsAttribute(attribute2);

    // Assert
    Map<String, List<ExtensionAttribute>> definitionsAttributes = bpmnModel.getDefinitionsAttributes();
    assertEquals(1, definitionsAttributes.size());
    List<ExtensionAttribute> getResult = definitionsAttributes.get("Name");
    assertEquals(2, getResult.size());
    assertSame(attribute, getResult.get(0));
    assertSame(attribute2, getResult.get(1));
  }

  /**
   * Test {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}.
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String)} with name is
   * empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddDefinitionsAttribute_whenExtensionAttributeWithNameIsEmptyString() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute(""));

    // Assert that nothing has changed
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
  }

  /**
   * Test {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}.
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String)} with name is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddDefinitionsAttribute_whenExtensionAttributeWithNameIsNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute(null));

    // Assert that nothing has changed
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
  }

  /**
   * Test {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) DefinitionsAttributes
   * Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#addDefinitionsAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddDefinitionsAttribute_whenNull_thenBpmnModelDefinitionsAttributesEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addDefinitionsAttribute(null);

    // Assert that nothing has changed
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
  }

  /**
   * Test {@link BpmnModel#getMainProcess()}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   *   <li>Then return {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getMainProcess()}
   */
  @Test
  public void testGetMainProcess_givenBpmnModelAddProcessProcess_thenReturnProcess() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertSame(process, bpmnModel.getMainProcess());
  }

  /**
   * Test {@link BpmnModel#getMainProcess()}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getMainProcess()}
   */
  @Test
  public void testGetMainProcess_givenBpmnModel_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getMainProcess());
  }

  /**
   * Test {@link BpmnModel#getProcess(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getProcess(String)}
   */
  @Test
  public void testGetProcess_givenBpmnModelAddProcessProcess_whenNull_thenReturnProcess() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertSame(process, bpmnModel.getProcess(null));
  }

  /**
   * Test {@link BpmnModel#getProcess(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   *   <li>When {@code Pool Ref}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getProcess(String)}
   */
  @Test
  public void testGetProcess_givenBpmnModelAddProcessProcess_whenPoolRef_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertNull(bpmnModel.getProcess("Pool Ref"));
  }

  /**
   * Test {@link BpmnModel#getProcess(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>When {@code Pool Ref}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getProcess(String)}
   */
  @Test
  public void testGetProcess_givenBpmnModel_whenPoolRef_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getProcess("Pool Ref"));
  }

  /**
   * Test {@link BpmnModel#getProcessById(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getProcessById(String)}
   */
  @Test
  public void testGetProcessById_givenBpmnModel_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getProcessById("42"));
  }

  /**
   * Test {@link BpmnModel#getProcessById(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) Id is {@code 42}.</li>
   *   <li>Then return {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getProcessById(String)}
   */
  @Test
  public void testGetProcessById_givenProcessIdIs42_thenReturnProcess() {
    // Arrange
    Process process = new Process();
    process.setId("42");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertSame(process, bpmnModel.getProcessById("42"));
  }

  /**
   * Test {@link BpmnModel#getProcessById(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) Id is {@code Id}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getProcessById(String)}
   */
  @Test
  public void testGetProcessById_givenProcessIdIsId_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.setId("Id");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getProcessById("42"));
  }

  /**
   * Test {@link BpmnModel#addProcess(Process)}.
   * <p>
   * Method under test: {@link BpmnModel#addProcess(Process)}
   */
  @Test
  public void testAddProcess() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();

    // Act
    bpmnModel.addProcess(process);

    // Assert
    List<Process> processes = bpmnModel.getProcesses();
    assertEquals(1, processes.size());
    assertSame(process, processes.get(0));
    assertSame(process, bpmnModel.getMainProcess());
  }

  /**
   * Test {@link BpmnModel#getPool(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getPool(String)}
   */
  @Test
  public void testGetPool_when42() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getPool("42"));
  }

  /**
   * Test {@link BpmnModel#getPool(String)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getPool(String)}
   */
  @Test
  public void testGetPool_whenEmptyString() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getPool(""));
  }

  /**
   * Test {@link BpmnModel#getLane(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getLane(String)}
   */
  @Test
  public void testGetLane_givenBpmnModelAddProcessProcess_when42_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertNull(bpmnModel.getLane("42"));
  }

  /**
   * Test {@link BpmnModel#getLane(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getLane(String)}
   */
  @Test
  public void testGetLane_givenBpmnModelAddProcessProcess_whenNull_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertNull(bpmnModel.getLane(null));
  }

  /**
   * Test {@link BpmnModel#getLane(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getLane(String)}
   */
  @Test
  public void testGetLane_givenBpmnModel_when42_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getLane("42"));
  }

  /**
   * Test {@link BpmnModel#getLane(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getLane(String)}
   */
  @Test
  public void testGetLane_givenBpmnModel_whenEmptyString_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getLane(""));
  }

  /**
   * Test {@link BpmnModel#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement
   * {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenAdhocSubProcessAddFlowElementAdhocSubProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getFlowElement("42"));
  }

  /**
   * Test {@link BpmnModel#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Id is
   * {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenAdhocSubProcessIdIs42_when42_thenReturnAdhocSubProcess() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("42");
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertSame(element, bpmnModel.getFlowElement("42"));
  }

  /**
   * Test {@link BpmnModel#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenBpmnModelAddProcessProcess_when42_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertNull(bpmnModel.getFlowElement("42"));
  }

  /**
   * Test {@link BpmnModel#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenBpmnModel_when42_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getFlowElement("42"));
  }

  /**
   * Test {@link BpmnModel#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement
   * {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenProcessAddFlowElementAdhocSubProcess_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getFlowElement("42"));
  }

  /**
   * Test {@link BpmnModel#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement
   * {@link AdhocSubProcess} (default constructor).</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenProcessAddFlowElementAdhocSubProcess_whenEmptyString() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getFlowElement(""));
  }

  /**
   * Test {@link BpmnModel#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement
   * {@link AdhocSubProcess} (default constructor).</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenProcessAddFlowElementAdhocSubProcess_whenNull() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getFlowElement(null));
  }

  /**
   * Test {@link BpmnModel#getFlowElement(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement
   * {@link BooleanDataObject} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getFlowElement(String)}
   */
  @Test
  public void testGetFlowElement_givenProcessAddFlowElementBooleanDataObject_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getFlowElement("42"));
  }

  /**
   * Test {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetFlowElementInSubProcess_givenAdhocSubProcess() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(bpmnModel.getFlowElementInSubProcess("42", subProcess));
  }

  /**
   * Test {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetFlowElementInSubProcess_when42_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertNull(bpmnModel.getFlowElementInSubProcess("42", new SubProcess()));
  }

  /**
   * Test {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetFlowElementInSubProcess_whenEmptyString_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertNull(bpmnModel.getFlowElementInSubProcess("", new SubProcess()));
  }

  /**
   * Test {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#getFlowElementInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetFlowElementInSubProcess_whenNull_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertNull(bpmnModel.getFlowElementInSubProcess(null, new SubProcess()));
  }

  /**
   * Test {@link BpmnModel#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addArtifact
   * {@link Association} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenAdhocSubProcessAddArtifactAssociation_thenReturnNull() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.addArtifact(new Association());
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Test {@link BpmnModel#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement
   * {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenAdhocSubProcessAddFlowElementAdhocSubProcess_thenReturnNull() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Test {@link BpmnModel#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenBpmnModelAddProcessProcess_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Test {@link BpmnModel#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenBpmnModel_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getArtifact("42"));
  }

  /**
   * Test {@link BpmnModel#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addArtifact
   * {@link Association} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenProcessAddArtifactAssociation_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Test {@link BpmnModel#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement
   * {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenProcessAddFlowElementAdhocSubProcess_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Test {@link BpmnModel#getArtifact(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement
   * {@link BooleanDataObject} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getArtifact(String)}
   */
  @Test
  public void testGetArtifact_givenProcessAddFlowElementBooleanDataObject_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getArtifact("42"));
  }

  /**
   * Test {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetArtifactInSubProcess_givenAdhocSubProcess_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(bpmnModel.getArtifactInSubProcess("42", subProcess));
  }

  /**
   * Test {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}.
   * <ul>
   *   <li>Given {@link Association} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetArtifactInSubProcess_givenAssociation() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(new Association());
    subProcess.addFlowElement(new AdhocSubProcess());

    // Act and Assert
    assertNull(bpmnModel.getArtifactInSubProcess("42", subProcess));
  }

  /**
   * Test {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetArtifactInSubProcess_givenBooleanDataObject() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new BooleanDataObject());

    // Act and Assert
    assertNull(bpmnModel.getArtifactInSubProcess("42", subProcess));
  }

  /**
   * Test {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}.
   * <ul>
   *   <li>When {@link SubProcess} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#getArtifactInSubProcess(String, SubProcess)}
   */
  @Test
  public void testGetArtifactInSubProcess_whenSubProcess_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertNull(bpmnModel.getArtifactInSubProcess("42", new SubProcess()));
  }

  /**
   * Test {@link BpmnModel#addGraphicInfo(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link BpmnModel#addGraphicInfo(String, GraphicInfo)}
   */
  @Test
  public void testAddGraphicInfo() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertTrue(bpmnModel.hasDiagramInterchangeInfo());
    assertSame(graphicInfo, locationMap.get("Key"));
  }

  /**
   * Test {@link BpmnModel#getGraphicInfo(String)}.
   * <p>
   * Method under test: {@link BpmnModel#getGraphicInfo(String)}
   */
  @Test
  public void testGetGraphicInfo() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getGraphicInfo("Key"));
  }

  /**
   * Test {@link BpmnModel#getFlowLocationGraphicInfo(String)}.
   * <p>
   * Method under test: {@link BpmnModel#getFlowLocationGraphicInfo(String)}
   */
  @Test
  public void testGetFlowLocationGraphicInfo() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getFlowLocationGraphicInfo("Key"));
  }

  /**
   * Test {@link BpmnModel#hasDiagramInterchangeInfo()}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#hasDiagramInterchangeInfo()}
   */
  @Test
  public void testHasDiagramInterchangeInfo_givenBpmnModel_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).hasDiagramInterchangeInfo());
  }

  /**
   * Test {@link BpmnModel#hasDiagramInterchangeInfo()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#hasDiagramInterchangeInfo()}
   */
  @Test
  public void testHasDiagramInterchangeInfo_thenReturnTrue() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Act and Assert
    assertTrue(bpmnModel.hasDiagramInterchangeInfo());
  }

  /**
   * Test {@link BpmnModel#getLabelGraphicInfo(String)}.
   * <p>
   * Method under test: {@link BpmnModel#getLabelGraphicInfo(String)}
   */
  @Test
  public void testGetLabelGraphicInfo() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getLabelGraphicInfo("Key"));
  }

  /**
   * Test {@link BpmnModel#addLabelGraphicInfo(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link BpmnModel#addLabelGraphicInfo(String, GraphicInfo)}
   */
  @Test
  public void testAddLabelGraphicInfo() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    bpmnModel.addLabelGraphicInfo("Key", graphicInfo);

    // Assert
    Map<String, GraphicInfo> labelLocationMap = bpmnModel.getLabelLocationMap();
    assertEquals(1, labelLocationMap.size());
    assertSame(graphicInfo, labelLocationMap.get("Key"));
  }

  /**
   * Test {@link BpmnModel#addFlowGraphicInfoList(String, List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addFlowGraphicInfoList(String, List)}
   */
  @Test
  public void testAddFlowGraphicInfoList_givenGraphicInfoElementIsActivitiListener() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo);

    // Act
    bpmnModel.addFlowGraphicInfoList("Key", graphicInfoList);

    // Assert
    Map<String, List<GraphicInfo>> flowLocationMap = bpmnModel.getFlowLocationMap();
    assertEquals(1, flowLocationMap.size());
    assertSame(graphicInfoList, flowLocationMap.get("Key"));
  }

  /**
   * Test {@link BpmnModel#addFlowGraphicInfoList(String, List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Expanded is
   * {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addFlowGraphicInfoList(String, List)}
   */
  @Test
  public void testAddFlowGraphicInfoList_givenGraphicInfoExpandedIsFalse() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(false);
    graphicInfo2.setHeight(0.5d);
    graphicInfo2.setWidth(0.5d);
    graphicInfo2.setX(10.0d);
    graphicInfo2.setXmlColumnNumber(1);
    graphicInfo2.setXmlRowNumber(1);
    graphicInfo2.setY(10.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);
    graphicInfoList.add(graphicInfo);

    // Act
    bpmnModel.addFlowGraphicInfoList("Key", graphicInfoList);

    // Assert
    Map<String, List<GraphicInfo>> flowLocationMap = bpmnModel.getFlowLocationMap();
    assertEquals(1, flowLocationMap.size());
    assertSame(graphicInfoList, flowLocationMap.get("Key"));
  }

  /**
   * Test {@link BpmnModel#addFlowGraphicInfoList(String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addFlowGraphicInfoList(String, List)}
   */
  @Test
  public void testAddFlowGraphicInfoList_whenArrayList() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();

    // Act
    bpmnModel.addFlowGraphicInfoList("Key", graphicInfoList);

    // Assert
    Map<String, List<GraphicInfo>> flowLocationMap = bpmnModel.getFlowLocationMap();
    assertEquals(1, flowLocationMap.size());
    assertSame(graphicInfoList, flowLocationMap.get("Key"));
  }

  /**
   * Test {@link BpmnModel#setResources(Collection)}.
   * <ul>
   *   <li>Given {@link Resource#Resource(String, String)} with resourceId is
   * {@code 42} and {@code Resource Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setResources(Collection)}
   */
  @Test
  public void testSetResources_givenResourceWithResourceIdIs42AndResourceName() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<Resource> resourceList = new ArrayList<>();
    resourceList.add(new Resource("42", "Resource Name"));

    // Act
    bpmnModel.setResources(resourceList);

    // Assert
    assertEquals(resourceList, bpmnModel.getResources());
  }

  /**
   * Test {@link BpmnModel#setResources(Collection)}.
   * <ul>
   *   <li>Given {@link Resource#Resource(String, String)} with resourceId is
   * {@code 42} and {@code Resource Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setResources(Collection)}
   */
  @Test
  public void testSetResources_givenResourceWithResourceIdIs42AndResourceName2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<Resource> resourceList = new ArrayList<>();
    resourceList.add(new Resource("42", "Resource Name"));
    resourceList.add(new Resource("42", "Resource Name"));

    // Act
    bpmnModel.setResources(resourceList);

    // Assert
    assertEquals(resourceList, bpmnModel.getResources());
  }

  /**
   * Test {@link BpmnModel#setResources(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setResources(Collection)}
   */
  @Test
  public void testSetResources_whenArrayList_thenBpmnModelResourcesIsArrayList() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<Resource> resourceList = new ArrayList<>();

    // Act
    bpmnModel.setResources(resourceList);

    // Assert
    assertEquals(resourceList, bpmnModel.getResources());
  }

  /**
   * Test {@link BpmnModel#setResources(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setResources(Collection)}
   */
  @Test
  public void testSetResources_whenNull_thenBpmnModelResourcesList() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.setResources(null);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertTrue(resources.isEmpty());
  }

  /**
   * Test {@link BpmnModel#addResource(Resource)}.
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) Resources size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addResource(Resource)}
   */
  @Test
  public void testAddResource_thenBpmnModelResourcesSizeIsOne() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Resource resource = new Resource("42", "Resource Name");

    // Act
    bpmnModel.addResource(resource);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertEquals(1, resources.size());
    assertTrue(resources instanceof List);
    assertSame(resource, ((List<Resource>) resources).get(0));
  }

  /**
   * Test {@link BpmnModel#addResource(Resource)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addResource(Resource)}
   */
  @Test
  public void testAddResource_whenNull_thenBpmnModelResourcesEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addResource(null);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertTrue(resources.isEmpty());
  }

  /**
   * Test {@link BpmnModel#containsResourceId(String)}.
   * <p>
   * Method under test: {@link BpmnModel#containsResourceId(String)}
   */
  @Test
  public void testContainsResourceId() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addResource(new Resource("Resource Id", "Resource Name"));

    // Act and Assert
    assertFalse(bpmnModel.containsResourceId("42"));
  }

  /**
   * Test {@link BpmnModel#containsResourceId(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsResourceId(String)}
   */
  @Test
  public void testContainsResourceId_givenBpmnModel_when42_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsResourceId("42"));
  }

  /**
   * Test {@link BpmnModel#containsResourceId(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsResourceId(String)}
   */
  @Test
  public void testContainsResourceId_thenReturnTrue() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addResource(new Resource("42", "Resource Name"));

    // Act and Assert
    assertTrue(bpmnModel.containsResourceId("42"));
  }

  /**
   * Test {@link BpmnModel#getResource(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addResource
   * {@link Resource#Resource(String, String)} with {@code Resource Id} and
   * {@code Resource Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getResource(String)}
   */
  @Test
  public void testGetResource_givenBpmnModelAddResourceResourceWithResourceIdAndResourceName() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addResource(new Resource("Resource Id", "Resource Name"));

    // Act and Assert
    assertNull(bpmnModel.getResource("42"));
  }

  /**
   * Test {@link BpmnModel#getResource(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getResource(String)}
   */
  @Test
  public void testGetResource_givenBpmnModel_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getResource("42"));
  }

  /**
   * Test {@link BpmnModel#getResource(String)}.
   * <ul>
   *   <li>Then return {@link Resource#Resource(String, String)} with resourceId is
   * {@code 42} and {@code Resource Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getResource(String)}
   */
  @Test
  public void testGetResource_thenReturnResourceWithResourceIdIs42AndResourceName() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Resource resource = new Resource("42", "Resource Name");

    bpmnModel.addResource(resource);

    // Act and Assert
    assertSame(resource, bpmnModel.getResource("42"));
  }

  /**
   * Test {@link BpmnModel#setSignals(Collection)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setSignals(Collection)}
   */
  @Test
  public void testSetSignals_givenSignalWithIdIs42AndName() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(new Signal("42", "Name"));

    // Act
    bpmnModel.setSignals(signalList);

    // Assert
    assertEquals(signalList, bpmnModel.getSignals());
  }

  /**
   * Test {@link BpmnModel#setSignals(Collection)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setSignals(Collection)}
   */
  @Test
  public void testSetSignals_givenSignalWithIdIs42AndName2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(new Signal("42", "Name"));
    signalList.add(new Signal("42", "Name"));

    // Act
    bpmnModel.setSignals(signalList);

    // Assert
    assertEquals(signalList, bpmnModel.getSignals());
  }

  /**
   * Test {@link BpmnModel#setSignals(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Signals is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setSignals(Collection)}
   */
  @Test
  public void testSetSignals_whenArrayList_thenBpmnModelSignalsIsArrayList() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<Signal> signalList = new ArrayList<>();

    // Act
    bpmnModel.setSignals(signalList);

    // Assert
    assertEquals(signalList, bpmnModel.getSignals());
  }

  /**
   * Test {@link BpmnModel#setSignals(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Signals {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setSignals(Collection)}
   */
  @Test
  public void testSetSignals_whenNull_thenBpmnModelSignalsList() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.setSignals(null);

    // Assert that nothing has changed
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BpmnModel#addSignal(Signal)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Signals Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addSignal(Signal)}
   */
  @Test
  public void testAddSignal_whenNull_thenBpmnModelSignalsEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addSignal(null);

    // Assert that nothing has changed
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BpmnModel#addSignal(Signal)}.
   * <ul>
   *   <li>When {@link Signal#Signal(String, String)} with id is {@code 42} and
   * {@code Name}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Signals size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addSignal(Signal)}
   */
  @Test
  public void testAddSignal_whenSignalWithIdIs42AndName_thenBpmnModelSignalsSizeIsOne() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Signal signal = new Signal("42", "Name");

    // Act
    bpmnModel.addSignal(signal);

    // Assert
    Collection<Signal> signals = bpmnModel.getSignals();
    assertEquals(1, signals.size());
    assertTrue(signals instanceof List);
    assertSame(signal, ((List<Signal>) signals).get(0));
  }

  /**
   * Test {@link BpmnModel#containsSignalId(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addSignal
   * {@link Signal#Signal(String, String)} with {@code Id} and {@code Name}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsSignalId(String)}
   */
  @Test
  public void testContainsSignalId_givenBpmnModelAddSignalSignalWithIdAndName_thenReturnFalse() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("Id", "Name"));

    // Act and Assert
    assertFalse(bpmnModel.containsSignalId("42"));
  }

  /**
   * Test {@link BpmnModel#containsSignalId(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsSignalId(String)}
   */
  @Test
  public void testContainsSignalId_givenBpmnModel_when42_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsSignalId("42"));
  }

  /**
   * Test {@link BpmnModel#containsSignalId(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsSignalId(String)}
   */
  @Test
  public void testContainsSignalId_thenReturnTrue() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", "Name"));

    // Act and Assert
    assertTrue(bpmnModel.containsSignalId("42"));
  }

  /**
   * Test {@link BpmnModel#getSignal(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addSignal
   * {@link Signal#Signal(String, String)} with {@code Id} and {@code Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getSignal(String)}
   */
  @Test
  public void testGetSignal_givenBpmnModelAddSignalSignalWithIdAndName_thenReturnNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("Id", "Name"));

    // Act and Assert
    assertNull(bpmnModel.getSignal("42"));
  }

  /**
   * Test {@link BpmnModel#getSignal(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getSignal(String)}
   */
  @Test
  public void testGetSignal_givenBpmnModel_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getSignal("42"));
  }

  /**
   * Test {@link BpmnModel#getSignal(String)}.
   * <ul>
   *   <li>Then return {@link Signal#Signal(String, String)} with id is {@code 42}
   * and {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getSignal(String)}
   */
  @Test
  public void testGetSignal_thenReturnSignalWithIdIs42AndName() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Signal signal = new Signal("42", "Name");

    bpmnModel.addSignal(signal);

    // Act and Assert
    assertSame(signal, bpmnModel.getSignal("42"));
  }

  /**
   * Test {@link BpmnModel#addMessageFlow(MessageFlow)}.
   * <ul>
   *   <li>Given {@code Message Flow}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) MessageFlows size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addMessageFlow(MessageFlow)}
   */
  @Test
  public void testAddMessageFlow_givenMessageFlow_thenBpmnModelMessageFlowsSizeIsOne() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    MessageFlow messageFlow = new MessageFlow("Source Ref", "Target Ref");
    messageFlow.setId("Message Flow");

    // Act
    bpmnModel.addMessageFlow(messageFlow);

    // Assert
    Map<String, MessageFlow> messageFlows = bpmnModel.getMessageFlows();
    assertEquals(1, messageFlows.size());
    assertSame(messageFlow, messageFlows.get("Message Flow"));
  }

  /**
   * Test {@link BpmnModel#addMessageFlow(MessageFlow)}.
   * <ul>
   *   <li>When {@link MessageFlow#MessageFlow(String, String)} with
   * {@code Source Ref} and {@code Target Ref}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addMessageFlow(MessageFlow)}
   */
  @Test
  public void testAddMessageFlow_whenMessageFlowWithSourceRefAndTargetRef() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addMessageFlow(new MessageFlow("Source Ref", "Target Ref"));

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
  }

  /**
   * Test {@link BpmnModel#addMessageFlow(MessageFlow)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) MessageFlows Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addMessageFlow(MessageFlow)}
   */
  @Test
  public void testAddMessageFlow_whenNull_thenBpmnModelMessageFlowsEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addMessageFlow(null);

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
  }

  /**
   * Test {@link BpmnModel#getMessageFlow(String)}.
   * <p>
   * Method under test: {@link BpmnModel#getMessageFlow(String)}
   */
  @Test
  public void testGetMessageFlow() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getMessageFlow("42"));
  }

  /**
   * Test {@link BpmnModel#containsMessageFlowId(String)}.
   * <p>
   * Method under test: {@link BpmnModel#containsMessageFlowId(String)}
   */
  @Test
  public void testContainsMessageFlowId() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsMessageFlowId("42"));
  }

  /**
   * Test {@link BpmnModel#getMessages()}.
   * <p>
   * Method under test: {@link BpmnModel#getMessages()}
   */
  @Test
  public void testGetMessages() {
    // Arrange, Act and Assert
    assertTrue((new BpmnModel()).getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnModel#setMessages(Collection)}.
   * <ul>
   *   <li>Given {@link Message#Message(String, String, String)} with id is
   * {@code 42} and {@code Name} and {@code Item Ref} Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setMessages(Collection)}
   */
  @Test
  public void testSetMessages_givenMessageWithIdIs42AndNameAndItemRefIdIsNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    Message message = new Message("42", "Name", "Item Ref");
    message.setId(null);

    LinkedHashSet<Message> messageList = new LinkedHashSet<>();
    messageList.add(message);

    // Act
    bpmnModel.setMessages(messageList);

    // Assert
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Test {@link BpmnModel#setMessages(Collection)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setMessages(Collection)}
   */
  @Test
  public void testSetMessages_givenNull_whenLinkedHashSetAddNull_thenBpmnModelMessagesEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    LinkedHashSet<Message> messageList = new LinkedHashSet<>();
    messageList.add(null);

    // Act
    bpmnModel.setMessages(messageList);

    // Assert
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Test {@link BpmnModel#setMessages(Collection)}.
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) Messages size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setMessages(Collection)}
   */
  @Test
  public void testSetMessages_thenBpmnModelMessagesSizeIsOne() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    Message message = new Message("42", "Name", "Item Ref");
    message.setId("Message List");

    LinkedHashSet<Message> messageList = new LinkedHashSet<>();
    messageList.add(message);

    // Act
    bpmnModel.setMessages(messageList);

    // Assert
    assertEquals(1, bpmnModel.getMessages().size());
    Map<String, Message> stringMessageMap = bpmnModel.messageMap;
    assertEquals(1, stringMessageMap.size());
    assertSame(message, stringMessageMap.get("Message List"));
  }

  /**
   * Test {@link BpmnModel#setMessages(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setMessages(Collection)}
   */
  @Test
  public void testSetMessages_whenArrayList_thenBpmnModelMessagesEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.setMessages(new ArrayList<>());

    // Assert
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Test {@link BpmnModel#setMessages(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#setMessages(Collection)}
   */
  @Test
  public void testSetMessages_whenNull_thenBpmnModelMessagesEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.setMessages(null);

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Test {@link BpmnModel#addMessage(Message)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link Message#Message(String, String, String)} with id is
   * {@code 42} and {@code Name} and {@code Item Ref} Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addMessage(Message)}
   */
  @Test
  public void testAddMessage_givenNull_whenMessageWithIdIs42AndNameAndItemRefIdIsNull() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    Message message = new Message("42", "Name", "Item Ref");
    message.setId(null);

    // Act
    bpmnModel.addMessage(message);

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Test {@link BpmnModel#addMessage(Message)}.
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) Messages size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addMessage(Message)}
   */
  @Test
  public void testAddMessage_thenBpmnModelMessagesSizeIsOne() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Message message = new Message("42", "Name", "Item Ref");

    // Act
    bpmnModel.addMessage(message);

    // Assert
    assertEquals(1, bpmnModel.getMessages().size());
    Map<String, Message> stringMessageMap = bpmnModel.messageMap;
    assertEquals(1, stringMessageMap.size());
    assertSame(message, stringMessageMap.get("42"));
  }

  /**
   * Test {@link BpmnModel#addMessage(Message)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addMessage(Message)}
   */
  @Test
  public void testAddMessage_whenNull_thenBpmnModelMessagesEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addMessage(null);

    // Assert that nothing has changed
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.messageMap.isEmpty());
  }

  /**
   * Test {@link BpmnModel#getMessage(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getMessage(String)}
   */
  @Test
  public void testGetMessage_givenBpmnModel_when42_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getMessage("42"));
  }

  /**
   * Test {@link BpmnModel#getMessage(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getMessage(String)}
   */
  @Test
  public void testGetMessage_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnIdIs42() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));
    Message.Builder attributesResult = Message.builder().attributes(attributes);
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addMessage(message);

    // Act
    Message actualMessage = bpmnModel.getMessage("42");

    // Assert
    assertEquals("42", actualMessage.getId());
    assertEquals("Item Ref", actualMessage.getItemRef());
    assertEquals("Name", actualMessage.getName());
    assertEquals(10, actualMessage.getXmlColumnNumber());
    assertEquals(10, actualMessage.getXmlRowNumber());
    assertTrue(actualMessage.getAttributes().isEmpty());
    assertTrue(actualMessage.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BpmnModel#getMessage(String)}.
   * <ul>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getMessage(String)}
   */
  @Test
  public void testGetMessage_thenReturnIdIs42() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    bpmnModel.addMessage(message);

    // Act
    Message actualMessage = bpmnModel.getMessage("42");

    // Assert
    assertEquals("42", actualMessage.getId());
    assertEquals("Item Ref", actualMessage.getItemRef());
    assertEquals("Name", actualMessage.getName());
    assertEquals(10, actualMessage.getXmlColumnNumber());
    assertEquals(10, actualMessage.getXmlRowNumber());
    assertTrue(actualMessage.getAttributes().isEmpty());
    assertTrue(actualMessage.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BpmnModel#containsMessageId(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsMessageId(String)}
   */
  @Test
  public void testContainsMessageId_givenBpmnModel_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsMessageId("42"));
  }

  /**
   * Test {@link BpmnModel#containsMessageId(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsMessageId(String)}
   */
  @Test
  public void testContainsMessageId_thenReturnTrue() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    bpmnModel.addMessage(message);

    // Act and Assert
    assertTrue(bpmnModel.containsMessageId("42"));
  }

  /**
   * Test {@link BpmnModel#addError(String, String, String)}.
   * <ul>
   *   <li>When {@code An error occurred}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Errors size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addError(String, String, String)}
   */
  @Test
  public void testAddError_whenAnErrorOccurred_thenBpmnModelErrorsSizeIsOne() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addError("An error occurred", "An error occurred", "An error occurred");

    // Assert
    Map<String, Error> errors = bpmnModel.getErrors();
    assertEquals(1, errors.size());
    Error getResult = errors.get("An error occurred");
    assertEquals("An error occurred", getResult.getErrorCode());
    assertEquals("An error occurred", getResult.getId());
    assertEquals("An error occurred", getResult.getName());
  }

  /**
   * Test {@link BpmnModel#addError(String, String, String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Errors Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addError(String, String, String)}
   */
  @Test
  public void testAddError_whenEmptyString_thenBpmnModelErrorsEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addError("", "An error occurred", "An error occurred");

    // Assert that nothing has changed
    assertTrue(bpmnModel.getErrors().isEmpty());
  }

  /**
   * Test {@link BpmnModel#addError(String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Errors Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addError(String, String, String)}
   */
  @Test
  public void testAddError_whenNull_thenBpmnModelErrorsEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addError(null, "An error occurred", "An error occurred");

    // Assert that nothing has changed
    assertTrue(bpmnModel.getErrors().isEmpty());
  }

  /**
   * Test {@link BpmnModel#containsErrorRef(String)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsErrorRef(String)}
   */
  @Test
  public void testContainsErrorRef_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsErrorRef("An error occurred"));
  }

  /**
   * Test {@link BpmnModel#containsErrorRef(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsErrorRef(String)}
   */
  @Test
  public void testContainsErrorRef_thenReturnTrue() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addError("An error occurred", "An error occurred", "An error occurred");

    // Act and Assert
    assertTrue(bpmnModel.containsErrorRef("An error occurred"));
  }

  /**
   * Test {@link BpmnModel#addItemDefinition(String, ItemDefinition)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) ItemDefinitions size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#addItemDefinition(String, ItemDefinition)}
   */
  @Test
  public void testAddItemDefinition_when42_thenBpmnModelItemDefinitionsSizeIsOne() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ItemDefinition item = new ItemDefinition();

    // Act
    bpmnModel.addItemDefinition("42", item);

    // Assert
    Map<String, ItemDefinition> itemDefinitions = bpmnModel.getItemDefinitions();
    assertEquals(1, itemDefinitions.size());
    assertSame(item, itemDefinitions.get("42"));
  }

  /**
   * Test {@link BpmnModel#addItemDefinition(String, ItemDefinition)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then {@link BpmnModel} (default constructor) ItemDefinitions Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#addItemDefinition(String, ItemDefinition)}
   */
  @Test
  public void testAddItemDefinition_whenEmptyString_thenBpmnModelItemDefinitionsEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addItemDefinition("", new ItemDefinition());

    // Assert that nothing has changed
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
  }

  /**
   * Test {@link BpmnModel#addItemDefinition(String, ItemDefinition)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) ItemDefinitions Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnModel#addItemDefinition(String, ItemDefinition)}
   */
  @Test
  public void testAddItemDefinition_whenNull_thenBpmnModelItemDefinitionsEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addItemDefinition(null, new ItemDefinition());

    // Assert that nothing has changed
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
  }

  /**
   * Test {@link BpmnModel#containsItemDefinitionId(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsItemDefinitionId(String)}
   */
  @Test
  public void testContainsItemDefinitionId_givenBpmnModel_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsItemDefinitionId("42"));
  }

  /**
   * Test {@link BpmnModel#containsItemDefinitionId(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsItemDefinitionId(String)}
   */
  @Test
  public void testContainsItemDefinitionId_thenReturnTrue() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addItemDefinition("42", new ItemDefinition());

    // Act and Assert
    assertTrue(bpmnModel.containsItemDefinitionId("42"));
  }

  /**
   * Test {@link BpmnModel#getDataStore(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addDataStore {@code 42} and
   * {@link DataStore} (default constructor).</li>
   *   <li>Then return {@link DataStore} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getDataStore(String)}
   */
  @Test
  public void testGetDataStore_givenBpmnModelAddDataStore42AndDataStore_thenReturnDataStore() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    DataStore dataStore = new DataStore();
    bpmnModel.addDataStore("42", dataStore);

    // Act and Assert
    assertSame(dataStore, bpmnModel.getDataStore("42"));
  }

  /**
   * Test {@link BpmnModel#getDataStore(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getDataStore(String)}
   */
  @Test
  public void testGetDataStore_givenBpmnModel_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getDataStore("42"));
  }

  /**
   * Test {@link BpmnModel#addDataStore(String, DataStore)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) DataStores size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addDataStore(String, DataStore)}
   */
  @Test
  public void testAddDataStore_when42_thenBpmnModelDataStoresSizeIsOne() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    DataStore dataStore = new DataStore();

    // Act
    bpmnModel.addDataStore("42", dataStore);

    // Assert
    Map<String, DataStore> dataStores = bpmnModel.getDataStores();
    assertEquals(1, dataStores.size());
    assertSame(dataStore, dataStores.get("42"));
  }

  /**
   * Test {@link BpmnModel#addDataStore(String, DataStore)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then {@link BpmnModel} (default constructor) DataStores Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addDataStore(String, DataStore)}
   */
  @Test
  public void testAddDataStore_whenEmptyString_thenBpmnModelDataStoresEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addDataStore("", new DataStore());

    // Assert that nothing has changed
    assertTrue(bpmnModel.getDataStores().isEmpty());
  }

  /**
   * Test {@link BpmnModel#addDataStore(String, DataStore)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) DataStores Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#addDataStore(String, DataStore)}
   */
  @Test
  public void testAddDataStore_whenNull_thenBpmnModelDataStoresEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addDataStore(null, new DataStore());

    // Assert that nothing has changed
    assertTrue(bpmnModel.getDataStores().isEmpty());
  }

  /**
   * Test {@link BpmnModel#containsDataStore(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addDataStore {@code 42} and
   * {@link DataStore} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsDataStore(String)}
   */
  @Test
  public void testContainsDataStore_givenBpmnModelAddDataStore42AndDataStore_thenReturnTrue() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addDataStore("42", new DataStore());

    // Act and Assert
    assertTrue(bpmnModel.containsDataStore("42"));
  }

  /**
   * Test {@link BpmnModel#containsDataStore(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsDataStore(String)}
   */
  @Test
  public void testContainsDataStore_givenBpmnModel_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsDataStore("42"));
  }

  /**
   * Test {@link BpmnModel#addNamespace(String, String)}.
   * <p>
   * Method under test: {@link BpmnModel#addNamespace(String, String)}
   */
  @Test
  public void testAddNamespace() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    bpmnModel.addNamespace("Prefix", "Uri");

    // Assert
    Map<String, String> namespaces = bpmnModel.getNamespaces();
    assertEquals(1, namespaces.size());
    assertEquals("Uri", namespaces.get("Prefix"));
  }

  /**
   * Test {@link BpmnModel#containsNamespacePrefix(String)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsNamespacePrefix(String)}
   */
  @Test
  public void testContainsNamespacePrefix_givenBpmnModel_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BpmnModel()).containsNamespacePrefix("Prefix"));
  }

  /**
   * Test {@link BpmnModel#containsNamespacePrefix(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#containsNamespacePrefix(String)}
   */
  @Test
  public void testContainsNamespacePrefix_thenReturnTrue() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addNamespace("Prefix", "Uri");

    // Act and Assert
    assertTrue(bpmnModel.containsNamespacePrefix("Prefix"));
  }

  /**
   * Test {@link BpmnModel#getNamespace(String)}.
   * <p>
   * Method under test: {@link BpmnModel#getNamespace(String)}
   */
  @Test
  public void testGetNamespace() {
    // Arrange, Act and Assert
    assertNull((new BpmnModel()).getNamespace("Prefix"));
  }

  /**
   * Test {@link BpmnModel#getStartFormKey(String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) Id is {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModel#getStartFormKey(String)}
   */
  @Test
  public void testGetStartFormKey_givenProcessIdIs42_thenReturnNull() {
    // Arrange
    Process process = new Process();
    process.setId("42");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act and Assert
    assertNull(bpmnModel.getStartFormKey("42"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BpmnModel}
   *   <li>{@link BpmnModel#setDataStores(Map)}
   *   <li>{@link BpmnModel#setDefinitionsAttributes(Map)}
   *   <li>{@link BpmnModel#setErrors(Map)}
   *   <li>{@link BpmnModel#setEventSupport(Object)}
   *   <li>{@link BpmnModel#setGlobalArtifacts(List)}
   *   <li>{@link BpmnModel#setImports(List)}
   *   <li>{@link BpmnModel#setInterfaces(List)}
   *   <li>{@link BpmnModel#setItemDefinitions(Map)}
   *   <li>{@link BpmnModel#setMessageFlows(Map)}
   *   <li>{@link BpmnModel#setPools(List)}
   *   <li>{@link BpmnModel#setSourceSystemId(String)}
   *   <li>{@link BpmnModel#setStartEventFormTypes(List)}
   *   <li>{@link BpmnModel#setTargetNamespace(String)}
   *   <li>{@link BpmnModel#setUserTaskFormTypes(List)}
   *   <li>{@link BpmnModel#getDataStores()}
   *   <li>{@link BpmnModel#getDefinitionsAttributes()}
   *   <li>{@link BpmnModel#getErrors()}
   *   <li>{@link BpmnModel#getEventSupport()}
   *   <li>{@link BpmnModel#getFlowLocationMap()}
   *   <li>{@link BpmnModel#getGlobalArtifacts()}
   *   <li>{@link BpmnModel#getImports()}
   *   <li>{@link BpmnModel#getInterfaces()}
   *   <li>{@link BpmnModel#getItemDefinitions()}
   *   <li>{@link BpmnModel#getLabelLocationMap()}
   *   <li>{@link BpmnModel#getLocationMap()}
   *   <li>{@link BpmnModel#getMessageFlows()}
   *   <li>{@link BpmnModel#getNamespaces()}
   *   <li>{@link BpmnModel#getPools()}
   *   <li>{@link BpmnModel#getProcesses()}
   *   <li>{@link BpmnModel#getResources()}
   *   <li>{@link BpmnModel#getSignals()}
   *   <li>{@link BpmnModel#getSourceSystemId()}
   *   <li>{@link BpmnModel#getStartEventFormTypes()}
   *   <li>{@link BpmnModel#getTargetNamespace()}
   *   <li>{@link BpmnModel#getUserTaskFormTypes()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BpmnModel actualBpmnModel = new BpmnModel();
    HashMap<String, DataStore> dataStoreMap = new HashMap<>();
    actualBpmnModel.setDataStores(dataStoreMap);
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    actualBpmnModel.setDefinitionsAttributes(attributes);
    HashMap<String, Error> errorMap = new HashMap<>();
    actualBpmnModel.setErrors(errorMap);
    actualBpmnModel.setEventSupport("Event Support");
    ArrayList<Artifact> globalArtifacts = new ArrayList<>();
    actualBpmnModel.setGlobalArtifacts(globalArtifacts);
    ArrayList<Import> imports = new ArrayList<>();
    actualBpmnModel.setImports(imports);
    ArrayList<Interface> interfaces = new ArrayList<>();
    actualBpmnModel.setInterfaces(interfaces);
    HashMap<String, ItemDefinition> itemDefinitionMap = new HashMap<>();
    actualBpmnModel.setItemDefinitions(itemDefinitionMap);
    HashMap<String, MessageFlow> messageFlows = new HashMap<>();
    actualBpmnModel.setMessageFlows(messageFlows);
    ArrayList<Pool> pools = new ArrayList<>();
    actualBpmnModel.setPools(pools);
    actualBpmnModel.setSourceSystemId("42");
    ArrayList<String> startEventFormTypes = new ArrayList<>();
    actualBpmnModel.setStartEventFormTypes(startEventFormTypes);
    actualBpmnModel.setTargetNamespace("Target Namespace");
    ArrayList<String> userTaskFormTypes = new ArrayList<>();
    actualBpmnModel.setUserTaskFormTypes(userTaskFormTypes);
    Map<String, DataStore> actualDataStores = actualBpmnModel.getDataStores();
    Map<String, List<ExtensionAttribute>> actualDefinitionsAttributes = actualBpmnModel.getDefinitionsAttributes();
    Map<String, Error> actualErrors = actualBpmnModel.getErrors();
    Object actualEventSupport = actualBpmnModel.getEventSupport();
    Map<String, List<GraphicInfo>> actualFlowLocationMap = actualBpmnModel.getFlowLocationMap();
    List<Artifact> actualGlobalArtifacts = actualBpmnModel.getGlobalArtifacts();
    List<Import> actualImports = actualBpmnModel.getImports();
    List<Interface> actualInterfaces = actualBpmnModel.getInterfaces();
    Map<String, ItemDefinition> actualItemDefinitions = actualBpmnModel.getItemDefinitions();
    Map<String, GraphicInfo> actualLabelLocationMap = actualBpmnModel.getLabelLocationMap();
    Map<String, GraphicInfo> actualLocationMap = actualBpmnModel.getLocationMap();
    Map<String, MessageFlow> actualMessageFlows = actualBpmnModel.getMessageFlows();
    Map<String, String> actualNamespaces = actualBpmnModel.getNamespaces();
    List<Pool> actualPools = actualBpmnModel.getPools();
    List<Process> actualProcesses = actualBpmnModel.getProcesses();
    Collection<Resource> actualResources = actualBpmnModel.getResources();
    Collection<Signal> actualSignals = actualBpmnModel.getSignals();
    String actualSourceSystemId = actualBpmnModel.getSourceSystemId();
    List<String> actualStartEventFormTypes = actualBpmnModel.getStartEventFormTypes();
    String actualTargetNamespace = actualBpmnModel.getTargetNamespace();
    List<String> actualUserTaskFormTypes = actualBpmnModel.getUserTaskFormTypes();

    // Assert that nothing has changed
    assertTrue(actualResources instanceof List);
    assertTrue(actualSignals instanceof List);
    assertEquals("42", actualSourceSystemId);
    assertEquals("Event Support", actualEventSupport);
    assertEquals("Target Namespace", actualTargetNamespace);
    assertTrue(actualGlobalArtifacts.isEmpty());
    assertTrue(actualImports.isEmpty());
    assertTrue(actualInterfaces.isEmpty());
    assertTrue(actualPools.isEmpty());
    assertTrue(actualProcesses.isEmpty());
    assertTrue(actualStartEventFormTypes.isEmpty());
    assertTrue(actualUserTaskFormTypes.isEmpty());
    assertTrue(actualDataStores.isEmpty());
    assertTrue(actualDefinitionsAttributes.isEmpty());
    assertTrue(actualErrors.isEmpty());
    assertTrue(actualFlowLocationMap.isEmpty());
    assertTrue(actualItemDefinitions.isEmpty());
    assertTrue(actualLabelLocationMap.isEmpty());
    assertTrue(actualLocationMap.isEmpty());
    assertTrue(actualMessageFlows.isEmpty());
    assertTrue(actualNamespaces.isEmpty());
    assertTrue(actualBpmnModel.messageMap.isEmpty());
    assertSame(globalArtifacts, actualGlobalArtifacts);
    assertSame(imports, actualImports);
    assertSame(interfaces, actualInterfaces);
    assertSame(pools, actualPools);
    assertSame(startEventFormTypes, actualStartEventFormTypes);
    assertSame(userTaskFormTypes, actualUserTaskFormTypes);
    assertSame(dataStoreMap, actualDataStores);
    assertSame(attributes, actualDefinitionsAttributes);
    assertSame(errorMap, actualErrors);
    assertSame(itemDefinitionMap, actualItemDefinitions);
    assertSame(messageFlows, actualMessageFlows);
  }
}
