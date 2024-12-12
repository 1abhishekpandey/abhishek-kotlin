package com.rudderstack.sdk.kotlin.core.internals.plugins

import com.rudderstack.sdk.kotlin.core.Analytics
import com.rudderstack.sdk.kotlin.core.internals.models.Message

/**
 * The `Plugin` interface defines the contract for creating plugins that can be integrated into the RudderStack SDK.
 * Plugins allow for customized processing of analytics events by providing hooks at different stages in the event processing lifecycle.
 * Each plugin must implement this interface to define how it processes events and integrates with the main `Analytics` instance.
 *
 * Plugins are categorized into different types (e.g., `PreProcess`, `OnProcess`, `Destination`, `After`, `Manual`),
 * allowing for flexibility in defining their execution order and purpose.
 */
interface Plugin {

    /**
     * The type of the plugin. Defines when the plugin will be executed in the event processing lifecycle.
     *
     * Example types include:
     * - `PreProcess`: Executed before any message processing begins.
     * - `OnProcess`: Executed during the initial stage of message processing.
     * - `Destination`: Executed when messages are being passed to destinations.
     * - `After`: Executed after all message processing is complete (useful for cleanup operations).
     * - `Manual`: Executed only when called manually (e.g., for handling sessions).
     */
    val pluginType: PluginType

    /**
     * A reference to the `Analytics` instance that this plugin is associated with.
     * This allows the plugin to access analytics configuration, dispatch events, and interact with other plugins.
     *
     * This property must be set using the `setup` method when the plugin is initialized.
     */
    var analytics: Analytics

    /**
     * Sets up the plugin with the provided `Analytics` instance. This method should be called
     * when the plugin is initialized to associate it with an `Analytics` instance.
     *
     * @param analytics The `Analytics` instance to associate with this plugin.
     */
    fun setup(analytics: Analytics) {
        this.analytics = analytics
    }

    /**
     * Executes the plugin's logic on the provided `Message`. This method allows the plugin to
     * modify, enrich, or filter the message before it is processed further or sent to a destination.
     * By default, this method returns the unmodified message.
     *
     * @param message The `Message` object representing the event to be processed.
     * @return The potentially modified `Message` object, or `null` if the message should be discarded.
     */
    suspend fun execute(message: Message): Message? {
        return message
    }

    /**
     * Performs any necessary teardown or cleanup operations when the plugin is removed or the `Analytics` instance is shut down.
     * By default, this method does nothing, but plugins can override it to provide custom cleanup logic.
     */
    fun teardown() {}

    /**
     * An enumeration of possible plugin types that define when a plugin should be executed in the event processing flow.
     */
    enum class PluginType {
        /**
         * Plugins of this type are executed before any message processing begins.
         * Useful for pre-processing messages or adding context data.
         */
        PreProcess,

        /**
         * Plugins of this type are executed as the first level of message processing.
         * Useful for applying transformations or validations early in the pipeline.
         */
        OnProcess,

        /**
         * Plugins of this type are executed when messages are about to be passed off to their destinations.
         * Typically used for modifying messages specifically for certain destinations.
         */
        Destination,

        /**
         * Plugins of this type are executed after all message processing is completed.
         * Useful for cleanup operations or finalizing tasks.
         */
        After,

        /**
         * Plugins of this type are executed only when called manually.
         * For example, session-based plugins that trigger on specific user actions.
         */
        Manual
    }
}