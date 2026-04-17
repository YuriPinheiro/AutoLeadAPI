package com.autolead.dto.image;

import java.util.UUID;

public record ImageResponse(
        UUID id,
        String url
) {}