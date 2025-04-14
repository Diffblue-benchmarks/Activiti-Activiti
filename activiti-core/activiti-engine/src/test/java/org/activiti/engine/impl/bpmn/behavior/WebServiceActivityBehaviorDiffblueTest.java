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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataAssociation;
import org.activiti.bpmn.model.Import;
import org.activiti.bpmn.model.Interface;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.Operation;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.bpmn.data.AbstractDataAssociation;
import org.activiti.engine.impl.bpmn.data.ItemDefinition;
import org.activiti.engine.impl.bpmn.data.ItemKind;
import org.activiti.engine.impl.bpmn.webservice.BpmnInterface;
import org.activiti.engine.impl.bpmn.webservice.MessageDefinition;
import org.activiti.engine.impl.bpmn.webservice.MessageImplicitDataInputAssociation;
import org.activiti.engine.impl.bpmn.webservice.MessageImplicitDataOutputAssociation;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class WebServiceActivityBehaviorDiffblueTest {
  /**
   * Test new {@link WebServiceActivityBehavior} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link WebServiceActivityBehavior}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.<init>()"})
  public void testNewWebServiceActivityBehavior() {
    // Arrange and Act
    WebServiceActivityBehavior actualWebServiceActivityBehavior = new WebServiceActivityBehavior();

    // Assert
    assertNull(actualWebServiceActivityBehavior.getMultiInstanceActivityBehavior());
    Map<String, ItemDefinition> stringItemDefinitionMap = actualWebServiceActivityBehavior.itemDefinitionMap;
    assertEquals(1, stringItemDefinitionMap.size());
    assertFalse(actualWebServiceActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualWebServiceActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
    assertTrue(actualWebServiceActivityBehavior.messageDefinitionMap.isEmpty());
    assertTrue(actualWebServiceActivityBehavior.operationMap.isEmpty());
    assertTrue(actualWebServiceActivityBehavior.structureDefinitionMap.isEmpty());
    assertTrue(actualWebServiceActivityBehavior.wsOperationMap.isEmpty());
    assertTrue(actualWebServiceActivityBehavior.wsServiceMap.isEmpty());
    assertTrue(actualWebServiceActivityBehavior.xmlImporterMap.isEmpty());
  }

  /**
   * Test {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}.
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.fillDefinitionMaps(BpmnModel)"})
  public void testFillDefinitionMaps() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    BpmnModel bpmnModel = new BpmnModel();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    bpmnModel.addMessage(message);

    // Act
    webServiceActivityBehavior.fillDefinitionMaps(bpmnModel);

    // Assert
    Map<String, MessageDefinition> stringMessageDefinitionMap = webServiceActivityBehavior.messageDefinitionMap;
    assertEquals(1, stringMessageDefinitionMap.size());
    MessageDefinition getResult = stringMessageDefinitionMap.get("42");
    assertEquals("42", getResult.getId());
    assertNull(getResult.getItemDefinition());
    Map<String, ItemDefinition> stringItemDefinitionMap = webServiceActivityBehavior.itemDefinitionMap;
    assertEquals(1, stringItemDefinitionMap.size());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
  }

  /**
   * Test {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}.
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.fillDefinitionMaps(BpmnModel)"})
  public void testFillDefinitionMaps2() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    BpmnModel bpmnModel = new BpmnModel();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    bpmnModel.addMessage(message);

    // Act
    webServiceActivityBehavior.fillDefinitionMaps(bpmnModel);

    // Assert
    Map<String, MessageDefinition> stringMessageDefinitionMap = webServiceActivityBehavior.messageDefinitionMap;
    assertEquals(1, stringMessageDefinitionMap.size());
    MessageDefinition getResult = stringMessageDefinitionMap.get("42");
    assertEquals("42", getResult.getId());
    assertNull(getResult.getItemDefinition());
    Map<String, ItemDefinition> stringItemDefinitionMap = webServiceActivityBehavior.itemDefinitionMap;
    assertEquals(1, stringItemDefinitionMap.size());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
  }

  /**
   * Test {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}.
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.fillDefinitionMaps(BpmnModel)"})
  public void testFillDefinitionMaps3() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    org.activiti.bpmn.model.ItemDefinition item = new org.activiti.bpmn.model.ItemDefinition();
    item.setStructureRef("Trying to load class with current thread context classloader: {}");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addItemDefinition("42", item);

    // Act
    webServiceActivityBehavior.fillDefinitionMaps(bpmnModel);

    // Assert
    Map<String, ItemDefinition> stringItemDefinitionMap = webServiceActivityBehavior.itemDefinitionMap;
    assertEquals(2, stringItemDefinitionMap.size());
    ItemDefinition getResult = stringItemDefinitionMap.get(null);
    assertNull(getResult.getId());
    assertNull(getResult.getStructureDefinition());
    assertEquals(ItemKind.Information, getResult.getItemKind());
    assertFalse(getResult.isCollection());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
  }

  /**
   * Test {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}.
   * <ul>
   *   <li>Given {@link org.activiti.bpmn.model.ItemDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.fillDefinitionMaps(BpmnModel)"})
  public void testFillDefinitionMaps_givenItemDefinition() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addItemDefinition("42", new org.activiti.bpmn.model.ItemDefinition());

    // Act
    webServiceActivityBehavior.fillDefinitionMaps(bpmnModel);

    // Assert
    Map<String, ItemDefinition> stringItemDefinitionMap = webServiceActivityBehavior.itemDefinitionMap;
    assertEquals(2, stringItemDefinitionMap.size());
    ItemDefinition getResult = stringItemDefinitionMap.get(null);
    assertNull(getResult.getId());
    assertNull(getResult.getStructureDefinition());
    assertEquals(ItemKind.Information, getResult.getItemKind());
    assertFalse(getResult.isCollection());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
  }

  /**
   * Test {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}.
   * <ul>
   *   <li>Given {@code Trying to load class with local classloader: {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.fillDefinitionMaps(BpmnModel)"})
  public void testFillDefinitionMaps_givenTryingToLoadClassWithLocalClassloader() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addItemDefinition("Trying to load class with local classloader: {}",
        new org.activiti.bpmn.model.ItemDefinition());
    bpmnModel.addItemDefinition("42", new org.activiti.bpmn.model.ItemDefinition());

    // Act
    webServiceActivityBehavior.fillDefinitionMaps(bpmnModel);

    // Assert
    Map<String, ItemDefinition> stringItemDefinitionMap = webServiceActivityBehavior.itemDefinitionMap;
    assertEquals(2, stringItemDefinitionMap.size());
    ItemDefinition getResult = stringItemDefinitionMap.get(null);
    assertNull(getResult.getId());
    assertNull(getResult.getStructureDefinition());
    assertEquals(ItemKind.Information, getResult.getItemKind());
    assertFalse(getResult.isCollection());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
  }

  /**
   * Test {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}.
   * <ul>
   *   <li>Then {@link WebServiceActivityBehavior} (default constructor) {@link WebServiceActivityBehavior#messageDefinitionMap} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#fillDefinitionMaps(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.fillDefinitionMaps(BpmnModel)"})
  public void testFillDefinitionMaps_thenWebServiceActivityBehaviorMessageDefinitionMapEmpty() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    // Act
    webServiceActivityBehavior.fillDefinitionMaps(new BpmnModel());

    // Assert that nothing has changed
    Map<String, ItemDefinition> stringItemDefinitionMap = webServiceActivityBehavior.itemDefinitionMap;
    assertEquals(1, stringItemDefinitionMap.size());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
    assertTrue(webServiceActivityBehavior.messageDefinitionMap.isEmpty());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createItemDefinitions(BpmnModel)}.
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createItemDefinitions(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createItemDefinitions(BpmnModel)"})
  public void testCreateItemDefinitions() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    // Act
    webServiceActivityBehavior.createItemDefinitions(new BpmnModel());

    // Assert that nothing has changed
    Map<String, ItemDefinition> stringItemDefinitionMap = webServiceActivityBehavior.itemDefinitionMap;
    assertEquals(1, stringItemDefinitionMap.size());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
  }

  /**
   * Test {@link WebServiceActivityBehavior#createItemDefinitions(BpmnModel)}.
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createItemDefinitions(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createItemDefinitions(BpmnModel)"})
  public void testCreateItemDefinitions2() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addItemDefinition("Trying to load class with current thread context classloader: {}",
        new org.activiti.bpmn.model.ItemDefinition());
    bpmnModel.addItemDefinition("42", new org.activiti.bpmn.model.ItemDefinition());

    // Act
    webServiceActivityBehavior.createItemDefinitions(bpmnModel);

    // Assert
    Map<String, ItemDefinition> stringItemDefinitionMap = webServiceActivityBehavior.itemDefinitionMap;
    assertEquals(2, stringItemDefinitionMap.size());
    ItemDefinition getResult = stringItemDefinitionMap.get(null);
    assertNull(getResult.getId());
    assertNull(getResult.getStructureDefinition());
    assertEquals(ItemKind.Information, getResult.getItemKind());
    assertFalse(getResult.isCollection());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
  }

  /**
   * Test {@link WebServiceActivityBehavior#createItemDefinitions(BpmnModel)}.
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createItemDefinitions(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createItemDefinitions(BpmnModel)"})
  public void testCreateItemDefinitions3() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    org.activiti.bpmn.model.ItemDefinition item = new org.activiti.bpmn.model.ItemDefinition();
    item.setStructureRef("Trying to load class with current thread context classloader: {}");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addItemDefinition("42", item);

    // Act
    webServiceActivityBehavior.createItemDefinitions(bpmnModel);

    // Assert
    Map<String, ItemDefinition> stringItemDefinitionMap = webServiceActivityBehavior.itemDefinitionMap;
    assertEquals(2, stringItemDefinitionMap.size());
    ItemDefinition getResult = stringItemDefinitionMap.get(null);
    assertNull(getResult.getId());
    assertNull(getResult.getStructureDefinition());
    assertEquals(ItemKind.Information, getResult.getItemKind());
    assertFalse(getResult.isCollection());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
  }

  /**
   * Test {@link WebServiceActivityBehavior#createItemDefinitions(BpmnModel)}.
   * <ul>
   *   <li>Given {@link org.activiti.bpmn.model.ItemDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createItemDefinitions(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createItemDefinitions(BpmnModel)"})
  public void testCreateItemDefinitions_givenItemDefinition() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addItemDefinition("42", new org.activiti.bpmn.model.ItemDefinition());

    // Act
    webServiceActivityBehavior.createItemDefinitions(bpmnModel);

    // Assert
    Map<String, ItemDefinition> stringItemDefinitionMap = webServiceActivityBehavior.itemDefinitionMap;
    assertEquals(2, stringItemDefinitionMap.size());
    ItemDefinition getResult = stringItemDefinitionMap.get(null);
    assertNull(getResult.getId());
    assertNull(getResult.getStructureDefinition());
    assertEquals(ItemKind.Information, getResult.getItemKind());
    assertFalse(getResult.isCollection());
    assertTrue(stringItemDefinitionMap.containsKey("http://www.w3.org/2001/XMLSchema:string"));
  }

  /**
   * Test {@link WebServiceActivityBehavior#createMessages(BpmnModel)}.
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createMessages(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createMessages(BpmnModel)"})
  public void testCreateMessages() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    BpmnModel bpmnModel = new BpmnModel();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    bpmnModel.addMessage(message);

    // Act
    webServiceActivityBehavior.createMessages(bpmnModel);

    // Assert
    Map<String, MessageDefinition> stringMessageDefinitionMap = webServiceActivityBehavior.messageDefinitionMap;
    assertEquals(1, stringMessageDefinitionMap.size());
    MessageDefinition getResult = stringMessageDefinitionMap.get("42");
    assertEquals("42", getResult.getId());
    assertNull(getResult.getItemDefinition());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createMessages(BpmnModel)}.
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createMessages(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createMessages(BpmnModel)"})
  public void testCreateMessages2() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    BpmnModel bpmnModel = new BpmnModel();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    bpmnModel.addMessage(message);

    // Act
    webServiceActivityBehavior.createMessages(bpmnModel);

    // Assert
    Map<String, MessageDefinition> stringMessageDefinitionMap = webServiceActivityBehavior.messageDefinitionMap;
    assertEquals(1, stringMessageDefinitionMap.size());
    MessageDefinition getResult = stringMessageDefinitionMap.get("42");
    assertEquals("42", getResult.getId());
    assertNull(getResult.getItemDefinition());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createMessages(BpmnModel)}.
   * <ul>
   *   <li>Then {@link WebServiceActivityBehavior} (default constructor) {@link WebServiceActivityBehavior#messageDefinitionMap} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createMessages(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createMessages(BpmnModel)"})
  public void testCreateMessages_thenWebServiceActivityBehaviorMessageDefinitionMapEmpty() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    // Act
    webServiceActivityBehavior.createMessages(new BpmnModel());

    // Assert that nothing has changed
    assertTrue(webServiceActivityBehavior.messageDefinitionMap.isEmpty());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createOperations(BpmnModel)}.
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createOperations(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createOperations(BpmnModel)"})
  public void testCreateOperations() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(new Operation());

    Interface resultInterface = new Interface();
    resultInterface.setOperations(operations);

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);

    // Act
    webServiceActivityBehavior.createOperations(bpmnModel);

    // Assert
    verify(bpmnModel).getInterfaces();
    Map<String, org.activiti.engine.impl.bpmn.webservice.Operation> stringOperationMap = webServiceActivityBehavior.operationMap;
    assertEquals(1, stringOperationMap.size());
    org.activiti.engine.impl.bpmn.webservice.Operation getResult = stringOperationMap.get(null);
    BpmnInterface resultInterface2 = getResult.getInterface();
    assertNull(resultInterface2.getId());
    assertNull(resultInterface2.getName());
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertNull(resultInterface2.getImplementation());
    assertNull(getResult.getInMessage());
    assertNull(getResult.getOutMessage());
    assertNull(getResult.getImplementation());
    assertTrue(resultInterface2.getOperations().isEmpty());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createOperations(BpmnModel)}.
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createOperations(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createOperations(BpmnModel)"})
  public void testCreateOperations2() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(new Operation());
    operations.add(new Operation());

    Interface resultInterface = new Interface();
    resultInterface.setOperations(operations);

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);

    // Act
    webServiceActivityBehavior.createOperations(bpmnModel);

    // Assert
    verify(bpmnModel).getInterfaces();
    Map<String, org.activiti.engine.impl.bpmn.webservice.Operation> stringOperationMap = webServiceActivityBehavior.operationMap;
    assertEquals(1, stringOperationMap.size());
    org.activiti.engine.impl.bpmn.webservice.Operation getResult = stringOperationMap.get(null);
    BpmnInterface resultInterface2 = getResult.getInterface();
    assertNull(resultInterface2.getId());
    assertNull(resultInterface2.getName());
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertNull(resultInterface2.getImplementation());
    assertNull(getResult.getInMessage());
    assertNull(getResult.getOutMessage());
    assertNull(getResult.getImplementation());
    assertTrue(resultInterface2.getOperations().isEmpty());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createOperations(BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createOperations(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createOperations(BpmnModel)"})
  public void testCreateOperations_givenArrayList() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(new ArrayList<>());

    // Act
    webServiceActivityBehavior.createOperations(bpmnModel);

    // Assert that nothing has changed
    verify(bpmnModel).getInterfaces();
    assertTrue(webServiceActivityBehavior.operationMap.isEmpty());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createOperations(BpmnModel)}.
   * <ul>
   *   <li>Given {@link Operation} {@link Operation#getOutMessageRef()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createOperations(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createOperations(BpmnModel)"})
  public void testCreateOperations_givenOperationGetOutMessageRefReturnEmptyString() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();
    Operation operation = mock(Operation.class);
    when(operation.getId()).thenReturn("42");
    when(operation.getImplementationRef()).thenReturn("Implementation Ref");
    when(operation.getInMessageRef()).thenReturn("In Message Ref");
    when(operation.getName()).thenReturn("Name");
    when(operation.getOutMessageRef()).thenReturn("");

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(operation);

    Interface resultInterface = new Interface();
    resultInterface.setOperations(operations);

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);

    // Act
    webServiceActivityBehavior.createOperations(bpmnModel);

    // Assert
    verify(operation, atLeast(1)).getId();
    verify(bpmnModel).getInterfaces();
    verify(operation).getImplementationRef();
    verify(operation).getInMessageRef();
    verify(operation).getName();
    verify(operation).getOutMessageRef();
    Map<String, org.activiti.engine.impl.bpmn.webservice.Operation> stringOperationMap = webServiceActivityBehavior.operationMap;
    assertEquals(1, stringOperationMap.size());
    org.activiti.engine.impl.bpmn.webservice.Operation getResult = stringOperationMap.get("42");
    assertEquals("42", getResult.getId());
    assertEquals("Name", getResult.getName());
    BpmnInterface resultInterface2 = getResult.getInterface();
    assertNull(resultInterface2.getId());
    assertNull(resultInterface2.getName());
    assertNull(resultInterface2.getImplementation());
    assertNull(getResult.getInMessage());
    assertNull(getResult.getOutMessage());
    assertNull(getResult.getImplementation());
    assertTrue(resultInterface2.getOperations().isEmpty());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createOperations(BpmnModel)}.
   * <ul>
   *   <li>Given {@link Operation} {@link Operation#getOutMessageRef()} return {@code Out Message Ref}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createOperations(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createOperations(BpmnModel)"})
  public void testCreateOperations_givenOperationGetOutMessageRefReturnOutMessageRef() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();
    Operation operation = mock(Operation.class);
    when(operation.getId()).thenReturn("42");
    when(operation.getImplementationRef()).thenReturn("Implementation Ref");
    when(operation.getInMessageRef()).thenReturn("In Message Ref");
    when(operation.getName()).thenReturn("Name");
    when(operation.getOutMessageRef()).thenReturn("Out Message Ref");

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(operation);

    Interface resultInterface = new Interface();
    resultInterface.setOperations(operations);

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);

    // Act
    webServiceActivityBehavior.createOperations(bpmnModel);

    // Assert
    verify(operation, atLeast(1)).getId();
    verify(bpmnModel).getInterfaces();
    verify(operation).getImplementationRef();
    verify(operation).getInMessageRef();
    verify(operation).getName();
    verify(operation, atLeast(1)).getOutMessageRef();
    Map<String, org.activiti.engine.impl.bpmn.webservice.Operation> stringOperationMap = webServiceActivityBehavior.operationMap;
    assertEquals(1, stringOperationMap.size());
    org.activiti.engine.impl.bpmn.webservice.Operation getResult = stringOperationMap.get("42");
    assertEquals("42", getResult.getId());
    assertEquals("Name", getResult.getName());
    BpmnInterface resultInterface2 = getResult.getInterface();
    assertNull(resultInterface2.getId());
    assertNull(resultInterface2.getName());
    assertNull(resultInterface2.getImplementation());
    assertNull(getResult.getInMessage());
    assertNull(getResult.getOutMessage());
    assertNull(getResult.getImplementation());
    assertTrue(resultInterface2.getOperations().isEmpty());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createOperations(BpmnModel)}.
   * <ul>
   *   <li>Then {@link WebServiceActivityBehavior} (default constructor) {@link WebServiceActivityBehavior#operationMap} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createOperations(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createOperations(BpmnModel)"})
  public void testCreateOperations_thenWebServiceActivityBehaviorOperationMapEmpty() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(new Interface());
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);

    // Act
    webServiceActivityBehavior.createOperations(bpmnModel);

    // Assert that nothing has changed
    verify(bpmnModel).getInterfaces();
    assertTrue(webServiceActivityBehavior.operationMap.isEmpty());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createOperations(BpmnModel)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createOperations(BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.createOperations(BpmnModel)"})
  public void testCreateOperations_whenBpmnModel() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    // Act
    webServiceActivityBehavior.createOperations(new BpmnModel());

    // Assert that nothing has changed
    assertTrue(webServiceActivityBehavior.operationMap.isEmpty());
  }

  /**
   * Test {@link WebServiceActivityBehavior#fillImporterInfo(Import, String)}.
   * <ul>
   *   <li>Given {@code http://schemas.xmlsoap.org/wsdl/}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#fillImporterInfo(Import, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.fillImporterInfo(Import, String)"})
  public void testFillImporterInfo_givenHttpSchemasXmlsoapOrgWsdl() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    Import theImport = new Import();
    theImport.setImportType("http://schemas.xmlsoap.org/wsdl/");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> webServiceActivityBehavior.fillImporterInfo(theImport, "42"));
  }

  /**
   * Test {@link WebServiceActivityBehavior#fillImporterInfo(Import, String)}.
   * <ul>
   *   <li>Given {@code Import Type}.</li>
   *   <li>Then calls {@link Import#getImportType()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#fillImporterInfo(Import, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebServiceActivityBehavior.fillImporterInfo(Import, String)"})
  public void testFillImporterInfo_givenImportType_thenCallsGetImportType() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();
    Import theImport = mock(Import.class);
    when(theImport.getImportType()).thenReturn("Import Type");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> webServiceActivityBehavior.fillImporterInfo(theImport, "42"));
    verify(theImport, atLeast(1)).getImportType();
  }

  /**
   * Test {@link WebServiceActivityBehavior#createDataInputAssociation(DataAssociation)}.
   * <ul>
   *   <li>Then return {@link MessageImplicitDataInputAssociation}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createDataInputAssociation(DataAssociation)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractDataAssociation WebServiceActivityBehavior.createDataInputAssociation(DataAssociation)"})
  public void testCreateDataInputAssociation_thenReturnMessageImplicitDataInputAssociation() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    // Act
    AbstractDataAssociation actualCreateDataInputAssociationResult = webServiceActivityBehavior
        .createDataInputAssociation(new DataAssociation());

    // Assert
    assertTrue(actualCreateDataInputAssociationResult instanceof MessageImplicitDataInputAssociation);
    assertNull(actualCreateDataInputAssociationResult.getSource());
    assertNull(actualCreateDataInputAssociationResult.getTarget());
    assertNull(actualCreateDataInputAssociationResult.getSourceExpression());
  }

  /**
   * Test {@link WebServiceActivityBehavior#createDataOutputAssociation(DataAssociation)}.
   * <ul>
   *   <li>Then return {@link MessageImplicitDataOutputAssociation}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebServiceActivityBehavior#createDataOutputAssociation(DataAssociation)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AbstractDataAssociation WebServiceActivityBehavior.createDataOutputAssociation(DataAssociation)"})
  public void testCreateDataOutputAssociation_thenReturnMessageImplicitDataOutputAssociation() {
    // Arrange
    WebServiceActivityBehavior webServiceActivityBehavior = new WebServiceActivityBehavior();

    DataAssociation dataAssociationElement = new DataAssociation();
    dataAssociationElement.setSourceRef("not empty");

    // Act
    AbstractDataAssociation actualCreateDataOutputAssociationResult = webServiceActivityBehavior
        .createDataOutputAssociation(dataAssociationElement);

    // Assert
    assertTrue(actualCreateDataOutputAssociationResult instanceof MessageImplicitDataOutputAssociation);
    assertEquals("not empty", actualCreateDataOutputAssociationResult.getSource());
    assertNull(actualCreateDataOutputAssociationResult.getTarget());
    assertNull(actualCreateDataOutputAssociationResult.getSourceExpression());
  }
}
