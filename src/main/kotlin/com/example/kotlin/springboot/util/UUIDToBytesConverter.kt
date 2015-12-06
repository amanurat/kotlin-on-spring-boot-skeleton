package com.example.kotlin.springboot.util

import java.nio.ByteBuffer
import java.util.UUID

import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * [java.util.UUID] &lt;=&gt; `byte[]`変換を行うJPAコンバーター
 */
@Converter(autoApply = false)
class UUIDToBytesConverter : AttributeConverter<UUID, ByteArray> {

    override fun convertToDatabaseColumn(attribute: UUID?): ByteArray? {
        if (attribute == null)
            return null

        return ByteBuffer.allocate(16).putLong(attribute.mostSignificantBits).putLong(attribute.leastSignificantBits).array()
    }

    override fun convertToEntityAttribute(dbData: ByteArray?): UUID? {
        if (dbData == null)
            return null

        val buffer = ByteBuffer.wrap(dbData)
        val most = buffer.long
        val least = buffer.long
        return UUID(most, least)
    }
}
