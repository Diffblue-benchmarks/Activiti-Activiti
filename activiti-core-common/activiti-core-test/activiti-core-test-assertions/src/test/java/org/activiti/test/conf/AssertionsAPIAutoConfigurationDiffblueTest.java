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
package org.activiti.test.conf;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.test.operations.AwaitableProcessOperations;
import org.activiti.test.operations.AwaitableTaskOperations;
import org.activiti.test.operations.ProcessOperations;
import org.activiti.test.operations.TaskOperations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AssertionsAPIAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
@ExtendWith(MockitoExtension.class)
class AssertionsAPIAutoConfigurationDiffblueTest {
  @Autowired
  private AssertionsAPIAutoConfiguration assertionsAPIAutoConfiguration;

  @MockBean
  private ProcessOperations processOperations;

  @MockBean
  private TaskOperations taskOperations;

  /**
   * Test {@link AssertionsAPIAutoConfiguration#processOperations(ProcessOperations, boolean)}.
   * <ul>
   *   <li>Then return {@link AwaitableProcessOperations}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AssertionsAPIAutoConfiguration#processOperations(ProcessOperations, boolean)}
   */
  @Test
  @DisplayName("Test processOperations(ProcessOperations, boolean); then return AwaitableProcessOperations")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProcessOperations AssertionsAPIAutoConfiguration.processOperations(ProcessOperations, boolean)"})
  void testProcessOperations_thenReturnAwaitableProcessOperations() {
    // Arrange, Act and Assert
    assertTrue(assertionsAPIAutoConfiguration.processOperations(new AwaitableProcessOperations(null, true),
        true) instanceof AwaitableProcessOperations);
  }

  /**
   * Test {@link AssertionsAPIAutoConfiguration#taskOperations(TaskOperations, boolean)}.
   * <p>
   * Method under test: {@link AssertionsAPIAutoConfiguration#taskOperations(TaskOperations, boolean)}
   */
  @Test
  @DisplayName("Test taskOperations(TaskOperations, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"TaskOperations AssertionsAPIAutoConfiguration.taskOperations(TaskOperations, boolean)"})
  void testTaskOperations() {
    // Arrange, Act and Assert
    assertTrue(assertionsAPIAutoConfiguration.taskOperations(new AwaitableTaskOperations(null, true),
        true) instanceof AwaitableTaskOperations);
  }
}
