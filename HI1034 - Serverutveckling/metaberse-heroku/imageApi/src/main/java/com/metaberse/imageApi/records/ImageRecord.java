package com.metaberse.imageApi.records;

import org.springframework.core.io.ByteArrayResource;

public record ImageRecord (long id, ByteArrayResource content, String type) {
}
