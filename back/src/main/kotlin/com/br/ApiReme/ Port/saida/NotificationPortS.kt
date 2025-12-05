package com.br.ApiReme.` Port`.saida

import com.br.ApiReme.domain.Notification

interface NotificationPortS {
    fun save(notification: Notification): Notification
    fun findById(id: Long): Notification?
    fun findByReminderId(reminderId: Long): List<Notification>
    fun deleteById(id: Long)
    fun findAll(): List<Notification>
}