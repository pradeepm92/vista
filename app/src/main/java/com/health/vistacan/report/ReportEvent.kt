package com.health.vistacan.report

sealed class ReportEvent {

    object NewSearchEvent : ReportEvent()

    object NextPageEvent : ReportEvent()

    // restore after process death
    object RestoreStateEvent: ReportEvent()
}