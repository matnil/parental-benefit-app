/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.planner.examples.common.persistence;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

import org.apache.commons.io.IOUtils;
import org.drools.planner.core.solution.Solution;

public abstract class AbstractTxtSolutionExporter extends AbstractSolutionExporter {

    protected static final String DEFAULT_OUTPUT_FILE_SUFFIX = ".txt";

    protected AbstractTxtSolutionExporter(SolutionDao solutionDao) {
        super(solutionDao);
    }

    protected String getOutputFileSuffix() {
        return DEFAULT_OUTPUT_FILE_SUFFIX;
    }

    public abstract TxtOutputBuilder createTxtOutputBuilder();

    public void writeSolution(Solution solution, File outputFile) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            TxtOutputBuilder txtOutputBuilder = createTxtOutputBuilder();
            txtOutputBuilder.setBufferedWriter(bufferedWriter);
            txtOutputBuilder.setSolution(solution);
            txtOutputBuilder.writeSolution();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not write the file (" + outputFile.getName() + ").", e);
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
        }
    }

    public abstract class TxtOutputBuilder {

        protected BufferedWriter bufferedWriter;

        public void setBufferedWriter(BufferedWriter bufferedWriter) {
            this.bufferedWriter = bufferedWriter;
        }

        public abstract void setSolution(Solution solution);

        public abstract void writeSolution() throws IOException;

        // ************************************************************************
        // Helper methods
        // ************************************************************************

    }

}
