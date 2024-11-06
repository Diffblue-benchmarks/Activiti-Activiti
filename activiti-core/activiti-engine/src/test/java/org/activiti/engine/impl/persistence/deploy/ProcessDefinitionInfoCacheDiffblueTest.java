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
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.TransactionManager;
import java.util.Map;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.JtaRetryInterceptor;
import org.junit.Test;
import org.mockito.Mockito;

public class ProcessDefinitionInfoCacheDiffblueTest {
  /**
   * Test
   * {@link ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor)}
   */
  @Test
  public void testNewProcessDefinitionInfoCache() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    CommandContextInterceptor first = new CommandContextInterceptor();

    // Act and Assert
    CommandExecutor commandExecutor = (new ProcessDefinitionInfoCache(
        new CommandExecutorImpl(defaultConfig, first))).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
  }

  /**
   * Test
   * {@link ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor)}
   */
  @Test
  public void testNewProcessDefinitionInfoCache2() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));

    // Act and Assert
    CommandExecutor commandExecutor = (new ProcessDefinitionInfoCache(
        new CommandExecutorImpl(defaultConfig, first))).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
  }

  /**
   * Test
   * {@link ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor, int)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor, int)}
   */
  @Test
  public void testNewProcessDefinitionInfoCache3() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    CommandContextInterceptor first = new CommandContextInterceptor();

    // Act and Assert
    CommandExecutor commandExecutor = (new ProcessDefinitionInfoCache(new CommandExecutorImpl(defaultConfig, first),
        1)).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
  }

  /**
   * Test
   * {@link ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor, int)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionInfoCache#ProcessDefinitionInfoCache(CommandExecutor, int)}
   */
  @Test
  public void testNewProcessDefinitionInfoCache4() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    JtaRetryInterceptor first = new JtaRetryInterceptor(mock(TransactionManager.class));

    // Act and Assert
    CommandExecutor commandExecutor = (new ProcessDefinitionInfoCache(new CommandExecutorImpl(defaultConfig, first),
        1)).commandExecutor;
    assertTrue(commandExecutor instanceof CommandExecutorImpl);
    assertSame(first, ((CommandExecutorImpl) commandExecutor).getFirst());
  }

  /**
   * Test {@link ProcessDefinitionInfoCache#get(String)}.
   * <ul>
   *   <li>Then return {@link ProcessDefinitionInfoCacheObject} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionInfoCache#get(String)}
   */
  @Test
  public void testGet_thenReturnProcessDefinitionInfoCacheObject() {
    // Arrange
    ProcessDefinitionInfoCacheObject processDefinitionInfoCacheObject = new ProcessDefinitionInfoCacheObject();
    processDefinitionInfoCacheObject.setId("42");
    processDefinitionInfoCacheObject.setInfoNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    processDefinitionInfoCacheObject.setRevision(1);
    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(processDefinitionInfoCacheObject);

    // Act
    ProcessDefinitionInfoCacheObject actualGetResult = (new ProcessDefinitionInfoCache(
        new CommandExecutorImpl(mock(CommandConfig.class), first))).get("42");

    // Assert
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(processDefinitionInfoCacheObject, actualGetResult);
  }

  /**
   * Test
   * {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}
   */
  @Test
  public void testAdd() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    ProcessDefinitionInfoCache processDefinitionInfoCache = new ProcessDefinitionInfoCache(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ProcessDefinitionInfoCacheObject obj = new ProcessDefinitionInfoCacheObject();
    obj.setId("42");
    obj.setInfoNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    obj.setRevision(1);

    // Act
    processDefinitionInfoCache.add("42", obj);

    // Assert
    Map<String, ProcessDefinitionInfoCacheObject> stringProcessDefinitionInfoCacheObjectMap = processDefinitionInfoCache.cache;
    assertEquals(1, stringProcessDefinitionInfoCacheObjectMap.size());
    assertEquals(1, processDefinitionInfoCache.size());
    assertSame(obj, stringProcessDefinitionInfoCacheObjectMap.get("42"));
  }

  /**
   * Test
   * {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}
   */
  @Test
  public void testAdd2() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);
    ProcessDefinitionInfoCache processDefinitionInfoCache = new ProcessDefinitionInfoCache(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ProcessDefinitionInfoCacheObject obj = new ProcessDefinitionInfoCacheObject();
    obj.setId("42");
    obj.setInfoNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    obj.setRevision(1);

    // Act
    processDefinitionInfoCache.add("42", obj);

    // Assert
    Map<String, ProcessDefinitionInfoCacheObject> stringProcessDefinitionInfoCacheObjectMap = processDefinitionInfoCache.cache;
    assertEquals(1, stringProcessDefinitionInfoCacheObjectMap.size());
    assertEquals(1, processDefinitionInfoCache.size());
    assertSame(obj, stringProcessDefinitionInfoCacheObjectMap.get("42"));
  }

  /**
   * Test
   * {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}
   */
  @Test
  public void testAdd3() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    ProcessDefinitionInfoCache processDefinitionInfoCache = new ProcessDefinitionInfoCache(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()), 1);

    ProcessDefinitionInfoCacheObject obj = new ProcessDefinitionInfoCacheObject();
    obj.setId("42");
    obj.setInfoNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    obj.setRevision(1);

    // Act
    processDefinitionInfoCache.add("42", obj);

    // Assert
    Map<String, ProcessDefinitionInfoCacheObject> stringProcessDefinitionInfoCacheObjectMap = processDefinitionInfoCache.cache;
    assertEquals(1, stringProcessDefinitionInfoCacheObjectMap.size());
    assertEquals(1, processDefinitionInfoCache.size());
    assertSame(obj, stringProcessDefinitionInfoCacheObjectMap.get("42"));
  }

  /**
   * Test
   * {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionInfoCache#add(String, ProcessDefinitionInfoCacheObject)}
   */
  @Test
  public void testAdd4() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    ProcessDefinitionInfoCache processDefinitionInfoCache = new ProcessDefinitionInfoCache(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()), 0);

    ProcessDefinitionInfoCacheObject obj = new ProcessDefinitionInfoCacheObject();
    obj.setId("42");
    obj.setInfoNode(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    obj.setRevision(1);

    // Act
    processDefinitionInfoCache.add("42", obj);

    // Assert
    assertEquals(0, processDefinitionInfoCache.size());
    assertTrue(processDefinitionInfoCache.cache.isEmpty());
  }

  /**
   * Test {@link ProcessDefinitionInfoCache#size()}.
   * <p>
   * Method under test: {@link ProcessDefinitionInfoCache#size()}
   */
  @Test
  public void testSize() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();

    // Act and Assert
    assertEquals(0,
        (new ProcessDefinitionInfoCache(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())))
            .size());
  }

  /**
   * Test {@link ProcessDefinitionInfoCache#size()}.
   * <ul>
   *   <li>Given {@link JtaRetryInterceptor#JtaRetryInterceptor(TransactionManager)}
   * with {@link TransactionManager}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionInfoCache#size()}
   */
  @Test
  public void testSize_givenJtaRetryInterceptorWithTransactionManager() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();

    // Act and Assert
    assertEquals(0, (new ProcessDefinitionInfoCache(
        new CommandExecutorImpl(defaultConfig, new JtaRetryInterceptor(mock(TransactionManager.class))))).size());
  }
}
