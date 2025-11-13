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

import static org.junit.Assert.assertArrayEquals;
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
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.github.dockerjava.api.command.CreateConfigResponse;
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
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link LongJsonType#LongJsonType(int, ObjectMapper, boolean, JsonTypeConverter)}
   *   <li>{@link LongJsonType#getTypeName()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void LongJsonType.<init>(int, ObjectMapper, boolean, JsonTypeConverter)",
    "java.lang.String LongJsonType.getTypeName()"
  })
  public void testGettersAndSetters() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    // Act
    LongJsonType actualLongJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Assert
    assertEquals(LongJsonType.LONG_JSON, actualLongJsonType.getTypeName());
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, false, jsonTypeConverter);

    // Act and Assert
    assertFalse(longJsonType.isAbleToStore(new CreateConfigResponse()));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore2() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(1, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertTrue(longJsonType.isAbleToStore(new CreateConfigResponse()));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore3() {
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

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(longJsonType.isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore4() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(longJsonType.isAbleToStore(42));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
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

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(longJsonType.isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.
   *   <li>When forty-two.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenBuilderDefaultLeniencyTrue_whenFortyTwo_thenReturnFalse() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(longJsonType.isAbleToStore(42));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>Given {@code JsonNode}.
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
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

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(longJsonType.isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_when42_thenReturnTrue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertTrue(longJsonType.isAbleToStore("42"));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link CreateConfigResponse} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenCreateConfigResponse_thenReturnTrue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertTrue(longJsonType.isAbleToStore(new CreateConfigResponse()));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenFortyTwo_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(longJsonType.isAbleToStore(42));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, false, jsonTypeConverter);

    // Act and Assert
    assertTrue(longJsonType.isAbleToStore(null));
  }

  /**
   * Test {@link LongJsonType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LongJsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertFalse(longJsonType.isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LongJsonType#serialize(Object, ValueFields)}.
   *
   * <p>Method under test: {@link LongJsonType#serialize(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] LongJsonType.serialize(Object, ValueFields)"})
  public void testSerialize() throws UnsupportedEncodingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);
    CreateConfigResponse createConfigResponse = new CreateConfigResponse();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    byte[] actualSerializeResult = longJsonType.serialize(createConfigResponse, valueFields);

    // Assert
    assertEquals(
        "com.github.dockerjava.api.command.CreateConfigResponse", valueFields.getTextValue2());
    assertArrayEquals("{\"ID\":null}".getBytes("UTF-8"), actualSerializeResult);
  }

  /**
   * Test {@link LongJsonType#serialize(Object, ValueFields)}.
   *
   * <p>Method under test: {@link LongJsonType#serialize(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] LongJsonType.serialize(Object, ValueFields)"})
  public void testSerialize2() throws UnsupportedEncodingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);
    CreateConfigResponse createConfigResponse = new CreateConfigResponse();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    byte[] actualSerializeResult = longJsonType.serialize(createConfigResponse, valueFields);

    // Assert
    assertEquals(
        "com.github.dockerjava.api.command.CreateConfigResponse", valueFields.getTextValue2());
    assertArrayEquals("{\"ID\":null}".getBytes("UTF-8"), actualSerializeResult);
  }

  /**
   * Test {@link LongJsonType#serialize(Object, ValueFields)}.
   *
   * <p>Method under test: {@link LongJsonType#serialize(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] LongJsonType.serialize(Object, ValueFields)"})
  public void testSerialize3() {
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

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    byte[] actualSerializeResult = longJsonType.serialize(42, valueFields);

    // Assert
    assertEquals("java.lang.Integer", valueFields.getTextValue2());
    assertArrayEquals(new byte[] {'4', '2'}, actualSerializeResult);
  }

  /**
   * Test {@link LongJsonType#serialize(Object, ValueFields)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#serialize(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] LongJsonType.serialize(Object, ValueFields)"})
  public void testSerialize_whenNull_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act and Assert
    assertNull(longJsonType.serialize(null, valueFields));
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(objectMapper2, "");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act
    Object actualDeserializeResult =
        longJsonType.deserialize(
            new byte[] {}, new HistoricDetailVariableInstanceUpdateEntityImpl());

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
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize2() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, null);

    // Act and Assert
    assertNull(
        longJsonType.deserialize(
            new byte[] {}, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize3() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, null);

    // Act and Assert
    assertNull(
        longJsonType.deserialize(
            new byte[] {0, 'X', 'A', 'X', 'A', 'X', 'A', 'X'},
            new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   *
   * <ul>
   *   <li>Given {@code MissingNode}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_givenComFasterxmlJacksonDatabindNodeMissingNode() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLastUpdatedTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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
    Object actualDeserializeResult = longJsonType.deserialize(new byte[] {}, valueFields);

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
   *
   * <ul>
   *   <li>Given {@code Detail Type}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_givenDetailType() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

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
    valueFields.setTextValue("42");
    valueFields.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue2("42");

    // Act and Assert
    assertNull(longJsonType.deserialize(null, valueFields));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   *
   * <ul>
   *   <li>Given {@code Detail Type}.
   *   <li>When array of {@code byte} with {@code A} and {@code X}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_givenDetailType_whenArrayOfByteWithAAndX_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

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
    valueFields.setTextValue("42");
    valueFields.setTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue2("42");

    // Act and Assert
    assertNull(
        longJsonType.deserialize(new byte[] {'A', 'X', 'A', 'X', 'A', 'X', 'A', 'X'}, valueFields));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   *
   * <ul>
   *   <li>When array of {@code byte} with {@code A} and minus one.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenArrayOfByteWithAAndMinusOne_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertNull(
        longJsonType.deserialize(
            new byte[] {'A', -1, 'A', 'X', 'A', 'X', 'A', 'X'},
            new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   *
   * <ul>
   *   <li>When array of {@code byte} with {@link Byte#MAX_VALUE} and {@code X}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenArrayOfByteWithMax_valueAndX_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertNull(
        longJsonType.deserialize(
            new byte[] {Byte.MAX_VALUE, 'X', 'A', 'X', 'A', 'X', 'A', 'X'},
            new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   *
   * <ul>
   *   <li>When array of {@code byte} with zero and {@code X}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenArrayOfByteWithZeroAndX_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act and Assert
    assertNull(
        longJsonType.deserialize(
            new byte[] {0, 'X', 'A', 'X', 'A', 'X', 'A', 'X'},
            new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   *
   * <ul>
   *   <li>When {@code AXAXAXAX} Bytes is {@code UTF-8}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenAxaxaxaxBytesIsUtf8_thenReturnNull()
      throws UnsupportedEncodingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);
    byte[] bytes = "AXAXAXAX".getBytes("UTF-8");

    // Act and Assert
    assertNull(
        longJsonType.deserialize(bytes, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongJsonType#deserialize(byte[], ValueFields)}.
   *
   * <ul>
   *   <li>When empty array of {@code byte}.
   *   <li>Then return {@link MissingNode}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenEmptyArrayOfByte_thenReturnMissingNode() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    // Act
    Object actualDeserializeResult =
        longJsonType.deserialize(
            new byte[] {}, new HistoricDetailVariableInstanceUpdateEntityImpl());

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
   *
   * <ul>
   *   <li>When {@link HistoricVariableInstanceEntityImpl} (default constructor) TextValue2 is
   *       {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LongJsonType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_whenHistoricVariableInstanceEntityImplTextValue2Is42() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLastUpdatedTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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
    Object actualDeserializeResult = longJsonType.deserialize(new byte[] {}, valueFields);

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
