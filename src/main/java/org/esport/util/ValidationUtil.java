package org.esport.util;

import org.esport.model.enums.TournementStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ValidationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtil.class);

    public static void validateTournamentId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid tournament ID.");
        }
    }

    public static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
    }

    public static void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
    }

    public static void validateSpectatorCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Number of spectators cannot be negative.");
        }
    }

    public static void validateTime(int time) {
        if (time < 0) {
            throw new IllegalArgumentException("Time values cannot be negative.");
        }
    }

    public static void validateStatus(TournementStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
    }

    public static String readNonEmptyString(Scanner scanner, String prompt) {
        String input;
        do {
            LOGGER.info(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                LOGGER.warn("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static Long readPositiveLong(Scanner scanner, String prompt) {
        Long input = null;
        while (input == null) {
            LOGGER.info(prompt);
            try {
                input = Long.parseLong(scanner.nextLine().trim());
                if (input <= 0) {
                    LOGGER.warn("Input must be a positive number. Please try again.");
                    input = null;
                }
            } catch (NumberFormatException e) {
                LOGGER.warn("Invalid input. Please enter a valid number.");
            }
        }
        return input;
    }

    public static LocalDate readDate(Scanner scanner, String prompt, String pattern) {
        LocalDate date = null;
        while (date == null) {
            LOGGER.info(prompt);
            try {
                date = LocalDate.parse(scanner.nextLine().trim(), DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException e) {
                LOGGER.warn("Invalid date format. Please use the format: " + pattern);
            }
        }
        return date;
    }

    public static int readNonNegativeInt(Scanner scanner, String prompt) {
        Integer input = null;
        while (input == null) {
            LOGGER.info(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input < 0) {
                    LOGGER.warn("Input must be a non-negative number. Please try again.");
                    input = null;
                }
            } catch (NumberFormatException e) {
                LOGGER.warn("Invalid input. Please enter a valid number.");
            }
        }
        return input;
    }
}
