package Common.Network.Utilities;

import Common.Network.WorkerState;

public class ServerState {

    public WorkerState[] workerStateTab;

    public ServerState() {
        workerStateTab = new WorkerState[2];
        workerStateTab[workerId(1)] = WorkerState.CONNECTING;
        workerStateTab[workerId(2)] = WorkerState.CONNECTING;
    }

    public WorkerState getWorkerState(int playerId) {
        return workerStateTab[workerId(playerId)];
    }

    public void setWorkerState(int playerId, WorkerState workerState) {
        workerStateTab[workerId(playerId)] = workerState;
    }

    private int workerId(int playerId) {
        return playerId - 1;
    }

}
