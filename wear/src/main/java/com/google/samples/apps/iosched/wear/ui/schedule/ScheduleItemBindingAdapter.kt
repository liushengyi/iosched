/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.iosched.wear.ui.schedule

import android.content.Context
import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.samples.apps.iosched.wear.R
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime
import timber.log.Timber

/**
 * Creates custom Strings and Icon names for the views in item_sessionased on data
 * passed in via Data Binding.
 */
private const val TAG: String = "SchedItemBindingAdapter"
private const val DRAWABLE: String = "drawable"

@BindingAdapter(value = ["sessionStart", "sessionEnd", "sessionRoom"], requireAll = true)
fun sessionDurationAndLocation(
    textView: TextView,
    startTime: ZonedDateTime,
    endTime: ZonedDateTime,
    room: String
) {
    textView.text = textView.context.getString(
            R.string.session_duration_location,
            durationAndLocationString(textView.context, Duration.between(startTime, endTime)), room
    )
}

private fun durationAndLocationString(context: Context, duration: Duration): String {
    val hours = duration.toHours()
    return if (hours > 0L) {
        context.resources.getQuantityString(R.plurals.duration_hours, hours.toInt(), hours)
    } else {
        val minutes = duration.toMinutes()
        context.resources.getQuantityString(R.plurals.duration_minutes, minutes.toInt(), minutes)
    }
}

@BindingAdapter(value = ["sessionStart"])
fun sessionTimeIcon(
    imageView: ImageView,
    startTime: ZonedDateTime
) {
    imageView.setImageResource(findTimeResource(imageView.context, startTime))
}

private fun findTimeResource(context: Context, startTime: ZonedDateTime): Int {

    // TODO: Revise code based on deliver of final time icons + add time zone checks (remove Log).
    val hour = if(startTime.hour > 12) {
        startTime.hour - 12
    } else {
        startTime.hour
    }

    // TODO: Verify start times are only in 00 and 30 offline.
    val minute = if(startTime.minute == 30) {
        startTime.minute
    } else {
        0
    }

    // TODO: Swap placeholder w/ dynamic name once final time assets are delivered & remove log.
    Timber.d(TAG, "Actual time: ${startTime.hour}:${startTime.minute}")
    Timber.d(TAG, "ic_time_$hour$minute")

    return context.resources.getIdentifier("ic_time_placeholder", DRAWABLE, context.packageName)
}
