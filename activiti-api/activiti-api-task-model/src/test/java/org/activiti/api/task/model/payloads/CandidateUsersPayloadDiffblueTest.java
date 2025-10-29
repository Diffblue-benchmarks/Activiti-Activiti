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

class CandidateUsersPayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CandidateUsersPayload#setCandidateUsers(List)}
   *   <li>{@link CandidateUsersPayload#setTaskId(String)}
   *   <li>{@link CandidateUsersPayload#getCandidateUsers()}
   *   <li>{@link CandidateUsersPayload#getId()}
   *   <li>{@link CandidateUsersPayload#getTaskId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    candidateUsersPayload.setCandidateUsers(candidateUsers);
    candidateUsersPayload.setTaskId("42");
    List<String> actualCandidateUsers = candidateUsersPayload.getCandidateUsers();
    candidateUsersPayload.getId();

    // Assert that nothing has changed
    assertEquals("42", candidateUsersPayload.getTaskId());
    assertTrue(actualCandidateUsers.isEmpty());
    assertSame(candidateUsers, actualCandidateUsers);
  }

  /**
   * Method under test: {@link CandidateUsersPayload#CandidateUsersPayload()}
   */
  @Test
  void testNewCandidateUsersPayload() {
    // Arrange and Act
    CandidateUsersPayload actualCandidateUsersPayload = new CandidateUsersPayload();

    // Assert
    assertNull(actualCandidateUsersPayload.getTaskId());
    assertNull(actualCandidateUsersPayload.getCandidateUsers());
  }

  /**
   * Method under test:
   * {@link CandidateUsersPayload#CandidateUsersPayload(String, List)}
   */
  @Test
  void testNewCandidateUsersPayload2() {
    // Arrange
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CandidateUsersPayload actualCandidateUsersPayload = new CandidateUsersPayload("42", candidateUsers);

    // Assert
    assertEquals("42", actualCandidateUsersPayload.getTaskId());
    List<String> candidateUsers2 = actualCandidateUsersPayload.getCandidateUsers();
    assertTrue(candidateUsers2.isEmpty());
    assertSame(candidateUsers, candidateUsers2);
  }

  /**
   * Method under test:
   * {@link CandidateUsersPayload#CandidateUsersPayload(String, List)}
   */
  @Test
  void testNewCandidateUsersPayload3() {
    // Arrange
    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("foo");

    // Act
    CandidateUsersPayload actualCandidateUsersPayload = new CandidateUsersPayload("42", candidateUsers);

    // Assert
    assertEquals("42", actualCandidateUsersPayload.getTaskId());
    List<String> candidateUsers2 = actualCandidateUsersPayload.getCandidateUsers();
    assertEquals(1, candidateUsers2.size());
    assertEquals("foo", candidateUsers2.get(0));
    assertSame(candidateUsers, candidateUsers2);
  }

  /**
   * Method under test:
   * {@link CandidateUsersPayload#CandidateUsersPayload(String, List)}
   */
  @Test
  void testNewCandidateUsersPayload4() {
    // Arrange
    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("42");
    candidateUsers.add("foo");

    // Act
    CandidateUsersPayload actualCandidateUsersPayload = new CandidateUsersPayload("42", candidateUsers);

    // Assert
    assertEquals("42", actualCandidateUsersPayload.getTaskId());
    assertSame(candidateUsers, actualCandidateUsersPayload.getCandidateUsers());
  }
}
