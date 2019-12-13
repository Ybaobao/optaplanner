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

package org.optaplanner.spring.boot.example.solver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.optaplanner.core.api.solver.SolverStatus;
import org.optaplanner.spring.boot.example.domain.TimeTable;
import org.optaplanner.spring.boot.example.domain.TimeTableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "optaplanner.solver.termination.spent-limit=1h", // Effectively disable this termination
        "optaplanner.solver.termination.best-score-limit=0hard/*soft"})
public class TimeTableSolverServiceTest {

    @Autowired
    private TimeTableSolverService timeTableSolverService;

    @Test(timeout = 600_000)
    public void solveDemoDataUntilFeasible() throws InterruptedException {
        timeTableSolverService.solve();
        TimeTableView timeTableView = timeTableSolverService.getTimeTableView();
        while (timeTableView.getSolverStatus() != SolverStatus.NOT_SOLVING) {
            // Quick polling (not a Test Thread Sleep anti-pattern)
            // Test is still fast on fast machines and doesn't randomly fail on slow machines.
            Thread.sleep(100L);
            timeTableView = timeTableSolverService.getTimeTableView();
        }
        TimeTable timeTable = timeTableView.getTimeTable();
        assertTrue(timeTable.getLessonList().size() > 10);
        assertTrue(timeTable.getScore().isFeasible());
    }

}
