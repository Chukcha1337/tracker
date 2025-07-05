package com.chuckcha.tt.userservice.handler;

import java.util.Map;

public record ErrorResponse(
    Map<String, String> errors
) {

}
