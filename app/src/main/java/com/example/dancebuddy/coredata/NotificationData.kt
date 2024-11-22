package com.example.dancebuddy.coredata

object NotificationData {
    private val notifications: MutableList<Notification> = mutableListOf()

    fun getAllNotifications(): List<Notification> = notifications
    fun addNotification(notification: Notification) { notifications.add(notification) }
    fun removeNotification(notification: Notification) { notifications.remove(notification) }
    fun clearNotifications() { notifications.clear() }
}