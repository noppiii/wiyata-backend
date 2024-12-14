package com.wiyata.wiyata.backend.converter;

import lombok.AllArgsConstructor;
import lombok.Data;

public class PlanConverter {

    @Data
    @AllArgsConstructor
    public static class Error<T> {
        private int httpStatus;
        private String message;
    }

    @Data
    @AllArgsConstructor
    public static class CreatePlan<T> {
        private int httpStatus;
        private String message;
        private Long planId;
    }


    @Data
    @AllArgsConstructor
    public static class UserPlan<T> {
        private int httpStatus;
        private String message;
        private T planForm;
    }

    @Data
    @AllArgsConstructor
    public static class UpdateConcept<T> {
        private int httpStatus;
        private String message;
    }

    @Data
    @AllArgsConstructor
    public static class CreateDay<T> {
        private int httpStatus;
        private String message;
    }

    @Data
    @AllArgsConstructor
    public static class UserDay<T> {
        private int httpStatus;
        private String message;
        private T dayForm;
    }

    @Data
    @AllArgsConstructor
    public static class GetDay<T> {
        private int httpStatus;
        private String message;
    }

    @Data
    @AllArgsConstructor
    public static class UpdatePlan<T> {
        private int httpStatus;
        private String message;
    }

    @Data
    @AllArgsConstructor
    public static class UpdateSelectedLocation<T> {
        private int httpStatus;
        private String message;
    }
}
