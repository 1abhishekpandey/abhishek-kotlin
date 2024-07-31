package com.rudderstack.core.internals.plugins

import com.rudderstack.core.Analytics
import com.rudderstack.core.internals.models.Message

interface Plugin {

    val pluginType: PluginType

    var analytics: Analytics

    fun setup(analytics: Analytics) {
        this.analytics = analytics
    }

    fun execute(event: Message): Message? {
        return event
    }

    fun teardown() {}

    enum class PluginType {
        PreProcess,
        OnProcess,
        Integrations,
        Utility,
    }
}