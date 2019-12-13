/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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

package org.optaplanner.spring.boot.example.poc.api.solver;

import java.util.function.Consumer;

import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.spring.boot.example.poc.impl.solver.DefaultSolverManager;

/**
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 */
// THIS IS JUST A PROOF OF CONCEPT IN THE EXAMPLE FOR 7.30.0.Final. THIS CLASS WILL BECOME OBSOLETE VERY SOON.
// TODO Clean this up and move this class into optaplanner-core
public interface SolverManager<Solution_> extends AutoCloseable {

    static <Solution_> SolverManager<Solution_> create(SolverFactory<Solution_> solverFactory) {
        return new DefaultSolverManager<>(solverFactory);
    }

    SolverFuture solve(Solution_ planningProblem,
            Consumer<Solution_> bestSolutionConsumer);

    void reloadProblem();

}
