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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DynamicBpmnServiceImplDiffblueTest {
  @InjectMocks
  private DynamicBpmnServiceImpl dynamicBpmnServiceImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  public void testChangeServiceTaskClassName() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42",
        "Class Name");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskClassNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeServiceTaskClassNameResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Class Name\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskClassName\" : \"Class Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskClassName\" : \"Class Name\"\n    }\n  }\n}",
        actualChangeServiceTaskClassNameResult.toPrettyString());
    assertEquals("{\n  \"serviceTaskClassName\" : \"Class Name\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeServiceTaskClassNameResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeServiceTaskClassNameResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeServiceTaskClassNameResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeServiceTaskClassNameResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeServiceTaskClassNameResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeServiceTaskClassNameResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeServiceTaskClassNameResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeServiceTaskClassNameResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeServiceTaskClassNameResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeServiceTaskClassNameResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeServiceTaskClassNameResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeServiceTaskClassNameResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeServiceTaskClassNameResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeServiceTaskClassNameResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeServiceTaskClassNameResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeServiceTaskClassNameResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeServiceTaskClassNameResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeServiceTaskClassNameResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeServiceTaskClassNameResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeServiceTaskClassNameResult.isValueNode());
    assertFalse(actualChangeServiceTaskClassNameResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeServiceTaskClassNameResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeServiceTaskClassNameResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  public void testChangeServiceTaskClassName2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42",
        "Class Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskClassNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  public void testChangeServiceTaskClassName3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42",
        "Class Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskClassNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  public void testChangeServiceTaskClassName4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42",
        "Class Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Class Name"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskClassNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String)}
   */
  @Test
  public void testChangeServiceTaskClassName5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskClassNameResult = dynamicBpmnServiceImpl.changeServiceTaskClassName("42",
        "Class Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("serviceTaskClassName"), eq("Class Name"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeServiceTaskClassNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)}
   */
  @Test
  public void testChangeServiceTaskClassName6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "Class Name", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Class Name\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskClassName\" : \"Class Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskClassName\" : \"Class Name\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskClassName\" : \"Class Name\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskClassName(String, String, ObjectNode)}
   */
  @Test
  public void testChangeServiceTaskClassName7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskClassName("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskClassName\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskClassName\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskClassName\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskExpression() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42",
        "Expression");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeServiceTaskExpressionResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Expression\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskExpression\" : \"Expression\"\n    }\n  }\n}",
        actualChangeServiceTaskExpressionResult.toPrettyString());
    assertEquals("{\n  \"serviceTaskExpression\" : \"Expression\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeServiceTaskExpressionResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeServiceTaskExpressionResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeServiceTaskExpressionResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeServiceTaskExpressionResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeServiceTaskExpressionResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeServiceTaskExpressionResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeServiceTaskExpressionResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeServiceTaskExpressionResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeServiceTaskExpressionResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeServiceTaskExpressionResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeServiceTaskExpressionResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeServiceTaskExpressionResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeServiceTaskExpressionResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeServiceTaskExpressionResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeServiceTaskExpressionResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeServiceTaskExpressionResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeServiceTaskExpressionResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeServiceTaskExpressionResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeServiceTaskExpressionResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeServiceTaskExpressionResult.isValueNode());
    assertFalse(actualChangeServiceTaskExpressionResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeServiceTaskExpressionResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeServiceTaskExpressionResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskExpression2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42",
        "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskExpressionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskExpression3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42",
        "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskExpressionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskExpression4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42",
        "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Expression"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskExpressionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskExpression5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskExpressionResult = dynamicBpmnServiceImpl.changeServiceTaskExpression("42",
        "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("serviceTaskExpression"), eq("Expression"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeServiceTaskExpressionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   */
  @Test
  public void testChangeServiceTaskExpression6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "Expression", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Expression\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskExpression\" : \"Expression\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskExpression\" : \"Expression\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskExpression(String, String, ObjectNode)}
   */
  @Test
  public void testChangeServiceTaskExpression7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskExpression("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskExpression\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskExpression\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskExpression\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpression() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeServiceTaskDelegateExpressionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeServiceTaskDelegateExpressionResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Expression\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskDelegateExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskDelegateExpression\" : \"Expression\"\n    }\n  }\n}",
        actualChangeServiceTaskDelegateExpressionResult.toPrettyString());
    assertEquals("{\n  \"serviceTaskDelegateExpression\" : \"Expression\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeServiceTaskDelegateExpressionResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeServiceTaskDelegateExpressionResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isValueNode());
    assertFalse(actualChangeServiceTaskDelegateExpressionResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeServiceTaskDelegateExpressionResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeServiceTaskDelegateExpressionResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpression2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskDelegateExpressionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpression3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskDelegateExpressionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpression4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Expression"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeServiceTaskDelegateExpressionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpression5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeServiceTaskDelegateExpressionResult = dynamicBpmnServiceImpl
        .changeServiceTaskDelegateExpression("42", "Expression");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("serviceTaskDelegateExpression"), eq("Expression"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeServiceTaskDelegateExpressionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpression6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", "Expression", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Expression\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskDelegateExpression\" : \"Expression\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskDelegateExpression\" : \"Expression\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskDelegateExpression\" : \"Expression\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeServiceTaskDelegateExpression(String, String, ObjectNode)}
   */
  @Test
  public void testChangeServiceTaskDelegateExpression7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeServiceTaskDelegateExpression("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"serviceTaskDelegateExpression\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"serviceTaskDelegateExpression\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"serviceTaskDelegateExpression\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  public void testChangeScriptTaskScript() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeScriptTaskScriptResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeScriptTaskScriptResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Script\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"scriptTaskScript\" : \"Script\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"scriptTaskScript\" : \"Script\"\n    }\n  }\n}",
        actualChangeScriptTaskScriptResult.toPrettyString());
    assertEquals("{\n  \"scriptTaskScript\" : \"Script\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeScriptTaskScriptResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeScriptTaskScriptResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeScriptTaskScriptResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeScriptTaskScriptResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeScriptTaskScriptResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeScriptTaskScriptResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeScriptTaskScriptResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeScriptTaskScriptResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeScriptTaskScriptResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeScriptTaskScriptResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeScriptTaskScriptResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeScriptTaskScriptResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeScriptTaskScriptResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeScriptTaskScriptResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeScriptTaskScriptResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeScriptTaskScriptResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeScriptTaskScriptResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeScriptTaskScriptResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeScriptTaskScriptResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeScriptTaskScriptResult.isValueNode());
    assertFalse(actualChangeScriptTaskScriptResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeScriptTaskScriptResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeScriptTaskScriptResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  public void testChangeScriptTaskScript2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeScriptTaskScriptResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  public void testChangeScriptTaskScript3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeScriptTaskScriptResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  public void testChangeScriptTaskScript4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Script"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeScriptTaskScriptResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String)}
   */
  @Test
  public void testChangeScriptTaskScript5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeScriptTaskScriptResult = dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("scriptTaskScript"), eq("Script"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeScriptTaskScriptResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)}
   */
  @Test
  public void testChangeScriptTaskScript6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", "Script", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Script\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"scriptTaskScript\" : \"Script\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"scriptTaskScript\" : \"Script\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"scriptTaskScript\" : \"Script\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeScriptTaskScript(String, String, ObjectNode)}
   */
  @Test
  public void testChangeScriptTaskScript7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeScriptTaskScript("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"scriptTaskScript\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"scriptTaskScript\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"scriptTaskScript\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  public void testChangeUserTaskName() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "Name");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeUserTaskNameResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Name\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"userTaskName\" : \"Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskName\" : \"Name\"\n    }\n  }\n}",
        actualChangeUserTaskNameResult.toPrettyString());
    assertEquals("{\n  \"userTaskName\" : \"Name\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeUserTaskNameResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeUserTaskNameResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeUserTaskNameResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeUserTaskNameResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeUserTaskNameResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeUserTaskNameResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeUserTaskNameResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeUserTaskNameResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeUserTaskNameResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeUserTaskNameResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeUserTaskNameResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeUserTaskNameResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeUserTaskNameResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeUserTaskNameResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeUserTaskNameResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeUserTaskNameResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeUserTaskNameResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeUserTaskNameResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeUserTaskNameResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeUserTaskNameResult.isValueNode());
    assertFalse(actualChangeUserTaskNameResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeUserTaskNameResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeUserTaskNameResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  public void testChangeUserTaskName2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  public void testChangeUserTaskName3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  public void testChangeUserTaskName4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Name"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String)}
   */
  @Test
  public void testChangeUserTaskName5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskNameResult = dynamicBpmnServiceImpl.changeUserTaskName("42", "Name");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskName"), eq("Name"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskName6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", "Name", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Name\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskName\" : \"Name\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskName\" : \"Name\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskName\" : \"Name\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskName(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskName7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskName("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskName\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskName\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskName\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  public void testChangeUserTaskDescription() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42",
        "The characteristics of someone or something");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeUserTaskDescriptionResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"The characteristics of someone or something\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals(
        "{\n  \"42\" : {\n    \"userTaskDescription\" : \"The characteristics of someone or something\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n" + "  \"bpmn\" : {\n" + "    \"42\" : {\n"
        + "      \"userTaskDescription\" : \"The characteristics of someone or something\"\n" + "    }\n" + "  }\n"
        + "}", actualChangeUserTaskDescriptionResult.toPrettyString());
    assertEquals("{\n  \"userTaskDescription\" : \"The characteristics of someone or something\"\n}",
        nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeUserTaskDescriptionResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeUserTaskDescriptionResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeUserTaskDescriptionResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeUserTaskDescriptionResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeUserTaskDescriptionResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeUserTaskDescriptionResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeUserTaskDescriptionResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeUserTaskDescriptionResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeUserTaskDescriptionResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeUserTaskDescriptionResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeUserTaskDescriptionResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeUserTaskDescriptionResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeUserTaskDescriptionResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeUserTaskDescriptionResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeUserTaskDescriptionResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeUserTaskDescriptionResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeUserTaskDescriptionResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeUserTaskDescriptionResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeUserTaskDescriptionResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeUserTaskDescriptionResult.isValueNode());
    assertFalse(actualChangeUserTaskDescriptionResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeUserTaskDescriptionResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeUserTaskDescriptionResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  public void testChangeUserTaskDescription2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42",
        "The characteristics of someone or something");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskDescriptionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  public void testChangeUserTaskDescription3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42",
        "The characteristics of someone or something");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskDescriptionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  public void testChangeUserTaskDescription4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42",
        "The characteristics of someone or something");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("The characteristics of someone or something"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskDescriptionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String)}
   */
  @Test
  public void testChangeUserTaskDescription5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDescriptionResult = dynamicBpmnServiceImpl.changeUserTaskDescription("42",
        "The characteristics of someone or something");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskDescription"), eq("The characteristics of someone or something"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskDescriptionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskDescription6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription("42", "The characteristics of someone or something", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"The characteristics of someone or something\"", nextResult3.toPrettyString());
    assertEquals(
        "{\n  \"42\" : {\n    \"userTaskDescription\" : \"The characteristics of someone or something\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals("{\n" + "  \"bpmn\" : {\n" + "    \"42\" : {\n"
        + "      \"userTaskDescription\" : \"The characteristics of someone or something\"\n" + "    }\n" + "  }\n"
        + "}", infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskDescription\" : \"The characteristics of someone or something\"\n}",
        nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDescription(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskDescription7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDescription("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskDescription\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDescription\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskDescription\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  public void testChangeUserTaskDueDate() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskDueDateResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeUserTaskDueDateResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"2020-03-01\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"userTaskDueDate\" : \"2020-03-01\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDueDate\" : \"2020-03-01\"\n    }\n  }\n}",
        actualChangeUserTaskDueDateResult.toPrettyString());
    assertEquals("{\n  \"userTaskDueDate\" : \"2020-03-01\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeUserTaskDueDateResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeUserTaskDueDateResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeUserTaskDueDateResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeUserTaskDueDateResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeUserTaskDueDateResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeUserTaskDueDateResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeUserTaskDueDateResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeUserTaskDueDateResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeUserTaskDueDateResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeUserTaskDueDateResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeUserTaskDueDateResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeUserTaskDueDateResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeUserTaskDueDateResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeUserTaskDueDateResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeUserTaskDueDateResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeUserTaskDueDateResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeUserTaskDueDateResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeUserTaskDueDateResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeUserTaskDueDateResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeUserTaskDueDateResult.isValueNode());
    assertFalse(actualChangeUserTaskDueDateResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeUserTaskDueDateResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeUserTaskDueDateResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  public void testChangeUserTaskDueDate2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskDueDateResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  public void testChangeUserTaskDueDate3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskDueDateResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  public void testChangeUserTaskDueDate4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("2020-03-01"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskDueDateResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String)}
   */
  @Test
  public void testChangeUserTaskDueDate5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskDueDateResult = dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskDueDate"), eq("2020-03-01"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskDueDateResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskDueDate6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "2020-03-01", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"2020-03-01\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskDueDate\" : \"2020-03-01\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDueDate\" : \"2020-03-01\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskDueDate\" : \"2020-03-01\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskDueDate(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskDueDate7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskDueDate("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskDueDate\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskDueDate\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskDueDate\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  public void testChangeUserTaskPriority() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskPriorityResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeUserTaskPriorityResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Priority\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"userTaskPriority\" : \"Priority\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskPriority\" : \"Priority\"\n    }\n  }\n}",
        actualChangeUserTaskPriorityResult.toPrettyString());
    assertEquals("{\n  \"userTaskPriority\" : \"Priority\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeUserTaskPriorityResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeUserTaskPriorityResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeUserTaskPriorityResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeUserTaskPriorityResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeUserTaskPriorityResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeUserTaskPriorityResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeUserTaskPriorityResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeUserTaskPriorityResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeUserTaskPriorityResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeUserTaskPriorityResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeUserTaskPriorityResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeUserTaskPriorityResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeUserTaskPriorityResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeUserTaskPriorityResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeUserTaskPriorityResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeUserTaskPriorityResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeUserTaskPriorityResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeUserTaskPriorityResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeUserTaskPriorityResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeUserTaskPriorityResult.isValueNode());
    assertFalse(actualChangeUserTaskPriorityResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeUserTaskPriorityResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeUserTaskPriorityResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  public void testChangeUserTaskPriority2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskPriorityResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  public void testChangeUserTaskPriority3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskPriorityResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  public void testChangeUserTaskPriority4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Priority"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskPriorityResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String)}
   */
  @Test
  public void testChangeUserTaskPriority5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskPriorityResult = dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskPriority"), eq("Priority"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskPriorityResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskPriority6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", "Priority", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Priority\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskPriority\" : \"Priority\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskPriority\" : \"Priority\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskPriority\" : \"Priority\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskPriority(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskPriority7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskPriority("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskPriority\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskPriority\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskPriority\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  public void testChangeUserTaskCategory() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskCategoryResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeUserTaskCategoryResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Category\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCategory\" : \"Category\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCategory\" : \"Category\"\n    }\n  }\n}",
        actualChangeUserTaskCategoryResult.toPrettyString());
    assertEquals("{\n  \"userTaskCategory\" : \"Category\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeUserTaskCategoryResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeUserTaskCategoryResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeUserTaskCategoryResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeUserTaskCategoryResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeUserTaskCategoryResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeUserTaskCategoryResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeUserTaskCategoryResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeUserTaskCategoryResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeUserTaskCategoryResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeUserTaskCategoryResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeUserTaskCategoryResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeUserTaskCategoryResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeUserTaskCategoryResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeUserTaskCategoryResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeUserTaskCategoryResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeUserTaskCategoryResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeUserTaskCategoryResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeUserTaskCategoryResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeUserTaskCategoryResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeUserTaskCategoryResult.isValueNode());
    assertFalse(actualChangeUserTaskCategoryResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeUserTaskCategoryResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeUserTaskCategoryResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  public void testChangeUserTaskCategory2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCategoryResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  public void testChangeUserTaskCategory3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCategoryResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  public void testChangeUserTaskCategory4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Category"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCategoryResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String)}
   */
  @Test
  public void testChangeUserTaskCategory5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCategoryResult = dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskCategory"), eq("Category"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskCategoryResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskCategory6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", "Category", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Category\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCategory\" : \"Category\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCategory\" : \"Category\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskCategory\" : \"Category\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCategory(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskCategory7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskCategory("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskCategory\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskCategory\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskCategory\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  public void testChangeUserTaskFormKey() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskFormKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeUserTaskFormKeyResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Form Key\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"userTaskFormKey\" : \"Form Key\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskFormKey\" : \"Form Key\"\n    }\n  }\n}",
        actualChangeUserTaskFormKeyResult.toPrettyString());
    assertEquals("{\n  \"userTaskFormKey\" : \"Form Key\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeUserTaskFormKeyResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeUserTaskFormKeyResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeUserTaskFormKeyResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeUserTaskFormKeyResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeUserTaskFormKeyResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeUserTaskFormKeyResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeUserTaskFormKeyResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeUserTaskFormKeyResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeUserTaskFormKeyResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeUserTaskFormKeyResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeUserTaskFormKeyResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeUserTaskFormKeyResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeUserTaskFormKeyResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeUserTaskFormKeyResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeUserTaskFormKeyResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeUserTaskFormKeyResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeUserTaskFormKeyResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeUserTaskFormKeyResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeUserTaskFormKeyResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeUserTaskFormKeyResult.isValueNode());
    assertFalse(actualChangeUserTaskFormKeyResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeUserTaskFormKeyResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeUserTaskFormKeyResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  public void testChangeUserTaskFormKey2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskFormKeyResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  public void testChangeUserTaskFormKey3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskFormKeyResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  public void testChangeUserTaskFormKey4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Form Key"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskFormKeyResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String)}
   */
  @Test
  public void testChangeUserTaskFormKey5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskFormKeyResult = dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskFormKey"), eq("Form Key"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskFormKeyResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskFormKey6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "Form Key", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Form Key\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskFormKey\" : \"Form Key\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskFormKey\" : \"Form Key\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskFormKey\" : \"Form Key\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskFormKey(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskFormKey7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskFormKey("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskFormKey\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskFormKey\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskFormKey\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  public void testChangeUserTaskAssignee() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskAssigneeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeUserTaskAssigneeResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Assignee\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"userTaskAssignee\" : \"Assignee\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskAssignee\" : \"Assignee\"\n    }\n  }\n}",
        actualChangeUserTaskAssigneeResult.toPrettyString());
    assertEquals("{\n  \"userTaskAssignee\" : \"Assignee\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeUserTaskAssigneeResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeUserTaskAssigneeResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeUserTaskAssigneeResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeUserTaskAssigneeResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeUserTaskAssigneeResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeUserTaskAssigneeResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeUserTaskAssigneeResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeUserTaskAssigneeResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeUserTaskAssigneeResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeUserTaskAssigneeResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeUserTaskAssigneeResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeUserTaskAssigneeResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeUserTaskAssigneeResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeUserTaskAssigneeResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeUserTaskAssigneeResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeUserTaskAssigneeResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeUserTaskAssigneeResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeUserTaskAssigneeResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeUserTaskAssigneeResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeUserTaskAssigneeResult.isValueNode());
    assertFalse(actualChangeUserTaskAssigneeResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeUserTaskAssigneeResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeUserTaskAssigneeResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  public void testChangeUserTaskAssignee2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskAssigneeResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  public void testChangeUserTaskAssignee3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskAssigneeResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  public void testChangeUserTaskAssignee4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Assignee"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskAssigneeResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String)}
   */
  @Test
  public void testChangeUserTaskAssignee5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskAssigneeResult = dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskAssignee"), eq("Assignee"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskAssigneeResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskAssignee6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "Assignee", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Assignee\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskAssignee\" : \"Assignee\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskAssignee\" : \"Assignee\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskAssignee\" : \"Assignee\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskAssignee(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskAssignee7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskAssignee("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskAssignee\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskAssignee\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskAssignee\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  public void testChangeUserTaskOwner() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeUserTaskOwnerResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeUserTaskOwnerResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Owner\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"userTaskOwner\" : \"Owner\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskOwner\" : \"Owner\"\n    }\n  }\n}",
        actualChangeUserTaskOwnerResult.toPrettyString());
    assertEquals("{\n  \"userTaskOwner\" : \"Owner\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeUserTaskOwnerResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeUserTaskOwnerResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeUserTaskOwnerResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeUserTaskOwnerResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeUserTaskOwnerResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeUserTaskOwnerResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeUserTaskOwnerResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeUserTaskOwnerResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeUserTaskOwnerResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeUserTaskOwnerResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeUserTaskOwnerResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeUserTaskOwnerResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeUserTaskOwnerResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeUserTaskOwnerResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeUserTaskOwnerResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeUserTaskOwnerResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeUserTaskOwnerResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeUserTaskOwnerResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeUserTaskOwnerResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeUserTaskOwnerResult.isValueNode());
    assertFalse(actualChangeUserTaskOwnerResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeUserTaskOwnerResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeUserTaskOwnerResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  public void testChangeUserTaskOwner2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskOwnerResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  public void testChangeUserTaskOwner3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskOwnerResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  public void testChangeUserTaskOwner4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Owner"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskOwnerResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String)}
   */
  @Test
  public void testChangeUserTaskOwner5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskOwnerResult = dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("userTaskOwner"), eq("Owner"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskOwnerResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskOwner6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", "Owner", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Owner\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskOwner\" : \"Owner\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskOwner\" : \"Owner\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskOwner\" : \"Owner\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskOwner(String, String, ObjectNode)}
   */
  @Test
  public void testChangeUserTaskOwner7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeUserTaskOwner("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"userTaskOwner\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"userTaskOwner\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"userTaskOwner\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateUser() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateUserResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateUser2() {
    // Arrange
    JsonNodeFactory nf = mock(JsonNodeFactory.class);
    when(nf.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(new ArrayNode(nf));
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(nf).textNode(eq("2020-03-01"));
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateUserResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateUser3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateUserResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateUser4() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateUserResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateUser(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateUser5() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.set(Mockito.<String>any(), Mockito.<JsonNode>any())).thenReturn(MissingNode.getInstance());
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateUserResult = dynamicBpmnServiceImpl.changeUserTaskCandidateUser("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).set(eq("userTaskCandidateUsers"), isA(JsonNode.class));
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskCandidateUserResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateGroup() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateGroupResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateGroup2() {
    // Arrange
    JsonNodeFactory nf = mock(JsonNodeFactory.class);
    when(nf.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(new ArrayNode(nf));
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(nf).textNode(eq("2020-03-01"));
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateGroupResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateGroup3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateGroupResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateGroup4() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode, actualChangeUserTaskCandidateGroupResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeUserTaskCandidateGroup(String, String, boolean)}
   */
  @Test
  public void testChangeUserTaskCandidateGroup5() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.add(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.set(Mockito.<String>any(), Mockito.<JsonNode>any())).thenReturn(MissingNode.getInstance());
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createArrayNode()).thenReturn(arrayNode);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeUserTaskCandidateGroupResult = dynamicBpmnServiceImpl.changeUserTaskCandidateGroup("42",
        "2020-03-01", true);

    // Assert
    verify(objectMapper).createArrayNode();
    verify(objectMapper).createObjectNode();
    verify(arrayNode).add(eq("2020-03-01"));
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).set(eq("userTaskCandidateGroups"), isA(JsonNode.class));
    verify(processEngineConfigurationImpl, atLeast(1)).getObjectMapper();
    assertSame(objectNode2, actualChangeUserTaskCandidateGroupResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKey() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "Decision Table Key");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeDmnTaskDecisionTableKeyResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeDmnTaskDecisionTableKeyResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Decision Table Key\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n    }\n  }\n}",
        actualChangeDmnTaskDecisionTableKeyResult.toPrettyString());
    assertEquals("{\n  \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeDmnTaskDecisionTableKeyResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeDmnTaskDecisionTableKeyResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isValueNode());
    assertFalse(actualChangeDmnTaskDecisionTableKeyResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeDmnTaskDecisionTableKeyResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeDmnTaskDecisionTableKeyResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKey2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "Decision Table Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeDmnTaskDecisionTableKeyResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKey3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "Decision Table Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeDmnTaskDecisionTableKeyResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKey4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "Decision Table Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Decision Table Key"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeDmnTaskDecisionTableKeyResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKey5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeDmnTaskDecisionTableKeyResult = dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42",
        "Decision Table Key");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("dmnTaskDecisionTableKey"), eq("Decision Table Key"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeDmnTaskDecisionTableKeyResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKey6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", "Decision Table Key", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Decision Table Key\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"bpmn\" : {\n    \"42\" : {\n      \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"dmnTaskDecisionTableKey\" : \"Decision Table Key\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeDmnTaskDecisionTableKey(String, String, ObjectNode)}
   */
  @Test
  public void testChangeDmnTaskDecisionTableKey7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeDmnTaskDecisionTableKey("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"dmnTaskDecisionTableKey\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"dmnTaskDecisionTableKey\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"dmnTaskDecisionTableKey\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  public void testChangeSequenceFlowCondition() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42",
        "Condition");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeSequenceFlowConditionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult3.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = actualChangeSequenceFlowConditionResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"Condition\"", nextResult3.toPrettyString());
    Version versionResult = traverseResult4.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"sequenceFlowCondition\" : \"Condition\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"sequenceFlowCondition\" : \"Condition\"\n    }\n  }\n}",
        actualChangeSequenceFlowConditionResult.toPrettyString());
    assertEquals("{\n  \"sequenceFlowCondition\" : \"Condition\"\n}", nextResult2.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult4.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeSequenceFlowConditionResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeSequenceFlowConditionResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeSequenceFlowConditionResult.isArray());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeSequenceFlowConditionResult.isBigDecimal());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeSequenceFlowConditionResult.isBigInteger());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeSequenceFlowConditionResult.isBinary());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeSequenceFlowConditionResult.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeSequenceFlowConditionResult.isDouble());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeSequenceFlowConditionResult.isFloat());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeSequenceFlowConditionResult.isFloatingPointNumber());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeSequenceFlowConditionResult.isInt());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeSequenceFlowConditionResult.isIntegralNumber());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeSequenceFlowConditionResult.isLong());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeSequenceFlowConditionResult.isMissingNode());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeSequenceFlowConditionResult.isNull());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeSequenceFlowConditionResult.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeSequenceFlowConditionResult.isPojo());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeSequenceFlowConditionResult.isShort());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeSequenceFlowConditionResult.isTextual());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeSequenceFlowConditionResult.isValueNode());
    assertFalse(actualChangeSequenceFlowConditionResult.isEmpty());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeSequenceFlowConditionResult.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(actualChangeSequenceFlowConditionResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  public void testChangeSequenceFlowCondition2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42",
        "Condition");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeSequenceFlowConditionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  public void testChangeSequenceFlowCondition3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42",
        "Condition");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeSequenceFlowConditionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  public void testChangeSequenceFlowCondition4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42",
        "Condition");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("Condition"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeSequenceFlowConditionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String)}
   */
  @Test
  public void testChangeSequenceFlowCondition5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    ObjectNode objectNode2 = new ObjectNode(nc2);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeSequenceFlowConditionResult = dynamicBpmnServiceImpl.changeSequenceFlowCondition("42",
        "Condition");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("sequenceFlowCondition"), eq("Condition"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeSequenceFlowConditionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   */
  @Test
  public void testChangeSequenceFlowCondition6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "Condition", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"Condition\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"sequenceFlowCondition\" : \"Condition\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"sequenceFlowCondition\" : \"Condition\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"sequenceFlowCondition\" : \"Condition\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeSequenceFlowCondition(String, String, ObjectNode)}
   */
  @Test
  public void testChangeSequenceFlowCondition7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeSequenceFlowCondition("42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"sequenceFlowCondition\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"sequenceFlowCondition\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"sequenceFlowCondition\" : \"\"\n}", nextResult2.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}
   */
  @Test
  public void testGetBpmnElementProperties() {
    // Arrange, Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnElementProperties("42",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
    assertNull(dynamicBpmnServiceImpl.getBpmnElementProperties("42", new ObjectNode(mock(JsonNodeFactory.class))));
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getBpmnElementProperties(String, ObjectNode)}
   */
  @Test
  public void testGetBpmnElementProperties2() {
    // Arrange
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(null);

    // Act
    ObjectNode actualBpmnElementProperties = dynamicBpmnServiceImpl.getBpmnElementProperties("42", infoNode);

    // Assert
    verify(infoNode).get(eq("bpmn"));
    assertNull(actualBpmnElementProperties);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  public void testChangeLocalizationName() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationNameResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    JsonParser traverseResult = nextResult4.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult3.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult2.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = nextResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonParser traverseResult5 = actualChangeLocalizationNameResult.traverse();
    assertTrue(traverseResult5 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    JsonStreamContext parsingContext5 = traverseResult5.getParsingContext();
    assertEquals("ROOT", parsingContext5.getTypeDesc());
    assertEquals("\"42\"", nextResult4.toPrettyString());
    Version versionResult = traverseResult5.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"name\" : \"42\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : \"42\"\n      }\n    }\n  }\n}",
        actualChangeLocalizationNameResult.toPrettyString());
    assertEquals("{\n  \"name\" : \"42\"\n}", nextResult3.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult5.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult5.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult5.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult5.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult5.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    assertNull(traverseResult5.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult5.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult5.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult5.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult5.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult5.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(traverseResult5.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(parsingContext5.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult5.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult5.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertNull(traverseResult5.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult5.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult5.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult5.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult5.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, traverseResult5.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext5.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext5.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, parsingContext5.getNestingDepth());
    assertEquals(0, nextResult4.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult5.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(0L, traverseResult5.getValueAsLong());
    assertEquals(1, nextResult3.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeLocalizationNameResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult3.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeLocalizationNameResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult5.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult5.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult5.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult5.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult5.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult5.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult5.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(traverseResult5.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext5.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext5.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(parsingContext5.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult4.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeLocalizationNameResult.isArray());
    assertFalse(nextResult4.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeLocalizationNameResult.isBigDecimal());
    assertFalse(nextResult4.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeLocalizationNameResult.isBigInteger());
    assertFalse(nextResult4.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeLocalizationNameResult.isBinary());
    assertFalse(nextResult4.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeLocalizationNameResult.isBoolean());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeLocalizationNameResult.isDouble());
    assertFalse(nextResult3.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult4.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeLocalizationNameResult.isFloat());
    assertFalse(nextResult4.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeLocalizationNameResult.isFloatingPointNumber());
    assertFalse(nextResult4.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeLocalizationNameResult.isInt());
    assertFalse(nextResult4.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeLocalizationNameResult.isIntegralNumber());
    assertFalse(nextResult4.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeLocalizationNameResult.isLong());
    assertFalse(nextResult4.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeLocalizationNameResult.isMissingNode());
    assertFalse(nextResult4.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeLocalizationNameResult.isNull());
    assertFalse(nextResult4.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeLocalizationNameResult.isNumber());
    assertFalse(nextResult4.isObject());
    assertFalse(nextResult4.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeLocalizationNameResult.isPojo());
    assertFalse(nextResult4.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeLocalizationNameResult.isShort());
    assertFalse(nextResult3.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeLocalizationNameResult.isTextual());
    assertFalse(nextResult3.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeLocalizationNameResult.isValueNode());
    assertFalse(actualChangeLocalizationNameResult.isEmpty());
    assertFalse(nextResult4.iterator().hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult3.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeLocalizationNameResult.isContainerNode());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult3.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult4.isValueNode());
    assertTrue(actualChangeLocalizationNameResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult4.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(currentLocation, traverseResult5.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
    assertSame(versionResult, traverseResult4.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  public void testChangeLocalizationName2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeLocalizationNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  public void testChangeLocalizationName3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeLocalizationNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  public void testChangeLocalizationName4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    JsonNodeFactory nc4 = mock(JsonNodeFactory.class);
    when(nc4.objectNode()).thenReturn(new ObjectNode(nc3));
    ObjectNode objectNode = new ObjectNode(nc4);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc4).objectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("42"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeLocalizationNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String)}
   */
  @Test
  public void testChangeLocalizationName5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode2 = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationNameResult = dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("name"), eq("42"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeLocalizationNameResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   */
  @Test
  public void testChangeLocalizationName6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = nextResult4.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"42\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"name\" : \"42\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : \"42\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"name\" : \"42\"\n}", nextResult3.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult4.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult3.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult3.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult4.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult4.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult4.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult4.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult4.isBoolean());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult3.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult4.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult4.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult4.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult4.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult4.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult4.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult4.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult4.isNumber());
    assertFalse(nextResult4.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult4.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult4.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult3.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult3.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(nextResult4.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isContainerNode());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isObject());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult4.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationName(String, String, String, ObjectNode)}
   */
  @Test
  public void testChangeLocalizationName7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationName("en", "42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = nextResult4.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"name\" : \"\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"name\" : \"\"\n    }\n  }\n}", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"name\" : \"\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertEquals("{\n  \"name\" : \"\"\n}", nextResult3.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult4.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult3.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult3.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult4.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult4.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult4.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult4.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult4.isBoolean());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult3.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult4.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult4.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult4.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult4.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult4.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult4.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult4.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult4.isNumber());
    assertFalse(nextResult4.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult4.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult4.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult3.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult3.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(nextResult4.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isContainerNode());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isObject());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult4.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  public void testChangeLocalizationDescription() throws IOException {
    // Arrange
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(new ObjectMapper());

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getObjectMapper();
    Iterator<JsonNode> iteratorResult = actualChangeLocalizationDescriptionResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult2 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    JsonParser traverseResult = nextResult4.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult3.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult2.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = nextResult.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonParser traverseResult5 = actualChangeLocalizationDescriptionResult.traverse();
    assertTrue(traverseResult5 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    JsonStreamContext parsingContext5 = traverseResult5.getParsingContext();
    assertEquals("ROOT", parsingContext5.getTypeDesc());
    assertEquals("\"42\"", nextResult4.toPrettyString());
    Version versionResult = traverseResult5.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals("{\n  \"42\" : {\n    \"description\" : \"42\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"description\" : \"42\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"description\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"description\" : \"42\"\n      }\n    }\n  }\n}",
        actualChangeLocalizationDescriptionResult.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult5.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult5.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult5.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult5.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult5.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    assertNull(traverseResult5.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult5.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult5.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult5.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult5.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult5.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(traverseResult5.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(parsingContext5.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult5.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult5.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertNull(traverseResult5.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult5.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult5.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult5.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult5.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, traverseResult5.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext5.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext5.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, parsingContext5.getNestingDepth());
    assertEquals(0, nextResult4.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult5.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(0L, traverseResult5.getValueAsLong());
    assertEquals(1, nextResult3.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult.size());
    assertEquals(1, actualChangeLocalizationDescriptionResult.size());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.OBJECT, nextResult3.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, actualChangeLocalizationDescriptionResult.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult5.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult5.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult5.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult5.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult5.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult5.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult5.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(traverseResult5.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext5.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext5.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(parsingContext5.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(nextResult4.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult.isArray());
    assertFalse(actualChangeLocalizationDescriptionResult.isArray());
    assertFalse(nextResult4.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(actualChangeLocalizationDescriptionResult.isBigDecimal());
    assertFalse(nextResult4.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult.isBigInteger());
    assertFalse(actualChangeLocalizationDescriptionResult.isBigInteger());
    assertFalse(nextResult4.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult.isBinary());
    assertFalse(actualChangeLocalizationDescriptionResult.isBinary());
    assertFalse(nextResult4.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult.isBoolean());
    assertFalse(actualChangeLocalizationDescriptionResult.isBoolean());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult.isDouble());
    assertFalse(actualChangeLocalizationDescriptionResult.isDouble());
    assertFalse(nextResult3.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult4.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult.isFloat());
    assertFalse(actualChangeLocalizationDescriptionResult.isFloat());
    assertFalse(nextResult4.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(actualChangeLocalizationDescriptionResult.isFloatingPointNumber());
    assertFalse(nextResult4.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult.isInt());
    assertFalse(actualChangeLocalizationDescriptionResult.isInt());
    assertFalse(nextResult4.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(actualChangeLocalizationDescriptionResult.isIntegralNumber());
    assertFalse(nextResult4.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult.isLong());
    assertFalse(actualChangeLocalizationDescriptionResult.isLong());
    assertFalse(nextResult4.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult.isMissingNode());
    assertFalse(actualChangeLocalizationDescriptionResult.isMissingNode());
    assertFalse(nextResult4.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult.isNull());
    assertFalse(actualChangeLocalizationDescriptionResult.isNull());
    assertFalse(nextResult4.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult.isNumber());
    assertFalse(actualChangeLocalizationDescriptionResult.isNumber());
    assertFalse(nextResult4.isObject());
    assertFalse(nextResult4.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult.isPojo());
    assertFalse(actualChangeLocalizationDescriptionResult.isPojo());
    assertFalse(nextResult4.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult.isShort());
    assertFalse(actualChangeLocalizationDescriptionResult.isShort());
    assertFalse(nextResult3.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isTextual());
    assertFalse(actualChangeLocalizationDescriptionResult.isTextual());
    assertFalse(nextResult3.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult.isValueNode());
    assertFalse(actualChangeLocalizationDescriptionResult.isValueNode());
    assertFalse(actualChangeLocalizationDescriptionResult.isEmpty());
    assertFalse(nextResult4.iterator().hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult3.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult.isContainerNode());
    assertTrue(actualChangeLocalizationDescriptionResult.isContainerNode());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult3.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult4.isValueNode());
    assertTrue(actualChangeLocalizationDescriptionResult.isObject());
    assertSame(currentLocation, traverseResult.getCurrentLocation());
    assertSame(currentLocation, traverseResult2.getCurrentLocation());
    assertSame(currentLocation, traverseResult3.getCurrentLocation());
    assertSame(currentLocation, traverseResult4.getCurrentLocation());
    assertSame(currentLocation, traverseResult.getTokenLocation());
    assertSame(currentLocation, traverseResult2.getTokenLocation());
    assertSame(currentLocation, traverseResult3.getTokenLocation());
    assertSame(currentLocation, traverseResult4.getTokenLocation());
    assertSame(currentLocation, traverseResult5.getTokenLocation());
    assertSame(versionResult, traverseResult.version());
    assertSame(versionResult, traverseResult2.version());
    assertSame(versionResult, traverseResult3.version());
    assertSame(versionResult, traverseResult4.version());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  public void testChangeLocalizationDescription2() {
    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeLocalizationDescriptionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  public void testChangeLocalizationDescription3() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode objectNode = new ObjectNode(nc);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc).objectNode();
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeLocalizationDescriptionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  public void testChangeLocalizationDescription4() {
    // Arrange
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.textNode(Mockito.<String>any())).thenReturn(new TextNode("foo"));
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    JsonNodeFactory nc4 = mock(JsonNodeFactory.class);
    when(nc4.objectNode()).thenReturn(new ObjectNode(nc3));
    ObjectNode objectNode = new ObjectNode(nc4);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc4).objectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).textNode(eq("42"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode, actualChangeLocalizationDescriptionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String)}
   */
  @Test
  public void testChangeLocalizationDescription5() {
    // Arrange
    ObjectNode objectNode = mock(ObjectNode.class);
    when(objectNode.put(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    JsonNodeFactory nc = mock(JsonNodeFactory.class);
    when(nc.objectNode()).thenReturn(objectNode);
    JsonNodeFactory nc2 = mock(JsonNodeFactory.class);
    when(nc2.objectNode()).thenReturn(new ObjectNode(nc));
    JsonNodeFactory nc3 = mock(JsonNodeFactory.class);
    when(nc3.objectNode()).thenReturn(new ObjectNode(nc2));
    ObjectNode objectNode2 = new ObjectNode(nc3);
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.createObjectNode()).thenReturn(objectNode2);
    when(processEngineConfigurationImpl.getObjectMapper()).thenReturn(objectMapper);

    // Act
    ObjectNode actualChangeLocalizationDescriptionResult = dynamicBpmnServiceImpl.changeLocalizationDescription("en",
        "42", "42");

    // Assert
    verify(objectMapper).createObjectNode();
    verify(nc3).objectNode();
    verify(nc2).objectNode();
    verify(nc).objectNode();
    verify(objectNode).put(eq("description"), eq("42"));
    verify(processEngineConfigurationImpl).getObjectMapper();
    assertSame(objectNode2, actualChangeLocalizationDescriptionResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)}
   */
  @Test
  public void testChangeLocalizationDescription6() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = nextResult4.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"42\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"description\" : \"42\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"description\" : \"42\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"description\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"description\" : \"42\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult4.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult3.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult3.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult4.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult4.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult4.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult4.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult4.isBoolean());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult3.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult4.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult4.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult4.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult4.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult4.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult4.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult4.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult4.isNumber());
    assertFalse(nextResult4.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult4.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult4.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult3.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult3.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(nextResult4.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isContainerNode());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isObject());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult4.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#changeLocalizationDescription(String, String, String, ObjectNode)}
   */
  @Test
  public void testChangeLocalizationDescription7() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.changeLocalizationDescription("en", "42", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = nextResult4.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"description\" : \"\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"description\" : \"\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"description\" : \"\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"description\" : \"\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult4.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult3.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult3.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult4.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult4.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult4.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult4.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult4.isBoolean());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult3.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult4.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult4.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult4.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult4.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult4.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult4.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult4.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult4.isNumber());
    assertFalse(nextResult4.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult4.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult4.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult3.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult3.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(nextResult4.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isContainerNode());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isObject());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult4.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}
   */
  @Test
  public void testGetLocalizationElementProperties() {
    // Arrange, Act and Assert
    assertNull(dynamicBpmnServiceImpl.getLocalizationElementProperties("en", "42",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
    assertNull(dynamicBpmnServiceImpl.getLocalizationElementProperties("en", "42",
        new ObjectNode(mock(JsonNodeFactory.class))));
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getLocalizationElementProperties(String, String, ObjectNode)}
   */
  @Test
  public void testGetLocalizationElementProperties2() {
    // Arrange
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(null);

    // Act
    ObjectNode actualLocalizationElementProperties = dynamicBpmnServiceImpl.getLocalizationElementProperties("en", "42",
        infoNode);

    // Assert
    verify(infoNode).get(eq("localization"));
    assertNull(actualLocalizationElementProperties);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist() {
    // Arrange, Act and Assert
    assertFalse(dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
    assertFalse(dynamicBpmnServiceImpl.doesElementPropertyExist("42", "Property Name",
        new ObjectNode(mock(JsonNodeFactory.class))));
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist2() {
    // Arrange
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist3() {
    // Arrange
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist4() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode, atLeast(1)).get(eq("42"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist5() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode, atLeast(1)).get(eq("42"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist6() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode2, atLeast(1)).get(eq("42"));
    verify(arrayNode, atLeast(1)).get(eq("Property Name"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertTrue(actualDoesElementPropertyExistResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist7() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode2, atLeast(1)).get(eq("42"));
    verify(arrayNode, atLeast(1)).get(eq("Property Name"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertTrue(actualDoesElementPropertyExistResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist8() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode2, atLeast(1)).get(eq("42"));
    verify(arrayNode, atLeast(1)).get(eq("Property Name"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertTrue(actualDoesElementPropertyExistResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist9() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode3, atLeast(1)).get(eq("42"));
    verify(arrayNode2, atLeast(1)).get(eq("Property Name"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#doesElementPropertyExist(String, String, ObjectNode)}
   */
  @Test
  public void testDoesElementPropertyExist10() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    boolean actualDoesElementPropertyExistResult = dynamicBpmnServiceImpl.doesElementPropertyExist("42",
        "Property Name", infoNode);

    // Assert
    verify(arrayNode2, atLeast(1)).get(eq("42"));
    verify(arrayNode, atLeast(1)).get(eq("Property Name"));
    verify(infoNode, atLeast(1)).get(eq("bpmn"));
    assertFalse(actualDoesElementPropertyExistResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)}
   */
  @Test
  public void testSetElementProperty() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"42\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"Property Name\" : \"42\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"Property Name\" : \"42\"\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"Property Name\" : \"42\"\n    }\n  }\n}",
        infoNode.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#setElementProperty(String, String, String, ObjectNode)}
   */
  @Test
  public void testSetElementProperty2() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.setElementProperty("42", "Property Name", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"Property Name\" : \"\"\n  }\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"Property Name\" : \"\"\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"bpmn\" : {\n    \"42\" : {\n      \"Property Name\" : \"\"\n    }\n  }\n}",
        infoNode.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult3.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#createOrGetBpmnNode(ObjectNode)}
   */
  @Test
  public void testCreateOrGetBpmnNode() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(processEngineConfiguration);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(null);

    // Act
    ObjectNode actualCreateOrGetBpmnNodeResult = dynamicBpmnServiceImpl.createOrGetBpmnNode(infoNode);

    // Assert
    verify(infoNode).has(eq("bpmn"));
    verify(infoNode).get(eq("bpmn"));
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    assertNull(actualCreateOrGetBpmnNodeResult);
  }

  /**
   * Method under test: {@link DynamicBpmnServiceImpl#getBpmnNode(ObjectNode)}
   */
  @Test
  public void testGetBpmnNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test: {@link DynamicBpmnServiceImpl#getBpmnNode(ObjectNode)}
   */
  @Test
  public void testGetBpmnNode2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(processEngineConfiguration);

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getBpmnNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testSetLocalizationProperty() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", "42", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = nextResult4.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"42\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"Property Name\" : \"42\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"Property Name\" : \"42\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"Property Name\" : \"42\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"localization\" : {\n" + "    \"en\" : {\n" + "      \"42\" : {\n"
            + "        \"Property Name\" : \"42\"\n" + "      }\n" + "    }\n" + "  }\n" + "}",
        infoNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult4.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult3.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult3.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult4.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult4.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult4.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult4.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult4.isBoolean());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult3.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult4.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult4.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult4.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult4.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult4.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult4.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult4.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult4.isNumber());
    assertFalse(nextResult4.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult4.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult4.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult3.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult3.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(nextResult4.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isContainerNode());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isObject());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult4.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#setLocalizationProperty(String, String, String, String, ObjectNode)}
   */
  @Test
  public void testSetLocalizationProperty2() throws IOException {
    // Arrange
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    dynamicBpmnServiceImpl.setLocalizationProperty("en", "42", "Property Name", "", infoNode);

    // Assert
    Iterator<JsonNode> iteratorResult = infoNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult4 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult4.next();
    assertTrue(nextResult4 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult2.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult3.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = nextResult4.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    JsonStreamContext parsingContext2 = traverseResult2.getParsingContext();
    assertEquals("ROOT", parsingContext2.getTypeDesc());
    JsonStreamContext parsingContext3 = traverseResult3.getParsingContext();
    assertEquals("ROOT", parsingContext3.getTypeDesc());
    JsonStreamContext parsingContext4 = traverseResult4.getParsingContext();
    assertEquals("ROOT", parsingContext4.getTypeDesc());
    assertEquals("\"\"", nextResult4.toPrettyString());
    assertEquals("{\n  \"42\" : {\n    \"Property Name\" : \"\"\n  }\n}", nextResult2.toPrettyString());
    assertEquals("{\n  \"Property Name\" : \"\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"en\" : {\n    \"42\" : {\n      \"Property Name\" : \"\"\n    }\n  }\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"localization\" : {\n    \"en\" : {\n      \"42\" : {\n        \"Property Name\" : \"\"\n      }\n    }\n  }\n}",
        infoNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult2.getBinaryValue());
    assertNull(traverseResult3.getBinaryValue());
    assertNull(traverseResult4.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult2.getSchema());
    assertNull(traverseResult3.getSchema());
    assertNull(traverseResult4.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult2.getCurrentToken());
    assertNull(traverseResult3.getCurrentToken());
    assertNull(traverseResult4.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult2.getLastClearedToken());
    assertNull(traverseResult3.getLastClearedToken());
    assertNull(traverseResult4.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult2.getCodec());
    assertNull(traverseResult3.getCodec());
    assertNull(traverseResult4.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult2.getNonBlockingInputFeeder());
    assertNull(traverseResult3.getNonBlockingInputFeeder());
    assertNull(traverseResult4.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult2.getCurrentValue());
    assertNull(traverseResult3.getCurrentValue());
    assertNull(traverseResult4.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult2.getEmbeddedObject());
    assertNull(traverseResult3.getEmbeddedObject());
    assertNull(traverseResult4.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult2.getInputSource());
    assertNull(traverseResult3.getInputSource());
    assertNull(traverseResult4.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult2.getObjectId());
    assertNull(traverseResult3.getObjectId());
    assertNull(traverseResult4.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(traverseResult2.getTypeId());
    assertNull(traverseResult3.getTypeId());
    assertNull(traverseResult4.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(parsingContext2.getCurrentValue());
    assertNull(parsingContext3.getCurrentValue());
    assertNull(parsingContext4.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult2.getCurrentName());
    assertNull(traverseResult3.getCurrentName());
    assertNull(traverseResult4.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult2.getText());
    assertNull(traverseResult3.getText());
    assertNull(traverseResult4.getText());
    assertNull(traverseResult.getValueAsString());
    assertNull(traverseResult2.getValueAsString());
    assertNull(traverseResult3.getValueAsString());
    assertNull(traverseResult4.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult2.getCurrentTokenId());
    assertEquals(0, traverseResult3.getCurrentTokenId());
    assertEquals(0, traverseResult4.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult2.getFeatureMask());
    assertEquals(0, traverseResult3.getFeatureMask());
    assertEquals(0, traverseResult4.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult2.getFormatFeatures());
    assertEquals(0, traverseResult3.getFormatFeatures());
    assertEquals(0, traverseResult4.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult2.getTextOffset());
    assertEquals(0, traverseResult3.getTextOffset());
    assertEquals(0, traverseResult4.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, traverseResult2.getValueAsInt());
    assertEquals(0, traverseResult3.getValueAsInt());
    assertEquals(0, traverseResult4.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext2.getCurrentIndex());
    assertEquals(0, parsingContext3.getCurrentIndex());
    assertEquals(0, parsingContext4.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext2.getEntryCount());
    assertEquals(0, parsingContext3.getEntryCount());
    assertEquals(0, parsingContext4.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, parsingContext2.getNestingDepth());
    assertEquals(0, parsingContext3.getNestingDepth());
    assertEquals(0, parsingContext4.getNestingDepth());
    assertEquals(0, nextResult4.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult2.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult3.getValueAsDouble(), 0.0);
    assertEquals(0.0d, traverseResult4.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, nextResult2.size());
    assertEquals(1, nextResult3.size());
    assertEquals(1, infoNode.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult3.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult2.getValueAsBoolean());
    assertFalse(traverseResult3.getValueAsBoolean());
    assertFalse(traverseResult4.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult2.hasCurrentToken());
    assertFalse(traverseResult3.hasCurrentToken());
    assertFalse(traverseResult4.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult2.hasTextCharacters());
    assertFalse(traverseResult3.hasTextCharacters());
    assertFalse(traverseResult4.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult2.isClosed());
    assertFalse(traverseResult3.isClosed());
    assertFalse(traverseResult4.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult2.isExpectedNumberIntToken());
    assertFalse(traverseResult3.isExpectedNumberIntToken());
    assertFalse(traverseResult4.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult2.isExpectedStartArrayToken());
    assertFalse(traverseResult3.isExpectedStartArrayToken());
    assertFalse(traverseResult4.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult2.isExpectedStartObjectToken());
    assertFalse(traverseResult3.isExpectedStartObjectToken());
    assertFalse(traverseResult4.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(traverseResult2.isNaN());
    assertFalse(traverseResult3.isNaN());
    assertFalse(traverseResult4.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext2.hasCurrentIndex());
    assertFalse(parsingContext3.hasCurrentIndex());
    assertFalse(parsingContext4.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext2.hasCurrentName());
    assertFalse(parsingContext3.hasCurrentName());
    assertFalse(parsingContext4.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(parsingContext2.hasPathSegment());
    assertFalse(parsingContext3.hasPathSegment());
    assertFalse(parsingContext4.hasPathSegment());
    assertFalse(nextResult.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult4.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult4.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult4.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult4.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult4.isBoolean());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult3.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult4.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult4.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult4.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult4.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult4.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult4.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult4.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult4.isNumber());
    assertFalse(nextResult4.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult4.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult4.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult3.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(nextResult3.isValueNode());
    assertFalse(infoNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult4.hasNext());
    assertFalse(nextResult4.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isContainerNode());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isObject());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult4.isValueNode());
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#createOrGetLocalizationNode(ObjectNode)}
   */
  @Test
  public void testCreateOrGetLocalizationNode() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(processEngineConfiguration);
    ObjectNode infoNode = mock(ObjectNode.class);
    when(infoNode.has(Mockito.<String>any())).thenReturn(true);
    when(infoNode.get(Mockito.<String>any())).thenReturn(null);

    // Act
    ObjectNode actualCreateOrGetLocalizationNodeResult = dynamicBpmnServiceImpl.createOrGetLocalizationNode(infoNode);

    // Assert
    verify(infoNode).has(eq("localization"));
    verify(infoNode).get(eq("localization"));
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    assertNull(actualCreateOrGetLocalizationNodeResult);
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getLocalizationNode(ObjectNode)}
   */
  @Test
  public void testGetLocalizationNode() {
    // Arrange
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getLocalizationNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#getLocalizationNode(ObjectNode)}
   */
  @Test
  public void testGetLocalizationNode2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DynamicBpmnServiceImpl dynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(processEngineConfiguration);

    // Act and Assert
    assertNull(dynamicBpmnServiceImpl.getLocalizationNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Method under test:
   * {@link DynamicBpmnServiceImpl#DynamicBpmnServiceImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewDynamicBpmnServiceImpl() {
    // Arrange and Act
    DynamicBpmnServiceImpl actualDynamicBpmnServiceImpl = new DynamicBpmnServiceImpl(
        new JtaProcessEngineConfiguration());

    // Assert
    assertNull(actualDynamicBpmnServiceImpl.getCommandExecutor());
    assertNull(
        ((DynamicBpmnServiceImpl) actualDynamicBpmnServiceImpl.processEngineConfiguration.getDynamicBpmnService())
            .getCommandExecutor());
  }
}
