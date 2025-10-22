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
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LongJsonTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link LongJsonType#LongJsonType(int, ObjectMapper, boolean, JsonTypeConverter)}
   *   <li>{@link LongJsonType#getTypeName()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LongJsonType.<init>(int, ObjectMapper, boolean, JsonTypeConverter)",
      "java.lang.String LongJsonType.getTypeName()"})
  public void testGettersAndSetters() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertEquals(LongJsonType.LONG_JSON,
        (new LongJsonType(3, objectMapper, true,
            new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
            .getTypeName());
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.</li>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenBuilderAddMixInObjectAndObject_whenNull_thenReturnFalse() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertFalse((new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@code JsonNode}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenComFasterxmlJacksonDatabindJsonNode_thenReturnFalse() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<JsonNode> mixinSource = JsonNode.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertFalse((new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertFalse((new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.serializerFactory(mock(SerializerFactory.class));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertTrue((new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(null));
  }

  /**
   * Test {@link LongJsonType#serialize(Object, ValueFields)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#serialize(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"byte[] LongJsonType.serialize(Object, ValueFields)"})
  public void testSerialize_whenNull_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(longJsonType.serialize(null, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), ""));

    // Act
    Object actualDeserializeResult = longJsonType.deserialize(new byte[]{},
        new HistoricDetailVariableInstanceUpdateEntityImpl());

    // Assert
    assertTrue(actualDeserializeResult instanceof MissingNode);
    assertTrue(((MissingNode) actualDeserializeResult).traverse() instanceof TreeTraversingParser);
    assertEquals("", ((MissingNode) actualDeserializeResult).toPrettyString());
    assertEquals(0, ((MissingNode) actualDeserializeResult).size());
    assertEquals(JsonNodeType.MISSING, ((MissingNode) actualDeserializeResult).getNodeType());
    assertFalse(((MissingNode) actualDeserializeResult).isArray());
    assertFalse(((MissingNode) actualDeserializeResult).isBigDecimal());
    assertFalse(((MissingNode) actualDeserializeResult).isBigInteger());
    assertFalse(((MissingNode) actualDeserializeResult).isBinary());
    assertFalse(((MissingNode) actualDeserializeResult).isBoolean());
    assertFalse(((MissingNode) actualDeserializeResult).isContainerNode());
    assertFalse(((MissingNode) actualDeserializeResult).isDouble());
    assertFalse(((MissingNode) actualDeserializeResult).isFloat());
    assertFalse(((MissingNode) actualDeserializeResult).isFloatingPointNumber());
    assertFalse(((MissingNode) actualDeserializeResult).isInt());
    assertFalse(((MissingNode) actualDeserializeResult).isIntegralNumber());
    assertFalse(((MissingNode) actualDeserializeResult).isLong());
    assertFalse(((MissingNode) actualDeserializeResult).isNull());
    assertFalse(((MissingNode) actualDeserializeResult).isNumber());
    assertFalse(((MissingNode) actualDeserializeResult).isObject());
    assertFalse(((MissingNode) actualDeserializeResult).isPojo());
    assertFalse(((MissingNode) actualDeserializeResult).isShort());
    assertFalse(((MissingNode) actualDeserializeResult).isTextual());
    assertFalse(((MissingNode) actualDeserializeResult).isValueNode());
    assertFalse(((MissingNode) actualDeserializeResult).iterator().hasNext());
    assertTrue(((MissingNode) actualDeserializeResult).isMissingNode());
    assertTrue(((MissingNode) actualDeserializeResult).isEmpty());
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize2() {
    // Arrange
    LongJsonType longJsonType = new LongJsonType(3, JsonMapper.builder().findAndAddModules().build(), true, null);

    // Act and Assert
    assertNull(longJsonType.deserialize(new byte[]{}, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_givenNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

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

    // Act
    Object actualDeserializeResult = longJsonType.deserialize(new byte[]{}, valueFields);

    // Assert
    assertTrue(actualDeserializeResult instanceof MissingNode);
    assertTrue(((MissingNode) actualDeserializeResult).traverse() instanceof TreeTraversingParser);
    assertEquals("", ((MissingNode) actualDeserializeResult).toPrettyString());
    assertEquals(0, ((MissingNode) actualDeserializeResult).size());
    assertEquals(JsonNodeType.MISSING, ((MissingNode) actualDeserializeResult).getNodeType());
    assertFalse(((MissingNode) actualDeserializeResult).isArray());
    assertFalse(((MissingNode) actualDeserializeResult).isBigDecimal());
    assertFalse(((MissingNode) actualDeserializeResult).isBigInteger());
    assertFalse(((MissingNode) actualDeserializeResult).isBinary());
    assertFalse(((MissingNode) actualDeserializeResult).isBoolean());
    assertFalse(((MissingNode) actualDeserializeResult).isContainerNode());
    assertFalse(((MissingNode) actualDeserializeResult).isDouble());
    assertFalse(((MissingNode) actualDeserializeResult).isFloat());
    assertFalse(((MissingNode) actualDeserializeResult).isFloatingPointNumber());
    assertFalse(((MissingNode) actualDeserializeResult).isInt());
    assertFalse(((MissingNode) actualDeserializeResult).isIntegralNumber());
    assertFalse(((MissingNode) actualDeserializeResult).isLong());
    assertFalse(((MissingNode) actualDeserializeResult).isNull());
    assertFalse(((MissingNode) actualDeserializeResult).isNumber());
    assertFalse(((MissingNode) actualDeserializeResult).isObject());
    assertFalse(((MissingNode) actualDeserializeResult).isPojo());
    assertFalse(((MissingNode) actualDeserializeResult).isShort());
    assertFalse(((MissingNode) actualDeserializeResult).isTextual());
    assertFalse(((MissingNode) actualDeserializeResult).isValueNode());
    assertFalse(((MissingNode) actualDeserializeResult).iterator().hasNext());
    assertTrue(((MissingNode) actualDeserializeResult).isMissingNode());
    assertTrue(((MissingNode) actualDeserializeResult).isEmpty());
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <ul>
   *   <li>When array of {@code byte} with {@code A} and minus one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenArrayOfByteWithAAndMinusOne_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(longJsonType.deserialize(new byte[]{'A', -1, 'A', 'X', 'A', 'X', 'A', 'X'},
        new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <ul>
   *   <li>When array of {@code byte} with {@link Byte#MAX_VALUE} and {@code X}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenArrayOfByteWithMax_valueAndX_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(longJsonType.deserialize(new byte[]{Byte.MAX_VALUE, 'X', 'A', 'X', 'A', 'X', 'A', 'X'},
        new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <ul>
   *   <li>When array of {@code byte} with zero and {@code X}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenArrayOfByteWithZeroAndX_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(longJsonType.deserialize(new byte[]{0, 'X', 'A', 'X', 'A', 'X', 'A', 'X'},
        new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <ul>
   *   <li>When array of {@code byte} with zero and zero.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenArrayOfByteWithZeroAndZero_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(longJsonType.deserialize(new byte[]{0, 0, 'A', 'X', 'A', 'X', 'A', 'X'},
        new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <ul>
   *   <li>When array of {@code byte} with zero and zero.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenArrayOfByteWithZeroAndZero_thenReturnNull2() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(longJsonType.deserialize(new byte[]{0, 0, 0, 'X', 'A', 'X', 'A', 'X'},
        new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <ul>
   *   <li>When array of {@code byte} with zero and zero.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenArrayOfByteWithZeroAndZero_thenReturnNull3() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(longJsonType.deserialize(new byte[]{0, 0, 'A', 0, 'A', 'X', 'A', 'X'},
        new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <ul>
   *   <li>When {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenAxaxaxaxBytesIsUtf8_thenReturnNull() throws UnsupportedEncodingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));
    byte[] bytes = "AXAXAXAX".getBytes("UTF-8");

    // Act and Assert
    assertNull(longJsonType.deserialize(bytes, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   * <ul>
   *   <li>When empty array of {@code byte}.</li>
   *   <li>Then return {@link MissingNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenEmptyArrayOfByte_thenReturnMissingNode() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act
    Object actualDeserializeResult = longJsonType.deserialize(new byte[]{},
        new HistoricDetailVariableInstanceUpdateEntityImpl());

    // Assert
    assertTrue(actualDeserializeResult instanceof MissingNode);
    assertTrue(((MissingNode) actualDeserializeResult).traverse() instanceof TreeTraversingParser);
    assertEquals("", ((MissingNode) actualDeserializeResult).toPrettyString());
    assertEquals(0, ((MissingNode) actualDeserializeResult).size());
    assertEquals(JsonNodeType.MISSING, ((MissingNode) actualDeserializeResult).getNodeType());
    assertFalse(((MissingNode) actualDeserializeResult).isArray());
    assertFalse(((MissingNode) actualDeserializeResult).isBigDecimal());
    assertFalse(((MissingNode) actualDeserializeResult).isBigInteger());
    assertFalse(((MissingNode) actualDeserializeResult).isBinary());
    assertFalse(((MissingNode) actualDeserializeResult).isBoolean());
    assertFalse(((MissingNode) actualDeserializeResult).isContainerNode());
    assertFalse(((MissingNode) actualDeserializeResult).isDouble());
    assertFalse(((MissingNode) actualDeserializeResult).isFloat());
    assertFalse(((MissingNode) actualDeserializeResult).isFloatingPointNumber());
    assertFalse(((MissingNode) actualDeserializeResult).isInt());
    assertFalse(((MissingNode) actualDeserializeResult).isIntegralNumber());
    assertFalse(((MissingNode) actualDeserializeResult).isLong());
    assertFalse(((MissingNode) actualDeserializeResult).isNull());
    assertFalse(((MissingNode) actualDeserializeResult).isNumber());
    assertFalse(((MissingNode) actualDeserializeResult).isObject());
    assertFalse(((MissingNode) actualDeserializeResult).isPojo());
    assertFalse(((MissingNode) actualDeserializeResult).isShort());
    assertFalse(((MissingNode) actualDeserializeResult).isTextual());
    assertFalse(((MissingNode) actualDeserializeResult).isValueNode());
    assertFalse(((MissingNode) actualDeserializeResult).iterator().hasNext());
    assertTrue(((MissingNode) actualDeserializeResult).isMissingNode());
    assertTrue(((MissingNode) actualDeserializeResult).isEmpty());
  }
}
