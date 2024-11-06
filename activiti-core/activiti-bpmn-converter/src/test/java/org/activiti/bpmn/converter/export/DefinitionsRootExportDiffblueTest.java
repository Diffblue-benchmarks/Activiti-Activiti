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
package org.activiti.bpmn.converter.export;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefinitionsRootExportDiffblueTest {
  /**
   * Test
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute(String)} with name is
   * {@code 1.0}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}
   */
  @Test
  @DisplayName("Test writeRootElement(BpmnModel, XMLStreamWriter, String); given ArrayList() add ExtensionAttribute(String) with name is '1.0'")
  void testWriteRootElement_givenArrayListAddExtensionAttributeWithNameIs10() throws Exception {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("1.0"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("1.0", extensionAttributeList);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getDefinitionsAttributes()).thenReturn(stringListMap);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).setDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    DefinitionsRootExport.writeRootElement(model, xtw, "UTF-8");

    // Assert
    verify(xtw).setDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeStartDocument(eq("UTF-8"), eq("1.0"));
    verify(xtw).writeStartElement(eq("bpmn2"), eq("definitions"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getDefinitionsAttributes();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}
   */
  @Test
  @DisplayName("Test writeRootElement(BpmnModel, XMLStreamWriter, String); given empty string")
  void testWriteRootElement_givenEmptyString() throws Exception {
    // Arrange
    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("");
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).setDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    DefinitionsRootExport.writeRootElement(model, xtw, "UTF-8");

    // Assert
    verify(xtw).setDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeStartDocument(eq("UTF-8"), eq("1.0"));
    verify(xtw).writeStartElement(eq("bpmn2"), eq("definitions"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getDefinitionsAttributes();
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getTargetNamespace();
  }

  /**
   * Test
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute} {@link ExtensionAttribute#getName()}
   * return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}
   */
  @Test
  @DisplayName("Test writeRootElement(BpmnModel, XMLStreamWriter, String); given ExtensionAttribute getName() return 'Name'")
  void testWriteRootElement_givenExtensionAttributeGetNameReturnName() throws Exception {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("1.0", extensionAttributeList);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getDefinitionsAttributes()).thenReturn(stringListMap);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).setDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    DefinitionsRootExport.writeRootElement(model, xtw, "UTF-8");

    // Assert
    verify(xtw).setDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("Namespace Prefix"), eq("Namespace"), eq("Name"), eq("42"));
    verify(xtw).writeDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeStartDocument(eq("UTF-8"), eq("1.0"));
    verify(xtw).writeStartElement(eq("bpmn2"), eq("definitions"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getDefinitionsAttributes();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
  }

  /**
   * Test
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute} {@link ExtensionAttribute#getName()}
   * return {@code typeLanguage}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}
   */
  @Test
  @DisplayName("Test writeRootElement(BpmnModel, XMLStreamWriter, String); given ExtensionAttribute getName() return 'typeLanguage'")
  void testWriteRootElement_givenExtensionAttributeGetNameReturnTypeLanguage() throws Exception {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("typeLanguage");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("1.0", extensionAttributeList);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getDefinitionsAttributes()).thenReturn(stringListMap);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).setDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    DefinitionsRootExport.writeRootElement(model, xtw, "UTF-8");

    // Assert
    verify(xtw).setDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("Namespace Prefix"), eq("Namespace"), eq("typeLanguage"), eq("42"));
    verify(xtw).writeDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeStartDocument(eq("UTF-8"), eq("1.0"));
    verify(xtw).writeStartElement(eq("bpmn2"), eq("definitions"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getDefinitionsAttributes();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
  }

  /**
   * Test
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 1.0} is {@code 1.0}.</li>
   *   <li>Then calls {@link BpmnModel#getDefinitionsAttributes()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}
   */
  @Test
  @DisplayName("Test writeRootElement(BpmnModel, XMLStreamWriter, String); given HashMap() '1.0' is '1.0'; then calls getDefinitionsAttributes()")
  void testWriteRootElement_givenHashMap10Is10_thenCallsGetDefinitionsAttributes() throws Exception {
    // Arrange
    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("1.0", "1.0");
    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(stringStringMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).setDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    DefinitionsRootExport.writeRootElement(model, xtw, "UTF-8");

    // Assert
    verify(xtw).setDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeStartDocument(eq("UTF-8"), eq("1.0"));
    verify(xtw).writeStartElement(eq("bpmn2"), eq("definitions"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getDefinitionsAttributes();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 1.0} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link BpmnModel#getDefinitionsAttributes()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}
   */
  @Test
  @DisplayName("Test writeRootElement(BpmnModel, XMLStreamWriter, String); given HashMap() '1.0' is ArrayList(); then calls getDefinitionsAttributes()")
  void testWriteRootElement_givenHashMap10IsArrayList_thenCallsGetDefinitionsAttributes() throws Exception {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("1.0", new ArrayList<>());
    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getDefinitionsAttributes()).thenReturn(stringListMap);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).setDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    DefinitionsRootExport.writeRootElement(model, xtw, "UTF-8");

    // Assert
    verify(xtw).setDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeStartDocument(eq("UTF-8"), eq("1.0"));
    verify(xtw).writeStartElement(eq("bpmn2"), eq("definitions"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getDefinitionsAttributes();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code bpmn2} is {@code bpmn2}.</li>
   *   <li>Then calls {@link BpmnModel#getDefinitionsAttributes()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}
   */
  @Test
  @DisplayName("Test writeRootElement(BpmnModel, XMLStreamWriter, String); given HashMap() 'bpmn2' is 'bpmn2'; then calls getDefinitionsAttributes()")
  void testWriteRootElement_givenHashMapBpmn2IsBpmn2_thenCallsGetDefinitionsAttributes() throws Exception {
    // Arrange
    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");
    stringStringMap.put("1.0", "1.0");
    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(stringStringMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).setDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    DefinitionsRootExport.writeRootElement(model, xtw, "UTF-8");

    // Assert
    verify(xtw).setDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeStartDocument(eq("UTF-8"), eq("1.0"));
    verify(xtw).writeStartElement(eq("bpmn2"), eq("definitions"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getDefinitionsAttributes();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} empty string is {@code 1.0}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}
   */
  @Test
  @DisplayName("Test writeRootElement(BpmnModel, XMLStreamWriter, String); given HashMap() empty string is '1.0'")
  void testWriteRootElement_givenHashMapEmptyStringIs10() throws Exception {
    // Arrange
    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");
    stringStringMap.put("", "1.0");
    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(stringStringMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).setDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    DefinitionsRootExport.writeRootElement(model, xtw, "UTF-8");

    // Assert
    verify(xtw).setDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeStartDocument(eq("UTF-8"), eq("1.0"));
    verify(xtw).writeStartElement(eq("bpmn2"), eq("definitions"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getDefinitionsAttributes();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}.
   * <ul>
   *   <li>Given {@code Target Namespace}.</li>
   *   <li>Then calls {@link BpmnModel#getDefinitionsAttributes()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefinitionsRootExport#writeRootElement(BpmnModel, XMLStreamWriter, String)}
   */
  @Test
  @DisplayName("Test writeRootElement(BpmnModel, XMLStreamWriter, String); given 'Target Namespace'; then calls getDefinitionsAttributes()")
  void testWriteRootElement_givenTargetNamespace_thenCallsGetDefinitionsAttributes() throws Exception {
    // Arrange
    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).setDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeDefaultNamespace(Mockito.<String>any());
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    DefinitionsRootExport.writeRootElement(model, xtw, "UTF-8");

    // Assert
    verify(xtw).setDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeDefaultNamespace(eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(xtw, atLeast(1)).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeStartDocument(eq("UTF-8"), eq("1.0"));
    verify(xtw).writeStartElement(eq("bpmn2"), eq("definitions"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getDefinitionsAttributes();
    verify(model, atLeast(1)).getNamespaces();
    verify(model, atLeast(1)).getTargetNamespace();
  }
}
