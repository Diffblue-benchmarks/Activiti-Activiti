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
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JsonTypeDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link JsonType#JsonType(int, ObjectMapper, boolean, JsonTypeConverter)}
   *   <li>{@link JsonType#getTypeName()}
   *   <li>{@link JsonType#isCachable()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonType.<init>(int, ObjectMapper, boolean, JsonTypeConverter)",
    "String JsonType.getTypeName()",
    "boolean JsonType.isCachable()"
  })
  public void testGettersAndSetters() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    // Act
    JsonType actualJsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    String actualTypeName = actualJsonType.getTypeName();

    // Assert
    assertTrue(actualJsonType.isCachable());
    assertEquals(JsonType.JSON, actualTypeName);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   *
   * <p>Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(objectMapper2, null);

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    valueFields.setActivityInstanceId("42");
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setDeleted(true);
    valueFields.setDetailType("Detail Type");
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue("42");
    valueFields.setTextValue2(null);

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    assertTrue(actualValue instanceof IntNode);
    assertTrue(((IntNode) actualValue).traverse() instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   *
   * <p>Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue2() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true, null);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    valueFields.setActivityInstanceId("42");
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setDeleted(true);
    valueFields.setDetailType("Detail Type");
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue("42");
    valueFields.setTextValue2(null);

    // Act and Assert
    assertNull(jsonType.getValue(valueFields));
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Given {@code IntNode}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_givenComFasterxmlJacksonDatabindNodeIntNode() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    valueFields.setActivityInstanceId("42");
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setDeleted(true);
    valueFields.setDetailType("Detail Type");
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.IntNode");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    assertTrue(actualValue instanceof IntNode);
    assertTrue(((IntNode) actualValue).traverse() instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_givenEmptyString() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    valueFields.setActivityInstanceId("42");
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setDeleted(true);
    valueFields.setDetailType("Detail Type");
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue("");
    valueFields.setTextValue2(null);

    // Act and Assert
    assertNull(jsonType.getValue(valueFields));
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then return {@link IntNode}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_givenNull_thenReturnIntNode() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    valueFields.setActivityInstanceId("42");
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setDeleted(true);
    valueFields.setDetailType("Detail Type");
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue("42");
    valueFields.setTextValue2(null);

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    assertTrue(actualValue instanceof IntNode);
    assertTrue(((IntNode) actualValue).traverse() instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Given {@code Text Value}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_givenTextValue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    valueFields.setActivityInstanceId("42");
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setDeleted(true);
    valueFields.setDetailType("Detail Type");
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue("Text Value");
    valueFields.setTextValue2(null);

    // Act and Assert
    assertNull(jsonType.getValue(valueFields));
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor)
   *       TextValue2 is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImplTextValue2Is42() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();
    valueFields.setActivityInstanceId("42");
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setDeleted(true);
    valueFields.setDetailType("Detail Type");
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue("42");
    valueFields.setTextValue2("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    assertTrue(actualValue instanceof IntNode);
    assertTrue(((IntNode) actualValue).traverse() instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertNull(jsonType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   *
   * <p>Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   *
   * <p>Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue2() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(
        new DefaultTypeResolverBuilder(DefaultTyping.OBJECT_AND_NON_CONCRETE));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   *
   * <p>Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue3() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(
        new DefaultTypeResolverBuilder(DefaultTyping.NON_CONCRETE_AND_ARRAYS));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   *
   * <p>Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue4() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.NON_FINAL));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_givenBuilderAddMixInObjectAndObject() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Given builder annotationIntrospector {@link JacksonAnnotationIntrospector} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_givenBuilderAnnotationIntrospectorJacksonAnnotationIntrospector() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.annotationIntrospector(new JacksonAnnotationIntrospector());
    Class<Object> target = Object.class;
    Class<JsonNode> mixinSource = JsonNode.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Given {@code JsonNode}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_givenComFasterxmlJacksonDatabindJsonNode() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<JsonNode> mixinSource = JsonNode.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor)
   *       TextValue is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIs42() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(42, valueFields);

    // Assert
    assertEquals("42", valueFields.getTextValue());
    assertEquals("java.lang.Integer", valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor)
   *       TextValue is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIsNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_whenNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(null, valueFields);

    // Assert
    assertEquals("null", valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   *
   * <p>Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, false, jsonTypeConverter);

    // Act and Assert
    assertFalse(jsonType.isAbleToStore(42));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   *
   * <p>Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore2() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<JsonNode> mixinSource = JsonNode.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(1, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(jsonType.isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   *
   * <p>Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore3() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertTrue(jsonType.isAbleToStore(42));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenBuilderAddMixInObjectAndObject_whenNull_thenReturnFalse() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(jsonType.isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.
   *   <li>When forty-two.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenBuilderDefaultLeniencyTrue_whenFortyTwo_thenReturnTrue() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertTrue(jsonType.isAbleToStore(42));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>Given {@code JsonNode}.
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenComFasterxmlJacksonDatabindJsonNode_whenNull() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<JsonNode> mixinSource = JsonNode.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(jsonType.isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_when42_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(jsonType.isAbleToStore("42"));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenFortyTwo_thenReturnTrue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertTrue(jsonType.isAbleToStore(42));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, false, jsonTypeConverter);

    // Act and Assert
    assertTrue(jsonType.isAbleToStore(null));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    JsonType jsonType = new JsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(jsonType.isAbleToStore(JSONObject.NULL));
  }
}
