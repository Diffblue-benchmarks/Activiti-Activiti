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
package org.activiti.engine.impl.persistence.deploy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Map;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ProcessDefinitionInfoCacheDiffblueTest {
  /**
   * Test {@link ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDefinitionInfoCache.<init>(CommandExecutor)"})
  public void testNewProcessDefinitionInfoCache() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    CommandContextInterceptor first = new CommandContextInterceptor();

    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, first);

    // Act
    ProcessDefinitionInfoCache actualProcessDefinitionInfoCache =
        new ProcessDefinitionInfoCache(commandExecutor);

    // Assert
    CommandExecutor commandExecutor2 = actualProcessDefinitionInfoCache.commandExecutor;
    assertTrue(commandExecutor2 instanceof CommandExecutorImpl);
    assertEquals(0, actualProcessDefinitionInfoCache.size());
    assertTrue(actualProcessDefinitionInfoCache.cache.isEmpty());
    assertSame(defaultConfig, commandExecutor2.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor2).getFirst());
  }

  /**
   * Test {@link ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor, int)}.
   *
   * <ul>
   *   <li>Then {@link ProcessDefinitionInfoCache#commandExecutor} return {@link
   *       CommandExecutorImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDefinitionInfoCache.<init>(CommandExecutor, int)"})
  public void testNewProcessDefinitionInfoCache_thenCommandExecutorReturnCommandExecutorImpl() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    CommandContextInterceptor first = new CommandContextInterceptor();

    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, first);

    // Act
    ProcessDefinitionInfoCache actualProcessDefinitionInfoCache =
        new ProcessDefinitionInfoCache(commandExecutor, 1);

    // Assert
    CommandExecutor commandExecutor2 = actualProcessDefinitionInfoCache.commandExecutor;
    assertTrue(commandExecutor2 instanceof CommandExecutorImpl);
    assertEquals(0, actualProcessDefinitionInfoCache.size());
    assertTrue(actualProcessDefinitionInfoCache.cache.isEmpty());
    assertSame(defaultConfig, commandExecutor2.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor2).getFirst());
  }

  /**
   * Test {@link ProcessDefinitionInfoCache#get(String)}.
   *
   * <ul>
   *   <li>Then return {@link ProcessDefinitionInfoCacheObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessDefinitionInfoCache#get(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessDefinitionInfoCacheObject ProcessDefinitionInfoCache.get(String)"})
  public void testGet_thenReturnProcessDefinitionInfoCacheObject() {
    // Arrange
    ProcessDefinitionInfoCacheObject processDefinitionInfoCacheObject =
        new ProcessDefinitionInfoCacheObject();
    processDefinitionInfoCacheObject.setId("42");
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    processDefinitionInfoCacheObject.setInfoNode(new ObjectNode(nc));
    processDefinitionInfoCacheObject.setRevision(1);

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(
            Mockito.<CommandConfig>any(), Mockito.<Command<ProcessDefinitionInfoCacheObject>>any()))
        .thenReturn(processDefinitionInfoCacheObject);
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(new CommandConfig(), first);

    // Act
    ProcessDefinitionInfoCacheObject actualGetResult =
        new ProcessDefinitionInfoCache(commandExecutor).get("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(processDefinitionInfoCacheObject, actualGetResult);
  }

  /**
   * Test {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}.
   *
   * <p>Method under test: {@link ProcessDefinitionInfoCache#add(String,
   * ProcessDefinitionInfoCacheObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoCache.add(String, ProcessDefinitionInfoCacheObject)"
  })
  public void testAdd() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    ProcessDefinitionInfoCache processDefinitionInfoCache =
        new ProcessDefinitionInfoCache(commandExecutor);

    ProcessDefinitionInfoCacheObject obj = new ProcessDefinitionInfoCacheObject();
    obj.setId("42");
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    obj.setInfoNode(new ObjectNode(nc));
    obj.setRevision(1);

    // Act
    processDefinitionInfoCache.add("42", obj);

    // Assert
    Map<String, ProcessDefinitionInfoCacheObject> stringProcessDefinitionInfoCacheObjectMap =
        processDefinitionInfoCache.cache;
    assertEquals(1, stringProcessDefinitionInfoCacheObjectMap.size());
    assertEquals(1, processDefinitionInfoCache.size());
    assertSame(obj, stringProcessDefinitionInfoCacheObjectMap.get("42"));
  }

  /**
   * Test {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}.
   *
   * <p>Method under test: {@link ProcessDefinitionInfoCache#add(String,
   * ProcessDefinitionInfoCacheObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoCache.add(String, ProcessDefinitionInfoCacheObject)"
  })
  public void testAdd2() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    ProcessDefinitionInfoCache processDefinitionInfoCache =
        new ProcessDefinitionInfoCache(commandExecutor, 1);

    ProcessDefinitionInfoCacheObject obj = new ProcessDefinitionInfoCacheObject();
    obj.setId("42");
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    obj.setInfoNode(new ObjectNode(nc));
    obj.setRevision(1);

    // Act
    processDefinitionInfoCache.add("42", obj);

    // Assert
    Map<String, ProcessDefinitionInfoCacheObject> stringProcessDefinitionInfoCacheObjectMap =
        processDefinitionInfoCache.cache;
    assertEquals(1, stringProcessDefinitionInfoCacheObjectMap.size());
    assertEquals(1, processDefinitionInfoCache.size());
    assertSame(obj, stringProcessDefinitionInfoCacheObjectMap.get("42"));
  }

  /**
   * Test {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}.
   *
   * <ul>
   *   <li>Given {@link ProcessDefinitionInfoCacheObject} (default constructor) Id is {@code Id}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessDefinitionInfoCache#add(String,
   * ProcessDefinitionInfoCacheObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoCache.add(String, ProcessDefinitionInfoCacheObject)"
  })
  public void testAdd_givenProcessDefinitionInfoCacheObjectIdIsId() {
    // Arrange
    ProcessDefinitionInfoCacheObject obj = new ProcessDefinitionInfoCacheObject();
    obj.setId("Id");
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    obj.setInfoNode(new ObjectNode(nc));
    obj.setRevision(0);
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    ProcessDefinitionInfoCache processDefinitionInfoCache =
        new ProcessDefinitionInfoCache(commandExecutor, 1);
    processDefinitionInfoCache.add("Id", obj);

    ProcessDefinitionInfoCacheObject obj2 = new ProcessDefinitionInfoCacheObject();
    obj2.setId("42");
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);
    obj2.setInfoNode(new ObjectNode(nc2));
    obj2.setRevision(1);

    // Act
    processDefinitionInfoCache.add("42", obj2);

    // Assert
    Map<String, ProcessDefinitionInfoCacheObject> stringProcessDefinitionInfoCacheObjectMap =
        processDefinitionInfoCache.cache;
    assertEquals(1, stringProcessDefinitionInfoCacheObjectMap.size());
    assertEquals(1, processDefinitionInfoCache.size());
    assertSame(obj2, stringProcessDefinitionInfoCacheObjectMap.get("42"));
  }

  /**
   * Test {@link ProcessDefinitionInfoCache#size()}.
   *
   * <p>Method under test: {@link ProcessDefinitionInfoCache#size()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProcessDefinitionInfoCache.size()"})
  public void testSize() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());

    // Act and Assert
    assertEquals(0, new ProcessDefinitionInfoCache(commandExecutor).size());
  }
}
