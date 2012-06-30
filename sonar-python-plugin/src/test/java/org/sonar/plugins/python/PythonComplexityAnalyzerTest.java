/*
 * Sonar Python Plugin
 * Copyright (C) 2011 Waleri Enns
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.python;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.ProjectFileSystem;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PythonComplexityAnalyzerTest {
  private Project project;
  private ProjectFileSystem pfs;

  @Before
  public void init() {
    pfs = mock(ProjectFileSystem.class);
    when(pfs.getSonarWorkingDirectory()).thenReturn(new File(System.getProperty("java.io.tmpdir")));
    project = mock(Project.class);
    when(project.getFileSystem()).thenReturn(pfs);
  }

  @Test
  public void complexityTest() {
    Map<String, Integer> result_expected = new HashMap<String, Integer>() {
      {
        put("if_else", 3);
        put("if_elif_else", 5);
        put("if_compl_cond1", 5);
        put("if_compl_cond2", 4);
        put("for_else", 2);
        put("while_comp_cond", 3);
        put("while_else", 2);
        put("while_else_compl_cond1", 3);
        put("while_else_compl_cond2", 5);
        put("while_else_compl_cond3", 4);
        put("list_compr", 2);
        put("list_compr_filter", 3);
        put("gen_expr", 2);
        put("gen_expr_filter", 3);
      }
    };

    String resourceName = "/org/sonar/plugins/python/complexity/code_chunks.py";
    String pathName = getClass().getResource(resourceName).getPath();

    PythonComplexityAnalyzer analyzer = new PythonComplexityAnalyzer(project);
    List<ComplexityStat> stats = analyzer.analyzeComplexity(pathName);
    stats = stats.subList(1, stats.size());

    assertThat(stats.size()).isEqualTo(14);
    for (ComplexityStat stat : stats) {
      assertThat(stat.count).isEqualTo(result_expected.get(stat.name));
    }
  }

  // @Test
  // public void extractTest() {
  // PythonComplexityAnalyzer analyzer = new PythonComplexityAnalyzer();
  // analyzer.extractPygenie("/tmp");
  // }
}