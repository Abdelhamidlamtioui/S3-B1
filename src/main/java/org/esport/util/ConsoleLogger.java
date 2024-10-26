package org.esport.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleLogger.class);

    public void afficherMessage(String message) {
        LOGGER.info(message);
        System.out.println(message);
    }

    public void afficherErreur(String message) {
        LOGGER.error(message);
        System.err.println("Erreur: " + message);
    }
}
