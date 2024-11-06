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
package org.activiti.engine.impl.interceptor;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RetryInterceptorDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RetryInterceptor}
   *   <li>{@link RetryInterceptor#setNumOfRetries(int)}
   *   <li>{@link RetryInterceptor#setWaitIncreaseFactor(int)}
   *   <li>{@link RetryInterceptor#setWaitTimeInMs(int)}
   *   <li>{@link RetryInterceptor#getNumOfRetries()}
   *   <li>{@link RetryInterceptor#getWaitIncreaseFactor()}
   *   <li>{@link RetryInterceptor#getWaitTimeInMs()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    RetryInterceptor actualRetryInterceptor = new RetryInterceptor();
    actualRetryInterceptor.setNumOfRetries(10);
    actualRetryInterceptor.setWaitIncreaseFactor(3);
    actualRetryInterceptor.setWaitTimeInMs(1);
    int actualNumOfRetries = actualRetryInterceptor.getNumOfRetries();
    int actualWaitIncreaseFactor = actualRetryInterceptor.getWaitIncreaseFactor();

    // Assert that nothing has changed
    assertEquals(1, actualRetryInterceptor.getWaitTimeInMs());
    assertEquals(10, actualNumOfRetries);
    assertEquals(3, actualWaitIncreaseFactor);
  }
}
