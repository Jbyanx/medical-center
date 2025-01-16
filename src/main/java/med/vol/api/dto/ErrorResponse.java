package med.vol.api.dto;

import java.util.List;

public record ErrorResponse(
        String message,
        String backendMessage
) {
}
