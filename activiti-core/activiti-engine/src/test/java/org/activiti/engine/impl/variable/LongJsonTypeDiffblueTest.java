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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.impl.AsDeductionTypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class LongJsonTypeDiffblueTest {
  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertFalse((new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore2() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act and Assert
    assertFalse((new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore3() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore4() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any())).thenReturn(null);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore5() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    Class<JsonNode> type = JsonNode.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore6() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    Class<LongJsonType> type = LongJsonType.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore7() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new NumberSerializers.FloatSerializer());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore8() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, false,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore9() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(mock(SerializerFactory.class));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act and Assert
    assertTrue((new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(null));
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore10() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore11() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new ToStringSerializer());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertTrue(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore12() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any())).thenReturn(null);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(4, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore13() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any())).thenReturn(null);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(7, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore14() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any())).thenReturn(null);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(8, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore15() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any())).thenReturn(null);
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new FailingSerializer("Msg"));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Method under test: {@link LongJsonType#serialize(Object, ValueFields)}
   */
  @Test
  public void testSerialize() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act and Assert
    assertNull(longJsonType.serialize(null, valueFields));
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Method under test: {@link LongJsonType#serialize(Object, ValueFields)}
   */
  @Test
  public void testSerialize2() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    byte[] actualSerializeResult = longJsonType.serialize(42, valueFields);

    // Assert
    assertEquals("java.lang.Integer", valueFields.getTextValue2());
    assertArrayEquals(new byte[]{'4', '2'}, actualSerializeResult);
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize() throws UnsupportedEncodingException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    byte[] bytes = "AXAXAXAX".getBytes("UTF-8");

    // Act and Assert
    assertNull(longJsonType.deserialize(bytes, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize2() throws UnsupportedEncodingException {
    // Arrange
    LongJsonType longJsonType = new LongJsonType(3, null, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    byte[] bytes = "AXAXAXAX".getBytes("UTF-8");

    // Act and Assert
    assertNull(longJsonType.deserialize(bytes, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize3() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(longJsonType.deserialize(new byte[]{0, 'X', 'A', 'X', 'A', 'X', 'A', 'X'},
        new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize4() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(longJsonType.deserialize(new byte[]{Byte.MAX_VALUE, 'X', 'A', 'X', 'A', 'X', 'A', 'X'},
        new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize5() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(longJsonType.deserialize(new byte[]{'A', -1, 'A', 'X', 'A', 'X', 'A', 'X'},
        new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize6() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));

    // Act and Assert
    assertTrue(longJsonType.deserialize(new byte[]{},
        new HistoricDetailVariableInstanceUpdateEntityImpl()) instanceof MissingNode);
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize7() throws UnsupportedEncodingException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    byte[] bytes = "AXAXAXAX".getBytes("UTF-8");
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");

    // Act
    Object actualDeserializeResult = longJsonType.deserialize(bytes, valueFields);

    // Assert
    verify(valueFields).getName();
    assertNull(actualDeserializeResult);
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize8() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue2()).thenReturn("42");
    when(valueFields.getName()).thenReturn("Name");

    // Act
    Object actualDeserializeResult = longJsonType.deserialize(new byte[]{}, valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue2();
    assertTrue(actualDeserializeResult instanceof MissingNode);
    JsonParser traverseResult = ((MissingNode) actualDeserializeResult).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("", ((MissingNode) actualDeserializeResult).toPrettyString());
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
    assertEquals(0, ((MissingNode) actualDeserializeResult).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.MISSING, ((MissingNode) actualDeserializeResult).getNodeType());
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
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize9() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    Object actualDeserializeResult = (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), ""))).deserialize(new byte[]{}, mock(ValueFields.class));

    // Assert
    assertTrue(actualDeserializeResult instanceof MissingNode);
    JsonParser traverseResult = ((MissingNode) actualDeserializeResult).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("", ((MissingNode) actualDeserializeResult).toPrettyString());
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
    assertEquals(0, ((MissingNode) actualDeserializeResult).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.MISSING, ((MissingNode) actualDeserializeResult).getNodeType());
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
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize10() {
    // Arrange
    LongJsonType longJsonType = new LongJsonType(3, new ObjectMapper(), true, null);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");

    // Act
    Object actualDeserializeResult = longJsonType.deserialize(new byte[]{}, valueFields);

    // Assert
    verify(valueFields).getName();
    assertNull(actualDeserializeResult);
  }

  /**
   * Method under test: {@link LongJsonType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize11() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue2()).thenReturn("com.fasterxml.jackson.databind.node.MissingNode");

    // Act
    Object actualDeserializeResult = longJsonType.deserialize(new byte[]{}, valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getTextValue2();
    assertTrue(actualDeserializeResult instanceof MissingNode);
    JsonParser traverseResult = ((MissingNode) actualDeserializeResult).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("", ((MissingNode) actualDeserializeResult).toPrettyString());
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
    assertEquals(0, ((MissingNode) actualDeserializeResult).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.MISSING, ((MissingNode) actualDeserializeResult).getNodeType());
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
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link LongJsonType#LongJsonType(int, ObjectMapper, boolean, JsonTypeConverter)}
   *   <li>{@link LongJsonType#getTypeName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertEquals(LongJsonType.LONG_JSON, (new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).getTypeName());
  }
}
