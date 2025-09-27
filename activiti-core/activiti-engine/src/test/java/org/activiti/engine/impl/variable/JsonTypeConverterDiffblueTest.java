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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.DoubleNode;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JsonTypeConverterDiffblueTest {
  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   *
   * <p>Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(objectMapper, "not blank");
    DoubleNode jsonValue = DoubleNode.valueOf(10.0d);

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

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertSame(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   *
   * <p>Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue2() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(objectMapper, " ");
    DoubleNode jsonValue = DoubleNode.valueOf(10.0d);

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
    valueFields.setTextValue2(null);

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertSame(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   *
   * <p>Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue3() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(objectMapper, null);
    DoubleNode jsonValue = DoubleNode.valueOf(10.0d);

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
    valueFields.setTextValue2(null);

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertSame(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   *
   * <ul>
   *   <li>Given {@code DoubleNode}.
   * </ul>
   *
   * <p>Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_givenComFasterxmlJacksonDatabindNodeDoubleNode() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(objectMapper, "not blank");
    DoubleNode jsonValue = DoubleNode.valueOf(10.0d);

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
    valueFields.setTextValue2("com.fasterxml.jackson.databind.node.DoubleNode");

    // Act
    Object actualConvertToValueResult = jsonTypeConverter.convertToValue(jsonValue, valueFields);

    // Assert
    assertSame(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   *
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenHistoricDetailVariableInstanceUpdateEntityImpl() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper, "Java Class Field For Jackson");
    DoubleNode jsonValue = DoubleNode.valueOf(10.0d);

    // Act
    Object actualConvertToValueResult =
        jsonTypeConverter.convertToValue(
            jsonValue, new HistoricDetailVariableInstanceUpdateEntityImpl());

    // Assert
    assertSame(jsonValue, actualConvertToValueResult);
  }

  /**
   * Test {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonTypeConverter#convertToValue(JsonNode, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonTypeConverter.convertToValue(JsonNode, ValueFields)"})
  public void testConvertToValue_whenNull_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(objectMapper, "not blank");

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
    valueFields.setTextValue2(null);

    // Act and Assert
    assertNull(jsonTypeConverter.convertToValue(null, valueFields));
  }
}
