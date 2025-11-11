package com.br.ApiReme.` Port`.entrada

import Notification

interface NotificatePortE {


        fun createNotification(notification: Notification): Notification
        fun findNotificationById(id: Long): Notification?
        fun findNotificationsByReminder(reminderId: Long): List<Notification>
        fun updateNotification(id: Long, updatedNotification: Notification): Notification
        fun deleteNotification(id: Long)
        fun findAllNotifications(): List<Notification>
}