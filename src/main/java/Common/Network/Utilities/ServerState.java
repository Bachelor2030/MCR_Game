package Common.Network.Utilities;

import Common.Network.WorkerState;

public class ServerState {

    public WorkerState workerState;

    public ServerState(WorkerState workerState) {
        this.workerState = workerState;
    }

    public WorkerState getWorkerState() {
        return workerState;
    }

    public void setWorkerState(WorkerState workerState) {
        this.workerState = workerState;
    }

}
