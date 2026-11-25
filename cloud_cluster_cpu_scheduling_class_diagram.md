@startuml

class Process {
  - name : String
  - arrivalTime : int
  - burstTime : int
  - remainingTime : int
  - initialPriority : int
  - effectivePriority : int
  - finishTime : int
  - waitingTime : int
  - turnaroundTime : int
  - responseTime : int
  - isBatchJob : boolean
  - tier2WaitStartTime : int
}

abstract class Scheduler {
  # contextSwitchTime : int
  # processes : List<Process>
  # averageWaitingTime : double
  # averageTurnaroundTime : double
  + schedule() : void
  + calculateMetrics() : void
}

class PreemptiveSJF {
  + schedule() : void
}

class AdaptiveContainerScheduler {
  - quantum : int
  - tier1Queue : Queue<Process>
  - tier2Queue : PriorityQueue<Process>
  + schedule() : void
  - demoteToTier2(p : Process) : void
  - applyAging(currentTime : int) : void
  - outputHeuristicDemotionMatrix(timeStep : int) : void
}

class Simulator {
  - contextSwitch : int
  - quantum : int
  + run(scheduler : Scheduler, processes : List<Process>) : void
}

class GUI {
  + display() : void
}

Scheduler <|-- PreemptiveSJF
Scheduler <|-- AdaptiveContainerScheduler
Scheduler "1" o-- "*" Process : manages
Simulator "1" --> "1" Scheduler : executes
Simulator "1" --> "*" Process : loads
GUI ..> Simulator : visualizes

@enduml