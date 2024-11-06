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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CandidateUsersPayloadBuilderDiffblueTest {
  /**
   * Test {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List); given '42'; when ArrayList() add '42'")
  void testWithCandidateUsers_given42_whenArrayListAdd42() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("42");
    candidateUsers.add("foo");

    // Act and Assert
    assertSame(candidateUsers, addCandidateUsersResult.withCandidateUsers(candidateUsers).build().getCandidateUsers());
    assertSame(candidateUsers, addCandidateUsersResult.build().getCandidateUsers());
  }

  /**
   * Test {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return build CandidateUsers is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List); given 'foo'; then return build CandidateUsers is ArrayList()")
  void testWithCandidateUsers_givenFoo_thenReturnBuildCandidateUsersIsArrayList() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("foo");

    // Act and Assert
    assertSame(candidateUsers, addCandidateUsersResult.withCandidateUsers(candidateUsers).build().getCandidateUsers());
    assertSame(candidateUsers, addCandidateUsersResult.build().getCandidateUsers());
  }

  /**
   * Test {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return build CandidateUsers Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List); when ArrayList(); then return build CandidateUsers Empty")
  void testWithCandidateUsers_whenArrayList_thenReturnBuildCandidateUsersEmpty() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act and Assert
    assertTrue(addCandidateUsersResult.withCandidateUsers(candidateUsers).build().getCandidateUsers().isEmpty());
    assertSame(candidateUsers, addCandidateUsersResult.build().getCandidateUsers());
  }

  /**
   * Test {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return addCandidateUsers.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateUsersPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List); when 'null'; then return addCandidateUsers")
  void testWithCandidateUsers_whenNull_thenReturnAddCandidateUsers() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    // Act and Assert
    assertSame(addCandidateUsersResult, addCandidateUsersResult.withCandidateUsers(null));
  }

  /**
   * Test {@link CandidateUsersPayloadBuilder#withCandidateUser(String)}.
   * <p>
   * Method under test:
   * {@link CandidateUsersPayloadBuilder#withCandidateUser(String)}
   */
  @Test
  @DisplayName("Test withCandidateUser(String)")
  void testWithCandidateUser() {
    // Arrange
    CandidateUsersPayloadBuilder addCandidateUsersResult = TaskPayloadBuilder.addCandidateUsers();

    // Act and Assert
    assertSame(addCandidateUsersResult, addCandidateUsersResult.withCandidateUser("2020-03-01"));
  }

  /**
   * Test {@link CandidateUsersPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CandidateUsersPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link CandidateUsersPayloadBuilder}
   *   <li>{@link CandidateUsersPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
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
