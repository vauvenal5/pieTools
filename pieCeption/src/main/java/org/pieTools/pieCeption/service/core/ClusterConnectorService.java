package org.pieTools.pieCeption.service.core;

import org.pieTools.pieCeption.service.core.api.IPieCeptionConnectorService;
import org.pieTools.pieCeption.service.core.exception.PieCeptionServiceException;
import org.pieTools.piePlate.service.cluster.api.IClusterService;
import org.pieTools.piePlate.service.exception.ClusterServiceException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Svetoslav on 30.12.13.
 */
public class ClusterConnectorService implements IPieCeptionConnectorService {

    private IClusterService clusterService;
    private String serviceName = null;

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    @Override
    public void connectToMaster() throws PieCeptionServiceException {
        this.checkServiceName();

        try {
            String localName = InetAddress.getLocalHost().getHostName();
            clusterService.connect(serviceName + localName);
        } catch (UnknownHostException e) {
            //todo-sv: error handling
            e.printStackTrace();
        } catch (ClusterServiceException e) {
            //todo-sv: error handling
            e.printStackTrace();
        }
    }

    @Override
    public void connectToMaster(String serviceName) throws PieCeptionServiceException {
        this.setServiceName(serviceName);
        this.connectToMaster();
    }

    @Override
    public boolean isPieShareRunning() throws PieCeptionServiceException {
        if(!this.clusterService.isConnectedToCluster()){
            throw new PieCeptionServiceException("You are not connected to the PieCeption channel!");
        }

        if(this.clusterService.getMembersCount() > 1){
            return true;
        }

        return false;
    }

    private void checkServiceName() throws PieCeptionServiceException {
        if(this.serviceName == null){
            throw new PieCeptionServiceException("Service name not set!");
        }
    }
}
