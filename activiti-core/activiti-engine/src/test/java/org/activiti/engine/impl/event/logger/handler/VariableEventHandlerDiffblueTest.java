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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData2() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData3() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData4() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData5() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData6() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData7() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData8() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData9() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData10() {
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

  /**
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData11() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData12() {
    // Arrange
    VariableCreatedEventHandler variableCreatedEventHandler = new VariableCreatedEventHandler();

    ActivitiVariableEventImpl variableEvent = new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    variableEvent.setVariableType(null);
    variableEvent.setVariableValue(null);

    // Act and Assert
    assertTrue(variableCreatedEventHandler.createData(variableEvent).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData13() {
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
   * Method under test:
   * {@link VariableEventHandler#createData(ActivitiVariableEvent)}
   */
  @Test
  public void testCreateData14() {
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
}
