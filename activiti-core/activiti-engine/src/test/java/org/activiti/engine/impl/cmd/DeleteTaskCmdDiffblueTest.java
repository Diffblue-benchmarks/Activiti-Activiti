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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DeleteTaskCmdDiffblueTest {
  /**
   * Test {@link DeleteTaskCmd#DeleteTaskCmd(String, String, boolean)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@link DeleteTaskCmd#taskId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteTaskCmd#DeleteTaskCmd(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteTaskCmd.<init>(String, String, boolean)",
    "void DeleteTaskCmd.<init>(String, String, boolean, boolean)",
    "void DeleteTaskCmd.<init>(Collection, String, boolean)",
    "void DeleteTaskCmd.<init>(Collection, String, boolean, boolean)"
  })
  public void testNewDeleteTaskCmd_when42_thenReturnTaskIdIs42() {
    // Arrange and Act
    DeleteTaskCmd actualDeleteTaskCmd = new DeleteTaskCmd("42", "Just cause", true);

    // Assert
    assertEquals("42", actualDeleteTaskCmd.taskId);
    assertEquals("Just cause", actualDeleteTaskCmd.deleteReason);
    assertNull(actualDeleteTaskCmd.taskIds);
    assertFalse(actualDeleteTaskCmd.cancel);
    assertTrue(actualDeleteTaskCmd.cascade);
  }

  /**
   * Test {@link DeleteTaskCmd#DeleteTaskCmd(String, String, boolean, boolean)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@link DeleteTaskCmd#taskId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteTaskCmd#DeleteTaskCmd(String, String, boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteTaskCmd.<init>(String, String, boolean)",
    "void DeleteTaskCmd.<init>(String, String, boolean, boolean)",
    "void DeleteTaskCmd.<init>(Collection, String, boolean)",
    "void DeleteTaskCmd.<init>(Collection, String, boolean, boolean)"
  })
  public void testNewDeleteTaskCmd_when42_thenReturnTaskIdIs422() {
    // Arrange and Act
    DeleteTaskCmd actualDeleteTaskCmd = new DeleteTaskCmd("42", "Just cause", true, true);

    // Assert
    assertEquals("42", actualDeleteTaskCmd.taskId);
    assertEquals("Just cause", actualDeleteTaskCmd.deleteReason);
    assertNull(actualDeleteTaskCmd.taskIds);
    assertTrue(actualDeleteTaskCmd.cancel);
    assertTrue(actualDeleteTaskCmd.cascade);
  }

  /**
   * Test {@link DeleteTaskCmd#DeleteTaskCmd(Collection, String, boolean)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link DeleteTaskCmd#taskIds} return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteTaskCmd#DeleteTaskCmd(Collection, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteTaskCmd.<init>(String, String, boolean)",
    "void DeleteTaskCmd.<init>(String, String, boolean, boolean)",
    "void DeleteTaskCmd.<init>(Collection, String, boolean)",
    "void DeleteTaskCmd.<init>(Collection, String, boolean, boolean)"
  })
  public void testNewDeleteTaskCmd_whenArrayList_thenTaskIdsReturnList() {
    // Arrange and Act
    DeleteTaskCmd actualDeleteTaskCmd = new DeleteTaskCmd(new ArrayList<>(), "Just cause", true);

    // Assert
    Collection<String> collection = actualDeleteTaskCmd.taskIds;
    assertTrue(collection instanceof List);
    assertEquals("Just cause", actualDeleteTaskCmd.deleteReason);
    assertNull(actualDeleteTaskCmd.taskId);
    assertFalse(actualDeleteTaskCmd.cancel);
    assertTrue(collection.isEmpty());
    assertTrue(actualDeleteTaskCmd.cascade);
  }

  /**
   * Test {@link DeleteTaskCmd#DeleteTaskCmd(Collection, String, boolean, boolean)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link DeleteTaskCmd#taskIds} return {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteTaskCmd#DeleteTaskCmd(Collection, String, boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteTaskCmd.<init>(String, String, boolean)",
    "void DeleteTaskCmd.<init>(String, String, boolean, boolean)",
    "void DeleteTaskCmd.<init>(Collection, String, boolean)",
    "void DeleteTaskCmd.<init>(Collection, String, boolean, boolean)"
  })
  public void testNewDeleteTaskCmd_whenArrayList_thenTaskIdsReturnList2() {
    // Arrange and Act
    DeleteTaskCmd actualDeleteTaskCmd =
        new DeleteTaskCmd(new ArrayList<>(), "Just cause", true, true);

    // Assert
    Collection<String> collection = actualDeleteTaskCmd.taskIds;
    assertTrue(collection instanceof List);
    assertEquals("Just cause", actualDeleteTaskCmd.deleteReason);
    assertNull(actualDeleteTaskCmd.taskId);
    assertTrue(collection.isEmpty());
    assertTrue(actualDeleteTaskCmd.cancel);
    assertTrue(actualDeleteTaskCmd.cascade);
  }

  /**
   * Test {@link DeleteTaskCmd#execute(CommandContext)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteTaskCmd#execute(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Void DeleteTaskCmd.execute(CommandContext)"})
  public void testExecute_thenReturnNull() {
    // Arrange
    DeleteTaskCmd deleteTaskCmd = new DeleteTaskCmd(new ArrayList<>(), "Just cause", true);

    // Act and Assert
    assertNull(deleteTaskCmd.execute(null));
  }
}
