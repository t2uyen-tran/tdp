package com.example.assetmonitoring.ui.main

import androidx.lifecycle.ViewModel
import java.util.*

class CasesViewModel: ViewModel() {

    var currentIndex = 0

    //this should be read from Database once setup - hardcode the list for the time being
    private val caseBank = listOf(
        Cases(1000001,
            "Footpath",
            "Potholes",
            Date(5/2/2022),
            "9 Sorrento St., Broadmeadows 3047",
            "",
            "",
            "",
            "2 to 5 days",
            1,
            "",
            "CW-00004",
            "",
            NotifyUpdate = false,
            ""),
        Cases(1000002,
            "Footpath",
            "Raised wooden edging",
            Date(5/13/2022),
            "118 Pascoe Vale Rd., Broadmeadows 3047",
            "",
            "",
            "",
            "2 to 5 days",
            1,
            "",
            "CW-00017",
            "",
            NotifyUpdate = true,
            "103196737@student.swin.edu.au"),
        Cases(1000003,
            "Road",
            "Oil on road",
            Date(5/14/2022),
            "7 Nathalia St., Broadmeadows 3047",
            "",
            "",
            "",
            "2 to 5 days",
            1,
            "",
            "To be assigned",
            "",
            NotifyUpdate = true,
            "103196737@student.swin.edu.au")
    )



    val currCase: Cases
        get() = caseBank[currentIndex]

}