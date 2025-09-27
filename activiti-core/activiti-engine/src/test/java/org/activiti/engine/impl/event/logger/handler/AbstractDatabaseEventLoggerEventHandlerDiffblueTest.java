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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractDatabaseEventLoggerEventHandlerDiffblueTest {
  /**
   * Test {@link AbstractDatabaseEventLoggerEventHandler#setEvent(ActivitiEvent)}.
   *
   * <p>Method under test: {@link AbstractDatabaseEventLoggerEventHandler#setEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractDatabaseEventLoggerEventHandler.setEvent(ActivitiEvent)"})
  public void testSetEvent() {
    // Arrange
    ActivityCompensatedEventHandler activityCompensatedEventHandler =
        new ActivityCompensatedEventHandler();

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
   * Test {@link AbstractDatabaseEventLoggerEventHandler#setObjectMapper(ObjectMapper)}.
   *
   * <p>Method under test: {@link
   * AbstractDatabaseEventLoggerEventHandler#setObjectMapper(ObjectMapper)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractDatabaseEventLoggerEventHandler.setObjectMapper(ObjectMapper)"})
  public void testSetObjectMapper() {
    // Arrange
    ActivityCompensatedEventHandler activityCompensatedEventHandler =
        new ActivityCompensatedEventHandler();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act
    activityCompensatedEventHandler.setObjectMapper(objectMapper);

    // Assert
    ObjectMapper objectMapper2 = activityCompensatedEventHandler.objectMapper;
    assertTrue(objectMapper2 instanceof JsonMapper);
    assertTrue(objectMapper2.getSerializerProviderInstance() instanceof Impl);
    assertNull(objectMapper2.getInjectableValues());
    assertNull(objectMapper2.getPropertyNamingStrategy());
    JsonFactory factory = objectMapper.getFactory();
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper2.getJsonFactory());
    assertSame(factory, objectMapper2.getFactory());
  }

  /**
   * Test {@link AbstractDatabaseEventLoggerEventHandler#getEntityFromEvent()}.
   *
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link AbstractDatabaseEventLoggerEventHandler#getEntityFromEvent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AbstractDatabaseEventLoggerEventHandler.getEntityFromEvent()"})
  public void testGetEntityFromEvent_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    ActivityCompensatedEventHandler activityCompensatedEventHandler =
        new ActivityCompensatedEventHandler();
    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    activityCompensatedEventHandler.setEvent(
        new ActivitiProcessCancelledEventImpl(processInstance));

    // Act and Assert
    assertSame(processInstance, activityCompensatedEventHandler.getEntityFromEvent());
  }

  /**
   * Test {@link AbstractDatabaseEventLoggerEventHandler#putInMapIfNotNull(Map, String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link HashMap#HashMap()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link AbstractDatabaseEventLoggerEventHandler#putInMapIfNotNull(Map,
   * String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractDatabaseEventLoggerEventHandler.putInMapIfNotNull(Map, String, Object)"
  })
  public void testPutInMapIfNotNull_whenNull_thenHashMapEmpty() {
    // Arrange
    ActivityCompensatedEventHandler activityCompensatedEventHandler =
        new ActivityCompensatedEventHandler();
    HashMap<String, Object> map = new HashMap<>();

    // Act
    activityCompensatedEventHandler.putInMapIfNotNull(map, "Key", null);

    // Assert that nothing has changed
    assertTrue(map.isEmpty());
  }

  /**
   * Test {@link AbstractDatabaseEventLoggerEventHandler#putInMapIfNotNull(Map, String, Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then {@link HashMap#HashMap()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link AbstractDatabaseEventLoggerEventHandler#putInMapIfNotNull(Map,
   * String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractDatabaseEventLoggerEventHandler.putInMapIfNotNull(Map, String, Object)"
  })
  public void testPutInMapIfNotNull_whenNull_thenHashMapSizeIsOne() {
    // Arrange
    ActivityCompensatedEventHandler activityCompensatedEventHandler =
        new ActivityCompensatedEventHandler();
    HashMap<String, Object> map = new HashMap<>();
    Object object = JSONObject.NULL;

    // Act
    activityCompensatedEventHandler.putInMapIfNotNull(map, "Key", object);

    // Assert
    assertEquals(1, map.size());
    assertSame(object, map.get("Key"));
  }
}
