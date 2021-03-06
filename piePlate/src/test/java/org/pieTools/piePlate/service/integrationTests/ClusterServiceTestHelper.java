package org.pieTools.piePlate.service.integrationTests;

import org.jgroups.Message;
import org.pieTools.piePlate.dto.PieMessage;
import org.pieTools.piePlate.service.cluster.api.IClusterService;
import org.pieTools.piePlate.service.exception.ClusterServiceException;

/**
 * Created by Svetoslav on 14.12.13.
 */
public abstract class ClusterServiceTestHelper implements Runnable {

    private IClusterService service;

    private boolean done = false;

    public ClusterServiceTestHelper(IClusterService service) {
        this.service = service;
    }

    public IClusterService getService() {
        return this.service;
    }

    public boolean getDone() {
        return this.done;
    }

    public void setDone() {
        this.done = true;
    }
}
