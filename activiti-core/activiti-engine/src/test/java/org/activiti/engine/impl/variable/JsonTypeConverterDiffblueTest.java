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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.FloatNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JsonTypeConverterDiffblueTest {
  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), null);
    MissingNode jsonValue = MissingNode.getInstance();

    // Act and Assert
    assertSame(jsonValue,
        jsonTypeConverter.convertToValue(jsonValue, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>Then return {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_thenReturnArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    ArrayNode jsonValue = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertTrue(actualConvertToValueResult instanceof ArrayNode);
    assertEquals(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then return {@link BinaryNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenA_thenReturnBinaryNode() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    BinaryNode jsonValue = new BinaryNode(new byte[]{'A', 1, 'A', 1, 'A', 1, 'A', 1});

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertTrue(actualConvertToValueResult instanceof BinaryNode);
    assertEquals(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then return {@link DecimalNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenBigDecimalWith23_thenReturnDecimalNode() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    DecimalNode jsonValue = new DecimalNode(new BigDecimal("2.3"));

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertTrue(actualConvertToValueResult instanceof DecimalNode);
    assertEquals(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   *   <li>Then return {@link BigIntegerNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenBigIntegerNodeWithVIsValueOfOne_thenReturnBigIntegerNode() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    BigIntegerNode jsonValue = new BigIntegerNode(BigInteger.valueOf(1L));

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertTrue(actualConvertToValueResult instanceof BigIntegerNode);
    assertEquals(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When False.</li>
   *   <li>Then return {@link BooleanNode#FALSE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenFalse_thenReturnFalse() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    BooleanNode jsonValue = BooleanNode.getFalse();

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertSame(((BooleanNode) actualConvertToValueResult).FALSE, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenHistoricDetailVariableInstanceUpdateEntityImpl() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    MissingNode jsonValue = MissingNode.getInstance();

    // Act and Assert
    assertSame(jsonValue,
        jsonTypeConverter.convertToValue(jsonValue, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricVariableInstanceEntityImpl} (default constructor) TextValue2 is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenHistoricVariableInstanceEntityImplTextValue2Is42() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    MissingNode jsonValue = MissingNode.getInstance();

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("42");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act and Assert
    assertSame(jsonValue, jsonTypeConverter.convertToValue(jsonValue, valueFields));
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenInstance_thenReturnInstance() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    MissingNode jsonValue = MissingNode.getInstance();

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act and Assert
    assertSame(jsonValue, jsonTypeConverter.convertToValue(jsonValue, valueFields));
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@link NullNode#instance}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenInstance_thenReturnInstance2() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    NullNode jsonValue = NullNode.getInstance();

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertSame(((NullNode) actualConvertToValueResult).instance, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenNull_thenReturnNull() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");

    // Act and Assert
    assertNull(jsonTypeConverter.convertToValue(null, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When True.</li>
   *   <li>Then return {@link BooleanNode#TRUE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenTrue_thenReturnTrue() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    BooleanNode jsonValue = BooleanNode.getTrue();

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertSame(((BooleanNode) actualConvertToValueResult).TRUE, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then return valueOf one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenValueOfOne_thenReturnValueOfOne() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    IntNode jsonValue = IntNode.valueOf(1);

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act and Assert
    assertSame(jsonValue, jsonTypeConverter.convertToValue(jsonValue, valueFields));
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When valueOf ten.</li>
   *   <li>Then return {@link DoubleNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenValueOfTen_thenReturnDoubleNode() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    DoubleNode jsonValue = DoubleNode.valueOf(10.0d);

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertTrue(actualConvertToValueResult instanceof DoubleNode);
    assertEquals(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When valueOf ten.</li>
   *   <li>Then return {@link FloatNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenValueOfTen_thenReturnFloatNode() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(),
        "Java Class Field For Jackson");
    FloatNode jsonValue = FloatNode.valueOf(10.0f);

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.MissingNode");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertTrue(actualConvertToValueResult instanceof FloatNode);
    assertEquals(jsonValue, actualConvertToValueResult);
  }
}
