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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ProcessEngineInfoImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessEngineInfoImpl#ProcessEngineInfoImpl(String, String, String)}
   *   <li>{@link ProcessEngineInfoImpl#getException()}
   *   <li>{@link ProcessEngineInfoImpl#getName()}
   *   <li>{@link ProcessEngineInfoImpl#getResourceUrl()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ProcessEngineInfoImpl actualProcessEngineInfoImpl = new ProcessEngineInfoImpl("Name", "https://example.org/example",
        "Exception");
    String actualException = actualProcessEngineInfoImpl.getException();
    String actualName = actualProcessEngineInfoImpl.getName();

    // Assert
    assertEquals("Exception", actualException);
    assertEquals("Name", actualName);
    assertEquals("https://example.org/example", actualProcessEngineInfoImpl.getResourceUrl());
  }
}