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
package org.activiti.engine.impl.db;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.Test;
import org.mockito.Mockito;

public class BulkDeleteOperationDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BulkDeleteOperation#BulkDeleteOperation(String, Object)}
   *   <li>{@link BulkDeleteOperation#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("bulk delete: MD(null)", (new BulkDeleteOperation("MD", JSONObject.NULL)).toString());
  }

  /**
   * Test {@link BulkDeleteOperation#execute(SqlSession)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link DefaultSqlSession}
   * {@link DefaultSqlSession#delete(String, Object)} return one.</li>
   *   <li>Then calls {@link DefaultSqlSession#delete(String, Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BulkDeleteOperation#execute(SqlSession)}
   */
  @Test
  public void testExecute_givenOne_whenDefaultSqlSessionDeleteReturnOne_thenCallsDelete() {
    // Arrange
    BulkDeleteOperation bulkDeleteOperation = new BulkDeleteOperation("MD", JSONObject.NULL);
    DefaultSqlSession sqlSession = mock(DefaultSqlSession.class);
    when(sqlSession.delete(Mockito.<String>any(), Mockito.<Object>any())).thenReturn(1);

    // Act
    bulkDeleteOperation.execute(sqlSession);

    // Assert that nothing has changed
    verify(sqlSession).delete(eq("MD"), isA(Object.class));
  }
}
