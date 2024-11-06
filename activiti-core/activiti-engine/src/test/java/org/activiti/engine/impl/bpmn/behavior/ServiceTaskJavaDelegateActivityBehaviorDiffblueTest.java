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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import org.activiti.engine.delegate.JavaDelegate;
import org.junit.Test;

public class ServiceTaskJavaDelegateActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link ServiceTaskJavaDelegateActivityBehavior#ServiceTaskJavaDelegateActivityBehavior()}.
   * <p>
   * Method under test:
   * {@link ServiceTaskJavaDelegateActivityBehavior#ServiceTaskJavaDelegateActivityBehavior()}
   */
  @Test
  public void testNewServiceTaskJavaDelegateActivityBehavior() {
    // Arrange and Act
    ServiceTaskJavaDelegateActivityBehavior actualServiceTaskJavaDelegateActivityBehavior = new ServiceTaskJavaDelegateActivityBehavior();

    // Assert
    assertNull(actualServiceTaskJavaDelegateActivityBehavior.javaDelegate);
    assertNull(actualServiceTaskJavaDelegateActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualServiceTaskJavaDelegateActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualServiceTaskJavaDelegateActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test
   * {@link ServiceTaskJavaDelegateActivityBehavior#ServiceTaskJavaDelegateActivityBehavior(JavaDelegate)}.
   * <p>
   * Method under test:
   * {@link ServiceTaskJavaDelegateActivityBehavior#ServiceTaskJavaDelegateActivityBehavior(JavaDelegate)}
   */
  @Test
  public void testNewServiceTaskJavaDelegateActivityBehavior2() {
    // Arrange and Act
    ServiceTaskJavaDelegateActivityBehavior actualServiceTaskJavaDelegateActivityBehavior = new ServiceTaskJavaDelegateActivityBehavior(
        mock(JavaDelegate.class));

    // Assert
    assertNull(actualServiceTaskJavaDelegateActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualServiceTaskJavaDelegateActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualServiceTaskJavaDelegateActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
