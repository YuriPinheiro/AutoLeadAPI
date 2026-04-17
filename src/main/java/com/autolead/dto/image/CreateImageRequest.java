package com.autolead.dto.image;

import com.autolead.domain.enums.ImageType;

public record CreateImageRequest(
        ImageType type
) {
}
