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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivitiClassLoadingExceptionDiffblueTest {
  @InjectMocks
  private ActivitiClassLoadingException activitiClassLoadingException;

  @InjectMocks
  private String string;

  @InjectMocks
  private Throwable throwable;

  /**
   * Test
   * {@link ActivitiClassLoadingException#ActivitiClassLoadingException(String, Throwable)}.
   * <p>
   * Method under test:
   * {@link ActivitiClassLoadingException#ActivitiClassLoadingException(String, Throwable)}
   */
  @Test
  public void testNewActivitiClassLoadingException() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ActivitiClassLoadingException actualActivitiClassLoadingException = new ActivitiClassLoadingException("Class Name",
        cause);

    // Assert
    assertEquals("Class Name", actualActivitiClassLoadingException.getClassName());
    assertEquals("Could not load class: Class Name", actualActivitiClassLoadingException.getLocalizedMessage());
    assertEquals("Could not load class: Class Name", actualActivitiClassLoadingException.getMessage());
    assertEquals(0, actualActivitiClassLoadingException.getSuppressed().length);
    assertSame(cause, actualActivitiClassLoadingException.getCause());
  }

  /**
   * Test {@link ActivitiClassLoadingException#getClassName()}.
   * <p>
   * Method under test: {@link ActivitiClassLoadingException#getClassName()}
   */
  @Test
  public void testGetClassName() {
    // Arrange, Act and Assert
    assertEquals("Class Name", (new ActivitiClassLoadingException("Class Name", new Throwable())).getClassName());
  }
}
