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

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RemoveEventListenerCommandDiffblueTest {
  /**
   * Test {@link RemoveEventListenerCommand#RemoveEventListenerCommand(ActivitiEventListener)}.
   *
   * <p>Method under test: {@link
   * RemoveEventListenerCommand#RemoveEventListenerCommand(ActivitiEventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RemoveEventListenerCommand.<init>(ActivitiEventListener)"})
  public void testNewRemoveEventListenerCommand() {
    // Arrange, Act and Assert
    ActivitiEventListener activitiEventListener =
        new RemoveEventListenerCommand(new BaseEntityEventListener(true)).listener;
    assertTrue(activitiEventListener instanceof BaseEntityEventListener);
    assertTrue(activitiEventListener.isFailOnException());
  }

  /**
   * Test {@link RemoveEventListenerCommand#execute(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link RemoveEventListenerCommand#execute(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Void RemoveEventListenerCommand.execute(CommandContext)"})
  public void testExecute_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new RemoveEventListenerCommand(null).execute(null));
  }
}
