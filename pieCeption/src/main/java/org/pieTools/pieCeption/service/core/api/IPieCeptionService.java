package org.pieTools.pieCeption.service.core.api;

import org.pieTools.pieCeption.service.core.exception.PieCeptionServiceException;

/**
 * Created by Svetoslav on 29.12.13.
 */
public interface IPieCeptionService {
    void parseArgs(String[] args) throws PieCeptionServiceException;
}
