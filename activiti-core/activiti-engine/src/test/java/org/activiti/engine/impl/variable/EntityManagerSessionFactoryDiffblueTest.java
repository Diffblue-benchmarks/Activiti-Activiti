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
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import jakarta.persistence.EntityManagerFactory;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.junit.Test;

public class EntityManagerSessionFactoryDiffblueTest {
  /**
   * Test
   * {@link EntityManagerSessionFactory#EntityManagerSessionFactory(Object, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link EntityManagerSessionFactory#EntityManagerSessionFactory(Object, boolean, boolean)}
   */
  @Test
  public void testNewEntityManagerSessionFactory() {
    // Arrange
    SessionFactoryDelegatingImpl sessionFactoryDelegatingImpl = new SessionFactoryDelegatingImpl(null);

    // Act
    EntityManagerSessionFactory actualEntityManagerSessionFactory = new EntityManagerSessionFactory(
        sessionFactoryDelegatingImpl, true, true);

    // Assert
    EntityManagerFactory entityManagerFactory = actualEntityManagerSessionFactory.getEntityManagerFactory();
    assertTrue(entityManagerFactory instanceof SessionFactoryDelegatingImpl);
    assertTrue(actualEntityManagerSessionFactory.closeEntityManager);
    assertTrue(actualEntityManagerSessionFactory.handleTransactions);
    Class<EntityManagerSession> expectedSessionType = EntityManagerSession.class;
    assertEquals(expectedSessionType, actualEntityManagerSessionFactory.getSessionType());
    assertSame(sessionFactoryDelegatingImpl, entityManagerFactory);
  }

  /**
   * Test
   * {@link EntityManagerSessionFactory#EntityManagerSessionFactory(Object, boolean, boolean)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EntityManagerSessionFactory#EntityManagerSessionFactory(Object, boolean, boolean)}
   */
  @Test
  public void testNewEntityManagerSessionFactory_whenNull() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new EntityManagerSessionFactory(JSONObject.NULL, true, true));

    assertThrows(ActivitiIllegalArgumentException.class, () -> new EntityManagerSessionFactory(null, true, true));
  }
}
