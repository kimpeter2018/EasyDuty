package com.example.oneduty

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
) {
    object Calendar : BottomNavItem(R.string.text_calendar,  R.drawable.baseline_home_24, CALENDAR)
    object Timeline : BottomNavItem(R.string.text_list, R.drawable.baseline_list_24, LIST)
    object Settings : BottomNavItem(R.string.text_profile, R.drawable.baseline_person_24, PROFILE)
}