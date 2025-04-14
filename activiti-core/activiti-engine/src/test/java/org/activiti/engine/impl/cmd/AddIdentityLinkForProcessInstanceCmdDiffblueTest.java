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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AddIdentityLinkForProcessInstanceCmdDiffblueTest {
  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String)}.
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"})
  public void testNewAddIdentityLinkForProcessInstanceCmd() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkForProcessInstanceCmd(null, null, null, null));

  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String)}.
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"})
  public void testNewAddIdentityLinkForProcessInstanceCmd2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkForProcessInstanceCmd("42", null, null, null));

  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String)}.
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"})
  public void testNewAddIdentityLinkForProcessInstanceCmd3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkForProcessInstanceCmd("42", null, null, "Type"));

  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String, byte[])}.
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String, byte[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String, byte[])"})
  public void testNewAddIdentityLinkForProcessInstanceCmd4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new AddIdentityLinkForProcessInstanceCmd(null, null,
        null, null, new byte[]{'A', 'X', 'A', 'X', 'A', 'X', 'A', 'X'}));

  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String, byte[])}.
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String, byte[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String, byte[])"})
  public void testNewAddIdentityLinkForProcessInstanceCmd5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new AddIdentityLinkForProcessInstanceCmd("42", null,
        null, null, new byte[]{'A', 'X', 'A', 'X', 'A', 'X', 'A', 'X'}));

  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String, byte[])}.
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String, byte[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String, byte[])"})
  public void testNewAddIdentityLinkForProcessInstanceCmd6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new AddIdentityLinkForProcessInstanceCmd("42", null,
        null, "Type", new byte[]{'A', 'X', 'A', 'X', 'A', 'X', 'A', 'X'}));

  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String, byte[])}.
   * <ul>
   *   <li>Then return {@link AddIdentityLinkForProcessInstanceCmd#userId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String, byte[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String, byte[])"})
  public void testNewAddIdentityLinkForProcessInstanceCmd_thenReturnUserIdIs42() throws UnsupportedEncodingException {
    // Arrange and Act
    AddIdentityLinkForProcessInstanceCmd actualAddIdentityLinkForProcessInstanceCmd = new AddIdentityLinkForProcessInstanceCmd(
        "42", "42", "42", "Type", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.userId);
    assertEquals("Type", actualAddIdentityLinkForProcessInstanceCmd.type);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualAddIdentityLinkForProcessInstanceCmd.details);
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then return {@link AddIdentityLinkForProcessInstanceCmd#userId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"})
  public void testNewAddIdentityLinkForProcessInstanceCmd_whenType_thenReturnUserIdIs42() {
    // Arrange and Act
    AddIdentityLinkForProcessInstanceCmd actualAddIdentityLinkForProcessInstanceCmd = new AddIdentityLinkForProcessInstanceCmd(
        "42", "42", "42", "Type");

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.userId);
    assertEquals("Type", actualAddIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualAddIdentityLinkForProcessInstanceCmd.details);
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then return {@link AddIdentityLinkForProcessInstanceCmd#userId} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"})
  public void testNewAddIdentityLinkForProcessInstanceCmd_whenType_thenReturnUserIdIsNull() {
    // Arrange and Act
    AddIdentityLinkForProcessInstanceCmd actualAddIdentityLinkForProcessInstanceCmd = new AddIdentityLinkForProcessInstanceCmd(
        "42", null, "42", "Type");

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("Type", actualAddIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualAddIdentityLinkForProcessInstanceCmd.details);
    assertNull(actualAddIdentityLinkForProcessInstanceCmd.userId);
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String, byte[])}.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then return {@link AddIdentityLinkForProcessInstanceCmd#userId} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String, String, String, byte[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String, byte[])"})
  public void testNewAddIdentityLinkForProcessInstanceCmd_whenType_thenReturnUserIdIsNull2()
      throws UnsupportedEncodingException {
    // Arrange and Act
    AddIdentityLinkForProcessInstanceCmd actualAddIdentityLinkForProcessInstanceCmd = new AddIdentityLinkForProcessInstanceCmd(
        "42", null, "42", "Type", new byte[]{'A', 'X', 'A', 'X', 'A', 'X', 'A', 'X'});

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("Type", actualAddIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualAddIdentityLinkForProcessInstanceCmd.userId);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualAddIdentityLinkForProcessInstanceCmd.details);
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String, String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"})
  public void testValidateParams_when42_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AddIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type")).validateParams("42", null, null,
            null));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"})
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AddIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type")).validateParams(null, null, null,
            null));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String, String)}.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AddIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"})
  public void testValidateParams_whenType_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AddIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type")).validateParams("42", null, null,
            "Type"));
  }
}
