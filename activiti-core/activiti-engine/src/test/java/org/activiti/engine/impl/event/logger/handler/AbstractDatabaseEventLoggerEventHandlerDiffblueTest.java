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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractDatabaseEventLoggerEventHandlerDiffblueTest {
  @InjectMocks
  private ActivityCompensatedEventHandler activityCompensatedEventHandler;

  /**
   * Test {@link AbstractDatabaseEventLoggerEventHandler#setEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link ActivityCompensatedEventHandler} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractDatabaseEventLoggerEventHandler#setEvent(ActivitiEvent)}
   */
  @Test
  public void testSetEvent_givenActivityCompensatedEventHandler() {
    // Arrange
    ActivityCompensatedEventHandler activityCompensatedEventHandler = new ActivityCompensatedEventHandler();

    // Act
    activityCompensatedEventHandler.setEvent(new ActivitiActivityCancelledEventImpl());

    // Assert
    ActivitiEvent activitiEvent = activityCompensatedEventHandler.event;
    assertTrue(activitiEvent instanceof ActivitiActivityCancelledEventImpl);
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getCause());
    assertNull(activitiEvent.getExecutionId());
    assertNull(activitiEvent.getProcessDefinitionId());
    assertNull(activitiEvent.getProcessInstanceId());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getActivityId());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getActivityName());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getActivityType());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getBehaviorClass());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_CANCELLED, activitiEvent.getType());
  }

  /**
   * Test {@link AbstractDatabaseEventLoggerEventHandler#setEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link ActivityCompensatedEventHandler} (default constructor)
   * TimeStamp is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractDatabaseEventLoggerEventHandler#setEvent(ActivitiEvent)}
   */
  @Test
  public void testSetEvent_givenActivityCompensatedEventHandlerTimeStampIsDate() {
    // Arrange
    ActivityCompensatedEventHandler activityCompensatedEventHandler = new ActivityCompensatedEventHandler();
    activityCompensatedEventHandler.setTimeStamp(mock(Date.class));

    // Act
    activityCompensatedEventHandler.setEvent(new ActivitiActivityCancelledEventImpl());

    // Assert
    ActivitiEvent activitiEvent = activityCompensatedEventHandler.event;
    assertTrue(activitiEvent instanceof ActivitiActivityCancelledEventImpl);
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getCause());
    assertNull(activitiEvent.getExecutionId());
    assertNull(activitiEvent.getProcessDefinitionId());
    assertNull(activitiEvent.getProcessInstanceId());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getActivityId());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getActivityName());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getActivityType());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getBehaviorClass());
    assertNull(((ActivitiActivityCancelledEventImpl) activitiEvent).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_CANCELLED, activitiEvent.getType());
  }

  /**
   * Test
   * {@link AbstractDatabaseEventLoggerEventHandler#setObjectMapper(ObjectMapper)}.
   * <ul>
   *   <li>Given {@link SerializerFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractDatabaseEventLoggerEventHandler#setObjectMapper(ObjectMapper)}
   */
  @Test
  public void testSetObjectMapper_givenSerializerFactory() {
    // Arrange
    ActivityCompensatedEventHandler activityCompensatedEventHandler = new ActivityCompensatedEventHandler();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(mock(SerializerFactory.class));

    // Act
    activityCompensatedEventHandler.setObjectMapper(objectMapper);

    // Assert
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    ObjectMapper objectMapper2 = activityCompensatedEventHandler.objectMapper;
    SerializerProvider serializerProviderInstance = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    assertNull(serializerProviderInstance.getGenerator());
    assertNull(objectMapper2.getInjectableValues());
    assertNull(objectMapper2.getPropertyNamingStrategy());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(serializerProviderInstance.getActiveView());
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper2.getJsonFactory());
    assertSame(factory, objectMapper2.getFactory());
  }

  /**
   * Test
   * {@link AbstractDatabaseEventLoggerEventHandler#setObjectMapper(ObjectMapper)}.
   * <ul>
   *   <li>When {@link ObjectMapper#ObjectMapper()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractDatabaseEventLoggerEventHandler#setObjectMapper(ObjectMapper)}
   */
  @Test
  public void testSetObjectMapper_whenObjectMapper() {
    // Arrange
    ActivityCompensatedEventHandler activityCompensatedEventHandler = new ActivityCompensatedEventHandler();
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    activityCompensatedEventHandler.setObjectMapper(objectMapper);

    // Assert
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    ObjectMapper objectMapper2 = activityCompensatedEventHandler.objectMapper;
    SerializerProvider serializerProviderInstance = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    assertNull(serializerProviderInstance.getGenerator());
    assertNull(objectMapper2.getInjectableValues());
    assertNull(objectMapper2.getPropertyNamingStrategy());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(serializerProviderInstance.getActiveView());
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper2.getJsonFactory());
    assertSame(factory, objectMapper2.getFactory());
  }

  /**
   * Test {@link AbstractDatabaseEventLoggerEventHandler#getEntityFromEvent()}.
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractDatabaseEventLoggerEventHandler#getEntityFromEvent()}
   */
  @Test
  public void testGetEntityFromEvent_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    ActivityCompensatedEventHandler activityCompensatedEventHandler = new ActivityCompensatedEventHandler();
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    activityCompensatedEventHandler.setEvent(new ActivitiProcessCancelledEventImpl(processInstance));

    // Act and Assert
    assertSame(processInstance, activityCompensatedEventHandler.getEntityFromEvent());
  }

  /**
   * Test
   * {@link AbstractDatabaseEventLoggerEventHandler#putInMapIfNotNull(Map, String, Object)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractDatabaseEventLoggerEventHandler#putInMapIfNotNull(Map, String, Object)}
   */
  @Test
  public void testPutInMapIfNotNull_givenFoo_whenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));
    Object object = JSONObject.NULL;

    // Act
    activityCompensatedEventHandler.putInMapIfNotNull(map, "Key", object);

    // Assert
    assertEquals(1, map.size());
    assertSame(object, map.get("Key"));
  }

  /**
   * Test
   * {@link AbstractDatabaseEventLoggerEventHandler#putInMapIfNotNull(Map, String, Object)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then {@link HashMap#HashMap()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractDatabaseEventLoggerEventHandler#putInMapIfNotNull(Map, String, Object)}
   */
  @Test
  public void testPutInMapIfNotNull_whenHashMap_thenHashMapSizeIsOne() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    Object object = JSONObject.NULL;

    // Act
    activityCompensatedEventHandler.putInMapIfNotNull(map, "Key", object);

    // Assert
    assertEquals(1, map.size());
    assertSame(object, map.get("Key"));
  }

  /**
   * Test
   * {@link AbstractDatabaseEventLoggerEventHandler#putInMapIfNotNull(Map, String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link HashMap#HashMap()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractDatabaseEventLoggerEventHandler#putInMapIfNotNull(Map, String, Object)}
   */
  @Test
  public void testPutInMapIfNotNull_whenNull_thenHashMapEmpty() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();

    // Act
    activityCompensatedEventHandler.putInMapIfNotNull(map, "Key", null);

    // Assert that nothing has changed
    assertTrue(map.isEmpty());
  }
}
