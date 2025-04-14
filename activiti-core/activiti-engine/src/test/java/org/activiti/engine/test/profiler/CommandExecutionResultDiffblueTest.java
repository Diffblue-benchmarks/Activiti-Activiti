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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CommandExecutionResultDiffblueTest {
  /**
   * Test {@link CommandExecutionResult#addDbSelect(String)}.
   * <ul>
   *   <li>Then {@link CommandExecutionResult} (default constructor) DbSelects {@code Select} longValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbSelect(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CommandExecutionResult.addDbSelect(String)"})
  public void testAddDbSelect_thenCommandExecutionResultDbSelectsSelectLongValueIsOne() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();

    // Act
    commandExecutionResult.addDbSelect("Select");

    // Assert
    Map<String, Long> dbSelects = commandExecutionResult.getDbSelects();
    assertEquals(1, dbSelects.size());
    assertEquals(1L, dbSelects.get("Select").longValue());
  }

  /**
   * Test {@link CommandExecutionResult#addDbSelect(String)}.
   * <ul>
   *   <li>Then {@link CommandExecutionResult} (default constructor) DbSelects {@code Select} longValue is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbSelect(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CommandExecutionResult.addDbSelect(String)"})
  public void testAddDbSelect_thenCommandExecutionResultDbSelectsSelectLongValueIsTwo() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.addDbSelect("Select");

    // Act
    commandExecutionResult.addDbSelect("Select");

    // Assert
    Map<String, Long> dbSelects = commandExecutionResult.getDbSelects();
    assertEquals(1, dbSelects.size());
    assertEquals(2L, dbSelects.get("Select").longValue());
  }

  /**
   * Test {@link CommandExecutionResult#addDbInsert(String)}.
   * <ul>
   *   <li>Then {@link CommandExecutionResult} (default constructor) DbInserts {@code Insert} longValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbInsert(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CommandExecutionResult.addDbInsert(String)"})
  public void testAddDbInsert_thenCommandExecutionResultDbInsertsInsertLongValueIsOne() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();

    // Act
    commandExecutionResult.addDbInsert("Insert");

    // Assert
    Map<String, Long> dbInserts = commandExecutionResult.getDbInserts();
    assertEquals(1, dbInserts.size());
    assertEquals(1L, dbInserts.get("Insert").longValue());
  }

  /**
   * Test {@link CommandExecutionResult#addDbInsert(String)}.
   * <ul>
   *   <li>Then {@link CommandExecutionResult} (default constructor) DbInserts {@code Insert} longValue is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbInsert(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CommandExecutionResult.addDbInsert(String)"})
  public void testAddDbInsert_thenCommandExecutionResultDbInsertsInsertLongValueIsTwo() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.addDbInsert("Insert");

    // Act
    commandExecutionResult.addDbInsert("Insert");

    // Assert
    Map<String, Long> dbInserts = commandExecutionResult.getDbInserts();
    assertEquals(1, dbInserts.size());
    assertEquals(2L, dbInserts.get("Insert").longValue());
  }

  /**
   * Test {@link CommandExecutionResult#addDbUpdate(String)}.
   * <ul>
   *   <li>Then {@link CommandExecutionResult} (default constructor) DbUpdates {@code 2020-03-01} longValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbUpdate(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CommandExecutionResult.addDbUpdate(String)"})
  public void testAddDbUpdate_thenCommandExecutionResultDbUpdates20200301LongValueIsOne() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();

    // Act
    commandExecutionResult.addDbUpdate("2020-03-01");

    // Assert
    Map<String, Long> dbUpdates = commandExecutionResult.getDbUpdates();
    assertEquals(1, dbUpdates.size());
    assertEquals(1L, dbUpdates.get("2020-03-01").longValue());
  }

  /**
   * Test {@link CommandExecutionResult#addDbUpdate(String)}.
   * <ul>
   *   <li>Then {@link CommandExecutionResult} (default constructor) DbUpdates {@code 2020-03-01} longValue is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbUpdate(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CommandExecutionResult.addDbUpdate(String)"})
  public void testAddDbUpdate_thenCommandExecutionResultDbUpdates20200301LongValueIsTwo() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.addDbUpdate("2020-03-01");

    // Act
    commandExecutionResult.addDbUpdate("2020-03-01");

    // Assert
    Map<String, Long> dbUpdates = commandExecutionResult.getDbUpdates();
    assertEquals(1, dbUpdates.size());
    assertEquals(2L, dbUpdates.get("2020-03-01").longValue());
  }

  /**
   * Test {@link CommandExecutionResult#addDbDelete(String)}.
   * <ul>
   *   <li>Then {@link CommandExecutionResult} (default constructor) DbDeletes {@code Delete} longValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbDelete(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CommandExecutionResult.addDbDelete(String)"})
  public void testAddDbDelete_thenCommandExecutionResultDbDeletesDeleteLongValueIsOne() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();

    // Act
    commandExecutionResult.addDbDelete("Delete");

    // Assert
    Map<String, Long> dbDeletes = commandExecutionResult.getDbDeletes();
    assertEquals(1, dbDeletes.size());
    assertEquals(1L, dbDeletes.get("Delete").longValue());
  }

  /**
   * Test {@link CommandExecutionResult#addDbDelete(String)}.
   * <ul>
   *   <li>Then {@link CommandExecutionResult} (default constructor) DbDeletes {@code Delete} longValue is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandExecutionResult#addDbDelete(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CommandExecutionResult.addDbDelete(String)"})
  public void testAddDbDelete_thenCommandExecutionResultDbDeletesDeleteLongValueIsTwo() {
    // Arrange
    CommandExecutionResult commandExecutionResult = new CommandExecutionResult();
    commandExecutionResult.addDbDelete("Delete");

    // Act
    commandExecutionResult.addDbDelete("Delete");

    // Assert
    Map<String, Long> dbDeletes = commandExecutionResult.getDbDeletes();
    assertEquals(1, dbDeletes.size());
    assertEquals(2L, dbDeletes.get("Delete").longValue());
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CommandExecutionResult.<init>()", "void CommandExecutionResult.addDatabaseTime(long)",
      "String CommandExecutionResult.getCommandFqn()", "long CommandExecutionResult.getDatabaseTimeInMs()",
      "Map CommandExecutionResult.getDbDeletes()", "Map CommandExecutionResult.getDbInserts()",
      "Map CommandExecutionResult.getDbSelects()", "Map CommandExecutionResult.getDbUpdates()",
      "long CommandExecutionResult.getTotalTimeInMs()", "void CommandExecutionResult.setCommandFqn(String)",
      "void CommandExecutionResult.setDatabaseTimeInMs(long)", "void CommandExecutionResult.setDbDeletes(Map)",
      "void CommandExecutionResult.setDbInserts(Map)", "void CommandExecutionResult.setDbSelects(Map)",
      "void CommandExecutionResult.setDbUpdates(Map)", "void CommandExecutionResult.setTotalTimeInMs(long)"})
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

    // Assert
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
