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
import java.util.Iterator;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.bpmn.model.ValuedDataObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnJsonConverterUtilDiffblueTest {
  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#createBoundsNode(double, double, double, double)}
   */
  @Test
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#createPositionNode(double, double)}
   */
  @Test
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
   * Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  void testCreateResourceNode() throws IOException {
    // Arrange and Act
    ObjectNode actualCreateResourceNodeResult = BpmnJsonConverterUtil.createResourceNode("42");

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateResourceNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = actualCreateResourceNodeResult.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    assertEquals("\"42\"", nextResult.toPrettyString());
    Version versionResult = traverseResult2.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"resourceId\" : \"42\"\n}", actualCreateResourceNodeResult.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult2.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(1, actualCreateResourceNodeResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, actualCreateResourceNodeResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult.isArray());
    assertFalse(actualCreateResourceNodeResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualCreateResourceNodeResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualCreateResourceNodeResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(actualCreateResourceNodeResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualCreateResourceNodeResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(actualCreateResourceNodeResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(actualCreateResourceNodeResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualCreateResourceNodeResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(actualCreateResourceNodeResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualCreateResourceNodeResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(actualCreateResourceNodeResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualCreateResourceNodeResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(actualCreateResourceNodeResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(actualCreateResourceNodeResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(actualCreateResourceNodeResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(actualCreateResourceNodeResult.isShort());
    assertFalse(actualCreateResourceNodeResult.isTextual());
    assertFalse(actualCreateResourceNodeResult.isValueNode());
    assertFalse(actualCreateResourceNodeResult.isEmpty());
    assertFalse(nextResult.iterator().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(actualCreateResourceNodeResult.isContainerNode());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
    assertTrue(actualCreateResourceNodeResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
  }

  /**
   * Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  void testCreateResourceNode2() throws IOException {
    // Arrange and Act
    ObjectNode actualCreateResourceNodeResult = BpmnJsonConverterUtil.createResourceNode(null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateResourceNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = actualCreateResourceNodeResult.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    Version versionResult = traverseResult2.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"resourceId\" : null\n}", actualCreateResourceNodeResult.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult2.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(1, actualCreateResourceNodeResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualCreateResourceNodeResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult.isArray());
    assertFalse(actualCreateResourceNodeResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualCreateResourceNodeResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualCreateResourceNodeResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(actualCreateResourceNodeResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualCreateResourceNodeResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(actualCreateResourceNodeResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(actualCreateResourceNodeResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualCreateResourceNodeResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(actualCreateResourceNodeResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualCreateResourceNodeResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(actualCreateResourceNodeResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualCreateResourceNodeResult.isMissingNode());
    assertFalse(actualCreateResourceNodeResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(actualCreateResourceNodeResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(actualCreateResourceNodeResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(actualCreateResourceNodeResult.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(actualCreateResourceNodeResult.isTextual());
    assertFalse(actualCreateResourceNodeResult.isValueNode());
    assertFalse(actualCreateResourceNodeResult.isEmpty());
    assertFalse(nextResult.iterator().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(actualCreateResourceNodeResult.isContainerNode());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isNull());
    assertTrue(nextResult.isValueNode());
    assertTrue(actualCreateResourceNodeResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
  }

  /**
   * Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  void testCreateResourceNode3() throws IOException {
    // Arrange and Act
    ObjectNode actualCreateResourceNodeResult = BpmnJsonConverterUtil.createResourceNode("");

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateResourceNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = actualCreateResourceNodeResult.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    assertEquals("\"\"", nextResult.toPrettyString());
    Version versionResult = traverseResult2.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"resourceId\" : \"\"\n}", actualCreateResourceNodeResult.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult2.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(1, actualCreateResourceNodeResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, actualCreateResourceNodeResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult.isArray());
    assertFalse(actualCreateResourceNodeResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualCreateResourceNodeResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualCreateResourceNodeResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(actualCreateResourceNodeResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualCreateResourceNodeResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(actualCreateResourceNodeResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(actualCreateResourceNodeResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualCreateResourceNodeResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(actualCreateResourceNodeResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualCreateResourceNodeResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(actualCreateResourceNodeResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualCreateResourceNodeResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(actualCreateResourceNodeResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(actualCreateResourceNodeResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(actualCreateResourceNodeResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(actualCreateResourceNodeResult.isShort());
    assertFalse(actualCreateResourceNodeResult.isTextual());
    assertFalse(actualCreateResourceNodeResult.isValueNode());
    assertFalse(actualCreateResourceNodeResult.isEmpty());
    assertFalse(nextResult.iterator().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(actualCreateResourceNodeResult.isContainerNode());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
    assertTrue(actualCreateResourceNodeResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
  }

  /**
   * Method under test: {@link BpmnJsonConverterUtil#getStencilId(JsonNode)}
   */
  @Test
  void testGetStencilId() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getStencilId(MissingNode.getInstance()));
    assertNull(BpmnJsonConverterUtil.getStencilId(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  void testGetElementId() {
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
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  void testGetElementId2() {
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
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  void testGetElementId3() {
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
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  void testGetElementId4() {
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
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  void testGetElementId5() {
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
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  void testGetElementId6() {
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
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  void testGetElementId7() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)}
   */
  @Test
  void testConvertMessagesToJson() throws IOException {
    // Arrange
    ArrayList<Message> messages = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(messages, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("[ ]", nextResult.toPrettyString());
    assertEquals("{\n  \"messages\" : [ ]\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.ARRAY, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(nextResult.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isArray());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertMessagesToJson(BpmnModel, ObjectNode)}
   */
  @Test
  void testConvertMessagesToJson2() throws IOException {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("[ ]", nextResult.toPrettyString());
    assertEquals("{\n  \"messagedefinitions\" : [ ]\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.ARRAY, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(nextResult.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isArray());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}
   */
  @Test
  void testConvertListenersToJson() throws IOException {
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
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"executionListeners\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"executionlisteners\" : {\n    \"executionListeners\" : [ ]\n  }\n}",
        propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, nextResult2.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.ARRAY, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult2.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isArray());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult2.isEmpty());
    assertTrue(nextResult.isObject());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}
   */
  @Test
  void testConvertListenersToJson2() throws IOException {
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
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"taskListeners\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"tasklisteners\" : {\n    \"taskListeners\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, nextResult2.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.ARRAY, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult2.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isArray());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult2.isEmpty());
    assertTrue(nextResult.isObject());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertEventListenersToJson(List, ObjectNode)}
   */
  @Test
  void testConvertEventListenersToJson() throws IOException {
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
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"eventListeners\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"eventlisteners\" : {\n    \"eventListeners\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, nextResult2.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.ARRAY, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult2.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isArray());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult2.isEmpty());
    assertTrue(nextResult.isObject());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}
   */
  @Test
  void testConvertSignalDefinitionsToJson() throws IOException {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertSignalDefinitionsToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("[ ]", nextResult.toPrettyString());
    assertEquals("{\n  \"signaldefinitions\" : [ ]\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.ARRAY, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(nextResult.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isArray());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners() {
    // Arrange
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert that nothing has changed
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners2() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners3() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners4() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners5() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners6() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners7() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners8() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners9() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners10() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners11() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners12() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToListeners13() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages() {
    // Arrange
    MissingNode objectNode = MissingNode.getInstance();
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages2() {
    // Arrange
    ArrayNode objectNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages3() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages4() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages5() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages6() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages7() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages8() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages9() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages10() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages11() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages12() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages13() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages14() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages15() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages16() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages17() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testConvertJsonToMessages18() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners() {
    // Arrange
    Process element = new Process();

    // Act
    BpmnJsonConverterUtil.parseListeners(null, element, false);

    // Assert that nothing has changed
    assertTrue(element.getExecutionListeners().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners2() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners3() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners4() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners5() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners6() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners7() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners8() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners9() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners10() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners11() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners12() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners13() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners14() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners15() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners16() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners17() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners18() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners19() {
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
    assertTrue(element.getExecutionListeners().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  void testParseListeners20() {
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
    assertTrue(element.getTaskListeners().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testParseMessages() {
    // Arrange
    MissingNode messagesNode = MissingNode.getInstance();
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testParseMessages2() {
    // Arrange
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(null, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testParseMessages3() {
    // Arrange
    ArrayNode messagesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testParseMessages4() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testParseMessages5() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testParseMessages6() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testParseMessages7() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testParseMessages8() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testParseMessages9() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  void testParseMessages10() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners2() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners3() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners4() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners5() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners6() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners7() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners8() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners9() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners10() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners11() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners12() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners13() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners14() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners15() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners16() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners17() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners18() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners19() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners20() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  void testParseEventListeners21() {
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
   * Method under test:
   * {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  void testLookForSourceRef() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", MissingNode.getInstance()));
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", null));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  void testLookForSourceRef2() {
    // Arrange
    ArrayNode childShapesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    childShapesNode.add(MissingNode.getInstance());

    // Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  void testLookForSourceRef3() {
    // Arrange
    ArrayNode childShapesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    childShapesNode.addArray();
    childShapesNode.add(MissingNode.getInstance());

    // Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  void testLookForSourceRef4() {
    // Arrange
    ArrayNode childShapesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    childShapesNode.addObject();
    childShapesNode.add(MissingNode.getInstance());

    // Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToDataProperties() {
    // Arrange
    MissingNode objectNode = MissingNode.getInstance();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToDataProperties2() {
    // Arrange and Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(null, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToDataProperties3() {
    // Arrange
    ArrayNode objectNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToDataProperties4() {
    // Arrange
    BigIntegerNode objectNode = new BigIntegerNode(BigInteger.valueOf(1L));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToDataProperties5() throws UnsupportedEncodingException {
    // Arrange
    BinaryNode objectNode = new BinaryNode("AXAXAXAX".getBytes("UTF-8"));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToDataProperties6() {
    // Arrange
    BooleanNode objectNode = BooleanNode.getFalse();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToDataProperties7() {
    // Arrange
    BooleanNode objectNode = BooleanNode.getTrue();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToDataProperties8() {
    // Arrange
    NullNode objectNode = NullNode.getInstance();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  void testConvertJsonToDataProperties9() {
    // Arrange
    DecimalNode objectNode = new DecimalNode(new BigDecimal("2.3"));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}
   */
  @Test
  void testConvertDataPropertiesToJson() throws IOException {
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
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"dataproperties\" : {\n    \"items\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertEquals("{\n  \"items\" : [ ]\n}", nextResult.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, nextResult2.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.ARRAY, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult2.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isArray());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult2.isEmpty());
    assertTrue(nextResult.isObject());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  void testValidateIfNodeIsTextual() throws IOException {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(MissingNode.getInstance());

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof MissingNode);
    JsonParser traverseResult = actualValidateIfNodeIsTextualResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("", actualValidateIfNodeIsTextualResult.toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, actualValidateIfNodeIsTextualResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.MISSING, actualValidateIfNodeIsTextualResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(actualValidateIfNodeIsTextualResult.isArray());
    assertFalse(actualValidateIfNodeIsTextualResult.isBigDecimal());
    assertFalse(actualValidateIfNodeIsTextualResult.isBigInteger());
    assertFalse(actualValidateIfNodeIsTextualResult.isBinary());
    assertFalse(actualValidateIfNodeIsTextualResult.isBoolean());
    assertFalse(actualValidateIfNodeIsTextualResult.isContainerNode());
    assertFalse(actualValidateIfNodeIsTextualResult.isDouble());
    assertFalse(actualValidateIfNodeIsTextualResult.isFloat());
    assertFalse(actualValidateIfNodeIsTextualResult.isFloatingPointNumber());
    assertFalse(actualValidateIfNodeIsTextualResult.isInt());
    assertFalse(actualValidateIfNodeIsTextualResult.isIntegralNumber());
    assertFalse(actualValidateIfNodeIsTextualResult.isLong());
    assertFalse(actualValidateIfNodeIsTextualResult.isNull());
    assertFalse(actualValidateIfNodeIsTextualResult.isNumber());
    assertFalse(actualValidateIfNodeIsTextualResult.isObject());
    assertFalse(actualValidateIfNodeIsTextualResult.isPojo());
    assertFalse(actualValidateIfNodeIsTextualResult.isShort());
    assertFalse(actualValidateIfNodeIsTextualResult.isTextual());
    assertFalse(actualValidateIfNodeIsTextualResult.isValueNode());
    assertFalse(actualValidateIfNodeIsTextualResult.iterator().hasNext());
    assertTrue(actualValidateIfNodeIsTextualResult.isEmpty());
    assertTrue(actualValidateIfNodeIsTextualResult.isMissingNode());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  void testValidateIfNodeIsTextual2() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.validateIfNodeIsTextual(null));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  void testValidateIfNodeIsTextual3() throws IOException {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof ArrayNode);
    JsonParser traverseResult = actualValidateIfNodeIsTextualResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("[ ]", actualValidateIfNodeIsTextualResult.toPrettyString());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, actualValidateIfNodeIsTextualResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.ARRAY, actualValidateIfNodeIsTextualResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(actualValidateIfNodeIsTextualResult.isBigDecimal());
    assertFalse(actualValidateIfNodeIsTextualResult.isBigInteger());
    assertFalse(actualValidateIfNodeIsTextualResult.isBinary());
    assertFalse(actualValidateIfNodeIsTextualResult.isBoolean());
    assertFalse(actualValidateIfNodeIsTextualResult.isDouble());
    assertFalse(actualValidateIfNodeIsTextualResult.isFloat());
    assertFalse(actualValidateIfNodeIsTextualResult.isFloatingPointNumber());
    assertFalse(actualValidateIfNodeIsTextualResult.isInt());
    assertFalse(actualValidateIfNodeIsTextualResult.isIntegralNumber());
    assertFalse(actualValidateIfNodeIsTextualResult.isLong());
    assertFalse(actualValidateIfNodeIsTextualResult.isMissingNode());
    assertFalse(actualValidateIfNodeIsTextualResult.isNull());
    assertFalse(actualValidateIfNodeIsTextualResult.isNumber());
    assertFalse(actualValidateIfNodeIsTextualResult.isObject());
    assertFalse(actualValidateIfNodeIsTextualResult.isPojo());
    assertFalse(actualValidateIfNodeIsTextualResult.isShort());
    assertFalse(actualValidateIfNodeIsTextualResult.isTextual());
    assertFalse(actualValidateIfNodeIsTextualResult.isValueNode());
    assertFalse(actualValidateIfNodeIsTextualResult.elements().hasNext());
    assertFalse(actualValidateIfNodeIsTextualResult.iterator().hasNext());
    assertTrue(actualValidateIfNodeIsTextualResult.isArray());
    assertTrue(actualValidateIfNodeIsTextualResult.isContainerNode());
    assertTrue(actualValidateIfNodeIsTextualResult.isEmpty());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  void testValidateIfNodeIsTextual4() throws IOException {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(new BigIntegerNode(BigInteger.valueOf(1L)));

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof BigIntegerNode);
    JsonParser traverseResult = actualValidateIfNodeIsTextualResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("1", actualValidateIfNodeIsTextualResult.toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, actualValidateIfNodeIsTextualResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NUMBER, actualValidateIfNodeIsTextualResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(actualValidateIfNodeIsTextualResult.isArray());
    assertFalse(actualValidateIfNodeIsTextualResult.isBigDecimal());
    assertFalse(actualValidateIfNodeIsTextualResult.isBinary());
    assertFalse(actualValidateIfNodeIsTextualResult.isBoolean());
    assertFalse(actualValidateIfNodeIsTextualResult.isContainerNode());
    assertFalse(actualValidateIfNodeIsTextualResult.isDouble());
    assertFalse(actualValidateIfNodeIsTextualResult.isFloat());
    assertFalse(actualValidateIfNodeIsTextualResult.isFloatingPointNumber());
    assertFalse(actualValidateIfNodeIsTextualResult.isInt());
    assertFalse(actualValidateIfNodeIsTextualResult.isLong());
    assertFalse(actualValidateIfNodeIsTextualResult.isMissingNode());
    assertFalse(actualValidateIfNodeIsTextualResult.isNull());
    assertFalse(actualValidateIfNodeIsTextualResult.isObject());
    assertFalse(actualValidateIfNodeIsTextualResult.isPojo());
    assertFalse(actualValidateIfNodeIsTextualResult.isShort());
    assertFalse(actualValidateIfNodeIsTextualResult.isTextual());
    assertFalse(((BigIntegerNode) actualValidateIfNodeIsTextualResult).isNaN());
    assertFalse(actualValidateIfNodeIsTextualResult.iterator().hasNext());
    assertTrue(actualValidateIfNodeIsTextualResult.isBigInteger());
    assertTrue(actualValidateIfNodeIsTextualResult.isEmpty());
    assertTrue(actualValidateIfNodeIsTextualResult.isIntegralNumber());
    assertTrue(actualValidateIfNodeIsTextualResult.isNumber());
    assertTrue(actualValidateIfNodeIsTextualResult.isValueNode());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  void testValidateIfNodeIsTextual5() throws IOException {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(NullNode.getInstance());

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof NullNode);
    JsonParser traverseResult = actualValidateIfNodeIsTextualResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("null", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, actualValidateIfNodeIsTextualResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NULL, actualValidateIfNodeIsTextualResult.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(actualValidateIfNodeIsTextualResult.isArray());
    assertFalse(actualValidateIfNodeIsTextualResult.isBigDecimal());
    assertFalse(actualValidateIfNodeIsTextualResult.isBigInteger());
    assertFalse(actualValidateIfNodeIsTextualResult.isBinary());
    assertFalse(actualValidateIfNodeIsTextualResult.isBoolean());
    assertFalse(actualValidateIfNodeIsTextualResult.isContainerNode());
    assertFalse(actualValidateIfNodeIsTextualResult.isDouble());
    assertFalse(actualValidateIfNodeIsTextualResult.isFloat());
    assertFalse(actualValidateIfNodeIsTextualResult.isFloatingPointNumber());
    assertFalse(actualValidateIfNodeIsTextualResult.isInt());
    assertFalse(actualValidateIfNodeIsTextualResult.isIntegralNumber());
    assertFalse(actualValidateIfNodeIsTextualResult.isLong());
    assertFalse(actualValidateIfNodeIsTextualResult.isMissingNode());
    assertFalse(actualValidateIfNodeIsTextualResult.isNumber());
    assertFalse(actualValidateIfNodeIsTextualResult.isObject());
    assertFalse(actualValidateIfNodeIsTextualResult.isPojo());
    assertFalse(actualValidateIfNodeIsTextualResult.isShort());
    assertFalse(actualValidateIfNodeIsTextualResult.isTextual());
    assertFalse(actualValidateIfNodeIsTextualResult.iterator().hasNext());
    assertTrue(actualValidateIfNodeIsTextualResult.isEmpty());
    assertTrue(actualValidateIfNodeIsTextualResult.isNull());
    assertTrue(actualValidateIfNodeIsTextualResult.isValueNode());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}
   */
  @Test
  void testGetValueAsString() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getValueAsString("Name", MissingNode.getInstance()));
    assertNull(
        BpmnJsonConverterUtil.getValueAsString("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  void testGetPropertyValueAsString() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getPropertyValueAsString("Name", MissingNode.getInstance()));
    assertNull(BpmnJsonConverterUtil.getPropertyValueAsString("Name",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}
   */
  @Test
  void testGetProperty() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getProperty("Name", MissingNode.getInstance()));
    assertNull(BpmnJsonConverterUtil.getProperty("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }
}
