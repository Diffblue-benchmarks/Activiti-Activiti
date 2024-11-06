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
package org.activiti.editor.language.json.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.ItemDefinition;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.UserTask;
import org.activiti.bpmn.model.ValuedDataObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnJsonConverterUtilDiffblueTest {
  /**
   * Test
   * {@link BpmnJsonConverterUtil#createChildShape(String, String, double, double, double, double)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#createChildShape(String, String, double, double, double, double)}
   */
  @Test
  @DisplayName("Test createChildShape(String, String, double, double, double, double)")
  void testCreateChildShape() {
    // Arrange and Act
    ObjectNode actualCreateChildShapeResult = BpmnJsonConverterUtil.createChildShape("", "Type", 10.0d, 10.0d, 10.0d,
        10.0d);

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateChildShapeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    JsonNode nextResult2 = iteratorResult.next();
    JsonNode nextResult3 = iteratorResult.next();
    JsonNode nextResult4 = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult3 instanceof ArrayNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult5 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult5.iterator();
    JsonNode nextResult6 = iteratorResult3.next();
    assertTrue(nextResult6 instanceof DoubleNode);
    assertTrue(iteratorResult3.next() instanceof DoubleNode);
    assertTrue(nextResult5 instanceof ObjectNode);
    assertTrue(iteratorResult2.next() instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult4 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult4.iterator();
    JsonNode nextResult7 = iteratorResult4.next();
    assertTrue(nextResult7 instanceof TextNode);
    assertTrue(nextResult2 instanceof TextNode);
    assertTrue(nextResult6.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult5.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult7.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult4.traverse() instanceof TreeTraversingParser);
    assertTrue(actualCreateChildShapeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("\"\"", nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 10.0,\n"
        + "      \"y\" : 10.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : 10.0,\n"
        + "      \"y\" : 10.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : \"\",\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"Type\"\n" + "  }\n" + "}",
        actualCreateChildShapeResult.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(actualHasNextResult);
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#createChildShape(String, String, double, double, double, double)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return iterator next toPrettyString is {@code "42"}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#createChildShape(String, String, double, double, double, double)}
   */
  @Test
  @DisplayName("Test createChildShape(String, String, double, double, double, double); when '42'; then return iterator next toPrettyString is '\"42\"'")
  void testCreateChildShape_when42_thenReturnIteratorNextToPrettyStringIs42() {
    // Arrange and Act
    ObjectNode actualCreateChildShapeResult = BpmnJsonConverterUtil.createChildShape("42", "Type", 10.0d, 10.0d, 10.0d,
        10.0d);

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateChildShapeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    JsonNode nextResult2 = iteratorResult.next();
    JsonNode nextResult3 = iteratorResult.next();
    JsonNode nextResult4 = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult3 instanceof ArrayNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult5 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult5.iterator();
    JsonNode nextResult6 = iteratorResult3.next();
    assertTrue(nextResult6 instanceof DoubleNode);
    assertTrue(iteratorResult3.next() instanceof DoubleNode);
    assertTrue(nextResult5 instanceof ObjectNode);
    assertTrue(iteratorResult2.next() instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult4 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult4.iterator();
    JsonNode nextResult7 = iteratorResult4.next();
    assertTrue(nextResult7 instanceof TextNode);
    assertTrue(nextResult2 instanceof TextNode);
    assertTrue(nextResult6.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult5.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult7.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult2.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult3.traverse() instanceof TreeTraversingParser);
    assertTrue(nextResult4.traverse() instanceof TreeTraversingParser);
    assertTrue(actualCreateChildShapeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("\"42\"", nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 10.0,\n"
        + "      \"y\" : 10.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : 10.0,\n"
        + "      \"y\" : 10.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : \"42\",\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : \"Type\"\n" + "  }\n" + "}",
        actualCreateChildShapeResult.toPrettyString());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(actualHasNextResult);
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#createBoundsNode(double, double, double, double)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#createBoundsNode(double, double, double, double)}
   */
  @Test
  @DisplayName("Test createBoundsNode(double, double, double, double)")
  void testCreateBoundsNode() throws IOException {
    // Arrange and Act
    ObjectNode actualCreateBoundsNodeResult = BpmnJsonConverterUtil.createBoundsNode(10.0d, 10.0d, 10.0d, 10.0d);

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateBoundsNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof DoubleNode);
    assertTrue(iteratorResult2.next() instanceof DoubleNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    JsonParser traverseResult = nextResult2.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = actualCreateBoundsNodeResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    assertEquals("10.0", nextResult2.toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    Version versionResult = traverseResult3.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals(
        "{\n" + "  \"lowerRight\" : {\n" + "    \"x\" : 10.0,\n" + "    \"y\" : 10.0\n" + "  },\n"
            + "  \"upperLeft\" : {\n" + "    \"x\" : 10.0,\n" + "    \"y\" : 10.0\n" + "  }\n" + "}",
        actualCreateBoundsNodeResult.toPrettyString());
    assertEquals("{\n  \"x\" : 10.0,\n  \"y\" : 10.0\n}", nextResult.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult3.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, nextResult2.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0.0d, traverseResult3.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, nextResult.size());
    assertEquals(2, actualCreateBoundsNodeResult.size());
    assertEquals(JsonNodeType.NUMBER, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualCreateBoundsNodeResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualCreateBoundsNodeResult.isArray());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualCreateBoundsNodeResult.isBigDecimal());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualCreateBoundsNodeResult.isBigInteger());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualCreateBoundsNodeResult.isBinary());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualCreateBoundsNodeResult.isBoolean());
    assertFalse(nextResult2.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(actualCreateBoundsNodeResult.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualCreateBoundsNodeResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualCreateBoundsNodeResult.isFloatingPointNumber());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualCreateBoundsNodeResult.isInt());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualCreateBoundsNodeResult.isIntegralNumber());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualCreateBoundsNodeResult.isLong());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualCreateBoundsNodeResult.isMissingNode());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualCreateBoundsNodeResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(actualCreateBoundsNodeResult.isNumber());
    assertFalse(nextResult2.isObject());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualCreateBoundsNodeResult.isPojo());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualCreateBoundsNodeResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualCreateBoundsNodeResult.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualCreateBoundsNodeResult.isValueNode());
    assertFalse(((DoubleNode) nextResult2).isNaN());
    assertFalse(actualCreateBoundsNodeResult.isEmpty());
    assertFalse(nextResult2.iterator().hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualCreateBoundsNodeResult.isContainerNode());
    assertTrue(nextResult2.isDouble());
    assertTrue(nextResult2.isEmpty());
    assertTrue(nextResult2.isFloatingPointNumber());
    assertTrue(nextResult2.isNumber());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isValueNode());
    assertTrue(actualCreateBoundsNodeResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#createPositionNode(double, double)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#createPositionNode(double, double)}
   */
  @Test
  @DisplayName("Test createPositionNode(double, double)")
  void testCreatePositionNode() throws IOException {
    // Arrange and Act
    ObjectNode actualCreatePositionNodeResult = BpmnJsonConverterUtil.createPositionNode(2.0d, 3.0d);

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreatePositionNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof DoubleNode);
    JsonNode nextResult2 = iteratorResult.next();
    assertTrue(nextResult2 instanceof DoubleNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = actualCreatePositionNodeResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    assertEquals("2.0", nextResult.toPrettyString());
    assertEquals("3.0", nextResult2.toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    Version versionResult = traverseResult3.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"x\" : 2.0,\n  \"y\" : 3.0\n}", actualCreatePositionNodeResult.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult3.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0, nextResult2.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0.0d, traverseResult3.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, actualCreatePositionNodeResult.size());
    assertEquals(JsonNodeType.NUMBER, nextResult.getNodeType());
    assertEquals(JsonNodeType.NUMBER, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualCreatePositionNodeResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(actualCreatePositionNodeResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(actualCreatePositionNodeResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(actualCreatePositionNodeResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(actualCreatePositionNodeResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(actualCreatePositionNodeResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult2.isContainerNode());
    assertFalse(actualCreatePositionNodeResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(actualCreatePositionNodeResult.isFloat());
    assertFalse(actualCreatePositionNodeResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(actualCreatePositionNodeResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(actualCreatePositionNodeResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(actualCreatePositionNodeResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(actualCreatePositionNodeResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(actualCreatePositionNodeResult.isNull());
    assertFalse(actualCreatePositionNodeResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult2.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(actualCreatePositionNodeResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(actualCreatePositionNodeResult.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(actualCreatePositionNodeResult.isTextual());
    assertFalse(actualCreatePositionNodeResult.isValueNode());
    assertFalse(((DoubleNode) nextResult).isNaN());
    assertFalse(((DoubleNode) nextResult2).isNaN());
    assertFalse(actualCreatePositionNodeResult.isEmpty());
    assertFalse(nextResult.iterator().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(actualCreatePositionNodeResult.isContainerNode());
    assertTrue(nextResult.isDouble());
    assertTrue(nextResult2.isDouble());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult2.isEmpty());
    assertTrue(nextResult.isFloatingPointNumber());
    assertTrue(nextResult2.isFloatingPointNumber());
    assertTrue(nextResult.isNumber());
    assertTrue(nextResult2.isNumber());
    assertTrue(nextResult.isValueNode());
    assertTrue(nextResult2.isValueNode());
    assertTrue(actualCreatePositionNodeResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#createResourceNode(String)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  @DisplayName("Test createResourceNode(String)")
  void testCreateResourceNode() {
    // Arrange and Act
    ObjectNode actualCreateResourceNodeResult = BpmnJsonConverterUtil.createResourceNode("");

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateResourceNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualCreateResourceNodeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("\"\"", nextResult.toPrettyString());
    assertEquals("{\n  \"resourceId\" : \"\"\n}", actualCreateResourceNodeResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#createResourceNode(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return iterator next toPrettyString is {@code "42"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  @DisplayName("Test createResourceNode(String); when '42'; then return iterator next toPrettyString is '\"42\"'")
  void testCreateResourceNode_when42_thenReturnIteratorNextToPrettyStringIs42() {
    // Arrange and Act
    ObjectNode actualCreateResourceNodeResult = BpmnJsonConverterUtil.createResourceNode("42");

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateResourceNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualCreateResourceNodeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("\"42\"", nextResult.toPrettyString());
    assertEquals("{\n  \"resourceId\" : \"42\"\n}", actualCreateResourceNodeResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#createResourceNode(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then iterator next return {@link NullNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  @DisplayName("Test createResourceNode(String); when 'null'; then iterator next return NullNode")
  void testCreateResourceNode_whenNull_thenIteratorNextReturnNullNode() {
    // Arrange and Act
    ObjectNode actualCreateResourceNodeResult = BpmnJsonConverterUtil.createResourceNode(null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateResourceNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualCreateResourceNodeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"resourceId\" : null\n}", actualCreateResourceNodeResult.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getStencilId(JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getStencilId(JsonNode)}
   */
  @Test
  @DisplayName("Test getStencilId(JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then return 'null'")
  void testGetStencilId_whenArrayNodeWithNfIsWithExactBigDecimalsTrue_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getStencilId(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getStencilId(JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getStencilId(JsonNode)}
   */
  @Test
  @DisplayName("Test getStencilId(JsonNode); when Instance; then return 'null'")
  void testGetStencilId_whenInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getStencilId(MissingNode.getInstance()));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode)")
  void testGetElementId() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("overrideid"));
    verify(arrayNode).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then return {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); given ArrayNode get(String) return Instance; then return 'As Text'")
  void testGetElementId_givenArrayNodeGetReturnInstance_thenReturnAsText() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("overrideid"));
    verify(arrayNode).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then return {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); given ArrayNode get(String) return Instance; then return 'As Text'")
  void testGetElementId_givenArrayNodeGetReturnInstance_thenReturnAsText2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("overrideid"));
    verify(arrayNode).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); given ArrayNode isNull() return 'true'; then calls isNull()")
  void testGetElementId_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(arrayNode).isNull();
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2).get(eq("overrideid"));
    verify(arrayNode2).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testGetElementId_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); given Instance; when ArrayNode get(String) return Instance")
  void testGetElementId_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); then return '1'")
  void testGetElementId_thenReturn1() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(arrayNode, atLeast(1)).get(eq("overrideid"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertEquals("1", actualElementId);
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertMessagesToJson(BpmnModel, ObjectNode)}
   * with {@code bpmnModel}, {@code propertiesNode}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertMessagesToJson(BpmnModel, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertMessagesToJson(BpmnModel, ObjectNode) with 'bpmnModel', 'propertiesNode'")
  void testConvertMessagesToJsonWithBpmnModelPropertiesNode() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    assertEquals("[ ]", nextResult.toPrettyString());
    assertEquals("{\n  \"messagedefinitions\" : [ ]\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult.size());
    assertFalse(nextResult.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)}
   * with {@code messages}, {@code propertiesNode}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertMessagesToJson(Collection, ObjectNode) with 'messages', 'propertiesNode'")
  void testConvertMessagesToJsonWithMessagesPropertiesNode() {
    // Arrange
    ArrayList<Message> messages = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(messages, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    assertEquals("[ ]", nextResult.toPrettyString());
    assertEquals("{\n  \"messages\" : [ ]\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult.size());
    assertFalse(nextResult.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)}
   * with {@code messages}, {@code propertiesNode}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertMessagesToJson(Collection, ObjectNode) with 'messages', 'propertiesNode'")
  void testConvertMessagesToJsonWithMessagesPropertiesNode2() {
    // Arrange
    Message message = new Message("42", "Name", "Item Ref");
    message.setId(null);
    message.setItemRef(null);
    message.setName(null);

    LinkedHashSet<Message> messages = new LinkedHashSet<>();
    messages.add(message);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(messages, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult.elements();
    JsonNode nextResult2 = elementsResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals("[ {\n  \"message_id\" : null,\n  \"message_name\" : null,\n  \"message_item_ref\" : null\n} ]",
        nextResult.toPrettyString());
    assertEquals("{\n  \"message_id\" : null,\n  \"message_name\" : null,\n  \"message_item_ref\" : null\n}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"messages\" : [ {\n" + "    \"message_id\" : null,\n" + "    \"message_name\" : null,\n"
        + "    \"message_item_ref\" : null\n" + "  } ]\n" + "}", propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.iterator().hasNext());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)}
   * with {@code messages}, {@code propertiesNode}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertMessagesToJson(Collection, ObjectNode) with 'messages', 'propertiesNode'")
  void testConvertMessagesToJsonWithMessagesPropertiesNode3() {
    // Arrange
    ArrayList<Message> messages = new ArrayList<>();
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message buildResult = attributesResult.extensionElements(new HashMap<>())
        .id("")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    messages.add(buildResult);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(messages, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult.elements();
    JsonNode nextResult2 = elementsResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult2.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonNode nextResult4 = iteratorResult2.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals(
        "[ {\n  \"message_id\" : \"\",\n  \"message_name\" : \"Name\",\n  \"message_item_ref\" : \"Item Ref\"\n} ]",
        nextResult.toPrettyString());
    assertEquals("\"Name\"", nextResult4.toPrettyString());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals(
        "{\n  \"message_id\" : \"\",\n  \"message_name\" : \"Name\",\n  \"message_item_ref\" : \"Item Ref\"\n}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"messages\" : [ {\n" + "    \"message_id\" : \"\",\n"
        + "    \"message_name\" : \"Name\",\n" + "    \"message_item_ref\" : \"Item Ref\"\n" + "  } ]\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(nextResult3.isNull());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult3.isTextual());
    assertTrue(iteratorResult2.hasNext());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertListenersToJson(List, boolean, ObjectNode)")
  void testConvertListenersToJson() {
    // Arrange
    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertListenersToJson(listeners, true, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"executionListeners\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"executionlisteners\" : {\n    \"executionListeners\" : [ ]\n  }\n}",
        propertiesNode.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertListenersToJson(List, boolean, ObjectNode)")
  void testConvertListenersToJson2() {
    // Arrange
    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertListenersToJson(listeners, false, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"taskListeners\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"tasklisteners\" : {\n    \"taskListeners\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertListenersToJson(List, boolean, ObjectNode)")
  void testConvertListenersToJson3() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setEvent("executionlisteners");

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    listeners.add(activitiListener);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertListenersToJson(listeners, true, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult3.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals("[ {\n  \"event\" : \"executionlisteners\"\n} ]", nextResult2.toPrettyString());
    assertEquals("\"executionlisteners\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"event\" : \"executionlisteners\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"executionListeners\" : [ {\n    \"event\" : \"executionlisteners\"\n  } ]\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"executionlisteners\" : {\n" + "    \"executionListeners\" : [ {\n"
            + "      \"event\" : \"executionlisteners\"\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(nextResult4.isNull());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(nextResult4.isTextual());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertEventListenersToJson(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertEventListenersToJson(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertEventListenersToJson(List, ObjectNode)")
  void testConvertEventListenersToJson() {
    // Arrange
    ArrayList<EventListener> listeners = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertEventListenersToJson(listeners, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"eventListeners\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"eventlisteners\" : {\n    \"eventListeners\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertSignalDefinitionsToJson(BpmnModel, ObjectNode)")
  void testConvertSignalDefinitionsToJson() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertSignalDefinitionsToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    assertEquals("[ ]", nextResult.toPrettyString());
    assertEquals("{\n  \"signaldefinitions\" : [ ]\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult.size());
    assertFalse(nextResult.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertSignalDefinitionsToJson(BpmnModel, ObjectNode)")
  void testConvertSignalDefinitionsToJson2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", "signaldefinitions"));
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertSignalDefinitionsToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult.elements();
    JsonNode nextResult2 = elementsResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult2.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("[ {\n  \"id\" : \"42\",\n  \"name\" : \"signaldefinitions\",\n  \"scope\" : null\n} ]",
        nextResult.toPrettyString());
    assertEquals("\"42\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"id\" : \"42\",\n  \"name\" : \"signaldefinitions\",\n  \"scope\" : null\n}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"signaldefinitions\" : [ {\n" + "    \"id\" : \"42\",\n"
        + "    \"name\" : \"signaldefinitions\",\n" + "    \"scope\" : null\n" + "  } ]\n" + "}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(iteratorResult2.hasNext());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertSignalDefinitionsToJson(BpmnModel, ObjectNode)")
  void testConvertSignalDefinitionsToJson3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("", "signaldefinitions"));
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertSignalDefinitionsToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult.elements();
    JsonNode nextResult2 = elementsResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult2.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("[ {\n  \"id\" : \"\",\n  \"name\" : \"signaldefinitions\",\n  \"scope\" : null\n} ]",
        nextResult.toPrettyString());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"id\" : \"\",\n  \"name\" : \"signaldefinitions\",\n  \"scope\" : null\n}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"signaldefinitions\" : [ {\n" + "    \"id\" : \"\",\n"
        + "    \"name\" : \"signaldefinitions\",\n" + "    \"scope\" : null\n" + "  } ]\n" + "}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(iteratorResult2.hasNext());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given ArrayList() add Instance; then calls asText()")
  void testConvertJsonToListeners_givenArrayListAddInstance_thenCallsAsText() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);
    when(jsonNode.iterator()).thenReturn(iteratorResult);
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).isTextual();
    verify(jsonNode).iterator();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given Instance; when JsonNode get(String) return Instance")
  void testConvertJsonToListeners_givenInstance_whenJsonNodeGetReturnInstance() {
    // Arrange
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return {@code 42}.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return '42'; then calls asText()")
  void testConvertJsonToListeners_givenJsonNodeAsTextReturn42_thenCallsAsText() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("42");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).isTextual();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return
   * {@code As Text}.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return 'As Text'; then calls asText()")
  void testConvertJsonToListeners_givenJsonNodeAsTextReturnAsText_thenCallsAsText() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).isTextual();
    verify(jsonNode).iterator();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return
   * {@code As Text}.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return 'As Text'; then calls asText()")
  void testConvertJsonToListeners_givenJsonNodeAsTextReturnAsText_thenCallsAsText2() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(false);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode2, atLeast(1)).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode2).isTextual();
    verify(jsonNode).isTextual();
    verify(jsonNode).iterator();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return
   * {@code As Text}.</li>
   *   <li>When {@link UserTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return 'As Text'; when UserTask (default constructor)")
  void testConvertJsonToListeners_givenJsonNodeAsTextReturnAsText_whenUserTask() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(false);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new UserTask());

    // Assert
    verify(jsonNode2, atLeast(1)).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode3, atLeast(1)).get(Mockito.<String>any());
    verify(jsonNode2, atLeast(1)).get(Mockito.<String>any());
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2, atLeast(1)).isNull();
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode2, atLeast(1)).isTextual();
    verify(jsonNode, atLeast(1)).isTextual();
    verify(jsonNode, atLeast(1)).iterator();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return empty
   * string.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return empty string; then calls asText()")
  void testConvertJsonToListeners_givenJsonNodeAsTextReturnEmptyString_thenCallsAsText() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(jsonNode).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).isTextual();
    verify(jsonNode).iterator();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return
   * {@code null}.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return 'null'; then calls asText()")
  void testConvertJsonToListeners_givenJsonNodeAsTextReturnNull_thenCallsAsText() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn(null);
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(jsonNode).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).isTextual();
    verify(jsonNode).iterator();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return False.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode get(String) return False")
  void testConvertJsonToListeners_givenJsonNodeGetReturnFalse() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.get(Mockito.<String>any())).thenReturn(BooleanNode.getFalse());
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(jsonNode).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode get(String) return Instance")
  void testConvertJsonToListeners_givenJsonNodeGetReturnInstance() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(jsonNode).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode get(String) return Instance")
  void testConvertJsonToListeners_givenJsonNodeGetReturnInstance2() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(jsonNode).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode get(String) return Instance; then calls isNull()")
  void testConvertJsonToListeners_givenJsonNodeGetReturnInstance_thenCallsIsNull() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(jsonNode.isNull()).thenReturn(true);
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(jsonNode).get(eq("executionListeners"));
    verify(jsonNode2).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode).isNull();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode isNull() return 'true'; then calls iterator()")
  void testConvertJsonToListeners_givenJsonNodeIsNullReturnTrue_thenCallsIterator() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).iterator();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel)")
  void testConvertJsonToMessages() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel)")
  void testConvertJsonToMessages2() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);
    when(arrayNode.iterator()).thenReturn(iteratorResult);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel)")
  void testConvertJsonToMessages3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayList() add Instance; then calls isTextual()")
  void testConvertJsonToMessages_givenArrayListAddInstance_thenCallsIsTextual() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);
    when(arrayNode.iterator()).thenReturn(iteratorResult);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return '42'; then calls isTextual()")
  void testConvertJsonToMessages_givenArrayNodeAsTextReturn42_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("42");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code As Text}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return 'As Text'; then calls isTextual()")
  void testConvertJsonToMessages_givenArrayNodeAsTextReturnAsText_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return empty string")
  void testConvertJsonToMessages_givenArrayNodeAsTextReturnEmptyString() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code null}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return 'null'; then calls isTextual()")
  void testConvertJsonToMessages_givenArrayNodeAsTextReturnNull_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn(null);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode get(String) return BigIntegerNode(BigInteger) with v is valueOf one")
  void testConvertJsonToMessages_givenArrayNodeGetReturnBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode get(String) return Instance")
  void testConvertJsonToMessages_givenArrayNodeGetReturnInstance() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode get(String) return Instance; then calls isTextual()")
  void testConvertJsonToMessages_givenArrayNodeGetReturnInstance_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode isNull() return 'true'; then calls isTextual()")
  void testConvertJsonToMessages_givenArrayNodeIsNullReturnTrue_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.isNull()).thenReturn(false);
    when(arrayNode3.isTextual()).thenReturn(true);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode3).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode3, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode isNull() return 'true'; then calls iterator()")
  void testConvertJsonToMessages_givenArrayNodeIsNullReturnTrue_thenCallsIterator() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToMessages_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given Instance; when ArrayNode get(String) return Instance")
  void testConvertJsonToMessages_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) Messages size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); then BpmnModel (default constructor) Messages size is one")
  void testConvertJsonToMessages_thenBpmnModelMessagesSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2, atLeast(1)).asText();
    assertEquals(1, element.getMessages().size());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToMessages_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayNode objectNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); when Instance; then BpmnModel (default constructor) Messages Empty")
  void testConvertJsonToMessages_whenInstance_thenBpmnModelMessagesEmpty() {
    // Arrange
    MissingNode objectNode = MissingNode.getInstance();
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean)")
  void testParseListeners() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).get(eq("event"));
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean)")
  void testParseListeners2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);
    SequenceFlow element = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, element, true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<ActivitiListener> executionListeners = element.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    ActivitiListener getResult = executionListeners.get(0);
    assertEquals("As Text", getResult.getEvent());
    assertEquals("As Text", getResult.getImplementation());
    assertEquals("class", getResult.getImplementationType());
    assertNull(getResult.getInstance());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given '42'; when ArrayNode asText() return '42'; then calls isTextual()")
  void testParseListeners_given42_whenArrayNodeAsTextReturn42_thenCallsIsTextual() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("42");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayList() add ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testParseListeners_givenArrayListAddArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayList() add Instance; then calls get(String)")
  void testParseListeners_givenArrayListAddInstance_thenCallsGet() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayList() add Instance; when ArrayNode asText() return 'As Text'")
  void testParseListeners_givenArrayListAddInstance_whenArrayNodeAsTextReturnAsText() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode get(String) return BigIntegerNode(BigInteger) with v is valueOf one")
  void testParseListeners_givenArrayNodeGetReturnBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode get(String) return Instance; then calls get(String)")
  void testParseListeners_givenArrayNodeGetReturnInstance_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).get(eq("event"));
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode isNull() return 'true'; then calls get(String)")
  void testParseListeners_givenArrayNodeIsNullReturnTrue_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).get(eq("event"));
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode isNull() return 'true'; then calls get(String)")
  void testParseListeners_givenArrayNodeIsNullReturnTrue_thenCallsGet2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#iterator()} return
   * {@link ArrayList#ArrayList()} iterator.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode iterator() return ArrayList() iterator; then calls get(String)")
  void testParseListeners_givenArrayNodeIteratorReturnArrayListIterator_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@code As Text}.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given 'As Text'; when ArrayNode asText() return 'As Text'")
  void testParseListeners_givenAsText_whenArrayNodeAsTextReturnAsText() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given empty string; when ArrayNode asText() return empty string")
  void testParseListeners_givenEmptyString_whenArrayNodeAsTextReturnEmptyString() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert that nothing has changed
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code null}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given 'null'; when ArrayNode asText() return 'null'; then calls isTextual()")
  void testParseListeners_givenNull_whenArrayNodeAsTextReturnNull_thenCallsIsTextual() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn(null);
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert that nothing has changed
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode).asText();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ExecutionListeners
   * size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); then AdhocSubProcess (default constructor) ExecutionListeners size is one")
  void testParseListeners_thenAdhocSubProcessExecutionListenersSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, element, true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<ActivitiListener> executionListeners = element.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    ActivitiListener getResult = executionListeners.get(0);
    assertEquals("As Text", getResult.getEvent());
    assertEquals("As Text", getResult.getImplementation());
    assertEquals("class", getResult.getImplementationType());
    assertNull(getResult.getInstance());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>When {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); when ArrayNode isNull() return 'true'; then calls iterator()")
  void testParseListeners_whenArrayNodeIsNullReturnTrue_thenCallsIterator() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert that nothing has changed
    verify(listenersNode).isNull();
    verify(listenersNode).iterator();
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link Process} (default constructor) ExecutionListeners Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); when 'null'; then Process (default constructor) ExecutionListeners Empty")
  void testParseListeners_whenNull_thenProcessExecutionListenersEmpty() {
    // Arrange
    Process element = new Process();

    // Act
    BpmnJsonConverterUtil.parseListeners(null, element, false);

    // Assert that nothing has changed
    assertTrue(element.getExecutionListeners().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link Process} (default constructor) ExecutionListeners size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); when Process (default constructor); then Process (default constructor) ExecutionListeners size is one")
  void testParseListeners_whenProcess_thenProcessExecutionListenersSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);
    Process element = new Process();

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, element, true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<ActivitiListener> executionListeners = element.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    ActivitiListener getResult = executionListeners.get(0);
    assertEquals("As Text", getResult.getEvent());
    assertEquals("As Text", getResult.getImplementation());
    assertEquals("class", getResult.getImplementationType());
    assertNull(getResult.getInstance());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>When {@link UserTask} (default constructor).</li>
   *   <li>Then {@link UserTask} (default constructor) ExecutionListeners size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); when UserTask (default constructor); then UserTask (default constructor) ExecutionListeners size is one")
  void testParseListeners_whenUserTask_thenUserTaskExecutionListenersSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);
    UserTask element = new UserTask();

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, element, false);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<ActivitiListener> executionListeners = element.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    ActivitiListener getResult = executionListeners.get(0);
    assertEquals("As Text", getResult.getEvent());
    assertEquals("As Text", getResult.getImplementation());
    assertEquals("class", getResult.getImplementationType());
    assertNull(getResult.getInstance());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>When {@link UserTask} (default constructor).</li>
   *   <li>Then {@link UserTask} (default constructor) TaskListeners size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); when UserTask (default constructor); then UserTask (default constructor) TaskListeners size is one")
  void testParseListeners_whenUserTask_thenUserTaskTaskListenersSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);
    UserTask element = new UserTask();

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, element, true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<ActivitiListener> taskListeners = element.getTaskListeners();
    assertEquals(1, taskListeners.size());
    ActivitiListener getResult = taskListeners.get(0);
    assertEquals("As Text", getResult.getEvent());
    assertEquals("As Text", getResult.getImplementation());
    assertEquals("class", getResult.getImplementationType());
    assertNull(getResult.getInstance());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel)")
  void testParseMessages() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert
    verify(messagesNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); given ArrayList() add ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testParseMessages_givenArrayListAddArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert
    verify(messagesNode).iterator();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); given ArrayList() add Instance; then calls iterator()")
  void testParseMessages_givenArrayListAddInstance_thenCallsIterator() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert
    verify(messagesNode).iterator();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} iterator.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); given ArrayList() iterator; then calls iterator()")
  void testParseMessages_givenArrayListIterator_thenCallsIterator() {
    // Arrange
    ArrayNode messagesNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    verify(messagesNode).iterator();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); given ArrayNode get(String) return Instance; then calls get(String)")
  void testParseMessages_givenArrayNodeGetReturnInstance_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert
    verify(messagesNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); given ArrayNode isNull() return 'true'; then calls isNull()")
  void testParseMessages_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert
    verify(arrayNode, atLeast(1)).isNull();
    verify(messagesNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) Messages size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); then BpmnModel (default constructor) Messages size is one")
  void testParseMessages_thenBpmnModelMessagesSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert
    verify(messagesNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals(1, element.getMessages().size());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testParseMessages_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayNode messagesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); when Instance; then BpmnModel (default constructor) Messages Empty")
  void testParseMessages_whenInstance_thenBpmnModelMessagesEmpty() {
    // Arrange
    MissingNode messagesNode = MissingNode.getInstance();
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); when 'null'; then BpmnModel (default constructor) Messages Empty")
  void testParseMessages_whenNull_thenBpmnModelMessagesEmpty() {
    // Arrange
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(null, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  void testParseEventListeners() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  void testParseEventListeners2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).get(eq("events"));
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  void testParseEventListeners3() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("events"));
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  void testParseEventListeners4() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode).get(eq("event"));
    verify(arrayNode3).get(eq("events"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given '42'; when ArrayNode asText() return '42'; then calls isTextual()")
  void testParseEventListeners_given42_whenArrayNodeAsTextReturn42_thenCallsIsTextual() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("42");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link ArrayNode#isArray()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayList() add Instance; then calls isArray()")
  void testParseEventListeners_givenArrayListAddInstance_thenCallsIsArray() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("events"));
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayList() add Instance; then calls isTextual()")
  void testParseEventListeners_givenArrayListAddInstance_thenCallsIsTextual() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#asBoolean()} return
   * {@code false}.</li>
   *   <li>Then calls {@link JsonNode#asBoolean()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asBoolean() return 'false'; then calls asBoolean()")
  void testParseEventListeners_givenArrayNodeAsBooleanReturnFalse_thenCallsAsBoolean() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(false);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(arrayNode2, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code As Text}.</li>
   *   <li>Then calls {@link JsonNode#asBoolean()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'As Text'; then calls asBoolean()")
  void testParseEventListeners_givenArrayNodeAsTextReturnAsText_thenCallsAsBoolean() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(true);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code As Text}.</li>
   *   <li>Then calls {@link ArrayNode#isArray()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'As Text'; then calls isArray()")
  void testParseEventListeners_givenArrayNodeAsTextReturnAsText_thenCallsIsArray() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode3).iterator();
    verify(arrayNode2).get(eq("event"));
    verify(arrayNode4).get(eq("events"));
    verify(arrayNode3).isArray();
    verify(arrayNode3).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code error}.</li>
   *   <li>Then calls {@link JsonNode#asBoolean()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'error'; then calls asBoolean()")
  void testParseEventListeners_givenArrayNodeAsTextReturnError_thenCallsAsBoolean() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(true);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("error");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(arrayNode2).asText();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code globalSignal}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'globalSignal'")
  void testParseEventListeners_givenArrayNodeAsTextReturnGlobalSignal() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(true);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("globalSignal");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code message}.</li>
   *   <li>Then calls {@link JsonNode#asBoolean()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'message'; then calls asBoolean()")
  void testParseEventListeners_givenArrayNodeAsTextReturnMessage_thenCallsAsBoolean() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(true);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("message");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code signal}.</li>
   *   <li>Then calls {@link JsonNode#asBoolean()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'signal'; then calls asBoolean()")
  void testParseEventListeners_givenArrayNodeAsTextReturnSignal_thenCallsAsBoolean() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(true);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("signal");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode get(String) return Instance; then calls get(String)")
  void testParseEventListeners_givenArrayNodeGetReturnInstance_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).get(eq("events"));
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link ArrayNode#isArray()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode get(String) return Instance; then calls isArray()")
  void testParseEventListeners_givenArrayNodeGetReturnInstance_thenCallsIsArray() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode).get(eq("event"));
    verify(arrayNode3).get(eq("events"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@code As Text}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given 'As Text'; then calls isTextual()")
  void testParseEventListeners_givenAsText_thenCallsIsTextual() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given empty string; when ArrayNode asText() return empty string")
  void testParseEventListeners_givenEmptyString_whenArrayNodeAsTextReturnEmptyString() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert that nothing has changed
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given 'null'; when ArrayNode asText() return 'null'")
  void testParseEventListeners_givenNull_whenArrayNodeAsTextReturnNull() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn(null);
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert that nothing has changed
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Then calls {@link ArrayNode#isArray()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); then calls isArray()")
  void testParseEventListeners_thenCallsIsArray() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("events"));
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>When {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); when ArrayNode isNull() return 'true'; then calls iterator()")
  void testParseEventListeners_whenArrayNodeIsNullReturnTrue_thenCallsIterator() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert that nothing has changed
    verify(listenersNode).isNull();
    verify(listenersNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); given Instance")
  void testLookForSourceRef_givenInstance() {
    // Arrange
    ArrayNode childShapesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    childShapesNode.add(MissingNode.getInstance());

    // Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testLookForSourceRef_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true} addArray.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true' addArray")
  void testLookForSourceRef_whenArrayNodeWithNfIsWithExactBigDecimalsTrueAddArray() {
    // Arrange
    ArrayNode childShapesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    childShapesNode.addArray();
    childShapesNode.add(MissingNode.getInstance());

    // Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true} addObject.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true' addObject")
  void testLookForSourceRef_whenArrayNodeWithNfIsWithExactBigDecimalsTrueAddObject() {
    // Arrange
    ArrayNode childShapesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    childShapesNode.addObject();
    childShapesNode.add(MissingNode.getInstance());

    // Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when Instance")
  void testLookForSourceRef_whenInstance() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", MissingNode.getInstance()));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when 'null'")
  void testLookForSourceRef_whenNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", null));
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToDataProperties_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayNode objectNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when BigDecimal(String) with '2.3'")
  void testConvertJsonToDataProperties_whenBigDecimalWith23() {
    // Arrange
    DecimalNode objectNode = new DecimalNode(new BigDecimal("2.3"));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when BigIntegerNode(BigInteger) with v is valueOf one")
  void testConvertJsonToDataProperties_whenBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    BigIntegerNode objectNode = new BigIntegerNode(BigInteger.valueOf(1L));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX}
   * Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  void testConvertJsonToDataProperties_whenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    BinaryNode objectNode = new BinaryNode("AXAXAXAX".getBytes("UTF-8"));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When False.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when False")
  void testConvertJsonToDataProperties_whenFalse() {
    // Arrange
    BooleanNode objectNode = BooleanNode.getFalse();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when Instance")
  void testConvertJsonToDataProperties_whenInstance() {
    // Arrange
    MissingNode objectNode = MissingNode.getInstance();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when Instance")
  void testConvertJsonToDataProperties_whenInstance2() {
    // Arrange
    NullNode objectNode = NullNode.getInstance();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when 'null'")
  void testConvertJsonToDataProperties_whenNull() {
    // Arrange and Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(null, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When True.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when True")
  void testConvertJsonToDataProperties_whenTrue() {
    // Arrange
    BooleanNode objectNode = BooleanNode.getTrue();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertDataPropertiesToJson(List, ObjectNode)")
  void testConvertDataPropertiesToJson() {
    // Arrange
    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertDataPropertiesToJson(dataObjects, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"dataproperties\" : {\n    \"items\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertEquals("{\n  \"items\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertDataPropertiesToJson(List, ObjectNode)")
  void testConvertDataPropertiesToJson2() {
    // Arrange
    ItemDefinition itemSubjectRef = new ItemDefinition();
    itemSubjectRef.setStructureRef("dataproperty_id");

    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setItemSubjectRef(itemSubjectRef);
    booleanDataObject.setId(null);
    booleanDataObject.setName(null);

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(booleanDataObject);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertDataPropertiesToJson(dataObjects, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(
        "[ {\n" + "  \"dataproperty_id\" : null,\n" + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"dataproperty_id\",\n" + "  \"dataproperty_value\" : \"\"\n" + "} ]",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"dataproperties\" : {\n" + "    \"items\" : [ {\n" + "      \"dataproperty_id\" : null,\n"
        + "      \"dataproperty_name\" : null,\n" + "      \"dataproperty_type\" : \"dataproperty_id\",\n"
        + "      \"dataproperty_value\" : \"\"\n" + "    } ]\n" + "  }\n" + "}", propertiesNode.toPrettyString());
    assertEquals(
        "{\n" + "  \"dataproperty_id\" : null,\n" + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"dataproperty_id\",\n" + "  \"dataproperty_value\" : \"\"\n" + "}",
        nextResult3.toPrettyString());
    assertEquals("{\n" + "  \"items\" : [ {\n" + "    \"dataproperty_id\" : null,\n"
        + "    \"dataproperty_name\" : null,\n" + "    \"dataproperty_type\" : \"dataproperty_id\",\n"
        + "    \"dataproperty_value\" : \"\"\n" + "  } ]\n" + "}", nextResult.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}.
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertDataPropertiesToJson(List, ObjectNode)")
  void testConvertDataPropertiesToJson3() {
    // Arrange
    ItemDefinition itemSubjectRef = new ItemDefinition();
    itemSubjectRef.setStructureRef("dataproperty_id");

    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setValue("Value");
    booleanDataObject.setItemSubjectRef(itemSubjectRef);
    booleanDataObject.setId(null);
    booleanDataObject.setName(null);

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(booleanDataObject);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertDataPropertiesToJson(dataObjects, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(
        "[ {\n" + "  \"dataproperty_id\" : null,\n" + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"dataproperty_id\",\n" + "  \"dataproperty_value\" : \"false\"\n" + "} ]",
        nextResult2.toPrettyString());
    assertEquals(
        "{\n" + "  \"dataproperties\" : {\n" + "    \"items\" : [ {\n" + "      \"dataproperty_id\" : null,\n"
            + "      \"dataproperty_name\" : null,\n" + "      \"dataproperty_type\" : \"dataproperty_id\",\n"
            + "      \"dataproperty_value\" : \"false\"\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(
        "{\n" + "  \"dataproperty_id\" : null,\n" + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"dataproperty_id\",\n" + "  \"dataproperty_value\" : \"false\"\n" + "}",
        nextResult3.toPrettyString());
    assertEquals("{\n" + "  \"items\" : [ {\n" + "    \"dataproperty_id\" : null,\n"
        + "    \"dataproperty_name\" : null,\n" + "    \"dataproperty_type\" : \"dataproperty_id\",\n"
        + "    \"dataproperty_value\" : \"false\"\n" + "  } ]\n" + "}", nextResult.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   * <ul>
   *   <li>Then return {@link ArrayNode}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); then return ArrayNode")
  void testValidateIfNodeIsTextual_thenReturnArrayNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof ArrayNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("[ ]", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(0, actualValidateIfNodeIsTextualResult.size());
    assertEquals(JsonNodeType.ARRAY, actualValidateIfNodeIsTextualResult.getNodeType());
    assertFalse(actualValidateIfNodeIsTextualResult.elements().hasNext());
    assertTrue(actualValidateIfNodeIsTextualResult.isArray());
    assertTrue(actualValidateIfNodeIsTextualResult.isContainerNode());
    assertTrue(actualValidateIfNodeIsTextualResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   * <ul>
   *   <li>Then return {@link BigIntegerNode}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); then return BigIntegerNode")
  void testValidateIfNodeIsTextual_thenReturnBigIntegerNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(new BigIntegerNode(BigInteger.valueOf(1L)));

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof BigIntegerNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("1", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(JsonNodeType.NUMBER, actualValidateIfNodeIsTextualResult.getNodeType());
    assertFalse(((BigIntegerNode) actualValidateIfNodeIsTextualResult).isNaN());
    assertTrue(actualValidateIfNodeIsTextualResult.isBigInteger());
    assertTrue(actualValidateIfNodeIsTextualResult.isIntegralNumber());
    assertTrue(actualValidateIfNodeIsTextualResult.isNumber());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@link MissingNode}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); when Instance; then return MissingNode")
  void testValidateIfNodeIsTextual_whenInstance_thenReturnMissingNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(MissingNode.getInstance());

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof MissingNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(JsonNodeType.MISSING, actualValidateIfNodeIsTextualResult.getNodeType());
    assertTrue(actualValidateIfNodeIsTextualResult.isMissingNode());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@link NullNode}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); when Instance; then return NullNode")
  void testValidateIfNodeIsTextual_whenInstance_thenReturnNullNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(NullNode.getInstance());

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof NullNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("null", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(JsonNodeType.NULL, actualValidateIfNodeIsTextualResult.getNodeType());
    assertTrue(actualValidateIfNodeIsTextualResult.isNull());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); when 'null'; then return 'null'")
  void testValidateIfNodeIsTextual_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.validateIfNodeIsTextual(null));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsString(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testGetValueAsString_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange, Act and Assert
    assertNull(
        BpmnJsonConverterUtil.getValueAsString("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsString(String, JsonNode); when Instance; then return 'null'")
  void testGetValueAsString_whenInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsString(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testGetPropertyValueAsString_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getPropertyValueAsString("Name",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test
   * {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsString(String, JsonNode); when Instance; then return 'null'")
  void testGetPropertyValueAsString_whenInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getPropertyValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then return 'null'")
  void testGetProperty_whenArrayNodeWithNfIsWithExactBigDecimalsTrue_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getProperty("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when Instance; then return 'null'")
  void testGetProperty_whenInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getProperty("Name", MissingNode.getInstance()));
  }
}
