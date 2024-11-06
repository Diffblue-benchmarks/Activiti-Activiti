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
package org.activiti.engine.impl.persistence;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.Session;
import org.junit.Test;

public class GenericManagerFactoryDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GenericManagerFactory#GenericManagerFactory(Class)}
   *   <li>{@link GenericManagerFactory#getSessionType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Class<Session> implementationClass = Session.class;

    // Act
    GenericManagerFactory actualGenericManagerFactory = new GenericManagerFactory(implementationClass);

    // Assert
    assertSame(actualGenericManagerFactory.implementationClass, actualGenericManagerFactory.getSessionType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GenericManagerFactory#GenericManagerFactory(Class, Class)}
   *   <li>{@link GenericManagerFactory#getSessionType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters2() {
    // Arrange
    Class<Session> typeClass = Session.class;
    Class<Session> implementationClass = Session.class;

    // Act
    GenericManagerFactory actualGenericManagerFactory = new GenericManagerFactory(typeClass, implementationClass);

    // Assert
    assertSame(actualGenericManagerFactory.implementationClass, actualGenericManagerFactory.getSessionType());
  }

  /**
   * Test {@link GenericManagerFactory#openSession(CommandContext)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GenericManagerFactory#openSession(CommandContext)}
   */
  @Test
  public void testOpenSession_thenThrowActivitiException() {
    // Arrange
    Class<Session> implementationClass = Session.class;

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new GenericManagerFactory(implementationClass)).openSession(null));
  }
}
