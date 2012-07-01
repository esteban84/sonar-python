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
package org.sonar.python.parser;

import com.sonar.sslr.impl.Parser;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.sonar.python.api.PythonGrammar;

import java.io.File;
import java.util.Collection;

public class PythonParserTest {

  private final Parser<PythonGrammar> parser = PythonParser.create();

  @Test
  public void test() {
    Collection<File> files = listFiles();
    for (File file : files) {
      parser.parse(file);
    }
  }

  private static Collection<File> listFiles() {
    File dir = new File("src/test/resources/parser/");
    return FileUtils.listFiles(dir, new String[] {"py"}, true);
  }

}