package com.example.kotlin.springboot.domain.models.extentions

import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Temporal
import javax.persistence.TemporalType

interface Timestamps {
    interface All : Created, Updated {}
    interface Created : Timestamps {
        var createdAt: Timestamp
    }
    interface Updated : Timestamps {
        var updatedAt: Timestamp
    }
}
