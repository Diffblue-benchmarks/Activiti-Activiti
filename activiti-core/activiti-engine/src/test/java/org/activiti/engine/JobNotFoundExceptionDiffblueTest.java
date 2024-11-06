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
package org.activiti.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JobNotFoundExceptionDiffblueTest {
  @InjectMocks
  private JobNotFoundException jobNotFoundException;

  @InjectMocks
  private String string;

  /**
   * Test {@link JobNotFoundException#JobNotFoundException(String)}.
   * <p>
   * Method under test: {@link JobNotFoundException#JobNotFoundException(String)}
   */
  @Test
  public void testNewJobNotFoundException() {
    // Arrange and Act
    JobNotFoundException actualJobNotFoundException = new JobNotFoundException("42");

    // Assert
    assertEquals("42", actualJobNotFoundException.getJobId());
    assertEquals("No job found with id '42'.", actualJobNotFoundException.getLocalizedMessage());
    assertEquals("No job found with id '42'.", actualJobNotFoundException.getMessage());
    assertNull(actualJobNotFoundException.getCause());
    assertEquals(0, actualJobNotFoundException.getSuppressed().length);
    Class<Job> expectedObjectClass = Job.class;
    assertEquals(expectedObjectClass, actualJobNotFoundException.getObjectClass());
  }

  /**
   * Test {@link JobNotFoundException#getJobId()}.
   * <p>
   * Method under test: {@link JobNotFoundException#getJobId()}
   */
  @Test
  public void testGetJobId() {
    // Arrange, Act and Assert
    assertEquals("42", (new JobNotFoundException("42")).getJobId());
  }
}
