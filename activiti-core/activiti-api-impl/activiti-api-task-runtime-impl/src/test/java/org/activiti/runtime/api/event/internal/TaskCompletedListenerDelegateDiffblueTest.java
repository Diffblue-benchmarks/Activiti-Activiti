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
package org.activiti.runtime.api.event.internal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.api.task.runtime.events.TaskCompletedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.runtime.api.event.impl.ToTaskCompletedConverter;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TaskCompletedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TaskCompletedListenerDelegate#TaskCompletedListenerDelegate(List,
   *       ToTaskCompletedConverter)}
   *   <li>{@link TaskCompletedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TaskCompletedListenerDelegate.<init>(List, ToTaskCompletedConverter)",
    "boolean TaskCompletedListenerDelegate.isFailOnException()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<TaskRuntimeEventListener<TaskCompletedEvent>> listeners = new ArrayList<>();
    APITaskConverter converter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    ToTaskCompletedConverter taskCompletedConverter =
        new ToTaskCompletedConverter(converter, mock(SecurityManager.class));

    // Act
    TaskCompletedListenerDelegate actualTaskCompletedListenerDelegate =
        new TaskCompletedListenerDelegate(listeners, taskCompletedConverter);

    // Assert
    assertFalse(actualTaskCompletedListenerDelegate.isFailOnException());
  }
}
