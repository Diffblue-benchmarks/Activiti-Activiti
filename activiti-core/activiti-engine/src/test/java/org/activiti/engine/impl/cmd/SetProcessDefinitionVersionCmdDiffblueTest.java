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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SetProcessDefinitionVersionCmdDiffblueTest {
  /**
   * Test {@link SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link
   * SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetProcessDefinitionVersionCmd.<init>(String, Integer)"})
  public void testNewSetProcessDefinitionVersionCmd_whenEmptyString() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new SetProcessDefinitionVersionCmd("", 1));
  }

  /**
   * Test {@link SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetProcessDefinitionVersionCmd.<init>(String, Integer)"})
  public void testNewSetProcessDefinitionVersionCmd_whenNull() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new SetProcessDefinitionVersionCmd(null, null));
  }

  /**
   * Test {@link SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetProcessDefinitionVersionCmd.<init>(String, Integer)"})
  public void testNewSetProcessDefinitionVersionCmd_whenNull2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new SetProcessDefinitionVersionCmd("42", null));
  }

  /**
   * Test {@link SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}.
   *
   * <ul>
   *   <li>When zero.
   * </ul>
   *
   * <p>Method under test: {@link
   * SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetProcessDefinitionVersionCmd.<init>(String, Integer)"})
  public void testNewSetProcessDefinitionVersionCmd_whenZero() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new SetProcessDefinitionVersionCmd("42", 0));
  }
}
