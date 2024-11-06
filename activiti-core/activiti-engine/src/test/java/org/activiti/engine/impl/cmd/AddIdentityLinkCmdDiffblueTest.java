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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;

public class AddIdentityLinkCmdDiffblueTest {
  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}.
   * <ul>
   *   <li>When {@code assignee}.</li>
   *   <li>Then return {@link AddIdentityLinkCmd#identityType} is
   * {@code assignee}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenAssignee_thenReturnIdentityTypeIsAssignee() {
    // Arrange and Act
    AddIdentityLinkCmd actualAddIdentityLinkCmd = new AddIdentityLinkCmd("42", null, 1, "assignee");

    // Assert
    assertEquals("42", actualAddIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended", actualAddIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("assignee", actualAddIdentityLinkCmd.identityType);
    assertNull(actualAddIdentityLinkCmd.details);
    assertNull(actualAddIdentityLinkCmd.identityId);
    assertEquals(1, actualAddIdentityLinkCmd.identityIdType);
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String, byte[])}.
   * <ul>
   *   <li>When {@code assignee}.</li>
   *   <li>Then return {@link AddIdentityLinkCmd#identityType} is
   * {@code assignee}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String, byte[])}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenAssignee_thenReturnIdentityTypeIsAssignee2()
      throws UnsupportedEncodingException {
    // Arrange and Act
    AddIdentityLinkCmd actualAddIdentityLinkCmd = new AddIdentityLinkCmd("42", null, 1, "assignee",
        "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    assertEquals("42", actualAddIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended", actualAddIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("assignee", actualAddIdentityLinkCmd.identityType);
    assertNull(actualAddIdentityLinkCmd.identityId);
    assertEquals(1, actualAddIdentityLinkCmd.identityIdType);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualAddIdentityLinkCmd.details);
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new AddIdentityLinkCmd(null, "42", 1, "Identity Type"));

    assertThrows(ActivitiIllegalArgumentException.class, () -> new AddIdentityLinkCmd("42", null, 1, "Identity Type"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkCmd(null, "42", 1, "Identity Type", "AXAXAXAX".getBytes("UTF-8")));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkCmd("42", null, 1, "Identity Type", "AXAXAXAX".getBytes("UTF-8")));
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@link AddIdentityLinkCmd#identityId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenOne_thenReturnIdentityIdIs42() {
    // Arrange and Act
    AddIdentityLinkCmd actualAddIdentityLinkCmd = new AddIdentityLinkCmd("42", "42", 1, "Identity Type");

    // Assert
    assertEquals("42", actualAddIdentityLinkCmd.identityId);
    assertEquals("42", actualAddIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended", actualAddIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("Identity Type", actualAddIdentityLinkCmd.identityType);
    assertNull(actualAddIdentityLinkCmd.details);
    assertEquals(1, actualAddIdentityLinkCmd.identityIdType);
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String, byte[])}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@link AddIdentityLinkCmd#identityId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String, byte[])}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenOne_thenReturnIdentityIdIs422() throws UnsupportedEncodingException {
    // Arrange and Act
    AddIdentityLinkCmd actualAddIdentityLinkCmd = new AddIdentityLinkCmd("42", "42", 1, "Identity Type",
        "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    assertEquals("42", actualAddIdentityLinkCmd.identityId);
    assertEquals("42", actualAddIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended", actualAddIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("Identity Type", actualAddIdentityLinkCmd.identityType);
    assertEquals(1, actualAddIdentityLinkCmd.identityIdType);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualAddIdentityLinkCmd.details);
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenOne_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new AddIdentityLinkCmd("42", "42", 1, null));

    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkCmd("42", "42", 1, null, "AXAXAXAX".getBytes("UTF-8")));
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}.
   * <ul>
   *   <li>When {@code owner}.</li>
   *   <li>Then return {@link AddIdentityLinkCmd#identityType} is
   * {@code owner}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenOwner_thenReturnIdentityTypeIsOwner() {
    // Arrange and Act
    AddIdentityLinkCmd actualAddIdentityLinkCmd = new AddIdentityLinkCmd("42", null, 1, "owner");

    // Assert
    assertEquals("42", actualAddIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended", actualAddIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("owner", actualAddIdentityLinkCmd.identityType);
    assertNull(actualAddIdentityLinkCmd.details);
    assertNull(actualAddIdentityLinkCmd.identityId);
    assertEquals(1, actualAddIdentityLinkCmd.identityIdType);
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String, byte[])}.
   * <ul>
   *   <li>When {@code owner}.</li>
   *   <li>Then return {@link AddIdentityLinkCmd#identityType} is
   * {@code owner}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String, byte[])}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenOwner_thenReturnIdentityTypeIsOwner2() throws UnsupportedEncodingException {
    // Arrange and Act
    AddIdentityLinkCmd actualAddIdentityLinkCmd = new AddIdentityLinkCmd("42", null, 1, "owner",
        "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    assertEquals("42", actualAddIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended", actualAddIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("owner", actualAddIdentityLinkCmd.identityType);
    assertNull(actualAddIdentityLinkCmd.identityId);
    assertEquals(1, actualAddIdentityLinkCmd.identityIdType);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualAddIdentityLinkCmd.details);
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenThree_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new AddIdentityLinkCmd("42", "42", 3, "Identity Type"));

    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkCmd("42", "42", 3, "Identity Type", "AXAXAXAX".getBytes("UTF-8")));
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@link AddIdentityLinkCmd#identityIdType} is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenTwo_thenReturnIdentityIdTypeIsTwo() {
    // Arrange and Act
    AddIdentityLinkCmd actualAddIdentityLinkCmd = new AddIdentityLinkCmd("42", "42", 2, "Identity Type");

    // Assert
    assertEquals("42", actualAddIdentityLinkCmd.identityId);
    assertEquals("42", actualAddIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended", actualAddIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("Identity Type", actualAddIdentityLinkCmd.identityType);
    assertNull(actualAddIdentityLinkCmd.details);
    assertEquals(2, actualAddIdentityLinkCmd.identityIdType);
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String, byte[])}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@link AddIdentityLinkCmd#identityIdType} is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String, byte[])}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenTwo_thenReturnIdentityIdTypeIsTwo2() throws UnsupportedEncodingException {
    // Arrange and Act
    AddIdentityLinkCmd actualAddIdentityLinkCmd = new AddIdentityLinkCmd("42", "42", 2, "Identity Type",
        "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    assertEquals("42", actualAddIdentityLinkCmd.identityId);
    assertEquals("42", actualAddIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended", actualAddIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("Identity Type", actualAddIdentityLinkCmd.identityType);
    assertEquals(2, actualAddIdentityLinkCmd.identityIdType);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualAddIdentityLinkCmd.details);
  }

  /**
   * Test
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#AddIdentityLinkCmd(String, String, int, String)}
   */
  @Test
  public void testNewAddIdentityLinkCmd_whenTwo_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new AddIdentityLinkCmd("42", null, 2, "Identity Type"));

    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkCmd("42", null, 2, "Identity Type", "AXAXAXAX".getBytes("UTF-8")));
  }

  /**
   * Test {@link AddIdentityLinkCmd#validateParams(String, String, int, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#validateParams(String, String, int, String)}
   */
  @Test
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AddIdentityLinkCmd("42", "42", 1, "Identity Type")).validateParams(null, "42", 1, "Identity Type"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AddIdentityLinkCmd("42", "42", 1, "Identity Type")).validateParams("42", null, 1, "Identity Type"));
  }

  /**
   * Test {@link AddIdentityLinkCmd#validateParams(String, String, int, String)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#validateParams(String, String, int, String)}
   */
  @Test
  public void testValidateParams_whenOne_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AddIdentityLinkCmd("42", "42", 1, "Identity Type")).validateParams("42", "42", 1, null));
  }

  /**
   * Test {@link AddIdentityLinkCmd#validateParams(String, String, int, String)}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#validateParams(String, String, int, String)}
   */
  @Test
  public void testValidateParams_whenThree_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AddIdentityLinkCmd("42", "42", 1, "Identity Type")).validateParams("42", "42", 3, "Identity Type"));
  }

  /**
   * Test {@link AddIdentityLinkCmd#validateParams(String, String, int, String)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkCmd#validateParams(String, String, int, String)}
   */
  @Test
  public void testValidateParams_whenTwo_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AddIdentityLinkCmd("42", "42", 1, "Identity Type")).validateParams("42", null, 2, "Identity Type"));
  }
}
