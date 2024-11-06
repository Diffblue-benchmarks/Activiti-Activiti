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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.junit.Test;

public class DispatchEventCommandDiffblueTest {
  /**
   * Test {@link DispatchEventCommand#DispatchEventCommand(ActivitiEvent)}.
   * <p>
   * Method under test:
   * {@link DispatchEventCommand#DispatchEventCommand(ActivitiEvent)}
   */
  @Test
  public void testNewDispatchEventCommand() {
    // Arrange, Act and Assert
    ActivitiEvent activitiEvent = (new DispatchEventCommand(new ActivitiActivityCancelledEventImpl())).event;
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
}
