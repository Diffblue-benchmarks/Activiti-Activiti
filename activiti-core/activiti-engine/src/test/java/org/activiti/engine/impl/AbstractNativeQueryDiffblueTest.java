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
package org.activiti.engine.impl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractNativeQueryDiffblueTest {
  @InjectMocks
  private NativeDeploymentQueryImpl nativeDeploymentQueryImpl;

  /**
   * Test {@link AbstractNativeQuery#sql(String)}.
   * <p>
   * Method under test: {@link AbstractNativeQuery#sql(String)}
   */
  @Test
  public void testSql() {
    // Arrange, Act and Assert
    assertSame(nativeDeploymentQueryImpl, nativeDeploymentQueryImpl.sql("Sql Statement"));
  }

  /**
   * Test {@link AbstractNativeQuery#parameter(String, Object)}.
   * <p>
   * Method under test: {@link AbstractNativeQuery#parameter(String, Object)}
   */
  @Test
  public void testParameter() {
    // Arrange, Act and Assert
    assertSame(nativeDeploymentQueryImpl, nativeDeploymentQueryImpl.parameter("Name", JSONObject.NULL));
  }

  /**
   * Test {@link AbstractNativeQuery#getParameters()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractNativeQuery#getParameters()}
   */
  @Test
  public void testGetParameters_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new NativeDeploymentQueryImpl((CommandContext) null)).getParameters().isEmpty());
  }
}
