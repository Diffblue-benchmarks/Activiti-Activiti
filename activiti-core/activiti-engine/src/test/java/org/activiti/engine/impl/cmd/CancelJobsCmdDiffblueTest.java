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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CancelJobsCmdDiffblueTest {
  @InjectMocks
  private CancelJobsCmd cancelJobsCmd;

  /**
   * Test {@link CancelJobsCmd#CancelJobsCmd(List)}.
   * <p>
   * Method under test: {@link CancelJobsCmd#CancelJobsCmd(List)}
   */
  @Test
  public void testNewCancelJobsCmd() {
    // Arrange, Act and Assert
    assertTrue((new CancelJobsCmd(new ArrayList<>())).jobIds.isEmpty());
  }

  /**
   * Test {@link CancelJobsCmd#CancelJobsCmd(String)}.
   * <p>
   * Method under test: {@link CancelJobsCmd#CancelJobsCmd(String)}
   */
  @Test
  public void testNewCancelJobsCmd2() {
    // Arrange, Act and Assert
    List<String> stringList = (new CancelJobsCmd("42")).jobIds;
    assertEquals(1, stringList.size());
    assertEquals("42", stringList.get(0));
  }
}
