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
package org.activiti.engine.test.profiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommandExecutionResultDiffblueTest {
  @InjectMocks
  private CommandExecutionResult commandExecutionResult;

  /**
   * Test {@link CommandExecutionResult#addDbSelect(String)}.
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbSelect(String)}
   */
  @Test
  public void testAddDbSelect() {
    // Arrange and Act
    commandExecutionResult.addDbSelect("Select");

    // Assert
    assertEquals(1, commandExecutionResult.getDbSelects().size());
  }

  /**
   * Test {@link CommandExecutionResult#addDbInsert(String)}.
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbInsert(String)}
   */
  @Test
  public void testAddDbInsert() {
    // Arrange and Act
    commandExecutionResult.addDbInsert("Insert");

    // Assert
    assertEquals(1, commandExecutionResult.getDbInserts().size());
  }

  /**
   * Test {@link CommandExecutionResult#addDbUpdate(String)}.
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbUpdate(String)}
   */
  @Test
  public void testAddDbUpdate() {
    // Arrange and Act
    commandExecutionResult.addDbUpdate("2020-03-01");

    // Assert
    assertEquals(1, commandExecutionResult.getDbUpdates().size());
  }

  /**
   * Test {@link CommandExecutionResult#addDbDelete(String)}.
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbDelete(String)}
   */
  @Test
  public void testAddDbDelete() {
    // Arrange and Act
    commandExecutionResult.addDbDelete("Delete");

    // Assert
    assertEquals(1, commandExecutionResult.getDbDeletes().size());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CommandExecutionResult}
   *   <li>{@link CommandExecutionResult#setCommandFqn(String)}
   *   <li>{@link CommandExecutionResult#setDatabaseTimeInMs(long)}
   *   <li>{@link CommandExecutionResult#setDbDeletes(Map)}
   *   <li>{@link CommandExecutionResult#setDbInserts(Map)}
   *   <li>{@link CommandExecutionResult#setDbSelects(Map)}
   *   <li>{@link CommandExecutionResult#setDbUpdates(Map)}
   *   <li>{@link CommandExecutionResult#setTotalTimeInMs(long)}
   *   <li>{@link CommandExecutionResult#addDatabaseTime(long)}
   *   <li>{@link CommandExecutionResult#getCommandFqn()}
   *   <li>{@link CommandExecutionResult#getDatabaseTimeInMs()}
   *   <li>{@link CommandExecutionResult#getDbDeletes()}
   *   <li>{@link CommandExecutionResult#getDbInserts()}
   *   <li>{@link CommandExecutionResult#getDbSelects()}
   *   <li>{@link CommandExecutionResult#getDbUpdates()}
   *   <li>{@link CommandExecutionResult#getTotalTimeInMs()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    CommandExecutionResult actualCommandExecutionResult = new CommandExecutionResult();
    actualCommandExecutionResult.setCommandFqn("Command Fqn");
    actualCommandExecutionResult.setDatabaseTimeInMs(1L);
    HashMap<String, Long> dbDeletes = new HashMap<>();
    actualCommandExecutionResult.setDbDeletes(dbDeletes);
    HashMap<String, Long> dbInserts = new HashMap<>();
    actualCommandExecutionResult.setDbInserts(dbInserts);
    HashMap<String, Long> dbSelects = new HashMap<>();
    actualCommandExecutionResult.setDbSelects(dbSelects);
    HashMap<String, Long> dbUpdates = new HashMap<>();
    actualCommandExecutionResult.setDbUpdates(dbUpdates);
    actualCommandExecutionResult.setTotalTimeInMs(1L);
    actualCommandExecutionResult.addDatabaseTime(10L);
    String actualCommandFqn = actualCommandExecutionResult.getCommandFqn();
    long actualDatabaseTimeInMs = actualCommandExecutionResult.getDatabaseTimeInMs();
    Map<String, Long> actualDbDeletes = actualCommandExecutionResult.getDbDeletes();
    Map<String, Long> actualDbInserts = actualCommandExecutionResult.getDbInserts();
    Map<String, Long> actualDbSelects = actualCommandExecutionResult.getDbSelects();
    Map<String, Long> actualDbUpdates = actualCommandExecutionResult.getDbUpdates();

    // Assert that nothing has changed
    assertEquals("Command Fqn", actualCommandFqn);
    assertEquals(11L, actualDatabaseTimeInMs);
    assertEquals(1L, actualCommandExecutionResult.getTotalTimeInMs());
    assertTrue(actualDbDeletes.isEmpty());
    assertTrue(actualDbInserts.isEmpty());
    assertTrue(actualDbSelects.isEmpty());
    assertTrue(actualDbUpdates.isEmpty());
    assertSame(dbDeletes, actualDbDeletes);
    assertSame(dbInserts, actualDbInserts);
    assertSame(dbSelects, actualDbSelects);
    assertSame(dbUpdates, actualDbUpdates);
  }
}
