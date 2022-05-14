package com.example.assetmonitoring.ui.main
//SL

import androidx.lifecycle.ViewModel
import com.example.assetmonitoring.R
import java.util.*

class CasesViewModel: ViewModel() {

    var currentIndex = 0

    //SL: this should be read from Database once setup - hardcode the list for the time being
    private val caseBank = listOf(
        Cases(1000001,
            "Footpath",
            "Rough surfaces",
            Date(5/2/2022),
            "9 Sorrento St., Broadmeadows 3047",
            R.drawable.grandcanyon,
            "",
            "In Progress",
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
            R.drawable.noimage,
            "",
            "In Progress",
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
            R.drawable.grandcanyon,
            "",
            "Resolved",
            "2 to 5 days",
            1,
            "",
            "To be assigned",
            "",
            NotifyUpdate = true,
            "103196737@student.swin.edu.au"),
        Cases(1000004,
            "Footpath",
            "Lifted or broken slabs",
            Date(5/10/2022),
            "3 Rau Crt., Broadmeadows 3047",
            R.drawable.grandcanyon,
            "",
            "Wait for third party",
            "2 to 5 days",
            1,
            "",
            "To be assigned",
            "",
            NotifyUpdate = false,
            ""),
        Cases(1000005,
            "Road",
            "Falling tree",
            Date(5/10/2022),
            "7 Rau Crt., Broadmeadows 3047",
            R.drawable.grandcanyon,
            "",
            "In Progress",
            "2 to 5 days",
            1,
            "",
            "To be assigned",
            "",
            NotifyUpdate = false,
            "")
    )



    val currCase: Cases
        get() = caseBank[currentIndex]

}