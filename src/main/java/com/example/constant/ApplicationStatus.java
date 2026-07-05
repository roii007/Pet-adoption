package com.example.constant;

public final class ApplicationStatus {

    public static final String PENDING = "pending";
    public static final String APPROVED = "approved";
    public static final String REJECTED = "rejected";
    public static final String CANCELLED = "cancelled";

    private ApplicationStatus() {
    }

    public static String normalize(String status) {
        if (status == null || status.trim().isEmpty()) {
            return PENDING;
        }
        String value = status.trim();
        if (PENDING.equalsIgnoreCase(value) || "\u5f85\u5ba1\u6838".equals(value)) {
            return PENDING;
        }
        if (APPROVED.equalsIgnoreCase(value) || "\u901a\u8fc7".equals(value)) {
            return APPROVED;
        }
        if (REJECTED.equalsIgnoreCase(value) || "\u62d2\u7edd".equals(value)) {
            return REJECTED;
        }
        if (CANCELLED.equalsIgnoreCase(value) || "\u5df2\u53d6\u6d88".equals(value)) {
            return CANCELLED;
        }
        return PENDING;
    }

    public static String toText(String status) {
        switch (normalize(status)) {
            case APPROVED:
                return "\u901a\u8fc7";
            case REJECTED:
                return "\u62d2\u7edd";
            case CANCELLED:
                return "\u5df2\u53d6\u6d88";
            case PENDING:
            default:
                return "\u5f85\u5ba1\u6838";
        }
    }

    public static boolean isPending(String status) {
        return PENDING.equals(normalize(status));
    }

    public static boolean isApproved(String status) {
        return APPROVED.equals(normalize(status));
    }

    public static boolean isRejected(String status) {
        return REJECTED.equals(normalize(status));
    }

    public static boolean isCancelled(String status) {
        return CANCELLED.equals(normalize(status));
    }
}
