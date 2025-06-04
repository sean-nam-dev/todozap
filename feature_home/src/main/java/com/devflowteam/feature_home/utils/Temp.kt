package com.devflowteam.feature_home.utils

import com.devflowteam.domain.model.Status
import com.devflowteam.domain.model.ToDo

object Temp {
    fun getItems() = listOf(
        // PENDING
        ToDo(1, "Buy groceries", "Need to buy milk, eggs, bread, and some vegetables for the week. Don’t forget to check for discounts on fruits.", Status.PENDING, "2025-10-01"),
        ToDo(2, "Read book", "Finish chapter 4 of 'Clean Architecture' and write for the main concepts to apply later. Finish chapter 4 of 'Clean Architecture' and write down notes for the main concepts to apply later.", Status.PENDING, "2025-11-02"),
        ToDo(3, "Workout", "Leg day at the gym: squats, lunges, deadlifts. Aim for consistency this week and track progress.", Status.PENDING, "2025-12-03"),
        ToDo(4, "Plan trip", "Research flights and accommodations for the upcoming vacation to Spain. Focus on affordable yet central hotels.", Status.PENDING, "2025-08-04"),
        ToDo(5, "Write blog", "Draft a technical blog post on useful Kotlin tips and patterns used in production-ready Android apps. Draft a technical blog post on useful Kotlin tips and patterns used in production-ready Android apps.", Status.PENDING, "2025-09-05"),
        ToDo(6, "Call mom", "Have a long conversation. Ask about her recent doctor visit too. Have a long conversation. Ask about her recent doctor visit too.", Status.PENDING, "2025-10-06"),
        ToDo(7, "Clean garage", "Sort out tools, clean shelves, throw away broken items, and arrange everything for easy access.", Status.PENDING, "2025-11-07"),
        ToDo(8, "Learn Compose", "Follow the official Jetpack Compose tutorial, build a sample UI and experiment with layouts.", Status.PENDING, "2025-12-08"),

        // OVERDUE
        ToDo(9, "Dentist appointment", "Was supposed to visit for a cleaning session last week. Call the clinic and reschedule. Was supposed to visit for a cleaning session last week. Call the clinic and reschedule.", Status.OVERDUE, "2025-01-07"),
        ToDo(10, "Pay electricity bill", "Forgot to pay the last bill. Check the due amount and pay immediately to avoid penalties. Forgot to pay the last bill. Check the due amount and pay immediately to avoid penalties.", Status.OVERDUE, "2025-01-08"),
        ToDo(11, "Submit report", "Compile the monthly performance report with all metrics, graphs, and summaries. Compile the monthly performance report with all metrics, graphs, and summaries.", Status.OVERDUE, "2025-01-09"),
        ToDo(12, "Water plants", "Backyard plants haven't been watered since last weekend. Some might already be wilting.", Status.OVERDUE, "2025-02-10"),
        ToDo(13, "Buy birthday gift", "Choose a meaningful gift for Sarah. She likes books, art supplies, or something handmade.", Status.OVERDUE, "2025-02-11"),
        ToDo(14, "Fix bike", "Rear tire is flat and brakes are squeaky. Visit a repair shop or do it manually at home. Rear tire is flat and brakes are squeaky. Visit a repair shop or do it manually at home.", Status.OVERDUE, "2025-03-12"),
        ToDo(15, "Renew insurance", "Car insurance expires soon. Look for better deals and ensure uninterrupted coverage.", Status.OVERDUE, "2025-04-13"),
        ToDo(16, "Schedule meeting", "Coordinate with the team to find a suitable time for next week's planning session.", Status.OVERDUE, "2025-05-14"),

        // COMPLETE
        ToDo(17, "Finish taxes", "Collected all documents, used the online portal to submit returns successfully before the deadline.", Status.COMPLETE, "2025-06-15"),
        ToDo(18, "Upgrade laptop", "Installed 16GB RAM and a 1TB SSD. System performance.", Status.COMPLETE, "2025-07-16"),
        ToDo(19, "Watch movie", "Finally watched 'The Matrix' after many recommendations. It lived up to the hype! Finally watched 'The Matrix' after many recommendations. It lived up to the hype!", Status.COMPLETE, "2025-08-17"),
        ToDo(20, "Organize bookshelf", "Sorted books by genre and author. Donated the ones I won’t reread to the local library. Sorted books by genre and author. Donated the ones I won’t reread to the local library.", Status.COMPLETE, "2025-09-18"),
        ToDo(21, "Learn Koin", "Integrated Koin into a sample Android project. Understood scopes and modules clearly.", Status.COMPLETE, "2025-10-19"),
        ToDo(22, "Do laundry", "Washed and dried clothes, folded and organized them by category into the wardrobe. Washed and dried clothes, folded and organized them by category into the wardrobe.", Status.COMPLETE, "2025-11-20"),
        ToDo(23, "Refactor code", "Improved ViewModel logic and separated concerns. Code is now cleaner and easier to maintain.", Status.COMPLETE, "2025-12-21"),
        ToDo(24, "Practice meditation", "Completed 10-minute guided sessions daily for a week.", Status.COMPLETE, "2025-12-22")
    )
}