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
package org.activiti.api.task.model.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.task.model.payloads.CandidateUsersPayload;
import org.junit.jupiter.api.Test;

class CandidateUsersPayloadBuilderDiffblueTest {
  /**
   * Method under test:
   * {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  void testWithCandidateUsers() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CandidateUsersPayloadBuilder actualWithCandidateUsersResult = addCandidateUsersResult
        .withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, addCandidateUsersResult.build().getCandidateUsers());
    assertSame(addCandidateUsersResult, actualWithCandidateUsersResult);
  }

  /**
   * Method under test:
   * {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  void testWithCandidateUsers2() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    // Act and Assert
    assertSame(addCandidateUsersResult, addCandidateUsersResult.withCandidateUsers(null));
  }

  /**
   * Method under test:
   * {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  void testWithCandidateUsers3() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("foo");

    // Act
    CandidateUsersPayloadBuilder actualWithCandidateUsersResult = addCandidateUsersResult
        .withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, addCandidateUsersResult.build().getCandidateUsers());
    assertSame(addCandidateUsersResult, actualWithCandidateUsersResult);
  }

  /**
   * Method under test:
   * {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  void testWithCandidateUsers4() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("42");
    candidateUsers.add("foo");

    // Act
    CandidateUsersPayloadBuilder actualWithCandidateUsersResult = addCandidateUsersResult
        .withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, addCandidateUsersResult.build().getCandidateUsers());
    assertSame(addCandidateUsersResult, actualWithCandidateUsersResult);
  }

  /**
   * Method under test:
   * {@link CandidateUsersPayloadBuilder#withCandidateUser(String)}
   */
  @Test
  void testWithCandidateUser() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    // Act and Assert
    assertSame(addCandidateUsersResult, addCandidateUsersResult.withCandidateUser("2020-03-01"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CandidateUsersPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link CandidateUsersPayloadBuilder}
   *   <li>{@link CandidateUsersPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  void testBuild() {
    // Arrange
    CandidateUsersPayloadBuilder candidateUsersPayloadBuilder = new CandidateUsersPayloadBuilder();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CandidateUsersPayload actualBuildResult = candidateUsersPayloadBuilder.withCandidateUsers(candidateUsers)
        .withTaskId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getTaskId());
    List<String> candidateUsers2 = actualBuildResult.getCandidateUsers();
    assertTrue(candidateUsers2.isEmpty());
    assertSame(candidateUsers, candidateUsers2);
  }
}
