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

package org.optaplanner.spring.boot.example.domain;

import java.util.List;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

/**
 * Currently separated from {@link TimeTableSolution}
 * because it contains a {@link SolverStatus} and the {@link #score} is never null.
 */
public class TimeTableView {

    private List<Timeslot> timeslotList;
    private List<Room> roomList;
    private List<Lesson> lessonList;

    private HardSoftScore score;
    private SolverStatus solverStatus;

    public TimeTableView(List<Timeslot> timeslotList, List<Room> roomList, List<Lesson> lessonList,
            HardSoftScore score, SolverStatus solverStatus) {
        this.timeslotList = timeslotList;
        this.roomList = roomList;
        this.lessonList = lessonList;
        this.score = score;
        this.solverStatus = solverStatus;
    }

    public List<Timeslot> getTimeslotList() {
        return timeslotList;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public SolverStatus getSolverStatus() {
        return solverStatus;
    }

}
