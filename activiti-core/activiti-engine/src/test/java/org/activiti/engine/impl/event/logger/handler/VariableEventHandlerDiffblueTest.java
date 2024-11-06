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
import static org.junit.Assert.assertTrue;
import java.util.Map;
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

public class VariableEventHandlerDiffblueTest {
  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@link BooleanType} (default constructor).</li>
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is
   * {@link VariableEventHandler#TYPE_BOOLEAN}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenBooleanType_thenReturnVariable_typeIsType_boolean() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new BooleanType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_BOOLEAN, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@link DateType} (default constructor).</li>
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is
   * {@link VariableEventHandler#TYPE_DATE}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenDateType_thenReturnVariable_typeIsType_date() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new DateType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_DATE, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@link DoubleType} (default constructor).</li>
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is
   * {@link VariableEventHandler#TYPE_DOUBLE}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenDoubleType_thenReturnVariable_typeIsType_double() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new DoubleType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_DOUBLE, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given forty-two.</li>
   *   <li>Then return size is five.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenFortyTwo_thenReturnSizeIsFive() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new IntegerType());
    variableEvent.setVariableValue(42);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(5, actualCreateDataResult.size());
    assertEquals(42.0d, ((Double) actualCreateDataResult.get(Fields.VALUE_DOUBLE)).doubleValue(), 0.0);
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE));
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE_INTEGER));
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE_LONG));
    assertEquals(VariableEventHandler.TYPE_INTEGER, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@link IntegerType} (default constructor).</li>
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is
   * {@link VariableEventHandler#TYPE_INTEGER}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenIntegerType_thenReturnVariable_typeIsType_integer() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new IntegerType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_INTEGER, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@link LongStringType#LongStringType(int)} with minLength is
   * three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenLongStringTypeWithMinLengthIsThree() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new LongStringType(3));
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_STRING, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@link LongType} (default constructor).</li>
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is
   * {@link VariableEventHandler#TYPE_LONG}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenLongType_thenReturnVariable_typeIsType_long() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new LongType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_LONG, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenNull() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new SerializableType(true));
    variableEvent.setVariableValue(JSONObject.NULL);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>Then return size is six.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenOne_thenReturnSizeIsSix() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new ShortType());
    variableEvent.setVariableValue((short) 1);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(6, actualCreateDataResult.size());
    assertEquals(1.0d, ((Double) actualCreateDataResult.get(Fields.VALUE_DOUBLE)).doubleValue(), 0.0);
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE));
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE_INTEGER));
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE_LONG));
    assertTrue(actualCreateDataResult.containsKey(Fields.VALUE_SHORT));
    assertEquals(VariableEventHandler.TYPE_SHORT, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@link ShortType} (default constructor).</li>
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is
   * {@link VariableEventHandler#TYPE_SHORT}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenShortType_thenReturnVariable_typeIsType_short() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new ShortType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_SHORT, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@link StringType#StringType(int)} with maxLength is three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenStringTypeWithMaxLengthIsThree() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new StringType(3));
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_STRING, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@link UUIDType} (default constructor).</li>
   *   <li>Then return {@link Fields#VARIABLE_TYPE} is
   * {@link VariableEventHandler#TYPE_UUID}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_givenUUIDType_thenReturnVariable_typeIsType_uuid() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new UUIDType());
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(1, actualCreateDataResult.size());
    assertEquals(VariableEventHandler.TYPE_UUID, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_thenReturnEmpty() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(null);
    variableEvent.setVariableValue(null);

    // Act and Assert
    assertTrue(variableCreatedEventHandler.createData(variableEvent).isEmpty());
  }

  /**
   * Test {@link VariableEventHandler#createData(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Then return size is three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData_thenReturnSizeIsThree() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(new SerializableType(true));
    variableEvent.setVariableValue(null);

    // Act
    Map<String, Object> actualCreateDataResult = variableCreatedEventHandler.createData(variableEvent);

    // Assert
    assertEquals(3, actualCreateDataResult.size());
    assertEquals("null", actualCreateDataResult.get(Fields.VALUE));
    assertEquals("null", actualCreateDataResult.get(Fields.VALUE_JSON));
    assertEquals(VariableEventHandler.TYPE_JSON, actualCreateDataResult.get(Fields.VARIABLE_TYPE));
  }
}
