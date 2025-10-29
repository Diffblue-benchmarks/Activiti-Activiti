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
package org.activiti.engine.impl.scripting;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.core.util.COWArrayList;
import groovy.lang.GroovyClassLoader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.script.Bindings;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.junit.Test;
import org.mockito.Mockito;

public class ScriptingEnginesDiffblueTest {
  /**
   * Method under test:
   * {@link ScriptingEngines#addScriptEngineFactory(ScriptEngineFactory)}
   */
  @Test
  public void testAddScriptEngineFactory() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());

    // Act and Assert
    assertSame(scriptingEngines, scriptingEngines.addScriptEngineFactory(new JuelScriptEngineFactory()));
  }

  /**
   * Method under test:
   * {@link ScriptingEngines#addScriptEngineFactory(ScriptEngineFactory)}
   */
  @Test
  public void testAddScriptEngineFactory2() throws MalformedURLException {
    // Arrange
    URLStreamHandlerFactory urlStreamHandlerFactory = mock(URLStreamHandlerFactory.class);
    when(urlStreamHandlerFactory.createURLStreamHandler(Mockito.<String>any())).thenReturn(null);
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager(
        new URLClassLoader(new URL[]{Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()},
            new GroovyClassLoader(), urlStreamHandlerFactory)));

    // Act
    ScriptingEngines actualAddScriptEngineFactoryResult = scriptingEngines
        .addScriptEngineFactory(new JuelScriptEngineFactory());

    // Assert
    verify(urlStreamHandlerFactory).createURLStreamHandler(eq("jar"));
    assertSame(scriptingEngines, actualAddScriptEngineFactoryResult);
  }

  /**
   * Method under test: {@link ScriptingEngines#setScriptEngineFactories(List)}
   */
  @Test
  public void testSetScriptEngineFactories() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    COWArrayList<ScriptEngineFactory> scriptEngineFactories = mock(COWArrayList.class);

    ArrayList<ScriptEngineFactory> scriptEngineFactoryList = new ArrayList<>();
    when(scriptEngineFactories.iterator()).thenReturn(scriptEngineFactoryList.iterator());

    // Act
    scriptingEngines.setScriptEngineFactories(scriptEngineFactories);

    // Assert that nothing has changed
    verify(scriptEngineFactories).iterator();
  }

  /**
   * Method under test: {@link ScriptingEngines#setScriptEngineFactories(List)}
   */
  @Test
  public void testSetScriptEngineFactories2() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    COWArrayList<ScriptEngineFactory> scriptEngineFactories = mock(COWArrayList.class);
    when(scriptEngineFactories.iterator()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> scriptingEngines.setScriptEngineFactories(scriptEngineFactories));
    verify(scriptEngineFactories).iterator();
  }

  /**
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, Bindings)}
   */
  @Test
  public void testEvaluate() {
    // Arrange
    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> scriptingEngines.evaluate("Script", "en", new SimpleBindings()));
  }

  /**
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, VariableScope)}
   */
  @Test
  public void testEvaluate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptingEngines scriptingEngines = new ScriptingEngines(
        new ScriptBindingsFactory(processEngineConfiguration, new ArrayList<>()));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> scriptingEngines.evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, VariableScope)}
   */
  @Test
  public void testEvaluate3() {
    // Arrange
    ResolverFactory resolverFactory = mock(ResolverFactory.class);
    when(resolverFactory.createResolver(Mockito.<ProcessEngineConfigurationImpl>any(), Mockito.<VariableScope>any()))
        .thenReturn(new BeansResolverFactory());

    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(resolverFactory);
    ScriptingEngines scriptingEngines = new ScriptingEngines(
        new ScriptBindingsFactory(new JtaProcessEngineConfiguration(), resolverFactories));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> scriptingEngines.evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance()));
    verify(resolverFactory).createResolver(isA(ProcessEngineConfigurationImpl.class), isA(VariableScope.class));
  }

  /**
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)}
   */
  @Test
  public void testEvaluate4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptingEngines scriptingEngines = new ScriptingEngines(
        new ScriptBindingsFactory(processEngineConfiguration, new ArrayList<>()));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> scriptingEngines.evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance(), true));
  }

  /**
   * Method under test:
   * {@link ScriptingEngines#evaluate(String, String, VariableScope, boolean)}
   */
  @Test
  public void testEvaluate5() {
    // Arrange
    ResolverFactory resolverFactory = mock(ResolverFactory.class);
    when(resolverFactory.createResolver(Mockito.<ProcessEngineConfigurationImpl>any(), Mockito.<VariableScope>any()))
        .thenReturn(new BeansResolverFactory());

    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(resolverFactory);
    ScriptingEngines scriptingEngines = new ScriptingEngines(
        new ScriptBindingsFactory(new JtaProcessEngineConfiguration(), resolverFactories));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> scriptingEngines.evaluate("Script", "en", NoExecutionVariableScope.getSharedInstance(), true));
    verify(resolverFactory).createResolver(isA(ProcessEngineConfigurationImpl.class), isA(VariableScope.class));
  }

  /**
   * Method under test: {@link ScriptingEngines#getEngineByName(String)}
   */
  @Test
  public void testGetEngineByName() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new ScriptingEngines(new ScriptEngineManager())).getEngineByName("en"));
  }

  /**
   * Method under test: {@link ScriptingEngines#createBindings(VariableScope)}
   */
  @Test
  public void testCreateBindings() {
    // Arrange
    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(mock(ResolverFactory.class));
    ScriptBindingsFactory scriptBindingsFactory = mock(ScriptBindingsFactory.class);
    SimpleBindings simpleBindings = new SimpleBindings();
    when(scriptBindingsFactory.createBindings(Mockito.<VariableScope>any())).thenReturn(simpleBindings);
    doNothing().when(scriptBindingsFactory).setResolverFactories(Mockito.<List<ResolverFactory>>any());
    scriptBindingsFactory.setResolverFactories(resolverFactories);

    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    scriptingEngines.setScriptBindingsFactory(scriptBindingsFactory);

    // Act
    Bindings actualCreateBindingsResult = scriptingEngines.createBindings(NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(scriptBindingsFactory).createBindings(isA(VariableScope.class));
    verify(scriptBindingsFactory).setResolverFactories(isA(List.class));
    assertTrue(actualCreateBindingsResult.isEmpty());
    assertSame(simpleBindings, actualCreateBindingsResult);
  }

  /**
   * Method under test:
   * {@link ScriptingEngines#createBindings(VariableScope, boolean)}
   */
  @Test
  public void testCreateBindings2() {
    // Arrange
    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(mock(ResolverFactory.class));
    ScriptBindingsFactory scriptBindingsFactory = mock(ScriptBindingsFactory.class);
    SimpleBindings simpleBindings = new SimpleBindings();
    when(scriptBindingsFactory.createBindings(Mockito.<VariableScope>any(), anyBoolean())).thenReturn(simpleBindings);
    doNothing().when(scriptBindingsFactory).setResolverFactories(Mockito.<List<ResolverFactory>>any());
    scriptBindingsFactory.setResolverFactories(resolverFactories);

    ScriptingEngines scriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    scriptingEngines.setScriptBindingsFactory(scriptBindingsFactory);

    // Act
    Bindings actualCreateBindingsResult = scriptingEngines.createBindings(NoExecutionVariableScope.getSharedInstance(),
        true);

    // Assert
    verify(scriptBindingsFactory).createBindings(isA(VariableScope.class), eq(true));
    verify(scriptBindingsFactory).setResolverFactories(isA(List.class));
    assertTrue(actualCreateBindingsResult.isEmpty());
    assertSame(simpleBindings, actualCreateBindingsResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ScriptingEngines#ScriptingEngines(ScriptEngineManager)}
   *   <li>{@link ScriptingEngines#setCacheScriptingEngines(boolean)}
   *   <li>{@link ScriptingEngines#setScriptBindingsFactory(ScriptBindingsFactory)}
   *   <li>{@link ScriptingEngines#getScriptBindingsFactory()}
   *   <li>{@link ScriptingEngines#isCacheScriptingEngines()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ScriptingEngines actualScriptingEngines = new ScriptingEngines(new ScriptEngineManager());
    actualScriptingEngines.setCacheScriptingEngines(true);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory = new ScriptBindingsFactory(processEngineConfiguration,
        new ArrayList<>());

    actualScriptingEngines.setScriptBindingsFactory(scriptBindingsFactory);
    ScriptBindingsFactory actualScriptBindingsFactory = actualScriptingEngines.getScriptBindingsFactory();
    boolean actualIsCacheScriptingEnginesResult = actualScriptingEngines.isCacheScriptingEngines();

    // Assert that nothing has changed
    assertTrue(actualScriptingEngines.cachedEngines.isEmpty());
    assertTrue(actualIsCacheScriptingEnginesResult);
    assertSame(scriptBindingsFactory, actualScriptBindingsFactory);
  }

  /**
   * Method under test:
   * {@link ScriptingEngines#ScriptingEngines(ScriptBindingsFactory)}
   */
  @Test
  public void testNewScriptingEngines() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory = new ScriptBindingsFactory(processEngineConfiguration,
        new ArrayList<>());

    // Act
    ScriptingEngines actualScriptingEngines = new ScriptingEngines(scriptBindingsFactory);

    // Assert
    assertTrue(actualScriptingEngines.cachedEngines.isEmpty());
    assertTrue(actualScriptingEngines.isCacheScriptingEngines());
    assertSame(scriptBindingsFactory, actualScriptingEngines.getScriptBindingsFactory());
  }

  /**
   * Method under test:
   * {@link ScriptingEngines#ScriptingEngines(ScriptBindingsFactory)}
   */
  @Test
  public void testNewScriptingEngines2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ScriptBindingsFactory scriptBindingsFactory = new ScriptBindingsFactory(processEngineConfiguration,
        new ArrayList<>());

    // Act
    ScriptingEngines actualScriptingEngines = new ScriptingEngines(scriptBindingsFactory);

    // Assert
    assertTrue(actualScriptingEngines.cachedEngines.isEmpty());
    assertTrue(actualScriptingEngines.isCacheScriptingEngines());
    assertSame(scriptBindingsFactory, actualScriptingEngines.getScriptBindingsFactory());
  }
}
