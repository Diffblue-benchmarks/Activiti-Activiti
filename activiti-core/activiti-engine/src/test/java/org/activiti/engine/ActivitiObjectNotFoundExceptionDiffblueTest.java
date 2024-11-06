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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ActivitiObjectNotFoundExceptionDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code An error occurred}.</li>
   *   <li>Then return Message is {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ActivitiObjectNotFoundException#ActivitiObjectNotFoundException(String, Class)}
   *   <li>{@link ActivitiObjectNotFoundException#getObjectClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenAnErrorOccurred_thenReturnMessageIsAnErrorOccurred() {
    // Arrange
    Class<Object> objectClass = Object.class;

    // Act
    ActivitiObjectNotFoundException actualActivitiObjectNotFoundException = new ActivitiObjectNotFoundException(
        "An error occurred", objectClass);
    Class<?> actualObjectClass = actualActivitiObjectNotFoundException.getObjectClass();

    // Assert
    assertEquals("An error occurred", actualActivitiObjectNotFoundException.getMessage());
    assertNull(actualActivitiObjectNotFoundException.getCause());
    assertEquals(0, actualActivitiObjectNotFoundException.getSuppressed().length);
    Class<Object> expectedObjectClass = Object.class;
    assertEquals(expectedObjectClass, actualObjectClass);
    assertSame(objectClass, actualObjectClass);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code An error occurred}.</li>
   *   <li>Then return ObjectClass is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ActivitiObjectNotFoundException#ActivitiObjectNotFoundException(String)}
   *   <li>{@link ActivitiObjectNotFoundException#getObjectClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenAnErrorOccurred_thenReturnObjectClassIsNull() {
    // Arrange and Act
    ActivitiObjectNotFoundException actualActivitiObjectNotFoundException = new ActivitiObjectNotFoundException(
        "An error occurred");

    // Assert
    assertEquals("An error occurred", actualActivitiObjectNotFoundException.getMessage());
    assertNull(actualActivitiObjectNotFoundException.getObjectClass());
    assertNull(actualActivitiObjectNotFoundException.getCause());
    assertEquals(0, actualActivitiObjectNotFoundException.getSuppressed().length);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return Message is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ActivitiObjectNotFoundException#ActivitiObjectNotFoundException(Class)}
   *   <li>{@link ActivitiObjectNotFoundException#getObjectClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenJavaLangObject_thenReturnMessageIsNull() {
    // Arrange
    Class<Object> objectClass = Object.class;

    // Act
    ActivitiObjectNotFoundException actualActivitiObjectNotFoundException = new ActivitiObjectNotFoundException(
        objectClass);
    Class<?> actualObjectClass = actualActivitiObjectNotFoundException.getObjectClass();

    // Assert
    assertNull(actualActivitiObjectNotFoundException.getMessage());
    assertNull(actualActivitiObjectNotFoundException.getCause());
    assertEquals(0, actualActivitiObjectNotFoundException.getSuppressed().length);
    Class<Object> expectedObjectClass = Object.class;
    assertEquals(expectedObjectClass, actualObjectClass);
    assertSame(objectClass, actualObjectClass);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.</li>
   *   <li>Then return Cause is {@link Throwable#Throwable()}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ActivitiObjectNotFoundException#ActivitiObjectNotFoundException(String, Class, Throwable)}
   *   <li>{@link ActivitiObjectNotFoundException#getObjectClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Class<Object> objectClass = Object.class;
    Throwable cause = new Throwable();

    // Act
    ActivitiObjectNotFoundException actualActivitiObjectNotFoundException = new ActivitiObjectNotFoundException(
        "An error occurred", objectClass, cause);
    Class<?> actualObjectClass = actualActivitiObjectNotFoundException.getObjectClass();

    // Assert
    assertEquals("An error occurred", actualActivitiObjectNotFoundException.getMessage());
    assertEquals(0, actualActivitiObjectNotFoundException.getSuppressed().length);
    Class<Object> expectedObjectClass = Object.class;
    assertEquals(expectedObjectClass, actualObjectClass);
    assertSame(cause, actualActivitiObjectNotFoundException.getCause());
    assertSame(objectClass, actualObjectClass);
  }
}
