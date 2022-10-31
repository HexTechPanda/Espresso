package com.espresso.commons.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(20000, "Success"),
    ERROR(999, "Error"),
    UNAUTHENTICATED(401, "Please authenticate first"),
    AUTH_FAIL(1400, "Authentication fail"),
    BIND_ERROR(400101, "Validation error: %s"),
    DATA_VALIDATION_ERROR(400102, "Data validation error"),
    // about token
    TOKEN_PAST(1401, "Token expires, please login again"),
    TOKEN_ERROR(1402, "Token error"),
    HEADER_ERROR(1403, "Request header error"),
    AUTH_USERNAME_NONE(1405, "Username should not be empty"),
    AUTH_PASSWORD_NONE(1406, "Password should not be empty"),
    MENU_NO(306, "No access, please contact admin"),
    // about ticket
    TICKET_NOT_FOUND(601, "Target ticket not found."),
    TICKETS_SOLD_OUT(602, "Purchase failed, tickets sold out."),
    INSUFFICIENT_STOCK(603, "Purchase failed, insufficient ticket stock.");

    private Integer code;
    private String desc;
}
