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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class JvmUtilDiffblueTest {
  /**
   * Test {@link JvmUtil#getJavaVersion()}.
   * <p>
   * Method under test: {@link JvmUtil#getJavaVersion()}
   */
  @Test
  public void testGetJavaVersion() {
    // Arrange, Act and Assert
    assertEquals(System.getProperty("java.version"), JvmUtil.getJavaVersion());
  }

  /**
   * Test {@link JvmUtil#isJDK8()}.
   * <p>
   * Method under test: {@link JvmUtil#isJDK8()}
   */
  @Test
  public void testIsJDK8() {
    // Arrange, Act and Assert
    assertFalse(JvmUtil.isJDK8());
  }

  /**
   * Test {@link JvmUtil#isJDK7()}.
   * <p>
   * Method under test: {@link JvmUtil#isJDK7()}
   */
  @Test
  public void testIsJDK7() {
    // Arrange, Act and Assert
    assertFalse(JvmUtil.isJDK7());
  }

  /**
   * Test {@link JvmUtil#isAtLeastJDK7()}.
   * <p>
   * Method under test: {@link JvmUtil#isAtLeastJDK7()}
   */
  @Test
  public void testIsAtLeastJDK7() {
    // Arrange, Act and Assert
    assertFalse(JvmUtil.isAtLeastJDK7());
  }
}
