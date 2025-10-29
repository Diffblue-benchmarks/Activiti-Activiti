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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserTaskJsonConverterDiffblueTest {
  /**
   * Method under test: {@link UserTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    UserTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<UserTaskJsonConverter> expectedGetResult = UserTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("UserTask"));
  }

  /**
   * Method under test: {@link UserTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  void testFillJsonTypes2() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();
    convertersToBpmnMap.computeIfPresent("UserTask", mock(BiFunction.class));

    // Act
    UserTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<UserTaskJsonConverter> expectedGetResult = UserTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("UserTask"));
  }

  /**
   * Method under test: {@link UserTaskJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  void testGetStencilId() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    // Act and Assert
    assertEquals("UserTask", userTaskJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  void testConvertElementToJson() throws IOException {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    userTaskJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("activiti-idm-assignee", new ArrayList<>());
    extensionElements.put("activiti-idm-assignee-field", new ArrayList<>());
    extensionElements.put("activiti-idm-candidate-user", new ArrayList<>());
    extensionElements.put("activiti-idm-candidate-group", new ArrayList<>());
    extensionElements.put("initiator-can-complete", extensionElementList);

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("${taskAssignmentBean.assignTaskToCandidateUsers(");

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("${taskAssignmentBean.assignTaskToCandidateGroups(");

    UserTask baseElement = new UserTask();
    baseElement.setExtensionElements(extensionElements);
    baseElement.setAssignee("${taskAssignmentBean.assignTaskToAssignee(");
    baseElement.setCandidateUsers(candidateUsers);
    baseElement.setCandidateGroups(candidateGroups);
    baseElement.setPriority(null);

    // Act
    userTaskJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonNode nextResult4 = iteratorResult3.next();
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
    assertEquals("\"${taskAssignmentBean.assignTaskToAssignee(\"", nextResult4.toPrettyString());
    assertEquals("\"static\"", nextResult3.toPrettyString());
    assertEquals(
        "{\n" + "  \"assignment\" : {\n" + "    \"type\" : \"static\",\n"
            + "    \"assignee\" : \"${taskAssignmentBean.assignTaskToAssignee(\",\n" + "    \"candidateUsers\" : [ {\n"
            + "      \"value\" : \"${taskAssignmentBean.assignTaskToCandidateUsers(\"\n" + "    } ],\n"
            + "    \"candidateGroups\" : [ {\n"
            + "      \"value\" : \"${taskAssignmentBean.assignTaskToCandidateGroups(\"\n" + "    } ]\n" + "  }\n" + "}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"type\" : \"static\",\n" + "  \"assignee\" : \"${taskAssignmentBean.assignTaskToAssignee(\",\n"
            + "  \"candidateUsers\" : [ {\n" + "    \"value\" : \"${taskAssignmentBean.assignTaskToCandidateUsers(\"\n"
            + "  } ],\n" + "  \"candidateGroups\" : [ {\n"
            + "    \"value\" : \"${taskAssignmentBean.assignTaskToCandidateGroups(\"\n" + "  } ]\n" + "}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"usertaskassignment\" : {\n" + "    \"assignment\" : {\n"
        + "      \"type\" : \"static\",\n" + "      \"assignee\" : \"${taskAssignmentBean.assignTaskToAssignee(\",\n"
        + "      \"candidateUsers\" : [ {\n"
        + "        \"value\" : \"${taskAssignmentBean.assignTaskToCandidateUsers(\"\n" + "      } ],\n"
        + "      \"candidateGroups\" : [ {\n"
        + "        \"value\" : \"${taskAssignmentBean.assignTaskToCandidateGroups(\"\n" + "      } ]\n" + "    }\n"
        + "  }\n" + "}", propertiesNode.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0, nextResult4.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0.0d, traverseResult3.getValueAsDouble());
    assertEquals(0.0d, traverseResult4.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, propertiesNode.size());
    assertEquals(4, nextResult2.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
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
    assertFalse(nextResult3.isObject());
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
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(nextResult4.isValueNode());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  void testConvertElementToJson2() throws IOException {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    userTaskJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("activiti-idm-assignee", new ArrayList<>());
    extensionElements.put("activiti-idm-assignee-field", new ArrayList<>());
    extensionElements.put("activiti-idm-candidate-user", new ArrayList<>());
    extensionElements.put("activiti-idm-candidate-group", new ArrayList<>());
    extensionElements.put("initiator-can-complete", extensionElementList);

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("${taskAssignmentBean.assignTaskToCandidateUsers(");

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("${taskAssignmentBean.assignTaskToCandidateGroups(");

    UserTask baseElement = new UserTask();
    baseElement.setExtensionElements(extensionElements);
    baseElement.setAssignee("${taskAssignmentBean.assignTaskToAssignee(");
    baseElement.setCandidateUsers(candidateUsers);
    baseElement.setCandidateGroups(candidateGroups);
    baseElement.setPriority("Base Element");

    // Act
    userTaskJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    JsonNode nextResult3 = iteratorResult.next();
    assertTrue(nextResult3 instanceof TextNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult4 = iteratorResult3.next();
    assertTrue(nextResult4 instanceof TextNode);
    JsonNode nextResult5 = iteratorResult3.next();
    assertTrue(nextResult5 instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonParser traverseResult2 = nextResult3.traverse();
    assertTrue(traverseResult2 instanceof TreeTraversingParser);
    JsonParser traverseResult3 = nextResult2.traverse();
    assertTrue(traverseResult3 instanceof TreeTraversingParser);
    JsonParser traverseResult4 = nextResult4.traverse();
    assertTrue(traverseResult4 instanceof TreeTraversingParser);
    JsonParser traverseResult5 = nextResult5.traverse();
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
    assertEquals("\"${taskAssignmentBean.assignTaskToAssignee(\"", nextResult5.toPrettyString());
    assertEquals("\"Base Element\"", nextResult3.toPrettyString());
    assertEquals("\"static\"", nextResult4.toPrettyString());
    assertEquals(
        "{\n" + "  \"assignment\" : {\n" + "    \"type\" : \"static\",\n"
            + "    \"assignee\" : \"${taskAssignmentBean.assignTaskToAssignee(\",\n" + "    \"candidateUsers\" : [ {\n"
            + "      \"value\" : \"${taskAssignmentBean.assignTaskToCandidateUsers(\"\n" + "    } ],\n"
            + "    \"candidateGroups\" : [ {\n"
            + "      \"value\" : \"${taskAssignmentBean.assignTaskToCandidateGroups(\"\n" + "    } ]\n" + "  }\n" + "}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"type\" : \"static\",\n" + "  \"assignee\" : \"${taskAssignmentBean.assignTaskToAssignee(\",\n"
            + "  \"candidateUsers\" : [ {\n" + "    \"value\" : \"${taskAssignmentBean.assignTaskToCandidateUsers(\"\n"
            + "  } ],\n" + "  \"candidateGroups\" : [ {\n"
            + "    \"value\" : \"${taskAssignmentBean.assignTaskToCandidateGroups(\"\n" + "  } ]\n" + "}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"usertaskassignment\" : {\n" + "    \"assignment\" : {\n"
        + "      \"type\" : \"static\",\n" + "      \"assignee\" : \"${taskAssignmentBean.assignTaskToAssignee(\",\n"
        + "      \"candidateUsers\" : [ {\n"
        + "        \"value\" : \"${taskAssignmentBean.assignTaskToCandidateUsers(\"\n" + "      } ],\n"
        + "      \"candidateGroups\" : [ {\n"
        + "        \"value\" : \"${taskAssignmentBean.assignTaskToCandidateGroups(\"\n" + "      } ]\n" + "    }\n"
        + "  },\n" + "  \"prioritydefinition\" : \"Base Element\"\n" + "}", propertiesNode.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0, nextResult4.size());
    assertEquals(0, nextResult5.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0.0d, traverseResult3.getValueAsDouble());
    assertEquals(0.0d, traverseResult4.getValueAsDouble());
    assertEquals(0.0d, traverseResult5.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(0L, traverseResult5.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(2, propertiesNode.size());
    assertEquals(4, nextResult2.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult5.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult3.isArray());
    assertFalse(nextResult2.isArray());
    assertFalse(nextResult4.isArray());
    assertFalse(nextResult5.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult3.isBigDecimal());
    assertFalse(nextResult2.isBigDecimal());
    assertFalse(nextResult4.isBigDecimal());
    assertFalse(nextResult5.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult3.isBigInteger());
    assertFalse(nextResult2.isBigInteger());
    assertFalse(nextResult4.isBigInteger());
    assertFalse(nextResult5.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult3.isBinary());
    assertFalse(nextResult2.isBinary());
    assertFalse(nextResult4.isBinary());
    assertFalse(nextResult5.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult3.isBoolean());
    assertFalse(nextResult2.isBoolean());
    assertFalse(nextResult4.isBoolean());
    assertFalse(nextResult5.isBoolean());
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult5.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult5.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult3.isFloat());
    assertFalse(nextResult2.isFloat());
    assertFalse(nextResult4.isFloat());
    assertFalse(nextResult5.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult3.isFloatingPointNumber());
    assertFalse(nextResult2.isFloatingPointNumber());
    assertFalse(nextResult4.isFloatingPointNumber());
    assertFalse(nextResult5.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult3.isInt());
    assertFalse(nextResult2.isInt());
    assertFalse(nextResult4.isInt());
    assertFalse(nextResult5.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult3.isIntegralNumber());
    assertFalse(nextResult2.isIntegralNumber());
    assertFalse(nextResult4.isIntegralNumber());
    assertFalse(nextResult5.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult3.isLong());
    assertFalse(nextResult2.isLong());
    assertFalse(nextResult4.isLong());
    assertFalse(nextResult5.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult3.isMissingNode());
    assertFalse(nextResult2.isMissingNode());
    assertFalse(nextResult4.isMissingNode());
    assertFalse(nextResult5.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult3.isNull());
    assertFalse(nextResult2.isNull());
    assertFalse(nextResult4.isNull());
    assertFalse(nextResult5.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult3.isNumber());
    assertFalse(nextResult2.isNumber());
    assertFalse(nextResult4.isNumber());
    assertFalse(nextResult5.isNumber());
    assertFalse(nextResult3.isObject());
    assertFalse(nextResult4.isObject());
    assertFalse(nextResult5.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult3.isPojo());
    assertFalse(nextResult2.isPojo());
    assertFalse(nextResult4.isPojo());
    assertFalse(nextResult5.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(nextResult3.isShort());
    assertFalse(nextResult2.isShort());
    assertFalse(nextResult4.isShort());
    assertFalse(nextResult5.isShort());
    assertFalse(nextResult.isTextual());
    assertFalse(nextResult2.isTextual());
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult5.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult5.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(nextResult4.isValueNode());
    assertTrue(nextResult5.isValueNode());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  void testConvertElementToJson3() throws IOException {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    userTaskJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());
    extensionElements.put("activiti-idm-assignee-field", new ArrayList<>());
    extensionElements.put("activiti-idm-candidate-user", new ArrayList<>());
    extensionElements.put("activiti-idm-candidate-group", new ArrayList<>());
    extensionElements.put("initiator-can-complete", extensionElementList);

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("${taskAssignmentBean.assignTaskToCandidateUsers(");

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("Base Element");

    UserTask baseElement = new UserTask();
    baseElement.setExtensionElements(extensionElements);
    baseElement.setAssignee("${taskAssignmentBean.assignTaskToAssignee(");
    baseElement.setCandidateUsers(candidateUsers);
    baseElement.setCandidateGroups(candidateGroups);
    baseElement.setPriority(null);

    // Act
    userTaskJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult3.next();
    assertTrue(nextResult3 instanceof TextNode);
    JsonNode nextResult4 = iteratorResult3.next();
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
    assertEquals("\"${taskAssignmentBean.assignTaskToAssignee(\"", nextResult4.toPrettyString());
    assertEquals("\"static\"", nextResult3.toPrettyString());
    assertEquals(
        "{\n" + "  \"assignment\" : {\n" + "    \"type\" : \"static\",\n"
            + "    \"assignee\" : \"${taskAssignmentBean.assignTaskToAssignee(\",\n" + "    \"candidateUsers\" : [ {\n"
            + "      \"value\" : \"${taskAssignmentBean.assignTaskToCandidateUsers(\"\n" + "    } ],\n"
            + "    \"candidateGroups\" : [ {\n" + "      \"value\" : \"Base Element\"\n" + "    } ]\n" + "  }\n" + "}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"type\" : \"static\",\n" + "  \"assignee\" : \"${taskAssignmentBean.assignTaskToAssignee(\",\n"
            + "  \"candidateUsers\" : [ {\n" + "    \"value\" : \"${taskAssignmentBean.assignTaskToCandidateUsers(\"\n"
            + "  } ],\n" + "  \"candidateGroups\" : [ {\n" + "    \"value\" : \"Base Element\"\n" + "  } ]\n" + "}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"usertaskassignment\" : {\n" + "    \"assignment\" : {\n"
        + "      \"type\" : \"static\",\n" + "      \"assignee\" : \"${taskAssignmentBean.assignTaskToAssignee(\",\n"
        + "      \"candidateUsers\" : [ {\n"
        + "        \"value\" : \"${taskAssignmentBean.assignTaskToCandidateUsers(\"\n" + "      } ],\n"
        + "      \"candidateGroups\" : [ {\n" + "        \"value\" : \"Base Element\"\n" + "      } ]\n" + "    }\n"
        + "  }\n" + "}", propertiesNode.toPrettyString());
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
    assertEquals(0, nextResult3.size());
    assertEquals(0, nextResult4.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0.0d, traverseResult2.getValueAsDouble());
    assertEquals(0.0d, traverseResult3.getValueAsDouble());
    assertEquals(0.0d, traverseResult4.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(0L, traverseResult2.getValueAsLong());
    assertEquals(0L, traverseResult3.getValueAsLong());
    assertEquals(0L, traverseResult4.getValueAsLong());
    assertEquals(1, nextResult.size());
    assertEquals(1, propertiesNode.size());
    assertEquals(4, nextResult2.size());
    assertEquals(JsonNodeType.OBJECT, nextResult.getNodeType());
    assertEquals(JsonNodeType.OBJECT, nextResult2.getNodeType());
    assertEquals(JsonNodeType.STRING, nextResult3.getNodeType());
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
    assertFalse(nextResult3.isContainerNode());
    assertFalse(nextResult4.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult2.isDouble());
    assertFalse(nextResult3.isDouble());
    assertFalse(nextResult4.isDouble());
    assertFalse(nextResult.isEmpty());
    assertFalse(nextResult2.isEmpty());
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
    assertFalse(nextResult3.isObject());
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
    assertFalse(nextResult.isValueNode());
    assertFalse(nextResult2.isValueNode());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertTrue(nextResult.isContainerNode());
    assertTrue(nextResult2.isContainerNode());
    assertTrue(nextResult3.isEmpty());
    assertTrue(nextResult4.isEmpty());
    assertTrue(nextResult.isObject());
    assertTrue(nextResult2.isObject());
    assertTrue(nextResult3.isTextual());
    assertTrue(nextResult4.isTextual());
    assertTrue(nextResult3.isValueNode());
    assertTrue(nextResult4.isValueNode());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#getExtensionElementValue(String, UserTask)}
   */
  @Test
  void testGetExtensionElementValue() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    // Act and Assert
    assertEquals("", userTaskJsonConverter.getExtensionElementValue("Name", new UserTask()));
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = userTaskJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof UserTask);
    assertNull(((UserTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((UserTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((UserTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((UserTask) actualConvertJsonToElementResult).getAssignee());
    assertNull(((UserTask) actualConvertJsonToElementResult).getBusinessCalendarName());
    assertNull(((UserTask) actualConvertJsonToElementResult).getCategory());
    assertNull(((UserTask) actualConvertJsonToElementResult).getDueDate());
    assertNull(((UserTask) actualConvertJsonToElementResult).getExtensionId());
    assertNull(((UserTask) actualConvertJsonToElementResult).getFormKey());
    assertNull(((UserTask) actualConvertJsonToElementResult).getOwner());
    assertNull(((UserTask) actualConvertJsonToElementResult).getPriority());
    assertNull(((UserTask) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((UserTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((UserTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((UserTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isExtended());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCandidateGroups().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCandidateUsers().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCustomProperties().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getFormProperties().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getTaskListeners().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCustomGroupIdentityLinks().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCustomUserIdentityLinks().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode elementNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = userTaskJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof UserTask);
    assertNull(((UserTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((UserTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((UserTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((UserTask) actualConvertJsonToElementResult).getAssignee());
    assertNull(((UserTask) actualConvertJsonToElementResult).getBusinessCalendarName());
    assertNull(((UserTask) actualConvertJsonToElementResult).getCategory());
    assertNull(((UserTask) actualConvertJsonToElementResult).getDueDate());
    assertNull(((UserTask) actualConvertJsonToElementResult).getExtensionId());
    assertNull(((UserTask) actualConvertJsonToElementResult).getFormKey());
    assertNull(((UserTask) actualConvertJsonToElementResult).getOwner());
    assertNull(((UserTask) actualConvertJsonToElementResult).getPriority());
    assertNull(((UserTask) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((UserTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((UserTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((UserTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isExtended());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCandidateGroups().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCandidateUsers().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCustomProperties().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getFormProperties().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getTaskListeners().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCustomGroupIdentityLinks().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCustomUserIdentityLinks().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement3() {
    // Arrange
    HashMap<String, String> formMap = new HashMap<>();
    formMap.computeIfPresent("foo", mock(BiFunction.class));

    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    userTaskJsonConverter.setFormMap(formMap);
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = userTaskJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof UserTask);
    assertNull(((UserTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((UserTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((UserTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((UserTask) actualConvertJsonToElementResult).getAssignee());
    assertNull(((UserTask) actualConvertJsonToElementResult).getBusinessCalendarName());
    assertNull(((UserTask) actualConvertJsonToElementResult).getCategory());
    assertNull(((UserTask) actualConvertJsonToElementResult).getDueDate());
    assertNull(((UserTask) actualConvertJsonToElementResult).getExtensionId());
    assertNull(((UserTask) actualConvertJsonToElementResult).getFormKey());
    assertNull(((UserTask) actualConvertJsonToElementResult).getOwner());
    assertNull(((UserTask) actualConvertJsonToElementResult).getPriority());
    assertNull(((UserTask) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((UserTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((UserTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((UserTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((UserTask) actualConvertJsonToElementResult).isExtended());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCandidateGroups().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCandidateUsers().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCustomProperties().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getFormProperties().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getTaskListeners().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCustomGroupIdentityLinks().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).getCustomUserIdentityLinks().isEmpty());
    assertTrue(((UserTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillAssigneeInfo() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    MissingNode idmDefNode = MissingNode.getInstance();
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillAssigneeInfo(idmDefNode, canCompleteTaskNode, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillAssigneeInfo2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    MissingNode idmDefNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillAssigneeInfo(idmDefNode, null, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillAssigneeInfo3() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    MissingNode idmDefNode = MissingNode.getInstance();
    NullNode canCompleteTaskNode = NullNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillAssigneeInfo(idmDefNode, canCompleteTaskNode, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillAssigneeInfo(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillAssigneeInfo4() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    MissingNode idmDefNode = MissingNode.getInstance();
    BooleanNode canCompleteTaskNode = BooleanNode.getFalse();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillAssigneeInfo(idmDefNode, canCompleteTaskNode, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    MissingNode idmDefNode = MissingNode.getInstance();
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode idmDefNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers3() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers4() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers5() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers6() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers7() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers8() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(null);
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers9() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers10() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers11() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers12() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers13() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    verify(arrayNode2).size();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals("${taskAssignmentBean.assignTaskToCandidateUsers('1', execution)}", candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("user-info-email-1");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("1", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("user-info-externalid-1");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("1", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("user-info-firstname-1");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("1", getResult6.getElementText());
    List<ExtensionElement> getResult7 = extensionElements.get("user-info-lastname-1");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("1", getResult8.getElementText());
    List<ExtensionElement> getResult9 = extensionElements.get("activiti-idm-candidate-user");
    assertEquals(1, getResult9.size());
    ExtensionElement getResult10 = getResult9.get(0);
    assertEquals("activiti-idm-candidate-user", getResult10.getName());
    List<ExtensionElement> getResult11 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult11.size());
    ExtensionElement getResult12 = getResult11.get(0);
    assertEquals("initiator-can-complete", getResult12.getName());
    assertEquals("modeler", getResult10.getNamespacePrefix());
    assertEquals("modeler", getResult12.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertEquals("user-info-email-1", getResult2.getName());
    assertEquals("user-info-externalid-1", getResult4.getName());
    assertEquals("user-info-firstname-1", getResult6.getName());
    assertEquals("user-info-lastname-1", getResult8.getName());
    assertNull(getResult10.getId());
    assertNull(getResult12.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult6.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult10.getXmlColumnNumber());
    assertEquals(0, getResult12.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult10.getXmlRowNumber());
    assertEquals(0, getResult12.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult10.getAttributes().isEmpty());
    assertTrue(getResult12.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult10.getExtensionElements().isEmpty());
    assertTrue(getResult12.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult10.getChildElements().isEmpty());
    assertTrue(getResult12.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult12.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult10.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult10.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult12.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers14() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.size()).thenReturn(0);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    verify(arrayNode2).size();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals("1", candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("user-info-email-1");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("1", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("user-info-externalid-1");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("1", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("user-info-firstname-1");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("1", getResult6.getElementText());
    List<ExtensionElement> getResult7 = extensionElements.get("user-info-lastname-1");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("1", getResult8.getElementText());
    List<ExtensionElement> getResult9 = extensionElements.get("activiti-idm-candidate-user");
    assertEquals(1, getResult9.size());
    ExtensionElement getResult10 = getResult9.get(0);
    assertEquals("activiti-idm-candidate-user", getResult10.getName());
    List<ExtensionElement> getResult11 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult11.size());
    ExtensionElement getResult12 = getResult11.get(0);
    assertEquals("initiator-can-complete", getResult12.getName());
    assertEquals("modeler", getResult10.getNamespacePrefix());
    assertEquals("modeler", getResult12.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertEquals("user-info-email-1", getResult2.getName());
    assertEquals("user-info-externalid-1", getResult4.getName());
    assertEquals("user-info-firstname-1", getResult6.getName());
    assertEquals("user-info-lastname-1", getResult8.getName());
    assertNull(getResult10.getId());
    assertNull(getResult12.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult6.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult10.getXmlColumnNumber());
    assertEquals(0, getResult12.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult10.getXmlRowNumber());
    assertEquals(0, getResult12.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult10.getAttributes().isEmpty());
    assertTrue(getResult12.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult10.getExtensionElements().isEmpty());
    assertTrue(getResult12.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult10.getChildElements().isEmpty());
    assertTrue(getResult12.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult12.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult10.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult10.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult12.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers15() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers16() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals("${taskAssignmentBean.assignTaskToCandidateUsers('As Text', execution)}", candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("user-info-email-As Text");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("As Text", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("user-info-externalid-As Text");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("As Text", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("user-info-firstname-As Text");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("As Text", getResult6.getElementText());
    List<ExtensionElement> getResult7 = extensionElements.get("user-info-lastname-As Text");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("As Text", getResult8.getElementText());
    List<ExtensionElement> getResult9 = extensionElements.get("activiti-idm-candidate-user");
    assertEquals(1, getResult9.size());
    ExtensionElement getResult10 = getResult9.get(0);
    assertEquals("activiti-idm-candidate-user", getResult10.getName());
    List<ExtensionElement> getResult11 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult11.size());
    ExtensionElement getResult12 = getResult11.get(0);
    assertEquals("initiator-can-complete", getResult12.getName());
    assertEquals("modeler", getResult10.getNamespacePrefix());
    assertEquals("modeler", getResult12.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertEquals("user-info-email-As Text", getResult2.getName());
    assertEquals("user-info-externalid-As Text", getResult4.getName());
    assertEquals("user-info-firstname-As Text", getResult6.getName());
    assertEquals("user-info-lastname-As Text", getResult8.getName());
    assertNull(getResult10.getId());
    assertNull(getResult12.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult6.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult10.getXmlColumnNumber());
    assertEquals(0, getResult12.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult10.getXmlRowNumber());
    assertEquals(0, getResult12.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult10.getAttributes().isEmpty());
    assertTrue(getResult12.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult10.getExtensionElements().isEmpty());
    assertTrue(getResult12.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult10.getChildElements().isEmpty());
    assertTrue(getResult12.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult12.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult10.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult10.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult12.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers17() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn(null);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(task.getCandidateUsers().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers18() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    ArrayNode canCompleteTaskNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals("${taskAssignmentBean.assignTaskToCandidateUsers('As Text', execution)}", candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("user-info-email-As Text");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("As Text", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("user-info-externalid-As Text");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("As Text", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("user-info-firstname-As Text");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("As Text", getResult6.getElementText());
    List<ExtensionElement> getResult7 = extensionElements.get("user-info-lastname-As Text");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("As Text", getResult8.getElementText());
    List<ExtensionElement> getResult9 = extensionElements.get("activiti-idm-candidate-user");
    assertEquals(1, getResult9.size());
    ExtensionElement getResult10 = getResult9.get(0);
    assertEquals("activiti-idm-candidate-user", getResult10.getName());
    List<ExtensionElement> getResult11 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult11.size());
    ExtensionElement getResult12 = getResult11.get(0);
    assertEquals("initiator-can-complete", getResult12.getName());
    assertEquals("modeler", getResult10.getNamespacePrefix());
    assertEquals("modeler", getResult12.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertEquals("user-info-email-As Text", getResult2.getName());
    assertEquals("user-info-externalid-As Text", getResult4.getName());
    assertEquals("user-info-firstname-As Text", getResult6.getName());
    assertEquals("user-info-lastname-As Text", getResult8.getName());
    assertNull(getResult10.getId());
    assertNull(getResult12.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult6.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult10.getXmlColumnNumber());
    assertEquals(0, getResult12.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult10.getXmlRowNumber());
    assertEquals(0, getResult12.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult10.getAttributes().isEmpty());
    assertTrue(getResult12.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult10.getExtensionElements().isEmpty());
    assertTrue(getResult12.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult10.getChildElements().isEmpty());
    assertTrue(getResult12.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult12.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult10.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult10.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult12.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers19() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, null, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals("${taskAssignmentBean.assignTaskToCandidateUsers('As Text', execution)}", candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("user-info-email-As Text");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("As Text", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("user-info-externalid-As Text");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("As Text", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("user-info-firstname-As Text");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("As Text", getResult6.getElementText());
    List<ExtensionElement> getResult7 = extensionElements.get("user-info-lastname-As Text");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("As Text", getResult8.getElementText());
    List<ExtensionElement> getResult9 = extensionElements.get("activiti-idm-candidate-user");
    assertEquals(1, getResult9.size());
    ExtensionElement getResult10 = getResult9.get(0);
    assertEquals("activiti-idm-candidate-user", getResult10.getName());
    List<ExtensionElement> getResult11 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult11.size());
    ExtensionElement getResult12 = getResult11.get(0);
    assertEquals("initiator-can-complete", getResult12.getName());
    assertEquals("modeler", getResult10.getNamespacePrefix());
    assertEquals("modeler", getResult12.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertEquals("user-info-email-As Text", getResult2.getName());
    assertEquals("user-info-externalid-As Text", getResult4.getName());
    assertEquals("user-info-firstname-As Text", getResult6.getName());
    assertEquals("user-info-lastname-As Text", getResult8.getName());
    assertNull(getResult10.getId());
    assertNull(getResult12.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult6.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult10.getXmlColumnNumber());
    assertEquals(0, getResult12.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult10.getXmlRowNumber());
    assertEquals(0, getResult12.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult10.getAttributes().isEmpty());
    assertTrue(getResult12.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult10.getExtensionElements().isEmpty());
    assertTrue(getResult12.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult10.getChildElements().isEmpty());
    assertTrue(getResult12.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult12.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult10.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult10.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult12.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers20() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(true);
    when(canCompleteTaskNode.asText()).thenReturn("As Text");
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateUsers = task.getCandidateUsers();
    assertEquals(1, candidateUsers.size());
    assertEquals("${taskAssignmentBean.assignTaskToCandidateUsers('As Text', execution)}", candidateUsers.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(6, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("user-info-email-As Text");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("As Text", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("user-info-externalid-As Text");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("As Text", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("user-info-firstname-As Text");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("As Text", getResult6.getElementText());
    List<ExtensionElement> getResult7 = extensionElements.get("user-info-lastname-As Text");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("As Text", getResult8.getElementText());
    List<ExtensionElement> getResult9 = extensionElements.get("activiti-idm-candidate-user");
    assertEquals(1, getResult9.size());
    ExtensionElement getResult10 = getResult9.get(0);
    assertEquals("activiti-idm-candidate-user", getResult10.getName());
    List<ExtensionElement> getResult11 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult11.size());
    ExtensionElement getResult12 = getResult11.get(0);
    assertEquals("initiator-can-complete", getResult12.getName());
    assertEquals("modeler", getResult10.getNamespacePrefix());
    assertEquals("modeler", getResult12.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertEquals("user-info-email-As Text", getResult2.getName());
    assertEquals("user-info-externalid-As Text", getResult4.getName());
    assertEquals("user-info-firstname-As Text", getResult6.getName());
    assertEquals("user-info-lastname-As Text", getResult8.getName());
    assertNull(getResult10.getId());
    assertNull(getResult12.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult6.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult10.getXmlColumnNumber());
    assertEquals(0, getResult12.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult10.getXmlRowNumber());
    assertEquals(0, getResult12.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult10.getAttributes().isEmpty());
    assertTrue(getResult12.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult10.getExtensionElements().isEmpty());
    assertTrue(getResult12.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult10.getChildElements().isEmpty());
    assertTrue(getResult12.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult12.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult10.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult10.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult12.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateUsers(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateUsers21() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(true);
    when(canCompleteTaskNode.asText()).thenReturn("As Text");
    UserTask task = mock(UserTask.class);
    doNothing().when(task).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(task).setCandidateUsers(Mockito.<List<String>>any());

    // Act
    userTaskJsonConverter.fillCandidateUsers(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    verify(task, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(task).setCandidateUsers(isA(List.class));
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    MissingNode idmDefNode = MissingNode.getInstance();
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode idmDefNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups3() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups4() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups5() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups6() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups7() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups8() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(null);
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups9() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups10() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups11() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups12() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups13() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    verify(arrayNode2).size();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals("${taskAssignmentBean.assignTaskToCandidateGroups('1', execution)}", candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("group-info-externalid-1");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("1", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("group-info-name-1");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("1", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("activiti-idm-candidate-group");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("activiti-idm-candidate-group", getResult6.getName());
    assertEquals("group-info-externalid-1", getResult2.getName());
    assertEquals("group-info-name-1", getResult4.getName());
    List<ExtensionElement> getResult7 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("initiator-can-complete", getResult8.getName());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertNull(getResult6.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult8.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult6.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups14() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    when(arrayNode.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.size()).thenReturn(0);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).isArray();
    verify(arrayNode2).size();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals("1", candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("group-info-externalid-1");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("1", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("group-info-name-1");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("1", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("activiti-idm-candidate-group");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("activiti-idm-candidate-group", getResult6.getName());
    assertEquals("group-info-externalid-1", getResult2.getName());
    assertEquals("group-info-name-1", getResult4.getName());
    List<ExtensionElement> getResult7 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("initiator-can-complete", getResult8.getName());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertNull(getResult6.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult8.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult6.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups15() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups16() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals("${taskAssignmentBean.assignTaskToCandidateGroups('As Text', execution)}", candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("group-info-externalid-As Text");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("As Text", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("group-info-name-As Text");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("As Text", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("activiti-idm-candidate-group");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("activiti-idm-candidate-group", getResult6.getName());
    assertEquals("group-info-externalid-As Text", getResult2.getName());
    assertEquals("group-info-name-As Text", getResult4.getName());
    List<ExtensionElement> getResult7 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("initiator-can-complete", getResult8.getName());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertNull(getResult6.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult8.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult6.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups17() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn(null);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    MissingNode canCompleteTaskNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode).asText();
    assertTrue(task.getCandidateGroups().isEmpty());
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups18() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    ArrayNode canCompleteTaskNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals("${taskAssignmentBean.assignTaskToCandidateGroups('As Text', execution)}", candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("group-info-externalid-As Text");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("As Text", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("group-info-name-As Text");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("As Text", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("activiti-idm-candidate-group");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("activiti-idm-candidate-group", getResult6.getName());
    assertEquals("group-info-externalid-As Text", getResult2.getName());
    assertEquals("group-info-name-As Text", getResult4.getName());
    List<ExtensionElement> getResult7 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("initiator-can-complete", getResult8.getName());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertNull(getResult6.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult8.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult6.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups19() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, null, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals("${taskAssignmentBean.assignTaskToCandidateGroups('As Text', execution)}", candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("group-info-externalid-As Text");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("As Text", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("group-info-name-As Text");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("As Text", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("activiti-idm-candidate-group");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("activiti-idm-candidate-group", getResult6.getName());
    assertEquals("group-info-externalid-As Text", getResult2.getName());
    assertEquals("group-info-name-As Text", getResult4.getName());
    List<ExtensionElement> getResult7 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("initiator-can-complete", getResult8.getName());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertNull(getResult6.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult8.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult6.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups20() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(true);
    when(canCompleteTaskNode.asText()).thenReturn("As Text");
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    List<String> candidateGroups = task.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals("${taskAssignmentBean.assignTaskToCandidateGroups('As Text', execution)}", candidateGroups.get(0));
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(4, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("group-info-externalid-As Text");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("As Text", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("group-info-name-As Text");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("As Text", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("activiti-idm-candidate-group");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("activiti-idm-candidate-group", getResult6.getName());
    assertEquals("group-info-externalid-As Text", getResult2.getName());
    assertEquals("group-info-name-As Text", getResult4.getName());
    List<ExtensionElement> getResult7 = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult7.size());
    ExtensionElement getResult8 = getResult7.get(0);
    assertEquals("initiator-can-complete", getResult8.getName());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult8.getNamespacePrefix());
    assertNull(getResult6.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult8.getId());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult8.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult8.getXmlRowNumber());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult8.getAttributes().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult8.getExtensionElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult8.getChildElements().isEmpty());
    String expectedElementText = Boolean.FALSE.toString();
    assertEquals(expectedElementText, getResult8.getElementText());
    String expectedElementText2 = Boolean.TRUE.toString();
    assertEquals(expectedElementText2, getResult6.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult8.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#fillCandidateGroups(JsonNode, JsonNode, UserTask)}
   */
  @Test
  void testFillCandidateGroups21() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode idmDefNode = mock(ArrayNode.class);
    when(idmDefNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    ArrayNode canCompleteTaskNode = mock(ArrayNode.class);
    when(canCompleteTaskNode.isNull()).thenReturn(true);
    when(canCompleteTaskNode.asText()).thenReturn("As Text");
    UserTask task = mock(UserTask.class);
    doNothing().when(task).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(task).setCandidateGroups(Mockito.<List<String>>any());

    // Act
    userTaskJsonConverter.fillCandidateGroups(idmDefNode, canCompleteTaskNode, task);

    // Assert
    verify(arrayNode2).isNull();
    verify(canCompleteTaskNode).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(idmDefNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).isArray();
    verify(arrayNode3).size();
    verify(arrayNode, atLeast(1)).asText();
    verify(task, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(task).setCandidateGroups(isA(List.class));
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#addInitiatorCanCompleteExtensionElement(boolean, UserTask)}
   */
  @Test
  void testAddInitiatorCanCompleteExtensionElement() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addInitiatorCanCompleteExtensionElement(true, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("initiator-can-complete");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("initiator-can-complete", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    String expectedElementText = Boolean.TRUE.toString();
    assertEquals(expectedElementText, getResult2.getElementText());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)}
   */
  @Test
  void testAddExtensionElement() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)}
   */
  @Test
  void testAddExtensionElement2() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", (JsonNode) null, task);

    // Assert that nothing has changed
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)}
   */
  @Test
  void testAddExtensionElement3() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    ArrayNode elementNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)}
   */
  @Test
  void testAddExtensionElement4() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    BigIntegerNode elementNode = new BigIntegerNode(BigInteger.valueOf(1L));
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", elementNode, task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("Name");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("1", getResult2.getElementText());
    assertEquals("Name", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#addExtensionElement(String, JsonNode, UserTask)}
   */
  @Test
  void testAddExtensionElement5() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    NullNode elementNode = NullNode.getInstance();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#addExtensionElement(String, String, UserTask)}
   */
  @Test
  void testAddExtensionElement6() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("Name", "Element Text", task);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = task.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("Name");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("Element Text", getResult2.getElementText());
    assertEquals("Name", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Method under test:
   * {@link UserTaskJsonConverter#addExtensionElement(String, String, UserTask)}
   */
  @Test
  void testAddExtensionElement7() {
    // Arrange
    UserTaskJsonConverter userTaskJsonConverter = new UserTaskJsonConverter();
    UserTask task = new UserTask();

    // Act
    userTaskJsonConverter.addExtensionElement("", "Element Text", task);

    // Assert
    assertTrue(task.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link UserTaskJsonConverter}
   */
  @Test
  void testNewUserTaskJsonConverter() throws MissingResourceException {
    // Arrange and Act
    UserTaskJsonConverter actualUserTaskJsonConverter = new UserTaskJsonConverter();

    // Assert
    ObjectMapper objectMapper = actualUserTaskJsonConverter.objectMapper;
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertTrue(serializationConfig.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    ContextAttributes attributes = deserializationConfig.getAttributes();
    assertTrue(attributes instanceof ContextAttributes.Impl);
    CacheProvider cacheProvider = deserializationConfig.getCacheProvider();
    assertTrue(cacheProvider instanceof DefaultCacheProvider);
    DeserializationContext deserializationContext = objectMapper.getDeserializationContext();
    DeserializerFactory factory2 = deserializationContext.getFactory();
    assertTrue(factory2 instanceof BeanDeserializerFactory);
    assertTrue(deserializationContext instanceof DefaultDeserializationContext.Impl);
    ClassIntrospector classIntrospector = deserializationConfig.getClassIntrospector();
    assertTrue(classIntrospector instanceof BasicClassIntrospector);
    AccessorNamingStrategy.Provider accessorNaming = deserializationConfig.getAccessorNaming();
    assertTrue(accessorNaming instanceof DefaultAccessorNamingStrategy.Provider);
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    VisibilityChecker<?> visibilityChecker = objectMapper.getVisibilityChecker();
    assertTrue(visibilityChecker instanceof VisibilityChecker.Std);
    PolymorphicTypeValidator polymorphicTypeValidator = objectMapper.getPolymorphicTypeValidator();
    assertTrue(polymorphicTypeValidator instanceof LaissezFaireSubTypeValidator);
    SubtypeResolver subtypeResolver = objectMapper.getSubtypeResolver();
    assertTrue(subtypeResolver instanceof StdSubtypeResolver);
    SerializerFactory serializerFactory = objectMapper.getSerializerFactory();
    assertTrue(serializerFactory instanceof BeanSerializerFactory);
    SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
    assertTrue(serializerProvider instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    JsonSerializer<Object> defaultNullKeySerializer = serializerProvider.getDefaultNullKeySerializer();
    assertTrue(defaultNullKeySerializer instanceof FailingSerializer);
    JsonSerializer<Object> defaultNullValueSerializer = serializerProvider.getDefaultNullValueSerializer();
    assertTrue(defaultNullValueSerializer instanceof NullSerializer);
    DeserializerFactoryConfig factoryConfig = ((BeanDeserializerFactory) factory2).getFactoryConfig();
    Iterable<Deserializers> deserializersResult = factoryConfig.deserializers();
    assertTrue(deserializersResult instanceof ArrayIterator);
    SerializerFactoryConfig factoryConfig2 = ((BeanSerializerFactory) serializerFactory).getFactoryConfig();
    Iterable<Serializers> serializersResult = factoryConfig2.serializers();
    assertTrue(serializersResult instanceof ArrayIterator);
    DateFormat dateFormat = objectMapper.getDateFormat();
    assertTrue(dateFormat instanceof StdDateFormat);
    assertEquals(" ", factory.getRootValueSeparator());
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getISO3Country());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    TimeZone timeZone = deserializationConfig.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("English", locale.getDisplayName());
    assertEquals("JSON", factory.getFormatName());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
    assertEquals("[one of: 'yyyy-MM-dd'T'HH:mm:ss.SSSX', 'EEE, dd MMM yyyy HH:mm:ss zzz' (lenient)]",
        ((StdDateFormat) dateFormat).toPattern());
    Version versionResult = factory.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    Version versionResult2 = objectMapper.version();
    assertEquals("com.fasterxml.jackson.core", versionResult2.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-core/2.17.2", versionResult.toFullString());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult2.toFullString());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("jackson-core", versionResult.getArtifactId());
    assertEquals("jackson-databind", versionResult2.getArtifactId());
    assertEquals('=', base64Variant.getPaddingChar());
    assertNull(serializerProvider.getGenerator());
    assertNull(serializerProviderInstance.getGenerator());
    assertNull(deserializationContext.getParser());
    assertNull(factory.getCharacterEscapes());
    assertNull(factory.getInputDecorator());
    assertNull(factory.getOutputDecorator());
    assertNull(deserializationContext.getConfig());
    assertNull(objectMapper.getInjectableValues());
    assertNull(deserializationContext.getContextualType());
    assertNull(defaultNullKeySerializer.getDelegatee());
    assertNull(defaultNullValueSerializer.getDelegatee());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(serializationConfig.getFullRootName());
    assertNull(objectMapper.getPropertyNamingStrategy());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(serializationConfig.getPropertyNamingStrategy());
    assertNull(serializerProvider.getConfig());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getHandlerInstantiator());
    assertNull(actualUserTaskJsonConverter.shapesArrayNode);
    assertNull(actualUserTaskJsonConverter.flowElementNode);
    assertNull(serializationConfig.getFilterProvider());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(serializationConfig.getDefaultMergeable());
    assertNull(factory.getFormatReadFeatureType());
    assertNull(factory.getFormatWriteFeatureType());
    JsonInclude.Value defaultPropertyInclusion = deserializationConfig.getDefaultPropertyInclusion();
    assertNull(defaultPropertyInclusion.getContentFilter());
    assertNull(defaultPropertyInclusion.getValueFilter());
    assertNull(deserializationContext.getActiveView());
    assertNull(serializerProvider.getActiveView());
    assertNull(serializerProviderInstance.getActiveView());
    assertNull(deserializationConfig.getActiveView());
    assertNull(serializationConfig.getActiveView());
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    assertNull(typeFactory.getClassLoader());
    assertNull(deserializationConfig.getRootName());
    assertNull(serializationConfig.getRootName());
    assertNull(dateFormat.getNumberFormat());
    assertNull(dateFormat.getCalendar());
    assertNull(actualUserTaskJsonConverter.formMap);
    assertNull(actualUserTaskJsonConverter.formKeyMap);
    assertNull(dateFormat.getTimeZone());
    assertNull(actualUserTaskJsonConverter.model);
    assertNull(actualUserTaskJsonConverter.processor);
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0.0d, actualUserTaskJsonConverter.subProcessX);
    assertEquals(0.0d, actualUserTaskJsonConverter.subProcessY);
    assertEquals(1, factory.getParserFeatures());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(17, versionResult2.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult2.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, versionResult2.getPatchLevel());
    assertEquals(2079, factory.getGeneratorFeatures());
    assertEquals(21771068, serializationConfig.getSerializationFeatures());
    assertEquals(31, factory.getFactoryFeatures());
    assertEquals(473998480, deserializationConfig.getDeserializationFeatures());
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
    assertEquals(9999, nodeFactory.getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
    JsonSetter.Value defaultSetterInfo = deserializationConfig.getDefaultSetterInfo();
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getContentNulls());
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getValueNulls());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult2.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult2.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(versionResult2.isUnknownVersion());
    assertFalse(defaultNullKeySerializer.isUnwrappingSerializer());
    assertFalse(defaultNullValueSerializer.isUnwrappingSerializer());
    assertFalse(factoryConfig.hasAbstractTypeResolvers());
    assertFalse(factoryConfig.hasDeserializerModifiers());
    assertFalse(factoryConfig.hasDeserializers());
    assertFalse(factoryConfig.hasValueInstantiators());
    assertFalse(deserializationConfig.hasExplicitTimeZone());
    assertFalse(serializationConfig.hasExplicitTimeZone());
    assertFalse(factoryConfig2.hasKeySerializers());
    assertFalse(factoryConfig2.hasSerializerModifiers());
    assertFalse(factoryConfig2.hasSerializers());
    assertFalse(((ArrayIterator<Deserializers>) deserializersResult).hasNext());
    assertFalse(((ArrayIterator<Serializers>) serializersResult).hasNext());
    assertFalse(locale.hasExtensions());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(serializationConfig.isAnnotationProcessingEnabled());
    assertTrue(((StdDateFormat) dateFormat).isColonIncludedInTimeZone());
    assertTrue(dateFormat.isLenient());
    Set<Object> registeredModuleIds = objectMapper.getRegisteredModuleIds();
    assertTrue(registeredModuleIds.isEmpty());
    assertEquals(Integer.MAX_VALUE, base64Variant.getMaxLineLength());
    assertEquals('=', base64Variant.getPaddingByte());
    assertSame(nodeFactory, deserializationConfig.getNodeFactory());
    assertSame(registeredModuleIds, locale.getExtensionKeys());
    assertSame(registeredModuleIds, locale.getUnicodeLocaleAttributes());
    assertSame(registeredModuleIds, locale.getUnicodeLocaleKeys());
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    assertSame(typeFactory, serializerProviderInstance.getTypeFactory());
    assertSame(typeFactory, deserializationConfig.getTypeFactory());
    assertSame(typeFactory, serializationConfig.getTypeFactory());
    assertSame(versionResult2, annotationIntrospector.version());
    assertSame(base64Variant, serializationConfig.getBase64Variant());
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    assertSame(timeZone, serializerProviderInstance.getTimeZone());
    assertSame(timeZone, serializationConfig.getTimeZone());
    assertSame(defaultPropertyInclusion, serializationConfig.getDefaultPropertyInclusion());
    assertSame(defaultSetterInfo, serializationConfig.getDefaultSetterInfo());
    ObjectMapper expectedCodec = actualUserTaskJsonConverter.objectMapper;
    assertSame(expectedCodec, factory.getCodec());
    assertSame(factory, objectMapper.getJsonFactory());
    assertSame(attributes, serializationConfig.getAttributes());
    assertSame(cacheProvider, serializationConfig.getCacheProvider());
    assertSame(classIntrospector, serializationConfig.getClassIntrospector());
    assertSame(accessorNaming, serializationConfig.getAccessorNaming());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
    assertSame(visibilityChecker, deserializationConfig.getDefaultVisibilityChecker());
    assertSame(visibilityChecker, serializationConfig.getDefaultVisibilityChecker());
    assertSame(polymorphicTypeValidator, deserializationConfig.getPolymorphicTypeValidator());
    assertSame(polymorphicTypeValidator, serializationConfig.getPolymorphicTypeValidator());
    assertSame(subtypeResolver, deserializationConfig.getSubtypeResolver());
    assertSame(subtypeResolver, serializationConfig.getSubtypeResolver());
    assertSame(defaultNullKeySerializer, serializerProviderInstance.getDefaultNullKeySerializer());
    assertSame(defaultNullValueSerializer, serializerProviderInstance.getDefaultNullValueSerializer());
    assertSame(dateFormat, deserializationConfig.getDateFormat());
    assertSame(dateFormat, serializationConfig.getDateFormat());
  }
}
