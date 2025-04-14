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
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.CommentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisCommentDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AddCommentCmdDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddCommentCmd#AddCommentCmd(String, String, String)}
   *   <li>{@link AddCommentCmd#getSuspendedExceptionMessage()}
   *   <li>{@link AddCommentCmd#getSuspendedTaskException()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddCommentCmd.<init>(String, String, String)",
      "void AddCommentCmd.<init>(String, String, String, String)",
      "String AddCommentCmd.getSuspendedExceptionMessage()", "String AddCommentCmd.getSuspendedTaskException()"})
  public void testGettersAndSetters_whenNotAllWhoWanderAreLost() {
    // Arrange and Act
    AddCommentCmd actualAddCommentCmd = new AddCommentCmd("42", "42", "Not all who wander are lost");
    String actualSuspendedExceptionMessage = actualAddCommentCmd.getSuspendedExceptionMessage();

    // Assert
    assertEquals("Cannot add a comment to a suspended execution", actualSuspendedExceptionMessage);
    assertEquals("Cannot add a comment to a suspended task", actualAddCommentCmd.getSuspendedTaskException());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Type}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddCommentCmd#AddCommentCmd(String, String, String, String)}
   *   <li>{@link AddCommentCmd#getSuspendedExceptionMessage()}
   *   <li>{@link AddCommentCmd#getSuspendedTaskException()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddCommentCmd.<init>(String, String, String)",
      "void AddCommentCmd.<init>(String, String, String, String)",
      "String AddCommentCmd.getSuspendedExceptionMessage()", "String AddCommentCmd.getSuspendedTaskException()"})
  public void testGettersAndSetters_whenType() {
    // Arrange and Act
    AddCommentCmd actualAddCommentCmd = new AddCommentCmd("42", "42", "Type", "Not all who wander are lost");
    String actualSuspendedExceptionMessage = actualAddCommentCmd.getSuspendedExceptionMessage();

    // Assert
    assertEquals("Cannot add a comment to a suspended execution", actualSuspendedExceptionMessage);
    assertEquals("Cannot add a comment to a suspended task", actualAddCommentCmd.getSuspendedTaskException());
  }

  /**
   * Test {@link AddCommentCmd#executeInternal(CommandContext, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddCommentCmd#executeInternal(CommandContext, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.task.Comment AddCommentCmd.executeInternal(CommandContext, String)"})
  public void testExecuteInternal_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    AddCommentCmd addCommentCmd = new AddCommentCmd("42", "42", "Not all who wander are lost");
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    when(commandContext.getCommentEntityManager()).thenReturn(new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> addCommentCmd.executeInternal(commandContext, "42"));
    verify(commandContext).getCommentEntityManager();
    verify(commandContext).getProcessEngineConfiguration();
  }
}
