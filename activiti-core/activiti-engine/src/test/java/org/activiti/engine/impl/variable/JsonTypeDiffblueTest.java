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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.util.LRUMap;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class JsonTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link JsonType#JsonType(int, ObjectMapper, boolean, JsonTypeConverter)}
   *   <li>{@link JsonType#getTypeName()}
   *   <li>{@link JsonType#isCachable()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.<init>(int, ObjectMapper, boolean, JsonTypeConverter)",
      "String JsonType.getTypeName()", "boolean JsonType.isCachable()"})
  public void testGettersAndSetters() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act
    JsonType actualJsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));
    String actualTypeName = actualJsonType.getTypeName();

    // Assert
    assertTrue(actualJsonType.isCachable());
    assertEquals(JsonType.JSON, actualTypeName);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), ""));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getTextValue();
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
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue2() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    builderResult.cacheProvider(cacheProvider);
    JsonType jsonType = new JsonType(3, builderResult.findAndAddModules().build(), true, null);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given builder DefaultTyping is {@link StdTypeResolverBuilder#StdTypeResolverBuilder()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_givenBuilderDefaultTypingIsStdTypeResolverBuilder() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new StdTypeResolverBuilder());
    builderResult.cacheProvider(cacheProvider);
    JsonType jsonType = new JsonType(3, builderResult.findAndAddModules().build(), true, null);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@link CacheProvider} {@link CacheProvider#forDeserializerCache(DeserializationConfig)} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_givenCacheProviderForDeserializerCacheReturnNull() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(null);
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);
    JsonType jsonType = new JsonType(3, builderResult.findAndAddModules().build(), true, null);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link ValueFields} {@link ValueFields#getTextValue()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_givenEmptyString_whenValueFieldsGetTextValueReturnEmptyString() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new StdTypeResolverBuilder());
    builderResult.cacheProvider(cacheProvider);
    JsonType jsonType = new JsonType(3, builderResult.findAndAddModules().build(), true, null);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue()).thenReturn("");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ValueFields} {@link ValueFields#getTextValue()} return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_givenFoo_whenValueFieldsGetTextValueReturnFoo() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new StdTypeResolverBuilder());
    builderResult.cacheProvider(cacheProvider);
    JsonType jsonType = new JsonType(3, builderResult.findAndAddModules().build(), true, null);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("foo");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HistoricVariableInstanceEntityImpl} (default constructor) CachedValue is {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_givenNull_whenHistoricVariableInstanceEntityImplCachedValueIsNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
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
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(jsonType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#getName()} return {@code Name}.</li>
   *   <li>Then calls {@link ValueFields#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JsonType.getValue(ValueFields)"})
  public void testGetValue_whenValueFieldsGetNameReturnName_thenCallsGetName() {
    // Arrange
    JsonType jsonType = new JsonType(3, JsonMapper.builder().findAndAddModules().build(), true, null);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act
    jsonType.setValue(JSONObject.NULL, new HistoricDetailVariableInstanceUpdateEntityImpl());

    // Assert that nothing has changed
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue2() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.OBJECT_AND_NON_CONCRETE));
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act
    jsonType.setValue(JSONObject.NULL, new HistoricDetailVariableInstanceUpdateEntityImpl());

    // Assert that nothing has changed
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue3() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.NON_CONCRETE_AND_ARRAYS));
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act
    jsonType.setValue(JSONObject.NULL, new HistoricDetailVariableInstanceUpdateEntityImpl());

    // Assert that nothing has changed
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue4() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.NON_FINAL));
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act
    jsonType.setValue(JSONObject.NULL, new HistoricDetailVariableInstanceUpdateEntityImpl());

    // Assert that nothing has changed
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue5() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.NON_FINAL_AND_ENUMS));
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));

    // Act
    jsonType.setValue(JSONObject.NULL, new HistoricDetailVariableInstanceUpdateEntityImpl());

    // Assert that nothing has changed
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_givenBuilderDefaultLeniencyTrue() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(42, valueFields);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertEquals("42", valueFields.getTextValue());
    assertEquals("java.lang.Integer", valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Given {@code JsonNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_givenComFasterxmlJacksonDatabindJsonNode() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<JsonNode> mixinSource = JsonNode.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor) TextValue is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIs42() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(42, valueFields);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertEquals("42", valueFields.getTextValue());
    assertEquals("java.lang.Integer", valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor) TextValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIsNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor) TextValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIsNull2() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JsonType.setValue(Object, ValueFields)"})
  public void testSetValue_whenNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(null, valueFields);

    // Assert
    assertEquals("null", valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 1));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(42);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertTrue(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore2() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, false,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(42);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore3() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(42);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertTrue(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.</li>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenBuilderAddMixInObjectAndObject_whenNull_thenReturnFalse() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertFalse((new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.</li>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenBuilderDefaultLeniencyTrue_whenFortyTwo_thenReturnTrue() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(42);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertTrue(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@link CacheProvider} {@link CacheProvider#forSerializerCache(SerializationConfig)} return {@code null}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenCacheProviderForSerializerCacheReturnNull_whenNull() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(null);
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(null);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertTrue(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@code JsonNode}.</li>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenComFasterxmlJacksonDatabindJsonNode_whenNull() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<JsonNode> mixinSource = JsonNode.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertFalse((new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_thenReturnTrue() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(42);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertTrue(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_when42_thenReturnFalse() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(cacheProvider);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore("42");

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean JsonType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertFalse((new JsonType(3, objectMapper, true,
        new JsonTypeConverter(JsonMapper.builder().findAndAddModules().build(), "Java Class Field For Jackson")))
        .isAbleToStore(JSONObject.NULL));
  }
}
