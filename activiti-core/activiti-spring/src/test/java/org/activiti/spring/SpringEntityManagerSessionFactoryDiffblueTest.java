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
package org.activiti.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.junit.Test;

public class SpringEntityManagerSessionFactoryDiffblueTest {
  /**
   * Method under test:
   * {@link SpringEntityManagerSessionFactory#SpringEntityManagerSessionFactory(Object, boolean, boolean)}
   */
  @Test
  public void testNewSpringEntityManagerSessionFactory() {
    // Arrange and Act
    SpringEntityManagerSessionFactory actualSpringEntityManagerSessionFactory = new SpringEntityManagerSessionFactory(
        null, true, true);

    // Assert
    assertNull(actualSpringEntityManagerSessionFactory.entityManagerFactory);
    assertTrue(actualSpringEntityManagerSessionFactory.closeEntityManager);
    assertTrue(actualSpringEntityManagerSessionFactory.handleTransactions);
    Class<EntityManagerFactory> expectedSessionType = EntityManagerFactory.class;
    assertEquals(expectedSessionType, actualSpringEntityManagerSessionFactory.getSessionType());
  }

  /**
   * Method under test:
   * {@link SpringEntityManagerSessionFactory#SpringEntityManagerSessionFactory(Object, boolean, boolean)}
   */
  @Test
  public void testNewSpringEntityManagerSessionFactory2() {
    // Arrange and Act
    SpringEntityManagerSessionFactory actualSpringEntityManagerSessionFactory = new SpringEntityManagerSessionFactory(
        mock(SessionFactoryDelegatingImpl.class), true, true);

    // Assert
    assertTrue(actualSpringEntityManagerSessionFactory.closeEntityManager);
    assertTrue(actualSpringEntityManagerSessionFactory.handleTransactions);
    Class<EntityManagerFactory> expectedSessionType = EntityManagerFactory.class;
    assertEquals(expectedSessionType, actualSpringEntityManagerSessionFactory.getSessionType());
  }
}
