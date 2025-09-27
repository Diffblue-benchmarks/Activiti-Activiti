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
package org.activiti.engine.impl.event.logger.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiVariableEvent;
import org.activiti.engine.delegate.event.impl.ActivitiVariableEventImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BooleanType;
import org.activiti.engine.impl.variable.DateType;
import org.activiti.engine.impl.variable.DoubleType;
import org.activiti.engine.impl.variable.IntegerType;
import org.activiti.engine.impl.variable.LongStringType;
import org.activiti.engine.impl.variable.LongType;
import org.activiti.engine.impl.variable.SerializableType;
import org.activiti.engine.impl.variable.ShortType;
import org.activiti.engine.impl.variable.StringType;
import org.activiti.engine.impl.variable.UUIDType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class VariableEventHandlerDiffblueTest {
  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new StringType(3));
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(
        VariableEventHandler.TYPE_STRING, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@link DateType} (default constructor).
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is {@link VariableEventHandler#TYPE_DATE}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenDateType_thenReturnVariable_typeIsType_date() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new DateType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_DATE, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@link DoubleType} (default constructor).
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is {@link VariableEventHandler#TYPE_DOUBLE}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenDoubleType_thenReturnVariable_typeIsType_double() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new DoubleType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(
        VariableEventHandler.TYPE_DOUBLE, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given forty-two.
   *   <li>Then return {@link Fields#VALUE} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenFortyTwo_thenReturnValueIs42() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(null);
    variableEvent.setVariableValue(42);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(3, actualCreateDataResult.size());
    assertEquals("42", actualCreateDataResult.get(Fields.VALUE));
    assertEquals("42", actualCreateDataResult.get(Fields.VALUE_JSON));
    assertEquals(VariableEventHandler.TYPE_JSON, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@link IntegerType} (default constructor).
   *   <li>Then return {@link Fields#VALUE} intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenIntegerType_thenReturnValueIntValueIsFortyTwo() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new IntegerType());
    variableEvent.setVariableValue(42);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(5, actualCreateDataResult.size());
    assertEquals(42, ((Integer) actualCreateDataResult.get(Fields.VALUE)).intValue());
    assertEquals(42, ((Integer) actualCreateDataResult.get(Fields.VALUE_INTEGER)).intValue());
    assertEquals(
        42.0d, ((Double) actualCreateDataResult.get(Fields.VALUE_DOUBLE)).doubleValue(), 0.0);
    assertEquals(42L, ((Long) actualCreateDataResult.get(Fields.VALUE_LONG)).longValue());
    assertEquals(
        VariableEventHandler.TYPE_INTEGER, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@link IntegerType} (default constructor).
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is {@link VariableEventHandler#TYPE_INTEGER}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenIntegerType_thenReturnVariable_typeIsType_integer() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new IntegerType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(
        VariableEventHandler.TYPE_INTEGER, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@link LongStringType#LongStringType(int)} with minLength is three.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenLongStringTypeWithMinLengthIsThree() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new LongStringType(3));
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(
        VariableEventHandler.TYPE_STRING, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@link LongType} (default constructor).
   *   <li>Then return {@link Fields#VALUE} longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenLongType_thenReturnValueLongValueIsFortyTwo() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new LongType());
    variableEvent.setVariableValue(42L);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(4, actualCreateDataResult.size());
    assertEquals(
        42.0d, ((Double) actualCreateDataResult.get(Fields.VALUE_DOUBLE)).doubleValue(), 0.0);
    assertEquals(42L, ((Long) actualCreateDataResult.get(Fields.VALUE)).longValue());
    assertEquals(42L, ((Long) actualCreateDataResult.get(Fields.VALUE_LONG)).longValue());
    assertEquals(VariableEventHandler.TYPE_LONG, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@link LongType} (default constructor).
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is {@link VariableEventHandler#TYPE_LONG}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenLongType_thenReturnVariable_typeIsType_long() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new LongType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_LONG, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>Then return containsKey {@link Fields#VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenNull_thenReturnContainsKeyValue() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(null);
    variableEvent.setVariableValue(JSONObject.NULL);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>Then return size is six.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenOne_thenReturnSizeIsSix() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new ShortType());
    variableEvent.setVariableValue((short) 1);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(6, actualCreateDataResult.size());
    assertEquals(1, ((Integer) actualCreateDataResult.get(Fields.VALUE_INTEGER)).intValue());
    assertEquals(
        1.0d, ((Double) actualCreateDataResult.get(Fields.VALUE_DOUBLE)).doubleValue(), 0.0);
    assertEquals(1L, ((Long) actualCreateDataResult.get(Fields.VALUE_LONG)).longValue());
    assertEquals((short) 1, ((Short) actualCreateDataResult.get(Fields.VALUE)).shortValue());
    assertEquals((short) 1, ((Short) actualCreateDataResult.get(Fields.VALUE_SHORT)).shortValue());
    assertTrue(actualCreateDataResult.containsKey(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given randomUUID.
   *   <li>Then return containsKey {@link Fields#VALUE_STRING}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenRandomUUID_thenReturnContainsKeyValue_string() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new UUIDType());
    UUID randomUUIDResult = UUID.randomUUID();
    variableEvent.setVariableValue(randomUUIDResult);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(4, actualCreateDataResult.size());
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE_STRING));
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE_UUID));
    assertEquals(VariableEventHandler.TYPE_UUID, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
    assertSame(randomUUIDResult, actualCreateDataResult.get(Fields.VALUE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@link ShortType} (default constructor).
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is {@link VariableEventHandler#TYPE_SHORT}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenShortType_thenReturnVariable_typeIsType_short() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new ShortType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_SHORT, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>Then return {@link Fields#VALUE_INTEGER} intValue is ten.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenTen_thenReturnValue_integerIntValueIsTen() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new DoubleType());
    variableEvent.setVariableValue(10.0d);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(5, actualCreateDataResult.size());
    assertEquals(10, ((Integer) actualCreateDataResult.get(Fields.VALUE_INTEGER)).intValue());
    assertEquals(10.0d, ((Double) actualCreateDataResult.get(Fields.VALUE)).doubleValue(), 0.0);
    assertEquals(
        10.0d, ((Double) actualCreateDataResult.get(Fields.VALUE_DOUBLE)).doubleValue(), 0.0);
    assertEquals(10L, ((Long) actualCreateDataResult.get(Fields.VALUE_LONG)).longValue());
    assertTrue(actualCreateDataResult.containsKey(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@link UUIDType} (default constructor).
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is {@link VariableEventHandler#TYPE_UUID}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_givenUUIDType_thenReturnVariable_typeIsType_uuid() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new UUIDType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_UUID, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Then return containsKey {@link Fields#VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_thenReturnContainsKeyValue() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new SerializableType(true));
    variableEvent.setVariableValue(JSONObject.NULL);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Then return {@link Fields#VALUE} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_thenReturnValueIsNull() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new SerializableType(true));
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(3, actualCreateDataResult.size());
    assertEquals("null", actualCreateDataResult.get(Fields.VALUE));
    assertEquals("null", actualCreateDataResult.get(Fields.VALUE_JSON));
    assertEquals(VariableEventHandler.TYPE_JSON, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Then return {@link Fields#VALUE_DATE} longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_thenReturnValue_dateLongValueIsZero() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new DateType());
    Date fromResult =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    variableEvent.setVariableValue(fromResult);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(3, actualCreateDataResult.size());
    assertEquals(0L, ((Long) actualCreateDataResult.get(Fields.VALUE_DATE)).longValue());
    assertTrue(actualCreateDataResult.containsKey(Fields.VARIABLE_TYPE));
    assertSame(fromResult, actualCreateDataResult.get(Fields.VALUE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is {@link VariableEventHandler#TYPE_BOOLEAN}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map VariableEventHandler.createData(ActivitiVariableEvent)"})
  public void testCreateData_thenReturnVariable_typeIsType_boolean() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new BooleanType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult =
        variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(
        VariableEventHandler.TYPE_BOOLEAN, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }
}
