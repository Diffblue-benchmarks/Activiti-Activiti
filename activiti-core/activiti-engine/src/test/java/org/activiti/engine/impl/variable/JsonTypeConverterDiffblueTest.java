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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class JsonTypeConverterDiffblueTest {
  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <p>
   * Method under test:
   * {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  public void testConvertToValue() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(new ObjectMapper(), null);
    MissingNode jsonValue = MissingNode.getInstance();

    // Act and Assert
    assertSame(jsonValue,
        jsonTypeConverter.convertToValue(jsonValue, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ValueFields} {@link ValueFields#getTextValue2()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link ValueFields#getName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  public void testConvertToValue_given42_whenValueFieldsGetTextValue2Return42_thenCallsGetName() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson");
    MissingNode jsonValue = MissingNode.getInstance();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue2()).thenReturn("42");

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue2();
    assertSame(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  public void testConvertToValue_givenNull() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson");
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
   *   <li>Then return Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  public void testConvertToValue_thenReturnInstance() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson");
    MissingNode jsonValue = MissingNode.getInstance();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue2()).thenReturn("com.fasterxml.jackson.databind.node.MissingNode");

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getTextValue2();
    assertSame(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>Then return {@link NullNode#instance}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  public void testConvertToValue_thenReturnInstance2() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson");
    NullNode jsonValue = NullNode.getInstance();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue2()).thenReturn("com.fasterxml.jackson.databind.node.MissingNode");

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getTextValue2();
    assertSame(((NullNode) actualConvertToValueResult).instance, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When False.</li>
   *   <li>Then return {@link BooleanNode#FALSE}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  public void testConvertToValue_whenFalse_thenReturnFalse() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson");
    BooleanNode jsonValue = BooleanNode.getFalse();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue2()).thenReturn("com.fasterxml.jackson.databind.node.MissingNode");

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getTextValue2();
    assertSame(((BooleanNode) actualConvertToValueResult).FALSE, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  public void testConvertToValue_whenHistoricDetailVariableInstanceUpdateEntityImpl() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson");
    MissingNode jsonValue = MissingNode.getInstance();

    // Act and Assert
    assertSame(jsonValue,
        jsonTypeConverter.convertToValue(jsonValue, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  public void testConvertToValue_whenNull_thenReturnNull() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson");

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
   * Method under test:
   * {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  public void testConvertToValue_whenTrue_thenReturnTrue() {
    // Arrange
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson");
    BooleanNode jsonValue = BooleanNode.getTrue();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue2()).thenReturn("com.fasterxml.jackson.databind.node.MissingNode");

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getTextValue2();
    assertSame(((BooleanNode) actualConvertToValueResult).TRUE, actualConvertToValueResult);
  }
}
