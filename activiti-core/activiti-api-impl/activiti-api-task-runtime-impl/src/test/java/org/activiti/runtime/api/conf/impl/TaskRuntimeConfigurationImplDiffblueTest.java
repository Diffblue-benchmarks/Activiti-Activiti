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
package org.activiti.runtime.api.conf.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskRuntimeConfigurationImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TaskRuntimeConfigurationImplDiffblueTest {
  @Autowired
  private List<TaskRuntimeEventListener<RuntimeEvent<?, ?>>> list;

  @Autowired
  private List<VariableEventListener<RuntimeEvent<?, ?>>> list2;

  @Autowired
  private TaskRuntimeConfigurationImpl taskRuntimeConfigurationImpl;

  @MockBean
  private TaskRuntimeEventListener<RuntimeEvent<?, ?>> taskRuntimeEventListener;

  @MockBean
  private VariableEventListener<RuntimeEvent<?, ?>> variableEventListener;

  /**
   * Test {@link TaskRuntimeConfigurationImpl#taskRuntimeEventListeners()}.
   * <p>
   * Method under test:
   * {@link TaskRuntimeConfigurationImpl#taskRuntimeEventListeners()}
   */
  @Test
  @DisplayName("Test taskRuntimeEventListeners()")
  void testTaskRuntimeEventListeners() {
    // Arrange, Act and Assert
    assertEquals(1, taskRuntimeConfigurationImpl.taskRuntimeEventListeners().size());
  }

  /**
   * Test {@link TaskRuntimeConfigurationImpl#variableEventListeners()}.
   * <p>
   * Method under test:
   * {@link TaskRuntimeConfigurationImpl#variableEventListeners()}
   */
  @Test
  @DisplayName("Test variableEventListeners()")
  void testVariableEventListeners() {
    // Arrange, Act and Assert
    assertEquals(1, taskRuntimeConfigurationImpl.variableEventListeners().size());
  }
}
