package com.example.kotlin.springboot.util

import java.util.UUID

import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * [java.util.UUID] &lt;=&gt; [java.lang.String]変換を行うJPAコンバーター
 */
@Converter(autoApply = false)
class UUIDToStringConverter : AttributeConverter<UUID, String> {

    override fun convertToDatabaseColumn(attribute: UUID?): String? {
        return attribute?.toString()
    }

    override fun convertToEntityAttribute(dbData: String?): UUID? {
        return if (dbData == null) null else UUID.fromString(dbData)
    }
}
