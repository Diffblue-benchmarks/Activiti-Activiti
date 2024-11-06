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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ErrorEndEventActivityBehaviorDiffblueTest {
  @InjectMocks
  private ErrorEndEventActivityBehavior errorEndEventActivityBehavior;

  @InjectMocks
  private String string;

  /**
   * Test
   * {@link ErrorEndEventActivityBehavior#ErrorEndEventActivityBehavior(String)}.
   * <p>
   * Method under test:
   * {@link ErrorEndEventActivityBehavior#ErrorEndEventActivityBehavior(String)}
   */
  @Test
  public void testNewErrorEndEventActivityBehavior() {
    // Arrange, Act and Assert
    assertEquals("An error occurred", (new ErrorEndEventActivityBehavior("An error occurred")).getErrorRef());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ErrorEndEventActivityBehavior#setErrorRef(String)}
   *   <li>{@link ErrorEndEventActivityBehavior#getErrorRef()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ErrorEndEventActivityBehavior errorEndEventActivityBehavior = new ErrorEndEventActivityBehavior(
        "An error occurred");

    // Act
    errorEndEventActivityBehavior.setErrorRef("An error occurred");

    // Assert that nothing has changed
    assertEquals("An error occurred", errorEndEventActivityBehavior.getErrorRef());
  }
}
