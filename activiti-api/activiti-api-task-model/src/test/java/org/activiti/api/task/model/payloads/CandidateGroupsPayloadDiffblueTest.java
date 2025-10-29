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
package org.activiti.api.task.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CandidateGroupsPayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CandidateGroupsPayload#setCandidateGroups(List)}
   *   <li>{@link CandidateGroupsPayload#setTaskId(String)}
   *   <li>{@link CandidateGroupsPayload#getCandidateGroups()}
   *   <li>{@link CandidateGroupsPayload#getId()}
   *   <li>{@link CandidateGroupsPayload#getTaskId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    CandidateGroupsPayload candidateGroupsPayload = new CandidateGroupsPayload();
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act
    candidateGroupsPayload.setCandidateGroups(candidateGroups);
    candidateGroupsPayload.setTaskId("42");
    List<String> actualCandidateGroups = candidateGroupsPayload.getCandidateGroups();
    candidateGroupsPayload.getId();

    // Assert that nothing has changed
    assertEquals("42", candidateGroupsPayload.getTaskId());
    assertTrue(actualCandidateGroups.isEmpty());
    assertSame(candidateGroups, actualCandidateGroups);
  }

  /**
   * Method under test: {@link CandidateGroupsPayload#CandidateGroupsPayload()}
   */
  @Test
  void testNewCandidateGroupsPayload() {
    // Arrange and Act
    CandidateGroupsPayload actualCandidateGroupsPayload = new CandidateGroupsPayload();

    // Assert
    assertNull(actualCandidateGroupsPayload.getTaskId());
    assertNull(actualCandidateGroupsPayload.getCandidateGroups());
  }

  /**
   * Method under test:
   * {@link CandidateGroupsPayload#CandidateGroupsPayload(String, List)}
   */
  @Test
  void testNewCandidateGroupsPayload2() {
    // Arrange
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act
    CandidateGroupsPayload actualCandidateGroupsPayload = new CandidateGroupsPayload("42", candidateGroups);

    // Assert
    assertEquals("42", actualCandidateGroupsPayload.getTaskId());
    List<String> candidateGroups2 = actualCandidateGroupsPayload.getCandidateGroups();
    assertTrue(candidateGroups2.isEmpty());
    assertSame(candidateGroups, candidateGroups2);
  }

  /**
   * Method under test:
   * {@link CandidateGroupsPayload#CandidateGroupsPayload(String, List)}
   */
  @Test
  void testNewCandidateGroupsPayload3() {
    // Arrange
    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("foo");

    // Act
    CandidateGroupsPayload actualCandidateGroupsPayload = new CandidateGroupsPayload("42", candidateGroups);

    // Assert
    assertEquals("42", actualCandidateGroupsPayload.getTaskId());
    List<String> candidateGroups2 = actualCandidateGroupsPayload.getCandidateGroups();
    assertEquals(1, candidateGroups2.size());
    assertEquals("foo", candidateGroups2.get(0));
    assertSame(candidateGroups, candidateGroups2);
  }

  /**
   * Method under test:
   * {@link CandidateGroupsPayload#CandidateGroupsPayload(String, List)}
   */
  @Test
  void testNewCandidateGroupsPayload4() {
    // Arrange
    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("foo");

    // Act
    CandidateGroupsPayload actualCandidateGroupsPayload = new CandidateGroupsPayload("42", candidateGroups);

    // Assert
    assertEquals("42", actualCandidateGroupsPayload.getTaskId());
    assertSame(candidateGroups, actualCandidateGroupsPayload.getCandidateGroups());
  }
}
