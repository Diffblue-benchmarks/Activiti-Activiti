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
package org.activiti.engine.impl.delegate;

import static org.junit.Assert.assertTrue;
import org.activiti.engine.test.bpmn.event.message.MessageThrowEventTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ThrowMessageDelegateFactoryDiffblueTest {
  @InjectMocks
  private MessageThrowEventTest.MyThrowMessageDelegateFactory myThrowMessageDelegateFactory;

  /**
   * Test {@link ThrowMessageDelegateFactory#create()}.
   * <p>
   * Method under test: {@link ThrowMessageDelegateFactory#create()}
   */
  @Test
  public void testCreate() {
    // Arrange, Act and Assert
    assertTrue(myThrowMessageDelegateFactory.create() instanceof DefaultThrowMessageJavaDelegate);
  }
}
