package com.example.InsuranceSystem.v11.exception;

public class InsuranceExceptions {
    public static class InvalidCarTypeException extends RuntimeException {
        public InvalidCarTypeException(String message) {
            super(message);
        }
    }

    public static class NoPoliciesException extends RuntimeException {
        public NoPoliciesException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
    public static class WrongPasswordException extends RuntimeException {
        public WrongPasswordException(String message) {
            super(message);
        }
    }
    public static class InsuranceCompanyExists extends RuntimeException {
        public InsuranceCompanyExists(String message) {
            super(message);
        }
    }
}
