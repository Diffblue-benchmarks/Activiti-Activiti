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
package org.activiti.bpmn.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SubprocessXMLConverterDiffblueTest {
  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SubprocessXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ", "  ");
    attribute.setValue("  ");

    Process process = new Process();
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel model = new BpmnModel();
    model.addNamespace("", "");
    model.addProcess(process);
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> subprocessXMLConverter.convertToXML(model, "UTF-8"));
  }

  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <p>Method under test: {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName("Test convertToXML(BpmnModel, String) with 'model', 'encoding'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SubprocessXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding2() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ");
    attribute.setNamespacePrefix("  ");
    attribute.setValue("  ");

    Process process = new Process();
    process.setId(null);
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel model = new BpmnModel();
    model.addNamespace("", "");
    model.addProcess(process);
    model.addDefinitionsAttribute(attribute);

    // Act and Assert
    assertThrows(XMLException.class, () -> subprocessXMLConverter.convertToXML(model, "UTF-8"));
  }

  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is seven hundred ten.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is seven hundred ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SubprocessXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSevenHundredTen() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    model.addMessage(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(710, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[691]);
    assertEquals('2', actualConvertToXMLResult[696]);
    assertEquals(':', actualConvertToXMLResult[697]);
    assertEquals('<', actualConvertToXMLResult[690]);
    assertEquals('>', actualConvertToXMLResult[688]);
    assertEquals('>', actualConvertToXMLResult[709]);
    assertEquals('\n', actualConvertToXMLResult[689]);
    assertEquals('a', actualConvertToXMLResult[685]);
    assertEquals('b', actualConvertToXMLResult[692]);
    assertEquals('d', actualConvertToXMLResult[698]);
    assertEquals('e', actualConvertToXMLResult[687]);
    assertEquals('f', actualConvertToXMLResult[700]);
    assertEquals('g', actualConvertToXMLResult[686]);
    assertEquals('i', actualConvertToXMLResult[701]);
    assertEquals('i', actualConvertToXMLResult[703]);
    assertEquals('i', actualConvertToXMLResult[705]);
    assertEquals('m', actualConvertToXMLResult[694]);
    assertEquals('n', actualConvertToXMLResult[695]);
    assertEquals('n', actualConvertToXMLResult[702]);
    assertEquals('o', actualConvertToXMLResult[706]);
    assertEquals('p', actualConvertToXMLResult[693]);
    assertEquals('s', actualConvertToXMLResult[708]);
    assertEquals('t', actualConvertToXMLResult[704]);
  }

  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is seven hundred twenty-two.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is seven hundred twenty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SubprocessXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSevenHundredTwentyTwo() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addNamespace("\n", "\n");

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    model.addMessage(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(722, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[703]);
    assertEquals('2', actualConvertToXMLResult[708]);
    assertEquals(':', actualConvertToXMLResult[709]);
    assertEquals('<', actualConvertToXMLResult[702]);
    assertEquals('>', actualConvertToXMLResult[700]);
    assertEquals('>', actualConvertToXMLResult[721]);
    assertEquals('\n', actualConvertToXMLResult[701]);
    assertEquals('a', actualConvertToXMLResult[697]);
    assertEquals('b', actualConvertToXMLResult[704]);
    assertEquals('d', actualConvertToXMLResult[710]);
    assertEquals('e', actualConvertToXMLResult[711]);
    assertEquals('f', actualConvertToXMLResult[712]);
    assertEquals('g', actualConvertToXMLResult[698]);
    assertEquals('i', actualConvertToXMLResult[713]);
    assertEquals('i', actualConvertToXMLResult[715]);
    assertEquals('i', actualConvertToXMLResult[717]);
    assertEquals('m', actualConvertToXMLResult[706]);
    assertEquals('n', actualConvertToXMLResult[714]);
    assertEquals('n', actualConvertToXMLResult[719]);
    assertEquals('o', actualConvertToXMLResult[718]);
    assertEquals('p', actualConvertToXMLResult[705]);
    assertEquals('s', actualConvertToXMLResult[720]);
    assertEquals('t', actualConvertToXMLResult[716]);
  }

  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is six hundred eighty-four.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is six hundred eighty-four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SubprocessXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSixHundredEightyFour() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.setTargetNamespace("  ");

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    model.addMessage(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(684, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[665]);
    assertEquals('2', actualConvertToXMLResult[670]);
    assertEquals(':', actualConvertToXMLResult[671]);
    assertEquals('<', actualConvertToXMLResult[664]);
    assertEquals('>', actualConvertToXMLResult[662]);
    assertEquals('>', actualConvertToXMLResult[683]);
    assertEquals('\n', actualConvertToXMLResult[663]);
    assertEquals('a', actualConvertToXMLResult[659]);
    assertEquals('b', actualConvertToXMLResult[666]);
    assertEquals('d', actualConvertToXMLResult[672]);
    assertEquals('e', actualConvertToXMLResult[661]);
    assertEquals('e', actualConvertToXMLResult[673]);
    assertEquals('f', actualConvertToXMLResult[674]);
    assertEquals('g', actualConvertToXMLResult[660]);
    assertEquals('i', actualConvertToXMLResult[675]);
    assertEquals('i', actualConvertToXMLResult[677]);
    assertEquals('i', actualConvertToXMLResult[679]);
    assertEquals('m', actualConvertToXMLResult[668]);
    assertEquals('n', actualConvertToXMLResult[669]);
    assertEquals('n', actualConvertToXMLResult[676]);
    assertEquals('n', actualConvertToXMLResult[681]);
    assertEquals('o', actualConvertToXMLResult[680]);
    assertEquals('p', actualConvertToXMLResult[667]);
    assertEquals('s', actualConvertToXMLResult[682]);
    assertEquals('t', actualConvertToXMLResult[678]);
  }

  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is six hundred fifty.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is six hundred fifty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SubprocessXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSixHundredFifty() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addNamespace("  ", "  ");
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(650, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[631]);
    assertEquals('2', actualConvertToXMLResult[636]);
    assertEquals(':', actualConvertToXMLResult[637]);
    assertEquals('<', actualConvertToXMLResult[630]);
    assertEquals('>', actualConvertToXMLResult[629]);
    assertEquals('>', actualConvertToXMLResult[649]);
    assertEquals('"', actualConvertToXMLResult[628]);
    assertEquals('b', actualConvertToXMLResult[632]);
    assertEquals('d', actualConvertToXMLResult[638]);
    assertEquals('e', actualConvertToXMLResult[639]);
    assertEquals('f', actualConvertToXMLResult[640]);
    assertEquals('i', actualConvertToXMLResult[641]);
    assertEquals('i', actualConvertToXMLResult[643]);
    assertEquals('i', actualConvertToXMLResult[645]);
    assertEquals('m', actualConvertToXMLResult[634]);
    assertEquals('n', actualConvertToXMLResult[635]);
    assertEquals('n', actualConvertToXMLResult[642]);
    assertEquals('n', actualConvertToXMLResult[647]);
    assertEquals('o', actualConvertToXMLResult[646]);
    assertEquals('p', actualConvertToXMLResult[633]);
    assertEquals('s', actualConvertToXMLResult[626]);
    assertEquals('s', actualConvertToXMLResult[648]);
    assertEquals('t', actualConvertToXMLResult[627]);
    assertEquals('t', actualConvertToXMLResult[644]);
  }

  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is six hundred forty-four.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is six hundred forty-four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SubprocessXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSixHundredFortyFour() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    ExtensionAttribute attribute = new ExtensionAttribute("  ");
    attribute.setValue("  ");

    BpmnModel model = new BpmnModel();
    model.addProcess(new Process());
    model.addDefinitionsAttribute(attribute);

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(644, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[620]);
    assertEquals(' ', actualConvertToXMLResult[621]);
    assertEquals('/', actualConvertToXMLResult[625]);
    assertEquals('2', actualConvertToXMLResult[630]);
    assertEquals(':', actualConvertToXMLResult[631]);
    assertEquals('<', actualConvertToXMLResult[624]);
    assertEquals('>', actualConvertToXMLResult[623]);
    assertEquals('>', actualConvertToXMLResult[643]);
    assertEquals('"', actualConvertToXMLResult[619]);
    assertEquals('"', actualConvertToXMLResult[622]);
    assertEquals('b', actualConvertToXMLResult[626]);
    assertEquals('d', actualConvertToXMLResult[632]);
    assertEquals('e', actualConvertToXMLResult[633]);
    assertEquals('f', actualConvertToXMLResult[634]);
    assertEquals('i', actualConvertToXMLResult[635]);
    assertEquals('i', actualConvertToXMLResult[637]);
    assertEquals('i', actualConvertToXMLResult[639]);
    assertEquals('m', actualConvertToXMLResult[628]);
    assertEquals('n', actualConvertToXMLResult[629]);
    assertEquals('n', actualConvertToXMLResult[636]);
    assertEquals('n', actualConvertToXMLResult[641]);
    assertEquals('o', actualConvertToXMLResult[640]);
    assertEquals('p', actualConvertToXMLResult[627]);
    assertEquals('s', actualConvertToXMLResult[642]);
    assertEquals('t', actualConvertToXMLResult[638]);
  }

  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is six hundred seventy-five.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is six hundred seventy-five")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SubprocessXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSixHundredSeventyFive() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addSignal(new Signal("42", "  "));
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(675, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[656]);
    assertEquals('2', actualConvertToXMLResult[661]);
    assertEquals(':', actualConvertToXMLResult[662]);
    assertEquals('<', actualConvertToXMLResult[655]);
    assertEquals('>', actualConvertToXMLResult[653]);
    assertEquals('>', actualConvertToXMLResult[674]);
    assertEquals('\n', actualConvertToXMLResult[654]);
    assertEquals('a', actualConvertToXMLResult[651]);
    assertEquals('b', actualConvertToXMLResult[657]);
    assertEquals('d', actualConvertToXMLResult[663]);
    assertEquals('e', actualConvertToXMLResult[664]);
    assertEquals('f', actualConvertToXMLResult[665]);
    assertEquals('i', actualConvertToXMLResult[666]);
    assertEquals('i', actualConvertToXMLResult[668]);
    assertEquals('i', actualConvertToXMLResult[670]);
    assertEquals('l', actualConvertToXMLResult[652]);
    assertEquals('m', actualConvertToXMLResult[659]);
    assertEquals('n', actualConvertToXMLResult[650]);
    assertEquals('n', actualConvertToXMLResult[660]);
    assertEquals('n', actualConvertToXMLResult[667]);
    assertEquals('n', actualConvertToXMLResult[672]);
    assertEquals('o', actualConvertToXMLResult[671]);
    assertEquals('p', actualConvertToXMLResult[658]);
    assertEquals('s', actualConvertToXMLResult[673]);
    assertEquals('t', actualConvertToXMLResult[669]);
  }

  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is six hundred ten.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is six hundred ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SubprocessXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSixHundredTen() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.setTargetNamespace("  ");
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(610, actualConvertToXMLResult.length);
    assertEquals(' ', actualConvertToXMLResult[586]);
    assertEquals(' ', actualConvertToXMLResult[587]);
    assertEquals('/', actualConvertToXMLResult[591]);
    assertEquals('2', actualConvertToXMLResult[596]);
    assertEquals(':', actualConvertToXMLResult[597]);
    assertEquals('<', actualConvertToXMLResult[590]);
    assertEquals('>', actualConvertToXMLResult[589]);
    assertEquals('>', actualConvertToXMLResult[609]);
    assertEquals('"', actualConvertToXMLResult[585]);
    assertEquals('"', actualConvertToXMLResult[588]);
    assertEquals('b', actualConvertToXMLResult[592]);
    assertEquals('d', actualConvertToXMLResult[598]);
    assertEquals('e', actualConvertToXMLResult[599]);
    assertEquals('f', actualConvertToXMLResult[600]);
    assertEquals('i', actualConvertToXMLResult[601]);
    assertEquals('i', actualConvertToXMLResult[603]);
    assertEquals('i', actualConvertToXMLResult[605]);
    assertEquals('m', actualConvertToXMLResult[594]);
    assertEquals('n', actualConvertToXMLResult[595]);
    assertEquals('n', actualConvertToXMLResult[602]);
    assertEquals('n', actualConvertToXMLResult[607]);
    assertEquals('o', actualConvertToXMLResult[606]);
    assertEquals('p', actualConvertToXMLResult[593]);
    assertEquals('s', actualConvertToXMLResult[608]);
    assertEquals('t', actualConvertToXMLResult[604]);
  }

  /**
   * Test {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)} with {@code model}, {@code
   * encoding}.
   *
   * <ul>
   *   <li>Then return array length is six hundred thirty-six.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#convertToXML(BpmnModel, String)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(BpmnModel, String) with 'model', 'encoding'; then return array length is six hundred thirty-six")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SubprocessXMLConverter.convertToXML(BpmnModel, String)"})
  void testConvertToXMLWithModelEncoding_thenReturnArrayLengthIsSixHundredThirtySix() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    model.addProcess(new Process());

    // Act
    byte[] actualConvertToXMLResult = subprocessXMLConverter.convertToXML(model, "UTF-8");

    // Assert
    assertEquals(636, actualConvertToXMLResult.length);
    assertEquals('/', actualConvertToXMLResult[617]);
    assertEquals('2', actualConvertToXMLResult[622]);
    assertEquals(':', actualConvertToXMLResult[623]);
    assertEquals('<', actualConvertToXMLResult[616]);
    assertEquals('>', actualConvertToXMLResult[615]);
    assertEquals('>', actualConvertToXMLResult[635]);
    assertEquals('"', actualConvertToXMLResult[614]);
    assertEquals('b', actualConvertToXMLResult[618]);
    assertEquals('d', actualConvertToXMLResult[624]);
    assertEquals('e', actualConvertToXMLResult[611]);
    assertEquals('f', actualConvertToXMLResult[626]);
    assertEquals('i', actualConvertToXMLResult[627]);
    assertEquals('i', actualConvertToXMLResult[629]);
    assertEquals('i', actualConvertToXMLResult[631]);
    assertEquals('m', actualConvertToXMLResult[620]);
    assertEquals('n', actualConvertToXMLResult[621]);
    assertEquals('n', actualConvertToXMLResult[628]);
    assertEquals('n', actualConvertToXMLResult[633]);
    assertEquals('o', actualConvertToXMLResult[632]);
    assertEquals('p', actualConvertToXMLResult[619]);
    assertEquals('s', actualConvertToXMLResult[612]);
    assertEquals('s', actualConvertToXMLResult[634]);
    assertEquals('t', actualConvertToXMLResult[613]);
    assertEquals('t', actualConvertToXMLResult[630]);
  }

  /**
   * Test {@link SubprocessXMLConverter#parseSubModels(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement {@link BooleanDataObject}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseSubModels(BpmnModel) with 'model'; given Process (default constructor) addFlowElement BooleanDataObject (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SubprocessXMLConverter.parseSubModels(BpmnModel)"})
  void testParseSubModelsWithModel_givenProcessAddFlowElementBooleanDataObject() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    BpmnModel model = new BpmnModel();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(1, actualParseSubModelsResult.size());
    BpmnModel getResult = actualParseSubModelsResult.get(0);
    List<Process> processes = getResult.getProcesses();
    assertEquals(1, processes.size());
    assertSame(process, processes.get(0));
    assertSame(process, getResult.getMainProcess());
  }

  /**
   * Test {@link SubprocessXMLConverter#parseSubModels(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseSubModels(BpmnModel) with 'model'; given Process (default constructor); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SubprocessXMLConverter.parseSubModels(BpmnModel)"})
  void testParseSubModelsWithModel_givenProcess_thenReturnSizeIsOne() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    BpmnModel model = new BpmnModel();
    Process process = new Process();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(1, actualParseSubModelsResult.size());
    BpmnModel getResult = actualParseSubModelsResult.get(0);
    List<Process> processes = getResult.getProcesses();
    assertEquals(1, processes.size());
    assertSame(process, processes.get(0));
    assertSame(process, getResult.getMainProcess());
  }

  /**
   * Test {@link SubprocessXMLConverter#parseSubModels(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return first MainProcess FlowElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseSubModels(BpmnModel) with 'model'; then return first MainProcess FlowElements size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SubprocessXMLConverter.parseSubModels(BpmnModel)"})
  void testParseSubModelsWithModel_thenReturnFirstMainProcessFlowElementsSizeIsOne() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    BooleanDataObject element2 = new BooleanDataObject();
    element.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel model = new BpmnModel();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(2, actualParseSubModelsResult.size());
    Collection<FlowElement> flowElements =
        actualParseSubModelsResult.get(0).getMainProcess().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    Collection<FlowElement> flowElements2 =
        actualParseSubModelsResult.get(1).getMainProcess().getFlowElements();
    assertEquals(1, flowElements2.size());
    assertTrue(flowElements2 instanceof List);
    FlowElement getResult = ((List<FlowElement>) flowElements2).get(0);
    Collection<FlowElement> flowElements3 = ((AdhocSubProcess) getResult).getFlowElements();
    assertEquals(1, flowElements3.size());
    assertTrue(flowElements3 instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    FlowElement getResult2 = ((List<FlowElement>) flowElements).get(0);
    assertTrue(getResult2 instanceof BooleanDataObject);
    assertSame(element, getResult2.getParentContainer());
    assertSame(element, getResult2.getSubProcess());
    assertSame(element2, ((List<FlowElement>) flowElements3).get(0));
  }

  /**
   * Test {@link SubprocessXMLConverter#parseSubModels(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return second Processes size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseSubModels(BpmnModel) with 'model'; then return second Processes size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SubprocessXMLConverter.parseSubModels(BpmnModel)"})
  void testParseSubModelsWithModel_thenReturnSecondProcessesSizeIsOne() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel model = new BpmnModel();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(2, actualParseSubModelsResult.size());
    BpmnModel getResult = actualParseSubModelsResult.get(1);
    List<Process> processes = getResult.getProcesses();
    assertEquals(1, processes.size());
    assertSame(process, processes.get(0));
    assertSame(process, getResult.getMainProcess());
  }

  /**
   * Test {@link SubprocessXMLConverter#parseSubModels(BpmnModel)} with {@code model}.
   *
   * <ul>
   *   <li>Then return size is three.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessXMLConverter#parseSubModels(BpmnModel)}
   */
  @Test
  @DisplayName("Test parseSubModels(BpmnModel) with 'model'; then return size is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SubprocessXMLConverter.parseSubModels(BpmnModel)"})
  void testParseSubModelsWithModel_thenReturnSizeIsThree() {
    // Arrange
    SubprocessXMLConverter subprocessXMLConverter = new SubprocessXMLConverter();

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);

    BpmnModel model = new BpmnModel();
    model.addProcess(process);

    // Act
    List<BpmnModel> actualParseSubModelsResult = subprocessXMLConverter.parseSubModels(model);

    // Assert
    assertEquals(3, actualParseSubModelsResult.size());
    BpmnModel getResult = actualParseSubModelsResult.get(2);
    Collection<Resource> resources = getResult.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = getResult.getSignals();
    assertTrue(signals instanceof List);
    assertNull(getResult.getEventSupport());
    assertNull(getResult.getSourceSystemId());
    assertNull(getResult.getTargetNamespace());
    assertNull(getResult.getStartEventFormTypes());
    assertNull(getResult.getUserTaskFormTypes());
    assertEquals(1, getResult.getProcesses().size());
    assertFalse(getResult.hasDiagramInterchangeInfo());
    assertTrue(getResult.getMessages().isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult.getGlobalArtifacts().isEmpty());
    assertTrue(getResult.getImports().isEmpty());
    assertTrue(getResult.getInterfaces().isEmpty());
    assertTrue(getResult.getPools().isEmpty());
    assertTrue(getResult.getDataStores().isEmpty());
    assertTrue(getResult.getDefinitionsAttributes().isEmpty());
    assertTrue(getResult.getErrors().isEmpty());
    assertTrue(getResult.getFlowLocationMap().isEmpty());
    assertTrue(getResult.getItemDefinitions().isEmpty());
    assertTrue(getResult.getLabelLocationMap().isEmpty());
    assertTrue(getResult.getLocationMap().isEmpty());
    assertTrue(getResult.getMessageFlows().isEmpty());
    assertTrue(getResult.getNamespaces().isEmpty());
    assertSame(process, getResult.getMainProcess());
  }

  /**
   * Test new {@link SubprocessXMLConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link SubprocessXMLConverter}
   */
  @Test
  @DisplayName("Test new SubprocessXMLConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubprocessXMLConverter.<init>()"})
  void testNewSubprocessXMLConverter() {
    // Arrange and Act
    SubprocessXMLConverter actualSubprocessXMLConverter = new SubprocessXMLConverter();

    // Assert
    assertEquals(
        "documentation", actualSubprocessXMLConverter.documentationParser.getElementName());
    assertEquals(
        "ioSpecification", actualSubprocessXMLConverter.ioSpecificationParser.getElementName());
    assertEquals(
        "multiInstanceLoopCharacteristics",
        actualSubprocessXMLConverter.multiInstanceParser.getElementName());
    assertNull(actualSubprocessXMLConverter.classloader);
    assertNull(actualSubprocessXMLConverter.startEventFormTypes);
    assertNull(actualSubprocessXMLConverter.userTaskFormTypes);
  }
}
