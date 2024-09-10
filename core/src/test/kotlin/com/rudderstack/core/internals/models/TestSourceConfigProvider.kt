package com.rudderstack.core.internals.models

import kotlinx.serialization.json.JsonObject

fun provideSourceConfig(
    source: RudderServerConfigSource
): SourceConfig = SourceConfig(
    source = source,
)

fun provideRudderServerConfigSource(
    sourceId: String,
    sourceName: String,
    writeKey: String,
    metricConfig: MetricsConfig = provideMetricsConfig(),
    isSourceEnabled: Boolean,
    workspaceId: String,
    destinations: List<Destination> = emptyList(),
    updatedAt: String,
): RudderServerConfigSource = RudderServerConfigSource(
    sourceId = sourceId,
    sourceName = sourceName,
    writeKey = writeKey,
    metricConfig = metricConfig,
    isSourceEnabled = isSourceEnabled,
    workspaceId = workspaceId,
    destinations = destinations,
    updatedAt = updatedAt,
)

fun provideMetricsConfig(
    statsCollection: StatsCollection = provideStatsCollection(),
): MetricsConfig = MetricsConfig(
    statsCollection = statsCollection
)

fun provideStatsCollection(
    errors: Errors = provideErrors(),
    metrics: Metrics = provideMetrics()
) = StatsCollection(
    errors = errors,
    metrics = metrics
)

fun provideErrors(
    enabled: Boolean = false
) = Errors(
    enabled = enabled
)

fun provideMetrics(
    enabled: Boolean = false
) = Metrics(
    enabled = enabled
)

fun provideDestination(
    destinationId: String = "<DESTINATION_ID>",
    destinationName: String,
    isDestinationEnabled: Boolean = true,
    destinationConfig: JsonObject = emptyJsonObject,
    destinationDefinitionId: String = "<DESTINATION_DEFINITION_ID>",
    destinationDefinition: DestinationDefinition,
    updatedAt: String,
    shouldApplyDeviceModeTransformation: Boolean = false,
    propagateEventsUntransformedOnError: Boolean = false,
) = Destination(
    destinationId = destinationId,
    destinationName = destinationName,
    isDestinationEnabled = isDestinationEnabled,
    destinationConfig = destinationConfig,
    destinationDefinitionId = destinationDefinitionId,
    destinationDefinition = destinationDefinition,
    updatedAt = updatedAt,
    shouldApplyDeviceModeTransformation = shouldApplyDeviceModeTransformation,
    propagateEventsUntransformedOnError = propagateEventsUntransformedOnError
)

fun provideDestinationDefinition(
    name: String,
    displayName: String
) = DestinationDefinition(
    name = name,
    displayName = displayName
)