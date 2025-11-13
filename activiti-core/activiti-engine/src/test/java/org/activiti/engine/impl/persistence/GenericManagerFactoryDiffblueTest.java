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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.Session;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class GenericManagerFactoryDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link GenericManagerFactory#GenericManagerFactory(Class)}
   *   <li>{@link GenericManagerFactory#getSessionType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void GenericManagerFactory.<init>(Class)",
    "void GenericManagerFactory.<init>(Class, Class)",
    "Class GenericManagerFactory.getSessionType()"
  })
  public void testGettersAndSetters() {
    // Arrange
    Class<Session> implementationClass = Session.class;

    // Act
    Class<?> actualSessionType = new GenericManagerFactory(implementationClass).getSessionType();

    // Assert
    Class<Session> expectedSessionType = Session.class;
    assertEquals(expectedSessionType, actualSessionType);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link GenericManagerFactory#GenericManagerFactory(Class, Class)}
   *   <li>{@link GenericManagerFactory#getSessionType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void GenericManagerFactory.<init>(Class)",
    "void GenericManagerFactory.<init>(Class, Class)",
    "Class GenericManagerFactory.getSessionType()"
  })
  public void testGettersAndSetters2() {
    // Arrange
    Class<Session> typeClass = Session.class;
    Class<Session> implementationClass = Session.class;

    // Act
    Class<?> actualSessionType =
        new GenericManagerFactory(typeClass, implementationClass).getSessionType();

    // Assert
    Class<Session> expectedSessionType = Session.class;
    assertEquals(expectedSessionType, actualSessionType);
  }

  /**
   * Test {@link GenericManagerFactory#openSession(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link GenericManagerFactory#openSession(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Session GenericManagerFactory.openSession(CommandContext)"})
  public void testOpenSession_thenThrowActivitiException() {
    // Arrange
    Class<Session> implementationClass = Session.class;

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new GenericManagerFactory(implementationClass).openSession(null));
  }
}
