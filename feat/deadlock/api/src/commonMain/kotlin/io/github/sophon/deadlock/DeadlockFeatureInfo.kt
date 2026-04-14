package io.github.sophon.deadlock

import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.deadlock.api.BuildKonfig

object DeadlockFeatureInfo {
    val featureInfo = FeatureInfo(
        name = FEATURE_NAME,
        url = FEATURE_URL,
        version = BuildKonfig.VERSION,
    )
}
