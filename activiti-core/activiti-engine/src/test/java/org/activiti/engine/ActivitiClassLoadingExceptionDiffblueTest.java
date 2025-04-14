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
package org.activiti.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitiClassLoadingExceptionDiffblueTest {
  /**
   * Test {@link ActivitiClassLoadingException#ActivitiClassLoadingException(String, Throwable)}.
   * <p>
   * Method under test: {@link ActivitiClassLoadingException#ActivitiClassLoadingException(String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ActivitiClassLoadingException.<init>(String, Throwable)"})
  public void testNewActivitiClassLoadingException() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ActivitiClassLoadingException actualActivitiClassLoadingException = new ActivitiClassLoadingException("Class Name",
        cause);

    // Assert
    assertEquals("Could not load class: Class Name", actualActivitiClassLoadingException.getLocalizedMessage());
    assertEquals("Could not load class: Class Name", actualActivitiClassLoadingException.getMessage());
    assertSame(cause, actualActivitiClassLoadingException.getCause());
  }

  /**
   * Test {@link ActivitiClassLoadingException#ActivitiClassLoadingException(String, Throwable)}.
   * <p>
   * Method under test: {@link ActivitiClassLoadingException#ActivitiClassLoadingException(String, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ActivitiClassLoadingException.<init>(String, Throwable)"})
  public void testNewActivitiClassLoadingException2() {
    // Arrange
    ClassNotFoundException cause = new ClassNotFoundException();

    // Act
    ActivitiClassLoadingException actualActivitiClassLoadingException = new ActivitiClassLoadingException("Class Name",
        cause);

    // Assert
    assertEquals("Class not found: Class Name", actualActivitiClassLoadingException.getLocalizedMessage());
    assertEquals("Class not found: Class Name", actualActivitiClassLoadingException.getMessage());
    assertSame(cause, actualActivitiClassLoadingException.getCause());
  }

  /**
   * Test {@link ActivitiClassLoadingException#getClassName()}.
   * <p>
   * Method under test: {@link ActivitiClassLoadingException#getClassName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ActivitiClassLoadingException.getClassName()"})
  public void testGetClassName() {
    // Arrange, Act and Assert
    assertEquals("Class Name", (new ActivitiClassLoadingException("Class Name", new Throwable())).getClassName());
  }
}
